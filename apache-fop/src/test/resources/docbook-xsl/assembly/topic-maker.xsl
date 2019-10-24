<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:exsl="http://exslt.org/common"
  xmlns="http://docbook.org/ns/docbook"
  exclude-result-prefixes="exsl"
  version="1.0">

<!-- $Id: topic-maker.xsl,v 1.3 2012-04-16 00:29:35 bobs Exp $ -->

<!-- This stylesheet convert DocBook elements into topic element.
     The chunking takes place elsewhere.  -->

<xsl:import href="../xhtml/docbook.xsl"/>
    
    
<xsl:param name="assembly.filename">myassembly.xml</xsl:param>
<xsl:param name="chunk.section.depth" select="3"/>
<xsl:param name="chunk.first.sections" select="1"/>
<xsl:param name="use.id.as.filename" select="1"/>
<xsl:param name="html.ext">.xml</xsl:param> 
<xsl:param name="base.dir">topics/</xsl:param>
<xsl:param name="root.filename" select="local-name(/*)"/>
<xsl:param name="html.extra.head.links" select="0"/>
<xsl:param name="stylesheet.result.type">xhtml</xsl:param>
<xsl:param name="navig.showtitles" select="0"/>
<xsl:param name="suppress.navigation" select="1"/>
<xsl:param name="chunk.append"/>
<xsl:param name="chunk.quietly" select="0"/>
<xsl:param name="chunker.output.method" select="'xml'"/>
<xsl:param name="chunker.output.encoding" select="'UTF-8'"/>
<xsl:param name="chunker.output.indent" select="'no'"/>
<xsl:param name="chunker.output.omit-xml-declaration" select="'no'"/>
<xsl:param name="chunker.output.standalone" select="'no'"/>
<xsl:param name="chunker.output.doctype-public" select="''"/>
<xsl:param name="chunker.output.doctype-system" select="''"/>
<xsl:param name="namespace">http://docbook.org/ns/docbook</xsl:param>

<!-- These elements are converted to topic elements -->
<xsl:param name="topic.elements">preface chapter article section</xsl:param>
<xsl:variable name="topic.list"
              select="concat(' ', normalize-space($topic.elements), ' ')"/>

<!-- Default behavior is identity copy -->
<xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="@*"/>
      <xsl:apply-templates/>
   </xsl:copy>
</xsl:template>

<xsl:template match="preface|chapter|appendix|section|article">
  <xsl:variable name="element.name">
    <xsl:call-template name="element.name"/>
  </xsl:variable>

  <xsl:element name="{$element.name}" namespace="{$namespace}">
    <xsl:apply-templates select="@*"/>
    <xsl:apply-templates/>
  </xsl:element>
</xsl:template>

<xsl:template name="element.name">
  <xsl:param name="node" select="."/>

  <xsl:variable name="src.element" select="concat(' ', local-name($node), ' ')"/>
  <xsl:choose>
    <xsl:when test="contains($topic.list, $src.element)">
      <xsl:text>topic</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="local-name($node)"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>

