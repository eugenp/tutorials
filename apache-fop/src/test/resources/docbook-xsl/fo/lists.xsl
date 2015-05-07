<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version='1.0'>

<!-- ********************************************************************
     $Id: lists.xsl 9668 2012-11-28 00:47:59Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="itemizedlist">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:variable name="pi-label-width">
    <xsl:call-template name="pi.dbfo_label-width"/>
  </xsl:variable>

  <xsl:variable name="label-width">
    <xsl:choose>
      <xsl:when test="$pi-label-width = ''">
        <xsl:value-of select="$itemizedlist.label.width"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$pi-label-width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="title">
    <xsl:apply-templates select="title" mode="list.title.mode"/>
  </xsl:if>

  <!-- Preserve order of PIs and comments -->
  <xsl:apply-templates 
      select="*[not(self::listitem
                or self::title
                or self::titleabbrev)]
              |comment()[not(preceding-sibling::listitem)]
              |processing-instruction()[not(preceding-sibling::listitem)]"/>

  <xsl:variable name="content">
    <xsl:apply-templates 
          select="listitem
                  |comment()[preceding-sibling::listitem]
                  |processing-instruction()[preceding-sibling::listitem]"/>
  </xsl:variable>

  <!-- nested lists don't add extra list-block spacing -->
  <xsl:choose>
    <xsl:when test="ancestor::listitem">
      <fo:list-block id="{$id}" xsl:use-attribute-sets="itemizedlist.properties">
        <xsl:attribute name="provisional-distance-between-starts">
          <xsl:value-of select="$label-width"/>
        </xsl:attribute>
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$content"/>
      </fo:list-block>
    </xsl:when>
    <xsl:otherwise>
      <fo:list-block id="{$id}" xsl:use-attribute-sets="list.block.spacing itemizedlist.properties">
        <xsl:attribute name="provisional-distance-between-starts">
          <xsl:value-of select="$label-width"/>
        </xsl:attribute>
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$content"/>
      </fo:list-block>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<xsl:template match="itemizedlist/title|orderedlist/title">
  <!--nop-->
</xsl:template>

<xsl:template match="variablelist/title" mode="vl.as.list">
  <!--nop-->
</xsl:template>

<xsl:template match="variablelist/title" mode="vl.as.blocks">
  <!--nop-->
</xsl:template>

<xsl:template match="itemizedlist/titleabbrev|orderedlist/titleabbrev">
  <!--nop-->
</xsl:template>

<xsl:template match="procedure/titleabbrev">
  <!--nop-->
</xsl:template>

<xsl:template match="variablelist/titleabbrev" mode="vl.as.list">
  <!--nop-->
</xsl:template>

<xsl:template match="variablelist/titleabbrev" mode="vl.as.blocks">
  <!--nop-->
</xsl:template>

