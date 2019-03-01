# IBOX

This is a prototype of a Dropbox application which detects the changes at runtime in the demo folder which include : new, deleted and updated files. It will sync up all the changes from local demo folder to Amazon S3 cloud storage service.

### Prerequisites

Set credentials file in the AWS credentials profile file on your local system, located at:
~/.aws/credentials (on Linux, macOS, or Unix)
C:\Users\USERNAME\ .aws\credentials (on Windows)

```
[default]
aws_access_key_id = your_access_key_id
aws_secret_access_key = your_secret_access_key
```

## Usage

Open Ibox_cpp with any Java IDE: Intellij, Eclips, NetBeans etc.
Run IboxApplication (Ibox_cpp\src\main\java\Ibox\Ibox\IboxApplication)

Do created deleted or updated files in folder demoDrive (Ibox_cpp\src\main\resources\demoDrive\)

It will sync up all the changes from demoDrive folder to Amazon S3 bucket: ibox-bucket (will created if not exist)
![avatar](https://github.com/yanyichi/Ibox_cpp/blob/master/pic/ibox_bucket.png)

## Running the tests
```
mvn clean package
```
OR
Open Ibox_cpp with any Java IDE: Intellij, Eclips, NetBeans etc.
Run IboxApplicationTests (Ibox_cpp\src\test\java\Ibox\Ibox\IboxApplicationTests)

## code coverage
![avatar](https://github.com/yanyichi/Ibox_cpp/blob/master/pic/Code%20Covered.jpg)

## CircleCI
[Ibox_cpp CircleCI Link](https://circleci.com/gh/yanyichi/Ibox_cpp/32)
![avatar](https://github.com/yanyichi/Ibox_cpp/blob/master/pic/CircleCI.jpg)

## Authors

* **Yichi Tan** - [Ibox_cpp](https://github.com/yanyichi/Ibox_cpp)

## License

Apache License, Version 2.0 - see the [LICENSE-2.0.txt](http://www.apache.org/licenses/LICENSE-2.0.txt) file for details


