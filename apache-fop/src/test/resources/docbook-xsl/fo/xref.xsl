<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:exsl="http://exslt.org/common"
                xmlns:xlink='http://www.w3.org/1999/xlink'
                exclude-result-prefixes="exsl xlink"
                version='1.0'>

<!-- ********************************************************************
     $Id: xref.xsl 9723 2013-02-06 13:08:06Z kosek $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- Use internal variable for olink xlink role for consistency -->
<xsl:variable 
      name="xolink.role">http://docbook.org/xlink/role/olink</xsl:variable>

<!-- ==================================================================== -->

<xsl:template match="anchor">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="wrapper.name">
    <xsl:call-template name="inline.or.block"/>
  </xsl:variable>

  <xsl:element name="{$wrapper.name}">
    <xsl:attribute name="id">
      <xsl:value-of select="$id"/>
    </xsl:attribute>
  </xsl:element>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="xref" name="xref">
  <xsl:param name="xhref" select="@xlink:href"/>
  <!-- is the @xlink:href a local idref link? -->
  <xsl:param name="xlink.idref">
    <xsl:if test="starts-with($xhref,'#')
                  and (not(contains($xhref,'&#40;'))
                  or starts-with($xhref, '#xpointer&#40;id&#40;'))">
      <xsl:call-template name="xpointer.idref">
        <xsl:with-param name="xpointer" select="$xhref"/>
      </xsl:call-template>
   </xsl:if>
  </xsl:param>
  <xsl:param name="xlink.targets" select="key('id',$xlink.idref)"/>
  <xsl:param name="linkend.targets" select="key('id',@linkend)"/>
  <xsl:param name="target" select="($xlink.targets | $linkend.targets)[1]"/>
  <xsl:param name="refelem" select="local-name($target)"/>

  <xsl:variable name="xrefstyle">
    <xsl:choose>
      <xsl:when test="@role and not(@xrefstyle) 
                      and $use.role.as.xrefstyle != 0">
        <xsl:value-of select="@role"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@xrefstyle"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="content">
    <fo:inline xsl:use-attribute-sets="xref.properties">
      <xsl:choose>
        <xsl:when test="@endterm">
          <xsl:variable name="etargets" select="key('id',@endterm)"/>
          <xsl:variable name="etarget" select="$etargets[1]"/>
          <xsl:choose>
            <xsl:when test="count($etarget) = 0">
              <xsl:message>
                <xsl:value-of select="count($etargets)"/>
                <xsl:text>Endterm points to nonexistent ID: </xsl:text>
                <xsl:value-of select="@endterm"/>
              </xsl:message>
              <xsl:text>???</xsl:text>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="$etarget" mode="endterm"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
  
        <xsl:when test="$target/@xreflabel">
          <xsl:call-template name="xref.xreflabel">
            <xsl:with-param name="target" select="$target"/>
          </xsl:call-template>
        </xsl:when>
  
        <xsl:when test="$target">
          <xsl:if test="not(parent::citation)">
            <xsl:apply-templates select="$target" mode="xref-to-prefix"/>
          </xsl:if>
  
          <xsl:apply-templates select="$target" mode="xref-to">
            <xsl:with-param name="referrer" select="."/>
            <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
          </xsl:apply-templates>
  
          <xsl:if test="not(parent::citation)">
            <xsl:apply-templates select="$target" mode="xref-to-suffix"/>
          </xsl:if>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>
            <xsl:text>ERROR: xref linking to </xsl:text>
            <xsl:value-of select="@linkend|@xlink:href"/>
            <xsl:text> has no generated link text.</xsl:text>
          </xsl:message>
          <xsl:text>???</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </fo:inline>
  </xsl:variable>

  <!-- Convert it into an active link -->
  <xsl:call-template name="simple.xlink">
    <xsl:with-param name="content" select="$content"/>
  </xsl:call-template>

  <!-- Add standard page reference? -->
  <xsl:choose>
    <xsl:when test="not($target)">
      <!-- page numbers only for local targets -->
    </xsl:when>
    <xsl:when test="starts-with(normalize-space($xrefstyle), 'select:') 
                  and contains($xrefstyle, 'nopage')">
      <!-- negative xrefstyle in instance turns it off -->
    </xsl:when>
    <!-- positive xrefstyle already handles it -->
    <xsl:when test="not(starts-with(normalize-space($xrefstyle), 'select:') 
                  and (contains($xrefstyle, 'page')
                       or contains($xrefstyle, 'Page')))
                  and ( $insert.xref.page.number = 'yes' 
                     or $insert.xref.page.number = '1')
                  or (local-name($target) = 'para' and
		      $xrefstyle = '')">
      <xsl:apply-templates select="$target" mode="page.citation">
        <xsl:with-param name="id" select="$target/@id|$target/@xml:id"/>
      </xsl:apply-templates>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<!-- Handled largely like an xref -->