<xsl:template match="itemizedlist/listitem">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:variable name="item.contents">
    <fo:list-item-label end-indent="label-end()" xsl:use-attribute-sets="itemizedlist.label.properties">
      <fo:block>
        <xsl:call-template name="itemizedlist.label.markup">
          <xsl:with-param name="itemsymbol">
            <xsl:call-template name="list.itemsymbol">
              <xsl:with-param name="node" select="parent::itemizedlist"/>
            </xsl:call-template>
          </xsl:with-param>
        </xsl:call-template>
      </fo:block>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates/>
      </fo:block>
    </fo:list-item-body>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="parent::*/@spacing = 'compact'">
      <fo:list-item id="{$id}" xsl:use-attribute-sets="compact.list.item.spacing">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$item.contents"/>
      </fo:list-item>
    </xsl:when>
    <xsl:otherwise>
      <fo:list-item id="{$id}" xsl:use-attribute-sets="list.item.spacing">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$item.contents"/>
      </fo:list-item>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="itemizedlist.label.markup">
  <xsl:param name="itemsymbol" select="'disc'"/>

  <xsl:choose>
    <xsl:when test="$itemsymbol='none'"></xsl:when>
    <xsl:when test="$itemsymbol='disc'">&#x2022;</xsl:when>
    <xsl:when test="$itemsymbol='bullet'">&#x2022;</xsl:when>
    <xsl:when test="$itemsymbol='endash'">&#x2013;</xsl:when>
    <xsl:when test="$itemsymbol='emdash'">&#x2014;</xsl:when>
    <!-- Some of these may work in your XSL-FO processor and fonts -->
    <!--
    <xsl:when test="$itemsymbol='square'">&#x25A0;</xsl:when>
    <xsl:when test="$itemsymbol='box'">&#x25A0;</xsl:when>
    <xsl:when test="$itemsymbol='smallblacksquare'">&#x25AA;</xsl:when>
    <xsl:when test="$itemsymbol='circle'">&#x25CB;</xsl:when>
    <xsl:when test="$itemsymbol='opencircle'">&#x25CB;</xsl:when>
    <xsl:when test="$itemsymbol='whitesquare'">&#x25A1;</xsl:when>
    <xsl:when test="$itemsymbol='smallwhitesquare'">&#x25AB;</xsl:when>
    <xsl:when test="$itemsymbol='round'">&#x25CF;</xsl:when>
    <xsl:when test="$itemsymbol='blackcircle'">&#x25CF;</xsl:when>
    <xsl:when test="$itemsymbol='whitebullet'">&#x25E6;</xsl:when>
    <xsl:when test="$itemsymbol='triangle'">&#x2023;</xsl:when>
    <xsl:when test="$itemsymbol='point'">&#x203A;</xsl:when>
    <xsl:when test="$itemsymbol='hand'"><fo:inline 
                         font-family="Wingdings 2">A</fo:inline></xsl:when>
    -->
    <xsl:otherwise>&#x2022;</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="orderedlist">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="pi-label-width">
    <xsl:call-template name="pi.dbfo_label-width"/>
  </xsl:variable>

  <xsl:variable name="label-width">
    <xsl:choose>
      <xsl:when test="$pi-label-width = ''">
        <xsl:value-of select="$orderedlist.label.width"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$pi-label-width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:if test="title">
    <xsl:apply-templates select="title" mode="list.title.mode"/>
  </xsl:if>

  <!-- Preserve order of PIs and comments -->
  <xsl:apply-templates 
      select="*[not(self::listitem
                or self::title
                or self::titleabbrev)]
              |comment()[not(preceding-sibling::listitem)]
              |processing-instruction()[not(preceding-sibling::listitem)]"/>

  <xsl:variable name="content">
    <xsl:apply-templates 
          select="listitem
                  |comment()[preceding-sibling::listitem]
                  |processing-instruction()[preceding-sibling::listitem]"/>
  </xsl:variable>

  <!-- nested lists don't add extra list-block spacing -->
  <xsl:choose>
    <xsl:when test="ancestor::listitem">
      <fo:list-block id="{$id}" xsl:use-attribute-sets="orderedlist.properties">
        <xsl:attribute name="provisional-distance-between-starts">
          <xsl:value-of select="$label-width"/>
        </xsl:attribute>
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$content"/>
      </fo:list-block>
    </xsl:when>
    <xsl:otherwise>
      <fo:list-block id="{$id}" xsl:use-attribute-sets="list.block.spacing orderedlist.properties">
        <xsl:attribute name="provisional-distance-between-starts">
          <xsl:value-of select="$label-width"/>
        </xsl:attribute>
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$content"/>
      </fo:list-block>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="orderedlist/listitem">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:variable name="item.contents">
    <fo:list-item-label end-indent="label-end()" xsl:use-attribute-sets="orderedlist.label.properties">
      <fo:block>
        <xsl:apply-templates select="." mode="item-number"/>
      </fo:block>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates/>
      </fo:block>
    </fo:list-item-body>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="parent::*/@spacing = 'compact'">
      <fo:list-item id="{$id}" xsl:use-attribute-sets="compact.list.item.spacing">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$item.contents"/>
      </fo:list-item>
    </xsl:when>
    <xsl:otherwise>
      <fo:list-item id="{$id}" xsl:use-attribute-sets="list.item.spacing">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$item.contents"/>
      </fo:list-item>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="listitem/*[1][local-name()='para' or 
                                   local-name()='simpara' or 
                                   local-name()='formalpara']
                     |glossdef/*[1][local-name()='para' or 
                                   local-name()='simpara' or 
                                   local-name()='formalpara']
                     |step/*[1][local-name()='para' or 
                                   local-name()='simpara' or 
                                   local-name()='formalpara']
                     |callout/*[1][local-name()='para' or 
                                   local-name()='simpara' or 
                                   local-name()='formalpara']"
              priority="2">
  <fo:block>
    <xsl:call-template name="anchor"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="variablelist">
  <xsl:variable name="presentation">
    <xsl:call-template name="pi.dbfo_list-presentation"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$presentation = 'list'">
      <xsl:apply-templates select="." mode="vl.as.list"/>
    </xsl:when>
    <xsl:when test="$presentation = 'blocks'">
      <xsl:apply-templates select="." mode="vl.as.blocks"/>
    </xsl:when>
    <xsl:when test="$variablelist.as.blocks != 0">
      <xsl:apply-templates select="." mode="vl.as.blocks"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="." mode="vl.as.list"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="variablelist" mode="vl.as.list">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:variable name="term-width">
    <xsl:call-template name="pi.dbfo_term-width"/>
  </xsl:variable>

  <xsl:variable name="termlength">
    <xsl:choose>
      <xsl:when test="$term-width != ''">
        <xsl:value-of select="$term-width"/>
      </xsl:when>
      <xsl:when test="@termlength">
        <xsl:variable name="termlength.is.number">
          <xsl:value-of select="@termlength + 0"/>
        </xsl:variable>
        <xsl:choose>
          <xsl:when test="string($termlength.is.number) = 'NaN'">
            <!-- if the term length isn't just a number, assume it's a measurement -->
            <xsl:value-of select="@termlength"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="@termlength"/>
            <xsl:text>em * 0.60</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="longest.term">
          <xsl:with-param name="terms" select="varlistentry/term"/>
          <xsl:with-param name="maxlength" select="$variablelist.max.termlength"/>
        </xsl:call-template>
        <xsl:text>em * 0.60</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

<!--
  <xsl:message>
    <xsl:text>term width: </xsl:text>
    <xsl:value-of select="$termlength"/>
  </xsl:message>
-->

  <xsl:variable name="label-separation">1em</xsl:variable>
  <xsl:variable name="distance-between-starts">
    <xsl:value-of select="$termlength"/>
    <xsl:text>+</xsl:text>
    <xsl:value-of select="$label-separation"/>
  </xsl:variable>

  <xsl:if test="title">
    <xsl:apply-templates select="title" mode="list.title.mode"/>
  </xsl:if>

  <!-- Preserve order of PIs and comments -->
  <xsl:apply-templates 
    select="*[not(self::varlistentry
              or self::title
              or self::titleabbrev)]
            |comment()[not(preceding-sibling::varlistentry)]
            |processing-instruction()[not(preceding-sibling::varlistentry)]"/>

  <xsl:variable name="content">
    <xsl:apply-templates mode="vl.as.list"
      select="varlistentry
              |comment()[preceding-sibling::varlistentry]
              |processing-instruction()[preceding-sibling::varlistentry]"/>
  </xsl:variable>

  <!-- nested lists don't add extra list-block spacing -->
  <xsl:choose>
    <xsl:when test="ancestor::listitem">
      <fo:list-block id="{$id}"
                     provisional-distance-between-starts=
                        "{$distance-between-starts}"
                     provisional-label-separation="{$label-separation}">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$content"/>
      </fo:list-block>
    </xsl:when>
    <xsl:otherwise>
      <fo:list-block id="{$id}"
                     provisional-distance-between-starts=
                        "{$distance-between-starts}"
                     provisional-label-separation="{$label-separation}"
                     xsl:use-attribute-sets="list.block.spacing">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$content"/>
      </fo:list-block>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="longest.term">
  <xsl:param name="longest" select="0"/>
  <xsl:param name="terms" select="."/>
  <xsl:param name="maxlength" select="-1"/>

  <!-- Process out any indexterms in the term -->
  <xsl:variable name="term.text">
    <xsl:apply-templates select="$terms[1]"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$longest &gt; $maxlength and $maxlength &gt; 0">
      <xsl:value-of select="$maxlength"/>
    </xsl:when>
    <xsl:when test="not($terms)">
      <xsl:value-of select="$longest"/>
    </xsl:when>
    <xsl:when test="string-length($term.text) &gt; $longest">
      <xsl:call-template name="longest.term">
        <xsl:with-param name="longest" 
            select="string-length($term.text)"/>
        <xsl:with-param name="maxlength" select="$maxlength"/>
        <xsl:with-param name="terms" select="$terms[position() &gt; 1]"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="longest.term">
        <xsl:with-param name="longest" select="$longest"/>
        <xsl:with-param name="maxlength" select="$maxlength"/>
        <xsl:with-param name="terms" select="$terms[position() &gt; 1]"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="varlistentry" mode="vl.as.list">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:variable name="item.contents">
    <fo:list-item-label end-indent="label-end()" text-align="start">
      <fo:block xsl:use-attribute-sets="variablelist.term.properties">
        <xsl:apply-templates select="term"/>
      </fo:block>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates select="listitem"/>
      </fo:block>
    </fo:list-item-body>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="parent::*/@spacing = 'compact'">
      <fo:list-item id="{$id}"
          xsl:use-attribute-sets="compact.list.item.spacing">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$item.contents"/>
      </fo:list-item>
    </xsl:when>
    <xsl:otherwise>
      <fo:list-item id="{$id}" xsl:use-attribute-sets="list.item.spacing">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$item.contents"/>
      </fo:list-item>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>


