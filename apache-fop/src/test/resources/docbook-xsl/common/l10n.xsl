<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0"
                exclude-result-prefixes="l"
                version='1.0'>

<!-- ********************************************************************
     $Id: l10n.xsl 9708 2013-01-22 13:41:24Z kosek $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     This file contains localization templates (for internationalization)
     ******************************************************************** -->

<xsl:param name="l10n.xml" select="document('../common/l10n.xml')"/>
<xsl:param name="local.l10n.xml" select="document('')"/>
<xsl:param name="empty.local.l10n.xml" select="not($local.l10n.xml//l:l10n)"/>

<xsl:key name="l10n-lang" match="l:l10n" use="@language"/>
<xsl:key name="l10n-gentext" match="l:l10n/l:gentext" use="@key"/>
<xsl:key name="l10n-dingbat" match="l:l10n/l:dingbat" use="@key"/>
<xsl:key name="l10n-context" match="l:l10n/l:context" use="@name"/>
<xsl:key name="l10n-template" match="l:l10n/l:context/l:template[not(@style)]" use="concat(../@name, '#', @name)"/>
<xsl:key name="l10n-template-style" match="l:l10n/l:context/l:template[@style]" use="concat(../@name, '#', @name, '#', @style)"/>

<xsl:template name="l10n.language">
  <xsl:param name="target" select="."/>
  <xsl:param name="xref-context" select="false()"/>

  <xsl:variable name="mc-language">
    <xsl:choose>
      <xsl:when test="$l10n.gentext.language != ''">
        <xsl:value-of select="$l10n.gentext.language"/>
      </xsl:when>

      <xsl:when test="$xref-context or $l10n.gentext.use.xref.language != 0">
        <!-- can't do this one step: attributes are unordered! -->
        <xsl:variable name="lang-scope"
                      select="$target/ancestor-or-self::*
                              [@lang or @xml:lang][1]"/>
        <xsl:variable name="lang-attr"
                      select="($lang-scope/@lang | $lang-scope/@xml:lang)[1]"/>
        <xsl:choose>
          <xsl:when test="string($lang-attr) = ''">
            <xsl:value-of select="$l10n.gentext.default.language"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$lang-attr"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:otherwise>
        <!-- can't do this one step: attributes are unordered! -->
        <xsl:variable name="lang-scope"
                      select="$target/ancestor-or-self::*
                              [@lang or @xml:lang][1]"/>
        <xsl:variable name="lang-attr"
                      select="($lang-scope/@lang | $lang-scope/@xml:lang)[1]"/>

        <xsl:choose>
          <xsl:when test="string($lang-attr) = ''">
            <xsl:value-of select="$l10n.gentext.default.language"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$lang-attr"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="language" select="translate($mc-language,
                                        'ABCDEFGHIJKLMNOPQRSTUVWXYZ-',
                                        'abcdefghijklmnopqrstuvwxyz_')"/>

  <xsl:for-each select="$l10n.xml">   <!-- We need to change context in order to get key work -->
    <xsl:choose>
      <xsl:when test="key('l10n-lang', $language)">
        <xsl:value-of select="$language"/>
      </xsl:when>
      <!-- try just the lang code without country -->
      <xsl:when test="key('l10n-lang', substring-before($language,'_'))">
        <xsl:value-of select="substring-before($language,'_')"/>
      </xsl:when>
      <!-- or use the default -->
      <xsl:otherwise>
        <xsl:message>
          <xsl:text>No localization exists for "</xsl:text>
          <xsl:value-of select="$language"/>
          <xsl:text>" or "</xsl:text>
          <xsl:value-of select="substring-before($language,'_')"/>
          <xsl:text>". Using default "</xsl:text>
          <xsl:value-of select="$l10n.gentext.default.language"/>
          <xsl:text>".</xsl:text>
        </xsl:message>
        <xsl:value-of select="$l10n.gentext.default.language"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:for-each>
</xsl:template>

