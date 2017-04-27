#!/bin/sh
# running tests
# find all classes in target/test-classes and run each of them with jUnit
################################################################################

CurrentDir=`pwd`

BuildDir="target"
ObjDir="$BuildDir/classes"
RunFlags="-classpath $ObjDir"

if test ! -d $BuildDir
then
   echo "Error: cannot run, '$BuildDir' directory does not exist"
   echo "Run 'mvn package' and try again"
   exit 1
fi

JarFile=`find $BuildDir -name "*.jar"`
TestObjDir="$BuildDir/test-classes"
JunitPath="/usr/share/java/junit4.jar"
TestRunFlags="-classpath $TestObjDir:$JarFile:$JunitPath"
TestMainPath="org.junit.runner.JUnitCore"

if test "X$JarFile" = "X"
then
   echo "############################################################"
   echo "No jar file, building with 'mvn package' before running"
   echo "############################################################"
   mvn package
   echo "############################################################"
fi

cd $TestObjDir
TestClassList=`find . -name "*.class" | sed -e "s/\.\///" -e "s/\.class//" -e "s/\//\./g"`
cd $CurrentDir

for testPath in $TestClassList
do
   echo "------------------------------------------------------------"
   echo "---- $testPath"
   echo "------------------------------------------------------------"
   java $TestRunFlags $TestMainPath $testPath
done
