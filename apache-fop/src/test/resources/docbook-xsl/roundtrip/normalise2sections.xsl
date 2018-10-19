<?xml version="1.0"?>
<axsl:stylesheet xmlns:axsl="http://www.w3.org/1999/XSL/Transform" xmlns:dbk="http://docbook.org/ns/docbook" xmlns:rnd="http://docbook.org/ns/docbook/roundtrip" version="1.0">
<!--====================================-->
<!--=                                  =-->
<!--=   DO NOT EDIT THIS STYLESHEET    =-->
<!--=                                  =-->
<!--= This stylesheet is generated     =-->
<!--= by makeSections.xsl and a        =-->
<!--= mapping specification.           =-->
<!--=                                  =-->
<!--= Revision history:                =-->
<!--=                                  =-->
<!--= $Id: normalise2sections.xsl 8107 2008-08-17 22:39:58Z balls $ =-->
<!--=                                  =-->
<!--====================================-->
  <axsl:output indent="yes"/>
  <axsl:strip-space elements="*"/>
  <axsl:preserve-space elements="dbk:para dbk:emphasis"/>
  <axsl:template match="dbk:article">
    <axsl:copy>
      <axsl:for-each select="@*">
        <axsl:copy/>
      </axsl:for-each>
      <axsl:variable name="books" select="dbk:para[@rnd:style = &quot;book&quot; or @rnd:style = &quot;book-title&quot;]"/>
      <axsl:variable name="toplevel-components" select="dbk:para[@rnd:style = &quot;article&quot; or @rnd:style = &quot;article-title&quot; or @rnd:style = &quot;appendix&quot; or @rnd:style = &quot;appendix-title&quot; or @rnd:style = &quot;chapter&quot; or @rnd:style = &quot;chapter-title&quot; or @rnd:style = &quot;preface&quot; or @rnd:style = &quot;preface-title&quot;]"/>
      <axsl:choose>
        <axsl:when test="$books">
          <axsl:apply-templates select="$books[1]/preceding-sibling::*"/>
          <axsl:apply-templates select="$books[1]" mode="book">
            <axsl:with-param name="books" select="$books[position() != 1]"/>
          </axsl:apply-templates>
        </axsl:when>
        <axsl:when test="$toplevel-components">
          <axsl:apply-templates select="$toplevel-components[1]/preceding-sibling::*"/>
          <axsl:apply-templates select="$toplevel-components[1]" mode="toplevel-component">
            <axsl:with-param name="toplevel-components" select="$toplevel-components[position() != 1]"/>
          </axsl:apply-templates>
        </axsl:when>
        <axsl:otherwise>
          <axsl:apply-templates/>
        </axsl:otherwise>
      </axsl:choose>
    </axsl:copy>
  </axsl:template>
  <axsl:template match="dbk:para" mode="book">
    <axsl:param name="books" select="/.."/>
    <axsl:choose>
      <axsl:when test="$books and (@rnd:style = &quot;book&quot; or @rnd:style = &quot;book-title&quot;)">
        <axsl:call-template name="make-book">
          <axsl:with-param name="books" select="$books"/>
          <axsl:with-param name="book-components" select="$books[1]/preceding-sibling::dbk:para[@rnd:style = &quot;part&quot; or @rnd:style = &quot;part-title&quot; or @rnd:style = &quot;article&quot; or @rnd:style = &quot;article-title&quot; or @rnd:style = &quot;appendix&quot; or @rnd:style = &quot;appendix-title&quot; or @rnd:style = &quot;chapter&quot; or @rnd:style = &quot;chapter-title&quot; or @rnd:style = &quot;preface&quot; or @rnd:style = &quot;preface-title&quot;]"/>
        </axsl:call-template>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="make-book">
          <axsl:with-param name="books" select="$books"/>
          <axsl:with-param name="book-components" select="following-sibling::dbk:para[@rnd:style = &quot;part&quot; or @rnd:style = &quot;part-title&quot; or @rnd:style = &quot;article&quot; or @rnd:style = &quot;article-title&quot; or @rnd:style = &quot;appendix&quot; or @rnd:style = &quot;appendix-title&quot; or @rnd:style = &quot;chapter&quot; or @rnd:style = &quot;chapter-title&quot; or @rnd:style = &quot;preface&quot; or @rnd:style = &quot;preface-title&quot;]"/>
        </axsl:call-template>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template name="make-book">
    <axsl:param name="books" select="/.."/>
    <axsl:param name="book-components" select="/.."/>