<xsl:template name="l10n.language.name">
  <xsl:param name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:param>

  <xsl:for-each select="$l10n.xml">
    <xsl:value-of
	select="document(key('l10n-lang', $lang)/@href)/l:l10n/@english-language-name"/>
  </xsl:for-each>
</xsl:template>

<xsl:template name="language.attribute">
  <xsl:param name="node" select="."/>

  <xsl:variable name="language">
    <xsl:choose>
      <xsl:when test="$l10n.gentext.language != ''">
        <xsl:value-of select="$l10n.gentext.language"/>
      </xsl:when>

      <xsl:otherwise>
        <!-- can't do this one step: attributes are unordered! -->
        <xsl:variable name="lang-scope"
                      select="$node/ancestor-or-self::*
                              [@lang or @xml:lang][1]"/>
        <xsl:variable name="lang-attr"
                      select="($lang-scope/@lang | $lang-scope/@xml:lang)[1]"/>

        <xsl:choose>
          <xsl:when test="string($lang-attr) = ''">
            <xsl:value-of select="$l10n.gentext.default.language"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$lang-attr"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="$language != ''">
    <xsl:attribute name="lang">
      <xsl:choose>
        <xsl:when test="$l10n.lang.value.rfc.compliant != 0">
          <xsl:value-of select="translate($language, '_', '-')"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$language"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:attribute>
  </xsl:if>

  <!-- FIXME: This is sort of hack, but it was the easiest way to add at least partial support for dir attribute -->
  <xsl:copy-of select="ancestor-or-self::*[@dir][1]/@dir"/>
</xsl:template>

<!-- Duplication of language.attribute template to allow for xml:lang attribute
     creation for XHTML 1.1 and epub target -->
<xsl:template name="xml.language.attribute">
  <xsl:param name="node" select="."/>

  <xsl:variable name="language">
    <xsl:choose>
      <xsl:when test="$l10n.gentext.language != ''">
        <xsl:value-of select="$l10n.gentext.language"/>
      </xsl:when>

      <xsl:otherwise>
        <!-- can't do this one step: attributes are unordered! -->
        <xsl:variable name="lang-scope"
                      select="$node/ancestor-or-self::*
                              [@lang or @xml:lang][1]"/>
        <xsl:variable name="lang-attr"
                      select="($lang-scope/@lang | $lang-scope/@xml:lang)[1]"/>

        <xsl:choose>
          <xsl:when test="string($lang-attr) = ''">
            <xsl:value-of select="$l10n.gentext.default.language"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$lang-attr"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="$language != ''">
    <xsl:attribute name="xml:lang">
      <xsl:choose>
        <xsl:when test="$l10n.lang.value.rfc.compliant != 0">
          <xsl:value-of select="translate($language, '_', '-')"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$language"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:attribute>
  </xsl:if>

  <!-- FIXME: This is sort of hack, but it was the easiest way to add at least partial support for dir attribute -->
  <xsl:copy-of select="ancestor-or-self::*[@dir][1]/@dir"/>
</xsl:template>

<xsl:template name="gentext">
  <xsl:param name="key" select="local-name(.)"/>
  <xsl:param name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:param>

  <xsl:for-each select="$l10n.xml">  <!-- We need to switch context in order to make key() work -->
    <xsl:for-each select="document(key('l10n-lang', $lang)/@href)">
      <xsl:variable name="local.l10n.gentext"
		    select="($local.l10n.xml//l:i18n/l:l10n[@language=$lang]/l:gentext[@key=$key])[1]"/>

      <xsl:variable name="l10n.gentext"
		    select="key('l10n-gentext', $key)[1]"/>

      <xsl:choose>
	<xsl:when test="$local.l10n.gentext">
	  <xsl:value-of select="$local.l10n.gentext/@text"/>
	</xsl:when>
	<xsl:when test="$l10n.gentext">
	  <xsl:value-of select="$l10n.gentext/@text"/>
	</xsl:when>
	<xsl:otherwise>
	  <xsl:message>
	    <xsl:text>No "</xsl:text>
	    <xsl:value-of select="$lang"/>
	    <xsl:text>" localization of "</xsl:text>
	    <xsl:value-of select="$key"/>
	    <xsl:text>" exists</xsl:text>
	    <xsl:choose>
	      <xsl:when test="$lang = 'en'">
		 <xsl:text>.</xsl:text>
	      </xsl:when>
	      <xsl:otherwise>
		 <xsl:text>; using "en".</xsl:text>
	      </xsl:otherwise>
	    </xsl:choose>
	  </xsl:message>
	  
	  <xsl:for-each select="$l10n.xml">  <!-- We need to switch context in order to make key() work -->
	    <xsl:for-each select="document(key('l10n-lang', 'en')/@href)">
	      <xsl:value-of select="key('l10n-gentext', $key)[1]/@text"/>
	    </xsl:for-each>
	  </xsl:for-each>
	</xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
  </xsl:for-each>
