<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xweb="xalan://com.nwalsh.xalan.Website"
                xmlns:sweb="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.Website"
                exclude-result-prefixes="sweb xweb"
                version="1.0">

<xsl:output method="html"/>

<xsl:param name="output-root" select="'.'"/>
<xsl:param name="dry-run" select="'0'"/>
<xsl:param name="rebuild-all" select="'0'"/>

<xsl:template match="autolayout">
  <!-- Regenerate olink database? -->
  <xsl:if test="$collect.xref.targets = 'yes' or
                $collect.xref.targets = 'only'">
    <xsl:apply-templates select="." mode="collect.targets"/>
  </xsl:if>

  <xsl:if test="$collect.xref.targets != 'only'" >
    <xsl:apply-templates select="toc|notoc" mode="make"/>
  </xsl:if>
</xsl:template>

<xsl:template match="toc|tocentry|notoc" mode="make">
  <xsl:call-template name="make.tocentry"/>
  <xsl:apply-templates select="tocentry" mode="make"/>
</xsl:template>

<xsl:template name="make.tocentry">
  <xsl:variable name="srcFile" select="@page"/>

  <xsl:if test="@page and @href">
    <xsl:message terminate="yes">
      <xsl:text>Fail: tocentry has both page and href attributes.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="@filename">
        <xsl:value-of select="@filename"/>
      </xsl:when>
      <xsl:otherwise>index.html</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="dir">
    <xsl:apply-templates select="." mode="calculate-dir"/>
  </xsl:variable>

<!--
  <xsl:message>
    <xsl:text>!!</xsl:text>
    <xsl:value-of select="$dir"/>
    <xsl:text>!!</xsl:text>
    <xsl:value-of select="$filename-prefix"/>
    <xsl:text>!!</xsl:text>
    <xsl:value-of select="$filename"/>
  </xsl:message>
-->

  <xsl:variable name="targetFile">
    <xsl:value-of select="$dir"/>
    <xsl:value-of select="$filename-prefix"/>
    <xsl:value-of select="$filename"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="function-available('sweb:exists')">
      <xsl:if test="not(@href) and not(sweb:exists($srcFile))">
        <xsl:message terminate="yes">
          <xsl:value-of select="$srcFile"/>
          <xsl:text> does not exist.</xsl:text>
        </xsl:message>
      </xsl:if>
    </xsl:when>
    <xsl:when test="function-available('xweb:exists')">
      <xsl:if test="not(@href) and not(xweb:exists($srcFile))">
        <xsl:message terminate="yes">
          <xsl:value-of select="$srcFile"/>
          <xsl:text> does not exist.</xsl:text>
        </xsl:message>
      </xsl:if>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message terminate="no">
        <xsl:value-of select="$srcFile"/>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:variable name="output-file">
    <xsl:choose>
      <xsl:when test="@href">
        <xsl:value-of select="@href"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$output-root"/>
        <xsl:text>/</xsl:text>
        <xsl:value-of select="$targetFile"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="needsUpdate">
    <xsl:choose>
      <xsl:when test="@href">0</xsl:when>
      <xsl:when test="function-available('sweb:needsUpdate')">
        <xsl:choose>
          <xsl:when test="$rebuild-all != 0
                          or sweb:needsUpdate($autolayout-file, $output-file)
                          or sweb:needsUpdate($srcFile, $output-file)">
            <xsl:text>1</xsl:text>
          </xsl:when>
          <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:when test="function-available('xweb:needsUpdate')">
        <xsl:choose>
          <xsl:when test="$rebuild-all != 0
                          or xweb:needsUpdate($autolayout-file, $output-file)
                          or xweb:needsUpdate($srcFile, $output-file)">
            <xsl:text>1</xsl:text>
          </xsl:when>
          <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$needsUpdate != 0">
      <xsl:message>
        <xsl:text>Update: </xsl:text>
        <xsl:value-of select="$output-file"/>
        <xsl:text>: </xsl:text>
        <xsl:value-of select="$srcFile"/>
      </xsl:message>

      <xsl:variable name="webpage" select="document($srcFile,.)"/>
      <xsl:variable name="content">
        <xsl:apply-templates select="$webpage/webpage"/>
      </xsl:variable>

      <xsl:if test="$dry-run = 0">
        <xsl:call-template name="write.chunk">
          <xsl:with-param name="filename" select="$output-file"/>
          <xsl:with-param name="content" select="$content"/>
        </xsl:call-template>
      </xsl:if>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>
        <xsl:text>Up-to-date: </xsl:text>
        <xsl:value-of select="$output-file"/>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*" mode="calculate-dir">
  <xsl:choose>
    <xsl:when test="@dir">
      <!-- if there's a directory, use it -->
      <xsl:choose>
        <xsl:when test="starts-with(@dir, '/')">
          <!-- if the directory on this begins with a "/", we're done... -->
          <xsl:value-of select="substring-after(@dir, '/')"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="@dir"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <xsl:when test="parent::*">
      <!-- if there's a parent, try it -->
      <xsl:apply-templates select="parent::*" mode="calculate-dir"/>
    </xsl:when>

    <xsl:otherwise>
      <!-- nop -->
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="autolayout" mode="collect.targets">
  <xsl:choose>
    <xsl:when test="$website.database.document = ''">
      <xsl:message>
        Must specify a $website.database.document parameter when
        $collect.xref.targets is set to 'yes' or 'only'.
        The xref targets were not collected.
      </xsl:message>
    </xsl:when> 
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$website.database.document">
          <xsl:call-template name="write.chunk">
            <xsl:with-param name="filename"
	                    select="$website.database.document"/>
            <xsl:with-param name="method" select="'xml'"/>
            <xsl:with-param name="encoding" select="'utf-8'"/>
            <xsl:with-param name="omit-xml-declaration" select="'no'"/>
            <xsl:with-param name="indent" select="'yes'"/>
            <xsl:with-param name="quiet" select="0"/>
            <xsl:with-param name="content">
              <targetset>
                <xsl:apply-templates select="." mode="olink.mode"/>
              </targetset>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <!-- Else write to standard output -->
          <xsl:apply-templates select="." mode="olink.mode"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
 
</xsl:template>

</xsl:stylesheet>