<!-- mode toplevel-templates -->
    <book xmlns="http://docbook.org/ns/docbook">
      <axsl:call-template name="copy"/>
      <axsl:apply-templates select="following-sibling::*[1]" mode="book-component">
        <axsl:with-param name="nextbook" select="$books[1]"/>
        <axsl:with-param name="book-components" select="$book-components"/>
      </axsl:apply-templates>
    </book>
    <axsl:apply-templates select="$books[1]" mode="book">
      <axsl:with-param name="books" select="$books[position() != 1]"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="dbk:para" mode="toplevel-component">
    <axsl:param name="toplevel-components" select="/.."/>
    <axsl:choose>
      <axsl:when test="$toplevel-components and (@rnd:style = &quot;article&quot; or @rnd:style = &quot;article-title&quot; or @rnd:style = &quot;appendix&quot; or @rnd:style = &quot;appendix-title&quot; or @rnd:style = &quot;chapter&quot; or @rnd:style = &quot;chapter-title&quot; or @rnd:style = &quot;preface&quot; or @rnd:style = &quot;preface-title&quot;)">
        <axsl:call-template name="make-toplevel-component">
          <axsl:with-param name="toplevel-components" select="$toplevel-components"/>
          <axsl:with-param name="sect1s" select="$toplevel-components[1]/preceding-sibling::dbk:para[@rnd:style = &quot;sect1&quot; or @rnd:style = &quot;sect1-title&quot;]"/>
        </axsl:call-template>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="make-toplevel-component">
          <axsl:with-param name="toplevel-components" select="$toplevel-components"/>
          <axsl:with-param name="sect1s" select="following-sibling::dbk:para[@rnd:style = &quot;sect1&quot; or @rnd:style = &quot;sect1-title&quot;]"/>
        </axsl:call-template>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template name="make-toplevel-component">
    <axsl:param name="toplevel-components" select="/.."/>
    <axsl:param name="sect1s" select="/.."/>
<!-- mode toplevel-templates: group:level - do not create element -->
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect1">
      <axsl:with-param name="nexttoplevel-component" select="$toplevel-components[1]"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
    </axsl:apply-templates>
    <axsl:apply-templates select="$toplevel-components[1]" mode="toplevel-component">
      <axsl:with-param name="toplevel-components" select="$toplevel-components[position() != 1]"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="dbk:para" mode="book-component">
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextbook)"/>
      <axsl:when test="@rnd:style = &quot;part&quot; or @rnd:style = &quot;part-title&quot; or @rnd:style = &quot;article&quot; or @rnd:style = &quot;article-title&quot; or @rnd:style = &quot;appendix&quot; or @rnd:style = &quot;appendix-title&quot; or @rnd:style = &quot;chapter&quot; or @rnd:style = &quot;chapter-title&quot; or @rnd:style = &quot;preface&quot; or @rnd:style = &quot;preface-title&quot;">
        <axsl:variable name="nextbook-component" select="following-sibling::dbk:para[@rnd:style = &quot;part&quot; or @rnd:style = &quot;part-title&quot; or @rnd:style = &quot;article&quot; or @rnd:style = &quot;article-title&quot; or @rnd:style = &quot;appendix&quot; or @rnd:style = &quot;appendix-title&quot; or @rnd:style = &quot;chapter&quot; or @rnd:style = &quot;chapter-title&quot; or @rnd:style = &quot;preface&quot; or @rnd:style = &quot;preface-title&quot;][1]"/>
        <axsl:choose>
          <axsl:when test="$nextbook-component">
            <axsl:variable name="sect1s" select="$nextbook-component/preceding-sibling::dbk:para[@rnd:style = &quot;sect1&quot; or @rnd:style = &quot;sect1-title&quot;]"/>
<!-- mode sections 1 -->
            <book-component xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect1">
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </book-component>
            <axsl:if test="count($book-components|$nextbook-component) = count($book-components)">
              <axsl:apply-templates select="$nextbook-component" mode="book-component">
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:when>
          <axsl:otherwise>
            <axsl:variable name="sect1s" select="following-sibling::dbk:para[@rnd:style = &quot;sect1&quot; or @rnd:style = &quot;sect1-title&quot;]"/>
<!-- mode sections 2 -->
            <book-component xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect1">
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </book-component>
            <axsl:if test="count($book-components|$nextbook-component) = count($book-components)">
              <axsl:apply-templates select="$nextbook-component" mode="book-component">
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:otherwise>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="book-component">
          <axsl:with-param name="nextbook" select="$nextbook"/>
          <axsl:with-param name="book-components" select="$book-components"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="book-sect1">
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextbook-component)"/>
      <axsl:when test="generate-id() = generate-id($nextbook)"/>
      <axsl:when test="@rnd:style = &quot;sect1&quot; or @rnd:style = &quot;sect1-title&quot;">
        <axsl:variable name="nextsect1" select="following-sibling::dbk:para[@rnd:style = &quot;sect1&quot; or @rnd:style = &quot;sect1-title&quot;][1]"/>
        <axsl:choose>
          <axsl:when test="$nextsect1">
            <axsl:variable name="sect2s" select="$nextsect1/preceding-sibling::dbk:para[@rnd:style = &quot;sect2&quot; or @rnd:style = &quot;sect2-title&quot;]"/>
