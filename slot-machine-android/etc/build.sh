#!/bin/sh

BUILD_DIRECTORY=$1
USER_NAME=$2
PROFILE=$3
CLEAN=$4
BRANCH=$5
PROJECT_NAME=slot-machine

# Help
# ****
if [ $# -eq 1 ] && [ $1 = -h ]
then
        echo "Help"
        echo "****"
        echo ""
        echo "This script will build the application."
        echo "Available parameters"
        echo ""
        echo " 1) The path to a directory where the code will be checked out and the assemblies would be generated. For example: /home/user/build. Required."
        echo ""
        echo " 2) The Git user name used to checkout the code. Required."
        echo ""
        echo " 3) The Profile used to build the application. For example: qa, staging, production. Required."
        echo ""
        echo " 4) Whether the source code and assemblies on the build directory should be cleaned or not. Required. Default value: true"
        echo ""
        echo " 5) The branch from where check out the code. Optional. Default value: master"
        echo ""
        exit 0
fi

# Parameters validation
# ************************
if [ -z "$BUILD_DIRECTORY" ]
then
	echo "[ERROR] The BUILD_DIRECTORY parameter is required"
	echo "Run the script with '-h' for help"
	exit 1;
fi

if [ ! -d "$BUILD_DIRECTORY" ]
then
	echo "[ERROR] - The BUILD_DIRECTORY directory does not exist."
	echo "Run the script with '-h' for help"
	exit 1;
fi

if [ -z "$USER_NAME" ]
then
	echo "[ERROR] The USER_NAME parameter is required"
        echo "Run the script with '-h' for help"
        exit 1
fi

if [ -z "$PROFILE" ]
then
	echo "[ERROR] The PROFILE parameter is required"
	echo "Run the script with '-h' for help"
	exit 1;
fi

if [ -z "$CLEAN" ]
then
	CLEAN="true"
fi

if [ -z "$BRANCH" ]
then
	BRANCH=master
fi

SOURCE_DIRECTORY=$BUILD_DIRECTORY/$PROJECT_NAME/source
ASSEMBLIES_DIRECTORY=$BUILD_DIRECTORY/$PROJECT_NAME/assemblies

# Cleaning
# ************************
if [ "$CLEAN" = "true" ]
then
	# Clean the directories
	rm -r -f $SOURCE_DIRECTORY
	mkdir -p $SOURCE_DIRECTORY

	rm -r -f $ASSEMBLIES_DIRECTORY
	mkdir -p $ASSEMBLIES_DIRECTORY

	# Checkout the project
	cd $SOURCE_DIRECTORY
	echo Cloning git@github.com:$USER_NAME/slot-machine.git
	git clone git@github.com:$USER_NAME/slot-machine.git $PROJECT_NAME

	cd $SOURCE_DIRECTORY/$PROJECT_NAME
	if [ "$BRANCH" != 'master' ] 
	then
		git checkout -b $BRANCH origin/$BRANCH --track
	fi
fi

# Assemblies Generation
# ************************

# Generate the android apk
cd $SOURCE_DIRECTORY/$PROJECT_NAME/slot-machine-android
mvn dependency:resolve -P $PROFILE,slot-machine-free clean install -Dmaven.test.skip=true
cp ./target/*.apk $ASSEMBLIES_DIRECTORY/

cd $SOURCE_DIRECTORY/$PROJECT_NAME/slot-machine-android
mvn dependency:resolve -P $PROFILE,slot-machine-paid clean install -Dmaven.test.skip=true
cp ./target/*.apk $ASSEMBLIES_DIRECTORY/
