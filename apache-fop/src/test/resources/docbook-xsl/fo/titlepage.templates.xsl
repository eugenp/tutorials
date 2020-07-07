<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exsl="http://exslt.org/common" version="1.0" exclude-result-prefixes="exsl">

<!-- This stylesheet was created by template/titlepage.xsl-->

<xsl:template name="article.titlepage.recto">
  <xsl:choose>
    <xsl:when test="articleinfo/title">
      <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/title"/>
    </xsl:when>
    <xsl:when test="artheader/title">
      <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="articleinfo/subtitle">
      <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="artheader/subtitle">
      <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/corpauthor"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/corpauthor"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/authorgroup"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/authorgroup"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/author"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/author"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/othercredit"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/othercredit"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/releaseinfo"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/releaseinfo"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/copyright"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/copyright"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/legalnotice"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/legalnotice"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/pubdate"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/pubdate"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/revision"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/revision"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/revhistory"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/revhistory"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/abstract"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/abstract"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="articleinfo/itermset"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="artheader/itermset"/>
  <xsl:apply-templates mode="article.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="article.titlepage.verso">
</xsl:template>

<xsl:template name="article.titlepage.separator">
</xsl:template>

<xsl:template name="article.titlepage.before.recto">
</xsl:template>

<xsl:template name="article.titlepage.before.verso">
</xsl:template>

<xsl:template name="article.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="{$title.fontset}">
    <xsl:variable name="recto.content">
      <xsl:call-template name="article.titlepage.before.recto"/>
      <xsl:call-template name="article.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block start-indent="0pt" text-align="center"><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="article.titlepage.before.verso"/>
      <xsl:call-template name="article.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="article.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="article.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="article.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" keep-with-next.within-column="always" font-size="24.8832pt" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::article[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em" font-size="14.4pt">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em" font-size="14.4pt">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em" font-size="14.4pt">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" text-align="start" margin-left="0.5in" margin-right="0.5in" font-family="{$body.fontset}">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style" space-before="0.5em" text-align="start" margin-left="0.5in" margin-right="0.5in" font-family="{$body.fontset}">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="article.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="article.titlepage.recto.style">
<xsl:apply-templates select="." mode="article.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="set.titlepage.recto">
  <xsl:choose>
    <xsl:when test="setinfo/title">
      <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="setinfo/subtitle">
      <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/corpauthor"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/authorgroup"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/author"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/othercredit"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/releaseinfo"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/copyright"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/legalnotice"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/pubdate"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/revision"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/revhistory"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/abstract"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="setinfo/itermset"/>
  <xsl:apply-templates mode="set.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="set.titlepage.verso">
</xsl:template>

<xsl:template name="set.titlepage.separator">
</xsl:template>

<xsl:template name="set.titlepage.before.recto">
</xsl:template>

<xsl:template name="set.titlepage.before.verso">
</xsl:template>

<xsl:template name="set.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="set.titlepage.before.recto"/>
      <xsl:call-template name="set.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="set.titlepage.before.verso"/>
      <xsl:call-template name="set.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="set.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="set.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="set.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style" text-align="center" font-size="24.8832pt" space-before="18.6624pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="division.title">
<xsl:with-param name="node" select="ancestor-or-self::set[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style" font-family="{$title.fontset}" text-align="center">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="set.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="set.titlepage.recto.style">
<xsl:apply-templates select="." mode="set.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="book.titlepage.recto">
  <xsl:choose>
    <xsl:when test="bookinfo/title">
      <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="bookinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="bookinfo/subtitle">
      <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="bookinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="bookinfo/corpauthor"/>
  <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="bookinfo/authorgroup"/>
  <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="bookinfo/author"/>
  <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="bookinfo/itermset"/>
  <xsl:apply-templates mode="book.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="book.titlepage.verso">
  <xsl:choose>
    <xsl:when test="bookinfo/title">
      <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/corpauthor"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/authorgroup"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/author"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/othercredit"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/releaseinfo"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/pubdate"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/copyright"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/abstract"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="bookinfo/legalnotice"/>
  <xsl:apply-templates mode="book.titlepage.verso.auto.mode" select="info/legalnotice"/>
</xsl:template>

<xsl:template name="book.titlepage.separator"><fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" break-after="page"/>
</xsl:template>

<xsl:template name="book.titlepage.before.recto">
</xsl:template>

<xsl:template name="book.titlepage.before.verso"><fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" break-after="page"/>
</xsl:template>

<xsl:template name="book.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="book.titlepage.before.recto"/>
      <xsl:call-template name="book.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="book.titlepage.before.verso"/>
      <xsl:call-template name="book.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="book.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="book.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="book.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="book.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.recto.style" text-align="center" font-size="24.8832pt" space-before="18.6624pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="division.title">
<xsl:with-param name="node" select="ancestor-or-self::book[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="book.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.recto.style" text-align="center" font-size="20.736pt" space-before="15.552pt" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="book.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="book.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.recto.style" font-size="17.28pt" keep-with-next.within-column="always" space-before="2in">
<xsl:apply-templates select="." mode="book.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="book.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.recto.style" space-before="2in">
<xsl:apply-templates select="." mode="book.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="book.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.recto.style" font-size="17.28pt" space-before="10.8pt" keep-with-next.within-column="always">
<xsl:apply-templates select="." mode="book.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="book.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.recto.style">
<xsl:apply-templates select="." mode="book.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="title" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style" font-size="14.4pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="book.verso.title">
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style">
<xsl:apply-templates select="." mode="book.titlepage.verso.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style">
<xsl:call-template name="verso.authorgroup">
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style">
<xsl:apply-templates select="." mode="book.titlepage.verso.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style">
<xsl:apply-templates select="." mode="book.titlepage.verso.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style" space-before="0.5em">
<xsl:apply-templates select="." mode="book.titlepage.verso.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style" space-before="1em">
<xsl:apply-templates select="." mode="book.titlepage.verso.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style">
<xsl:apply-templates select="." mode="book.titlepage.verso.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style">
<xsl:apply-templates select="." mode="book.titlepage.verso.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="book.titlepage.verso.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="book.titlepage.verso.style" font-size="8pt">
<xsl:apply-templates select="." mode="book.titlepage.verso.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="part.titlepage.recto">
  <xsl:choose>
    <xsl:when test="partinfo/title">
      <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="partinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="partinfo/subtitle">
      <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="partinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="partinfo/itermset"/>
  <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="part.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="part.titlepage.verso">
</xsl:template>

<xsl:template name="part.titlepage.separator">
</xsl:template>

<xsl:template name="part.titlepage.before.recto">
</xsl:template>

<xsl:template name="part.titlepage.before.verso">
</xsl:template>

<xsl:template name="part.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="part.titlepage.before.recto"/>
      <xsl:call-template name="part.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="part.titlepage.before.verso"/>
      <xsl:call-template name="part.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="part.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="part.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="part.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="part.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="part.titlepage.recto.style" text-align="center" font-size="24.8832pt" space-before="18.6624pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="division.title">
