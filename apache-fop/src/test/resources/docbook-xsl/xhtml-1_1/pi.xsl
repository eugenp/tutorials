<?xml version="1.0" encoding="ASCII"?>
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:doc="http://nwalsh.com/xsl/documentation/1.0" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns="http://www.w3.org/1999/xhtml" exclude-result-prefixes="doc" version="1.0">

<!-- ********************************************************************
     $Id: pi.xsl 9022 2011-07-14 19:21:36Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<doc:reference xmlns=""><info xmlns="http://www.w3.org/1999/xhtml"><title>HTML Processing Instruction Reference</title>
    <releaseinfo role="meta">
      $Id: pi.xsl 9022 2011-07-14 19:21:36Z bobstayton $
    </releaseinfo>
  </info>
  <partintro xmlns="http://www.w3.org/1999/xhtml" xml:id="partintro">
    <title>Introduction</title>
    <para>This is generated reference documentation for all
      user-specifiable processing instructions (PIs) in the DocBook
      XSL stylesheets for HTML output.
      <note>
        <para>You add these PIs at particular points in a document to
          cause specific &#8220;exceptions&#8221; to formatting/output behavior. To
          make global changes in formatting/output behavior across an
          entire document, it&#8217;s better to do it by setting an
          appropriate stylesheet parameter (if there is one).</para>
      </note>
    </para>
  </partintro>
</doc:reference>

<!-- ==================================================================== -->

<doc:pi xmlns="" name="dbhtml_background-color">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Sets background color for an image</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml background-color</tag> PI before or
      after an image (<tag>graphic</tag>, <tag>inlinegraphic</tag>,
      <tag>imagedata</tag>, or <tag>videodata</tag> element) as a
      sibling to the element, to set a background color for the
      image.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml background-color="<replaceable>color</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>background-color="<replaceable>color</replaceable>"</term>
        <listitem>
          <para>An HTML color value</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="BGcolor.html">Background color</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_background-color">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'background-color'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_bgcolor">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Sets background color on a CALS table row or table cell</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml bgcolor</tag> PI as child of a CALS table row
      or cell to set a background color for that table row or cell.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml bgcolor="<replaceable>color</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>bgcolor="<replaceable>color</replaceable>"</term>
        <listitem>
          <para>An HTML color value</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="BGtableColor.html#CellBGColor">Cell background color</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_bgcolor">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'bgcolor'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_cellpadding">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies cellpadding in CALS table or qandaset output</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml cellpadding</tag> PI as a child of a
      CALS <tag>table</tag> or <tag>qandaset</tag> to specify the value
      for the HTML <literal>cellpadding</literal> attribute in the
      output HTML table.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml cellpadding="<replaceable>number</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>cellpadding="<replaceable>number</replaceable>"</term>
        <listitem>
          <para>Specifies the cellpadding</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>html.cellpadding</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="CellSpacing.html">Cell spacing and cell padding</link>,
      <link role="tcg" xlink:href="QandAformat.html">Q and A formatting</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_cellpadding">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'cellpadding'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_cellspacing">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies cellspacing in CALS table or qandaset output</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml cellspacing</tag> PI as a child of a
      CALS <tag>table</tag> or <tag>qandaset</tag> to specify the value
      for the HTML <literal>cellspacing</literal> attribute in the
      output HTML table.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml cellspacing="<replaceable>number</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>cellspacing="<replaceable>number</replaceable>"</term>
        <listitem>
          <para>Specifies the cellspacing</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>html.cellspacing</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="CellSpacing.html">Cell spacing and cell padding</link>,
      <link role="tcg" xlink:href="QandAformat.html">Q and A formatting</link></para>
    </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_cellspacing">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'cellspacing'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_class">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Set value of the class attribute for a CALS table row</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml class</tag> PI as a child of a
      <tag>row</tag> to specify a <literal>class</literal>
      attribute and value in the HTML output for that row.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml class="<replaceable>name</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>class="<replaceable>name</replaceable>"</term>
        <listitem>
          <para>Specifies the class name</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="CSSTableCells.html">Table styles in HTML output</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_class">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'class'"/>
  </xsl:call-template>
