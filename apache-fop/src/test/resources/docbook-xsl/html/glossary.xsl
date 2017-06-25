<?xml version='1.0'?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY % common.entities SYSTEM "../common/entities.ent">
%common.entities;
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xlink='http://www.w3.org/1999/xlink'
                exclude-result-prefixes="xlink"
                version='1.0'>

<!-- ********************************************************************
     $Id: glossary.xsl 9709 2013-01-22 18:56:09Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="glossary">
  &setup-language-variable;
  <xsl:call-template name="id.warning"/>

  <xsl:element name="{$div.element}">
    <xsl:apply-templates select="." mode="common.html.attributes"/>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:call-template name="glossary.titlepage"/>

    <xsl:choose>
      <xsl:when test="glossdiv">
        <xsl:apply-templates select="(glossdiv[1]/preceding-sibling::*)"/>
      </xsl:when>
      <xsl:when test="glossentry">
        <xsl:apply-templates select="(glossentry[1]/preceding-sibling::*)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates/>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:choose>
      <xsl:when test="glossdiv">
        <xsl:apply-templates select="glossdiv"/>
      </xsl:when>
      <xsl:when test="glossentry">
        <dl>
          <xsl:choose>
            <xsl:when test="$glossary.sort != 0">
              <xsl:apply-templates select="glossentry">
				<xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
              </xsl:apply-templates>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="glossentry"/>
            </xsl:otherwise>
          </xsl:choose>
        </dl>
      </xsl:when>
      <xsl:otherwise>
        <!-- empty glossary -->
      </xsl:otherwise>
    </xsl:choose>

    <xsl:if test="not(parent::article)">
      <xsl:call-template name="process.footnotes"/>
    </xsl:if>
  </xsl:element>
</xsl:template>

<xsl:template match="glossary/glossaryinfo"></xsl:template>
<xsl:template match="glossary/info"></xsl:template>
<xsl:template match="glossary/title"></xsl:template>
<xsl:template match="glossary/subtitle"></xsl:template>
<xsl:template match="glossary/titleabbrev"></xsl:template>

<!-- ==================================================================== -->

<xsl:template match="glosslist">
  &setup-language-variable;
  <div>
    <xsl:apply-templates select="." mode="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>
    <xsl:if test="blockinfo/title|info/title|title">
      <xsl:call-template name="formal.object.heading"/>
    </xsl:if>
    <dl>
      <xsl:choose>
        <xsl:when test="$glossary.sort != 0">
          <xsl:apply-templates select="glossentry">
				<xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="glossentry"/>
        </xsl:otherwise>
      </xsl:choose>
    </dl>
  </div>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="glossdiv">
  &setup-language-variable;
  <xsl:call-template name="id.warning"/>

  <div>
    <xsl:apply-templates select="." mode="common.html.attributes"/>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:apply-templates select="(glossentry[1]/preceding-sibling::*)"/>

    <dl>
      <xsl:choose>
        <xsl:when test="$glossary.sort != 0">
          <xsl:apply-templates select="glossentry">
            <xsl:sort lang="{$language}"
                      select="translate(glossterm, $lowercase, 
                                        $uppercase)"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="glossentry"/>
        </xsl:otherwise>
      </xsl:choose>
    </dl>
  </div>
</xsl:template>

<xsl:template match="glossdiv/title">
  <h3>
    <xsl:apply-templates select="." mode="common.html.attributes"/>
    <xsl:apply-templates/>
  </h3>
</xsl:template>

<!-- ==================================================================== -->

<!--
GlossEntry ::=
  GlossTerm, Acronym?, Abbrev?,
  (IndexTerm)*,
  RevHistory?,
  (GlossSee | GlossDef+)
-->

