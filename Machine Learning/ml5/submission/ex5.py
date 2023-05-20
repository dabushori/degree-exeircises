import torch
from torch.nn import *
import torch.nn.functional as F
import numpy as np
import os
import os.path
import soundfile as sf
import librosa
import numpy as np
import torch
from torch.nn.modules import padding
import torch.utils.data as data


DEVICE = torch.device('cuda:0' if torch.cuda.is_available() else 'cpu')

AUDIO_EXTENSIONS = [
    '.wav', '.WAV',
]


def is_audio_file(filename):
    return any(filename.endswith(extension) for extension in AUDIO_EXTENSIONS)


def find_classes(dir):
    classes = [d for d in os.listdir(
        dir) if os.path.isdir(os.path.join(dir, d))]
    classes.sort()
    class_to_idx = {classes[i]: i for i in range(len(classes))}
    return classes, class_to_idx


def make_dataset(dir, class_to_idx):
    spects = []
    dir = os.path.expanduser(dir)
    for target in sorted(os.listdir(dir)):
        d = os.path.join(dir, target)
        if not os.path.isdir(d):
            continue

        for root, _, fnames in sorted(os.walk(d)):
            for fname in sorted(fnames, key=lambda x: int(os.path.splitext(x)[0]) if os.path.splitext(x)[0].isnumeric() else x):
                if is_audio_file(fname):
                    path = os.path.join(root, fname)
                    item = (path, class_to_idx[target])
                    spects.append(item)
    return spects


def spect_loader(path, window_size, window_stride, window, normalize, max_len=101):
    y, sr = sf.read(path)
    # n_fft = 4096
    n_fft = int(sr * window_size)
    win_length = n_fft
    hop_length = int(sr * window_stride)

    # STFT
    D = librosa.stft(y, n_fft=n_fft, hop_length=hop_length,
                     win_length=win_length, window=window)
    spect, phase = librosa.magphase(D)

    # S = log(S+1)
    spect = np.log1p(spect)

    # make all spects with the same dims
    # TODO: change that in the future
    if spect.shape[1] < max_len:
        pad = np.zeros((spect.shape[0], max_len - spect.shape[1]))
        spect = np.hstack((spect, pad))
    elif spect.shape[1] > max_len:
        spect = spect[:, :max_len]
    spect = np.resize(spect, (1, spect.shape[0], spect.shape[1]))
    spect = torch.FloatTensor(spect)

    # z-score normalization
    if normalize:
        mean = spect.mean()
        std = spect.std()
        if std != 0:
            spect.add_(-mean)
            spect.div_(std)

    return spect


class GCommandLoader(data.Dataset):
    """A google command data set loader where the wavs are arranged in this way: ::
        root/one/xxx.wav
        root/one/xxy.wav
        root/one/xxz.wav
        root/head/123.wav
        root/head/nsdf3.wav
        root/head/asd932_.wav
    Args:
        root (string): Root directory path.
        transform (callable, optional): A function/transform that  takes in an PIL image
            and returns a transformed version. E.g, ``transforms.RandomCrop``
        target_transform (callable, optional): A function/transform that takes in the
            target and transforms it.
        window_size: window size for the stft, default value is .02
        window_stride: window stride for the stft, default value is .01
        window_type: typye of window to extract the stft, default value is 'hamming'
        normalize: boolean, whether or not to normalize the spect to have zero mean and one std
        max_len: the maximum length of frames to use
     Attributes:
        classes (list): List of the class names.
        class_to_idx (dict): Dict with items (class_name, class_index).
        spects (list): List of (spects path, class_index) tuples
        STFT parameter: window_size, window_stride, window_type, normalize
    """

    def __init__(self, root, transform=None, target_transform=None, window_size=.02,
                 window_stride=.01, window_type='hamming', normalize=True, max_len=101):
        classes, class_to_idx = find_classes(root)
        # print(classes, class_to_idx)
        spects = make_dataset(root, class_to_idx)

        if len(spects) == 0:
            raise (RuntimeError("Found 0 sound files in subfolders of: " + root +
                                "Supported audio file extensions are: " + ",".join(AUDIO_EXTENSIONS)))

        self.root = root
        self.spects = spects
        self.classes = classes
        self.class_to_idx = class_to_idx
        self.transform = transform
        self.target_transform = target_transform
        self.loader = spect_loader
        self.window_size = window_size
        self.window_stride = window_stride
        self.window_type = window_type
        self.normalize = normalize
        self.max_len = max_len
        self.len = len(self.spects)

    def __getitem__(self, index):
        """
        Args:
            index (int): Index
        Returns:
            tuple: (spect, target) where target is class_index of the target class.
        """
        # print(index)
        path, target = self.spects[index]
        spect = self.loader(path, self.window_size, self.window_stride,
                            self.window_type, self.normalize, self.max_len)
        # print (path)
        if self.transform is not None:
            spect = self.transform(spect)
        if self.target_transform is not None:
            target = self.target_transform(target)

        return spect, target, os.path.basename(path)

    def __len__(self):
        return self.len



