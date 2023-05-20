cd data
data_dir=$(pwd)
cd "$data_dir/train"
base=$(pwd)
for dir in */; do
    cd $dir
    ls -tp | grep -v '/$' | tail -n 900 | tr '\n' '\0' | xargs -0 rm --
    cd $base
done
cd "$data_dir/valid"
base=$(pwd)
for dir in */; do
    cd $dir
    ls -tp | grep -v '/$' | tail -n 100 | tr '\n' '\0' | xargs -0 rm --
    cd $base
done
cd "$data_dir/test"
ls -tp | grep -v '/$' | tail -n 6800 | tr '\n' '\0' | xargs -0 rm --