</xsl:template> 

<doc:pi xmlns="" name="dbhtml_dir">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies a directory name in which to write files</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>When chunking output, use the <tag class="xmlpi">dbhtml dir</tag> PI
      as a child of a chunk source to cause the output of that
      chunk to be written to the specified directory; also, use it
      as a child of a <tag>mediaobject</tag> to specify a
      directory into which any long-description files for that
      <tag>mediaobject</tag> will be written.</para>

<para>The output directory specification is inherited by all
chunks of the descendants of the element.  If descendants need
to go to a different directory, then add another 
<tag class="xmlpi">dbhtml dir</tag> processing
instruction as a child of the source element
for that chunk, and specify the path relative to the
ancestor path.</para>

<para>For example, to put most chunk files into 
<filename class="directory">shared</filename>
but one chapter into 
<filename class="directory">exception</filename>
at the same level, use:</para>

<programlisting>&lt;book&gt;
  &lt;?dbhtml dir="shared"?&gt;
  ...
  &lt;chapter&gt;
    &lt;?dbhtml dir="../exception"?&gt;
  &lt;/chapter&gt;
&lt;/book&gt;
</programlisting>


  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml dir="<replaceable>path</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>dir="<replaceable>path</replaceable>"</term>
        <listitem>
          <para>Specifies the pathname for the directory</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>base.dir</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Chunking.html#dbhtmlDirPI">dbhtml dir processing instruction</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_dir">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'dir'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_filename">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies a filename for a chunk</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
<para>When chunking output, use the <tag class="xmlpi">dbhtml filename</tag>
      PI as a child of a chunk source to specify a filename for
      the output file for that chunk. Include the filename suffix.</para>

<para>You cannot include a directory path in the filename value,
or your links may not work.  Add a 
<tag class="xmlpi">dbhtml dir</tag> processing instruction
to specify the output directory. You can also combine the two
specifications in one processing instruction: 
<tag class="xmlpi">dbhtml dir="mydir" filename="myfile.html"</tag>.</para>

  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml filename="<replaceable>filename</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>filename="<replaceable>path</replaceable>"</term>
        <listitem>
          <para>Specifies the filename for the file</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>use.id.as.filename</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Chunking.html#DbhtmlFilenames">dbhtml filenames</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_filename">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'filename'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_funcsynopsis-style">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies presentation style for a funcsynopsis</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml funcsynopsis-style</tag> PI as a child of
      a <tag>funcsynopsis</tag> or anywhere within a funcsynopsis
      to control the presentation style for output of all
      <tag>funcprototype</tag> instances within that funcsynopsis.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml funcsynopsis-style="kr"|"ansi"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>funcsynopsis-style="kr"</term>
        <listitem>
          <para>Displays <tag>funcprototype</tag> output in K&amp;R style</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>funcsynopsis-style="ansi"</term>
        <listitem>
          <para>Displays <tag>funcprototype</tag> output in ANSI style</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>funcsynopsis.style</parameter></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_funcsynopsis-style">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'funcsynopsis-style'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_img.src.path">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies a path to the location of an image file</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml img.src.path</tag> PI before or
      after an image (<tag>graphic</tag>,
      <tag>inlinegraphic</tag>, <tag>imagedata</tag>, or
      <tag>videodata</tag> element) as a sibling to the element,
      to specify a path to the location of the image; in HTML
      output, the value specified for the
      <code>img.src.path</code> attribute is prepended to the
      filename.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml img.src.path="<replaceable>path</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>img.src.path="<replaceable>path</replaceable>"</term>
        <listitem>
          <para>Specifies the pathname to prepend to the name of the image file</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>img.src.path</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="GraphicsLocations.html#UsingFileref">Using fileref</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_img.src.path">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'img.src.path'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_label-width">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies the label width for a qandaset</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml label-width</tag> PI as a child of a
      <tag>qandaset</tag> to specify the width of labels.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml label-width="<replaceable>width</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>label-width="<replaceable>width</replaceable>"</term>
        <listitem>
          <para>Specifies the label width (including units)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="QandAformat.html">Q and A formatting</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_label-width">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'label-width'"/>
  </xsl:call-template>
