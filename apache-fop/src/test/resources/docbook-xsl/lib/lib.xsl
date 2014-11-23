<?xml version="1.0"?>
<!-- ********************************************************************
     $Id: lib.xweb 9040 2011-08-19 21:51:47Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     This module implements DTD-independent functions

     ******************************************************************** -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:template name="dot.count">
  <!-- Returns the number of "." characters in a string -->
  <xsl:param name="string"/>
  <xsl:param name="count" select="0"/>
  <xsl:choose>
    <xsl:when test="contains($string, '.')">
      <xsl:call-template name="dot.count">
        <xsl:with-param name="string" select="substring-after($string, '.')"/>
        <xsl:with-param name="count" select="$count+1"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$count"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="copy-string">
  <!-- returns 'count' copies of 'string' -->
  <xsl:param name="string"/>
  <xsl:param name="count" select="0"/>
  <xsl:param name="result"/>

  <xsl:choose>
    <xsl:when test="$count&gt;0">
      <xsl:call-template name="copy-string">
        <xsl:with-param name="string" select="$string"/>
        <xsl:with-param name="count" select="$count - 1"/>
        <xsl:with-param name="result">
          <xsl:value-of select="$result"/>
          <xsl:value-of select="$string"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$result"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="string.subst">
  <xsl:param name="string"/>
  <xsl:param name="target"/>
  <xsl:param name="replacement"/>

  <xsl:choose>
    <xsl:when test="contains($string, $target)">
      <xsl:variable name="rest">
        <xsl:call-template name="string.subst">
          <xsl:with-param name="string" select="substring-after($string, $target)"/>
          <xsl:with-param name="target" select="$target"/>
          <xsl:with-param name="replacement" select="$replacement"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:value-of select="concat(substring-before($string, $target),                                    $replacement,                                    $rest)"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$string"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="xpointer.idref">
  <xsl:param name="xpointer">http://...</xsl:param>
  <xsl:choose>
    <xsl:when test="starts-with($xpointer, '#xpointer(id(')">
      <xsl:variable name="rest" select="substring-after($xpointer, '#xpointer(id(')"/>
      <xsl:variable name="quote" select="substring($rest, 1, 1)"/>
      <xsl:value-of select="substring-before(substring-after($xpointer, $quote), $quote)"/>
    </xsl:when>
    <xsl:when test="starts-with($xpointer, '#')">
      <xsl:value-of select="substring-after($xpointer, '#')"/>
    </xsl:when>
    <!-- otherwise it's a pointer to some other document -->
  </xsl:choose>
</xsl:template>
<xsl:template name="length-magnitude">
  <xsl:param name="length" select="'0pt'"/>

  <xsl:choose>
    <xsl:when test="string-length($length) = 0"/>
    <xsl:when test="substring($length,1,1) = '0'                     or substring($length,1,1) = '1'                     or substring($length,1,1) = '2'                     or substring($length,1,1) = '3'                     or substring($length,1,1) = '4'                     or substring($length,1,1) = '5'                     or substring($length,1,1) = '6'                     or substring($length,1,1) = '7'                     or substring($length,1,1) = '8'                     or substring($length,1,1) = '9'                     or substring($length,1,1) = '.'">
      <xsl:value-of select="substring($length,1,1)"/>
      <xsl:call-template name="length-magnitude">
        <xsl:with-param name="length" select="substring($length,2)"/>
      </xsl:call-template>
    </xsl:when>
  </xsl:choose>
