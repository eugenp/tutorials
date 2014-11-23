<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version='1.0'>

<!-- ********************************************************************
     $Id: biblio.xsl 9297 2012-04-22 03:56:16Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:template match="bibliography">
  <xsl:call-template name="id.warning"/>

  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="1"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>

    <xsl:call-template name="bibliography.titlepage"/>

    <xsl:apply-templates/>

    <xsl:if test="not(parent::article)">
      <xsl:call-template name="process.footnotes"/>
    </xsl:if>
  </div>
</xsl:template>

<xsl:template match="bibliography/bibliographyinfo"></xsl:template>
<xsl:template match="bibliography/info"></xsl:template>
<xsl:template match="bibliography/title"></xsl:template>
<xsl:template match="bibliography/subtitle"></xsl:template>
<xsl:template match="bibliography/titleabbrev"></xsl:template>

<!-- ==================================================================== -->

<xsl:template match="bibliodiv">
  <xsl:call-template name="id.warning"/>

  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="0"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute">
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:apply-templates/>
  </div>
</xsl:template>

<xsl:template match="bibliodiv/title">
  <h3>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="anchor">
      <xsl:with-param name="node" select=".."/>
      <xsl:with-param name="conditional" select="0"/>
    </xsl:call-template>
    <xsl:apply-templates/>
  </h3>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="bibliolist">
  <div>
    <xsl:call-template name="common.html.attributes">
      <xsl:with-param name="inherit" select="0"/>
    </xsl:call-template>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>
    <xsl:if test="blockinfo/title|info/title|title">
      <xsl:call-template name="formal.object.heading"/>
    </xsl:if>
    <xsl:apply-templates select="*[not(self::blockinfo)
                                   and not(self::info)
                                   and not(self::title)
                                   and not(self::titleabbrev)
                                   and not(self::biblioentry)
                                   and not(self::bibliomixed)]"/>
    <xsl:apply-templates select="biblioentry|bibliomixed"/>
  </div>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="biblioentry">
  <xsl:param name="label">
    <xsl:call-template name="biblioentry.label"/>
  </xsl:param>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="string(.) = ''">
      <xsl:variable name="bib" select="document($bibliography.collection,.)"/>
      <xsl:variable name="entry" select="$bib/bibliography//
                                         *[@id=$id or @xml:id=$id][1]"/>
      <xsl:choose>
        <xsl:when test="$entry">
          <xsl:choose>
            <xsl:when test="$bibliography.numbered != 0">
              <xsl:apply-templates select="$entry">
                <xsl:with-param name="label" select="$label"/>
              </xsl:apply-templates>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="$entry"/>
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
          <div>
            <xsl:call-template name="common.html.attributes"/>
            <xsl:call-template name="id.attribute"/>
            <xsl:call-template name="anchor"/>
            <p>
              <xsl:copy-of select="$label"/>
              <xsl:text>Error: no bibliography entry: </xsl:text>
              <xsl:value-of select="$id"/>
              <xsl:text> found in </xsl:text>
              <xsl:value-of select="$bibliography.collection"/>
            </p>
          </div>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <div>
        <xsl:call-template name="common.html.attributes"/>
        <xsl:call-template name="id.attribute">
          <xsl:with-param name="conditional" select="0"/>
        </xsl:call-template>
        <xsl:call-template name="anchor">
          <xsl:with-param name="conditional" select="0"/>
        </xsl:call-template>
        <p>
          <xsl:copy-of select="$label"/>
	  <xsl:choose>
	    <xsl:when test="$bibliography.style = 'iso690'">
	      <xsl:call-template name="iso690.makecitation"/>
	    </xsl:when>
	    <xsl:otherwise>
	      <xsl:apply-templates mode="bibliography.mode"/>
	    </xsl:otherwise>
	  </xsl:choose>
        </p>
      </div>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="bibliomixed">
  <xsl:param name="label">
    <xsl:call-template name="biblioentry.label"/>
  </xsl:param>

  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="string(.) = ''">
      <xsl:variable name="bib" select="document($bibliography.collection,.)"/>
      <xsl:variable name="entry" select="$bib/bibliography//
                                         *[@id=$id or @xml:id=$id][1]"/>
      <xsl:choose>
        <xsl:when test="$entry">
          <xsl:choose>
            <xsl:when test="$bibliography.numbered != 0">
              <xsl:apply-templates select="$entry">
                <xsl:with-param name="label" select="$label"/>
              </xsl:apply-templates>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="$entry"/>
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
          <div>
            <xsl:call-template name="common.html.attributes"/>
            <xsl:call-template name="id.attribute"/>
            <xsl:call-template name="anchor"/>
            <p>
              <xsl:copy-of select="$label"/>
              <xsl:text>Error: no bibliography entry: </xsl:text>
              <xsl:value-of select="$id"/>
              <xsl:text> found in </xsl:text>
              <xsl:value-of select="$bibliography.collection"/>
            </p>
          </div>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <div>
        <xsl:call-template name="common.html.attributes"/>
        <xsl:call-template name="id.attribute">
          <xsl:with-param name="conditional" select="0"/>
        </xsl:call-template>
        <xsl:call-template name="anchor">
          <xsl:with-param name="conditional" select="0"/>
        </xsl:call-template>
        <p>
          <xsl:call-template name="common.html.attributes"/>
          <xsl:copy-of select="$label"/>
          <xsl:apply-templates mode="bibliomixed.mode"/>
        </p>
      </div>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="biblioentry.label">
  <xsl:param name="node" select="."/>

  <xsl:choose>
    <xsl:when test="$bibliography.numbered != 0">
      <xsl:text>[</xsl:text>
      <xsl:number from="bibliography" count="biblioentry|bibliomixed"
                  level="any" format="1"/>
      <xsl:text>] </xsl:text>
    </xsl:when>
    <xsl:when test="local-name($node/child::*[1]) = 'abbrev'">
      <xsl:text>[</xsl:text>
      <xsl:apply-templates select="$node/abbrev[1]"/>
      <xsl:text>] </xsl:text>
    </xsl:when>
    <xsl:when test="$node/@xreflabel">
      <xsl:text>[</xsl:text>
      <xsl:value-of select="$node/@xreflabel"/>
      <xsl:text>] </xsl:text>
    </xsl:when>
    <xsl:when test="$node/@id">
      <xsl:text>[</xsl:text>
      <xsl:value-of select="$node/@id"/>
      <xsl:text>] </xsl:text>
    </xsl:when>
    <xsl:when test="$node/@xml:id">
      <xsl:text>[</xsl:text>
      <xsl:value-of select="$node/@xml:id"/>
      <xsl:text>] </xsl:text>
    </xsl:when>
    <xsl:otherwise><!-- nop --></xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*" mode="bibliography.mode">
  <xsl:apply-templates select="."/><!-- try the default mode -->
