<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:exsl="http://exslt.org/common"
  xmlns:xlink="http://www.w3.org/1999/xlink"
  xmlns="http://docbook.org/ns/docbook"
  exclude-result-prefixes="exsl d xlink"
  version="1.0">

<!-- $Id: assemble.xsl,v 1.10 2012-04-10 07:56:58 bobs Exp $ -->

<xsl:preserve-space elements="*"/>
<xsl:strip-space elements="d:assembly d:structure d:module d:resources d:resource"/>

<xsl:key name="id" match="*" use="@id|@xml:id"/>


<xsl:param name="docbook.version">5.0</xsl:param>
<xsl:param name="root.default.renderas">book</xsl:param>
<xsl:param name="topic.default.renderas">section</xsl:param>

<xsl:param name="output.type" select="''"/>
<xsl:param name="output.format" select="''"/>
<!-- May be used to select one structure among several to  process -->
<xsl:param name="structure.id" select="''"/>


<!-- default mode is to copy all content nodes -->
<xsl:template match="node()|@*" priority="-5" mode="copycontent">
  <xsl:param name="omittitles"/>
    <xsl:copy>
      <xsl:apply-templates select="@*" mode="copycontent"/>
      <xsl:apply-templates mode="copycontent">
        <xsl:with-param name="omittitles" select="$omittitles"/>
      </xsl:apply-templates>
   </xsl:copy>
</xsl:template>

<xsl:template match="processing-instruction('oxygen')"/>

<!-- skip assembly info elements -->
<xsl:template match="d:info"/>

<!-- including for structure info element -->
<xsl:template match="d:structure/d:info"/>

