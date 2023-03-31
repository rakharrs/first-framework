name_project=$1
tomcat=/home/rakharrs/Downloads/apache-tomcat-10.0.27

cd test-framework/
jar -cf $name_project.war ./WEB-INF
mv $name_project.war $tomcat/webapps
