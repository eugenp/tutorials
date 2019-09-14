<?xml version="1.0" encoding="ASCII"?>
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" version="1.0">


<!-- ********************************************************************
     $Id: biblio.xsl 6402 2006-11-12 08:23:21Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     The original code for processing bibliography in ISO690 style
     was provided by Jana Dvorakova <jana4u@seznam.cz>

     ******************************************************************** -->

<!-- ==================================================================== -->

<!-- if biblioentry.alt.primary.seps is set to nonzero value then use alternative separators for primary responsibility - $alt.person.two.sep, $alt.person.last.sep, $alt.person.more.sep  -->
<xsl:param name="biblioentry.alt.primary.seps" select="0"/>

<!-- how many authors will be printed if there is more than three authors - set to number 1 (default value), 2 or 3 -->
<xsl:param name="biblioentry.primary.count" select="1"/>

<!-- ==================================================================== -->

<xsl:template name="iso690.makecitation">
<!-- Types of resources -->
  <xsl:choose>

    <!-- SYSTEMS OF ELECTRONIC COMMUNICATION : ENTIRE MESSAGE SYSTEM -->
    <!-- same as Monographs -->
    <xsl:when test="./@role='messagesystem'">
      <xsl:call-template name="iso690.monogr"/>
    </xsl:when>

    <!-- SYSTEMS OF ELECTRONIC COMMUNICATION : ELECTRONIC MESSAGES -->
    <!-- same as Contributions to Monographs -->
    <xsl:when test="./@role='message'">
      <xsl:call-template name="iso690.paper.mon"/>
    </xsl:when>

    <!-- SERIALS -->
    <xsl:when test="./@role='serial' or ./biblioid/@class='issn' or ./issn">
      <xsl:call-template name="iso690.serial"/>
    </xsl:when>

    <!-- PARTS OF MONOGRAPHS -->
    <xsl:when test="./@role='part' or (./bibliomisc[@role='secnum']|./bibliomisc[@role='sectitle'])">
      <xsl:call-template name="iso690.monogr.part"/>
    </xsl:when>

    <!-- CONTRIBUTIONS TO MONOGRAPHS -->
    <xsl:when test="./@role='contribution' or (./biblioset/@relation='part' and ./biblioset/@relation='book')">
      <xsl:call-template name="iso690.paper.mon"/>
    </xsl:when>

    <!-- ARTICLES, ETC., IN SERIALS -->
    <xsl:when test="./@role='article' or (./biblioset/@relation='journal' and ./biblioset/@relation='article')">
      <xsl:call-template name="iso690.article"/>
    </xsl:when>

    <!-- PATENT DOCUMENTS -->
    <xsl:when test="./@role='patent' or (./bibliomisc[@role='patenttype'] and ./bibliomisc[@role='patentnum'])">
      <xsl:call-template name="iso690.patent"/>
    </xsl:when>

    <!-- MONOGRAPHS -->
    <xsl:otherwise>
      <xsl:call-template name="iso690.monogr"/>
    </xsl:otherwise>

  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<!-- MONOGRAPHS -->
<xsl:template name="iso690.monogr">
  <!-- Primary responsibility -->
  <xsl:call-template name="iso690.primary"/>
  <!-- Title and Type of medium -->
  <xsl:call-template name="iso690.title"/>
  <!-- Subordinate responsibility -->
  <xsl:call-template name="iso690.secondary"/>
  <!-- Edition -->
  <xsl:call-template name="iso690.edition"/>
  <!-- Place of publication, Publisher, Year/Date of publication, Date of update/revision, Date of citation -->
  <xsl:call-template name="iso690.pub"/>
  <!-- Extent -->
  <xsl:call-template name="iso690.extent"/>
  <!-- Series -->
  <xsl:call-template name="iso690.serie"/>
  <!-- Notes -->
  <xsl:call-template name="iso690.notice"/>
  <!-- Avaibility and access -->
  <xsl:call-template name="iso690.access"/>
  <!-- Standard number -->
  <xsl:call-template name="iso690.isbn"/>
</xsl:template>

<!-- SERIALS -->
<xsl:template name="iso690.serial">
  <!-- Title and Type of medium -->
  <xsl:call-template name="iso690.title"/>
  <!-- Responsibility [nonEL] -->
  <xsl:if test="not(./bibliomisc[@role='medium'])">
    <xsl:call-template name="iso690.secondary"/>
  </xsl:if>
  <!-- Edition -->
  <xsl:call-template name="iso690.edition">
    <xsl:with-param name="after" select="./bibliomisc[@role='issuing']"/>
  </xsl:call-template>
  <!-- Issue designation (date and/or num) [nonEL] -->
  <xsl:if test="not(./bibliomisc[@role='medium'])">
    <xsl:call-template name="iso690.issuing"/>
  </xsl:if>
  <!-- Place of publication, Publisher, Year/Date of publication, Date of update/revision, Date of citation -->
  <xsl:call-template name="iso690.pub"/>
  <!-- Series -->
  <xsl:call-template name="iso690.serie"/>
  <!-- Notes -->
  <xsl:call-template name="iso690.notice"/>
  <!-- Avaibility and access -->
  <xsl:call-template name="iso690.access"/>
  <!-- Standard number -->
  <xsl:call-template name="iso690.issn"/>
</xsl:template>

