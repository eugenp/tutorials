<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                exclude-result-prefixes="doc"
                version='1.0'>

<!-- ********************************************************************
     $Id: pi.xsl 7644 2008-01-16 11:04:07Z xmldoc $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<doc:reference xmlns=""><info><title>manpages Processing Instruction Reference</title>
    <releaseinfo role="meta">
      $Id: pi.xsl 7644 2008-01-16 11:04:07Z xmldoc $
    </releaseinfo>
  </info>
  <partintro xml:id="partintro">
    <title>Introduction</title>
    <para>This is generated reference documentation for all
      user-specifiable processing instructions (PIs) in the DocBook
      XSL stylesheets for manpages output.
      <note>
        <para>You add these PIs at particular points in a document to
          cause specific “exceptions” to formatting/output behavior. To
          make global changes in formatting/output behavior across an
          entire document, it’s better to do it by setting an
          appropriate stylesheet parameter (if there is one).</para>
      </note>
    </para>
  </partintro>
</doc:reference>

<!-- ==================================================================== -->

<doc:pi name="dbman_funcsynopsis-style" xmlns="">
  <refpurpose>Specifies presentation style for a funcsynopsis.</refpurpose>
  <refdescription>
    <para>Use the <tag class="xmlpi">dbman
        funcsynopsis-style</tag> PI as a child of a
      <tag>funcsynopsis</tag> or anywhere within a funcsynopsis
      to control the presentation style for output of all
      <tag>funcprototype</tag> instances within that funcsynopsis.</para>
  </refdescription>
  <refsynopsisdiv>
    <synopsis><tag class="xmlpi">dbman funcsynopsis-style="kr"|"ansi"</tag></synopsis>
  </refsynopsisdiv>
  <refparameter>
    <variablelist>
      <varlistentry><term>funcsynopsis-style="kr"</term>
        <listitem>
          <para>Displays the <tag>funcprototype</tag> in K&amp;R style</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>funcsynopsis-style="ansi"</term>
        <listitem>
          <para>Displays the <tag>funcprototype</tag> in ANSI style</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
  <refsee role="params">
    <para><parameter>man.funcsynopsis.style</parameter></para>
  </refsee>
</doc:pi>
<xsl:template name="pi.dbman_funcsynopsis-style">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="pi-attribute">
    <xsl:with-param name="pis" select="$node/processing-instruction('dbman')"/>
    <xsl:with-param name="attribute" select="'funcsynopsis-style'"/>
  </xsl:call-template>
</xsl:template>

</xsl:stylesheet>
