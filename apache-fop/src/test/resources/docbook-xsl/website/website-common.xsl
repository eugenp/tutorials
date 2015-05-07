<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:html='http://www.w3.org/1999/xhtml'
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                exclude-result-prefixes="doc html"
                version='1.0'>

<!-- ********************************************************************
     $Id: website-common.xsl 9396 2012-06-02 21:56:19Z bobstayton $
     ********************************************************************

     This file is part of the WebSite distribution.
     See ../README or http://nwalsh.com/website/ for copyright
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:import href="../html/docbook.xsl"/>
<xsl:import href="xbel.xsl"/>
<xsl:include href="../VERSION.xsl"/>
<xsl:include href="param.xsl"/>
<xsl:include href="head.xsl"/>
<xsl:include href="rss.xsl"/>
<xsl:include href="olink.xsl"/>

<xsl:preserve-space elements="*"/>
<xsl:strip-space elements="website webpage"/>

<xsl:output method="html"
            indent="no"/>

<!-- ==================================================================== -->

<xsl:template name="admon.graphic">
  <xsl:param name="node" select="."/>
  <xsl:call-template name="root-rel-path"/>
  <xsl:value-of select="$admon.graphics.path"/>
  <xsl:choose>
    <xsl:when test="name($node)='note'">note</xsl:when>
    <xsl:when test="name($node)='warning'">warning</xsl:when>
    <xsl:when test="name($node)='caution'">caution</xsl:when>
    <xsl:when test="name($node)='tip'">tip</xsl:when>
    <xsl:when test="name($node)='important'">important</xsl:when>
    <xsl:otherwise>note</xsl:otherwise>
  </xsl:choose>
  <xsl:value-of select="$admon.graphics.extension"/>
</xsl:template>

<doc:template name="admon.graphic">
<refpurpose>Select appropriate admonition graphic</refpurpose>
<refdescription>
<para>Selects the appropriate admonition graphic file and returns the
fully qualified path to it.</para>
</refdescription>
<refparam>
<variablelist>
<varlistentry><term>node</term>
<listitem>
<para>The source node to use for the purpose of selection. It should
be one of the admonition elements (<sgmltag>note</sgmltag>,
<sgmltag>warning</sgmltag>, etc.). The default node is the context
node.</para>
</listitem>
</varlistentry>
</variablelist>
</refparam>
<refreturns>
<para>The fully qualified path to the admonition graphic. If the
<varname>node</varname> is not an admonition element, the
  <quote>note</quote> graphic is returned.</para>
</refreturns>
</doc:template>

<!-- ==================================================================== -->

<xsl:template match="/">
  <xsl:apply-templates/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="allpages.banner"/>

<!-- ==================================================================== -->

<xsl:template name="webpage.table.footer"/>

