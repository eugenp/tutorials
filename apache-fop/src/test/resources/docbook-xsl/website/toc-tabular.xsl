<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<xsl:output method="html"/>

<xsl:param name="nav.graphics" select="1"/>
<xsl:param name="nav.pointer" select="1"/>
<xsl:param name="nav.revisionflag" select="1"/>

<xsl:param name="toc.spacer.text">&#160;&#160;&#160;</xsl:param>
<xsl:param name="toc.spacer.image">graphics/blank.gif</xsl:param>

<xsl:param name="nav.icon.path">graphics/navicons/</xsl:param>
<xsl:param name="nav.icon.extension">.gif</xsl:param>

<!-- styles: folder, folder16, plusminus, triangle, arrow -->
<xsl:param name="nav.icon.style">triangle</xsl:param>

<xsl:param name="nav.text.spacer">&#160;</xsl:param>
<xsl:param name="nav.text.current.open">+</xsl:param>
<xsl:param name="nav.text.current.page">+</xsl:param>
<xsl:param name="nav.text.other.open">&#160;</xsl:param>
<xsl:param name="nav.text.other.closed">&#160;</xsl:param>
<xsl:param name="nav.text.other.page">&#160;</xsl:param>
<xsl:param name="nav.text.revisionflag.added">New</xsl:param>
<xsl:param name="nav.text.revisionflag.changed">Changed</xsl:param>
<xsl:param name="nav.text.revisionflag.deleted"></xsl:param>
<xsl:param name="nav.text.revisionflag.off"></xsl:param>

<xsl:param name="nav.text.pointer">&lt;-</xsl:param>

<xsl:param name="toc.expand.depth" select="1"/>

<!-- ==================================================================== --> 