<!-- To be done: add support for begin, end, and units attributes -->
<xsl:template match="biblioref" name="biblioref">
  <xsl:variable name="targets" select="key('id',@linkend)"/>
  <xsl:variable name="target" select="$targets[1]"/>
  <xsl:variable name="refelem" select="local-name($target)"/>

  <xsl:call-template name="check.id.unique">
    <xsl:with-param name="linkend" select="@linkend"/>
  </xsl:call-template>

  <xsl:choose>
    <xsl:when test="$refelem=''">
      <xsl:message>
        <xsl:text>XRef to nonexistent id: </xsl:text>
        <xsl:value-of select="@linkend"/>
      </xsl:message>
      <xsl:text>???</xsl:text>
    </xsl:when>

    <xsl:when test="@endterm">
      <fo:basic-link internal-destination="{@linkend}"
                     xsl:use-attribute-sets="xref.properties">
        <xsl:variable name="etargets" select="key('id',@endterm)"/>
        <xsl:variable name="etarget" select="$etargets[1]"/>
        <xsl:choose>
          <xsl:when test="count($etarget) = 0">
            <xsl:message>
              <xsl:value-of select="count($etargets)"/>
              <xsl:text>Endterm points to nonexistent ID: </xsl:text>
              <xsl:value-of select="@endterm"/>
            </xsl:message>
            <xsl:text>???</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="$etarget" mode="endterm"/>
          </xsl:otherwise>
        </xsl:choose>
      </fo:basic-link>
    </xsl:when>

    <xsl:when test="$target/@xreflabel">
      <fo:basic-link internal-destination="{@linkend}"
                     xsl:use-attribute-sets="xref.properties">
        <xsl:call-template name="xref.xreflabel">
          <xsl:with-param name="target" select="$target"/>
        </xsl:call-template>
      </fo:basic-link>
    </xsl:when>

    <xsl:otherwise>
      <xsl:if test="not(parent::citation)">
        <xsl:apply-templates select="$target" mode="xref-to-prefix"/>
      </xsl:if>

      <fo:basic-link internal-destination="{@linkend}"
                     xsl:use-attribute-sets="xref.properties">
        <xsl:apply-templates select="$target" mode="xref-to">
          <xsl:with-param name="referrer" select="."/>
          <xsl:with-param name="xrefstyle">
            <xsl:choose>
              <xsl:when test="@role and not(@xrefstyle) and $use.role.as.xrefstyle != 0">
                <xsl:value-of select="@role"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="@xrefstyle"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:apply-templates>
      </fo:basic-link>

      <xsl:if test="not(parent::citation)">
        <xsl:apply-templates select="$target" mode="xref-to-suffix"/>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*" mode="endterm">
  <!-- Process the children of the endterm element -->
  <xsl:variable name="endterm">
    <xsl:apply-templates select="child::node()"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$exsl.node.set.available != 0">
      <xsl:apply-templates select="exsl:node-set($endterm)" mode="remove-ids"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$endterm"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*" mode="remove-ids">
  <xsl:copy>
    <xsl:for-each select="@*">
      <xsl:choose>
        <xsl:when test="name(.) != 'id'">
          <xsl:copy/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>removing <xsl:value-of select="name(.)"/></xsl:message>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
    <xsl:apply-templates mode="remove-ids"/>
  </xsl:copy>
</xsl:template>

<!--- ==================================================================== -->

<xsl:template match="*" mode="xref-to-prefix"/>
<xsl:template match="*" mode="xref-to-suffix"/>

<xsl:template match="*" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>


  <xsl:if test="$verbose != 0">
    <xsl:message>
      <xsl:text>Don't know what gentext to create for xref to: "</xsl:text>
      <xsl:value-of select="name(.)"/>
      <xsl:text>"</xsl:text>
    </xsl:message>
    <xsl:text>???</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="title" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <!-- if you xref to a title, xref to the parent... -->
  <xsl:choose>
    <!-- FIXME: how reliable is this? -->
    <xsl:when test="contains(local-name(parent::*), 'info')">
      <xsl:apply-templates select="parent::*[2]" mode="xref-to">
        <xsl:with-param name="referrer" select="$referrer"/>
        <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="parent::*" mode="xref-to">
        <xsl:with-param name="referrer" select="$referrer"/>
        <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="abstract|article|authorblurb|bibliodiv|bibliomset
                     |biblioset|blockquote|calloutlist|caution|colophon
                     |constraintdef|formalpara|glossdiv|important|indexdiv
                     |itemizedlist|legalnotice|lot|msg|msgexplan|msgmain
                     |msgrel|msgset|msgsub|note|orderedlist|partintro
                     |productionset|qandadiv|refsynopsisdiv|screenshot|segmentedlist
                     |set|setindex|sidebar|tip|toc|variablelist|warning"
              mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <!-- catch-all for things with (possibly optional) titles -->
  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="author|editor|othercredit|personname" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:call-template name="person.name"/>
</xsl:template>

<xsl:template match="authorgroup" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:call-template name="person.name.list"/>
</xsl:template>

<xsl:template match="figure|example|table|equation" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="procedure" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="task" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="cmdsynopsis" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="(.//command)[1]" mode="xref"/>
</xsl:template>

