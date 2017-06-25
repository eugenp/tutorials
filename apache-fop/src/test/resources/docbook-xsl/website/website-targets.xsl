<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<xsl:import href="tabular.xsl"/>

<xsl:output method="xml" 
         indent="no" 
	 encoding="utf-8"
         doctype-public="-//Norman Walsh//DTD DocBook OLink Summary V2.0//EN"
         doctype-system="http://docbook.sourceforge.net/release/xsl/current/common/targetdatabase.dtd"/>


<!-- Used only when processing autolayout.xml -->
<xsl:template match="/">
  <xsl:apply-templates mode="collect.targets"/>
</xsl:template>

<xsl:template match="*"/>

<xsl:template match="autolayout" mode="collect.targets">
  <targetset>
    <xsl:apply-templates mode="olink.mode"/>
  </targetset>
</xsl:template>

</xsl:stylesheet>
