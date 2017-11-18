<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version='1.0'>

<!-- ********************************************************************
     $Id: block.xsl 9667 2012-11-26 23:10:44Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->
<!-- What should we do about styling blockinfo? -->

<xsl:template match="blockinfo|info">
  <!-- suppress -->
</xsl:template>

<!-- ==================================================================== -->

<xsl:template name="block.object">
  <div>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>
    <xsl:apply-templates/>
  </div>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="para">
  <xsl:call-template name="paragraph">
    <xsl:with-param name="class">
      <xsl:if test="@role and $para.propagates.style != 0">
        <xsl:value-of select="@role"/>
      </xsl:if>
    </xsl:with-param>
    <xsl:with-param name="content">
      <xsl:if test="position() = 1 and parent::listitem">
        <xsl:call-template name="anchor">
          <xsl:with-param name="node" select="parent::listitem"/>
        </xsl:call-template>
      </xsl:if>

      <xsl:call-template name="anchor"/>
      <xsl:apply-templates/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template name="paragraph">
  <xsl:param name="class" select="''"/>
  <xsl:param name="content"/>

  <xsl:variable name="p">
    <p>
      <xsl:call-template name="id.attribute"/>
      <xsl:choose>
        <xsl:when test="$class != ''">
          <xsl:call-template name="common.html.attributes">
            <xsl:with-param name="class" select="$class"/>
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="common.html.attributes">
            <xsl:with-param name="class" select="''"/>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:copy-of select="$content"/>
    </p>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$html.cleanup != 0">
      <xsl:call-template name="unwrap.p">
        <xsl:with-param name="p" select="$p"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$p"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="simpara">
  <!-- see also listitem/simpara in lists.xsl -->
  <p>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="locale.html.attributes"/>
    <xsl:if test="@role and $para.propagates.style != 0">
      <xsl:apply-templates select="." mode="class.attribute">
        <xsl:with-param name="class" select="@role"/>
      </xsl:apply-templates>
    </xsl:if>

    <xsl:call-template name="anchor"/>
    <xsl:apply-templates/>
  </p>
</xsl:template>

<xsl:template match="formalpara">
  <xsl:call-template name="paragraph">
    <xsl:with-param name="class">
      <xsl:if test="@role and $para.propagates.style != 0">
        <xsl:value-of select="@role"/>
      </xsl:if>
    </xsl:with-param>
    <xsl:with-param name="content">
      <xsl:call-template name="anchor"/>
      <xsl:apply-templates/>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<!-- Only use title from info -->
<xsl:template match="formalpara/info">
  <xsl:apply-templates select="title"/>
</xsl:template>

<xsl:template match="formalpara/title|formalpara/info/title">
  <xsl:variable name="titleStr">
      <xsl:apply-templates/>
  </xsl:variable>
  <xsl:variable name="lastChar">
    <xsl:if test="$titleStr != ''">
      <xsl:value-of select="substring($titleStr,string-length($titleStr),1)"/>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$make.clean.html != 0">
      <span class="formalpara-title">
        <xsl:copy-of select="$titleStr"/>
        <xsl:if test="$lastChar != ''
                      and not(contains($runinhead.title.end.punct, $lastChar))">
          <xsl:value-of select="$runinhead.default.title.end.punct"/>
        </xsl:if>
        <xsl:text>&#160;</xsl:text>
      </span>
    </xsl:when>
    <xsl:otherwise>
      <b>
        <xsl:copy-of select="$titleStr"/>
        <xsl:if test="$lastChar != ''
                      and not(contains($runinhead.title.end.punct, $lastChar))">
          <xsl:value-of select="$runinhead.default.title.end.punct"/>
        </xsl:if>
        <xsl:text>&#160;</xsl:text>
      </b>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="formalpara/para">
  <xsl:apply-templates/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="blockquote">
  <div>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>

    <xsl:choose>
      <xsl:when test="attribution">
        <table border="{$table.border.off}" class="blockquote">
          <xsl:if test="$css.decoration != 0">
            <xsl:attribute name="style">
              <xsl:text>width: 100%; cellspacing: 0; cellpadding: 0;</xsl:text>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="$div.element != 'section'">
            <xsl:attribute name="summary">Block quote</xsl:attribute>
          </xsl:if>
          <tr>
            <td width="10%" valign="top">&#160;</td>
            <td width="80%" valign="top">
              <xsl:apply-templates select="child::*[local-name(.)!='attribution']"/>
            </td>
            <td width="10%" valign="top">&#160;</td>
          </tr>
          <tr>
            <td width="10%" valign="top">&#160;</td>
            <td colspan="2" align="{$direction.align.end}" valign="top">
              <xsl:text>--</xsl:text>
              <xsl:apply-templates select="attribution"/>
            </td>
          </tr>
        </table>
      </xsl:when>
      <xsl:otherwise>
        <blockquote>
          <xsl:call-template name="common.html.attributes"/>
          <xsl:apply-templates/>
        </blockquote>
      </xsl:otherwise>
    </xsl:choose>
  </div>
