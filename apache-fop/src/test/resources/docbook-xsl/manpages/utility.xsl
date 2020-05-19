<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                xmlns:dyn="http://exslt.org/dynamic"
                xmlns:saxon="http://icl.com/saxon"
                exclude-result-prefixes="exsl dyn saxon"
                version='1.0'>

<!-- ********************************************************************
     $Id: utility.xsl 9703 2013-01-07 20:13:05Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<!-- * This file contains "utility" templates that are called multiple -->
<!-- * times per each Refentry. -->

<!-- ==================================================================== -->

  <!-- * NOTE TO DEVELOPERS: For ease of maintenance, the current -->
  <!-- * manpages stylesheets use the "bold" and "italic" named -->
  <!-- * templates for anything and everything that needs to get -->
  <!-- * boldfaced or italicized. -->
  <!-- * -->
  <!-- * So if you add anything that needs bold or italic character -->
  <!-- * formatting, try to apply these templates to it rather than -->
  <!-- * writing separate code to format it. This can be a little odd if -->
  <!-- * the content you want to format is not element content; in those -->
  <!-- * cases, you need to turn it into element content before applying -->
  <!-- * the template; see examples of this in the existing code. -->

  <xsl:template name="bold">
    <xsl:param name="node"/>
    <xsl:param name="context"/>
    <xsl:choose>
      <xsl:when test="not($context[ancestor::title])">
        <xsl:for-each select="$node/node()">
          <xsl:text>\fB</xsl:text>
          <xsl:apply-templates select="."/>
          <xsl:text>\fR</xsl:text>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="$node/node()"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name="italic">
    <xsl:param name="node"/>
    <xsl:param name="context"/>
    <xsl:for-each select="$node/node()">
      <xsl:text>\fI</xsl:text>
      <xsl:apply-templates select="."/>
      <xsl:text>\fR</xsl:text>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="inline.monoseq">
    <xsl:param name="content">
      <xsl:apply-templates/>
    </xsl:param>
    <xsl:call-template name="code-inline-start"/>
    <xsl:copy-of select="$content"/>
    <xsl:call-template name="code-inline-end"/>
  </xsl:template>

  <xsl:template name="code-inline-start">
    <xsl:if test="not($man.output.better.ps.enabled = 0)">
      <xsl:text>\FC</xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template name="code-inline-end">
    <xsl:if test="not($man.output.better.ps.enabled = 0)">
      <xsl:text>\F[]</xsl:text>
    </xsl:if>
  </xsl:template>

  <!-- ================================================================== -->

  <xsl:template name="verbatim-block-start">
    <xsl:if test="not($man.output.better.ps.enabled = 0)">
      <xsl:text>.fam C&#10;</xsl:text>
      <xsl:text>.ps -1&#10;</xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template name="verbatim-block-end">
    <xsl:if test="not($man.output.better.ps.enabled = 0)">
      <xsl:text>.fam&#10;</xsl:text>
      <xsl:text>.ps +1&#10;</xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template name="synopsis-block-start">
    <xsl:if test="not($man.output.better.ps.enabled = 0)">
      <xsl:text>.fam C&#10;</xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template name="synopsis-block-end">
    <xsl:if test="not($man.output.better.ps.enabled = 0)">
      <xsl:text>.fam&#10;</xsl:text>
    </xsl:if>
  </xsl:template>

  <!-- ================================================================== -->

  <!-- * NOTE TO DEVELOPERS: For ease of maintenance, the current -->
  <!-- * manpages stylesheets use the mode="prevent.line.breaking" -->
  <!-- * templates for anything and everything that needs to have -->
  <!-- * embedded spaces turned into no-break spaces in output - in -->
  <!-- * order to prevent that output from getting broken across lines -->
  <!-- * -->
  <!-- * So if you add anything that whose output, try to apply this -->
  <!-- * template to it rather than writing separate code to format -->
  <!-- * it. This can be a little odd if the content you want to -->
  <!-- * format is not element content; in those cases, you need to -->
  <!-- * turn it into element content before applying the template; -->
  <!-- * see examples of this in the existing code. -->
  <!-- * -->
  <!-- * This template is currently called by the funcdef and paramdef -->
  <!-- * and group/arg templates. -->
  <xsl:template mode="prevent.line.breaking" match="*">
    <xsl:variable name="rcontent">
      <xsl:apply-templates/>
    </xsl:variable>
    <xsl:variable name="content">
      <xsl:value-of select="normalize-space($rcontent)"/>
    </xsl:variable>
    <xsl:call-template name="string.subst">
      <xsl:with-param name="string" select="$content"/>
      <xsl:with-param name="target" select="' '"/>
      <!-- * U+2580 is a "UPPER HALF BLOCK"; we use it here because -->
      <!-- * if we were to just use a normal space, it would get -->
      <!-- * replaced when normalization is done. We replace it -->
      <!-- * later with the groff markup for non-breaking space. -->
      <xsl:with-param name="replacement" select="'&#x2580;'"/>
    </xsl:call-template>
  </xsl:template>

  <!-- ================================================================== -->

  <xsl:template name="suppress.hyphenation">
    <!-- * we need to suppress hyphenation inline only if hyphenation is -->
    <!-- * actually on, and even then only outside of Cmdsynopsis and -->
    <!-- * Funcsynopsis, where it is already always turned off -->
    <xsl:if test="$man.hyphenate != 0 and
                  not(ancestor::cmdsynopsis) and
                  not(ancestor::funcsynopsis)">
      <xsl:text>\%</xsl:text>
    </xsl:if>
  </xsl:template>

  <!-- ================================================================== -->

  <!-- * The replace.dots.and.dashes template is used to cause real -->
  <!-- * dots and dashes to be output in the top comment (instead of -->
  <!-- * escaped ones as in the source for the text displayed in the -->
  <!-- * body of the page) -->
  <xsl:template name="replace.dots.and.dashes">
    <xsl:param name="content">
      <xsl:apply-templates/>
    </xsl:param>
    <xsl:variable name="dot-content">
      <xsl:call-template name="string.subst">
        <xsl:with-param name="string" select="$content"/>
        <xsl:with-param name="target" select="'\&amp;.'"/>
        <xsl:with-param name="replacement" select="'.'"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:call-template name="string.subst">
      <xsl:with-param name="string" select="$dot-content"/>
      <xsl:with-param name="target" select="'\-'"/>
      <xsl:with-param name="replacement" select="'-'"/>
    </xsl:call-template>
  </xsl:template>

  <!-- ================================================================== -->

  <!-- * The nested-section-title template is called for refsect3, and any -->
  <!-- * refsection nested more than 2 levels deep. -->
  <xsl:template name="nested-section-title">
    <xsl:text>.sp&#10;</xsl:text>
    <xsl:call-template name="pinch.together"/>
    <xsl:text>.ps +1&#10;</xsl:text>
    <xsl:call-template name="make.bold.title"/>
  </xsl:template>

  <xsl:template name="pinch.together">
    <!-- * arcane roff code to suppress line spacing after headings -->
    <xsl:text>.it 1 an-trap&#10;</xsl:text>
    <xsl:text>.nr an-no-space-flag 1&#10;</xsl:text>
    <xsl:text>.nr an-break-flag 1&#10;</xsl:text>
    <xsl:text>.br&#10;</xsl:text>
  </xsl:template>

  <xsl:template name="make.bold.title">
    <!-- * make title wrapper so that we can use "bold" template to apply -->
    <!-- * character formatting to it -->
    <xsl:variable name="title.wrapper">
      <xsl:choose>
        <xsl:when test="title">
          <xsl:value-of select="normalize-space(title[1])"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="." mode="object.title.markup.textonly"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:call-template name="mark.subheading"/>
    <xsl:call-template name="bold">
      <xsl:with-param name="node" select="exsl:node-set($title.wrapper)"/>
      <xsl:with-param name="context" select="."/>
    </xsl:call-template>
    <xsl:text>&#10;</xsl:text>
    <xsl:call-template name="mark.subheading"/>
  </xsl:template>

  <!-- ================================================================== -->

  <!-- * The mixed-block template jumps through a few hoops to deal with -->
  <!-- * mixed-content blocks, so that we don't end up munging verbatim -->
  <!-- * environments or lists and so that we don't gobble up whitespace -->
  <!-- * when we shouldn't -->
  <xsl:template name="mixed-block">
    <xsl:for-each select="node()">
      <xsl:choose>
        <!-- * Check to see if this node is a verbatim environment. -->
        <!-- * If so, put a line of space before it. -->
        <!-- * -->
        <!-- * Yes, address and synopsis are vertabim environments. -->
        <!-- * -->
        <!-- * The code here previously also treated informaltable as a -->
        <!-- * verbatim, presumably to support some kludge; I removed it -->
        <xsl:when test="self::address|self::literallayout|self::programlisting|
                        self::screen|self::synopsis">
          <xsl:text>&#10;</xsl:text>
          <xsl:text>.sp&#10;</xsl:text>
          <xsl:call-template name="mark.up.block.start"/>
          <xsl:apply-templates select="."/>
        </xsl:when>
        <!-- * Check to see if this node is a list; if it is, we don't -->
        <!-- * want to normalize-space(), so we just apply-templates. -->
        <!-- * Do same for all admonitions -->
        <xsl:when test="(self::itemizedlist|self::orderedlist|
                        self::variablelist|self::glosslist|
                        self::simplelist[@type !='inline']|
                        self::segmentedlist|
                        self::caution|self::important|
                        self::note|self::tip|self::warning|
                        self::table|self::informaltable)">
          <xsl:call-template name="mark.up.block.start"/>
          <xsl:apply-templates select="."/>
        </xsl:when>
        <xsl:when test="self::text()">
          <!-- * Check to see if this is a text node. -->
          <!-- * -->
          <!-- * If so, replace all whitespace at the beginning or end of it -->
          <!-- * with a single linebreak. -->
          <!-- * -->
          <xsl:variable name="content">
            <xsl:apply-templates select="."/>
          </xsl:variable>
          <xsl:if
              test="starts-with(translate(.,'&#9;&#10;&#13; ','    '), ' ')
                    and preceding-sibling::node()[1][name(.)!='']
                    and normalize-space($content) != ''
                    and not(
                    preceding-sibling::*[1][
                    self::caution or
                    self::important or
                    self::note or
                    self::tip or
                    self::warning or
                    self::variablelist or
                    self::glosslist or
                    self::itemizedlist or
                    self::orderedlist or
                    self::segmentedlist or
                    self::procedure or
                    self::address or
                    self::literallayout or
                    self::programlisting or
                    self::synopsis or
                    self::screen or
                    self::table or
                    self::informaltable
                    ]
                    )
                    ">
            <xsl:text>&#10;</xsl:text>
          </xsl:if>
          <xsl:value-of select="normalize-space($content)"/>
          <xsl:if
              test="(translate(substring(., string-length(.), 1),'&#9;&#10;&#13; ','    ')  = ' '
                    and following-sibling::node()[1][name(.)!=''])
                    or following-sibling::node()[1][self::comment()]
                    or following-sibling::node()[1][self::processing-instruction()]
                    ">
            <xsl:if test="normalize-space($content) != ''
                          or concat(normalize-space($content), ' ') != ' '">
              <xsl:text>&#10;</xsl:text>
            </xsl:if>
          </xsl:if>
        </xsl:when>
        <xsl:otherwise>
          <!-- * At this point, we know that this node is not a verbatim -->
          <!-- * environment, list, admonition, or text node; so we can -->
          <!-- * safely normalize-space() it. -->
          <xsl:variable name="content">
            <xsl:apply-templates select="."/>
          </xsl:variable>
          <xsl:value-of select="normalize-space($content)"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
    <xsl:call-template name="mark.up.block.end"/>
  </xsl:template>

  <!-- ================================================================== -->

  <!-- * Footnote and annotation contents are displayed using a hanging -->
  <!-- * indent out to $man.indent.width If a paragraph-level block -->
  <!-- * element (verbatim, list, or admonition) is the first block -->
  <!-- * element nested at its same level within the same footnote or -->
  <!-- * annotation, then we push it over by the same indent width. -->
  <!-- * -->
  <!-- * We don't reset the indent for each following sibling, but -->
  <!-- * instead do it after for-eaching over all block siblings at -->
  <!-- * the same level. So the effect is that if there are any -->
  <!-- * following-sibling blocks after the block that starts this -->
  <!-- * indent, then they just retain the indent that was already set -->

  <xsl:template name="mark.up.block.start">
    <xsl:choose>
      <xsl:when test="(ancestor::footnote
                      or ancestor::annotation)">
        <xsl:if test="not(preceding-sibling::address|
                      preceding-sibling::literallayout|
                      preceding-sibling::programlisting|
                      preceding-sibling::screen|
                      preceding-sibling::synopsis|
                      preceding-sibling::itemizedlist|
                      preceding-sibling::orderedlist|
                      preceding-sibling::variablelist|
                      preceding-sibling::glosslist|
                      preceding-sibling::simplelist[@type !='inline']|
                      preceding-sibling::segmentedlist|
                      preceding-sibling::caution|
                      preceding-sibling::important|
                      preceding-sibling::note|
                      preceding-sibling::tip|
                      preceding-sibling::warning|
                      preceding-sibling::table|
                      preceding-sibling::informaltable
                      )">
          <xsl:text>.RS</xsl:text>
          <xsl:if test="not($list-indent = '')">
            <xsl:text> </xsl:text>
            <xsl:value-of select="$list-indent"/>
          </xsl:if>
          <xsl:text>&#10;</xsl:text>
        </xsl:if>
      </xsl:when>
    </xsl:choose>
  </xsl:template>

  <!-- * Check to see if we were called from a block within a footnote or -->
  <!-- * annotation; if so, and the block contains any nested block -->
  <!-- * content, then we know the mark.up.block.end template was already -->
  <!-- * called to generate a .RS macro to indent that nested block -->
  <!-- * content; so we need to generate a .RE to set the margin back to -->
  <!-- * where it was prior to the .RS call. -->
  <xsl:template name="mark.up.block.end">
    <xsl:if test="(ancestor::footnote
                  or ancestor::annotation)">
      <xsl:if test="address|
                    literallayout|
                    programlisting|
                    screen|
                    synopsis|
                    itemizedlist|
                    orderedlist|
                    variablelist|
                    glosslist|
                    simplelist[@type !='inline']|
                    segmentedlist|
                    caution|
                    important|
                    note|
                    tip|
                    warning|
                    table|
                    informaltable">
        <xsl:text>&#10;</xsl:text>
        <xsl:text>.RE</xsl:text>
      <xsl:text>&#10;</xsl:text>
      </xsl:if>
    </xsl:if>
  </xsl:template>

  <!-- ================================================================== -->

  <!-- * The person.name template in the HTML stylesheets outputs extra -->
  <!-- * spaces that we need to strip out for manpages output. This -->
  <!-- * template calls person.name, then tries to do some smart -->
  <!-- * normalization of the result tree fragment from that. -->
  <xsl:template name="person.name.normalized">
    <xsl:variable name="contents">
      <xsl:call-template name="person.name"/>
    </xsl:variable>
    <!-- * We put the output of person.name into a node-set and then we -->
    <!-- * check it node-by-node and strip out space only where needed. -->
    <xsl:variable name="contents.tree" select="exsl:node-set($contents)"/>
    <xsl:for-each select="$contents.tree/node()">
      <xsl:choose>
        <!-- * We don't want to monkey with single spaces or commas/periods -->
        <!-- * followed by spaces, because those are bits of text that are -->
        <!-- * actually generated by the person.name template itself (that -->
        <!-- * is, they're not in the source). So, we preserve them. -->
        <xsl:when test=". = ' ' or . = ', ' or . = '. '">
          <xsl:value-of select="."/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="normalize-space(.)"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
  </xsl:template>

  <!-- ================================================================== -->

  <xsl:template name="make.adjusted.man.filename">
    <xsl:param name="name"/>
    <xsl:param name="lang"/>
    <xsl:param name="name.with.lang">
      <xsl:choose>
        <xsl:when test="$lang != 'en'
          and not($man.output.lang.in.name.enabled = 0)
          and ($man.output.subdirs.enabled = 0 or
          $man.output.in.separate.dir = 0)">
          <!-- * $lang is not en (English) -->
          <!-- * AND user has specified man.output.lang.in.name.enabled -->
          <!-- * AND doesn't want output going into separate dirs, -->
          <!-- * SO... we include the $lang value in the filename; e.g., -->
          <!-- * foo.ja.1 -->
          <xsl:value-of select="concat($name, '.', $lang)"/>
        </xsl:when>
        <xsl:otherwise>
          <!-- * user either has man.output.lang.in.name.enabled unset -->
          <!-- * or has set it but also has man.output.subdirs.enabled -->
          <!-- * set (in which case the $lang value is used to add a -->
          <!-- * $lang subdir in the pathname); in either case, we don't -->
          <!-- * want to include the $lang in the filename -->
          <xsl:value-of select="$name"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:param>
    <xsl:param name="section"/>
    <xsl:param name="dirname">
      <xsl:if test="not($man.output.in.separate.dir = 0)">
        <xsl:choose>
          <xsl:when test="not($man.output.subdirs.enabled = 0)">
            <xsl:variable name="lang.subdir">
              <xsl:if test="not($man.output.lang.in.name.enabled = 0)">
                <!-- * user has man.output.lang.in.name.enabled set, so -->
                <!-- * we need to add a $lang subdir -->
                <xsl:value-of select="concat($lang, '/')"/>
              </xsl:if>
            </xsl:variable>
            <xsl:value-of
                select="concat($man.output.base.dir, $lang.subdir,
                        'man', normalize-space($section), '/')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$man.output.base.dir"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </xsl:param>
    <xsl:call-template name="string.subst">
      <!-- * To create the man filename, replace any spaces in filename with -->
      <!-- * underscores and then append a dot plus a section number. -->
      <xsl:with-param name="string"
                      select="concat($dirname,
                              normalize-space($name.with.lang),
                              '.', normalize-space($section))"/>
      <xsl:with-param name="target" select="' '"/>
      <xsl:with-param name="replacement" select="'_'"/>
    </xsl:call-template>
  </xsl:template>

  <!-- ================================================================== -->

  <xsl:template name="make.subheading">
    <xsl:param name="title"/>
    <xsl:call-template name="mark.subheading"/>
    <xsl:text>.SH</xsl:text>
    <xsl:text> </xsl:text>
    <xsl:text>"</xsl:text>
    <xsl:choose>
      <xsl:when test="not($man.output.better.ps.enabled = 0)">
        <xsl:value-of select="$title"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="string.upper">
          <xsl:with-param name="string" select="$title"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text>"</xsl:text>
    <xsl:text>&#10;</xsl:text>
    <xsl:call-template name="mark.subheading"/>
  </xsl:template>

  <!-- * Put a horizontal rule or other divider around section titles -->
  <!-- * in roff source (just to make things easier to read). -->
  <xsl:template name="mark.subheading">
    <xsl:if test="$man.subheading.divider.enabled != 0">
      <xsl:text>.\" </xsl:text>
      <xsl:value-of select="$man.subheading.divider"/>
      <xsl:text>&#10;</xsl:text>
    </xsl:if>
  </xsl:template>

  <!-- ================================================================== -->

  <xsl:template name="roff-if-else-start">
    <xsl:param name="condition">n</xsl:param>
    <xsl:text>.ie </xsl:text>
    <xsl:value-of select="$condition"/>
    <xsl:text> \{\&#10;</xsl:text>
  </xsl:template>

  <xsl:template name="roff-if-start">
    <xsl:param name="condition">n</xsl:param>
    <xsl:text>.if </xsl:text>
    <xsl:value-of select="$condition"/>
    <xsl:text> \{\&#10;</xsl:text>
  </xsl:template>

  <xsl:template name="roff-else">
    <xsl:text>.\}&#10;</xsl:text>
    <xsl:text>.el \{\&#10;</xsl:text>
  </xsl:template>

  <xsl:template name="roff-if-end">
    <xsl:text>.\}&#10;</xsl:text>
  </xsl:template>

</xsl:stylesheet>
