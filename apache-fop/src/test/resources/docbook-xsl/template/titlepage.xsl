<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:t="http://nwalsh.com/docbook/xsl/template/1.0"
                xmlns:param="http://nwalsh.com/docbook/xsl/template/1.0/param"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:exsl="http://exslt.org/common"
                exclude-result-prefixes="doc t param exsl"
                version='1.0'>

<!-- ********************************************************************
     $Id: titlepage.xsl 9600 2012-09-11 12:12:09Z kosek $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<!-- Namespace for wrapper elements. Please set it for XHTML. -->
<xsl:param name="ns">
  <!-- Guess correct setting for cases where parameter is not supplied -->
  <xsl:choose>
    <xsl:when test="//*[namespace-uri() = 'http://www.w3.org/1999/XSL/Format']">http://www.w3.org/1999/XSL/Format</xsl:when>
    <xsl:when test="//*[namespace-uri() = 'http://www.w3.org/1999/xhtml']">http://www.w3.org/1999/xhtml</xsl:when>
  </xsl:choose>
</xsl:param>

<xsl:template match="/">
  <xsl:text>&#x0a;</xsl:text>
  <xsl:apply-templates/>
  <xsl:text>&#x0a;</xsl:text>
</xsl:template>

<doc:reference xmlns="" xml:id="template">
  <?dbhtml dir="template"?>
  <?dbhtml filename="index.html"?>
  <info>
    <title>Titlepage Template Stylesheet Reference</title>
    <releaseinfo role="meta">
      $Id: titlepage.xsl 9600 2012-09-11 12:12:09Z kosek $
    </releaseinfo>
  </info>
  <partintro xml:id="intro_partintro">
    <title>Introduction</title>
    <para>This is technical reference documentation for the
      “titlepage” templates in the DocBook XSL Stylesheets.</para>
    <para>This is not intended to be user documentation.  It is
      provided for developers writing customization layers for the
      stylesheets.</para>
  </partintro>
</doc:reference>

<!-- ==================================================================== -->

<xsl:preserve-space elements="*"/>
<xsl:strip-space elements="xsl:* t:*"/>

<!-- ==================================================================== -->

<doc:template match="t:templates" xmlns="" id="templates">
<refpurpose>Construct a stylesheet for the templates provided</refpurpose>

<refdescription>
<para>The <literal>t:templates</literal> element is the root of a
set of templates. This template creates an appropriate
<literal>xsl:stylesheet</literal> for the templates.</para>

<para>If the <literal>t:templates</literal> element has a
<literal>base-stylesheet</literal> attribute, an
<literal>xsl:import</literal> statement is constructed for it.</para>
</refdescription>
</doc:template>

<xsl:template match="t:templates">
  <xsl:element name="xsl:stylesheet">

    <xsl:for-each select="document('')/xsl:stylesheet/namespace::exsl">
      <xsl:copy/>
    </xsl:for-each>

    <xsl:attribute name="version">1.0</xsl:attribute>
    <xsl:attribute name="exclude-result-prefixes">exsl</xsl:attribute>

    <xsl:text>&#xA;&#xA;</xsl:text>
    <xsl:comment>
      <xsl:text> This stylesheet was created by </xsl:text>
      <xsl:text>template/titlepage.xsl</xsl:text>
    </xsl:comment>

    <xsl:if test="@t:base-stylesheet">
      <xsl:text>&#xA;&#xA;</xsl:text>
      <xsl:element name="xsl:import">
        <xsl:attribute name="href">
          <xsl:value-of select="@t:base-stylesheet"/>
        </xsl:attribute>
      </xsl:element>
    </xsl:if>

    <xsl:apply-templates/>

    <xsl:text>&#xA;&#xA;</xsl:text>
  </xsl:element>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="xsl:*" xmlns="" id="star">
<refpurpose>Copy xsl: elements straight through</refpurpose>

<refdescription>
<para>This template simply copies the xsl: elements
straight through into the result tree.</para>
</refdescription>
</doc:template>

<xsl:template match="xsl:*">
  <xsl:apply-templates select="." mode="copy"/>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="t:titlepage" xmlns="" id="titlepage">
<refpurpose>Create the templates necessary to construct a title page</refpurpose>

<refdescription>
<para>The <literal>t:titlepage</literal> element creates a set of
templates for processing the titlepage for an element. The
<quote>root</quote> of this template set is the template named
<quote><literal>wrapper.titlepage</literal></quote>. That is the
template that should be called to generate the title page.
</para>

<para>The <literal>t:titlepage</literal> element has three attributes:

<variablelist>
<varlistentry><term>element</term>
<listitem><para>The name of the source document element for which
these templates apply. In other words, to make a title page for the
<tag>article</tag> element, set the
<tag class="attribute">element</tag> attribute to
<quote><literal>article</literal></quote>. This attribute is required.
</para></listitem>
</varlistentry>
<varlistentry><term>wrapper</term>
<listitem><para>The entire title page can be wrapped with an element.
This attribute identifies that element.
</para></listitem>
</varlistentry>
<varlistentry><term>class</term>
<listitem><para>If the <tag class="attribute">class</tag> attribute
is set, a <tag class="attribute">class</tag> attribute with this
value will be added to the wrapper element that surrounds the entire
title page.
</para></listitem>
</varlistentry>
</variablelist>
</para>

<para>Any other attributes are copied through literally to the
wrapper element.</para>

<para>The content of a <literal>t:titlepage</literal> is one or
more <literal>t:titlepage-content</literal>,
<literal>t:titlepage-separator</literal>, and
<literal>t:titlepage-before</literal> elements.</para>

<para>Each of these elements may be provided for the <quote>recto</quote>
and <quote>verso</quote> sides of the title page.</para>

</refdescription>
</doc:template>

