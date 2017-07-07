![DBug - STAMP Product - European Commission - H2020](docs/images/logo_readme_md.png)

**DBug** - **D**atabase of **BUG**s

A collection of real regression use cases.

DBug contains real regression cases collected from software products.

As part of the [STAMP](https://www.stamp-project.eu) project, it is used to test and validate test automation tools, like Dspot and Pitest-Descartes, both available in [STAMP-project github](https://github.com/STAMP-project).

The regression cases
--------------------
For each regression case (reg#N directory) you'll find:

* a REAMDE.md file
that gives you information about the regression, like:
    - the source project,
    - the commit numbers of each version,
    - a bug description,
    - the test(s) which fails to detect the regression,
    - the test added to detect the regression
    - or any information that may be useful to understand the case

* v0 directory
    - Use case initial version and the corresponding test suite
    - All tests are OK

* v1 directory
    - Use case version with the regression and the corresponding test suite that does not detect the regression
    - All tests are OK

* v2_before_fix directory (optional)
    - Use case version with the regression and the corresponding test suite that detects the regression
    - The added test(s) fail(s)

* v2 directory
    - Use case version with the regression fix and the corresponding test suite that detects the regression
    - All tests are OK


Environement
------------
Tools versions used to test the regression cases:
* Ubuntu 16.04.1 LTS
* Java 1.8
* Git version 2.7.4
* Maven 3.3.9
* Python 2.7.12


Getting started
----------------

* Get the repository
```
git clone https://github.com/STAMP-project/dbug.git
```

* To build and execute tests for a regression:
```
cd reg#N
./run_tests.sh
```
more options with: `./run_tests.sh --help`

* If you want to compare directories, you can use tools/cmp_dir.py:
```
cmp_dir.py --help
```

