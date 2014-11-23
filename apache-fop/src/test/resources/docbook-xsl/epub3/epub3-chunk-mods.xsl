<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:exsl="http://exslt.org/common"
  xmlns:epub="http://www.idpf.org/2007/ops"
  xmlns:dc="http://purl.org/dc/elements/1.1/"  
  xmlns:ncx="http://www.daisy.org/z3986/2005/ncx/"
  xmlns:db="http://docbook.org/ns/docbook"
  xmlns:opf="http://www.idpf.org/2007/opf"
  xmlns:stext="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.TextFactory"
  xmlns:str="http://exslt.org/strings"
  xmlns:xtext="xalan://com.nwalsh.xalan.Text"
  extension-element-prefixes="stext xtext"
  exclude-result-prefixes="exsl dc ncx opf stext str xtext"
  version="1.0">

<!-- $Id: epub3-chunk-mods.xsl,v 1.1 2011-09-16 21:43:45 bobs Exp $ -->

<xsl:include href="../xhtml5/html5-chunk-mods.xsl"/>

<!--==============================================================-->
<!--  DocBook XSL Parameter settings                              -->
<!--==============================================================-->

<!--==============================================================-->
<!--  Template customizations                                     -->
<!--==============================================================-->

<!-- EPUB3: customize to generate package files -->
<xsl:template match="*" mode="process.root" priority="2">
  <xsl:call-template name="check.for.xalan"/>
  <xsl:apply-templates select="."/>
  <xsl:call-template name="generate.css.files"/>

  <xsl:call-template name="generate.epub.files"/>

</xsl:template>

<xsl:template name="check.for.xalan">
  <xsl:if test="contains(system-property('xsl:vendor'), 'Apache Software Foundation')">
    <xsl:message terminate="yes">
      <xsl:text>&#10;</xsl:text>
      <xsl:text>FATAL ERROR: </xsl:text>
      <xsl:text>Xalan processor not supported by DocBook Epub3 stylesheets. </xsl:text>
      <xsl:text>Xalan does not properly support XSL output method="text", </xsl:text>
      <xsl:text>which is required for the various epub support files.</xsl:text>
    </xsl:message>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