<xsl:template match="funcsynopsis" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="(.//function)[1]" mode="xref"/>
</xsl:template>

<xsl:template match="dedication|acknowledgements|preface|chapter|appendix" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="bibliography" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="biblioentry|bibliomixed" mode="xref-to-prefix">
  <xsl:text>[</xsl:text>
</xsl:template>

<xsl:template match="biblioentry|bibliomixed" mode="xref-to-suffix">
  <xsl:text>]</xsl:text>
</xsl:template>

<xsl:template match="biblioentry|bibliomixed" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <!-- handles both biblioentry and bibliomixed -->
  <xsl:choose>
    <xsl:when test="string(.) = ''">
      <xsl:variable name="bib" select="document($bibliography.collection,.)"/>
      <xsl:variable name="id" select="(@id|@xml:id)[1]"/>
      <xsl:variable name="entry" select="$bib/bibliography/
                                         *[@id=$id or @xml:id=$id][1]"/>
      <xsl:choose>
        <xsl:when test="$entry">
          <xsl:choose>
            <xsl:when test="$bibliography.numbered != 0">
              <xsl:number from="bibliography" count="biblioentry|bibliomixed"
                          level="any" format="1"/>
            </xsl:when>
            <xsl:when test="local-name($entry/*[1]) = 'abbrev'">
              <xsl:apply-templates select="$entry/*[1]"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="(@id|@xml:id)[1]"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>
            <xsl:text>No bibliography entry: </xsl:text>
            <xsl:value-of select="$id"/>
            <xsl:text> found in </xsl:text>
            <xsl:value-of select="$bibliography.collection"/>
          </xsl:message>
          <xsl:value-of select="(@id|@xml:id)[1]"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$bibliography.numbered != 0">
          <xsl:number from="bibliography" count="biblioentry|bibliomixed"
                      level="any" format="1"/>
        </xsl:when>
        <xsl:when test="local-name(*[1]) = 'abbrev'">
          <xsl:apply-templates select="*[1]"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="(@id|@xml:id)[1]"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="glossary" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="glossentry" mode="xref-to">
  <xsl:choose>
    <xsl:when test="$glossentry.show.acronym = 'primary'">
      <xsl:choose>
        <xsl:when test="acronym|abbrev">
          <xsl:apply-templates select="(acronym|abbrev)[1]"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="glossterm[1]" mode="xref-to"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="glossterm[1]" mode="xref-to"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="glossterm|firstterm" mode="xref-to">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="index" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="listitem" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="section|simplesect
                     |sect1|sect2|sect3|sect4|sect5
                     |refsect1|refsect2|refsect3|refsection" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
  <!-- What about "in Chapter X"? -->
</xsl:template>

<xsl:template match="topic" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="bridgehead" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
  <!-- What about "in Chapter X"? -->
</xsl:template>

<xsl:template match="qandaset" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="qandadiv" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="qandaentry" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="question[1]" mode="xref-to">
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="question|answer" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:choose>
    <xsl:when test="string-length(label) != 0">
      <xsl:apply-templates select="." mode="label.markup"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="." mode="object.xref.markup">
        <xsl:with-param name="purpose" select="'xref'"/>
        <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
        <xsl:with-param name="referrer" select="$referrer"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="part|reference" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="refentry" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:choose>
    <xsl:when test="refmeta/refentrytitle">
      <xsl:apply-templates select="refmeta/refentrytitle"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="refnamediv/refname[1]"/>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:apply-templates select="refmeta/manvolnum"/>
</xsl:template>

<xsl:template match="refnamediv" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="refname[1]" mode="xref-to">
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="refname" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates mode="xref-to">
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="step" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'Step'"/>
  </xsl:call-template>
  <xsl:text> </xsl:text>
  <xsl:apply-templates select="." mode="number"/>
</xsl:template>

<xsl:template match="varlistentry" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="term[1]" mode="xref-to">
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="varlistentry/term" mode="xref-to">
  <xsl:param name="verbose" select="1"/>
  <!-- to avoid the comma that will be generated if there are several terms -->
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="co" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="callout-bug"/>
</xsl:template>

<xsl:template match="area|areaset" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>

  <xsl:call-template name="callout-bug">
    <xsl:with-param name="conum">
      <xsl:apply-templates select="." mode="conumber"/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="book" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose" select="1"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
    <xsl:with-param name="verbose" select="$verbose"/>
  </xsl:apply-templates>
</xsl:template>

<!-- These are elements for which no link text exists, so an xref to one
     uses the xrefstyle attribute if specified, or if not it falls back
     to the container element's link text -->
