<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:sverb="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.Verbatim"
                xmlns:xverb="com.nwalsh.xalan.Verbatim"
                xmlns:lxslt="http://xml.apache.org/xslt"
                xmlns:exsl="http://exslt.org/common"
                exclude-result-prefixes="sverb xverb lxslt exsl"
                version='1.0'>

<!-- ********************************************************************
     $Id: verbatim.xsl 9590 2012-09-02 20:53:23Z tom_schr $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- XSLTHL highlighting is turned off by default. See highlighting/README
     for instructions on how to turn on XSLTHL -->
<xsl:template name="apply-highlighting">
    <xsl:apply-templates/>
</xsl:template>

<lxslt:component prefix="xverb"
                 functions="numberLines"/>

<xsl:template match="programlisting|screen|synopsis">
  <xsl:param name="suppress-numbers" select="'0'"/>
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <xsl:variable name="content">
    <xsl:choose>
      <xsl:when test="$suppress-numbers = '0'
                      and @linenumbering = 'numbered'
                      and $use.extensions != '0'
                      and $linenumbering.extension != '0'">
        <xsl:call-template name="number.rtf.lines">
          <xsl:with-param name="rtf">
            <xsl:choose>
              <xsl:when test="$highlight.source != 0">
                <xsl:call-template name="apply-highlighting"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:apply-templates/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:choose>
          <xsl:when test="$highlight.source != 0">
            <xsl:call-template name="apply-highlighting"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:variable name="block.content">
    <xsl:choose>
      <xsl:when test="$shade.verbatim != 0">
        <fo:block id="{$id}"
             xsl:use-attribute-sets="monospace.verbatim.properties shade.verbatim.style">
	  <xsl:if test="$keep.together != ''">
	    <xsl:attribute name="keep-together.within-column"><xsl:value-of
	    select="$keep.together"/></xsl:attribute>
	  </xsl:if>
          <xsl:choose>
            <xsl:when test="$hyphenate.verbatim != 0 and 
                            $exsl.node.set.available != 0">
              <xsl:apply-templates select="exsl:node-set($content)" 
                                   mode="hyphenate.verbatim"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:copy-of select="$content"/>
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
      </xsl:when>
      <xsl:otherwise>
        <fo:block id="{$id}"
                  xsl:use-attribute-sets="monospace.verbatim.properties">
	  <xsl:if test="$keep.together != ''">
	    <xsl:attribute name="keep-together.within-column"><xsl:value-of
	    select="$keep.together"/></xsl:attribute>
	  </xsl:if>
          <xsl:choose>
            <xsl:when test="$hyphenate.verbatim != 0 and 
                            $exsl.node.set.available != 0">
              <xsl:apply-templates select="exsl:node-set($content)" 
                                   mode="hyphenate.verbatim"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:copy-of select="$content"/>
            </xsl:otherwise>
          </xsl:choose>
        </fo:block>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <!-- Need a block-container for these features -->
    <xsl:when test="@width != '' or
                    (self::programlisting and
                    starts-with($writing.mode, 'rl'))">
      <fo:block-container start-indent="0pt" end-indent="0pt">
        <xsl:if test="@width != ''">
          <xsl:attribute name="width">
            <xsl:value-of select="concat(@width, '*', $monospace.verbatim.font.width)"/>
          </xsl:attribute>
        </xsl:if>
        <!-- All known program code is left-to-right -->
        <xsl:if test="self::programlisting and
                      starts-with($writing.mode, 'rl')">
          <xsl:attribute name="writing-mode">lr-tb</xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$block.content"/>
      </fo:block-container>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$block.content"/>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<xsl:template match="literallayout">
  <xsl:param name="suppress-numbers" select="'0'"/>

  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:variable name="content">
    <xsl:choose>
      <xsl:when test="$suppress-numbers = '0'
                      and @linenumbering = 'numbered'
                      and $use.extensions != '0'
                      and $linenumbering.extension != '0'">
        <xsl:call-template name="number.rtf.lines">
          <xsl:with-param name="rtf">
            <xsl:apply-templates/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="@class='monospaced'">
      <xsl:choose>
        <xsl:when test="$shade.verbatim != 0">
          <fo:block id="{$id}"
                    xsl:use-attribute-sets="monospace.verbatim.properties shade.verbatim.style">
	    <xsl:if test="$keep.together != ''">
	      <xsl:attribute name="keep-together.within-column"><xsl:value-of
	      select="$keep.together"/></xsl:attribute>
	    </xsl:if>
            <xsl:copy-of select="$content"/>
          </fo:block>
        </xsl:when>
        <xsl:otherwise>
          <fo:block id="{$id}"
                    xsl:use-attribute-sets="monospace.verbatim.properties">
	    <xsl:if test="$keep.together != ''">
	      <xsl:attribute name="keep-together.within-column"><xsl:value-of
	      select="$keep.together"/></xsl:attribute>
	    </xsl:if>
            <xsl:copy-of select="$content"/>
          </fo:block>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$shade.verbatim != 0">
          <fo:block id="{$id}"
                    xsl:use-attribute-sets="verbatim.properties shade.verbatim.style">
	    <xsl:if test="$keep.together != ''">
	      <xsl:attribute name="keep-together.within-column"><xsl:value-of
	      select="$keep.together"/></xsl:attribute>
	    </xsl:if>
            <xsl:copy-of select="$content"/>
          </fo:block>
        </xsl:when>
        <xsl:otherwise>
          <fo:block id="{$id}"
                    xsl:use-attribute-sets="verbatim.properties">
	    <xsl:if test="$keep.together != ''">
	      <xsl:attribute name="keep-together.within-column"><xsl:value-of
	      select="$keep.together"/></xsl:attribute>
	    </xsl:if>
            <xsl:copy-of select="$content"/>
          </fo:block>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="address">
  <xsl:param name="suppress-numbers" select="'0'"/>

  <xsl:variable name="content">
    <xsl:choose>
      <xsl:when test="$suppress-numbers = '0'
                      and @linenumbering = 'numbered'
                      and $use.extensions != '0'
                      and $linenumbering.extension != '0'">
        <xsl:call-template name="number.rtf.lines">
          <xsl:with-param name="rtf">
            <xsl:apply-templates/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:block xsl:use-attribute-sets="verbatim.properties">
    <xsl:copy-of select="$content"/>
  </fo:block>
