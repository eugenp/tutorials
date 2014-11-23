<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                exclude-result-prefixes="exsl"
                version='1.0'>

<!-- ********************************************************************
     $Id: block.xsl 8703 2010-07-06 20:57:06Z nwalsh $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="caution|important|note|tip|warning">
  <xsl:call-template name="roff-if-start">
    <xsl:with-param name="condition">n</xsl:with-param>
  </xsl:call-template>
  <xsl:text>.sp&#10;</xsl:text>
  <xsl:call-template name="roff-if-end"/>
  <xsl:text>.RS 4&#10;</xsl:text>
  <xsl:if test="not($man.output.better.ps.enabled = 0)">
    <xsl:text>.BM yellow&#10;</xsl:text>
  </xsl:if>
  <xsl:call-template name="pinch.together"/>
  <xsl:text>.ps +1&#10;</xsl:text>
  <xsl:call-template name="make.bold.title"/>
  <xsl:text>.ps -1&#10;</xsl:text>
  <xsl:text>.br&#10;</xsl:text>
  <xsl:apply-templates/>
  <xsl:text>.sp .5v&#10;</xsl:text>
  <xsl:if test="not($man.output.better.ps.enabled = 0)">
    <xsl:text>.EM yellow&#10;</xsl:text>
  </xsl:if>
  <xsl:text>.RE&#10;</xsl:text>
</xsl:template> 

<xsl:template match="formalpara">
  <xsl:variable name="title.wrapper">
    <xsl:value-of select="normalize-space(title[1])"/>
  </xsl:variable>
  <xsl:text>.PP&#10;</xsl:text>
  <!-- * don't put linebreak after head; instead render it as a "run in" -->
  <!-- * head, that is, inline, with a period and space following it -->
  <xsl:call-template name="bold">
    <xsl:with-param name="node" select="exsl:node-set($title.wrapper)"/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>
  <xsl:text>. </xsl:text>
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="formalpara/para">
  <xsl:call-template name="mixed-block"/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="para">
  <!-- * FIXME: Need to extract the ancestor::footnote, etc. checking and -->
  <!-- * move to named template so that we can call it from templates for -->
  <!-- * other block elements also -->
  <xsl:choose>
    <!-- * If a para is a descendant of a footnote, etc., then indent it -->
    <!-- * (unless it is the first child, in which case don't generate -->
    <!-- * anything at all to mark its start). -->
    <!-- * FIXME: *blurb checking should not be munged in here the way -->
    <!-- * it currently is; this probably breaks blurb indenting. -->
    <xsl:when test="ancestor::footnote or
                    ancestor::annotation or
                    ancestor::authorblurb or
                    ancestor::personblurb or
                    ancestor::callout">
      <xsl:if test="preceding-sibling::*[not(name() ='')]">
        <xsl:text>.sp</xsl:text>
        <xsl:text>&#10;</xsl:text>
        <xsl:text>.RS 4n</xsl:text>
        <xsl:text>&#10;</xsl:text>
      </xsl:if>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>.PP</xsl:text>
      <xsl:text>&#10;</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:call-template name="mixed-block"/>
    <xsl:if test="ancestor::footnote or
                  ancestor::annotation or
                  ancestor::authorblurb or
                  ancestor::personblurb">
      <xsl:if test="preceding-sibling::*[not(name() ='')]">
        <xsl:text>&#10;</xsl:text>
        <xsl:text>.RE</xsl:text>
        <xsl:text>&#10;</xsl:text>
      </xsl:if>
    </xsl:if>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="simpara">
  <xsl:choose>
    <xsl:when test="ancestor::footnote or
                    ancestor::annotation or
                    ancestor::authorblurb or
                    ancestor::personblurb or
                    ancestor::callout">
      <xsl:if test="preceding-sibling::*[not(name() ='')]">
        <xsl:text>.sp</xsl:text>
        <xsl:text>&#10;</xsl:text>
        <xsl:text>.RS 4n</xsl:text>
        <xsl:text>&#10;</xsl:text>
      </xsl:if>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>.sp</xsl:text>
      <xsl:text>&#10;</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:variable name="content">
    <xsl:apply-templates/>
  </xsl:variable>
  <xsl:value-of select="normalize-space($content)"/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<!-- ==================================================================== -->