<xsl:template match="para|phrase|simpara|anchor|quote" mode="xref-to">
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="verbose"/>

  <xsl:variable name="context" select="(ancestor::simplesect
                                       |ancestor::section
                                       |ancestor::sect1
                                       |ancestor::sect2
                                       |ancestor::sect3
                                       |ancestor::sect4
                                       |ancestor::sect5
                                       |ancestor::topic
                                       |ancestor::refsection
                                       |ancestor::refsect1
                                       |ancestor::refsect2
                                       |ancestor::refsect3
                                       |ancestor::chapter
                                       |ancestor::appendix
                                       |ancestor::preface
                                       |ancestor::partintro
                                       |ancestor::dedication
                                       |ancestor::acknowledgements
                                       |ancestor::colophon
                                       |ancestor::bibliography
                                       |ancestor::index
                                       |ancestor::glossary
                                       |ancestor::glossentry
                                       |ancestor::listitem
                                       |ancestor::varlistentry)[last()]"/>

  <xsl:choose>
    <xsl:when test="$xrefstyle != ''">
      <xsl:apply-templates select="." mode="object.xref.markup">
        <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
        <xsl:with-param name="referrer" select="$referrer"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="$verbose != 0">
        <xsl:message>
          <xsl:text>WARNING: xref to &lt;</xsl:text>
          <xsl:value-of select="local-name()"/>
          <xsl:text> id="</xsl:text>
          <xsl:value-of select="@id|@xml:id"/>
          <xsl:text>"&gt; has no generated text. Trying its ancestor elements.</xsl:text>
        </xsl:message>
      </xsl:if>
      <xsl:apply-templates select="$context" mode="xref-to">
        <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
        <xsl:with-param name="referrer" select="$referrer"/>
        <xsl:with-param name="verbose" select="$verbose"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="indexterm" mode="xref-to">
  <xsl:value-of select="primary"/>
</xsl:template>

<xsl:template match="primary|secondary|tertiary" mode="xref-to">
  <xsl:value-of select="."/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="link" name="link">
  <xsl:param name="linkend" select="@linkend"/>
  <xsl:param name="targets" select="key('id',$linkend)"/>
  <xsl:param name="target" select="$targets[1]"/>

  <xsl:variable name="xrefstyle">
    <xsl:choose>
      <xsl:when test="@role and not(@xrefstyle) 
                      and $use.role.as.xrefstyle != 0">
        <xsl:value-of select="@role"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@xrefstyle"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="content">
    <fo:inline xsl:use-attribute-sets="xref.properties">
      <xsl:choose>
        <xsl:when test="count(child::node()) &gt; 0">
          <!-- If it has content, use it -->
          <xsl:apply-templates/>
        </xsl:when>
        <!-- look for an endterm -->
        <xsl:when test="@endterm">
          <xsl:variable name="etargets" select="key('id',@endterm)"/>
          <xsl:variable name="etarget" select="$etargets[1]"/>
          <xsl:choose>
            <xsl:when test="count($etarget) = 0">
              <xsl:message>
                <xsl:value-of select="count($etargets)"/>
                <xsl:text>Endterm points to nonexistent ID: </xsl:text>
                <xsl:value-of select="@endterm"/>
              </xsl:message>
              <xsl:text>???</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="$etarget" mode="endterm"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <!-- Use the xlink:href if no other text -->
        <xsl:when test="@xlink:href">
	  <fo:inline hyphenate="false">
	    <xsl:call-template name="hyphenate-url">
	      <xsl:with-param name="url" select="@xlink:href"/>
	    </xsl:call-template>
	  </fo:inline>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>
            <xsl:text>Link element has no content and no Endterm. </xsl:text>
            <xsl:text>Nothing to show in the link to </xsl:text>
            <xsl:value-of select="$target"/>
          </xsl:message>
          <xsl:text>???</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </fo:inline>
  </xsl:variable>

  <xsl:call-template name="simple.xlink">
    <xsl:with-param name="node" select="."/>
    <xsl:with-param name="linkend" select="$linkend"/>
    <xsl:with-param name="content" select="$content"/>
  </xsl:call-template>

  <!-- Add standard page reference? -->
  <xsl:choose>
    <!-- page numbering on link only enabled for @linkend -->
    <!-- There is no link element in DB5 with xlink:href -->
    <xsl:when test="not($linkend)">
    </xsl:when>
    <!-- negative xrefstyle in instance turns it off -->
    <xsl:when test="starts-with(normalize-space($xrefstyle), 'select:') 
                  and contains($xrefstyle, 'nopage')">
    </xsl:when>
    <xsl:when test="(starts-with(normalize-space($xrefstyle), 'select:') 
                  and $insert.link.page.number = 'maybe'  
                  and (contains($xrefstyle, 'page')
                       or contains($xrefstyle, 'Page')))
                  or ( $insert.link.page.number = 'yes' 
                     or $insert.link.page.number = '1')
                  or local-name($target) = 'para'">
      <xsl:apply-templates select="$target" mode="page.citation">
        <xsl:with-param name="id" select="$linkend"/>
      </xsl:apply-templates>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="ulink" name="ulink">
  <xsl:param name="url" select="@url"/>

  <xsl:variable name ="ulink.url">
    <xsl:call-template name="fo-external-image">
      <xsl:with-param name="filename" select="$url"/>
    </xsl:call-template>
  </xsl:variable>

  <fo:basic-link xsl:use-attribute-sets="xref.properties"
                 external-destination="{$ulink.url}">
    <xsl:choose>
      <xsl:when test="count(child::node())=0 or (string(.) = $url)">
	<fo:inline hyphenate="false">
	  <xsl:call-template name="hyphenate-url">
	    <xsl:with-param name="url" select="$url"/>
	  </xsl:call-template>
	</fo:inline>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates/>
      </xsl:otherwise>
    </xsl:choose>
  </fo:basic-link>
  <!-- * Call the template for determining whether the URL for this -->
  <!-- * hyperlink is displayed, and how to display it (either inline or -->
  <!-- * as a numbered footnote). -->
  <xsl:call-template name="hyperlink.url.display">
    <xsl:with-param name="url" select="$url"/>
    <xsl:with-param name="ulink.url" select="$ulink.url"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="hyperlink.url.display">
  <!-- * This template is called for all external hyperlinks (ulinks and -->
  <!-- * for all simple xlinks); it determines whether the URL for the -->
  <!-- * hyperlink is displayed, and how to display it (either inline or -->
  <!-- * as a numbered footnote). -->
  <xsl:param name="url"/>
  <xsl:param name="ulink.url">
    <!-- * ulink.url is just the value of the URL wrapped in 'url(...)' -->
    <xsl:call-template name="fo-external-image">
      <xsl:with-param name="filename" select="$url"/>
    </xsl:call-template>
  </xsl:param>

  <xsl:if test="count(child::node()) != 0
                and string(.) != $url
                and $ulink.show != 0">
    <!-- * Display the URL for this hyperlink only if it is non-empty, -->
    <!-- * and the value of its content is not a URL that is the same as -->
    <!-- * URL it links to, and if ulink.show is non-zero. -->
    <xsl:choose>
      <xsl:when test="$ulink.footnotes != 0 and not(ancestor::footnote) and not(ancestor::*[@floatstyle='before'])">
        <!-- * ulink.show and ulink.footnote are both non-zero; that -->
        <!-- * means we display the URL as a footnote (instead of inline) -->
        <fo:footnote>
          <xsl:call-template name="ulink.footnote.number"/>
          <fo:footnote-body xsl:use-attribute-sets="footnote.properties">
            <fo:block>
              <xsl:call-template name="ulink.footnote.number"/>
              <xsl:text> </xsl:text>
              <fo:basic-link external-destination="{$ulink.url}">
                <xsl:value-of select="$url"/>
              </fo:basic-link>
            </fo:block>
          </fo:footnote-body>
        </fo:footnote>
      </xsl:when>
      <xsl:otherwise>
        <!-- * ulink.show is non-zero, but ulink.footnote is not; that -->
        <!-- * means we display the URL inline -->
        <fo:inline hyphenate="false">
          <!-- * put square brackets around the URL -->
          <xsl:text> [</xsl:text>
          <fo:basic-link external-destination="{$ulink.url}">
            <xsl:call-template name="hyphenate-url">
              <xsl:with-param name="url" select="$url"/>
            </xsl:call-template>
          </fo:basic-link>
          <xsl:text>]</xsl:text>
        </fo:inline>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>

