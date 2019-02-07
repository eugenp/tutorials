<?xml version="1.0"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY % common.entities SYSTEM "../common/entities.ent">
%common.entities;
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exslt="http://exslt.org/common"
                extension-element-prefixes="exslt"
                exclude-result-prefixes="exslt"
                version="1.0">

<!-- ********************************************************************
     $Id: autoidx.xsl 9707 2013-01-21 17:18:44Z bobstayton $
     ********************************************************************

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

     ******************************************************************** -->

<!-- ==================================================================== -->
<!-- The "basic" method derived from Jeni Tennison's work. -->
<!-- The "kosek" method contributed by Jirka Kosek. -->
<!-- The "kimber" method contributed by Eliot Kimber of Innodata Isogen. -->

<xsl:variable name="kimber.imported" select="0"/>
<xsl:variable name="kosek.imported" select="0"/>

<xsl:key name="letter"
         match="indexterm"
         use="translate(substring(&primary;, 1, 1),&lowercase;,&uppercase;)"/>

<xsl:key name="primary"
         match="indexterm"
         use="&primary;"/>

<xsl:key name="secondary"
         match="indexterm"
         use="concat(&primary;, &sep;, &secondary;)"/>

<xsl:key name="tertiary"
         match="indexterm"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;)"/>

<xsl:key name="endofrange"
         match="indexterm[@class='endofrange']"
         use="@startref"/>

<xsl:key name="primary-section"
         match="indexterm[not(secondary) and not(see)]"
         use="concat(&primary;, &sep;, &section.id;)"/>

<xsl:key name="secondary-section"
         match="indexterm[not(tertiary) and not(see)]"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &section.id;)"/>

<xsl:key name="tertiary-section"
         match="indexterm[not(see)]"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, &section.id;)"/>

<xsl:key name="see-also"
         match="indexterm[seealso]"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, seealso)"/>

<xsl:key name="see"
         match="indexterm[see]"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, see)"/>

<xsl:key name="sections" match="*[@id or @xml:id]" use="@id|@xml:id"/>


<xsl:template name="generate-index">
  <xsl:param name="scope" select="(ancestor::book|/)[last()]"/>

  <xsl:choose>
    <xsl:when test="$index.method = 'kosek'">
      <xsl:call-template name="generate-kosek-index">
        <xsl:with-param name="scope" select="$scope"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="$index.method = 'kimber'">
      <xsl:call-template name="generate-kimber-index">
        <xsl:with-param name="scope" select="$scope"/>
      </xsl:call-template>
    </xsl:when>

    <xsl:otherwise>
      <xsl:call-template name="generate-basic-index">
        <xsl:with-param name="scope" select="$scope"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
      