<xsl:template match="variablelist" mode="vl.as.blocks">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <!-- termlength is irrelevant -->

  <xsl:if test="title">
    <xsl:apply-templates select="title" mode="list.title.mode"/>
  </xsl:if>

  <!-- Preserve order of PIs and comments -->
  <xsl:apply-templates 
    select="*[not(self::varlistentry
              or self::title
              or self::titleabbrev)]
            |comment()[not(preceding-sibling::varlistentry)]
            |processing-instruction()[not(preceding-sibling::varlistentry)]"/>

  <xsl:variable name="content">
    <xsl:apply-templates mode="vl.as.blocks"
      select="varlistentry
              |comment()[preceding-sibling::varlistentry]
              |processing-instruction()[preceding-sibling::varlistentry]"/>
  </xsl:variable>

  <!-- nested lists don't add extra list-block spacing -->
  <xsl:choose>
    <xsl:when test="ancestor::listitem">
      <fo:block id="{$id}">
        <xsl:copy-of select="$content"/>
      </fo:block>
    </xsl:when>
    <xsl:otherwise>
      <fo:block id="{$id}" xsl:use-attribute-sets="list.block.spacing">
        <xsl:copy-of select="$content"/>
      </fo:block>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="varlistentry" mode="vl.as.blocks">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <fo:block id="{$id}" xsl:use-attribute-sets="variablelist.term.properties
                                               list.item.spacing"  
      keep-together.within-column="always" 
      keep-with-next.within-column="always">
    <xsl:apply-templates select="term"/>
  </fo:block>

  <fo:block>
    <xsl:attribute name="margin-{$direction.align.start}">0.25in</xsl:attribute>
    <xsl:apply-templates select="listitem"/>
  </fo:block>
