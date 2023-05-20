import numpy as np
import sys

# misc functions
def distance(x, y):
    return np.dot(x - y, x - y) ** 0.5

def shuffle(x,y):
    rand_state = np.random.get_state()
    np.random.shuffle(x)
    np.random.set_state(rand_state)
    np.random.shuffle(y)

class KNN:
    def __init__(self, k, nclasses):
        self.k = k
        self.nclasses = nclasses
        
    def set_data(self, x_data, y_data, norm_func = None):
        self.norm_func = norm_func(x_data) if norm_func else lambda x:x
        x_data = np.array([self.norm_func(x) for x in x_data])
        self.data = list(zip(x_data, y_data))
        
    def get_class(self, x):
        for x_val, y_val in self.data:
            if (x==x_val).all():
                return y_val
        return None
        
    def predict(self, x):
        x = self.norm_func(x)
        dists = []
        for x_val, _ in self.data:
            d = distance(x_val, x)
            dists.append((d, x_val))
        dists = sorted(dists, key=lambda x:x[0])
        classes = np.zeros(self.nclasses)
        for d, x_val in dists[0:self.k]:
            classes[self.get_class(x_val)] += 1
        return np.argmax(classes)
    
    def test_validation(self, validation_x, validation_y):
        loss = 0
        for x, y in zip(validation_x, validation_y):
            if self.predict(x) != y:
                loss += 1
        return loss
        

class Perceptron:
    def __init__(self, nclasses, eta, epochs, dim):
        self.nclasses = nclasses
        self.eta = eta
        self.epochs = epochs
        self.dim = dim
        
    def test_validation(self, w, bias, validation_x, validation_y, is_normed = True):
        if not is_normed:
            validation_x = np.array([self.norm_func(a) for a in validation_x])
        loss = 0
        for x, y in zip(validation_x, validation_y):
            if np.argmax(np.dot(w, x) + bias) != y:
                loss += 1
        return loss

    def train(self, trainx_data, trainy_data, k_folds, norm_func = None):
        if k_folds < 2:
            print('Error - k_folds must be >= 1')
            exit(1)
        
        self.norm_func = norm_func(trainx_data) if norm_func else lambda x:x
        trainx_data = np.array([self.norm_func(x) for x in trainx_data])
        
        res_w = np.zeros((self.nclasses, self.dim))
        res_bias = 0
        
        validation_len = len(trainx_data) // k_folds
        last_len = validation_len
        
        for val_idx in range(k_folds):
            if val_idx == k_folds - 1:
                last_len += len(trainx_data) % k_folds
        
            validation_x = trainx_data[val_idx * validation_len: val_idx * validation_len + last_len]
            training_x = np.delete(trainx_data, np.s_[val_idx * validation_len: val_idx * validation_len + last_len], axis=0)
            validation_y = trainy_data[val_idx * validation_len: val_idx * validation_len + last_len]
            training_y = np.delete(trainy_data, np.s_[val_idx * validation_len: val_idx * validation_len + last_len], axis=0)
            
            min_loss = np.inf
            w = np.zeros((self.nclasses, self.dim))
            bias = np.zeros(self.nclasses)
            for _ in range(self.epochs):
                shuffle(training_x, training_y)
                for x, y in zip(training_x, training_y):
                    yhat = np.argmax(np.dot(w, x) + bias)
                    if y != yhat:
                        w[y, :] += self.eta * x
                        bias[y] += self.eta
                        w[yhat, :] -= self.eta * x
                        bias[yhat] -= self.eta

                # if w is better, update w
                curr_loss = self.test_validation(w, bias, validation_x, validation_y)
                if curr_loss < min_loss:
                    min_loss = curr_loss
                    fold_w = w
                    fold_bias = bias
            
            res_w += fold_w
            res_bias += fold_bias
            
        self.w = res_w / k_folds
        self.bias = res_bias / k_folds

    def predict(self, x):
        return np.argmax(np.dot(self.w, self.norm_func(x)) + self.bias)