</xsl:template>
<xsl:template name="length-units">
  <xsl:param name="length" select="'0pt'"/>
  <xsl:param name="default.units" select="'px'"/>
  <xsl:variable name="magnitude">
    <xsl:call-template name="length-magnitude">
      <xsl:with-param name="length" select="$length"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="units">
    <xsl:value-of select="substring($length, string-length($magnitude)+1)"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$units = ''">
      <xsl:value-of select="$default.units"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$units"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="length-spec">
  <xsl:param name="length" select="'0pt'"/>
  <xsl:param name="default.units" select="'px'"/>

  <xsl:variable name="magnitude">
    <xsl:call-template name="length-magnitude">
      <xsl:with-param name="length" select="$length"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="units">
    <xsl:value-of select="substring($length, string-length($magnitude)+1)"/>
  </xsl:variable>

  <xsl:value-of select="$magnitude"/>
  <xsl:choose>
    <xsl:when test="$units='cm'                     or $units='mm'                     or $units='in'                     or $units='pt'                     or $units='pc'                     or $units='px'                     or $units='em'">
      <xsl:value-of select="$units"/>
    </xsl:when>
    <xsl:when test="$units = ''">
      <xsl:value-of select="$default.units"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>
        <xsl:text>Unrecognized unit of measure: </xsl:text>
        <xsl:value-of select="$units"/>
        <xsl:text>.</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="length-in-points">
  <xsl:param name="length" select="'0pt'"/>
  <xsl:param name="em.size" select="10"/>
  <xsl:param name="pixels.per.inch" select="90"/>

  <xsl:variable name="magnitude">
    <xsl:call-template name="length-magnitude">
      <xsl:with-param name="length" select="$length"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="units">
    <xsl:value-of select="substring($length, string-length($magnitude)+1)"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$units = 'pt'">
      <xsl:value-of select="$magnitude"/>
    </xsl:when>
    <xsl:when test="$units = 'cm'">
      <xsl:value-of select="$magnitude div 2.54 * 72.0"/>
    </xsl:when>
    <xsl:when test="$units = 'mm'">
      <xsl:value-of select="$magnitude div 25.4 * 72.0"/>
    </xsl:when>
    <xsl:when test="$units = 'in'">
      <xsl:value-of select="$magnitude * 72.0"/>
    </xsl:when>
    <xsl:when test="$units = 'pc'">
      <xsl:value-of select="$magnitude * 12.0"/>
    </xsl:when>
    <xsl:when test="$units = 'px'">
      <xsl:value-of select="$magnitude div $pixels.per.inch * 72.0"/>
    </xsl:when>
    <xsl:when test="$units = 'em'">
      <xsl:value-of select="$magnitude * $em.size"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>
        <xsl:text>Unrecognized unit of measure: </xsl:text>
        <xsl:value-of select="$units"/>
        <xsl:text>.</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="pi-attribute">
  <xsl:param name="pis" select="processing-instruction('BOGUS_PI')"/>
  <xsl:param name="attribute">filename</xsl:param>
  <xsl:param name="count">1</xsl:param>

  <xsl:choose>
    <xsl:when test="$count&gt;count($pis)">
      <!-- not found -->
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="pi">
        <xsl:value-of select="$pis[$count]"/>
      </xsl:variable>
      <xsl:variable name="pivalue">
        <xsl:value-of select="concat(' ', normalize-space($pi))"/>
      </xsl:variable>
      <xsl:choose>
        <xsl:when test="contains($pivalue,concat(' ', $attribute, '='))">
          <xsl:variable name="rest" select="substring-after($pivalue,concat(' ', $attribute,'='))"/>
          <xsl:variable name="quote" select="substring($rest,1,1)"/>
          <xsl:value-of select="substring-before(substring($rest,2),$quote)"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="pi-attribute">
            <xsl:with-param name="pis" select="$pis"/>
            <xsl:with-param name="attribute" select="$attribute"/>
            <xsl:with-param name="count" select="$count + 1"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="lookup.key">
  <xsl:param name="key" select="''"/>
  <xsl:param name="table" select="''"/>

  <xsl:if test="contains($table, ' ')">
    <xsl:choose>
      <xsl:when test="substring-before($table, ' ') = $key">
        <xsl:variable name="rest" select="substring-after($table, ' ')"/>
        <xsl:choose>
          <xsl:when test="contains($rest, ' ')">
            <xsl:value-of select="substring-before($rest, ' ')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$rest"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="lookup.key">
          <xsl:with-param name="key" select="$key"/>
          <xsl:with-param name="table" select="substring-after(substring-after($table,' '), ' ')"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>
