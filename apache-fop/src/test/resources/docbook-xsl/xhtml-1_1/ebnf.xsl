<?xml version="1.0" encoding="ASCII"?><!--This file was created automatically by html2xhtml--><!--from the HTML stylesheets.--><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:doc="http://nwalsh.com/xsl/documentation/1.0" xmlns="http://www.w3.org/1999/xhtml" exclude-result-prefixes="doc" version="1.0">

<!-- ********************************************************************
     $Id: ebnf.xsl 9664 2012-11-07 20:02:17Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<doc:reference xmlns="">
<referenceinfo xmlns="http://www.w3.org/1999/xhtml">
<releaseinfo role="meta">
$Id: ebnf.xsl 9664 2012-11-07 20:02:17Z bobstayton $
</releaseinfo>
<author><surname>Walsh</surname>
<firstname>Norman</firstname></author>
<copyright><year>1999</year><year>2000</year>
<holder>Norman Walsh</holder>
</copyright>
</referenceinfo>
<title xmlns="http://www.w3.org/1999/xhtml">HTML EBNF Reference</title>

<partintro xmlns="http://www.w3.org/1999/xhtml">
<section><title>Introduction</title>

<para>This is technical reference documentation for the DocBook XSL
Stylesheets; it documents (some of) the parameters, templates, and
other elements of the stylesheets.</para>

<para>This reference describes the templates and parameters relevant
to formatting EBNF markup.</para>

<para>This is not intended to be <quote>user</quote> documentation.
It is provided for developers writing customization layers for the
stylesheets, and for anyone who's interested in <quote>how it
works</quote>.</para>

<para>Although I am trying to be thorough, this documentation is known
to be incomplete. Don't forget to read the source, too :-)</para>
</section>
</partintro>
</doc:reference>

<!-- ==================================================================== -->

<xsl:template match="productionset">
  <table width="100%" cellpadding="5">
    <xsl:if test="$ebnf.table.bgcolor != ''">
      <xsl:attribute name="style"><xsl:text>background-color: </xsl:text>
        <xsl:value-of select="$ebnf.table.bgcolor"/>
      </xsl:attribute>
    </xsl:if>
    <xsl:if test="$ebnf.table.border != 0">
      <xsl:attribute name="border">1</xsl:attribute>
    </xsl:if>
    <xsl:attribute name="class">
      <xsl:value-of select="local-name(.)"/>
    </xsl:attribute>
    <xsl:attribute name="summary">
      <xsl:text>EBNF</xsl:text>
      <xsl:if test="title|info/title">
        <xsl:text> for </xsl:text>
        <xsl:value-of select="title|info/title[1]"/>
      </xsl:if>
    </xsl:attribute>

    <xsl:if test="title|info/title">
      <tr>
        <th align="{$direction.align.start}" valign="top">
          <xsl:apply-templates select="." mode="class.attribute"/>
          <xsl:apply-templates select="title|info/title[1]"/>
        </th>
      </tr>
    </xsl:if>
    <tr>
      <td>
        <table border="0" width="99%" cellpadding="0">
          <xsl:if test="$ebnf.table.bgcolor != ''">
            <xsl:attribute name="style"><xsl:text>background-color: </xsl:text>
              <xsl:value-of select="$ebnf.table.bgcolor"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:attribute name="class">
            <xsl:value-of select="local-name(.)"/>
          </xsl:attribute>
          <xsl:attribute name="summary">EBNF productions</xsl:attribute>
          <xsl:apply-templates select="production|productionrecap"/>
        </table>
      </td>
    </tr>
  </table>
</xsl:template>

<xsl:template match="productionset/title">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="production">
  <xsl:param name="recap" select="false()"/>
  <tr>
    <td align="{$direction.align.start}" valign="top">
      <xsl:text>[</xsl:text>
      <xsl:apply-templates select="." mode="label.markup"/>
      <xsl:text>]</xsl:text>
    </td>
    <td align="{$direction.align.end}" valign="top">
      <xsl:choose>
        <xsl:when test="$recap">
          <a>
            <xsl:attribute name="href">
              <xsl:call-template name="href.target">
                <xsl:with-param name="object" select="."/>
              </xsl:call-template>
            </xsl:attribute>
            <xsl:apply-templates select="lhs"/>
          </a>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="id.attribute"/>
          <xsl:call-template name="anchor"/>
          <xsl:apply-templates select="lhs"/>
        </xsl:otherwise>
      </xsl:choose>
    </td>
    <td valign="top" align="center">
      <xsl:copy-of select="$ebnf.assignment"/>
    </td>
    <td valign="top">
      <xsl:apply-templates select="rhs"/>
      <xsl:copy-of select="$ebnf.statement.terminator"/>
    </td>
    <td align="{$direction.align.start}" valign="top">
      <xsl:choose>
        <xsl:when test="rhs/lineannotation|constraint">
          <xsl:apply-templates select="rhs/lineannotation" mode="rhslo"/>
          <xsl:apply-templates select="constraint"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:text>&#160;</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </td>
  </tr>