<xsl:template name="webpage.footer">
  <xsl:variable name="page" select="."/>
  <xsl:variable name="footers" select="$page/config[@param='footer']
                                       |$page/config[@param='footlink']
                                       |$autolayout/autolayout/config[@param='footer']
                                       |$autolayout/autolayout/config[@param='footlink']"/>

  <xsl:variable name="tocentry" select="$autolayout//*[@id=$page/@id]"/>
  <xsl:variable name="toc" select="($tocentry/ancestor-or-self::toc[1]
                                   | $autolayout//toc[1])[last()]"/>

  <xsl:variable name="feedback">
    <xsl:choose>
      <xsl:when test="$page/config[@param='feedback.href']">
        <xsl:value-of select="($page/config[@param='feedback.href'])[1]/@value"/>
      </xsl:when>
      <xsl:when test="$autolayout/autolayout/config[@param='feedback.href']">
        <xsl:value-of select="($autolayout/autolayout/config[@param='feedback.href'])[1]/@value"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$feedback.href"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <div class="navfoot">
    <xsl:if test="$footer.hr != 0"><hr/></xsl:if>
    <table width="100%" border="0" summary="Footer navigation">
      <tr>
        <td width="33%" align="left">
          <span class="footdate">
            <xsl:call-template name="rcsdate.format">
              <xsl:with-param name="rcsdate"
                              select="$page/config[@param='rcsdate']/@value"/>
            </xsl:call-template>
          </span>
        </td>
        <td width="34%" align="center">
          <xsl:choose>
            <xsl:when test="not($toc)">
              <xsl:message>
                <xsl:text>Cannot determine TOC for </xsl:text>
                <xsl:value-of select="$page/@id"/>
              </xsl:message>
            </xsl:when>
            <xsl:when test="$toc/@id = $page/@id">
              <!-- nop; this is the home page -->
            </xsl:when>
            <xsl:otherwise>
              <span class="foothome">
                <a>
                  <xsl:attribute name="href">
                    <xsl:call-template name="homeuri"/>
                  </xsl:attribute>
                  <xsl:call-template name="gentext.nav.home"/>
                </a>
                <xsl:if test="$footers">
                  <xsl:text> | </xsl:text>
                </xsl:if>
              </span>
            </xsl:otherwise>
          </xsl:choose>

          <xsl:apply-templates select="$footers" mode="footer.link.mode"/>
        </td>
        <td width="33%" align="right">
            <xsl:choose>
              <xsl:when test="$feedback != ''">
                <span class="footfeed">
                  <a>
                    <xsl:choose>
                      <xsl:when test="$feedback.with.ids != 0">
                        <xsl:attribute name="href">
                          <xsl:value-of select="$feedback"/>
                          <xsl:value-of select="$page/@id"/>
                        </xsl:attribute>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:attribute name="href">
                          <xsl:value-of select="$feedback"/>
                        </xsl:attribute>
                      </xsl:otherwise>
                    </xsl:choose>
                    <xsl:value-of select="$feedback.link.text"/>
                  </a>
                </span>
              </xsl:when>
              <xsl:otherwise>&#160;</xsl:otherwise>
            </xsl:choose>
        </td>
      </tr>
      <tr>
        <td colspan="3" align="right">
          <span class="footcopy">
            <xsl:choose>
              <xsl:when test="head/copyright">
                <xsl:apply-templates select="head/copyright" mode="footer.mode"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:apply-templates mode="footer.mode"
                                     select="$autolayout/autolayout/copyright"/>
              </xsl:otherwise>
            </xsl:choose>
          </span>
        </td>
      </tr>
      <xsl:if test="$sequential.links != 0">
        <tr>
          <xsl:variable name="prev">
            <xsl:call-template name="prev.page"/>
          </xsl:variable>
          <xsl:variable name="next">
            <xsl:call-template name="next.page"/>
          </xsl:variable>
          <xsl:variable name="ptoc"
                        select="$autolayout/autolayout//*[$prev=@id]"/>
          <xsl:variable name="ntoc"
                        select="$autolayout/autolayout//*[$next=@id]"/>

          <td align="left" valign="top">
            <xsl:choose>
              <xsl:when test="$prev != ''">
                <xsl:call-template name="link.to.page">
                  <xsl:with-param name="frompage" select="$tocentry"/>
                  <xsl:with-param name="page" select="$ptoc"/>
                  <xsl:with-param name="linktext" select="'Prev'"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:otherwise>&#160;</xsl:otherwise>
            </xsl:choose>
          </td>
          <td>&#160;</td>
          <td align="right" valign="top">
            <xsl:choose>
              <xsl:when test="$next != ''">
                <xsl:call-template name="link.to.page">
                  <xsl:with-param name="frompage" select="$tocentry"/>
                  <xsl:with-param name="page" select="$ntoc"/>
                  <xsl:with-param name="linktext" select="'Next'"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:otherwise>&#160;</xsl:otherwise>
            </xsl:choose>
          </td>
        </tr>
      </xsl:if>
    </table>
  </div>
</xsl:template>

<xsl:template name="rcsdate.format">
  <xsl:param name="rcsdate" select="./config[@param='rcsdate']/@value"/>
  <xsl:value-of select="$rcsdate"/>
</xsl:template>

