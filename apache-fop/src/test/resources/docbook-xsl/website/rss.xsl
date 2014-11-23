<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rss="http://purl.org/rss/1.0/"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:cvsf="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.CVS"
                xmlns:cvs="http://nwalsh.com/rdf/cvs#"
                xmlns:dc="http://purl.org/dc/elements/1.1/"
                exclude-result-prefixes="rss rdf cvs dc cvsf"
                version="1.0">

<xsl:output method="html"/>

<xsl:template match="rss">
  <xsl:variable name="rss" select="document(@feed, .)"/>

  <div class='rss'>
    <xsl:choose>
      <xsl:when test="not($rss)">
        <xsl:message>RSS Failed: <xsl:value-of select="@feed"/></xsl:message>
        <xsl:text>[RSS Failed: </xsl:text>
        <xsl:value-of select="@feed"/>
      </xsl:when>
      <xsl:when test="$rss/rdf:RDF">
        <xsl:apply-templates select="$rss/*/rss:channel"/>
      </xsl:when>
      <xsl:otherwise>
        <!-- is there an otherwise case? -->
        <xsl:apply-templates select="$rss//rss:channel"/>
      </xsl:otherwise>
    </xsl:choose>
  </div>
</xsl:template>

<xsl:template match="rss:channel">
  <xsl:variable name="image-resource" select="rss:image/@rdf:resource"/>
  <xsl:variable name="image" select="//rss:image[@rdf:about = $image-resource]"/>

  <xsl:if test="$image">
    <xsl:choose>
      <xsl:when test="$image/rss:link">
        <a href="{$image/rss:link}">
          <img src="{$image/rss:url}" alt="{$image/rss:title}" align="right" border="0"/>
        </a>
      </xsl:when>
      <xsl:otherwise>
        <img src="{$image/rss:url}" alt="{$image/rss:title}" align="right"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>

  <xsl:apply-templates select="rss:title"/>
  <xsl:apply-templates select="rss:description"/>
  <xsl:apply-templates select="rss:items"/>

  <xsl:if test="$image">
    <br clear="right"/>
  </xsl:if>
</xsl:template>

<xsl:template match="rss:title">
  <xsl:param name="wrapper" select="'h3'"/>

  <xsl:element name="{$wrapper}">
    <xsl:choose>
      <xsl:when test="../rss:link">
        <a href="{../rss:link[1]}">
          <xsl:apply-templates/>
        </a>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates/>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:if test="../dc:date|../cvs:date">
      <xsl:choose>
        <xsl:when test="../dc:date">
          <xsl:text> (</xsl:text>
          <xsl:value-of select="../dc:date[1]"/>
          <xsl:text>)</xsl:text>
        </xsl:when>
        <xsl:when test="function-available('cvsf:localTime')">
          <xsl:variable name="timeString" select="cvsf:localTime(../cvs:date[1])"/>
          <xsl:text> (</xsl:text>
          <xsl:value-of select="substring($timeString, 1, 3)"/>
          <xsl:text>, </xsl:text>
          <xsl:value-of select="substring($timeString, 9, 2)"/>
          <xsl:text> </xsl:text>
          <xsl:value-of select="substring($timeString, 5, 3)"/>
          <xsl:text> </xsl:text>
          <xsl:value-of select="substring($timeString, 25, 4)"/>
          <xsl:text>)</xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="../cvs:date[1]"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>
  </xsl:element>
</xsl:template>

<xsl:template match="rss:description">
  <p>
    <xsl:apply-templates/>
  </p>
</xsl:template>

<xsl:template match="rss:items">
  <dl>
    <xsl:for-each select="rdf:Seq/rdf:li[@rdf:resource and @rdf:resource != '']">
      <xsl:variable name="resource" select="@rdf:resource"/>
      <xsl:variable name="item" select="//rss:item[@rdf:about = $resource]"/>
      <xsl:if test="not($item)">
        <xsl:message>
          <xsl:text>RSS Warning: there is no item labelled: </xsl:text>
          <xsl:value-of select="$resource"/>
        </xsl:message>
      </xsl:if>
      <xsl:if test="count($item) &gt; 1">
        <xsl:message>
          <xsl:text>RSS Warning: there is more than one item labelled: </xsl:text>
          <xsl:value-of select="$resource"/>
        </xsl:message>
      </xsl:if>
      <xsl:apply-templates select="$item"/>
    </xsl:for-each>
  </dl>
</xsl:template>

<xsl:template match="rss:item">
  <xsl:message>RSS item: <xsl:value-of select="rss:title"/></xsl:message>

  <xsl:apply-templates select="rss:title">
    <xsl:with-param name="wrapper" select="'dt'"/>
  </xsl:apply-templates>
  <xsl:if test="rss:description">
    <dd>
      <xsl:apply-templates select="rss:description"/>
    </dd>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