<xsl:template match="t:titlepage">
  <!-- process the children to make the templates for the content,
       separator, and before elements -->
  <xsl:apply-templates/>

  <!-- output the title page template -->
  <xsl:text>&#xA;&#xA;</xsl:text>
  <xsl:element name="xsl:template">
    <xsl:attribute name="name">
      <xsl:value-of select="@t:element"/>
      <xsl:text>.titlepage</xsl:text>
    </xsl:attribute>
    <xsl:text>&#xA;  </xsl:text>
    <xsl:element name="{@t:wrapper}" namespace="{$ns}">
      <xsl:apply-templates select="@*" mode="copy.literal.atts"/>
      <xsl:text>&#xA;    </xsl:text>
      <xsl:element name="xsl:variable">
        <xsl:attribute name="name">recto.content</xsl:attribute>
        <xsl:text>&#xA;      </xsl:text>
        <xsl:element name="xsl:call-template">
          <xsl:attribute name="name">
            <xsl:value-of select="@t:element"/>
            <xsl:text>.titlepage.before.recto</xsl:text>
          </xsl:attribute>
        </xsl:element>
        <xsl:text>&#xA;      </xsl:text>
        <xsl:element name="xsl:call-template">
          <xsl:attribute name="name">
            <xsl:value-of select="@t:element"/>
            <xsl:text>.titlepage.recto</xsl:text>
          </xsl:attribute>
        </xsl:element>
        <xsl:text>&#xA;    </xsl:text>
      </xsl:element>
      <xsl:text>&#xA;    </xsl:text>
      <xsl:element name="xsl:variable">
	<xsl:attribute name="name">recto.elements.count</xsl:attribute>
	<xsl:text>&#xA;      </xsl:text>
	<xsl:element name="xsl:choose">
	  <xsl:text>&#xA;        </xsl:text>
	  <xsl:element name="xsl:when">
	    <xsl:attribute name="test">function-available('exsl:node-set')</xsl:attribute>
	    <xsl:element name="xsl:value-of">
	      <xsl:attribute name="select">count(exsl:node-set($recto.content)/*)</xsl:attribute>
	    </xsl:element>
	  </xsl:element>
	  <xsl:text>&#xA;        </xsl:text>
	  <xsl:element name="xsl:when">
	    <xsl:attribute name="test">contains(system-property('xsl:vendor'), 'Apache Software Foundation')</xsl:attribute>
	    <xsl:text>&#xA;          </xsl:text>
	    <xsl:comment>Xalan quirk</xsl:comment>
	    <xsl:element name="xsl:value-of">
	      <xsl:attribute name="select">count(exsl:node-set($recto.content)/*)</xsl:attribute>
	    </xsl:element>
	  </xsl:element>
	  <xsl:text>&#xA;        </xsl:text>
	  <xsl:element name="xsl:otherwise">
	    <xsl:text>1</xsl:text>
	  </xsl:element>
	  <xsl:text>&#xA;      </xsl:text>
	</xsl:element>
	<xsl:text>&#xA;    </xsl:text>
      </xsl:element>
      <xsl:text>&#xA;    </xsl:text>
      <xsl:element name="xsl:if">
        <xsl:attribute name="test">(normalize-space($recto.content) != '') or ($recto.elements.count > 0)</xsl:attribute>
        <xsl:text>&#xA;      </xsl:text>
        <xsl:element name="{@t:wrapper}" namespace="{$ns}">
          <xsl:apply-templates select="t:titlepage-content[@t:side='recto']/@*"
                               mode="copy.literal.atts"/>
          <xsl:element name="xsl:copy-of">
            <xsl:attribute name="select">$recto.content</xsl:attribute>
          </xsl:element>
        </xsl:element>
        <xsl:text>&#xA;    </xsl:text>
      </xsl:element>
      <xsl:text>&#xA;    </xsl:text>
      <xsl:element name="xsl:variable">
        <xsl:attribute name="name">verso.content</xsl:attribute>
        <xsl:text>&#xA;      </xsl:text>
        <xsl:element name="xsl:call-template">
          <xsl:attribute name="name">
            <xsl:value-of select="@t:element"/>
            <xsl:text>.titlepage.before.verso</xsl:text>
          </xsl:attribute>
        </xsl:element>
        <xsl:text>&#xA;      </xsl:text>
        <xsl:element name="xsl:call-template">
          <xsl:attribute name="name">
            <xsl:value-of select="@t:element"/>
            <xsl:text>.titlepage.verso</xsl:text>
          </xsl:attribute>
        </xsl:element>
        <xsl:text>&#xA;    </xsl:text>
      </xsl:element>
      <xsl:text>&#xA;    </xsl:text>
      <xsl:element name="xsl:variable">
	<xsl:attribute name="name">verso.elements.count</xsl:attribute>
	<xsl:text>&#xA;      </xsl:text>
	<xsl:element name="xsl:choose">
	  <xsl:text>&#xA;        </xsl:text>
	  <xsl:element name="xsl:when">
	    <xsl:attribute name="test">function-available('exsl:node-set')</xsl:attribute>
	    <xsl:element name="xsl:value-of">
	      <xsl:attribute name="select">count(exsl:node-set($verso.content)/*)</xsl:attribute>
	    </xsl:element>
	  </xsl:element>
	  <xsl:text>&#xA;        </xsl:text>
	  <xsl:element name="xsl:when">
	    <xsl:attribute name="test">contains(system-property('xsl:vendor'), 'Apache Software Foundation')</xsl:attribute>
	    <xsl:text>&#xA;          </xsl:text>
	    <xsl:comment>Xalan quirk</xsl:comment>
	    <xsl:element name="xsl:value-of">
	      <xsl:attribute name="select">count(exsl:node-set($verso.content)/*)</xsl:attribute>
	    </xsl:element>
	  </xsl:element>
	  <xsl:text>&#xA;        </xsl:text>
	  <xsl:element name="xsl:otherwise">
	    <xsl:text>1</xsl:text>
	  </xsl:element>
	  <xsl:text>&#xA;      </xsl:text>
	</xsl:element>
	<xsl:text>&#xA;    </xsl:text>
      </xsl:element>
      <xsl:text>&#xA;    </xsl:text>
      <xsl:element name="xsl:if">
        <xsl:attribute name="test">(normalize-space($verso.content) != '') or ($verso.elements.count > 0)</xsl:attribute>
        <xsl:text>&#xA;      </xsl:text>
        <xsl:element name="{@t:wrapper}" namespace="{$ns}">
          <xsl:apply-templates select="t:titlepage-content[@t:side='verso']/@*"
                               mode="copy.literal.atts"/>
          <xsl:element name="xsl:copy-of">
            <xsl:attribute name="select">$verso.content</xsl:attribute>
          </xsl:element>
        </xsl:element>
        <xsl:text>&#xA;    </xsl:text>
      </xsl:element>
      <xsl:text>&#xA;    </xsl:text>
      <xsl:element name="xsl:call-template">
        <xsl:attribute name="name">
          <xsl:value-of select="@t:element"/>
          <xsl:text>.titlepage.separator</xsl:text>
        </xsl:attribute>
      </xsl:element>
      <xsl:text>&#xA;  </xsl:text>
    </xsl:element>
    <xsl:text>&#xA;</xsl:text>
  </xsl:element>

  <!-- If we're not importing a base stylesheet, output a default rule
       for the recto- and verso-mode elements. (If we are importing a
       base stylesheet, don't do this since the *-rules in the stylesheet
       will totally override the rules that would otherwise be imported.)
       -->

  <xsl:if test="not(../@t:base-stylesheet)">
    <!-- output a default rule for the recto-modes elements -->
    <xsl:text>&#xA;&#xA;</xsl:text>
    <xsl:element name="xsl:template">
      <xsl:attribute name="match">*</xsl:attribute>
      <xsl:attribute name="mode">
        <xsl:value-of select="@t:element"/>
        <xsl:text>.titlepage.recto.mode</xsl:text>
      </xsl:attribute>
      <xsl:text>&#xA;  </xsl:text>
      <xsl:comment> if an element isn't found in this mode, </xsl:comment>
      <xsl:text>&#xA;  </xsl:text>
      <xsl:comment> try the generic titlepage.mode </xsl:comment>
      <xsl:text>&#xA;  </xsl:text>
      <xsl:element name="xsl:apply-templates">
        <xsl:attribute name="select">.</xsl:attribute>
        <xsl:attribute name="mode">titlepage.mode</xsl:attribute>
      </xsl:element>
      <xsl:text>&#xA;</xsl:text>
    </xsl:element>

    <!-- output a default rule for the verso-modes elements -->
    <xsl:text>&#xA;&#xA;</xsl:text>
    <xsl:element name="xsl:template">
      <xsl:attribute name="match">*</xsl:attribute>
      <xsl:attribute name="mode">
        <xsl:value-of select="@t:element"/>
        <xsl:text>.titlepage.verso.mode</xsl:text>
      </xsl:attribute>
      <xsl:text>&#xA;  </xsl:text>
      <xsl:comment> if an element isn't found in this mode, </xsl:comment>
      <xsl:text>&#xA;  </xsl:text>
      <xsl:comment> try the generic titlepage.mode </xsl:comment>
      <xsl:text>&#xA;  </xsl:text>
      <xsl:element name="xsl:apply-templates">
        <xsl:attribute name="select">.</xsl:attribute>
        <xsl:attribute name="mode">titlepage.mode</xsl:attribute>
      </xsl:element>
      <xsl:text>&#xA;</xsl:text>
    </xsl:element>
  </xsl:if>

  <!-- output default templates for each of the elements listed in  -->
  <!-- the titlepage-content. If a template is suppressed or forced -->
  <!-- to be off, or has already been output, don't output it.      -->
  <xsl:for-each select="t:titlepage-content/*">
    <xsl:variable name="thisnode" select="."/>
    <xsl:if test="(not(@t:suppress-template) or @t:suppress-template='0')
                  and (not(@t:force) or @t:force='0')
                  and (not(preceding-sibling::*[name(.)=name($thisnode)]))">
      <xsl:text>&#xA;&#xA;</xsl:text>
      <xsl:element name="xsl:template">
        <xsl:attribute name="match">
          <xsl:value-of select="name(.)"/>
        </xsl:attribute>
        <xsl:attribute name="mode">
          <xsl:value-of select="../../@t:element"/>
          <xsl:text>.titlepage.</xsl:text>
          <xsl:value-of select="../@t:side"/>
          <xsl:text>.auto.mode</xsl:text>
        </xsl:attribute>
        <xsl:text>&#xA;</xsl:text>
        <xsl:element name="{../../@t:wrapper}" namespace="{$ns}">
          <xsl:attribute name="xsl:use-attribute-sets">
            <xsl:value-of select="../../@t:element"/>
            <xsl:text>.titlepage.</xsl:text>
            <xsl:value-of select="../@t:side"/>
            <xsl:text>.style</xsl:text>
          </xsl:attribute>
          <xsl:for-each select="@*">
            <xsl:if test="not(starts-with(namespace-uri(.),
                                'http://nwalsh.com/docbook/xsl/template/1.0'))">
              <xsl:attribute name="{name(.)}" namespace="{namespace-uri(.)}">
                <xsl:value-of select="."/>
              </xsl:attribute>
            </xsl:if>
          </xsl:for-each>
          <xsl:text>&#xA;</xsl:text>

          <xsl:choose>
            <xsl:when test="@t:named-template">
              <xsl:element name="xsl:call-template">
                <xsl:attribute name="name">
                  <xsl:value-of select="@t:named-template"/>
                </xsl:attribute>
                <xsl:for-each select="@*">
                  <xsl:if test="namespace-uri(.)='http://nwalsh.com/docbook/xsl/template/1.0/param'">
                    <xsl:text>&#xA;</xsl:text>
                    <xsl:element name="xsl:with-param">
                      <xsl:attribute name="name">
                        <xsl:value-of select="local-name(.)"/>
                      </xsl:attribute>
                      <xsl:attribute name="select">
                        <xsl:value-of select="."/>
                      </xsl:attribute>
                    </xsl:element>
                  </xsl:if>
                </xsl:for-each>
                <xsl:text>&#xA;</xsl:text>
              </xsl:element>
            </xsl:when>
            <xsl:otherwise>
              <xsl:element name="xsl:apply-templates">
                <xsl:attribute name="select">.</xsl:attribute>
                <xsl:attribute name="mode">
                  <xsl:value-of select="../../@t:element"/>
                  <xsl:text>.titlepage.</xsl:text>
                  <xsl:value-of select="../@t:side"/>
                  <xsl:text>.mode</xsl:text>
                </xsl:attribute>
              </xsl:element>
            </xsl:otherwise>
          </xsl:choose>

          <xsl:text>&#xA;</xsl:text>
        </xsl:element>
        <xsl:text>&#xA;</xsl:text>
      </xsl:element>
    </xsl:if>
  </xsl:for-each>
</xsl:template>

<doc:template match="@*" mode="copy.literal.atts" xmlns=""
              id="attr_star_in_copy.literal.atts">
<refpurpose>Copy t:titlepage attributes</refpurpose>

<refdescription>
<para>This template copies all of the <quote>other</quote> attributes
from a <literal>t:titlepage</literal> element onto the specified
wrapper.</para>
</refdescription>
</doc:template>

<xsl:template match="@*" mode="copy.literal.atts">
  <xsl:if test="not(starts-with(namespace-uri(.),
                                'http://nwalsh.com/docbook/xsl/template/1.0'))">
    <xsl:attribute name="{name(.)}">
      <xsl:value-of select="."/>
    </xsl:attribute>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="t:titlepage-content" id="titlepage-content">
<refpurpose>Create templates for the content of one side of a title page</refpurpose>

<refdescription>
<para>The title page content, that is, the elements from the source
document that are rendered on the title page, can be controlled independently
for the recto and verso sides of the title page.</para>

<para>The <literal>t:titlepage-content</literal> element has two attributes:

<variablelist>
<varlistentry><term>side</term>
<listitem><para>Identifies the side of the page to which this title
page content applies. The
<tag class="attribute">side</tag> attribute is required and
must be set to either 
<quote><literal>recto</literal></quote> or
<quote><literal>verso</literal></quote>. In addition, you must specify
exactly one <literal>t:titlepage-content</literal> for each side
within each <literal>t:titlepage</literal>.</para>
</listitem>
</varlistentry>
<varlistentry><term>order</term>
<listitem><para>Indicates how the order of the elements presented on
the title page is determined. If the
<tag class="attribute">order</tag> is
<quote><literal>document</literal></quote>, the elements are presented
in document order. Otherwise (if the
<tag class="attribute">order</tag> is
<quote><literal>stylesheet</literal></quote>), the elements are presented
in the order that they appear in the template (and consequently in
the stylesheet).</para>
</listitem>
</varlistentry>
</variablelist>
</para>

<para>The content of a <literal>t:titlepage-content</literal> element is
a list of element names. These names should be unqualified.  They identify
the elements in the source document that should appear on the title page.
</para>

<para>Each element may have a single attribute:
<tag class="attribute">predicate</tag>. The value of this
attribute is used as a predicate for the expression that matches
the element on which it occurs.</para>

<para>In other words, to put only the first three authors on the
recto-side of a title
page, you could specify:

<screen><![CDATA[
  <t:titlepage-contents side="recto">
    <!-- other titlepage elements -->
    <author predicate="[count(previous-sibling::author)<2]"/>
    <!-- other titlepage elements -->
  </t:titlepage-contents>
]]></screen>
</para>

<para>Usually, the elements so named are empty. But it is possible to
make one level of selection within them. Suppose that you want to
process <literal>authorgroup</literal> elements on the title page, but
you want to select only proper authors, editors, or corporate authors,
not collaborators or other credited authors.</para>

<para>In that case, you can put a <literal>t:or</literal> group inside
the <literal>authorgroup</literal> element:

<screen><![CDATA[
  <t:titlepage-contents side="recto">
    <!-- other titlepage elements -->
    <authorgroup>
      <t:or>
        <author/>
        <editor/>
        <corpauthor/>
      </t:or>
    </authorgroup>
    <!-- other titlepage elements -->
  </t:titlepage-contents>
]]></screen>
</para>

<para>This will have the effect of automatically generating a template
for processing <literal>authorgroup</literal>s in the title page mode,
selecting only the specified children. If you need more complex processing,
you'll have to construct the templates by hand.</para>

</refdescription>
</doc:template>

<xsl:template match="t:titlepage-content">
  <xsl:variable name="side">
    <xsl:choose>
      <xsl:when test="@t:side='recto' or @t:side='verso'">
        <xsl:value-of select="@t:side"/>
      </xsl:when>
      <xsl:when test="@t:side">
        <xsl:message terminate="yes">
          <xsl:text>Illegal value specified for @t:side </xsl:text>
          <xsl:text>on t:titlepage-content: </xsl:text>
          <xsl:value-of select="@t:side"/>
        </xsl:message>
      </xsl:when>
      <xsl:otherwise>
        <xsl:message terminate="yes">
          <xsl:text>The @t:side attribute is required on </xsl:text>
          <xsl:text>t:titlepage-content.</xsl:text>
        </xsl:message>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="mode">
    <xsl:value-of select="../@t:element"/>
    <xsl:text>.titlepage.</xsl:text>
    <xsl:value-of select="$side"/>
    <xsl:text>.auto.mode</xsl:text>
  </xsl:variable>

  <xsl:text>&#xA;&#xA;</xsl:text>
  <xsl:element name="xsl:template">
    <xsl:attribute name="name">
      <xsl:value-of select="../@t:element"/>
      <xsl:text>.titlepage.</xsl:text>
      <xsl:value-of select="$side"/>
    </xsl:attribute>

    <xsl:choose>
      <!-- if document order is selected, make a huge select statement
           on a single xsl:apply-templates to pick out the right elements
           for the title page. -->
      <xsl:when test="@t:order='document'">
        <xsl:if test="count(child::*)&gt;0">
          <xsl:element name="xsl:apply-templates">
            <xsl:attribute name="mode">
              <xsl:value-of select="$mode"/>
            </xsl:attribute>
            <xsl:attribute name="select">
              <xsl:apply-templates mode="document.order"/>
            </xsl:attribute>
          </xsl:element>
        </xsl:if>
      </xsl:when>

      <!-- otherwise, select each of the elements in the specified order -->
      <xsl:otherwise>
        <xsl:apply-templates mode="stylesheet.order"/>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text>&#xA;</xsl:text>
  </xsl:element>
  <xsl:apply-templates mode="titlepage.specialrules"/>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="t:titlepage-separator" id="titlepage-separator">
<refpurpose>Create templates for the separator</refpurpose>

<refdescription>
<para>The title page is separated from the content which follows it by
the markup specified in the <literal>t:titlepage-separator</literal>
element.</para>
</refdescription>
</doc:template>

<xsl:template match="t:titlepage-separator">
  <xsl:text>&#xA;&#xA;</xsl:text>
  <xsl:element name="xsl:template">
    <xsl:attribute name="name">
      <xsl:value-of select="../@t:element"/>
      <xsl:text>.titlepage.separator</xsl:text>
    </xsl:attribute>

    <xsl:apply-templates mode="copy"/>
    <xsl:text>&#xA;</xsl:text>
  </xsl:element>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="t:titlepage-before" id="titlepage-before">
<refpurpose>Create templates for what precedes a title page</refpurpose>

<refdescription>
<para>Each side of the title page is preceded by the markup specified
in the <literal>t:titlepage-before</literal> element for that
side.</para>
</refdescription>
</doc:template>

<xsl:template match="t:titlepage-before">
  <xsl:text>&#xA;&#xA;</xsl:text>
  <xsl:element name="xsl:template">
    <xsl:attribute name="name">
      <xsl:value-of select="../@t:element"/>
      <xsl:text>.titlepage.before.</xsl:text>
      <xsl:value-of select="@t:side"/>
    </xsl:attribute>

    <xsl:apply-templates mode="copy"/>
    <xsl:text>&#xA;</xsl:text>
  </xsl:element>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="*" mode="copy" xmlns="" id="star_in_copy">
<refpurpose>Copy elements</refpurpose>

<refdescription>
<para>This template simply copies the elements that it applies to
straight through into the result tree.</para>
</refdescription>
</doc:template>

<xsl:template match="*" mode="copy">
  <xsl:choose>
    <xsl:when test="(name(.) = local-name(.)) and namespace-uri(.) != ''">
      <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
	<xsl:apply-templates select="@*" mode="copy"/>
	<xsl:apply-templates mode="copy"/>
      </xsl:element>
    </xsl:when>
    <xsl:otherwise>
      <xsl:element name="{name(.)}">
	<xsl:apply-templates select="@*" mode="copy"/>
	<xsl:apply-templates mode="copy"/>
      </xsl:element>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="@*" mode="copy" xmlns="" id="attr_star_in_copy">
<refpurpose>Copy attributes</refpurpose>

<refdescription>
<para>This template simply copies the attributes that it applies to
straight through into the result tree.</para>
</refdescription>
</doc:template>

<xsl:template match="@*" mode="copy">
  <xsl:choose>
    <xsl:when test="(name(.) = local-name(.)) and namespace-uri(.) != ''">
      <xsl:attribute name="{name(.)}" namespace="{namespace-uri(.)}">
	<xsl:value-of select="."/>
      </xsl:attribute>
    </xsl:when>
    <xsl:otherwise>
      <xsl:attribute name="{name(.)}">
	<xsl:value-of select="."/>
      </xsl:attribute>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="*" mode="document.order" xmlns="" id="attr_star_in_document.order">
<refpurpose>Create rules to process titlepage elements in document order</refpurpose>

<refdescription>
<para>This template is called to process all of the children of the
<literal>t:titlepage-content</literal> element. It creates the hairy
select expression necessary to process each of those elements in
the title page.</para>

<para>Note that this template automatically handles the case where
some DocBook elements, like title and subtitle, can occur both inside
the *info elements where metadata is usually stored and outside.
</para>

<para>It also automatically calculates the name for the *info container
and handles elements that have historically had containers with different
names.</para>

</refdescription>
</doc:template>

<xsl:template match="*" mode="document.order">
  <xsl:variable name="docinfo">
    <xsl:value-of select="ancestor::t:titlepage/@t:element"/>
    <xsl:text>info</xsl:text>
  </xsl:variable>

  <xsl:variable name="altinfo">
    <xsl:choose>
      <xsl:when test="ancestor::t:titlepage/@t:element='article'">
        <xsl:text>artheader</xsl:text>
      </xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='qandaset'">
        <xsl:text>blockinfo</xsl:text>
      </xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='section'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect1'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect2'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect3'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect4'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect5'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='book'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='set'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='topic'"></xsl:when>
      <xsl:otherwise>docinfo</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="side">
    <xsl:choose>
      <xsl:when test="ancestor::t:titlepage-content/@t:side">
        <xsl:value-of select="ancestor::t:titlepage-content/@t:side"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>recto</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="mode">
    <xsl:value-of select="ancestor::t:titlepage/@t:element"/>
    <xsl:text>.titlepage.</xsl:text>
    <xsl:value-of select="$side"/>
    <xsl:text>.auto.mode</xsl:text>
  </xsl:variable>

  <xsl:if test="preceding-sibling::*">
    <xsl:text>|</xsl:text>
  </xsl:if>

  <xsl:value-of select="$docinfo"/>
  <xsl:text>/</xsl:text>
  <xsl:value-of select="name(.)"/>
  <xsl:if test="@t:predicate">
    <xsl:value-of select="@t:predicate"/>
  </xsl:if>

  <xsl:if test="$altinfo != ''">
    <xsl:text>|</xsl:text>
    <xsl:value-of select="$altinfo"/>
    <xsl:text>/</xsl:text>
    <xsl:value-of select="name(.)"/>
    <xsl:if test="@t:predicate">
      <xsl:value-of select="@t:predicate"/>
    </xsl:if>
  </xsl:if>

  <!-- info -->
  <xsl:text>|info</xsl:text>
  <xsl:text>/</xsl:text>
  <xsl:value-of select="name(.)"/>
  <xsl:if test="@t:predicate">
    <xsl:value-of select="@t:predicate"/>
  </xsl:if>

  <xsl:if test="local-name(.) = 'title'
                or local-name(.) = 'subtitle'
                or local-name(.) = 'titleabbrev'">
    <xsl:text>|</xsl:text>
    <xsl:value-of select="name(.)"/>
    <xsl:if test="@t:predicate">
      <xsl:value-of select="@t:predicate"/>
    </xsl:if>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="*" mode="document.order" xmlns="" id="star_in_document.order">
<refpurpose>Create rules to process titlepage elements in stylesheet order</refpurpose>

<refdescription>
<para>This template is called to process all of the children of the
<literal>t:titlepage-content</literal> element. It creates the set
of <literal>xsl:apply-templates</literal> elements necessary
process each of those elements in the title page.</para>

<para>Note that this template automatically handles the case where
some DocBook elements, like title and subtitle, can occur both inside
the *info elements where metadata is usually stored and outside.
</para>

<para>It also automatically calculates the name for the *info container
and handles elements that have historically had containers with different
names.</para>

</refdescription>
</doc:template>

<xsl:template match="*" mode="stylesheet.order">
  <xsl:variable name="docinfo">
    <xsl:value-of select="ancestor::t:titlepage/@t:element"/>
    <xsl:text>info</xsl:text>
  </xsl:variable>

  <xsl:variable name="altinfo">
    <xsl:choose>
      <xsl:when test="ancestor::t:titlepage/@t:element='article'">
        <xsl:text>artheader</xsl:text>
      </xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='qandaset'">
        <xsl:text>blockinfo</xsl:text>
      </xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='section'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect1'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect2'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect3'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect4'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='sect5'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='book'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='set'"></xsl:when>
      <xsl:when test="ancestor::t:titlepage/@t:element='topic'"></xsl:when>
      <xsl:otherwise>docinfo</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="side">
    <xsl:choose>
      <xsl:when test="ancestor::t:titlepage-content/@t:side">
        <xsl:value-of select="ancestor::t:titlepage-content/@t:side"/>
      </xsl:when>
      <xsl:otherwise>recto</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="mode">
    <xsl:value-of select="ancestor::t:titlepage/@t:element"/>
    <xsl:text>.titlepage.</xsl:text>
    <xsl:value-of select="$side"/>
    <xsl:text>.auto.mode</xsl:text>
  </xsl:variable>

  <xsl:text>&#xA;  </xsl:text>

  <xsl:choose>
    <xsl:when test="@t:force and @t:force != '0'">
      <xsl:choose>
        <xsl:when test="@t:named-template">
          <xsl:element name="{../../@t:wrapper}" namespace="{$ns}">
            <xsl:attribute name="xsl:use-attribute-sets">
              <xsl:value-of select="../../@t:element"/>
              <xsl:text>.titlepage.</xsl:text>
              <xsl:value-of select="../@t:side"/>
              <xsl:text>.style</xsl:text>
            </xsl:attribute>
            <xsl:for-each select="@*">
              <xsl:if test="not(starts-with(namespace-uri(.),
                                  'http://nwalsh.com/docbook/xsl/template/1.0'))">
                <xsl:attribute name="{name(.)}" namespace="{namespace-uri(.)}">
                  <xsl:value-of select="."/>
                </xsl:attribute>
              </xsl:if>
            </xsl:for-each>
            <xsl:text>&#xA;</xsl:text>
            <xsl:element name="xsl:call-template">
              <xsl:attribute name="name">
                <xsl:value-of select="@t:named-template"/>
              </xsl:attribute>
              <xsl:for-each select="@*">
                <xsl:if test="namespace-uri(.)='http://nwalsh.com/docbook/xsl/template/1.0/param'">
                  <xsl:text>&#xA;</xsl:text>
                  <xsl:element name="xsl:with-param">
                    <xsl:attribute name="name">
                      <xsl:value-of select="local-name(.)"/>
                    </xsl:attribute>
                    <xsl:attribute name="select">
                      <xsl:value-of select="."/>
                    </xsl:attribute>
                  </xsl:element>
                </xsl:if>
              </xsl:for-each>
              <xsl:text>&#xA;</xsl:text>
            </xsl:element>
          </xsl:element>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message terminate="yes">
            <xsl:text>Force can only be used with named-templates.</xsl:text>
          </xsl:message>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>

      <xsl:choose>
        <xsl:when test="local-name(.) = 'title'
                        or local-name(.) = 'subtitle'
                        or local-name(.) = 'titleabbrev'">
          <!-- the title, subtitle, and titleabbrev elements are special -->
          <xsl:element name="xsl:choose">
            <xsl:text>&#xA;    </xsl:text>
            <xsl:element name="xsl:when">
              <xsl:attribute name="test">
                <xsl:value-of select="$docinfo"/>
                <xsl:text>/</xsl:text>
                <xsl:value-of select="name(.)"/>
              </xsl:attribute>
              <xsl:text>&#xA;      </xsl:text>
              <xsl:element name="xsl:apply-templates">
                <xsl:attribute name="mode">
                  <xsl:value-of select="$mode"/>
                </xsl:attribute>
                <xsl:attribute name="select">
                  <xsl:value-of select="$docinfo"/>
                  <xsl:text>/</xsl:text>
                  <xsl:value-of select="name(.)"/>
                  <xsl:if test="@t:predicate">
                    <xsl:value-of select="@t:predicate"/>
                  </xsl:if>
                </xsl:attribute>
              </xsl:element>
              <xsl:text>&#xA;    </xsl:text>
            </xsl:element>

            <xsl:if test="$altinfo != ''">
              <xsl:text>&#xA;    </xsl:text>
              <xsl:element name="xsl:when">
                <xsl:attribute name="test">
                  <xsl:value-of select="$altinfo"/>
                  <xsl:text>/</xsl:text>
                  <xsl:value-of select="name(.)"/>
                </xsl:attribute>
                <xsl:text>&#xA;      </xsl:text>
                <xsl:element name="xsl:apply-templates">
                  <xsl:attribute name="mode">
                    <xsl:value-of select="$mode"/>
                  </xsl:attribute>
                  <xsl:attribute name="select">
                    <xsl:value-of select="$altinfo"/>
                    <xsl:text>/</xsl:text>
                    <xsl:value-of select="name(.)"/>
                    <xsl:if test="@t:predicate">
                      <xsl:value-of select="@t:predicate"/>
                    </xsl:if>
                  </xsl:attribute>
                </xsl:element>
                <xsl:text>&#xA;    </xsl:text>
              </xsl:element>
            </xsl:if>

            <!-- info -->
            <xsl:text>&#xA;    </xsl:text>
            <xsl:element name="xsl:when">
              <xsl:attribute name="test">
                <xsl:value-of select="'info'"/>
                <xsl:text>/</xsl:text>
                <xsl:value-of select="name(.)"/>
              </xsl:attribute>
              <xsl:text>&#xA;      </xsl:text>
              <xsl:element name="xsl:apply-templates">
                <xsl:attribute name="mode">
                  <xsl:value-of select="$mode"/>
                </xsl:attribute>
                <xsl:attribute name="select">
                  <xsl:value-of select="'info'"/>
                  <xsl:text>/</xsl:text>
                  <xsl:value-of select="name(.)"/>
                  <xsl:if test="@t:predicate">
                    <xsl:value-of select="@t:predicate"/>
                  </xsl:if>
                </xsl:attribute>
              </xsl:element>
              <xsl:text>&#xA;    </xsl:text>
            </xsl:element>

            <xsl:text>&#xA;    </xsl:text>
            <xsl:element name="xsl:when">
              <xsl:attribute name="test">
                <xsl:value-of select="name(.)"/>
              </xsl:attribute>
              <xsl:text>&#xA;      </xsl:text>
              <xsl:element name="xsl:apply-templates">
                <xsl:attribute name="mode">
                  <xsl:value-of select="$mode"/>
                </xsl:attribute>
                <xsl:attribute name="select">
                  <xsl:value-of select="name(.)"/>
                  <xsl:if test="@t:predicate">
                    <xsl:value-of select="@t:predicate"/>
                  </xsl:if>
                </xsl:attribute>
              </xsl:element>
              <xsl:text>&#xA;    </xsl:text>
            </xsl:element>
            <xsl:text>&#xA;  </xsl:text>
          </xsl:element>
          <xsl:text>&#xA;</xsl:text>
        </xsl:when>
        <xsl:otherwise>

          <!-- first take care of the $docinfo version -->
          <xsl:element name="xsl:apply-templates">
            <xsl:attribute name="mode">
              <xsl:value-of select="$mode"/>
            </xsl:attribute>
            <xsl:attribute name="select">
              <xsl:value-of select="$docinfo"/>
              <xsl:text>/</xsl:text>
              <xsl:value-of select="name(.)"/>
              <xsl:if test="@t:predicate">
                <xsl:value-of select="@t:predicate"/>
              </xsl:if>
            </xsl:attribute>
          </xsl:element>

          <!-- then take care of the $altinfo version -->
          <xsl:if test="$altinfo != ''">
            <xsl:text>&#xA;  </xsl:text>
            <xsl:element name="xsl:apply-templates">
              <xsl:attribute name="mode">
                <xsl:value-of select="$mode"/>
              </xsl:attribute>
              <xsl:attribute name="select">
                <xsl:value-of select="$altinfo"/>
                <xsl:text>/</xsl:text>
                <xsl:value-of select="name(.)"/>
                <xsl:if test="@t:predicate">
                  <xsl:value-of select="@t:predicate"/>
                </xsl:if>
              </xsl:attribute>
            </xsl:element>
          </xsl:if>

          <!-- info -->
          <xsl:text>&#xA;  </xsl:text>
          <xsl:element name="xsl:apply-templates">
            <xsl:attribute name="mode">
              <xsl:value-of select="$mode"/>
            </xsl:attribute>
            <xsl:attribute name="select">
              <xsl:value-of select="'info'"/>
              <xsl:text>/</xsl:text>
              <xsl:value-of select="name(.)"/>
              <xsl:if test="@t:predicate">
                <xsl:value-of select="@t:predicate"/>
              </xsl:if>
            </xsl:attribute>
          </xsl:element>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="*" mode="titlepage.specialrules" xmlns=""
              id="star_in_titlepage.specialrules">
<refpurpose>Create templates for special rules</refpurpose>

<refdescription>
<para>This template is called to process all of the descendants of the
<literal>t:titlepage-content</literal> element that require special
processing. At present, that's just <literal>t:or</literal> elements.
</para>
</refdescription>
</doc:template>

<xsl:template match="*" mode="titlepage.specialrules">
  <xsl:variable name="side">
    <xsl:choose>
      <xsl:when test="ancestor::t:titlepage-content/@t:side">
        <xsl:value-of select="ancestor::t:titlepage-content/@t:side"/>
      </xsl:when>
      <xsl:otherwise>recto</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="mode">
    <xsl:value-of select="ancestor::t:titlepage/@t:element"/>
    <xsl:text>.titlepage.</xsl:text>
    <xsl:value-of select="$side"/>
    <xsl:text>.auto.mode</xsl:text>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="name(.)='t:or'">
      <xsl:apply-templates select="*" mode="titlepage.specialrules"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="*"><!-- does this element have children? -->
      <xsl:text>&#xA;&#xA;</xsl:text>
        <xsl:element name="xsl:template">
          <xsl:attribute name="match">
            <xsl:value-of select="name(.)"/>
          </xsl:attribute>
          <xsl:attribute name="mode">
            <xsl:value-of select="$mode"/>
          </xsl:attribute>
          <xsl:apply-templates select="*" mode="titlepage.subrules"/>
          <xsl:text>&#xA;</xsl:text>
        </xsl:element>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="*" mode="titlepage.subrules" xmlns=""
              id="star_in_titlepage.subrules">
<refpurpose>Create template for individual special rules</refpurpose>

<refdescription>
<para>This template is called to process the children of special
template elements.
</para>
</refdescription>
</doc:template>

<xsl:template match="*" mode="titlepage.subrules">
  <xsl:variable name="side">
    <xsl:choose>
      <xsl:when test="ancestor::t:titlepage-content/@t:side">
        <xsl:value-of select="ancestor::t:titlepage-content/@t:side"/>
      </xsl:when>
      <xsl:otherwise>recto</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="mode">
    <xsl:value-of select="ancestor::t:titlepage/@t:element"/>
    <xsl:text>.titlepage.</xsl:text>
    <xsl:value-of select="$side"/>
    <xsl:text>.auto.mode</xsl:text>
  </xsl:variable>

  <xsl:element name="xsl:apply-templates">
    <xsl:attribute name="select">
      <xsl:value-of select="name(.)"/>
    </xsl:attribute>
    <xsl:attribute name="mode">
      <xsl:value-of select="$mode"/>
    </xsl:attribute>
  </xsl:element>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="t:or" xmlns="" id="or">
<refpurpose>Process the t:or special rule</refpurpose>

<refdescription>
<para>This template processes t:or.</para>
</refdescription>
</doc:template>

<xsl:template match="t:or">
  <xsl:variable name="side">
    <xsl:choose>
      <xsl:when test="ancestor::t:titlepage-content/@t:side">
        <xsl:value-of select="ancestor::t:titlepage-content/@t:side"/>
      </xsl:when>
      <xsl:otherwise>recto</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="mode">
    <xsl:value-of select="ancestor::t:titlepage/@t:element"/>
    <xsl:text>.titlepage.</xsl:text>
    <xsl:value-of select="$side"/>
    <xsl:text>.auto.mode</xsl:text>
  </xsl:variable>

  <xsl:text>&#xA;  </xsl:text>
  <xsl:element name="xsl:apply-templates">
    <xsl:attribute name="select">
      <xsl:call-template name="element-or-list"/>
    </xsl:attribute>
    <xsl:attribute name="mode">
      <xsl:value-of select="$mode"/>
    </xsl:attribute>
  </xsl:element>
</xsl:template>

<!-- ==================================================================== -->

<doc:template match="t:or" mode="titlepage.subrules" xmlns=""
              id="or_in_titlepage.subrules">
<refpurpose>Process the t:or special rule in
titlepage.subrules mode</refpurpose>

<refdescription>
<para>The titlepage.subrules mode doesn't apply to t:or, so just
reprocess this node in the normal mode.</para>
</refdescription>
</doc:template>

<xsl:template match="t:or" mode="titlepage.subrules">
  <xsl:apply-templates select="."/><!-- use normal mode -->
</xsl:template>

<!-- ==================================================================== -->

<doc:template name="element-or-list" xmlns="">
<refpurpose>Construct the "or-list" used in the select attribute for
special rules.</refpurpose>

<refdescription>
<para>Walk through each of the children of t:or, producing the
text of the select attribute.</para>
</refdescription>
</doc:template>

<xsl:template name="element-or-list">
  <xsl:param name="elements" select="*"/>
  <xsl:param name="element.count" select="count($elements)"/>
  <xsl:param name="count" select="1"/>
  <xsl:param name="orlist"></xsl:param>

  <xsl:choose>
    <xsl:when test="$count>$element.count">
      <xsl:value-of select="$orlist"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="element-or-list">
        <xsl:with-param name="elements" select="$elements"/>
        <xsl:with-param name="element.count" select="$element.count"/>
        <xsl:with-param name="count" select="$count+1"/>
        <xsl:with-param name="orlist">
          <xsl:value-of select="$orlist"/>
          <xsl:if test="not($orlist='')">|</xsl:if>
          <xsl:value-of select="name($elements[position()=$count])"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