<xsl:template name="generate-basic-index">
  <xsl:param name="scope" select="NOTANODE"/>

  <xsl:variable name="role">
    <xsl:if test="$index.on.role != 0">
      <xsl:value-of select="@role"/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="type">
    <xsl:if test="$index.on.type != 0">
      <xsl:value-of select="@type"/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="terms"
                select="//indexterm
                        [count(.|key('letter',
                          translate(substring(&primary;, 1, 1),
                             &lowercase;,
                             &uppercase;))
                          [&scope;][1]) = 1
                          and not(@class = 'endofrange')]"/>

  <xsl:variable name="alphabetical"
                select="$terms[contains(concat(&lowercase;, &uppercase;),
                                        substring(&primary;, 1, 1))]"/>

  <xsl:variable name="others" select="$terms[not(contains(concat(&lowercase;,
                                                 &uppercase;),
                                             substring(&primary;, 1, 1)))]"/>
  <div class="index">
    <xsl:if test="$others">
      <xsl:choose>
        <xsl:when test="normalize-space($type) != '' and 
                        $others[@type = $type][count(.|key('primary', &primary;)[&scope;][1]) = 1]">
          <div class="indexdiv">
            <h3>
              <xsl:call-template name="gentext">
                <xsl:with-param name="key" select="'index symbols'"/>
              </xsl:call-template>
            </h3>
            <dl>
              <xsl:apply-templates select="$others[count(.|key('primary', &primary;)[&scope;][1]) = 1]"
                                   mode="index-symbol-div">
                <xsl:with-param name="position" select="position()"/>                                
                <xsl:with-param name="scope" select="$scope"/>
                <xsl:with-param name="role" select="$role"/>
                <xsl:with-param name="type" select="$type"/>
                <xsl:sort select="translate(&primary;, &lowercase;, &uppercase;)"/>
              </xsl:apply-templates>
            </dl>
          </div>
        </xsl:when>
        <xsl:when test="normalize-space($type) != ''"> 
          <!-- Output nothing, as there isn't a match for $other using this $type -->
        </xsl:when>  
        <xsl:otherwise>
          <div class="indexdiv">
            <h3>
              <xsl:call-template name="gentext">
                <xsl:with-param name="key" select="'index symbols'"/>
              </xsl:call-template>
            </h3>
            <dl>
              <xsl:apply-templates select="$others[count(.|key('primary',
                                          &primary;)[&scope;][1]) = 1]"
                                  mode="index-symbol-div">
                <xsl:with-param name="position" select="position()"/>                                
                <xsl:with-param name="scope" select="$scope"/>
                <xsl:with-param name="role" select="$role"/>
                <xsl:with-param name="type" select="$type"/>
                <xsl:sort select="translate(&primary;, &lowercase;, &uppercase;)"/>
              </xsl:apply-templates>
            </dl>
          </div>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>

    <xsl:apply-templates select="$alphabetical[count(.|key('letter',
                                 translate(substring(&primary;, 1, 1),
                                           &lowercase;,&uppercase;))[&scope;][1]) = 1]"
                         mode="index-div-basic">
      <xsl:with-param name="position" select="position()"/>
      <xsl:with-param name="scope" select="$scope"/>
      <xsl:with-param name="role" select="$role"/>
      <xsl:with-param name="type" select="$type"/>
      <xsl:sort select="translate(&primary;, &lowercase;, &uppercase;)"/>
    </xsl:apply-templates>
  </div>
</xsl:template>

<!-- This template not used if html/autoidx-kosek.xsl is imported -->
<xsl:template name="generate-kosek-index">
  <xsl:param name="scope" select="NOTANODE"/>

  <xsl:variable name="vendor" select="system-property('xsl:vendor')"/>
  <xsl:if test="contains($vendor, 'libxslt')">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method does not </xsl:text>
      <xsl:text>work with the xsltproc XSLT processor.</xsl:text>
    </xsl:message>
  </xsl:if>


  <xsl:if test="$exsl.node.set.available = 0">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method requires the </xsl:text>
      <xsl:text>exslt:node-set() function. Use a processor that </xsl:text>
      <xsl:text>has it, or use a different index method.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="$kosek.imported = 0">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kosek' index method requires the&#xA;</xsl:text>
      <xsl:text>kosek index extensions be imported:&#xA;</xsl:text>
      <xsl:text>  xsl:import href="html/autoidx-kosek.xsl"</xsl:text>
    </xsl:message>
  </xsl:if>

</xsl:template>

<!-- This template not used if html/autoidx-kimber.xsl is imported -->
<xsl:template name="generate-kimber-index">
  <xsl:param name="scope" select="NOTANODE"/>

  <xsl:variable name="vendor" select="system-property('xsl:vendor')"/>
  <xsl:if test="not(contains($vendor, 'SAXON '))">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kimber' index method requires the </xsl:text>
      <xsl:text>Saxon version 6 or 8 XSLT processor.</xsl:text>
    </xsl:message>
  </xsl:if>

  <xsl:if test="$kimber.imported = 0">
    <xsl:message terminate="yes">
      <xsl:text>ERROR: the 'kimber' index method requires the&#xA;</xsl:text>
      <xsl:text>kimber index extensions be imported:&#xA;</xsl:text>
      <xsl:text>  xsl:import href="html/autoidx-kimber.xsl"</xsl:text>
    </xsl:message>
  </xsl:if>

</xsl:template>

