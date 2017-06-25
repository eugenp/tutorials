<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<!-- Include common profiling stylesheet -->
<xsl:import href="profile-mode.xsl"/>

<!-- This file must be included, because profile-mode is using templates from it -->
<xsl:import href="../common/stripns.xsl"/>

<!-- In the two pass processing there is no need for base URI fixup -->
<xsl:param name="profile.baseuri.fixup" select="false()"/>

<!-- If you need to validate profiled content against DTD, 
     create customization that will import this stylesheet and
     will use xsl:output (see example bellow) to output reference 
     to the desired DTD version. --> 
<!-- Generate DocBook instance with correct DOCTYPE -->
<!--
<xsl:output method="xml" 
            doctype-public="-//OASIS//DTD DocBook XML V4.5//EN"
            doctype-system="http://www.oasis-open.org/docbook/xml/4.5/docbookx.dtd"/>
-->

<!-- Profiling parameters -->
<xsl:param name="profile.arch" select="''"/>
<xsl:param name="profile.audience" select="''"/>
<xsl:param name="profile.condition" select="''"/>
<xsl:param name="profile.conformance" select="''"/>
<xsl:param name="profile.lang" select="''"/>
<xsl:param name="profile.os" select="''"/>
<xsl:param name="profile.revision" select="''"/>
<xsl:param name="profile.revisionflag" select="''"/>
<xsl:param name="profile.role" select="''"/>
<xsl:param name="profile.security" select="''"/>
<xsl:param name="profile.status" select="''"/>
<xsl:param name="profile.userlevel" select="''"/>
<xsl:param name="profile.vendor" select="''"/>
<xsl:param name="profile.wordsize" select="''"/>
<xsl:param name="profile.attribute" select="''"/>
<xsl:param name="profile.value" select="''"/>
<xsl:param name="profile.separator" select="';'"/>

<xsl:param name="exsl.node.set.available"> 
  <xsl:choose>
    <xsl:when xmlns:exsl="http://exslt.org/common" exsl:foo="" test="function-available('exsl:node-set') or contains(system-property('xsl:vendor'), 'Apache Software Foundation')">1</xsl:when>
    <xsl:otherwise>0</xsl:otherwise>
  </xsl:choose>
</xsl:param>

<!-- Call common profiling mode -->
<xsl:template match="/">
  <xsl:apply-templates select="." mode="profile"/>
</xsl:template>

</xsl:stylesheet>