class SVM:
    def __init__(self, nclasses, eta, lmbda, epochs, dim):
        self.nclasses = nclasses
        self.eta = eta
        self.lmbda = lmbda
        self.epochs = epochs
        self.dim = dim
        
    def test_validation(self, w, bias, validation_x, validation_y, zero_one = False, is_normed = True):
        if not is_normed:
            validation_x = np.array([self.norm_func(a) for a in validation_x])
        if zero_one:
            loss = 0
            for x, y in zip(validation_x, validation_y):
                if np.argmax(np.dot(w, x) + bias) != y:
                    loss += 1
            return loss
        else:
            loss = 0
            for x, y in zip(validation_x, validation_y):
                loss += self.loss(x,y,w,bias)
            return loss
        
    def loss(self, x, y, w, bias, is_normed = True):
        if not is_normed:
            x = self.norm_func(x)    
        res = np.dot(w, x) + bias
        res_y = res[y]
        res[y] = -np.inf
        yhat = np.argmax(res)
        return max(0, 1 - res_y + res[yhat])
        
    def train(self, trainx_data, trainy_data, k_folds, norm_func = None):
        if k_folds < 2:
            print('Error - k_folds must be >= 1')
            exit(1)
        
        self.norm_func = norm_func(trainx_data) if norm_func else lambda x:x
        trainx_data = np.array([self.norm_func(x) for x in trainx_data])
        
        res_w = np.zeros((self.nclasses, self.dim))
        res_bias = np.zeros(self.nclasses)
        
        validation_len = len(trainx_data) // k_folds
        last_len = validation_len
        
        for val_idx in range(k_folds):
            if val_idx == k_folds - 1:
                last_len += len(trainx_data) % k_folds
        
            validation_x = trainx_data[val_idx * validation_len: val_idx * validation_len + last_len]
            training_x = np.delete(trainx_data, np.s_[val_idx * validation_len: val_idx * validation_len + last_len], axis=0)
            validation_y = trainy_data[val_idx * validation_len: val_idx * validation_len + last_len]
            training_y = np.delete(trainy_data, np.s_[val_idx * validation_len: val_idx * validation_len + last_len], axis=0)
            
            min_loss = np.inf
            fold_w = None
            fold_bias = None
            w = np.zeros((self.nclasses, self.dim))
            bias = np.zeros(self.nclasses)
            for _ in range(self.epochs):
                shuffle(training_x, training_y)
                for x, y in zip(training_x, training_y):
                    
                    res = np.dot(w, x) + bias
                    res[y] = -np.inf
                    r = np.argmax(res)
                    
                    if self.loss(x, y, w, bias) > 0:
                        for i in range(self.nclasses):
                            if i == y:
                                w[y, :] = (1 - self.eta * self.lmbda) * w[y, :] + self.eta * x
                                bias[y] = (1 - self.eta * self.lmbda) * bias[y] + self.eta
                            elif i == r:
                                w[r, :] = (1 - self.eta * self.lmbda) * w[r, :] - self.eta * x
                                bias[r] = (1 - self.eta * self.lmbda) * bias[r] - self.eta
                            else:
                                w[i, :] = (1 - self.eta * self.lmbda) * w[i, :]
                                bias[i] = (1 - self.eta * self.lmbda) * bias[i]
                    else:
                        for i in range(self.nclasses):
                            w[i, :] = (1 - self.eta * self.lmbda) * w[i, :]
                            bias[i] = (1 - self.eta * self.lmbda) * bias[i]
                        
                curr_loss = self.test_validation(w, bias, validation_x, validation_y)
                if curr_loss < min_loss:
                    min_loss = curr_loss
                    fold_w = w
                    fold_bias = bias
            
            res_w += fold_w
            res_bias += fold_bias
            
        self.w = res_w / k_folds
        self.bias = res_bias / k_folds

    def predict(self, x):
        return np.argmax(np.dot(self.w, self.norm_func(x)) + self.bias)