</xsl:template>

<xsl:template name="number.rtf.lines">
  <xsl:param name="rtf" select="''"/>
  <xsl:param name="pi.context" select="."/>

  <!-- Save the global values -->
  <xsl:variable name="global.linenumbering.everyNth"
                select="$linenumbering.everyNth"/>

  <xsl:variable name="global.linenumbering.separator"
                select="$linenumbering.separator"/>

  <xsl:variable name="global.linenumbering.width"
                select="$linenumbering.width"/>

  <!-- Extract the <?dbfo linenumbering.*?> PI values -->
  <xsl:variable name="pi.linenumbering.everyNth">
    <xsl:call-template name="pi.dbfo_linenumbering.everyNth">
      <xsl:with-param name="node" select="$pi.context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="pi.linenumbering.separator">
    <xsl:call-template name="pi.dbfo_linenumbering.separator">
      <xsl:with-param name="node" select="$pi.context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="pi.linenumbering.width">
    <xsl:call-template name="pi.dbfo_linenumbering.width">
      <xsl:with-param name="node" select="$pi.context"/>
    </xsl:call-template>
  </xsl:variable>

  <!-- Construct the 'in-context' values -->
  <xsl:variable name="linenumbering.everyNth">
    <xsl:choose>
      <xsl:when test="$pi.linenumbering.everyNth != ''">
        <xsl:value-of select="$pi.linenumbering.everyNth"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$global.linenumbering.everyNth"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="linenumbering.separator">
    <xsl:choose>
      <xsl:when test="$pi.linenumbering.separator != ''">
        <xsl:value-of select="$pi.linenumbering.separator"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$global.linenumbering.separator"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="linenumbering.width">
    <xsl:choose>
      <xsl:when test="$pi.linenumbering.width != ''">
        <xsl:value-of select="$pi.linenumbering.width"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$global.linenumbering.width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="linenumbering.startinglinenumber">
    <xsl:choose>
      <xsl:when test="$pi.context/@startinglinenumber">
        <xsl:value-of select="$pi.context/@startinglinenumber"/>
      </xsl:when>
      <xsl:when test="$pi.context/@continuation='continues'">
        <xsl:variable name="lastLine">
          <xsl:choose>
            <xsl:when test="$pi.context/self::programlisting">
              <xsl:call-template name="lastLineNumber">
                <xsl:with-param name="listings"
                     select="preceding::programlisting[@linenumbering='numbered']"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$pi.context/self::screen">
              <xsl:call-template name="lastLineNumber">
                <xsl:with-param name="listings"
                     select="preceding::screen[@linenumbering='numbered']"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$pi.context/self::literallayout">
              <xsl:call-template name="lastLineNumber">
                <xsl:with-param name="listings"
                     select="preceding::literallayout[@linenumbering='numbered']"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$pi.context/self::address">
              <xsl:call-template name="lastLineNumber">
                <xsl:with-param name="listings"
                     select="preceding::address[@linenumbering='numbered']"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$pi.context/self::synopsis">
              <xsl:call-template name="lastLineNumber">
                <xsl:with-param name="listings"
                     select="preceding::synopsis[@linenumbering='numbered']"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:message>
                <xsl:text>Unexpected verbatim environment: </xsl:text>
                <xsl:value-of select="local-name(.)"/>
              </xsl:message>
              <xsl:value-of select="0"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>

        <xsl:value-of select="$lastLine + 1"/>
      </xsl:when>
      <xsl:otherwise>1</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="function-available('sverb:numberLines')">
      <xsl:copy-of select="sverb:numberLines($rtf)"/>
    </xsl:when>
    <xsl:when test="function-available('xverb:numberLines')">
      <xsl:copy-of select="xverb:numberLines($rtf)"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message terminate="yes">
        <xsl:text>No numberLines function available.</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ======================================================================== -->

