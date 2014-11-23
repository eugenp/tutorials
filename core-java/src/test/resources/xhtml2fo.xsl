<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xhtml="http://www.w3.org/1999/xhtml"
  exclude-result-prefixes="xhtml">
<xsl:output method="xml" version="1.0" standalone="yes" omit-xml-declaration="yes" 
  encoding="utf-8" media-type="text/xml" indent="yes"/>
<xsl:strip-space elements="*"/>
<xsl:preserve-space elements="xhtml:listing listing xhtml:plaintext plaintext xhtml:pre pre xhtml:samp samp"/>
<xsl:param name="font-size" select="''"/>
<xsl:param name="font.symbol" select="'Arial Unicode MS'"/>

<xsl:template name="common-atts">
  <xsl:copy-of select="@id|@color|@height|@width|@xml:lang"/>
  <xsl:if test="@align"><xsl:attribute name="text-align"><xsl:value-of select="@align"/></xsl:attribute></xsl:if>
  <xsl:if test="@nowrap"><xsl:attribute name="wrap-option">no-wrap</xsl:attribute></xsl:if>
</xsl:template>

<xsl:template match="xhtml:html|html">
  <fo:root>
    <fo:layout-master-set>
      <fo:simple-page-master master-name="page">
        <fo:region-body margin=".75in .75in .75in .75in"/>
        <fo:region-before extent=".5in"/>
        <fo:region-after extent=".5in"/>
      </fo:simple-page-master>
    </fo:layout-master-set>
    <fo:page-sequence master-reference="page">
      <fo:static-content flow-name="xsl-region-before">
        <fo:block display-align="after" padding-before=".2in" text-align="center" font-size="9pt">
          <xsl:apply-templates select="xhtml:head/xhtml:title|head/title"/>
        </fo:block>
      </fo:static-content>
      <fo:static-content flow-name="xsl-region-after">
        <fo:block display-align="before" text-align="center" font-size="8pt">
          <xsl:text>page </xsl:text>
          <fo:page-number/><xsl:text> of </xsl:text>
          <fo:page-number-citation ref-id="__END__"/>
        </fo:block>
      </fo:static-content>
      <xsl:apply-templates/>
    </fo:page-sequence>
  </fo:root>
</xsl:template>

<xsl:template match="xhtml:title|title">
  <fo:block><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:basefont|basefont">
  <xsl:copy-of select="@color"/>
  <xsl:choose>
    <xsl:when test="@size=1"><xsl:attribute name="font-size">xx-small</xsl:attribute></xsl:when>
    <xsl:when test="@size=2"><xsl:attribute name="font-size">x-small</xsl:attribute></xsl:when>
    <xsl:when test="@size=3"><xsl:attribute name="font-size">small</xsl:attribute></xsl:when>
    <xsl:when test="@size=4"><xsl:attribute name="font-size">medium</xsl:attribute></xsl:when>
    <xsl:when test="@size=5"><xsl:attribute name="font-size">large</xsl:attribute></xsl:when>
    <xsl:when test="@size=6"><xsl:attribute name="font-size">x-large</xsl:attribute></xsl:when>
    <xsl:when test="@size=7"><xsl:attribute name="font-size">xx-large</xsl:attribute></xsl:when>
  </xsl:choose>
  <xsl:if test="@face"><xsl:attribute name="font-family"><xsl:value-of select="@face"/></xsl:attribute></xsl:if>
</xsl:template>

<xsl:template match="xhtml:body|body">
  <fo:flow flow-name="xsl-region-body">
    <xsl:call-template name="common-atts"/>
    <xsl:apply-templates select="//basefont[1]"/>
    <xsl:if test="$font-size"><xsl:attribute name="font-size"><xsl:value-of select="$font-size"/></xsl:attribute></xsl:if>
    <xsl:apply-templates/>
    <fo:block id="__END__"/>
  </fo:flow>
</xsl:template>

