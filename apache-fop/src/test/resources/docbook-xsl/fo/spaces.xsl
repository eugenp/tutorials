<?xml version='1.0' encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version='1.0'>

<!-- ********************************************************************
     $Id: spaces.xsl 9647 2012-10-26 17:42:03Z bobstayton $
     ********************************************************************
     XSL-FO specification treats all space characters like ordinary spaces.
     We need to map them to fo:leader with different widths in order to
     simulate desired behaviour.
     ******************************************************************** -->

<xsl:template match="text()[namespace-uri(..) = '' or 
                     namespace-uri(..) = 'http://docbook.org/ns/docbook']">
  <xsl:call-template name="space.2000.subst">
    <xsl:with-param name="string" select="."/>
  </xsl:call-template>
</xsl:template>

<xsl:param name="space.enquad.width">0.5em</xsl:param>   <!-- U+2000 -->
<xsl:param name="space.emquad.width">1em</xsl:param>     <!-- U+2001 -->
<xsl:param name="space.enspace.width">0.5em</xsl:param>  <!-- U+2002 -->
<xsl:param name="space.emspace.width">1em</xsl:param>    <!-- U+2003 -->
<xsl:param name="space.3emspace.width">0.33em</xsl:param><!-- U+2004 -->
<xsl:param name="space.4emspace.width">0.25em</xsl:param><!-- U+2005 -->
<xsl:param name="space.6emspace.width">0.16em</xsl:param><!-- U+2006 -->
<xsl:param name="space.figspace.width"></xsl:param>      <!-- U+2007 -->
<xsl:param name="space.punctspace.width"></xsl:param>    <!-- U+2008 -->
<xsl:param name="space.thinspace.width">0.2em</xsl:param><!-- U+2009 -->
<xsl:param name="space.hairspace.width">0.1em</xsl:param><!-- U+200A -->

<xsl:template name="space.2000.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2000;') and $space.enquad.width != ''">
      <xsl:call-template name="space.2001.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2000;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.enquad.width}"/>
      <xsl:call-template name="space.2000.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2000;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.2001.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.2001.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2001;') and $space.emquad.width != ''">
      <xsl:call-template name="space.2002.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2001;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.emquad.width}"/>
      <xsl:call-template name="space.2001.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2001;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.2002.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.2002.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2002;') and $space.enspace.width != ''">
      <xsl:call-template name="space.2003.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2002;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.enspace.width}"/>
      <xsl:call-template name="space.2002.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2002;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.2003.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.2003.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2003;') and $space.emspace.width != ''">
      <xsl:call-template name="space.2004.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2003;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.emspace.width}"/>
      <xsl:call-template name="space.2003.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2003;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.2004.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.2004.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2004;') and $space.3emspace.width != ''">
      <xsl:call-template name="space.2005.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2004;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.3emspace.width}"/>
      <xsl:call-template name="space.2004.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2004;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.2005.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.2005.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2005;') and $space.4emspace.width != ''">
      <xsl:call-template name="space.2006.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2005;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.4emspace.width}"/>
      <xsl:call-template name="space.2005.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2005;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.2006.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.2006.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2006;') and $space.6emspace.width != ''">
      <xsl:call-template name="space.2007.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2006;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.6emspace.width}"/>
      <xsl:call-template name="space.2006.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2006;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.2007.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.2007.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2007;') and $space.figspace.width != ''">
      <xsl:call-template name="space.2008.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2007;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.figspace.width}"/>
      <xsl:call-template name="space.2007.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2007;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.2008.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.2008.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2008;') and $space.punctspace.width != ''">
      <xsl:call-template name="space.2009.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2008;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.punctspace.width}"/>
      <xsl:call-template name="space.2008.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2008;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.2009.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.2009.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x2009;') and $space.thinspace.width != ''">
      <xsl:call-template name="space.200A.subst">
	<xsl:with-param name="string" select="substring-before($string, '&#x2009;')"/>
      </xsl:call-template>
      <fo:leader leader-length="{$space.thinspace.width}"/>
      <xsl:call-template name="space.2009.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x2009;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="space.200A.subst">
	<xsl:with-param name="string" select="$string"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="space.200A.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '&#x200A;') and $space.hairspace.width != ''">
      <xsl:value-of select="substring-before($string, '&#x200A;')"/>
      <fo:leader leader-length="{$space.hairspace.width}"/>
      <xsl:call-template name="space.200A.subst">
	<xsl:with-param name="string" select="substring-after($string, '&#x200A;')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$string"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>

