<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:saxon="http://icl.com/saxon"
                exclude-result-prefixes="saxon"
                version="1.0">

<!-- Should be base URI for imagedata and so on fixed? -->
<xsl:param name="profile.baseuri.fixup" select="true()"/>

<!-- Copy all non-element nodes -->
<xsl:template match="@*|text()|comment()|processing-instruction()" mode="profile">
  <xsl:copy/>
</xsl:template>

<!-- Profile elements based on input parameters -->
<xsl:template match="*" mode="profile">

  <xsl:variable name="arch.content">
    <xsl:if test="@arch">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.arch"/>
        <xsl:with-param name="b" select="@arch"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="arch.ok" select="not(@arch) or not($profile.arch) or
                                       $arch.content != '' or @arch = ''"/>

  <xsl:variable name="audience.content">
    <xsl:if test="@audience">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.audience"/>
        <xsl:with-param name="b" select="@audience"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="audience.ok" 
                        select="not(@audience) or not($profile.audience) or
                                $audience.content != '' or @audience = ''"/>

  <xsl:variable name="condition.content">
    <xsl:if test="@condition">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.condition"/>
        <xsl:with-param name="b" select="@condition"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="condition.ok" select="not(@condition) or not($profile.condition) or
                                            $condition.content != '' or @condition = ''"/>

  <xsl:variable name="conformance.content">
    <xsl:if test="@conformance">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.conformance"/>
        <xsl:with-param name="b" select="@conformance"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="conformance.ok" select="not(@conformance) or not($profile.conformance) or
                                              $conformance.content != '' or @conformance = ''"/>

  <xsl:variable name="lang.content">
    <xsl:if test="@lang | @xml:lang">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.lang"/>
        <xsl:with-param name="b" select="(@lang | @xml:lang)[1]"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="lang.ok" select="not(@lang | @xml:lang) or not($profile.lang) or
                                       $lang.content != '' or @lang = '' or @xml:lang = ''"/>

  <xsl:variable name="os.content">
    <xsl:if test="@os">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.os"/>
        <xsl:with-param name="b" select="@os"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="os.ok" select="not(@os) or not($profile.os) or
                                     $os.content != '' or @os = ''"/>

  <xsl:variable name="revision.content">
    <xsl:if test="@revision">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.revision"/>
        <xsl:with-param name="b" select="@revision"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="revision.ok" select="not(@revision) or not($profile.revision) or
                                           $revision.content != '' or @revision = ''"/>

  <xsl:variable name="revisionflag.content">
    <xsl:if test="@revisionflag">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.revisionflag"/>
        <xsl:with-param name="b" select="@revisionflag"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="revisionflag.ok" select="not(@revisionflag) or not($profile.revisionflag) or
                                               $revisionflag.content != '' or @revisionflag = ''"/>

  <xsl:variable name="role.content">
    <xsl:if test="@role">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.role"/>
        <xsl:with-param name="b" select="@role"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="role.ok" select="not(@role) or not($profile.role) or
                                       $role.content != '' or @role = ''"/>

  <xsl:variable name="security.content">
    <xsl:if test="@security">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.security"/>
        <xsl:with-param name="b" select="@security"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="security.ok" select="not(@security) or not($profile.security) or
                                           $security.content != '' or @security = ''"/>

  <xsl:variable name="status.content">
    <xsl:if test="@status">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.status"/>
        <xsl:with-param name="b" select="@status"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="status.ok" select="not(@status) or not($profile.status) or
                                           $status.content != '' or @status = ''"/>

  <xsl:variable name="userlevel.content">
    <xsl:if test="@userlevel">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.userlevel"/>
        <xsl:with-param name="b" select="@userlevel"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="userlevel.ok" select="not(@userlevel) or not($profile.userlevel) or
                                            $userlevel.content != '' or @userlevel = ''"/>

  <xsl:variable name="vendor.content">
    <xsl:if test="@vendor">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.vendor"/>
        <xsl:with-param name="b" select="@vendor"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="vendor.ok" select="not(@vendor) or not($profile.vendor) or
                                         $vendor.content != '' or @vendor = ''"/>

  <xsl:variable name="wordsize.content">
    <xsl:if test="@wordsize">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.wordsize"/>
        <xsl:with-param name="b" select="@wordsize"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="wordsize.ok" 
                        select="not(@wordsize) or not($profile.wordsize) or
                                $wordsize.content != '' or @wordsize = ''"/>

  <xsl:variable name="attribute.content">
    <xsl:if test="@*[local-name()=$profile.attribute]">
      <xsl:call-template name="cross.compare">
        <xsl:with-param name="a" select="$profile.value"/>
        <xsl:with-param name="b" select="@*[local-name()=$profile.attribute]"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:variable>
  <xsl:variable name="attribute.ok" 
                select="not(@*[local-name()=$profile.attribute]) or 
                        not($profile.value) or $attribute.content != '' or 
                        @*[local-name()=$profile.attribute] = '' or 
                        not($profile.attribute)"/>

  <xsl:if test="$arch.ok and 
                $audience.ok and 
                $condition.ok and 
                $conformance.ok and 
                $lang.ok and 
                $os.ok and 
                $revision.ok and 
                $revisionflag.ok and 
                $role.ok and 
                $security.ok and 
                $status.ok and 
                $userlevel.ok and 
                $vendor.ok and 
                $wordsize.ok and 
                $attribute.ok">
    <xsl:copy>
      <xsl:apply-templates mode="profile" select="@*"/>

      <!-- Entity references must be replaced with filereferences for temporary tree -->
      <xsl:if test="@entityref and $profile.baseuri.fixup">
        <xsl:attribute name="fileref">
          <xsl:value-of select="unparsed-entity-uri(@entityref)"/>
        </xsl:attribute>
      </xsl:if>

      <!-- xml:base is eventually added to the root element -->
      <xsl:if test="not(../..) and $profile.baseuri.fixup">
        <xsl:call-template name="add-xml-base"/>
      </xsl:if>

      <xsl:apply-templates select="node()" mode="profile"/>
    </xsl:copy>
  </xsl:if>
</xsl:template>

<!-- Returns non-empty string if list in $b contains one ore more values from list $a -->
<xsl:template name="cross.compare">
  <xsl:param name="a"/>
  <xsl:param name="b"/>
  <xsl:param name="sep" select="$profile.separator"/>
  <xsl:variable name="head" select="substring-before(concat($a, $sep), $sep)"/>
  <xsl:variable name="tail" select="substring-after($a, $sep)"/>
<!-- <xsl:message> -->
<!-- a="<xsl:value-of select="$a"/>" -->
<!-- a="<xsl:value-of select="normalize-space($a)"/>" -->
<!-- head="<xsl:value-of select="$head"/>" -->
<!-- tail="<xsl:value-of select="$tail"/>" -->
<!-- </xsl:message> -->
  <xsl:if test="contains(concat($sep, $b, $sep), concat($sep, $head, $sep)) or normalize-space($a) = '' ">1</xsl:if>
  <xsl:if test="$tail">
    <xsl:call-template name="cross.compare">
      <xsl:with-param name="a" select="$tail"/>
      <xsl:with-param name="b" select="$b"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>

