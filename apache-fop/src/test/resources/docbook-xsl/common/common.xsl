<?xml version='1.0'?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY lowercase "'abcdefghijklmnopqrstuvwxyz'">
<!ENTITY uppercase "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'">
 ]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                xmlns:dyn="http://exslt.org/dynamic"
                xmlns:saxon="http://icl.com/saxon"
                exclude-result-prefixes="doc dyn saxon"
                version='1.0'>

<!-- ********************************************************************
     $Id: common.xsl 9347 2012-05-11 03:49:49Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<doc:reference xmlns="" xml:id="base">
  <info>
    <title>Common » Base Template Reference</title>
    <releaseinfo role="meta">
      $Id: common.xsl 9347 2012-05-11 03:49:49Z bobstayton $
    </releaseinfo>
  </info>
  <!-- * yes, partintro is a valid child of a reference... -->
  <partintro xml:id="partintro">
    <title>Introduction</title>
    <para>This is technical reference documentation for the “base”
      set of common templates in the DocBook XSL Stylesheets.</para>
    <para>This is not intended to be user documentation. It is
      provided for developers writing customization layers for the
      stylesheets.</para>
  </partintro>
</doc:reference>

<!-- ==================================================================== -->
<!-- Establish strip/preserve whitespace rules -->

<xsl:preserve-space elements="*"/>

<xsl:strip-space elements="
abstract affiliation anchor answer appendix area areaset areaspec
artheader article audiodata audioobject author authorblurb authorgroup
beginpage bibliodiv biblioentry bibliography biblioset blockquote book
bookinfo callout calloutlist caption caution chapter
citerefentry cmdsynopsis co collab colophon colspec confgroup
copyright dedication docinfo editor entrytbl epigraph equation
example figure footnote footnoteref formalpara funcprototype
funcsynopsis glossary glossdef glossdiv glossentry glosslist graphicco
group highlights imagedata imageobject imageobjectco important index
indexdiv indexentry indexterm info informalequation informalexample
informalfigure informaltable inlineequation inlinemediaobject
itemizedlist itermset keycombo keywordset legalnotice listitem lot
mediaobject mediaobjectco menuchoice msg msgentry msgexplan msginfo
msgmain msgrel msgset msgsub msgtext note objectinfo
orderedlist othercredit part partintro preface printhistory procedure
programlistingco publisher qandadiv qandaentry qandaset question
refentry reference refmeta refnamediv refsection refsect1 refsect1info refsect2
refsect2info refsect3 refsect3info refsynopsisdiv refsynopsisdivinfo
revhistory revision row sbr screenco screenshot sect1 sect1info sect2
sect2info sect3 sect3info sect4 sect4info sect5 sect5info section
sectioninfo seglistitem segmentedlist seriesinfo set setindex setinfo
shortcut sidebar simplelist simplesect spanspec step subject
subjectset substeps synopfragment table tbody textobject tfoot tgroup
thead tip toc tocchap toclevel1 toclevel2 toclevel3 toclevel4
toclevel5 tocpart topic varargs variablelist varlistentry videodata
videoobject void warning subjectset

classsynopsis
constructorsynopsis
destructorsynopsis
fieldsynopsis
methodparam
methodsynopsis
ooclass
ooexception
oointerface
simplemsgentry
manvolnum
"/>
<!-- ====================================================================== -->

<doc:template name="is.component" xmlns="">
<refpurpose>Tests if a given node is a component-level element</refpurpose>

<refdescription id="is.component-desc">
<para>This template returns '1' if the specified node is a component
(Chapter, Appendix, etc.), and '0' otherwise.</para>
</refdescription>

<refparameter id="is.component-params">
<variablelist>
<varlistentry><term>node</term>
<listitem>
<para>The node which is to be tested.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>

<refreturn id="is.component-returns">
<para>This template returns '1' if the specified node is a component
(Chapter, Appendix, etc.), and '0' otherwise.</para>
</refreturn>
</doc:template>

<xsl:template name="is.component">
  <xsl:param name="node" select="."/>
  <xsl:choose>
    <xsl:when test="local-name($node) = 'appendix'
                    or local-name($node) = 'article'
                    or local-name($node) = 'chapter'
                    or local-name($node) = 'preface'
                    or local-name($node) = 'bibliography'
                    or local-name($node) = 'glossary'
                    or local-name($node) = 'index'">1</xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ====================================================================== -->

<doc:template name="is.section" xmlns="">
<refpurpose>Tests if a given node is a section-level element</refpurpose>

<refdescription id="is.section-desc">
<para>This template returns '1' if the specified node is a section
(Section, Sect1, Sect2, etc.), and '0' otherwise.</para>
</refdescription>

<refparameter id="is.section-params">
<variablelist>
<varlistentry><term>node</term>
<listitem>
<para>The node which is to be tested.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>

<refreturn id="is.section-returns">
<para>This template returns '1' if the specified node is a section
(Section, Sect1, Sect2, etc.), and '0' otherwise.</para>
</refreturn>
</doc:template>

<xsl:template name="is.section">
  <xsl:param name="node" select="."/>
  <xsl:choose>
    <xsl:when test="local-name($node) = 'section'
                    or local-name($node) = 'sect1'
                    or local-name($node) = 'sect2'
                    or local-name($node) = 'sect3'
                    or local-name($node) = 'sect4'
                    or local-name($node) = 'sect5'
                    or local-name($node) = 'refsect1'
                    or local-name($node) = 'refsect2'
                    or local-name($node) = 'refsect3'
                    or local-name($node) = 'simplesect'">1</xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ====================================================================== -->

<doc:template name="section.level" xmlns="">
<refpurpose>Returns the hierarchical level of a section</refpurpose>

<refdescription id="section.level-desc">
<para>This template calculates the hierarchical level of a section.
The element <tag>sect1</tag> is at level 1, <tag>sect2</tag> is
at level 2, etc.</para>

<para>Recursive sections are calculated down to the fifth level.</para>
</refdescription>

<refparameter id="section.level-params">
<variablelist>
<varlistentry><term>node</term>
<listitem>
<para>The section node for which the level should be calculated.
Defaults to the context node.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>

<refreturn id="section.level-returns">
<para>The section level, <quote>1</quote>, <quote>2</quote>, etc.
</para>
</refreturn>
</doc:template>

<xsl:template name="section.level">
  <xsl:param name="node" select="."/>
  <xsl:choose>
    <xsl:when test="local-name($node)='sect1'">1</xsl:when>
    <xsl:when test="local-name($node)='sect2'">2</xsl:when>
    <xsl:when test="local-name($node)='sect3'">3</xsl:when>
    <xsl:when test="local-name($node)='sect4'">4</xsl:when>
    <xsl:when test="local-name($node)='sect5'">5</xsl:when>
    <xsl:when test="local-name($node)='section'">
      <xsl:choose>
        <xsl:when test="$node/../../../../../../section">6</xsl:when>
        <xsl:when test="$node/../../../../../section">5</xsl:when>
        <xsl:when test="$node/../../../../section">4</xsl:when>
        <xsl:when test="$node/../../../section">3</xsl:when>
        <xsl:when test="$node/../../section">2</xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="local-name($node)='refsect1' or
                    local-name($node)='refsect2' or
                    local-name($node)='refsect3' or
                    local-name($node)='refsection' or
                    local-name($node)='refsynopsisdiv'">
      <xsl:call-template name="refentry.section.level">
        <xsl:with-param name="node" select="$node"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="local-name($node)='simplesect'">
      <xsl:choose>
        <xsl:when test="$node/../../sect1">2</xsl:when>
        <xsl:when test="$node/../../sect2">3</xsl:when>
        <xsl:when test="$node/../../sect3">4</xsl:when>
        <xsl:when test="$node/../../sect4">5</xsl:when>
        <xsl:when test="$node/../../sect5">5</xsl:when>
        <xsl:when test="$node/../../section">
          <xsl:choose>
            <xsl:when test="$node/../../../../../section">5</xsl:when>
            <xsl:when test="$node/../../../../section">4</xsl:when>
            <xsl:when test="$node/../../../section">3</xsl:when>
            <xsl:otherwise>2</xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>1</xsl:otherwise>
  </xsl:choose>
</xsl:template><!-- section.level -->

