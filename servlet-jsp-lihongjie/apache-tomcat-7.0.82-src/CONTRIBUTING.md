# Contributing to Apache Tomcat

Firstly, thanks for your interest in contributing! I hope that this will be a
pleasant first experience for you, and that you will return to continue
contributing.

Please visit our [Get Involved page](http://tomcat.apache.org/getinvolved.html)
for more information on how to contribute.

## Code of Conduct

This project and everyone participating in it is governed by the Apache
software Foundation's
[Code of Conduct](http://www.apache.org/foundation/policies/conduct.html). By
participating, you are expected to adhere to this code. If you are aware of
unacceptable behavior, please visit the
[Reporting Guidelines page](http://www.apache.org/foundation/policies/conduct.html#reporting-guidelines)
and follow the instructions there.

## How Can I Contribute?

Most of the contributions that we receive are code contributions, but you can
also contribute to the documentation, wiki, etc., or simply report solid bugs
for us to fix.

### Reporting Bugs

Please review our [guide](http://tomcat.apache.org/bugreport.html) on how to
submit a bug report. This page also has links to other resources to assist
you.

### Your First Code Contribution

### Trouble Deciding How to Contribute?

Unsure where to begin contributing to Tomcat? You can start by taking a look at
the issues marked 'Beginner', link below. Please note that the Beginner keyword
is pretty new to the project, so if there aren't any issues in the filter feel
free to ask on the [dev list](http://tomcat.apache.org/lists.html#tomcat-dev).

* [Beginner issues](https://bz.apache.org/bugzilla/buglist.cgi?bug_status=NEW&bug_status=ASSIGNED&bug_status=REOPENED&bug_status=NEEDINFO&keywords=Beginner&keywords_type=allwords&list_id=160824&product=Tomcat%207&product=Tomcat%208&product=Tomcat%209&query_format=advanced) -
issues which should only require a few lines of code, and a test or two to
resolve.

The list above shows all bugs that are marked 'Beginner' and are open in the
currently supported Tomcat versions (7, 8, and 9).

If you prefer C over Java, you may also take a look at the tomcat-native and
Tomcat Connectors products in Bugzilla.

### How to Provide Your First Patch

Excited yet? This section will guide you through providing a patch to the
committers of the project for review and acceptance.

##### Chose Your Method of Submission

You can provide a patch in one of the following ways (in order of preference):

* Patch attachment to the Bugzilla issue
* Github Pull Request
> **Note:** Github is a mirror of the SVN repository that Tomcat is stored in
and therefore it can't be merged outright. Your contribution will be converted
into an SVN patch and committed with a mention of your name for credit.
* Email the patch to the developer list. This is not preferred, but if no bug
is associated with the patch, or you would like a developer review, an email
may be appropriate.

##### Get the Sources

Now that you've chosen how you want to submit a patch, you need to get the
source code.

###### Download The Source Distribution

This method works if you want to submit a patch (like you would do for SVN), but
the difference in using the sources distribution and a VCS is that you have to
manually generate the patch file by using diff. If this is what you want, you
can download the sources from the "Source Code Distributions" section of the
[Download Page](https://tomcat.apache.org/download-90.cgi).

###### SVN

If you have chosen to attach a patch to the Bugzilla issue (or email
one), then you'll need to checkout the SVN version. Instructions for new
committers to learn how to do this are found
[here](http://www.apache.org/dev/contributors.html#svnbasics). However, in the
interest of a fast ramp up, the short version is below. Note that the root of
the SVN repository is
[tomcat/trunk](http://svn.apache.org/repos/asf/tomcat/trunk),
but you can clone specific versions too, such as
[tc8.5.x](http://svn.apache.org/repos/asf/tomcat/tc8.5.x/trunk/) or even tags (
[TOMCAT_8_5_15](http://svn.apache.org/repos/asf/tomcat/tc8.5.x/tags/TOMCAT_8_5_15/)).

```
$ svn co http://svn.apache.org/repos/asf/tomcat/trunk/
```

##### Github

For Github, it's almost the same. Chose the major version that you want (for
now they're in different repositories), fork the repository, and then clone
your fork to do that work.

```
$ git clone https://github.com/$USERNAME/tomcat.git
```

#### Submitting Your Patch!

After you've chosen your method of submission, retrieved the sources, and
fixed the issue it's time to submit your work. At this point, just follow
the method of submission you chose earlier.

* Bugzilla attachment - attach the SVN patch to the Bugzilla issue
* Github PR - after resolving the issue in your local fork and pushing to your
copy of the repository, open a Github PR for review.
* Email - again, not preferred, but you may send an email to the developer list
with a patch attached for review.

#### Waiting For Feedback

It may take a while for committers to review. Please be patient during this
time as all committers are volunteers on the project. If a significant amount
of time has lapsed since your submission, such as a couple of months, feel free
to either update your BZ, PR, or email the dev list with a message to bump your
issue. Sometimes things get lost in all the work and we need a reminder :smile:

## Style Guide

Apache Tomcat has very loosely defined coding conventions, but the following
guidelines will be useful:

* Use spaces for indenting, not tabs
* 100 char line width for Java source, 80 char line width for documentation
source (.txt, .xml)
* Java source: { at end of line, 4 space indents
* XML source: 2 space indents

## Did we miss something?

Have you reviewed this guide and found it lacking? Or are you confused about
some particular step? If so, please let us know! Or better yet, submit a PR to
address the issue :wink:
