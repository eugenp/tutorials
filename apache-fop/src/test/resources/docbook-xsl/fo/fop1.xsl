<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fox="http://xmlgraphics.apache.org/fop/extensions"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version='1.0'>

<!-- ********************************************************************
     $Id: fop1.xsl 9293 2012-04-19 18:42:11Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:variable name="bookmarks.state">
  <xsl:choose>
    <xsl:when test="$bookmarks.collapse != 0">hide</xsl:when>
    <xsl:otherwise>show</xsl:otherwise>
  </xsl:choose>
</xsl:variable>

<xsl:template match="*" mode="fop1.outline">
  <xsl:apply-templates select="*" mode="fop1.outline"/>
</xsl:template>

<xsl:template match="set|book|part|reference|
                     preface|chapter|appendix|article|topic
                     |glossary|bibliography|index|setindex
                     |refentry
                     |sect1|sect2|sect3|sect4|sect5|section"
              mode="fop1.outline">

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <xsl:variable name="bookmark-label">
    <xsl:apply-templates select="." mode="object.title.markup"/>
  </xsl:variable>

  <!-- Put the root element bookmark at the same level as its children -->
  <!-- If the object is a set or book, generate a bookmark for the toc -->

  <xsl:choose>
    <xsl:when test="self::index and $generate.index = 0"/>	
    <xsl:when test="parent::*">
      <fo:bookmark internal-destination="{$id}">
	<xsl:attribute name="starting-state">
	  <xsl:value-of select="$bookmarks.state"/>
	</xsl:attribute>
        <fo:bookmark-title>
          <xsl:value-of select="normalize-space($bookmark-label)"/>
        </fo:bookmark-title>
        <xsl:apply-templates select="*" mode="fop1.outline"/>
      </fo:bookmark>
    </xsl:when>
    <xsl:otherwise>
      <fo:bookmark internal-destination="{$id}">
	<xsl:attribute name="starting-state">
	  <xsl:value-of select="$bookmarks.state"/>
	</xsl:attribute>
        <fo:bookmark-title>
          <xsl:value-of select="normalize-space($bookmark-label)"/>
        </fo:bookmark-title>
      </fo:bookmark>

      <xsl:variable name="toc.params">
        <xsl:call-template name="find.path.params">
          <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:if test="contains($toc.params, 'toc')
                    and (book|part|reference|preface|chapter|appendix|article|topic
                         |glossary|bibliography|index|setindex
                         |refentry
                         |sect1|sect2|sect3|sect4|sect5|section)">
        <fo:bookmark internal-destination="toc...{$id}">
          <fo:bookmark-title>
            <xsl:call-template name="gentext">
              <xsl:with-param name="key" select="'TableofContents'"/>
            </xsl:call-template>
          </fo:bookmark-title>
        </fo:bookmark>
      </xsl:if>
      <xsl:apply-templates select="*" mode="fop1.outline"/>
    </xsl:otherwise>
  </xsl:choose>
<!--
  <fo:bookmark internal-destination="{$id}"/>
-->
</xsl:template>

<xsl:template match="*" mode="fop1.foxdest">
  <xsl:apply-templates select="*" mode="fop1.foxdest"/>
</xsl:template>

<xsl:template match="set|book|part|reference|
                     preface|chapter|appendix|article|topic
                     |glossary|bibliography|index|setindex
                     |refentry
                     |sect1|sect2|sect3|sect4|sect5|section"
              mode="fop1.foxdest">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <xsl:variable name="bookmark-label">
    <xsl:apply-templates select="." mode="object.title.markup"/>
  </xsl:variable>
  <!--xsl:if test="$id != ''">
    <fox:destination internal-destination="{$id}"/>
  </xsl:if-->

  <!-- Put the root element bookmark at the same level as its children -->
  <!-- If the object is a set or book, generate a bookmark for the toc -->

  <xsl:choose>
    <xsl:when test="self::index and $generate.index = 0"/>	
    <xsl:when test="parent::*">
      <fox:destination internal-destination="{$id}"/>
        <xsl:apply-templates select="*" mode="fop1.foxdest"/>
    </xsl:when>
    <xsl:otherwise>
      <fox:destination internal-destination="{$id}"/>
      <xsl:apply-templates select="*" mode="fop1.foxdest"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<!-- Metadata support ("Document Properties" in Adobe Reader) -->
<xsl:template name="fop1-document-information">
  <xsl:variable name="authors" select="(//author|//editor|//corpauthor|//authorgroup)[1]"/>

  <xsl:variable name="title">
    <xsl:apply-templates select="/*[1]" mode="label.markup"/>
    <xsl:apply-templates select="/*[1]" mode="title.markup"/>
    <xsl:variable name="subtitle">
      <xsl:apply-templates select="/*[1]" mode="subtitle.markup">
        <xsl:with-param name="verbose" select="0"/>
      </xsl:apply-templates>
    </xsl:variable>
    <xsl:if test="$subtitle !=''">
      <xsl:text> - </xsl:text>
      <xsl:value-of select="$subtitle"/>
    </xsl:if>
  </xsl:variable>

  <fo:declarations>
    <x:xmpmeta xmlns:x="adobe:ns:meta/">
      <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
        <rdf:Description rdf:about="" xmlns:dc="http://purl.org/dc/elements/1.1/">
          <!-- Dublin Core properties go here -->

          <!-- Title -->
          <dc:title><xsl:value-of select="normalize-space($title)"/></dc:title>

          <!-- Author -->
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

            <dc:creator><xsl:value-of select="normalize-space($author)"/></dc:creator>
          </xsl:if>

          <!-- Subject -->
          <xsl:if test="//subjectterm">
            <dc:description>
              <xsl:for-each select="//subjectterm">
                <xsl:value-of select="normalize-space(.)"/>
                <xsl:if test="position() != last()">
                  <xsl:text>, </xsl:text>
                </xsl:if>
              </xsl:for-each>
            </dc:description>
          </xsl:if>
        </rdf:Description>

        <rdf:Description rdf:about="" xmlns:pdf="http://ns.adobe.com/pdf/1.3/">
          <!-- PDF properties go here -->

          <!-- Keywords -->
          <xsl:if test="//keyword">
            <pdf:Keywords>
              <xsl:for-each select="//keyword">
                <xsl:value-of select="normalize-space(.)"/>
                <xsl:if test="position() != last()">
                  <xsl:text>, </xsl:text>
                </xsl:if>
              </xsl:for-each>
            </pdf:Keywords>
          </xsl:if>
        </rdf:Description>

        <rdf:Description rdf:about="" xmlns:xmp="http://ns.adobe.com/xap/1.0/">
          <!-- XMP properties go here -->

          <!-- Creator Tool -->
          <xmp:CreatorTool>DocBook XSL Stylesheets with Apache FOP</xmp:CreatorTool>
        </rdf:Description>

      </rdf:RDF>
    </x:xmpmeta>
  </fo:declarations>
</xsl:template>

</xsl:stylesheet>
