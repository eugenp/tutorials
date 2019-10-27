<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:output method="xml" omit-xml-declaration="no" doctype-public="-//OASIS//DTD DocBook XML V4.4//EN" doctype-system="http://www.oasis-open.org/docbook/xml/4.4/docbookx.dtd" indent="no"/>
  <xsl:template match="@*|*|comment()|processing-instruction()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="text()">
    <xsl:value-of select="replace(replace(., '[a-z]', 'x'), '[0-9]', 'd')"/>
  </xsl:template>
</xsl:stylesheet>
