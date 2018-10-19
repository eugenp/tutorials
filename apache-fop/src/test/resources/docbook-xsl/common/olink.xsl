<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<!-- ********************************************************************
     $Id: olink.xsl 9650 2012-10-26 18:24:02Z bobstayton $
     ********************************************************************

     This file is part of the DocBook XSL Stylesheet distribution.
     See ../README or http://docbook.sf.net/ for copyright
     copyright and other information.

     ******************************************************************** -->

<!-- Create keys for quickly looking up olink targets -->
<xsl:key name="targetdoc-key" match="document" use="@targetdoc" />
<xsl:key name="targetptr-key"  match="div|obj"
         use="concat(ancestor::document/@targetdoc, '/',
                     @targetptr, '/', ancestor::document/@lang)" />

<!-- Return filename of database -->
<xsl:template name="select.target.database">
  <xsl:param name="targetdoc.att" select="''"/>
  <xsl:param name="targetptr.att" select="''"/>
  <xsl:param name="olink.lang" select="''"/>

  <!-- use root's xml:base if exists -->
  <xsl:variable name="xml.base" select="/*/@xml:base"/>

  <!-- This selection can be customized if needed -->
  <xsl:variable name="target.database.filename">
    <xsl:choose>
      <xsl:when test="$xml.base != '' and
                   not(starts-with($target.database.document, 'file:/')) and
                   not(starts-with($target.database.document, '/'))">
        <xsl:call-template name="systemIdToBaseURI">
          <xsl:with-param name="systemId" select="$xml.base"/>
        </xsl:call-template>
        <xsl:value-of select="$target.database.document"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$target.database.document"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="target.database" 
      select="document($target.database.filename,/)"/>

  <xsl:choose>
    <!-- Was the database document parameter not set? -->
    <xsl:when test="$target.database.document = ''">
      <xsl:message>
        <xsl:text>Olinks not processed: must specify a </xsl:text>
        <xsl:text>$target.database.document parameter&#10;</xsl:text>
        <xsl:text>when using olinks with targetdoc </xsl:text>
        <xsl:text>and targetptr attributes.</xsl:text>
      </xsl:message>
    </xsl:when>
    <xsl:when test="namespace-uri($target.database/*) != ''">
      <xsl:message>
        <xsl:text>Olink error: the targetset element and children in '</xsl:text>
        <xsl:value-of select="$target.database.document"/>
        <xsl:text>' should not be in any namespace.</xsl:text>
      </xsl:message>
    </xsl:when>
    <!-- Did it not open? Should be a targetset element -->
    <xsl:when test="not($target.database/*)">
      <xsl:message>
        <xsl:text>Olink error: could not open target database '</xsl:text>
        <xsl:value-of select="$target.database.filename"/>
        <xsl:text>'.</xsl:text>
      </xsl:message>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$target.database.filename"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="select.olink.key">
  <xsl:param name="targetdoc.att" select="''"/>
  <xsl:param name="targetptr.att" select="''"/>
  <xsl:param name="olink.lang" select="''"/>
  <xsl:param name="target.database"/>

  <xsl:if test="$target.database/*">
    <xsl:variable name="olink.fallback.sequence">
      <xsl:call-template name="select.olink.lang.fallback">
        <xsl:with-param name="olink.lang" select="$olink.lang"/>
      </xsl:call-template>
    </xsl:variable>
  
    <!-- Recurse through the languages until you find a match -->
    <xsl:call-template name="select.olink.key.in.lang">
      <xsl:with-param name="targetdoc.att" select="$targetdoc.att"/>
      <xsl:with-param name="targetptr.att" select="$targetptr.att"/>
      <xsl:with-param name="olink.lang" select="$olink.lang"/>
      <xsl:with-param name="target.database" select="$target.database"/>
      <xsl:with-param name="fallback.index" select="1"/>
      <xsl:with-param name="olink.fallback.sequence"
                      select="$olink.fallback.sequence"/>
    </xsl:call-template>
  </xsl:if>
  
</xsl:template>

