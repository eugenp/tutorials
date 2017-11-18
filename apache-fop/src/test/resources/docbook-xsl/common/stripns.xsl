<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ng="http://docbook.org/docbook-ng"
                xmlns:db="http://docbook.org/ns/docbook"
                xmlns:saxon="http://icl.com/saxon"
                xmlns:NodeInfo="http://org.apache.xalan.lib.NodeInfo"
                xmlns:exsl="http://exslt.org/common"
		xmlns:xlink="http://www.w3.org/1999/xlink"
                exclude-result-prefixes="db ng exsl saxon NodeInfo xlink"
                version='1.0'>

<!-- ********************************************************************
     $Id: stripns.xsl 9016 2011-06-07 12:09:34Z nwalsh $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- put an xml:base attribute on the root element -->
<xsl:template match="/*" mode="stripNS">
  <xsl:choose>
    <xsl:when test="self::ng:* or self::db:*">
      <xsl:element name="{local-name(.)}">
        <xsl:copy-of select="@*[not(name(.) = 'xml:id')
                                and not(name(.) = 'version')]"/>
        <xsl:if test="@xml:id">
          <xsl:attribute name="id">
            <xsl:value-of select="@xml:id"/>
          </xsl:attribute>
        </xsl:if>

        <xsl:call-template name="add-xml-base"/>

        <xsl:apply-templates mode="stripNS"/>
      </xsl:element>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy>
        <xsl:copy-of select="@*[not(name(.) = 'xml:id')
                                and not(name(.) = 'version')]"/>
        <xsl:if test="@xml:id">
          <xsl:attribute name="id">
            <xsl:value-of select="@xml:id"/>
          </xsl:attribute>
        </xsl:if>

        <xsl:call-template name="add-xml-base"/>

        <xsl:apply-templates mode="stripNS"/>
      </xsl:copy>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="*" mode="stripNS">
  <xsl:choose>
    <xsl:when test="self::ng:* or self::db:*">
      <xsl:element name="{local-name(.)}">
        <xsl:copy-of select="@*[not(name(.) = 'xml:id')
                                and not(name(.) = 'version')]"/>
        <xsl:if test="@xml:id">
          <xsl:attribute name="id">
            <xsl:value-of select="@xml:id"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:apply-templates mode="stripNS"/>
      </xsl:element>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy>
        <xsl:copy-of select="@*[not(name(.) = 'xml:id')
                                and not(name(.) = 'version')]"/>
        <xsl:if test="@xml:id">
          <xsl:attribute name="id">
            <xsl:value-of select="@xml:id"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:apply-templates mode="stripNS"/>
      </xsl:copy>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="db:info" mode="stripNS">
  <xsl:variable name="info">
    <xsl:choose>
      <xsl:when test="parent::db:article
                      |parent::db:appendix
                      |parent::db:bibliography
                      |parent::db:book
                      |parent::db:chapter
                      |parent::db:glossary
                      |parent::db:index
                      |parent::db:part
                      |parent::db:preface
                      |parent::db:refentry
                      |parent::db:reference
                      |parent::db:refsect1
                      |parent::db:refsect2
                      |parent::db:refsect3
                      |parent::db:refsection
                      |parent::db:refsynopsisdiv
                      |parent::db:sect1
                      |parent::db:sect2
                      |parent::db:sect3
                      |parent::db:sect4
                      |parent::db:sect5
                      |parent::db:section
                      |parent::db:setindex
                      |parent::db:set
                      |parent::db:slides
                      |parent::db:sidebar">
        <xsl:value-of select="local-name(parent::*)"/>
        <xsl:text>info</xsl:text>
      </xsl:when>
      <xsl:when test="parent::db:audioobject
                      |parent::db:imageobject
                      |parent::db:inlinemediaobject
                      |parent::db:mediaobject
                      |parent::db:mediaobjectco
                      |parent::db:textobject
                      |parent::db:videoobject">
        <xsl:text>objectinfo</xsl:text>
      </xsl:when>
      <xsl:otherwise>blockinfo</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:element name="{$info}">
    <xsl:copy-of select="@*[not(name(.) = 'xml:id')
                         and not(name(.) = 'version')]"/>
    <xsl:if test="@xml:id">
      <xsl:attribute name="id">
        <xsl:value-of select="@xml:id"/>
      </xsl:attribute>
    </xsl:if>
    <xsl:apply-templates mode="stripNS"/>
  </xsl:element>

  <xsl:if test="(not(../db:title) and not(../ng:title))
                and ($info = 'prefaceinfo'
                     or $info = 'chapterinfo'
                     or $info = 'sectioninfo'
                     or $info = 'sect1info'
                     or $info = 'sect2info'
                     or $info = 'sect3info'
                     or $info = 'sect4info'
                     or $info = 'sect5info'
                     or $info = 'refsectioninfo'
                     or $info = 'refsect1info'
                     or $info = 'refsect2info'
                     or $info = 'refsect3info'
                     or $info = 'blockinfo'
                     or $info = 'appendixinfo')">
    <xsl:apply-templates select="db:title|ng:title" mode="stripNS"/>
  </xsl:if>