<doc:template name="qanda.section.level" xmlns="">
<refpurpose>Returns the hierarchical level of a QandASet</refpurpose>

<refdescription id="qanda.section.level-desc">
<para>This template calculates the hierarchical level of a QandASet.
</para>
</refdescription>

<refreturn id="qanda.section.level-returns">
<para>The level, <quote>1</quote>, <quote>2</quote>, etc.
</para>
</refreturn>
</doc:template>

<xsl:template name="qanda.section.level">
  <xsl:variable name="section"
                select="(ancestor::section
                         |ancestor::simplesect
                         |ancestor::sect5
                         |ancestor::sect4
                         |ancestor::sect3
                         |ancestor::sect2
                         |ancestor::sect1
                         |ancestor::refsect3
                         |ancestor::refsect2
                         |ancestor::refsect1)[last()]"/>

  <xsl:choose>
    <xsl:when test="count($section) = '0'">1</xsl:when>
    <xsl:otherwise>
      <xsl:variable name="slevel">
        <xsl:call-template name="section.level">
          <xsl:with-param name="node" select="$section"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:value-of select="$slevel + 1"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Finds the total section depth of a section in a refentry -->
<xsl:template name="refentry.section.level">
  <xsl:param name="node" select="."/>

  <xsl:variable name="RElevel">
    <xsl:call-template name="refentry.level">
      <xsl:with-param name="node" select="$node/ancestor::refentry[1]"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="levelinRE">
    <xsl:choose>
      <xsl:when test="local-name($node)='refsynopsisdiv'">1</xsl:when>
      <xsl:when test="local-name($node)='refsect1'">1</xsl:when>
      <xsl:when test="local-name($node)='refsect2'">2</xsl:when>
      <xsl:when test="local-name($node)='refsect3'">3</xsl:when>
      <xsl:when test="local-name($node)='refsection'">
        <xsl:choose>
          <xsl:when test="$node/../../../../../refsection">5</xsl:when>
          <xsl:when test="$node/../../../../refsection">4</xsl:when>
          <xsl:when test="$node/../../../refsection">3</xsl:when>
          <xsl:when test="$node/../../refsection">2</xsl:when>
          <xsl:otherwise>1</xsl:otherwise>
        </xsl:choose>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>

  <xsl:value-of select="$levelinRE + $RElevel"/>
</xsl:template>

<!-- Finds the section depth of a refentry -->
<xsl:template name="refentry.level">
  <xsl:param name="node" select="."/>
  <xsl:variable name="container"
                select="($node/ancestor::section |
                        $node/ancestor::sect1 |
                        $node/ancestor::sect2 |
                        $node/ancestor::sect3 |
                        $node/ancestor::sect4 |
                        $node/ancestor::sect5)[last()]"/>

  <xsl:choose>
    <xsl:when test="$container">
      <xsl:variable name="slevel">
        <xsl:call-template name="section.level">
          <xsl:with-param name="node" select="$container"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:value-of select="$slevel + 1"/>
    </xsl:when>
    <xsl:otherwise>1</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="qandadiv.section.level">
  <xsl:variable name="section.level">
    <xsl:call-template name="qanda.section.level"/>
  </xsl:variable>
  <xsl:variable name="anc.divs" select="ancestor::qandadiv"/>

  <xsl:value-of select="count($anc.divs) + number($section.level)"/>
</xsl:template>

<xsl:template name="question.answer.label">
  <xsl:variable name="deflabel">
    <xsl:choose>
      <xsl:when test="ancestor-or-self::*[@defaultlabel]">
        <xsl:value-of select="(ancestor-or-self::*[@defaultlabel])[last()]
                              /@defaultlabel"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$qanda.defaultlabel"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="label" select="@label"/>

<!--
 (hnr      (hierarchical-number-recursive (normalize "qandadiv") node))

         (parsect  (ancestor-member node (section-element-list)))

         (defnum   (if (and %qanda-inherit-numeration% 
                            %section-autolabel%)
                       (if (node-list-empty? parsect)
                           (section-autolabel-prefix node)
                           (section-autolabel parsect))
                       ""))

         (hnumber  (let loop ((numlist hnr) (number defnum) 
                              (sep (if (equal? defnum "") "" ".")))
                     (if (null? numlist)
                         number
                         (loop (cdr numlist) 
                               (string-append number
                                              sep
                                              (number->string (car numlist)))
                               "."))))
         (cnumber  (child-number (parent node)))
         (number   (string-append hnumber 
                                  (if (equal? hnumber "")
                                      ""
                                      ".")
                                  (number->string cnumber))))
-->

  <xsl:choose>
    <xsl:when test="$deflabel = 'qanda'">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key">
          <xsl:choose>
            <xsl:when test="local-name(.) = 'question'">question</xsl:when>
            <xsl:when test="local-name(.) = 'answer'">answer</xsl:when>
            <xsl:when test="local-name(.) = 'qandadiv'">qandadiv</xsl:when>
            <xsl:otherwise>qandaset</xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$deflabel = 'label'">
      <xsl:value-of select="$label"/>
    </xsl:when>
    <xsl:when test="$deflabel = 'number'
                    and local-name(.) = 'question'">
      <xsl:apply-templates select="ancestor::qandaset[1]"
                           mode="number"/>
      <xsl:choose>
        <xsl:when test="ancestor::qandadiv">
          <xsl:apply-templates select="ancestor::qandadiv[1]"
                               mode="number"/>
          <xsl:apply-templates select="ancestor::qandaentry"
                               mode="number"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="ancestor::qandaentry"
                               mode="number"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <!-- nothing -->
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="qandaset" mode="number">
  <!-- FIXME: -->
</xsl:template>

<xsl:template match="qandadiv" mode="number">
  <xsl:number level="multiple" from="qandaset" format="1."/>
</xsl:template>

<xsl:template match="qandaentry" mode="number">
  <xsl:choose>
    <xsl:when test="ancestor::qandadiv">
      <xsl:number level="single" from="qandadiv" format="1."/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:number level="single" from="qandaset" format="1."/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ====================================================================== -->

<xsl:template name="object.id">
  <xsl:param name="object" select="."/>
  <xsl:choose>
    <xsl:when test="$object/@id">
      <xsl:value-of select="$object/@id"/>
    </xsl:when>
    <xsl:when test="$object/@xml:id">
      <xsl:value-of select="$object/@xml:id"/>
    </xsl:when>
    <xsl:when test="$generate.consistent.ids != 0">
      <!-- Make $object the current node -->
      <xsl:for-each select="$object">
        <xsl:text>id-</xsl:text>
        <xsl:number level="multiple" count="*"/>
      </xsl:for-each>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="generate-id($object)"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="person.name">
  <!-- Formats a personal name. Handles corpauthor as a special case. -->
  <xsl:param name="node" select="."/>

  <xsl:variable name="style">
    <xsl:choose>
      <xsl:when test="$node/@role">
        <xsl:value-of select="$node/@role"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="context" select="'styles'"/>
          <xsl:with-param name="name" select="'person-name'"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <!-- the personname element is a specialcase -->
    <xsl:when test="$node/personname">
      <xsl:call-template name="person.name">
        <xsl:with-param name="node" select="$node/personname"/>
      </xsl:call-template>
    </xsl:when>

    <!-- handle corpauthor as a special case...-->
    <!-- * MikeSmith 2007-06: I'm wondering if the person.name template -->
    <!-- * actually ever gets called to handle corpauthor.. maybe -->
    <!-- * we don't actually need to check for corpauthor here. -->
    <xsl:when test="local-name($node)='corpauthor'">
      <xsl:apply-templates select="$node"/>
    </xsl:when>

    <xsl:otherwise>
      <xsl:choose>
        <!-- Handle case when personname contains only general markup (DocBook 5.0) -->
        <xsl:when test="$node/self::personname and not($node/firstname or $node/honorific or $node/lineage or $node/othername or $node/surname)">
          <xsl:apply-templates select="$node/node()"/>
        </xsl:when>
        <xsl:when test="$style = 'family-given'">
          <xsl:call-template name="person.name.family-given">
            <xsl:with-param name="node" select="$node"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:when test="$style = 'last-first'">
          <xsl:call-template name="person.name.last-first">
            <xsl:with-param name="node" select="$node"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="person.name.first-last">
            <xsl:with-param name="node" select="$node"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="person.name.family-given">
  <xsl:param name="node" select="."/>

  <!-- The family-given style applies a convention for identifying given -->
  <!-- and family names in locales where it may be ambiguous -->
  <xsl:apply-templates select="$node//surname[1]"/>

  <xsl:if test="$node//surname and $node//firstname">
    <xsl:text> </xsl:text>
  </xsl:if>

  <xsl:apply-templates select="$node//firstname[1]"/>

  <xsl:text> [FAMILY Given]</xsl:text>
