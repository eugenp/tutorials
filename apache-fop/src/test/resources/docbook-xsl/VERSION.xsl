<?xml version='1.0'?> <!-- -*- nxml -*- vim: set foldlevel=2: -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fm="http://freshmeat.net/projects/freshmeat-submit/"
  xmlns:sf="http://sourceforge.net/"
  xmlns:dyn="http://exslt.org/dynamic"
  xmlns:saxon="http://icl.com/saxon"
  exclude-result-prefixes="fm sf"
  version='1.0'>

<xsl:param name="get"/>
<xsl:param name="VERSION" select="string(document('')//fm:Version[1])"/>
<xsl:param name="Tag" select="concat('V',translate(string(document('')//fm:Version[1]),'.',''))"/>
<xsl:param name="DistroTitle" select="string(document('')//fm:Branch[1])"/>
<xsl:param name="sf-relid" select="0"/>

<xsl:param name="DistroName">docbook-xsl</xsl:param>
<xsl:param name="PreviousRelease">1.78.0</xsl:param>
<xsl:param name="PreviousReleaseRevision">9696</xsl:param>
<xsl:param name="Revision">$Revision: 9731 $</xsl:param>
<xsl:param name="VersionFileURL">$URL: https://docbook.svn.sourceforge.net/svnroot/docbook/trunk/xsl/VERSION $</xsl:param>

<xsl:strip-space elements="fm:*"/>

<fm:project>
  <fm:Project>DocBook</fm:Project>
  <fm:Branch>XSL Stylesheets</fm:Branch>
  <!-- * set/keep fm:version as N.NN.N-pre except for official releases, -->
  <!-- * then after the release, revert it to N.NN.N-pre & check back in -->
  <fm:Version>1.78.1</fm:Version>
<!--
  <fm:License>MIT/X Consortium License</fm:License>
-->
  <fm:Release-Focus>
<!-- * Initial freshmeat announcement -->
<!-- * Documentation -->
<!-- * Code cleanup -->
<!-- * Minor feature enhancements  -->
* Major feature enhancements 
<!-- * Minor bugfixes  -->
<!-- * Major bugfixes -->
<!-- * Minor security fixes -->
<!-- * Major security fixes -->
  </fm:Release-Focus>
  <fm:Home-Page-URL>http://sourceforge.net/projects/docbook/</fm:Home-Page-URL>
  <fm:Gzipped-Tar-URL>http://prdownloads.sourceforge.net/docbook/{DISTRONAME-VERSION}.tar.gz?download</fm:Gzipped-Tar-URL>
  <fm:Zipped-Tar-URL>http://prdownloads.sourceforge.net/docbook/{DISTRONAME-VERSION}.zip?download</fm:Zipped-Tar-URL>
  <fm:Bzipped-Tar-URL>http://prdownloads.sourceforge.net/docbook/{DISTRONAME-VERSION}.bz2?download</fm:Bzipped-Tar-URL>
  <fm:Changelog-URL>http://sourceforge.net/project/shownotes.php?release_id={SFRELID}</fm:Changelog-URL>
  <fm:CVS-URL>http://docbook.svn.sourceforge.net/viewvc/docbook/</fm:CVS-URL>
  <fm:Mailing-List-URL>http://lists.oasis-open.org/archives/docbook-apps/</fm:Mailing-List-URL>
  <fm:Changes>This is a release with bugfixes and some enhancements.</fm:Changes>
</fm:project>

<xsl:template match="/" priority="-100">
  <xsl:choose>
    <xsl:when test="$get = 'Tag'">
      <xsl:value-of select="$Tag"/>
    </xsl:when>
    <xsl:when test="$get = 'PreviousRelease'">
      <xsl:value-of select="$PreviousRelease"/>
    </xsl:when>
    <xsl:when test="$get = 'PreviousReleaseRevision'">
      <xsl:value-of select="$PreviousReleaseRevision"/>
    </xsl:when>
    <xsl:when test="$get = 'DistroTitle'">
      <xsl:value-of select="$DistroTitle"/>
    </xsl:when>
    <xsl:when test="$get = 'VERSION'">
      <xsl:value-of select="$VERSION"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="$sf-relid = 0">
        <xsl:message terminate="yes">
         <xsl:text>You must specify the sf-relid as a parameter.</xsl:text>
        </xsl:message>
      </xsl:if>
      <xsl:apply-templates select="//fm:project"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="fm:project">
  <xsl:apply-templates/>
  <xsl:text>&#10;</xsl:text>
  <xsl:apply-templates select="fm:Changes" mode="text"/>
</xsl:template>

<xsl:template match="fm:Changes"/>

<xsl:template match="fm:Gzipped-Tar-URL|fm:Zipped-Tar-URL|fm:Bzipped-Tar-URL">
  <xsl:value-of select="local-name(.)"/>
  <xsl:text>: </xsl:text>
  <xsl:value-of select="substring-before(., '{DISTRONAME-VERSION}')"/>
  <xsl:value-of select="concat($DistroName, '-', $VERSION)"/>
  <xsl:value-of select="substring-after(., '{DISTRONAME-VERSION}')"/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="fm:Changelog-URL">
  <xsl:value-of select="local-name(.)"/>
  <xsl:text>: </xsl:text>
  <xsl:value-of select="substring-before(., '{SFRELID}')"/>
  <xsl:value-of select="$sf-relid"/>
  <xsl:value-of select="substring-after(., '{SFRELID}')"/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

<xsl:template match="fm:*">
  <xsl:value-of select="local-name(.)"/>
  <xsl:text>: </xsl:text>
  <xsl:value-of select="normalize-space(.)"/>
  <xsl:text>&#10;</xsl:text>
</xsl:template>

</xsl:stylesheet>
