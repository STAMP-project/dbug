Source project
--------
DBug Hello World application

An basic example use to test the tools.


Bug description
--------
A space is missing in the Hello World string on stdout and in the produced file.
The tests tested the consistency between stout and the produced file, but not the expected result.


Test(s) which fails to detect the regression
--------
`v1/src/test/java/myWorld/HelloAppTest.java`


The test added to detect the regression
--------
`v2_before_fix/src/test/java/myWorld/HelloAppTest.java: testHelloAppRun3()`
