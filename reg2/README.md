Source project
--------
[SAT4J](https://gitlab.ow2.org/sat4j/sat4j)


Commit numbers of each version
--------
- v0

    `Release 2.3.4`

    `e99c3f264d37afff789e66bb378625684944fe2b`

- v1

    `Fix for SAT95`

    `9d8fbc9170f3536dd208e2f2e90befecbcf1feda`

- v2_before_fix

    `564161ccc340d9923fab4203c6349edb945443b4`

- v2

    `bad2affd812de1843fad23cdb705e1b7b67d407a`


Bug description
--------
[SAT-107](https://jira.ow2.org/browse/SAT-107)

The regression was introduced when correcting this default: [SAT-95](https://jira.ow2.org/browse/SAT-95)


Test(s) which fails to detect the regression
--------
`v1/org.sat4j.core/src/test/java/org/sat4j` 

The test added to detect the regression
--------
`v2_before_fix/org.sat4j.core/src/test/java/org/sat4j/BugSAT107.java`
