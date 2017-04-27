#!/usr/bin/env python
# -*- coding: utf-8 -*-
################################################################################

import os
import sys
import os.path
import argparse
import filecmp
import shutil

################################################################################
# command line parsing

myParser = argparse.ArgumentParser(formatter_class = argparse.RawTextHelpFormatter,
   description="""
Name
----

cmp_dir.py


Description
-----------

Compare file trees and optionnaly copy different files.


User guide
----------

To run cmp_dir.py.

From a terminal:
   <cmp_dir_path>/cmp_dir.py --help
or
   <cmp_dir_path>/cmp_dir.py [-r] [-all] <source_directory> [<target_directory>]

If you want to exclude directories from the comparison, add a file named '.cmp_dir_excluded' in every directory you want to ignore in <source_directory>. When cmp_dir.py finds a '.cmp_dir_excluded' file, it ignores the whole subtree.

Files that don't differ are not displayed, and here are the displayed differences:
- SKIP: found a '.cmp_dir_excluded' and so do not compare the directory
- NEWD: directory exists in <source_directory> but not in <target_directory>
- OLDD: directory exists in <target_directory> but not in <source_directory>
- NEWF: file exists in <source_directory> but not in <target_directory>
- OLDF: file exists in <target_directory> but not in <source_directory>
- DIFF: file exists in both directories but are different
""")

myParser.add_argument('source_directory', nargs='?', default = '', \
   help = 'The source directory to compare to the target directory.')
myParser.add_argument('target_directory', nargs='?', default = '.', \
   help = 'The target directory to compare to the source directory.')

myParser.add_argument('-r', '--recurse', action = 'store_true', \
   help = 'Recurse on sub directories')

myParser.add_argument('-all', '--all_status', action = 'store_true', \
   help = 'Display all files with the status, ok or different; otherwise display only differences.')

myArgs = myParser.parse_args()

################################################################################
# functions
def isExcluded(dirName):
   excludingFile = os.path.join(dirName, ".cmp_dir_excluded")
   result = False
   if os.path.isfile(excludingFile):
      result = True
   return(result)


def processDiffFile(fileName, sourceDir, targetDir, displayAll):
   sourceFile = os.path.join(sourceDir, fileName)
   targetFile = os.path.join(targetDir, fileName)
   print('DIFF: ' + sourceFile + "\n      " + targetFile)


def processNewFile(fileName, sourceDir, targetDir, displayAll):
   sourceFile = os.path.join(sourceDir, fileName)
   targetFile = os.path.join(targetDir, fileName)
   if os.path.isdir(sourceFile):
      if isExcluded(sourceFile):
         print('SKIP: ' + sourceFile)
      else:
         print('NEWD: ' + sourceFile)
   else:
      print('NEWF: ' + sourceFile)


def processOldFile(fileName, targetDir, displayAll):
   targetFile = os.path.join(targetDir, fileName)
   if os.path.isdir(targetFile):
      print('OLDD: ' + targetFile)
   else:
      print('OLDF: ' + targetFile)


def compareDir(dirComparison, shouldRecurse, displayAll):
   for name in dirComparison.diff_files:
      processDiffFile(name, dirComparison.left, dirComparison.right, displayAll)
   for name in dirComparison.left_only:
      processNewFile(name, dirComparison.left, dirComparison.right, displayAll)
   for name in dirComparison.right_only:
      processOldFile(name, dirComparison.right, displayAll)

   # recurse if option is set
   if shouldRecurse:
      for subComparison in dirComparison.subdirs.values():
         if isExcluded(subComparison.left):
            print('SKIP: ' + subComparison.left)
         else:
            compareDir(subComparison, shouldRecurse, displayAll)


################################################################################
argSourceDir = myArgs.source_directory
argTargetDir = myArgs.target_directory
argShouldRecurse = myArgs.recurse
argDisplayAll = myArgs.all_status

if len(argSourceDir) == 0:
   print('Error: missing SOURCE_DIR argument.')
   print('Use --help for usage and help.')
   sys.exit(1)

# compare dirs
rootComparison = filecmp.dircmp(argSourceDir, argTargetDir)
compareDir(rootComparison, argShouldRecurse, argDisplayAll)
