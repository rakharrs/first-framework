project_path=/home/rakharrs/Workspace/java/first-framework
framework_path=$project_path/framework
webapp_path=$project_path/test-framework
src=$framework_path/src
build=$framework_path/build
lib_path=$src/lib

shopt -s globstar

javac -parameters -d $build -cp $lib_path/* $src/**/*.java
rm -R ./test-framework/WEB-INF/classes/*

cd $build
jar -cf $webapp_path/WEB-INF/lib/framework.jar etu1999

javac -parameters -d $webapp_path/WEB-INF/classes -cp $webapp_path/WEB-INF/lib/*.jar $webapp_path/java/**/*.java