<!-- PARTS OF MONOGRAPHS -->
<xsl:template name="iso690.monogr.part">
  <!-- Primary responsibility of host document -->
  <xsl:call-template name="iso690.primary"/>
  <!-- Title and Type of medium of host document -->
  <xsl:call-template name="iso690.title"/>
  <!-- Subordinate responsibility of host document [EL] -->
  <xsl:if test="./bibliomisc[@role='medium']">
    <xsl:call-template name="iso690.secondary"/>
  </xsl:if>
  <!-- Edition -->
  <xsl:call-template name="iso690.edition">
    <xsl:with-param name="after" select="./volumenum"/>
  </xsl:call-template>
  <!-- Numeration of the part [nonEL]-->
  <xsl:if test="not(./bibliomisc[@role='medium'])">
    <xsl:call-template name="iso690.partnr"/>
  <!-- Subordinate responsibility [nonEL] -->
    <xsl:call-template name="iso690.secondary"/>
  </xsl:if>
  <!-- Place of publication, Publisher, Year/Date of publication, Date of update/revision, Date of citation -->
  <xsl:call-template name="iso690.pub"/>
  <!-- Location within host -->
  <xsl:call-template name="iso690.part.location"/>
  <xsl:if test="./bibliomisc[@role='medium']">
  <!-- Numeration within host document [EL] -->
  <!-- Notes [EL] -->
    <xsl:call-template name="iso690.notice"/>
  <!-- Avaibility and access [EL] -->
    <xsl:call-template name="iso690.access"/>
  <!-- Standard number [EL] -->
    <xsl:call-template name="iso690.isbn"/>
  </xsl:if>
</xsl:template>

<!-- CONTRIBUTIONS TO MONOGRAPHS -->
<xsl:template name="iso690.paper.mon">
<!-- Contribution -->
  <xsl:apply-templates mode="iso690.paper.part" select="./biblioset[@relation='part']"/>
<!-- In -->
  <xsl:text>In </xsl:text>
<!-- Host -->
  <xsl:apply-templates mode="iso690.paper.book" select="./biblioset[@relation='book']"/>
</xsl:template>

<xsl:template match="biblioset" mode="iso690.paper.part">
<!-- Contribution -->
  <!-- Primary responsibility -->
  <xsl:call-template name="iso690.primary"/>
  <!-- Title -->
  <xsl:call-template name="iso690.title">
    <xsl:with-param name="italic" select="0"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="biblioset" mode="iso690.paper.book">
<!-- Host -->
  <!-- Primary responsibility -->
  <xsl:call-template name="iso690.primary"/>
  <!-- Title and Type of medium -->
  <xsl:call-template name="iso690.title"/>
  <!-- Subordinate responsibility [EL] -->
  <xsl:if test="./bibliomisc[@role='medium']">
    <xsl:call-template name="iso690.secondary"/>
  </xsl:if>
  <!-- Edition -->
  <xsl:call-template name="iso690.edition"/>
  <!-- Place of publication, Publisher, Year/Date of publication, Date of update/revision, Date of citation -->
  <xsl:call-template name="iso690.paper.pub"/>
  <!-- Numeration within host document [EL] -->
  <!-- Location within host -->
  <xsl:call-template name="iso690.location"/>
  <xsl:if test="./bibliomisc[@role='medium']">
  <!-- Notes [EL] -->
    <xsl:call-template name="iso690.notice"/>
  <!-- Avaibility and access [EL] -->
    <xsl:call-template name="iso690.access"/>
  <!-- Standard number [EL] -->
    <xsl:call-template name="iso690.isbn"/>
  </xsl:if>
</xsl:template>

<!-- ARTICLES, ETC., IN SERIALS -->
<xsl:template name="iso690.article">
<!-- Article -->
  <xsl:apply-templates mode="iso690.article.art" select="./biblioset[@relation='article']"/>
<!-- Serial -->
  <xsl:apply-templates mode="iso690.article.jour" select="./biblioset[@relation='journal']"/>
</xsl:template>

<xsl:template match="biblioset" mode="iso690.article.art">
<!-- Article -->
  <!-- Primary responsibility -->
  <xsl:call-template name="iso690.primary"/>
  <!-- Title -->
  <xsl:call-template name="iso690.title">
    <xsl:with-param name="italic" select="0"/>
  </xsl:call-template>
  <!-- Subordinate responsibility [nonEL] -->
  <xsl:if test="not(../*/bibliomisc[@role='medium'])">
    <xsl:call-template name="iso690.secondary"/>
  </xsl:if>
</xsl:template>

<xsl:template match="biblioset" mode="iso690.article.jour">
<!-- Serial -->
  <!-- Title and Type of medium -->
  <xsl:call-template name="iso690.title"/>
  <!-- Edition -->
  <xsl:call-template name="iso690.edition">
    <xsl:with-param name="after" select="./pubdate[not(@role='issuing')]|./volumenum|./issuenum|./pagenums"/>
  </xsl:call-template>
  <!-- Number designation [EL] -->
  <!-- Location within host -->
  <xsl:call-template name="iso690.article.location"/>
  <xsl:if test="./bibliomisc[@role='medium']">
  <!-- Notes [EL] -->
    <xsl:call-template name="iso690.notice"/>
  <!-- Avaibility and access [EL] -->
    <xsl:call-template name="iso690.access"/>
  <!-- Standard number [EL] -->
    <xsl:call-template name="iso690.issn"/>
  </xsl:if>
</xsl:template>

