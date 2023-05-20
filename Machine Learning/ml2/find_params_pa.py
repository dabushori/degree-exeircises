from ex2 import PA, min_max, z_score
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
with open(os.path.join('logs',f'pa_log_{num}.csv'), 'w') as k_log:
    print('k_folds,norm,train loss,test loss',file=k_log)
    for k_folds in range(2,20):
        for norm in [min_max, z_score, None]:
            print(f'{k_folds},{strings[norm]}', file=k_log, end='')
            for _ in range(16):
                pa = PA(nclasses, epochs, dim)
                pa.train(trainx_data.copy(), trainy_data.copy(), k_folds, norm)
                testy_data = np.loadtxt('test_y.txt', delimiter=',', dtype=int) # load classes as int instead of floats
                print(f',{pa.test_validation(pa.w, pa.bias, trainx_data, trainy_data, True, False)},{pa.test_validation(pa.w, pa.bias, testx_data, testy_data, True, False)}', file=k_log, end='')
            print('', file=k_log)
