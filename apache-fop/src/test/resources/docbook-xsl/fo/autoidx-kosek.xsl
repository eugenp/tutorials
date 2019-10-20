<?xml version="1.0"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY % common.entities SYSTEM "../common/entities.ent">
%common.entities;
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:rx="http://www.renderx.com/XSL/Extensions"
                xmlns:axf="http://www.antennahouse.com/names/XSL/Extensions"
                xmlns:i="urn:cz-kosek:functions:index"
                xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0"
                xmlns:func="http://exslt.org/functions"
                xmlns:exslt="http://exslt.org/common"
                extension-element-prefixes="func exslt"
                exclude-result-prefixes="func exslt i l"
                version="1.0">

<!-- ********************************************************************
     $Id: autoidx-kosek.xsl 8725 2010-07-15 08:08:04Z kosek $
     ********************************************************************

     This file is part of the DocBook XSL Stylesheet distribution.
     See ../README or http://docbook.sf.net/ for copyright
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->
<!-- The "kosek" method contributed by Jirka Kosek. -->

<xsl:include href="../common/autoidx-kosek.xsl"/>

<xsl:template name="generate-kosek-index">
  <xsl:param name="scope" select="NOTANODE"/>

  <xsl:variable name="vendor" select="system-property('xsl:vendor')"/>
  <xsl:if test="contains($vendor, 'libxslt')">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method does not </xsl:text>
      <xsl:text>work with the xsltproc XSLT processor.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="contains($vendor, 'Saxonica')">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method does not </xsl:text>
      <xsl:text>work with the Saxon 8 XSLT processor.</xsl:text>
    </xsl:message>
  </xsl:if>


  <xsl:if test="$exsl.node.set.available = 0">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method requires the </xsl:text>
      <xsl:text>exslt:node-set() function. Use a processor that </xsl:text>
      <xsl:text>has it, or use a different index method.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="not(function-available('i:group-index'))">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method requires the&#xA;</xsl:text>
      <xsl:text>index extension functions be imported:&#xA;</xsl:text>
      <xsl:text>  xsl:import href="common/autoidx-kosek.xsl"</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:variable name="role">
    <xsl:if test="$index.on.role != 0">
      <xsl:value-of select="@role"/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="type">
    <xsl:if test="$index.on.type != 0">
      <xsl:value-of select="@type"/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="terms"
                select="//indexterm[count(.|key('group-code',
                                          i:group-index(&primary;))
                                          [&scope;][1]) = 1
                                and not(@class = 'endofrange')]"/>
  <fo:block>
    <xsl:apply-templates select="$terms" mode="index-div-kosek">
      <xsl:with-param name="scope" select="$scope"/>
      <xsl:with-param name="role" select="$role"/>
      <xsl:with-param name="type" select="$type"/>
      <xsl:sort select="i:group-index(&primary;)" data-type="number"/>
    </xsl:apply-templates>
  </fo:block>
</xsl:template>

<xsl:template match="indexterm" mode="index-div-kosek">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:variable name="key"
                select="i:group-index(&primary;)"/>

  <xsl:variable name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:variable>

  <xsl:if test="key('group-code', $key)[&scope;]
                [count(.|key('primary', &primary;)[&scope;][1]) = 1]">
    <fo:block>
      <xsl:call-template name="indexdiv.title">
        <xsl:with-param name="titlecontent">
          <xsl:choose>
            <xsl:when test="$key = 0">
              <xsl:call-template name="gentext">
                <xsl:with-param name="key" select="'index symbols'"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="i:group-letter($key)"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
      </xsl:call-template>
      <fo:block>
        <xsl:apply-templates select="key('group-code', $key)[&scope;]
                                     [count(.|key('primary', &primary;)
                                     [&scope;][1])=1]"
                             mode="index-primary">
          <xsl:sort select="&primary;" lang="{$lang}"/>
          <xsl:with-param name="scope" select="$scope"/>
          <xsl:with-param name="role" select="$role"/>
          <xsl:with-param name="type" select="$type"/>
        </xsl:apply-templates>
      </fo:block>
    </fo:block>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
