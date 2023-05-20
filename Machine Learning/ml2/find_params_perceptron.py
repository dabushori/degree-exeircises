from ex2 import Perceptron, min_max, z_score
import os, sys, numpy as np

if len(sys.argv) < 4:
    print('Usage: python ex2.py <train_x_path> <train_y_path> <test_x_path>')
    exit(0)
    
trainx_path, trainy_path, testx_path = sys.argv[1:4]
trainx_data = np.loadtxt(trainx_path, delimiter=',')
trainy_data = np.loadtxt(trainy_path, delimiter=',', dtype=int) # load classes as int instead of floats
testx_data = np.loadtxt(testx_path, delimiter=',')

nclasses = 3
dim = 5
epochs = 100

strings = {
    min_max : 'min_max',
    z_score : 'z_score',
    None: 'none'
}

num = sys.argv[4] if len(sys.argv) > 4 else ''

with open(os.path.join('logs',f'perceptron_log_{num}.csv'), 'w') as k_log:
    print('k_folds,norm,eta,train loss,test loss',file=k_log)
    for k_folds in range(2,21):
        for norm in [min_max, z_score, None]:
            # for i in range(1, 11):
            for i in range(1, 101):
                eta = np.round(0.01 * i, decimals=4)
                # eta = np.round(0.1 * i, decimals=4)
                print(f'{k_folds},{strings[norm]},{eta}', end='', file=k_log)
                for _ in range(4):
                    # python ex2.py .\train_x.txt .\train_y.txt .\test_x.txt .\output.txt
                    # ex2.main(k=k, argv=['ex2.py', 'train_x.txt', 'train_y.txt', 'test_x.txt', f'output_{k}.txt'])
                    perceptron = Perceptron(nclasses, eta, epochs, dim)
                    perceptron.train(trainx_data.copy(), trainy_data.copy(), k_folds, norm)
                    testy_data = np.loadtxt('test_y.txt', delimiter=',', dtype=int) # load classes as int instead of floats
                    print(f',{perceptron.test_validation(perceptron.w, perceptron.bias, trainx_data, trainy_data, False)},{perceptron.test_validation(perceptron.w, perceptron.bias, testx_data, testy_data, False)}', file=k_log, end='')
                print('', file=k_log)
