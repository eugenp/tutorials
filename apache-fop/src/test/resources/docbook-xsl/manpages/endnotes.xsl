<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                xmlns:ng="http://docbook.org/docbook-ng"
                xmlns:db="http://docbook.org/ns/docbook"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                exclude-result-prefixes="db ng exsl xlink"
                version='1.0'>

<!-- ********************************************************************
     $Id: endnotes.xsl 8703 2010-07-06 20:57:06Z nwalsh $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->
<!-- * -->
<!-- * The templates in this file handle elements whose contents can't -->
<!-- * be displayed completely within the main text flow in output, but -->
<!-- * instead need to be displayed "out of line". Those elements are: -->
<!-- * -->
<!-- *   - elements providing annotative text (annotation|alt|footnote) -->
<!-- *   - elements pointing at external resources (ulink, link, and -->
<!-- *     any elements with xlink:href attributes; and imagedata, -->
<!-- *     audiodata, and videodata - which (using their fileref -->
<!-- *     attribute) reference external files -->
<!-- * -->
<!-- * Within this stylesheet, the above are collectively referred to as -->
<!-- * a "notesources". This stylesheet handles those notesources in -->
<!-- * this way: -->
<!-- * -->
<!-- * 1. Constructs a numbered in-memory index of all unique "earmarks“ -->
<!-- *    of all notesources in the document. For each link, the -->
<!-- *    earmark is the value of its url or xlink:href attribute; for -->
<!-- *    each imagedata|audiodata|videodata: the value of its fileref -->
<!-- *    attribute; for each annotative element: its content. -->
<!-- * -->
<!-- *    Notesources with the same earmark are assigned the same -->
<!-- *    number. -->
<!-- * -->
<!-- *    By design, that index excludes any element whose string value -->
<!-- *    is identical to the value of its url xlink:href attribute). -->
<!-- * -->
<!-- * 2. Puts a numbered marker inline to mark the place where the -->
<!-- *    notesource occurs in the main text flow. -->
<!-- * -->
<!-- * 3. Generates a numbered endnotes list (titled NOTES in English) -->
<!-- *    at the end of the man page, with the contents of each -->
<!-- *    notesource. -->
<!-- * -->
<!-- * Note that table footnotes are not listed in the endnotes list, -->
<!-- * and are not handled by this stylesheet (they are instead handled -->
<!-- * by the table.xsl stylesheet). -->
<!-- * -->
<!-- * Also, we don't get notesources in *info sections or Refmeta or -->
<!-- * Refnamediv or Indexterm, because, in manpages output, contents of -->
<!-- * those are either suppressed or are displayed out of document -->
<!-- * order - for example, the Info/Author content gets moved to the -->
<!-- * end of the page. So, if we were to number notesources in the -->
<!-- * Author content, it would "throw off" the numbering at the -->
<!-- * beginning of the main text flow. -->
<!-- * -->
<!-- * And for the record, one reason we don't use xsl:key to index the -->
<!-- * earmarks is that we need to get and check the sets of -->
<!-- * earmarks for uniqueness per-Refentry (not per-document). -->
<!-- * -->
<!-- * FIXME: -->
<!-- * as with "repeat" URLS, alt instances that have the same string value -->
<!-- * as preceding ones (likely to occur for repeat acroynyms and -->
<!-- * abbreviations) should be listed only once in the endnotes list, -->
<!-- * and numbered accordingly inline; split man.indent.width into -->
<!-- * man.indent.width.value (default 4) and man.indent.width.units -->
<!-- * (default n); also, if the first child of notesource is some block -->
<!-- * content other than a (non-formal) paragraph, the current code -->
<!-- * will probably end up generating a blank line after the -->
<!-- * corresponding number in the endnotes list... we should probably -->
<!-- * try to instead display the title of that block content there (if -->
<!-- * there is one: e.g., the list title, admonition title, etc.) -->

<!-- ==================================================================== -->