</xsl:template>
<xsl:template name="xpath.location">
  <xsl:param name="node" select="."/>
  <xsl:param name="path" select="''"/>

  <xsl:variable name="next.path">
    <xsl:value-of select="local-name($node)"/>
    <xsl:if test="$path != ''">/</xsl:if>
    <xsl:value-of select="$path"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$node/parent::*">
      <xsl:call-template name="xpath.location">
        <xsl:with-param name="node" select="$node/parent::*"/>
        <xsl:with-param name="path" select="$next.path"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>/</xsl:text>
      <xsl:value-of select="$next.path"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="comment-escape-string">
  <xsl:param name="string" select="''"/>

  <xsl:if test="starts-with($string, '-')">
    <xsl:text> </xsl:text>
  </xsl:if>

  <xsl:call-template name="comment-escape-string.recursive">
    <xsl:with-param name="string" select="$string"/>
  </xsl:call-template>

  <xsl:if test="substring($string, string-length($string), 1) = '-'">
    <xsl:text> </xsl:text>
  </xsl:if>
</xsl:template>
<xsl:template name="comment-escape-string.recursive">
  <xsl:param name="string" select="''"/>
  <xsl:choose>
    <xsl:when test="contains($string, '--')">
      <xsl:value-of select="substring-before($string, '--')"/>
      <xsl:value-of select="'- -'"/>
      <xsl:call-template name="comment-escape-string.recursive">
        <xsl:with-param name="string" select="substring-after($string, '--')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$string"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
  <xsl:template name="str.tokenize.keep.delimiters">
    <xsl:param name="string" select="''"/>
    <xsl:param name="delimiters" select="' '"/>
    <xsl:choose>
      <xsl:when test="not($string)"/>
      <xsl:when test="not($delimiters)">
        <xsl:call-template name="str.tokenize.keep.delimiters-characters">
          <xsl:with-param name="string" select="$string"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="str.tokenize.keep.delimiters-delimiters">
          <xsl:with-param name="string" select="$string"/>
          <xsl:with-param name="delimiters" select="$delimiters"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template name="str.tokenize.keep.delimiters-characters">
    <xsl:param name="string"/>
    <xsl:if test="$string">
      <ssb:token xmlns:ssb="http://sideshowbarker.net/ns"><xsl:value-of select="substring($string, 1, 1)"/></ssb:token>
      <xsl:call-template name="str.tokenize.keep.delimiters-characters">
        <xsl:with-param name="string" select="substring($string, 2)"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:template>
  <xsl:template name="str.tokenize.keep.delimiters-delimiters">
    <xsl:param name="string"/>
    <xsl:param name="delimiters"/>
    <xsl:variable name="delimiter" select="substring($delimiters, 1, 1)"/>
    <xsl:choose>
      <xsl:when test="not($delimiter)">
        <ssb:token xmlns:ssb="http://sideshowbarker.net/ns"><xsl:value-of select="$string"/></ssb:token>
      </xsl:when>
      <xsl:when test="contains($string, $delimiter)">
        <xsl:if test="not(starts-with($string, $delimiter))">
          <xsl:call-template name="str.tokenize.keep.delimiters-delimiters">
            <xsl:with-param name="string" select="substring-before($string, $delimiter)"/>
            <xsl:with-param name="delimiters" select="substring($delimiters, 2)"/>
          </xsl:call-template>
        </xsl:if>
        <!-- output each delimiter -->
        <xsl:value-of select="$delimiter"/>
        <xsl:call-template name="str.tokenize.keep.delimiters-delimiters">
          <xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
          <xsl:with-param name="delimiters" select="$delimiters"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="str.tokenize.keep.delimiters-delimiters">
          <xsl:with-param name="string" select="$string"/>
          <xsl:with-param name="delimiters" select="substring($delimiters, 2)"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
    <xsl:template name="apply-string-subst-map">
      <xsl:param name="content"/>
      <xsl:param name="map.contents"/>
      <xsl:variable name="replaced_text">
        <xsl:call-template name="string.subst">
          <xsl:with-param name="string" select="$content"/>
          <xsl:with-param name="target" select="$map.contents[1]/@oldstring"/>
          <xsl:with-param name="replacement" select="$map.contents[1]/@newstring"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:choose>
        <xsl:when test="$map.contents[2]">
          <xsl:call-template name="apply-string-subst-map">
            <xsl:with-param name="content" select="$replaced_text"/>
            <xsl:with-param name="map.contents" select="$map.contents[position() &gt; 1]"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$replaced_text"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:template>
  
