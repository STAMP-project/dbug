#!/bin/sh
################################################################################
printUsage()
{
   echo "$scriptName [-h | --help | -traces | -clean]"
   echo "-h | --help: this help"
   echo "-traces: duplicate stdout and stdin in a file named <prefix>$tracePattern"
   echo "         where <prefix> is 'date +%Y%m%d_%Hh%M'"
   echo "-clean: cleaning instead of building"
}

################################################################################
runTests()
{
   targetFile="org.sat4j.core/pom.xml"
   targetList="`ls -d */$targetFile`"

   for theTarget in $targetList
   do
      theVersion=`dirname $theTarget`
      cd $currentDir
      cd $theVersion

      echo "############################################################"
      echo "#### $theVersion"
      echo "############################################################"
      startDate="`date +'%Y-%m-%d / %H:%M'`"
      mvn clean $buildOption
      endDate="`date +'%H:%M'`"
      echo "############################################################"
      echo "#### $theVersion: $startDate --> $endDate"
      echo "############################################################"
   done

   return 0
}

################################################################################
scriptName="$0"
currentDir=`pwd`

thePrefix=
tracePattern="_mvn_clean_test.traces"
traceFile=
buildOption="test"

while test ! "X$1" = "X"
do
   if test "$1" = "-h" || test "$1" = "--help"
   then
      printUsage
      exit 0
   elif test "$1" = "-traces"
   then
      thePrefix="`date +%Y%m%d_%Hh%M`"
      traceFile="$currentDir/$thePrefix$tracePattern"
   elif test "$1" = "-clean"
   then
      buildOption=
   else
      echo "Error: invalid argument: $1"
      echo
      printUsage
      exit 1
   fi
   shift
done

################################################################################
if test "X$traceFile" = "X"
then
   runTests
else
   runTests 2>&1 | tee -a $traceFile
fi