<!-- PATENT DOCUMENTS -->
<xsl:template name="iso690.patent">
  <!-- Primary responsibility (applicant) -->
  <xsl:call-template name="iso690.primary"/>
  <!-- Title of the invention -->
  <xsl:call-template name="iso690.title"/>
  <!-- Subordinate responsibility -->
  <xsl:call-template name="iso690.secondary"/>
  <!-- Notes -->
  <xsl:call-template name="iso690.notice"/>
  <!-- Identification -->
  <xsl:call-template name="iso690.pat.ident"/>
</xsl:template>

<!-- ==================================================================== -->
<!-- Elements -->

<!-- Primary responsibility -->
<xsl:template name="iso690.primary">
  <xsl:param name="primary.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'primary.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="./authorgroup/author|./author">
      <xsl:call-template name="iso690.author.list">
        <xsl:with-param name="person.list" select=".//authorgroup/author|.//author"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="./authorgroup/editor|./editor">
      <xsl:call-template name="iso690.author.list">
        <xsl:with-param name="person.list" select=".//authorgroup/editor|.//editor"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="./authorgroup/corpauthor|./corpauthor">
      <xsl:call-template name="iso690.author.list">
        <xsl:with-param name="person.list" select=".//authorgroup/corpauthor|.//corpauthor"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="(./firstname)and(./surname)">
        <xsl:call-template name="iso690.author"/>
        <xsl:call-template name="iso690.endsep">
          <xsl:with-param name="text" select="string(./firstname[1])"/>
          <xsl:with-param name="sep" select="$primary.sep"/>
        </xsl:call-template>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="iso690.author.list">
  <xsl:param name="person.list" select="author|corpauthor|editor"/>
  <xsl:param name="person.count" select="count($person.list)"/>
  <xsl:param name="count" select="1"/>
  <xsl:param name="group" select="./authorgroup[@role='many']"/>
  <xsl:param name="many" select="0"/>

  <xsl:param name="primary.many">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'primary.many'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="primary.editor">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'primary.editor'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="primary.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'primary.sep'"/></xsl:call-template>
  </xsl:param>

  <xsl:choose>
    <xsl:when test="$count &gt; $person.count"/>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$person.count &lt; 4 and not($group)">
          <xsl:call-template name="iso690.author">
            <xsl:with-param name="node" select="$person.list[position()=$count]"/>
          </xsl:call-template>
          <xsl:choose>
            <xsl:when test="$person.count = 2 and $count = 1 and $biblioentry.alt.primary.seps != 0">
              <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'alt.person.two.sep'"/></xsl:call-template>
            </xsl:when>
            <xsl:when test="$person.count = 2 and $count = 1">
              <xsl:call-template name="gentext.template">
                <xsl:with-param name="context" select="'authorgroup'"/>
                <xsl:with-param name="name" select="'sep2'"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$person.count &gt; 2 and $count+1 = $person.count and $biblioentry.alt.primary.seps != 0">
              <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'alt.person.last.sep'"/></xsl:call-template>
            </xsl:when>
            <xsl:when test="$person.count &gt; 2 and $count+1 = $person.count">
              <xsl:call-template name="gentext.template">
                <xsl:with-param name="context" select="'authorgroup'"/>
                <xsl:with-param name="name" select="'seplast'"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="$count &lt; $person.count and $biblioentry.alt.primary.seps != 0">
              <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'alt.person.more.sep'"/></xsl:call-template>
            </xsl:when>
            <xsl:when test="$count &lt; $person.count">
              <xsl:call-template name="gentext.template">
                <xsl:with-param name="context" select="'authorgroup'"/>
                <xsl:with-param name="name" select="'sep'"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="($count = $person.count)">
              <xsl:choose>
                <xsl:when test="$many!=0">
                  <xsl:if test="name($person.list[position()=$count])='editor'">
                    <xsl:value-of select="$primary.editor"/>
                  </xsl:if>
                  <xsl:value-of select="$primary.many"/>
                  <xsl:call-template name="iso690.endsep">
                    <xsl:with-param name="text" select="$primary.many"/>
                    <xsl:with-param name="sep" select="$primary.sep"/>
                  </xsl:call-template>
                </xsl:when>
                <xsl:when test="name($person.list[position()=$count])='editor'">
                  <xsl:value-of select="$primary.editor"/>
                  <xsl:value-of select="$primary.sep"/>
                </xsl:when>
                <xsl:when test="name($person.list[position()=$count])='corpauthor'">
                  <xsl:call-template name="iso690.endsep">
                    <xsl:with-param name="text" select="string($person.list[position()=$count])"/>
                    <xsl:with-param name="sep" select="$primary.sep"/>
                  </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:call-template name="iso690.endsep">
                    <xsl:with-param name="text" select="string($person.list[position()=$count]//firstname[1])"/>
                    <xsl:with-param name="sep" select="$primary.sep"/>
                  </xsl:call-template>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:when>
          </xsl:choose>

          <xsl:call-template name="iso690.author.list">
            <xsl:with-param name="person.list" select="$person.list"/>
            <xsl:with-param name="person.count" select="$person.count"/>
            <xsl:with-param name="count" select="$count+1"/>
            <xsl:with-param name="many" select="$many"/>
            <xsl:with-param name="group"/>
          </xsl:call-template>
        </xsl:when>

        <xsl:otherwise>
          <xsl:choose>
            <xsl:when test="($biblioentry.primary.count&gt;=3) and ($person.count&gt;=3)">
              <xsl:call-template name="iso690.author.list">
                <xsl:with-param name="person.list" select="$person.list[1]|$person.list[2]|$person.list[3]"/>
                <xsl:with-param name="person.count" select="3"/>
                <xsl:with-param name="count" select="1"/>
                <xsl:with-param name="many" select="1"/>
                <xsl:with-param name="group"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:when test="($biblioentry.primary.count&gt;1) and  ($person.count&gt;1)">
              <xsl:call-template name="iso690.author.list">
                <xsl:with-param name="person.list" select="$person.list[1]|$person.list[2]"/>
                <xsl:with-param name="person.count" select="2"/>
                <xsl:with-param name="count" select="1"/>
                <xsl:with-param name="many" select="1"/>
                <xsl:with-param name="group"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:call-template name="iso690.author.list">
                <xsl:with-param name="person.list" select="$person.list[1]"/>
                <xsl:with-param name="person.count" select="1"/>
                <xsl:with-param name="count" select="1"/>
                <xsl:with-param name="many" select="1"/>
                <xsl:with-param name="group"/>
              </xsl:call-template>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="iso690.author">
  <xsl:param name="node" select="."/>
  <xsl:param name="lastfirst.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'lastfirst.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="name($node)!='corpauthor'">
      <span style="text-transform:uppercase">
        <xsl:apply-templates mode="iso690.mode" select="$node//surname[1]"/>
      </span>
      <xsl:if test="$node//surname and $node//firstname">
        <xsl:value-of select="$lastfirst.sep"/>
      </xsl:if>
      <xsl:apply-templates mode="iso690.mode" select="$node//firstname[1]"/>
    </xsl:when>
    <xsl:otherwise>
      <span style="text-transform:uppercase">
        <xsl:apply-templates mode="iso690.mode" select="$node"/>
      </span>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="corpauthor|firstname|surname" mode="iso690.mode">
  <xsl:apply-templates mode="iso690.mode"/>