<xsl:with-param name="node" select="ancestor-or-self::part[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="part.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="part.titlepage.recto.style" text-align="center" font-size="20.736pt" space-before="15.552pt" font-weight="bold" font-style="italic" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="part.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="part.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="part.titlepage.recto.style">
<xsl:apply-templates select="." mode="part.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="partintro.titlepage.recto">
  <xsl:choose>
    <xsl:when test="partintroinfo/title">
      <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="partintroinfo/subtitle">
      <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/corpauthor"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/corpauthor"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/authorgroup"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/authorgroup"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/author"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/author"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/othercredit"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/othercredit"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/releaseinfo"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/releaseinfo"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/copyright"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/copyright"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/legalnotice"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/legalnotice"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/pubdate"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/pubdate"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/revision"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/revision"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/revhistory"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/revhistory"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/abstract"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/abstract"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="partintroinfo/itermset"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="partintro.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="partintro.titlepage.verso">
</xsl:template>

<xsl:template name="partintro.titlepage.separator">
</xsl:template>

<xsl:template name="partintro.titlepage.before.recto">
</xsl:template>

<xsl:template name="partintro.titlepage.before.verso">
</xsl:template>

<xsl:template name="partintro.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="partintro.titlepage.before.recto"/>
      <xsl:call-template name="partintro.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="partintro.titlepage.before.verso"/>
      <xsl:call-template name="partintro.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="partintro.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="partintro.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="partintro.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style" text-align="center" font-size="24.8832pt" font-weight="bold" space-before="1em" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style" text-align="center" font-size="14.4pt" font-weight="bold" font-style="italic" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="partintro.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="partintro.titlepage.recto.style">
<xsl:apply-templates select="." mode="partintro.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="reference.titlepage.recto">
  <xsl:choose>
    <xsl:when test="referenceinfo/title">
      <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="referenceinfo/subtitle">
      <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/corpauthor"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/corpauthor"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/authorgroup"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/authorgroup"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/author"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/author"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/othercredit"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/othercredit"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/releaseinfo"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/releaseinfo"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/copyright"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/copyright"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/legalnotice"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/legalnotice"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/pubdate"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/pubdate"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/revision"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/revision"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/revhistory"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/revhistory"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/abstract"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/abstract"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="referenceinfo/itermset"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="reference.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="reference.titlepage.verso">
</xsl:template>

<xsl:template name="reference.titlepage.separator">
</xsl:template>

<xsl:template name="reference.titlepage.before.recto">
</xsl:template>

<xsl:template name="reference.titlepage.before.verso">
</xsl:template>

<xsl:template name="reference.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="reference.titlepage.before.recto"/>
      <xsl:call-template name="reference.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="reference.titlepage.before.verso"/>
      <xsl:call-template name="reference.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="reference.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="reference.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="reference.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style" text-align="center" font-size="24.8832pt" space-before="18.6624pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="division.title">
<xsl:with-param name="node" select="ancestor-or-self::reference[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style" font-family="{$title.fontset}" text-align="center">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="reference.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="reference.titlepage.recto.style">
<xsl:apply-templates select="." mode="reference.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="refsynopsisdiv.titlepage.recto">
  <xsl:choose>
    <xsl:when test="refsynopsisdivinfo/title">
      <xsl:apply-templates mode="refsynopsisdiv.titlepage.recto.auto.mode" select="refsynopsisdivinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="refsynopsisdiv.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="refsynopsisdiv.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="refsynopsisdiv.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="refsynopsisdiv.titlepage.recto.auto.mode" select="refsynopsisdivinfo/itermset"/>
  <xsl:apply-templates mode="refsynopsisdiv.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="refsynopsisdiv.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="refsynopsisdiv.titlepage.verso">
</xsl:template>

<xsl:template name="refsynopsisdiv.titlepage.separator">
</xsl:template>

<xsl:template name="refsynopsisdiv.titlepage.before.recto">
</xsl:template>

<xsl:template name="refsynopsisdiv.titlepage.before.verso">
</xsl:template>

<xsl:template name="refsynopsisdiv.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="refsynopsisdiv.titlepage.before.recto"/>
      <xsl:call-template name="refsynopsisdiv.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="refsynopsisdiv.titlepage.before.verso"/>
      <xsl:call-template name="refsynopsisdiv.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="refsynopsisdiv.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="refsynopsisdiv.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="refsynopsisdiv.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="refsynopsisdiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsynopsisdiv.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="refsynopsisdiv.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="refsynopsisdiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsynopsisdiv.titlepage.recto.style">
<xsl:apply-templates select="." mode="refsynopsisdiv.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="refsection.titlepage.recto">
  <xsl:choose>
    <xsl:when test="refsectioninfo/title">
      <xsl:apply-templates mode="refsection.titlepage.recto.auto.mode" select="refsectioninfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="refsection.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="refsection.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="refsection.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="refsection.titlepage.recto.auto.mode" select="refsectioninfo/itermset"/>
  <xsl:apply-templates mode="refsection.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="refsection.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="refsection.titlepage.verso">
</xsl:template>

<xsl:template name="refsection.titlepage.separator">
</xsl:template>

<xsl:template name="refsection.titlepage.before.recto">
</xsl:template>

<xsl:template name="refsection.titlepage.before.verso">
</xsl:template>

<xsl:template name="refsection.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="refsection.titlepage.before.recto"/>
      <xsl:call-template name="refsection.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="refsection.titlepage.before.verso"/>
      <xsl:call-template name="refsection.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="refsection.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="refsection.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="refsection.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="refsection.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsection.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="refsection.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="refsection.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsection.titlepage.recto.style">
<xsl:apply-templates select="." mode="refsection.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="refsect1.titlepage.recto">
  <xsl:choose>
    <xsl:when test="refsect1info/title">
      <xsl:apply-templates mode="refsect1.titlepage.recto.auto.mode" select="refsect1info/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="refsect1.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="refsect1.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="refsect1.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="refsect1.titlepage.recto.auto.mode" select="refsect1info/itermset"/>
  <xsl:apply-templates mode="refsect1.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="refsect1.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="refsect1.titlepage.verso">
</xsl:template>

<xsl:template name="refsect1.titlepage.separator">
</xsl:template>

<xsl:template name="refsect1.titlepage.before.recto">
</xsl:template>

<xsl:template name="refsect1.titlepage.before.verso">
</xsl:template>

<xsl:template name="refsect1.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="refsect1.titlepage.before.recto"/>
      <xsl:call-template name="refsect1.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="refsect1.titlepage.before.verso"/>
      <xsl:call-template name="refsect1.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="refsect1.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="refsect1.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="refsect1.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="refsect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsect1.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="refsect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="refsect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="refsect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="refsect2.titlepage.recto">
  <xsl:choose>
    <xsl:when test="refsect2info/title">
      <xsl:apply-templates mode="refsect2.titlepage.recto.auto.mode" select="refsect2info/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="refsect2.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="refsect2.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="refsect2.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="refsect2.titlepage.recto.auto.mode" select="refsect2info/itermset"/>
  <xsl:apply-templates mode="refsect2.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="refsect2.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="refsect2.titlepage.verso">
</xsl:template>

<xsl:template name="refsect2.titlepage.separator">
</xsl:template>

<xsl:template name="refsect2.titlepage.before.recto">
</xsl:template>

<xsl:template name="refsect2.titlepage.before.verso">
</xsl:template>