</xsl:template>

<xsl:template name="ulink.footnote.number">
  <fo:inline xsl:use-attribute-sets="footnote.mark.properties">
    <xsl:choose>
      <xsl:when test="$fop.extensions != 0">
        <xsl:attribute name="vertical-align">super</xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="baseline-shift">super</xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:variable name="fnum">
      <!-- * Determine the footnote number to display for this hyperlink, -->
      <!-- * by counting all foonotes, ulinks, and any elements that have -->
      <!-- * an xlink:href attribute that meets the following criteria: -->
      <!-- * -->
      <!-- * - the content of the element is not a URI that is the same -->
      <!-- *   URI as the value of the href attribute -->
      <!-- * - the href attribute is not an internal ID reference (does -->
      <!-- *   not start with a hash sign) -->
      <!-- * - the href is not part of an olink reference (the element -->
      <!-- * - does not have an xlink:role attribute that indicates it is -->
      <!-- *   an olink, and the href does not contain a hash sign) -->
      <!-- * - the element either has no xlink:type attribute or has -->
      <!-- *   an xlink:type attribute whose value is 'simple' -->
      <!-- FIXME: list in @from is probably not complete -->
      <xsl:number level="any" 
                  from="chapter|appendix|preface|article|refentry|bibliography[not(parent::article)]"
                  count="footnote[not(@label)][not(ancestor::tgroup)]
                  |ulink[node()][@url != .][not(ancestor::footnote)]
                  |*[node()][@xlink:href][not(@xlink:href = .)][not(starts-with(@xlink:href,'#'))]
                    [not(contains(@xlink:href,'#') and @xlink:role = $xolink.role)]
                    [not(@xlink:type) or @xlink:type='simple']
                    [not(ancestor::footnote)]"
                  format="1"/>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="string-length($footnote.number.symbols) &gt;= $fnum">
        <xsl:value-of select="substring($footnote.number.symbols, $fnum, 1)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:number value="$fnum" format="{$footnote.number.format}"/>
      </xsl:otherwise>
    </xsl:choose>
  </fo:inline>