</xsl:template>

<xsl:template match="varlistentry/term">
  <fo:inline>
    <xsl:call-template name="simple.xlink">
      <xsl:with-param name="content">
        <xsl:apply-templates/>
      </xsl:with-param>
    </xsl:call-template>
  </fo:inline>
  <xsl:choose>
    <xsl:when test="not(following-sibling::term)"/> <!-- do nothing -->
    <xsl:otherwise>
      <!-- * if we have multiple terms in the same varlistentry, generate -->
      <!-- * a separator (", " by default) and/or an additional line -->
      <!-- * break after each one except the last -->
      <fo:inline><xsl:value-of select="$variablelist.term.separator"/></fo:inline>
      <xsl:if test="not($variablelist.term.break.after = '0')">
        <fo:block/>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="varlistentry/listitem">
  <xsl:apply-templates/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="title" mode="list.title.mode">
  <xsl:call-template name="formal.object.heading">
    <xsl:with-param name="object" select=".."/>
  </xsl:call-template>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="simplelist|simplelist[@type='vert']">
  <!-- with no type specified, the default is 'vert' -->

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="explicit.table.width">
    <xsl:call-template name="dbfo-attribute">
      <xsl:with-param name="pis"
                      select="processing-instruction('dbfo')"/>
      <xsl:with-param name="attribute" select="'list-width'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="table.width">
    <xsl:choose>
      <xsl:when test="$explicit.table.width != ''">
        <xsl:value-of select="$explicit.table.width"/>
      </xsl:when>
      <xsl:when test="$default.table.width = ''">
        <xsl:text>100%</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$default.table.width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:table id="{$id}" xsl:use-attribute-sets="normal.para.spacing">

    <xsl:choose>
      <xsl:when test="$axf.extensions != 0 or $xep.extensions != 0">
        <xsl:attribute name="table-layout">auto</xsl:attribute>
        <xsl:if test="$explicit.table.width != ''">
          <xsl:attribute name="width"><xsl:value-of 
          select="$explicit.table.width"/></xsl:attribute>
        </xsl:if>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="table-layout">fixed</xsl:attribute>
        <xsl:attribute name="width"><xsl:value-of 
                                      select="$table.width"/></xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:call-template name="simplelist.table.columns">
      <xsl:with-param name="cols">
        <xsl:choose>
          <xsl:when test="@columns">
            <xsl:value-of select="@columns"/>
          </xsl:when>
          <xsl:otherwise>1</xsl:otherwise>
        </xsl:choose>
      </xsl:with-param>
    </xsl:call-template>
    <fo:table-body start-indent="0pt" end-indent="0pt">
      <xsl:call-template name="simplelist.vert">
        <xsl:with-param name="cols">
          <xsl:choose>
            <xsl:when test="@columns">
              <xsl:value-of select="@columns"/>
            </xsl:when>
            <xsl:otherwise>1</xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
      </xsl:call-template>
    </fo:table-body>
  </fo:table>
