<?xml version='1.0'?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY lowercase "'abcdefghijklmnopqrstuvwxyz'">
<!ENTITY uppercase "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'">
 ]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                xmlns:stext="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.TextFactory"
                xmlns:xtext="com.nwalsh.xalan.Text"
                xmlns:lxslt="http://xml.apache.org/xslt"
                exclude-result-prefixes="xlink stext xtext lxslt"
                extension-element-prefixes="stext xtext"
                version='1.0'>

<!-- ********************************************************************
     $Id: graphics.xsl 9647 2012-10-26 17:42:03Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     Contributors:
     Colin Paul Adams, <colin@colina.demon.co.uk>
     Paul Grosso, <pgrosso@arbortext.com>

     ******************************************************************** -->

<!-- ==================================================================== -->
<!-- Graphic format tests for the FO backend -->

<xsl:param name="graphic.notations">
  <!-- n.b. exactly one leading space, one trailing space, and one inter-word space -->
  <xsl:choose>
    <xsl:when test="$fop1.extensions != 0">
      <xsl:text> BMP GIF TIFF SVG PNG EPS JPG JPEG linespecific </xsl:text>
    </xsl:when>
    <xsl:when test="$fop.extensions != 0">
      <xsl:text> BMP GIF TIFF SVG PNG EPS JPG JPEG linespecific </xsl:text>
    </xsl:when>
    <xsl:when test="$arbortext.extensions != 0">
      <xsl:text> PNG PDF JPG JPEG linespecific GIF GIF87a GIF89a TIFF BMP </xsl:text>
    </xsl:when>
    <xsl:when test="$xep.extensions != 0">
      <xsl:text> SVG PNG PDF JPG JPEG linespecific GIF GIF87a GIF89a TIFF BMP </xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text> PNG PDF JPG JPEG linespecific GIF GIF87a GIF89a TIFF BMP </xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:template name="is.graphic.format">
  <xsl:param name="format"/>
  <xsl:if test="contains($graphic.notations, concat(' ',$format,' '))">1</xsl:if>
</xsl:template>

<xsl:param name="graphic.extensions">
  <!-- n.b. exactly one leading space, one trailing space, and one inter-word space -->
  <xsl:choose>
    <xsl:when test="$fop1.extensions != 0">
      <xsl:text> bmp gif tif tiff svg png pdf jpg jpeg eps </xsl:text>
    </xsl:when>
    <xsl:when test="$fop.extensions != 0">
      <xsl:text> bmp gif tif tiff svg png pdf jpg jpeg eps </xsl:text>
    </xsl:when>
    <xsl:when test="$arbortext.extensions != 0">
      <xsl:text> png pdf jpg jpeg gif tif tiff bmp </xsl:text>
    </xsl:when>
    <xsl:when test="$xep.extensions != 0">
      <xsl:text> svg png pdf jpg jpeg gif tif tiff bmp eps </xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text> svg png pdf jpg jpeg gif tif tiff bmp eps </xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:template name="is.graphic.extension">
  <xsl:param name="ext"/>
  <xsl:variable name="lcext" select="translate($ext,
                                       'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
                                       'abcdefghijklmnopqrstuvwxyz')"/>

  <xsl:if test="contains($graphic.extensions,
                         concat(' ', $lcext, ' '))">1</xsl:if>
</xsl:template>


<!-- ==================================================================== -->

<xsl:template match="screenshot">
  <fo:block>
    <xsl:call-template name="anchor"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="screenshot/title">
  <xsl:call-template name="formal.object.heading">
    <xsl:with-param name="object" select=".."/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="screeninfo">
</xsl:template>

<!-- ==================================================================== -->
<!-- Override these templates for FO -->
<!-- ==================================================================== -->