</xsl:template> 

<doc:pi xmlns="" name="dbhtml_linenumbering.everyNth">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies interval for line numbers in verbatims</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml linenumbering.everyNth</tag> PI as a child
      of a &#8220;verbatim&#8221; element &#8211; <tag>programlisting</tag>,
      <tag>screen</tag>, <tag>synopsis</tag> &#8212; to specify
      the interval at which lines are numbered.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml linenumbering.everyNth="<replaceable>N</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>linenumbering.everyNth="<replaceable>N</replaceable>"</term>
        <listitem>
          <para>Specifies numbering interval; a number is output
            before every <replaceable>N</replaceable>th line</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>linenumbering.everyNth</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="AnnotateListing.html#LineNumbering">Line numbering</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_linenumbering.everyNth">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'linenumbering.everyNth'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_linenumbering.separator">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies separator text for line numbers in verbatims</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml linenumbering.separator</tag> PI as a child
      of a &#8220;verbatim&#8221; element &#8211; <tag>programlisting</tag>,
      <tag>screen</tag>, <tag>synopsis</tag> &#8212; to specify
      the separator text output between the line numbers and content.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml linenumbering.separator="<replaceable>text</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>linenumbering.separator="<replaceable>text</replaceable>"</term>
        <listitem>
          <para>Specifies the text (zero or more characters)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>linenumbering.separator</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="AnnotateListing.html#LineNumbering">Line numbering</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_linenumbering.separator">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'linenumbering.separator'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_linenumbering.width">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies width for line numbers in verbatims</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml linenumbering.width</tag> PI as a child
      of a &#8220;verbatim&#8221; element &#8211; <tag>programlisting</tag>,
      <tag>screen</tag>, <tag>synopsis</tag> &#8212; to specify
      the width set aside for line numbers.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml linenumbering.width="<replaceable>width</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>linenumbering.width="<replaceable>width</replaceable>"</term>
        <listitem>
          <para>Specifies the width (inluding units)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>linenumbering.width</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="AnnotateListing.html#LineNumbering">Line numbering</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_linenumbering.width">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'linenumbering.width'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_list-presentation">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies presentation style for a variablelist or
    segmentedlist</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml list-presentation</tag> PI as a child of
      a <tag>variablelist</tag> or <tag>segmentedlist</tag> to
      control the presentation style for the list (to cause it, for
      example, to be displayed as a table).</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml list-presentation="list"|"table"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>list-presentation="list"</term>
        <listitem>
          <para>Displays the list as a list</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>list-presentation="table"</term>
        <listitem>
          <para>Displays the list as a table</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <itemizedlist>
      <listitem>
        <para><parameter>variablelist.as.table</parameter></para>
      </listitem>
      <listitem>
        <para><parameter>segmentedlist.as.table</parameter></para>
      </listitem>
    </itemizedlist>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Variablelists.html#VarListFormatting">Variable list formatting in HTML</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_list-presentation">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'list-presentation'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_list-width">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies the width of a variablelist or simplelist</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml list-width</tag> PI as a child of a
      <tag>variablelist</tag> or a <tag>simplelist</tag> presented
      as a table, to specify the output width.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml list-width="<replaceable>width</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>list-width="<replaceable>width</replaceable>"</term>
        <listitem>
          <para>Specifies the output width (including units)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Variablelists.html#VarListFormatting">Variable list formatting in HTML</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_list-width">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'list-width'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_row-height">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies the height for a CALS table row</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml row-height</tag> PI as a child of a
      <tag>row</tag> to specify the height of the row.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml row-height="<replaceable>height</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>row-height="<replaceable>height</replaceable>"</term>
        <listitem>
          <para>Specifies the row height (including units)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="RowHeight.html">Row height</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_row-height">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'row-height'"/>
  </xsl:call-template>
</xsl:template> 

