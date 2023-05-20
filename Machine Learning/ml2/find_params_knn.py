from ex2 import KNN, min_max, z_score
import os, sys, numpy as np

if len(sys.argv) < 4:
    print('Usage: python ex2.py <train_x_path> <train_y_path> <test_x_path>')
    exit(0)
    
trainx_path, trainy_path, testx_path = sys.argv[1:4]
num = sys.argv[4] if len(sys.argv) > 4 else ''
trainx_data = np.loadtxt(trainx_path, delimiter=',')
trainy_data = np.loadtxt(trainy_path, delimiter=',', dtype=int) # load classes as int instead of floats
testx_data = np.loadtxt(testx_path, delimiter=',')

strings = {
    min_max : 'min_max',
    z_score : 'z_score',
    None: 'none'
}

with open(os.path.join('logs',f'knn_log_{num}.csv'), 'w') as k_log:
    print('k,norm,train loss,test loss',file=k_log)
    for norm in [min_max, z_score, None]:
        for k in range(1, 100):
            # python ex2.py .\train_x.txt .\train_y.txt .\test_x.txt .\output.txt
            # ex2.main(k=k, argv=['ex2.py', 'train_x.txt', 'train_y.txt', 'test_x.txt', f'output_{k}.txt'])
            nclasses = 3
            knn = KNN(k, nclasses) # for testing, TODO: change k
            knn.set_data(trainx_data.copy(), trainy_data.copy(), norm)
            testy_data = np.loadtxt('test_y.txt', delimiter=',', dtype=int) # load classes as int instead of floats
            print(f'{k},{strings[norm]},{knn.test_validation(trainx_data, trainy_data)},{knn.test_validation(testx_data, testy_data)}', file=k_log)