</xsl:template>

<xsl:template name="hyphenate-url">
  <xsl:param name="url" select="''"/>
  <xsl:choose>
    <xsl:when test="$ulink.hyphenate = ''">
      <xsl:value-of select="$url"/>
    </xsl:when>
    <xsl:when test="string-length($url) &gt; 1">
      <xsl:variable name="char" select="substring($url, 1, 1)"/>
      <xsl:value-of select="$char"/>
      <xsl:if test="contains($ulink.hyphenate.chars, $char)">
        <!-- Do not hyphen in-between // -->
        <xsl:if test="not($char = '/' and substring($url,2,1) = '/')">
          <xsl:copy-of select="$ulink.hyphenate"/>
        </xsl:if>
      </xsl:if>
      <!-- recurse to the next character -->
      <xsl:call-template name="hyphenate-url">
        <xsl:with-param name="url" select="substring($url, 2)"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$url"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="olink" name="olink">
  <!-- olink content may be passed in from xlink olink -->
  <xsl:param name="content" select="NOTANELEMENT"/>

  <xsl:choose>
    <!-- olinks resolved by stylesheet and target database -->
    <xsl:when test="@targetdoc or @targetptr or
                    (@xlink:role=$xolink.role and
                     contains(@xlink:href, '#') )" >

      <xsl:variable name="targetdoc.att">
        <xsl:choose>
          <xsl:when test="@targetdoc != ''">
            <xsl:value-of select="@targetdoc"/>
          </xsl:when>
          <xsl:when test="@xlink:role=$xolink.role and
                       contains(@xlink:href, '#')" >
            <xsl:value-of select="substring-before(@xlink:href, '#')"/>
          </xsl:when>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="targetptr.att">
        <xsl:choose>
          <xsl:when test="@targetptr != ''">
            <xsl:value-of select="@targetptr"/>
          </xsl:when>
          <xsl:when test="@xlink:role=$xolink.role and
                       contains(@xlink:href, '#')" >
            <xsl:value-of select="substring-after(@xlink:href, '#')"/>
          </xsl:when>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="olink.lang">
        <xsl:call-template name="l10n.language">
          <xsl:with-param name="xref-context" select="true()"/>
        </xsl:call-template>
      </xsl:variable>
    
      <xsl:variable name="target.database.filename">
        <xsl:call-template name="select.target.database">
          <xsl:with-param name="targetdoc.att" select="$targetdoc.att"/>
          <xsl:with-param name="targetptr.att" select="$targetptr.att"/>
          <xsl:with-param name="olink.lang" select="$olink.lang"/>
        </xsl:call-template>
      </xsl:variable>
    
      <xsl:variable name="target.database" 
          select="document($target.database.filename, /)"/>
    
      <xsl:if test="$olink.debug != 0">
        <xsl:message>
          <xsl:text>Olink debug: root element of target.database is '</xsl:text>
          <xsl:value-of select="local-name($target.database/*[1])"/>
          <xsl:text>'.</xsl:text>
        </xsl:message>
      </xsl:if>
    
      <xsl:variable name="olink.key">
        <xsl:call-template name="select.olink.key">
          <xsl:with-param name="targetdoc.att" select="$targetdoc.att"/>
          <xsl:with-param name="targetptr.att" select="$targetptr.att"/>
          <xsl:with-param name="olink.lang" select="$olink.lang"/>
          <xsl:with-param name="target.database" select="$target.database"/>
        </xsl:call-template>
      </xsl:variable>
    
      <xsl:if test="string-length($olink.key) = 0">
        <xsl:message>
          <xsl:text>Error: unresolved olink: </xsl:text>
          <xsl:text>targetdoc/targetptr = '</xsl:text>
          <xsl:value-of select="$targetdoc.att"/>
          <xsl:text>/</xsl:text>
          <xsl:value-of select="$targetptr.att"/>
          <xsl:text>'.</xsl:text>
        </xsl:message>
      </xsl:if>

      <xsl:variable name="href">
        <xsl:call-template name="make.olink.href">
          <xsl:with-param name="olink.key" select="$olink.key"/>
          <xsl:with-param name="target.database" select="$target.database"/>
        </xsl:call-template>
      </xsl:variable>

      <!-- Olink that points to internal id can be a link -->
      <xsl:variable name="linkend">
        <xsl:call-template name="olink.as.linkend">
          <xsl:with-param name="olink.key" select="$olink.key"/>
          <xsl:with-param name="olink.lang" select="$olink.lang"/>
          <xsl:with-param name="target.database" select="$target.database"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:variable name="hottext">
        <xsl:choose>
          <xsl:when test="string-length($content) != 0">
            <xsl:copy-of select="$content"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name="olink.hottext">
              <xsl:with-param name="olink.key" select="$olink.key"/>
              <xsl:with-param name="olink.lang" select="$olink.lang"/>
              <xsl:with-param name="target.database" select="$target.database"/>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="olink.docname.citation">
        <xsl:call-template name="olink.document.citation">
          <xsl:with-param name="olink.key" select="$olink.key"/>
          <xsl:with-param name="target.database" select="$target.database"/>
          <xsl:with-param name="olink.lang" select="$olink.lang"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:variable name="olink.page.citation">
        <xsl:call-template name="olink.page.citation">
          <xsl:with-param name="olink.key" select="$olink.key"/>
          <xsl:with-param name="target.database" select="$target.database"/>
          <xsl:with-param name="olink.lang" select="$olink.lang"/>
          <xsl:with-param name="linkend" select="$linkend"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:choose>
        <xsl:when test="$linkend != ''">
          <fo:basic-link internal-destination="{$linkend}"
                       xsl:use-attribute-sets="xref.properties">
            <xsl:call-template name="anchor"/>
            <xsl:copy-of select="$hottext"/>
            <xsl:copy-of select="$olink.page.citation"/>
          </fo:basic-link>
        </xsl:when>
        <xsl:when test="$href != ''">
          <xsl:choose>
            <xsl:when test="$fop1.extensions != 0">
              <xsl:variable name="mybeg" select="substring-before($href,'#')"/>
              <xsl:variable name="myend" select="substring-after($href,'#')"/>
              <fo:basic-link external-destination="url({concat($mybeg,'#dest=',$myend)})"
                             xsl:use-attribute-sets="olink.properties">
                <xsl:copy-of select="$hottext"/>
              </fo:basic-link>
              <xsl:copy-of select="$olink.page.citation"/>
              <xsl:copy-of select="$olink.docname.citation"/>
            </xsl:when>
            <xsl:when test="$xep.extensions != 0">
              <fo:basic-link external-destination="url({$href})"
                             xsl:use-attribute-sets="olink.properties">
                <xsl:call-template name="anchor"/>
                <xsl:copy-of select="$hottext"/>
              </fo:basic-link>
              <xsl:copy-of select="$olink.page.citation"/>
              <xsl:copy-of select="$olink.docname.citation"/>
            </xsl:when>
            <xsl:when test="$axf.extensions != 0">
              <fo:basic-link external-destination="{$href}"
                             xsl:use-attribute-sets="olink.properties">
                <xsl:copy-of select="$hottext"/>
              </fo:basic-link>
              <xsl:copy-of select="$olink.page.citation"/>
              <xsl:copy-of select="$olink.docname.citation"/>
            </xsl:when>
            <xsl:otherwise>
              <fo:basic-link external-destination="{$href}"
                             xsl:use-attribute-sets="olink.properties">
                <xsl:copy-of select="$hottext"/>
              </fo:basic-link>
              <xsl:copy-of select="$olink.page.citation"/>
              <xsl:copy-of select="$olink.docname.citation"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
          <xsl:copy-of select="$hottext"/>
          <xsl:copy-of select="$olink.page.citation"/>
          <xsl:copy-of select="$olink.docname.citation"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>

    <!-- olink never implemented in FO for old olink entity syntax -->
    <xsl:otherwise>
      <xsl:apply-templates/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*" mode="insert.olink.docname.markup">
  <xsl:param name="docname" select="''"/>
  
  <fo:inline font-style="italic">
    <xsl:value-of select="$docname"/>
  </fo:inline>