<xsl:template match="toc/title|tocentry/title|titleabbrev">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="toc">
  <xsl:param name="pageid" select="@id"/>

  <xsl:variable name="relpath">
    <xsl:call-template name="toc-rel-path">
      <xsl:with-param name="pageid" select="$pageid"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="homebanner"
                select="/autolayout/config[@param='homebanner-tabular'][1]"/>

  <xsl:variable name="banner"
                select="/autolayout/config[@param='banner-tabular'][1]"/>

  <xsl:choose>
    <xsl:when test="$pageid = @id">
      <img align="left" border="0">
        <xsl:attribute name="src">
          <xsl:value-of select="$relpath"/>
          <xsl:value-of select="$homebanner/@value"/>
        </xsl:attribute>
        <xsl:attribute name="alt">
          <xsl:value-of select="$homebanner/@altval"/>
        </xsl:attribute>
      </img>
      <br clear="all"/>
      <br/>
    </xsl:when>
    <xsl:otherwise>
      <a href="{$relpath}{@dir}{$filename-prefix}{@filename}">
        <img align="left" border="0">
          <xsl:attribute name="src">
            <xsl:value-of select="$relpath"/>
            <xsl:value-of select="$banner/@value"/>
          </xsl:attribute>
          <xsl:attribute name="alt">
            <xsl:value-of select="$banner/@altval"/>
          </xsl:attribute>
        </img>
      </a>
      <br clear="all"/>
      <br/>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:apply-templates select="tocentry">
    <xsl:with-param name="pageid" select="$pageid"/>
    <xsl:with-param name="relpath" select="$relpath"/>
  </xsl:apply-templates>
  <br/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="tocentry">
  <xsl:param name="pageid" select="@id"/>
  <xsl:param name="toclevel" select="count(ancestor::*)"/>
  <xsl:param name="relpath" select="''"/>
  <xsl:param name="revisionflag" select="@revisionflag"/>

  <xsl:variable name="page" select="."/>
  <xsl:variable name="target"
                select="($page/descendant-or-self::tocentry[not(@tocskip) or @tocskip = '0']
                       |$page/following::tocentry[@tocskip and @tocskip='0'])[1]"/>

  <xsl:variable name="depth" select="count(ancestor::*)-1"/>

  <xsl:variable name="isdescendant">
    <xsl:choose>
      <xsl:when test="ancestor::*[@id=$pageid]">1</xsl:when>
      <xsl:otherwise>0</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="hasdescendant">
    <xsl:choose>
      <xsl:when test="descendant::tocentry != ''">1</xsl:when>
      <xsl:otherwise>0</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="isancestor">
    <xsl:choose>
      <xsl:when test="descendant::*[@id=$pageid]">1</xsl:when>
      <xsl:otherwise>0</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="use.toc.expand.depth">
    <xsl:variable name="config-param" select="ancestor::autolayout/config[@param='toc.expand.depth']/@value"/>
    <xsl:choose>
      <!-- toc.expand.depth attribute is not in DTD -->
      <xsl:when test="ancestor::toc/@toc.expand.depth">
        <xsl:value-of select="ancestor::toc/@toc.expand.depth"/>
      </xsl:when>
      <xsl:when test="floor($config-param) > 0">
        <xsl:value-of select="$config-param"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$toc.expand.depth"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="is.open">
    <xsl:choose>
      <xsl:when test="$pageid = @id
                      or $isancestor='1'
                      or $depth &lt; $use.toc.expand.depth">1</xsl:when>
      <xsl:otherwise>0</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <!-- For any entry in the TOC:
       1. It is the current page
          a. it is a leaf             current/leaf
          b. it is an open page       current/open
       2. It is not the current page
          a. it is a leaf             other/leaf
          b. it is an open page       other/open
          c. it is a closed page      other/closed
  -->

  <xsl:variable name="preceding-icon">
    <xsl:value-of select="$relpath"/>
    <xsl:value-of select="$nav.icon.path"/>
    <xsl:value-of select="$nav.icon.style"/>
    <xsl:choose>
      <xsl:when test="$pageid=@id">
        <xsl:choose>
          <xsl:when test="$hasdescendant != 0">
            <xsl:text>/current/open</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text>/current/leaf</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:choose>
          <xsl:when test="$hasdescendant = 0">
            <xsl:text>/other/leaf</xsl:text>
          </xsl:when>
          <xsl:when test="$is.open != 0">
            <xsl:text>/other/open</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text>/other/closed</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:value-of select="$nav.icon.extension"/>
  </xsl:variable>

  <xsl:variable name="preceding-text">
    <xsl:choose>
      <xsl:when test="$pageid=@id">
        <xsl:choose>
          <xsl:when test="$hasdescendant != 0">
            <xsl:value-of select="$nav.text.current.open"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$nav.text.current.page"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <xsl:choose>
          <xsl:when test="$isancestor != 0">
            <xsl:value-of select="$nav.text.other.open"/>
          </xsl:when>
          <xsl:when test="$hasdescendant != 0">
            <xsl:value-of select="$nav.text.other.closed"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$nav.text.other.page"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="following-icon">
    <xsl:value-of select="$relpath"/>
    <xsl:value-of select="$nav.icon.path"/>
    <xsl:value-of select="$nav.icon.style"/>
    <xsl:text>/current/pointer</xsl:text>
    <xsl:value-of select="$nav.icon.extension"/>
  </xsl:variable>

  <xsl:variable name="following-text">
    <xsl:value-of select="$nav.text.pointer"/>
  </xsl:variable>

  <xsl:variable name="revisionflag-icon">
    <xsl:value-of select="$relpath"/>
    <xsl:value-of select="$nav.icon.path"/>
    <xsl:value-of select="$nav.icon.style"/>
    <xsl:text>/</xsl:text>
    <xsl:value-of select="$revisionflag"/>
    <xsl:value-of select="$nav.icon.extension"/>
  </xsl:variable>

  <xsl:variable name="revisionflag-text">
    <xsl:choose>
      <xsl:when test="$revisionflag = 'changed'">
        <xsl:value-of select="$nav.text.revisionflag.changed"/>
      </xsl:when>
      <xsl:when test="$revisionflag = 'added'">
        <xsl:value-of select="$nav.text.revisionflag.added"/>
      </xsl:when>
      <xsl:when test="$revisionflag = 'deleted'">
        <xsl:value-of select="$nav.text.revisionflag.deleted"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$nav.text.revisionflag.off"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <span>
    <xsl:if test="$toclevel = 2">
      <xsl:attribute name="class">
        <xsl:text>toplevel</xsl:text>
      </xsl:attribute>
    </xsl:if>

    <xsl:if test="$toclevel &gt; 2">
      <xsl:attribute name="class">
        <xsl:text>shrink</xsl:text>
        <xsl:value-of select="$toclevel - 2"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:call-template name="insert.spacers">
      <xsl:with-param name="count" select="$toclevel - 1"/>
      <xsl:with-param name="relpath" select="$relpath"/>
    </xsl:call-template>

    <xsl:choose>
      <xsl:when test="$nav.graphics != 0">
        <xsl:call-template name="link.to.page">
          <xsl:with-param name="href" select="@href"/>
          <xsl:with-param name="page" select="$target"/>
          <xsl:with-param name="relpath" select="$relpath"/>
          <xsl:with-param name="linktext">
            <img src="{$preceding-icon}" alt="{$preceding-text}" border="0"/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$preceding-text"/>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:choose>
      <xsl:when test="$pageid = @id">
        <span class="curpage">
          <xsl:choose>
            <xsl:when test="titleabbrev">
              <xsl:apply-templates select="titleabbrev"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="title"/>
            </xsl:otherwise>
          </xsl:choose>

          <xsl:if test="$nav.revisionflag != '0' and $revisionflag">
            <xsl:value-of select="$nav.text.spacer"/>
            <xsl:choose>
              <xsl:when test="$nav.graphics = '1'">
                <img src="{$revisionflag-icon}" alt="{$revisionflag-text}" align="bottom"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>(</xsl:text>
                <xsl:value-of select="$revisionflag-text"/>
                <xsl:text>)</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
	  </xsl:if>

          <xsl:if test="$nav.pointer != '0'">
            <xsl:value-of select="$nav.text.spacer"/>
            <xsl:choose>
              <xsl:when test="$nav.graphics = '1'">
                <img src="{$following-icon}" alt="{$following-text}"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="$following-text"/>
              </xsl:otherwise>
            </xsl:choose>
	  </xsl:if>
        </span>
        <br/>
      </xsl:when>
      <xsl:otherwise>
        <span>
          <xsl:choose>
            <xsl:when test="$isdescendant='0'">
              <xsl:choose>
                <xsl:when test="$isancestor='1'">
                  <xsl:attribute name="class">ancestor</xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:attribute name="class">otherpage</xsl:attribute>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
              <!-- IS a descendant of curpage -->
              <xsl:attribute name="class">descendant</xsl:attribute>
            </xsl:otherwise>
          </xsl:choose>

          <xsl:call-template name="link.to.page">
            <xsl:with-param name="href" select="@href"/>
            <xsl:with-param name="page" select="$target"/>
            <xsl:with-param name="relpath" select="$relpath"/>
            <xsl:with-param name="linktext">
              <xsl:choose>
                <xsl:when test="titleabbrev">
                  <xsl:apply-templates select="titleabbrev"/>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:apply-templates select="title"/>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
          </xsl:call-template>

          <xsl:if test="$nav.revisionflag != '0' and $revisionflag">
            <xsl:value-of select="$nav.text.spacer"/>
            <xsl:choose>
              <xsl:when test="$nav.graphics = '1'">
                <img src="{$revisionflag-icon}" alt="{$revisionflag-text}"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>(</xsl:text>
                <xsl:value-of select="$revisionflag-text"/>
                <xsl:text>)</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
	  </xsl:if>

        </span>
        <br/>
      </xsl:otherwise>
    </xsl:choose>
  </span>

  <xsl:if test="$is.open != 0">
    <xsl:apply-templates select="tocentry">
      <xsl:with-param name="pageid" select="$pageid"/>
      <xsl:with-param name="relpath" select="$relpath"/>
    </xsl:apply-templates>
  </xsl:if>