<xsl:template match="xhtml:head|head|xhtml:applet|applet|xhtml:area|area|xhtml:base|base
  |xhtml:bgsound|bgsound|xhtml:embed|embed|xhtml:frame|frame|xhtml:frameset|frameset|xhtml:iframe|iframe
  |xhtml:ilayer|ilayer|xhtml:layer|layer|xhtml:input[@type='hidden']|input[@type='hidden']
  |xhtml:isindex|isindex|xhtml:link|link|xhtml:map|map|xhtml:meta|meta|xhtml:object|object|xhtml:param|param
  |xhtml:ruby|ruby|xhtml:rt|rt|xhtml:script|script|xhtml:spacer|spacer|xhtml:style|style|xhtml:wbr|wbr
  |xhtml:xml|xml|xhtml:xmp|xmp"/>
<xsl:template match="comment">
  <xsl:comment><xsl:apply-templates/></xsl:comment>
</xsl:template>
<xsl:template match="processing-instruction()">
  <xsl:copy-of select="."/>
</xsl:template>

<!-- Links and Media -->

<xsl:template match="xhtml:a|a">
  <fo:inline><xsl:call-template name="common-atts"/>
    <xsl:if test="@name and not(@id)">
      <xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute>
    </xsl:if>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:a[@href]|a[@href]">
  <fo:basic-link color="blue" text-decoration="underline">
    <xsl:if test="@type"><xsl:attribute name="content-type"><xsl:value-of select="@type"/></xsl:attribute></xsl:if>
    <xsl:choose>
      <xsl:when test="starts-with(@href,'#')">
        <xsl:attribute name="internal-destination"><xsl:value-of select="substring-after(@href,'#')"/></xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="external-destination">
          <xsl:text>url(&apos;</xsl:text>
          <xsl:value-of select="concat(//base/@href,@href)"/>
          <xsl:text>&apos;)</xsl:text>
        </xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:basic-link>
</xsl:template>

<xsl:template match="xhtml:img|img|xhtml:input[@type='image']|input[@type='image']">
  <fo:external-graphic content-type="{@type}" src="{concat(//base/@href,@src)}">
    <xsl:call-template name="common-atts"/>
  </fo:external-graphic>
</xsl:template>

<xsl:template match="xhtml:object[starts-with(@type,'image/')]|object[starts-with(@type,'image/')]">
  <fo:external-graphic content-type="{@type}" src="{concat(//base/@href,@data)}">
    <xsl:call-template name="common-atts"/>
  </fo:external-graphic>
</xsl:template>

<!-- Tables -->

<xsl:template match="xhtml:table">
  <xsl:apply-templates select="caption"/>
  <fo:table width="100%"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates select="colgroup|col"/>
    <xsl:variable name="tr1" select="(xhtml:tr|xhtml:thead/xhtml:tr|xhtml:tbody/xhtml:tr|xhtml:tfoot/xhtml:tr)[1]"/>
    <xsl:variable name="cols" select="xhtml:colgroup/xhtml:col|xhtml:col"/>
    <xsl:call-template name="mock-col">
      <xsl:with-param name="cols" select="(count($tr1/xhtml:*[not(@colspan)])+sum($tr1/xhtml:*/@colspan))
        -(count($cols[not(@colspan)])+sum($cols/@colspan))"/>
    </xsl:call-template>
    <xsl:apply-templates select="xhtml:thead|xhtml:tfoot|xhtml:tbody"/>
    <xsl:if test="xhtml:tr">
      <fo:table-body><xsl:call-template name="common-atts"/>
        <xsl:apply-templates select="xhtml:tr"/>
      </fo:table-body>
    </xsl:if>
  </fo:table>
</xsl:template>

<xsl:template match="table">
  <xsl:apply-templates select="caption"/>
  <fo:table width="100%"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates select="colgroup|col"/>
    <xsl:variable name="tr1" select="(tr|thead/tr|tbody/tr|tfoot/tr)[1]"/>
    <xsl:variable name="cols" select="colgroup/col|col"/>
    <xsl:call-template name="mock-col">
      <xsl:with-param name="cols" select="(count($tr1/*[not(@colspan)])+sum($tr1/*/@colspan))
        -(count($cols[not(@colspan)])+sum($cols/@colspan))"/>
    </xsl:call-template>
    <xsl:apply-templates select="thead|tfoot|tbody"/>
    <xsl:if test="tr">
      <fo:table-body><xsl:call-template name="common-atts"/>
        <xsl:apply-templates select="tr"/>
      </fo:table-body>
    </xsl:if>
  </fo:table>