<!-- mode sections 1 -->
            <sect1 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect2">
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </sect1>
            <axsl:if test="$nextsect1 and (not($book-components) or count($sect1s|$nextsect1) = count($sect1s))">
              <axsl:apply-templates select="$nextsect1" mode="book-sect1">
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:when>
          <axsl:otherwise>
            <axsl:variable name="sect2s" select="following-sibling::dbk:para[@rnd:style = &quot;sect2&quot; or @rnd:style = &quot;sect2-title&quot;]"/>
<!-- mode sections 2 -->
            <sect1 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect2">
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </sect1>
            <axsl:if test="$nextsect1 and (not($book-components) or count($sect1s|$nextsect1) = count($sect1s))">
              <axsl:apply-templates select="$nextsect1" mode="book-sect1">
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:otherwise>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect1">
          <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
          <axsl:with-param name="nextbook" select="$nextbook"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
          <axsl:with-param name="book-components" select="$book-components"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="book-sect2">
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nextbook-component)"/>
      <axsl:when test="generate-id() = generate-id($nextbook)"/>
      <axsl:when test="@rnd:style = &quot;sect2&quot; or @rnd:style = &quot;sect2-title&quot;">
        <axsl:variable name="nextsect2" select="following-sibling::dbk:para[@rnd:style = &quot;sect2&quot; or @rnd:style = &quot;sect2-title&quot;][1]"/>
        <axsl:choose>
          <axsl:when test="$nextsect2">
            <axsl:variable name="sect3s" select="$nextsect2/preceding-sibling::dbk:para[@rnd:style = &quot;sect3&quot; or @rnd:style = &quot;sect3-title&quot;]"/>
<!-- mode sections 1 -->
            <sect2 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect3">
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </sect2>
            <axsl:if test="$nextsect2 and (not($sect1s) or count($sect2s|$nextsect2) = count($sect2s))">
              <axsl:apply-templates select="$nextsect2" mode="book-sect2">
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:when>
          <axsl:otherwise>
            <axsl:variable name="sect3s" select="following-sibling::dbk:para[@rnd:style = &quot;sect3&quot; or @rnd:style = &quot;sect3-title&quot;]"/>
<!-- mode sections 2 -->
            <sect2 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect3">
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </sect2>
            <axsl:if test="$nextsect2 and (not($sect1s) or count($sect2s|$nextsect2) = count($sect2s))">
              <axsl:apply-templates select="$nextsect2" mode="book-sect2">
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:otherwise>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect2">
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
          <axsl:with-param name="nextbook" select="$nextbook"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
          <axsl:with-param name="book-components" select="$book-components"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="book-sect3">
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect2)"/>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nextbook-component)"/>
      <axsl:when test="generate-id() = generate-id($nextbook)"/>
      <axsl:when test="@rnd:style = &quot;sect3&quot; or @rnd:style = &quot;sect3-title&quot;">
        <axsl:variable name="nextsect3" select="following-sibling::dbk:para[@rnd:style = &quot;sect3&quot; or @rnd:style = &quot;sect3-title&quot;][1]"/>
        <axsl:choose>
          <axsl:when test="$nextsect3">
            <axsl:variable name="sect4s" select="$nextsect3/preceding-sibling::dbk:para[@rnd:style = &quot;sect4&quot; or @rnd:style = &quot;sect4-title&quot;]"/>
<!-- mode sections 1 -->
            <sect3 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect4">
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </sect3>
            <axsl:if test="$nextsect3 and (not($sect2s) or count($sect3s|$nextsect3) = count($sect3s))">
              <axsl:apply-templates select="$nextsect3" mode="book-sect3">
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:when>
          <axsl:otherwise>
            <axsl:variable name="sect4s" select="following-sibling::dbk:para[@rnd:style = &quot;sect4&quot; or @rnd:style = &quot;sect4-title&quot;]"/>
<!-- mode sections 2 -->
            <sect3 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect4">
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </sect3>
            <axsl:if test="$nextsect3 and (not($sect2s) or count($sect3s|$nextsect3) = count($sect3s))">
              <axsl:apply-templates select="$nextsect3" mode="book-sect3">
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:otherwise>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect3">
          <axsl:with-param name="nextsect2" select="$nextsect2"/>
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
          <axsl:with-param name="nextbook" select="$nextbook"/>
          <axsl:with-param name="sect3s" select="$sect3s"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
          <axsl:with-param name="book-components" select="$book-components"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="book-sect4">
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect3)"/>
      <axsl:when test="generate-id() = generate-id($nextsect2)"/>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nextbook-component)"/>
      <axsl:when test="generate-id() = generate-id($nextbook)"/>
      <axsl:when test="@rnd:style = &quot;sect4&quot; or @rnd:style = &quot;sect4-title&quot;">
        <axsl:variable name="nextsect4" select="following-sibling::dbk:para[@rnd:style = &quot;sect4&quot; or @rnd:style = &quot;sect4-title&quot;][1]"/>
        <axsl:choose>
          <axsl:when test="$nextsect4">
            <axsl:variable name="sect5s" select="$nextsect4/preceding-sibling::dbk:para[@rnd:style = &quot;sect5&quot; or @rnd:style = &quot;sect5-title&quot;]"/>