<xsl:template name="get.all.earmark.indexes.in.current.document">
  <!-- * Here we create a tree to hold indexes of all earmarks in -->
  <!-- * the current document. If the current document contains -->
  <!-- * multiple refentry instances, then this tree will contain -->
  <!-- * multiple indexes. -->
  <xsl:if test="$man.endnotes.are.numbered != 0">
    <!-- * Only create earmark indexes if user wants numbered endnotes -->
    <xsl:for-each select="//refentry">
      <earmark.index>
        <xsl:attribute name="idref">
          <xsl:value-of select="generate-id()"/>
        </xsl:attribute>
        <xsl:for-each
            select=".//*[self::*[@xlink:href]
                    or self::ulink
                    or self::imagedata
                    or self::audiodata
                    or self::videodata
                    or self::footnote[not(ancestor::table)]
                    or self::annotation
                    or self::alt]
                    [(node()
                      or self::imagedata
                      or self::audiodata
                      or self::videodata
                      )
                    and not(ancestor::refentryinfo)
                    and not(ancestor::info)
                    and not(ancestor::docinfo)
                    and not(ancestor::refmeta)
                    and not(ancestor::refnamediv)
                    and not(ancestor::indexterm)
                    and not(. = @url)
                    and not(. = @xlink:href)
                    and not(@url =
                    preceding::ulink[node()
                    and not(ancestor::refentryinfo)
                    and not(ancestor::info)
                    and not(ancestor::docinfo)
                    and not(ancestor::refmeta)
                    and not(ancestor::refnamediv)
                    and not(ancestor::indexterm)
                    and (generate-id(ancestor::refentry)
                    = generate-id(current()))]/@url)
                    and not(@xlink:href =
                    preceding::*[@xlink:href][node()
                    and not(ancestor::refentryinfo)
                    and not(ancestor::info)
                    and not(ancestor::docinfo)
                    and not(ancestor::refmeta)
                    and not(ancestor::refnamediv)
                    and not(ancestor::indexterm)
                    and (generate-id(ancestor::refentry)
                    = generate-id(current()))]/@xlink:href)
                    and not(@fileref =
                    preceding::*[@fileref][
                    not(ancestor::refentryinfo)
                    and not(ancestor::info)
                    and not(ancestor::docinfo)
                    and not(ancestor::refmeta)
                    and not(ancestor::refnamediv)
                    and not(ancestor::indexterm)
                    and (generate-id(ancestor::refentry)
                    = generate-id(current()))]/@fileref)]">
          <earmark>
            <xsl:attribute name="id">
              <xsl:value-of select="generate-id()"/>
            </xsl:attribute>
            <xsl:attribute name="number">
              <xsl:value-of select="position()"/>
            </xsl:attribute>
            <xsl:if test="@url|@xlink:href|@fileref">
              <!-- * Only add a uri attribute if the notesource is -->
              <!-- * a link or an element that references an external -->
              <!-- * (an imagedata, audiodata, or videodata element) -->
              <xsl:attribute name="uri">
                <xsl:value-of select="@url|@xlink:href|@fileref"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:copy>
              <xsl:copy-of select="node()"/>
            </xsl:copy>
          </earmark>
        </xsl:for-each>
      </earmark.index>
    </xsl:for-each>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*[@xlink:href]|ulink
  |imagedata|audiodata|videodata
  |footnote[not(ancestor::table)]
  |annotation|alt">
  <xsl:variable name="refname" select="ancestor::refentry/refnamediv[1]/refname[1]"/>
  <xsl:variable name="all.earmark.indexes.in.current.document.rtf">
    <xsl:call-template name="get.all.earmark.indexes.in.current.document"/>
  </xsl:variable>
  <xsl:variable name="all.earmark.indexes.in.current.document"
                select="exsl:node-set($all.earmark.indexes.in.current.document.rtf)"/>
  <xsl:variable name="all.earmarks.in.current.refentry.rtf">
    <!-- * get the set of all earmarks for the ancestor Refentry of -->
    <!-- * this notesource -->
    <xsl:copy-of
        select="$all.earmark.indexes.in.current.document/earmark.index
                [@idref =
                generate-id(current()/ancestor::refentry)]/earmark"/>
  </xsl:variable>
  <xsl:variable name="all.earmarks.in.current.refentry"
                select="exsl:node-set($all.earmarks.in.current.refentry.rtf)"/>

  <!-- * identify the earmark for the current element -->
  <xsl:variable name="earmark">
    <xsl:choose>
      <xsl:when test="@url|@xlink:href">
        <xsl:value-of select="@url|@xlink:href"/>
      </xsl:when>
      <xsl:when test="@fileref">
        <xsl:value-of select="@fileref"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="generate-id()"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="notesource.number">
    <!-- * Get the number for this notesource -->
    <!-- * -->
    <!-- * If this is an imagedata, audiodata, or videodata element -->
    <!-- * OR if it's a non-empty element AND its string value is not -->
    <!-- * equal to the value of its url or xlink:href attribute (if -->
    <!-- * it has one) AND user wants endnotes numbered, only then -->
    <!-- * do we output a number for it -->
    <xsl:if test="(self::imagedata or
      self::audiodata or
      self::videodata or
      (node()
      and not(. = @url)
      and not(. = @xlink:href))
      )
      and $man.endnotes.are.numbered != 0">
      <!-- * To select the number for this notesource, we -->
      <!-- * check the index of all earmarks for the current refentry -->
      <!-- * and find the number of the indexed earmark which matches -->
      <!-- * this notesource's earmark. -->
      <!-- * Note that multiple notesources may share the same -->
      <!-- * numbered earmark; in that case, they get the same number. -->
      <!-- * -->
      <xsl:choose>
        <xsl:when test="self::ulink or
          self::*[@xlink:href] or
          self::imagedata or
          self::audiodata or
          self::videodata">
          <xsl:value-of select="$all.earmarks.in.current.refentry/earmark[@uri = $earmark]/@number"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$all.earmarks.in.current.refentry/earmark[@id  = $earmark]/@number"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="notesource.contents">
    <xsl:choose>
      <!-- * check to see if the element is empty or not -->
      <xsl:when test="node()">
        <!-- * this is a non-empty node, so process its contents -->
        <xsl:apply-templates/>
        <xsl:if test="../footnote or ../annotation">
          <!-- * if this element is a footnote or annotation, we need to -->
          <!-- * do some further checking on it, so we can emit warnings -->
          <!-- * about potential problems -->
        <xsl:for-each select="node()">
          <xsl:if test="local-name() != 'para' and
                        local-name() != 'simpara' and
                        local-name() !=''">
            <!-- * for each node we find as a child of a footnote or -->
            <!-- * annotation, if it's not a para or a text node, emit a -->
            <!-- * warning... because in manpages output, we can't render -->
            <!-- * block-level child content of an endnote properly unless -->
            <!-- * it's wrapped in a para that has some "prefatory" text -->
            <xsl:variable name="parent-name" select="local-name(..)"/>
            <xsl:variable name="endnote-number">
              <xsl:call-template name="pad-string">
                <!-- * endnote number may be 2 digits, so pad it with a space -->
                <!-- * if we have only 1 digit -->
                <xsl:with-param name="padVar" select="concat('#',$notesource.number)"/>
                <xsl:with-param name="length" select="3"/>
              </xsl:call-template>
            </xsl:variable>
            <xsl:call-template name="log.message">
              <xsl:with-param name="level">Warn</xsl:with-param>
              <xsl:with-param name="source" select="$refname"/>
              <xsl:with-param name="context-desc">
                <xsl:text>endnote </xsl:text>
                <xsl:value-of select="$endnote-number"/>
              </xsl:with-param>
              <xsl:with-param name="message">
                <xsl:text>Bad: </xsl:text>
                <xsl:value-of select="$parent-name"/> 
                <!-- * figure out which occurance of this element type this -->
                <!-- * instance is and output a number in square brackets so -->
                <!-- * that end-user can know which element to fix -->
                <xsl:text>[</xsl:text>
                <xsl:value-of select="count(preceding::*[local-name() = $parent-name]) + 1"/>
                <xsl:text>]</xsl:text>
                <xsl:text> in source</xsl:text>
              </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="log.message">
              <xsl:with-param name="level">Note</xsl:with-param>
              <xsl:with-param name="source" select="$refname"/>
              <xsl:with-param name="context-desc">
                <xsl:text>endnote </xsl:text>
                <xsl:value-of select="$endnote-number"/>
              </xsl:with-param>
              <xsl:with-param name="message">
                <xsl:text>Has: </xsl:text>
                <xsl:value-of select="$parent-name"/> 
                <xsl:text>/</xsl:text>
                <xsl:value-of select="local-name(.)"/>
              </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="log.message">
              <xsl:with-param name="level">Note</xsl:with-param>
              <xsl:with-param name="source" select="$refname"/>
              <xsl:with-param name="context-desc">
                <xsl:text>endnote </xsl:text>
                <xsl:value-of select="$endnote-number"/>
              </xsl:with-param>
              <xsl:with-param name="message">
                <xsl:text>Fix: </xsl:text>
                <xsl:value-of select="$parent-name"/> 
                <xsl:text>/</xsl:text>
                <xsl:text>para/</xsl:text>
                <xsl:value-of select="local-name(.)"/>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:if>
        </xsl:for-each>
      </xsl:if>
      </xsl:when>
      <xsl:otherwise>
        <!-- * Otherwise this is an empty link or an empty imagedata, -->
        <!-- * audiodata, or videodata element, so we just get the -->
        <!-- * value of its url, xlink:href, or fileref attribute. -->
        <xsl:if test="$man.hyphenate.urls = 0
          and $man.break.after.slash = 0">
          <!-- * Add hyphenation suppression in URL output only if -->
          <!-- * break.after.slash is also non-zero -->
          <xsl:call-template name="suppress.hyphenation"/>
          <xsl:text>\%</xsl:text>
        </xsl:if>
        <xsl:value-of select="$earmark"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="self::ulink or self::*[@xlink:href]">
    <xsl:variable name="link.wrapper">
      <xsl:value-of select="normalize-space($notesource.contents)"/>
    </xsl:variable>
    <xsl:text>\m[blue]</xsl:text>
    <!-- * This is a hyperlink, so we need to determine if the user wants -->
    <!-- * font formatting applied to it, and if so, what font -->
    <xsl:choose>
      <xsl:when test="$man.font.links = 'B'">
        <xsl:call-template name="bold">
          <xsl:with-param name="node" select="exsl:node-set($link.wrapper)"/>
          <xsl:with-param name="context" select="."/>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="$man.font.links = 'I'">
        <xsl:call-template name="italic">
          <xsl:with-param name="node" select="exsl:node-set($link.wrapper)"/>
          <xsl:with-param name="context" select="."/>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test="$man.font.links = ''">
        <!-- * if man.font.links is empty, user doesn't want links -->
        <!-- * underlined, so just display content -->
        <xsl:value-of select="$notesource.contents"/>
      </xsl:when>
      <xsl:otherwise>
        <!-- * otherwise the user has specified an unsupported value for -->
        <!-- * man.font.links, so emit a warning and don't apply any font -->
        <!-- * formatting -->
        <xsl:message>
          <xsl:call-template name="log.message">
            <xsl:with-param name="level">Warn</xsl:with-param>
            <xsl:with-param name="source" select="$refname"/>
            <xsl:with-param name="context-desc">
              <xsl:text>link font</xsl:text>
            </xsl:with-param>
            <xsl:with-param name="message">
              <xsl:text>invalid $man.font.links value: </xsl:text>
              <xsl:text>'</xsl:text>
              <xsl:value-of select="$man.font.links"/>
              <xsl:text>'</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:message>
        <xsl:value-of select="$notesource.contents"/>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text>\m[]</xsl:text>
  </xsl:if>

  <xsl:if test="$notesource.number != ''">
    <!-- * Format the number by placing it in square brackets. -->
    <!-- * Also, set the number in font-size -2, and superscripted (\u -->
    <!-- * means to move up half a line vertically) -->
    <xsl:text>\&amp;\s-2\u[</xsl:text>
    <xsl:value-of select="$notesource.number"/>
    <xsl:text>]\d\s+2</xsl:text>
    <!-- * Revert superscripting (\d means to move down half a line), and -->
    <!-- * move the font-size back to what it was before. -->
    <!-- * Note that the reason for the \& before the opening bracket -->
    <!-- * is to prevent any possible linebreak from being introduced -->
    <!-- * between the opening bracket and the following text. -->
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="endnotes.list">
  <!-- We have stored earmark indexes for all refentry instances in the -->
  <!-- current document, with the ID for each index being the same ID as -->
  <!-- its corresponding refentry; so we now need to get the ID for the -->
  <!-- current refentry so we can grab its corresponding earmark index -->
  <xsl:variable name="current.refentry.id">
    <xsl:value-of select="generate-id(.)"/>
  </xsl:variable>

  <xsl:variable name="endnotes.rtf">
    <xsl:variable name="all.earmark.indexes.in.current.document.rtf">
      <xsl:call-template  name="get.all.earmark.indexes.in.current.document"/>
    </xsl:variable>
    <xsl:variable name="all.earmark.indexes.in.current.document"
                  select="exsl:node-set($all.earmark.indexes.in.current.document.rtf)"/>
      <xsl:copy-of
          select="$all.earmark.indexes.in.current.document/earmark.index
                  [@idref = $current.refentry.id]/earmark"/>
  </xsl:variable>

  <xsl:variable name="endnotes" select="exsl:node-set($endnotes.rtf)"/>

  <!-- * check to see if we have actually found any content to use as -->
  <!-- * endnotes; if we have, we generate the endnotes list, if not, -->
  <!-- * we do nothing -->
  <xsl:if test="$endnotes/node()">
    <xsl:call-template name="format.endnotes.list">
      <xsl:with-param name="endnotes" select="$endnotes"/>
    </xsl:call-template>
  </xsl:if>