</xsl:template>

<xsl:template match="simplelist[@type='inline']">
  <!-- if dbchoice PI exists, use that to determine the choice separator -->
  <!-- (that is, equivalent of "and" or "or" in current locale), or literal -->
  <!-- value of "choice" otherwise -->
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <fo:inline id="{$id}"><xsl:variable name="localized-choice-separator">
    <xsl:choose>
      <xsl:when test="processing-instruction('dbchoice')">
        <xsl:call-template name="select.choice.separator"/>
      </xsl:when>
      <xsl:otherwise>
        <!-- empty -->
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:for-each select="member">
    <xsl:apply-templates/>
    <xsl:choose>
      <xsl:when test="position() = last()"/> <!-- do nothing -->
      <xsl:otherwise>
        <xsl:text>, </xsl:text>
        <xsl:if test="position() = last() - 1">
          <xsl:if test="$localized-choice-separator != ''">
            <xsl:value-of select="$localized-choice-separator"/>
            <xsl:text> </xsl:text>
          </xsl:if>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:for-each></fo:inline>
</xsl:template>

<xsl:template match="simplelist[@type='horiz']">

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="explicit.table.width">
    <xsl:call-template name="pi.dbfo_list-width"/>
  </xsl:variable>

  <xsl:variable name="table.width">
    <xsl:choose>
      <xsl:when test="$explicit.table.width != ''">
        <xsl:value-of select="$explicit.table.width"/>
      </xsl:when>
      <xsl:when test="$default.table.width = ''">
        <xsl:text>100%</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$default.table.width"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <fo:table id="{$id}" xsl:use-attribute-sets="normal.para.spacing">
    <xsl:choose>
      <xsl:when test="$axf.extensions != 0 or $xep.extensions != 0">
        <xsl:attribute name="table-layout">auto</xsl:attribute>
        <xsl:if test="$explicit.table.width != ''">
          <xsl:attribute name="width"><xsl:value-of 
                             select="$explicit.table.width"/></xsl:attribute>
        </xsl:if>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="table-layout">fixed</xsl:attribute>
        <xsl:attribute name="width"><xsl:value-of 
                                      select="$table.width"/></xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:call-template name="simplelist.table.columns">
      <xsl:with-param name="cols">
        <xsl:choose>
          <xsl:when test="@columns">
            <xsl:value-of select="@columns"/>
          </xsl:when>
          <xsl:otherwise>1</xsl:otherwise>
        </xsl:choose>
      </xsl:with-param>
    </xsl:call-template>
    <fo:table-body start-indent="0pt" end-indent="0pt">
      <xsl:call-template name="simplelist.horiz">
        <xsl:with-param name="cols">
          <xsl:choose>
            <xsl:when test="@columns">
              <xsl:value-of select="@columns"/>
            </xsl:when>
            <xsl:otherwise>1</xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
      </xsl:call-template>
    </fo:table-body>
  </fo:table>
</xsl:template>