<xsl:template name="refsect2.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="refsect2.titlepage.before.recto"/>
      <xsl:call-template name="refsect2.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="refsect2.titlepage.before.verso"/>
      <xsl:call-template name="refsect2.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="refsect2.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="refsect2.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="refsect2.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="refsect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsect2.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="refsect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="refsect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="refsect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="refsect3.titlepage.recto">
  <xsl:choose>
    <xsl:when test="refsect3info/title">
      <xsl:apply-templates mode="refsect3.titlepage.recto.auto.mode" select="refsect3info/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="refsect3.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="refsect3.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="refsect3.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="refsect3.titlepage.recto.auto.mode" select="refsect3info/itermset"/>
  <xsl:apply-templates mode="refsect3.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="refsect3.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="refsect3.titlepage.verso">
</xsl:template>

<xsl:template name="refsect3.titlepage.separator">
</xsl:template>

<xsl:template name="refsect3.titlepage.before.recto">
</xsl:template>

<xsl:template name="refsect3.titlepage.before.verso">
</xsl:template>

<xsl:template name="refsect3.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="refsect3.titlepage.before.recto"/>
      <xsl:call-template name="refsect3.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="refsect3.titlepage.before.verso"/>
      <xsl:call-template name="refsect3.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="refsect3.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="refsect3.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="refsect3.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="refsect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsect3.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="refsect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="refsect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="refsect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="refsect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="dedication.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="dedication.titlepage.recto.style" margin-left="{$title.margin.left}" font-size="24.8832pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::dedication[1]"/>
</xsl:call-template></fo:block>
  <xsl:choose>
    <xsl:when test="dedicationinfo/subtitle">
      <xsl:apply-templates mode="dedication.titlepage.recto.auto.mode" select="dedicationinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="dedication.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="dedication.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="dedication.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="dedication.titlepage.recto.auto.mode" select="dedicationinfo/itermset"/>
  <xsl:apply-templates mode="dedication.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="dedication.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="dedication.titlepage.verso">
</xsl:template>

<xsl:template name="dedication.titlepage.separator">
</xsl:template>

<xsl:template name="dedication.titlepage.before.recto">
</xsl:template>

<xsl:template name="dedication.titlepage.before.verso">
</xsl:template>

<xsl:template name="dedication.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="dedication.titlepage.before.recto"/>
      <xsl:call-template name="dedication.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="dedication.titlepage.before.verso"/>
      <xsl:call-template name="dedication.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="dedication.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="dedication.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="dedication.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subtitle" mode="dedication.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="dedication.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="dedication.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="dedication.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="dedication.titlepage.recto.style">
<xsl:apply-templates select="." mode="dedication.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="acknowledgements.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="acknowledgements.titlepage.recto.style" margin-left="{$title.margin.left}" font-size="24.8832pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::acknowledgements[1]"/>
</xsl:call-template></fo:block>
  <xsl:choose>
    <xsl:when test="acknowledgementsinfo/subtitle">
      <xsl:apply-templates mode="acknowledgements.titlepage.recto.auto.mode" select="acknowledgementsinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="acknowledgements.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="acknowledgements.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="acknowledgements.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="acknowledgements.titlepage.recto.auto.mode" select="acknowledgementsinfo/itermset"/>
  <xsl:apply-templates mode="acknowledgements.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="acknowledgements.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="acknowledgements.titlepage.verso">
</xsl:template>

<xsl:template name="acknowledgements.titlepage.separator">
</xsl:template>

<xsl:template name="acknowledgements.titlepage.before.recto">
</xsl:template>

<xsl:template name="acknowledgements.titlepage.before.verso">
</xsl:template>

<xsl:template name="acknowledgements.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="acknowledgements.titlepage.before.recto"/>
      <xsl:call-template name="acknowledgements.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="acknowledgements.titlepage.before.verso"/>
      <xsl:call-template name="acknowledgements.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="acknowledgements.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="acknowledgements.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="acknowledgements.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subtitle" mode="acknowledgements.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="acknowledgements.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="acknowledgements.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="acknowledgements.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="acknowledgements.titlepage.recto.style">
<xsl:apply-templates select="." mode="acknowledgements.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="preface.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style" margin-left="{$title.margin.left}" font-size="24.8832pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::preface[1]"/>
</xsl:call-template></fo:block>
  <xsl:choose>
    <xsl:when test="prefaceinfo/subtitle">
      <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/corpauthor"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/corpauthor"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/authorgroup"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/authorgroup"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/author"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/author"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/othercredit"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/othercredit"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/releaseinfo"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/releaseinfo"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/copyright"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/copyright"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/legalnotice"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/legalnotice"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/pubdate"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/pubdate"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/revision"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/revision"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/revhistory"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/revhistory"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/abstract"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/abstract"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="prefaceinfo/itermset"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="preface.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="preface.titlepage.verso">
</xsl:template>

<xsl:template name="preface.titlepage.separator">
</xsl:template>

<xsl:template name="preface.titlepage.before.recto">
</xsl:template>

<xsl:template name="preface.titlepage.before.verso">
</xsl:template>

<xsl:template name="preface.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="preface.titlepage.before.recto"/>
      <xsl:call-template name="preface.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="preface.titlepage.before.verso"/>
      <xsl:call-template name="preface.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="preface.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="preface.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="preface.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subtitle" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="preface.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="preface.titlepage.recto.style">
<xsl:apply-templates select="." mode="preface.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="chapter.titlepage.recto">
  <xsl:choose>
    <xsl:when test="chapterinfo/title">
      <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="chapterinfo/subtitle">
      <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/corpauthor"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/corpauthor"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/authorgroup"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/authorgroup"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/author"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/author"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/othercredit"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/othercredit"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/releaseinfo"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/releaseinfo"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/copyright"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/copyright"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/legalnotice"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/legalnotice"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/pubdate"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/pubdate"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/revision"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/revision"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/revhistory"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/revhistory"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/abstract"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/abstract"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="chapterinfo/itermset"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="chapter.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="chapter.titlepage.verso">
</xsl:template>

<xsl:template name="chapter.titlepage.separator">
</xsl:template>

<xsl:template name="chapter.titlepage.before.recto">
</xsl:template>

<xsl:template name="chapter.titlepage.before.verso">
</xsl:template>

<xsl:template name="chapter.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="{$title.fontset}">
    <xsl:variable name="recto.content">
      <xsl:call-template name="chapter.titlepage.before.recto"/>
      <xsl:call-template name="chapter.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block margin-left="{$title.margin.left}"><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="chapter.titlepage.before.verso"/>
      <xsl:call-template name="chapter.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="chapter.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="chapter.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="chapter.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style" font-size="24.8832pt" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::chapter[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style" space-before="0.5em" font-style="italic" font-size="14.4pt" font-weight="bold">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style" space-before="0.5em" space-after="0.5em" font-size="14.4pt">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style" space-before="0.5em" space-after="0.5em" font-size="14.4pt">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style" space-before="0.5em" space-after="0.5em" font-size="14.4pt">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="chapter.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="chapter.titlepage.recto.style">
<xsl:apply-templates select="." mode="chapter.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="appendix.titlepage.recto">
  <xsl:choose>
    <xsl:when test="appendixinfo/title">
      <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="appendixinfo/subtitle">
      <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/corpauthor"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/corpauthor"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/authorgroup"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/authorgroup"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/author"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/author"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/othercredit"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/othercredit"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/releaseinfo"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/releaseinfo"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/copyright"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/copyright"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/legalnotice"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/legalnotice"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/pubdate"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/pubdate"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/revision"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/revision"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/revhistory"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/revhistory"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/abstract"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/abstract"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="appendixinfo/itermset"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="appendix.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="appendix.titlepage.verso">
