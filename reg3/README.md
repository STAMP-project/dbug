Source project
--------
[xwiki-platform](https://github.com/xwiki/xwiki-platform)


Commit numbers of each version
--------
- v0

    `TAG: xwiki-platform-8.2.1`

- v1

    `TAG: xwiki-platform-8.2.2`

- v2_before_fix


- v2

    `TAG: xwiki-platform-8.4.2`


Bug description
--------
Regression in xwiki-platform, can be demonstrated with a unit test.

Issue: http://jira.xwiki.org/browse/XWIKI-13916
BaseClass#addTextAreaField try to modify existing field without checking if it's already the right type.
Before that addTextAreaField was not doing anything if the field already existed.

Regression caused by commit: https://github.com/xwiki/xwiki-platform/commit/dc2ae27469957d9d93d01005f9f9a66450892c6c

Regression fixed by commit: https://github.com/xwiki/xwiki-platform/commit/2d6845283dc9a7d1f0964127fe8116795f1b0581

Note that a test was added to prove that the fix worked and so that we don’t cause more regression in the future.


Test(s) which fails to detect the regression
--------



The test added to detect the regression
--------



Build issues
-------
In case of problem building this case you can refer to:
http://dev.xwiki.org/xwiki/bin/view/Community/Building

A useful part is the Maven settings.xml configuration at:
http://dev.xwiki.org/xwiki/bin/view/Community/Building#HInstallingMaven
