<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:doc="http://nwalsh.com/xsl/documentation/1.0"
                xmlns:exsl="http://exslt.org/common"
                xmlns:set="http://exslt.org/sets"
		version="1.0"
                exclude-result-prefixes="doc exsl set">

<!-- ********************************************************************
     $Id$
     ******************************************************************** 

     This file is part customization layer on top of the XSL DocBook
     Stylesheet distribution that generates webhelp output.

     ******************************************************************** -->

<xsl:import href="../../xhtml/chunk.xsl"/>
<xsl:include href="webhelp-common.xsl"/>
<xsl:include href="titlepage.templates.xsl"/>

</xsl:stylesheet>