</xsl:template>

<xsl:template match="blockquote/title|blockquote/info/title">
  <xsl:choose>
    <xsl:when test="$make.clean.html != 0">
      <div class="blockquote-title">
        <xsl:apply-templates/>
      </div>
    </xsl:when>
    <xsl:otherwise>
      <div class="blockquote-title">
        <p>
          <b>
            <xsl:apply-templates/>
          </b>
        </p>
      </div>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Use an em dash per Chicago Manual of Style and https://sourceforge.net/tracker/index.php?func=detail&aid=2793878&group_id=21935&atid=373747 -->
<xsl:template match="epigraph">
  <div>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates select="para|simpara|formalpara|literallayout"/>
    <xsl:if test="attribution">
      <div class="attribution">
        <span>&#x2014;<xsl:apply-templates select="attribution"/></span>
      </div>
    </xsl:if>
  </div>
</xsl:template>

<xsl:template match="attribution">
  <span>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:apply-templates/>
  </span>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="sidebar">
  <div>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <xsl:call-template name="anchor"/>
    <xsl:call-template name="sidebar.titlepage"/>
    <xsl:apply-templates/>
  </div>
</xsl:template>

<xsl:template match="abstract/title|sidebar/title">
</xsl:template>

<xsl:template match="sidebar/sidebarinfo|sidebar/info"/>

<xsl:template match="abstract">
  <div>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="anchor"/>
    <xsl:call-template name="formal.object.heading">
      <xsl:with-param name="title">
        <xsl:apply-templates select="." mode="title.markup">
          <xsl:with-param name="allow-anchors" select="'1'"/>
        </xsl:apply-templates>
      </xsl:with-param>
    </xsl:call-template>
    <xsl:apply-templates/>
  </div>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="msgset">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="msgentry">
  <xsl:call-template name="block.object"/>
</xsl:template>

<xsl:template match="simplemsgentry">
  <xsl:call-template name="block.object"/>
</xsl:template>

<xsl:template match="msg">
  <xsl:call-template name="block.object"/>
</xsl:template>

<xsl:template match="msgmain">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="msgmain/title">
  <xsl:choose>
    <xsl:when test="$make.clean.html != 0">
      <span class="msgmain-title">
        <xsl:apply-templates/>
      </span>
    </xsl:when>
    <xsl:otherwise>
      <b><xsl:apply-templates/></b>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="msgsub">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="msgsub/title">
  <xsl:choose>
    <xsl:when test="$make.clean.html != 0">
      <span class="msgsub-title">
        <xsl:apply-templates/>
      </span>
    </xsl:when>
    <xsl:otherwise>
      <b><xsl:apply-templates/></b>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="msgrel">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="msgrel/title">
  <xsl:choose>
    <xsl:when test="$make.clean.html != 0">
      <span class="msgrel-title">
        <xsl:apply-templates/>
      </span>
    </xsl:when>
    <xsl:otherwise>
      <b><xsl:apply-templates/></b>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="msgtext">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="msginfo">
  <xsl:call-template name="block.object"/>
</xsl:template>

<xsl:template match="msglevel">
  <xsl:choose>
    <xsl:when test="$make.clean.html != 0">
      <div class="msglevel">
        <span class="msglevel-title">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'msgset'"/>
            <xsl:with-param name="name" select="'MsgLevel'"/>
          </xsl:call-template>
        </span>
        <xsl:apply-templates/>
      </div>
    </xsl:when>
    <xsl:otherwise>
      <p>
        <b>
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'msgset'"/>
            <xsl:with-param name="name" select="'MsgLevel'"/>
          </xsl:call-template>
        </b>
        <xsl:apply-templates/>
      </p>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="msgorig">
  <xsl:choose>
    <xsl:when test="$make.clean.html != 0">
      <div class="msgorig">
        <span class="msgorig-title">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'msgset'"/>
            <xsl:with-param name="name" select="'MsgOrig'"/>
          </xsl:call-template>
        </span>
        <xsl:apply-templates/>
      </div>
    </xsl:when>
    <xsl:otherwise>
      <p>
        <b>
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'msgset'"/>
            <xsl:with-param name="name" select="'MsgOrig'"/>
          </xsl:call-template>
        </b>
        <xsl:apply-templates/>
      </p>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="msgaud">
  <xsl:choose>
    <xsl:when test="$make.clean.html != 0">
      <div class="msgaud">
        <span class="msgaud-title">
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'msgset'"/>
            <xsl:with-param name="name" select="'MsgAud'"/>
          </xsl:call-template>
        </span>
        <xsl:apply-templates/>
      </div>
    </xsl:when>
    <xsl:otherwise>
      <p>
        <b>
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="context" select="'msgset'"/>
            <xsl:with-param name="name" select="'MsgAud'"/>
          </xsl:call-template>
        </b>
        <xsl:apply-templates/>
      </p>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="msgexplan">
  <xsl:call-template name="block.object"/>