<xsl:template name="simplelist.table.columns">
  <xsl:param name="cols" select="1"/>
  <xsl:param name="curcol" select="1"/>
  <fo:table-column column-number="{$curcol}"
                   column-width="proportional-column-width(1)"/>
  <xsl:if test="$curcol &lt; $cols">
    <xsl:call-template name="simplelist.table.columns">
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="curcol" select="$curcol + 1"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="simplelist.horiz">
  <xsl:param name="cols">1</xsl:param>
  <xsl:param name="cell">1</xsl:param>
  <xsl:param name="members" select="./member"/>

  <xsl:if test="$cell &lt;= count($members)">
    <fo:table-row>
      <xsl:call-template name="simplelist.horiz.row">
        <xsl:with-param name="cols" select="$cols"/>
        <xsl:with-param name="cell" select="$cell"/>
        <xsl:with-param name="members" select="$members"/>
      </xsl:call-template>
   </fo:table-row>
    <xsl:call-template name="simplelist.horiz">
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="cell" select="$cell + $cols"/>
      <xsl:with-param name="members" select="$members"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="simplelist.horiz.row">
  <xsl:param name="cols">1</xsl:param>
  <xsl:param name="cell">1</xsl:param>
  <xsl:param name="members" select="./member"/>
  <xsl:param name="curcol">1</xsl:param>

  <xsl:if test="$curcol &lt;= $cols">
    <fo:table-cell>
      <fo:block>
        <xsl:if test="$members[position()=$cell]">
          <xsl:apply-templates select="$members[position()=$cell]"/>
        </xsl:if>
      </fo:block>
    </fo:table-cell>
    <xsl:call-template name="simplelist.horiz.row">
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="cell" select="$cell+1"/>
      <xsl:with-param name="members" select="$members"/>
      <xsl:with-param name="curcol" select="$curcol+1"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="simplelist.vert">
  <xsl:param name="cols">1</xsl:param>
  <xsl:param name="cell">1</xsl:param>
  <xsl:param name="members" select="./member"/>
  <xsl:param name="rows"
             select="floor((count($members)+$cols - 1) div $cols)"/>

  <xsl:if test="$cell &lt;= $rows">
    <fo:table-row>
      <xsl:call-template name="simplelist.vert.row">
        <xsl:with-param name="cols" select="$cols"/>
        <xsl:with-param name="rows" select="$rows"/>
        <xsl:with-param name="cell" select="$cell"/>
        <xsl:with-param name="members" select="$members"/>
      </xsl:call-template>
   </fo:table-row>
    <xsl:call-template name="simplelist.vert">
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="cell" select="$cell+1"/>
      <xsl:with-param name="members" select="$members"/>
      <xsl:with-param name="rows" select="$rows"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="simplelist.vert.row">
  <xsl:param name="cols">1</xsl:param>
  <xsl:param name="rows">1</xsl:param>
  <xsl:param name="cell">1</xsl:param>
  <xsl:param name="members" select="./member"/>
  <xsl:param name="curcol">1</xsl:param>

  <xsl:if test="$curcol &lt;= $cols">
    <fo:table-cell>
      <fo:block>
        <xsl:if test="$members[position()=$cell]">
          <xsl:apply-templates select="$members[position()=$cell]"/>
        </xsl:if>
      </fo:block>
    </fo:table-cell>
    <xsl:call-template name="simplelist.vert.row">
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="rows" select="$rows"/>
      <xsl:with-param name="cell" select="$cell+$rows"/>
      <xsl:with-param name="members" select="$members"/>
      <xsl:with-param name="curcol" select="$curcol+1"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template match="member">
  <xsl:call-template name="simple.xlink">
    <xsl:with-param name="content">
      <xsl:apply-templates/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="procedure">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

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

  <!-- Preserve order of PIs and comments -->
  <xsl:variable name="preamble"
        select="*[not(self::step
                  or self::title
                  or self::titleabbrev)]
                |comment()[not(preceding-sibling::step)]
                |processing-instruction()[not(preceding-sibling::step)]"/>

  <xsl:variable name="steps" 
                select="step
                        |comment()[preceding-sibling::step]
                        |processing-instruction()[preceding-sibling::step]"/>

  <fo:block id="{$id}" xsl:use-attribute-sets="procedure.properties list.block.spacing">
    <xsl:if test="./title and $placement = 'before'">
      <!-- n.b. gentext code tests for $formal.procedures and may make an "informal" -->
      <!-- heading even though we called formal.object.heading. odd but true. -->
      <xsl:call-template name="formal.object.heading"/>
    </xsl:if>

    <xsl:apply-templates select="$preamble"/>

    <fo:list-block xsl:use-attribute-sets="list.block.spacing"
                   provisional-distance-between-starts="2em"
                   provisional-label-separation="0.2em">
      <xsl:apply-templates select="$steps"/>
    </fo:list-block>

    <xsl:if test="./title and $placement != 'before'">
      <!-- n.b. gentext code tests for $formal.procedures and may make an "informal" -->
      <!-- heading even though we called formal.object.heading. odd but true. -->
      <xsl:call-template name="formal.object.heading"/>
    </xsl:if>
  </fo:block>
</xsl:template>

<xsl:template match="procedure/title">
</xsl:template>

<xsl:template match="substeps">
  <fo:list-block xsl:use-attribute-sets="list.block.spacing"
                 provisional-distance-between-starts="2em"
                 provisional-label-separation="0.2em">
    <xsl:apply-templates/>
  </fo:list-block>
</xsl:template>

<xsl:template match="procedure/step|substeps/step">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <fo:list-item xsl:use-attribute-sets="list.item.spacing">
    <xsl:if test="$keep.together != ''">
      <xsl:attribute name="keep-together.within-column"><xsl:value-of
                      select="$keep.together"/></xsl:attribute>
    </xsl:if>
    <fo:list-item-label end-indent="label-end()">
      <fo:block id="{$id}">
        <!-- dwc: fix for one step procedures. Use a bullet if there's no step 2 -->
        <xsl:choose>
          <xsl:when test="count(../step) = 1">
            <xsl:text>&#x2022;</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="." mode="number">
              <xsl:with-param name="recursive" select="0"/>
            </xsl:apply-templates>.
          </xsl:otherwise>
        </xsl:choose>
      </fo:block>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates/>
      </fo:block>
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<xsl:template match="stepalternatives">
  <fo:list-block provisional-distance-between-starts="2em"
                 provisional-label-separation="0.2em">
    <xsl:apply-templates select="step"/>
  </fo:list-block>
