<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                exclude-result-prefixes="doc"
                version='1.0'>

<!-- ********************************************************************
     $Id: ebnf.xsl 9664 2012-11-07 20:02:17Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<doc:reference xmlns="">
<referenceinfo>
<releaseinfo role="meta">
$Id: ebnf.xsl 9664 2012-11-07 20:02:17Z bobstayton $
</releaseinfo>
<author><surname>Walsh</surname>
<firstname>Norman</firstname></author>
<copyright><year>1999</year><year>2000</year><year>2001</year>
<holder>Norman Walsh</holder>
</copyright>
</referenceinfo>
<title>HTML EBNF Reference</title>

<partintro>
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
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <xsl:choose>
    <xsl:when test="title">
      <fo:block id="{$id}" xsl:use-attribute-sets="formal.object.properties">
        <xsl:call-template name="formal.object.heading">
          <xsl:with-param name="placement" select="'before'"/>
        </xsl:call-template>

        <fo:table table-layout="fixed" width="100%">
          <fo:table-column column-number="1" column-width="3%"/>
          <fo:table-column column-number="2" column-width="15%"/>
          <fo:table-column column-number="3" column-width="5%"/>
          <fo:table-column column-number="4" column-width="52%"/>
          <fo:table-column column-number="5" column-width="25%"/>
          <fo:table-body start-indent="0pt" end-indent="0pt">
            <xsl:apply-templates select="production|productionrecap"/>
          </fo:table-body>
        </fo:table>
      </fo:block>
    </xsl:when>
    <xsl:otherwise>
      <fo:table id="{$id}" table-layout="fixed" width="100%">
        <fo:table-column column-number="1" column-width="3%"/>
        <fo:table-column column-number="2" column-width="15%"/>
        <fo:table-column column-number="3" column-width="5%"/>
        <fo:table-column column-number="4" column-width="52%"/>
        <fo:table-column column-number="5" column-width="25%"/>
        <fo:table-body start-indent="0pt" end-indent="0pt">
          <xsl:apply-templates select="production|productionrecap"/>
        </fo:table-body>
      </fo:table>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="productionset/title">
  <!-- suppressed -->
</xsl:template>

<xsl:template match="production">
  <xsl:param name="recap" select="false()"/>
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>
  <fo:table-row>
    <fo:table-cell>
      <fo:block text-align="start">
        <xsl:text>[</xsl:text>
        <xsl:apply-templates select="." mode="label.markup"/>
        <xsl:text>]</xsl:text>
      </fo:block>
    </fo:table-cell>
    <fo:table-cell>
      <fo:block text-align="end">
        <xsl:choose>
          <xsl:when test="$recap">
            <fo:basic-link internal-destination="{$id}"
                           xsl:use-attribute-sets="xref.properties">
              <xsl:apply-templates select="lhs"/>
            </fo:basic-link>
          </xsl:when>
          <xsl:otherwise>
            <fo:wrapper id="{$id}">
              <xsl:apply-templates select="lhs"/>
            </fo:wrapper>
          </xsl:otherwise>
        </xsl:choose>
      </fo:block>
    </fo:table-cell>
    <fo:table-cell>
      <fo:block text-align="center">
        <xsl:copy-of select="$ebnf.assignment"/>
      </fo:block>
    </fo:table-cell>
    <fo:table-cell>
      <fo:block>
        <xsl:apply-templates select="rhs"/>
        <xsl:copy-of select="$ebnf.statement.terminator"/>
      </fo:block>
    </fo:table-cell>
    <fo:table-cell border-start-width="3pt">
      <fo:block text-align="start">
        <xsl:choose>
          <xsl:when test="rhs/lineannotation|constraint">
            <xsl:apply-templates select="rhs/lineannotation" mode="rhslo"/>
            <xsl:apply-templates select="constraint"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text>&#160;</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </fo:block>
    </fo:table-cell>
  </fo:table-row>
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

  <xsl:if test="count($targets)>1">
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
        <xsl:call-template name="object.id">
          <xsl:with-param name="object" select="$target"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
	<xsl:value-of select="@def"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:basic-link internal-destination="{$href}"
                 xsl:use-attribute-sets="xref.properties">
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
  </fo:basic-link>
</xsl:template>

<xsl:template match="rhs/lineannotation">
  <!--nop-->
</xsl:template>

<xsl:template match="rhs/lineannotation" mode="rhslo">
  <xsl:text>/*&#160;</xsl:text>
  <xsl:apply-templates/>
  <xsl:text>&#160;*/</xsl:text>
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
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$target"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="preceding-sibling::constraint">
    <fo:inline linefeed-treatment="preserve">&#xA;</fo:inline>
  </xsl:if>
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

  <fo:basic-link internal-destination="{$href}"
                 xsl:use-attribute-sets="xref.properties">
    <xsl:variable name="targets" select="key('id',@linkend)"/>
    <xsl:variable name="target" select="$targets[1]"/>
    <xsl:apply-templates select="$target" mode="title.markup"/>
  </fo:basic-link>
  <xsl:text>&#160;]</xsl:text>
</xsl:template>

<xsl:template match="constraintdef">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>
  <fo:block id="{$id}">
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="constraintdef/title">
  <fo:block font-weight="bold">
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