</xsl:template>

<xsl:template name="appendix.titlepage.separator">
</xsl:template>

<xsl:template name="appendix.titlepage.before.recto">
</xsl:template>

<xsl:template name="appendix.titlepage.before.verso">
</xsl:template>

<xsl:template name="appendix.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="appendix.titlepage.before.recto"/>
      <xsl:call-template name="appendix.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="appendix.titlepage.before.verso"/>
      <xsl:call-template name="appendix.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="appendix.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="appendix.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="appendix.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style" margin-left="{$title.margin.left}" font-size="24.8832pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::appendix[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="appendix.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="appendix.titlepage.recto.style">
<xsl:apply-templates select="." mode="appendix.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="section.titlepage.recto">
  <xsl:choose>
    <xsl:when test="sectioninfo/title">
      <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="sectioninfo/subtitle">
      <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/corpauthor"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/authorgroup"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/author"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/othercredit"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/releaseinfo"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/copyright"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/legalnotice"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/pubdate"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/revision"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/revhistory"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/abstract"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="sectioninfo/itermset"/>
  <xsl:apply-templates mode="section.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="section.titlepage.verso">
</xsl:template>

<xsl:template name="section.titlepage.separator">
</xsl:template>

<xsl:template name="section.titlepage.before.recto">
</xsl:template>

<xsl:template name="section.titlepage.before.verso">
</xsl:template>

<xsl:template name="section.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="section.titlepage.before.recto"/>
      <xsl:call-template name="section.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="section.titlepage.before.verso"/>
      <xsl:call-template name="section.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="section.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="section.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="section.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style" margin-left="{$title.margin.left}" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="section.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="section.titlepage.recto.style">
<xsl:apply-templates select="." mode="section.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="sect1.titlepage.recto">
  <xsl:choose>
    <xsl:when test="sect1info/title">
      <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="sect1info/subtitle">
      <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/corpauthor"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/authorgroup"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/author"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/othercredit"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/releaseinfo"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/copyright"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/legalnotice"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/pubdate"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/revision"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/revhistory"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/abstract"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="sect1info/itermset"/>
  <xsl:apply-templates mode="sect1.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="sect1.titlepage.verso">
</xsl:template>

<xsl:template name="sect1.titlepage.separator">
</xsl:template>

<xsl:template name="sect1.titlepage.before.recto">
</xsl:template>

<xsl:template name="sect1.titlepage.before.verso">
</xsl:template>

<xsl:template name="sect1.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="sect1.titlepage.before.recto"/>
      <xsl:call-template name="sect1.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="sect1.titlepage.before.verso"/>
      <xsl:call-template name="sect1.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="sect1.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="sect1.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="sect1.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style" margin-left="{$title.margin.left}" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="sect1.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect1.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect1.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="sect2.titlepage.recto">
  <xsl:choose>
    <xsl:when test="sect2info/title">
      <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="sect2info/subtitle">
      <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/corpauthor"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/authorgroup"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/author"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/othercredit"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/releaseinfo"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/copyright"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/legalnotice"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/pubdate"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/revision"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/revhistory"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/abstract"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="sect2info/itermset"/>
  <xsl:apply-templates mode="sect2.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="sect2.titlepage.verso">
</xsl:template>

<xsl:template name="sect2.titlepage.separator">
</xsl:template>

<xsl:template name="sect2.titlepage.before.recto">
</xsl:template>

<xsl:template name="sect2.titlepage.before.verso">
</xsl:template>

<xsl:template name="sect2.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="sect2.titlepage.before.recto"/>
      <xsl:call-template name="sect2.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="sect2.titlepage.before.verso"/>
      <xsl:call-template name="sect2.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="sect2.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="sect2.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="sect2.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style" margin-left="{$title.margin.left}" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="sect2.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect2.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect2.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="sect3.titlepage.recto">
  <xsl:choose>
    <xsl:when test="sect3info/title">
      <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="sect3info/subtitle">
      <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/corpauthor"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/authorgroup"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/author"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/othercredit"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/releaseinfo"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/copyright"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/legalnotice"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/pubdate"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/revision"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/revhistory"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/abstract"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="sect3info/itermset"/>
  <xsl:apply-templates mode="sect3.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="sect3.titlepage.verso">
</xsl:template>

<xsl:template name="sect3.titlepage.separator">
</xsl:template>

<xsl:template name="sect3.titlepage.before.recto">
</xsl:template>

<xsl:template name="sect3.titlepage.before.verso">
</xsl:template>

<xsl:template name="sect3.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="sect3.titlepage.before.recto"/>
      <xsl:call-template name="sect3.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="sect3.titlepage.before.verso"/>
      <xsl:call-template name="sect3.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="sect3.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="sect3.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="sect3.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style" margin-left="{$title.margin.left}" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="sect3.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect3.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect3.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="sect4.titlepage.recto">
  <xsl:choose>
    <xsl:when test="sect4info/title">
      <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="sect4info/subtitle">
      <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/corpauthor"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/authorgroup"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/author"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/othercredit"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/releaseinfo"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/copyright"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/legalnotice"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/pubdate"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/revision"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/revhistory"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/abstract"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="sect4info/itermset"/>
  <xsl:apply-templates mode="sect4.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="sect4.titlepage.verso">
</xsl:template>

<xsl:template name="sect4.titlepage.separator">
</xsl:template>

<xsl:template name="sect4.titlepage.before.recto">
</xsl:template>

<xsl:template name="sect4.titlepage.before.verso">
</xsl:template>

<xsl:template name="sect4.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="sect4.titlepage.before.recto"/>
      <xsl:call-template name="sect4.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="sect4.titlepage.before.verso"/>
      <xsl:call-template name="sect4.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="sect4.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="sect4.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="sect4.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style" margin-left="{$title.margin.left}" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="sect4.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect4.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect4.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="sect5.titlepage.recto">
  <xsl:choose>
    <xsl:when test="sect5info/title">
      <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="sect5info/subtitle">
      <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/corpauthor"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/authorgroup"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/author"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/othercredit"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/releaseinfo"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/copyright"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/legalnotice"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/pubdate"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/revision"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/revhistory"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/abstract"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="sect5info/itermset"/>
  <xsl:apply-templates mode="sect5.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="sect5.titlepage.verso">
</xsl:template>

<xsl:template name="sect5.titlepage.separator">
</xsl:template>

<xsl:template name="sect5.titlepage.before.recto">
</xsl:template>

<xsl:template name="sect5.titlepage.before.verso">
</xsl:template>

<xsl:template name="sect5.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="sect5.titlepage.before.recto"/>
      <xsl:call-template name="sect5.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="sect5.titlepage.before.verso"/>
      <xsl:call-template name="sect5.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="sect5.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="sect5.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="sect5.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style" margin-left="{$title.margin.left}" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="sect5.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sect5.titlepage.recto.style">
<xsl:apply-templates select="." mode="sect5.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="simplesect.titlepage.recto">
  <xsl:choose>
    <xsl:when test="simplesectinfo/title">
      <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="simplesectinfo/subtitle">
      <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/corpauthor"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/corpauthor"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/authorgroup"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/authorgroup"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/author"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/author"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/othercredit"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/othercredit"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/releaseinfo"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/releaseinfo"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/copyright"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/copyright"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/legalnotice"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/legalnotice"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/pubdate"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/pubdate"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/revision"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/revision"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/revhistory"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/revhistory"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/abstract"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/abstract"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="simplesectinfo/itermset"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="simplesect.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="simplesect.titlepage.verso">
</xsl:template>

<xsl:template name="simplesect.titlepage.separator">
</xsl:template>

<xsl:template name="simplesect.titlepage.before.recto">
</xsl:template>

<xsl:template name="simplesect.titlepage.before.verso">
</xsl:template>

<xsl:template name="simplesect.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="simplesect.titlepage.before.recto"/>
      <xsl:call-template name="simplesect.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="simplesect.titlepage.before.verso"/>
      <xsl:call-template name="simplesect.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="simplesect.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="simplesect.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="simplesect.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style" margin-left="{$title.margin.left}" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="simplesect.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="simplesect.titlepage.recto.style">
<xsl:apply-templates select="." mode="simplesect.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="topic.titlepage.recto">
  <xsl:choose>
    <xsl:when test="topicinfo/title">
      <xsl:apply-templates mode="topic.titlepage.recto.auto.mode" select="topicinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="topic.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="topic.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="topicinfo/subtitle">
      <xsl:apply-templates mode="topic.titlepage.recto.auto.mode" select="topicinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="topic.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="topic.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

</xsl:template>

<xsl:template name="topic.titlepage.verso">
</xsl:template>

<xsl:template name="topic.titlepage.separator">
</xsl:template>

<xsl:template name="topic.titlepage.before.recto">
</xsl:template>

<xsl:template name="topic.titlepage.before.verso">
</xsl:template>

<xsl:template name="topic.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="topic.titlepage.before.recto"/>
      <xsl:call-template name="topic.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="topic.titlepage.before.verso"/>
      <xsl:call-template name="topic.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="topic.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="topic.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="topic.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="topic.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="topic.titlepage.recto.style" font-weight="bold" font-size="17.28pt" space-before="1em" space-after="1em" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="topic.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="topic.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="topic.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="topic.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="bibliography.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="bibliography.titlepage.recto.style" margin-left="{$title.margin.left}" font-size="24.8832pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::bibliography[1]"/>
</xsl:call-template></fo:block>
  <xsl:choose>
    <xsl:when test="bibliographyinfo/subtitle">
      <xsl:apply-templates mode="bibliography.titlepage.recto.auto.mode" select="bibliographyinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="bibliography.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="bibliography.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="bibliography.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="bibliography.titlepage.recto.auto.mode" select="bibliographyinfo/itermset"/>
  <xsl:apply-templates mode="bibliography.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="bibliography.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="bibliography.titlepage.verso">
</xsl:template>

<xsl:template name="bibliography.titlepage.separator">
</xsl:template>

<xsl:template name="bibliography.titlepage.before.recto">
</xsl:template>

<xsl:template name="bibliography.titlepage.before.verso">
</xsl:template>

<xsl:template name="bibliography.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="bibliography.titlepage.before.recto"/>
      <xsl:call-template name="bibliography.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="bibliography.titlepage.before.verso"/>
      <xsl:call-template name="bibliography.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="bibliography.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="bibliography.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="bibliography.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subtitle" mode="bibliography.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="bibliography.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="bibliography.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="bibliography.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="bibliography.titlepage.recto.style">
<xsl:apply-templates select="." mode="bibliography.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="bibliodiv.titlepage.recto">
  <xsl:choose>
    <xsl:when test="bibliodivinfo/title">
      <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="bibliodivinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="bibliodivinfo/subtitle">
      <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="bibliodivinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="bibliodivinfo/itermset"/>
  <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="bibliodiv.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="bibliodiv.titlepage.verso">
</xsl:template>

<xsl:template name="bibliodiv.titlepage.separator">
</xsl:template>

<xsl:template name="bibliodiv.titlepage.before.recto">
</xsl:template>

<xsl:template name="bibliodiv.titlepage.before.verso">
</xsl:template>

<xsl:template name="bibliodiv.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="bibliodiv.titlepage.before.recto"/>
      <xsl:call-template name="bibliodiv.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="bibliodiv.titlepage.before.verso"/>
      <xsl:call-template name="bibliodiv.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="bibliodiv.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="bibliodiv.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="bibliodiv.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="bibliodiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="bibliodiv.titlepage.recto.style" margin-left="{$title.margin.left}" font-size="20.736pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::bibliodiv[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="bibliodiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="bibliodiv.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="bibliodiv.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="bibliodiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="bibliodiv.titlepage.recto.style">
<xsl:apply-templates select="." mode="bibliodiv.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="glossary.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="glossary.titlepage.recto.style" margin-left="{$title.margin.left}" font-size="24.8832pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::glossary[1]"/>
</xsl:call-template></fo:block>
  <xsl:choose>
    <xsl:when test="glossaryinfo/subtitle">
      <xsl:apply-templates mode="glossary.titlepage.recto.auto.mode" select="glossaryinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="glossary.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="glossary.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="glossary.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="glossary.titlepage.recto.auto.mode" select="glossaryinfo/itermset"/>
  <xsl:apply-templates mode="glossary.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="glossary.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="glossary.titlepage.verso">
</xsl:template>

<xsl:template name="glossary.titlepage.separator">
</xsl:template>

<xsl:template name="glossary.titlepage.before.recto">
</xsl:template>

<xsl:template name="glossary.titlepage.before.verso">
</xsl:template>

<xsl:template name="glossary.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="glossary.titlepage.before.recto"/>
      <xsl:call-template name="glossary.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="glossary.titlepage.before.verso"/>
      <xsl:call-template name="glossary.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="glossary.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="glossary.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="glossary.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subtitle" mode="glossary.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="glossary.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="glossary.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="glossary.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="glossary.titlepage.recto.style">
<xsl:apply-templates select="." mode="glossary.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="glossdiv.titlepage.recto">
  <xsl:choose>
    <xsl:when test="glossdivinfo/title">
      <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="glossdivinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="glossdivinfo/subtitle">
      <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="glossdivinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="glossdivinfo/itermset"/>
  <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="glossdiv.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="glossdiv.titlepage.verso">
</xsl:template>

<xsl:template name="glossdiv.titlepage.separator">
</xsl:template>

<xsl:template name="glossdiv.titlepage.before.recto">
</xsl:template>

<xsl:template name="glossdiv.titlepage.before.verso">
</xsl:template>

<xsl:template name="glossdiv.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="glossdiv.titlepage.before.recto"/>
      <xsl:call-template name="glossdiv.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="glossdiv.titlepage.before.verso"/>
      <xsl:call-template name="glossdiv.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="glossdiv.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="glossdiv.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="glossdiv.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="glossdiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="glossdiv.titlepage.recto.style" margin-left="{$title.margin.left}" font-size="20.736pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::glossdiv[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="glossdiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="glossdiv.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="glossdiv.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="glossdiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="glossdiv.titlepage.recto.style">
<xsl:apply-templates select="." mode="glossdiv.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="index.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="index.titlepage.recto.style" margin-left="0pt" font-size="24.8832pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::index[1]"/>
<xsl:with-param name="pagewide" select="1"/>
</xsl:call-template></fo:block>
  <xsl:choose>
    <xsl:when test="indexinfo/subtitle">
      <xsl:apply-templates mode="index.titlepage.recto.auto.mode" select="indexinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="index.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="index.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="index.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="index.titlepage.recto.auto.mode" select="indexinfo/itermset"/>
  <xsl:apply-templates mode="index.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="index.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="index.titlepage.verso">
</xsl:template>

<xsl:template name="index.titlepage.separator">
</xsl:template>

<xsl:template name="index.titlepage.before.recto">
</xsl:template>

<xsl:template name="index.titlepage.before.verso">
</xsl:template>

<xsl:template name="index.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="index.titlepage.before.recto"/>
      <xsl:call-template name="index.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="index.titlepage.before.verso"/>
      <xsl:call-template name="index.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="index.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="index.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="index.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subtitle" mode="index.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="index.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="index.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="index.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="index.titlepage.recto.style">
<xsl:apply-templates select="." mode="index.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="indexdiv.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="indexdiv.titlepage.recto.style">
<xsl:call-template name="indexdiv.title">
<xsl:with-param name="title" select="title"/>
</xsl:call-template></fo:block>
  <xsl:choose>
    <xsl:when test="indexdivinfo/subtitle">
      <xsl:apply-templates mode="indexdiv.titlepage.recto.auto.mode" select="indexdivinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="indexdiv.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="indexdiv.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="indexdiv.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="indexdiv.titlepage.recto.auto.mode" select="indexdivinfo/itermset"/>
  <xsl:apply-templates mode="indexdiv.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="indexdiv.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="indexdiv.titlepage.verso">
</xsl:template>

<xsl:template name="indexdiv.titlepage.separator">
</xsl:template>

<xsl:template name="indexdiv.titlepage.before.recto">
</xsl:template>

<xsl:template name="indexdiv.titlepage.before.verso">
</xsl:template>

<xsl:template name="indexdiv.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="indexdiv.titlepage.before.recto"/>
      <xsl:call-template name="indexdiv.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="indexdiv.titlepage.before.verso"/>
      <xsl:call-template name="indexdiv.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="indexdiv.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="indexdiv.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="indexdiv.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subtitle" mode="indexdiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="indexdiv.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="indexdiv.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="indexdiv.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="indexdiv.titlepage.recto.style">
<xsl:apply-templates select="." mode="indexdiv.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="setindex.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="setindex.titlepage.recto.style" margin-left="0pt" font-size="24.8832pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::setindex[1]"/>
<xsl:with-param name="pagewide" select="1"/>
</xsl:call-template></fo:block>
  <xsl:choose>
    <xsl:when test="setindexinfo/subtitle">
      <xsl:apply-templates mode="setindex.titlepage.recto.auto.mode" select="setindexinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="setindex.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="setindex.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="setindex.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="setindex.titlepage.recto.auto.mode" select="setindexinfo/itermset"/>
  <xsl:apply-templates mode="setindex.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="setindex.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="setindex.titlepage.verso">
</xsl:template>

<xsl:template name="setindex.titlepage.separator">
</xsl:template>

<xsl:template name="setindex.titlepage.before.recto">
</xsl:template>

<xsl:template name="setindex.titlepage.before.verso">
</xsl:template>

<xsl:template name="setindex.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="setindex.titlepage.before.recto"/>
      <xsl:call-template name="setindex.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="setindex.titlepage.before.verso"/>
      <xsl:call-template name="setindex.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="setindex.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="setindex.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="setindex.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subtitle" mode="setindex.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="setindex.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="setindex.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="setindex.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="setindex.titlepage.recto.style">
<xsl:apply-templates select="." mode="setindex.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="colophon.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="colophon.titlepage.recto.style" margin-left="{$title.margin.left}" font-size="24.8832pt" font-family="{$title.fontset}" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::colophon[1]"/>
</xsl:call-template></fo:block>
  <xsl:choose>
    <xsl:when test="colophoninfo/subtitle">
      <xsl:apply-templates mode="colophon.titlepage.recto.auto.mode" select="colophoninfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="colophon.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="colophon.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="colophon.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="colophon.titlepage.recto.auto.mode" select="colophoninfo/itermset"/>
  <xsl:apply-templates mode="colophon.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="colophon.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="colophon.titlepage.verso">
</xsl:template>

<xsl:template name="colophon.titlepage.separator">
</xsl:template>

<xsl:template name="colophon.titlepage.before.recto">
</xsl:template>

<xsl:template name="colophon.titlepage.before.verso">
</xsl:template>

<xsl:template name="colophon.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="colophon.titlepage.before.recto"/>
      <xsl:call-template name="colophon.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="colophon.titlepage.before.verso"/>
      <xsl:call-template name="colophon.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="colophon.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="colophon.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="colophon.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="subtitle" mode="colophon.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="colophon.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="colophon.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="colophon.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="colophon.titlepage.recto.style">
<xsl:apply-templates select="." mode="colophon.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="sidebar.titlepage.recto">
  <xsl:choose>
    <xsl:when test="sidebarinfo/title">
      <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="sidebarinfo/title"/>
    </xsl:when>
    <xsl:when test="docinfo/title">
      <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="docinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="sidebarinfo/subtitle">
      <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="sidebarinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="docinfo/subtitle">
      <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="docinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="sidebarinfo/itermset"/>
  <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="docinfo/itermset"/>
  <xsl:apply-templates mode="sidebar.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="sidebar.titlepage.verso">
</xsl:template>

<xsl:template name="sidebar.titlepage.separator">
</xsl:template>

<xsl:template name="sidebar.titlepage.before.recto">
</xsl:template>

<xsl:template name="sidebar.titlepage.before.verso">
</xsl:template>

<xsl:template name="sidebar.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="sidebar.titlepage.before.recto"/>
      <xsl:call-template name="sidebar.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="sidebar.titlepage.before.verso"/>
      <xsl:call-template name="sidebar.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="sidebar.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="sidebar.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="sidebar.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="sidebar.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sidebar.titlepage.recto.style" font-family="{$title.fontset}" font-weight="bold">
<xsl:apply-templates select="." mode="sidebar.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="sidebar.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sidebar.titlepage.recto.style" font-family="{$title.fontset}">
<xsl:apply-templates select="." mode="sidebar.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="sidebar.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="sidebar.titlepage.recto.style">
<xsl:apply-templates select="." mode="sidebar.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="qandaset.titlepage.recto">
  <xsl:choose>
    <xsl:when test="qandasetinfo/title">
      <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/title"/>
    </xsl:when>
    <xsl:when test="blockinfo/title">
      <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/title"/>
    </xsl:when>
    <xsl:when test="info/title">
      <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/title"/>
    </xsl:when>
    <xsl:when test="title">
      <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="title"/>
    </xsl:when>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="qandasetinfo/subtitle">
      <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="blockinfo/subtitle">
      <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/subtitle"/>
    </xsl:when>
    <xsl:when test="info/subtitle">
      <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/subtitle"/>
    </xsl:when>
    <xsl:when test="subtitle">
      <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="subtitle"/>
    </xsl:when>
  </xsl:choose>

  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/corpauthor"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/corpauthor"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/corpauthor"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/authorgroup"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/authorgroup"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/authorgroup"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/author"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/author"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/author"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/othercredit"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/othercredit"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/othercredit"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/releaseinfo"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/releaseinfo"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/releaseinfo"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/copyright"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/copyright"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/copyright"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/legalnotice"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/legalnotice"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/legalnotice"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/pubdate"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/pubdate"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/pubdate"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/revision"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/revision"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/revision"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/revhistory"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/revhistory"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/revhistory"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/abstract"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/abstract"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/abstract"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="qandasetinfo/itermset"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="blockinfo/itermset"/>
  <xsl:apply-templates mode="qandaset.titlepage.recto.auto.mode" select="info/itermset"/>
</xsl:template>

<xsl:template name="qandaset.titlepage.verso">
</xsl:template>

<xsl:template name="qandaset.titlepage.separator">
</xsl:template>

<xsl:template name="qandaset.titlepage.before.recto">
</xsl:template>

<xsl:template name="qandaset.titlepage.before.verso">
</xsl:template>

<xsl:template name="qandaset.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="{$title.fontset}">
    <xsl:variable name="recto.content">
      <xsl:call-template name="qandaset.titlepage.before.recto"/>
      <xsl:call-template name="qandaset.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block start-indent="0pt" text-align="center"><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="qandaset.titlepage.before.verso"/>
      <xsl:call-template name="qandaset.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="qandaset.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="qandaset.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="qandaset.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="title" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" keep-with-next.within-column="always" font-size="24.8832pt" font-weight="bold">
<xsl:call-template name="component.title">
<xsl:with-param name="node" select="ancestor-or-self::qandaset[1]"/>
</xsl:call-template>
</fo:block>
</xsl:template>

<xsl:template match="subtitle" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="corpauthor" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em" font-size="14.4pt">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="authorgroup" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em" font-size="14.4pt">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="author" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em" font-size="14.4pt">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="othercredit" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="releaseinfo" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="copyright" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="legalnotice" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" text-align="start" margin-left="0.5in" margin-right="0.5in" font-family="{$body.fontset}">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="pubdate" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revision" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="revhistory" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="abstract" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style" space-before="0.5em" text-align="start" margin-left="0.5in" margin-right="0.5in" font-family="{$body.fontset}">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template match="itermset" mode="qandaset.titlepage.recto.auto.mode">
<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="qandaset.titlepage.recto.style">
<xsl:apply-templates select="." mode="qandaset.titlepage.recto.mode"/>
</fo:block>
</xsl:template>

<xsl:template name="table.of.contents.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="table.of.contents.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em" space-after="0.5em" start-indent="0pt" font-size="17.28pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'TableofContents'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="table.of.contents.titlepage.verso">
</xsl:template>

<xsl:template name="table.of.contents.titlepage.separator">
</xsl:template>

<xsl:template name="table.of.contents.titlepage.before.recto">
</xsl:template>

<xsl:template name="table.of.contents.titlepage.before.verso">
</xsl:template>

<xsl:template name="table.of.contents.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="table.of.contents.titlepage.before.recto"/>
      <xsl:call-template name="table.of.contents.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="table.of.contents.titlepage.before.verso"/>
      <xsl:call-template name="table.of.contents.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="table.of.contents.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="table.of.contents.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="table.of.contents.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="list.of.tables.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="list.of.tables.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em" space-after="0.5em" start-indent="0pt" font-size="17.28pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofTables'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="list.of.tables.titlepage.verso">
</xsl:template>

<xsl:template name="list.of.tables.titlepage.separator">
</xsl:template>

<xsl:template name="list.of.tables.titlepage.before.recto">
</xsl:template>

<xsl:template name="list.of.tables.titlepage.before.verso">
</xsl:template>

<xsl:template name="list.of.tables.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="list.of.tables.titlepage.before.recto"/>
      <xsl:call-template name="list.of.tables.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="list.of.tables.titlepage.before.verso"/>
      <xsl:call-template name="list.of.tables.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="list.of.tables.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="list.of.tables.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="list.of.tables.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="list.of.figures.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="list.of.figures.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em" space-after="0.5em" start-indent="0pt" font-size="17.28pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofFigures'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="list.of.figures.titlepage.verso">
</xsl:template>

<xsl:template name="list.of.figures.titlepage.separator">
</xsl:template>

<xsl:template name="list.of.figures.titlepage.before.recto">
</xsl:template>

<xsl:template name="list.of.figures.titlepage.before.verso">
</xsl:template>

<xsl:template name="list.of.figures.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="list.of.figures.titlepage.before.recto"/>
      <xsl:call-template name="list.of.figures.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="list.of.figures.titlepage.before.verso"/>
      <xsl:call-template name="list.of.figures.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="list.of.figures.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="list.of.figures.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="list.of.figures.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="list.of.examples.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="list.of.examples.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em" space-after="0.5em" start-indent="0pt" font-size="17.28pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofExamples'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="list.of.examples.titlepage.verso">
</xsl:template>

<xsl:template name="list.of.examples.titlepage.separator">
</xsl:template>

<xsl:template name="list.of.examples.titlepage.before.recto">
</xsl:template>

<xsl:template name="list.of.examples.titlepage.before.verso">
</xsl:template>

<xsl:template name="list.of.examples.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="list.of.examples.titlepage.before.recto"/>
      <xsl:call-template name="list.of.examples.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="list.of.examples.titlepage.before.verso"/>
      <xsl:call-template name="list.of.examples.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="list.of.examples.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="list.of.examples.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="list.of.examples.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="list.of.equations.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="list.of.equations.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em" space-after="0.5em" start-indent="0pt" font-size="17.28pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofEquations'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="list.of.equations.titlepage.verso">
</xsl:template>

<xsl:template name="list.of.equations.titlepage.separator">
</xsl:template>

<xsl:template name="list.of.equations.titlepage.before.recto">
</xsl:template>

<xsl:template name="list.of.equations.titlepage.before.verso">
</xsl:template>

<xsl:template name="list.of.equations.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="list.of.equations.titlepage.before.recto"/>
      <xsl:call-template name="list.of.equations.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="list.of.equations.titlepage.before.verso"/>
      <xsl:call-template name="list.of.equations.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="list.of.equations.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="list.of.equations.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="list.of.equations.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="list.of.procedures.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="list.of.procedures.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em" space-after="0.5em" start-indent="0pt" font-size="17.28pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofProcedures'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="list.of.procedures.titlepage.verso">
</xsl:template>

<xsl:template name="list.of.procedures.titlepage.separator">
</xsl:template>

<xsl:template name="list.of.procedures.titlepage.before.recto">
</xsl:template>

<xsl:template name="list.of.procedures.titlepage.before.verso">
</xsl:template>

<xsl:template name="list.of.procedures.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="list.of.procedures.titlepage.before.recto"/>
      <xsl:call-template name="list.of.procedures.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="list.of.procedures.titlepage.before.verso"/>
      <xsl:call-template name="list.of.procedures.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="list.of.procedures.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="list.of.procedures.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="list.of.procedures.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="list.of.unknowns.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="list.of.unknowns.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em" space-after="0.5em" start-indent="0pt" font-size="17.28pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofUnknown'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="list.of.unknowns.titlepage.verso">
</xsl:template>

<xsl:template name="list.of.unknowns.titlepage.separator">
</xsl:template>

<xsl:template name="list.of.unknowns.titlepage.before.recto">
</xsl:template>

<xsl:template name="list.of.unknowns.titlepage.before.verso">
</xsl:template>

<xsl:template name="list.of.unknowns.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="list.of.unknowns.titlepage.before.recto"/>
      <xsl:call-template name="list.of.unknowns.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="list.of.unknowns.titlepage.before.verso"/>
      <xsl:call-template name="list.of.unknowns.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="list.of.unknowns.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="list.of.unknowns.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="list.of.unknowns.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="component.list.of.tables.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="component.list.of.tables.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1em" space-before.maximum="1em" space-after="0.5em" margin-left="{$title.margin.left}" font-size="12pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofTables'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="component.list.of.tables.titlepage.verso">
</xsl:template>

<xsl:template name="component.list.of.tables.titlepage.separator">
</xsl:template>

<xsl:template name="component.list.of.tables.titlepage.before.recto">
</xsl:template>

<xsl:template name="component.list.of.tables.titlepage.before.verso">
</xsl:template>

<xsl:template name="component.list.of.tables.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="component.list.of.tables.titlepage.before.recto"/>
      <xsl:call-template name="component.list.of.tables.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="component.list.of.tables.titlepage.before.verso"/>
      <xsl:call-template name="component.list.of.tables.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="component.list.of.tables.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="component.list.of.tables.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="component.list.of.tables.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="component.list.of.figures.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="component.list.of.figures.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1em" space-before.maximum="1em" space-after="0.5em" margin-left="{$title.margin.left}" font-size="12pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofFigures'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="component.list.of.figures.titlepage.verso">
</xsl:template>

<xsl:template name="component.list.of.figures.titlepage.separator">
</xsl:template>

<xsl:template name="component.list.of.figures.titlepage.before.recto">
</xsl:template>

<xsl:template name="component.list.of.figures.titlepage.before.verso">
</xsl:template>

<xsl:template name="component.list.of.figures.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="component.list.of.figures.titlepage.before.recto"/>
      <xsl:call-template name="component.list.of.figures.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="component.list.of.figures.titlepage.before.verso"/>
      <xsl:call-template name="component.list.of.figures.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="component.list.of.figures.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="component.list.of.figures.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="component.list.of.figures.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="component.list.of.examples.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="component.list.of.examples.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1em" space-before.maximum="1em" space-after="0.5em" margin-left="{$title.margin.left}" font-size="12pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofExamples'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="component.list.of.examples.titlepage.verso">
</xsl:template>

<xsl:template name="component.list.of.examples.titlepage.separator">
</xsl:template>

<xsl:template name="component.list.of.examples.titlepage.before.recto">
</xsl:template>

<xsl:template name="component.list.of.examples.titlepage.before.verso">
</xsl:template>

<xsl:template name="component.list.of.examples.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="component.list.of.examples.titlepage.before.recto"/>
      <xsl:call-template name="component.list.of.examples.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="component.list.of.examples.titlepage.before.verso"/>
      <xsl:call-template name="component.list.of.examples.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="component.list.of.examples.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="component.list.of.examples.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="component.list.of.examples.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="component.list.of.equations.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="component.list.of.equations.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1em" space-before.maximum="1em" space-after="0.5em" margin-left="{$title.margin.left}" font-size="12pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofEquations'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="component.list.of.equations.titlepage.verso">
</xsl:template>

<xsl:template name="component.list.of.equations.titlepage.separator">
</xsl:template>

<xsl:template name="component.list.of.equations.titlepage.before.recto">
</xsl:template>

<xsl:template name="component.list.of.equations.titlepage.before.verso">
</xsl:template>

<xsl:template name="component.list.of.equations.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="component.list.of.equations.titlepage.before.recto"/>
      <xsl:call-template name="component.list.of.equations.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="component.list.of.equations.titlepage.before.verso"/>
      <xsl:call-template name="component.list.of.equations.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="component.list.of.equations.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="component.list.of.equations.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="component.list.of.equations.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="component.list.of.procedures.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="component.list.of.procedures.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1em" space-before.maximum="1em" space-after="0.5em" margin-left="{$title.margin.left}" font-size="12pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofProcedures'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="component.list.of.procedures.titlepage.verso">
</xsl:template>

<xsl:template name="component.list.of.procedures.titlepage.separator">
</xsl:template>

<xsl:template name="component.list.of.procedures.titlepage.before.recto">
</xsl:template>

<xsl:template name="component.list.of.procedures.titlepage.before.verso">
</xsl:template>

<xsl:template name="component.list.of.procedures.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="component.list.of.procedures.titlepage.before.recto"/>
      <xsl:call-template name="component.list.of.procedures.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="component.list.of.procedures.titlepage.before.verso"/>
      <xsl:call-template name="component.list.of.procedures.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="component.list.of.procedures.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="component.list.of.procedures.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="component.list.of.procedures.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template name="component.list.of.unknowns.titlepage.recto">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:use-attribute-sets="component.list.of.unknowns.titlepage.recto.style" space-before.minimum="1em" space-before.optimum="1em" space-before.maximum="1em" space-after="0.5em" margin-left="{$title.margin.left}" font-size="12pt" font-weight="bold" font-family="{$title.fontset}">
<xsl:call-template name="gentext">
<xsl:with-param name="key" select="'ListofUnknown'"/>
</xsl:call-template></fo:block>
</xsl:template>

<xsl:template name="component.list.of.unknowns.titlepage.verso">
</xsl:template>

<xsl:template name="component.list.of.unknowns.titlepage.separator">
</xsl:template>

<xsl:template name="component.list.of.unknowns.titlepage.before.recto">
</xsl:template>

<xsl:template name="component.list.of.unknowns.titlepage.before.verso">
</xsl:template>

<xsl:template name="component.list.of.unknowns.titlepage">
  <fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:variable name="recto.content">
      <xsl:call-template name="component.list.of.unknowns.titlepage.before.recto"/>
      <xsl:call-template name="component.list.of.unknowns.titlepage.recto"/>
    </xsl:variable>
    <xsl:variable name="recto.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($recto.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($recto.content) != '') or ($recto.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$recto.content"/></fo:block>
    </xsl:if>
    <xsl:variable name="verso.content">
      <xsl:call-template name="component.list.of.unknowns.titlepage.before.verso"/>
      <xsl:call-template name="component.list.of.unknowns.titlepage.verso"/>
    </xsl:variable>
    <xsl:variable name="verso.elements.count">
      <xsl:choose>
        <xsl:when test="function-available('exsl:node-set')"><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:when test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
          <!--Xalan quirk--><xsl:value-of select="count(exsl:node-set($verso.content)/*)"/></xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="(normalize-space($verso.content) != '') or ($verso.elements.count &gt; 0)">
      <fo:block><xsl:copy-of select="$verso.content"/></fo:block>
    </xsl:if>
    <xsl:call-template name="component.list.of.unknowns.titlepage.separator"/>
  </fo:block>
</xsl:template>

<xsl:template match="*" mode="component.list.of.unknowns.titlepage.recto.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="*" mode="component.list.of.unknowns.titlepage.verso.mode">
  <!-- if an element isn't found in this mode, -->
  <!-- try the generic titlepage.mode -->
  <xsl:apply-templates select="." mode="titlepage.mode"/>
</xsl:template>

</xsl:stylesheet>