</xsl:template>

<xsl:template name="person.name.last-first">
  <xsl:param name="node" select="."/>

  <xsl:apply-templates select="$node//surname[1]"/>

  <xsl:if test="$node//surname and $node//firstname">
    <xsl:text>, </xsl:text>
  </xsl:if>

  <xsl:apply-templates select="$node//firstname[1]"/>
</xsl:template>

<xsl:template name="person.name.first-last">
  <xsl:param name="node" select="."/>

  <xsl:if test="$node//honorific">
    <xsl:apply-templates select="$node//honorific[1]"/>
    <xsl:value-of select="$punct.honorific"/>
  </xsl:if>

  <xsl:if test="$node//firstname">
    <xsl:if test="$node//honorific">
      <xsl:text> </xsl:text>
    </xsl:if>
    <xsl:apply-templates select="$node//firstname[1]"/>
  </xsl:if>

  <xsl:if test="$node//othername and $author.othername.in.middle != 0">
    <xsl:if test="$node//honorific or $node//firstname">
      <xsl:text> </xsl:text>
    </xsl:if>
    <xsl:apply-templates select="$node//othername[1]"/>
  </xsl:if>

  <xsl:if test="$node//surname">
    <xsl:if test="$node//honorific or $node//firstname
                  or ($node//othername and $author.othername.in.middle != 0)">
      <xsl:text> </xsl:text>
    </xsl:if>
    <xsl:apply-templates select="$node//surname[1]"/>
  </xsl:if>

  <xsl:if test="$node//lineage">
    <xsl:text>, </xsl:text>
    <xsl:apply-templates select="$node//lineage[1]"/>
  </xsl:if>
</xsl:template>

<xsl:template name="person.name.list">
  <!-- Return a formatted string representation of the contents of
       the current element. The current element must contain one or
       more AUTHORs, CORPAUTHORs, OTHERCREDITs, and/or EDITORs.

       John Doe
     or
       John Doe and Jane Doe
     or
       John Doe, Jane Doe, and A. Nonymous
  -->
  <xsl:param name="person.list"
             select="author|corpauthor|othercredit|editor"/>
  <xsl:param name="person.count" select="count($person.list)"/>
  <xsl:param name="count" select="1"/>

  <xsl:choose>
    <xsl:when test="$count &gt; $person.count"></xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="person.name">
        <xsl:with-param name="node" select="$person.list[position()=$count]"/>
      </xsl:call-template>

      <xsl:choose>
        <xsl:when test="$person.count = 2 and $count = 1">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'authorgroup'"/>
            <xsl:with-param name="name" select="'sep2'"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:when test="$person.count &gt; 2 and $count+1 = $person.count">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'authorgroup'"/>
            <xsl:with-param name="name" select="'seplast'"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:when test="$count &lt; $person.count">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'authorgroup'"/>
            <xsl:with-param name="name" select="'sep'"/>
          </xsl:call-template>
        </xsl:when>
      </xsl:choose>

      <xsl:call-template name="person.name.list">
        <xsl:with-param name="person.list" select="$person.list"/>
        <xsl:with-param name="person.count" select="$person.count"/>
        <xsl:with-param name="count" select="$count+1"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template><!-- person.name.list -->

<!-- === synopsis ======================================================= -->
<!-- The following definitions match those given in the reference
     documentation for DocBook V3.0
-->

<xsl:param name="arg.choice.opt.open.str">[</xsl:param>
<xsl:param name="arg.choice.opt.close.str">]</xsl:param>
<xsl:param name="arg.choice.req.open.str">{</xsl:param>
<xsl:param name="arg.choice.req.close.str">}</xsl:param>
<xsl:param name="arg.choice.plain.open.str"><xsl:text> </xsl:text></xsl:param>
<xsl:param name="arg.choice.plain.close.str"><xsl:text> </xsl:text></xsl:param>
<xsl:param name="arg.choice.def.open.str">[</xsl:param>
<xsl:param name="arg.choice.def.close.str">]</xsl:param>
<xsl:param name="arg.rep.repeat.str">...</xsl:param>
<xsl:param name="arg.rep.norepeat.str"></xsl:param>
<xsl:param name="arg.rep.def.str"></xsl:param>
<xsl:param name="arg.or.sep"> | </xsl:param>
<xsl:param name="cmdsynopsis.hanging.indent">4pi</xsl:param>

<!-- ====================================================================== -->

<!--
<xsl:template name="xref.g.subst">
  <xsl:param name="string"></xsl:param>
  <xsl:param name="target" select="."/>
  <xsl:variable name="subst">%g</xsl:variable>

  <xsl:choose>
    <xsl:when test="contains($string, $subst)">
      <xsl:value-of select="substring-before($string, $subst)"/>
      <xsl:call-template name="gentext.element.name">
        <xsl:with-param name="element.name" select="local-name($target)"/>
      </xsl:call-template>
      <xsl:call-template name="xref.g.subst">
        <xsl:with-param name="string"
                        select="substring-after($string, $subst)"/>
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$string"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="xref.t.subst">
  <xsl:param name="string"></xsl:param>
  <xsl:param name="target" select="."/>
  <xsl:variable name="subst">%t</xsl:variable>

  <xsl:choose>
    <xsl:when test="contains($string, $subst)">
      <xsl:call-template name="xref.g.subst">
        <xsl:with-param name="string"
                        select="substring-before($string, $subst)"/>
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
      <xsl:call-template name="title.xref">
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
      <xsl:call-template name="xref.t.subst">
        <xsl:with-param name="string"
                        select="substring-after($string, $subst)"/>
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="xref.g.subst">
        <xsl:with-param name="string" select="$string"/>
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="xref.n.subst">
  <xsl:param name="string"></xsl:param>
  <xsl:param name="target" select="."/>
  <xsl:variable name="subst">%n</xsl:variable>

  <xsl:choose>
    <xsl:when test="contains($string, $subst)">
      <xsl:call-template name="xref.t.subst">
        <xsl:with-param name="string"
                        select="substring-before($string, $subst)"/>
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
      <xsl:call-template name="number.xref">
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
      <xsl:call-template name="xref.t.subst">
        <xsl:with-param name="string"
                        select="substring-after($string, $subst)"/>
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="xref.t.subst">
        <xsl:with-param name="string" select="$string"/>
        <xsl:with-param name="target" select="$target"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="subst.xref.text">
  <xsl:param name="xref.text"></xsl:param>
  <xsl:param name="target" select="."/>

  <xsl:call-template name="xref.n.subst">
    <xsl:with-param name="string" select="$xref.text"/>
    <xsl:with-param name="target" select="$target"/>
  </xsl:call-template>
</xsl:template>
-->

<!-- ====================================================================== -->

<xsl:template name="filename-basename">
  <!-- We assume all filenames are really URIs and use "/" -->
  <xsl:param name="filename"></xsl:param>
  <xsl:param name="recurse" select="false()"/>

  <xsl:choose>
    <xsl:when test="substring-after($filename, '/') != ''">
      <xsl:call-template name="filename-basename">
        <xsl:with-param name="filename"
                        select="substring-after($filename, '/')"/>
        <xsl:with-param name="recurse" select="true()"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$filename"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="filename-extension">
  <xsl:param name="filename"></xsl:param>
  <xsl:param name="recurse" select="false()"/>

  <!-- Make sure we only look at the base name... -->
  <xsl:variable name="basefn">
    <xsl:choose>
      <xsl:when test="$recurse">
        <xsl:value-of select="$filename"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="filename-basename">
          <xsl:with-param name="filename" select="$filename"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="substring-after($basefn, '.') != ''">
      <xsl:call-template name="filename-extension">
        <xsl:with-param name="filename"
                        select="substring-after($basefn, '.')"/>
        <xsl:with-param name="recurse" select="true()"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$recurse">
      <xsl:value-of select="$basefn"/>
    </xsl:when>
    <xsl:otherwise></xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ====================================================================== -->

<doc:template name="select.mediaobject" xmlns="">
<refpurpose>Selects and processes an appropriate media object from a list</refpurpose>

<refdescription id="select.mediaobject-desc">
<para>This template takes a list of media objects (usually the
children of a mediaobject or inlinemediaobject) and processes
the "right" object.</para>

<para>This template relies on a template named 
"select.mediaobject.index" to determine which object
in the list is appropriate.</para>

<para>If no acceptable object is located, nothing happens.</para>
</refdescription>

<refparameter id="select.mediaobject-params">
<variablelist>
<varlistentry><term>olist</term>
<listitem>
<para>The node list of potential objects to examine.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>

<refreturn id="select.mediaobject-returns">
<para>Calls &lt;xsl:apply-templates&gt; on the selected object.</para>
</refreturn>
</doc:template>

<xsl:template name="select.mediaobject">
  <xsl:param name="olist"
             select="imageobject|imageobjectco
                     |videoobject|audioobject|textobject"/>
  
  <xsl:variable name="mediaobject.index">
    <xsl:call-template name="select.mediaobject.index">
      <xsl:with-param name="olist" select="$olist"/>
      <xsl:with-param name="count" select="1"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$mediaobject.index != ''">
    <xsl:apply-templates select="$olist[position() = $mediaobject.index]"/>
  </xsl:if>
</xsl:template>

<!-- ====================================================================== -->

<doc:template name="select.mediaobject.index" xmlns="">
<refpurpose>Selects the position of the appropriate media object from a list</refpurpose>

<refdescription id="select.mediaobject.index-desc">
<para>This template takes a list of media objects (usually the
children of a mediaobject or inlinemediaobject) and determines
the "right" object. It returns the position of that object
to be used by the calling template.</para>

<para>If the parameter <parameter>use.role.for.mediaobject</parameter>
is nonzero, then it first checks for an object with
a role attribute of the appropriate value.  It takes the first
of those.  Otherwise, it takes the first acceptable object
through a recursive pass through the list.</para>

<para>This template relies on a template named "is.acceptable.mediaobject"
to determine if a given object is an acceptable graphic. The semantics
of media objects is that the first acceptable graphic should be used.
</para>

<para>If no acceptable object is located, no index is returned.</para>
</refdescription>

<refparameter id="select.mediaobject.index-params">
<variablelist>
<varlistentry><term>olist</term>
<listitem>
<para>The node list of potential objects to examine.</para>
</listitem>
</varlistentry>
<varlistentry><term>count</term>
<listitem>
<para>The position in the list currently being considered by the 
recursive process.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>

<refreturn id="select.mediaobject.index-returns">
<para>Returns the position in the original list of the selected object.</para>
</refreturn>
</doc:template>

<xsl:template name="select.mediaobject.index">
  <xsl:param name="olist"
             select="imageobject|imageobjectco
                     |videoobject|audioobject|textobject"/>
  <xsl:param name="count">1</xsl:param>

  <xsl:choose>
    <!-- Test for objects preferred by role -->
    <xsl:when test="$use.role.for.mediaobject != 0 
               and $preferred.mediaobject.role != ''
               and $olist[@role = $preferred.mediaobject.role]"> 
      
      <!-- Get the first hit's position index -->
      <xsl:for-each select="$olist">
        <xsl:if test="@role = $preferred.mediaobject.role and
             not(preceding-sibling::*[@role = $preferred.mediaobject.role])"> 
          <xsl:value-of select="position()"/> 
        </xsl:if>
      </xsl:for-each>
    </xsl:when>

    <xsl:when test="$use.role.for.mediaobject != 0 
               and $olist[@role = $stylesheet.result.type]">
      <!-- Get the first hit's position index -->
      <xsl:for-each select="$olist">
        <xsl:if test="@role = $stylesheet.result.type and 
              not(preceding-sibling::*[@role = $stylesheet.result.type])"> 
          <xsl:value-of select="position()"/> 
        </xsl:if>
      </xsl:for-each>
    </xsl:when>
    <!-- Accept 'html' for $stylesheet.result.type = 'xhtml' -->
    <xsl:when test="$use.role.for.mediaobject != 0 
               and $stylesheet.result.type = 'xhtml'
               and $olist[@role = 'html']">
      <!-- Get the first hit's position index -->
      <xsl:for-each select="$olist">
        <xsl:if test="@role = 'html' and 
              not(preceding-sibling::*[@role = 'html'])"> 
          <xsl:value-of select="position()"/> 
        </xsl:if>
      </xsl:for-each>
    </xsl:when>

    <!-- If no selection by role, and there is only one object, use it -->
    <xsl:when test="count($olist) = 1 and $count = 1">
      <xsl:value-of select="$count"/> 
    </xsl:when>

    <xsl:otherwise>
      <!-- Otherwise select first acceptable object -->
      <xsl:if test="$count &lt;= count($olist)">
        <xsl:variable name="object" select="$olist[position()=$count]"/>
    
        <xsl:variable name="useobject">
          <xsl:choose>
            <!-- select videoobject or audioobject before textobject -->
            <xsl:when test="local-name($object) = 'videoobject'">
              <xsl:text>1</xsl:text> 
            </xsl:when>
            <xsl:when test="local-name($object) = 'audioobject'">
              <xsl:text>1</xsl:text> 
            </xsl:when>
            <!-- skip textobject if also video, audio, or image out of order -->
            <xsl:when test="local-name($object) = 'textobject' and
                            ../imageobject or
                            ../audioobject or
                            ../videoobject">
              <xsl:text>0</xsl:text> 
            </xsl:when>
            <!-- The phrase is used only when contains TeX Math and output is FO -->
            <xsl:when test="local-name($object)='textobject' and $object/phrase
                            and $object/@role='tex' and $stylesheet.result.type = 'fo'
                            and $tex.math.in.alt != ''">
              <xsl:text>1</xsl:text> 
            </xsl:when>
            <!-- The phrase is never used -->
            <xsl:when test="local-name($object)='textobject' and $object/phrase">
              <xsl:text>0</xsl:text>
            </xsl:when>
            <xsl:when test="local-name($object)='textobject'
                            and $object/ancestor::equation ">
            <!-- The first textobject is not a reasonable fallback
                 for equation image -->
              <xsl:text>0</xsl:text>
            </xsl:when>
            <!-- The first textobject is a reasonable fallback -->
            <xsl:when test="local-name($object)='textobject'
                            and $object[not(@role) or @role!='tex']">
              <xsl:text>1</xsl:text>
            </xsl:when>
            <!-- don't use graphic when output is FO, TeX Math is used 
                 and there is math in alt element -->
            <xsl:when test="$object/ancestor::equation and 
                            $object/ancestor::equation/alt[@role='tex']
                            and $stylesheet.result.type = 'fo'
                            and $tex.math.in.alt != ''">
              <xsl:text>0</xsl:text>
            </xsl:when>
            <!-- If there's only one object, use it -->
            <xsl:when test="$count = 1 and count($olist) = 1">
               <xsl:text>1</xsl:text>
            </xsl:when>
            <!-- Otherwise, see if this one is a useable graphic -->
            <xsl:otherwise>
              <xsl:choose>
                <!-- peek inside imageobjectco to simplify the test -->
                <xsl:when test="local-name($object) = 'imageobjectco'">
                  <xsl:call-template name="is.acceptable.mediaobject">
                    <xsl:with-param name="object" select="$object/imageobject"/>
                  </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:call-template name="is.acceptable.mediaobject">
                    <xsl:with-param name="object" select="$object"/>
                  </xsl:call-template>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
    
        <xsl:choose>
          <xsl:when test="$useobject='1'">
            <xsl:value-of select="$count"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name="select.mediaobject.index">
              <xsl:with-param name="olist" select="$olist"/>
              <xsl:with-param name="count" select="$count + 1"/>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<doc:template name="is.acceptable.mediaobject" xmlns="">
<refpurpose>Returns '1' if the specified media object is recognized</refpurpose>

<refdescription id="is.acceptable.mediaobject-desc">
<para>This template examines a media object and returns '1' if the
object is recognized as a graphic.</para>
</refdescription>

<refparameter id="is.acceptable.mediaobject-params">
<variablelist>
<varlistentry><term>object</term>
<listitem>
<para>The media object to consider.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>

<refreturn id="is.acceptable.mediaobject-returns">
<para>0 or 1</para>
</refreturn>
</doc:template>

<xsl:template name="is.acceptable.mediaobject">
  <xsl:param name="object"></xsl:param>

  <xsl:variable name="filename">
    <xsl:call-template name="mediaobject.filename">
      <xsl:with-param name="object" select="$object"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="ext">
    <xsl:call-template name="filename-extension">
      <xsl:with-param name="filename" select="$filename"/>
    </xsl:call-template>
  </xsl:variable>

  <!-- there will only be one -->
  <xsl:variable name="data" select="$object/videodata
                                    |$object/imagedata
                                    |$object/audiodata"/>

  <xsl:variable name="format" select="$data/@format"/>

  <xsl:variable name="graphic.format">
    <xsl:if test="$format">
      <xsl:call-template name="is.graphic.format">
        <xsl:with-param name="format" select="$format"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="graphic.ext">
    <xsl:if test="$ext">
      <xsl:call-template name="is.graphic.extension">
        <xsl:with-param name="ext" select="$ext"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$use.svg = 0 and $format = 'SVG'">0</xsl:when>
    <xsl:when xmlns:svg="http://www.w3.org/2000/svg"
              test="$use.svg != 0 and $object/svg:*">1</xsl:when>
    <xsl:when test="$graphic.format = '1'">1</xsl:when>
    <xsl:when test="$graphic.ext = '1'">1</xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="mediaobject.filename">
  <xsl:param name="object"></xsl:param>

  <xsl:variable name="data" select="$object/videodata
                                    |$object/imagedata
                                    |$object/audiodata
                                    |$object"/>

  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="$data[@fileref]">
        <xsl:apply-templates select="$data/@fileref"/>
      </xsl:when>
      <xsl:when test="$data[@entityref]">
        <xsl:value-of select="unparsed-entity-uri($data/@entityref)"/>
      </xsl:when>
      <xsl:otherwise></xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="real.ext">
    <xsl:call-template name="filename-extension">
      <xsl:with-param name="filename" select="$filename"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="ext">
    <xsl:choose>
      <xsl:when test="$real.ext != ''">
        <xsl:value-of select="$real.ext"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$graphic.default.extension"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="graphic.ext">
    <xsl:call-template name="is.graphic.extension">
      <xsl:with-param name="ext" select="$ext"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$real.ext = ''">
      <xsl:choose>
        <xsl:when test="$ext != ''">
          <xsl:value-of select="$filename"/>
          <xsl:text>.</xsl:text>
          <xsl:value-of select="$ext"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$filename"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="not($graphic.ext)">
      <xsl:choose>
        <xsl:when test="$graphic.default.extension != ''">
          <xsl:value-of select="$filename"/>
          <xsl:text>.</xsl:text>
          <xsl:value-of select="$graphic.default.extension"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$filename"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$filename"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ====================================================================== -->

<doc:template name="check.id.unique" xmlns="">
<refpurpose>Warn users about references to non-unique IDs</refpurpose>
<refdescription id="check.id.unique-desc">
<para>If passed an ID in <varname>linkend</varname>,
<function>check.id.unique</function> prints
a warning message to the user if either the ID does not exist or
the ID is not unique.</para>
</refdescription>
</doc:template>

<xsl:template name="check.id.unique">
  <xsl:param name="linkend"></xsl:param>
  <xsl:if test="$linkend != ''">
    <xsl:variable name="targets" select="key('id',$linkend)"/>
    <xsl:variable name="target" select="$targets[1]"/>

    <xsl:if test="count($targets)=0">
      <xsl:message>
        <xsl:text>Error: no ID for constraint linkend: </xsl:text>
        <xsl:value-of select="$linkend"/>
        <xsl:text>.</xsl:text>
      </xsl:message>
      <!--
      <xsl:message>
        <xsl:text>If the ID exists in your document, did your </xsl:text>
        <xsl:text>XSLT Processor load the DTD?</xsl:text>
      </xsl:message>
      -->
    </xsl:if>

    <xsl:if test="count($targets)>1">
      <xsl:message>
        <xsl:text>Warning: multiple "IDs" for constraint linkend: </xsl:text>
        <xsl:value-of select="$linkend"/>
        <xsl:text>.</xsl:text>
      </xsl:message>
    </xsl:if>
  </xsl:if>
</xsl:template>

<doc:template name="check.idref.targets" xmlns="">
<refpurpose>Warn users about incorrectly typed references</refpurpose>
<refdescription id="check.idref.targets-desc">
<para>If passed an ID in <varname>linkend</varname>,
<function>check.idref.targets</function> makes sure that the element
pointed to by the link is one of the elements listed in
<varname>element-list</varname> and warns the user otherwise.</para>
</refdescription>
</doc:template>

<xsl:template name="check.idref.targets">
  <xsl:param name="linkend"></xsl:param>
  <xsl:param name="element-list"></xsl:param>
  <xsl:if test="$linkend != ''">
    <xsl:variable name="targets" select="key('id',$linkend)"/>
    <xsl:variable name="target" select="$targets[1]"/>

    <xsl:if test="count($target) &gt; 0">
      <xsl:if test="not(contains(concat(' ', $element-list, ' '), local-name($target)))">
        <xsl:message>
          <xsl:text>Error: linkend (</xsl:text>
          <xsl:value-of select="$linkend"/>
          <xsl:text>) points to "</xsl:text>
          <xsl:value-of select="local-name($target)"/>
          <xsl:text>" not (one of): </xsl:text>
          <xsl:value-of select="$element-list"/>
        </xsl:message>
      </xsl:if>
    </xsl:if>
  </xsl:if>
</xsl:template>

<!-- ====================================================================== -->
<!-- Procedure Step Numeration -->

<xsl:param name="procedure.step.numeration.formats" select="'1aiAI'"/>

<xsl:template name="procedure.step.numeration">
  <xsl:param name="context" select="."/>
  <xsl:variable name="format.length"
                select="string-length($procedure.step.numeration.formats)"/>
  <xsl:choose>
    <xsl:when test="local-name($context) = 'substeps'">
      <xsl:variable name="ssdepth"
                    select="count($context/ancestor::substeps)"/>
      <xsl:variable name="sstype" select="($ssdepth mod $format.length)+2"/>
      <xsl:choose>
        <xsl:when test="$sstype &gt; $format.length">
          <xsl:value-of select="substring($procedure.step.numeration.formats,1,1)"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="substring($procedure.step.numeration.formats,$sstype,1)"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="local-name($context) = 'step'">
      <xsl:variable name="sdepth"
                    select="count($context/ancestor::substeps)"/>
      <xsl:variable name="stype" select="($sdepth mod $format.length)+1"/>
      <xsl:value-of select="substring($procedure.step.numeration.formats,$stype,1)"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>
        <xsl:text>Unexpected context in procedure.step.numeration: </xsl:text>
        <xsl:value-of select="local-name($context)"/>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="step" mode="number">
  <xsl:param name="rest" select="''"/>
  <xsl:param name="recursive" select="1"/>
  <xsl:variable name="format">
    <xsl:call-template name="procedure.step.numeration"/>
  </xsl:variable>
  <xsl:variable name="num">
    <xsl:number count="step" format="{$format}"/>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="$recursive != 0 and ancestor::step">
      <xsl:apply-templates select="ancestor::step[1]" mode="number">
        <xsl:with-param name="rest" select="concat('.', $num, $rest)"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="concat($num, $rest)"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ====================================================================== -->
<!-- OrderedList Numeration -->
<xsl:template name="output-orderedlist-starting-number">
  <xsl:param name="list"/>
  <xsl:param name="pi-start"/>
  <xsl:choose>
    <xsl:when test="not($list/@continuation = 'continues')">
      <xsl:choose>
        <xsl:when test="$list/@startingnumber">
          <xsl:value-of select="$list/@startingnumber"/>
        </xsl:when>
        <xsl:when test="$pi-start != ''">
          <xsl:value-of select="$pi-start"/>
        </xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <!-- match on previous list at same nesting level -->
      <xsl:variable name="prevlist" 
       select="$list/preceding::orderedlist
                [count($list/ancestor::orderedlist) = count(ancestor::orderedlist)][1]"/>
      <xsl:choose>
        <xsl:when test="count($prevlist) = 0">2</xsl:when>
        <xsl:otherwise>
          <xsl:variable name="prevlength" select="count($prevlist/listitem)"/>
          <xsl:variable name="prevstart">
            <xsl:call-template name="orderedlist-starting-number">
              <xsl:with-param name="list" select="$prevlist"/>
            </xsl:call-template>
          </xsl:variable>
          <xsl:value-of select="$prevstart + $prevlength"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="orderedlist-item-number">
  <!-- context node must be a listitem in an orderedlist -->
  <xsl:param name="node" select="."/>
  <xsl:choose>
    <xsl:when test="$node/@override">
      <xsl:value-of select="$node/@override"/>
    </xsl:when>
    <xsl:when test="$node/preceding-sibling::listitem">
      <xsl:variable name="pnum">
        <xsl:call-template name="orderedlist-item-number">
          <xsl:with-param name="node" select="$node/preceding-sibling::listitem[1]"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:value-of select="$pnum + 1"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="orderedlist-starting-number">
        <xsl:with-param name="list" select="parent::*"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="next.numeration">
  <xsl:param name="numeration" select="'default'"/>
  <xsl:choose>
    <!-- Change this list if you want to change the order of numerations -->
    <xsl:when test="$numeration = 'arabic'">loweralpha</xsl:when>
    <xsl:when test="$numeration = 'loweralpha'">lowerroman</xsl:when>
    <xsl:when test="$numeration = 'lowerroman'">upperalpha</xsl:when>
    <xsl:when test="$numeration = 'upperalpha'">upperroman</xsl:when>
    <xsl:when test="$numeration = 'upperroman'">arabic</xsl:when>
    <xsl:otherwise>arabic</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="list.numeration">
  <xsl:param name="node" select="."/>

  <xsl:choose>
    <xsl:when test="$node/@numeration">
      <xsl:value-of select="$node/@numeration"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$node/ancestor::orderedlist">
          <xsl:call-template name="next.numeration">
            <xsl:with-param name="numeration">
              <xsl:call-template name="list.numeration">
                <xsl:with-param name="node" select="$node/ancestor::orderedlist[1]"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="next.numeration"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="orderedlist/listitem" mode="item-number">
  <xsl:variable name="numeration">
    <xsl:call-template name="list.numeration">
      <xsl:with-param name="node" select="parent::orderedlist"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="type">
    <xsl:choose>
      <xsl:when test="$numeration='arabic'">1.</xsl:when>
      <xsl:when test="$numeration='loweralpha'">a.</xsl:when>
      <xsl:when test="$numeration='lowerroman'">i.</xsl:when>
      <xsl:when test="$numeration='upperalpha'">A.</xsl:when>
      <xsl:when test="$numeration='upperroman'">I.</xsl:when>
      <!-- What!? This should never happen -->
      <xsl:otherwise>
        <xsl:message>
          <xsl:text>Unexpected numeration: </xsl:text>
          <xsl:value-of select="$numeration"/>
        </xsl:message>
        <xsl:value-of select="1."/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="item-number">
    <xsl:call-template name="orderedlist-item-number"/>
  </xsl:variable>

  <xsl:if test="parent::orderedlist/@inheritnum='inherit'
                and ancestor::listitem[parent::orderedlist]">
    <xsl:apply-templates select="ancestor::listitem[parent::orderedlist][1]"
                         mode="item-number"/>
  </xsl:if>

  <xsl:number value="$item-number" format="{$type}"/>
</xsl:template>

<!-- ====================================================================== -->
<!-- ItemizedList "Numeration" -->

<xsl:template name="next.itemsymbol">
  <xsl:param name="itemsymbol" select="'default'"/>
  <xsl:choose>
    <!-- Change this list if you want to change the order of symbols -->
    <xsl:when test="$itemsymbol = 'disc'">circle</xsl:when>
    <xsl:when test="$itemsymbol = 'circle'">square</xsl:when>
    <xsl:otherwise>disc</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="list.itemsymbol">
  <xsl:param name="node" select="."/>

  <xsl:choose>
    <xsl:when test="@override">
      <xsl:value-of select="@override"/>
    </xsl:when>
    <xsl:when test="$node/@mark">
      <xsl:value-of select="$node/@mark"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$node/ancestor::itemizedlist">
          <xsl:call-template name="next.itemsymbol">
            <xsl:with-param name="itemsymbol">
              <xsl:call-template name="list.itemsymbol">
                <xsl:with-param name="node" select="$node/ancestor::itemizedlist[1]"/>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="next.itemsymbol"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ====================================================================== -->

<doc:template name="copyright.years" xmlns="">
<refpurpose>Print a set of years with collapsed ranges</refpurpose>

<refdescription id="copyright.years-desc">
<para>This template prints a list of year elements with consecutive
years printed as a range. In other words:</para>

<screen><![CDATA[<year>1992</year>
<year>1993</year>
<year>1994</year>]]></screen>

<para>is printed <quote>1992-1994</quote>, whereas:</para>

<screen><![CDATA[<year>1992</year>
<year>1994</year>]]></screen>

<para>is printed <quote>1992, 1994</quote>.</para>

<para>This template assumes that all the year elements contain only
decimal year numbers, that the elements are sorted in increasing
numerical order, that there are no duplicates, and that all the years
are expressed in full <quote>century+year</quote>
(<quote>1999</quote> not <quote>99</quote>) notation.</para>
</refdescription>

<refparameter id="copyright.years-params">
<variablelist>
<varlistentry><term>years</term>
<listitem>
<para>The initial set of year elements.</para>
</listitem>
</varlistentry>
<varlistentry><term>print.ranges</term>
<listitem>
<para>If non-zero, multi-year ranges are collapsed. If zero, all years
are printed discretely.</para>
</listitem>
</varlistentry>
<varlistentry><term>single.year.ranges</term>
<listitem>
<para>If non-zero, two consecutive years will be printed as a range,
otherwise, they will be printed discretely. In other words, a single
year range is <quote>1991-1992</quote> but discretely it's
<quote>1991, 1992</quote>.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>

<refreturn id="copyright.years-returns">
<para>This template returns the formatted list of years.</para>
</refreturn>
</doc:template>

<xsl:template name="copyright.years">
  <xsl:param name="years"/>
  <xsl:param name="print.ranges" select="1"/>
  <xsl:param name="single.year.ranges" select="0"/>
  <xsl:param name="firstyear" select="0"/>
  <xsl:param name="nextyear" select="0"/>

  <!--
  <xsl:message terminate="no">
    <xsl:text>CY: </xsl:text>
    <xsl:value-of select="count($years)"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$firstyear"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$nextyear"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$print.ranges"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="$single.year.ranges"/>
    <xsl:text> (</xsl:text>
    <xsl:value-of select="$years[1]"/>
    <xsl:text>)</xsl:text>
  </xsl:message>
  -->
    
  <xsl:choose>
    <xsl:when test="$print.ranges = 0 and count($years) &gt; 0">
      <xsl:choose>
        <xsl:when test="count($years) = 1">
          <xsl:apply-templates select="$years[1]" mode="titlepage.mode"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="$years[1]" mode="titlepage.mode"/>
          <xsl:text>, </xsl:text>
          <xsl:call-template name="copyright.years">
            <xsl:with-param name="years"
                            select="$years[position() &gt; 1]"/>
            <xsl:with-param name="print.ranges" select="$print.ranges"/>
            <xsl:with-param name="single.year.ranges"
                            select="$single.year.ranges"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="count($years) = 0">
      <xsl:variable name="lastyear" select="$nextyear - 1"/>
      <xsl:choose>
        <xsl:when test="$firstyear = 0">
          <!-- there weren't any years at all -->
        </xsl:when>
        <!-- Just output a year with range in its text -->
        <xsl:when test="contains($firstyear, '-') or contains($firstyear, ',')">
          <xsl:value-of select="$firstyear"/>
        </xsl:when>
        <xsl:when test="$firstyear = $lastyear">
          <xsl:value-of select="$firstyear"/>
        </xsl:when>
        <xsl:when test="$single.year.ranges = 0
                        and $lastyear = $firstyear + 1">
          <xsl:value-of select="$firstyear"/>
          <xsl:text>, </xsl:text>
          <xsl:value-of select="$lastyear"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$firstyear"/>
          <xsl:text>-</xsl:text>
          <xsl:value-of select="$lastyear"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="contains($firstyear, '-') or contains($firstyear, ',')">
      <!-- Just output a year with range in its text -->
      <xsl:value-of select="$firstyear"/>
      <xsl:if test="count($years) != 0">
        <xsl:text>, </xsl:text>
      </xsl:if>
      <xsl:call-template name="copyright.years">
        <xsl:with-param name="years"
              select="$years[position() &gt; 1]"/>
        <xsl:with-param name="firstyear" select="$years[1]"/>
        <xsl:with-param name="nextyear" select="$years[1] + 1"/>
        <xsl:with-param name="print.ranges" select="$print.ranges"/>
        <xsl:with-param name="single.year.ranges"
                select="$single.year.ranges"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$firstyear = 0">
      <xsl:call-template name="copyright.years">
        <xsl:with-param name="years"
                        select="$years[position() &gt; 1]"/>
        <xsl:with-param name="firstyear" select="$years[1]"/>
        <xsl:with-param name="nextyear" select="$years[1] + 1"/>
        <xsl:with-param name="print.ranges" select="$print.ranges"/>
        <xsl:with-param name="single.year.ranges"
                        select="$single.year.ranges"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$nextyear = $years[1]">
      <xsl:call-template name="copyright.years">
        <xsl:with-param name="years"
                        select="$years[position() &gt; 1]"/>
        <xsl:with-param name="firstyear" select="$firstyear"/>
        <xsl:with-param name="nextyear" select="$nextyear + 1"/>
        <xsl:with-param name="print.ranges" select="$print.ranges"/>
        <xsl:with-param name="single.year.ranges"
                        select="$single.year.ranges"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <!-- we have years left, but they aren't in the current range -->
      <xsl:choose>
        <xsl:when test="$nextyear = $firstyear + 1">
          <xsl:value-of select="$firstyear"/>
          <xsl:text>, </xsl:text>
        </xsl:when>
        <xsl:when test="$single.year.ranges = 0
                        and $nextyear = $firstyear + 2">
          <xsl:value-of select="$firstyear"/>
          <xsl:text>, </xsl:text>
          <xsl:value-of select="$nextyear - 1"/>
          <xsl:text>, </xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$firstyear"/>
          <xsl:text>-</xsl:text>
          <xsl:value-of select="$nextyear - 1"/>
          <xsl:text>, </xsl:text>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:call-template name="copyright.years">
        <xsl:with-param name="years"
                        select="$years[position() &gt; 1]"/>
        <xsl:with-param name="firstyear" select="$years[1]"/>
        <xsl:with-param name="nextyear" select="$years[1] + 1"/>
        <xsl:with-param name="print.ranges" select="$print.ranges"/>
        <xsl:with-param name="single.year.ranges"
                        select="$single.year.ranges"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ====================================================================== -->

<doc:template name="find.path.params" xmlns="">
<refpurpose>Search in a table for the "best" match for the node</refpurpose>

<refdescription id="find.path.params-desc">
<para>This template searches in a table for the value that most-closely
(in the typical best-match sense of XSLT) matches the current (element)
node location.</para>
</refdescription>
</doc:template>

<xsl:template name="find.path.params">
  <xsl:param name="node" select="."/>
  <xsl:param name="table" select="''"/>
  <xsl:param name="location">
    <xsl:call-template name="xpath.location">
      <xsl:with-param name="node" select="$node"/>
    </xsl:call-template>
  </xsl:param>

  <xsl:variable name="value">
    <xsl:call-template name="lookup.key">
      <xsl:with-param name="key" select="$location"/>
      <xsl:with-param name="table" select="$table"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$value != ''">
      <xsl:value-of select="$value"/>
    </xsl:when>
    <xsl:when test="contains($location, '/')">
      <xsl:call-template name="find.path.params">
        <xsl:with-param name="node" select="$node"/>
        <xsl:with-param name="table" select="$table"/>
        <xsl:with-param name="location" select="substring-after($location, '/')"/>
      </xsl:call-template>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template name="relative-uri">
  <xsl:param name="filename" select="."/>
  <xsl:param name="destdir" select="''"/>
  
  <xsl:variable name="srcurl">
    <xsl:call-template name="strippath">
      <xsl:with-param name="filename">
        <xsl:call-template name="xml.base.dirs">
          <xsl:with-param name="base.elem" 
                          select="$filename/ancestor-or-self::*
                                   [@xml:base != ''][1]"/>
        </xsl:call-template>
        <xsl:value-of select="$filename"/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="srcurl.trimmed">
    <xsl:call-template name="trim.common.uri.paths">
      <xsl:with-param name="uriA" select="$srcurl"/>
      <xsl:with-param name="uriB" select="$destdir"/>
      <xsl:with-param name="return" select="'A'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="destdir.trimmed">
    <xsl:call-template name="trim.common.uri.paths">
      <xsl:with-param name="uriA" select="$srcurl"/>
      <xsl:with-param name="uriB" select="$destdir"/>
      <xsl:with-param name="return" select="'B'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="depth">
    <xsl:call-template name="count.uri.path.depth">
      <xsl:with-param name="filename" select="$destdir.trimmed"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:call-template name="copy-string">
    <xsl:with-param name="string" select="'../'"/>
    <xsl:with-param name="count" select="$depth"/>
  </xsl:call-template>
  <xsl:value-of select="$srcurl.trimmed"/>

</xsl:template>

<!-- ===================================== -->

<xsl:template name="xml.base.dirs">
  <xsl:param name="base.elem" select="NONODE"/>

  <!-- Recursively resolve xml:base attributes, up to a 
       full path with : in uri -->
  <xsl:if test="$base.elem/ancestor::*[@xml:base != ''] and
                not(contains($base.elem/@xml:base, ':'))">
    <xsl:call-template name="xml.base.dirs">
      <xsl:with-param name="base.elem" 
                      select="$base.elem/ancestor::*[@xml:base != ''][1]"/>
    </xsl:call-template>
  </xsl:if>
  <xsl:call-template name="getdir">
    <xsl:with-param name="filename" select="$base.elem/@xml:base"/>
  </xsl:call-template>

</xsl:template>

<!-- ===================================== -->

<xsl:template name="strippath">
  <xsl:param name="filename" select="''"/>
  <xsl:choose>
    <!-- Leading .. are not eliminated -->
    <xsl:when test="starts-with($filename, '../')">
      <xsl:value-of select="'../'"/>
      <xsl:call-template name="strippath">
        <xsl:with-param name="filename" select="substring-after($filename, '../')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="contains($filename, '/../')">
      <xsl:call-template name="strippath">
        <xsl:with-param name="filename">
          <xsl:call-template name="getdir">
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

<!-- ===================================== -->

<xsl:template name="getdir">
  <xsl:param name="filename" select="''"/>
  <xsl:if test="contains($filename, '/')">
    <xsl:value-of select="substring-before($filename, '/')"/>
    <xsl:text>/</xsl:text>
    <xsl:call-template name="getdir">
      <xsl:with-param name="filename" select="substring-after($filename, '/')"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<!-- ===================================== -->

<doc:template name="string.upper" xmlns="">
<refpurpose>Converts a string to all uppercase letters</refpurpose>

<refdescription id="string.upper-desc">
<para>Given a string, this template does a language-aware conversion
of that string to all uppercase letters, based on the values of the
<literal>lowercase.alpha</literal> and
<literal>uppercase.alpha</literal> gentext keys for the current
locale. It affects only those characters found in the values of
<literal>lowercase.alpha</literal> and
<literal>uppercase.alpha</literal>. All other characters are left
unchanged.</para>
</refdescription>

<refparameter id="string.upper-params">
<variablelist>
<varlistentry><term>string</term>
<listitem>
<para>The string to convert to uppercase.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>
</doc:template>
<xsl:template name="string.upper">
  <xsl:param name="string" select="''"/>
  <xsl:variable name="lowercase.alpha">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'lowercase.alpha'"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="uppercase.alpha">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'uppercase.alpha'"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:value-of select="translate($string,$lowercase.alpha,$uppercase.alpha)"/>
</xsl:template>

<!-- ===================================== -->

<doc:template name="string.lower" xmlns="">
<refpurpose>Converts a string to all lowercase letters</refpurpose>

<refdescription id="string.lower-desc">
<para>Given a string, this template does a language-aware conversion
of that string to all lowercase letters, based on the values of the
<literal>uppercase.alpha</literal> and
<literal>lowercase.alpha</literal> gentext keys for the current
locale. It affects only those characters found in the values of
<literal>uppercase.alpha</literal> and
<literal>lowercase.alpha</literal>. All other characters are left
unchanged.</para>
</refdescription>

<refparameter id="string.lower-params">
<variablelist>
<varlistentry><term>string</term>
<listitem>
<para>The string to convert to lowercase.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>
</doc:template>
<xsl:template name="string.lower">
  <xsl:param name="string" select="''"/>
  <xsl:variable name="uppercase.alpha">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'uppercase.alpha'"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="lowercase.alpha">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'lowercase.alpha'"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:value-of select="translate($string,$uppercase.alpha,$lowercase.alpha)"/>
</xsl:template>

<!-- ===================================== -->

<doc:template name="select.choice.separator" xmlns="">
  <refpurpose>Returns localized choice separator</refpurpose>
  <refdescription id="select.choice.separator-desc">
    <para>This template enables auto-generation of an appropriate
    localized "choice" separator (for example, "and" or "or") before
    the final item in an inline list (though it could also be useful
    for generating choice separators for non-inline lists).</para>
    <para>It currently works by evaluating a processing instruction
    (PI) of the form &lt;?dbchoice&#xa0;choice="foo"?> :
    <itemizedlist>
      <listitem>
        <simpara>if the value of the <tag>choice</tag>
        pseudo-attribute is "and" or "or", returns a localized "and"
        or "or"</simpara>
      </listitem>
      <listitem>
        <simpara>otherwise returns the literal value of the
        <tag>choice</tag> pseudo-attribute</simpara>
      </listitem>
    </itemizedlist>
    The latter is provided only as a temporary workaround because the
    locale files do not currently have translations for the word
    <wordasword>or</wordasword>. So if you want to generate a a
    logical "or" separator in French (for example), you currently need
    to do this:
    <literallayout>&lt;?dbchoice choice="ou"?></literallayout>
    </para>
    <warning>
      <para>The <tag>dbchoice</tag> processing instruction is
      an unfortunate hack; support for it may disappear in the future
      (particularly if and when a more appropriate means for marking
      up "choice" lists becomes available in DocBook).</para>
    </warning>
  </refdescription>
</doc:template>
<xsl:template name="select.choice.separator">
  <xsl:variable name="choice">
    <xsl:call-template name="pi.dbchoice_choice"/>
  </xsl:variable>
  <xsl:choose>
    <!-- if value of $choice is "and" or "or", translate to equivalent in -->
    <!-- current locale -->
    <xsl:when test="$choice = 'and' or $choice = 'or'">
      <xsl:call-template name="gentext">
        <xsl:with-param name="key" select="$choice"/>
      </xsl:call-template>
    </xsl:when>
    <!--  otherwise, just output value of $choice, whatever it is -->
    <xsl:otherwise>
      <xsl:value-of select="$choice"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ===================================== -->

<doc:template name="evaluate.info.profile" xmlns="">
  <refpurpose>Evaluates an info profile</refpurpose>
  <refdescription id="evaluate.info.profile-desc">
    <para>This template evaluates an "info profile" matching the XPath
    expression given by the <parameter>profile</parameter>
    parameter. It relies on the XSLT <function>evaluate()</function>
    extension function.</para>

    <para>The value of the <parameter>profile</parameter> parameter
    can include the literal string <literal>$info</literal>. If found
    in the value of the <parameter>profile</parameter> parameter, the
    literal string <literal>$info</literal> string is replaced with
    the value of the <parameter>info</parameter> parameter, which
    should be a set of <replaceable>*info</replaceable> nodes; the
    expression is then evaluated using the XSLT
    <function>evaluate()</function> extension function.</para>
  </refdescription>
  <refparameter id="evaluate.info.profile-params">
    <variablelist>
       <varlistentry>
        <term>profile</term>
        <listitem>
          <para>A string representing an XPath expression </para>
        </listitem>
      </varlistentry>
       <varlistentry>
        <term>info</term>
        <listitem>
          <para>A set of *info nodes</para>
        </listitem>
      </varlistentry>
    </variablelist>
  </refparameter>

  <refreturn id="evaluate.info.profile-returns">
    <para>Returns a node (the result of evaluating the
    <parameter>profile</parameter> parameter)</para>
  </refreturn>
</doc:template>
  <xsl:template name="evaluate.info.profile">
    <xsl:param name="profile"/>
    <xsl:param name="info"/>
    <xsl:choose>
      <!-- * xsltproc and Xalan both support dyn:evaluate() -->
      <xsl:when test="function-available('dyn:evaluate')">
        <xsl:apply-templates
            select="dyn:evaluate($profile)" mode="get.refentry.metadata"/>
      </xsl:when>
      <!-- * Saxon has its own evaluate() & doesn't support dyn:evaluate() -->
      <xsl:when test="function-available('saxon:evaluate')">
        <xsl:apply-templates
            select="saxon:evaluate($profile)" mode="get.refentry.metadata"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:message terminate="yes">
Error: The "info profiling" mechanism currently requires an XSLT
engine that supports the evaluate() XSLT extension function. Your XSLT
engine does not support it.
</xsl:message>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>


<doc:template name="graphic.format.content-type" xmlns="">
  <refpurpose>Returns mimetype for media format</refpurpose>
  <refdescription id="graphic.format.content-type-desc">
    <para>This takes as input a 'format' param and returns
    a mimetype string.  It uses an xsl:choose after first
    converting the input to all uppercase.</para>
  </refdescription>
</doc:template>
<xsl:template name="graphic.format.content-type">
  <xsl:param name="format"/>
  <xsl:variable name="upperformat" select="translate($format,&lowercase;,&uppercase;)"/>
  <xsl:choose>
    <xsl:when test="$upperformat = ''"></xsl:when>
    <xsl:when test="$upperformat = 'linespecific'"></xsl:when>
    <xsl:when test="$upperformat = 'PS'">application/postscript</xsl:when>
    <xsl:when test="$upperformat = 'PDF'">application/pdf</xsl:when>
    <xsl:when test="$upperformat = 'PNG'">image/png</xsl:when>
    <xsl:when test="$upperformat = 'SVG'">image/svg+xml</xsl:when>
    <xsl:when test="$upperformat = 'JPG'">image/jpeg</xsl:when>
    <xsl:when test="$upperformat = 'JPEG'">image/jpeg</xsl:when>
    <xsl:when test="$upperformat = 'GIF'">image/gif</xsl:when>
    <xsl:when test="$upperformat = 'GIF87A'">image/gif</xsl:when>
    <xsl:when test="$upperformat = 'GIF89A'">image/gif</xsl:when>
    <xsl:when test="$upperformat = 'ACC'">audio/acc</xsl:when>
    <xsl:when test="$upperformat = 'MPG'">audio/mpeg</xsl:when>
    <xsl:when test="$upperformat = 'MP1'">audio/mpeg</xsl:when>
    <xsl:when test="$upperformat = 'MP2'">audio/mpeg</xsl:when>
    <xsl:when test="$upperformat = 'MP3'">audio/mpeg</xsl:when>
    <xsl:when test="$upperformat = 'M4A'">audio/mp4</xsl:when>
    <xsl:when test="$upperformat = 'MPEG'">audio/mpeg</xsl:when>
    <xsl:when test="$upperformat = 'WAV'">audio/wav</xsl:when>
    <xsl:when test="$upperformat = 'MP4'">video/mp4</xsl:when>
    <xsl:when test="$upperformat = 'M4V'">video/mp4</xsl:when>
    <xsl:when test="$upperformat = 'OGV'">video/ogg</xsl:when>
    <xsl:when test="$upperformat = 'OGG'">video/ogg</xsl:when>
    <xsl:when test="$upperformat = 'WEBM'">video/webm</xsl:when>
    <xsl:otherwise>
        <xsl:value-of select="concat('image/', $upperformat)"/> 
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