</xsl:template>

<xsl:template match="ng:tag|db:tag" mode="stripNS">
  <xsl:choose>
    <xsl:when test="@xlink:href">
      <ulink url="{@xlink:href}">
	<sgmltag>
	  <xsl:copy-of select="@*[not(name(.) = 'xml:id')
			       and not(name(.) = 'version')
			       and not(local-name(.) = 'href')]"/>
          <xsl:if test="@xml:id">
            <xsl:attribute name="id">
              <xsl:value-of select="@xml:id"/>
            </xsl:attribute>
          </xsl:if>
	  <xsl:apply-templates mode="stripNS"/>
	</sgmltag>
      </ulink>
    </xsl:when>
    <xsl:otherwise>
      <sgmltag>
	<xsl:copy-of select="@*[not(name(.) = 'xml:id')
                                and not(name(.) = 'version')]"/>
          <xsl:if test="@xml:id">
            <xsl:attribute name="id">
              <xsl:value-of select="@xml:id"/>
            </xsl:attribute>
          </xsl:if>
	<xsl:apply-templates mode="stripNS"/>
      </sgmltag>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="db:link[@xlink:href]" mode="stripNS">
  <ulink url="{@xlink:href}">
    <xsl:if test="@role">
      <xsl:attribute name="role">
        <xsl:value-of select="@role"/>
      </xsl:attribute>
    </xsl:if>
    <xsl:apply-templates mode="stripNS"/>
  </ulink>
</xsl:template>

<xsl:template match="db:citetitle[@xlink:href]" mode="stripNS">
  <ulink url="{@xlink:href}">
    <citetitle>
      <xsl:copy-of select="@*[not(name(.) = 'xml:id')
			   and not(name(.) = 'version')
			   and not(local-name(.) = 'href')]"/>
      <xsl:if test="@xml:id">
        <xsl:attribute name="id">
          <xsl:value-of select="@xml:id"/>
        </xsl:attribute>
      </xsl:if>
      <xsl:apply-templates mode="stripNS"/>
    </citetitle>
  </ulink>
</xsl:template>

<xsl:template match="db:citetitle[@linkend]" mode="stripNS">
  <citetitle>
    <xsl:copy-of select="@*[not(name(.) = 'xml:id')
			 and not(name(.) = 'version')
			 and not(name(.) = 'linkend')
			 and not(local-name(.) = 'href')]"/>
    <xsl:if test="@xml:id">
      <xsl:attribute name="id">
        <xsl:value-of select="@xml:id"/>
      </xsl:attribute>
    </xsl:if>
      <xsl:apply-templates mode="stripNS"/>
  </citetitle>
</xsl:template>

<xsl:template match="db:alt" mode="stripNS"/>

<xsl:template match="ng:textdata|db:textdata
                     |ng:imagedata|db:imagedata
                     |ng:videodata|db:videodata
                     |ng:audiodata|db:audiodata" mode="stripNS">
  <xsl:element name="{local-name(.)}">
    <xsl:copy-of select="@*[not(name(.) = 'xml:id')
                            and not(name(.) = 'version')
                            and not(name(.) = 'entityref')]"/>
    <xsl:if test="@xml:id">
      <xsl:attribute name="id">
        <xsl:value-of select="@xml:id"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:choose>
      <xsl:when test="@entityref">
        <xsl:attribute name="fileref">
          <xsl:value-of select="unparsed-entity-uri(@entityref)"/>
        </xsl:attribute>
      </xsl:when>
    </xsl:choose>

    <xsl:apply-templates mode="stripNS"/>
  </xsl:element>