</xsl:template>

<xsl:template match="abbrev" mode="bibliography.mode">
  <xsl:if test="preceding-sibling::*">
    <xsl:apply-templates mode="bibliography.mode"/>
  </xsl:if>
</xsl:template>

<xsl:template match="abstract" mode="bibliography.mode">
  <!-- suppressed -->
</xsl:template>

<xsl:template match="address" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="affiliation" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="shortaffil" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="jobtitle" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="artheader|articleinfo|info" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="artpagenums" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="author" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:choose>
      <xsl:when test="orgname">
        <xsl:apply-templates select="orgname" mode="bibliography.mode"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="person.name"/>
        <xsl:copy-of select="$biblioentry.item.separator"/>
      </xsl:otherwise>
    </xsl:choose>
  </span>
</xsl:template>

<xsl:template match="authorblurb|personblurb" mode="bibliography.mode">
  <!-- suppressed -->
</xsl:template>

<xsl:template match="authorgroup" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="person.name.list"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="authorinitials" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="bibliomisc" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="bibliomset" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<!-- ================================================== -->

<xsl:template match="biblioset" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
  </span>
</xsl:template>

<xsl:template match="biblioset/title|biblioset/citetitle" 
              mode="bibliography.mode">
  <xsl:variable name="relation" select="../@relation"/>
  <xsl:choose>
    <xsl:when test="$relation='article' or @pubwork='article'">
      <xsl:call-template name="gentext.startquote"/>
      <xsl:apply-templates/>
      <xsl:call-template name="gentext.endquote"/>
    </xsl:when>
    <xsl:otherwise>
      <i><xsl:apply-templates/></i>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:copy-of select="$biblioentry.item.separator"/>
</xsl:template>

<!-- ================================================== -->

<xsl:template match="citetitle" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:choose>
      <xsl:when test="@pubwork = 'article'">
        <xsl:call-template name="gentext.startquote"/>
        <xsl:call-template name="inline.charseq"/>
        <xsl:call-template name="gentext.endquote"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="inline.italicseq"/>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="collab" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="collabname" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="confgroup" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="confdates" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="conftitle" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="confnum" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="confsponsor" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="contractnum" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="contractsponsor" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="contrib" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<!-- ================================================== -->

<xsl:template match="copyright" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'Copyright'"/>
    </xsl:call-template>
    <xsl:call-template name="gentext.space"/>
    <xsl:call-template name="dingbat">
      <xsl:with-param name="dingbat">copyright</xsl:with-param>
    </xsl:call-template>
    <xsl:call-template name="gentext.space"/>
    <xsl:apply-templates select="year" mode="bibliography.mode"/>
    <xsl:if test="holder">
      <xsl:call-template name="gentext.space"/>
      <xsl:apply-templates select="holder" mode="bibliography.mode"/>
    </xsl:if>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="year" mode="bibliography.mode">
  <xsl:apply-templates/><xsl:text>, </xsl:text>
