Source project
--------
[xwiki-platform](https://github.com/xwiki/xwiki-platform)


Commit numbers of each version
--------
- v0

    `TAG: xwiki-platform-8.3`

- v1

    `TAG: xwiki-platform-8.1-milestone-1`

- v2_before_fix


- v2

    `TAG: xwiki-platform-8.4`


Bug description
--------
Regression in xwiki-platform, can be demonstrated with a functional test

Issue: http://jira.xwiki.org/browse/XWIKI-13847
Create button not available for users which only have edit rights on a subtree in the wiki (and not on the whole wiki).

To reproduce:
* remove edit rights from XWiki.XWikiAllGroup on a wiki
* create a user
* give edit right to this user on the Sandbox subtree (page and children)
* login with the new user
* go to Sandbox.WebHome

Expected result:
* the user should see the Create button in the Page content menu, so that he can create pages in the subtree, where he has the rights

Actual result:
* the user does not see the Create button in the page content menu, he cannot create a new page other than by creating a link to that page in the content of an existing page and then clicking on this link


Regression caused by commit: https://github.com/xwiki/xwiki-platform/commit/c0c181cdf0b21f79ee31014615d5a96335a24467

Regression fixed by commit: https://github.com/xwiki/xwiki-platform/commit/f27aff73d66b0810368d54574c522305bb4534ac

Note that no test was added to prove the fix.


Test(s) which fails to detect the regression
--------



Test(s) added to detect the regression
--------



Build issues
-------
In case of problem building this case you can refer to:
http://dev.xwiki.org/xwiki/bin/view/Community/Building

A useful part is the Maven settings.xml configuration at:
http://dev.xwiki.org/xwiki/bin/view/Community/Building#HInstallingMaven