</xsl:template>

<!-- Title and Type of medium -->
<xsl:template name="iso690.title">
  <xsl:param name="medium" select="./bibliomisc[@role='medium']"/>
  <xsl:param name="italic" select="1"/>
  <xsl:param name="sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'title.sep'"/></xsl:call-template>
  </xsl:param>

  <xsl:apply-templates mode="iso690.mode" select="./title">
    <xsl:with-param name="medium" select="$medium"/>
    <xsl:with-param name="italic" select="$italic"/>
    <xsl:with-param name="sep" select="$sep"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="title" mode="iso690.mode">
  <xsl:param name="medium"/>
  <xsl:param name="italic" select="1"/>
  <xsl:param name="sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'title.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="medium1">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'medium1'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="medium2">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'medium2'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="$italic=1">
      <xsl:call-template name="iso690.italic.title"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="iso690.make.title"/>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:if test="$medium">
    <xsl:value-of select="$medium1"/>
    <xsl:apply-templates mode="iso690.mode" select="$medium"/>
    <xsl:value-of select="$medium2"/>
  </xsl:if>
  <xsl:call-template name="iso690.endsep">
    <xsl:with-param name="text" select="concat(string(.),string(../subtitle))"/>
    <xsl:with-param name="sep" select="$sep"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="iso690.italic.title">
  <em xmlns:xslo="http://www.w3.org/1999/XSL/Transform">
    <xsl:call-template name="iso690.make.title"/>
  </em>
</xsl:template>

<xsl:template name="iso690.make.title">
  <xsl:param name="submaintitle.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'submaintitle.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:if test="../subtitle|../info/subtitle">
    <xsl:value-of select="$submaintitle.sep"/>
    <xsl:apply-templates mode="iso690.mode" select="../subtitle|../info/subtitle"/>
  </xsl:if>
</xsl:template>

<xsl:template match="subtitle" mode="iso690.mode">
  <xsl:apply-templates mode="iso690.mode"/>
</xsl:template>

<xsl:template match="bibliomisc[@role='medium']" mode="iso690.mode">
  <xsl:apply-templates mode="iso690.mode"/>
</xsl:template>

<!-- Subordinate responsibility -->
<xsl:template name="iso690.secondary">
  <xsl:param name="secondary.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'secondary.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="secondary.person.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'secondary.person.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:for-each select="./bibliomisc[@role='secondary']">
    <xsl:apply-templates mode="iso690.mode" select="."/>
    <xsl:choose>
      <xsl:when test="position()=count(../bibliomisc[@role='secondary'])">
        <xsl:call-template name="iso690.endsep">
          <xsl:with-param name="text" select="string(.)"/>
          <xsl:with-param name="sep" select="$secondary.sep"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$secondary.person.sep"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:for-each>
</xsl:template>

<xsl:template match="bibliomisc[@role='secondary']" mode="iso690.mode">
  <xsl:apply-templates mode="iso690.mode"/>
</xsl:template>

<!-- Edition -->
<xsl:template name="iso690.edition">
  <xsl:param name="after"/>
  <xsl:param name="edition.serial.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'edition.serial.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="string($after)!=''">
      <xsl:apply-templates mode="iso690.mode" select="./edition">
        <xsl:with-param name="sep" select="$edition.serial.sep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="iso690.mode" select="./edition"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="edition" mode="iso690.mode">
  <xsl:param name="sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'edition.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:call-template name="iso690.endsep">
    <xsl:with-param name="text" select="string(.)"/>
    <xsl:with-param name="sep" select="$sep"/>
  </xsl:call-template>
</xsl:template>

