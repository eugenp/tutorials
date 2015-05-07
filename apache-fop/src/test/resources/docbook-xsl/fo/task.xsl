<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0">

<!-- ********************************************************************
     $Id: task.xsl 9122 2011-10-09 07:21:49Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="task">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="param.placement"
                select="substring-after(normalize-space($formal.title.placement),
                                        concat(local-name(.), ' '))"/>

  <xsl:variable name="placement">
    <xsl:choose>
      <xsl:when test="contains($param.placement, ' ')">
        <xsl:value-of select="substring-before($param.placement, ' ')"/>
      </xsl:when>
      <xsl:when test="$param.placement = ''">before</xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$param.placement"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="preamble"
                select="*[not(self::title
                              or self::titleabbrev)]"/>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <fo:block id="{$id}"
            xsl:use-attribute-sets="task.properties">

    <xsl:if test="$keep.together != ''">
      <xsl:attribute name="keep-together.within-column"><xsl:value-of
      select="$keep.together"/></xsl:attribute>
    </xsl:if>

    <xsl:call-template name="anchor"/>

    <xsl:if test="title and $placement = 'before'">
      <xsl:call-template name="formal.object.heading"/>
    </xsl:if>

    <xsl:apply-templates select="$preamble"/>

    <xsl:if test="title and $placement != 'before'">
      <xsl:call-template name="formal.object.heading"/>
    </xsl:if>
  </fo:block>
</xsl:template>

<xsl:template match="task/title">
  <!-- nop -->
</xsl:template>

<xsl:template match="tasksummary">
  <xsl:call-template name="semiformal.object"/>
</xsl:template>

<xsl:template match="tasksummary/title"/>

<xsl:template match="taskprerequisites">
  <xsl:call-template name="semiformal.object"/>
</xsl:template>

<xsl:template match="taskprerequisites/title"/>

<xsl:template match="taskrelated">
  <xsl:call-template name="semiformal.object"/>
</xsl:template>

<xsl:template match="taskrelated/title"/>

</xsl:stylesheet>