<!-- mode sections 1 -->
            <sect4 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect5">
                <axsl:with-param name="nextsect4" select="$nextsect4"/>
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect5s" select="$sect5s"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </sect4>
            <axsl:if test="$nextsect4 and (not($sect3s) or count($sect4s|$nextsect4) = count($sect4s))">
              <axsl:apply-templates select="$nextsect4" mode="book-sect4">
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:when>
          <axsl:otherwise>
            <axsl:variable name="sect5s" select="following-sibling::dbk:para[@rnd:style = &quot;sect5&quot; or @rnd:style = &quot;sect5-title&quot;]"/>
<!-- mode sections 2 -->
            <sect4 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect5">
                <axsl:with-param name="nextsect4" select="$nextsect4"/>
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect5s" select="$sect5s"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </sect4>
            <axsl:if test="$nextsect4 and (not($sect3s) or count($sect4s|$nextsect4) = count($sect4s))">
              <axsl:apply-templates select="$nextsect4" mode="book-sect4">
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
                <axsl:with-param name="nextbook" select="$nextbook"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
                <axsl:with-param name="book-components" select="$book-components"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:otherwise>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect4">
          <axsl:with-param name="nextsect3" select="$nextsect3"/>
          <axsl:with-param name="nextsect2" select="$nextsect2"/>
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
          <axsl:with-param name="nextbook" select="$nextbook"/>
          <axsl:with-param name="sect4s" select="$sect4s"/>
          <axsl:with-param name="sect3s" select="$sect3s"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
          <axsl:with-param name="book-components" select="$book-components"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="book-sect5">
    <axsl:param name="nextsect4" select="/.."/>
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect5s"/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect4)"/>
      <axsl:when test="generate-id() = generate-id($nextsect3)"/>
      <axsl:when test="generate-id() = generate-id($nextsect2)"/>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nextbook-component)"/>
      <axsl:when test="generate-id() = generate-id($nextbook)"/>
      <axsl:when test="@rnd:style = &quot;sect5&quot; or @rnd:style = &quot;sect5-title&quot;">
        <axsl:variable name="nextsect5" select="following-sibling::dbk:para[@rnd:style = &quot;sect5&quot; or @rnd:style = &quot;sect5-title&quot;][1]"/>
<!-- mode sections 3 -->
        <sect5 xmlns="http://docbook.org/ns/docbook">
          <axsl:call-template name="copy"/>
          <axsl:apply-templates select="following-sibling::*[1]" mode="book-terminal">
            <axsl:with-param name="nextsect5" select="$nextsect5"/>
            <axsl:with-param name="nextsect4" select="$nextsect4"/>
            <axsl:with-param name="nextsect3" select="$nextsect3"/>
            <axsl:with-param name="nextsect2" select="$nextsect2"/>
            <axsl:with-param name="nextsect1" select="$nextsect1"/>
            <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
            <axsl:with-param name="nextbook" select="$nextbook"/>
            <axsl:with-param name="sect5s" select="$sect5s"/>
            <axsl:with-param name="sect4s" select="$sect4s"/>
            <axsl:with-param name="sect3s" select="$sect3s"/>
            <axsl:with-param name="sect2s" select="$sect2s"/>
            <axsl:with-param name="sect1s" select="$sect1s"/>
            <axsl:with-param name="book-components" select="$book-components"/>
          </axsl:apply-templates>
        </sect5>
        <axsl:if test="$nextsect5 and (not($sect4s) or count($sect5s|$nextsect5) = count($sect5s))">
          <axsl:apply-templates select="$nextsect5" mode="book-sect5">
            <axsl:with-param name="nextsect4" select="$nextsect4"/>
            <axsl:with-param name="nextsect3" select="$nextsect3"/>
            <axsl:with-param name="nextsect2" select="$nextsect2"/>
            <axsl:with-param name="nextsect1" select="$nextsect1"/>
            <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
            <axsl:with-param name="nextbook" select="$nextbook"/>
            <axsl:with-param name="sect5s" select="$sect5s"/>
            <axsl:with-param name="sect4s" select="$sect4s"/>
            <axsl:with-param name="sect3s" select="$sect3s"/>
            <axsl:with-param name="sect2s" select="$sect2s"/>
            <axsl:with-param name="sect1s" select="$sect1s"/>
            <axsl:with-param name="book-components" select="$book-components"/>
          </axsl:apply-templates>
        </axsl:if>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect5">
          <axsl:with-param name="nextsect4" select="$nextsect4"/>
          <axsl:with-param name="nextsect3" select="$nextsect3"/>
          <axsl:with-param name="nextsect2" select="$nextsect2"/>
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
          <axsl:with-param name="nextbook" select="$nextbook"/>
          <axsl:with-param name="sect5s" select="$sect5s"/>
          <axsl:with-param name="sect4s" select="$sect4s"/>
          <axsl:with-param name="sect3s" select="$sect3s"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
          <axsl:with-param name="book-components" select="$book-components"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="toplevel-sect1">
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nexttoplevel-component)"/>
      <axsl:when test="@rnd:style = &quot;sect1&quot; or @rnd:style = &quot;sect1-title&quot;">
        <axsl:variable name="nextsect1" select="following-sibling::dbk:para[@rnd:style = &quot;sect1&quot; or @rnd:style = &quot;sect1-title&quot;][1]"/>
        <axsl:choose>
          <axsl:when test="$nextsect1">
            <axsl:variable name="sect2s" select="$nextsect1/preceding-sibling::dbk:para[@rnd:style = &quot;sect2&quot; or @rnd:style = &quot;sect2-title&quot;]"/>