<doc:pi xmlns="" name="dbhtml_start">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">(obsolete) Sets the starting number on an ordered list</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para><emphasis>This PI is obsolete</emphasis>. The intent of
      this PI was to provide a means for setting a specific starting
      number for an ordered list. Instead of this PI, set a value
      for the <literal>override</literal> attribute on the first
      <tag>listitem</tag> in the list; that will have the same
      effect as what this PI was intended for.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml start="<replaceable>character</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>start="<replaceable>character</replaceable>"</term>
        <listitem>
          <para>Specifies the character to use as the starting
            number; use 0-9, a-z, A-Z, or lowercase or uppercase
            Roman numerals</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Orderedlists.html#ListStartNum">List starting number</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_start">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="pi-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'start'"/>
  </xsl:call-template>
</xsl:template>

  <doc:pi xmlns="" name="dbhtml_stop-chunking">
	<refpurpose xmlns="http://www.w3.org/1999/xhtml">Do not chunk any descendants of this element.</refpurpose>
	<refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>When generating chunked HTML output, adding this PI as the child of an element that contains elements that would normally be generated on separate pages if generating chunked output causes chunking to stop at this point. No descendants of the current element will be split into new HTML pages:
<programlisting>&lt;section&gt;
&lt;title&gt;Configuring pencil&lt;/title&gt;
&lt;?dbhtml stop-chunking?&gt;

...

&lt;/section&gt;</programlisting>
</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml stop-chunking</tag></synopsis>
  </refsynopsisdiv>	
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Chunking.html">Chunking into multiple HTML files</link></para>
  </refsee>
  </doc:pi>
  <!-- The code that handles the stop-chunking pi is in chunk-common.xsl -->

<doc:pi xmlns="" name="dbhtml_table-summary">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies summary for CALS table, variablelist, segmentedlist, or qandaset output</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml table-summary</tag> PI as a child of
      a CALS <tag>table</tag>, <tag>variablelist</tag>,
      <tag>segmentedlist</tag>, or <tag>qandaset</tag> to specify
      the text for the HTML <literal>summary</literal> attribute
      in the output HTML table.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml table-summary="<replaceable>text</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>table-summary="<replaceable>text</replaceable>"</term>
        <listitem>
          <para>Specifies the summary text (zero or more characters)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Variablelists.html#VarListFormatting">Variable list formatting in HTML</link>,
      <link role="tcg" xlink:href="TableSummary.html">Table summary text</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_table-summary">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'table-summary'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_table-width">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies the width for a CALS table</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml table-width</tag> PI as a child of a
      CALS <tag>table</tag> to specify the width of the table in
      output.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml table-width="<replaceable>width</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>table-width="<replaceable>width</replaceable>"</term>
        <listitem>
          <para>Specifies the table width (including units or as a percentage)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>default.table.width</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Tables.html#TableWidth">Table width</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_table-width">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'table-width'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_term-presentation">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Sets character formatting for terms in a variablelist</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml term-presentation</tag> PI as a child
      of a <tag>variablelist</tag> to set character formatting for
      the <tag>term</tag> output of the list.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml term-presentation="bold"|"italic"|"bold-italic"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>term-presentation="<replaceable>bold</replaceable>"</term>
        <listitem>
          <para>Specifies that terms are displayed in bold</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>term-presentation="<replaceable>italic</replaceable>"</term>
        <listitem>
          <para>Specifies that terms are displayed in italic</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>term-presentation="<replaceable>bold-italic</replaceable>"</term>
        <listitem>
          <para>Specifies that terms are displayed in bold-italic</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Variablelists.html#VarListFormatting">Variable list formatting in HTML</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_term-presentation">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'term-presentation'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_term-separator">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies separator text among terms in a varlistentry</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml term-separator</tag> PI as a child
      of a <tag>variablelist</tag> to specify the separator text
      among <tag>term</tag> instances.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml term-separator="<replaceable>text</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>term-separator="<replaceable>text</replaceable>"</term>
        <listitem>
          <para>Specifies the text (zero or more characters)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>variablelist.term.separator</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Variablelists.html#VarListFormatting">Variable list formatting in HTML</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_term-separator">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'term-separator'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_term-width">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies the term width for a variablelist</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml term-width</tag> PI as a child of a
      <tag>variablelist</tag> to specify the width for
      <tag>term</tag> output.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml term-width="<replaceable>width</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>term-width="<replaceable>width</replaceable>"</term>
        <listitem>
          <para>Specifies the term width (including units)</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="Variablelists.html#VarListFormatting">Variable list formatting in HTML</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_term-width">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'term-width'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbhtml_toc">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Specifies whether a TOC should be generated for a qandaset</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml toc</tag> PI as a child of a
      <tag>qandaset</tag> to specify whether a table of contents
      (TOC) is generated for the <tag>qandaset</tag>.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml toc="0"|"1"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>toc="0"</term>
        <listitem>
          <para>If zero, no TOC is generated</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>toc="1"</term>
        <listitem>
          <para>If <code>1</code> (or any non-zero value),
            a TOC is generated</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="QandAtoc.html">Q and A list of questions</link>,
      <link role="tcg" xlink:href="QandAformat.html">Q and A formatting</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml_toc">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="dbhtml-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbhtml')"/>
    <xsl:with-param name="attribute" select="'toc'"/>
  </xsl:call-template>
