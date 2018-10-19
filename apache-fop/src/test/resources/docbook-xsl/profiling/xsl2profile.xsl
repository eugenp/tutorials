<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xslo="http://www.w3.org/1999/XSL/TransformAlias"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                exclude-result-prefixes="fo"
                version="1.0">

<xsl:include href="../lib/lib.xsl"/>

<xsl:output method="xml" encoding="US-ASCII"/>

<xsl:namespace-alias stylesheet-prefix="xslo" result-prefix="xsl"/>

<xsl:preserve-space elements="*"/>

<xsl:template match="/">
  <xsl:comment>This file was created automatically by xsl2profile</xsl:comment>
  <xsl:comment>from the DocBook XSL stylesheets.</xsl:comment>
  <xsl:apply-templates/>
</xsl:template>

<!-- Make sure we override some templates and parameters appropriately for XHTML -->
<xsl:template match="xsl:stylesheet">
  <xsl:copy>
    <xsl:attribute name="exslt:dummy" xmlns:exslt="http://exslt.org/common">dummy</xsl:attribute>
    <xsl:attribute name="ng:dummy" xmlns:ng="http://docbook.org/docbook-ng">dummy</xsl:attribute>
    <xsl:attribute name="db:dummy" xmlns:db="http://docbook.org/ns/docbook">dummy</xsl:attribute>
    <xsl:if test="not(@extension-element-prefixes)">
      <xsl:attribute name="extension-element-prefixes">exslt</xsl:attribute>
    </xsl:if>
    <xsl:if test="not(@exclude-result-prefixes)">
      <xsl:attribute name="exclude-result-prefixes">exslt</xsl:attribute>
    </xsl:if>
    <xsl:for-each select="@*">
      <xsl:choose>
        <xsl:when test="local-name(.) = 'extension-element-prefixes' or
                        local-name(.) = 'exclude-result-prefixes'">
          <xsl:attribute name="{local-name(.)}"><xsl:value-of select="concat(., ' exslt')"/></xsl:attribute>
        </xsl:when>
        <xsl:otherwise>
          <xsl:attribute name="{local-name(.)}"><xsl:value-of select="."/></xsl:attribute>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
    <xsl:apply-templates/>
  </xsl:copy>
</xsl:template>

<xsl:template match="*">
  <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates/>
  </xsl:copy>
</xsl:template>

<xsl:template match="comment()|processing-instruction()|text()">
  <xsl:copy/>
</xsl:template>

<xsl:template match="xsl:template[@match='/']">
  <xslo:include href="../profiling/profile-mode.xsl"/>
  <xslo:variable name="profiled-content">
    <xslo:choose>
      <xslo:when test="*/self::ng:* or */self::db:*">
        <xslo:message>Note: namesp. cut : stripped namespace before processing</xslo:message>
        <xslo:variable name="stripped-content">
          <xslo:apply-templates select="/" mode="stripNS"/>
        </xslo:variable>
        <xslo:message>Note: namesp. cut : processing stripped document</xslo:message>
        <xslo:apply-templates select="exslt:node-set($stripped-content)" mode="profile"/>
      </xslo:when>
      <xslo:otherwise>
        <xslo:apply-templates select="/" mode="profile"/>
      </xslo:otherwise>
    </xslo:choose>
  </xslo:variable>
  <xslo:variable name="profiled-nodes" select="exslt:node-set($profiled-content)"/>
  <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="correct"/>
  </xsl:copy>
</xsl:template>

<xsl:template match="xsl:template[@name='hhc-main' or @name='hhp-main' or @name='hhk' or @name='hh-map' or @name='hh-alias' or @name='etoc'] | xsl:variable[@name='raw.help.title']">
  <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="correct"/>
  </xsl:copy>
</xsl:template>

<xsl:template match="*[starts-with(@select, '/')]" mode="correct">
  <xsl:copy>
    <xsl:for-each select="@*">
      <xsl:choose>
        <xsl:when test="local-name(.) = 'select' and string(.) =  '/'">
          <xsl:attribute name="{local-name(.)}">$profiled-nodes</xsl:attribute>
        </xsl:when>
        <xsl:when test="local-name(.) = 'select' and starts-with(., '/')">
          <xsl:attribute name="{local-name(.)}">$profiled-nodes<xsl:value-of select="."/></xsl:attribute>
        </xsl:when>
        <xsl:otherwise>
          <xsl:attribute name="{local-name(.)}"><xsl:value-of select="."/></xsl:attribute>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
    <xsl:apply-templates mode="correct"/>
  </xsl:copy>
</xsl:template>

<xsl:template match='*[contains(@*, "key(&apos;id&apos;,$rootid)")]' mode="correct" priority="2">
  <xsl:copy>
    <xsl:for-each select="@*">
      <xsl:choose>
        <xsl:when test='contains(., "key(&apos;id&apos;,$rootid)")'>
          <xsl:attribute name="{local-name(.)}">
            <xsl:call-template name="string.subst">
              <xsl:with-param name="string" select="."/>
              <xsl:with-param name="target">key('id',$rootid)</xsl:with-param>
              <xsl:with-param name="replacement">$profiled-nodes//*[@id=$rootid or @xml:id=$rootid]</xsl:with-param>
            </xsl:call-template>
          </xsl:attribute>
        </xsl:when>
        <xsl:otherwise>
          <xsl:attribute name="{local-name(.)}"><xsl:value-of select="."/></xsl:attribute>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
    <xsl:apply-templates mode="correct"/>
  </xsl:copy>
</xsl:template>

<!-- FO stylesheet has apply-templates without select, we must detect it by context -->
<xsl:template match="fo:root//xsl:apply-templates" mode="correct">
  <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:attribute name="select">$profiled-nodes/node()</xsl:attribute>
    <xsl:apply-templates mode="correct"/>
  </xsl:copy>
</xsl:template>

<!-- DB5 namespace stripping is already done  -->
<xsl:template match="xsl:when[contains(@test, 'self::db')]" mode="correct">
  <xsl:copy>
    <xsl:attribute name="test">false()</xsl:attribute>
  </xsl:copy>
</xsl:template>

<xsl:template match="*" mode="correct">
  <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="correct"/>
  </xsl:copy>
</xsl:template>

<xsl:template match="comment()|processing-instruction()|text()" mode="correct">
  <xsl:copy/>
</xsl:template>

</xsl:stylesheet>