</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="format.endnotes.list">
  <xsl:param name="endnotes"/>

  <!-- * ======= make the endnotes-list section heading ============= -->
  <xsl:call-template name="make.subheading">
    <xsl:with-param name="title">
      <xsl:choose>
        <!-- * if user has specified a heading, use that -->
        <xsl:when test="$man.endnotes.list.heading != ''">
          <xsl:value-of select="$man.endnotes.list.heading"/>
        </xsl:when>
        <xsl:otherwise>
          <!-- * otherwise, get localized heading from gentext -->
          <!-- * (in English, NOTES) -->
          <xsl:call-template name="gentext">
            <xsl:with-param name="key" select="'Notes'"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:with-param>
  </xsl:call-template>

  <!-- * ================ process each earmark ====================== -->
  <xsl:for-each select="$endnotes/earmark">
    <!-- * make paragraph with hanging indent, and starting with a -->
    <!-- * number in the form " 1." (padded to $man.indent.width - 1) -->
    <xsl:text>.IP</xsl:text>
    <xsl:text> "</xsl:text>
    <xsl:variable name="endnote.number">
      <xsl:value-of select="@number"/>
      <xsl:text>.</xsl:text>
    </xsl:variable>
    <xsl:call-template name="pad-string">
      <xsl:with-param name="padVar" select="$endnote.number"/>
      <!-- FIXME: the following assumes that $man.indent.width is in -->
      <!-- en's; also, this should probably use $list.indent instead -->
      <xsl:with-param name="length" select="$man.indent.width - 1"/>
    </xsl:call-template>
    <xsl:text>"</xsl:text>
    <xsl:if test="not($list-indent = '')">
      <xsl:text> </xsl:text>
      <xsl:value-of select="$list-indent"/>
    </xsl:if>
    <xsl:text>&#10;</xsl:text>

    <!-- * ========================================================= -->
    <!-- *           print the notesource/endnote contents -->
    <!-- * ========================================================= -->
    <xsl:choose>
      <xsl:when test="*/node()">
        <!-- * if the earmark has non-empty child content, then -->
        <!-- * its corresponding notesource is either a link or -->
        <!-- * an instance of annotative text, so we want to -->
        <!-- * display that content -->
        <xsl:choose>
          <xsl:when test="*/node()[name(.)!='']">
            <!-- * if node is not text only, then process it as-is -->
            <xsl:apply-templates select="*/node()"/>
          </xsl:when>
          <xsl:otherwise>
            <!-- * otherwise node is text-only, so normalize it -->
            <xsl:value-of select="normalize-space(*/node())"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <!-- * otherwise, this earmark has empty content, -->
        <!-- * which means its corresponding notesource is an -->
        <!-- * imagedata, audiodata, or videodata instance; in -->
        <!-- * that case, we use the value of the notesource's -->
        <!-- * @fileref attribute (which is stored in the -->
        <!-- * earmark uri attribute) as the "contents" for -->
        <!-- * this endnote/notesource -->
        <xsl:call-template name="display.uri">
          <xsl:with-param name="uri" select="@uri"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text>&#10;</xsl:text>

    <!-- * ========================================================= -->
    <!-- *           print the URL for links -->
    <!-- * ========================================================= -->
    <!-- * In addition to the notesource contents, if the -->
    <!-- * notesource is a link, we display the URL for the link. -->
    <!-- * But for notesources that are imagedata, audiodata, or -->
    <!-- * videodata instances, we don't want to (re)display the -->
    <!-- * URL for those here, because for those elements, the -->
    <!-- * notesource contents are the URL (the value of the -->
    <!-- * @fileref attribute), and we have already rendered them. -->
    <!-- * -->
    <!-- * We know an earmark is a link if it has non-empty child -->
    <!-- * content and a uri attribute; so we check for that -->
    <!-- * condition here. -->
    <xsl:if test="*/node() and @uri">
      <xsl:text>.RS</xsl:text>
      <xsl:if test="not($list-indent = '')">
        <xsl:text> </xsl:text>
        <xsl:value-of select="$list-indent"/>
      </xsl:if>
      <xsl:text>&#10;</xsl:text>
      <!-- * Add hyphenation suppression in URL output only if -->
      <!-- * $break.after.slash is also non-zero -->
      <xsl:if test="$man.hyphenate.urls = 0
                    and $man.break.after.slash = 0">
        <xsl:call-template name="suppress.hyphenation"/>
        <xsl:text>\%</xsl:text>
      </xsl:if>
      <xsl:call-template name="display.uri">
        <xsl:with-param name="uri" select="@uri"/>
      </xsl:call-template>
      <xsl:text>&#10;</xsl:text>
      <xsl:text>.RE</xsl:text>
      <xsl:text>&#10;</xsl:text>
    </xsl:if>

  </xsl:for-each>
</xsl:template>

<xsl:template name="display.uri">
  <xsl:param name="uri"/>
  <xsl:choose>
    <xsl:when test="contains($uri, ':')">
      <!-- * if this URI contains a colon character, it’s probably -->
      <!-- * an absolute URI with a scheme, so we output it as-is -->
      <xsl:value-of select="$uri"/>
    </xsl:when>
    <xsl:otherwise>
      <!-- * otherwise this is probably not an absolute URI, so we -->
      <!-- * need to prepend $man.base.url.for.relative.links to -->
      <!-- * give the URI some "context" in man-page output -->
      <xsl:value-of
        select="concat($man.base.url.for.relative.links, $uri)"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