class ResNetModel(Module):
    def __init__(self, _shape_x, _shape_y, params):
        super().__init__()

        self.resnet_layers = Sequential(
            Conv2d(1, params['conv0']['kernel_depth'], kernel_size=params['conv0']['kernel_size'],
                   stride=params['conv0']['stride'], padding=params['conv0']['padding'], device=DEVICE),
            BatchNorm2d(params['conv0']['kernel_depth'], device=DEVICE),
            ReLU(inplace=True),

            make_layer(params['conv1']['kernel_size'], params['conv0']['kernel_depth'], params['conv1']['kernel_depth'],
                       params['conv1']['stride'], params['conv1']['padding'], params['conv1']['p']),
            MaxPool2d(params['maxpool1']['kernel_size'],
                      params['maxpool1']['stride']),
            make_layer(params['conv2']['kernel_size'], params['conv1']['kernel_depth'], params['conv2']['kernel_depth'],
                       params['conv2']['stride'], params['conv2']['padding'], params['conv2']['p']),
            MaxPool2d(params['maxpool2']['kernel_size'],
                      params['maxpool2']['stride']),
            make_layer(params['conv3']['kernel_size'], params['conv2']['kernel_depth'], params['conv3']['kernel_depth'],
                       params['conv3']['stride'], params['conv3']['padding'], params['conv3']['p']),
            MaxPool2d(params['maxpool3']['kernel_size'],
                      params['maxpool3']['stride']),
        )


        self.linear_input_shape = self.resnet_layers(torch.zeros(
            1, 1, _shape_x, _shape_y, device=DEVICE)).numel()

        self.linear_layers = Sequential(
            Dropout(p=params['linear1']['p']),
            Linear(self.linear_input_shape, params['linear1']['out'], device=DEVICE),
            BatchNorm1d(params['linear1']['out']),
            ReLU(inplace=True),
            Dropout(p=params['linear2']['p']),
            Linear(params['linear1']['out'], params['linear2']['out'], device=DEVICE),
        )

        self.optimizer = torch.optim.Adam(self.parameters(), lr=0.0004, betas=(0.999, 0.999), eps=1e-08, weight_decay=0, amsgrad=True)

    def forward(self, x):
        x = self.resnet_layers(x)
        x = x.view((-1, self.linear_input_shape))
        x = self.linear_layers(x)
        return F.log_softmax(x, dim=1)



def make_layer(kernel_size, in_channels, kernel_depth, _stride, _padding, _p):
    return Sequential(
        Conv2d(in_channels, kernel_depth, kernel_size=kernel_size,
               stride=_stride, padding=_padding, device=DEVICE),
        BatchNorm2d(kernel_depth, device=DEVICE),
        ReLU(inplace=True),

        # block
        Conv2d(kernel_depth, kernel_depth, stride=_stride,
               padding=_padding, kernel_size=kernel_size, device=DEVICE),
        BatchNorm2d(kernel_depth, device=DEVICE),
        ReLU(inplace=True),
        Dropout(p=_p),
        Conv2d(kernel_depth, kernel_depth, stride=_stride,
               padding=_padding, kernel_size=kernel_size, device=DEVICE),
        BatchNorm2d(kernel_depth, device=DEVICE),
        Conv2d(kernel_depth, kernel_depth, stride=_stride,
               padding=_padding, kernel_size=kernel_size, device=DEVICE),
        BatchNorm2d(kernel_depth, device=DEVICE),

        # block
        Conv2d(kernel_depth, kernel_depth, stride=_stride,
               padding=_padding, kernel_size=kernel_size, device=DEVICE),
        BatchNorm2d(kernel_depth, device=DEVICE),
        ReLU(inplace=True),
        Dropout(p=_p),
        Conv2d(kernel_depth, kernel_depth, stride=_stride,
               padding=_padding, kernel_size=kernel_size, device=DEVICE),
        BatchNorm2d(kernel_depth, device=DEVICE),
        Conv2d(kernel_depth, kernel_depth, stride=_stride,
               padding=_padding, kernel_size=kernel_size, device=DEVICE),
        BatchNorm2d(kernel_depth, device=DEVICE),
    )