</xsl:template>

<doc:pi xmlns="" name="dbcmdlist">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Generates a hyperlinked list of commands</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbcmdlist</tag> PI as the child of any
      element (for example, <tag>refsynopsisdiv</tag>) containing multiple
      <tag>cmdsynopsis</tag> instances; a hyperlinked navigational
      &#8220;command list&#8221; will be generated at the top of output for that
      element, enabling users to quickly jump
      to each command synopsis.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbcmdlist</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <para>[No parameters]</para>
  </refparameter>
</doc:pi>
<xsl:template name="pi.dbcmdlist">
  <xsl:variable name="cmdsynopses" select="..//cmdsynopsis"/>
  <xsl:if test="count($cmdsynopses)&lt;1">
    <xsl:message><xsl:text>No cmdsynopsis elements matched dbcmdlist PI, perhaps it's nested too deep?</xsl:text>
    </xsl:message>
  </xsl:if>
  <dl>
    <xsl:call-template name="process.cmdsynopsis.list">
      <xsl:with-param name="cmdsynopses" select="$cmdsynopses"/>
    </xsl:call-template>
  </dl>
</xsl:template>

<doc:pi xmlns="" name="dbfunclist">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Generates a hyperlinked list of functions</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbfunclist</tag> PI as the child of any
      element (for example, <tag>refsynopsisdiv</tag>) containing multiple
      <tag>funcsynopsis</tag> instances; a hyperlinked
      navigational &#8220;function list&#8221; will be generated at the top of
      output for that element, enabling users to quickly
      jump to to each function synopsis.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbfunclist</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <para>[No parameters]</para>
  </refparameter>
</doc:pi>
<xsl:template name="pi.dbfunclist">
  <xsl:variable name="funcsynopses" select="..//funcsynopsis"/>
  <xsl:if test="count($funcsynopses)&lt;1">
    <xsl:message><xsl:text>No funcsynopsis elements matched dbfunclist PI, perhaps it's nested too deep?</xsl:text>
    </xsl:message>
  </xsl:if>
  <dl>
    <xsl:call-template name="process.funcsynopsis.list">
      <xsl:with-param name="funcsynopses" select="$funcsynopses"/>
    </xsl:call-template>
  </dl>
</xsl:template>