<xsl:template match="glossentry">
  <xsl:choose>
    <xsl:when test="$glossentry.show.acronym = 'primary'">
      <dt>
        <xsl:call-template name="id.attribute">
          <xsl:with-param name="conditional">
            <xsl:choose>
              <xsl:when test="$glossterm.auto.link != 0">0</xsl:when>
              <xsl:otherwise>1</xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="anchor">
          <xsl:with-param name="conditional">
            <xsl:choose>
              <xsl:when test="$glossterm.auto.link != 0">0</xsl:when>
              <xsl:otherwise>1</xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>

        <xsl:choose>
          <xsl:when test="acronym|abbrev">
            <xsl:apply-templates select="acronym|abbrev"/>
            <xsl:text> (</xsl:text>
            <xsl:apply-templates select="glossterm"/>
            <xsl:text>)</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="glossterm"/>
          </xsl:otherwise>
        </xsl:choose>
      </dt>
    </xsl:when>
    <xsl:when test="$glossentry.show.acronym = 'yes'">
      <dt>
        <xsl:call-template name="id.attribute">
          <xsl:with-param name="conditional">
            <xsl:choose>
              <xsl:when test="$glossterm.auto.link != 0">0</xsl:when>
              <xsl:otherwise>1</xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="anchor">
          <xsl:with-param name="conditional">
            <xsl:choose>
              <xsl:when test="$glossterm.auto.link != 0">0</xsl:when>
              <xsl:otherwise>1</xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>

        <xsl:apply-templates select="glossterm"/>

        <xsl:if test="acronym|abbrev">
          <xsl:text> (</xsl:text>
          <xsl:apply-templates select="acronym|abbrev"/>
          <xsl:text>)</xsl:text>
        </xsl:if>
      </dt>
    </xsl:when>
    <xsl:otherwise>
      <dt>
        <xsl:call-template name="id.attribute">
          <xsl:with-param name="conditional">
            <xsl:choose>
              <xsl:when test="$glossterm.auto.link != 0">0</xsl:when>
              <xsl:otherwise>1</xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="anchor">
          <xsl:with-param name="conditional">
            <xsl:choose>
              <xsl:when test="$glossterm.auto.link != 0">0</xsl:when>
              <xsl:otherwise>1</xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>

        <xsl:apply-templates select="glossterm"/>
      </dt>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:apply-templates select="indexterm|revhistory|glosssee|glossdef"/>
</xsl:template>

<xsl:template match="glossentry/glossterm">
  <span>
    <xsl:apply-templates select="." mode="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>
    <xsl:apply-templates/>
  </span>
  <xsl:if test="following-sibling::glossterm">, </xsl:if>
</xsl:template>

<xsl:template match="glossentry/acronym">
  <xsl:apply-templates/>
  <xsl:if test="following-sibling::acronym|following-sibling::abbrev">, </xsl:if>
</xsl:template>

<xsl:template match="glossentry/abbrev">
  <xsl:apply-templates/>
  <xsl:if test="following-sibling::acronym|following-sibling::abbrev">, </xsl:if>
</xsl:template>

<xsl:template match="glossentry/revhistory">
</xsl:template>

<xsl:template match="glossentry/glosssee">
  <xsl:variable name="otherterm" select="@otherterm"/>
  <xsl:variable name="targets" select="key('id', $otherterm)"/>
  <xsl:variable name="target" select="$targets[1]"/>
  <xsl:variable name="xlink" select="@xlink:href"/>

  <dd>
    <p>
      <xsl:variable name="template">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'glossary'"/>
          <xsl:with-param name="name" select="'see'"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:variable name="title">
        <xsl:choose>
          <xsl:when test="$target">
            <a>
              <xsl:apply-templates select="." mode="common.html.attributes"/>
              <xsl:call-template name="id.attribute"/>
              <xsl:attribute name="href">
                <xsl:call-template name="href.target">
                  <xsl:with-param name="object" select="$target"/>
                </xsl:call-template>
              </xsl:attribute>
              <xsl:apply-templates select="$target" mode="xref-to"/>
            </a>
          </xsl:when>
          <xsl:when test="$xlink">
            <xsl:call-template name="simple.xlink">
              <xsl:with-param name="content">
                <xsl:apply-templates/>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:when>
          <xsl:when test="$otherterm != '' and not($target)">
            <xsl:message>
              <xsl:text>Warning: glosssee @otherterm reference not found: </xsl:text>
              <xsl:value-of select="$otherterm"/>
            </xsl:message>
            <xsl:apply-templates/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:call-template name="substitute-markup">
        <xsl:with-param name="template" select="$template"/>
        <xsl:with-param name="title" select="$title"/>
      </xsl:call-template>
    </p>
  </dd>
</xsl:template>

<xsl:template match="glossentry/glossdef">
  <dd>
    <xsl:apply-templates select="." mode="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>
    <xsl:apply-templates select="*[local-name(.) != 'glossseealso']"/>
    <xsl:if test="glossseealso">
      <p>
        <xsl:variable name="template">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'glossary'"/>
            <xsl:with-param name="name" select="'seealso'"/>
          </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="title">
          <xsl:apply-templates select="glossseealso"/>
        </xsl:variable>
        <xsl:call-template name="substitute-markup">
          <xsl:with-param name="template" select="$template"/>
          <xsl:with-param name="title" select="$title"/>
        </xsl:call-template>
      </p>
    </xsl:if>
  </dd>
</xsl:template>