</xsl:template>

<xsl:template match="msgexplan/title">
  <xsl:choose>
    <xsl:when test="$make.clean.html != 0">
      <div class="msgexplan">
        <span class="msgexplan-title">
          <xsl:apply-templates/>
        </span>
      </div>
    </xsl:when>
    <xsl:otherwise>
      <p>
        <b>
          <xsl:apply-templates/>
        </b>
      </p>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="revhistory">
  <div>
    <xsl:call-template name="common.html.attributes"/>
    <xsl:call-template name="id.attribute"/>
    <table>
      <xsl:if test="$css.decoration != 0">
        <xsl:attribute name="style">
          <xsl:text>border-style:solid; width:100%;</xsl:text>
        </xsl:attribute>
      </xsl:if>
      <!-- include summary attribute if not HTML5 -->
      <xsl:if test="$div.element != 'section'">
        <xsl:attribute name="summary">
          <xsl:call-template name="gentext">
            <xsl:with-param name="key">revhistory</xsl:with-param>
          </xsl:call-template>
        </xsl:attribute>
      </xsl:if>
      <tr>
        <th align="{$direction.align.start}" valign="top" colspan="3">
          <b>
            <xsl:call-template name="gentext">
              <xsl:with-param name="key" select="'RevHistory'"/>
            </xsl:call-template>
          </b>
        </th>
      </tr>
      <xsl:apply-templates/>
    </table>
  </div>
</xsl:template>

<xsl:template match="revhistory/revision">
  <xsl:variable name="revnumber" select="revnumber"/>
  <xsl:variable name="revdate"   select="date"/>
  <xsl:variable name="revauthor" select="authorinitials|author"/>
  <xsl:variable name="revremark" select="revremark|revdescription"/>
  <tr>
    <td align="{$direction.align.start}">
      <xsl:if test="$revnumber">
        <xsl:call-template name="gentext">
          <xsl:with-param name="key" select="'Revision'"/>
        </xsl:call-template>
        <xsl:call-template name="gentext.space"/>
        <xsl:apply-templates select="$revnumber"/>
      </xsl:if>
    </td>
    <td align="{$direction.align.start}">
      <xsl:apply-templates select="$revdate"/>
    </td>
    <xsl:choose>
      <xsl:when test="count($revauthor)=0">
        <td align="{$direction.align.start}">
          <xsl:call-template name="dingbat">
            <xsl:with-param name="dingbat">nbsp</xsl:with-param>
          </xsl:call-template>
        </td>
      </xsl:when>
      <xsl:otherwise>
        <td align="{$direction.align.start}">
          <xsl:for-each select="$revauthor">
            <xsl:apply-templates select="."/>
            <xsl:if test="position() != last()">
              <xsl:text>, </xsl:text>
            </xsl:if>
          </xsl:for-each>
        </td>
      </xsl:otherwise>
    </xsl:choose>
  </tr>
  <xsl:if test="$revremark">
    <tr>
      <td align="{$direction.align.start}" colspan="3">
        <xsl:apply-templates select="$revremark"/>
      </td>
    </tr>
  </xsl:if>
</xsl:template>

<xsl:template match="revision/revnumber">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="revision/date">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="revision/authorinitials">
  <xsl:text>, </xsl:text>
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="revision/authorinitials[1]" priority="2">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="revision/revremark">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="revision/revdescription">
  <xsl:apply-templates/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="ackno|acknowledgements[parent::article]">
  <xsl:call-template name="block.object"/>
</xsl:template>

<!-- ==================================================================== -->

<xsl:template match="highlights">
  <xsl:call-template name="block.object"/>
</xsl:template>

<!-- ==================================================================== -->

</xsl:stylesheet>
