<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                exclude-result-prefixes="doc"
                version="1.0">

<!-- ********************************************************************
     $Id: table.xsl 8392 2009-04-01 08:47:55Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template name="blank.spans">
  <xsl:param name="cols" select="1"/>
  <xsl:if test="$cols &gt; 0">
    <xsl:text>0:</xsl:text>
    <xsl:call-template name="blank.spans">
      <xsl:with-param name="cols" select="$cols - 1"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="calculate.following.spans">
  <xsl:param name="colspan" select="1"/>
  <xsl:param name="spans" select="''"/>

  <xsl:choose>
    <xsl:when test="$colspan &gt; 0">
      <xsl:call-template name="calculate.following.spans">
        <xsl:with-param name="colspan" select="$colspan - 1"/>
        <xsl:with-param name="spans" select="substring-after($spans,':')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$spans"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="finaltd">
  <xsl:param name="spans"/>
  <xsl:param name="col" select="0"/>

  <xsl:if test="$spans != ''">
    <xsl:choose>
      <xsl:when test="starts-with($spans,'0:')">
        <xsl:call-template name="empty.table.cell">
          <xsl:with-param name="colnum" select="$col"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise></xsl:otherwise>
    </xsl:choose>

    <xsl:call-template name="finaltd">
      <xsl:with-param name="spans" select="substring-after($spans,':')"/>
      <xsl:with-param name="col" select="$col+1"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="sfinaltd">
  <xsl:param name="spans"/>

  <xsl:if test="$spans != ''">
    <xsl:choose>
      <xsl:when test="starts-with($spans,'0:')">0:</xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="substring-before($spans,':')-1"/>
        <xsl:text>:</xsl:text>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:call-template name="sfinaltd">
      <xsl:with-param name="spans" select="substring-after($spans,':')"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="entry.colnum">
  <xsl:param name="entry" select="."/>

  <xsl:choose>
    <xsl:when test="$entry/@spanname">
      <xsl:variable name="spanname" select="$entry/@spanname"/>
      <xsl:variable name="spanspec"
                    select="($entry/ancestor::tgroup/spanspec[@spanname=$spanname]
                             |$entry/ancestor::entrytbl/spanspec[@spanname=$spanname])[last()]"/>
      <xsl:variable name="colspec"
                    select="($entry/ancestor::tgroup/colspec[@colname=$spanspec/@namest]
                             |$entry/ancestor::entrytbl/colspec[@colname=$spanspec/@namest])[last()]"/>
      <xsl:call-template name="colspec.colnum">
        <xsl:with-param name="colspec" select="$colspec"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$entry/@colname">
      <xsl:variable name="colname" select="$entry/@colname"/>
      <xsl:variable name="colspec"
                    select="($entry/ancestor::tgroup/colspec[@colname=$colname]
                             |$entry/ancestor::entrytbl/colspec[@colname=$colname])[last()]"/>
      <xsl:call-template name="colspec.colnum">
        <xsl:with-param name="colspec" select="$colspec"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$entry/@namest">
      <xsl:variable name="namest" select="$entry/@namest"/>
      <xsl:variable name="colspec"
                    select="($entry/ancestor::tgroup/colspec[@colname=$namest]
                             |$entry/ancestor::entrytbl/colspec[@colname=$namest])[last()]"/>
      <xsl:call-template name="colspec.colnum">
        <xsl:with-param name="colspec" select="$colspec"/>
      </xsl:call-template>
    </xsl:when>
    <!-- no idea, return 0 -->
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<doc:template name="entry.colnum" xmlns="">
<refpurpose>Determine the column number in which a given entry occurs</refpurpose>
<refdescription id="entry.colnum-desc">
<para>If an <tag>entry</tag> has a
<tag class="attribute">colname</tag> or
<tag class="attribute">namest</tag> attribute, this template
will determine the number of the column in which the entry should occur.
For other <tag>entry</tag>s, nothing is returned.</para>
</refdescription>
<refparameter id="entry.colnum-params">
<variablelist>
<varlistentry><term>entry</term>
<listitem>
<para>The <tag>entry</tag>-element which is to be tested.</para>
</listitem>
</varlistentry>
</variablelist>
</refparameter>