</xsl:template>

<!-- This prevents error message when processing olinks with xrefstyle -->
<xsl:template match="olink" mode="object.xref.template"/>


<xsl:template name="olink.as.linkend">
  <xsl:param name="olink.key" select="''"/>
  <xsl:param name="olink.lang" select="''"/>
  <xsl:param name="target.database" select="NotANode"/>

  <xsl:variable name="targetdoc">
    <xsl:value-of select="substring-before($olink.key, '/')"/>
  </xsl:variable>

  <xsl:variable name="targetptr">
    <xsl:value-of 
       select="substring-before(substring-after($olink.key, '/'), '/')"/>
  </xsl:variable>

  <xsl:variable name="target.lang">
    <xsl:variable name="candidate">
      <xsl:for-each select="$target.database" >
        <xsl:value-of 
                  select="key('targetptr-key', $olink.key)[1]/@lang" />
      </xsl:for-each>
    </xsl:variable>
    <xsl:choose>
      <xsl:when test="$candidate != ''">
        <xsl:value-of select="$candidate"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$olink.lang"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="$current.docid = $targetdoc and 
                $olink.lang = $target.lang">
    <xsl:variable name="targets" select="key('id',$targetptr)"/>
    <xsl:variable name="target" select="$targets[1]"/>
    <xsl:if test="$target">
      <xsl:value-of select="$targetptr"/>
    </xsl:if>
  </xsl:if>

</xsl:template>


<!-- ==================================================================== -->

