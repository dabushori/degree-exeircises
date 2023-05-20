from ex2 import SVM, min_max, z_score
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

with open(os.path.join('logs',f'svm_log_{num}.csv'), 'w') as k_log:
    print('k_folds,norm,eta,lambda,train loss,test loss',file=k_log)
    for k_folds in range(2,21, 2):
        for norm in [min_max, z_score, None]:
            for i in range(1, 11):
            # for i in range(1, 101):
                for j in range(1,11):
                    # eta = np.round(0.01 * i, decimals=4)
                    eta = np.round(0.1 * i, decimals=4)
                    lmbda = np.round(0.1 * j, decimals=4)
                    # python ex2.py .\train_x.txt .\train_y.txt .\test_x.txt .\output.txt
                    # ex2.main(k=k, argv=['ex2.py', 'train_x.txt', 'train_y.txt', 'test_x.txt', f'output_{k}.txt'])
                    print(f'{k_folds},{strings[norm]},{eta},{lmbda}', file=k_log, end='')
                    for _ in range(4):
                        svm = SVM(nclasses, eta, lmbda, epochs, dim)
                        svm.train(trainx_data.copy(), trainy_data.copy(), k_folds, norm)
                        testy_data = np.loadtxt('test_y.txt', delimiter=',', dtype=int) # load classes as int instead of floats
                        print(f',{svm.test_validation(svm.w, svm.bias, trainx_data, trainy_data, True, False)},{svm.test_validation(svm.w, svm.bias, testx_data, testy_data, True, False)}', file=k_log, end='')
                    print('', file=k_log)