<!-- mode sections 1 -->
            <sect1 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect2">
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </sect1>
            <axsl:if test="count($sect1s|$nextsect1) = count($sect1s)">
              <axsl:apply-templates select="$nextsect1" mode="toplevel-sect1">
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:when>
          <axsl:otherwise>
            <axsl:variable name="sect2s" select="following-sibling::dbk:para[@rnd:style = &quot;sect2&quot; or @rnd:style = &quot;sect2-title&quot;]"/>
<!-- mode sections 2 -->
            <sect1 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect2">
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </sect1>
            <axsl:if test="count($sect1s|$nextsect1) = count($sect1s)">
              <axsl:apply-templates select="$nextsect1" mode="toplevel-sect1">
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:otherwise>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect1">
          <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="toplevel-sect2">
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nexttoplevel-component)"/>
      <axsl:when test="@rnd:style = &quot;sect2&quot; or @rnd:style = &quot;sect2-title&quot;">
        <axsl:variable name="nextsect2" select="following-sibling::dbk:para[@rnd:style = &quot;sect2&quot; or @rnd:style = &quot;sect2-title&quot;][1]"/>
        <axsl:choose>
          <axsl:when test="$nextsect2">
            <axsl:variable name="sect3s" select="$nextsect2/preceding-sibling::dbk:para[@rnd:style = &quot;sect3&quot; or @rnd:style = &quot;sect3-title&quot;]"/>
<!-- mode sections 1 -->
            <sect2 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect3">
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </sect2>
            <axsl:if test="$nextsect2 and (not($sect1s) or count($sect2s|$nextsect2) = count($sect2s))">
              <axsl:apply-templates select="$nextsect2" mode="toplevel-sect2">
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:when>
          <axsl:otherwise>
            <axsl:variable name="sect3s" select="following-sibling::dbk:para[@rnd:style = &quot;sect3&quot; or @rnd:style = &quot;sect3-title&quot;]"/>
<!-- mode sections 2 -->
            <sect2 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect3">
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </sect2>
            <axsl:if test="$nextsect2 and (not($sect1s) or count($sect2s|$nextsect2) = count($sect2s))">
              <axsl:apply-templates select="$nextsect2" mode="toplevel-sect2">
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:otherwise>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect2">
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="toplevel-sect3">
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect2)"/>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nexttoplevel-component)"/>
      <axsl:when test="@rnd:style = &quot;sect3&quot; or @rnd:style = &quot;sect3-title&quot;">
        <axsl:variable name="nextsect3" select="following-sibling::dbk:para[@rnd:style = &quot;sect3&quot; or @rnd:style = &quot;sect3-title&quot;][1]"/>
        <axsl:choose>
          <axsl:when test="$nextsect3">
            <axsl:variable name="sect4s" select="$nextsect3/preceding-sibling::dbk:para[@rnd:style = &quot;sect4&quot; or @rnd:style = &quot;sect4-title&quot;]"/>
<!-- mode sections 1 -->
            <sect3 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect4">
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </sect3>
            <axsl:if test="$nextsect3 and (not($sect2s) or count($sect3s|$nextsect3) = count($sect3s))">
              <axsl:apply-templates select="$nextsect3" mode="toplevel-sect3">
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:when>
          <axsl:otherwise>
            <axsl:variable name="sect4s" select="following-sibling::dbk:para[@rnd:style = &quot;sect4&quot; or @rnd:style = &quot;sect4-title&quot;]"/>