</xsl:template>

<xsl:template match="productionrecap">
  <xsl:variable name="targets" select="key('id',@linkend)"/>
  <xsl:variable name="target" select="$targets[1]"/>

  <xsl:if test="count($targets)=0">
    <xsl:message>
      <xsl:text>Error: no ID for productionrecap linkend: </xsl:text>
      <xsl:value-of select="@linkend"/>
      <xsl:text>.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="count($targets)&gt;1">
    <xsl:message>
      <xsl:text>Warning: multiple "IDs" for productionrecap linkend: </xsl:text>
      <xsl:value-of select="@linkend"/>
      <xsl:text>.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:apply-templates select="$target">
    <xsl:with-param name="recap" select="true()"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="lhs">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="rhs">
  <xsl:apply-templates/>
  <xsl:if test="following-sibling::rhs">
    <xsl:text> |</xsl:text>
    <br/>
  </xsl:if>
</xsl:template>

<xsl:template match="nonterminal">
  <xsl:variable name="linkend">
    <xsl:call-template name="xpointer.idref">
      <xsl:with-param name="xpointer" select="@def"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="check.id.unique">
    <xsl:with-param name="linkend" select="$linkend"/>
  </xsl:call-template>

  <xsl:call-template name="check.idref.targets">
    <xsl:with-param name="linkend" select="$linkend"/>
    <xsl:with-param name="element-list">production</xsl:with-param>
  </xsl:call-template>

  <!-- If you don't provide content, you can't point outside this doc. -->
  <xsl:choose>
    <xsl:when test="*|text()"><!--nop--></xsl:when>
    <xsl:otherwise>
      <xsl:if test="$linkend = ''">
        <xsl:message>
          <xsl:text>Non-terminals with no content must point to </xsl:text>
          <xsl:text>production elements in the current document.</xsl:text>
        </xsl:message>
        <xsl:message>
          <xsl:text>Invalid xpointer for empty nt: </xsl:text>
          <xsl:value-of select="@def"/>
        </xsl:message>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:variable name="href">
    <xsl:choose>
      <xsl:when test="$linkend != ''">
        <xsl:variable name="targets" select="key('id',$linkend)"/>
        <xsl:variable name="target" select="$targets[1]"/>
        <xsl:call-template name="href.target">
          <xsl:with-param name="object" select="$target"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@def"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <a href="{$href}">
    <xsl:choose>
      <xsl:when test="*|text()">
        <xsl:apply-templates/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:choose>
          <xsl:when test="$linkend != ''">
            <xsl:variable name="targets" select="key('id',$linkend)"/>
            <xsl:variable name="target" select="$targets[1]"/>
            <xsl:apply-templates select="$target/lhs"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text>???</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </a>
</xsl:template>

<xsl:template match="rhs/lineannotation">
  <!--nop-->
</xsl:template>

<xsl:template match="rhs/lineannotation" mode="rhslo">
  <xsl:text>/*&#160;</xsl:text>
  <xsl:apply-templates/>
  <xsl:text>&#160;*/</xsl:text>
  <br/>
</xsl:template>

<xsl:template match="constraint">
  <xsl:call-template name="check.id.unique">
    <xsl:with-param name="linkend" select="@linkend"/>
  </xsl:call-template>

  <xsl:call-template name="check.idref.targets">
    <xsl:with-param name="linkend" select="@linkend"/>
    <xsl:with-param name="element-list">constraintdef</xsl:with-param>
  </xsl:call-template>

  <xsl:variable name="href">
    <xsl:variable name="targets" select="key('id',@linkend)"/>
    <xsl:variable name="target" select="$targets[1]"/>
    <xsl:call-template name="href.target">
      <xsl:with-param name="object" select="$target"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:text>[&#160;</xsl:text>

  <xsl:choose>
    <xsl:when test="@role">
      <xsl:value-of select="@role"/>
      <xsl:text>: </xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="targets" select="key('id',@linkend)"/>
      <xsl:variable name="target" select="$targets[1]"/>
      <xsl:if test="$target/@role">
        <xsl:value-of select="$target/@role"/>
        <xsl:text>: </xsl:text>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>

  <a href="{$href}">
    <xsl:variable name="targets" select="key('id',@linkend)"/>
    <xsl:variable name="target" select="$targets[1]"/>
    <xsl:apply-templates select="$target" mode="title.markup"/>
  </a>
  <xsl:text>&#160;]</xsl:text>
  <xsl:if test="following-sibling::constraint">
    <br/>
  </xsl:if>
</xsl:template>

<xsl:template match="constraintdef">
  <div>
    <xsl:apply-templates select="." mode="class.attribute"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>
    <xsl:apply-templates/>
  </div>
</xsl:template>

<xsl:template match="constraintdef/title">
  <p><strong><xsl:apply-templates/></strong></p>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>