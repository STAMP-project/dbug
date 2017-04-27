#!/bin/sh
# running HelloApp with arguments
################################################################################

args="$*"

CurrentDir=`pwd`

BuildDir="target"
ObjDir="$BuildDir/classes"
RunFlags="-classpath $ObjDir"

SrcDir="src"
FileList=`find $SrcDir -name "*.java"`
MainName=`grep -w -l "main" $FileList | sed -e "s/.*\/java\///" -e "s/\.java//"`

MainPackage=`dirname $MainName | sed -e "s/\//\./g"`
MainClass=`basename $MainName`
MainPath="$MainPackage.$MainClass"
MainClassFile="$ObjDir/$MainPackage/$MainClass.class"

if test ! -f $MainClassFile
then
   echo "############################################################" 
   echo "$MainClassFile does not exist, building with 'mvn package' before running"
   echo "############################################################" 
   mvn package
   echo "############################################################" 
fi

java $RunFlags $MainPath $args
