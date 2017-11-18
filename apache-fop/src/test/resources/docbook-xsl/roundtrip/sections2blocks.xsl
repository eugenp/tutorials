<?xml version="1.0"?>
<axsl:stylesheet xmlns:axsl="http://www.w3.org/1999/XSL/Transform" xmlns:dbk="http://docbook.org/ns/docbook" xmlns:rnd="http://docbook.org/ns/docbook/roundtrip" version="1.0">
<!--====================================-->
<!--=                                  =-->
<!--=   DO NOT EDIT THIS STYLESHEET    =-->
<!--=                                  =-->
<!--= This stylesheet is generated     =-->
<!--= by makeSubsections.xsl and a     =-->
<!--= mapping specification.           =-->
<!--=                                  =-->
<!--= Revision history:                =-->
<!--=  1.1 2007-01-10 SRB              =-->
<!--=   Output DocBook 5.0.            =-->
<!--=  1.0 2005-11-08 SRB              =-->
<!--=   Initial version.               =-->
<!--=                                  =-->
<!--= $Id: sections2blocks.xsl 8107 2008-08-17 22:39:58Z balls $ =-->
<!--=                                  =-->
<!--====================================-->
  <axsl:output indent="yes"/>
  <axsl:strip-space elements="*"/>
  <axsl:preserve-space elements="dbk:para dbk:emphasis"/>
  <axsl:template match="dbk:appendix |&#10;  dbk:article |&#10;  dbk:book |&#10;  dbk:chapter |&#10;  dbk:part |&#10;  dbk:preface |&#10;  dbk:section |&#10;  dbk:sect1 |&#10;  dbk:sect2 |&#10;  dbk:sect3 |&#10;  dbk:sect4 |&#10;  dbk:sect5">
    <axsl:variable name="subsections" select="dbk:para[@rnd:style = &quot;bibliography&quot; or @rnd:style = &quot;bibliography-title&quot; or @rnd:style = &quot;glossary&quot; or @rnd:style = &quot;glossary-title&quot; or @rnd:style = &quot;qandaset&quot; or @rnd:style = &quot;qandaset-title&quot;]"/>
    <axsl:copy>
      <axsl:apply-templates select="@*"/>
      <axsl:choose>
        <axsl:when test="$subsections">
          <axsl:apply-templates select="$subsections[1]/preceding-sibling::node()"/>
          <axsl:apply-templates select="$subsections[1]" mode="subsections">
            <axsl:with-param name="subsections" select="$subsections[position() != 1]"/>
          </axsl:apply-templates>
        </axsl:when>
        <axsl:when test="dbk:appendix |&#10;  dbk:article |&#10;  dbk:book |&#10;  dbk:chapter |&#10;  dbk:part |&#10;  dbk:preface |&#10;  dbk:section |&#10;  dbk:sect1 |&#10;  dbk:sect2 |&#10;  dbk:sect3 |&#10;  dbk:sect4 |&#10;  dbk:sect5">
          <axsl:apply-templates select="*[self::dbk:appendix |&#10;  self::dbk:article |&#10;  self::dbk:book |&#10;  self::dbk:chapter |&#10;  self::dbk:part |&#10;  self::dbk:preface |&#10;  self::dbk:section |&#10;  self::dbk:sect1 |&#10;  self::dbk:sect2 |&#10;  self::dbk:sect3 |&#10;  self::dbk:sect4 |&#10;  self::dbk:sect5][1]/preceding-sibling::node()"/>
          <axsl:apply-templates select="dbk:appendix |&#10;  dbk:article |&#10;  dbk:book |&#10;  dbk:chapter |&#10;  dbk:part |&#10;  dbk:preface |&#10;  dbk:section |&#10;  dbk:sect1 |&#10;  dbk:sect2 |&#10;  dbk:sect3 |&#10;  dbk:sect4 |&#10;  dbk:sect5"/>
        </axsl:when>
        <axsl:otherwise>
          <axsl:apply-templates/>
        </axsl:otherwise>
      </axsl:choose>
    </axsl:copy>
    <axsl:choose>
      <axsl:when test="following-sibling::*[self::dbk:appendix |&#10;  self::dbk:article |&#10;  self::dbk:book |&#10;  self::dbk:chapter |&#10;  self::dbk:part |&#10;  self::dbk:preface |&#10;  self::dbk:section |&#10;  self::dbk:sect1 |&#10;  self::dbk:sect2 |&#10;  self::dbk:sect3 |&#10;  self::dbk:sect4 |&#10;  self::dbk:sect5] | following-sibling::dbk:para[@rnd:style = &quot;bibliography&quot; or @rnd:style = &quot;bibliography-title&quot; or @rnd:style = &quot;glossary&quot; or @rnd:style = &quot;glossary-title&quot; or @rnd:style = &quot;qandaset&quot; or @rnd:style = &quot;qandaset-title&quot;]">
        <axsl:variable name="nextComponent" select="following-sibling::*[self::dbk:appendix |&#10;  self::dbk:article |&#10;  self::dbk:book |&#10;  self::dbk:chapter |&#10;  self::dbk:part |&#10;  self::dbk:preface |&#10;  self::dbk:section |&#10;  self::dbk:sect1 |&#10;  self::dbk:sect2 |&#10;  self::dbk:sect3 |&#10;  self::dbk:sect4 |&#10;  self::dbk:sect5|self::dbk:para[@rnd:style = &quot;bibliography&quot; or @rnd:style = &quot;bibliography-title&quot; or @rnd:style = &quot;glossary&quot; or @rnd:style = &quot;glossary-title&quot; or @rnd:style = &quot;qandaset&quot; or @rnd:style = &quot;qandaset-title&quot;]][1]"/>
        <axsl:apply-templates select="following-sibling::*[generate-id(following-sibling::*[self::dbk:appendix |&#10;  self::dbk:article |&#10;  self::dbk:book |&#10;  self::dbk:chapter |&#10;  self::dbk:part |&#10;  self::dbk:preface |&#10;  self::dbk:section |&#10;  self::dbk:sect1 |&#10;  self::dbk:sect2 |&#10;  self::dbk:sect3 |&#10;  self::dbk:sect4 |&#10;  self::dbk:sect5|self::dbk:para[@rnd:style = &quot;bibliography&quot; or @rnd:style = &quot;bibliography-title&quot; or @rnd:style = &quot;glossary&quot; or @rnd:style = &quot;glossary-title&quot; or @rnd:style = &quot;qandaset&quot; or @rnd:style = &quot;qandaset-title&quot;]][1]) = generate-id($nextComponent)]"/>
      </axsl:when>
      <axsl:otherwise>
        <axsl:apply-templates select="following-sibling::*"/>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="subsections">
    <axsl:param name="subsections" select="/.."/>
    <axsl:choose>
      <axsl:when test="@rnd:style = &quot;bibliography&quot; or @rnd:style = &quot;bibliography-title&quot;">
        <bibliography xmlns="http://docbook.org/ns/docbook">
          <axsl:call-template name="copy"/>
          <axsl:variable name="bibliodivs" select="following-sibling::dbk:para[@rnd:style = &quot;bibliodiv&quot; or @rnd:style = &quot;bibliodiv-title&quot;]"/>
          <axsl:choose>
            <axsl:when test="$bibliodivs">
              <axsl:apply-templates select="following-sibling::*[1]" mode="bibliodivs">
                <axsl:with-param name="nextSubsection" select="$subsections[1]"/>
                <axsl:with-param name="bibliodivs" select="$bibliodivs[position() != 1]"/>
              </axsl:apply-templates>
            </axsl:when>
            <axsl:otherwise>
              <axsl:apply-templates select="following-sibling::*[1]" mode="terminal">
                <axsl:with-param name="nextSubsection" select="$subsections[1]"/>
              </axsl:apply-templates>
            </axsl:otherwise>
          </axsl:choose>
        </bibliography>
      </axsl:when>
      <axsl:when test="@rnd:style = &quot;glossary&quot; or @rnd:style = &quot;glossary-title&quot;">
        <glossary xmlns="http://docbook.org/ns/docbook">
          <axsl:call-template name="copy"/>
          <axsl:variable name="glossdivs" select="following-sibling::dbk:para[@rnd:style = &quot;glossdiv&quot; or @rnd:style = &quot;glossdiv-title&quot;]"/>
          <axsl:choose>
            <axsl:when test="$glossdivs">
              <axsl:apply-templates select="following-sibling::*[1]" mode="glossdivs">
                <axsl:with-param name="nextSubsection" select="$subsections[1]"/>
                <axsl:with-param name="glossdivs" select="$glossdivs[position() != 1]"/>
              </axsl:apply-templates>
            </axsl:when>
            <axsl:otherwise>
              <axsl:apply-templates select="following-sibling::*[1]" mode="terminal">
                <axsl:with-param name="nextSubsection" select="$subsections[1]"/>
              </axsl:apply-templates>
            </axsl:otherwise>
          </axsl:choose>
        </glossary>
      </axsl:when>
      <axsl:when test="@rnd:style = &quot;qandaset&quot; or @rnd:style = &quot;qandaset-title&quot;">
        <qandaset xmlns="http://docbook.org/ns/docbook">
          <axsl:call-template name="copy"/>
          <axsl:variable name="qandadivs" select="following-sibling::dbk:para[@rnd:style = &quot;qandadiv&quot; or @rnd:style = &quot;qandadiv-title&quot;]"/>
          <axsl:choose>
            <axsl:when test="$qandadivs">
              <axsl:apply-templates select="following-sibling::*[1]" mode="qandadivs">
                <axsl:with-param name="nextSubsection" select="$subsections[1]"/>
                <axsl:with-param name="qandadivs" select="$qandadivs[position() != 1]"/>
              </axsl:apply-templates>
            </axsl:when>
            <axsl:otherwise>
              <axsl:apply-templates select="following-sibling::*[1]" mode="terminal">
                <axsl:with-param name="nextSubsection" select="$subsections[1]"/>
              </axsl:apply-templates>
            </axsl:otherwise>
          </axsl:choose>
        </qandaset>
      </axsl:when>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="*" mode="subsections">
    <axsl:param name="subsections" select="/.."/>
    <axsl:copy>
      <axsl:apply-templates select="@*"/>
      <axsl:apply-templates mode="subsections"/>
    </axsl:copy>
  </axsl:template>
  <axsl:template match="dbk:para" mode="bibliodivs">
    <axsl:param name="nextSubsection" select="/.."/>
    <axsl:param name="bibliodivs" select="/.."/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextSubsection)"/>
      <axsl:when test="@rnd:style = &quot;bibliodiv&quot; or @rnd:style = &quot;bibliodiv-title&quot;">
        <bibliodiv xmlns="http://docbook.org/ns/docbook">
          <axsl:call-template name="copy"/>
          <axsl:apply-templates select="following-sibling::*[1]" mode="terminal">
            <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
            <axsl:with-param name="nextbibliodiv" select="$bibliodivs[1]"/>
          </axsl:apply-templates>
        </bibliodiv>
        <axsl:choose>
          <axsl:when test="$nextSubsection and         $bibliodivs and         count($nextSubsection/preceding-sibling::* | $bibliodivs[1]) = count($nextSubsection/preceding-sibling::*)">
            <axsl:apply-templates select="$bibliodivs[1]" mode="bibliodivs">
              <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
              <axsl:with-param name="bibliodivs" select="$bibliodivs[position() != 1]"/>
            </axsl:apply-templates>
          </axsl:when>
          <axsl:when test="$bibliodivs">
            <axsl:apply-templates select="$bibliodivs[1]" mode="bibliodivs">
              <axsl:with-param name="bibliodivs" select="$bibliodivs[position() != 1]"/>
            </axsl:apply-templates>
          </axsl:when>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="bibliodivs">
          <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="glossdivs">
    <axsl:param name="nextSubsection" select="/.."/>
    <axsl:param name="glossdivs" select="/.."/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextSubsection)"/>
      <axsl:when test="@rnd:style = &quot;glossdiv&quot; or @rnd:style = &quot;glossdiv-title&quot;">
        <glossdiv xmlns="http://docbook.org/ns/docbook">
          <axsl:call-template name="copy"/>
          <axsl:apply-templates select="following-sibling::*[1]" mode="terminal">
            <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
            <axsl:with-param name="nextglossdiv" select="$glossdivs[1]"/>
          </axsl:apply-templates>
        </glossdiv>
        <axsl:choose>
          <axsl:when test="$nextSubsection and         $glossdivs and         count($nextSubsection/preceding-sibling::* | $glossdivs[1]) = count($nextSubsection/preceding-sibling::*)">
            <axsl:apply-templates select="$glossdivs[1]" mode="glossdivs">
              <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
              <axsl:with-param name="glossdivs" select="$glossdivs[position() != 1]"/>
            </axsl:apply-templates>
          </axsl:when>
          <axsl:when test="$glossdivs">
            <axsl:apply-templates select="$glossdivs[1]" mode="glossdivs">
              <axsl:with-param name="glossdivs" select="$glossdivs[position() != 1]"/>
            </axsl:apply-templates>
          </axsl:when>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="glossdivs">
          <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="qandadivs">
    <axsl:param name="nextSubsection" select="/.."/>
    <axsl:param name="qandadivs" select="/.."/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextSubsection)"/>
      <axsl:when test="@rnd:style = &quot;qandadiv&quot; or @rnd:style = &quot;qandadiv-title&quot;">
        <qandadiv xmlns="http://docbook.org/ns/docbook">
          <axsl:call-template name="copy"/>
          <axsl:apply-templates select="following-sibling::*[1]" mode="terminal">
            <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
            <axsl:with-param name="nextqandadiv" select="$qandadivs[1]"/>
          </axsl:apply-templates>
        </qandadiv>
        <axsl:choose>
          <axsl:when test="$nextSubsection and         $qandadivs and         count($nextSubsection/preceding-sibling::* | $qandadivs[1]) = count($nextSubsection/preceding-sibling::*)">
            <axsl:apply-templates select="$qandadivs[1]" mode="qandadivs">
              <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
              <axsl:with-param name="qandadivs" select="$qandadivs[position() != 1]"/>
            </axsl:apply-templates>
          </axsl:when>
          <axsl:when test="$qandadivs">
            <axsl:apply-templates select="$qandadivs[1]" mode="qandadivs">
              <axsl:with-param name="qandadivs" select="$qandadivs[position() != 1]"/>
            </axsl:apply-templates>
          </axsl:when>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="qandadivs">
          <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="*" mode="terminal">
    <axsl:param name="nextSubsection" select="/.."/>
    <axsl:param name="nextbibliodiv" select="/.."/>
    <axsl:param name="nextglossdiv" select="/.."/>
    <axsl:param name="nextqandadiv" select="/.."/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextSubsection)"/>
      <axsl:when test="generate-id() = generate-id($nextbibliodiv)"/>
      <axsl:when test="generate-id() = generate-id($nextglossdiv)"/>
      <axsl:when test="generate-id() = generate-id($nextqandadiv)"/>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="terminal">
          <axsl:with-param name="nextSubsection" select="$nextSubsection"/>
          <axsl:with-param name="nextbibliodiv" select="$nextbibliodiv"/>
          <axsl:with-param name="nextglossdiv" select="$nextglossdiv"/>
          <axsl:with-param name="nextqandadiv" select="$nextqandadiv"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="*">
    <axsl:copy>
      <axsl:apply-templates select="@*"/>
      <axsl:apply-templates/>
    </axsl:copy>
  </axsl:template>
  <axsl:template name="copy">
    <axsl:copy>
      <axsl:apply-templates select="@*"/>
      <axsl:apply-templates/>
    </axsl:copy>
  </axsl:template>
  <axsl:template match="@*">
    <axsl:copy/>
  </axsl:template>
</axsl:stylesheet>