<xsl:template name="title.xref">
  <xsl:param name="target" select="."/>
  <xsl:choose>
    <xsl:when test="local-name($target) = 'figure'
                    or local-name($target) = 'example'
                    or local-name($target) = 'equation'
                    or local-name($target) = 'table'
                    or local-name($target) = 'dedication'
                    or local-name($target) = 'acknowledgements'
                    or local-name($target) = 'preface'
                    or local-name($target) = 'bibliography'
                    or local-name($target) = 'glossary'
                    or local-name($target) = 'index'
                    or local-name($target) = 'setindex'
                    or local-name($target) = 'colophon'">
      <xsl:call-template name="gentext.startquote"/>
      <xsl:apply-templates select="$target" mode="title.markup"/>
      <xsl:call-template name="gentext.endquote"/>
    </xsl:when>
    <xsl:otherwise>
      <fo:inline font-style="italic">
        <xsl:apply-templates select="$target" mode="title.markup"/>
      </fo:inline>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="number.xref">
  <xsl:param name="target" select="."/>
  <xsl:apply-templates select="$target" mode="label.markup"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="xref.xreflabel">
  <!-- called to process an xreflabel...you might use this to make  -->
  <!-- xreflabels come out in the right font for different targets, -->
  <!-- for example. -->
  <xsl:param name="target" select="."/>
  <xsl:value-of select="$target/@xreflabel"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="title" mode="xref">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="command" mode="xref">
  <xsl:call-template name="inline.boldseq"/>
</xsl:template>

<xsl:template match="function" mode="xref">
  <xsl:call-template name="inline.monoseq"/>
</xsl:template>

<xsl:template match="*" mode="page.citation">
  <xsl:param name="id" select="'???'"/>

  <fo:basic-link internal-destination="{$id}"
                 xsl:use-attribute-sets="xref.properties">
    <fo:inline keep-together.within-line="always">
      <xsl:call-template name="substitute-markup">
        <xsl:with-param name="template">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="name" select="'page.citation'"/>
            <xsl:with-param name="context" select="'xref'"/>
          </xsl:call-template>
        </xsl:with-param>
      </xsl:call-template>
    </fo:inline>
  </fo:basic-link>
</xsl:template>

<xsl:template match="*" mode="pagenumber.markup">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <fo:page-number-citation ref-id="{$id}"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*" mode="insert.title.markup">
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="title"/>

  <xsl:choose>
    <xsl:when test="$purpose = 'xref'">
      <xsl:copy-of select="$title"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$title"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="chapter|appendix" mode="insert.title.markup">
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="title"/>

  <xsl:choose>
    <xsl:when test="$purpose = 'xref'">
      <fo:inline font-style="italic">
        <xsl:copy-of select="$title"/>
      </fo:inline>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$title"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*" mode="insert.subtitle.markup">
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="subtitle"/>

  <xsl:copy-of select="$subtitle"/>
</xsl:template>

<xsl:template match="*" mode="insert.label.markup">
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="label"/>

  <xsl:copy-of select="$label"/>
</xsl:template>

<xsl:template match="*" mode="insert.pagenumber.markup">
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="pagenumber"/>

  <xsl:copy-of select="$pagenumber"/>
</xsl:template>

<xsl:template match="*" mode="insert.direction.markup">
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="direction"/>

  <xsl:copy-of select="$direction"/>
</xsl:template>

<xsl:template match="olink" mode="pagenumber.markup">
  <!-- Local olinks can use page-citation -->
  <xsl:variable name="targetdoc.att" select="@targetdoc"/>
  <xsl:variable name="targetptr.att" select="@targetptr"/>

  <xsl:variable name="olink.lang">
    <xsl:call-template name="l10n.language">
      <xsl:with-param name="xref-context" select="true()"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="target.database.filename">
    <xsl:call-template name="select.target.database">
      <xsl:with-param name="targetdoc.att" select="$targetdoc.att"/>
      <xsl:with-param name="targetptr.att" select="$targetptr.att"/>
      <xsl:with-param name="olink.lang" select="$olink.lang"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="target.database" 
      select="document($target.database.filename, /)"/>

  <xsl:if test="$olink.debug != 0">
    <xsl:message>
      <xsl:text>Olink debug: root element of target.database is '</xsl:text>
      <xsl:value-of select="local-name($target.database/*[1])"/>
      <xsl:text>'.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:variable name="olink.key">
    <xsl:call-template name="select.olink.key">
      <xsl:with-param name="targetdoc.att" select="$targetdoc.att"/>
      <xsl:with-param name="targetptr.att" select="$targetptr.att"/>
      <xsl:with-param name="olink.lang" select="$olink.lang"/>
      <xsl:with-param name="target.database" select="$target.database"/>
    </xsl:call-template>
  </xsl:variable>

  <!-- Olink that points to internal id can be a link -->
  <xsl:variable name="linkend">
    <xsl:call-template name="olink.as.linkend">
      <xsl:with-param name="olink.key" select="$olink.key"/>
      <xsl:with-param name="olink.lang" select="$olink.lang"/>
      <xsl:with-param name="target.database" select="$target.database"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$linkend != ''">
      <fo:page-number-citation ref-id="{$linkend}"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message>
        <xsl:text>Olink error: no page number linkend for local olink '</xsl:text>
        <xsl:value-of select="$olink.key"/>
        <xsl:text>'</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