<xsl:template name="image.scalefit">
  <xsl:choose>
    <xsl:when test="$ignore.image.scaling != 0">0</xsl:when>
    <xsl:when test="@contentwidth">0</xsl:when>
    <xsl:when test="@contentdepth and 
                    @contentdepth != '100%'">0</xsl:when>
    <xsl:when test="@scale">0</xsl:when>
    <xsl:when test="@scalefit"><xsl:value-of select="@scalefit"/></xsl:when>
    <xsl:when test="@width or @depth">1</xsl:when>
    <xsl:when test="$default.image.width != ''">1</xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="image.scale">
  <xsl:choose>
    <xsl:when test="$ignore.image.scaling != 0">0</xsl:when>
    <xsl:when test="@contentwidth or @contentdepth">1.0</xsl:when>
    <xsl:when test="@scale">
      <xsl:value-of select="@scale div 100.0"/>
    </xsl:when>
    <xsl:otherwise>1.0</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="image.filename">
  <xsl:choose>
    <xsl:when test="svg:*" xmlns:svg="http://www.w3.org/2000/svg">
      <!-- no filename for inline SVG content -->
    </xsl:when>
    <xsl:when test="mml:*" xmlns:mml="http://www.w3.org/1998/Math/MathML">
      <!-- no filename for inline MathML content -->
    </xsl:when>
    <xsl:when test="local-name(.) = 'graphic'
                    or local-name(.) = 'inlinegraphic'">
      <!-- handle legacy graphic and inlinegraphic by new template --> 
      <xsl:call-template name="mediaobject.filename">
        <xsl:with-param name="object" select="."/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <!-- imagedata, videodata, audiodata -->
      <xsl:call-template name="mediaobject.filename">
        <xsl:with-param name="object" select=".."/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="image.src">
  <xsl:param name="filename"/>

  <xsl:choose>
    <xsl:when test="svg:*" xmlns:svg="http://www.w3.org/2000/svg">
      <!-- no src attribute for inline SVG content -->
    </xsl:when>
    <xsl:when test="mml:*" xmlns:mml="http://www.w3.org/1998/Math/MathML">
      <!-- no src attribute for inline MathML content -->
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="fo-external-image">
        <xsl:with-param name="filename">
          <xsl:if test="$img.src.path != '' and
                        not(starts-with($filename, '/')) and
                        not(contains($filename, '://'))">
            <xsl:value-of select="$img.src.path"/>
          </xsl:if>
          <xsl:value-of select="$filename"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="image.content.type">
  <xsl:if test="@format">
    <xsl:call-template name="graphic.format.content-type">
      <xsl:with-param name="format" select="@format"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="image.bgcolor">
  <xsl:call-template name="pi.dbfo_background-color">
    <xsl:with-param name="node" select=".."/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="image.width">
  <xsl:choose>
    <xsl:when test="$ignore.image.scaling != 0">auto</xsl:when>
    <xsl:when test="contains(@width,'%')">
      <xsl:value-of select="@width"/>
    </xsl:when>
    <xsl:when test="@width and not(@width = '')">
      <xsl:call-template name="length-spec">
        <xsl:with-param name="length" select="@width"/>
        <xsl:with-param name="default.units" select="'px'"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="not(@depth) and $default.image.width != ''">
      <xsl:call-template name="length-spec">
        <xsl:with-param name="length" select="$default.image.width"/>
        <xsl:with-param name="default.units" select="'px'"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>auto</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="image.height">
  <xsl:choose>
    <xsl:when test="$ignore.image.scaling != 0">auto</xsl:when>
    <xsl:when test="contains(@depth,'%')">
      <xsl:value-of select="@depth"/>
    </xsl:when>
    <xsl:when test="@depth">
      <xsl:call-template name="length-spec">
        <xsl:with-param name="length" select="@depth"/>
        <xsl:with-param name="default.units" select="'px'"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>auto</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="image.content.width">
  <xsl:param name="scalefit" select="0"/>
  <xsl:param name="scale" select="'1.0'"/>

  <xsl:choose>
    <xsl:when test="$ignore.image.scaling != 0">auto</xsl:when>
    <xsl:when test="contains(@contentwidth,'%')">
      <xsl:value-of select="@contentwidth"/>
    </xsl:when>
    <xsl:when test="@contentwidth">
      <xsl:call-template name="length-spec">
        <xsl:with-param name="length" select="@contentwidth"/>
        <xsl:with-param name="default.units" select="'px'"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="number($scale) != 1.0">
      <xsl:value-of select="$scale * 100"/>
      <xsl:text>%</xsl:text>
    </xsl:when>
    <xsl:when test="$scalefit = 1">scale-to-fit</xsl:when>
    <xsl:otherwise>auto</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="image.content.height">
  <xsl:param name="scalefit" select="0"/>
  <xsl:param name="scale" select="0"/>

  <xsl:choose>
    <xsl:when test="$ignore.image.scaling != 0">auto</xsl:when>
    <xsl:when test="contains(@contentdepth,'%')">
      <xsl:value-of select="@contentdepth"/>
    </xsl:when>
    <xsl:when test="@contentdepth">
      <xsl:call-template name="length-spec">
        <xsl:with-param name="length" select="@contentdepth"/>
        <xsl:with-param name="default.units" select="'px'"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="number($scale) != 1.0">
      <xsl:value-of select="$scale * 100"/>
      <xsl:text>%</xsl:text>
    </xsl:when>
    <xsl:when test="$scalefit = 1">scale-to-fit</xsl:when>
    <xsl:otherwise>auto</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="image.align">
  <xsl:value-of select="@align"/>