<!-- Locate olink key in a particular language -->
<xsl:template name="select.olink.key.in.lang">
  <xsl:param name="targetdoc.att" select="''"/>
  <xsl:param name="targetptr.att" select="''"/>
  <xsl:param name="olink.lang" select="''"/>
  <xsl:param name="target.database"/>
  <xsl:param name="fallback.index" select="1"/>
  <xsl:param name="olink.fallback.sequence" select="''"/>
  
  <xsl:variable name="target.lang">
    <xsl:call-template name="select.target.lang">
      <xsl:with-param name="fallback.index" select="$fallback.index"/>
      <xsl:with-param name="olink.fallback.sequence"
                      select="$olink.fallback.sequence"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$olink.debug != 0">
    <xsl:message><xsl:text>Olink debug: cases for targetdoc='</xsl:text>
      <xsl:value-of select="$targetdoc.att"/>
      <xsl:text>' and targetptr='</xsl:text>
      <xsl:value-of select="$targetptr.att"/>
      <xsl:text>' in language '</xsl:text>
      <xsl:value-of select="$target.lang"/>
      <xsl:text>'.</xsl:text>
    </xsl:message>
  </xsl:if>

  <!-- Customize these cases if you want different selection logic -->
  <xsl:variable name="CaseA">
    <!-- targetdoc.att = not blank
         targetptr.att = not blank
    -->
    <xsl:if test="$targetdoc.att != '' and
                  $targetptr.att != ''">
      <xsl:for-each select="$target.database">
        <xsl:variable name="key" 
                      select="concat($targetdoc.att, '/', 
                                     $targetptr.att, '/',
                                     $target.lang)"/>
        <xsl:choose>
          <xsl:when test="key('targetptr-key', $key)[1]/@href != ''">
            <xsl:value-of select="$key"/>
            <xsl:if test="$olink.debug != 0">
              <xsl:message>Olink debug: CaseA matched.</xsl:message>
            </xsl:if>
          </xsl:when>
          <xsl:when test="$olink.debug != 0">
            <xsl:message>Olink debug: CaseA NOT matched</xsl:message>
          </xsl:when>
        </xsl:choose>
      </xsl:for-each>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="CaseB">
    <!-- targetdoc.att = not blank
         targetptr.att = not blank
         prefer.internal.olink = not zero
         current.docid = not blank 
    -->
    <xsl:if test="$targetdoc.att != '' and
                  $targetptr.att != '' and
                  $current.docid != '' and
                  $prefer.internal.olink != 0">
      <xsl:for-each select="$target.database">
        <xsl:variable name="key" 
                      select="concat($current.docid, '/', 
                                     $targetptr.att, '/',
                                     $target.lang)"/>
        <xsl:choose>
          <xsl:when test="key('targetptr-key', $key)[1]/@href != ''">
            <xsl:value-of select="$key"/>
            <xsl:if test="$olink.debug != 0">
              <xsl:message>Olink debug: CaseB matched.</xsl:message>
            </xsl:if>
          </xsl:when>
          <xsl:when test="$olink.debug != 0">
            <xsl:message>Olink debug: CaseB NOT matched</xsl:message>
          </xsl:when>
        </xsl:choose>
      </xsl:for-each>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="CaseC">
    <!-- targetdoc.att = blank
         targetptr.att = not blank
         current.docid = not blank 
    -->
    <xsl:if test="string-length($targetdoc.att) = 0 and
                  $targetptr.att != '' and
                  $current.docid != ''">
      <!-- Must use a for-each to change context for keys to work -->
      <xsl:for-each select="$target.database">
        <xsl:variable name="key" 
                      select="concat($current.docid, '/', 
                                     $targetptr.att, '/',
                                     $target.lang)"/>
        <xsl:choose>
          <xsl:when test="key('targetptr-key', $key)[1]/@href != ''">
            <xsl:value-of select="$key"/>
            <xsl:if test="$olink.debug != 0">
              <xsl:message>Olink debug: CaseC matched.</xsl:message>
            </xsl:if>
          </xsl:when>
          <xsl:when test="$olink.debug != 0">
            <xsl:message>Olink debug: CaseC NOT matched.</xsl:message>
          </xsl:when>
        </xsl:choose>
      </xsl:for-each>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="CaseD">
    <!-- targetdoc.att = blank
         targetptr.att = not blank
         current.docid = blank 
    -->
    <!-- This is possible if only one document in the database -->
    <xsl:if test="string-length($targetdoc.att) = 0 and
                  $targetptr.att != '' and
                  string-length($current.docid) = 0 and
                  count($target.database//document) = 1">
      <xsl:for-each select="$target.database">
        <xsl:variable name="key" 
                      select="concat(.//document/@targetdoc, '/', 
                                     $targetptr.att, '/',
                                     $target.lang)"/>
        <xsl:choose>
          <xsl:when test="key('targetptr-key', $key)[1]/@href != ''">
            <xsl:value-of select="$key"/>
            <xsl:if test="$olink.debug != 0">
              <xsl:message>Olink debug: CaseD matched.</xsl:message>
            </xsl:if>
          </xsl:when>
          <xsl:when test="$olink.debug != 0">
            <xsl:message>Olink debug: CaseD NOT matched</xsl:message>
          </xsl:when>
        </xsl:choose>
      </xsl:for-each>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="CaseE">
    <!-- targetdoc.att = not blank
         targetptr.att = blank
    -->
    <xsl:if test="$targetdoc.att != '' and
                  string-length($targetptr.att) = 0">

      <!-- Try the document's root element id -->
      <xsl:variable name="rootid">
        <xsl:choose>
          <xsl:when test="$target.lang != ''">
            <xsl:value-of select="$target.database//document[@targetdoc = $targetdoc.att and @lang = $target.lang]/*[1]/@targetptr"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$target.database//document[@targetdoc = $targetdoc.att and not(@lang)]/*[1]/@targetptr"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:for-each select="$target.database">
        <xsl:variable name="key" 
                      select="concat($targetdoc.att, '/', 
                                     $rootid, '/',
                                     $target.lang)"/>
        <xsl:choose>
          <xsl:when test="key('targetptr-key', $key)[1]/@href != ''">
            <xsl:value-of select="$key"/>
            <xsl:if test="$olink.debug != 0">
              <xsl:message>Olink debug: CaseE matched.</xsl:message>
            </xsl:if>
          </xsl:when>
          <xsl:when test="$olink.debug != 0">
            <xsl:message>Olink debug: CaseE NOT matched.</xsl:message>
          </xsl:when>
        </xsl:choose>
      </xsl:for-each>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="CaseF">
    <!-- targetdoc.att = not blank
         targetptr.att = blank
         prefer.internal.olink = not zero
         current.docid = not blank 
    -->
    <xsl:if test="$targetdoc.att != '' and
                  string-length($targetptr.att) = 0 and
                  $current.docid != '' and
                  $prefer.internal.olink != 0">
      <!-- Try the document's root element id -->
      <xsl:variable name="rootid">
        <xsl:choose>
          <xsl:when test="$target.lang != ''">
            <xsl:value-of select="$target.database//document[@targetdoc = $current.docid and @lang = $target.lang]/*[1]/@targetptr"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$target.database//document[@targetdoc = $current.docid and not(@lang)]/*[1]/@targetptr"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:for-each select="$target.database">
        <xsl:variable name="key" 
                      select="concat($current.docid, '/', 
                                     $rootid, '/',
                                     $target.lang)"/>
        <xsl:choose>
          <xsl:when test="key('targetptr-key', $key)[1]/@href != ''">
            <xsl:value-of select="$key"/>
            <xsl:if test="$olink.debug != 0">
              <xsl:message>Olink debug: CaseF matched.</xsl:message>
            </xsl:if>
          </xsl:when>
          <xsl:when test="$olink.debug != 0">
            <xsl:message>Olink debug: CaseF NOT matched.</xsl:message>
          </xsl:when>
        </xsl:choose>
      </xsl:for-each>
    </xsl:if>
  </xsl:variable>

  <!-- Now select the best match. Customize the order if needed -->
  <xsl:variable name="selected.key">
    <xsl:choose>
      <xsl:when test="$CaseB != ''">
        <xsl:value-of select="$CaseB"/>
        <xsl:if test="$olink.debug != 0">
          <xsl:message>
            <xsl:text>Olink debug: CaseB key is the final selection: </xsl:text>
            <xsl:value-of select="$CaseB"/>
          </xsl:message>
        </xsl:if>
      </xsl:when>
      <xsl:when test="$CaseA != ''">
        <xsl:value-of select="$CaseA"/>
        <xsl:if test="$olink.debug != 0">
          <xsl:message>
            <xsl:text>Olink debug: CaseA key is the final selection: </xsl:text>
            <xsl:value-of select="$CaseA"/>
          </xsl:message>
        </xsl:if>
      </xsl:when>
      <xsl:when test="$CaseC != ''">
        <xsl:value-of select="$CaseC"/>
        <xsl:if test="$olink.debug != 0">
          <xsl:message>
            <xsl:text>Olink debug: CaseC key is the final selection: </xsl:text>
            <xsl:value-of select="$CaseC"/>
          </xsl:message>
        </xsl:if>
      </xsl:when>
      <xsl:when test="$CaseD != ''">
        <xsl:value-of select="$CaseD"/>
        <xsl:if test="$olink.debug != 0">
          <xsl:message>
            <xsl:text>Olink debug: CaseD key is the final selection: </xsl:text>
            <xsl:value-of select="$CaseD"/>
          </xsl:message>
        </xsl:if>
      </xsl:when>
      <xsl:when test="$CaseF != ''">
        <xsl:value-of select="$CaseF"/>
        <xsl:if test="$olink.debug != 0">
          <xsl:message>
            <xsl:text>Olink debug: CaseF key is the final selection: </xsl:text>
            <xsl:value-of select="$CaseF"/>
          </xsl:message>
        </xsl:if>
      </xsl:when>
      <xsl:when test="$CaseE != ''">
        <xsl:value-of select="$CaseE"/>
        <xsl:if test="$olink.debug != 0">
          <xsl:message>
            <xsl:text>Olink debug: CaseE key is the final selection: </xsl:text>
            <xsl:value-of select="$CaseE"/>
          </xsl:message>
        </xsl:if>
      </xsl:when>
      <xsl:otherwise>
        <xsl:if test="$olink.debug != 0">
          <xsl:message>
            <xsl:text>Olink debug: No case matched for lang '</xsl:text>
            <xsl:value-of select="$target.lang"/>
            <xsl:text>'.</xsl:text>
          </xsl:message>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$selected.key != ''">
      <xsl:value-of select="$selected.key"/>
    </xsl:when>
    <xsl:when test="string-length($selected.key) = 0 and 
                    string-length($target.lang) = 0">
      <!-- No match on last try, and we are done -->
    </xsl:when>
    <xsl:otherwise>
      <!-- Recurse through next language -->
      <xsl:call-template name="select.olink.key.in.lang">
        <xsl:with-param name="targetdoc.att" select="$targetdoc.att"/>
        <xsl:with-param name="targetptr.att" select="$targetptr.att"/>
        <xsl:with-param name="olink.lang" select="$olink.lang"/>
        <xsl:with-param name="target.database" select="$target.database"/>
        <xsl:with-param name="fallback.index" select="$fallback.index + 1"/>
        <xsl:with-param name="olink.fallback.sequence"
                        select="$olink.fallback.sequence"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<xsl:template name="select.target.lang">
  <xsl:param name="fallback.index" select="1"/>
  <xsl:param name="olink.fallback.sequence" select="''"/>

  <!-- recurse backwards to find the lang matching the index -->
  <xsl:variable name="firstlang" 
                select="substring-before($olink.fallback.sequence, ' ')"/>
  <xsl:variable name="rest" 
                select="substring-after($olink.fallback.sequence, ' ')"/>
  <xsl:choose>
    <xsl:when test="$fallback.index = 1">
      <xsl:value-of select="$firstlang"/>
    </xsl:when>
    <xsl:when test="$fallback.index &gt; 1">
      <xsl:call-template name="select.target.lang">
        <xsl:with-param name="fallback.index" select="$fallback.index - 1"/>
        <xsl:with-param name="olink.fallback.sequence"
                        select="$rest"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="select.olink.lang.fallback">
  <xsl:param name="olink.lang" select="''"/>

  <!-- Prefer language of the olink element -->
  <xsl:value-of select="concat(normalize-space(concat($olink.lang, ' ', 
                        $olink.lang.fallback.sequence)), ' ')"/>