<!-- mode sections 2 -->
            <sect3 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect4">
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </sect3>
            <axsl:if test="$nextsect3 and (not($sect2s) or count($sect3s|$nextsect3) = count($sect3s))">
              <axsl:apply-templates select="$nextsect3" mode="toplevel-sect3">
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:otherwise>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect3">
          <axsl:with-param name="nextsect2" select="$nextsect2"/>
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
          <axsl:with-param name="sect3s" select="$sect3s"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="toplevel-sect4">
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect3)"/>
      <axsl:when test="generate-id() = generate-id($nextsect2)"/>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nexttoplevel-component)"/>
      <axsl:when test="@rnd:style = &quot;sect4&quot; or @rnd:style = &quot;sect4-title&quot;">
        <axsl:variable name="nextsect4" select="following-sibling::dbk:para[@rnd:style = &quot;sect4&quot; or @rnd:style = &quot;sect4-title&quot;][1]"/>
        <axsl:choose>
          <axsl:when test="$nextsect4">
            <axsl:variable name="sect5s" select="$nextsect4/preceding-sibling::dbk:para[@rnd:style = &quot;sect5&quot; or @rnd:style = &quot;sect5-title&quot;]"/>
<!-- mode sections 1 -->
            <sect4 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect5">
                <axsl:with-param name="nextsect4" select="$nextsect4"/>
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect5s" select="$sect5s"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </sect4>
            <axsl:if test="$nextsect4 and (not($sect3s) or count($sect4s|$nextsect4) = count($sect4s))">
              <axsl:apply-templates select="$nextsect4" mode="toplevel-sect4">
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:when>
          <axsl:otherwise>
            <axsl:variable name="sect5s" select="following-sibling::dbk:para[@rnd:style = &quot;sect5&quot; or @rnd:style = &quot;sect5-title&quot;]"/>
<!-- mode sections 2 -->
            <sect4 xmlns="http://docbook.org/ns/docbook">
              <axsl:call-template name="copy"/>
              <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect5">
                <axsl:with-param name="nextsect4" select="$nextsect4"/>
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect5s" select="$sect5s"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </sect4>
            <axsl:if test="$nextsect4 and (not($sect3s) or count($sect4s|$nextsect4) = count($sect4s))">
              <axsl:apply-templates select="$nextsect4" mode="toplevel-sect4">
                <axsl:with-param name="nextsect3" select="$nextsect3"/>
                <axsl:with-param name="nextsect2" select="$nextsect2"/>
                <axsl:with-param name="nextsect1" select="$nextsect1"/>
                <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
                <axsl:with-param name="sect4s" select="$sect4s"/>
                <axsl:with-param name="sect3s" select="$sect3s"/>
                <axsl:with-param name="sect2s" select="$sect2s"/>
                <axsl:with-param name="sect1s" select="$sect1s"/>
              </axsl:apply-templates>
            </axsl:if>
          </axsl:otherwise>
        </axsl:choose>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect4">
          <axsl:with-param name="nextsect3" select="$nextsect3"/>
          <axsl:with-param name="nextsect2" select="$nextsect2"/>
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
          <axsl:with-param name="sect4s" select="$sect4s"/>
          <axsl:with-param name="sect3s" select="$sect3s"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="toplevel-sect5">
    <axsl:param name="nextsect4" select="/.."/>
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect5s"/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect4)"/>
      <axsl:when test="generate-id() = generate-id($nextsect3)"/>
      <axsl:when test="generate-id() = generate-id($nextsect2)"/>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nexttoplevel-component)"/>
      <axsl:when test="@rnd:style = &quot;sect5&quot; or @rnd:style = &quot;sect5-title&quot;">
        <axsl:variable name="nextsect5" select="following-sibling::dbk:para[@rnd:style = &quot;sect5&quot; or @rnd:style = &quot;sect5-title&quot;][1]"/>