class PA:
    def __init__(self, nclasses, epochs, dim):
        self.nclasses = nclasses
        self.epochs = epochs
        self.dim = dim
        
    def test_validation(self, w, bias, validation_x, validation_y, zero_one = False, is_normed = True):
        if not is_normed:
            validation_x = np.array([self.norm_func(a) for a in validation_x])
        if zero_one:
            loss = 0
            for x, y in zip(validation_x, validation_y):
                if np.argmax(np.dot(w, x) + bias) != y:
                    loss += 1
            return loss
        else:
            loss = 0
            for x, y in zip(validation_x, validation_y):
                loss += self.loss(x,y,w,bias)
            return loss
        
    def loss(self, x, y, w, bias, is_normed = True):
        if not is_normed:
            x = self.norm_func(x)
        res = np.dot(w, x) + bias
        res_y = res[y]
        res[y] = -np.inf
        yhat = np.argmax(res)
        return max(0, 1 - res_y + res[yhat])
        
    def train(self, trainx_data, trainy_data, k_folds, norm_func = None):
        if k_folds < 2:
            print('Error - k_folds must be >= 1')
            exit(1)
        
        self.norm_func = norm_func(trainx_data) if norm_func else lambda x:x
        trainx_data = np.array([self.norm_func(x) for x in trainx_data])
        
        res_w = np.zeros((self.nclasses, self.dim))
        res_bias = np.zeros(self.nclasses)
        
        validation_len = len(trainx_data) // k_folds
        last_len = validation_len
        
        for val_idx in range(k_folds):
            if val_idx == k_folds - 1:
                last_len += len(trainx_data) % k_folds
        
            validation_x = trainx_data[val_idx * validation_len: val_idx * validation_len + last_len]
            training_x = np.delete(trainx_data, np.s_[val_idx * validation_len: val_idx * validation_len + last_len], axis=0)
            validation_y = trainy_data[val_idx * validation_len: val_idx * validation_len + last_len]
            training_y = np.delete(trainy_data, np.s_[val_idx * validation_len: val_idx * validation_len + last_len], axis=0)
            
            min_loss = np.inf
            fold_w = None
            fold_bias = None
            w = np.zeros((self.nclasses, self.dim))
            bias = np.zeros(self.nclasses)
            for _ in range(self.epochs):
                shuffle(training_x, training_y)
                for x, y in zip(training_x, training_y):
                    tau = self.loss(x, y, w, bias) / (2 * np.linalg.norm(x,0))
                    
                    res = np.dot(w, x) + bias
                    res[y] = -np.inf
                    r = np.argmax(res)
                    
                    w[y, :] += tau * x
                    bias[y] += tau
                    w[r, :] -= tau * x
                    bias[r] -= tau
                    
                curr_loss = self.test_validation(w, bias, validation_x, validation_y)
                if curr_loss < min_loss:
                    min_loss = curr_loss
                    fold_w = w
                    fold_bias = bias
            
            res_w += fold_w
            res_bias += fold_bias
            
        self.w = res_w / k_folds
        self.bias = res_bias / k_folds

    def predict(self, x):
        return np.argmax(np.dot(self.w, self.norm_func(x)) + self.bias)


# data normalization functions
def z_score(data):
    mean = np.mean(data, axis=0)
    stand_dev = np.std(data, axis=0)
    return lambda x: (x - mean) / stand_dev
    # return np.array([(x - mean) / stand_dev for x in data])
    
def min_max(data, new_min = 0, new_max = 1):
    min = np.min(data, axis=0)
    max = np.max(data, axis=0)
    new_diff = new_max - new_min
    diff = max - min
    return lambda x: ((x - min) * new_diff / diff) + new_min
    # return np.array([((x - min) * new_diff / diff) + new_min for x in data])