<!-- * Yes, address, synopsis, and funcsynopsisinfo are verbatim environments. -->
<xsl:template match="literallayout|programlisting|screen|
                     address|synopsis|funcsynopsisinfo">
  <xsl:param name="indent">
    <!-- * Only indent this verbatim if $man.indent.verbatims is -->
    <!-- * non-zero and it is not a child of a *synopsis element or a -->
    <!-- * descendant of a refsynopsisdiv -->
    <xsl:if test="not($man.indent.verbatims = 0)
      and not(substring(local-name(..),
      string-length(local-name(..))-7) = 'synopsis')
      and not(ancestor::*[local-name() = 'refsynopsisdiv'])
      ">
      <xsl:text>Yes</xsl:text>
    </xsl:if>
  </xsl:param>

  <!-- * if this verbatim environment starts with a newline/linebreak -->
  <!-- * (that is, if there is a linebreak after the opening tag), that -->
  <!-- * break would otherwise show up in output; that does not seem to -->
  <!-- * be what most users would expect, so we check to see if it does -->
  <!-- * indeed start with a leading newline. if so, later in this -->
  <!-- * template, we adjust for the leading new line by doing some -->
  <!-- * monkeyshines with "sp -1" vertical spacing -->
  <xsl:variable name="adjust-for-leading-newline">
    <xsl:if test="substring(., 1, 1) = '&#10;'">Yes</xsl:if>
  </xsl:variable>

  <xsl:choose>
    <!-- * Check to see if this verbatim item is within a parent element that -->
    <!-- * allows mixed content. -->
    <!-- * -->
    <!-- * If it is within a mixed-content parent, then a line space is -->
    <!-- * already added before it by the mixed-block template, so we don't -->
    <!-- * need to add one here. -->
    <!-- * -->
    <!-- * If it is not within a mixed-content parent, then we need to add a -->
    <!-- * line space before it. -->
    <xsl:when test="parent::caption|parent::entry|parent::para|
                    parent::td|parent::th" /> <!-- do nothing -->
    <xsl:otherwise>
      <xsl:text>&#10;</xsl:text>
      <xsl:text>.sp&#10;</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:if test="$indent = 'Yes'">
    <!-- * start indented section -->
    <xsl:call-template name="roff-if-start"/>
    <!-- * only indent in TTY output, not in non-TTY/PS -->
    <xsl:text>.RS</xsl:text> 
    <xsl:if test="not($man.indent.width = '')">
      <xsl:text> </xsl:text>
      <xsl:value-of select="$man.indent.width"/>
    </xsl:if>
    <xsl:text>&#10;</xsl:text>
    <xsl:call-template name="roff-if-end"/>
  </xsl:if>
  <xsl:choose>
    <xsl:when test="self::funcsynopsisinfo">
      <!-- * All Funcsynopsisinfo content is by default rendered in bold, -->
      <!-- * because the man(7) man page says this: -->
      <!-- * -->
      <!-- *   For functions, the arguments are always specified using -->
      <!-- *   italics, even in the SYNOPSIS section, where the rest of -->
      <!-- *   the function is specified in bold -->
      <!-- * -->
      <!-- * Look through the contents of the man/man2 and man3 directories -->
      <!-- * on your system, and you'll see that most existing pages do follow -->
      <!-- * this "bold everything in function synopsis" rule. -->
      <!-- * -->
      <!-- * Users who don't want the bold output can choose to adjust the -->
      <!-- * man.font.funcsynopsisinfo parameter on their own. So even if you -->
      <!-- * don't personally like the way it looks, please don't change the -->
      <!-- * default to be non-bold - because it's a convention that's -->
      <!-- * followed is the vast majority of existing man pages that document -->
      <!-- * functions, and we need to follow it by default, like it or no. -->
      <xsl:text>.ft </xsl:text>
      <xsl:value-of select="$man.font.funcsynopsisinfo"/>
      <xsl:text>&#10;</xsl:text>
      <xsl:call-template name="verbatim-block-start"/>
      <xsl:text>.nf&#10;</xsl:text>
      <xsl:apply-templates/>
      <xsl:text>&#10;</xsl:text>
      <xsl:text>.fi&#10;</xsl:text>
      <xsl:call-template name="verbatim-block-end"/>
      <xsl:text>.ft&#10;</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <!-- * Other verbatims do not need to get bolded -->
      <xsl:call-template name="verbatim-block-start"/>
      <xsl:text>.nf&#10;</xsl:text>
      <xsl:choose>
        <xsl:when test="self::literallayout|self::programlisting|self::screen
          and not(ancestor::*[local-name() = 'refsynopsisdiv'])
          and not($man.output.better.ps.enabled = 0)
          ">
          <!-- * if this is a literallayout|programlisting|screen, -->
          <!-- * and user has set man.output.better.ps.enabled to non-zero, -->
          <!-- * then we put a background behind it in non-TTY output; except -->
          <!-- * if it’s a descendant of a refsynopsisdiv (as can be -->
          <!-- * found in the git docs) -->
          <xsl:choose>
            <!-- * if content has a leading newline, we need to back up -->
            <!-- * one line vertically to get it boxed correctly -->
            <xsl:when test="not($adjust-for-leading-newline = '')">
              <xsl:call-template name="roff-if-start">
                <xsl:with-param name="condition">t</xsl:with-param>
              </xsl:call-template>
              <xsl:text>.sp -1&#10;</xsl:text>
              <xsl:call-template name="roff-if-end"/>
              <xsl:text>.BB lightgray</xsl:text>
              <xsl:text> </xsl:text>
              <xsl:text>adjust-for-leading-newline&#10;</xsl:text>
              <!-- * in non-TTY output, for the case where we have a -->
              <!-- * leading newline, we need to also back up one line -->
              <!-- * vertically inside the background box -->
              <xsl:text>.sp -1&#10;</xsl:text>
            </xsl:when>
            <xsl:otherwise>
              <xsl:text>.BB lightgray&#10;</xsl:text>
            </xsl:otherwise>
          </xsl:choose>
          <xsl:apply-templates/>
          <xsl:text>&#10;</xsl:text>
          <xsl:choose>
            <xsl:when test="not($adjust-for-leading-newline = '')">
              <xsl:text>.EB lightgray</xsl:text>
              <xsl:text> </xsl:text>
              <xsl:text>adjust-for-leading-newline&#10;</xsl:text>
              <xsl:call-template name="roff-if-start">
                <xsl:with-param name="condition">t</xsl:with-param>
              </xsl:call-template>
              <!-- * in non-TTY output, for the case where we have a -->
              <!-- * leading newline, we need to add back at the end of -->
              <!-- * the content some of the vertical space we chopped -->
              <!-- * off at the beginning -->
              <xsl:text>.sp 1&#10;</xsl:text>
              <xsl:call-template name="roff-if-end"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:text>.EB lightgray&#10;</xsl:text>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
          <!-- * otherwise this is not a literallayout|programlisting|screen, -->
          <!-- * so we don’t put a background behind -->
          <xsl:apply-templates/>
          <xsl:text>&#10;</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:text>.fi&#10;</xsl:text>
      <xsl:call-template name="verbatim-block-end"/>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:if test="$indent = 'Yes'">
    <!-- * end indented section -->
    <xsl:call-template name="roff-if-start"/>
    <xsl:text>.RE&#10;</xsl:text> 
    <xsl:call-template name="roff-if-end"/>
  </xsl:if>
  <!-- * if this verbatim environment has a following sibling node, -->
  <!-- * output a line of space to separate the content -->
  <xsl:if test="following-sibling::text()
    |following-sibling::para
    |following-sibling::simpara">
    <xsl:text>.sp&#10;</xsl:text>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="table|informaltable">
  <xsl:apply-templates select="." mode="to.tbl">
    <!--* we call the to.tbl mode with the "source" param so that we can -->
    <!--* preserve the context information and pass it down to the -->
    <!--* named templates that do the actual table processing -->
    <xsl:with-param name="source" select="ancestor::refentry/refnamediv[1]/refname[1]"/>
  </xsl:apply-templates>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="informalexample">
  <xsl:apply-templates/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="figure|example">
  <xsl:variable name="param.placement"
                select="substring-after(normalize-space($formal.title.placement),
                        concat(local-name(.), ' '))"/>

  <xsl:variable name="placement">
    <xsl:choose>
      <xsl:when test="contains($param.placement, ' ')">
        <xsl:value-of select="substring-before($param.placement, ' ')"/>
      </xsl:when>
      <xsl:when test="$param.placement = ''">before</xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$param.placement"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:text>.PP&#10;</xsl:text>
  <xsl:call-template name="formal.object">
    <xsl:with-param name="placement" select="$placement"/>
  </xsl:call-template>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="mediaobject">
  <xsl:text>.sp</xsl:text>
  <xsl:text>&#10;</xsl:text>
  <xsl:text>.RS</xsl:text> 
  <xsl:if test="not($list-indent = '')">
    <xsl:text> </xsl:text>
    <xsl:value-of select="$list-indent"/>
  </xsl:if>
  <xsl:text>&#10;</xsl:text>
  <xsl:apply-templates/>
  <xsl:text>&#10;</xsl:text>
  <xsl:text>.RE&#10;</xsl:text>
