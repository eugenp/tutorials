<?xml version="1.0" encoding="US-ASCII"?>
<!--This file was created automatically by xsl2profile-->
<!--from the DocBook XSL stylesheets.-->
<!--This file was created automatically by html2xhtml-->
<!--from the HTML stylesheets.-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:doc="http://nwalsh.com/xsl/documentation/1.0" xmlns="http://www.w3.org/1999/xhtml" xmlns:exslt="http://exslt.org/common" xmlns:ng="http://docbook.org/docbook-ng" xmlns:db="http://docbook.org/ns/docbook" exslt:dummy="dummy" ng:dummy="dummy" db:dummy="dummy" extension-element-prefixes="exslt" version="1.0" exclude-result-prefixes="doc exslt">

<!-- ********************************************************************
     $Id: onechunk.xsl 6910 2007-06-28 23:23:30Z xmldoc $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->

<xsl:import href="chunk.xsl"/>

<!-- Ok, using the onechunk parameter makes this all work again. -->
<!-- It does have the disadvantage that it only works for documents that have -->
<!-- a root element that is considered a chunk by the chunk.xsl stylesheet. -->
<!-- Ideally, onechunk would let anything be a chunk. But not today. -->

<xsl:param name="onechunk" select="1"/>
<xsl:param name="suppress.navigation">1</xsl:param>

<xsl:template name="href.target.uri">
  <xsl:param name="object" select="."/>
  <xsl:text>#</xsl:text>
  <xsl:call-template name="object.id">
    <xsl:with-param name="object" select="$object"/>
  </xsl:call-template>
</xsl:template>

</xsl:stylesheet>
