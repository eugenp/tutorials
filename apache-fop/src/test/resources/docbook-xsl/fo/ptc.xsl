<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:fo="http://www.w3.org/1999/XSL/Format"
		version="1.0">

<!-- ================================================================ -->
<!--                                                                  -->
<!-- PTC/Arbortext Code for XSL 1.1 bookmark support                  -->
<!--                                                                  -->
<!-- ================================================================ -->

<xsl:param name="ati.xsl11.bookmarks" select="1"/>

<xsl:variable name="ati-a-dia" select=
"'&#257;&#259;&#261;&#263;&#265;&#267;&#269;&#271;&#273;&#275;&#277;&#279;&#281;&#283;&#339;&#285;&#287;&#289;&#291;&#293;&#295;&#297;&#299;&#301;&#303;&#305;&#309;&#311;&#314;&#316;&#318;&#320;&#322;&#324;&#326;&#328;&#331;&#333;&#335;&#337;&#341;&#343;&#345;&#347;&#349;&#351;&#353;&#355;&#357;&#359;&#361;&#363;&#365;&#367;&#369;&#371;&#373;&#375;&#378;&#380;&#382;&#256;&#258;&#260;&#262;&#264;&#266;&#268;&#270;&#272;&#274;&#276;&#278;&#280;&#282;&#338;&#284;&#286;&#288;&#290;&#292;&#294;&#296;&#298;&#300;&#302;&#304;&#308;&#310;&#313;&#315;&#317;&#319;&#321;&#323;&#325;&#327;&#330;&#332;&#334;&#336;&#340;&#342;&#344;&#346;&#348;&#350;&#352;&#354;&#356;&#358;&#360;&#362;&#364;&#366;&#368;&#370;&#372;&#374;&#376;&#377;&#379;&#381;'"/>

<xsl:variable name="ati-a-asc" select=
"'aaaccccddeeeeeegggghhiiiiijklllllnnnnooorrrsssstttuuuuuuwyzzzAAACCCCDDEEEEEEGGGGHHIIIIIJKLLLLLNNNNOOORRRSSSSTTTUUUUUUWYYZZZ'"/>

<xsl:template match="*" mode="ati.xsl11.bookmarks">
  <xsl:apply-templates select="*" mode="ati.xsl11.bookmarks"/>
</xsl:template>

<xsl:template match="set|book|part|reference|preface|chapter|appendix|article
                     |glossary|bibliography|index|setindex
                     |refentry
                     |sect1|sect2|sect3|sect4|sect5|section"
              mode="ati.xsl11.bookmarks">
  <xsl:variable name="id">
    <xsl:call-template name="object.id"/>
  </xsl:variable>
  <xsl:variable name="bookmark-label">
    <xsl:apply-templates select="." mode="object.title.markup"/>
  </xsl:variable>

  <!-- Put the root element bookmark at the same level as its children -->
  <!-- If the object is a set or book, generate a bookmark for the toc -->

  <xsl:choose>
    <xsl:when test="self::index and $generate.index = 0"/>	
    <xsl:when test="parent::*">
      <fo:bookmark internal-destination="{$id}">
        <fo:bookmark-title>
          <xsl:value-of select="translate($bookmark-label, $ati-a-dia, $ati-a-asc)"/>
        </fo:bookmark-title>
        <xsl:apply-templates select="*" mode="ati.xsl11.bookmarks"/>
      </fo:bookmark>
    </xsl:when>
    <xsl:otherwise>
      <fo:bookmark internal-destination="{$id}">
        <fo:bookmark-title>
          <xsl:value-of select="translate($bookmark-label, $ati-a-dia, $ati-a-asc)"/>
        </fo:bookmark-title>
      </fo:bookmark>

      <xsl:variable name="toc.params">
        <xsl:call-template name="find.path.params">
          <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:if test="contains($toc.params, 'toc')
                    and section|sect1|refentry
                        |article|bibliography|glossary
                        |appendix">
        <fo:bookmark internal-destination="toc...{$id}">
          <fo:bookmark-title>
            <xsl:call-template name="gentext">
              <xsl:with-param name="key" select="'TableofContents'"/>
            </xsl:call-template>
          </fo:bookmark-title>
        </fo:bookmark>
      </xsl:if>
      <xsl:apply-templates select="*" mode="ati.xsl11.bookmarks"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

</xsl:stylesheet>