def train(model, train_loader):
    global DEVICE
    model.train()
    for (data, labels, _) in train_loader:
        data = data.to(DEVICE)
        labels = labels.to(DEVICE)
        model.optimizer.zero_grad()
        output = model(data)
        loss = F.nll_loss(output, labels)
        loss.backward()
        model.optimizer.step()


def test(model, test_loader, with_results=False):
    results = []

    model.eval()
    test_loss = 0
    correct = 0
    with torch.no_grad():
        for data, target, path in test_loader:
            data = data.to(DEVICE)
            target = target.to(DEVICE)
            output = model(data)
            test_loss += F.nll_loss(output, target, size_average=False).item()
            pred = output.max(1, keepdim=True)[1]
            correct += pred.eq(target.view_as(pred)).cpu().sum()
            if with_results:
                results.extend(list(zip(list(path), list(pred))))

    test_loss /= len(test_loader.dataset)
    # print('Test set: Average loss: {:.4f}, Accuracy: {}/{} ({:.0f}%)'.format(
    #     test_loss, correct, len(test_loader.dataset),
    #     100. * correct / len(test_loader.dataset)))
    return (test_loss, correct.item(), results) if with_results else (test_loss, correct.item())


def main():
    # path_to_data = 'very_small_data'
    path_to_data = 'data'
    state_path = 'state_dict'

    dataset = GCommandLoader(os.path.join(path_to_data, 'train'))
    train_loader = torch.utils.data.DataLoader(
        dataset, batch_size=10, shuffle=True, pin_memory=True)

    dataset = GCommandLoader(os.path.join(path_to_data, 'test'))
    test_loader = torch.utils.data.DataLoader(
        dataset, batch_size=100, shuffle=None,
        num_workers=0, pin_memory=True, sampler=None)

    dataset = GCommandLoader(os.path.join(path_to_data, 'valid'))
    val_loader = torch.utils.data.DataLoader(
        dataset, batch_size=100, shuffle=None,
        num_workers=0, pin_memory=True, sampler=None)

    epochs = 10
    _lr = 0.01
    _shape_x, _shape_y = 161, 101

    params = {
        "conv0": {"kernel_depth": 16,
                    "kernel_size": 3,
                    "stride": 1,
                    "padding": 1,
                    "p": 0.1
                    },
        "conv1": {"kernel_depth": 16,
                "kernel_size": 3,
                "stride": 1,
                "padding": 1,
                "p": 0.1
                },
        "conv2": {"kernel_depth": 32,
                "kernel_size": 3,
                "stride": 1,
                "padding": 1,
                "p": 0.1
                },
        "conv3": {"kernel_depth": 64,
                "kernel_size": 3,
                "stride": 1,
                "padding": 2,
                "p": 0.1
                },
        "maxpool1": {
            "kernel_size": 2, 
            "stride": 3},
        "maxpool2": {
            "kernel_size": 2, 
            "stride": 2},
        "maxpool3": {
            "kernel_size": 3, 
            "stride": 2},
        "linear1": {
            "out": 512,
            "p": 0.4},
        "linear2": {
            "out": 30,
            "p": 0.3
    }
}
    model = ResNetModel(_shape_x, _shape_y, params)
    model.to(DEVICE)

    # train
    min_loss = np.inf
    for i in range(1, epochs+1):
        print(f'epoch {i}')
        train(model, train_loader)
        print('testing validation:')
        loss, correct = test(model, val_loader)
        if loss < min_loss:
            print(f'updating: {min_loss = }, {loss = }')
            min_loss = loss
            # save the model if upgraded
            torch.save(model.state_dict(), state_path)

    # load the best model
    model.load_state_dict(torch.load(state_path))
    # test
    _, _, results = test(model, test_loader, with_results=True)
    classes, _ = find_classes(os.path.join(path_to_data, 'train'))
    with open('test_y', 'w') as testy:
        for path, pred in results:
            testy.write(f'{path},{classes[pred.item()]}\n')
            

if __name__ == '__main__':
    main()
