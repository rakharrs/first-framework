project_path=/home/rakharrs/first-framework/first-framework
framework_path=$project_path/framework
webapp_path=$project_path/test-framework
src=$framework_path/src
build=$framework_path/build
lib_path=$src/lib

shopt -s globstar

javac -d $build -cp $lib_path/* $src/**/*.java
rm -R ./test-framework/WEB-INF/classes/*
javac -d ./test-framework/WEB-INF/classes -cp ./test-framework/WEB-INF/lib/*.jar ./test-framework/java/**/*.java

cd $framework_path/build

jar -cvf $webapp_path/WEB-INF/lib/framework.jar etu1999 