</xsl:template>

<!-- Returns the complete olink href value if found -->
<xsl:template name="make.olink.href">
  <xsl:param name="olink.key" select="''"/>
  <xsl:param name="target.database"/>

  <xsl:if test="$olink.key != ''">
    <xsl:variable name="target.href" >
      <xsl:for-each select="$target.database" >
        <xsl:value-of select="key('targetptr-key', $olink.key)[1]/@href" />
      </xsl:for-each>
    </xsl:variable>
  
    <xsl:variable name="targetdoc">
      <xsl:value-of select="substring-before($olink.key, '/')"/>
    </xsl:variable>
  
    <!-- Does the target database use a sitemap? -->
    <xsl:variable name="use.sitemap">
      <xsl:choose>
        <xsl:when test="$target.database//sitemap">1</xsl:when>
        <xsl:otherwise>0</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
  
  
    <!-- Get the baseuri for this targetptr -->
    <xsl:variable name="baseuri" >
      <xsl:choose>
        <!-- Does the database use a sitemap? -->
        <xsl:when test="$use.sitemap != 0" >
          <xsl:choose>
            <!-- Was current.docid parameter set? -->
            <xsl:when test="$current.docid != ''">
              <!-- Was it found in the database? -->
              <xsl:variable name="currentdoc.key" >
                <xsl:for-each select="$target.database" >
                  <xsl:value-of select="key('targetdoc-key',
                                        $current.docid)[1]/@targetdoc" />
                </xsl:for-each>
              </xsl:variable>
              <xsl:choose>
                <xsl:when test="$currentdoc.key != ''">
                  <xsl:for-each select="$target.database" >
                    <xsl:call-template name="targetpath" >
                      <xsl:with-param name="dirnode" 
                          select="key('targetdoc-key', $current.docid)[1]/parent::dir"/>
                      <xsl:with-param name="targetdoc" select="$targetdoc"/>
                    </xsl:call-template>
                  </xsl:for-each >
                </xsl:when>
                <xsl:otherwise>
                  <xsl:message>
                    <xsl:text>Olink error: cannot compute relative </xsl:text>
                    <xsl:text>sitemap path because $current.docid '</xsl:text>
                    <xsl:value-of select="$current.docid"/>
                    <xsl:text>' not found in target database.</xsl:text>
                  </xsl:message>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
              <xsl:message>
                <xsl:text>Olink warning: cannot compute relative </xsl:text>
                <xsl:text>sitemap path without $current.docid parameter</xsl:text>
              </xsl:message>
            </xsl:otherwise>
          </xsl:choose> 
          <!-- In either case, add baseuri from its document entry-->
          <xsl:variable name="docbaseuri">
            <xsl:for-each select="$target.database" >
              <xsl:value-of select="key('targetdoc-key', $targetdoc)[1]/@baseuri" />
            </xsl:for-each>
          </xsl:variable>
          <xsl:if test="$docbaseuri != ''" >
            <xsl:value-of select="$docbaseuri"/>
          </xsl:if>
        </xsl:when>
        <!-- No database sitemap in use -->
        <xsl:otherwise>
          <!-- Just use any baseuri from its document entry -->
          <xsl:variable name="docbaseuri">
            <xsl:for-each select="$target.database" >
              <xsl:value-of select="key('targetdoc-key', $targetdoc)[1]/@baseuri" />
            </xsl:for-each>
          </xsl:variable>
          <xsl:if test="$docbaseuri != ''" >
            <xsl:value-of select="$docbaseuri"/>
          </xsl:if>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
  
    <!-- Is this olink to be active? -->
    <xsl:variable name="active.olink">
      <xsl:choose>
        <xsl:when test="$activate.external.olinks = 0">
          <xsl:choose>
            <xsl:when test="$current.docid = ''">1</xsl:when>
            <xsl:when test="$targetdoc = ''">1</xsl:when>
            <xsl:when test="$targetdoc = $current.docid">1</xsl:when>
            <xsl:otherwise>0</xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>1</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <!-- Form the href information -->
    <xsl:if test="$active.olink != 0">
      <xsl:if test="$baseuri != ''">
        <xsl:value-of select="$baseuri"/>
        <xsl:if test="substring($target.href,1,1) != '#'">
          <!--xsl:text>/</xsl:text-->
        </xsl:if>
      </xsl:if>
      <!-- optionally turn off frag for PDF references -->
      <xsl:if test="not($insert.olink.pdf.frag = 0 and
            translate(substring($baseuri, string-length($baseuri) - 3),
                      'PDF', 'pdf') = '.pdf'
            and starts-with($target.href, '#') )">
        <xsl:value-of select="$target.href"/>
      </xsl:if>
    </xsl:if>
  </xsl:if>