</xsl:template>

<xsl:template match="stepalternatives/step">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <fo:list-item xsl:use-attribute-sets="list.item.spacing">
    <xsl:if test="$keep.together != ''">
      <xsl:attribute name="keep-together.within-column"><xsl:value-of
                      select="$keep.together"/></xsl:attribute>
    </xsl:if>
    <fo:list-item-label end-indent="label-end()">
      <fo:block id="{$id}">
        <xsl:text>&#x2022;</xsl:text>
      </fo:block>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates/>
      </fo:block>
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<xsl:template match="step/title">
  <fo:block font-weight="bold"
            keep-together.within-column="always" 
            keep-with-next.within-column="always">
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="segmentedlist">
  <xsl:variable name="presentation">
    <xsl:call-template name="pi.dbfo_list-presentation"/>
  </xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$presentation = 'table'">
      <fo:block id="{$id}">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:apply-templates select="." mode="seglist-table"/>
      </fo:block>
    </xsl:when>
    <xsl:when test="$presentation = 'list'">
      <fo:block id="{$id}">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:apply-templates/>
      </fo:block>
    </xsl:when>
    <xsl:when test="$segmentedlist.as.table != 0">
      <fo:block id="{$id}">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>
        <xsl:apply-templates select="." mode="seglist-table"/>
      </fo:block>
    </xsl:when>
    <xsl:otherwise>
      <fo:block id="{$id}">
        <xsl:if test="$keep.together != ''">
          <xsl:attribute name="keep-together.within-column"><xsl:value-of
                          select="$keep.together"/></xsl:attribute>
        </xsl:if>

        <xsl:apply-templates/>
      </fo:block>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="segmentedlist/title">
  <xsl:apply-templates select="." mode="list.title.mode" />
</xsl:template>

<xsl:template match="segtitle">
</xsl:template>

<xsl:template match="segtitle" mode="segtitle-in-seg">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="seglistitem">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <fo:block id="{$id}">
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="seg">
  <xsl:variable name="segnum" select="count(preceding-sibling::seg)+1"/>
  <xsl:variable name="seglist" select="ancestor::segmentedlist"/>
  <xsl:variable name="segtitles" select="$seglist/segtitle"/>

  <!--
     Note: segtitle is only going to be the right thing in a well formed
     SegmentedList.  If there are too many Segs or too few SegTitles,
     you'll get something odd...maybe an error
  -->

  <fo:block>
    <fo:inline font-weight="bold">
      <xsl:apply-templates select="$segtitles[$segnum=position()]"
                           mode="segtitle-in-seg"/>
      <xsl:text>: </xsl:text>
    </fo:inline>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="segmentedlist" mode="seglist-table">
  <xsl:apply-templates select="title" mode="list.title.mode" />
  <fo:table table-layout="fixed">
    <xsl:call-template name="segmentedlist.table.columns">
      <xsl:with-param name="cols" select="count(segtitle)"/>
    </xsl:call-template>
    <fo:table-header start-indent="0pt" end-indent="0pt">
      <fo:table-row>
        <xsl:apply-templates select="segtitle" mode="seglist-table"/>
      </fo:table-row>
    </fo:table-header>
    <fo:table-body start-indent="0pt" end-indent="0pt">
      <xsl:apply-templates select="seglistitem" mode="seglist-table"/>
    </fo:table-body>
  </fo:table>
</xsl:template>

<xsl:template name="segmentedlist.table.columns">
  <xsl:param name="cols" select="1"/>
  <xsl:param name="curcol" select="1"/>

  <fo:table-column column-number="{$curcol}"
                   column-width="proportional-column-width(1)"/>
  <xsl:if test="$curcol &lt; $cols">
    <xsl:call-template name="segmentedlist.table.columns">
      <xsl:with-param name="cols" select="$cols"/>
      <xsl:with-param name="curcol" select="$curcol+1"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template match="segtitle" mode="seglist-table">
  <fo:table-cell>
    <fo:block font-weight="bold">
      <xsl:apply-templates/>
    </fo:block>
  </fo:table-cell>
</xsl:template>

<xsl:template match="seglistitem" mode="seglist-table">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <fo:table-row id="{$id}">
    <xsl:apply-templates mode="seglist-table"/>
  </fo:table-row>
