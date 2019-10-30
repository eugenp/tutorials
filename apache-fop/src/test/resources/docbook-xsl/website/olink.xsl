<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<xsl:param name="website.database.document"
           select="'website.database.xml'"/>

<xsl:template match="olink">
  <xsl:choose>
    <xsl:when test="@targetdoc != '' or @targetptr != ''">
      <xsl:apply-imports/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="olink-entity"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="olink-entity">
  <xsl:variable name="xmlfile"
                select="document(unparsed-entity-uri(@targetdocent),$autolayout)"/>
  <xsl:variable name="webpage"
                select="$xmlfile/webpage"/>
  <xsl:variable name="tocentry"
                select="$autolayout//*[$webpage/@id=@id]"/>

  <xsl:variable name="dir">
    <xsl:choose>
      <xsl:when test="starts-with($tocentry/@dir, '/')">
        <xsl:value-of select="substring($tocentry/@dir, 2)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$tocentry/@dir"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

<!-- debug
  <xsl:message>Olink for <xsl:value-of select="unparsed-entity-uri(@targetdocent)"/></xsl:message>
  <xsl:message>Page id <xsl:value-of select="$webpage/@id"/></xsl:message>
-->

  <xsl:choose>
    <xsl:when test="@type = 'embed'">
      <xsl:apply-templates select="$xmlfile"/>
    </xsl:when>
    <xsl:otherwise>
      <!-- @type = 'replace' or @type = 'new' -->
      <a>
        <xsl:if test="@id">
          <xsl:attribute name="name">
            <xsl:value-of select="@id"/>
          </xsl:attribute>
        </xsl:if>

<!-- debug
        <xsl:message>
          <xsl:text>href: </xsl:text>
          <xsl:call-template name="root-rel-path"/>
          <xsl:text>::</xsl:text>
          <xsl:value-of select="$dir"/>
          <xsl:text>::</xsl:text>
          <xsl:value-of select="$filename-prefix"/>
          <xsl:text>::</xsl:text>
          <xsl:value-of select="$tocentry/@filename"/>
          <xsl:text>::</xsl:text>
          <xsl:if test="@localinfo">
            <xsl:text>#</xsl:text>
            <xsl:value-of select="@localinfo"/>
          </xsl:if>
        </xsl:message>
-->

        <xsl:attribute name="href">
          <xsl:call-template name="root-rel-path"/>
          <xsl:value-of select="$dir"/>
          <xsl:value-of select="$filename-prefix"/>
          <xsl:value-of select="$tocentry/@filename"/>
          <xsl:if test="@localinfo">
            <xsl:text>#</xsl:text>
            <xsl:value-of select="@localinfo"/>
          </xsl:if>
        </xsl:attribute>

        <xsl:if test="@type = 'new'">
          <xsl:attribute name="target">_blank</xsl:attribute>
        </xsl:if>

        <xsl:choose>
          <xsl:when test="count(node()) = 0">
            <xsl:apply-templates select="$webpage/head/title"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates/>
          </xsl:otherwise>
        </xsl:choose>
      </a>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Customize the selection to use both website and offsite databases -->