<refreturn id="entry.colnum-returns">
<para>This template returns the column number if it can be determined,
or 0 (the empty string)</para>
</refreturn>
</doc:template>

<xsl:template name="colspec.colnum">
  <xsl:param name="colspec" select="."/>
  <xsl:choose>
    <xsl:when test="$colspec/@colnum">
      <xsl:value-of select="$colspec/@colnum"/>
    </xsl:when>
    <xsl:when test="$colspec/preceding-sibling::colspec">
      <xsl:variable name="prec.colspec.colnum">
        <xsl:call-template name="colspec.colnum">
          <xsl:with-param name="colspec"
                          select="$colspec/preceding-sibling::colspec[1]"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:value-of select="$prec.colspec.colnum + 1"/>
    </xsl:when>
    <xsl:otherwise>1</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="calculate.colspan">
  <xsl:param name="entry" select="."/>
  <xsl:variable name="spanname" select="$entry/@spanname"/>
  <xsl:variable name="spanspec"
                select="($entry/ancestor::tgroup/spanspec[@spanname=$spanname]
                         |$entry/ancestor::entrytbl/spanspec[@spanname=$spanname])[last()]"/>

  <xsl:variable name="namest">
    <xsl:choose>
      <xsl:when test="@spanname">
        <xsl:value-of select="$spanspec/@namest"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$entry/@namest"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="nameend">
    <xsl:choose>
      <xsl:when test="@spanname">
        <xsl:value-of select="$spanspec/@nameend"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$entry/@nameend"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="scol">
    <xsl:call-template name="colspec.colnum">
      <xsl:with-param name="colspec"
                      select="($entry/ancestor::tgroup/colspec[@colname=$namest]
                               |$entry/ancestor::entrytbl/colspec[@colname=$namest])[last()]"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="ecol">
    <xsl:call-template name="colspec.colnum">
      <xsl:with-param name="colspec"
                      select="($entry/ancestor::tgroup/colspec[@colname=$nameend]
                               |$entry/ancestor::entrytbl/colspec[@colname=$nameend])[last()]"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$namest != '' and $nameend != ''">
      <xsl:choose>
        <xsl:when test="number($ecol) &gt;= number($scol)">
          <xsl:value-of select="number($ecol) - number($scol) + 1"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="number($scol) - number($ecol) + 1"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>1</xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="calculate.rowsep">
  <xsl:param name="entry" select="."/>
  <xsl:param name="colnum" select="0"/>

  <xsl:call-template name="inherited.table.attribute">
    <xsl:with-param name="entry" select="$entry"/>
    <xsl:with-param name="colnum" select="$colnum"/>
    <xsl:with-param name="attribute" select="'rowsep'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="calculate.colsep">
  <xsl:param name="entry" select="."/>
  <xsl:param name="colnum" select="0"/>

  <xsl:call-template name="inherited.table.attribute">
    <xsl:with-param name="entry" select="$entry"/>
    <xsl:with-param name="colnum" select="$colnum"/>
    <xsl:with-param name="attribute" select="'colsep'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="inherited.table.attribute">
  <xsl:param name="entry" select="."/>
  <xsl:param name="row" select="$entry/ancestor-or-self::row[1]"/>
  <xsl:param name="colnum" select="0"/>
  <xsl:param name="attribute" select="'colsep'"/>

  <xsl:variable name="tgroup" select="$row/parent::*/parent::tgroup[1]"/>
  <xsl:variable name="tbody" select="$row/parent::*[1]"/>

  <xsl:variable name="table" select="($tgroup/ancestor::table
                                     |$tgroup/ancestor::informaltable
                                     |$entry/ancestor::entrytbl)[last()]"/>

  <xsl:variable name="entry.value">
    <xsl:call-template name="get-attribute">
      <xsl:with-param name="element" select="$entry"/>
      <xsl:with-param name="attribute" select="$attribute"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="row.value">
    <xsl:call-template name="get-attribute">
      <xsl:with-param name="element" select="$row"/>
      <xsl:with-param name="attribute" select="$attribute"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="span.value">
    <xsl:if test="$entry/@spanname">
      <xsl:variable name="spanname" select="$entry/@spanname"/>
      <xsl:variable name="spanspec"
                    select="$tgroup/spanspec[@spanname=$spanname]"/>
      <xsl:variable name="span.colspec"
                    select="$tgroup/colspec[@colname=$spanspec/@namest]"/>

      <xsl:variable name="spanspec.value">
        <xsl:call-template name="get-attribute">
          <xsl:with-param name="element" select="$spanspec"/>
          <xsl:with-param name="attribute" select="$attribute"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:variable name="scolspec.value">
        <xsl:call-template name="get-attribute">
          <xsl:with-param name="element" select="$span.colspec"/>
          <xsl:with-param name="attribute" select="$attribute"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:choose>
        <xsl:when test="$spanspec.value != ''">
          <xsl:value-of select="$spanspec.value"/>
        </xsl:when>
        <xsl:when test="$scolspec.value != ''">
          <xsl:value-of select="$scolspec.value"/>
        </xsl:when>
        <xsl:otherwise></xsl:otherwise>
      </xsl:choose>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="namest.value">
    <xsl:if test="$entry/@namest">
      <xsl:variable name="namest" select="$entry/@namest"/>
      <xsl:variable name="colspec"
                    select="$tgroup/colspec[@colname=$namest]"/>

      <xsl:variable name="inner.namest.value">
        <xsl:call-template name="get-attribute">
          <xsl:with-param name="element" select="$colspec"/>
          <xsl:with-param name="attribute" select="$attribute"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:choose>
        <xsl:when test="$inner.namest.value">
          <xsl:value-of select="$inner.namest.value"/>
        </xsl:when>
        <xsl:otherwise></xsl:otherwise>
      </xsl:choose>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="tgroup.value">
    <xsl:call-template name="get-attribute">
      <xsl:with-param name="element" select="$tgroup"/>
      <xsl:with-param name="attribute" select="$attribute"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="tbody.value">
    <xsl:call-template name="get-attribute">
      <xsl:with-param name="element" select="$tbody"/>
      <xsl:with-param name="attribute" select="$attribute"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="table.value">
    <xsl:call-template name="get-attribute">
      <xsl:with-param name="element" select="$table"/>
      <xsl:with-param name="attribute" select="$attribute"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="default.value">
    <!-- This section used to say that rowsep and colsep have defaults based -->
    <!-- on the frame setting. Further reflection and closer examination of the -->
    <!-- CALS spec reveals I was mistaken. The default is "1" for rowsep and colsep. -->
    <!-- For everything else, the default is the tgroup value -->
    <xsl:choose>
      <xsl:when test="$tgroup.value != ''">
        <xsl:value-of select="$tgroup.value"/>
      </xsl:when>
      <xsl:when test="$attribute = 'rowsep'">1</xsl:when>
      <xsl:when test="$attribute = 'colsep'">1</xsl:when>
      <xsl:otherwise><!-- empty --></xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="calc.colvalue">
    <xsl:if test="$colnum &gt; 0">
      <xsl:call-template name="colnum.colspec">
        <xsl:with-param name="colnum" select="$colnum"/>
        <xsl:with-param name="attribute" select="$attribute"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$entry.value != ''">
      <xsl:value-of select="$entry.value"/>
    </xsl:when>
    <xsl:when test="$row.value != ''">
      <xsl:value-of select="$row.value"/>
    </xsl:when>
    <xsl:when test="$span.value != ''">
      <xsl:value-of select="$span.value"/>
    </xsl:when>
    <xsl:when test="$namest.value != ''">
      <xsl:value-of select="$namest.value"/>
    </xsl:when>
    <xsl:when test="$calc.colvalue != ''">
      <xsl:value-of select="$calc.colvalue"/>
    </xsl:when>
    <xsl:when test="$tbody.value != ''">
      <xsl:value-of select="$tbody.value"/>
    </xsl:when>
    <xsl:when test="$tgroup.value != ''">
      <xsl:value-of select="$tgroup.value"/>
    </xsl:when>
    <xsl:when test="$table.value != ''">
      <xsl:value-of select="$table.value"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$default.value"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="colnum.colspec">
  <xsl:param name="colnum" select="0"/>
  <xsl:param name="attribute" select="'colname'"/>
  <xsl:param name="colspec.ancestor" 
             select="(ancestor::tgroup|ancestor::entrytbl)
                     [position() = last()]"/>
  <xsl:param name="colspecs" select="$colspec.ancestor/colspec"/>
  <xsl:param name="count" select="1"/>

  <xsl:choose>
    <xsl:when test="not($colspecs) or $count &gt; $colnum">
      <!-- nop -->
    </xsl:when>
    <xsl:when test="$colspecs[1]/@colnum">
      <xsl:choose>
        <xsl:when test="$colspecs[1]/@colnum = $colnum">
          <xsl:call-template name="get-attribute">
            <xsl:with-param name="element" select="$colspecs[1]"/>
            <xsl:with-param name="attribute" select="$attribute"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="colnum.colspec">
            <xsl:with-param name="colnum" select="$colnum"/>
            <xsl:with-param name="attribute" select="$attribute"/>
            <xsl:with-param name="colspecs"
                            select="$colspecs[position()&gt;1]"/>
            <xsl:with-param name="count"
                            select="$colspecs[1]/@colnum+1"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$count = $colnum">
          <xsl:call-template name="get-attribute">
            <xsl:with-param name="element" select="$colspecs[1]"/>
            <xsl:with-param name="attribute" select="$attribute"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="colnum.colspec">
            <xsl:with-param name="colnum" select="$colnum"/>
            <xsl:with-param name="attribute" select="$attribute"/>
            <xsl:with-param name="colspecs"
                            select="$colspecs[position()&gt;1]"/>
            <xsl:with-param name="count" select="$count+1"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="get-attribute">
  <xsl:param name="element" select="."/>
  <xsl:param name="attribute" select="''"/>

  <xsl:for-each select="$element/@*">
    <xsl:if test="local-name(.) = $attribute">
      <xsl:value-of select="."/>
    </xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template name="consume-row">
  <xsl:param name="spans"/>

  <xsl:if test="contains($spans,':')">
    <xsl:value-of select="substring-before($spans,':') - 1"/>
    <xsl:text>:</xsl:text>
    <xsl:call-template name="consume-row">
      <xsl:with-param name="spans" select="substring-after($spans,':')"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<!-- Returns the table style for the context element -->
<xsl:template name="tabstyle">
  <xsl:param name="node" select="."/>

  <xsl:variable name="tgroup" select="$node/tgroup[1] | 
                                      $node/ancestor-or-self::tgroup[1]"/>

  <xsl:variable name="table" 
                select="($node/ancestor-or-self::table | 
                         $node/ancestor-or-self::informaltable)[last()]"/>

  <xsl:variable name="tabstyle">
    <xsl:choose>
      <xsl:when test="$table/@tabstyle != ''">
        <xsl:value-of select="normalize-space($table/@tabstyle)"/>
      </xsl:when>
      <xsl:when test="$tgroup/@tgroupstyle != ''">
        <xsl:value-of select="normalize-space($tgroup/@tgroupstyle)"/>
      </xsl:when>
      <xsl:otherwise>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:value-of select="$tabstyle"/>
</xsl:template>

</xsl:stylesheet>
