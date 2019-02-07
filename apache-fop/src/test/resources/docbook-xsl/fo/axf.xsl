<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:axf="http://www.antennahouse.com/names/XSL/Extensions"
                version='1.0'>

<!-- ********************************************************************
     $Id: axf.xsl 8983 2011-03-27 07:41:25Z mzjn $
     ******************************************************************** -->

<xsl:template name="axf-document-information">

    <xsl:variable name="authors" select="(//author|//editor|
                                          //corpauthor|//authorgroup)[1]"/>
    <xsl:if test="$authors">
      <xsl:variable name="author">
        <xsl:choose>
          <xsl:when test="$authors[self::authorgroup]">
            <xsl:call-template name="person.name.list">
              <xsl:with-param name="person.list" 
                 select="$authors/*[self::author|self::corpauthor|
                               self::othercredit|self::editor]"/>
            </xsl:call-template>
          </xsl:when>
          <xsl:when test="$authors[self::corpauthor]">
            <xsl:value-of select="$authors"/>
          </xsl:when>
           <xsl:when test="$authors[orgname]">
            <xsl:value-of select="$authors/orgname"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name="person.name">
              <xsl:with-param name="node" select="$authors"/>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:element name="axf:document-info">
        <xsl:attribute name="name">author</xsl:attribute>
        <xsl:attribute name="value">
          <xsl:value-of select="normalize-space($author)"/>
        </xsl:attribute>
      </xsl:element>
    </xsl:if>

    <xsl:variable name="title">
      <xsl:apply-templates select="/*[1]" mode="label.markup"/>
      <xsl:apply-templates select="/*[1]" mode="title.markup"/>
    </xsl:variable>

    <!-- * see bug report #1465301 - mzjn -->
    <axf:document-info name="title">
      <xsl:attribute name="value">
        <xsl:value-of select="normalize-space($title)"/>
      </xsl:attribute>
    </axf:document-info>

    <xsl:if test="//keyword">
      <xsl:element name="axf:document-info">
        <xsl:attribute name="name">keywords</xsl:attribute>
        <xsl:attribute name="value">
          <xsl:for-each select="//keyword">
            <xsl:value-of select="normalize-space(.)"/>
            <xsl:if test="position() != last()">
              <xsl:text>, </xsl:text>
            </xsl:if>
          </xsl:for-each>
        </xsl:attribute>
      </xsl:element>
    </xsl:if>

    <xsl:if test="//subjectterm">
      <xsl:element name="axf:document-info">
        <xsl:attribute name="name">subject</xsl:attribute>
        <xsl:attribute name="value">
          <xsl:for-each select="//subjectterm">
            <xsl:value-of select="normalize-space(.)"/>
            <xsl:if test="position() != last()">
              <xsl:text>, </xsl:text>
            </xsl:if>
          </xsl:for-each>
        </xsl:attribute>
      </xsl:element>
    </xsl:if>

</xsl:template>

<!-- These properties are added to fo:simple-page-master -->
<xsl:template name="axf-page-master-properties">
  <xsl:param name="page.master" select="''"/>

  <xsl:if test="$crop.marks != 0">
    <xsl:attribute name="axf:printer-marks">crop</xsl:attribute>
    <xsl:attribute name="axf:bleed"><xsl:value-of
                          select="$crop.mark.bleed"/></xsl:attribute>
    <xsl:attribute name="axf:printer-marks-line-width"><xsl:value-of
                          select="$crop.mark.width"/></xsl:attribute>
    <xsl:attribute name="axf:crop-offset"><xsl:value-of
                          select="$crop.mark.offset"/></xsl:attribute>
  </xsl:if>

  <xsl:call-template name="user-axf-page-master-properties">
    <xsl:with-param name="page.master" select="$page.master"/>
  </xsl:call-template>

</xsl:template>

<xsl:template name="user-axf-page-master-properties">
  <xsl:param name="page.master" select="''"/>
</xsl:template>

</xsl:stylesheet>