<xsl:template name="select.target.database">
  <xsl:param name="targetdoc.att" select="''"/>
  <xsl:param name="targetptr.att" select="''"/>
  <xsl:param name="olink.lang" select="''"/>

  <!-- Is the target in the website database? -->
  <xsl:variable name="website.olink.key">
    <xsl:if test="$website.database.document != ''">
      <xsl:call-template name="select.olink.key">
        <xsl:with-param name="targetdoc.att" select="$targetdoc.att"/>
        <xsl:with-param name="targetptr.att" select="$targetptr.att"/>
        <xsl:with-param name="olink.lang" select="$olink.lang"/>
        <xsl:with-param name="target.database" 
                        select="document($website.database.document,/)"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>

  <!-- Is the target in the offsite database? -->
  <xsl:variable name="offsite.olink.key">
    <xsl:if test="$target.database.document != ''">
      <xsl:call-template name="select.olink.key">
        <xsl:with-param name="targetdoc.att" select="$targetdoc.att"/>
        <xsl:with-param name="targetptr.att" select="$targetptr.att"/>
        <xsl:with-param name="olink.lang" select="$olink.lang"/>
        <xsl:with-param name="target.database" 
                        select="document($target.database.document,/)"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$website.olink.key != ''">
      <xsl:value-of select="$website.database.document"/>
    </xsl:when>
    <xsl:when test="$offsite.olink.key != ''">
      <xsl:value-of select="$target.database.document"/>
    </xsl:when>
    <xsl:otherwise>
      <!-- Return a bogus string and let the olink template deal with it
      <xsl:text>NOMATCHINANYDATABASEDOCUMENT</xsl:text>
      -->
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Customize this template to use Website root-relative -->
<xsl:template name="make.olink.href">
  <xsl:param name="olink.key" select="''"/>
  <xsl:param name="target.database"/>

  <xsl:if test="$olink.key != ''">

    <xsl:variable name="targetdoc">
      <xsl:value-of select="substring-before($olink.key, '/')"/>
    </xsl:variable>
  
    <xsl:variable name="targetptr">
      <xsl:value-of select="substring-before(substring-after($olink.key, '/'),'/')"/>
    </xsl:variable>
  
    <xsl:variable name="target.href" >
      <xsl:for-each select="$target.database" >
        <xsl:value-of select="key('targetptr-key', $olink.key)/@href" />
      </xsl:for-each>
    </xsl:variable>
  
    <xsl:variable name="target.dir" >
      <xsl:for-each select="$target.database" >
        <xsl:value-of select="key('targetdoc-key', $targetdoc)/@dir" />
      </xsl:for-each>
    </xsl:variable>

    <xsl:variable name="target.element" >
      <xsl:for-each select="$target.database" >
        <xsl:value-of select="key('targetptr-key', $olink.key)/@element" />
      </xsl:for-each>
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
                                        $current.docid)/@targetdoc" />
                </xsl:for-each>
              </xsl:variable>
              <xsl:choose>
                <xsl:when test="$currentdoc.key != ''">
                  <xsl:for-each select="$target.database" >
                    <xsl:call-template name="targetpath" >
                      <xsl:with-param name="dirnode" 
                          select="key('targetdoc-key', $current.docid)/parent::dir"/>
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
              <xsl:value-of select="key('targetdoc-key', $targetdoc)/@baseuri" />
            </xsl:for-each>
          </xsl:variable>
          <xsl:if test="$docbaseuri != ''" >
            <xsl:value-of select="$docbaseuri"/>
          </xsl:if>
        </xsl:when>
        <!-- No database sitemap in use -->
        <xsl:otherwise>
          <!-- compute a root-relative path if current page has a @dir -->
          <xsl:variable name="root-rel">
            <xsl:call-template name="root-rel-path"/>
          </xsl:variable>
          <xsl:if test="$root-rel != ''">
            <xsl:value-of select="$root-rel"/>
          </xsl:if>
          <!-- Add the target's @dir to the path -->
          <xsl:if test="$target.dir != ''">
            <xsl:value-of select="$target.dir"/>
          </xsl:if>
          <!-- Just use any baseuri from its document entry -->
          <xsl:variable name="docbaseuri">
            <xsl:for-each select="$target.database" >
              <xsl:value-of select="key('targetdoc-key',
                                        $targetdoc)/@baseuri" />
            </xsl:for-each>
          </xsl:variable>
          <xsl:if test="$docbaseuri != ''" >
            <xsl:value-of select="$docbaseuri"/>
          </xsl:if>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
  
    <!-- Form the href information -->
    <xsl:if test="$baseuri != ''">
      <xsl:value-of select="$baseuri"/>
      <xsl:if test="substring($target.href,1,1) != '#'">
        <!--xsl:text>/</xsl:text-->
      </xsl:if>
    </xsl:if>
    <xsl:choose>
      <xsl:when test="$target.element = 'webpage' and
                      $targetdoc = $targetptr">
        <!-- Don't output #id because not needed -->
      </xsl:when>
      <!-- optionally turn off frag for PDF references -->
      <xsl:when test="not($insert.olink.pdf.frag = 0 and
            translate(substring($baseuri, string-length($baseuri) - 3),
                      'PDF', 'pdf') = '.pdf'
            and starts-with($target.href, '#') )">
        <xsl:value-of select="$target.href"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$target.href"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>
</xsl:template>


</xsl:stylesheet>