<doc:pi xmlns="" name="dbhtml-include_href">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Copies an external well-formed HTML/XML file into current doc</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhtml-include href</tag> PI anywhere in a
      document to cause the contents of the file referenced by the
      <code>href</code> pseudo-attribute to be copied/inserted &#8220;as
      is&#8221; into your HTML output at the point in document order
      where the PI occurs in the source.</para>
    <note>
      <para>The referenced file may contain plain text (as long as
        it is &#8220;wrapped&#8221; in an <tag>html</tag> element &#8212; see the
        note below) or markup in any arbitrary vocabulary,
        including HTML &#8212; but it must conform to XML
        well-formedness constraints (because the feature in XSLT
        1.0 for opening external files, the
        <function>document()</function> function, can only handle
        files that meet XML well-formedness constraints).</para>
      <para>Among other things, XML well-formedness constraints
        require a document to have <emphasis>a single root
          element</emphasis>. So if the content you want to
        include is plain text or is markup that does
        <emphasis>not</emphasis> have a single root element,
        <emphasis role="strong">wrap the content in an
          <tag>html</tag> element</emphasis>. The stylesheets will
        strip out that surrounding <tag>html</tag> &#8220;wrapper&#8221; when
        they find it, leaving just the content you want to
        insert.</para>
    </note>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhtml-include href="<replaceable>URI</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>href="<replaceable>URI</replaceable>"</term>
        <listitem>
          <para>Specifies the URI for the file to include; the URI
            can be, for example, a remote <literal>http:</literal>
            URI, or a local filesystem <literal>file:</literal>
            URI</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="params">
    <para><parameter>textinsert.extension</parameter></para>
  </refsee>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="InsertExtHtml.html">Inserting external HTML code</link>,
      <link role="tcg" xlink:href="ExternalCode.html">External code files</link></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbhtml-include">
  <xsl:param name="href">
    <xsl:call-template name="dbhtml-attribute">
      <xsl:with-param name="pis" select="."/>
      <xsl:with-param name="attribute">href</xsl:with-param>
    </xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="$href != ''">
      <xsl:variable name="content" select="document($href,/)"/>
      <xsl:choose>
        <xsl:when test="$content/*">
          <xsl:choose>
            <xsl:when test="$content/*[1][self::html]">
              <!-- include just the children of html wrapper -->
              <xsl:copy-of select="$content/*[1]/node()"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:copy-of select="$content"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>
            <xsl:text>ERROR: dbhtml-include processing instruction </xsl:text>
            <xsl:text>href has no content.</xsl:text>
          </xsl:message>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>
        <xsl:text>ERROR: dbhtml-include processing instruction has </xsl:text>
        <xsl:text>missing or empty href value.</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- There are two templates matching this PI in htmlhelp-common.xsl -->
<doc:pi xmlns="" name="dbhh">
  <refpurpose xmlns="http://www.w3.org/1999/xhtml">Sets topic name and topic id for context-sensitive HTML Help</refpurpose>
  <refdescription xmlns="http://www.w3.org/1999/xhtml">
    <para>Use the <tag class="xmlpi">dbhh</tag> PI as a child of components
      that should be used as targets for context-sensitive help requests.</para>
  </refdescription>
  <refsynopsisdiv xmlns="http://www.w3.org/1999/xhtml">
    <synopsis><tag class="xmlpi">dbhh topicname="<replaceable>name</replaceable>" topicid="<replaceable>id</replaceable>"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter xmlns="http://www.w3.org/1999/xhtml">
    <variablelist>
      <varlistentry><term>topicname="<replaceable>name</replaceable>"</term>
        <listitem>
          <para>Specifies a unique string constant that identifies a help topic</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>topicid="<replaceable>id</replaceable>"</term>
        <listitem>
          <para>Specifies a unique integer value for the <literal>topicname</literal> string</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee xmlns="http://www.w3.org/1999/xhtml" role="tcg">
    <para><link role="tcg" xlink:href="HtmlHelp.html#HHContextHelp">Context-sensitive help</link></para>
  </refsee>
</doc:pi>

<!-- ==================================================================== -->

<xsl:template name="dbhtml-attribute">
  <!-- * dbhtml-attribute is an interal utility template for retrieving -->
  <!-- * pseudo-attributes/parameters from PIs -->
  <xsl:param name="pis" select="processing-instruction('dbhtml')"/>
  <xsl:param name="attribute">filename</xsl:param>
  <xsl:call-template name="pi-attribute">
    <xsl:with-param name="pis" select="$pis"/>
    <xsl:with-param name="attribute" select="$attribute"/>
  </xsl:call-template>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="processing-instruction()">
</xsl:template>

