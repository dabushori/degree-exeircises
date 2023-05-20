rm *_*_*.bin

make clean

make

echo
echo checking -winBE to -winBE
valgrind ./a.out WIN_BE.bin WIN_BE_keepped.bin -win -win -keep
printf '%d\n' $?
cmp WIN_BE_keepped.bin WIN_BE.bin

echo
echo checking -winBE to -winLE
valgrind ./a.out WIN_BE.bin WIN_LE_swapped.bin -win -win -swap
printf '%d\n' $?
cmp WIN_LE_swapped.bin WIN_LE.bin

echo
echo checking -winBE to -macBE
valgrind ./a.out WIN_BE.bin MAC_BE_keepped.bin -win -mac -keep
printf '%d\n' $?
cmp MAC_BE_keepped.bin MAC_BE.bin

echo
echo checking -winBE to -macLE
valgrind ./a.out WIN_BE.bin MAC_LE_swapped.bin -win -mac -swap
printf '%d\n' $?
cmp MAC_LE_swapped.bin MAC_LE.bin

echo
echo checking -winBE to -unixBE
valgrind ./a.out WIN_BE.bin UNIX_BE_keepped.bin -win -unix -keep
printf '%d\n' $?
cmp UNIX_BE_keepped.bin UNIX_BE.bin

echo
echo checking -winBE to -unixLE
valgrind ./a.out WIN_BE.bin UNIX_LE_swapped.bin -win -unix -swap
printf '%d\n' $?
cmp UNIX_LE_swapped.bin UNIX_LE.bin

echo
echo checking -winLE to -winBE
valgrind ./a.out WIN_LE.bin WIN_BE_swapped.bin -win -win -swap
printf '%d\n' $?
cmp WIN_BE_swapped.bin WIN_BE.bin

echo
echo checking -winLE to -winLE
valgrind ./a.out WIN_LE.bin WIN_LE_keepped.bin -win -win -keep
printf '%d\n' $?
cmp WIN_LE_keepped.bin WIN_LE.bin

echo
echo checking -winLE to -macBE
valgrind ./a.out WIN_LE.bin MAC_BE_swapped.bin -win -mac -swap
printf '%d\n' $?
cmp MAC_BE_swapped.bin MAC_BE.bin

echo
echo checking -winLE to -macLE
valgrind ./a.out WIN_LE.bin MAC_LE_keepped.bin -win -mac -keep
printf '%d\n' $?
cmp MAC_LE_keepped.bin MAC_LE.bin

echo
echo checking -winLE to -unixBE
valgrind ./a.out WIN_LE.bin UNIX_BE_swapped.bin -win -unix -swap
printf '%d\n' $?
cmp UNIX_BE_swapped.bin UNIX_BE.bin

echo
echo checking -winLE to -unixLE
valgrind ./a.out WIN_LE.bin UNIX_LE_keepped.bin -win -unix -keep
printf '%d\n' $?
cmp UNIX_LE_keepped.bin UNIX_LE.bin

echo
echo checking -macBE to -winBE
valgrind ./a.out MAC_BE.bin WIN_BE_keepped.bin -mac -win -keep
printf '%d\n' $?
cmp WIN_BE_keepped.bin WIN_BE.bin

echo
echo checking -macBE to -winLE
valgrind ./a.out MAC_BE.bin WIN_LE_swapped.bin -mac -win -swap
printf '%d\n' $?
cmp WIN_LE_swapped.bin WIN_LE.bin

echo
echo checking -macBE to -macBE
valgrind ./a.out MAC_BE.bin MAC_BE_keepped.bin -mac -mac -keep
printf '%d\n' $?
cmp MAC_BE_keepped.bin MAC_BE.bin

echo
echo checking -macBE to -macLE
valgrind ./a.out MAC_BE.bin MAC_LE_swapped.bin -mac -mac -swap
printf '%d\n' $?
cmp MAC_LE_swapped.bin MAC_LE.bin

echo
echo checking -macBE to -unixBE
valgrind ./a.out MAC_BE.bin UNIX_BE_keepped.bin -mac -unix -keep
printf '%d\n' $?
cmp UNIX_BE_keepped.bin UNIX_BE.bin

echo
echo checking -macBE to -unixLE
valgrind ./a.out MAC_BE.bin UNIX_LE_swapped.bin -mac -unix -swap
printf '%d\n' $?
cmp UNIX_LE_swapped.bin UNIX_LE.bin