</xsl:template>

<xsl:template match="seg" mode="seglist-table">
  <fo:table-cell>
    <fo:block>
      <xsl:apply-templates/>
    </fo:block>
  </fo:table-cell>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="calloutlist">
  <xsl:variable name="id">
  <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:variable name="pi-label-width">
    <xsl:call-template name="pi.dbfo_label-width"/>
  </xsl:variable>

  <fo:block id="{$id}"
            text-align="{$alignment}">
    <!-- The above restores alignment altered by image align attribute -->
    <xsl:if test="title|info/title">
      <xsl:apply-templates select="(title|info/title)[1]" 
                           mode="list.title.mode"/>
    </xsl:if>

    <!-- Preserve order of PIs and comments -->
    <xsl:apply-templates 
         select="*[not(self::callout or self::title or self::titleabbrev)]
                   |comment()[not(preceding-sibling::callout)]
                   |processing-instruction()[not(preceding-sibling::callout)]"/>

    <fo:list-block xsl:use-attribute-sets="calloutlist.properties">

      <xsl:if test="$pi-label-width != ''">
        <xsl:attribute name="provisional-distance-between-starts">
          <xsl:value-of select="$pi-label-width"/>
        </xsl:attribute>
      </xsl:if>
      
      <xsl:apply-templates select="callout
                                |comment()[preceding-sibling::callout]
                                |processing-instruction()[preceding-sibling::callout]"/>
    </fo:list-block>
  </fo:block>
</xsl:template>

<xsl:template match="calloutlist/title">
</xsl:template>

<xsl:template match="callout">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <xsl:variable name="keep.together">
    <xsl:call-template name="pi.dbfo_keep-together"/>
  </xsl:variable>

  <fo:list-item id="{$id}" xsl:use-attribute-sets="callout.properties">
    <xsl:if test="$keep.together != ''">
      <xsl:attribute name="keep-together.within-column"><xsl:value-of
                      select="$keep.together"/></xsl:attribute>
    </xsl:if>
    <fo:list-item-label end-indent="label-end()">
      <fo:block>
        <xsl:call-template name="callout.arearefs">
          <xsl:with-param name="arearefs" select="@arearefs"/>
        </xsl:call-template>
      </fo:block>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates/>
      </fo:block>
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<xsl:template name="callout.arearefs">
  <xsl:param name="arearefs"></xsl:param>
  <xsl:if test="$arearefs!=''">
    <xsl:choose>
      <xsl:when test="substring-before($arearefs,' ')=''">
        <xsl:call-template name="callout.arearef">
          <xsl:with-param name="arearef" select="$arearefs"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="callout.arearef">
          <xsl:with-param name="arearef"
                          select="substring-before($arearefs,' ')"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:call-template name="callout.arearefs">
      <xsl:with-param name="arearefs"
                      select="substring-after($arearefs,' ')"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="callout.arearef">
  <xsl:param name="arearef"></xsl:param>
  <xsl:variable name="targets" select="key('id',$arearef)"/>
  <xsl:variable name="target" select="$targets[1]"/>

  <xsl:choose>
    <xsl:when test="count($target)=0">
      <xsl:value-of select="$arearef"/>
      <xsl:text>: ???</xsl:text>
    </xsl:when>
    <xsl:when test="local-name($target)='co'">
      <fo:basic-link internal-destination="{$arearef}">
        <xsl:apply-templates select="$target" mode="callout-bug"/>
      </fo:basic-link>
    </xsl:when>
    <xsl:when test="local-name($target)='areaset'">
      <xsl:call-template name="callout-bug">
        <xsl:with-param name="conum">
          <xsl:apply-templates select="$target" mode="conumber"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="local-name($target)='area'">
      <xsl:choose>
        <xsl:when test="$target/parent::areaset">
          <xsl:call-template name="callout-bug">
            <xsl:with-param name="conum">
              <xsl:apply-templates select="$target/parent::areaset"
                                   mode="conumber"/>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="callout-bug">
            <xsl:with-param name="conum">
              <xsl:apply-templates select="$target" mode="conumber"/>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>???</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="orderedlist-starting-number">
  <xsl:param name="list" select="."/>
  <xsl:variable name="pi-start">
    <xsl:call-template name="pi.dbfo_start">
      <xsl:with-param name="node" select="$list"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:call-template name="output-orderedlist-starting-number">
    <xsl:with-param name="list" select="$list"/>
    <xsl:with-param name="pi-start" select="$pi-start"/>
  </xsl:call-template>
</xsl:template>

</xsl:stylesheet>
