To compile application and tests, and run tests:
   mvn clean package

To run HelloApp:
   run_hello_app.sh [<arguments>]

To run HelloApp tests with jUnit:
   run_hello_app_tests.sh

------------------------------------------------------------

When running 'mvn clean package', you should have something like this:
   [INFO] Scanning for projects...
   [INFO]                                                                         
   [INFO] ------------------------------------------------------------------------
   [INFO] Building hello_app 1.0.0
   [INFO] ------------------------------------------------------------------------

   ...

   -------------------------------------------------------
    T E S T S
   -------------------------------------------------------
   Running myWorld.HelloAppTest
   -
   - Hello World !
   -
   --------
   -------- Hello World !
   --------
   Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.044 sec - in myWorld.HelloAppTest
   Running myWorld.MyStorageTest
   Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec - in myWorld.MyStorageTest
   
   Results :
   
   Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
   
   [INFO] 
   [INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ hello_app ---
   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD SUCCESS
   [INFO] ------------------------------------------------------------------------
   [INFO] Total time: 1.461 s
   [INFO] Finished at: 2017-03-20T16:59:21+01:00
   [INFO] Final Memory: 11M/289M
   [INFO] ------------------------------------------------------------------------