</xsl:template>

<xsl:template match="xhtml:colgroup|colgroup">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template name="mock-col">
  <xsl:param name="cols" select="1"/>
  <xsl:if test="$cols&gt;0">
    <fo:table-column column-width="proportional-column-width(1)"/>
    <xsl:call-template name="mock-col">
      <xsl:with-param name="cols" select="$cols -1"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template match="xhtml:col|col">
  <fo:table-column><xsl:call-template name="common-atts"/>
    <xsl:if test="@span">
      <xsl:attribute name="number-columns-spanned"><xsl:value-of select="@span"/></xsl:attribute>
    </xsl:if>
    <xsl:choose>
      <xsl:when test="@width">
        <xsl:attribute name="column-width"><xsl:value-of select="@width"/></xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="column-width">proportional-column-width(1)</xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
  </fo:table-column>
</xsl:template>

<xsl:template match="xhtml:tbody|tbody">
  <fo:table-body><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:table-body>
</xsl:template>

<xsl:template match="xhtml:thead|thead">
  <fo:table-header><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:table-header>
</xsl:template>

<xsl:template match="xhtml:tfoot|tfoot">
  <fo:table-footer><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:table-footer>
</xsl:template>

<xsl:template match="xhtml:tr|tr">
  <fo:table-row><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:table-row>
</xsl:template>

<xsl:template match="xhtml:th|th">
  <fo:table-cell font-weight="bold" padding=".1em"><xsl:call-template name="common-atts"/>
    <xsl:if test="@colspan">
      <xsl:attribute name="number-columns-spanned"><xsl:value-of select="@colspan"/></xsl:attribute>
    </xsl:if>
    <xsl:if test="@rowspan">
      <xsl:attribute name="number-rows-spanned"><xsl:value-of select="@rowspan"/></xsl:attribute>
    </xsl:if>
    <fo:block>
      <xsl:if test="parent::xhtml:tr/parent::xhtml:thead|parent::tr/parent::thead">
        <xsl:attribute name="text-align">center</xsl:attribute>
      </xsl:if>
      <xsl:apply-templates/>
    </fo:block>
  </fo:table-cell>
</xsl:template>

<xsl:template match="xhtml:td|td">
  <fo:table-cell padding=".1em"><xsl:call-template name="common-atts"/>
    <xsl:if test="@colspan">
      <xsl:attribute name="number-columns-spanned"><xsl:value-of select="@colspan"/></xsl:attribute>
    </xsl:if>
    <xsl:if test="@rowspan">
      <xsl:attribute name="number-rows-spanned"><xsl:value-of select="@rowspan"/></xsl:attribute>
    </xsl:if>
    <fo:block>
      <xsl:apply-templates/>
    </fo:block>
  </fo:table-cell>
</xsl:template>

<!-- Lists -->

<xsl:template match="xhtml:dd|dd">
  <fo:list-item><xsl:call-template name="common-atts"/>
    <fo:list-item-label><fo:block/></fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates/>
      </fo:block>
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<xsl:template match="xhtml:dl|dl">
  <fo:list-block provisional-label-separation=".2em" provisional-distance-between-starts="3em">
    <xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:list-block>
</xsl:template>

<xsl:template match="xhtml:dt|dt">
  <fo:list-item><xsl:call-template name="common-atts"/>
    <fo:list-item-label><fo:block/></fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates/>
      </fo:block>
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<xsl:template match="xhtml:ol|ol">
  <fo:list-block provisional-label-separation=".2em"
    provisional-distance-between-starts="{string-length(count(li))*.9+.6}em">
    <xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:list-block>
</xsl:template>