</xsl:template>

<xsl:template name="add-xml-base">
  <xsl:if test="not(@xml:base)">
    <xsl:variable name="base">
      <xsl:choose>
        <xsl:when test="function-available('saxon:systemId')">
          <xsl:value-of select="saxon:systemId()"/>
        </xsl:when>
        <xsl:when test="function-available('NodeInfo:systemId')">
          <xsl:value-of select="NodeInfo:systemId()"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>
            <xsl:text>WARNING: cannot add @xml:base to node </xsl:text>
            <xsl:text>set root element.  </xsl:text>
            <xsl:text>Relative paths may not work.</xsl:text>
          </xsl:message>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <!-- debug
    <xsl:message>base is <xsl:value-of select="$base"/></xsl:message>
    -->
    <xsl:if test="$base != ''">
      <xsl:attribute name="xml:base">
        <xsl:call-template name="systemIdToBaseURI">
          <xsl:with-param name="systemId">
            <!-- file: seems to confuse some processors. -->
            <xsl:choose>
              <!-- however, windows paths must use file:///c:/path -->
              <xsl:when test="starts-with($base, 'file:///') and
                              substring($base, 10, 1) = ':'">
                <xsl:value-of select="$base"/>
              </xsl:when>
              <xsl:when test="starts-with($base, 'file:/')
                              and substring($base, 8, 1) = ':'">
                <xsl:value-of select="concat('file:///', 
                                      substring-after($base,'file:/'))"/>
              </xsl:when>
              <xsl:when test="starts-with($base, 'file:///')">
                <xsl:value-of select="substring-after($base,'file://')"/>
              </xsl:when>
              <xsl:when test="starts-with($base, 'file://')">
                <xsl:value-of select="substring-after($base,'file:/')"/>
              </xsl:when>
              <xsl:when test="starts-with($base, 'file:/')">
                <xsl:value-of select="substring-after($base,'file:')"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="$base"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:attribute>
    </xsl:if>
  </xsl:if>
</xsl:template>

<xsl:template name="systemIdToBaseURI">
  <xsl:param name="systemId" select="''"/>
  <xsl:if test="contains($systemId,'/')">
    <xsl:value-of select="substring-before($systemId,'/')"/>
    <xsl:text>/</xsl:text>
    <xsl:call-template name="systemIdToBaseURI">
      <xsl:with-param name="systemId"
                      select="substring-after($systemId,'/')"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template match="comment()|processing-instruction()|text()" mode="stripNS">
  <xsl:copy/>
</xsl:template>

<xsl:template match="/" priority="-1">
  <!-- need a local version of this variable because this module imported many places-->
  <xsl:variable name="local.exsl.node.set.available">
    <xsl:choose>
      <xsl:when exsl:foo="" xmlns:exsl="http://exslt.org/common"
        test="function-available('exsl:node-set') or
                         contains(system-property('xsl:vendor'),
                           'Apache Software Foundation')">1</xsl:when>
      <xsl:otherwise>0</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="$local.exsl.node.set.available != 0
                    and (*/self::ng:* or */self::db:*)">
      <xsl:message>
        <xsl:text>Stripping namespace from DocBook 5 document. </xsl:text>
        <xsl:text>It is suggested to use namespaced version of the stylesheets </xsl:text>
        <xsl:text>available in distribution file 'docbook-xsl-ns' </xsl:text>
        <xsl:text>at //http://sourceforge.net/projects/docbook/files/</xsl:text>
        <xsl:text> which does not require namespace stripping step.</xsl:text>
      </xsl:message>
      <xsl:variable name="nons">
        <xsl:apply-templates mode="stripNS"/>
      </xsl:variable>
      <xsl:message>Processing stripped document.</xsl:message>
      <xsl:apply-templates select="exsl:node-set($nons)"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="node()"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