echo
echo checking -macLE to -winBE
valgrind ./a.out MAC_LE.bin WIN_BE_swapped.bin -mac -win -swap
printf '%d\n' $?
cmp WIN_BE_swapped.bin WIN_BE.bin

echo
echo checking -macLE to -winLE
valgrind ./a.out MAC_LE.bin WIN_LE_keepped.bin -mac -win -keep
printf '%d\n' $?
cmp WIN_LE_keepped.bin WIN_LE.bin

echo
echo checking -macLE to -macBE
valgrind ./a.out MAC_LE.bin MAC_BE_swapped.bin -mac -mac -swap
printf '%d\n' $?
cmp MAC_BE_swapped.bin MAC_BE.bin

echo
echo checking -macLE to -macLE
valgrind ./a.out MAC_LE.bin MAC_LE_keepped.bin -mac -mac -keep
printf '%d\n' $?
cmp MAC_LE_keepped.bin MAC_LE.bin

echo
echo checking -macLE to -unixBE
valgrind ./a.out MAC_LE.bin UNIX_BE_swapped.bin -mac -unix -swap
printf '%d\n' $?
cmp UNIX_BE_swapped.bin UNIX_BE.bin

echo
echo checking -macLE to -unixLE
valgrind ./a.out MAC_LE.bin UNIX_LE_keepped.bin -mac -unix -keep
printf '%d\n' $?
cmp UNIX_LE_keepped.bin UNIX_LE.bin

echo
echo checking -unixBE to -winBE
valgrind ./a.out UNIX_BE.bin WIN_BE_keepped.bin -unix -win -keep
printf '%d\n' $?
cmp WIN_BE_keepped.bin WIN_BE.bin

echo
echo checking -unixBE to -winLE
valgrind ./a.out UNIX_BE.bin WIN_LE_swapped.bin -unix -win -swap
printf '%d\n' $?
cmp WIN_LE_swapped.bin WIN_LE.bin

echo
echo checking -unixBE to -macBE
valgrind ./a.out UNIX_BE.bin MAC_BE_keepped.bin -unix -mac -keep
printf '%d\n' $?
cmp MAC_BE_keepped.bin MAC_BE.bin

echo
echo checking -unixBE to -macLE
valgrind ./a.out UNIX_BE.bin MAC_LE_swapped.bin -unix -mac -swap
printf '%d\n' $?
cmp MAC_LE_swapped.bin MAC_LE.bin

echo
echo checking -unixBE to -unixBE
valgrind ./a.out UNIX_BE.bin UNIX_BE_keepped.bin -unix -unix -keep
printf '%d\n' $?
cmp UNIX_BE_keepped.bin UNIX_BE.bin

echo
echo checking -unixBE to -unixLE
valgrind ./a.out UNIX_BE.bin UNIX_LE_swapped.bin -unix -unix -swap
printf '%d\n' $?
cmp UNIX_LE_swapped.bin UNIX_LE.bin

echo
echo checking -unixLE to -winBE
valgrind ./a.out UNIX_LE.bin WIN_BE_swapped.bin -unix -win -swap
printf '%d\n' $?
cmp WIN_BE_swapped.bin WIN_BE.bin

echo
echo checking -unixLE to -winLE
valgrind ./a.out UNIX_LE.bin WIN_LE_keepped.bin -unix -win -keep
printf '%d\n' $?
cmp WIN_LE_keepped.bin WIN_LE.bin

echo
echo checking -unixLE to -macBE
valgrind ./a.out UNIX_LE.bin MAC_BE_swapped.bin -unix -mac -swap
printf '%d\n' $?
cmp MAC_BE_swapped.bin MAC_BE.bin

echo
echo checking -unixLE to -macLE
valgrind ./a.out UNIX_LE.bin MAC_LE_keepped.bin -unix -mac -keep
printf '%d\n' $?
cmp MAC_LE_keepped.bin MAC_LE.bin

echo
echo checking -unixLE to -unixBE
valgrind ./a.out UNIX_LE.bin UNIX_BE_swapped.bin -unix -unix -swap
printf '%d\n' $?
cmp UNIX_BE_swapped.bin UNIX_BE.bin

echo
echo checking -unixLE to -unixLE
valgrind ./a.out UNIX_LE.bin UNIX_LE_keepped.bin -unix -unix -keep
printf '%d\n' $?
cmp UNIX_LE_keepped.bin UNIX_LE.bin

echo done!!!
make clean