</xsl:template>

<!-- Computes the href of the object containing the olink element -->
<xsl:template name="olink.from.uri">
  <xsl:param name="target.database"/>
  <xsl:param name="object" select="NotAnElement"/>
  <xsl:param name="object.targetdoc" select="$current.docid"/>
  <xsl:param name="object.lang" 
           select="concat($object/ancestor::*[last()]/@lang,
                          $object/ancestor::*[last()]/@xml:lang)"/>

  <xsl:variable name="parent.id">
    <xsl:call-template name="object.id">
      <xsl:with-param name="object" select="$object"/>
    </xsl:call-template>
  </xsl:variable>

  <!-- Get the olink key for the parent of olink element -->
  <xsl:variable name="from.key">
    <xsl:call-template name="select.olink.key">
      <xsl:with-param name="targetdoc.att" select="$object.targetdoc"/>
      <xsl:with-param name="targetptr.att" select="$parent.id"/>
      <xsl:with-param name="olink.lang" select="$object.lang"/>
      <xsl:with-param name="target.database" select="$target.database"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="from.olink.href">
    <xsl:for-each select="$target.database" >
      <xsl:value-of select="key('targetptr-key', $from.key)[1]/@href" />
    </xsl:for-each>
  </xsl:variable>

  <xsl:choose>
    <!-- we found the olink object -->
    <xsl:when test="$from.olink.href != ''">
      <xsl:value-of select="$from.olink.href"/>
    </xsl:when>
    <xsl:when test="not($object/parent::*)">
      <xsl:value-of select="$from.olink.href"/>
    </xsl:when>
    <xsl:otherwise>
      <!-- recurse upward in current document -->
      <xsl:call-template name="olink.from.uri">
        <xsl:with-param name="target.database" select="$target.database"/>
        <xsl:with-param name="object" select="$object/parent::*"/>
        <xsl:with-param name="object.targetdoc" select="$object.targetdoc"/>
        <xsl:with-param name="object.lang" select="$object.lang"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<xsl:template name="olink.hottext">
  <xsl:param name="target.database"/>
  <xsl:param name="olink.lang" select="''"/>
  <xsl:param name="olink.key" select="''"/>
  <xsl:param name="referrer" select="."/>
  <xsl:param name="xrefstyle">
    <xsl:choose>
      <xsl:when test="@role and not(@xrefstyle) 
                      and $use.role.as.xrefstyle != 0">
        <xsl:value-of select="@role"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@xrefstyle"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:param>

  <xsl:choose>
    <!-- If it has elements or text (not just PI or comment) -->
    <xsl:when test="child::text() or child::*">
      <xsl:apply-templates/>
    </xsl:when>
    <xsl:when test="$olink.key != ''">
      <!-- Get the xref text for this record -->
      <xsl:variable name="xref.text" >
        <xsl:for-each select="$target.database" >
          <xsl:call-template name="insert.targetdb.data">
            <xsl:with-param name="data"
                  select="key('targetptr-key', $olink.key)[1]/xreftext/node()" />
          </xsl:call-template>
        </xsl:for-each>
      </xsl:variable>

      <xsl:variable name="xref.number" >
        <xsl:for-each select="$target.database" >
          <xsl:value-of select="key('targetptr-key', $olink.key)[1]/@number" />
        </xsl:for-each>
      </xsl:variable>

      <xsl:variable name="target.elem" >
        <xsl:for-each select="$target.database" >
          <xsl:value-of select="key('targetptr-key', $olink.key)[1]/@element" />
        </xsl:for-each>
      </xsl:variable>

      <xsl:variable name="lang">
        <xsl:variable name="candidate">
          <xsl:for-each select="$target.database" >
            <xsl:value-of 
                      select="key('targetptr-key', $olink.key)[1]/@lang" />
          </xsl:for-each>
        </xsl:variable>
        <xsl:choose>
          <xsl:when test="$candidate != ''">
            <xsl:value-of select="$candidate"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="$olink.lang"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:variable>

      <xsl:variable name="targetdoc">
        <xsl:value-of select="substring-before($olink.key, '/')"/>
      </xsl:variable>

      <xsl:choose>
        <xsl:when test="$xrefstyle != '' and
                        starts-with(normalize-space($xrefstyle), 'select:') and
                        (contains($xrefstyle, 'nodocname') or
                        contains($xrefstyle, 'nopage')) and
                        not(contains($xrefstyle, 'title')) and
                        not(contains($xrefstyle, 'label'))"> 
          <xsl:copy-of select="$xref.text"/>
        </xsl:when>
        <xsl:when test="$xrefstyle != ''">
          <xsl:if test="$olink.debug != 0">
            <xsl:message>
              <xsl:text>xrefstyle is '</xsl:text>
              <xsl:value-of select="$xrefstyle"/>
              <xsl:text>'.</xsl:text>
            </xsl:message>
          </xsl:if>
          <xsl:variable name="template">
            <xsl:choose>
              <xsl:when test="starts-with(normalize-space($xrefstyle),
                                          'select:')">
                <xsl:call-template name="make.gentext.template">
                  <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
                  <xsl:with-param name="purpose" select="'olink'"/>
                  <xsl:with-param name="referrer" select="."/>
                  <xsl:with-param name="target.elem" select="$target.elem"/>
                </xsl:call-template>
              </xsl:when>
              <xsl:when test="starts-with(normalize-space($xrefstyle),
                                          'template:')">
                <xsl:value-of select="substring-after(
                                 normalize-space($xrefstyle), 'template:')"/>
              </xsl:when>
              <xsl:otherwise>
                <!-- Look for Gentext template with @style attribute -->
                <!-- Must compare to no style value because gentext.template
                     falls back to no style -->

                <xsl:variable name="xref-context">
                  <xsl:call-template name="gentext.template">
                    <xsl:with-param name="context" select="'xref'"/>
                    <xsl:with-param name="name" select="$target.elem"/>
                    <xsl:with-param name="lang" select="$lang"/>
                  </xsl:call-template>
                </xsl:variable>

                <xsl:variable name="styled-xref-context">
                  <xsl:call-template name="gentext.template">
                    <xsl:with-param name="context" select="'xref'"/>
                    <xsl:with-param name="name" select="$target.elem"/>
                    <xsl:with-param name="lang" select="$lang"/>
                    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
                  </xsl:call-template>
                </xsl:variable>

                <xsl:variable name="xref-number-context">
                  <xsl:call-template name="gentext.template">
                    <xsl:with-param name="context" select="'xref-number'"/>
                    <xsl:with-param name="name" select="$target.elem"/>
                    <xsl:with-param name="lang" select="$lang"/>
                  </xsl:call-template>
                </xsl:variable>

                <xsl:variable name="styled-xref-number-context">
                  <xsl:call-template name="gentext.template">
                    <xsl:with-param name="context" select="'xref-number'"/>
                    <xsl:with-param name="name" select="$target.elem"/>
                    <xsl:with-param name="lang" select="$lang"/>
                    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
                  </xsl:call-template>
                </xsl:variable>

                <xsl:variable name="xref-number-and-title-context">
                  <xsl:call-template name="gentext.template">
                    <xsl:with-param name="context" 
                                    select="'xref-number-and-title'"/>
                    <xsl:with-param name="name" select="$target.elem"/>
                    <xsl:with-param name="lang" select="$lang"/>
                  </xsl:call-template>
                </xsl:variable>

                <xsl:variable name="styled-xref-number-and-title-context">
                  <xsl:call-template name="gentext.template">
                    <xsl:with-param name="context" 
                                    select="'xref-number-and-title'"/>
                    <xsl:with-param name="name" select="$target.elem"/>
                    <xsl:with-param name="lang" select="$lang"/>
                    <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
                  </xsl:call-template>
                </xsl:variable>

                <xsl:choose>
                  <xsl:when test="$xref-number-and-title-context != 
                                 $styled-xref-number-and-title-context and
                                 $xref.number != '' and
                                 $xref.with.number.and.title != 0">
                    <xsl:value-of 
                            select="$styled-xref-number-and-title-context"/>
                  </xsl:when>
                  <xsl:when test="$xref-number-context != 
                                 $styled-xref-number-context and
                                 $xref.number != ''">
                    <xsl:value-of select="$styled-xref-number-context"/>
                  </xsl:when>
                  <xsl:when test="$xref-context != $styled-xref-context">
                    <xsl:value-of select="$styled-xref-context"/>
                  </xsl:when>
                  <xsl:when test="$xref-number-and-title-context != '' and
                                 $xref.number != '' and
                                 $xref.with.number.and.title != 0">
                    <xsl:value-of 
                            select="$xref-number-and-title-context"/>
                    <xsl:if test="$olink.debug">
                      <xsl:message>
                        <xsl:text>Olink error: no gentext template</xsl:text>
                        <xsl:text> exists for xrefstyle '</xsl:text>
                        <xsl:value-of select="$xrefstyle"/>
                        <xsl:text>' for element '</xsl:text>
                        <xsl:value-of select="$target.elem"/>
                        <xsl:text>' in language '</xsl:text>
                        <xsl:value-of select="$lang"/>
                        <xsl:text>' in context 'xref-number-and-title</xsl:text>
                        <xsl:text>'. Using template without @style.</xsl:text>
                      </xsl:message>
                    </xsl:if>
                  </xsl:when>
                  <xsl:when test="$xref-number-context != '' and
                                 $xref.number != ''">
                    <xsl:value-of select="$xref-number-context"/>
                    <xsl:if test="$olink.debug">
                      <xsl:message>
                        <xsl:text>Olink error: no gentext template</xsl:text>
                        <xsl:text> exists for xrefstyle '</xsl:text>
                        <xsl:value-of select="$xrefstyle"/>
                        <xsl:text>' for element '</xsl:text>
                        <xsl:value-of select="$target.elem"/>
                        <xsl:text>' in language '</xsl:text>
                        <xsl:value-of select="$lang"/>
                        <xsl:text>' in context 'xref-number</xsl:text>
                        <xsl:text>'. Using template without @style.</xsl:text>
                      </xsl:message>
                    </xsl:if>
                  </xsl:when>
                  <xsl:when test="$xref-context != ''">
                    <xsl:value-of select="$xref-context"/>
                    <xsl:if test="$olink.debug">
                      <xsl:message>
                        <xsl:text>Olink error: no gentext template</xsl:text>
                        <xsl:text> exists for xrefstyle '</xsl:text>
                        <xsl:value-of select="$xrefstyle"/>
                        <xsl:text>' for element '</xsl:text>
                        <xsl:value-of select="$target.elem"/>
                        <xsl:text>' in language '</xsl:text>
                        <xsl:value-of select="$lang"/>
                        <xsl:text>' in context 'xref</xsl:text>
                        <xsl:text>'. Using template without @style.</xsl:text>
                      </xsl:message>
                    </xsl:if>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:message>
                      <xsl:text>Olink error: no gentext template</xsl:text>
                      <xsl:text> exists for xrefstyle '</xsl:text>
                      <xsl:value-of select="$xrefstyle"/>
                      <xsl:text>' for element '</xsl:text>
                      <xsl:value-of select="$target.elem"/>
                      <xsl:text>' in language '</xsl:text>
                      <xsl:value-of select="$lang"/>
                      <xsl:text>'. Trying '%t'.</xsl:text>
                    </xsl:message>
                    <xsl:value-of select="'%t'"/>
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:variable>

          <xsl:if test="$olink.debug != 0">
            <xsl:message>
              <xsl:text>Olink debug: xrefstyle template is '</xsl:text>
              <xsl:value-of select="$template"/>
              <xsl:text>'.</xsl:text>
            </xsl:message>
          </xsl:if>

          <xsl:call-template name="substitute-markup">
            <xsl:with-param name="template" select="$template"/>
            <xsl:with-param name="title">
              <xsl:for-each select="$target.database" >
                <xsl:call-template name="insert.targetdb.data">
                  <xsl:with-param name="data"
                                  select="key('targetptr-key', $olink.key)/ttl/node()"/>
                </xsl:call-template>
              </xsl:for-each>
            </xsl:with-param>
            <xsl:with-param name="label">
              <xsl:for-each select="$target.database" >
                <xsl:value-of 
                        select="key('targetptr-key', $olink.key)[1]/@number" />
              </xsl:for-each>
            </xsl:with-param>
            <xsl:with-param name="pagenumber">
              <xsl:for-each select="$target.database" >
                <xsl:value-of 
                        select="key('targetptr-key', $olink.key)[1]/@page" />
              </xsl:for-each>
            </xsl:with-param>
            <xsl:with-param name="docname">
              <xsl:for-each select="$target.database" >
                <xsl:call-template name="insert.targetdb.data">
                  <xsl:with-param name="data"
                       select="key('targetdoc-key', $targetdoc)[1]/div[1]/ttl/node()" />
                </xsl:call-template>
              </xsl:for-each>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>

        <xsl:when test="$use.local.olink.style != 0">

          <!-- Is autonumbering on? -->
          <xsl:variable name="target.number">
            <xsl:for-each select="$target.database" >
              <xsl:value-of 
                      select="key('targetptr-key', $olink.key)[1]/@number" />
            </xsl:for-each>
          </xsl:variable>

          <xsl:variable name="autonumber">
            <xsl:choose>
              <xsl:when test="$target.number != ''">1</xsl:when>
              <xsl:otherwise>0</xsl:otherwise>
            </xsl:choose>
          </xsl:variable>
        
          <xsl:variable name="number-and-title-template">
            <xsl:call-template name="gentext.template.exists">
              <xsl:with-param name="context" select="'xref-number-and-title'"/>
              <xsl:with-param name="name" select="$target.elem"/>
            </xsl:call-template>
          </xsl:variable>
        
          <xsl:variable name="number-template">
            <xsl:call-template name="gentext.template.exists">
              <xsl:with-param name="context" select="'xref-number'"/>
              <xsl:with-param name="name" select="$target.elem"/>
            </xsl:call-template>
          </xsl:variable>
        
          <xsl:variable name="context">
            <xsl:choose>
              <xsl:when test="string($autonumber) != 0 
                              and $number-and-title-template != 0
                              and $xref.with.number.and.title != 0">
                 <xsl:value-of select="'xref-number-and-title'"/>
              </xsl:when>
              <xsl:when test="string($autonumber) != 0 
                              and $number-template != 0">
                 <xsl:value-of select="'xref-number'"/>
              </xsl:when>
              <xsl:otherwise>
                 <xsl:value-of select="'xref'"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:variable>
        
          <xsl:variable name="template">
            <xsl:call-template name="gentext.template">
              <xsl:with-param name="context" select="$context"/>
              <xsl:with-param name="name" select="$target.elem"/>
              <xsl:with-param name="lang" select="$lang"/>
            </xsl:call-template>
          </xsl:variable>

          <xsl:call-template name="substitute-markup">
            <xsl:with-param name="template" select="$template"/>
            <xsl:with-param name="title">
              <xsl:for-each select="$target.database" >
                <xsl:call-template name="insert.targetdb.data">
                  <xsl:with-param name="data"
                               select="key('targetptr-key', $olink.key)[1]/ttl/node()" />
                </xsl:call-template>
              </xsl:for-each>
            </xsl:with-param>
            <xsl:with-param name="label">
              <xsl:for-each select="$target.database" >
                <xsl:call-template name="insert.targetdb.data">
                  <xsl:with-param name="data"
                          select="key('targetptr-key', $olink.key)[1]/@number" />
                </xsl:call-template>
              </xsl:for-each>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <xsl:when test="$xref.text !=''">
          <xsl:copy-of select="$xref.text"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:message>
            <xsl:text>Olink error: no generated text for </xsl:text>
            <xsl:text>targetdoc/targetptr/lang = '</xsl:text>
            <xsl:value-of select="$olink.key"/>
            <xsl:text>'.</xsl:text>
          </xsl:message>
          <xsl:text>????</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="$olink.key != ''">
        <xsl:message>
          <xsl:text>Olink error: no generated text for </xsl:text>
          <xsl:text>targetdoc/targetptr/lang = '</xsl:text>
          <xsl:value-of select="$olink.key"/>
          <xsl:text>'.</xsl:text>
        </xsl:message>
      </xsl:if>
      <xsl:text>????</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="insert.targetdb.data">
  <xsl:param name="data"/>
  <!-- Customize this to massage data further -->
  <xsl:copy-of select="$data"/>
