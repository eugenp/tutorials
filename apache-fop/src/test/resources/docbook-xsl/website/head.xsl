<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<xsl:template match="head" mode="head.mode">
  <xsl:variable name="nodes" select="*"/>
  <head>
    <meta name="generator" content="Website XSL Stylesheet V{$VERSION}"/>
    <xsl:if test="$html.stylesheet != ''">
      <link rel="stylesheet" href="{$html.stylesheet}" type="text/css">
	<xsl:if test="$html.stylesheet.type != ''">
	  <xsl:attribute name="type">
	    <xsl:value-of select="$html.stylesheet.type"/>
	  </xsl:attribute>
	</xsl:if>
      </link>
    </xsl:if>

    <xsl:variable name="thisid" select="ancestor-or-self::webpage/@id"/>
    <xsl:variable name="thisrelpath">
      <xsl:apply-templates select="$autolayout//*[@id=$thisid]" mode="toc-rel-path"/>
    </xsl:variable>

    <xsl:variable name="topid">
      <xsl:call-template name="top.page"/>
    </xsl:variable>

    <xsl:if test="$topid != ''">
      <link rel="home">
        <xsl:attribute name="href">
          <xsl:call-template name="page.uri">
            <xsl:with-param name="page" select="$autolayout//*[@id=$topid]"/>
            <xsl:with-param name="relpath" select="$thisrelpath"/>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="$autolayout//*[@id=$topid]/title"/>
        </xsl:attribute>
      </link>
    </xsl:if>

    <xsl:variable name="upid">
      <xsl:call-template name="up.page"/>
    </xsl:variable>

    <xsl:if test="$upid != ''">
      <link rel="up">
        <xsl:attribute name="href">
          <xsl:call-template name="page.uri">
            <xsl:with-param name="page" select="$autolayout//*[@id=$upid]"/>
            <xsl:with-param name="relpath" select="$thisrelpath"/>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="$autolayout//*[@id=$upid]/title"/>
        </xsl:attribute>
      </link>
    </xsl:if>

    <xsl:variable name="previd">
      <xsl:call-template name="prev.page"/>
    </xsl:variable>

    <xsl:if test="$previd != ''">
      <link rel="previous">
        <xsl:attribute name="href">
          <xsl:call-template name="page.uri">
            <xsl:with-param name="page" select="$autolayout//*[@id=$previd]"/>
            <xsl:with-param name="relpath" select="$thisrelpath"/>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="$autolayout//*[@id=$previd]/title"/>
        </xsl:attribute>
      </link>
    </xsl:if>

    <xsl:variable name="nextid">
      <xsl:call-template name="next.page"/>
    </xsl:variable>

    <xsl:if test="$nextid != ''">
      <link rel="next">
        <xsl:attribute name="href">
          <xsl:call-template name="page.uri">
            <xsl:with-param name="page" select="$autolayout//*[@id=$nextid]"/>
            <xsl:with-param name="relpath" select="$thisrelpath"/>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="$autolayout//*[@id=$nextid]/title"/>
        </xsl:attribute>
      </link>
    </xsl:if>

    <xsl:variable name="firstid">
      <xsl:call-template name="first.page"/>
    </xsl:variable>

    <xsl:if test="$firstid != ''">
      <link rel="first">
        <xsl:attribute name="href">
          <xsl:call-template name="page.uri">
            <xsl:with-param name="page" select="$autolayout//*[@id=$firstid]"/>
            <xsl:with-param name="relpath" select="$thisrelpath"/>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="$autolayout//*[@id=$firstid]/title"/>
        </xsl:attribute>
      </link>
    </xsl:if>

    <xsl:variable name="lastid">
      <xsl:call-template name="last.page"/>
    </xsl:variable>

    <xsl:if test="$lastid != ''">
      <link rel="last">
        <xsl:attribute name="href">
          <xsl:call-template name="page.uri">
            <xsl:with-param name="page" select="$autolayout//*[@id=$lastid]"/>
            <xsl:with-param name="relpath" select="$thisrelpath"/>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:attribute name="title">
          <xsl:value-of select="$autolayout//*[@id=$lastid]/title"/>
        </xsl:attribute>
      </link>
    </xsl:if>

    <xsl:apply-templates select="$autolayout/autolayout/style
                                 |$autolayout/autolayout/script
                                 |$autolayout/autolayout/headlink"
                         mode="head.mode">
      <xsl:with-param name="webpage" select="ancestor::webpage"/>
    </xsl:apply-templates>
    <xsl:apply-templates mode="head.mode"/>
    <xsl:call-template name="user.head.content">
      <xsl:with-param name="node" select="ancestor::webpage"/>
    </xsl:call-template>
  </head>
