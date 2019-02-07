<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                xmlns="http://docbook.org/ns/docbook"
		version="1.0"
                exclude-result-prefixes="exsl">

<!-- ********************************************************************
     $Id: topic-maker-chunk.xsl,v 1.7 2012-04-16 00:29:35 bobs Exp $
     ********************************************************************
-->

<xsl:import href="topic-maker.xsl"/>

<xsl:import href="../xhtml/chunk-common.xsl"/>

<xsl:include href="../xhtml/chunk-code.xsl"/>

<xsl:param name="root.id.suffix">-info</xsl:param>
<xsl:param name="root.as.resourceref" select="1"/>

<xsl:template match="/" priority="1">
  <xsl:apply-templates select="/" mode="process.root"/>

  <xsl:call-template name="make.assembly"/>

</xsl:template>

<xsl:template name="chunk-element-content">
  <xsl:param name="content">
    <xsl:apply-imports/>
  </xsl:param>

  <xsl:copy-of select="$content"/>
</xsl:template>

<xsl:template name="make.assembly">
  <xsl:variable name="content">
    <assembly xmlns:xlink="http://www.w3.org/1999/xlink">
      <xsl:call-template name="make.resources"/>
      <xsl:call-template name="make.structure"/>
    </assembly>
  </xsl:variable>

  <xsl:variable name="filename">
    <xsl:if test="$manifest.in.base.dir != 0">
      <xsl:value-of select="$base.dir"/>
    </xsl:if>
    <xsl:value-of select="$assembly.filename"/>
  </xsl:variable>

  <xsl:call-template name="write.chunk">
    <xsl:with-param name="content" select="$content"/>
    <xsl:with-param name="filename" select="$filename"/>
    <xsl:with-param name="indent">yes</xsl:with-param>
  </xsl:call-template>

</xsl:template>

<xsl:template name="make.structure">
  <xsl:param name="root" select="/*[1]"/>

  <xsl:param name="root.resourceref">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$root"/>
    </xsl:call-template>
  </xsl:param>

  <xsl:choose>
    <xsl:when test="$root.as.resourceref = 0">
      <structure>
        <xsl:attribute name="type">
          <xsl:value-of select="local-name($root)"/>
        </xsl:attribute>
        <xsl:attribute name="xml:id">
          <xsl:value-of select="$root.resourceref"/>
        </xsl:attribute>
    
        <xsl:copy-of select="($root/title | $root/info/title)[1]"/>
    
        <!-- Put the title and info stuff in a content-only module -->
        <module resourceref="{$root.resourceref}{$root.id.suffix}" contentonly="true"/>
        <xsl:apply-templates select="$root/*" mode="module.list"/>
      </structure>
    </xsl:when>
    <xsl:otherwise>
      <structure>
        <xsl:attribute name="resourceref">
          <xsl:value-of select="$root.resourceref"/>
        </xsl:attribute>

        <output renderas="{local-name($root)}"/>

        <xsl:apply-templates select="$root/*" mode="module.list"/>
      </structure>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*" mode="module.list">
  <xsl:variable name="is.chunk">
    <xsl:call-template name="chunk">
      <xsl:with-param name="node" select="."/>
    </xsl:call-template>
  </xsl:variable>

  <!-- generate an output element for renderas? -->
  <xsl:variable name="src.element" select="concat(' ', local-name(.), ' ')"/>

  <xsl:variable name="is.topic">
    <xsl:choose>
      <xsl:when test="contains($topic.list, $src.element)">1</xsl:when>
      <xsl:otherwise>0</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="$is.chunk = 1">
    <module>
      <xsl:attribute name="resourceref">
        <xsl:call-template name="object.id"/>
      </xsl:attribute>

      <xsl:if test="$is.topic = 1">
        <output renderas="{local-name()}"/>
      </xsl:if>

      <xsl:apply-templates select="*" mode="module.list"/>
    </module>
  </xsl:if>
</xsl:template>

<xsl:template name="make.resources">
  <resources>
    <!-- Add xml:base from $base.dir if manifest not in base.dir -->
    <xsl:if test="string-length($base.dir) != 0 and
                  $manifest.in.base.dir = 0">
      <xsl:attribute name="xml:base">
        <!-- strip off trailing slash for xml:base -->
        <xsl:choose>
          <xsl:when test="substring($base.dir, string-length($base.dir),1) = '/'">
            <xsl:value-of select="substring($base.dir, 1, string-length($base.dir) -1)"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$base.dir"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:if>
    <xsl:apply-templates select="/*[1]" mode="resource.list"/>
  </resources>
</xsl:template>

<xsl:template match="*" mode="resource.list">
  <xsl:variable name="is.chunk">
    <xsl:call-template name="chunk">
      <xsl:with-param name="node" select="."/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$is.chunk = 1">
    <resource>
      <xsl:attribute name="fileref">
        <!--
        <xsl:if test="$manifest.in.base.dir = 0">
          <xsl:value-of select="$base.dir"/>
        </xsl:if>
        -->
        <xsl:apply-templates select="." mode="chunk-filename"/>
      </xsl:attribute>

      <xsl:attribute name="xml:id">
        <xsl:call-template name="object.id"/>
      </xsl:attribute>

      <xsl:variable name="title">
        <xsl:apply-templates select="." mode="title.markup"/>
      </xsl:variable>
      <xsl:if test="string-length($title) != 0">
        <description>
          <xsl:value-of select="$title"/>
        </description>
      </xsl:if>

    </resource>
  </xsl:if>

  <xsl:apply-templates select="*" mode="resource.list"/>
</xsl:template>

<!-- special case for root id on structure element -->
<xsl:template match="/*" mode="resource.list">
  <xsl:param name="root.resourceref">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="."/>
    </xsl:call-template>
  </xsl:param>

  <resource>
    <xsl:attribute name="fileref">
      <xsl:apply-templates select="." mode="chunk-filename"/>
    </xsl:attribute>
    <xsl:attribute name="xml:id">
      <xsl:choose>
        <xsl:when test="$root.as.resourceref = 0">
          <xsl:value-of select="concat($root.resourceref,$root.id.suffix)"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$root.resourceref"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:attribute>
  </resource>
  <xsl:apply-templates select="*" mode="resource.list"/>
</xsl:template>

<!-- This one must be here because of the template in chunk-code.xsl -->
<xsl:template match="@fileref" priority="1">
    <xsl:copy-of select="."/>
</xsl:template>

</xsl:stylesheet>
