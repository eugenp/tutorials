<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                xmlns:dyn="http://exslt.org/dynamic"
                xmlns:saxon="http://icl.com/saxon"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                exclude-result-prefixes="doc dyn saxon"
                version='1.0'>

<!-- ********************************************************************
     $Id: charmap.xsl 7266 2007-08-22 11:58:42Z xmldoc $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->
<doc:reference xmlns="" xml:id="charmap">
  <info>
    <title>Common » Character-Map Template Reference</title>
    <releaseinfo role="meta">
      $Id: charmap.xsl 7266 2007-08-22 11:58:42Z xmldoc $
    </releaseinfo>
  </info>
  <!-- * yes, partintro is a valid child of a reference... -->
  <partintro xml:id="partintro">
    <title>Introduction</title>
    <para>This is technical reference documentation for the
      character-map templates in the DocBook XSL Stylesheets.</para>
    <note>
      <para>These templates are defined in a separate file from the set
        of “common” templates because some of the common templates
        reference DocBook XSL stylesheet parameters, requiring the
        entire set of parameters to be imported/included in any
        stylesheet that imports/includes the common templates.</para>
      <para>The character-map templates don’t import or include
        any DocBook XSL stylesheet parameters, so the
        character-map templates can be used without importing the
        whole set of parameters.</para>
    </note>
    <para>This is not intended to be user documentation. It is
      provided for developers writing customization layers for the
      stylesheets.</para>
  </partintro>
</doc:reference>

<!-- ===================================== -->
<doc:template name="apply-character-map" xmlns="">
  <refpurpose>Applies an XSLT character map</refpurpose>
  <refdescription id="apply-character-map-desc">
    <para>This template applies an <link
      xlink:href="http://www.w3.org/TR/xslt20/#character-maps"
      >XSLT character map</link>; that is, it causes certain
      individual characters to be substituted with strings of one
      or more characters. It is useful mainly for replacing
      multiple “special” characters or symbols in the same target
      content. It uses the value of
      <parameter>map.contents</parameter> to do substitution on
      <parameter>content</parameter>, and then returns the
      modified contents.</para>
    <note>
      <para>This template is a very slightly modified version of
        Jeni Tennison’s <function>replace_strings</function>
        template in the <link
          xlink:href="http://www.dpawson.co.uk/xsl/sect2/StringReplace.html#d9351e13"
          >multiple string replacements</link> section of Dave Pawson’s
        <link xlink:href="http://www.dpawson.co.uk/xsl/index.html"
          >XSLT FAQ</link>.</para>
      <para>The <function>apply-string-subst-map</function>
        template is essentially the same template as the
        <function>apply-character-map</function> template; the
        only difference is that in the map that
        <function>apply-string-subst-map</function> expects, <tag
          class="attribute">oldstring</tag> and <tag
          class="attribute">newstring</tag> attributes are used
        instead of <tag class="attribute">character</tag> and <tag
          class="attribute">string</tag> attributes.</para>
    </note>
  </refdescription>
  <refparameter id="apply-character-map-params">
    <variablelist>
      <varlistentry><term>content</term>
        <listitem>
          <para>The content on which to perform the character-map
            substitution.</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>map.contents</term>
        <listitem>
          <para>A node set of elements, with each element having
            the following attributes:
            <itemizedlist>
              <listitem>
                <simpara><tag class="attribute">character</tag>, a
                  character to be replaced</simpara>
              </listitem>
              <listitem>
                <simpara><tag class="attribute">string</tag>, a
                  string with which to replace <tag
                    class="attribute">character</tag></simpara>
              </listitem>
            </itemizedlist>
          </para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
</doc:template>
<xsl:template name="apply-character-map">
  <xsl:param name="content"/>
  <xsl:param name="map.contents"/>
  <xsl:variable name="replaced_text">
    <xsl:call-template name="string.subst">
      <xsl:with-param name="string" select="$content" />
      <xsl:with-param name="target" 
        select="$map.contents[1]/@character" />
      <xsl:with-param name="replacement" 
        select="$map.contents[1]/@string" />
    </xsl:call-template>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="$map.contents[2]">
      <xsl:call-template name="apply-character-map">
        <xsl:with-param name="content" select="$replaced_text" />
        <xsl:with-param name="map.contents"
          select="$map.contents[position() > 1]" />
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$replaced_text" />
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ===================================== -->
<doc:template name="read-character-map" xmlns="">
  <refpurpose>Reads in all or part of an XSLT character map</refpurpose>
  <refdescription id="read-character-map-desc">
    <para>The XSLT 2.0 specification describes <link
        xlink:href="http://www.w3.org/TR/xslt20/#character-maps"
        >character maps</link> and explains how they may be used
      to allow a specific character appearing in a text or
      attribute node in a final result tree to be substituted by
      a specified string of characters during serialization. The
      <function>read-character-map</function> template provides a
      means for reading and using character maps with XSLT
      1.0-based tools.</para>
    <para>This template reads the character-map contents from
      <parameter>uri</parameter> (in full or in part, depending on
      the value of the <parameter>use.subset</parameter>
      parameter), then passes those contents to the
      <function>apply-character-map</function> template, along with
      <parameter>content</parameter>, the data on which to perform
      the character substitution.</para>
    <para>Using the character map “in part” means that it uses only
      those <tag>output-character</tag> elements that match the
      XPath expression given in the value of the
      <parameter>subset.profile</parameter> parameter. The current
      implementation of that capability here relies on the
      <function>evaluate</function> extension XSLT function.</para>
  </refdescription>
  <refparameter id="read-character-map-params">
    <variablelist>
      <varlistentry><term>use.subset</term>
        <listitem>
          <para>Specifies whether to use a subset of the character
            map instead of the whole map; boolean
            <literal>0</literal> or <literal>1</literal></para>
        </listitem>
      </varlistentry>
      <varlistentry><term>subset.profile</term>
        <listitem>
          <para>XPath expression that specifies what subset of the
            character map to use</para>
        </listitem>
      </varlistentry>
      <varlistentry><term>uri</term>
        <listitem>
          <para>URI for a character map</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>
</doc:template>
<xsl:template name="read-character-map">
  <xsl:param name="use.subset"/>
  <xsl:param name="subset.profile"/>
  <xsl:param name="uri"/>
  <xsl:choose>
    <xsl:when test="$use.subset != 0">
      <!-- *use a subset of the character map instead of the full map -->
      <xsl:choose>
        <!-- * xsltproc and Xalan both support dyn:evaluate() -->
        <xsl:when test="function-available('dyn:evaluate')">
          <xsl:copy-of select="document($uri)//*[local-name()='output-character']
            [dyn:evaluate($subset.profile)]"/>
        </xsl:when>
        <!-- * Saxon has its own evaluate() and doesn't support dyn:evaluate() -->
        <xsl:when test="function-available('saxon:evaluate')">
          <xsl:copy-of select="document($uri)//*[local-name()='output-character']
            [saxon:evaluate($subset.profile)]"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message terminate="yes"
>
Error: To process character-map subsets, you must use an XSLT engine
that supports the evaluate() XSLT extension function. Your XSLT engine
does not support it.
          </xsl:message>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <!-- *value of $use.subset is non-zero, so use the full map -->
      <xsl:copy-of select="document($uri)//*[local-name()='output-character']"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