<xsl:template name="lastLineNumber">
  <xsl:param name="listings"/>
  <xsl:param name="number" select="0"/>

  <xsl:variable name="lines">
    <xsl:call-template name="countLines">
      <xsl:with-param name="listing" select="string($listings[1])"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="not($listings)">
      <xsl:value-of select="$number"/>
    </xsl:when>
    <xsl:when test="$listings[1]/@startinglinenumber">
      <xsl:value-of select="$number + $listings[1]/@startinglinenumber + $lines - 1"/>
    </xsl:when>
    <xsl:when test="$listings[1]/@continuation='continues'">
      <xsl:call-template name="lastLineNumber">
        <xsl:with-param name="listings" select="$listings[position() &gt; 1]"/>
        <xsl:with-param name="number" select="$number + $lines"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$lines"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="countLines">
  <xsl:param name="listing"/>
  <xsl:param name="count" select="1"/>

  <xsl:choose>
    <xsl:when test="contains($listing, '&#10;')">
      <xsl:call-template name="countLines">
        <xsl:with-param name="listing" select="substring-after($listing, '&#10;')"/>
        <xsl:with-param name="count" select="$count + 1"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$count"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ======================================================================== -->

<xsl:template match="node()|@*" mode="hyphenate.verbatim">
  <xsl:copy>
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="hyphenate.verbatim"/>
  </xsl:copy>
</xsl:template>

<xsl:template match="text()" mode="hyphenate.verbatim" priority="2">
  <xsl:call-template name="hyphenate.verbatim.block">
    <xsl:with-param name="content" select="."/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="hyphenate.verbatim.block">
  <xsl:param name="content" select="''"/>
  <xsl:param name="count" select="1"/>

  <!-- recurse on lines first to keep recursion depth reasonable -->
  <xsl:choose>
    <xsl:when test="contains($content, '&#xA;')">
      <xsl:variable name="line" select="substring-before($content, '&#xA;')"/>
      <xsl:variable name="rest" select="substring-after($content, '&#xA;')"/>
      <xsl:call-template name="hyphenate.verbatim">
        <xsl:with-param name="content" select="concat($line, '&#xA;')"/>
      </xsl:call-template>
      <xsl:call-template name="hyphenate.verbatim.block">
        <xsl:with-param name="content" select="$rest"/>
        <xsl:with-param name="count" select="$count + 1"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="hyphenate.verbatim">
        <xsl:with-param name="content" select="$content"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
  
</xsl:template>

<xsl:template name="hyphenate.verbatim">
  <xsl:param name="content"/>
  <xsl:variable name="head" select="substring($content, 1, 1)"/>
  <xsl:variable name="tail" select="substring($content, 2)"/>
  <xsl:choose>
    <!-- Place soft-hyphen after space or non-breakable space. -->
    <xsl:when test="$head = ' ' or $head = '&#160;'">
      <xsl:text>&#160;</xsl:text>
      <xsl:text>&#x00AD;</xsl:text>
    </xsl:when>
    <xsl:when test="$hyphenate.verbatim.characters != '' and
                    translate($head, $hyphenate.verbatim.characters, '') = '' and not($tail = '')">
      <xsl:value-of select="$head"/>
      <xsl:text>&#x00AD;</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$head"/>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:if test="$tail">
    <xsl:call-template name="hyphenate.verbatim">
      <xsl:with-param name="content" select="$tail"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>


</xsl:stylesheet>