</xsl:template>

<xsl:template name="image.valign">
  <xsl:if test="@valign">
    <xsl:choose>
      <xsl:when test="ancestor::inlinemediaobject or ancestor-or-self::inlinegraphic">
        <xsl:choose>
          <xsl:when test="@valign = 'top'">baseline</xsl:when>
          <xsl:when test="@valign = 'middle'">central</xsl:when>
          <xsl:when test="@valign = 'bottom'">text-before-edge</xsl:when>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:choose>
          <xsl:when test="@valign = 'top'">before</xsl:when>
          <xsl:when test="@valign = 'middle'">center</xsl:when>
          <xsl:when test="@valign = 'bottom'">after</xsl:when>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>
</xsl:template>

  
<xsl:template name="process.image">
  <!-- When this template is called, the current node should be  -->
  <!-- a graphic, inlinegraphic, imagedata, or videodata. All    -->
  <!-- those elements have the same set of attributes, so we can -->
  <!-- handle them all in one place.                             -->

  <!-- Compute each attribute value with its own customizable template call -->
  <xsl:variable name="scalefit">
    <xsl:call-template name="image.scalefit"/>
  </xsl:variable>

  <xsl:variable name="scale">
    <xsl:call-template name="image.scale"/>
  </xsl:variable>

  <xsl:variable name="filename">
    <xsl:call-template name="image.filename"/>
  </xsl:variable>

  <xsl:variable name="src">
    <xsl:call-template name="image.src">
      <xsl:with-param name="filename" select="$filename"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="content.type">
    <xsl:call-template name="image.content.type"/>
  </xsl:variable>

  <xsl:variable name="bgcolor">
    <xsl:call-template name="image.bgcolor"/>
  </xsl:variable>

  <xsl:variable name="width">
    <xsl:call-template name="image.width"/>
  </xsl:variable>

  <xsl:variable name="height">
    <xsl:call-template name="image.height"/>
  </xsl:variable>

  <xsl:variable name="content.width">
    <xsl:call-template name="image.content.width">
      <xsl:with-param name="scalefit" select="$scalefit"/>
      <xsl:with-param name="scale" select="$scale"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="content.height">
    <xsl:call-template name="image.content.height">
      <xsl:with-param name="scalefit" select="$scalefit"/>
      <xsl:with-param name="scale" select="$scale"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="align">
    <xsl:call-template name="image.align"/>
  </xsl:variable>

  <xsl:variable name="valign">
    <xsl:call-template name="image.valign"/>
  </xsl:variable>

  <xsl:variable name="element.name">
    <xsl:choose>
      <xsl:when test="svg:*" xmlns:svg="http://www.w3.org/2000/svg">
        <xsl:text>fo:instream-foreign-object</xsl:text>
      </xsl:when>
      <xsl:when test="mml:*" xmlns:mml="http://www.w3.org/1998/Math/MathML">
        <xsl:text>fo:instream-foreign-object</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>fo:external-graphic</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:element name="{$element.name}">

    <xsl:if test="$src != ''">
      <xsl:attribute name="src">
        <xsl:value-of select="$src"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$width != ''">
      <xsl:attribute name="width">
        <xsl:value-of select="$width"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$height != ''">
      <xsl:attribute name="height">
        <xsl:value-of select="$height"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$content.width != ''">
      <xsl:attribute name="content-width">
        <xsl:value-of select="$content.width"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$content.height != ''">
      <xsl:attribute name="content-height">
        <xsl:value-of select="$content.height"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$content.type != ''">
      <xsl:attribute name="content-type">
        <xsl:value-of select="concat('content-type:',$content.type)"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$bgcolor != ''">
      <xsl:attribute name="background-color">
        <xsl:value-of select="$bgcolor"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$align != ''">
      <xsl:attribute name="text-align">
        <xsl:value-of select="$align"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$valign != ''">
      <xsl:variable name="att.name">
        <xsl:choose>
          <xsl:when test="ancestor::inlinemediaobject or ancestor-or-self::inlinegraphic">
            <xsl:text>alignment-baseline</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text>display-align</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>
      <xsl:attribute name="{$att.name}">
        <xsl:value-of select="$valign"/>
      </xsl:attribute>
    </xsl:if>

    <!-- copy literal SVG elements to output -->
    <xsl:if test="svg:*" xmlns:svg="http://www.w3.org/2000/svg">
      <xsl:call-template name="process.svg"/>
    </xsl:if>

    <xsl:if test="mml:*" xmlns:mml="http://www.w3.org/1998/Math/MathML">
      <xsl:call-template name="process.mml"/>
    </xsl:if>

  </xsl:element>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="graphic">
  <xsl:choose>
    <xsl:when test="parent::inlineequation">
      <xsl:call-template name="process.image"/>
    </xsl:when>
    <xsl:otherwise>
      <fo:block>
        <xsl:call-template name="anchor"/>
        <xsl:if test="@align">
          <xsl:attribute name="text-align">
            <xsl:value-of select="@align"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:call-template name="process.image"/>
      </fo:block>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="inlinegraphic">
  <xsl:variable name="vendor" select="system-property('xsl:vendor')"/>
  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="@entityref">
        <xsl:value-of select="unparsed-entity-uri(@entityref)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="@fileref"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="@format='linespecific'">
      <xsl:choose>
        <xsl:when test="$use.extensions != '0'
                        and $textinsert.extension != '0'">
          <xsl:choose>
            <xsl:when test="contains($vendor, 'SAXON')">
              <stext:insertfile href="{$filename}" encoding="{$textdata.default.encoding}"/>
            </xsl:when>
            <xsl:when test="contains($vendor, 'Apache Software Foundation')">
              <xtext:insertfile href="{$filename}"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:message terminate="yes">
                <xsl:text>Don't know how to insert files with </xsl:text>
                <xsl:value-of select="$vendor"/>
              </xsl:message>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
	<xsl:otherwise>
	  <xsl:message terminate="yes">
	    <xsl:text>Cannot insert </xsl:text><xsl:value-of select="$filename"/>
	    <xsl:text>. Check use.extensions and textinsert.extension parameters.</xsl:text> 
	  </xsl:message>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="process.image"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="mediaobject|mediaobjectco">

  <xsl:variable name="olist" select="imageobject|imageobjectco
                     |videoobject|audioobject
                     |textobject"/>

  <xsl:variable name="object.index">
    <xsl:call-template name="select.mediaobject.index">
      <xsl:with-param name="olist" select="$olist"/>
      <xsl:with-param name="count" select="1"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="object" select="$olist[position() = $object.index]"/>

  <xsl:variable name="align">
    <xsl:value-of select="$object/descendant::imagedata[@align][1]/@align"/>
  </xsl:variable>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <fo:block id="{$id}">
    <xsl:if test="$align != '' ">
      <xsl:attribute name="text-align">
        <xsl:value-of select="$align"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:apply-templates select="$object"/>
    <xsl:apply-templates select="caption"/>
  </fo:block>