</xsl:template>

<xsl:template match="*" mode="olink.docname.markup">
  <!-- No-op for now -->
</xsl:template>

<xsl:template name="targetpath">
  <xsl:param name="dirnode" />
  <xsl:param name="targetdoc" select="''"/>

<!-- 
<xsl:message>dirnode is <xsl:value-of select="$dirnode/@name"/></xsl:message>
<xsl:message>targetdoc is <xsl:value-of select="$targetdoc"/></xsl:message>
-->
  <!-- recursive template generates path to olink target directory -->
  <xsl:choose>
    <!-- Have we arrived at the final path step? -->
    <xsl:when test="$dirnode/child::document[@targetdoc = $targetdoc]">
      <!-- We are done -->
    </xsl:when>
    <!-- Have we reached the top without a match? -->
    <xsl:when test="local-name($dirnode) != 'dir'" >
        <xsl:message>Olink error: cannot locate targetdoc <xsl:value-of select="$targetdoc"/> in sitemap</xsl:message>
    </xsl:when>
    <!-- Is the target in a descendant? -->
    <xsl:when test="$dirnode/descendant::document/@targetdoc = $targetdoc">
      <xsl:variable name="step" select="$dirnode/child::dir[descendant::document/@targetdoc = $targetdoc]"/>
      <xsl:if test = "$step">
        <xsl:value-of select="$step/@name"/>
        <xsl:text>/</xsl:text>
      </xsl:if>
      <!-- Now recurse with the child -->
      <xsl:call-template name="targetpath" >
        <xsl:with-param name="dirnode" select="$step"/>
        <xsl:with-param name="targetdoc" select="$targetdoc"/>
      </xsl:call-template>
    </xsl:when>
    <!-- Otherwise we need to move up a step -->
    <xsl:otherwise>
      <xsl:if test="$dirnode/parent::dir">
        <xsl:text>../</xsl:text>
      </xsl:if>
      <xsl:call-template name="targetpath" >
        <xsl:with-param name="dirnode" select="$dirnode/parent::*"/>
        <xsl:with-param name="targetdoc" select="$targetdoc"/>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="olink.page.citation">
  <xsl:param name="olink.key" select="''"/>
  <xsl:param name="olink.lang" select="'en'"/>
  <xsl:param name="target.database"/>
  <xsl:param name="linkend" select="''"/>
  <xsl:param name="xrefstyle">
    <xsl:choose>
      <xsl:when test="@role and not(@xrefstyle) 
                      and $use.role.as.xrefstyle != 0">
        <xsl:value-of select="@role"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@xrefstyle"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:param>

  <xsl:variable name="targetdoc">
    <xsl:value-of select="substring-before($olink.key, '/')"/>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$linkend != ''">
      <xsl:call-template name="xref.page.citation">
        <xsl:with-param name="linkend" select="$linkend"/>
        <xsl:with-param name="target" select="key('id', $linkend)"/>
        <xsl:with-param name="xrefstyle" select="$xrefstyle"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:when test="not(starts-with(normalize-space($xrefstyle),
                        'select:') 
                and (contains($xrefstyle, 'page')
                     or contains($xrefstyle, 'Page')))
                and $current.docid != '' 
                and $current.docid != $targetdoc
                and $insert.olink.page.number = 'yes' ">
  
      <xsl:variable name="page-number">
        <xsl:for-each select="$target.database" >
          <xsl:value-of 
                 select="key('targetptr-key', $olink.key)[1]/@page" />
        </xsl:for-each>
      </xsl:variable>
  
      <xsl:if test="$page-number != ''">
        <xsl:call-template name="substitute-markup">
          <xsl:with-param name="template">
            <xsl:call-template name="gentext.template">
              <xsl:with-param name="name" select="'olink.page.citation'"/>
              <xsl:with-param name="context" select="'xref'"/>
              <xsl:with-param name="lang" select="$olink.lang"/>
            </xsl:call-template>
          </xsl:with-param>
          <xsl:with-param name="pagenumber" select="$page-number"/>
        </xsl:call-template>
      </xsl:if>
  
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template name="olink.document.citation">
  <xsl:param name="olink.key" select="''"/>
  <xsl:param name="olink.lang" select="'en'"/>
  <xsl:param name="target.database"/>
  <xsl:param name="xrefstyle">
    <xsl:choose>
      <xsl:when test="@role and not(@xrefstyle) 
                      and $use.role.as.xrefstyle != 0">
        <xsl:value-of select="@role"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@xrefstyle"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:param>

  <xsl:variable name="page">
    <xsl:for-each select="$target.database" >
      <xsl:value-of 
             select="key('targetptr-key', $olink.key)[1]/@page" />
    </xsl:for-each>
  </xsl:variable>

  <xsl:variable name="targetdoc">
    <xsl:value-of select="substring-before($olink.key, '/')"/>
  </xsl:variable>

  <xsl:variable name="targetptr">
    <xsl:value-of 
          select="substring-before(substring-after($olink.key, '/'), '/')"/>
  </xsl:variable>

  <!-- Don't add docname if pointing to root element -->
  <xsl:variable name="rootptr">
    <xsl:for-each select="$target.database" >
      <xsl:value-of 
             select="key('targetdoc-key', $targetdoc)[1]/div[1]/@targetptr" />
    </xsl:for-each>
  </xsl:variable>

  <xsl:variable name="docname">
    <xsl:for-each select="$target.database" >
      <xsl:call-template name="insert.targetdb.data">
        <xsl:with-param name="data"
             select="key('targetdoc-key', $targetdoc)[1]/div[1]/ttl/node()" />
      </xsl:call-template>
    </xsl:for-each>
  </xsl:variable>

  <xsl:if test="not(starts-with(normalize-space($xrefstyle), 'select:') 
              and (contains($xrefstyle, 'docname')))
              and ($olink.doctitle = 'yes' or $olink.doctitle = '1')
              and $current.docid != '' 
              and $rootptr != $targetptr
              and $current.docid != $targetdoc
              and $docname != ''">
    <xsl:call-template name="substitute-markup">
      <xsl:with-param name="template">
        <xsl:call-template name="gentext.template">
          <xsl:with-param name="name" select="'olink.document.citation'"/>
          <xsl:with-param name="context" select="'xref'"/>
          <xsl:with-param name="lang" select="$olink.lang"/>
        </xsl:call-template>
      </xsl:with-param>
      <xsl:with-param name="docname" select="$docname"/>
      <xsl:with-param name="pagenumber" select="$page"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="xref.page.citation">
  <!-- Determine if this xref should have a page citation.
       Context node is the xref or local olink element -->
  <xsl:param name="linkend" select="@linkend"/>
  <xsl:param name="target" select="key('id', $linkend)"/>
  <xsl:param name="xrefstyle">
    <xsl:choose>
      <xsl:when test="@role and not(@xrefstyle) 
                      and $use.role.as.xrefstyle != 0">
        <xsl:value-of select="@role"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@xrefstyle"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:param>

  <xsl:if test="not(starts-with(normalize-space($xrefstyle),'select:')
                    and (contains($xrefstyle, 'page')
                         or contains($xrefstyle, 'Page')))
                and ( $insert.xref.page.number = 'yes' 
                   or $insert.xref.page.number = '1')
                or local-name($target) = 'para'">
    <xsl:apply-templates select="$target" mode="page.citation">
      <xsl:with-param name="id" select="$linkend"/>
    </xsl:apply-templates>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>