</xsl:template>

<xsl:template match="year[position()=last()]" mode="bibliography.mode">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="holder" mode="bibliography.mode">
  <xsl:apply-templates/>
</xsl:template>

<!-- ================================================== -->

<xsl:template match="corpauthor" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="corpcredit" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="corpname" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="date" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="edition" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="editor" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="person.name"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="firstname" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="honorific" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="indexterm" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="invpartnumber" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="isbn" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="issn" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="issuenum" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="lineage" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="orgname" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="orgdiv" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="othercredit" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="othername" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="pagenums" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="printhistory" mode="bibliography.mode">
  <!-- suppressed -->
</xsl:template>

<xsl:template match="productname" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="productnumber" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="pubdate" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="publisher" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
  </span>
</xsl:template>

<xsl:template match="publishername" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="pubsnumber" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="releaseinfo" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="revhistory" mode="bibliography.mode">
  <!-- suppressed; how could this be represented? -->
</xsl:template>

<xsl:template match="seriesinfo" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
  </span>
</xsl:template>

<xsl:template match="seriesvolnums" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="subtitle" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="surname" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="title" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <i><xsl:apply-templates mode="bibliography.mode"/></i>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="titleabbrev" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="volumenum" mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="bibliocoverage|biblioid|bibliorelation|bibliosource"
              mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliography.mode"/>
    <xsl:copy-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<!-- See FR #1934434 and http://doi.org -->
<xsl:template match="biblioid[@class='doi']"
              mode="bibliography.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <a href="{concat('http://dx.doi.org/', .)}">doi:<xsl:value-of select="."/></a>
  </span>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="*" mode="bibliomixed.mode">
  <xsl:apply-templates select="."/><!-- try the default mode -->
</xsl:template>

<xsl:template match="abbrev" mode="bibliomixed.mode">
  <xsl:if test="preceding-sibling::*">
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </xsl:if>
</xsl:template>

<xsl:template match="abstract" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="address" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="affiliation" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="shortaffil" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="jobtitle" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="artpagenums" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="author" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:choose>
      <xsl:when test="orgname">
        <xsl:apply-templates select="orgname" mode="bibliomixed.mode"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="person.name"/>
      </xsl:otherwise>
    </xsl:choose>
  </span>
</xsl:template>

<xsl:template match="authorblurb|personblurb" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="authorgroup" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="authorinitials" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="bibliomisc" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<!-- ================================================== -->

<xsl:template match="bibliomset" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="bibliomset/title|bibliomset/citetitle" 
              mode="bibliomixed.mode">
  <xsl:variable name="relation" select="../@relation"/>
  <xsl:choose>
    <xsl:when test="$relation='article' or @pubwork='article'">
      <xsl:call-template name="gentext.startquote"/>
      <xsl:apply-templates/>
      <xsl:call-template name="gentext.endquote"/>
    </xsl:when>
    <xsl:otherwise>
      <i><xsl:apply-templates/></i>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ================================================== -->

<xsl:template match="biblioset" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="citetitle" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:choose>
      <xsl:when test="@pubwork = 'article'">
        <xsl:call-template name="gentext.startquote"/>
        <xsl:call-template name="inline.charseq"/>
        <xsl:call-template name="gentext.endquote"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="inline.italicseq"/>
      </xsl:otherwise>
    </xsl:choose>
  </span>
</xsl:template>


<xsl:template match="collab" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="confgroup" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="contractnum" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="contractsponsor" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="contrib" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="copyright" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="corpauthor" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="corpcredit" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="corpname" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="date" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="edition" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="editor" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="firstname" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="honorific" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="indexterm" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="invpartnumber" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="isbn" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="issn" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="issuenum" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="lineage" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="orgname" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="othercredit" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="othername" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="pagenums" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="printhistory" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="productname" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="productnumber" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="pubdate" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="publisher" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="publishername" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="pubsnumber" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="releaseinfo" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="revhistory" mode="bibliomixed.mode">
  <!-- suppressed; how could this be represented? -->
</xsl:template>

<xsl:template match="seriesvolnums" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="subtitle" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="surname" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="title" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="titleabbrev" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="volumenum" mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<xsl:template match="bibliocoverage|biblioid|bibliorelation|bibliosource"
              mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates mode="bibliomixed.mode"/>
  </span>
</xsl:template>

<!-- See FR #1934434 and http://doi.org -->
<xsl:template match="biblioid[@class='doi']"
              mode="bibliomixed.mode">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <a href="{concat('http://dx.doi.org/', .)}">doi:<xsl:value-of select="."/></a>
  </span>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