# main function
def main():
    if len(sys.argv) < 5:
        print('Usage: python ex2.py <train_x_path> <train_y_path> <test_x_path> <output_log_name>')
        exit(0)

    trainx_path, trainy_path, testx_path, output_fname = sys.argv[1:5]
    trainx_data = np.loadtxt(trainx_path, delimiter=',')
    trainy_data = np.loadtxt(trainy_path, delimiter=',', dtype=int) # load classes as int instead of floats
    testx_data = np.loadtxt(testx_path, delimiter=',')
    
    with open(output_fname, 'w') as output_log:
        # Exercise constants
        nclasses = 3
        dim = 5 
        # KNN params
        knn_k = 5
        knn_norm = min_max
        knn = KNN(knn_k, nclasses)
        # Perceptron params
        perceptron_eta = 0.8
        perceptron_epochs = 200
        perceptron_k_folds = 17
        perceptron_norm = z_score
        perceptron = Perceptron(nclasses, perceptron_eta, perceptron_epochs, dim)
        # SVM params
        svm_eta = 0.1
        svm_lmbda = 0.001
        svm_epochs = 200
        svm_k_folds = 10
        svm_norm = z_score
        svm = SVM(nclasses, svm_eta, svm_lmbda, svm_epochs, dim)
        # PA params
        pa_epochs = 200
        pa_norm = z_score
        pa_k_folds = 10
        pa = PA(nclasses, pa_epochs, dim)
        
        # train all the algorithms (or set the data in KNN's case)
        knn.set_data(trainx_data.copy(), trainy_data.copy(), knn_norm)
        perceptron.train(trainx_data.copy(), trainy_data.copy(), perceptron_k_folds, perceptron_norm)
        svm.train(trainx_data.copy(), trainy_data.copy(), svm_k_folds, svm_norm)
        pa.train(trainx_data.copy(), trainy_data.copy(), pa_k_folds, pa_norm)
        
        # for testing
        print(f'KNN train set loss = {knn.test_validation(trainx_data, trainy_data)} / {len(trainx_data)}')
        print(f'Perceptron train set loss = {perceptron.test_validation(perceptron.w, perceptron.bias, trainx_data, trainy_data, False)} / {len(trainx_data)}')
        print(f'SVM train set loss = {svm.test_validation(svm.w, svm.bias, trainx_data, trainy_data, True, False)} / {len(trainx_data)}')
        print(f'PA train set loss = {pa.test_validation(pa.w, pa.bias, trainx_data, trainy_data, True, False)} / {len(trainx_data)}')
        print()
        testy_data = np.loadtxt('test_y.txt', delimiter=',', dtype=int) # load classes as int instead of floats
        print(f'KNN test set loss = {knn.test_validation(testx_data, testy_data)} / {len(testx_data)}')
        print(f'Perceptron test set loss = {perceptron.test_validation(perceptron.w, perceptron.bias, testx_data, testy_data, False)} / {len(testx_data)}')
        print(f'SVM test set loss = {svm.test_validation(svm.w, svm.bias, testx_data, testy_data, True, False)} / {len(testx_data)}')
        print(f'PA test set loss = {pa.test_validation(pa.w, pa.bias, testx_data, testy_data, True, False)} / {len(testx_data)}')
        
        if testx_data.ndim == 1:
            knn_yhat = knn.predict(testx_data)
            perceptron_yhat = perceptron.predict(testx_data)
            svm_yhat = svm.predict(testx_data)
            pa_yhat = pa.predict(testx_data)
            output_log.write(f'knn: {knn_yhat}, perceptron: {perceptron_yhat}, svm: {svm_yhat}, pa: {pa_yhat}\n')
        else:
            for x in testx_data:
                knn_yhat = knn.predict(x)
                perceptron_yhat = perceptron.predict(x)
                svm_yhat = svm.predict(x)
                pa_yhat = pa.predict(x)
                output_log.write(f'knn: {knn_yhat}, perceptron: {perceptron_yhat}, svm: {svm_yhat}, pa: {pa_yhat}\n')


if __name__ == '__main__':
    import time, math
    start = time.time()
    main()
    end = time.time()
    delta = math.ceil(float(end-start))
    print(f'\nTime: {delta}sec, which is {delta // 60} minutes and {delta % 60} seconds')