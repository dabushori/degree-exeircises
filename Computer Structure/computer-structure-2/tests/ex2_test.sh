rm *_*_*.bin

make clean

make

echo
echo checking -winBE to -winBE
./a.out WIN_BE.bin WIN_BE_keepped.bin -win -win -keep
printf '%d\n' $?
cmp WIN_BE_keepped.bin WIN_BE.bin

echo
echo checking -winBE to -winLE
./a.out WIN_BE.bin WIN_LE_swapped.bin -win -win -swap
printf '%d\n' $?
cmp WIN_LE_swapped.bin WIN_LE.bin

echo
echo checking -winBE to -macBE
./a.out WIN_BE.bin MAC_BE_keepped.bin -win -mac -keep
printf '%d\n' $?
cmp MAC_BE_keepped.bin MAC_BE.bin

echo
echo checking -winBE to -macLE
./a.out WIN_BE.bin MAC_LE_swapped.bin -win -mac -swap
printf '%d\n' $?
cmp MAC_LE_swapped.bin MAC_LE.bin

echo
echo checking -winBE to -unixBE
./a.out WIN_BE.bin UNIX_BE_keepped.bin -win -unix -keep
printf '%d\n' $?
cmp UNIX_BE_keepped.bin UNIX_BE.bin

echo
echo checking -winBE to -unixLE
./a.out WIN_BE.bin UNIX_LE_swapped.bin -win -unix -swap
printf '%d\n' $?
cmp UNIX_LE_swapped.bin UNIX_LE.bin

echo
echo checking -winLE to -winBE
./a.out WIN_LE.bin WIN_BE_swapped.bin -win -win -swap
printf '%d\n' $?
cmp WIN_BE_swapped.bin WIN_BE.bin

echo
echo checking -winLE to -winLE
./a.out WIN_LE.bin WIN_LE_keepped.bin -win -win -keep
printf '%d\n' $?
cmp WIN_LE_keepped.bin WIN_LE.bin

echo
echo checking -winLE to -macBE
./a.out WIN_LE.bin MAC_BE_swapped.bin -win -mac -swap
printf '%d\n' $?
cmp MAC_BE_swapped.bin MAC_BE.bin

echo
echo checking -winLE to -macLE
./a.out WIN_LE.bin MAC_LE_keepped.bin -win -mac -keep
printf '%d\n' $?
cmp MAC_LE_keepped.bin MAC_LE.bin

echo
echo checking -winLE to -unixBE
./a.out WIN_LE.bin UNIX_BE_swapped.bin -win -unix -swap
printf '%d\n' $?
cmp UNIX_BE_swapped.bin UNIX_BE.bin

echo
echo checking -winLE to -unixLE
./a.out WIN_LE.bin UNIX_LE_keepped.bin -win -unix -keep
printf '%d\n' $?
cmp UNIX_LE_keepped.bin UNIX_LE.bin

echo
echo checking -macBE to -winBE
./a.out MAC_BE.bin WIN_BE_keepped.bin -mac -win -keep
printf '%d\n' $?
cmp WIN_BE_keepped.bin WIN_BE.bin

echo
echo checking -macBE to -winLE
./a.out MAC_BE.bin WIN_LE_swapped.bin -mac -win -swap
printf '%d\n' $?
cmp WIN_LE_swapped.bin WIN_LE.bin

echo
echo checking -macBE to -macBE
./a.out MAC_BE.bin MAC_BE_keepped.bin -mac -mac -keep
printf '%d\n' $?
cmp MAC_BE_keepped.bin MAC_BE.bin

echo
echo checking -macBE to -macLE
./a.out MAC_BE.bin MAC_LE_swapped.bin -mac -mac -swap
printf '%d\n' $?
cmp MAC_LE_swapped.bin MAC_LE.bin

echo
echo checking -macBE to -unixBE
./a.out MAC_BE.bin UNIX_BE_keepped.bin -mac -unix -keep
printf '%d\n' $?
cmp UNIX_BE_keepped.bin UNIX_BE.bin

echo
echo checking -macBE to -unixLE
./a.out MAC_BE.bin UNIX_LE_swapped.bin -mac -unix -swap
printf '%d\n' $?
cmp UNIX_LE_swapped.bin UNIX_LE.bin