<!-- mode sections 3 -->
        <sect5 xmlns="http://docbook.org/ns/docbook">
          <axsl:call-template name="copy"/>
          <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-terminal">
            <axsl:with-param name="nextsect5" select="$nextsect5"/>
            <axsl:with-param name="nextsect4" select="$nextsect4"/>
            <axsl:with-param name="nextsect3" select="$nextsect3"/>
            <axsl:with-param name="nextsect2" select="$nextsect2"/>
            <axsl:with-param name="nextsect1" select="$nextsect1"/>
            <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
            <axsl:with-param name="sect5s" select="$sect5s"/>
            <axsl:with-param name="sect4s" select="$sect4s"/>
            <axsl:with-param name="sect3s" select="$sect3s"/>
            <axsl:with-param name="sect2s" select="$sect2s"/>
            <axsl:with-param name="sect1s" select="$sect1s"/>
          </axsl:apply-templates>
        </sect5>
        <axsl:if test="$nextsect5 and (not($sect4s) or count($sect5s|$nextsect5) = count($sect5s))">
          <axsl:apply-templates select="$nextsect5" mode="toplevel-sect5">
            <axsl:with-param name="nextsect4" select="$nextsect4"/>
            <axsl:with-param name="nextsect3" select="$nextsect3"/>
            <axsl:with-param name="nextsect2" select="$nextsect2"/>
            <axsl:with-param name="nextsect1" select="$nextsect1"/>
            <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
            <axsl:with-param name="sect5s" select="$sect5s"/>
            <axsl:with-param name="sect4s" select="$sect4s"/>
            <axsl:with-param name="sect3s" select="$sect3s"/>
            <axsl:with-param name="sect2s" select="$sect2s"/>
            <axsl:with-param name="sect1s" select="$sect1s"/>
          </axsl:apply-templates>
        </axsl:if>
      </axsl:when>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect5">
          <axsl:with-param name="nextsect4" select="$nextsect4"/>
          <axsl:with-param name="nextsect3" select="$nextsect3"/>
          <axsl:with-param name="nextsect2" select="$nextsect2"/>
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
          <axsl:with-param name="sect5s" select="$sect5s"/>
          <axsl:with-param name="sect4s" select="$sect4s"/>
          <axsl:with-param name="sect3s" select="$sect3s"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="book-terminal">
    <axsl:param name="nextsect5" select="/.."/>
    <axsl:param name="nextsect4" select="/.."/>
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect5s"/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect5)"/>
      <axsl:when test="generate-id() = generate-id($nextsect4)"/>
      <axsl:when test="generate-id() = generate-id($nextsect3)"/>
      <axsl:when test="generate-id() = generate-id($nextsect2)"/>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nextbook-component)"/>
      <axsl:when test="generate-id() = generate-id($nextbook)"/>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="book-terminal">
          <axsl:with-param name="nextsect5" select="$nextsect5"/>
          <axsl:with-param name="nextsect4" select="$nextsect4"/>
          <axsl:with-param name="nextsect3" select="$nextsect3"/>
          <axsl:with-param name="nextsect2" select="$nextsect2"/>
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
          <axsl:with-param name="nextbook" select="$nextbook"/>
          <axsl:with-param name="sect5s" select="$sect5s"/>
          <axsl:with-param name="sect4s" select="$sect4s"/>
          <axsl:with-param name="sect3s" select="$sect3s"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
          <axsl:with-param name="book-components" select="$book-components"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="dbk:para" mode="toplevel-terminal">
    <axsl:param name="nextsect5" select="/.."/>
    <axsl:param name="nextsect4" select="/.."/>
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect5s"/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:choose>
      <axsl:when test="generate-id() = generate-id($nextsect5)"/>
      <axsl:when test="generate-id() = generate-id($nextsect4)"/>
      <axsl:when test="generate-id() = generate-id($nextsect3)"/>
      <axsl:when test="generate-id() = generate-id($nextsect2)"/>
      <axsl:when test="generate-id() = generate-id($nextsect1)"/>
      <axsl:when test="generate-id() = generate-id($nexttoplevel-component)"/>
      <axsl:otherwise>
        <axsl:call-template name="copy"/>
        <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-terminal">
          <axsl:with-param name="nextsect5" select="$nextsect5"/>
          <axsl:with-param name="nextsect4" select="$nextsect4"/>
          <axsl:with-param name="nextsect3" select="$nextsect3"/>
          <axsl:with-param name="nextsect2" select="$nextsect2"/>
          <axsl:with-param name="nextsect1" select="$nextsect1"/>
          <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
          <axsl:with-param name="sect5s" select="$sect5s"/>
          <axsl:with-param name="sect4s" select="$sect4s"/>
          <axsl:with-param name="sect3s" select="$sect3s"/>
          <axsl:with-param name="sect2s" select="$sect2s"/>
          <axsl:with-param name="sect1s" select="$sect1s"/>
        </axsl:apply-templates>
      </axsl:otherwise>
    </axsl:choose>
  </axsl:template>
  <axsl:template match="*">
    <axsl:call-template name="copy"/>
  </axsl:template>
  <axsl:template match="*" mode="book">
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="book"/>
  </axsl:template>
  <axsl:template match="*" mode="book-component">
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="book-component">
      <axsl:with-param name="nextbook" select="$nextbook"/>
      <axsl:with-param name="book-components" select="$book-components"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="book-sect1">
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect1">
      <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
      <axsl:with-param name="nextbook" select="$nextbook"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
      <axsl:with-param name="book-components" select="$book-components"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="book-sect2">
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect2">
      <axsl:with-param name="nextsect1" select="$nextsect1"/>
      <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
      <axsl:with-param name="nextbook" select="$nextbook"/>
      <axsl:with-param name="sect2s" select="$sect2s"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
      <axsl:with-param name="book-components" select="$book-components"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="book-sect3">
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect3">
      <axsl:with-param name="nextsect2" select="$nextsect2"/>
      <axsl:with-param name="nextsect1" select="$nextsect1"/>
      <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
      <axsl:with-param name="nextbook" select="$nextbook"/>
      <axsl:with-param name="sect3s" select="$sect3s"/>
      <axsl:with-param name="sect2s" select="$sect2s"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
      <axsl:with-param name="book-components" select="$book-components"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="book-sect4">
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect4">
      <axsl:with-param name="nextsect3" select="$nextsect3"/>
      <axsl:with-param name="nextsect2" select="$nextsect2"/>
      <axsl:with-param name="nextsect1" select="$nextsect1"/>
      <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
      <axsl:with-param name="nextbook" select="$nextbook"/>
      <axsl:with-param name="sect4s" select="$sect4s"/>
      <axsl:with-param name="sect3s" select="$sect3s"/>
      <axsl:with-param name="sect2s" select="$sect2s"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
      <axsl:with-param name="book-components" select="$book-components"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="book-sect5">
    <axsl:param name="nextsect4" select="/.."/>
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nextbook-component" select="/.."/>
    <axsl:param name="nextbook" select="/.."/>
    <axsl:param select="/.." name="sect5s"/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:param select="/.." name="book-components"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="book-sect5">
      <axsl:with-param name="nextsect4" select="$nextsect4"/>
      <axsl:with-param name="nextsect3" select="$nextsect3"/>
      <axsl:with-param name="nextsect2" select="$nextsect2"/>
      <axsl:with-param name="nextsect1" select="$nextsect1"/>
      <axsl:with-param name="nextbook-component" select="$nextbook-component"/>
      <axsl:with-param name="nextbook" select="$nextbook"/>
      <axsl:with-param name="sect5s" select="$sect5s"/>
      <axsl:with-param name="sect4s" select="$sect4s"/>
      <axsl:with-param name="sect3s" select="$sect3s"/>
      <axsl:with-param name="sect2s" select="$sect2s"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
      <axsl:with-param name="book-components" select="$book-components"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="toplevel-component">
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-component"/>
  </axsl:template>
  <axsl:template match="*" mode="toplevel-sect1">
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect1">
      <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="toplevel-sect2">
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect2">
      <axsl:with-param name="nextsect1" select="$nextsect1"/>
      <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
      <axsl:with-param name="sect2s" select="$sect2s"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="toplevel-sect3">
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect3">
      <axsl:with-param name="nextsect2" select="$nextsect2"/>
      <axsl:with-param name="nextsect1" select="$nextsect1"/>
      <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
      <axsl:with-param name="sect3s" select="$sect3s"/>
      <axsl:with-param name="sect2s" select="$sect2s"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="toplevel-sect4">
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect4">
      <axsl:with-param name="nextsect3" select="$nextsect3"/>
      <axsl:with-param name="nextsect2" select="$nextsect2"/>
      <axsl:with-param name="nextsect1" select="$nextsect1"/>
      <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
      <axsl:with-param name="sect4s" select="$sect4s"/>
      <axsl:with-param name="sect3s" select="$sect3s"/>
      <axsl:with-param name="sect2s" select="$sect2s"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template match="*" mode="toplevel-sect5">
    <axsl:param name="nextsect4" select="/.."/>
    <axsl:param name="nextsect3" select="/.."/>
    <axsl:param name="nextsect2" select="/.."/>
    <axsl:param name="nextsect1" select="/.."/>
    <axsl:param name="nexttoplevel-component" select="/.."/>
    <axsl:param select="/.." name="sect5s"/>
    <axsl:param select="/.." name="sect4s"/>
    <axsl:param select="/.." name="sect3s"/>
    <axsl:param select="/.." name="sect2s"/>
    <axsl:param select="/.." name="sect1s"/>
    <axsl:call-template name="copy"/>
    <axsl:apply-templates select="following-sibling::*[1]" mode="toplevel-sect5">
      <axsl:with-param name="nextsect4" select="$nextsect4"/>
      <axsl:with-param name="nextsect3" select="$nextsect3"/>
      <axsl:with-param name="nextsect2" select="$nextsect2"/>
      <axsl:with-param name="nextsect1" select="$nextsect1"/>
      <axsl:with-param name="nexttoplevel-component" select="$nexttoplevel-component"/>
      <axsl:with-param name="sect5s" select="$sect5s"/>
      <axsl:with-param name="sect4s" select="$sect4s"/>
      <axsl:with-param name="sect3s" select="$sect3s"/>
      <axsl:with-param name="sect2s" select="$sect2s"/>
      <axsl:with-param name="sect1s" select="$sect1s"/>
    </axsl:apply-templates>
  </axsl:template>
  <axsl:template name="copy">
    <axsl:copy>
      <axsl:for-each select="@*">
        <axsl:copy/>
      </axsl:for-each>
      <axsl:apply-templates/>
    </axsl:copy>
  </axsl:template>
</axsl:stylesheet>