</xsl:template>

<xsl:template match="title" mode="head.mode">
  <title><xsl:value-of select="."/></title>
</xsl:template>

<xsl:template match="titleabbrev" mode="head.mode">
  <!--nop-->
</xsl:template>

<xsl:template match="subtitle" mode="head.mode">
  <!--nop-->
</xsl:template>

<xsl:template match="summary" mode="head.mode">
  <!--nop-->
</xsl:template>

<xsl:template match="base" mode="head.mode">
  <base href="{@href}">
    <xsl:if test="@target">
      <xsl:attribute name="target">
        <xsl:value-of select="@target"/>
      </xsl:attribute>
    </xsl:if>
  </base>
</xsl:template>

<xsl:template match="keywords" mode="head.mode">
  <meta name="keyword" content="{.}"/>
  <meta name="keywords" content="{.}"/>
</xsl:template>

<xsl:template match="copyright" mode="head.mode">
  <!--nop-->
</xsl:template>

<xsl:template match="author" mode="head.mode">
  <!--nop-->
</xsl:template>

<xsl:template match="edition" mode="head.mode">
  <!--nop-->
</xsl:template>

<xsl:template match="meta" mode="head.mode">
  <xsl:choose>
    <xsl:when test="@http-equiv">
      <meta http-equiv="{@http-equiv}" content="{@content}"/>
    </xsl:when>
    <xsl:otherwise>
      <meta name="{@name}" content="{@content}"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="script" mode="head.mode">
  <script>
    <xsl:choose>
      <xsl:when test="@language">
	<xsl:attribute name="language">
	  <xsl:value-of select="@language"/>
	</xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
	<xsl:attribute name="language">JavaScript</xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="@type">
	<xsl:attribute name="type">
	  <xsl:value-of select="@type"/>
	</xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
	<xsl:attribute name="type">text/javascript</xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:apply-templates/>
  </script>
</xsl:template>

<xsl:template match="script[@src]" mode="head.mode" priority="2">
  <xsl:param name="webpage" select="ancestor::webpage"/>
  <xsl:variable name="relpath">
    <xsl:call-template name="root-rel-path">
      <xsl:with-param name="webpage" select="$webpage"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="language">
    <xsl:choose>
      <xsl:when test="@language">
	<xsl:value-of select="@language"/>
      </xsl:when>
      <xsl:otherwise>JavaScript</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="type">
    <xsl:choose>
      <xsl:when test="@type">
	<xsl:value-of select="@type"/>
      </xsl:when>
      <xsl:otherwise>text/javascript</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <script src="{$relpath}{@src}" language="{$language}" type="{$type}"/>
</xsl:template>

<xsl:template match="style" mode="head.mode">
  <style>
    <xsl:if test="@type">
      <xsl:attribute name="type">
	<xsl:value-of select="@type"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:apply-templates/>

  </style>
</xsl:template>

<xsl:template match="style[@src]" mode="head.mode" priority="2">
  <xsl:param name="webpage" select="ancestor::webpage"/>
  <xsl:variable name="relpath">
    <xsl:call-template name="root-rel-path">
      <xsl:with-param name="webpage" select="$webpage"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="starts-with(@src, '/')">
      <link rel="stylesheet" href="{@src}">
        <xsl:if test="@type">
          <xsl:attribute name="type">
            <xsl:value-of select="@type"/>
          </xsl:attribute>
        </xsl:if>
      </link>
    </xsl:when>
    <xsl:otherwise>
      <link rel="stylesheet" href="{$relpath}{@src}">
        <xsl:if test="@type">
          <xsl:attribute name="type">
            <xsl:value-of select="@type"/>
          </xsl:attribute>
        </xsl:if>
      </link>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="headlink" mode="head.mode">
  <link>
    <xsl:copy-of select="@*"/>
  </link>
</xsl:template>

<xsl:template match="abstract" mode="head.mode">
  <!--nop-->
</xsl:template>

<xsl:template match="revhistory" mode="head.mode">
  <!--nop-->
</xsl:template>

<xsl:template match="rddl:*" mode="head.mode"
              xmlns:rddl='http://www.rddl.org/'>
  <!--nop-->
</xsl:template>

</xsl:stylesheet>
