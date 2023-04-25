name_project=$1
tomcat=/home/rakharrs/Downloads/apache-tomcat-10.0.27

cd test-framework/
jar -cvf $name_project.war *
mv $name_project.war $tomcat/webapps