<!-- Issue designation (date and/or num) -->
<xsl:template name="iso690.issuing">
  <xsl:param name="issuing.div">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'issuing.div'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="issuing.range">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'issuing.range'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="issuing.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'issuing.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="./pubdate[@role='issuing'] and ./volumenum[2] and ./issuenum[2]">
      <xsl:call-template name="iso690.issuedate"/>
      <xsl:apply-templates mode="iso690.mode" select="./volumenum[1]">
        <xsl:with-param name="sep" select="$issuing.div"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./issuenum[1]">
        <xsl:with-param name="sep" select="$issuing.range"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./volumenum[2]">
        <xsl:with-param name="sep" select="$issuing.div"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./issuenum[2]">
        <xsl:with-param name="sep" select="$issuing.sep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="./pubdate[@role='issuing'] and ./volumenum[2]">
      <xsl:call-template name="iso690.issuedate"/>
      <xsl:apply-templates mode="iso690.mode" select="./volumenum[1]">
        <xsl:with-param name="sep" select="$issuing.range"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./volumenum[2]">
        <xsl:with-param name="sep" select="$issuing.sep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="./pubdate[@role='issuing'] and ./volumenum and ./issuenum">
      <xsl:apply-templates mode="iso690.mode" select="./pubdate[@role='issuing']">
        <xsl:with-param name="sep" select="$issuing.div"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./volumenum">
        <xsl:with-param name="sep" select="$issuing.div"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./issuenum">
        <xsl:with-param name="sep" select="$issuing.sep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="./pubdate[@role='issuing']">
      <xsl:apply-templates mode="iso690.mode" select="./pubdate[@role='issuing']">
        <xsl:with-param name="sep" select="$issuing.sep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="./volumenum">
      <xsl:apply-templates mode="iso690.mode" select="./volumenum">
        <xsl:with-param name="sep" select="$issuing.sep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="./issuenum">
      <xsl:apply-templates mode="iso690.mode" select="./issuenum">
        <xsl:with-param name="sep" select="$issuing.sep"/>
      </xsl:apply-templates>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template name="iso690.issuedate">
  <xsl:param name="issuing.div">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'issuing.div'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="issuing.range">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'issuing.range'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="issuing.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'issuing.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="./pubdate[@role='issuing'][2]">
      <xsl:apply-templates mode="iso690.mode" select="./pubdate[@role='issuing'][1]">
        <xsl:with-param name="sep" select="$issuing.range"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./pubdate[@role='issuing'][2]">
        <xsl:with-param name="sep" select="$issuing.div"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="iso690.mode" select="./pubdate[@role='issuing']">
        <xsl:with-param name="sep" select="$issuing.div"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="pubdate[@role='issuing']" mode="iso690.mode">
  <xsl:param name="sep"/>
  <xsl:variable name="substr" select="substring(string(.),string-length(string(.)))"/>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:call-template name="iso690.space">
    <xsl:with-param name="text" select="$substr"/>
  </xsl:call-template>
  <xsl:choose>
    <xsl:when test="$substr='-'">
      <xsl:call-template name="iso690.endsep">
        <xsl:with-param name="text" select="' '"/>
        <xsl:with-param name="sep" select="$sep"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="iso690.endsep">
        <xsl:with-param name="text" select="string(.)"/>
        <xsl:with-param name="sep" select="$sep"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Numeration of the part -->
<xsl:template name="iso690.partnr">
  <xsl:param name="partnr.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'partnr.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode" select="./volumenum">
    <xsl:with-param name="sep" select="$partnr.sep"/>
  </xsl:apply-templates>
</xsl:template>