<xsl:template match="xhtml:ol/xhtml:li|ol/li">
  <fo:list-item><xsl:call-template name="common-atts"/>
    <fo:list-item-label end-indent="label-end()">
      <fo:block text-align="end">
        <xsl:variable name="value">
          <xsl:choose>
            <xsl:when test="@value"><xsl:value-of select="@value"/></xsl:when>
            <xsl:otherwise><xsl:number/></xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <xsl:choose>
          <xsl:when test="@type='I'"><xsl:number format="I" value="$value"/></xsl:when>
          <xsl:when test="@type='A'"><xsl:number format="A" value="$value"/></xsl:when>
          <xsl:when test="@type='i'"><xsl:number format="i" value="$value"/></xsl:when>
          <xsl:when test="@type='a'"><xsl:number format="a" value="$value"/></xsl:when>
          <xsl:when test="parent::xhtml:ol/@type='I' or parent::ol/@type='I'"><xsl:number format="I" value="$value"/></xsl:when>
          <xsl:when test="parent::xhtml:ol/@type='A' or parent::ol/@type='I'"><xsl:number format="A" value="$value"/></xsl:when>
          <xsl:when test="parent::xhtml:ol/@type='i' or parent::ol/@type='I'"><xsl:number format="i" value="$value"/></xsl:when>
          <xsl:when test="parent::xhtml:ol/@type='a' or parent::ol/@type='I'"><xsl:number format="a" value="$value"/></xsl:when>
          <xsl:otherwise><xsl:number format="1" value="$value"/></xsl:otherwise>
        </xsl:choose>
        <xsl:text>.</xsl:text>
      </fo:block>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates/>
      </fo:block>
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<xsl:template match="xhtml:ul|ul|xhtml:menu|menu">
  <fo:list-block provisional-label-separation=".2em" provisional-distance-between-starts="1.6em">
    <xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:list-block>
</xsl:template>

<xsl:template match="xhtml:ul/xhtml:li|ul/li|xhtml:menu/xhtml:li|menu/li">
  <fo:list-item><xsl:call-template name="common-atts"/>
    <fo:list-item-label end-indent="label-end()">
      <fo:block text-align="end">
        <fo:inline font-family="{$font.symbol}">
          <xsl:choose>
            <xsl:when test="@type='square'"><xsl:text disable-output-escaping="yes">&amp;#x25AA;</xsl:text></xsl:when>
            <xsl:when test="@type='circle'"><xsl:text disable-output-escaping="yes">&amp;#x25CB;</xsl:text></xsl:when>
            <xsl:when test="parent::xhtml:ul/@type='square' or parent::ul/@type='square'">
              <xsl:text disable-output-escaping="yes">&amp;#x25AA;</xsl:text>
            </xsl:when>
            <xsl:when test="parent::xhtml:ul/@type='circle' or parent::ul/@type='square'">
              <xsl:text disable-output-escaping="yes">&amp;#x25CB;</xsl:text>
            </xsl:when>
            <xsl:otherwise><xsl:text disable-output-escaping="yes">&amp;#x2022;</xsl:text></xsl:otherwise>
          </xsl:choose>
        </fo:inline>
      </fo:block>
    </fo:list-item-label>
    <fo:list-item-body start-indent="body-start()">
      <fo:block>
        <xsl:apply-templates/>
      </fo:block>
    </fo:list-item-body>
  </fo:list-item>
</xsl:template>

<!-- Blocks -->

<xsl:template match="xhtml:address|address">
  <fo:block font-style="italic"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:blockquote|blockquote">
  <fo:block space-before="1em" space-after="1em" start-indent="3em" end-indent="3em">
    <xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:br|br">
  <fo:block white-space="pre"><xsl:call-template name="common-atts"/>
    <xsl:text disable-output-escaping="yes">&amp;#10;</xsl:text>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:caption|caption">
  <fo:block keep-with-next="always" text-align="center">
    <xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:center|center">
  <fo:block text-align="center">
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:div|div|xhtml:multicol|multicol|xhtml:noembed|noembed|xhtml:noframes|noframes
  |xhtml:nolayer|nolayer|xhtml:noscript|noscript">
  <fo:block><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:h1|h1">
  <fo:block font-size="180%" font-weight="bold"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:h2|h2">
  <fo:block font-size="160%" font-weight="bold"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:h3|h3">
  <fo:block font-size="140%" font-weight="bold"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:h4|h4">
  <fo:block font-size="120%" font-weight="bold"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:h5|h5">
  <fo:block font-size="110%" font-weight="bold"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:h6|h6|xhtml:legend|legend">
  <fo:block font-weight="bold"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:hr|hr">
  <fo:leader leader-pattern="rule" rule-style="groove">
    <xsl:if test="@size">
      <xsl:attribute name="rule-thickness"><xsl:value-of select="@size"/><xsl:text>pt</xsl:text></xsl:attribute>
    </xsl:if>
  </fo:leader>