</xsl:template>

<xsl:template name="gentext.element.name">
  <xsl:param name="element.name" select="local-name(.)"/>
  <xsl:param name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:param>

  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="$element.name"/>
    <xsl:with-param name="lang" select="$lang"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.space">
  <xsl:text> </xsl:text>
</xsl:template>

<xsl:template name="gentext.edited.by">
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'Editedby'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.by">
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'by'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.dingbat">
  <xsl:param name="dingbat">bullet</xsl:param>
  <xsl:param name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:param>

  <xsl:for-each select="$l10n.xml">  <!-- We need to switch context in order to make key() work -->
    <xsl:for-each select="document(key('l10n-lang', $lang)/@href)">
      <xsl:variable name="local.l10n.dingbat"
		    select="($local.l10n.xml//l:i18n/l:l10n[@language=$lang]/l:dingbat[@key=$dingbat])[1]"/>

      <xsl:variable name="l10n.dingbat"
		    select="key('l10n-dingbat', $dingbat)[1]"/>

      <xsl:choose>
	<xsl:when test="$local.l10n.dingbat">
	  <xsl:value-of select="$local.l10n.dingbat/@text"/>
	</xsl:when>
	<xsl:when test="$l10n.dingbat">
	  <xsl:value-of select="$l10n.dingbat/@text"/>
	</xsl:when>
	<xsl:otherwise>
	  <xsl:message>
	    <xsl:text>No "</xsl:text>
	    <xsl:value-of select="$lang"/>
	    <xsl:text>" localization of dingbat </xsl:text>
	    <xsl:value-of select="$dingbat"/>
	    <xsl:text> exists; using "en".</xsl:text>
	  </xsl:message>

	  <xsl:for-each select="$l10n.xml">  <!-- We need to switch context in order to make key() work -->
	    <xsl:for-each select="document(key('l10n-lang', 'en')/@href)">  
	      <xsl:value-of select="key('l10n-dingbat', $dingbat)[1]/@text"/>
	    </xsl:for-each>
	  </xsl:for-each>
	</xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
  </xsl:for-each>
</xsl:template>

<xsl:template name="gentext.startquote">
  <xsl:call-template name="gentext.dingbat">
    <xsl:with-param name="dingbat">startquote</xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.endquote">
  <xsl:call-template name="gentext.dingbat">
    <xsl:with-param name="dingbat">endquote</xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.nestedstartquote">
  <xsl:call-template name="gentext.dingbat">
    <xsl:with-param name="dingbat">nestedstartquote</xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.nestedendquote">
  <xsl:call-template name="gentext.dingbat">
    <xsl:with-param name="dingbat">nestedendquote</xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.nav.prev">
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'nav-prev'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.nav.next">
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'nav-next'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.nav.home">
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'nav-home'"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="gentext.nav.up">
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'nav-up'"/>
  </xsl:call-template>
</xsl:template>

<!-- ============================================================ -->

