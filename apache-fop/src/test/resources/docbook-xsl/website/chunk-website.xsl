<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xweb="xalan://com.nwalsh.xalan.Website"
                xmlns:sweb="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.Website"
                exclude-result-prefixes="sweb xweb"
                version="1.0">

<xsl:import href="website.xsl"/>
<xsl:import href="../html/chunker.xsl"/>
<xsl:include href="chunk-common.xsl"/>

</xsl:stylesheet>
