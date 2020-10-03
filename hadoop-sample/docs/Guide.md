# Quick build and deploy manual

## Required tools

* Unix ex. Centos 7
* git
* jdk 1.8 or later
* hadoop 2.10.0

## Build

### Clone project

```shell script
git clone https://github.com/Shipaaaa/DS-BDA.git
```

### Build project

```shell script
./hadoop-sample/gradlew build
```

After that you can see result in `./hadoop-sample/build/libs/hadoop-sample.jar`

## Start

### Start HDFS

```shell script
# in directory with hadoop
./hadoop/sbin/start-dfs.sh
```

### Start Yarn

```shell script
# in directory with hadoop
./hadoop/sbin/start-yarn.sh
```

### Generate input data and send to HDFS

```shell script
# -c=<count of words>;
# -o=<path_for_file> 
./random-word-generator.sh -c=10000 -o="./input/test_data.txt"

# hdfs dfs -put path/to/local/file/ path/for/hdfs/file/
hdfs dfs -put ./input/test_data.txt /user/root/input
```

### Run app

```shell script
#yarn jar path/to/jar /path/to/hdfs/input/data /path/to/hdfs/output/data
yarn jar ./hadoop-sample/build/libs/hadoop-sample.jar /user/root/input /user/root/output
```