echo
echo checking -macLE to -winBE
./a.out MAC_LE.bin WIN_BE_swapped.bin -mac -win -swap
printf '%d\n' $?
cmp WIN_BE_swapped.bin WIN_BE.bin

echo
echo checking -macLE to -winLE
./a.out MAC_LE.bin WIN_LE_keepped.bin -mac -win -keep
printf '%d\n' $?
cmp WIN_LE_keepped.bin WIN_LE.bin

echo
echo checking -macLE to -macBE
./a.out MAC_LE.bin MAC_BE_swapped.bin -mac -mac -swap
printf '%d\n' $?
cmp MAC_BE_swapped.bin MAC_BE.bin

echo
echo checking -macLE to -macLE
./a.out MAC_LE.bin MAC_LE_keepped.bin -mac -mac -keep
printf '%d\n' $?
cmp MAC_LE_keepped.bin MAC_LE.bin

echo
echo checking -macLE to -unixBE
./a.out MAC_LE.bin UNIX_BE_swapped.bin -mac -unix -swap
printf '%d\n' $?
cmp UNIX_BE_swapped.bin UNIX_BE.bin

echo
echo checking -macLE to -unixLE
./a.out MAC_LE.bin UNIX_LE_keepped.bin -mac -unix -keep
printf '%d\n' $?
cmp UNIX_LE_keepped.bin UNIX_LE.bin

echo
echo checking -unixBE to -winBE
./a.out UNIX_BE.bin WIN_BE_keepped.bin -unix -win -keep
printf '%d\n' $?
cmp WIN_BE_keepped.bin WIN_BE.bin

echo
echo checking -unixBE to -winLE
./a.out UNIX_BE.bin WIN_LE_swapped.bin -unix -win -swap
printf '%d\n' $?
cmp WIN_LE_swapped.bin WIN_LE.bin

echo
echo checking -unixBE to -macBE
./a.out UNIX_BE.bin MAC_BE_keepped.bin -unix -mac -keep
printf '%d\n' $?
cmp MAC_BE_keepped.bin MAC_BE.bin

echo
echo checking -unixBE to -macLE
./a.out UNIX_BE.bin MAC_LE_swapped.bin -unix -mac -swap
printf '%d\n' $?
cmp MAC_LE_swapped.bin MAC_LE.bin

echo
echo checking -unixBE to -unixBE
./a.out UNIX_BE.bin UNIX_BE_keepped.bin -unix -unix -keep
printf '%d\n' $?
cmp UNIX_BE_keepped.bin UNIX_BE.bin

echo
echo checking -unixBE to -unixLE
./a.out UNIX_BE.bin UNIX_LE_swapped.bin -unix -unix -swap
printf '%d\n' $?
cmp UNIX_LE_swapped.bin UNIX_LE.bin

echo
echo checking -unixLE to -winBE
./a.out UNIX_LE.bin WIN_BE_swapped.bin -unix -win -swap
printf '%d\n' $?
cmp WIN_BE_swapped.bin WIN_BE.bin

echo
echo checking -unixLE to -winLE
./a.out UNIX_LE.bin WIN_LE_keepped.bin -unix -win -keep
printf '%d\n' $?
cmp WIN_LE_keepped.bin WIN_LE.bin

echo
echo checking -unixLE to -macBE
./a.out UNIX_LE.bin MAC_BE_swapped.bin -unix -mac -swap
printf '%d\n' $?
cmp MAC_BE_swapped.bin MAC_BE.bin

echo
echo checking -unixLE to -macLE
./a.out UNIX_LE.bin MAC_LE_keepped.bin -unix -mac -keep
printf '%d\n' $?
cmp MAC_LE_keepped.bin MAC_LE.bin

echo
echo checking -unixLE to -unixBE
./a.out UNIX_LE.bin UNIX_BE_swapped.bin -unix -unix -swap
printf '%d\n' $?
cmp UNIX_BE_swapped.bin UNIX_BE.bin

echo
echo checking -unixLE to -unixLE
./a.out UNIX_LE.bin UNIX_LE_keepped.bin -unix -unix -keep
printf '%d\n' $?
cmp UNIX_LE_keepped.bin UNIX_LE.bin

echo done!!!
make clean