<xsl:template match="processing-instruction('dbhtml')">
  <!-- nop -->
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="processing-instruction('dbcmdlist')">
  <xsl:call-template name="pi.dbcmdlist"/>
</xsl:template>
<xsl:template name="process.cmdsynopsis.list">
  <xsl:param name="cmdsynopses"/><!-- empty node list by default -->
  <xsl:param name="count" select="1"/>

  <xsl:choose>
    <xsl:when test="$count&gt;count($cmdsynopses)"/>
    <xsl:otherwise>
      <xsl:variable name="cmdsyn" select="$cmdsynopses[$count]"/>

       <dt>
       <a>
         <xsl:attribute name="href">
           <xsl:text>#</xsl:text>
           <xsl:call-template name="object.id">
             <xsl:with-param name="object" select="$cmdsyn"/>
           </xsl:call-template>
         </xsl:attribute>

         <xsl:choose>
           <xsl:when test="$cmdsyn/@xreflabel">
             <xsl:call-template name="xref.xreflabel">
               <xsl:with-param name="target" select="$cmdsyn"/>
             </xsl:call-template>
           </xsl:when>
           <xsl:otherwise>
             <xsl:apply-templates select="$cmdsyn" mode="xref-to">
               <xsl:with-param name="target" select="$cmdsyn"/>
             </xsl:apply-templates>
           </xsl:otherwise>
         </xsl:choose>
       </a>
       </dt>

        <xsl:call-template name="process.cmdsynopsis.list">
          <xsl:with-param name="cmdsynopses" select="$cmdsynopses"/>
          <xsl:with-param name="count" select="$count+1"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="processing-instruction('dbfunclist')">
  <xsl:call-template name="pi.dbfunclist"/>
</xsl:template>
<xsl:template name="process.funcsynopsis.list">
  <xsl:param name="funcsynopses"/><!-- empty node list by default -->
  <xsl:param name="count" select="1"/>

  <xsl:choose>
    <xsl:when test="$count&gt;count($funcsynopses)"/>
    <xsl:otherwise>
      <xsl:variable name="cmdsyn" select="$funcsynopses[$count]"/>

       <dt>
       <a>
         <xsl:attribute name="href">
           <xsl:text>#</xsl:text>
           <xsl:call-template name="object.id">
             <xsl:with-param name="object" select="$cmdsyn"/>
           </xsl:call-template>
         </xsl:attribute>

         <xsl:choose>
           <xsl:when test="$cmdsyn/@xreflabel">
             <xsl:call-template name="xref.xreflabel">
               <xsl:with-param name="target" select="$cmdsyn"/>
             </xsl:call-template>
           </xsl:when>
           <xsl:otherwise>
              <xsl:apply-templates select="$cmdsyn" mode="xref-to">
                <xsl:with-param name="target" select="$cmdsyn"/>
              </xsl:apply-templates>
           </xsl:otherwise>
         </xsl:choose>
       </a>
       </dt>

        <xsl:call-template name="process.funcsynopsis.list">
          <xsl:with-param name="funcsynopses" select="$funcsynopses"/>
          <xsl:with-param name="count" select="$count+1"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="processing-instruction('dbhtml-include')">
  <xsl:call-template name="pi.dbhtml-include"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="dbhtml-dir">
  <xsl:param name="context" select="."/>
  <!-- directories are now inherited from previous levels -->
  <xsl:variable name="ppath">
    <xsl:if test="$context/parent::*">
      <xsl:call-template name="dbhtml-dir">
        <xsl:with-param name="context" select="$context/parent::*"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="path">
    <xsl:call-template name="pi.dbhtml_dir">
      <xsl:with-param name="node" select="$context"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="$path = ''">
      <xsl:if test="$ppath != ''">
        <xsl:value-of select="$ppath"/>
      </xsl:if>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="$ppath != ''">
        <xsl:value-of select="$ppath"/>
        <xsl:if test="substring($ppath, string-length($ppath), 1) != '/'">
          <xsl:text>/</xsl:text>
        </xsl:if>
      </xsl:if>
      <xsl:value-of select="$path"/>
      <xsl:text>/</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