<!-- handle omittitles, but only top level title of resource -->
<xsl:template match="/*/d:title 
                   | /*/d:info/d:title 
                   | /*/d:subtitle  
                   | /*/d:info/d:subtitle
                   | /*/d:titleabbrev  
                   | /*/d:info/d:titleabbrev"
              mode="copycontent">
  <xsl:param name="omittitles"/>

  <xsl:choose>
    <xsl:when test="$omittitles = 'yes' or $omittitles = 'true' or $omittitles = '1'">
      <!-- omit it -->
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy>
        <xsl:apply-templates select="@*" mode="copycontent"/>
        <xsl:apply-templates mode="copycontent"/>
      </xsl:copy>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- handled in a mode -->
<xsl:template match="d:resources"/>
<xsl:template match="d:output|d:filterin|d:filterout|d:merge|d:revhistory"/>
<xsl:template match="d:output|d:filterin|d:filterout|d:merge|d:revhistory"
              mode="copycontent"/>

<xsl:template match="d:assembly">
  <xsl:choose>
    <xsl:when test="$structure.id != ''">
      <xsl:variable name="id.structure" select="key('id', $structure.id)"/>
      <xsl:choose>
        <xsl:when test="count($id.structure) = 0">
          <xsl:message terminate="yes">
            <xsl:text>ERROR: structure.id param set to '</xsl:text>
            <xsl:value-of select="$structure.id"/>
            <xsl:text>' but no element with that xml:id exists in assembly.</xsl:text>
          </xsl:message>
        </xsl:when>
        <xsl:when test="local-name($id.structure) != 'structure'">
          <xsl:message terminate="yes">
            <xsl:text>ERROR: structure.id param set to '</xsl:text>
            <xsl:value-of select="$structure.id"/>
            <xsl:text>' but no structure with that xml:id exists in assembly.</xsl:text>
          </xsl:message>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="key('id', $structure.id)"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="$output.type != '' and not(d:structure[@type = $output.type])">
      <xsl:message terminate="yes">
        <xsl:text>ERROR: output.type param set to '</xsl:text>
        <xsl:value-of select="$output.type"/>
        <xsl:text> but no structure element has that type attribute. Exiting.</xsl:text>
      </xsl:message>
    </xsl:when>
    <xsl:when test="$output.type != '' and d:structure[@type = $output.type]">
      <xsl:apply-templates select="d:structure[@type = $output.type][1]"/>
    </xsl:when>
    <xsl:otherwise>
      <!-- otherwise process the first structure -->
      <xsl:apply-templates select="d:structure[1]"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="d:structure[not(@resourceref)]">

  <xsl:variable name="output.root.element">
    <xsl:apply-templates select="." mode="compute.element.name"/>
  </xsl:variable>

  <xsl:element name="{$output.root.element}" namespace="http://docbook.org/ns/docbook">
    <xsl:attribute name="version">
      <xsl:value-of select="$docbook.version"/>
    </xsl:attribute>
    <xsl:copy-of select="@xml:id"/>

    <xsl:apply-templates> 
      <xsl:with-param name="parent" select="$output.root.element"/>
    </xsl:apply-templates>

  </xsl:element>
</xsl:template>

<xsl:template match="d:glossary|d:bibliography|d:index|d:toc">
   <xsl:param name="parent" select="''"/>
  <xsl:apply-templates select="." mode="copycontent"/>
</xsl:template>

<xsl:template match="d:title|d:titleabbrev|d:subtitle">
   <xsl:param name="parent" select="''"/>
  <xsl:apply-templates select="." mode="copycontent"/>
</xsl:template>

<!-- module without a resourceref creates an element -->
<xsl:template match="d:module[not(@resourceref)]">
  <xsl:param name="parent" select="''"/>
  
  <xsl:variable name="module" select="."/>

  <xsl:variable name="element.name">
    <xsl:apply-templates select="." mode="compute.element.name"/>
  </xsl:variable>

  <xsl:element name="{$element.name}" namespace="http://docbook.org/ns/docbook">
    <xsl:choose>
      <!-- Use the module's xml:id if it has one -->
      <xsl:when test="@xml:id">
        <xsl:attribute name="xml:id">
          <xsl:value-of select="@xml:id"/>
        </xsl:attribute>
      </xsl:when>
    </xsl:choose>

    <xsl:call-template name="merge.info">
      <xsl:with-param name="merge.element" select="$module/d:merge"/>
    </xsl:call-template>
      
    <xsl:apply-templates>
      <xsl:with-param name="parent" select="$element.name"/>
    </xsl:apply-templates>

  </xsl:element>
</xsl:template>

<xsl:template name="compute.renderas">
  <xsl:variable name="output.value">
    <xsl:call-template name="compute.output.value">
      <xsl:with-param name="property">renderas</xsl:with-param>
    </xsl:call-template>
  </xsl:variable>

  <xsl:value-of select="$output.value"/>

</xsl:template>

<!-- This utility template is passed a value for output format
     and the name of a property and computes the value
     of the property from output children of context element -->
<xsl:template name="compute.output.value">
  <xsl:param name="property" select="''"/>
  
  <xsl:variable name="default.format"
                select="ancestor::d:structure/@defaultformat"/>

  <xsl:variable name="property.value">
    <xsl:choose>
      <!-- if a child output element has a format that matches the param value 
           and it has that property -->
      <!-- The format attribute can be multivalued -->
      <xsl:when test="$output.format != '' and 
                      d:output[contains(concat(' ', normalize-space(@format), ' '),  
                                 $output.format)]
                        [@*[local-name() = $property]]">
        <xsl:value-of 
           select="d:output[contains(concat(' ', normalize-space(@format), ' '), 
                              $output.format)]
                           [@*[local-name() = $property]][1]
                           /@*[local-name() = $property]"/>
      </xsl:when>
      <!-- try with the structure's @defaultformat -->
      <xsl:when test="$default.format != '' and 
                      d:output[contains(concat(' ', normalize-space(@format), ' '),  
                                 $default.format)]
                        [@*[local-name() = $property]]">
        <xsl:value-of 
           select="d:output[contains(concat(' ', normalize-space(@format), ' '), 
                              $default.format)]
                           [@*[local-name() = $property]][1]
                           /@*[local-name() = $property]"/>
      </xsl:when>
      <!-- try the first output with the property-->
      <xsl:when test="d:output[@*[local-name() = $property]]">
        <xsl:value-of 
           select="d:output[@*[local-name() = $property]][1]
                           /@*[local-name() = $property]"/>
      </xsl:when>
      <!-- and try the module element itself -->
      <xsl:when test="@*[local-name() = $property]">
        <xsl:value-of 
           select="@*[local-name() = $property]"/>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>

  <xsl:value-of select="$property.value"/>
</xsl:template>

<xsl:template match="d:module[not(@resourceref)]" mode="compute.element.name">

  <xsl:variable name="renderas">
    <xsl:call-template name="compute.renderas"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="string-length($renderas) != 0">
      <xsl:value-of select="$renderas"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message terminate="yes">
        <xsl:text>ERROR: cannot determine output element name for </xsl:text>
        <xsl:text>module with no @resourceref and no @renderas. Exiting.</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="d:module[@resourceref]" mode="compute.element.name">
  <xsl:param name="ref.name" select="''"/>

  <xsl:variable name="renderas">
    <xsl:call-template name="compute.renderas"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="string-length($renderas) != 0">
      <xsl:value-of select="$renderas"/>
    </xsl:when>
    <xsl:when test="$ref.name = 'topic' and 
                    string-length($topic.default.renderas) != 0"> 
      <xsl:value-of select="$topic.default.renderas"/>
    </xsl:when>
    <xsl:when test="string-length($ref.name) != 0">
      <xsl:value-of select="$ref.name"/> 
    </xsl:when>
    <xsl:otherwise>
      <xsl:message terminate="yes">
        <xsl:text>ERROR: cannot determine output element name for </xsl:text>
        <xsl:text>@resourceref="</xsl:text>
        <xsl:value-of select="@resourceref"/>
        <xsl:text>". Exiting.</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="d:structure" mode="compute.element.name">
  <xsl:param name="ref.name" select="''"/>

  <xsl:variable name="renderas">
    <xsl:call-template name="compute.renderas"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="string-length($renderas) != 0">
      <xsl:value-of select="$renderas"/>
    </xsl:when>
    <xsl:when test="string-length($ref.name) != 0">
      <xsl:value-of select="$ref.name"/> 
    </xsl:when>
    <xsl:when test="string-length($root.default.renderas) != 0">
      <xsl:value-of select="$root.default.renderas"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message terminate="yes">
        <xsl:text>ERROR: cannot determine output element name for </xsl:text>
        <xsl:text>structure with no @renderas and no $root.default.renderas. </xsl:text>
        <xsl:text>Exiting.</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="d:module[@resourceref] | d:structure[@resourceref]">
  <xsl:param name="parent" select="''"/>

  <xsl:variable name="module" select="."/>
  <xsl:variable name="resourceref" select="@resourceref"/>
  <xsl:variable name="resource" select="key('id', $resourceref)"/>

  <xsl:choose>
    <xsl:when test="not($resource)">
      <xsl:message terminate="yes">
        <xsl:text>ERROR: no xml:id matches @resourceref = '</xsl:text>
        <xsl:value-of select="$resourceref"/>
        <xsl:text>'.</xsl:text>
      </xsl:message>
    </xsl:when>
    <xsl:when test="not($resource/self::d:resource)">
      <xsl:message terminate="yes">
        <xsl:text>ERROR: xml:id matching @resourceref = '</xsl:text>
        <xsl:value-of select="$resourceref"/>
        <xsl:text> is not a resource element'.</xsl:text>
      </xsl:message>
    </xsl:when>
  </xsl:choose>

  <xsl:variable name="fileref.att" select="$resource/@fileref"/>

  <xsl:variable name="fragment.id">
    <xsl:if test="contains($fileref.att, '#')">
      <xsl:value-of select="substring-after($fileref.att, '#')"/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="filename">
    <xsl:choose>
      <xsl:when test="string-length($fragment.id) != 0">
        <xsl:value-of select="substring-before($fileref.att, '#')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$fileref.att"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="fileref">
    <xsl:choose>
      <xsl:when test="$resource/ancestor::d:resources/@xml:base">
        <xsl:value-of 
            select="concat($resource/ancestor::d:resources[@xml:base][1]/@xml:base,
                                 '/', $filename)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$filename"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="string-length($fileref) = 0">
      <!-- A resource without @fileref gets its content copied -->
      <xsl:apply-templates select="$resource/node()" mode="copycontent"/>
    </xsl:when>
    <xsl:otherwise>

      <xsl:variable name="ref.file.content" select="document($fileref,/)"/>
    
      <!-- selects root or fragmeht depending on if $fragment is blank -->
      <xsl:variable name="ref.content"
        select="$ref.file.content/*[1][$fragment.id = ''] |
                $ref.file.content/*[1][$fragment.id != '']/
                   descendant-or-self::*[@xml:id = $fragment.id]"/>
        
      <xsl:if test="count($ref.content) = 0">
        <xsl:message terminate="yes">
          <xsl:text>ERROR: @fileref = '</xsl:text>
          <xsl:value-of select="$fileref"/>
          <xsl:text>' has no content or is unresolved.</xsl:text>
        </xsl:message>
      </xsl:if>

      <xsl:variable name="element.name">
        <xsl:apply-templates select="." mode="compute.element.name">
          <xsl:with-param name="ref.name" select="local-name($ref.content)"/>
        </xsl:apply-templates>
      </xsl:variable>

      <xsl:variable name="omittitles.property">
        <xsl:call-template name="compute.output.value">
          <xsl:with-param name="property">omittitles</xsl:with-param>
        </xsl:call-template>
      </xsl:variable>
    
      <xsl:variable name="contentonly.property">
        <xsl:call-template name="compute.output.value">
          <xsl:with-param name="property">contentonly</xsl:with-param>
        </xsl:call-template>
      </xsl:variable>
    
      <xsl:choose>
        <xsl:when test="$contentonly.property = 'true' or 
                        $contentonly.property = 'yes' or
                        $contentonly.property = '1'">
          <xsl:apply-templates select="$ref.content/node()" mode="copycontent">
            <xsl:with-param name="omittitles" select="$omittitles.property"/>
          </xsl:apply-templates>
        </xsl:when>
        <!-- use xsl:copy if using the ref element itself to get its namespaces -->
        <xsl:when test="$element.name = local-name($ref.content)">
          <!-- must use for-each to set context node for xsl:copy -->
          <xsl:for-each select="$ref.content">
            <xsl:copy>
              <xsl:copy-of select="@*[not(name() = 'xml:id')]"/>
              <xsl:choose>
                <!-- Use the module's xml:id if it has one -->
                <xsl:when test="$module/@xml:id">
                  <xsl:attribute name="xml:id">
                    <xsl:value-of select="$module/@xml:id"/>
                  </xsl:attribute>
                </xsl:when>
                <!-- otherwise use the resource's id -->
                <xsl:otherwise>
                  <xsl:copy-of select="@xml:id"/>
                </xsl:otherwise>
              </xsl:choose>
          
              <xsl:call-template name="merge.info">
                <xsl:with-param name="merge.element" select="$module/d:merge"/>
                <xsl:with-param name="ref.content" select="$ref.content"/>
                <xsl:with-param name="omittitles" select="$omittitles.property"/>
              </xsl:call-template>
      
              <!-- copy through all but titles, which moved to info -->
              <xsl:apply-templates select="node()
                       [not(local-name() = 'title') and
                        not(local-name() = 'subtitle') and
                        not(local-name() = 'info') and
                        not(local-name() = 'titleabbrev')]" mode="copycontent"/>
          
              <xsl:apply-templates select="$module/node()"> 
                <xsl:with-param name="parent" select="$element.name"/>
              </xsl:apply-templates>
            </xsl:copy>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <!-- create the element instead of copying it -->
          <xsl:element name="{$element.name}" namespace="http://docbook.org/ns/docbook">
            <xsl:copy-of select="$ref.content/@*[not(name() = 'xml:id')]"/>
            <xsl:choose>
              <!-- Use the module's xml:id if it has one -->
              <xsl:when test="@xml:id">
                <xsl:attribute name="xml:id">
                  <xsl:value-of select="@xml:id"/>
                </xsl:attribute>
              </xsl:when>
              <!-- otherwise use the resource's id -->
              <xsl:when test="$ref.content/@xml:id">
                <xsl:attribute name="xml:id">
                  <xsl:value-of select="$ref.content/@xml:id"/>
                </xsl:attribute>
              </xsl:when>
            </xsl:choose>
        
            <xsl:call-template name="merge.info">
              <xsl:with-param name="merge.element" select="d:merge"/>
              <xsl:with-param name="ref.content" select="$ref.content"/>
              <xsl:with-param name="omittitles" select="$omittitles.property"/>
            </xsl:call-template>
    
            <!-- copy through all but titles, which moved to info -->
            <xsl:apply-templates select="$ref.content/node()
                     [not(local-name() = 'title') and
                      not(local-name() = 'subtitle') and
                      not(local-name() = 'info') and
                      not(local-name() = 'titleabbrev')]" mode="copycontent"/>
        
            <xsl:apply-templates> 
              <xsl:with-param name="parent" select="$element.name"/>
            </xsl:apply-templates>
          </xsl:element>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="merge.info">
  <xsl:param name="merge.element" select="NOTANODE"/>
  <xsl:param name="ref.content" select="NOTANODE"/>
  <xsl:param name="omittitles"/> 

  <!-- a merge element may use resourceref as well as literal content -->
  <!-- any literal content overrides the merge resourceref content -->
  <xsl:variable name="merge.ref.content">
    <xsl:if test="$merge.element/@resourceref">
      <xsl:variable name="resourceref" select="$merge.element/@resourceref"/>
      <xsl:variable name="resource" select="key('id', $resourceref)"/>

      <xsl:choose>
        <xsl:when test="not($resource)">
          <xsl:message terminate="yes">
            <xsl:text>ERROR: no xml:id matches @resourceref = '</xsl:text>
            <xsl:value-of select="$resourceref"/>
            <xsl:text>'.</xsl:text>
          </xsl:message>
        </xsl:when>
        <xsl:when test="not($resource/self::d:resource)">
          <xsl:message terminate="yes">
            <xsl:text>ERROR: xml:id matching @resourceref = '</xsl:text>
            <xsl:value-of select="$resourceref"/>
            <xsl:text> is not a resource element'.</xsl:text>
          </xsl:message>
        </xsl:when>
      </xsl:choose>

      <xsl:variable name="fileref.att" select="$resource/@fileref"/>

      <xsl:variable name="fileref">
        <xsl:choose>
          <xsl:when test="$resource/ancestor::d:resources/@xml:base">
            <xsl:value-of 
                select="concat($resource/ancestor::d:resources[@xml:base][1]/@xml:base,
                               '/', $fileref.att)"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$fileref.att"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:if test="string-length($fileref) != 0">
        <xsl:copy-of select="document($fileref,/)"/>
      </xsl:if>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="merge.ref.info" 
                select="exsl:node-set($merge.ref.content)//d:info[1]"/>

  <xsl:if test="$merge.element/@resourceref and not($merge.ref.info)">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: merge element with resourceref '</xsl:text>
      <xsl:value-of select="$merge.element/@resourceref"/>
      <xsl:text>' must point to something with an info element.'</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:variable name="omittitles.boolean">
    <xsl:choose>
      <xsl:when test="$omittitles = 'yes' or $omittitles = 'true' or omittitles = '1'">
        <xsl:value-of select="1"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="0"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <!-- output info if there is any -->
  <xsl:if test="$merge.element/node() or 
                $merge.ref.info/node() or
                $ref.content/d:info/node() or
                $ref.content/d:title[$omittitles.boolean = 0] or
                $ref.content/d:subtitle[$omittitles.boolean = 0] or
                $ref.content/d:titleabbrev[$omittitles.boolean = 0]">

    <xsl:variable name="ref.info" select="$ref.content/d:info"/>
    <xsl:variable name="ref.title" select="$ref.content/d:title"/>
    <xsl:variable name="ref.subtitle" select="$ref.content/d:subtitle"/>
    <xsl:variable name="ref.titleabbrev" select="$ref.content/d:titleabbrev"/>
    <xsl:variable name="ref.info.title" select="$ref.content/d:info/d:title"/>
    <xsl:variable name="ref.info.subtitle" select="$ref.content/d:info/d:subtitle"/>
    <xsl:variable name="ref.info.titleabbrev" select="$ref.content/d:info/d:titleabbrev"/>

    <info>
      <!-- First copy through any merge attributes and elements and comments -->
      <xsl:copy-of select="$merge.element/@*[not(local-name(.) = 'resourceref')]"/>

      <!-- And copy any resource info attributes not in merge-->
      <xsl:for-each select="$ref.info/@*">
        <xsl:variable name="resource.att" select="local-name(.)"/>
        <xsl:choose>
          <xsl:when test="$merge.element/@*[local-name(.) = $resource.att]">
            <!-- do nothing because overridden -->
          </xsl:when>
          <xsl:otherwise>
            <!-- copy through if not overridden -->
            <xsl:copy-of select="."/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:for-each>

      <!-- Copy through the merge children as they have highest priority -->
      <xsl:copy-of select="$merge.element/node()"/>

      <!-- and copy through those merge resource elements not in merge element -->
      <xsl:for-each select="$merge.ref.info/node()">
        <xsl:variable name="resource.node" select="local-name(.)"/>
        <xsl:choose>
          <xsl:when test="$merge.element/node()[local-name(.) = $resource.node]">
            <!-- do nothing because overridden -->
          </xsl:when>
          <xsl:otherwise>
            <!-- copy through -->
            <xsl:copy-of select="."/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:for-each>

      <!-- And copy any module's resource info node not in merge or merge.ref -->
      <xsl:for-each select="$ref.info/node() | 
                            $ref.title[$omittitles.boolean = 0] |
                            $ref.subtitle[$omittitles.boolean = 0] |
                            $ref.titleabbrev[$omittitles.boolean = 0] |
                            $ref.info.title[$omittitles.boolean = 0] |
                            $ref.info.subtitle[$omittitles.boolean = 0] |
                            $ref.info.titleabbrev[$omittitles.boolean = 0]">
        <xsl:variable name="resource.node" select="local-name(.)"/>
        <xsl:choose>
          <xsl:when test="$merge.element/node()[local-name(.) = $resource.node]">
            <!-- do nothing because overridden -->
          </xsl:when>
          <xsl:when test="$merge.ref.info/node()[local-name(.) = $resource.node]">
            <!-- do nothing because overridden -->
          </xsl:when>
          <xsl:otherwise>
            <!-- copy through -->
            <xsl:copy-of select="."/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:for-each>

    </info>
  </xsl:if>
</xsl:template>

<xsl:template match="d:relationships">
  <xsl:message>
    <xsl:text>WARNING: the &lt;relationships&gt; element is not currently </xsl:text>
    <xsl:text>supported by this stylesheet.</xsl:text>
  </xsl:message>
</xsl:template>

<xsl:template match="d:transforms">
  <xsl:message>
    <xsl:text>WARNING: the &lt;transforms&gt; element is not currently </xsl:text>
    <xsl:text>supported by this stylesheet.</xsl:text>
  </xsl:message>
</xsl:template>

<xsl:template match="d:filterin">
  <xsl:message>
    <xsl:text>WARNING: the &lt;filterin&gt; element is not currently </xsl:text>
    <xsl:text>supported by this stylesheet.</xsl:text>
  </xsl:message>
</xsl:template>

<xsl:template match="d:filterout">
  <xsl:message>
    <xsl:text>WARNING: the &lt;filterin&gt; element is not currently </xsl:text>
    <xsl:text>supported by this stylesheet.</xsl:text>
  </xsl:message>
</xsl:template>

</xsl:stylesheet>
