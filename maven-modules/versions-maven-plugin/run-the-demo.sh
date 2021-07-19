#!/bin/bash
#function to display commands
exe() { echo -e "\$ $@\n" ; "$@" ; }

TEXT_COLOR='\033[1;33m' #Yellow
NO_COLOR='\033[0m' # No Color

clear

echo -e "======================================================================================"
echo -e " Showcase for the BAELDUNG tutorial \"Use the latest version of a dependency in Maven\""
echo -e " Author: Andrea Ligios"
echo -e "======================================================================================"

echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " Resetting the demo environment (which will be altered during the run): "
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
rm -f pom.xml.versionsBackup
cp original/pom.xml pom.xml
ls -lt pom.*
echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " Checking for newer versions of the Maven dependencies:" 
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
exe mvn versions:display-dependency-updates
echo
read -p "Press enter to continue"

echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " Updating SNAPSHOT dependencies to their RELEASE version, if any:" 
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
exe mvn versions:use-releases
echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " A backup has been created automatically:"
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
ls -lt pom.*
echo
read -p "Press enter to continue"

echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " Updating RELEASE dependencies to their *next* RELEASE version:" 
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
exe mvn versions:use-next-releases
echo
read -p "Press enter to continue"

echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " Reverting every modification made since the beginning:"
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
exe mvn versions:revert
echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " The backup is gone, and the pom.xml contains the initial dependencies:"
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
ls -lt pom.*
echo
read -p "Press enter to continue"

echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " Updating RELEASE dependencies to their *latest* RELEASE version:"
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
exe mvn versions:use-latest-releases
echo
read -p "Press enter to continue"

echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " Committing the modifications to pom.xml:"
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
exe mvn versions:commit
echo -e "${TEXT_COLOR}\n--------------------------------------------------------------------------------------"
echo -e " The backup is gone, and the pom.xml contains the latest dependencies:"
echo -e "--------------------------------------------------------------------------------------${NO_COLOR}"
ls -lt pom.*
echo

echo -e "${TEXT_COLOR}\nThat's all folks!${NO_COLOR}\n"
