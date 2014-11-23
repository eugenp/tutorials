<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<xsl:output method="xml"/>

<xsl:param name="attributes" select="''"/>

<xsl:variable name="strip-attributes"
              select="concat(' ', normalize-space($attributes), ' ')"/>

<xsl:template match="@*|text()|comment()|processing-instruction()">
  <xsl:copy/>
</xsl:template>

<xsl:template match="*">
  <xsl:copy>
    <xsl:for-each select="@*">
      <xsl:if test="not(contains($strip-attributes, concat(' ',name(.),' ')))">
        <xsl:copy-of select="."/>
      </xsl:if>
    </xsl:for-each>
    <xsl:apply-templates select="node()"/>
  </xsl:copy>
</xsl:template>

</xsl:stylesheet>
