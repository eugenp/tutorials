<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                xmlns:date="http://exslt.org/dates-and-times"
                exclude-result-prefixes="doc date"
                version='1.0'>

<!-- ********************************************************************
     $Id: refentry.xsl 7867 2008-03-07 09:54:25Z xmldoc $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->
<doc:reference xmlns="" xml:id="refentry">
  <info>
    <title>Common » Refentry Metadata Template Reference</title>
    <releaseinfo role="meta">
      $Id: refentry.xsl 7867 2008-03-07 09:54:25Z xmldoc $
    </releaseinfo>
  </info>
  <!-- * yes, partintro is a valid child of a reference... -->
  <partintro xml:id="partintro">
    <title>Introduction</title>
    <para>This is technical reference documentation for the “refentry
    metadata” templates in the DocBook XSL Stylesheets.</para>
    <para>This is not intended to be user documentation. It is provided
    for developers writing customization layers for the stylesheets.</para>
    <note>
      <para>Currently, only the manpages stylesheets make use of these
      templates. They are, however, potentially useful elsewhere.</para>
    </note>
  </partintro>
</doc:reference>

<!-- ==================================================================== -->
<doc:template name="get.refentry.metadata" xmlns="">
  <refpurpose>Gathers metadata from a refentry and its ancestors</refpurpose>
  <refdescription id="get.refentry.metadata-desc">
    <para>Reference documentation for particular commands, functions,
    etc., is sometimes viewed in isolation from its greater "context". For
    example, users view Unix man pages as, well, individual pages, not as
    part of a "book" of some kind. Therefore, it is sometimes necessary to
    embed "context" information in output for each <tag>refentry</tag>.</para>

    <para>However, one problem is that different users mark up that
    context information in different ways. Often (usually), the
    context information is not actually part of the content of the
    <tag>refentry</tag> itself, but instead part of the content of a
    parent or ancestor element to the <tag>refentry</tag>. And
    even then, DocBook provides a variety of elements that users might
    potentially use to mark up the same kind of information. One user
    might use the <tag>productnumber</tag> element to mark up version
    information about a particular product, while another might use
    the <tag>releaseinfo</tag> element.</para>

    <para>Taking all that in mind, the
    <function>get.refentry.metadata</function> template tries to gather
    metadata from a <tag>refentry</tag> element and its ancestor
    elements in an intelligent and user-configurable way. The basic
    mechanism used in the XPath expressions throughout this stylesheet
    is to select the relevant metadata from the *info element that is
    closest to the actual <tag>refentry</tag>&#160;– either on the
    <tag>refentry</tag> itself, or on its nearest ancestor.</para>

    <note>
      <para>The <function>get.refentry.metadata</function>
        template is actually just sort of a "driver" template; it
        calls other templates that do the actual data collection,
        then returns the data as a set.</para>
    </note>

  </refdescription>
  <refparameter id="get.refentry.metadata-params">
    <variablelist>
      <varlistentry>
        <term>refname</term>
        <listitem>
          <para>The first <tag>refname</tag> in the refentry</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>info</term>
        <listitem>
          <para>A set of info nodes (from a <tag>refentry</tag>
          element and its ancestors)</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>prefs</term>
        <listitem>
          <para>A node containing user preferences (from global
          stylesheet parameters)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="get.refentry.metadata-returns">
    <para>Returns a node set with the following elements. The
    descriptions are verbatim from the <literal>man(7)</literal> man
    page.
    <variablelist>
      <varlistentry>
        <term>title</term>
        <listitem>
          <para>the title of the man page (e.g., <literal>MAN</literal>)</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>section</term>
        <listitem>
          <para>the section number the man page should be placed in (e.g.,
          <literal>7</literal>)</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>date</term>
        <listitem>
          <para>the date of the last revision</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>source</term>
        <listitem>
          <para>the source of the command</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>manual</term>
        <listitem>
          <para>the title of the manual (e.g., <citetitle>Linux
          Programmer's Manual</citetitle>)</para>
        </listitem>
      </varlistentry>
    </variablelist>
    </para>
  </refreturn>
</doc:template>
<xsl:template name="get.refentry.metadata">
  <xsl:param name="refname"/>
  <xsl:param name="info"/>
  <xsl:param name="prefs"/>
  <title>
    <xsl:call-template name="get.refentry.title">
      <xsl:with-param name="refname" select="$refname"/>
    </xsl:call-template>
  </title>
  <section>
    <xsl:call-template name="get.refentry.section">
      <xsl:with-param name="refname" select="$refname"/>
    </xsl:call-template>
  </section>
  <date>
    <xsl:call-template name="get.refentry.date">
      <xsl:with-param name="info" select="$info"/>
      <xsl:with-param name="refname" select="$refname"/>
      <xsl:with-param name="prefs" select="$prefs/DatePrefs"/>
    </xsl:call-template>
  </date>
  <source>
    <xsl:call-template name="get.refentry.source">
      <xsl:with-param name="info" select="$info"/>
      <xsl:with-param name="refname" select="$refname"/>
      <xsl:with-param name="prefs" select="$prefs/SourcePrefs"/>
    </xsl:call-template>
  </source>
  <manual>
    <xsl:call-template name="get.refentry.manual">
      <xsl:with-param name="info" select="$info"/>
      <xsl:with-param name="refname" select="$refname"/>
      <xsl:with-param name="prefs" select="$prefs/ManualPrefs"/>
    </xsl:call-template>
  </manual>
</xsl:template>

<!-- ====================================================================== -->
<doc:template name="get.refentry.title" xmlns="">
  <refpurpose>Gets title metadata for a refentry</refpurpose>
  <refdescription id="get.refentry.title-desc">
    <para>The <literal>man(7)</literal> man page describes this as "the
    title of the man page (e.g., <literal>MAN</literal>). This differs
    from <tag>refname</tag> in that, if the <tag>refentry</tag> has a
    <tag>refentrytitle</tag>, we use that as the <tag>title</tag>;
    otherwise, we just use first <tag>refname</tag> in the first
    <tag>refnamediv</tag> in the source.</para>
  </refdescription>
  <refparameter id="get.refentry.title-params">
    <variablelist>
      <varlistentry>
        <term>refname</term>
        <listitem>
          <para>The first <tag>refname</tag> in the refentry</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="get.refentry.title-returns">
  <para>Returns a <tag>title</tag> node.</para></refreturn>
</doc:template>
<xsl:template name="get.refentry.title">
  <xsl:param name="refname"/>
  <xsl:choose>
    <xsl:when test="refmeta/refentrytitle">
      <xsl:copy>
        <xsl:apply-templates select="refmeta/refentrytitle/node()"/>
      </xsl:copy>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$refname"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->
<doc:template name="get.refentry.section" xmlns="">
  <refpurpose>Gets section metadata for a refentry</refpurpose>
  <refdescription id="get.refentry.section-desc">
    <para>The <literal>man(7)</literal> man page describes this as "the
    section number the man page should be placed in (e.g.,
    <literal>7</literal>)". If we do not find a <tag>manvolnum</tag>
    specified in the source, and we find that the <tag>refentry</tag> is
    for a function, we use the section number <literal>3</literal>
    ["Library calls (functions within program libraries)"]; otherwise, we
    default to using <literal>1</literal> ["Executable programs or shell
    commands"].</para>
  </refdescription>
  <refparameter id="get.refentry.section-params">
    <variablelist>
      <varlistentry>
        <term>refname</term>
        <listitem>
          <para>The first <tag>refname</tag> in the refentry</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>quiet</term>
        <listitem>
          <para>If non-zero, no "missing" message is emitted</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="get.refentry.section-returns">
  <para>Returns a string representing a section number.</para></refreturn>
</doc:template>
<xsl:template name="get.refentry.section">
  <xsl:param name="refname"/>
  <xsl:param name="quiet" select="0"/>
  <xsl:choose>
    <xsl:when test="refmeta/manvolnum">
      <xsl:value-of select="refmeta/manvolnum"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="$quiet = 0">
        <xsl:if test="$refentry.meta.get.quietly = 0">
          <xsl:call-template name="log.message">
            <xsl:with-param name="level">Note</xsl:with-param>
            <xsl:with-param name="source" select="$refname"/>
            <xsl:with-param name="context-desc">meta manvol</xsl:with-param>
            <xsl:with-param name="message">
              <xsl:text>no refentry/refmeta/manvolnum</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="log.message">
            <xsl:with-param name="level">Note</xsl:with-param>
            <xsl:with-param name="source" select="$refname"/>
            <xsl:with-param name="context-desc">meta manvol</xsl:with-param>
            <xsl:with-param name="message">
              <xsl:text>see http://docbook.sf.net/el/manvolnum</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:if>
      </xsl:if>
      <xsl:choose>
        <xsl:when test=".//funcsynopsis">
          <xsl:if test="$quiet = 0">
            <xsl:if test="$refentry.meta.get.quietly = 0">
              <xsl:call-template name="log.message">
                <xsl:with-param name="level">Note</xsl:with-param>
                <xsl:with-param name="source" select="$refname"/>
                <xsl:with-param name="context-desc">meta manvol</xsl:with-param>
                <xsl:with-param name="message">
                  <xsl:text>Setting man section to 3</xsl:text>
                </xsl:with-param>
              </xsl:call-template>
            </xsl:if>
          </xsl:if>
          <xsl:text>3</xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <xsl:text>1</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->
<doc:template name="get.refentry.date" xmlns="">
  <refpurpose>Gets date metadata for a refentry</refpurpose>
  <refdescription id="get.refentry.date-desc">
    <para>The <literal>man(7)</literal> man page describes this as "the
    date of the last revision". If we cannot find a date in the source, we
    generate one.</para>
  </refdescription>
  <refparameter id="get.refentry.date-params">
    <variablelist>
      <varlistentry>
        <term>refname</term>
        <listitem>
          <para>The first <tag>refname</tag> in the refentry</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>info</term>
        <listitem>
          <para>A set of info nodes (from a <tag>refentry</tag>
          element and its ancestors)</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>prefs</term>
        <listitem>
          <para>A node containing users preferences (from global stylesheet parameters)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="get.refentry.date-returns">
    <para>Returns a <tag>date</tag> node.</para>
  </refreturn>
</doc:template>
<xsl:template name="get.refentry.date">
  <xsl:param name="refname"/>
  <xsl:param name="info"/>
  <xsl:param name="prefs"/>
  <xsl:variable name="Date">
    <xsl:choose>
      <!-- * if profiling is enabled for date, and the date -->
      <!-- * profile is non-empty, use it -->
      <xsl:when test="not($prefs/@profileEnabled = 0) and
                      not($prefs/@profile = '')">
        <xsl:call-template name="evaluate.info.profile">
          <xsl:with-param name="profile" select="$prefs/@profile"/>
          <xsl:with-param name="info" select="$info"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <!-- * either profiling is not enabled for date, or the-->
        <!-- * date profile is empty, so we need to look for date -->
        <!-- * in *info -->
        <xsl:choose>
          <!-- * look for date or pubdate in *info -->
          <xsl:when test="$info/date/node()
                          |$info/pubdate/node()">
            <xsl:apply-templates
                select="(($info[date])[last()]/date)[1]|
                        (($info[pubdate])[last()]/pubdate)[1]"/>
          </xsl:when>
          <xsl:otherwise>
            <!-- * found no Date or Pubdate -->
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="not($Date = '')">
      <xsl:value-of select="$Date"/>
    </xsl:when>
    <!-- * We couldn't find a date, so we generate a date. -->
    <!-- * And we make it an appropriately localized date. -->
    <xsl:otherwise>
      <!-- * The following block is commented out because: -->
      <!-- *  -->
      <!-- * - having a missing date in the source doesn’t result in -->
      <!-- *   any information being missing from the generated man -->
      <!-- *   page (since we generate the needed date) -->
      <!-- *  -->
      <!-- * - experience has shown the many users omit the date -->
      <!-- *   intentionally, because they want to be it generated -->
      <!-- *  -->
      <!-- * - in practice it’s not really a condition that most users -->
      <!-- *   want reported to them -->
      <!-- *  -->
      <!-- * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
      <!-- * <xsl:if test="$refentry.meta.get.quietly = 0"> -->
        <!-- * <xsl:call-template name="log.message"> -->
          <!-- * <xsl:with-param name="level">Note</xsl:with-param> -->
          <!-- * <xsl:with-param name="source" select="$refname"/> -->
          <!-- * <xsl:with-param name="context-desc">meta date</xsl:with-param> -->
          <!-- * <xsl:with-param name="message"> -->
            <!-- * <xsl:text>no date; using generated date</xsl:text> -->
          <!-- * </xsl:with-param> -->
        <!-- * </xsl:call-template> -->
        <!-- * <xsl:call-template name="log.message"> -->
          <!-- * <xsl:with-param name="level">Note</xsl:with-param> -->
          <!-- * <xsl:with-param name="source" select="$refname"/> -->
          <!-- * <xsl:with-param name="context-desc">meta date</xsl:with-param> -->
          <!-- * <xsl:with-param name="message"> -->
            <!-- * <xsl:text>see http://docbook.sf.net/el/date</xsl:text> -->
          <!-- * </xsl:with-param> -->
        <!-- * </xsl:call-template> -->
      <!-- * </xsl:if> -->
      <!-- * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
      <xsl:call-template name="datetime.format">
        <xsl:with-param name="date">
          <xsl:choose>
            <xsl:when test="function-available('date:date-time')">
              <xsl:value-of select="date:date-time()"/>
            </xsl:when>
            <xsl:when test="function-available('date:dateTime')">
              <!-- Xalan quirk -->
              <xsl:value-of select="date:dateTime()"/>
            </xsl:when>
          </xsl:choose>
        </xsl:with-param>
        <xsl:with-param name="format">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'datetime'"/>
            <xsl:with-param name="name" select="'format'"/>
          </xsl:call-template>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->
<doc:template name="get.refentry.source" xmlns="">
  <refpurpose>Gets source metadata for a refentry</refpurpose>
  <refdescription id="get.refentry.source-desc">
    <para>The <literal>man(7)</literal> man page describes this as "the
    source of the command", and provides the following examples:
    <itemizedlist>
      <listitem>
        <para>For binaries, use something like: GNU, NET-2, SLS
        Distribution, MCC Distribution.</para>
      </listitem>
      <listitem>
        <para>For system calls, use the version of the kernel that you are
        currently looking at: Linux 0.99.11.</para>
      </listitem>
      <listitem>
        <para>For library calls, use the source of the function: GNU, BSD
        4.3, Linux DLL 4.4.1.</para>
      </listitem>
    </itemizedlist>
    </para>

    <para>The <literal>solbook(5)</literal> man page describes
    something very much like what <literal>man(7)</literal> calls
    "source", except that <literal>solbook(5)</literal> names it
    "software" and describes it like this:
    <blockquote>
      <para>This is the name of the software product that the topic
      discussed on the reference page belongs to. For example UNIX
      commands are part of the <literal>SunOS x.x</literal>
      release.</para>
    </blockquote>
    </para>

    <para>In practice, there are many pages that simply have a version
    number in the "source" field. So, it looks like what we have is a
    two-part field,
    <replaceable>Name</replaceable>&#160;<replaceable>Version</replaceable>,
    where:
    <variablelist>
      <varlistentry>
        <term>Name</term>
        <listitem>
          <para>product name (e.g., BSD) or org. name (e.g., GNU)</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>Version</term>
        <listitem>
          <para>version name</para>
        </listitem>
      </varlistentry>
    </variablelist>
    Each part is optional. If the <replaceable>Name</replaceable> is a
    product name, then the <replaceable>Version</replaceable> is probably
    the version of the product. Or there may be no
    <replaceable>Name</replaceable>, in which case, if there is a
    <replaceable>Version</replaceable>, it is probably the version of the
    item itself, not the product it is part of. Or, if the
    <replaceable>Name</replaceable> is an organization name, then there
    probably will be no <replaceable>Version</replaceable>.
    </para>
  </refdescription>
  <refparameter id="get.refentry.source-params">
    <variablelist>
      <varlistentry>
        <term>refname</term>
        <listitem>
          <para>The first <tag>refname</tag> in the refentry</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>info</term>
        <listitem>
          <para>A set of info nodes (from a <tag>refentry</tag>
          element and its ancestors)</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>prefs</term>
        <listitem>
          <para>A node containing users preferences (from global
          stylesheet parameters)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="get.refentry.source-returns">
    <para>Returns a <tag>source</tag> node.</para>
  </refreturn>
</doc:template>
<xsl:template name="get.refentry.source">
  <xsl:param name="refname"/>
  <xsl:param name="info"/>
  <xsl:param name="prefs"/>
  <xsl:variable name="Name">
    <xsl:if test="$prefs/Name/@suppress = 0">
      <xsl:call-template name="get.refentry.source.name">
        <xsl:with-param name="info" select="$info"/>
        <xsl:with-param name="refname" select="$refname"/>
        <xsl:with-param name="prefs" select="$prefs/Name"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="Version">
    <xsl:if test="$prefs/Version/@suppress = 0">
      <xsl:call-template name="get.refentry.version">
        <xsl:with-param name="info" select="$info"/>
        <xsl:with-param name="refname" select="$refname"/>
        <xsl:with-param name="prefs" select="$prefs/Version"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:choose>
    <!-- * if we have a Name and/or Version, use either or both -->
    <!-- * of those, in the form "Name Version" or just "Name" -->
    <!-- * or just "Version" -->
    <xsl:when test="not($Name = '') or not($Version = '')">
      <xsl:choose>
        <xsl:when test="not($Name = '') and not($Version = '')">
          <xsl:copy-of select="$Name"/>
          <xsl:text> </xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <xsl:copy-of select="$Name"/>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:copy-of select="$Version"/>
    </xsl:when>
    <!-- * if no Name and no Version, use fallback (if any) -->
    <xsl:when test="not($prefs/@fallback = '')">
      <xsl:variable name="source.fallback">
        <xsl:call-template name="evaluate.info.profile">
          <xsl:with-param name="profile" select="$prefs/@fallback"/>
          <xsl:with-param name="info" select="$info"/>
        </xsl:call-template>
      </xsl:variable>
      <!-- * At this point, we know that we don't have properly marked-up -->
      <!-- * source metadata, so even if we do have source fallback -->
      <!-- * content, we still report to the user that it should be -->
      <!-- * marked up properly instead. -->
      <xsl:if test="$refentry.meta.get.quietly = 0">
        <xsl:call-template name="report.missing.source.name">
          <xsl:with-param name="refname" select="$refname"/>
        </xsl:call-template>
        <xsl:call-template name="report.missing.version">
          <xsl:with-param name="refname" select="$refname"/>
        </xsl:call-template>
      </xsl:if>
      <xsl:choose>
        <xsl:when test="not($source.fallback = '')">
          <xsl:value-of select="$source.fallback"/>
          <xsl:if test="$refentry.meta.get.quietly = 0">
            <xsl:call-template name="log.message">
              <xsl:with-param name="level">Warn</xsl:with-param>
              <xsl:with-param name="source" select="$refname"/>
              <xsl:with-param name="context-desc">meta source</xsl:with-param>
              <xsl:with-param name="message">
                <xsl:text>using</xsl:text>
                <xsl:text> "</xsl:text>
                <xsl:value-of select="$source.fallback"/>
                <xsl:text>" </xsl:text>
                <xsl:text>for "source"</xsl:text>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:if>
        </xsl:when>
        <xsl:otherwise>
          <!-- * we have no Name, no Version, and no fallback content, so -->
          <!-- * insert a fixme -->
          <xsl:text>[FIXME: source]</xsl:text>
          <xsl:if test="$refentry.meta.get.quietly = 0">
            <xsl:call-template name="log.message">
              <xsl:with-param name="level">Warn</xsl:with-param>
              <xsl:with-param name="source" select="$refname"/>
              <xsl:with-param name="context-desc">meta source</xsl:with-param>
              <xsl:with-param name="message">
                <xsl:text>no fallback for source, so inserted a fixme</xsl:text>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:if>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <!-- * we have no Name, no Version, and no fallback given, so -->
      <!-- * insert a fixme -->
      <xsl:text>[FIXME: source]</xsl:text>
      <xsl:if test="$refentry.meta.get.quietly = 0">
        <xsl:call-template name="log.message">
          <xsl:with-param name="level">Warn</xsl:with-param>
          <xsl:with-param name="source" select="$refname"/>
          <xsl:with-param name="context-desc">meta source</xsl:with-param>
          <xsl:with-param name="message">
            <xsl:text>no source fallback given, so inserted a fixme</xsl:text>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->
<doc:template name="get.refentry.source.name" xmlns="">
  <refpurpose>Gets source-name metadata for a refentry</refpurpose>
  <refdescription id="get.refentry.source.name-desc">
    <para>A "source name" is one part of a (potentially) two-part
    <replaceable>Name</replaceable>&#160;<replaceable>Version</replaceable>
    source field. For more details, see the documentation for the
    <function>get.refentry.source</function> template.</para>
  </refdescription>
  <refparameter id="get.refentry.source.name-params">
    <variablelist>
      <varlistentry>
        <term>refname</term>
        <listitem>
          <para>The first <tag>refname</tag> in the refentry</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>info</term>
        <listitem>
          <para>A set of info nodes (from a <tag>refentry</tag>
          element and its ancestors)</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>prefs</term>
        <listitem>
          <para>A node containing users preferences (from global
          stylesheet parameters)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="get.refentry.source.name-returns">
    <para>Depending on what output method is used for the
  current stylesheet, either returns a text node or possibly an element
  node, containing "source name" data.</para>
  </refreturn>
</doc:template>
<xsl:template name="get.refentry.source.name">
  <xsl:param name="refname"/>
  <xsl:param name="info"/>
  <xsl:param name="prefs"/>
  <xsl:choose>
    <!-- * if profiling is enabled for source.name, and the -->
    <!-- * source.name profile is non-empty, use it -->
    <xsl:when test="not($prefs/@profileEnabled = 0) and
                    not($prefs/@profile = '')">
      <xsl:call-template name="evaluate.info.profile">
        <xsl:with-param name="profile" select="$prefs/@profile"/>
        <xsl:with-param name="info" select="$info"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <!-- * either profiling for source.name is not enabled, or-->
      <!-- * the source.name profile is empty; so we need to look -->
      <!-- * for a name to use -->
      <xsl:choose>
        <xsl:when test="refmeta/refmiscinfo[@class = 'source' or @class = 'software']">
          <xsl:apply-templates 
              select="refmeta/refmiscinfo[@class = 'source' or @class='software'][1]/node()"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:choose>
            <xsl:when test="$info/productname">
              <xsl:call-template name="set.refentry.metadata">
                <xsl:with-param name="refname" select="$refname"/>
                <xsl:with-param
                    name="info"
                    select="($info[productname])[last()]"/>
                <xsl:with-param
                    name="contents"
                    select="(($info[productname])[last()]/productname)[1]"/>
                <xsl:with-param name="context">source</xsl:with-param>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$info/corpname">
              <xsl:call-template name="set.refentry.metadata">
                <xsl:with-param name="refname" select="$refname"/>
                <xsl:with-param
                    name="info"
                    select="($info[corpname])[last()]"/>
                <xsl:with-param
                    name="contents"
                    select="(($info[corpname])[last()]/corpname)[1]"/>
                <xsl:with-param name="context">source</xsl:with-param>
                <xsl:with-param name="preferred">productname</xsl:with-param>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$info/corpcredit">
              <xsl:call-template name="set.refentry.metadata">
                <xsl:with-param name="refname" select="$refname"/>
                <xsl:with-param
                    name="info"
                    select="($info[corpcredit])[last()]"/>
                <xsl:with-param
                    name="contents"
                    select="(($info[corpcredit])[last()]/corpcredit)[1]"/>
                <xsl:with-param name="context">source</xsl:with-param>
                <xsl:with-param name="preferred">productname</xsl:with-param>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$info/corpauthor">
              <xsl:call-template name="set.refentry.metadata">
                <xsl:with-param name="refname" select="$refname"/>
                <xsl:with-param
                    name="info"
                    select="($info[corpauthor])[last()]"/>
                <xsl:with-param
                    name="contents"
                    select="(($info[corpauthor])[last()]/corpauthor)[1]"/>
                <xsl:with-param name="context">source</xsl:with-param>
                <xsl:with-param name="preferred">productname</xsl:with-param>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$info//orgname">
              <xsl:call-template name="set.refentry.metadata">
                <xsl:with-param name="refname" select="$refname"/>
                <xsl:with-param
                    name="info"
                    select="($info[//orgname])[last()]"/>
                <xsl:with-param
                    name="contents"
                    select="(($info[//orgname])[last()]//orgname)[1]"/>
                <xsl:with-param name="context">source</xsl:with-param>
                <xsl:with-param name="preferred">productname</xsl:with-param>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$info//publishername">
              <xsl:call-template name="set.refentry.metadata">
                <xsl:with-param name="refname" select="$refname"/>
                <xsl:with-param
                    name="info"
                    select="($info[//publishername])[last()]"/>
                <xsl:with-param
                    name="contents"
                    select="(($info[//publishername])[last()]//publishername)[1]"/>
                <xsl:with-param name="context">source</xsl:with-param>
                <xsl:with-param name="preferred">productname</xsl:with-param>
              </xsl:call-template>
            </xsl:when>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="report.missing.source.name">
  <xsl:param name="refname"/>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta source</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>no *info/productname or alternative</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta source</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>see http://docbook.sf.net/el/productname</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta source</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>no refentry/refmeta/refmiscinfo@class=source</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta source</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>see http://docbook.sf.net/el/refmiscinfo</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<!-- ==================================================================== -->
<doc:template name="get.refentry.version" xmlns="">
  <refpurpose>Gets version metadata for a refentry</refpurpose>
  <refdescription id="get.refentry.version-desc">
    <para>A "version" is one part of a (potentially) two-part
    <replaceable>Name</replaceable>&#160;<replaceable>Version</replaceable>
    source field. For more details, see the documentation for the
    <function>get.refentry.source</function> template.</para>
  </refdescription>
  <refparameter id="get.refentry.version-params">
    <variablelist>
      <varlistentry>
        <term>refname</term>
        <listitem>
          <para>The first <tag>refname</tag> in the refentry</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>info</term>
        <listitem>
          <para>A set of info nodes (from a <tag>refentry</tag>
          element and its ancestors)</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>prefs</term>
        <listitem>
          <para>A node containing users preferences (from global
          stylesheet parameters)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="get.refentry.version-returns">
    <para>Depending on what output method is used for the
  current stylesheet, either returns a text node or possibly an element
  node, containing "version" data.</para>
  </refreturn>
</doc:template>
<xsl:template name="get.refentry.version">
  <xsl:param name="refname"/>
  <xsl:param name="info"/>
  <xsl:param name="prefs"/>
  <xsl:choose>
    <!-- * if profiling is enabled for version, and the -->
    <!-- * version profile is non-empty, use it -->
    <xsl:when test="not($prefs/@profileEnabled = 0) and
                    not($prefs/@profile = '')">
      <xsl:call-template name="evaluate.info.profile">
        <xsl:with-param name="profile" select="$prefs/@profile"/>
        <xsl:with-param name="info" select="$info"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <!-- * either profiling for source.name is not enabled, or-->
      <!-- * the source.name profile is empty; so we need to look -->
      <!-- * for a name to use -->
      <xsl:choose>
        <xsl:when test="refmeta/refmiscinfo[@class = 'version']">
          <xsl:apply-templates 
              select="refmeta/refmiscinfo[@class = 'version'][1]/node()"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:choose>
            <xsl:when test="$info/productnumber">
              <xsl:call-template name="set.refentry.metadata">
                <xsl:with-param name="refname" select="$refname"/>
                <xsl:with-param
                    name="info"
                    select="($info[productnumber])[last()]"/>
                <xsl:with-param
                    name="contents"
                    select="(($info[productnumber])[last()]/productnumber)[1]"/>
                <xsl:with-param name="context">version</xsl:with-param>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$info/edition">
              <xsl:call-template name="set.refentry.metadata">
                <xsl:with-param name="refname" select="$refname"/>
                <xsl:with-param
                    name="info"
                    select="($info[edition])[last()]"/>
                <xsl:with-param
                    name="contents"
                    select="(($info[edition])[last()]/edition)[1]"/>
                <xsl:with-param name="context">version</xsl:with-param>
                <xsl:with-param name="preferred">productnumber</xsl:with-param>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$info/releaseinfo">
              <xsl:call-template name="set.refentry.metadata">
                <xsl:with-param name="refname" select="$refname"/>
                <xsl:with-param
                    name="info"
                    select="($info[releaseinfo])[last()]"/>
                <xsl:with-param
                    name="contents"
                    select="(($info[releaseinfo])[last()]/releaseinfo)[1]"/>
                <xsl:with-param name="context">version</xsl:with-param>
                <xsl:with-param name="preferred">productnumber</xsl:with-param>
              </xsl:call-template>
            </xsl:when>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="report.missing.version">
  <xsl:param name="refname"/>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta version</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>no *info/productnumber or alternative</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta version</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>see http://docbook.sf.net/el/productnumber</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta version</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>no refentry/refmeta/refmiscinfo@class=version</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta version</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>see http://docbook.sf.net/el/refmiscinfo</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<!-- ==================================================================== -->
<doc:template name="get.refentry.manual" xmlns="">
  <refpurpose>Gets source metadata for a refentry</refpurpose>
  <refdescription id="get.refentry.manual-desc">
    <para>The <literal>man(7)</literal> man page describes this as "the
    title of the manual (e.g., <citetitle>Linux Programmer's
    Manual</citetitle>)". Here are some examples from existing man pages:
    <itemizedlist>
      <listitem>
        <para><citetitle>dpkg utilities</citetitle>
        (<command>dpkg-name</command>)</para>
      </listitem>
      <listitem>
        <para><citetitle>User Contributed Perl Documentation</citetitle>
        (<command>GET</command>)</para>
      </listitem>
      <listitem>
        <para><citetitle>GNU Development Tools</citetitle>
        (<command>ld</command>)</para>
      </listitem>
      <listitem>
        <para><citetitle>Emperor Norton Utilities</citetitle>
        (<command>ddate</command>)</para>
      </listitem>
      <listitem>
        <para><citetitle>Debian GNU/Linux manual</citetitle>
        (<command>faked</command>)</para>
      </listitem>
      <listitem>
        <para><citetitle>GIMP Manual Pages</citetitle>
        (<command>gimp</command>)</para>
      </listitem>
      <listitem>
        <para><citetitle>KDOC Documentation System</citetitle>
        (<command>qt2kdoc</command>)</para>
      </listitem>
    </itemizedlist>
    </para>

    <para>The <literal>solbook(5)</literal> man page describes
    something very much like what <literal>man(7)</literal> calls
    "manual", except that <literal>solbook(5)</literal> names it
    "sectdesc" and describes it like this:
    <blockquote>
      <para>This is the section title of the reference page; for
      example <literal>User Commands</literal>.</para>
    </blockquote>
    </para>

  </refdescription>
  <refparameter id="get.refentry.manual-params">
    <variablelist>
      <varlistentry>
        <term>refname</term>
        <listitem>
          <para>The first <tag>refname</tag> in the refentry</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>info</term>
        <listitem>
          <para>A set of info nodes (from a <tag>refentry</tag>
          element and its ancestors)</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>prefs</term>
        <listitem>
          <para>A node containing users preferences (from global
          stylesheet parameters)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="get.refentry.manual-returns">
    <para>Returns a <tag>manual</tag> node.</para>
  </refreturn>
</doc:template>
<xsl:template name="get.refentry.manual">
  <xsl:param name="refname"/>
  <xsl:param name="info"/>
  <xsl:param name="prefs"/>
  <xsl:variable name="Manual">
    <xsl:choose>
      <!-- * if profiling is enabled for manual, and the manual -->
      <!-- * profile is non-empty, use it -->
      <xsl:when test="not($prefs/@profileEnabled = 0) and
                      not($prefs/@profile = '')">
        <xsl:call-template name="evaluate.info.profile">
          <xsl:with-param name="profile" select="$prefs/@profile"/>
          <xsl:with-param name="info" select="$info"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:choose>
          <xsl:when test="refmeta/refmiscinfo[@class = 'manual' or @class = 'sectdesc']">
            <xsl:apply-templates 
                select="refmeta/refmiscinfo[@class = 'manual' or @class = 'sectdesc'][1]/node()"/>
          </xsl:when>
          <xsl:otherwise>
            <!-- * only in the case of choosing appropriate -->
            <!-- * "manual" content do we select the furthest -->
            <!-- * (first) matching element instead of the -->
            <!-- * closest (last) matching one -->
            <xsl:choose>
              <xsl:when test="ancestor::*/title">
                <xsl:call-template name="set.refentry.metadata">
                  <xsl:with-param name="refname" select="$refname"/>
                  <xsl:with-param
                      name="info"
                      select="(ancestor::*[title])[1]"/>
                  <xsl:with-param
                      name="contents"
                      select="(ancestor::*[title])[1]/title"/>
                  <xsl:with-param name="context">manual</xsl:with-param>
                </xsl:call-template>
              </xsl:when>
              <xsl:when test="$info/title">
                <xsl:call-template name="set.refentry.metadata">
                  <xsl:with-param name="refname" select="$refname"/>
                  <xsl:with-param
                      name="info"
                      select="($info[title])[1]"/>
                  <xsl:with-param
                      name="contents"
                      select="(($info[title])[1]/title)[1]"/>
                  <xsl:with-param name="context">manual</xsl:with-param>
                </xsl:call-template>
              </xsl:when>
              <xsl:otherwise>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="not($Manual = '')">
      <xsl:copy-of select="$Manual"/>
    </xsl:when>
    <!-- * if no Manual, use contents of specified fallback (if any) -->
    <xsl:when test="not($prefs/@fallback = '')">
      <xsl:variable name="manual.fallback">
        <xsl:call-template name="evaluate.info.profile">
          <xsl:with-param name="profile" select="$prefs/@fallback"/>
          <xsl:with-param name="info" select="$info"/>
        </xsl:call-template>
      </xsl:variable>
      <!-- * At this point, we know that we don't have properly marked-up -->
      <!-- * manual metadata, so even if we do have manual fallback -->
      <!-- * content, we still report to the user that it should be -->
      <!-- * marked up properly instead. -->
      <xsl:if test="$refentry.meta.get.quietly = 0">
        <xsl:call-template name="report.missing.manual">
          <xsl:with-param name="refname" select="$refname"/>
        </xsl:call-template>
      </xsl:if>
      <xsl:choose>
        <xsl:when test="not($manual.fallback = '')">
          <xsl:value-of select="$manual.fallback"/>
          <xsl:if test="$refentry.meta.get.quietly = 0">
            <xsl:call-template name="log.message">
              <xsl:with-param name="level">Warn</xsl:with-param>
              <xsl:with-param name="source" select="$refname"/>
              <xsl:with-param name="context-desc">meta manual</xsl:with-param>
              <xsl:with-param name="message">
                <xsl:text>using</xsl:text>
                <xsl:text> "</xsl:text>
                <xsl:value-of select="$manual.fallback"/>
                <xsl:text>" </xsl:text>
                <xsl:text>for "manual"</xsl:text>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:if>
        </xsl:when>
        <xsl:otherwise>
          <!-- * we have nothing appropriate to use for manual, and no fallback -->
          <!-- * content, so report insert a fixme -->
          <xsl:text>[FIXME: manual]</xsl:text>
          <xsl:if test="$refentry.meta.get.quietly = 0">
            <xsl:call-template name="log.message">
              <xsl:with-param name="level">Warn</xsl:with-param>
              <xsl:with-param name="source" select="$refname"/>
              <xsl:with-param name="context-desc">meta manual</xsl:with-param>
              <xsl:with-param name="message">
                <xsl:text>no fallback for manual, so inserted a fixme</xsl:text>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:if>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <!-- * we have nothing appropriate to use for manual, and no fallback -->
      <!-- * given, so insert a fixme -->
      <xsl:text>[FIXME: manual]</xsl:text>
      <xsl:if test="$refentry.meta.get.quietly = 0">
        <xsl:call-template name="log.message">
          <xsl:with-param name="level">Warn</xsl:with-param>
          <xsl:with-param name="source" select="$refname"/>
          <xsl:with-param name="context-desc">meta manual</xsl:with-param>
          <xsl:with-param name="message">
            <xsl:text>no manual fallback given, so inserted a fixme</xsl:text>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="report.missing.manual">
  <xsl:param name="refname"/>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta manual</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>no titled ancestor of refentry</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta manual</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>no refentry/refmeta/refmiscinfo@class=manual</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
  <xsl:call-template name="log.message">
    <xsl:with-param name="level">Note</xsl:with-param>
    <xsl:with-param name="source" select="$refname"/>
    <xsl:with-param name="context-desc">meta manual</xsl:with-param>
    <xsl:with-param name="message">
      <xsl:text>see http://docbook.sf.net/el/refmiscinfo</xsl:text>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>
<!-- ====================================================================== -->
<doc:template name="get.refentry.metadata.prefs" xmlns="">
  <refpurpose>Gets user preferences for refentry metadata gathering</refpurpose>
  <refdescription id="get.refentry.metadata.prefs-desc">
    <para>The DocBook XSL stylesheets include several user-configurable
    global stylesheet parameters for controlling <tag>refentry</tag>
    metadata gathering. Those parameters are not read directly by the
    other <tag>refentry</tag> metadata-gathering
    templates. Instead, they are read only by the
    <function>get.refentry.metadata.prefs</function> template,
    which assembles them into a structure that is then passed to
    the other <tag>refentry</tag> metadata-gathering
    templates.</para>

    <para>So the, <function>get.refentry.metadata.prefs</function>
    template is the only interface to collecting stylesheet parameters for
    controlling <tag>refentry</tag> metadata gathering.</para>
  </refdescription>
  <refparameter id="get.refentry.metadata.prefs-params">
    <para>There are no local parameters for this template; however, it
    does rely on a number of global parameters.</para>
  </refparameter>
  <refreturn id="get.refentry.metadata.prefs-returns">
    <para>Returns a <tag>manual</tag> node.</para>
  </refreturn>
</doc:template>
<xsl:template name="get.refentry.metadata.prefs">
  <DatePrefs>
    <xsl:attribute name="profile">
      <xsl:value-of select="$refentry.date.profile"/>
    </xsl:attribute>
    <xsl:attribute name="profileEnabled">
      <xsl:value-of select="$refentry.date.profile.enabled"/>
    </xsl:attribute>
  </DatePrefs>
  <SourcePrefs>
    <xsl:attribute name="fallback">
      <xsl:value-of select="$refentry.source.fallback.profile"/>
    </xsl:attribute>
    <Name>
      <xsl:attribute name="profile">
        <xsl:value-of select="$refentry.source.name.profile"/>
      </xsl:attribute>
      <xsl:attribute name="profileEnabled">
        <xsl:value-of select="$refentry.source.name.profile.enabled"/>
      </xsl:attribute>
      <xsl:attribute name="suppress">
        <xsl:value-of select="$refentry.source.name.suppress"/>
      </xsl:attribute>
    </Name>
    <Version>
      <xsl:attribute name="profile">
        <xsl:value-of select="$refentry.version.profile"/>
      </xsl:attribute>
      <xsl:attribute name="profileEnabled">
        <xsl:value-of select="$refentry.version.profile.enabled"/>
      </xsl:attribute>
      <xsl:attribute name="suppress">
        <xsl:value-of select="$refentry.version.suppress"/>
      </xsl:attribute>
    </Version>
  </SourcePrefs>
  <ManualPrefs>
    <xsl:attribute name="fallback">
      <xsl:value-of select="$refentry.manual.fallback.profile"/>
    </xsl:attribute>
    <xsl:attribute name="profile">
      <xsl:value-of select="$refentry.manual.profile"/>
    </xsl:attribute>
    <xsl:attribute name="profileEnabled">
      <xsl:value-of select="$refentry.manual.profile.enabled"/>
    </xsl:attribute>
  </ManualPrefs>
</xsl:template>

<!-- ====================================================================== -->
<doc:template name="set.refentry.metadata" xmlns="">
  <refpurpose>Sets content of a refentry metadata item</refpurpose>
  <refdescription id="set.refentry.metadata-desc">
    <para>The <function>set.refentry.metadata</function> template is
    called each time a suitable source element is found for a certain
    metadata field.</para>
  </refdescription>
  <refparameter id="set.refentry.metadata-params">
    <variablelist>
      <varlistentry>
        <term>refname</term>
        <listitem>
          <para>The first <tag>refname</tag> in the refentry</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>info</term>
        <listitem>
          <para>A single *info node that contains the selected source element.</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>contents</term>
        <listitem>
          <para>A node containing the selected source element.</para>
        </listitem>
      </varlistentry>
      <varlistentry>
        <term>context</term>
        <listitem>
          <para>A string describing the metadata context in which the
          <function>set.refentry.metadata</function> template was
          called: either "date", "source", "version", or "manual".</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refreturn id="set.refentry.metadata-returns">
  <para>Returns formatted contents of a selected source element.</para></refreturn>
</doc:template>
<xsl:template name="set.refentry.metadata">
  <xsl:param name="refname"/>
  <xsl:param name="info"/>
  <xsl:param name="contents"/>
  <xsl:param name="context"/>
  <xsl:param name="preferred"/>
  <!-- * <xsl:if test="not($preferred = '')"> -->
    <!-- * <xsl:if test="$refentry.meta.get.quietly = 0"> -->
      <!-- * <xsl:call-template name="log.message"> -->
        <!-- * <xsl:with-param name="level">Note</xsl:with-param> -->
        <!-- * <xsl:with-param name="source" select="$refname"/> -->
        <!-- * <xsl:with-param name="context-desc" select="concat('meta ', $context)"/> -->
        <!-- * <xsl:with-param name="message" select="concat('No ', $preferred)"/> -->
      <!-- * </xsl:call-template> -->
      <!-- * <xsl:call-template name="log.message"> -->
        <!-- * <xsl:with-param name="level">Note</xsl:with-param> -->
        <!-- * <xsl:with-param name="source" select="$refname"/> -->
        <!-- * <xsl:with-param name="context-desc" select="concat('meta ', $context)"/> -->
        <!-- * <xsl:with-param name="message"> -->
          <!-- * <xsl:text>no refentry/refmeta/refmiscinfo@class=</xsl:text> -->
          <!-- * <xsl:value-of select="$context"/> -->
        <!-- * </xsl:with-param> -->
      <!-- * </xsl:call-template> -->
      <!-- * <xsl:call-template name="log.message"> -->
        <!-- * <xsl:with-param name="level">Note</xsl:with-param> -->
        <!-- * <xsl:with-param name="source" select="$refname"/> -->
        <!-- * <xsl:with-param name="context-desc" select="concat('meta ', $context)"/> -->
        <!-- * <xsl:with-param name="message" select="concat('Using ', local-name($contents))"/> -->
      <!-- * </xsl:call-template> -->
    <!-- * </xsl:if> -->
  <!-- * </xsl:if> -->
  <xsl:value-of select="$contents"/>
</xsl:template>

</xsl:stylesheet>