</xsl:template>

<xsl:template name="insert.spacers">
  <xsl:param name="count" select="0"/>
  <xsl:param name="relpath"/>
  <xsl:if test="$count>0">
    <xsl:choose>
      <xsl:when test="$nav.graphics != 0">
        <img src="{$relpath}{$toc.spacer.image}" alt="{$toc.spacer.text}"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$toc.spacer.text"/>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:call-template name="insert.spacers">
      <xsl:with-param name="count" select="$count - 1"/>
      <xsl:with-param name="relpath" select="$relpath"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template match="toc|tocentry|notoc" mode="toc-rel-path">
  <xsl:call-template name="toc-rel-path"/>
</xsl:template>

<xsl:template name="toc-rel-path">
  <xsl:param name="pageid" select="@id"/>
  <xsl:variable name="entry" select="$autolayout//*[@id=$pageid]"/>
  <xsl:variable name="filename" select="concat($entry/@dir,$entry/@filename)"/>

  <xsl:variable name="slash-count">
    <xsl:call-template name="toc-directory-depth">
      <xsl:with-param name="filename" select="$filename"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="depth">
    <xsl:choose>
      <xsl:when test="starts-with($filename, '/')">
        <xsl:value-of select="$slash-count - 1"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$slash-count"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

<!--
  <xsl:message>
    <xsl:value-of select="$filename"/>
    <xsl:text> depth=</xsl:text>
    <xsl:value-of select="$depth"/>
  </xsl:message>
-->

  <xsl:if test="$depth > 0">
    <xsl:call-template name="copy-string">
      <xsl:with-param name="string">../</xsl:with-param>
      <xsl:with-param name="count" select="$depth"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="toc-directory-depth">
  <xsl:param name="filename"></xsl:param>
  <xsl:param name="count" select="0"/>

  <xsl:choose>
    <xsl:when test='contains($filename,"/")'>
      <xsl:call-template name="toc-directory-depth">
        <xsl:with-param name="filename"
                        select="substring-after($filename,'/')"/>
        <xsl:with-param name="count" select="$count + 1"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$count"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
