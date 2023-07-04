project_path=/home/rakharrs/Workspace/java/first-framework
framework_path=$project_path/framework
webapp_path=$project_path/test-framework
src=$framework_path/src
build=$framework_path/build
lib_path=$src/lib

shopt -s globstar

javac -cp $lib_path/gson-2.10.1.jar:$lib_path/servlet-api.jar -parameters -d $build $src/**/*.java
rm -R ./test-framework/WEB-INF/classes/*

cd $build
jar -cf $webapp_path/WEB-INF/lib/framework.jar etu1999

javac -parameters -cp "${webapp_path}/WEB-INF/lib/*" -d $webapp_path/WEB-INF/classes $webapp_path/java/**/*.java