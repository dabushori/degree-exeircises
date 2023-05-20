os = ['-win', '-mac', '-unix']
endianess = ['BE', 'LE']
change = ['-keep', '-swap']

def toFile(o, e, ch = 0):
    name = str(o[1: len(o)]).upper() + '_' + e
    if ch != 0:
        if ch == '-swap':
            name = name + '_swapped'
        else:
            name = name + '_keepped'

    return name + '.bin'

f = open('ex2_test.sh', 'wt')

f.write('rm *_*_*.bin\n\n')
f.write('make clean\n\n')
f.write('make\n\n')

for o1 in os:
    for e1 in endianess:
        for o2 in os:
            for e2 in endianess:
                f.write('echo\n')
                f.write('echo checking ' + o1 + e1 + ' to ' + o2 + e2 + '\n')

                ch = ''
                if e1 == e2:
                    ch = '-keep'
                else:
                    ch = '-swap'

                src = toFile(o1, e1)
                dst = toFile(o2, e2, ch)

                shouldBeEqualTo = toFile(o2, e2)

                f.write('./a.out {} {} {} {} {}\n'.format(src, dst, o1, o2, ch))
                f.write('printf \'%d\\n\' $?\n')
                f.write('cmp {} {}\n\n'.format(dst, shouldBeEqualTo))

f.write('echo done!!!\n')
f.write('make clean\n')

f.close()