<xsl:template match="indexterm" mode="index-div-basic">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:variable name="key"
                select="translate(substring(&primary;, 1, 1),
                         &lowercase;,&uppercase;)"/>

  <xsl:if test="key('letter', $key)[&scope;]
                [count(.|key('primary', &primary;)[&scope;][1]) = 1]">
    <div class="indexdiv">
      <xsl:if test="contains(concat(&lowercase;, &uppercase;), $key)">
        <h3>
          <xsl:value-of select="translate($key, &lowercase;, &uppercase;)"/>
        </h3>
      </xsl:if>
      <dl>
        <xsl:apply-templates select="key('letter', $key)[&scope;]
                                     [count(.|key('primary', &primary;)
                                     [&scope;][1])=1]"
                             mode="index-primary">
          <xsl:with-param name="position" select="position()"/>
          <xsl:with-param name="scope" select="$scope"/>
          <xsl:with-param name="role" select="$role"/>
          <xsl:with-param name="type" select="$type"/>
          <xsl:sort select="translate(&primary;, &lowercase;, &uppercase;)"/>
        </xsl:apply-templates>
      </dl>
    </div>
  </xsl:if>
</xsl:template>

<xsl:template match="indexterm" mode="index-symbol-div">
  <xsl:param name="scope" select="/"/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:variable name="key" select="translate(substring(&primary;, 1, 1),
                                             &lowercase;,&uppercase;)"/>

  <xsl:apply-templates select="key('letter', $key)
                               [&scope;][count(.|key('primary', &primary;)[1]) = 1]"
                       mode="index-primary">
    <xsl:with-param name="position" select="position()"/>
    <xsl:with-param name="scope" select="$scope"/>
    <xsl:with-param name="role" select="$role"/>
    <xsl:with-param name="type" select="$type"/>
    <xsl:sort select="translate(&primary;, &lowercase;, &uppercase;)"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="indexterm" mode="index-primary">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:variable name="key" select="&primary;"/>
  <xsl:variable name="refs" select="key('primary', $key)[&scope;]"/>
  <dt>
    <xsl:for-each select="$refs/primary">
      <xsl:if test="@id or @xml:id">
        <xsl:choose>
          <xsl:when test="$generate.id.attributes = 0">
            <a name="{(@id|@xml:id)[1]}"/>
          </xsl:when>
          <xsl:otherwise>
            <span>
              <xsl:call-template name="id.attribute"/>
            </span>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </xsl:for-each>
    <xsl:value-of select="primary"/>
    <xsl:choose>
      <xsl:when test="$index.links.to.section = 1">
        <xsl:for-each select="$refs[@zone != '' or generate-id() = generate-id(key('primary-section', concat($key, &sep;, &section.id;))[&scope;][1])]">
          <xsl:apply-templates select="." mode="reference">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
          </xsl:apply-templates>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:for-each select="$refs[not(see) 
                              and not(secondary)][&scope;]">
          <xsl:apply-templates select="." mode="reference">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
          </xsl:apply-templates>
        </xsl:for-each>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:if test="$refs[not(secondary)]/*[self::see]">
      <xsl:apply-templates select="$refs[generate-id() = generate-id(key('see', concat(&primary;, &sep;, &sep;, &sep;, see))[&scope;][1])]"
                           mode="index-see">
        <xsl:with-param name="position" select="position()"/>
        <xsl:with-param name="scope" select="$scope"/>
        <xsl:with-param name="role" select="$role"/>
        <xsl:with-param name="type" select="$type"/>
        <xsl:sort select="translate(see, &lowercase;, &uppercase;)"/>
      </xsl:apply-templates>
    </xsl:if>
  </dt>
  <xsl:choose>
    <xsl:when test="$refs/secondary or $refs[not(secondary)]/*[self::seealso]">
      <dd>
        <dl>
          <xsl:apply-templates select="$refs[generate-id() = generate-id(key('see-also', concat(&primary;, &sep;, &sep;, &sep;, seealso))[&scope;][1])]"
                               mode="index-seealso">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
            <xsl:sort select="translate(seealso, &lowercase;, &uppercase;)"/>
          </xsl:apply-templates>
          <xsl:apply-templates select="$refs[secondary and count(.|key('secondary', concat($key, &sep;, &secondary;))[&scope;][1]) = 1]" 
                               mode="index-secondary">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
            <xsl:sort select="translate(&secondary;, &lowercase;, &uppercase;)"/>
          </xsl:apply-templates>
        </dl>
      </dd>
    </xsl:when>
    <!-- HTML5 requires dd for each dt -->
    <xsl:when test="$div.element = 'section'">
      <dd></dd>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="indexterm" mode="index-secondary">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:variable name="key" select="concat(&primary;, &sep;, &secondary;)"/>
  <xsl:variable name="refs" select="key('secondary', $key)[&scope;]"/>
  <dt>
    <xsl:for-each select="$refs/secondary">
      <xsl:if test="@id or @xml:id">
        <xsl:choose>
          <xsl:when test="$generate.id.attributes = 0">
            <a name="{(@id|@xml:id)[1]}"/>
          </xsl:when>
          <xsl:otherwise>
            <span>
              <xsl:call-template name="id.attribute"/>
            </span>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </xsl:for-each>
    <xsl:value-of select="secondary"/>
    <xsl:choose>
      <xsl:when test="$index.links.to.section = 1">
        <xsl:for-each select="$refs[@zone != '' or generate-id() = generate-id(key('secondary-section', concat($key, &sep;, &section.id;))[&scope;][1])]">
          <xsl:apply-templates select="." mode="reference">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
          </xsl:apply-templates>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:for-each select="$refs[not(see) 
                                and not(tertiary)][&scope;]">
          <xsl:apply-templates select="." mode="reference">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
          </xsl:apply-templates>
        </xsl:for-each>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:if test="$refs[not(tertiary)]/*[self::see]">
      <xsl:apply-templates select="$refs[generate-id() = generate-id(key('see', concat(&primary;, &sep;, &secondary;, &sep;, &sep;, see))[&scope;][1])]"
                           mode="index-see">
        <xsl:with-param name="position" select="position()"/>
        <xsl:with-param name="scope" select="$scope"/>
        <xsl:with-param name="role" select="$role"/>
        <xsl:with-param name="type" select="$type"/>
        <xsl:sort select="translate(see, &lowercase;, &uppercase;)"/>
      </xsl:apply-templates>
    </xsl:if>
  </dt>
  <xsl:choose>
    <xsl:when test="$refs/tertiary or $refs[not(tertiary)]/*[self::seealso]">
      <dd>
        <dl>
          <xsl:apply-templates select="$refs[generate-id() = generate-id(key('see-also', concat(&primary;, &sep;, &secondary;, &sep;, &sep;, seealso))[&scope;][1])]"
                               mode="index-seealso">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
            <xsl:sort select="translate(seealso, &lowercase;, &uppercase;)"/>
          </xsl:apply-templates>
          <xsl:apply-templates select="$refs[tertiary and count(.|key('tertiary', concat($key, &sep;, &tertiary;))[&scope;][1]) = 1]" 
                               mode="index-tertiary">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
            <xsl:sort select="translate(&tertiary;, &lowercase;, &uppercase;)"/>
          </xsl:apply-templates>
        </dl>
      </dd>
    </xsl:when>
    <!-- HTML5 requires dd for each dt -->
    <xsl:when test="$div.element = 'section'">
      <dd></dd>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="indexterm" mode="index-tertiary">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:variable name="key" select="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;)"/>
  <xsl:variable name="refs" select="key('tertiary', $key)[&scope;]"/>
  <dt>
    <xsl:for-each select="$refs/tertiary">
      <xsl:if test="@id or @xml:id">
        <xsl:choose>
          <xsl:when test="$generate.id.attributes = 0">
            <a name="{(@id|@xml:id)[1]}"/>
          </xsl:when>
          <xsl:otherwise>
            <span>
              <xsl:call-template name="id.attribute"/>
            </span>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
    </xsl:for-each>
    <xsl:value-of select="tertiary"/>
    <xsl:choose>
      <xsl:when test="$index.links.to.section = 1">
        <xsl:for-each select="$refs[@zone != '' or generate-id() = generate-id(key('tertiary-section', concat($key, &sep;, &section.id;))[&scope;][1])]">
          <xsl:apply-templates select="." mode="reference">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
          </xsl:apply-templates>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
        <xsl:for-each select="$refs[not(see)][&scope;]">
          <xsl:apply-templates select="." mode="reference">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
          </xsl:apply-templates>
        </xsl:for-each>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:if test="$refs/see">
      <xsl:apply-templates select="$refs[generate-id() = generate-id(key('see', concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, see))[&scope;][1])]"
                           mode="index-see">
        <xsl:with-param name="position" select="position()"/>
        <xsl:with-param name="scope" select="$scope"/>
        <xsl:with-param name="role" select="$role"/>
        <xsl:with-param name="type" select="$type"/>
        <xsl:sort select="translate(see, &lowercase;, &uppercase;)"/>
      </xsl:apply-templates>
    </xsl:if>
  </dt>
  <xsl:choose>
    <xsl:when test="$refs/seealso">
      <dd>
        <dl>
          <xsl:apply-templates select="$refs[generate-id() = generate-id(key('see-also', concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, seealso))[&scope;][1])]"
                               mode="index-seealso">
            <xsl:with-param name="position" select="position()"/>
            <xsl:with-param name="scope" select="$scope"/>
            <xsl:with-param name="role" select="$role"/>
            <xsl:with-param name="type" select="$type"/>
            <xsl:sort select="translate(seealso, &lowercase;, &uppercase;)"/>
          </xsl:apply-templates>
        </dl>
      </dd>
    </xsl:when>
    <!-- HTML5 requires dd for each dt -->
    <xsl:when test="$div.element = 'section'">
      <dd></dd>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="indexterm" mode="reference">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>
  <xsl:param name="position"/>
  <xsl:param name="separator" select="''"/>
  
  <xsl:variable name="term.separator">
    <xsl:call-template name="index.separator">
      <xsl:with-param name="key" select="'index.term.separator'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="number.separator">
    <xsl:call-template name="index.separator">
      <xsl:with-param name="key" select="'index.number.separator'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="range.separator">
    <xsl:call-template name="index.separator">
      <xsl:with-param name="key" select="'index.range.separator'"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$separator != ''">
      <xsl:value-of select="$separator"/>
    </xsl:when>
    <xsl:when test="$position = 1">
      <xsl:value-of select="$term.separator"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$number.separator"/>
    </xsl:otherwise>
  </xsl:choose>

  <xsl:choose>
    <xsl:when test="@zone and string(@zone)">
      <xsl:call-template name="reference">
        <xsl:with-param name="zones" select="normalize-space(@zone)"/>
        <xsl:with-param name="position" select="position()"/>
        <xsl:with-param name="scope" select="$scope"/>
        <xsl:with-param name="role" select="$role"/>
        <xsl:with-param name="type" select="$type"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <a>
        <xsl:apply-templates select="." mode="class.attribute"/>
        <xsl:variable name="title">
          <xsl:choose>
            <xsl:when test="$index.prefer.titleabbrev != 0">
              <xsl:apply-templates select="&section;" mode="titleabbrev.markup"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="&section;" mode="title.markup"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>

        <xsl:attribute name="href">
          <xsl:choose>
            <xsl:when test="$index.links.to.section = 1">
              <xsl:call-template name="href.target">
                <xsl:with-param name="object" select="&section;"/>
                <xsl:with-param name="context" 
                                select="(//index[&scope;] | //setindex[&scope;])[1]"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:call-template name="href.target">
                <xsl:with-param name="object" select="."/>
                <xsl:with-param name="context" 
                                select="(//index[&scope;] | //setindex[&scope;])[1]"/>
              </xsl:call-template>
            </xsl:otherwise>
          </xsl:choose>

        </xsl:attribute>

        <xsl:value-of select="$title"/> <!-- text only -->
      </a>

      <xsl:variable name="id" select="(@id|@xml:id)[1]"/>
      <xsl:if test="key('endofrange', $id)[&scope;]">
        <xsl:apply-templates select="key('endofrange', $id)[&scope;][last()]"
                             mode="reference">
          <xsl:with-param name="position" select="position()"/>
          <xsl:with-param name="scope" select="$scope"/>
          <xsl:with-param name="role" select="$role"/>
          <xsl:with-param name="type" select="$type"/>
          <xsl:with-param name="separator" select="$range.separator"/>
        </xsl:apply-templates>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="reference">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>
  <xsl:param name="zones"/>

  <xsl:choose>
    <xsl:when test="contains($zones, ' ')">
      <xsl:variable name="zone" select="substring-before($zones, ' ')"/>
      <xsl:variable name="target" select="key('sections', $zone)"/>

      <a>
        <xsl:apply-templates select="." mode="class.attribute"/>
        <xsl:call-template name="id.attribute"/>
        <xsl:attribute name="href">
          <xsl:call-template name="href.target">
            <xsl:with-param name="object" select="$target[1]"/>
            <xsl:with-param name="context" select="//index[&scope;][1]"/>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:apply-templates select="$target[1]" mode="index-title-content"/>
      </a>
      <xsl:text>, </xsl:text>
      <xsl:call-template name="reference">
        <xsl:with-param name="zones" select="substring-after($zones, ' ')"/>
        <xsl:with-param name="position" select="position()"/>
        <xsl:with-param name="scope" select="$scope"/>
        <xsl:with-param name="role" select="$role"/>
        <xsl:with-param name="type" select="$type"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="zone" select="$zones"/>
      <xsl:variable name="target" select="key('sections', $zone)"/>

      <a>
        <xsl:apply-templates select="." mode="class.attribute"/>
        <xsl:call-template name="id.attribute"/>
        <xsl:attribute name="href">
          <xsl:call-template name="href.target">
            <xsl:with-param name="object" select="$target[1]"/>
            <xsl:with-param name="context" select="//index[&scope;][1]"/>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:apply-templates select="$target[1]" mode="index-title-content"/>
      </a>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="indexterm" mode="index-see">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:text> (</xsl:text>
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'see'"/>
  </xsl:call-template>
  <xsl:text> </xsl:text>
  <xsl:value-of select="see"/>
  <xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="indexterm" mode="index-seealso">
  <xsl:param name="scope" select="."/>
  <xsl:param name="role" select="''"/>
  <xsl:param name="type" select="''"/>

  <xsl:for-each select="seealso">
    <xsl:sort select="translate(., &lowercase;, &uppercase;)"/>
    <dt>
    <xsl:text>(</xsl:text>
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'seealso'"/>
    </xsl:call-template>
    <xsl:text> </xsl:text>
    <xsl:value-of select="."/>
    <xsl:text>)</xsl:text>
    </dt>
    <xsl:if test="$div.element = 'section'">
      <dd></dd>
    </xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template match="*" mode="index-title-content">
  <xsl:variable name="title">
    <xsl:apply-templates select="&section;" mode="title.markup"/>
  </xsl:variable>

  <xsl:value-of select="$title"/>
</xsl:template>

<xsl:template name="index.separator">
  <xsl:param name="key" select="''"/>
  <xsl:param name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:param>

  <xsl:choose>
    <xsl:when test="$key = 'index.term.separator'">
      <xsl:choose>
        <!-- Use the override if not blank -->
        <xsl:when test="$index.term.separator != ''">
          <xsl:copy-of select="$index.term.separator"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="lang" select="$lang"/>
            <xsl:with-param name="context">index</xsl:with-param>
            <xsl:with-param name="name">term-separator</xsl:with-param>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="$key = 'index.number.separator'">
      <xsl:choose>
        <!-- Use the override if not blank -->
        <xsl:when test="$index.number.separator != ''">
          <xsl:copy-of select="$index.number.separator"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="lang" select="$lang"/>
            <xsl:with-param name="context">index</xsl:with-param>
            <xsl:with-param name="name">number-separator</xsl:with-param>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:when test="$key = 'index.range.separator'">
      <xsl:choose>
        <!-- Use the override if not blank -->
        <xsl:when test="$index.range.separator != ''">
          <xsl:copy-of select="$index.range.separator"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="gentext.template">
            <xsl:with-param name="lang" select="$lang"/>
            <xsl:with-param name="context">index</xsl:with-param>
            <xsl:with-param name="name">range-separator</xsl:with-param>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
