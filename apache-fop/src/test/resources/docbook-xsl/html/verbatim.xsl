<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:sverb="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.Verbatim"
                xmlns:xverb="xalan://com.nwalsh.xalan.Verbatim"
                xmlns:lxslt="http://xml.apache.org/xslt"
                xmlns:exsl="http://exslt.org/common"
                exclude-result-prefixes="sverb xverb lxslt exsl"
                version='1.0'>

<!-- ********************************************************************
     $Id: verbatim.xsl 9589 2012-09-02 20:52:15Z tom_schr $
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

  <xsl:call-template name="anchor"/>

  <xsl:variable name="div.element">pre</xsl:variable>

  <xsl:if test="$shade.verbatim != 0">
    <xsl:message>
      <xsl:text>The shade.verbatim parameter is deprecated. </xsl:text>
      <xsl:text>Use CSS instead,</xsl:text>
    </xsl:message>
    <xsl:message>
      <xsl:text>for example: pre.</xsl:text>
      <xsl:value-of select="local-name(.)"/>
      <xsl:text> { background-color: #E0E0E0; }</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:choose>
    <xsl:when test="$suppress-numbers = '0'
                    and @linenumbering = 'numbered'
                    and $use.extensions != '0'
                    and $linenumbering.extension != '0'">
      <xsl:variable name="rtf">
        <xsl:choose>
          <xsl:when test="$highlight.source != 0">
            <xsl:call-template name="apply-highlighting"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>
      <xsl:element name="{$div.element}">
        <xsl:apply-templates select="." mode="common.html.attributes"/>
        <xsl:call-template name="id.attribute"/>
        <xsl:if test="@width != ''">
          <xsl:attribute name="width">
            <xsl:value-of select="@width"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:call-template name="number.rtf.lines">
          <xsl:with-param name="rtf" select="$rtf"/>
        </xsl:call-template>
      </xsl:element>
    </xsl:when>
    <xsl:otherwise>
      <xsl:element name="{$div.element}">
        <xsl:apply-templates select="." mode="common.html.attributes"/>
        <xsl:call-template name="id.attribute"/>
        <xsl:if test="@width != ''">
          <xsl:attribute name="width">
            <xsl:value-of select="@width"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:choose>
          <xsl:when test="$highlight.source != 0">
            <xsl:call-template name="apply-highlighting"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:element>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="literallayout">
  <xsl:param name="suppress-numbers" select="'0'"/>

  <xsl:variable name="rtf">
    <xsl:apply-templates/>
  </xsl:variable>

  <xsl:if test="$shade.verbatim != 0 and @class='monospaced'">
    <xsl:message>
      <xsl:text>The shade.verbatim parameter is deprecated. </xsl:text>
      <xsl:text>Use CSS instead,</xsl:text>
    </xsl:message>
    <xsl:message>
      <xsl:text>for example: pre.</xsl:text>
      <xsl:value-of select="local-name(.)"/>
      <xsl:text> { background-color: #E0E0E0; }</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:choose>
    <xsl:when test="$suppress-numbers = '0'
                    and @linenumbering = 'numbered'
                    and $use.extensions != '0'
                    and $linenumbering.extension != '0'">
      <xsl:choose>
        <xsl:when test="@class='monospaced'">
          <pre>
            <xsl:apply-templates select="." mode="common.html.attributes"/>
            <xsl:call-template name="id.attribute"/>
            <xsl:call-template name="number.rtf.lines">
              <xsl:with-param name="rtf" select="$rtf"/>
            </xsl:call-template>
          </pre>
        </xsl:when>
        <xsl:otherwise>
          <div>
            <xsl:apply-templates select="." mode="common.html.attributes"/>
            <xsl:call-template name="id.attribute"/>
            <p>
              <xsl:call-template name="number.rtf.lines">
                <xsl:with-param name="rtf" select="$rtf"/>
              </xsl:call-template>
            </p>
          </div>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="@class='monospaced'">
          <pre>
            <xsl:apply-templates select="." mode="common.html.attributes"/>
            <xsl:call-template name="id.attribute"/>
            <xsl:copy-of select="$rtf"/>
          </pre>
        </xsl:when>
        <xsl:otherwise>
          <div>
            <xsl:apply-templates select="." mode="common.html.attributes"/>
            <xsl:call-template name="id.attribute"/>
            <p>
              <xsl:call-template name="make-verbatim">
                <xsl:with-param name="rtf" select="$rtf"/>
              </xsl:call-template>
            </p>
          </div>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="address">
  <xsl:param name="suppress-numbers" select="'0'"/>

  <xsl:variable name="rtf">
    <xsl:apply-templates/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$suppress-numbers = '0'
                    and @linenumbering = 'numbered'
                    and $use.extensions != '0'
                    and $linenumbering.extension != '0'">
      <div>
        <xsl:apply-templates select="." mode="common.html.attributes"/>
        <xsl:call-template name="id.attribute"/>
        <p>
          <xsl:call-template name="number.rtf.lines">
            <xsl:with-param name="rtf" select="$rtf"/>
          </xsl:call-template>
        </p>
      </div>
    </xsl:when>

    <xsl:otherwise>
      <div>
        <xsl:apply-templates select="." mode="common.html.attributes"/>
        <xsl:call-template name="id.attribute"/>
        <p>
          <xsl:call-template name="make-verbatim">
            <xsl:with-param name="rtf" select="$rtf"/>
          </xsl:call-template>
        </p>
      </div>
    </xsl:otherwise>
  </xsl:choose>
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

  <!-- Extract the <?dbhtml linenumbering.*?> PI values -->
  <xsl:variable name="pi.linenumbering.everyNth">
    <xsl:call-template name="pi.dbhtml_linenumbering.everyNth">
      <xsl:with-param name="node" select="$pi.context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="pi.linenumbering.separator">
    <xsl:call-template name="pi.dbhtml_linenumbering.separator">
      <xsl:with-param name="node" select="$pi.context"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="pi.linenumbering.width">
    <xsl:call-template name="pi.dbhtml_linenumbering.width">
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
                <xsl:value-of select="local-name($pi.context)"/>
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

<xsl:template name="make-verbatim">
  <xsl:param name="rtf"/>

  <!-- I want to make this RTF verbatim. There are two possibilities: either
       I have access to the exsl:node-set extension function and I can "do it right"
       or I have to rely on CSS. -->

  <xsl:choose>
    <xsl:when test="$exsl.node.set.available != 0">
      <xsl:apply-templates select="exsl:node-set($rtf)" mode="make.verbatim.mode"/>
    </xsl:when>
    <xsl:otherwise>
      <span style="white-space: pre;">
        <xsl:copy-of select="$rtf"/>
      </span>
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

</xsl:stylesheet>