<!-- Place of publication, Publisher, Year/Date of publication, Date of update/revision, Date of citation -->
<xsl:template name="iso690.pub">
  <xsl:param name="onlydate" select="0"/>
  <xsl:param name="placesep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'placepubl.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="pubsep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'publyear.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="endsep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'pubinfo.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="(./publisher/publishername|./publishername|./publisher/address/city)and($onlydate=0)and(./pubdate[not(@role='issuing')]|./copyright/year|./date[@role='upd']|./date[@role='upd'])">
      <xsl:apply-templates mode="iso690.mode" select="./publisher/address/city">
        <xsl:with-param name="sep" select="$placesep"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./publisher/publishername|./publishername">
        <xsl:with-param name="sep" select="$pubsep"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./pubdate[not(@role='issuing')]|./copyright/year">
        <xsl:with-param name="sep" select="$endsep"/>
      </xsl:apply-templates>
      <xsl:if test="not(./pubdate[not(@role='issuing')]|./copyright/year)">
        <xsl:call-template name="iso690.data">
          <xsl:with-param name="sep" select="$endsep"/>
        </xsl:call-template>
      </xsl:if>
    </xsl:when>
    <xsl:when test="(./publisher/publishername|./publishername)and(./publisher/address/city)and($onlydate=0)">
      <xsl:apply-templates mode="iso690.mode" select="./publisher/address/city">
        <xsl:with-param name="sep" select="$placesep"/>
      </xsl:apply-templates>
      <xsl:apply-templates mode="iso690.mode" select="./publisher/publishername|./publishername">
        <xsl:with-param name="sep" select="$endsep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="($onlydate=1)or(./pubdate[not(@role='issuing')]|./copyright/year)">
      <xsl:apply-templates mode="iso690.mode" select="./pubdate[not(@role='issuing')]|./copyright/year">
        <xsl:with-param name="sep" select="$endsep"/>
      </xsl:apply-templates>
      <xsl:if test="$onlydate=1">
        <xsl:call-template name="iso690.location">
          <xsl:with-param name="onlypages" select="1"/>
        </xsl:call-template>
      </xsl:if>
    </xsl:when>
    <xsl:when test="not(./pubdate[not(@role='issuing')]|./copyright/year)">
      <xsl:call-template name="iso690.data">
        <xsl:with-param name="sep" select="$endsep"/>
      </xsl:call-template>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template name="iso690.paper.pub">
  <xsl:param name="spec.pubinfo.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'spec.pubinfo.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="./volumnum|./issuenum|./pagenums">
      <xsl:call-template name="iso690.pub">
        <xsl:with-param name="endsep" select="$spec.pubinfo.sep"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="iso690.pub"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="iso690.data">
  <xsl:param name="sep"/>
  <xsl:param name="datecit2">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'datecit2'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode" select="./date[@role='upd']">
    <xsl:with-param name="sep"/>
  </xsl:apply-templates>
  <xsl:apply-templates mode="iso690.mode" select="./date[@role='cit']"/>
  <xsl:choose>
    <xsl:when test="./date[@role='cit']">
      <xsl:call-template name="iso690.endsep">
        <xsl:with-param name="text" select="$datecit2"/>
        <xsl:with-param name="sep" select="$sep"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="./date[@role='upd']">
      <xsl:call-template name="iso690.endsep">
        <xsl:with-param name="text" select="string(./date[@role='upd'])"/>
        <xsl:with-param name="sep" select="$sep"/>
      </xsl:call-template>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="publisher/address/city|publishername" mode="iso690.mode">
  <xsl:param name="sep"/>
  <xsl:param name="upd" select="0"/>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:call-template name="iso690.endsep">
    <xsl:with-param name="text" select="string(.)"/>
    <xsl:with-param name="sep" select="$sep"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="pubdate|copyright/year" mode="iso690.mode">
  <xsl:param name="sep"/>
  <xsl:param name="upd" select="1"/>
  <xsl:param name="datecit2">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'datecit2'"/></xsl:call-template>
  </xsl:param>
  <xsl:variable name="substr" select="substring(string(.),string-length(string(.)))"/>
  <xsl:if test="name(.)!='pubdate'">
    <xsl:value-of select="'&#169;'"/><!-- copyright -->
  </xsl:if>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:call-template name="iso690.space">
    <xsl:with-param name="text" select="$substr"/>
  </xsl:call-template>
  <xsl:if test="$upd!=0">
    <xsl:choose>
      <xsl:when test="name(.)='pubdate'">
        <xsl:apply-templates mode="iso690.mode" select="../date[@role='upd']"/>
        <xsl:apply-templates mode="iso690.mode" select="../date[@role='cit']"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates mode="iso690.mode" select="../../date[@role='upd']"/>
        <xsl:apply-templates mode="iso690.mode" select="../../date[@role='cit']"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>
  <xsl:choose>
    <xsl:when test="../date[@role='cit']|../../date[@role='cit'] and $upd!=0">
      <xsl:call-template name="iso690.endsep">
        <xsl:with-param name="text" select="$datecit2"/>
        <xsl:with-param name="sep" select="$sep"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="../date[@role='upd']|../../date[@role='upd'] and $upd!=0">
      <xsl:call-template name="iso690.endsep">
        <xsl:with-param name="text" select="string(../date[@role='upd'])"/>
        <xsl:with-param name="sep" select="$sep"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$substr='-'">
      <xsl:call-template name="iso690.endsep">
        <xsl:with-param name="text" select="' '"/>
        <xsl:with-param name="sep" select="$sep"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="iso690.endsep">
        <xsl:with-param name="text" select="string(.)"/>
        <xsl:with-param name="sep" select="$sep"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="iso690.space">
  <xsl:param name="text" select="substring(string(.),string-length(string(.)))"/>
  <xsl:if test="$text='-'">
    <xsl:value-of select="' '"/>
  </xsl:if>
</xsl:template>

<!-- Date of update/revision -->
<xsl:template match="date[@role='upd']" mode="iso690.mode">
  <xsl:param name="sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'upd.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:value-of select="$sep"/>
  <xsl:apply-templates mode="iso690.mode"/>
</xsl:template>

<!-- Date of citation -->
<xsl:template match="date[@role='cit']" mode="iso690.mode">
  <xsl:param name="datecit1">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'datecit1'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="datecit2">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'datecit2'"/></xsl:call-template>
  </xsl:param>
  <xsl:value-of select="$datecit1"/>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:value-of select="$datecit2"/>
</xsl:template>

<!-- Extent -->
<xsl:template name="iso690.extent">
  <xsl:param name="extent.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'extent.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode" select="./pagenums">
    <xsl:with-param name="sep" select="$extent.sep"/>
  </xsl:apply-templates>
</xsl:template>

