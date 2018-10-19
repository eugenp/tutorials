<?xml version="1.0" encoding="ASCII"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns="http://docbook.org/ns/docbook"
		xmlns:dbs="http://docbook.org/ns/docbook-slides"
		xmlns:xlink="http://www.w3.org/1999/xlink"
		version="1.0">

<xsl:output method="xml" encoding="utf-8" indent="no"/>

<xsl:preserve-space elements="*"/>

<xsl:template match="/slides">
  <dbs:slides>
    <xsl:call-template name="process.content"/>
  </dbs:slides>
</xsl:template>

<xsl:template name="process.content">
    <xsl:apply-templates select="slidesinfo|foilgroupinfo|foilinfo|title|titleabbrev|subtitle"/>

    <xsl:apply-templates select="speakernotes"/>

    <xsl:apply-templates select="*[not(self::speakernotes)][not(self::title)][not(self::titleabbrev)][not(self::subtitle)][not(self::slidesinfo)][not(self::foilinfo)][not(self::foilgroupinfo)]"/>
</xsl:template>

<xsl:template match="foil">
  <dbs:foil>
    <xsl:call-template name="copy.attributes"/>

    <xsl:call-template name="process.content"/>
  </dbs:foil>
</xsl:template>

<xsl:template match="foilgroup">
  <dbs:foilgroup>
    <xsl:call-template name="copy.attributes"/>

    <xsl:call-template name="process.content"/>
  </dbs:foilgroup>
</xsl:template>

<xsl:template match="speakernotes">
  <dbs:speakernotes>
    <xsl:call-template name="copy.attributes"/>

    <xsl:apply-templates select="*"/>
  </dbs:speakernotes>
</xsl:template>

<xsl:template match="slidesinfo|foilgroupinfo|foilinfo">
  <info>
    <xsl:call-template name="copy.attributes"/>

    <xsl:apply-templates select="*"/>
  </info>
</xsl:template>

<!-- ******************************************************************
        DB4 -> DB5 related, adapted from db4-upgrade.xsl
     ****************************************************************** -->

<xsl:template name="copy.attributes">
  <xsl:param name="suppress" select="''"/>

  <xsl:for-each select="@*">
    <xsl:choose>
      <xsl:when test="local-name(.) = 'lang'">
        <xsl:attribute name="xml:lang">
          <xsl:value-of select="."/>
        </xsl:attribute>
      </xsl:when>
      <xsl:when test="local-name(.) = 'id'">
        <xsl:attribute name="xml:id">
          <xsl:value-of select="."/>
        </xsl:attribute>
      </xsl:when>
      <xsl:when test="local-name(.) = 'moreinfo'"/>
      <xsl:when test="$suppress = local-name(.)"/>
      <xsl:otherwise>
        <xsl:copy/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:for-each>
</xsl:template>

<xsl:template match="*">
  <xsl:element name="{local-name()}" namespace="http://docbook.org/ns/docbook">
    <xsl:call-template name="copy.attributes"/>

    <xsl:apply-templates />
  </xsl:element>
</xsl:template>

<xsl:template match="author[not(personname)]|editor[not(personname)]|othercredit[not(personname)]">
  <xsl:element name="{local-name()}" namespace="http://docbook.org/ns/docbook">
    <xsl:call-template name="copy.attributes"/>

    <personname>
      <xsl:apply-templates select="honorific|firstname|surname|othername|lineage"/>
    </personname>

    <xsl:apply-templates select="*[not(self::honorific|self::firstname|self::surname
                                   |self::othername|self::lineage)]"/>
  </xsl:element>
</xsl:template>

<xsl:template match="address|programlisting|screen|funcsynopsisinfo
                     |classsynopsisinfo|literallayout">
  <xsl:element name="{local-name()}" namespace="http://docbook.org/ns/docbook">
    <xsl:call-template name="copy.attributes">
      <xsl:with-param name="suppress" select="'format'"/>
    </xsl:call-template>
    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template match="inlinegraphic[@format='linespecific']">
  <textobject>
    <textdata>
      <xsl:call-template name="copy.attributes"/>
    </textdata>
  </textobject>
</xsl:template>

<xsl:template match="inlinegraphic">
  <inlinemediaobject>
    <imageobject>
      <imagedata>
        <xsl:call-template name="copy.attributes"/>
      </imagedata>
    </imageobject>
  </inlinemediaobject>
</xsl:template>

<xsl:template match="graphic[@format='linespecific']">
  <mediaobject>
    <textobject>
      <textdata>
        <xsl:call-template name="copy.attributes"/>
      </textdata>
    </textobject>
  </mediaobject>
</xsl:template>

<xsl:template match="graphic">
  <mediaobject>
    <imageobject>
      <imagedata>
        <xsl:call-template name="copy.attributes"/>
      </imagedata>
    </imageobject>
  </mediaobject>
</xsl:template>

<xsl:template match="ulink" priority="200">
  <xsl:choose>
    <xsl:when test="node()">
      <link xlink:href="{@url}">
        <xsl:call-template name="copy.attributes">
          <xsl:with-param name="suppress" select="'url'"/>
        </xsl:call-template>
        <xsl:apply-templates/>
      </link>
    </xsl:when>
    <xsl:otherwise>
      <uri xlink:href="{@url}">
        <xsl:call-template name="copy.attributes">
          <xsl:with-param name="suppress" select="'url'"/>
        </xsl:call-template>
        <xsl:value-of select="@url"/>
      </uri>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*[namespace-uri()]">
  <xsl:copy-of select="."/>
</xsl:template>

</xsl:stylesheet>