<xsl:template match="glossseealso">
  <xsl:variable name="otherterm" select="@otherterm"/>
  <xsl:variable name="targets" select="key('id', $otherterm)"/>
  <xsl:variable name="target" select="$targets[1]"/>
  <xsl:variable name="xlink" select="@xlink:href"/>

  <xsl:choose>
    <xsl:when test="$target">
      <a>
        <xsl:apply-templates select="." mode="common.html.attributes"/>
        <xsl:call-template name="id.attribute"/>
        <xsl:attribute name="href">
          <xsl:call-template name="href.target">
            <xsl:with-param name="object" select="$target"/>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:apply-templates select="$target" mode="xref-to"/>
      </a>
    </xsl:when>
    <xsl:when test="$xlink">
      <xsl:call-template name="simple.xlink">
        <xsl:with-param name="content">
          <xsl:apply-templates/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$otherterm != '' and not($target)">
      <xsl:message>
        <xsl:text>Warning: glossseealso @otherterm reference not found: </xsl:text>
        <xsl:value-of select="$otherterm"/>
      </xsl:message>
      <xsl:apply-templates/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates/>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="position() = last()"/>
    <xsl:otherwise>
		<xsl:call-template name="gentext.template">
		  <xsl:with-param name="context" select="'glossary'"/>
		  <xsl:with-param name="name" select="'seealso-separator'"/>
		</xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<!-- Glossary collection -->

<xsl:template match="glossary[@role='auto']" priority="2">
  &setup-language-variable;
  <xsl:variable name="terms" 
                select="//glossterm[not(parent::glossdef)]|//firstterm"/>
  <xsl:variable name="collection" select="document($glossary.collection, .)"/>

  <xsl:call-template name="id.warning"/>

  <xsl:if test="$glossary.collection = ''">
    <xsl:message>
      <xsl:text>Warning: processing automatic glossary </xsl:text>
      <xsl:text>without a glossary.collection file.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="not($collection) and $glossary.collection != ''">
    <xsl:message>
      <xsl:text>Warning: processing automatic glossary but unable to </xsl:text>
      <xsl:text>open glossary.collection file '</xsl:text>
      <xsl:value-of select="$glossary.collection"/>
      <xsl:text>'</xsl:text>
    </xsl:message>
  </xsl:if>

  <div>
    <xsl:apply-templates select="." mode="common.html.attributes"/>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:call-template name="glossary.titlepage"/>

    <xsl:choose>
      <xsl:when test="glossdiv and $collection//glossdiv">
        <xsl:for-each select="$collection//glossdiv">
          <!-- first see if there are any in this div -->
          <xsl:variable name="exist.test">
            <xsl:for-each select="glossentry">
              <xsl:variable name="cterm" select="glossterm"/>
              <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
                <xsl:value-of select="glossterm"/>
              </xsl:if>
            </xsl:for-each>
          </xsl:variable>

          <xsl:if test="$exist.test != ''">
            <xsl:apply-templates select="." mode="auto-glossary">
              <xsl:with-param name="terms" select="$terms"/>
            </xsl:apply-templates>
          </xsl:if>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <dl>
          <xsl:choose>
            <xsl:when test="$glossary.sort != 0">
              <xsl:for-each select="$collection//glossentry">
				<xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
                <xsl:variable name="cterm" select="glossterm"/>
                <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
                  <xsl:apply-templates select="." mode="auto-glossary"/>
                </xsl:if>
              </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
              <xsl:for-each select="$collection//glossentry">
                <xsl:variable name="cterm" select="glossterm"/>
                <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
                  <xsl:apply-templates select="." mode="auto-glossary"/>
                </xsl:if>
              </xsl:for-each>
            </xsl:otherwise>
          </xsl:choose>
        </dl>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:if test="not(parent::article)">
      <xsl:call-template name="process.footnotes"/>
    </xsl:if>
  </div>
</xsl:template>

<xsl:template match="*" mode="auto-glossary">
  <!-- pop back out to the default mode for most elements -->
  <xsl:apply-templates select="."/>
</xsl:template>

<xsl:template match="glossdiv" mode="auto-glossary">
  <xsl:param name="terms" select="."/>

  &setup-language-variable;

  <div>
    <xsl:apply-templates select="." mode="common.html.attributes"/>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:apply-templates select="(glossentry[1]/preceding-sibling::*)"/>

    <dl>
      <xsl:choose>
        <xsl:when test="$glossary.sort != 0">
          <xsl:for-each select="glossentry">
				<xsl:sort lang="{$language}" select="normalize-space(translate(concat(@sortas, glossterm[not(parent::glossentry/@sortas) or parent::glossentry/@sortas = '']), &lowercase;, &uppercase;))"/>
            <xsl:variable name="cterm" select="glossterm"/>
            <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
              <xsl:apply-templates select="." mode="auto-glossary"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:for-each select="glossentry">
            <xsl:variable name="cterm" select="glossterm"/>
            <xsl:if test="$terms[@baseform = $cterm or . = $cterm]">
              <xsl:apply-templates select="." mode="auto-glossary"/>
            </xsl:if>
          </xsl:for-each>
        </xsl:otherwise>
      </xsl:choose>
    </dl>
  </div>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