<!-- Location within host -->
<xsl:template name="iso690.part.location">
  <xsl:param name="location.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'location.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="./pagenums">
      <xsl:apply-templates mode="iso690.mode" select="./bibliomisc[@role='secnum']"/>
      <xsl:apply-templates mode="iso690.mode" select="./bibliomisc[@role='sectitle']"/>
      <xsl:apply-templates mode="iso690.mode" select="./pagenums"/>
    </xsl:when>
    <xsl:when test="./bibliomisc[@role='sectitle']">
      <xsl:apply-templates mode="iso690.mode" select="./bibliomisc[@role='secnum']"/>
      <xsl:apply-templates mode="iso690.mode" select="./bibliomisc[@role='sectitle']">
        <xsl:with-param name="sep" select="$location.sep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="iso690.mode" select="./bibliomisc[@role='secnum']">
        <xsl:with-param name="sep" select="$location.sep"/>
      </xsl:apply-templates>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="iso690.article.location">
  <xsl:param name="location.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'location.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="locs.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'locs.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="not(./date[@role='upd']|./date[@role='cit'])">
      <xsl:choose>
        <xsl:when test="./volumenum|./issuenum|./pagenums">
          <xsl:apply-templates mode="iso690.mode" select="./pubdate[not(@role='issuing')]">
            <xsl:with-param name="upd" select="0"/>
            <xsl:with-param name="sep" select="$locs.sep"/>
          </xsl:apply-templates>
          <xsl:call-template name="iso690.location"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates mode="iso690.mode" select="./pubdate[not(@role='issuing')]">
            <xsl:with-param name="sep" select="$location.sep"/>
          </xsl:apply-templates>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="./volumenum|./issuenum|./pagenums">
          <xsl:apply-templates mode="iso690.mode" select="./pubdate[not(@role='issuing')]">
            <xsl:with-param name="upd" select="0"/>
            <xsl:with-param name="sep" select="$locs.sep"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates mode="iso690.mode" select="./pubdate[not(@role='issuing')]">
            <xsl:with-param name="upd" select="0"/>
            <xsl:with-param name="sep" select="$location.sep"/>
          </xsl:apply-templates>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="./issuenum">
          <xsl:apply-templates mode="iso690.mode" select="./volumenum"/>
          <xsl:apply-templates mode="iso690.mode" select="./issuenum">
            <xsl:with-param name="sep"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates mode="iso690.mode" select="./volumenum">
            <xsl:with-param name="sep"/>
          </xsl:apply-templates>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="./pagenums">
          <xsl:call-template name="iso690.data">
            <xsl:with-param name="sep" select="$locs.sep"/>
          </xsl:call-template>
          <xsl:apply-templates mode="iso690.mode" select="./pagenums"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="iso690.data">
            <xsl:with-param name="sep" select="$location.sep"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="iso690.location">
  <xsl:param name="location.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'location.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="./volumenum and not(./issuenum) and not(./pagenums)">
      <xsl:apply-templates mode="iso690.mode" select="./volumenum">
        <xsl:with-param name="sep" select="$location.sep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="./issuenum and not(./pagenums)">
      <xsl:apply-templates mode="iso690.mode" select="./volumenum"/>
      <xsl:apply-templates mode="iso690.mode" select="./issuenum">
        <xsl:with-param name="sep" select="$location.sep"/>
      </xsl:apply-templates>
    </xsl:when>
    <xsl:when test="./pagenums">
      <xsl:apply-templates mode="iso690.mode" select="./volumenum"/>
      <xsl:apply-templates mode="iso690.mode" select="./issuenum"/>
      <xsl:apply-templates mode="iso690.mode" select="./pagenums"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="bibliomisc[@role='secnum']|bibliomisc[@role='sectitle']" mode="iso690.mode">
  <xsl:param name="sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'locs.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:call-template name="iso690.endsep">
    <xsl:with-param name="text" select="string(.)"/>
    <xsl:with-param name="sep" select="$sep"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="volumenum|issuenum" mode="iso690.mode">
  <xsl:param name="sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'locs.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:call-template name="iso690.endsep">
    <xsl:with-param name="text" select="string(.)"/>
    <xsl:with-param name="sep" select="$sep"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="pagenums" mode="iso690.mode">
  <xsl:param name="sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'location.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:call-template name="iso690.endsep">
    <xsl:with-param name="text" select="string(.)"/>
    <xsl:with-param name="sep" select="$sep"/>
  </xsl:call-template>
</xsl:template>

<!-- Series -->
<xsl:template name="iso690.serie">
  <xsl:apply-templates mode="iso690.mode" select=".//bibliomisc[@role='serie']"/>
</xsl:template>

<!-- Notes -->
<xsl:template name="iso690.notice">
  <xsl:apply-templates mode="iso690.mode" select=".//bibliomisc[not(@role)]"/>
</xsl:template>

<xsl:template match="bibliomisc[not(@role)]|bibliomisc[@role='serie']" mode="iso690.mode">
  <xsl:param name="notice.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'notice.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:call-template name="iso690.endsep">
    <xsl:with-param name="text" select="string(.)"/>
    <xsl:with-param name="sep" select="$notice.sep"/>
  </xsl:call-template>
</xsl:template>

<!-- Avaibility and access -->
<xsl:template name="iso690.access">
  <xsl:for-each select="./biblioid[@class='uri']|./bibliomisc[@role='access']">
    <xsl:choose>
      <xsl:when test="position()=1">
        <xsl:apply-templates mode="iso690.mode" select="."/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates mode="iso690.mode" select=".">
          <xsl:with-param name="firstacc" select="0"/>
        </xsl:apply-templates>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:for-each>
</xsl:template>

<xsl:template match="biblioid[@class='uri']/ulink|bibliomisc[@role='access']/ulink" mode="iso690.mode">
  <xsl:param name="link1">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'link1'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="link2">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'link2'"/></xsl:call-template>
  </xsl:param>
  <xsl:value-of select="$link1"/>
  <xsl:call-template name="ulink"/>
  <xsl:value-of select="$link2"/>
</xsl:template>

