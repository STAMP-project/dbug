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

# silent build
BuildTraces="/tmp/run_hello_app_test_`date +%Y%m%d_%Hh%M`.traces"
mvn clean package 2>&1 >$BuildTraces

java $RunFlags $MainPath $args
