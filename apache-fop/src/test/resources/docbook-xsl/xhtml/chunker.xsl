<?xml version="1.0" encoding="ASCII"?>
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://icl.com/saxon" xmlns:lxslt="http://xml.apache.org/xslt" xmlns:redirect="http://xml.apache.org/xalan/redirect" xmlns:exsl="http://exslt.org/common" xmlns:doc="http://nwalsh.com/xsl/documentation/1.0" xmlns="http://www.w3.org/1999/xhtml" version="1.0" exclude-result-prefixes="saxon lxslt redirect exsl doc" extension-element-prefixes="saxon redirect lxslt exsl">

<!-- ********************************************************************
     $Id: chunker.xsl 9656 2012-10-29 18:09:53Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<!-- This stylesheet works with XSLT implementations that support -->
<!-- exsl:document, saxon:output, or Xalan's redirect:write -->
<!-- Note: Only Saxon 6.4.2 or later is supported. -->

<xsl:param name="chunker.output.method" select="'xml'"/>
<xsl:param name="chunker.output.encoding" select="'UTF-8'"/>
<xsl:param name="chunker.output.indent" select="'no'"/>
<xsl:param name="chunker.output.omit-xml-declaration" select="'no'"/>
<xsl:param name="chunker.output.standalone" select="'no'"/>
<xsl:param name="chunker.output.doctype-public" select="'-//W3C//DTD XHTML 1.0 Transitional//EN'"/>
<xsl:param name="chunker.output.doctype-system" select="'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'"/>
<xsl:param name="chunker.output.media-type" select="''"/>
<xsl:param name="chunker.output.cdata-section-elements" select="''"/>

<!-- Make sure base.dir has a trailing slash now -->
<xsl:param name="chunk.base.dir">
  <xsl:choose>
    <xsl:when test="string-length($base.dir) = 0"/>
    <!-- make sure to add trailing slash if omitted by user -->
    <xsl:when test="substring($base.dir, string-length($base.dir), 1) = '/'">
      <xsl:value-of select="$base.dir"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="concat($base.dir, '/')"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:param name="saxon.character.representation" select="'entity;decimal'"/>

<!-- ==================================================================== -->