<xsl:template match="config" mode="footer.link.mode">
  <span class="foothome">
    <xsl:if test="position() &gt; 1">
      <xsl:text> | </xsl:text>
    </xsl:if>
    <xsl:choose>
      <xsl:when test="@param='footlink'">
        <xsl:variable name="id" select="@value"/>
        <xsl:variable name="tocentry"
                      select="$autolayout//*[@id=$id]"/>
        <xsl:if test="count($tocentry) != 1">
          <xsl:message>
            <xsl:text>Footlink to </xsl:text>
            <xsl:value-of select="$id"/>
            <xsl:text> does not id a unique page.</xsl:text>
          </xsl:message>
        </xsl:if>
        <xsl:variable name="dir">
          <xsl:choose>
            <xsl:when test="starts-with($tocentry/@dir, '/')">
              <xsl:value-of select="substring($tocentry/@dir, 2)"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="$tocentry/@dir"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <a>
          <xsl:attribute name="href">
            <xsl:call-template name="root-rel-path"/>
            <xsl:value-of select="$dir"/>
            <xsl:value-of select="$filename-prefix"/>
            <xsl:value-of select="$tocentry/@filename"/>
          </xsl:attribute>
          <xsl:value-of select="@altval"/>
        </a>
      </xsl:when>
      <xsl:otherwise>
        <a href="{@value}">
          <xsl:value-of select="@altval"/>
        </a>
      </xsl:otherwise>
    </xsl:choose>
  </span>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="homeuri">
  <xsl:param name="page" select="ancestor-or-self::webpage"/>
  <xsl:variable name="id" select="$page/@id"/>
  <xsl:variable name="tocentry"
                select="$autolayout//*[@id=$id]"/>
  <xsl:variable name="toc" select="$tocentry/ancestor::toc"/>
  <xsl:variable name="first-toc"
                select="$autolayout/autolayout/toc[1]"/>

  <xsl:call-template name="root-rel-path"/>
  <xsl:choose>
    <xsl:when test="$toc">
      <xsl:choose>
        <xsl:when test="starts-with($toc/@dir, '/')">
          <xsl:value-of select="substring($toc/@dir, 2)"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$toc/@dir"/>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:value-of select="$filename-prefix"/>
      <xsl:value-of select="$toc/@filename"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="starts-with($first-toc/@dir, '/')">
          <xsl:value-of select="substring($first-toc/@dir, 2)"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$first-toc/@dir"/>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:value-of select="$filename-prefix"/>
      <xsl:value-of select="$first-toc/@filename"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="copyright" mode="footer.mode">
  <span class="{name(.)}">
    <xsl:call-template name="gentext.element.name"/>
    <xsl:call-template name="gentext.space"/>
    <xsl:call-template name="dingbat">
      <xsl:with-param name="dingbat">copyright</xsl:with-param>
    </xsl:call-template>
    <xsl:call-template name="gentext.space"/>
    <xsl:apply-templates select="year" mode="footer.mode"/>
    <xsl:call-template name="gentext.space"/>
    <xsl:apply-templates select="holder" mode="footer.mode"/>
    <xsl:value-of select="$biblioentry.item.separator"/>
  </span>
</xsl:template>

<xsl:template match="year" mode="footer.mode">
  <xsl:apply-templates/><xsl:text>, </xsl:text>
</xsl:template>

<xsl:template match="year[position()=last()]" mode="footer.mode">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="holder" mode="footer.mode">
  <xsl:apply-templates/>
  <xsl:if test="position() != last()">, </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="config">
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="head">
</xsl:template>

<xsl:template match="head/title" mode="title.mode">
  <h1><xsl:apply-templates/></h1>
</xsl:template>

