<?xml version='1.0' encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version='1.0'>

<!-- ********************************************************************
     $Id: passivetex.xsl 5315 2005-10-20 10:08:42Z kosek $
     ********************************************************************
      This extension stops PassiveTeX from merging subsequent '-' to 
      dashes. You must set passivetex.extensions to '1' if you want get
      this functionality.
     ******************************************************************** -->

<xsl:template name="passivetex.dash.subst">
  <xsl:param name="string"/>

  <xsl:choose>
    <xsl:when test="contains($string, '--')">
      <xsl:variable name="rest">
        <xsl:call-template name="passivetex.dash.subst">
          <xsl:with-param name="string"
                          select="concat('-', substring-after($string, '--'))"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:value-of select="concat(substring-before($string, '--'),
                                   '-&#x200b;',
                                   $rest)"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$string"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>