</xsl:template>

<xsl:template match="xhtml:listing|listing|xhtml:plaintext|plaintext|xhtml:pre|pre|xhtml:samp|samp">
  <fo:block white-space="pre"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:p|p">
  <fo:block space-before=".6em" space-after=".6em"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<!-- Inlines -->

<xsl:template match="xhtml:abbr|abbr|xhtml:acronym|acronym">
  <fo:inline><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
  <xsl:text> (</xsl:text>
  <xsl:value-of select="@title"/>
  <xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="xhtml:b|b|xhtml:strong|strong">
  <fo:inline font-weight="bold"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:bdo|bdo">
  <fo:bidi-override direction="{@dir}"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:bidi-override>
</xsl:template>

<xsl:template match="xhtml:big|big">
  <fo:inline font-size="larger"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:blink|blink|xhtml:marquee|marquee">
  <fo:inline background-color="yellow"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:cite|cite|xhtml:dfn|dfn|xhtml:em|em|xhtml:i|i|xhtml:var|var">
  <fo:inline font-style="italic"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:code|code|xhtml:kbd|kbd|xhtml:tt|tt">
  <fo:inline font-family="monospace"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:del|del|xhtml:s|s|xhtml:strike|strike">
  <fo:inline text-decoration="line-through"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:font|font">
  <fo:inline><xsl:call-template name="common-atts"/>
    <xsl:choose>
      <xsl:when test="@size=1"><xsl:attribute name="font-size">xx-small</xsl:attribute></xsl:when>
      <xsl:when test="@size=2"><xsl:attribute name="font-size">x-small</xsl:attribute></xsl:when>
      <xsl:when test="@size=3"><xsl:attribute name="font-size">small</xsl:attribute></xsl:when>
      <xsl:when test="@size=4"><xsl:attribute name="font-size">medium</xsl:attribute></xsl:when>
      <xsl:when test="@size=5"><xsl:attribute name="font-size">large</xsl:attribute></xsl:when>
      <xsl:when test="@size=6"><xsl:attribute name="font-size">x-large</xsl:attribute></xsl:when>
      <xsl:when test="@size=7"><xsl:attribute name="font-size">xx-large</xsl:attribute></xsl:when>
    </xsl:choose>
    <xsl:if test="@face"><xsl:attribute name="font-family"><xsl:value-of select="@face"/></xsl:attribute></xsl:if>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:ins|ins|xhtml:u|u">
  <fo:inline text-decoration="underline"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:nowrap|nowrap">
  <fo:inline wrap-option="no-wrap"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:q|q">
  <fo:inline><xsl:call-template name="common-atts"/>
    <xsl:text disable-output-escaping="yes">&amp;#x201C;</xsl:text>
    <xsl:apply-templates/>
    <xsl:text disable-output-escaping="yes">&amp;#x201D;</xsl:text>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:q|q[starts-with(.,'&#x22;') or starts-with(.,'&#x201C;') or starts-with(.,'&#x201F;')]">
  <fo:inline><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:small|small">
  <fo:inline font-size="smaller"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:span|span">
  <fo:inline><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:sub|sub">
  <fo:inline baseline-shift="sub"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:sup|sup">
  <fo:inline baseline-shift="super"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<!-- Forms -->

<xsl:template match="xhtml:button|button">
  <fo:block background-color="silver" border="3pt outset silver" text-align="center" width="auto">
    <xsl:call-template name="common-atts"/>
    <xsl:text> </xsl:text>
    <xsl:apply-templates/>
    <xsl:text> </xsl:text>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:fieldset|fieldset">
  <fo:block border="1pt groove gray"><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:form|form">
  <fo:block><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:input|input"><!-- default input is text (also handles password & file) -->
  <fo:leader leader-pattern="rule"><xsl:call-template name="common-atts"/>
    <xsl:attribute name="leader-length">
      <xsl:choose>
        <xsl:when test="@size"><xsl:value-of select="@size"/><xsl:text>em</xsl:text></xsl:when>
        <xsl:otherwise>10em</xsl:otherwise>
      </xsl:choose>
    </xsl:attribute>
  </fo:leader>
</xsl:template>

<xsl:template match="xhtml:input[@type='checkbox']|input[@type='checkbox']">
  <fo:inline font-family="{$font.symbol}" font-size="larger"><xsl:call-template name="common-atts"/>
    <xsl:choose>
      <xsl:when test="@checked"><xsl:text disable-output-escaping="yes">&amp;#x2611;</xsl:text></xsl:when>
      <xsl:otherwise><xsl:text disable-output-escaping="yes">&amp;#x2610;</xsl:text></xsl:otherwise>
    </xsl:choose>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:input[@type='radio']|input[@type='radio']">
  <fo:inline font-family="{$font.symbol}" font-size="larger"><xsl:call-template name="common-atts"/>
    <xsl:choose>
      <xsl:when test="@checked"><xsl:text disable-output-escaping="yes">&amp;#x25C9;</xsl:text></xsl:when>
      <xsl:otherwise><xsl:text disable-output-escaping="yes">&amp;#x25CB;</xsl:text></xsl:otherwise>
    </xsl:choose>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:input[@type='button' or @type='submit' or @type='reset']
  |input[@type='button' or @type='submit' or @type='reset']">
  <fo:block background-color="silver" border="3pt outset silver" text-align="center" width="auto">
    <xsl:call-template name="common-atts"/>
    <xsl:text> </xsl:text>
    <xsl:choose>
      <xsl:when test="@value"><xsl:value-of select="@value"/></xsl:when>
      <xsl:otherwise><xsl:value-of select="@type"/></xsl:otherwise>
    </xsl:choose>
    <xsl:text> </xsl:text>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:label|label">
  <fo:inline><xsl:call-template name="common-atts"/>
    <xsl:apply-templates/>
  </fo:inline>
</xsl:template>

<xsl:template match="xhtml:select[@size=1]|select[@size=1]">
  <fo:leader leader-pattern="rule" leader-length="10em">
    <xsl:call-template name="common-atts"/>
  </fo:leader>
</xsl:template>

<xsl:template match="xhtml:select|select">
  <fo:block><xsl:call-template name="common-atts"/>
    <xsl:if test="@size">
      <xsl:attribute name="height"><xsl:value-of select="@size"/><xsl:text>em</xsl:text></xsl:attribute>
    </xsl:if>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:optgroup|optgroup">
  <fo:block font-style="italic" font-weight="bold"><xsl:call-template name="common-atts"/>
    <xsl:value-of select="@label"/>
  </fo:block>
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="xhtml:option|option">
  <fo:block><xsl:call-template name="common-atts"/>
    <xsl:if test="parent::xhtml:optgroup|parent::optgroup">
      <xsl:attribute name="start-indent">1em</xsl:attribute>
    </xsl:if>
    <xsl:choose>
      <xsl:when test="@label"><xsl:value-of select="@label"/></xsl:when>
      <xsl:otherwise><xsl:apply-templates/></xsl:otherwise>
    </xsl:choose>
  </fo:block>
</xsl:template>

<xsl:template match="xhtml:textarea|textarea">
  <fo:block border="2pt inset silver" height="{@rows}em" width="{@cols}em">
    <xsl:choose>
      <xsl:when test="node()"><xsl:apply-templates/></xsl:when>
      <xsl:otherwise><xsl:text> </xsl:text></xsl:otherwise>
    </xsl:choose>
  </fo:block>
</xsl:template>

</xsl:stylesheet>