<xsl:template name="gentext.template">
  <xsl:param name="context" select="'default'"/>
  <xsl:param name="name" select="'default'"/>
  <xsl:param name="origname" select="$name"/>
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="referrer"/>
  <xsl:param name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:param>
  <xsl:param name="verbose" select="1"/>

  <xsl:choose>
    <xsl:when test="$empty.local.l10n.xml">
      <xsl:for-each select="$l10n.xml">  <!-- We need to switch context in order to make key() work -->
	<xsl:for-each select="document(key('l10n-lang', $lang)/@href)">

	  <xsl:variable name="localization.node"
			select="key('l10n-lang', $lang)[1]"/>

	  <xsl:if test="count($localization.node) = 0
			and $verbose != 0">
	    <xsl:message>
	      <xsl:text>No "</xsl:text>
	      <xsl:value-of select="$lang"/>
	      <xsl:text>" localization exists.</xsl:text>
	    </xsl:message>
	  </xsl:if>

	  <xsl:variable name="context.node"
			select="key('l10n-context', $context)[1]"/>

	  <xsl:if test="count($context.node) = 0
			and $verbose != 0">
	    <xsl:message>
	      <xsl:text>No context named "</xsl:text>
	      <xsl:value-of select="$context"/>
	      <xsl:text>" exists in the "</xsl:text>
	      <xsl:value-of select="$lang"/>
	      <xsl:text>" localization.</xsl:text>
	    </xsl:message>
	  </xsl:if>

	  <xsl:for-each select="$context.node">
	    <xsl:variable name="template.node"
			  select="(key('l10n-template-style', concat($context, '#', $name, '#', $xrefstyle))
				   |key('l10n-template', concat($context, '#', $name)))[1]"/>

	    <xsl:choose>
	      <xsl:when test="$template.node/@text">
		<xsl:value-of select="$template.node/@text"/>
	      </xsl:when>
	      <xsl:otherwise>
		<xsl:choose>
		  <xsl:when test="contains($name, '/')">
		    <xsl:call-template name="gentext.template">
		      <xsl:with-param name="context" select="$context"/>
		      <xsl:with-param name="name" select="substring-after($name, '/')"/>
		      <xsl:with-param name="origname" select="$origname"/>
		      <xsl:with-param name="purpose" select="$purpose"/>
		      <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
		      <xsl:with-param name="referrer" select="$referrer"/>
		      <xsl:with-param name="lang" select="$lang"/>
		      <xsl:with-param name="verbose" select="$verbose"/>
		    </xsl:call-template>
		  </xsl:when>
		  <xsl:when test="$verbose = 0">
		    <!-- silence -->
		  </xsl:when>
		  <xsl:otherwise>
		    <xsl:message>
		      <xsl:text>No template for "</xsl:text>
		      <xsl:value-of select="$origname"/>
		      <xsl:text>" (or any of its leaves) exists in the context named "</xsl:text>
		      <xsl:value-of select="$context"/>
		      <xsl:text>" in the "</xsl:text>
		      <xsl:value-of select="$lang"/>
		      <xsl:text>" localization.</xsl:text>
		    </xsl:message>
		  </xsl:otherwise>
		</xsl:choose>
	      </xsl:otherwise>
	    </xsl:choose>
	  </xsl:for-each>
	</xsl:for-each>
      </xsl:for-each>
    </xsl:when>
    <xsl:otherwise>
      <xsl:for-each select="$l10n.xml">  <!-- We need to switch context in order to make key() work -->
	<xsl:for-each select="document(key('l10n-lang', $lang)/@href)">

	  <xsl:variable name="local.localization.node"
			select="($local.l10n.xml//l:i18n/l:l10n[@language=$lang])[1]"/>

	  <xsl:variable name="localization.node"
			select="key('l10n-lang', $lang)[1]"/>

	  <xsl:if test="count($localization.node) = 0
			and count($local.localization.node) = 0
			and $verbose != 0">
	    <xsl:message>
	      <xsl:text>No "</xsl:text>
	      <xsl:value-of select="$lang"/>
	      <xsl:text>" localization exists.</xsl:text>
	    </xsl:message>
	  </xsl:if>

	  <xsl:variable name="local.context.node"
			select="$local.localization.node/l:context[@name=$context]"/>

	  <xsl:variable name="context.node"
			select="key('l10n-context', $context)[1]"/>

	  <xsl:if test="count($context.node) = 0
			and count($local.context.node) = 0
			and $verbose != 0">
	    <xsl:message>
	      <xsl:text>No context named "</xsl:text>
	      <xsl:value-of select="$context"/>
	      <xsl:text>" exists in the "</xsl:text>
	      <xsl:value-of select="$lang"/>
	      <xsl:text>" localization.</xsl:text>
	    </xsl:message>
	  </xsl:if>

	  <xsl:variable name="local.template.node"
			select="($local.context.node/l:template[@name=$name
								and @style
								and @style=$xrefstyle]
				|$local.context.node/l:template[@name=$name
								and not(@style)])[1]"/>

	  <xsl:choose>
	    <xsl:when test="$local.template.node/@text">
	      <xsl:value-of select="$local.template.node/@text"/>
	    </xsl:when>
	    <xsl:otherwise>
	      <xsl:for-each select="$context.node">
		<xsl:variable name="template.node"
			      select="(key('l10n-template-style', concat($context, '#', $name, '#', $xrefstyle))
				       |key('l10n-template', concat($context, '#', $name)))[1]"/>

		<xsl:choose>
		  <xsl:when test="$template.node/@text">
		    <xsl:value-of select="$template.node/@text"/>
		  </xsl:when>
		  <xsl:otherwise>
		    <xsl:choose>
		      <xsl:when test="contains($name, '/')">
			<xsl:call-template name="gentext.template">
			  <xsl:with-param name="context" select="$context"/>
			  <xsl:with-param name="name" select="substring-after($name, '/')"/>
			  <xsl:with-param name="origname" select="$origname"/>
			  <xsl:with-param name="purpose" select="$purpose"/>
			  <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
			  <xsl:with-param name="referrer" select="$referrer"/>
			  <xsl:with-param name="lang" select="$lang"/>
			  <xsl:with-param name="verbose" select="$verbose"/>
			</xsl:call-template>
		      </xsl:when>
		      <xsl:when test="$verbose = 0">
			<!-- silence -->
		      </xsl:when>
		      <xsl:otherwise>
			<xsl:message>
			  <xsl:text>No template for "</xsl:text>
			  <xsl:value-of select="$origname"/>
			  <xsl:text>" (or any of its leaves) exists in the context named "</xsl:text>
			  <xsl:value-of select="$context"/>
			  <xsl:text>" in the "</xsl:text>
			  <xsl:value-of select="$lang"/>
			  <xsl:text>" localization.</xsl:text>
			</xsl:message>
		      </xsl:otherwise>
		    </xsl:choose>
		  </xsl:otherwise>
		</xsl:choose>
	      </xsl:for-each>
	    </xsl:otherwise>
	  </xsl:choose>
	</xsl:for-each>
      </xsl:for-each>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- silently test if a gentext template exists -->

<xsl:template name="gentext.template.exists">
  <xsl:param name="context" select="'default'"/>
  <xsl:param name="name" select="'default'"/>
  <xsl:param name="origname" select="$name"/>
  <xsl:param name="purpose"/>
  <xsl:param name="xrefstyle"/>
  <xsl:param name="referrer"/>
  <xsl:param name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:param>

  <xsl:variable name="template">
    <xsl:call-template name="gentext.template">
      <xsl:with-param name="context" select="$context"/>
      <xsl:with-param name="name" select="$name"/>
      <xsl:with-param name="origname" select="$origname"/>
      <xsl:with-param name="purpose" select="$purpose"/>
      <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
      <xsl:with-param name="referrer" select="$referrer"/>
      <xsl:with-param name="lang" select="$lang"/>
      <xsl:with-param name="verbose" select="0"/>
    </xsl:call-template>
  </xsl:variable>
  
  <xsl:choose>
    <xsl:when test="string-length($template) != 0">1</xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>