<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version='1.0'>

<xsl:template name="add.annotation.links">
  <xsl:param name="scripts" select="normalize-space($annotation.js)"/>
  <xsl:choose>
    <xsl:when test="contains($scripts, ' ')">
      <script type="text/javascript" src="{substring-before($scripts, ' ')}"/>
      <xsl:call-template name="add.annotation.links">
        <xsl:with-param name="scripts" select="substring-after($scripts, ' ')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <script type="text/javascript" src="{$scripts}"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="annotation"/>

<xsl:template name="apply-annotations">
  <xsl:if test="$annotation.support != 0">
  <!-- do any annotations apply to the context node? -->
  <xsl:variable name="id" select="(@id|@xml:id)[1]"/>

  <xsl:variable name="aids">
    <xsl:for-each select="//annotation">
      <xsl:if test="@annotates=$id
                    or starts-with(@annotates, concat($id, ' '))
                    or contains(@annotates, concat(' ', $id, ' '))
                    or substring(@annotates, string-length(@annotates)-3)
                       = concat(' ', $id)">
        <xsl:value-of select="generate-id()"/>
        <xsl:text> </xsl:text>
      </xsl:if>
    </xsl:for-each>
    <xsl:if test="normalize-space(@annotations) != ''">
      <xsl:call-template name="annotations-pointed-to">
        <xsl:with-param name="annotations"
                        select="normalize-space(@annotations)"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>

  <xsl:if test="$aids != ''">
    <xsl:call-template name="apply-annotations-by-gid">
      <xsl:with-param name="gids" select="normalize-space($aids)"/>
    </xsl:call-template>
  </xsl:if>
  </xsl:if>
</xsl:template>

<xsl:template name="annotations-pointed-to">
  <xsl:param name="annotations"/>
  <xsl:choose>
    <xsl:when test="contains($annotations, ' ')">
      <xsl:variable name='a'
                    select="key('id', substring-before($annotations, ' '))"/>
      <xsl:if test="$a">
        <xsl:value-of select="generate-id($a)"/>
        <xsl:text> </xsl:text>
      </xsl:if>
      <xsl:call-template name="annotations-pointed-to">
        <xsl:with-param name="annotations"
                        select="substring-after($annotations, ' ')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name='a'
                    select="key('id', $annotations)"/>
      <xsl:if test="$a">
        <xsl:value-of select="generate-id($a)"/>
        <xsl:text> </xsl:text>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="apply-annotations-by-gid">
  <xsl:param name="gids"/>

  <xsl:choose>
    <xsl:when test="contains($gids, ' ')">
      <xsl:variable name="gid" select="substring-before($gids, ' ')"/>
      <xsl:apply-templates select="key('gid', $gid)"
                           mode="annotation-inline"/>
      <xsl:call-template name="apply-annotations-by-gid">
        <xsl:with-param name="gids"
                        select="substring-after($gids, ' ')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates select="key('gid', $gids)"
                           mode="annotation-inline"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="annotation" mode="annotation-inline">
  <xsl:variable name="title">
    <xsl:choose>
      <xsl:when test="title">
        <xsl:value-of select="title"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>[Annotation #</xsl:text>
        <xsl:number count="annotation" level="any" format="1"/>
        <xsl:text>]</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <a href="#annot-{generate-id(.)}" title="{$title}"
     name="anch-{generate-id(.)}" id="anch-{generate-id(.)}">
    <xsl:apply-templates select="." mode="class.attribute"/>
    <xsl:attribute name="onClick">
      <xsl:text>popup_</xsl:text>
      <xsl:value-of select="generate-id(.)"/>
      <xsl:text>.showPopup('anch-</xsl:text>
      <xsl:value-of select="generate-id(.)"/>
      <xsl:text>'); return false;</xsl:text>
    </xsl:attribute>
    <img src="{$annotation.graphic.open}" border="0" alt="{$title}"/>
  </a>
</xsl:template>

<xsl:template match="annotation" mode="annotation-popup">
  <div class="annotation-nocss">
    <p>
      <a name="annot-{generate-id(.)}"/>
      <xsl:text>Annotation #</xsl:text>
      <xsl:number count="annotation" level="any" format="1"/>
      <xsl:text>:</xsl:text>
    </p>
  </div>

  <div id="popup-{generate-id(.)}" class="annotation-popup">
    <xsl:if test="string-length(.) &gt; 300">
      <xsl:attribute name="style">width:400px</xsl:attribute>
    </xsl:if>

    <xsl:call-template name="annotation-title"/>
    <div class="annotation-body">
      <xsl:apply-templates select="*[local-name(.) != 'title']"/>
    </div>
    <div class="annotation-close">
      <a href="#" onclick="popup_{generate-id(.)}.hidePopup();return false;">
        <xsl:apply-templates select="." mode="class.attribute"/>
        <img src="{$annotation.graphic.close}" alt="X" border="0"/>
      </a>
    </div>
  </div>
</xsl:template>

<xsl:template name="annotation-title">
  <div class="annotation-title">
    <xsl:choose>
      <xsl:when test="title">
        <xsl:apply-templates select="title/node()"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>Annotation</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </div>
</xsl:template>

</xsl:stylesheet>