<xsl:template match="head/title">
  <xsl:apply-templates/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="directory-depth">
  <xsl:param name="dir"></xsl:param>
  <xsl:param name="count" select="0"/>

  <xsl:choose>
    <xsl:when test='contains($dir,"/")'>
      <xsl:call-template name="directory-depth">
        <xsl:with-param name="dir" select="substring-after($dir,'/')"/>
        <xsl:with-param name="count" select="$count + 1"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test='$dir=""'>
          <xsl:value-of select="$count"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$count + 1"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="root-rel-path">
  <xsl:param name="webpage" select="ancestor-or-self::webpage"/>
  <xsl:variable name="tocentry" select="$autolayout//*[$webpage/@id=@id]"/>
  <xsl:apply-templates select="$tocentry" mode="toc-rel-path"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="footnote" mode="footnote.number">
  <xsl:choose>
    <xsl:when test="ancestor::table|ancestor::informaltable">
      <xsl:number level="any" from="table|informaltable" format="a"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:number level="any" from="webpage" format="1"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="process.footnotes">
  <!-- we're only interested in footnotes that occur on this page, not
       on descendants of this page (which will be similarly processed) -->
  <xsl:variable name="thispage"
                select="ancestor-or-self::webpage"/>
  <xsl:variable name="footnotes"
                select=".//footnote[ancestor-or-self::webpage=$thispage]"/>
  <xsl:variable name="table.footnotes"
                select=".//table//footnote[ancestor-or-self::webpage=$thispage]
                        |.//informaltable//footnote[ancestor-or-self::webpage
                                    =$thispage]"/>

  <!-- Only bother to do this if there's at least one non-table footnote -->
  <xsl:if test="count($footnotes)>count($table.footnotes)">
    <div class="footnotes">
      <hr width="100" align="left"/>
      <xsl:apply-templates select="$footnotes" mode="process.footnote.mode"/>
    </div>
  </xsl:if>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="@*" mode="copy">
  <xsl:attribute name="{local-name(.)}">
    <xsl:value-of select="."/>
  </xsl:attribute>
</xsl:template>

<xsl:template match="html:*">
  <xsl:element name="{local-name(.)}" namespace="">
    <xsl:apply-templates select="@*" mode="copy"/>
    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="processing-instruction('php')">
  <xsl:processing-instruction name="php">
    <xsl:value-of select="."/>
  </xsl:processing-instruction>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="rddl:*" xmlns:rddl='http://www.rddl.org/'>
  <xsl:element name="{name(.)}">
    <xsl:apply-templates select="@*" mode="copy"/>
    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template match="section[@rddl]" xmlns:rddl='http://www.rddl.org/'>
  <xsl:variable name="rddl" select="id(@rddl)"/>
  <xsl:choose>
    <xsl:when test="local-name($rddl) != 'resource'">
      <xsl:message>
        <xsl:text>Warning: section rddl isn't an rddl:resource: </xsl:text>
        <xsl:value-of select="@rddl"/>
      </xsl:message>
      <xsl:apply-imports/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:element name="{name($rddl)}">
        <xsl:apply-templates select="$rddl/@*" mode="copy"/>
        <xsl:apply-imports/>
      </xsl:element>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="page.uri">
  <xsl:param name="href" select="''"/>
  <xsl:param name="page" select="ancestor-or-self::tocentry"/>
  <xsl:param name="relpath">
    <xsl:call-template name="toc-rel-path">
      <xsl:with-param name="pageid" select="$page/@id"/>
    </xsl:call-template>
  </xsl:param>

<!--
  <xsl:message><xsl:value-of select="$page/@id"/>: <xsl:value-of select="$relpath"/></xsl:message>
-->

  <xsl:variable name="dir">
    <xsl:choose>
      <xsl:when test="starts-with($page/@dir, '/')">
        <xsl:value-of select="substring($page/@dir, 2)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$page/@dir"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="html.href">
    <xsl:choose>
      <xsl:when test="$href != ''">
        <xsl:value-of select="$href"/>
      </xsl:when>
      <xsl:when test="$page/@href">
	<xsl:value-of select="$page/@href"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="concat($relpath,$dir,$filename-prefix)"/>
        <xsl:value-of select="$page/@filename"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:value-of select="$html.href"/>
</xsl:template>

<xsl:template name="link.to.page">
  <xsl:param name="href" select="''"/>
  <xsl:param name="frompage"/>
  <xsl:param name="page" select="ancestor-or-self::tocentry"/>
  <xsl:param name="relpath">
    <xsl:choose>
      <xsl:when test="$frompage">
        <xsl:call-template name="toc-rel-path">
          <xsl:with-param name="pageid" select="$frompage/@id"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="toc-rel-path">
          <xsl:with-param name="pageid" select="$page/@id"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:param>
  <xsl:param name="linktext" select="'???'"/>

  <a>
    <xsl:attribute name="href">
      <xsl:call-template name="page.uri">
        <xsl:with-param name="href" select="$href"/>
        <xsl:with-param name="page" select="$page"/>
        <xsl:with-param name="relpath" select="$relpath"/>
      </xsl:call-template>
    </xsl:attribute>
    <xsl:if test="summary">
      <xsl:attribute name="title">
        <xsl:value-of select="normalize-space(string(summary))"/>
      </xsl:attribute>
    </xsl:if>
    <xsl:copy-of select="$linktext"/>
  </a>