<xsl:template name="count.uri.path.depth">
  <xsl:param name="filename" select="''"/>
  <xsl:param name="count" select="0"/>

  <xsl:choose>
    <xsl:when test="contains($filename, '/')">
      <xsl:call-template name="count.uri.path.depth">
        <xsl:with-param name="filename" select="substring-after($filename, '/')"/>
        <xsl:with-param name="count" select="$count + 1"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$count"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="trim.common.uri.paths">
  <xsl:param name="uriA" select="''"/>
  <xsl:param name="uriB" select="''"/>
  <xsl:param name="return" select="'A'"/>

  <!-- Resolve any ../ in the path -->
  <xsl:variable name="trimmed.uriA">
    <xsl:call-template name="resolve.path">
      <xsl:with-param name="filename" select="$uriA"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="trimmed.uriB">
    <xsl:call-template name="resolve.path">
      <xsl:with-param name="filename" select="$uriB"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="contains($trimmed.uriA, '/') and contains($trimmed.uriB, '/')                     and substring-before($trimmed.uriA, '/') = substring-before($trimmed.uriB, '/')">
      <xsl:call-template name="trim.common.uri.paths">
        <xsl:with-param name="uriA" select="substring-after($trimmed.uriA, '/')"/>
        <xsl:with-param name="uriB" select="substring-after($trimmed.uriB, '/')"/>
        <xsl:with-param name="return" select="$return"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$return = 'A'">
          <xsl:value-of select="$trimmed.uriA"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$trimmed.uriB"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
<xsl:template name="resolve.path">
  <xsl:param name="filename" select="''"/>
  <xsl:choose>
    <!-- Leading .. are not eliminated -->
    <xsl:when test="starts-with($filename, '../')">
      <xsl:value-of select="'../'"/>
      <xsl:call-template name="resolve.path">
        <xsl:with-param name="filename" select="substring-after($filename, '../')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="contains($filename, '/../')">
      <xsl:call-template name="resolve.path">
        <xsl:with-param name="filename">
          <xsl:call-template name="dirname">
            <xsl:with-param name="filename" select="substring-before($filename, '/../')"/>
          </xsl:call-template>
          <xsl:value-of select="substring-after($filename, '/../')"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$filename"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="dirname">
  <xsl:param name="filename" select="''"/>
  <xsl:if test="contains($filename, '/')">
    <xsl:value-of select="substring-before($filename, '/')"/>
    <xsl:text>/</xsl:text>
    <xsl:call-template name="dirname">
      <xsl:with-param name="filename" select="substring-after($filename, '/')"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>


  <xsl:template name="trim.text">
    <xsl:param name="contents" select="."/>
    <xsl:variable name="contents-left-trimmed">
      <xsl:call-template name="trim-left">
        <xsl:with-param name="contents" select="$contents"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="contents-trimmed">
      <xsl:call-template name="trim-right">
        <xsl:with-param name="contents" select="$contents-left-trimmed"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$contents-trimmed"/>
  </xsl:template>

  <xsl:template name="trim-left">
    <xsl:param name="contents"/>
    <xsl:choose>
      <xsl:when test="starts-with($contents,'&#10;') or                       starts-with($contents,'&#13;') or                       starts-with($contents,' ') or                       starts-with($contents,'&#9;')">
        <xsl:call-template name="trim-left">
          <xsl:with-param name="contents" select="substring($contents, 2)"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$contents"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name="trim-right">
    <xsl:param name="contents"/>
    <xsl:variable name="last-char">
      <xsl:value-of select="substring($contents, string-length($contents), 1)"/>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="($last-char = '&#10;') or                       ($last-char = '&#13;') or                       ($last-char = ' ') or                       ($last-char = '&#9;')">
        <xsl:call-template name="trim-right">
          <xsl:with-param name="contents" select="substring($contents, 1, string-length($contents) - 1)"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$contents"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

</xsl:stylesheet>