<xsl:template name="make-relative-filename">
  <xsl:param name="base.dir" select="'./'"/>
  <xsl:param name="base.name" select="''"/>

  <xsl:choose>
    <!-- put Saxon first to work around a bug in libxslt -->
    <xsl:when test="element-available('saxon:output')">
      <!-- Saxon doesn't make the chunks relative -->
      <xsl:value-of select="concat($base.dir,$base.name)"/>
    </xsl:when>
    <xsl:when test="element-available('exsl:document')">
      <!-- EXSL document does make the chunks relative, I think -->
      <xsl:choose>
        <xsl:when test="count(parent::*) = 0">
          <xsl:value-of select="concat($base.dir,$base.name)"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$base.name"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="element-available('redirect:write')">
      <!-- Xalan doesn't make the chunks relative -->
      <xsl:value-of select="concat($base.dir,$base.name)"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message terminate="yes">
        <xsl:text>Don't know how to chunk with </xsl:text>
        <xsl:value-of select="system-property('xsl:vendor')"/>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="write.chunk">
  <xsl:param name="filename" select="''"/>
  <xsl:param name="quiet" select="$chunk.quietly"/>
  <xsl:param name="suppress-context-node-name" select="0"/>
  <xsl:param name="message-prolog"/>
  <xsl:param name="message-epilog"/>

  <xsl:param name="method" select="$chunker.output.method"/>
  <xsl:param name="encoding" select="$chunker.output.encoding"/>
  <xsl:param name="indent" select="$chunker.output.indent"/>
  <xsl:param name="omit-xml-declaration" select="$chunker.output.omit-xml-declaration"/>
  <xsl:param name="standalone" select="$chunker.output.standalone"/>
  <xsl:param name="doctype-public" select="$chunker.output.doctype-public"/>
  <xsl:param name="doctype-system" select="$chunker.output.doctype-system"/>
  <xsl:param name="media-type" select="$chunker.output.media-type"/>
  <xsl:param name="cdata-section-elements" select="$chunker.output.cdata-section-elements"/>

  <xsl:param name="content"/>

  <xsl:if test="$quiet = 0">
    <xsl:message>
      <xsl:if test="not($message-prolog = '')">
        <xsl:value-of select="$message-prolog"/>
      </xsl:if>
      <xsl:text>Writing </xsl:text>
      <xsl:value-of select="$filename"/>
      <xsl:if test="name(.) != '' and $suppress-context-node-name = 0">
        <xsl:text> for </xsl:text>
        <xsl:value-of select="name(.)"/>
        <xsl:if test="@id or @xml:id">
          <xsl:text>(</xsl:text>
          <xsl:value-of select="(@id|@xml:id)[1]"/>
          <xsl:text>)</xsl:text>
        </xsl:if>
      </xsl:if>
      <xsl:if test="not($message-epilog = '')">
        <xsl:value-of select="$message-epilog"/>
      </xsl:if>
    </xsl:message>
  </xsl:if>

  <xsl:choose>
    <xsl:when test="element-available('exsl:document')">
      <xsl:choose>
        <!-- Handle the permutations ... -->
        <xsl:when test="$media-type != ''">
          <xsl:choose>
            <xsl:when test="$doctype-public != '' and $doctype-system != ''">
              <exsl:document href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" media-type="{$media-type}" doctype-public="{$doctype-public}" doctype-system="{$doctype-system}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </exsl:document>
            </xsl:when>
            <xsl:when test="$doctype-public != '' and $doctype-system = ''">
              <exsl:document href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" media-type="{$media-type}" doctype-public="{$doctype-public}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </exsl:document>
            </xsl:when>
            <xsl:when test="$doctype-public = '' and $doctype-system != ''">
              <exsl:document href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" media-type="{$media-type}" doctype-system="{$doctype-system}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </exsl:document>
            </xsl:when>
            <xsl:otherwise><!-- $doctype-public = '' and $doctype-system = ''"> -->
              <exsl:document href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" media-type="{$media-type}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </exsl:document>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
          <xsl:choose>
            <xsl:when test="$doctype-public != '' and $doctype-system != ''">
              <exsl:document href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" doctype-public="{$doctype-public}" doctype-system="{$doctype-system}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </exsl:document>
            </xsl:when>
            <xsl:when test="$doctype-public != '' and $doctype-system = ''">
              <exsl:document href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" doctype-public="{$doctype-public}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </exsl:document>
            </xsl:when>
            <xsl:when test="$doctype-public = '' and $doctype-system != ''">
              <exsl:document href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" doctype-system="{$doctype-system}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </exsl:document>
            </xsl:when>
            <xsl:otherwise><!-- $doctype-public = '' and $doctype-system = ''"> -->
              <exsl:document href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </exsl:document>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <xsl:when test="element-available('saxon:output')">
      <xsl:choose>
        <!-- Handle the permutations ... -->
        <xsl:when test="$media-type != ''">
          <xsl:choose>
            <xsl:when test="$doctype-public != '' and $doctype-system != ''">
              <saxon:output saxon:character-representation="{$saxon.character.representation}" href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" media-type="{$media-type}" doctype-public="{$doctype-public}" doctype-system="{$doctype-system}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </saxon:output>
            </xsl:when>
            <xsl:when test="$doctype-public != '' and $doctype-system = ''">
              <saxon:output saxon:character-representation="{$saxon.character.representation}" href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" media-type="{$media-type}" doctype-public="{$doctype-public}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </saxon:output>
            </xsl:when>
            <xsl:when test="$doctype-public = '' and $doctype-system != ''">
              <saxon:output saxon:character-representation="{$saxon.character.representation}" href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" media-type="{$media-type}" doctype-system="{$doctype-system}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </saxon:output>
            </xsl:when>
            <xsl:otherwise><!-- $doctype-public = '' and $doctype-system = ''"> -->
              <saxon:output saxon:character-representation="{$saxon.character.representation}" href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" media-type="{$media-type}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </saxon:output>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
          <xsl:choose>
            <xsl:when test="$doctype-public != '' and $doctype-system != ''">
              <saxon:output saxon:character-representation="{$saxon.character.representation}" href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" doctype-public="{$doctype-public}" doctype-system="{$doctype-system}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </saxon:output>
            </xsl:when>
            <xsl:when test="$doctype-public != '' and $doctype-system = ''">
              <saxon:output saxon:character-representation="{$saxon.character.representation}" href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" doctype-public="{$doctype-public}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </saxon:output>
            </xsl:when>
            <xsl:when test="$doctype-public = '' and $doctype-system != ''">
              <saxon:output saxon:character-representation="{$saxon.character.representation}" href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" doctype-system="{$doctype-system}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </saxon:output>
            </xsl:when>
            <xsl:otherwise><!-- $doctype-public = '' and $doctype-system = ''"> -->
              <saxon:output saxon:character-representation="{$saxon.character.representation}" href="{$filename}" method="{$method}" encoding="{$encoding}" indent="{$indent}" omit-xml-declaration="{$omit-xml-declaration}" cdata-section-elements="{$cdata-section-elements}" standalone="{$standalone}">
                <xsl:copy-of select="$content"/>
              </saxon:output>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <xsl:when test="element-available('redirect:write')">
      <!-- Xalan uses redirect -->
      <redirect:write file="{$filename}">
        <xsl:copy-of select="$content"/>
      </redirect:write>
    </xsl:when>

    <xsl:otherwise>
      <!-- it doesn't matter since we won't be making chunks... -->
      <xsl:message terminate="yes">
        <xsl:text>Can't make chunks with </xsl:text>
        <xsl:value-of select="system-property('xsl:vendor')"/>
        <xsl:text>'s processor.</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="write.chunk.with.doctype">
  <xsl:param name="filename" select="''"/>
  <xsl:param name="quiet" select="$chunk.quietly"/>

  <xsl:param name="method" select="$chunker.output.method"/>
  <xsl:param name="encoding" select="$chunker.output.encoding"/>
  <xsl:param name="indent" select="$chunker.output.indent"/>
  <xsl:param name="omit-xml-declaration" select="$chunker.output.omit-xml-declaration"/>
  <xsl:param name="standalone" select="$chunker.output.standalone"/>
  <xsl:param name="doctype-public" select="$chunker.output.doctype-public"/>
  <xsl:param name="doctype-system" select="$chunker.output.doctype-system"/>
  <xsl:param name="media-type" select="$chunker.output.media-type"/>
  <xsl:param name="cdata-section-elements" select="$chunker.output.cdata-section-elements"/>

  <xsl:param name="content"/>

  <xsl:call-template name="write.chunk">
    <xsl:with-param name="filename" select="$filename"/>
    <xsl:with-param name="quiet" select="$quiet"/>
    <xsl:with-param name="method" select="$method"/>
    <xsl:with-param name="encoding" select="$encoding"/>
    <xsl:with-param name="indent" select="$indent"/>
    <xsl:with-param name="omit-xml-declaration" select="$omit-xml-declaration"/>
    <xsl:with-param name="standalone" select="$standalone"/>
    <xsl:with-param name="doctype-public" select="$doctype-public"/>
    <xsl:with-param name="doctype-system" select="$doctype-system"/>
    <xsl:with-param name="media-type" select="$media-type"/>
    <xsl:with-param name="cdata-section-elements" select="$cdata-section-elements"/>
    <xsl:with-param name="content" select="$content"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="write.text.chunk">
  <xsl:param name="filename" select="''"/>
  <xsl:param name="quiet" select="$chunk.quietly"/>
  <xsl:param name="suppress-context-node-name" select="0"/>
  <xsl:param name="message-prolog"/>
  <xsl:param name="message-epilog"/>
  <xsl:param name="method" select="'text'"/>
  <xsl:param name="encoding" select="$chunker.output.encoding"/>
  <xsl:param name="media-type" select="$chunker.output.media-type"/>
  <xsl:param name="content"/>

  <xsl:call-template name="write.chunk">
    <xsl:with-param name="filename" select="$filename"/>
    <xsl:with-param name="quiet" select="$quiet"/>
    <xsl:with-param name="suppress-context-node-name" select="$suppress-context-node-name"/>
    <xsl:with-param name="message-prolog" select="$message-prolog"/>
    <xsl:with-param name="message-epilog" select="$message-epilog"/>
    <xsl:with-param name="method" select="$method"/>
    <xsl:with-param name="encoding" select="$encoding"/>
    <xsl:with-param name="indent" select="'no'"/>
    <xsl:with-param name="omit-xml-declaration" select="'yes'"/>
    <xsl:with-param name="standalone" select="'no'"/>
    <xsl:with-param name="doctype-public"/>
    <xsl:with-param name="doctype-system"/>
    <xsl:with-param name="media-type" select="$media-type"/>
    <xsl:with-param name="cdata-section-elements"/>
    <xsl:with-param name="content" select="$content"/>
  </xsl:call-template>
</xsl:template>


</xsl:stylesheet>