</xsl:template>

<xsl:template name="next.page">
  <xsl:param name="page" select="ancestor-or-self::webpage"/>
  <xsl:variable name="id" select="$page/@id"/>
  <xsl:variable name="tocentry"
                select="$autolayout//*[@id=$id]"/>
  <xsl:variable name="next-following"
                select="$tocentry/following::tocentry[1]"/>
  <xsl:variable name="next-child"
                select="$tocentry/descendant::tocentry[1]"/>

  <xsl:variable name="nextid">
    <xsl:choose>
      <xsl:when test="$next-child">
        <xsl:value-of select="$next-child/@id"/>
      </xsl:when>
      <xsl:when test="$next-following">
        <xsl:value-of select="$next-following/@id"/>
      </xsl:when>
      <xsl:otherwise></xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:value-of select="$nextid"/>
</xsl:template>

<xsl:template name="prev.page">
  <xsl:param name="page" select="ancestor-or-self::webpage"/>
  <xsl:variable name="id" select="$page/@id"/>
  <xsl:variable name="tocentry"
                select="$autolayout//*[@id=$id]"/>
  <xsl:variable name="prev-ancestor"
                select="($tocentry/ancestor::tocentry
                        |$tocentry/ancestor::toc)[last()]"/>
  <xsl:variable name="prev-sibling"
                select="$tocentry/preceding-sibling::tocentry[1]"/>

  <xsl:variable name="previd">
    <xsl:choose>
      <xsl:when test="$prev-sibling">
        <xsl:value-of select="$prev-sibling/@id"/>
      </xsl:when>
      <xsl:when test="$prev-ancestor">
        <xsl:value-of select="$prev-ancestor/@id"/>
      </xsl:when>
      <xsl:otherwise></xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:value-of select="$previd"/>
</xsl:template>

<xsl:template name="top.page">
  <xsl:param name="page" select="ancestor-or-self::webpage"/>
  <xsl:variable name="id" select="$page/@id"/>
  <xsl:variable name="tocentry"
                select="$autolayout//*[@id=$id]"/>

  <xsl:value-of select="$tocentry/ancestor::toc/@id"/>
</xsl:template>

<xsl:template name="up.page">
  <xsl:param name="page" select="ancestor-or-self::webpage"/>
  <xsl:variable name="id" select="$page/@id"/>
  <xsl:variable name="tocentry"
                select="$autolayout//*[@id=$id]"/>

  <xsl:choose>
    <xsl:when test="$tocentry/ancestor::tocentry">
      <xsl:value-of select="$tocentry/ancestor::tocentry[1]/@id"/>
    </xsl:when>
    <xsl:when test="$tocentry/ancestor::toc">
      <xsl:value-of select="$tocentry/ancestor::toc[1]/@id"/>
    </xsl:when>
    <xsl:otherwise></xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="first.page">
  <xsl:param name="page" select="ancestor-or-self::webpage"/>
  <xsl:variable name="id" select="$page/@id"/>
  <xsl:variable name="tocentry"
                select="$autolayout//*[@id=$id]"/>

  <xsl:value-of select="$tocentry/preceding-sibling::tocentry[last()]/@id"/>
</xsl:template>

<xsl:template name="last.page">
  <xsl:param name="page" select="ancestor-or-self::webpage"/>
  <xsl:variable name="id" select="$page/@id"/>
  <xsl:variable name="tocentry"
                select="$autolayout//*[@id=$id]"/>

  <xsl:variable name="prev-sibling"
                select="$tocentry/preceding-sibling::tocentry[1]"/>

  <xsl:value-of select="$tocentry/following-sibling::tocentry[last()]/@id"/>
</xsl:template>

<xsl:template match="autolayout" mode="collect.targets">
  <targetset>
    <xsl:apply-templates mode="olink.mode"/>
  </targetset>
</xsl:template>

<xsl:template match="toc|tocentry|notoc" mode="olink.mode">
    <xsl:text>&#10;</xsl:text>
    <xsl:call-template name="tocentry"/>
    <xsl:apply-templates select="tocentry" mode="olink.mode"/>
