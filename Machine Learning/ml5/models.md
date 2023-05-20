## Train: 93.95333333333333%
## Validation: 87.58458370108856%
model = CNNModel(_shape_x, _shape_y, _lr, {
        'conv1': {'kernel_depth': 16,
                     'kernel_size': 3,
                     'stride': 1,
                     'padding': 1,
                     },
        'conv2': {'kernel_depth': 32,
                  'kernel_size': 3, 
                  'stride': 2, 
                  'padding': 0
                  },
        'conv3': {'kernel_depth': 64,
                  'kernel_size': 5, 
                  'stride': 1, 
                  'padding': 2
                  },
        'maxpool1': {'kernel_size': 2, 'stride': 2},
        'maxpool2': {'kernel_size': 2, 'stride': 2},
        'maxpool3': {'kernel_size': 2, 'stride': 2},
        'dropout1': {'p': 0.1},
        'dropout2': {'p': 0.25},
        'dropout3': {'p': 0.1},
        'linear1': {'out': 128},
        'linear2': {'out': 64},
        'linear3': {'out': 30},
    })

## Train: 91.83%
## Validation: 86.58428949691086%
model = CNNModel(_shape_x, _shape_y, _lr, {
        'conv1': {'kernel_depth': 16,
                     'kernel_size': 3,
                     'stride': 1,
                     'padding': 1,
                     },
        'conv2': {'kernel_depth': 32,
                  'kernel_size': 3, 
                  'stride': 2, 
                  'padding': 0
                  },
        'conv3': {'kernel_depth': 64,
                  'kernel_size': 5, 
                  'stride': 1, 
                  'padding': 2
                  },
        'avgpool1': {'kernel_size': 2, 'stride': 2},
        'avgpool2': {'kernel_size': 2, 'stride': 2},
        'avgpool3': {'kernel_size': 2, 'stride': 2},
        'dropout1': {'p': 0.1},
        'dropout2': {'p': 0.25},
        'dropout3': {'p': 0.1},
        'linear1': {'out': 128},
        'linear2': {'out': 64},
        'linear3': {'out': 30},
    })
    
## Train: 27831, (92.77%)
## Validation: 5896, (86.73139158576052%)
same but batch size is 8



## Test: 87.90051207022677%
## Train: 94.76333333333334%
## Validation: 88.59958811415122%
model = CNNModel(_shape_x, _shape_y, _lr, {
        'conv1': {'kernel_depth': 64,
                     'kernel_size': 3,
                     'stride': 1,
                     'padding': 1,
                     },
        'conv2': {'kernel_depth': 128,
                  'kernel_size': 3, 
                  'stride': 2, 
                  'padding': 0
                  },
        'conv3': {'kernel_depth': 256,
                  'kernel_size': 5, 
                  'stride': 1, 
                  'padding': 2
                  },
        'avgpool1': {'kernel_size': 2, 'stride': 2},
        'avgpool2': {'kernel_size': 2, 'stride': 2},
        'avgpool3': {'kernel_size': 2, 'stride': 2},
        'dropout1': {'p': 0.1},
        'dropout2': {'p': 0.25},
        'dropout3': {'p': 0.1},
        'linear1': {'out': 128},
        'linear2': {'out': 64},
        'linear3': {'out': 30},
    })

# Resnet Model

{
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
```
10 epochs by loss:
Test: 6385 out of 6835, (93.41623994147768%)
30000
Train: 29377 out of 30000, (97.92333333333333%)
6798
Validation: 6332 out of 6798, (93.14504265960576%)
Measured time: 3632.687003135681 seconds, which is 60.0 minutes and 32.68700313568115 seconds.

10 epochs by correct:
Test: 6385 out of 6835, (93.41623994147768%)
30000
Train: 29292 out of 30000, (97.64%)
6798
Validation: 6325 out of 6798, (93.042071197411%)
Measured time: 3517.2579181194305 seconds, which is 58.0 minutes and 37.25791811943054 seconds.
```