</xsl:template>

<xsl:template match="inlinemediaobject">
  <xsl:call-template name="select.mediaobject"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="imageobjectco">
  <xsl:choose>
    <!-- select one imageobject? -->
    <xsl:when test="$use.role.for.mediaobject != 0 and
                    count(imageobject) &gt; 1 and
                    imageobject[@role]">
      <xsl:variable name="olist" select="imageobject"/>
    
      <xsl:variable name="object.index">
        <xsl:call-template name="select.mediaobject.index">
          <xsl:with-param name="olist" select="$olist"/>
          <xsl:with-param name="count" select="1"/>
        </xsl:call-template>
      </xsl:variable>
    
      <xsl:variable name="object" select="$olist[position() = $object.index]"/>
    
      <xsl:apply-templates select="$object"/>
    </xsl:when>
    <xsl:otherwise>
      <!-- otherwise process them all -->
      <xsl:apply-templates select="imageobject"/>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:apply-templates select="calloutlist"/>

</xsl:template>

<xsl:template match="imageobject">
  <xsl:choose>
    <xsl:when test="imagedata">
      <xsl:apply-templates select="imagedata"/>
    </xsl:when>
    <xsl:otherwise>
      <fo:instream-foreign-object>
        <xsl:apply-templates mode="copy-all"/>
      </fo:instream-foreign-object>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*" mode="copy-all">
  <xsl:copy>
    <xsl:for-each select="@*">
      <xsl:copy/>
    </xsl:for-each>
    <xsl:apply-templates mode="copy-all"/>
  </xsl:copy>