</xsl:template>


<xsl:template name="tocentry">
  <xsl:choose>
    <xsl:when test="@href">
      <!-- no op -->
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="not(@page)">
        <xsl:message terminate="yes">
          <xsl:text>All toc entries must have a page attribute.</xsl:text>
        </xsl:message>
      </xsl:if>
    
      <xsl:variable name="page" select="document(@page,.)"/>
    
      <xsl:if test="not($page/*[1]/@id)">
        <xsl:message terminate="yes">
          <xsl:value-of select="@page"/>
          <xsl:text>: missing ID.</xsl:text>
        </xsl:message>
      </xsl:if>
    
      <xsl:variable name="id" select="$page/*[1]/@id"/>
    
      <xsl:variable name="filename">
        <xsl:choose>
          <xsl:when test="@filename">
            <xsl:value-of select="$filename-prefix"/>
            <xsl:value-of select="@filename"/>
          </xsl:when>
          <xsl:when test="/layout/config[@param='default-filename']">
            <xsl:value-of select="$filename-prefix"/>
            <xsl:value-of select="(/layout/config[@param='default-filename'])[1]/@value"/>
          </xsl:when>
          <xsl:otherwise><xsl:value-of select="$filename-prefix"/>index.html</xsl:otherwise>
        </xsl:choose>
      </xsl:variable>
    
      <xsl:variable name="dir" select="@dir"/>
    
      <xsl:if test="$filename = ''">
        <xsl:message terminate="yes">
          <xsl:value-of select="@page"/>
          <xsl:text>: missing filename.</xsl:text>
        </xsl:message>
      </xsl:if>
    
    <!--
      <xsl:message>
        <xsl:value-of select="@page"/>
        <xsl:text>: </xsl:text>
        <xsl:if test="$dir != ''">
          <xsl:value-of select="$dir"/>
        </xsl:if>
        <xsl:value-of select="$filename"/>
      </xsl:message>
    -->
    
      <document>
        <xsl:attribute name="targetdoc">
          <xsl:value-of select="$id"/>
        </xsl:attribute>
        <xsl:attribute name="baseuri">
          <xsl:value-of select="$filename"/>
        </xsl:attribute>
        <xsl:if test="$dir != ''">
          <xsl:attribute name="dir">
            <xsl:value-of select="$dir"/>
          </xsl:attribute>
        </xsl:if>
    
        <xsl:apply-templates select="$page" mode="olink.mode"/>
      </document>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="webpage" mode="olink.mode">
  <xsl:call-template name="div"/>
</xsl:template>

<xsl:template match="webpage" mode="xref-to" >
  <xsl:param name="referrer"/>
  <xsl:param name="xrefstyle"/>

  <xsl:apply-templates select="." mode="object.xref.markup">
    <xsl:with-param name="purpose" select="'xref'"/>
    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
    <xsl:with-param name="referrer" select="$referrer"/>
  </xsl:apply-templates>
  <!-- FIXME: What about "in Chapter X"? -->
</xsl:template>

<xsl:template match="webpage" mode="title.markup">
  <xsl:param name="allow-anchors" select="0"/>
  <xsl:apply-templates select="head/title"
                       mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:param name="local.l10n.xml" select="document('')" />
<l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0"> 
  <l:l10n language="en">
    <l:context name="title">
      <l:template name="webpage" text="%t"/>
    </l:context>
    <l:context name="xref">
      <l:template name="webpage" text="%t"/>
    </l:context>
  </l:l10n>
  <l:l10n language="de">
    <l:context name="title">
      <l:template name="webpage" text="%t"/>
    </l:context>
    <l:context name="xref">
      <l:template name="webpage" text="%t"/>
    </l:context>
  </l:l10n>
  <l:l10n language="fr">
    <l:context name="title">
      <l:template name="webpage" text="%t"/>
    </l:context>
    <l:context name="xref">
      <l:template name="webpage" text="%t"/>
    </l:context>
  </l:l10n>
  <l:l10n language="es">
    <l:context name="title">
      <l:template name="webpage" text="%t"/>
    </l:context>
    <l:context name="xref">
      <l:template name="webpage" text="%t"/>
    </l:context>
  </l:l10n>
</l:i18n>
</xsl:stylesheet>
