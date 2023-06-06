#Variable(s)
name_project=$1

tomcat=/home/rakharrs/Downloads/apache-tomcat-10.0.27

java_path=test-framework/java

lib_path=test-framework/WEB-INF/lib

jsp_path=test-framework/jsp

web_xml=test-framework/WEB-INF/web.xml

shopt -s globstar

#Création d'un dossier temporaire
mkdir temp-project

#Création de la structure du projet pour tomcat
mkdir temp-project/WEB-INF temp-project/WEB-INF/classes temp-project/WEB-INF/lib

#Copier des fichiers de lib et des fichiers jsp
cp -R $jsp_path/* temp-project
cp -R $lib_path/* temp-project/WEB-INF/lib

#Compilation des class java vers le dossier temporaire
javac -parameters -d temp-project/WEB-INF/classes -cp $lib_path/*.jar $java_path/**/*.java

#Copie du fichier web.xml -> temp
cp $web_xml temp-project/WEB-INF

#Création de l'archive war
cd temp-project/
jar -cvf $name_project.war *

#Deplacement de l'archive war vers tomcat
mv $name_project.war $tomcat/webapps

#Suppression du projet temporaire
rm -R ../temp-project