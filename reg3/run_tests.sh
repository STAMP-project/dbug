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
   # maven settings
   MavenSettingsDir="$HOME/.m2"
   MavenSettingsTarget="settings.xml"
   MavenSettingsBackup="settings.xml.bak$$"

   mvnSourceFile="m2_settings.xml"
   mvnTargetFile=$MavenSettingsDir/$MavenSettingsTarget
   mvnBackupFile=$MavenSettingsDir/$MavenSettingsBackup

   # replace maven settings.xml
   if test -f $mvnTargetFile
   then
      cp -f $mvnTargetFile $mvnBackupFile
      echo "#### [info] Backuping $mvnTargetFile into $mvnBackupFile"
   fi
   echo "#### [info] Copying $mvnSourceFile into $mvnTargetFile"
   cp -f $mvnSourceFile $mvnTargetFile

   targetFile="pom.xml"
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

   # restore original maven settings.xml if needed or delete the created file
   if test -f $mvnBackupFile
   then
      echo "#### [info] Restoring $mvnBackupFile into $mvnTargetFile"
      cp -f $mvnBackupFile $mvnTargetFile
      rm -f $mvnBackupFile
   else
      echo "#### [info] Deleting $mvnTargetFile"
      rm -f $mvnTargetFile
   fi

   return 0
}

################################################################################
scriptName="$0"
currentDir=`pwd`

thePrefix=
tracePattern="_mvn_clean_install.traces"
traceFile=
buildOption="install"

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