</xsl:template>

<xsl:template match="text()|comment()|processing-instruction()" mode="copy-all">
  <xsl:copy/>
</xsl:template>

<xsl:template name="process.mml">
  <xsl:apply-templates mode="copy-all" select="*"/>
</xsl:template>

<xsl:template name="process.svg">
  <xsl:apply-templates mode="copy-all" select="*"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="imagedata">
  <xsl:choose>
    <xsl:when test="@format='linespecific'">
      <xsl:variable name="vendor" select="system-property('xsl:vendor')"/>
    
      <xsl:variable name="filename">
        <xsl:call-template name="mediaobject.filename">
          <xsl:with-param name="object" select=".."/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:choose>
        <xsl:when test="$use.extensions != '0'
                        and $textinsert.extension != '0'">
          <xsl:choose>
            <xsl:when test="contains($vendor, 'SAXON')">
              <stext:insertfile href="{$filename}" encoding="{$textdata.default.encoding}"/>
            </xsl:when>
            <xsl:when test="contains($vendor, 'Apache Software Foundation')">
              <xtext:insertfile href="{$filename}"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:message terminate="yes">
                <xsl:text>Don't know how to insert files with </xsl:text>
                <xsl:value-of select="$vendor"/>
              </xsl:message>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
	  <xsl:message terminate="yes">
	    <xsl:text>Cannot insert </xsl:text><xsl:value-of select="$filename"/>
	    <xsl:text>. Check use.extensions and textinsert.extension parameters.</xsl:text> 
	  </xsl:message>
	</xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="process.image"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="videoobject">
  <xsl:apply-templates select="videodata"/>
</xsl:template>

<xsl:template match="videodata">
  <xsl:call-template name="process.image"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="audioobject">
  <xsl:apply-templates select="audiodata"/>
</xsl:template>

<xsl:template match="audiodata">
  <xsl:call-template name="process.image"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="textobject">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="textdata">
  <xsl:variable name="vendor" select="system-property('xsl:vendor')"/>
  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="@entityref">
        <xsl:value-of select="unparsed-entity-uri(@entityref)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="@fileref"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="encoding">
    <xsl:choose>
      <xsl:when test="@encoding">
        <xsl:value-of select="@encoding"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$textdata.default.encoding"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$use.extensions != '0'
                    and $textinsert.extension != '0'">
      <xsl:choose>
        <xsl:when test="element-available('stext:insertfile')">
          <stext:insertfile href="{$filename}" encoding="{$encoding}"/>
        </xsl:when>
        <xsl:when test="element-available('xtext:insertfile')">
          <xtext:insertfile href="{$filename}"/>
        </xsl:when>
	<xsl:otherwise>
	  <xsl:message terminate="yes">
	    <xsl:text>Don't know how to insert files with </xsl:text>
	    <xsl:value-of select="$vendor"/>
	  </xsl:message>
	</xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message terminate="yes">
	<xsl:text>Cannot insert </xsl:text><xsl:value-of select="$filename"/>
	<xsl:text>. Check use.extensions and textinsert.extension parameters.</xsl:text> 
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="mediaobject/caption|figure/caption">
  <fo:block>
    <xsl:if test="@align = 'right' or @align = 'left' or @align='center'">
      <xsl:attribute name="text-align"><xsl:value-of
                         select="@align"/></xsl:attribute>
    </xsl:if>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="fo-external-image">
  <xsl:param name="filename"/>

  <xsl:choose>
    <xsl:when test="$fop.extensions != 0">
      <xsl:value-of select="$filename"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="concat('url(', $filename, ')')"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Resolve xml:base attributes -->
<xsl:template match="@fileref">
  <!-- need a check for absolute urls -->
  <xsl:choose>
    <xsl:when test="contains(., ':') or starts-with(.,'/')">
      <!-- it has a uri scheme or starts with '/', so it is an absolute uri -->
      <xsl:value-of select="."/>
    </xsl:when>
    <xsl:when test="$keep.relative.image.uris != 0">
      <!-- leave it alone -->
      <xsl:value-of select="."/>
    </xsl:when>
    <xsl:otherwise>
      <!-- its a relative uri -->
      <xsl:call-template name="relative-uri">
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
