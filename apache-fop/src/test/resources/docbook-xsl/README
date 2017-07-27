----------------------------------------------------------------------
              README file for the DocBook XSL Stylesheets
----------------------------------------------------------------------
$Id: README 9731 2013-03-17 05:01:54Z bobstayton $

These are XSL stylesheets for transforming DocBook XML document
instances into various output formats.

This README file provides only very minimal documentation on using
the stylesheets. For more complete information, see Bob Stayton's
book "DocBook XSL: The Complete Guide", available online at:

  http://www.sagehill.net/docbookxsl/

----------------------------------------------------------------------
Installation
----------------------------------------------------------------------
See the INSTALL file for information about installing this release.

----------------------------------------------------------------------
How to use the stylesheets
----------------------------------------------------------------------
The base canonical URI for these stylesheets is:

  http://docbook.sourceforge.net/release/xsl/current/

You call any of the stylesheets in this distribution by doing one
of the following:

  - Use the base canonical URI in combination with one of the
    pathnames below. For example, for "chunked" HTML, output:

    http://docbook.sourceforge.net/release/xsl/current/html/chunk.xsl

    If your system has a working XML Catalog or SGML Catalog setup
    (most Linux systems do), then that URI will automatically be
    resolved and replaced with a local pathname on your system.

  - Use a "real" local system base path in combination with one of
    the pathnames below. For example, for "chunked" HTML, output:

    /usr/share/xml/docbook/stylesheet/nwalsh/html/chunk.xsl

To transform documents created with the standard DocBook
schema/DTD, use one of the following stylesheets:

  fo/docbook.xsl              - for XSL-FO

  html/docbook.xsl            - for HTML (as a single file)
  html/chunk.xsl              - for HTML (chunked into multiple files)
  html/onechunk.xsl           - for HTML (chunked output in single file)

  xhtml/*.xsl                 - for XHTML versions of the above

  xhtml-1_1/*.xsl             - for XHTML 1.1 versions of the above

  xhtml5/*.xsl                 - for XHTML5 versions of the above

  epub/docbook.xsl            - for .epub version 2 and earlier
  epub3/docbook.xsl           - for .epub version 3 and later

  htmlhelp/htmlhelp.xsl       - for HTML Help
  javahelp/javahelp.xsl       - for JavaHelp
  eclipse/eclipse.xsl         - for Eclipse Help

  manpages/docbook.xsl        - for groff/nroff man pages

  */profile-*                 - single-pass-profiling versions of all above

  roundtrip/*.xsl             - for DocBook to WordML, etc., to DocBook

  assembly/assemble.xsl       - converts an assembly into a DocBook document
  assembly/topic-maker-chunk.xsl
                              - converts a DocBook document into an assembly
                                with topic files.

  webhelp/build.xml           - Ant script to generate webhelp output.  
  webhelp/Makefile            - Makefile to generate webhelp output.

To transform documents created with the DocBook Slides schema/DTD,
use one of the following stylesheets:

  slides/xhtml/*.xsl          - for XHTML slides of various kinds
  slides/fo/plain.xsl         - for XSL-FO slides

To transform documents created with the DocBook Website
schema/DTD, use one of the following stylesheets:

  website/website.xsl         - for non-tabular, non-chunked output
  website/tabular.xsl         - for tabular, non-chunked output
  website/chunk-*             - for chunked output

To generate a titlepage customization layer from a titlepage spec:

  template/titlepage.xsl

For fo titlepage customizations, set the stylesheet parameter named 'ns'
to 'http://www.w3.org/1999/XSL/Format' when using this stylesheet.
For xhtml titlepage customizations, set the stylesheet parameter named 'ns'
to 'http://www.w3.org/1999/xhtml' when using this stylesheet.

For details about creating titlepage spec files and generating and
using titlepage customization layers, see "DocBook XSL: The
Complete Guide" <http://www.sagehill.net/docbookxsl/>

----------------------------------------------------------------------
Manifest
----------------------------------------------------------------------
AUTHORS       contact information
BUGS          about known problems
COPYING       copyright information
INSTALL       installation instructions
README        this file
RELEASE.*     per-release cumulative summaries of user-visible changes
TODO          about planned features not yet implemented
VERSION       release metadata, including the current version
              number (note that the VERSION file is an XSL stylesheet)
NEWS          changes since the last public release (for a cumulative list of
              changes, see the ChangeHistory.xml file)

assembly/     for making and processing DocBook assemblies.
common/       code used among several output formats (HTML, FO, manpages,...)
docsrc/       documentation sources
eclipse/      for producing Eclipse Help
epub/         for producing .epub version 2.
epub3/        for producing .epub version 3 and beyond.
extensions/   DocBook XSL Java extensions
fo/           for producing XSL-FO
highlighting  files used for adding source-code syntax highlighting in output
html/         for producing HTML
htmlhelp/     for producing HTML Help
images/       images used in callouts and graphical admonitions
javahelp/     for producing Java Help
lib/          utility stylesheets with schema-independent functions
manpages/     for producing groff/troff man pages
profiling/    for profiling (omitting/including conditional text)
roundtrip/    for "round trip" conversion among DocBook and
              various word-processor formats (WordML, etc.)
slides/       for producing slides output (from Slides source)
template/     templates for building stylesheet customization layers
tools/        assorted supplementary tools
webhelp/      templates and scripts for generating webhelp output
website/      for producing website output (from Website source)
xhtml/        for producing XHTML
xhtml-1_1/    for producing (stricter) XHTML 1.1
xhtml5/       for producing XHTML5

----------------------------------------------------------------------
Changes
----------------------------------------------------------------------
See the NEWS file for changes made since the previous release.

See the RELEASE-NOTES.html or RELEASE-NOTES.txt or RELEASE-NOTES.pdf
files for per-release cumulative summaries of significant
user-visible changes.

For online access to a hyperlinked view of all changes made over
the entire history of the codebase, see the following:

  http://docbook.svn.sourceforge.net/viewvc/docbook/trunk/xsl/?view=log

WARNING: That above change history is a very long list and may
take a long time to load/download.

You can also create an XML-formatted "ChangeHistory.xml" copy of
the complete change history for the codebase by running the
following commands:

  svn checkout https://docbook.svn.sf.net/svnroot/docbook/trunk/xsl
  svn log --xml --verbose xsl > ChangeHistory.xml

----------------------------------------------------------------------
Copyright information
----------------------------------------------------------------------
See the accompanying file named COPYING.