<xsl:template match="biblioid[@class='uri']|bibliomisc[@role='access']" mode="iso690.mode">
  <xsl:param name="firstacc" select="1"/>
  <xsl:param name="access">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'access'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="acctoo">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'acctoo'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="onwww">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'onwww'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="oninet">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'oninet'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="access.end">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'access.end'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="access.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'access.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:choose>
    <xsl:when test="$firstacc=1">
      <xsl:value-of select="$access"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$acctoo"/>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:choose>
    <xsl:when test="(./ulink)and(string(./ulink)=string(.))">
      <xsl:choose>
        <xsl:when test="(starts-with(./ulink/@url,'http://')or(starts-with(./ulink/@url,'https://')))">
          <xsl:value-of select="$onwww"/>
          <xsl:value-of select="$access.end"/>
          <xsl:apply-templates mode="iso690.mode" select="./ulink"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$oninet"/>
          <xsl:value-of select="$access.end"/>
          <xsl:apply-templates mode="iso690.mode" select="./ulink"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="(./ulink)and(string(./ulink)!=string(.))">
      <xsl:value-of select="text()[1]"/>
      <xsl:call-template name="iso690.endsep">
        <xsl:with-param name="text" select="text()[1]"/>
        <xsl:with-param name="sep" select="$access.end"/>
      </xsl:call-template>
      <xsl:apply-templates mode="iso690.mode" select="./ulink"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="iso690.mode"/>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:value-of select="$access.sep"/>
</xsl:template>

<!-- Standard number - ISBN -->
<xsl:template name="iso690.isbn">
  <xsl:choose>
    <xsl:when test="./biblioid/@class='isbn'">
      <xsl:apply-templates mode="iso690.mode" select="./biblioid[@class='isbn']"/>
    </xsl:when>
    <xsl:when test="./isbn">
      <xsl:apply-templates mode="iso690.mode" select="./isbn"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="isbn|biblioid[@class='isbn']" mode="iso690.mode">
  <xsl:param name="isbn">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'isbn'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="stdnum.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'stdnum.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:value-of select="$isbn"/>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:value-of select="$stdnum.sep"/>
</xsl:template>

<!-- Standard number - ISSN -->
<xsl:template name="iso690.issn">
  <xsl:choose>
    <xsl:when test="./biblioid/@class='issn'">
      <xsl:apply-templates mode="iso690.mode" select="./biblioid[@class='issn']"/>
    </xsl:when>
    <xsl:when test="./issn">
      <xsl:apply-templates mode="iso690.mode" select="./issn"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="issn|biblioid[@class='issn']" mode="iso690.mode">
  <xsl:param name="issn">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'issn'"/></xsl:call-template>
  </xsl:param>
  <xsl:param name="stdnum.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'stdnum.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:value-of select="$issn"/>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:value-of select="$stdnum.sep"/>
</xsl:template>

<!-- Identification of patent document -->
<xsl:template name="iso690.pat.ident">
  <xsl:param name="patdate.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'patdate.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode" select="./address/country"/>
  <xsl:apply-templates mode="iso690.mode" select="./bibliomisc[@role='patenttype']"/>
  <xsl:choose>
    <xsl:when test="./biblioid[@class='other' and @otherclass='patentnum']">
      <xsl:apply-templates mode="iso690.mode" select="./biblioid[@class='other' and @otherclass='patentnum']"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates mode="iso690.mode" select="./bibliomisc[@role='patentnum']"/>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:apply-templates mode="iso690.mode" select="./pubdate[not(@role='issuing')]">
    <xsl:with-param name="sep" select="$patdate.sep"/>
  </xsl:apply-templates>
</xsl:template>

<!-- Country or issuing office -->
<xsl:template match="address/country" mode="iso690.mode">
  <xsl:param name="patcountry.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'patcountry.sep'"/></xsl:call-template>
  </xsl:param>
  <em xmlns:xslo="http://www.w3.org/1999/XSL/Transform">
    <xsl:apply-templates mode="iso690.mode"/>
  </em>
  <xsl:value-of select="$patcountry.sep"/>
</xsl:template>

<!-- Kind of patent document -->
<xsl:template match="bibliomisc[@role='patenttype']" mode="iso690.mode">
  <xsl:param name="pattype.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'pattype.sep'"/></xsl:call-template>
  </xsl:param>
  <em xmlns:xslo="http://www.w3.org/1999/XSL/Transform">
    <xsl:apply-templates mode="iso690.mode"/>
  </em>
  <xsl:value-of select="$pattype.sep"/>
</xsl:template>

<!-- Number -->
<xsl:template match="biblioid[@class='other' and @otherclass='patentnum']|bibliomisc[@role='patentnum']" mode="iso690.mode">
  <xsl:param name="patnum.sep">
    <xsl:call-template name="gentext.template"><xsl:with-param name="context" select="'iso690'"/><xsl:with-param name="name" select="'patnum.sep'"/></xsl:call-template>
  </xsl:param>
  <xsl:apply-templates mode="iso690.mode"/>
  <xsl:value-of select="$patnum.sep"/>
</xsl:template>

<!-- ==================================================================== -->
<!-- Supplementary templates -->

<xsl:template name="iso690.endsep">
  <xsl:param name="text"/>
  <xsl:param name="sep" select=". "/>
  <xsl:choose>
    <xsl:when test="substring($text,string-length($text))!=substring($sep,1,1)">
      <xsl:value-of select="$sep"/>
    </xsl:when>
    <xsl:when test="substring($text,string-length($text))=' '">
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="' '"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*" mode="iso690.mode">
  <xsl:apply-templates select="."/><!-- try the default mode -->
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
