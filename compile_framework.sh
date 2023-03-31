project_path=/home/rakharrs/first-framework/first-framework
framework_path=$project_path/framework
webapp_path=$project_path/test-framework
src=$framework_path/src
build=$framework_path/build
lib_path=$src/lib

shopt -s globstar

javac -d $build -cp $lib_path/* $src/**/*.java

cd $framework_path/build

jar -cf $webapp_path/WEB-INF/lib/framework.jar etu1999 