</xsl:template>

<xsl:template match="imageobject">
  <xsl:text>[IMAGE]</xsl:text>
  <xsl:apply-templates/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="textobject[parent::inlinemediaobject]">
  <xsl:text>[</xsl:text>
  <xsl:value-of select="."/>
  <xsl:text>]</xsl:text>
</xsl:template>

<xsl:template match="textobject">
  <xsl:apply-templates/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="formal.object">
  <xsl:param name="placement" select="'before'"/>
  <xsl:param name="class" select="local-name(.)"/>

  <xsl:choose>
    <xsl:when test="$placement = 'before'">
      <xsl:call-template name="formal.object.heading"/>
      <xsl:apply-templates/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates/>
      <xsl:call-template name="formal.object.heading"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="formal.object.heading">
  <xsl:param name="object" select="."/>
  <xsl:param name="title">
    <xsl:apply-templates select="$object" mode="object.title.markup.textonly"/>
  </xsl:param>
  <xsl:call-template name="bold">
    <xsl:with-param name="node" select="exsl:node-set($title)"/>
    <xsl:with-param name="context" select="."/>
  </xsl:call-template>

  <xsl:text>&#10;</xsl:text>
</xsl:template>

<!-- ==================================================================== -->

<!-- * suppress abstract -->
<xsl:template match="abstract"/>

</xsl:stylesheet>
