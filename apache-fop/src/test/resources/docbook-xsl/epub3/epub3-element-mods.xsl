<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY uppercase "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'">
<!ENTITY lowercase "'abcdefghijklmnopqrstuvwxyz'">
]>

<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:exsl="http://exslt.org/common"
  xmlns:set="http://exslt.org/sets"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:epub="http://www.idpf.org/2007/ops"
  xmlns:m="http://www.w3.org/1998/Math/MathML"
  xmlns:pls="http://www.w3.org/2005/01/pronunciation-lexicon"
  xmlns:ssml="http://www.w3.org/2001/10/synthesis"
  xmlns:svg="http://www.w3.org/2000/svg"
  xmlns:opf="http://www.idpf.org/2007/opf"
  xmlns:dc="http://purl.org/dc/elements/1.1/"  
  xmlns:cf="http://docbook.sourceforge.net/xmlns/chunkfast/1.0"
  xmlns:date="http://exslt.org/dates-and-times"
  xmlns:dcterms="http://purl.org/dc/terms/"
  xmlns:ncx="http://www.daisy.org/z3986/2005/ncx/"
  xmlns:db="http://docbook.org/ns/docbook"
  xmlns:stext="http://nwalsh.com/xslt/ext/com.nwalsh.saxon.TextFactory"
  xmlns:str="http://exslt.org/strings"
  xmlns:xtext="xalan://com.nwalsh.xalan.Text"

  extension-element-prefixes="stext xtext"
  exclude-result-prefixes="#default cf date db dc dcterms epub exsl m ncx opf pls set ssml stext str svg xtext"
  version="1.0">

<!-- $Id: epub3-element-mods.xsl,v 1.1 2011-09-16 21:43:45 bobs Exp $ -->

<xsl:import href="titlepage.templates.xsl"/>

<!--
<xsl:key name="image-filerefs" match="graphic|inlinegraphic|imagedata" use="@fileref"/>
-->

<!--==============================================================-->
<!--  DocBook XSL Parameter settings                              -->
<!--==============================================================-->
<!-- separate file for toc -->
<xsl:param name="chunk.tocs.and.lots" select="1"/>
<xsl:param name="toc.section.depth" select="2"/>
<xsl:param name="generate.toc">
book  toc,title,figure,table,example,equation
article  toc,title,figure,table,example,equation
</xsl:param>
<xsl:param name="generate.manifest" select="0"/>
<xsl:param name="manifest.in.base.dir" select="1"/>
<xsl:param name="base.dir" select="'OEBPS/'"/>
<xsl:param name="index.links.to.section" select="0"/>

<!-- Epub does not yet support external links -->
<xsl:param name="activate.external.olinks" select="0"/>

<!-- Turning this on crashes ADE, which is unbelievably awesome -->
<xsl:param name="formal.object.break.after">0</xsl:param>

<xsl:param name="callout.graphics" select="1"/>
<xsl:param name="callout.graphics.extension">.png</xsl:param>
<xsl:param name="callout.graphics.number.limit" select="15"/>
<xsl:param name="callout.graphics.path" select="'images/callouts/'"/>
<xsl:param name="show.comments" select="0"/>

<!-- no navigation in .epub -->
<xsl:param name="suppress.navigation" select="'1'"/> 

<!-- EPUB3: use ol lists in table of contents -->
<xsl:param name="toc.list.type">ol</xsl:param>
<xsl:param name="autotoc.label.in.hyperlink" select="1"/>

<xsl:param name="css.decoration" select="1"/>
<!-- generate the css file from a source file -->
<xsl:param name="make.clean.html" select="1"/>
<!-- specify the default epub3 stylesheet -->
<xsl:param name="docbook.css.source">docbook-epub.css.xml</xsl:param>
<!-- for custom CSS, use the custom.css.source param -->
<xsl:param name="custom.css.source"></xsl:param>

<!--==============================================================-->
<!--  New EPUB3 Parameters                                        -->
<!--==============================================================-->
<xsl:param name="epub.version">3.0</xsl:param>
<!-- optional ncx for backwards compatibility -->
<xsl:param name="epub.include.ncx" select="1"/>
<xsl:param name="epub.ncx.depth">4</xsl:param> <!-- Not functional until http://code.google.com/p/epubcheck/issues/detail?id=70 is resolved -->
<!-- currently optional duplicate dcterms properties, may be required in future -->
<xsl:param name="epub.include.metadata.dcterms" select="1"/>
<!-- optional guide element for backwards compatibility -->
<xsl:param name="epub.include.guide" select="1"/>
<!-- some dc: currently required, to be replaced in future version -->
<xsl:param name="epub.include.metadata.dc.elements" select="1"/>
<!-- Some dc: elements will remain optional according to the spec -->
<xsl:param name="epub.include.optional.metadata.dc.elements" select="1"/>
<xsl:param name="epub.autolabel" select="0"/>
<xsl:param 
  name="epub.vocabulary.profile.content">http://www.idpf.org/epub/30/profile/content/</xsl:param>
<xsl:param 
  name="epub.vocabulary.profile.package">http://www.idpf.org/epub/30/profile/package/</xsl:param>
<xsl:param name="epub.output.epub.types" select="1"/>
<xsl:param name="epub.oebps.dir" select="'OEBPS'"/> 
<xsl:param name="epub.metainf.dir" select="'META-INF/'"/> 
<xsl:param name="epub.ncx.filename" select="'toc.ncx'"/> 
<xsl:param name="epub.mimetype.filename" select="'mimetype'"/> 
<xsl:param name="epub.mimetype.value" select="'application/epub+zip'"/> 
<xsl:param name="epub.container.filename" select="'container.xml'"/> 
<xsl:param name="epub.package.filename" select="'package.opf'"/> 
<xsl:param name="epub.cover.filename" select="concat('cover', $html.ext)"/> 
<xsl:param name="epub.cover.linear" select="0" />

<!-- names of id attributes used in package files -->
<xsl:param name="epub.meta.identifier.id">meta-identifier</xsl:param> 
<xsl:param name="epub.dc.identifier.id">pub-identifier</xsl:param> 
<xsl:param name="epub.meta.title.id">meta-title</xsl:param> 
<xsl:param name="epub.dc.title.id">pub-title</xsl:param> 
<xsl:param name="epub.meta.language.id">meta-language</xsl:param> 
<xsl:param name="epub.dc.language.id">pub-language</xsl:param> 
<xsl:param name="epub.meta.creator.id">meta-creator</xsl:param> 
<xsl:param name="epub.dc.creator.id">pub-creator</xsl:param> 
<xsl:param name="epub.ncx.toc.id">ncxtoc</xsl:param>
<xsl:param name="epub.ncx.manifest.id">ncx</xsl:param>
<xsl:param name="epub.ncx.mediatype">application/x-dtbncx+xml</xsl:param>
<xsl:param name="epub.xhtml.mediatype">application/xhtml+xml</xsl:param>
<xsl:param name="epub.html.toc.id">htmltoc</xsl:param>
<xsl:param name="epub.cover.filename.id" select="'cover'"/> 
<xsl:param name="epub.cover.image.id" select="'cover-image'"/> 

<xsl:param name="epub.embedded.fonts"></xsl:param>
<xsl:param name="epub.namespace">http://www.idpf.org/2007/ops</xsl:param>
<xsl:param name="opf.namespace">http://www.idpf.org/2007/opf</xsl:param>
<xsl:param name="ncx.namespace">http://www.daisy.org/z3986/2005/ncx/</xsl:param>
<xsl:param name="dc.namespace">http://purl.org/dc/elements/1.1/</xsl:param>
<!-- prefix generated ids in package elements so they differ from content ids -->
<xsl:param name="epub.package.id.prefix">id-</xsl:param>
<!-- editor is either a creator or contributor -->
<xsl:param name="editor.property">contributor</xsl:param> 

<!-- Generate full output path -->
<xsl:param name="epub.package.dir" select="concat($chunk.base.dir, '../')"/>

<xsl:param name="epub.ncx.pathname" 
           select="concat($chunk.base.dir, $epub.ncx.filename)"/>
<xsl:param name="epub.container.pathname"
           select="concat($epub.package.dir, $epub.metainf.dir, 
           $epub.container.filename)"/>
<xsl:param name="epub.package.pathname"
           select="concat($chunk.base.dir, $epub.package.filename)"/>
<xsl:param name="epub.cover.pathname"
           select="concat($chunk.base.dir, $epub.cover.filename)"/>
<xsl:param name="epub.mimetype.pathname"
           select="concat($epub.package.dir, $epub.mimetype.filename)"/>

<xsl:param name="kindle.extensions" select="0"/>

<!--==============================================================-->
<!--  Internal variables used for computing certain metadata      -->
<!--==============================================================-->
<xsl:variable name="epub3.chunk.hierarchy">
  <xsl:apply-templates select="/*" mode="find.chunks"/>
</xsl:variable>

<xsl:variable name="chunkset" select="exsl:node-set($epub3.chunk.hierarchy)//cf:div"/>

<!--==============================================================-->
<!--  Template customizations                                     -->
<!--==============================================================-->

<!-- This is used only by ncx piece -->
<xsl:variable name="root.is.a.chunk">
  <xsl:choose>
    <xsl:when test="/*[not(self::book)][not(sect1) or not(section)]">
      <xsl:text>1</xsl:text>
    </xsl:when>
    <xsl:when test="/book[*[last()][self::bookinfo]]|book[bookinfo]">
      <xsl:text>1</xsl:text>
    </xsl:when>
    <xsl:when test="/book[*[last()][self::info]]|book[info]">
      <xsl:text>1</xsl:text>
    </xsl:when>
    <xsl:when test="/bibliography">
      <xsl:text>1</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>0</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:variable>

<!-- EPUB3: Add generation of epub publication files -->
<xsl:template match="*" mode="process.root">
  <xsl:variable name="doc" select="self::*"/>

  <xsl:call-template name="user.preroot"/>
  <xsl:call-template name="root.messages"/>

  <html>
    <head>
      <xsl:call-template name="system.head.content">
        <xsl:with-param name="node" select="$doc"/>
      </xsl:call-template>
      <xsl:call-template name="head.content">
        <xsl:with-param name="node" select="$doc"/>
      </xsl:call-template>
      <xsl:call-template name="user.head.content">
        <xsl:with-param name="node" select="$doc"/>
      </xsl:call-template>
    </head>
    <body>
      <xsl:call-template name="body.attributes"/>
      <xsl:call-template name="user.header.content">
        <xsl:with-param name="node" select="$doc"/>
      </xsl:call-template>
      <xsl:apply-templates select="."/>
      <xsl:call-template name="user.footer.content">
        <xsl:with-param name="node" select="$doc"/>
      </xsl:call-template>
    </body>
  </html>
  <xsl:value-of select="$html.append"/>
  
  <!-- Generate any css files only once, not once per chunk -->
  <xsl:call-template name="generate.css.files"/>

  <xsl:call-template name="generate.epub.files"/>

</xsl:template>

<xsl:template name="generate.epub.files">
  <!-- Generate epub3 files -->
  <xsl:message>
    <xsl:text>Generating EPUB package files.</xsl:text>
  </xsl:message>

  <xsl:apply-templates select="." mode="opf"/>
  <xsl:apply-templates select="." mode="container"/>
  <xsl:call-template name="mimetype"/>
  <xsl:if test="$epub.include.ncx != 0">
    <xsl:call-template name="ncx"/>
  </xsl:if>
</xsl:template>

<!-- Generate the package file -->
<xsl:template match="*" mode="opf">

  <xsl:variable name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:variable>

  <xsl:variable name="content">
    <!-- use xsl:element so output does not have a namespace prefix -->
    <xsl:element name="package" namespace="{$opf.namespace}">
      <!-- Add the package namespaces at the top -->
      <xsl:call-template name="add.package.namespaces"/>

      <xsl:attribute name="version">
        <xsl:value-of select="$epub.version"/>
      </xsl:attribute>
      <xsl:attribute name="xml:lang">
        <xsl:value-of select="$lang"/>
      </xsl:attribute>
      <!-- No profile att in 2011-09-06 spec 
      <xsl:attribute name="profile">
        <xsl:value-of select="$epub.vocabulary.profile.package"/>
      </xsl:attribute>
      -->
      <xsl:attribute name="unique-identifier">
        <xsl:value-of select="$epub.dc.identifier.id"/>
      </xsl:attribute>


      <xsl:call-template name="package.metadata"/>
      <xsl:call-template name="package.manifest"/>
      <xsl:call-template name="package.spine"/>

      <xsl:if test="$epub.include.guide != 0">
        <xsl:call-template name="package.guide"/>
      </xsl:if>

    </xsl:element>
  </xsl:variable>

  <xsl:call-template name="write.chunk">
    <xsl:with-param name="filename">
      <xsl:value-of select="$epub.package.pathname" />
    </xsl:with-param>
    <xsl:with-param name="content" select="$content"/>
    <xsl:with-param name="method" select="'xml'" />
    <xsl:with-param name="encoding" select="'utf-8'" />
    <xsl:with-param name="indent" select="'yes'" />
    <xsl:with-param name="quiet" select="$chunk.quietly" />
    <xsl:with-param name="doctype-public" select="''"/> <!-- intentionally blank -->
    <xsl:with-param name="doctype-system" select="''"/> <!-- intentionally blank -->
  </xsl:call-template>
</xsl:template>

<xsl:template name="package.metadata">

  <xsl:element name="metadata" namespace="{$opf.namespace}">

    <xsl:call-template name="metadata.identifier"/>
    <xsl:call-template name="metadata.title"/>
    <xsl:call-template name="metadata.language"/>
    <xsl:call-template name="metadata.modified"/>
    <xsl:call-template name="metadata.cover"/>
    <xsl:call-template name="metadata.other.info"/>
    
  </xsl:element>
</xsl:template>

<xsl:template name="doc.title">
  <xsl:apply-templates select="." mode="title.markup"/>
</xsl:template>

<xsl:template name="metadata.identifier">
  <xsl:variable name="package.id.value">
    <xsl:call-template name="package-identifier"/>
  </xsl:variable>

  <xsl:if test="$epub.include.metadata.dc.elements != 0">
    <!-- dc:identifier element -->
    <dc:identifier>
      <xsl:attribute name="id">
        <xsl:value-of select="$epub.dc.identifier.id"/>
      </xsl:attribute>
      <!--
      <xsl:attribute name="prefer">
        <xsl:value-of select="$epub.meta.identifier.id"/>
      </xsl:attribute>
      -->
      <xsl:copy-of select="$package.id.value"/>
    </dc:identifier>
  </xsl:if>

  <xsl:if test="$epub.include.metadata.dcterms != 0">
    <!-- equivalent meta identifier element -->
    <xsl:element name="meta" namespace="{$opf.namespace}">
      <xsl:attribute name="id">
        <xsl:value-of select="$epub.meta.identifier.id"/>
      </xsl:attribute>
      <xsl:attribute name="property">dcterms:identifier</xsl:attribute>
      <xsl:copy-of select="$package.id.value"/>
    </xsl:element>
  </xsl:if>
</xsl:template>

<xsl:template name="metadata.title">
  <xsl:variable name="doc.title">
    <xsl:call-template name="doc.title"/>
  </xsl:variable>

  <xsl:if test="$epub.include.metadata.dc.elements != 0">
    <dc:title>
      <xsl:attribute name="id">
        <xsl:value-of select="$epub.dc.title.id"/>
      </xsl:attribute>
      <!--
      <xsl:attribute name="prefer">
        <xsl:value-of select="$epub.meta.title.id"/>
      </xsl:attribute>
      -->
      <xsl:value-of select="normalize-space($doc.title)"/>
    </dc:title>
  </xsl:if>

  <xsl:if test="$epub.include.metadata.dcterms != 0">
  <!-- equivalent meta title element -->
    <xsl:element name="meta" namespace="{$opf.namespace}">
      <xsl:attribute name="property">dcterms:title</xsl:attribute>
      <xsl:attribute name="id">
        <xsl:value-of select="$epub.meta.title.id"/>
      </xsl:attribute>
      <xsl:value-of select="normalize-space($doc.title)"/>
    </xsl:element>
  </xsl:if>
</xsl:template>

<xsl:template name="metadata.language">
  <xsl:variable name="lang">
    <xsl:call-template name="l10n.language"/>
  </xsl:variable>

  <xsl:if test="$epub.include.metadata.dc.elements != 0">
    <dc:language>
      <xsl:if test="$kindle.extensions = 0">
        <xsl:attribute name="id">
          <xsl:value-of select="$epub.dc.language.id"/>
        </xsl:attribute>
      </xsl:if>
      <xsl:value-of select="$lang"/>
    </dc:language>
  </xsl:if>

  <xsl:if test="$epub.include.metadata.dcterms != 0">
  <!-- equivalent meta lang element -->
    <xsl:element name="meta" namespace="{$opf.namespace}">
      <xsl:attribute name="property">dcterms:language</xsl:attribute>
      <xsl:attribute name="id">
        <xsl:value-of select="$epub.meta.language.id"/>
      </xsl:attribute>
      <xsl:value-of select="$lang"/>
    </xsl:element>
  </xsl:if>
</xsl:template>
  
<xsl:template name="metadata.modified">
  <xsl:variable name="local.datetime" select="date:date-time()"/>
  <xsl:variable name="utc.datetime">
    <xsl:call-template name="convert.date.to.utc">
      <xsl:with-param name="date" select="$local.datetime"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="string-length($utc.datetime) != 0">
      <xsl:element name="meta" namespace="{$opf.namespace}">
        <xsl:attribute name="property">dcterms:modified</xsl:attribute>
        <xsl:value-of select="$utc.datetime"/>
      </xsl:element>
      <xsl:comment>The preceding date value is actually local time (not UTC) in UTC format because there is no function in XSLT 1.0 to generate a correct UTC time</xsl:comment>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message terminate="yes">
        <xsl:text>ERROR: no last-modified date value could be determined, </xsl:text>
        <xsl:text>so cannot output required meta element with </xsl:text>
        <xsl:text>dcterms:modified attribute. Exiting.</xsl:text>
      </xsl:message>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="convert.date.to.utc">
  <xsl:param name="date" select="''"/>
  <!-- input format is YYYY-MM-DDTHH:MM:SS-X:00
     where -X:00 is the offset from UTC. -->

  <!-- output format is YYYY-MM-DDTHH:MM:SSZ with no offset -->
  <!-- FIX ME:  Not so easy without a proper UTC date function. -->
  <!-- Currently it just converts the local time to this format, which is
       not the correct UTC time. -->
  <xsl:value-of select="concat(substring($date,1,19), 'Z')"/>
</xsl:template>

<!-- This cover meta element used by kindlegen, at least -->
<xsl:template name="metadata.cover">
  <xsl:variable name="info" select="./*[contains(local-name(.), 'info')][1]"/>
  <xsl:variable name="cover.image" 
                select="$info//mediaobject[@role='cover' or ancestor::cover]"/>

  <xsl:if test="$cover.image">
    <xsl:element name="meta" namespace="{$opf.namespace}">
      <xsl:attribute name="content">
        <xsl:value-of select="$epub.cover.image.id"/>
      </xsl:attribute>
      <xsl:attribute name="name">cover</xsl:attribute>
    </xsl:element>
  </xsl:if>
</xsl:template>

<xsl:template name="metadata.other.info">
  <!-- Take info relative to selected root element -->
  <xsl:variable name="info" select="./*[contains(local-name(.), 'info')][1]"/>

  <xsl:apply-templates select="$info/*" mode="opf.metadata"/>        
</xsl:template>

<xsl:template match="*" mode="opf.metadata">
  <!-- default is no output -->
</xsl:template>

<xsl:template match="authorgroup" mode="opf.metadata">
  <xsl:apply-templates select="*" mode="opf.metadata"/>
</xsl:template>

<xsl:template match="author|corpauthor" mode="opf.metadata">
  <xsl:variable name="n">
    <xsl:choose>
      <xsl:when test="self::corpauthor">
        <xsl:apply-templates/>
      </xsl:when>
      <xsl:when test="org/orgname">
        <xsl:apply-templates select="org/orgname"/>
      </xsl:when>
      <xsl:when test="orgname">
        <xsl:apply-templates select="orgname"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="person.name">
          <xsl:with-param name="node" select="."/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="string-length($n) != 0">
    <xsl:element name="meta" namespace="{$opf.namespace}">
      <xsl:attribute name="id">
        <xsl:value-of select="concat($epub.meta.creator.id, position())"/>
      </xsl:attribute>
      <xsl:attribute name="property">dcterms:creator</xsl:attribute>
      <xsl:value-of select="normalize-space(string($n))"/>
    </xsl:element>

    <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
      <dc:creator>
        <xsl:attribute name="id">
          <xsl:value-of select="concat($epub.dc.creator.id, position())"/>
        </xsl:attribute>
        <xsl:value-of select="$n"/>
      </dc:creator>
    </xsl:if>
  </xsl:if>
</xsl:template>

<xsl:template match="editor" mode="opf.metadata">
  <xsl:variable name="n">
    <xsl:choose>
      <xsl:when test="orgname">
        <xsl:apply-templates select="orgname"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="person.name">
          <xsl:with-param name="node" select="."/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="name">
    <xsl:choose>
      <xsl:when test="string-length($editor.property) != 0">
        <xsl:value-of select="$editor.property"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>contributor</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">
      <xsl:text>dcterms:</xsl:text>
      <xsl:value-of select="$name"/>
    </xsl:attribute>
    <xsl:value-of select="normalize-space($n)"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <xsl:choose>
      <xsl:when test="$name = 'creator'">
        <dc:creator>
          <xsl:value-of select="normalize-space($n)"/>
        </dc:creator>
      </xsl:when>
      <xsl:when test="$name = 'contributor'">
        <dc:contributor>
          <xsl:value-of select="normalize-space($n)"/>
        </dc:contributor>
      </xsl:when>
      <xsl:otherwise>
        <xsl:element namespace="{$dc.namespace}" name="{$name}">
          <xsl:value-of select="normalize-space($n)"/>
        </xsl:element>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:if>

</xsl:template>

<xsl:template match="corpcredit" mode="opf.metadata">
  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:contributor</xsl:attribute>
    <xsl:value-of select="normalize-space(.)"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:contributor>
      <xsl:value-of select="normalize-space(.)"/>
    </dc:contributor>
  </xsl:if>
</xsl:template>

<xsl:template match="collab|othercredit" mode="opf.metadata">
  <xsl:variable name="content">
    <xsl:choose>
      <xsl:when test="collabname">
        <xsl:apply-templates select="collabname"/>
      </xsl:when>
      <xsl:when test="org/orgname">
        <xsl:apply-templates select="org/orgname"/>
      </xsl:when>
      <xsl:when test="orgname">
        <xsl:apply-templates select="orgname"/>
      </xsl:when>
      <xsl:when test="personname|firstname|surname|othername">
        <xsl:call-template name="person.name"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="."/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:contributor</xsl:attribute>
    <xsl:value-of select="normalize-space($content)"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:contributor>
      <xsl:value-of select="normalize-space($content)"/>
    </dc:contributor>
  </xsl:if>

</xsl:template>

<xsl:template match="date|pubdate" mode="opf.metadata">
  <xsl:variable name="date">
    <xsl:call-template name="format.meta.date">
      <xsl:with-param name="string" select="normalize-space(.)"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="string-length($date) != 0">
    <xsl:element name="meta" namespace="{$opf.namespace}">
      <xsl:attribute name="property">dcterms:date</xsl:attribute>
      <xsl:value-of select="$date"/>
    </xsl:element>
  
    <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
      <dc:date>
        <xsl:value-of select="$date"/>
      </dc:date>
    </xsl:if>
  </xsl:if>

</xsl:template>

<!-- EPUB3 meta date should be of the form:
  YYYY, YYYY-MM or YYYY-MM-DD -->
<xsl:template name="format.meta.date">
  <xsl:param name="string" select="''"/>
  <xsl:param name="node" select="."/>
  
  <!-- FIXME: this needs further work, so just check the
  string format and return the date string for now -->
  <xsl:variable name="normalized" 
                select="translate($string, '0123456789', '##########')"/>

  <xsl:variable name="date.ok">
    <xsl:choose>
      <xsl:when test="string-length($string) = 4 and
                      $normalized = '####'">1</xsl:when>
      <xsl:when test="string-length($string) = 7 and
                      $normalized = '####-##'">1</xsl:when>
      <xsl:when test="string-length($string) = 10 and
                      $normalized = '####-##-##'">1</xsl:when>
      <xsl:when test="string-length($string) = 10 and
                      $normalized = '####-##-##'">1</xsl:when>
      <xsl:otherwise>0</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="$date.ok = 0">
    <xsl:message>
      <xsl:text>WARNING: wrong metadata date format: '</xsl:text>
      <xsl:value-of select="$string"/>
      <xsl:text>' in element </xsl:text>
      <xsl:value-of select="local-name($node/..)"/>
      <xsl:text>/</xsl:text>
      <xsl:value-of select="local-name($node)"/>
      <xsl:text>. It must be in one of these forms: </xsl:text>
      <xsl:text>YYYY, YYYY-MM, or YYYY-MM-DD.</xsl:text>
    </xsl:message>
  </xsl:if>

  <!-- return the string anyway -->
  <xsl:value-of select="$string"/>

</xsl:template>


<!-- Space separate the compontents of the abstract (dropping the inline markup, sadly) -->
<xsl:template match="abstract" mode="opf.metadata">
  <xsl:variable name="content">
    <xsl:for-each select="formalpara|para|simpara|title">
      <xsl:choose>
        <xsl:when test="self::formalpara">
          <xsl:value-of select="normalize-space(string(title))"/>
          <xsl:text>: </xsl:text>
          <xsl:value-of select="normalize-space(string(para))"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="normalize-space(string(.))"/>
        </xsl:otherwise>  
      </xsl:choose>
      <xsl:if test="self::title">
        <xsl:text>:</xsl:text>
      </xsl:if>
      <xsl:if test="not(position() = last())">
        <xsl:text> </xsl:text>
      </xsl:if>
    </xsl:for-each>  
  </xsl:variable>

  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:description</xsl:attribute>
    <xsl:copy-of select="$content"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:description>
      <xsl:copy-of select="$content"/>
    </dc:description>
  </xsl:if>
</xsl:template>

<xsl:template match="subjectset" mode="opf.metadata">
  <xsl:apply-templates select="subject/subjectterm" mode="opf.metadata"/>
</xsl:template>

<xsl:template match="subjectterm" mode="opf.metadata">
  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:subject</xsl:attribute>
    <xsl:value-of select="normalize-space(string(.))"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:subject>
      <xsl:value-of select="normalize-space(string(.))"/>
    </dc:subject>
  </xsl:if>
</xsl:template>

<xsl:template match="keywordset" mode="opf.metadata">
  <xsl:apply-templates select="keyword" mode="opf.metadata"/>
</xsl:template>

<xsl:template match="keyword" mode="opf.metadata">
  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:subject</xsl:attribute>
    <xsl:value-of select="normalize-space(string(.))"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:subject>
      <xsl:value-of select="normalize-space(string(.))"/>
    </dc:subject>
  </xsl:if>
</xsl:template>

<xsl:template match="publisher" mode="opf.metadata">
  <xsl:apply-templates select="publishername" mode="opf.metadata"/>
</xsl:template>

<xsl:template match="publishername" mode="opf.metadata">
  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:publisher</xsl:attribute>
    <xsl:value-of select="normalize-space(string(.))"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:publisher>
      <xsl:value-of select="normalize-space(string(.))"/>
    </dc:publisher>
  </xsl:if>
</xsl:template>

<xsl:template match="bibliocoverage" mode="opf.metadata">
  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:coverage</xsl:attribute>
    <xsl:value-of select="normalize-space(string(.))"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:coverage>
      <xsl:value-of select="normalize-space(string(.))"/>
    </dc:coverage>
  </xsl:if>
</xsl:template>

<xsl:template match="bibliorelation" mode="opf.metadata">
  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:relation</xsl:attribute>
    <xsl:value-of select="normalize-space(string(.))"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:relation>
      <xsl:value-of select="normalize-space(string(.))"/>
    </dc:relation>
  </xsl:if>
</xsl:template>

<xsl:template match="bibliosource" mode="opf.metadata">
  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:source</xsl:attribute>
    <xsl:value-of select="normalize-space(string(.))"/>
  </xsl:element>

  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:source>
      <xsl:value-of select="normalize-space(string(.))"/>
    </dc:source>
  </xsl:if>
</xsl:template>

<xsl:template match="copyright" mode="opf.metadata">
  <xsl:variable name="copyright.date">
    <xsl:call-template name="copyright.years">
      <xsl:with-param name="years" select="year"/>
      <xsl:with-param name="print.ranges" select="$make.year.ranges"/>
      <xsl:with-param name="single.year.ranges" select="$make.single.year.ranges"/>
    </xsl:call-template>
  </xsl:variable>

  <!-- if no docbook date element, use copyright year for single date metadata -->
  <xsl:if test="not(../date)">
    <xsl:variable name="date.content">
      <xsl:call-template name="format.meta.date">
        <xsl:with-param name="string">
          <xsl:call-template name="copyright.years">
            <xsl:with-param name="years" select="year[last()]"/>
            <xsl:with-param name="print.ranges" select="0"/>
            <xsl:with-param name="single.year.ranges" select="0"/>
          </xsl:call-template>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:variable>
    <xsl:element name="meta" namespace="{$opf.namespace}">
      <xsl:attribute name="property">dcterms:date</xsl:attribute>
      <xsl:copy-of select="$date.content"/>
    </xsl:element>
    <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
      <dc:date>
        <xsl:copy-of select="$date.content"/>
      </dc:date>
    </xsl:if>
  </xsl:if>

  <xsl:variable name="rights.content">
    <xsl:call-template name="gentext">
      <xsl:with-param name="key" select="'Copyright'"/>
    </xsl:call-template>
    <xsl:call-template name="gentext.space"/>
    <xsl:text>&#x00A9;</xsl:text>
    <xsl:call-template name="gentext.space"/>
    <xsl:value-of select="$copyright.date"/>
    <xsl:call-template name="gentext.space"/>
    <xsl:apply-templates select="holder" mode="titlepage.mode"/>
  </xsl:variable>

  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:rights</xsl:attribute>
    <xsl:copy-of select="$rights.content"/>
  </xsl:element>
  <xsl:if test="$epub.include.optional.metadata.dc.elements != 0">
    <dc:rights>
      <xsl:copy-of select="$rights.content"/>
    </dc:rights>
  </xsl:if>

  <xsl:element name="meta" namespace="{$opf.namespace}">
    <xsl:attribute name="property">dcterms:rightsHolder</xsl:attribute>
    <xsl:apply-templates select="holder" mode="titlepage.mode"/>
  </xsl:element>
</xsl:template>

<xsl:template name="package.guide">

  <xsl:variable name="info" select="./*[contains(local-name(.), 'info')][1]"/>

  <xsl:variable name="toc.params">
    <xsl:call-template name="find.path.params">
      <xsl:with-param name="node" select="."/>
      <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="contains($toc.params, 'toc') or 
                $info/cover or 
                $info//mediaobject[@role='cover' or ancestor::cover]"> 
    <xsl:element namespace="{$opf.namespace}" name="guide">
      <xsl:if test="$info/cover or 
                    $info//mediaobject[@role='cover' or ancestor::cover]"> 
        <xsl:element namespace="{$opf.namespace}" name="reference">
          <xsl:attribute name="href">
            <xsl:value-of select="$epub.cover.filename" />
          </xsl:attribute>
          <xsl:attribute name="type">cover</xsl:attribute>
          <xsl:attribute name="title">Cover</xsl:attribute>
        </xsl:element>
      </xsl:if>  

      <xsl:if test="contains($toc.params, 'toc')">
        <xsl:element namespace="{$opf.namespace}" name="reference">
          <xsl:attribute name="href">
            <xsl:call-template name="toc-href">
              <xsl:with-param name="node" select="."/>
            </xsl:call-template>
          </xsl:attribute>
          <xsl:attribute name="type">toc</xsl:attribute>
          <xsl:attribute name="title">Table of Contents</xsl:attribute>
        </xsl:element>
      </xsl:if>  
    </xsl:element>  
  </xsl:if>  
</xsl:template>


<xsl:template name="package-identifier">  

  <xsl:variable name="info" select="./*[contains(local-name(.), 'info')][1]"/>

  <xsl:choose>
    <xsl:when test="$info/biblioid">
      <xsl:if test="$info/biblioid[1][@class = 'doi' or 
                                      @class = 'isbn' or
                                      @class = 'isrn' or
                                      @class = 'istc' or
                                      @class = 'issn']">
        <xsl:text>urn:</xsl:text>
        <xsl:value-of select="$info/biblioid[1]/@class"/>
        <xsl:text>:</xsl:text>
      </xsl:if>
      <xsl:value-of select="normalize-space($info/biblioid[1])"/>
    </xsl:when>
    <xsl:when test="$info/isbn">
      <xsl:text>urn:isbn:</xsl:text>
      <xsl:value-of select="$info/isbn[1]"/>
    </xsl:when>
    <xsl:when test="$info/issn">
      <xsl:text>urn:issn:</xsl:text>
      <xsl:value-of select="$info/issn[1]"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$info/invpartnumber">
          <xsl:value-of select="$info/invpartnumber[1]"/>
        </xsl:when>
        <xsl:when test="$info/issuenum">
          <xsl:value-of select="$info/issuenum[1]"/>
        </xsl:when>
        <xsl:when test="$info/productnumber">
          <xsl:value-of select="$info/productnumber[1]"/>
        </xsl:when>
        <xsl:when test="$info/seriesvolnums">
          <xsl:value-of select="$info/seriesvolnums[1]"/>
        </xsl:when>
        <xsl:when test="$info/volumenum">
          <xsl:value-of select="$info/volumenum[1]"/>
        </xsl:when>
        <!-- Deprecated -->
        <xsl:when test="$info/pubsnumber">
          <xsl:value-of select="$info/pubsnumber[1]"/>
        </xsl:when>
      </xsl:choose>  
      <xsl:text>_</xsl:text>
      <xsl:choose>
        <xsl:when test="@id">
          <xsl:value-of select="@id"/>
        </xsl:when>
        <xsl:when test="@xml:id">
          <xsl:value-of select="@xml:id"/>
        </xsl:when>
        <xsl:otherwise>
          <!-- TODO: Do UUIDs here -->
          <xsl:value-of select="generate-id(.)"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- EPUB3: add epub:type attribute where appropriate -->
<xsl:template match="*" mode="common.html.attributes">
  <xsl:param name="class" select="local-name(.)"/>
  <xsl:param name="inherit" select="0"/>
  <xsl:call-template name="generate.html.lang"/>
  <xsl:call-template name="dir">
    <xsl:with-param name="inherit" select="$inherit"/>
  </xsl:call-template>
  <xsl:apply-templates select="." mode="class.attribute">
    <xsl:with-param name="class" select="$class"/>
  </xsl:apply-templates>
  <xsl:call-template name="generate.html.title"/>
  <xsl:apply-templates select="." mode="epub.type"/>
</xsl:template>

<xsl:template match="*" mode="epub.type" priority="-1"/>

<xsl:template match="chapter
                    |appendix
                    |epigraph
                    |warning
                    |preface
                    |index
                    |colophon
                    |glossary
                    |biblioentry
                    |bibliography
                    |dedication
                    |sidebar
                    |footnote
                    |glossterm
                    |glossdef
                    |bridgehead
                    |part" mode="epub.type">
  <xsl:variable name="type" select="local-name()"/>

  <xsl:if test="$epub.output.epub.types != 0">
    <xsl:attribute name="epub:type">
      <xsl:value-of select="$type"/>
    </xsl:attribute>
  </xsl:if>
</xsl:template>

<xsl:template match="section[parent::chapter] | sect1" mode="epub.type">
  <xsl:if test="$epub.output.epub.types != 0">
    <xsl:attribute name="epub:type">subchapter</xsl:attribute>
  </xsl:if>
</xsl:template>

<xsl:template match="section[not(parent::chapter)] |
                     sect2 |
                     sect3 |
                     sect4 |
                     sect5 |
                     sect6" mode="epub.type">
  <xsl:if test="$epub.output.epub.types != 0">
    <xsl:attribute name="epub:type">division</xsl:attribute>
  </xsl:if>
</xsl:template>

<xsl:template match="note|tip|caution|important" mode="epub.type">
  <xsl:if test="$epub.output.epub.types != 0">
    <xsl:attribute name="epub:type">notice</xsl:attribute>
  </xsl:if>
</xsl:template>

<xsl:template match="orderedlist|itemizedlist|variablelist|simplelist" mode="epub.type">
  <xsl:if test="$epub.output.epub.types != 0">
    <xsl:attribute name="epub:type">list</xsl:attribute>
  </xsl:if>
</xsl:template>

<xsl:template match="listitem" mode="epub.type">
  <xsl:if test="$epub.output.epub.types != 0">
    <xsl:attribute name="epub:type">list-item</xsl:attribute>
  </xsl:if>
</xsl:template>

<!-- EPUB3: to add attributes to root output element -->
<xsl:template name="root.attributes">
  <!-- collect and output all namespace declarations -->
  <xsl:call-template name="all.namespaces"/>
</xsl:template>

<xsl:template name="all.namespaces">
  <!-- add the epub3 namespaces to the top output element -->
  <xsl:variable name="temp">
    <epub:foo/>
    <m:foo/>
    <pls:foo/>
    <ssml:foo/>
    <svg:foo/>
  </xsl:variable>

  <xsl:variable name="nodes" select="exsl:node-set($temp)"/>
  <xsl:for-each select="$nodes//*/namespace::*">
    <xsl:copy-of select="."/>
  </xsl:for-each>

</xsl:template>

<xsl:template name="add.package.namespaces">
  <!-- add the epub3 package namespaces to the top output element -->
  <xsl:variable name="temp">
    <dc:foo/>
    <dcterms:foo/>
  </xsl:variable>

  <xsl:variable name="nodes" select="exsl:node-set($temp)"/>
  <xsl:for-each select="$nodes//*[local-name(.) ='foo']/namespace::*">
    <xsl:if test="contains(., 'purl.org')">
      <xsl:copy-of select="."/>
    </xsl:if>
  </xsl:for-each>

</xsl:template>


<xsl:template name="footnotes.attributes">
  <xsl:if test="$epub.output.epub.types != 0">
    <xsl:attribute name="epub:type">footnotes</xsl:attribute>
  </xsl:if>
</xsl:template>

<xsl:template name="package.manifest">
  <xsl:element name="manifest" namespace="{$opf.namespace}">
    <xsl:if test="$epub.include.ncx != 0">
      <xsl:call-template name="manifest.ncx"/>
    </xsl:if>
    <xsl:call-template name="manifest.fonts"/>
    <xsl:call-template name="manifest.toc"/>
    <xsl:call-template name="manifest.css"/>
    <xsl:call-template name="manifest.cover"/>
    <xsl:call-template name="manifest.other.items"/>
    <xsl:call-template name="manifest.content.items"/>
    <xsl:call-template name="user.manifest.items"/>
  </xsl:element>
</xsl:template>

<xsl:template name="user.manifest.items"/>

<xsl:template name="manifest.css">
  <xsl:if test="$html.stylesheet != ''">
    <xsl:call-template name="css.item">
      <xsl:with-param name="stylesheets" select="$html.stylesheet"/>
    </xsl:call-template>
  </xsl:if>
  <xsl:if test="string-length($docbook.css.source) != 0">
    <xsl:variable name="dfilename">
      <xsl:call-template name="css.output.filename">
        <xsl:with-param name="src" select="$docbook.css.source"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:element namespace="{$opf.namespace}" name="item">
      <xsl:attribute name="media-type">text/css</xsl:attribute>
      <xsl:attribute name="id">docbook-css</xsl:attribute>
      <xsl:attribute name="href">
        <xsl:value-of select="$dfilename"/>
      </xsl:attribute>
    </xsl:element>
  </xsl:if>
  <xsl:if test="string-length($custom.css.source) != 0">
    <xsl:variable name="cfilename">
      <xsl:call-template name="css.output.filename">
        <xsl:with-param name="src" select="$custom.css.source"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:element namespace="{$opf.namespace}" name="item">
      <xsl:attribute name="media-type">text/css</xsl:attribute>
      <xsl:attribute name="id">custom-css</xsl:attribute>
      <xsl:attribute name="href">
        <xsl:value-of select="$cfilename"/>
      </xsl:attribute>
    </xsl:element>
  </xsl:if>
</xsl:template>

<xsl:template name="css.item">
  <xsl:param name="stylesheets" select="''"/>
  <xsl:param name="count" select="1"/>

  <xsl:choose>
    <xsl:when test="contains($stylesheets, ' ')">
      <xsl:variable name="css.filename" select="substring-before($stylesheets, ' ')"/>
      <xsl:if test="$css.filename != ''">
        <xsl:element namespace="{$opf.namespace}" name="item">
          <xsl:attribute name="media-type">text/css</xsl:attribute>
          <xsl:attribute name="id">
            <xsl:text>html-css</xsl:text>
            <xsl:if test="$count &gt; 1">
              <xsl:value-of select="$count"/>
            </xsl:if>
          </xsl:attribute>
          <xsl:attribute name="href">
            <xsl:value-of select="$css.filename"/>
          </xsl:attribute>
        </xsl:element>
      </xsl:if>

      <xsl:call-template name="css.item">
        <xsl:with-param name="stylesheets" select="substring-after($stylesheets, ' ')"/>
        <xsl:with-param name="count" select="$count + 1"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:if test="$stylesheets != ''">
        <xsl:element namespace="{$opf.namespace}" name="item">
          <xsl:attribute name="media-type">text/css</xsl:attribute>
          <xsl:attribute name="id">
            <xsl:text>html-css</xsl:text>
            <xsl:if test="$count &gt; 1">
              <xsl:value-of select="$count"/>
            </xsl:if>
          </xsl:attribute>
          <xsl:attribute name="href">
            <xsl:value-of select="$stylesheets"/>
          </xsl:attribute>
        </xsl:element>
      </xsl:if>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="manifest.ncx">
  <xsl:element name="item" namespace="{$opf.namespace}">
    <xsl:attribute name="id">
      <xsl:value-of select="$epub.ncx.manifest.id"/>
    </xsl:attribute>
    <xsl:attribute name="href">
      <xsl:value-of select="$epub.ncx.filename"/>
    </xsl:attribute>
    <xsl:attribute name="media-type">
      <xsl:value-of select="$epub.ncx.mediatype"/>
    </xsl:attribute>
  </xsl:element>
</xsl:template>

<xsl:template name="manifest.fonts"/>

<!--Misc items in the manifest based on content -->
<xsl:template name="manifest.other.items">
</xsl:template>


<xsl:template name="manifest.cover">
  <xsl:variable name="info" select="./*[contains(local-name(.), 'info')][1]"/>
  <xsl:variable name="cover.image" 
                select="$info//mediaobject[@role='cover' or ancestor::cover]"/>

  <xsl:if test="$cover.image">

    <!-- generate the manifest link to that page -->
    <xsl:variable name="olist" select="$cover.image/imageobject|$cover.image/imageobjectco
                       |$cover.image/videoobject|$cover.image/audioobject
                       |$cover.image/textobject"/>
  
    <xsl:variable name="object.index">
      <xsl:call-template name="select.mediaobject.index">
        <xsl:with-param name="olist" select="$olist"/>
        <xsl:with-param name="count" select="1"/>
      </xsl:call-template>
    </xsl:variable>
  
    <xsl:variable name="object" select="$olist[position() = $object.index]"/>

    <xsl:variable name="output_filename">
      <xsl:call-template name="mediaobject.filename">
        <xsl:with-param name="object" select="$object"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="image.filename">
       <xsl:if test="$img.src.path != '' and
                       not(starts-with($output_filename, '/')) and
                       not(contains($output_filename, '://'))">
         <xsl:value-of select="$img.src.path"/>
       </xsl:if>
       <xsl:value-of select="$output_filename"/>
    </xsl:variable>
    <xsl:variable name="image.extension">
      <xsl:call-template name="filename-extension">
        <xsl:with-param name="filename" select="$image.filename"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="image.type">
      <xsl:call-template name="graphic.format.content-type">
        <xsl:with-param name="format" select="translate($image.extension, 
                   &lowercase;, &uppercase;)"/>
      </xsl:call-template>
    </xsl:variable>

    <!-- Reference to cover html file -->
    <xsl:element namespace="{$opf.namespace}" name="item">
      <xsl:attribute name="id">
        <xsl:value-of select="$epub.cover.filename.id"/>
      </xsl:attribute>
      <xsl:attribute name="href">
        <xsl:value-of select="$epub.cover.filename"/>
      </xsl:attribute>
      <xsl:attribute name="media-type">
        <xsl:value-of select="$epub.xhtml.mediatype"/>
      </xsl:attribute>
    </xsl:element>

    <!-- special item with property="cover-image" -->
    <xsl:element namespace="{$opf.namespace}" name="item">
      <xsl:attribute name="id">
        <xsl:value-of select="$epub.cover.image.id"/>
      </xsl:attribute>
      <xsl:attribute name="properties">cover-image</xsl:attribute>
      <xsl:attribute name="href">
        <xsl:value-of select="$image.filename"/>
      </xsl:attribute>
      <xsl:if test="$image.type">
        <xsl:attribute name="media-type">
          <xsl:value-of select="$image.type"/>
        </xsl:attribute>
      </xsl:if>
    </xsl:element>

    <!-- And generate the cover html file -->
    <xsl:apply-templates select="$cover.image"/>

  </xsl:if>
</xsl:template>

<xsl:template name="manifest.toc">
  <xsl:variable name="toc.params">
    <xsl:call-template name="find.path.params">
      <xsl:with-param name="node" select="."/>
      <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="contains($toc.params, 'toc')">
    <xsl:element namespace="{$opf.namespace}" name="item">
      <xsl:attribute name="id">
        <xsl:value-of select="$epub.html.toc.id"/>
      </xsl:attribute>
      <xsl:attribute name="properties">nav</xsl:attribute>
      <xsl:attribute name="media-type">
        <xsl:value-of select="$epub.xhtml.mediatype"/>
      </xsl:attribute>
      <xsl:attribute name="href">
        <xsl:call-template name="toc-href">
          <xsl:with-param name="node" select="."/>
        </xsl:call-template>
      </xsl:attribute>
    </xsl:element>
  </xsl:if>  
</xsl:template>

<xsl:template name="toc-href">
  <xsl:param name="node" select="."/>
  <!-- FIXME -->
  <xsl:apply-templates select="$node" mode="recursive-chunk-filename">
    <xsl:with-param name="recursive" select="true()"/>
  </xsl:apply-templates>
  <xsl:text>-toc</xsl:text>
  <xsl:value-of select="$html.ext"/>
</xsl:template>

<xsl:template match="*" mode="recursive-chunk-filename">
  <!-- placeholder for real template in chunking stylesheet -->
  <xsl:value-of select="concat(local-name(.), '-', generate-id(.))"/>
</xsl:template>

<xsl:template name="manifest.content.items">
  <xsl:apply-templates select="." mode="package.manifest"/>
  <xsl:call-template name="manifest.images"/>
</xsl:template>

<xsl:template match="text()" mode="package.manifest"/>

<xsl:template match="mediaobject|mediaobjectco|inlinemediaobject" priority="1"
              mode="package.manifest">
  <!-- These are handled out of line so a unique list is created
       to remove duplicate references -->
</xsl:template>

<xsl:template match="*" mode="package.manifest">

  <xsl:variable name="is.chunk">
    <xsl:call-template name="chunk">
      <xsl:with-param name="node" select="."/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$is.chunk != 0">
    <xsl:variable name="href">
      <xsl:call-template name="href.target.with.base.dir">
        <xsl:with-param name="context" select="/"/>
      </xsl:call-template>
    </xsl:variable>
  
    <xsl:variable name="id" select="concat($epub.package.id.prefix, generate-id())"/>

    <xsl:variable name="properties.set">
      <xsl:call-template name="svg.property"/>
      <xsl:text> </xsl:text>
      <xsl:call-template name="mathml.property"/>
    </xsl:variable>

    <xsl:variable name="properties" select="normalize-space($properties.set)"/>

    <xsl:element namespace="{$opf.namespace}" name="item">
      <xsl:attribute name="id">
        <xsl:value-of select="$id"/>
      </xsl:attribute>
      <xsl:attribute name="href">
        <xsl:value-of select="$href"/>
      </xsl:attribute>
      <xsl:attribute name="media-type">application/xhtml+xml</xsl:attribute>
      <xsl:if test="string-length($properties) != 0">
        <xsl:attribute name="properties">
          <xsl:value-of select="$properties"/>
        </xsl:attribute>
      </xsl:if>
    </xsl:element>
  </xsl:if>  
  <xsl:apply-templates mode="package.manifest"/>

</xsl:template>

<xsl:template name="svg.property">
  <xsl:param name="this.chunk" select="."/>

  <xsl:variable name="genid" select="generate-id($this.chunk)"/>

  <!-- get the chunkfast div element for this chunk -->
  <xsl:variable name="div" select="$chunkset[@id=$genid or @xml:id=$genid]"/>

  <!-- get the chunkfast div element the next chunk -->
  <xsl:variable name="nextdiv"
                select="($div/following-sibling::cf:div|
                         $div/following::cf:div|
                         $div/cf:div)[1]"/>

  <!-- get the element corresponding to the next chunk -->
  <xsl:variable name="next.chunk" select="key('genid', ($nextdiv/@id|$nextdiv/@xml:id)[1])"/>

  <xsl:choose>
    <xsl:when test="$next.chunk">
      <xsl:variable name="this.imagedata"
                    select="$this.chunk//mediaobject"/>
      <xsl:variable name="before.next"
                    select="$next.chunk/preceding::mediaobject"/>
      
      <!-- select for an SVG imagedata in the intersection of them -->
      <xsl:variable name="mediaobject.set"
          select="$this.imagedata[count(.|$before.next) = count($before.next)]"/>
      <xsl:variable name="svg.imagedata">
        <xsl:for-each select="$mediaobject.set">
          <xsl:variable name="olist" select="imageobject[not(@role = 'poster')] |
                                             imageobjectco"/>
          <xsl:variable name="mediaobject.index">
            <xsl:call-template name="select.mediaobject.index">
              <xsl:with-param name="olist" select="$olist"/>
            </xsl:call-template>
          </xsl:variable>
          <xsl:variable name="object" select="$olist[position() = $mediaobject.index]"/>
          <xsl:if test="$object/imagedata[contains(
                      substring(@fileref, string-length(@fileref)-3,4), '.svg')]">
            <xsl:text>svg</xsl:text>
          </xsl:if>
        </xsl:for-each>
      </xsl:variable>
    
      <xsl:if test="contains($svg.imagedata, 'svg')">
        <xsl:text>svg</xsl:text>
     </xsl:if>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="mediaobject.set"
                    select="$this.chunk//mediaobject"/>
      <xsl:variable name="svg.imagedata">
        <xsl:for-each select="$mediaobject.set">
          <xsl:variable name="olist" select="imageobject[not(@role = 'poster')] |
                                             imageobjectco"/>
          <xsl:variable name="mediaobject.index">
            <xsl:call-template name="select.mediaobject.index">
              <xsl:with-param name="olist" select="$olist"/>
            </xsl:call-template>
          </xsl:variable>
          <xsl:variable name="object" select="$olist[position() = $mediaobject.index]"/>
          <xsl:if test="$object/imagedata[contains(
                      substring(@fileref, string-length(@fileref)-3,4), '.svg')]">
            <xsl:text>svg</xsl:text>
          </xsl:if>
        </xsl:for-each>
      </xsl:variable>
    
      <xsl:if test="contains($svg.imagedata, 'svg')">
        <xsl:text>svg</xsl:text>
     </xsl:if>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<xsl:template name="mathml.property">
  <xsl:param name="this.chunk" select="."/>

  <xsl:variable name="genid" select="generate-id($this.chunk)"/>

  <!-- get the chunkfast div element for this chunk -->
  <xsl:variable name="div" select="$chunkset[@id=$genid or @xml:id=$genid]"/>

  <!-- get the chunkfast div element the next chunk -->
  <xsl:variable name="nextdiv"
                select="($div/following-sibling::cf:div|
                         $div/following::cf:div|
                         $div/cf:div)[1]"/>

  <!-- get the element corresponding to the next chunk -->
  <xsl:variable name="next.chunk" select="key('genid', ($nextdiv/@id|$nextdiv/@xml:id)[1])"/>

  <xsl:variable name="this.math"
                select="$this.chunk//m:*"/>
  <xsl:variable name="before.next"
                select="$next.chunk/preceding::m:*"/>
  
  <!-- select for an SVG imagedata in the intersection of them -->
  <xsl:variable name="intersection"
      select="$this.math[count(.|$before.next) = count($before.next)]"/>

  <xsl:if test="count($intersection) != 0">
    <xsl:text>mathml</xsl:text>
 </xsl:if>
</xsl:template>

<xsl:template name="manifest.image.item">
</xsl:template>

<!-- Need a uniqued list of images -->
<xsl:template name="manifest.images">
  <xsl:message>Generating image list ...</xsl:message>
  <xsl:variable name="imagelist">
    <xsl:choose>
      <xsl:when test="$rootid != ''">
        <xsl:apply-templates select="key('id', $rootid)" mode="enumerate-images"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="/" mode="enumerate-images"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$exsl.node.set.available != 0 and 
                    function-available('set:distinct')">
      <xsl:for-each select="set:distinct(exsl:node-set($imagelist)/*)">
        <xsl:if test="string-length(tmp-href) != 0">
          <!-- convert the child elements to attributes -->
          <xsl:element name="item" namespace="{$opf.namespace}">
            <xsl:attribute name="id">
              <xsl:value-of select="generate-id()"/>
            </xsl:attribute>
            <xsl:attribute name="href">
              <xsl:value-of select="tmp-href"/>
            </xsl:attribute>
            <xsl:attribute name="media-type">
              <xsl:value-of select="media-type"/>
            </xsl:attribute>
          </xsl:element>
        </xsl:if>
      </xsl:for-each>
    </xsl:when>
    <xsl:when test="$exsl.node.set.available != 0">
      <xsl:for-each select="exsl:node-set($imagelist)/*">
        <xsl:element name="item" namespace="{$opf.namespace}">
          <xsl:attribute name="id">
            <xsl:value-of select="generate-id()"/>
          </xsl:attribute>
          <xsl:attribute name="href">
            <xsl:value-of select="tmp-href"/>
          </xsl:attribute>
          <xsl:attribute name="media-type">
            <xsl:value-of select="media-type"/>
          </xsl:attribute>
        </xsl:element>
      </xsl:for-each>
    </xsl:when>
    <xsl:otherwise>
      <xsl:message terminate="yes">
        <xsl:text>ERROR: cannot process images list without </xsl:text>
        <xsl:text>exsl:node-set() function</xsl:text>
      </xsl:message>
      <!-- cannot do this without node-set() -->
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<xsl:template match="*" mode="enumerate-images">
  <xsl:apply-templates mode="enumerate-images"/>
</xsl:template>

<xsl:template match="text()" mode="enumerate-images"/>

<xsl:template match="graphic|inlinegraphic[@format!='linespecific']" 
              mode="enumerate-images">
</xsl:template>

<!-- cover image handled separates to give it an extra property attribute -->
<xsl:template match="mediaobject[@role='cover' or ancestor::cover]"
              mode="enumerate-images"/>

<xsl:template match="mediaobject|inlinemediaobject" mode="enumerate-images">

  <xsl:variable name="olist" 
                select="imageobject[not(@role = 'poster')] 
                       |imageobjectco
                       |videoobject
                       |audioobject
                       |textobject"/>
 
  <xsl:variable name="object.index">
    <xsl:call-template name="select.mediaobject.index">
      <xsl:with-param name="olist" select="$olist"/>
      <xsl:with-param name="count" select="1"/>
    </xsl:call-template>
  </xsl:variable>
  
  <xsl:variable name="object" select="$olist[position() = $object.index]"/>

  <xsl:apply-templates select="$object" mode="enumerate-images"/>

  <!-- also include a poster image if present -->
  <xsl:apply-templates select="imageobject[@role = 'poster']" mode="enumerate-images"/>

</xsl:template>

<xsl:template match="imageobject|videoobject|audioobject" mode="enumerate-images">
  <xsl:param name="object" select="."/>

  <xsl:if test="$object">
    <xsl:variable name="output_filename">
      <xsl:call-template name="mediaobject.filename">
        <xsl:with-param name="object" select="$object"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="image.filename">
       <xsl:if test="$img.src.path != '' and
                       not(starts-with($output_filename, '/')) and
                       not(contains($output_filename, '://'))">
         <xsl:value-of select="$img.src.path"/>
       </xsl:if>
       <xsl:value-of select="$output_filename"/>
    </xsl:variable>

    <xsl:variable name="image.extension">
      <xsl:call-template name="filename-extension">
        <xsl:with-param name="filename" select="$image.filename"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="image.type">
      <xsl:call-template name="graphic.format.content-type">
        <xsl:with-param name="format" select="translate($image.extension, 
                   &lowercase;, &uppercase;)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:element name="tmp-filename" namespace="">
      <xsl:element name="tmp-href" namespace="">
        <xsl:value-of select="$image.filename"/>
      </xsl:element>
      <xsl:element name="media-type" namespace="">
        <xsl:value-of select="$image.type"/>
      </xsl:element>
    </xsl:element>

  </xsl:if>
</xsl:template>
 
<!-- Add in the generated images -->
<xsl:template match="note|caution|warning|important|tip" mode="enumerate-images">
  <xsl:if test="$admon.graphics != 0">
    <xsl:variable name="image.filename">
      <xsl:call-template name="admon.graphic"/>
    </xsl:variable>

    <xsl:variable name="image.type">
      <xsl:call-template name="graphic.format.content-type">
        <xsl:with-param name="format" select="translate(
               substring-after($admon.graphics.extension,'.'), 
                   &lowercase;, &uppercase;)"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:element name="tmp-filename" namespace="">
      <xsl:element name="tmp-href" namespace="">
        <xsl:value-of select="$image.filename"/>
      </xsl:element>
      <xsl:element name="media-type" namespace="">
        <xsl:value-of select="$image.type"/>
      </xsl:element>
    </xsl:element>

  </xsl:if>
</xsl:template>

<xsl:template match="callout" mode="enumerate-images">
  <!-- process arearefs to get name of callout bug image files -->
  <xsl:if test="$callout.graphics != 0">
    <xsl:variable name="arearefs">
      <xsl:call-template name="callout.arearefs">
        <xsl:with-param name="arearefs" select="@arearefs"/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:variable name="nodes" select="exsl:node-set($arearefs)"/>

    <xsl:for-each select="$nodes//*[@src]">
      <xsl:variable name="image.filename" select="@src"/>

      <xsl:variable name="image.type">
        <xsl:call-template name="graphic.format.content-type">
          <xsl:with-param name="format" select="translate(
                 substring-after($callout.graphics.extension,'.'), 
                     &lowercase;, &uppercase;)"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:element name="tmp-filename" namespace="">
        <xsl:element name="tmp-href" namespace="">
          <xsl:value-of select="$image.filename"/>
        </xsl:element>
        <xsl:element name="media-type" namespace="">
          <xsl:value-of select="$image.type"/>
        </xsl:element>
      </xsl:element>
    </xsl:for-each>

  </xsl:if>
</xsl:template>

<xsl:template match="co" mode="enumerate-images">
  <!-- process co to get name of callout bug image file -->
  <xsl:if test="$callout.graphics != 0">
    <xsl:variable name="result">
      <xsl:apply-templates select="." mode="callout-bug"/>
    </xsl:variable>

    <xsl:variable name="nodes" select="exsl:node-set($result)"/>

    <xsl:for-each select="$nodes//*[@src]">
      <xsl:variable name="image.filename" select="@src"/>

      <xsl:variable name="image.type">
        <xsl:call-template name="graphic.format.content-type">
          <xsl:with-param name="format" select="translate(
                 substring-after($callout.graphics.extension,'.'), 
                     &lowercase;, &uppercase;)"/>
        </xsl:call-template>
      </xsl:variable>

      <xsl:element name="tmp-filename" namespace="">
        <xsl:element name="tmp-href" namespace="">
          <xsl:value-of select="$image.filename"/>
        </xsl:element>
        <xsl:element name="media-type" namespace="">
          <xsl:value-of select="$image.type"/>
        </xsl:element>
      </xsl:element>
    </xsl:for-each>

  </xsl:if>

</xsl:template>

<!-- ======================================================== -->
<!-- NCX templates are for backwards compatibility with EPUB2 -->
<!-- ======================================================== -->

<xsl:template name="ncx">
  <xsl:message>Generating NCX file ...</xsl:message>
  <xsl:call-template name="write.chunk">
    <xsl:with-param name="filename">
      <xsl:value-of select="$epub.ncx.pathname" />
    </xsl:with-param>
    <xsl:with-param name="method" select="'xml'" />
    <xsl:with-param name="encoding" select="'utf-8'" />
    <xsl:with-param name="indent" select="'no'" />
    <xsl:with-param name="quiet" select="$chunk.quietly" />
    <xsl:with-param name="doctype-public" select="''"/> <!-- intentionally blank -->
    <xsl:with-param name="doctype-system" select="''"/> <!-- intentionally blank -->
    <xsl:with-param name="content">
      <xsl:element name="ncx" namespace="{$ncx.namespace}">
        <xsl:attribute name="version">2005-1</xsl:attribute>

          <!-- Via Martin Goerner: On covers: the IDPF2.0 standard unfortunately does not have a provision for
          covers. We had to add one and we did so in conjunction with the IDPF and
          various publishers. The tag chosen to define the covers is:
          <meta name="cover" content="-reference to a manifest item-">
          Then, we also added a bit of logic to get rid cleanly of the HTML cover
          people usually add because the logical cover is not specced by the IDPF. So,
          if the HTML cover item is marked linear="no" AND there is a guide item of
          type="cover" pointing to it AND there is a logical cover specified in a
          <meta name="cover"> tag, THEN, the HTML cover is discarded. -->
        <xsl:element name="head" namespace="{$ncx.namespace}">
          <xsl:if test="/*/*[cover or contains(name(.), 'info')]//mediaobject[@role='cover' or ancestor::cover]"> 
            <xsl:element name="meta" namespace="{$ncx.namespace}">
              <xsl:attribute name="name">cover</xsl:attribute>
              <xsl:attribute name="content">
                <xsl:value-of select="$epub.cover.filename.id"/>
              </xsl:attribute>
            </xsl:element>
          </xsl:if>
          <xsl:element name="meta" namespace="{$ncx.namespace}">
            <xsl:attribute name="name">dtb:uid</xsl:attribute>
            <xsl:attribute name="content"><xsl:call-template name="package-identifier"/></xsl:attribute>
          </xsl:element>
        </xsl:element>

        <xsl:choose>
          <xsl:when test="$rootid != ''">
            <xsl:variable name="title">
              <xsl:if test="$epub.autolabel != 0">
                <xsl:variable name="label.markup">
                  <xsl:apply-templates select="key('id',$rootid)" mode="label.markup" />
                </xsl:variable>
                <xsl:if test="normalize-space($label.markup)">
                  <xsl:value-of select="concat($label.markup,$autotoc.label.separator)" />
                </xsl:if>
              </xsl:if>
              <xsl:apply-templates select="key('id',$rootid)" mode="title.markup" />
            </xsl:variable>
            <xsl:variable name="href">
              <xsl:call-template name="href.target.with.base.dir">
                <xsl:with-param name="object" select="key('id',$rootid)" />
              </xsl:call-template>
            </xsl:variable>
            <xsl:element name="docTitle" namespace="{$ncx.namespace}">
              <xsl:element name="text" namespace="{$ncx.namespace}"><xsl:value-of select="normalize-space($title)" />  </xsl:element>
            </xsl:element>
            <xsl:element name="navMap" namespace="{$ncx.namespace}">
              <xsl:apply-templates select="key('id',$rootid)/*" mode="ncx" />
            </xsl:element>
          </xsl:when>
          <xsl:otherwise>
            <xsl:variable name="title">
              <xsl:if test="$epub.autolabel != 0">
                <xsl:variable name="label.markup">
                  <xsl:apply-templates select="/*" mode="label.markup" />
                </xsl:variable>
                <xsl:if test="normalize-space($label.markup)">
                  <xsl:value-of select="concat($label.markup,$autotoc.label.separator)" />
                </xsl:if>
              </xsl:if>
              <xsl:apply-templates select="/*" mode="title.markup" />
            </xsl:variable>
            <xsl:variable name="href">
              <xsl:call-template name="href.target.with.base.dir">
                <xsl:with-param name="object" select="/" />
              </xsl:call-template>
            </xsl:variable>
            <xsl:element name="docTitle" namespace="{$ncx.namespace}">
              <xsl:element name="text" namespace="{$ncx.namespace}">
                <xsl:value-of select="normalize-space($title)" />
              </xsl:element>
            </xsl:element>
            <xsl:element name="navMap" namespace="{$ncx.namespace}">
              <xsl:choose>
                <xsl:when test="$root.is.a.chunk != '0'">
                  <xsl:apply-templates select="/*" mode="ncx" />
                  <xsl:apply-templates select="/*/*" mode="ncx" />
                </xsl:when>
                <xsl:otherwise>
                  <xsl:apply-templates select="/*/*" mode="ncx" />
                </xsl:otherwise>
              </xsl:choose>
            </xsl:element>
          </xsl:otherwise>

        </xsl:choose>
      </xsl:element>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template match="book|
                     article|
                     topic|
                     part|
                     reference|
                     preface|
                     chapter|
                     bibliography|
                     appendix|
                     glossary|
                     section|
                     sect1|
                     sect2|
                     sect3|
                     sect4|
                     sect5|
                     refentry|
                     colophon|
                     bibliodiv[title]|
                     setindex|
                     index"
              mode="ncx">
  <xsl:variable name="depth" select="count(ancestor::*)"/>
  <xsl:variable name="title">
    <xsl:if test="$epub.autolabel != 0">
      <xsl:variable name="label.markup">
        <xsl:apply-templates select="." mode="label.markup" />
      </xsl:variable>
      <xsl:if test="normalize-space($label.markup)">
        <xsl:value-of
          select="concat($label.markup,$autotoc.label.separator)" />
      </xsl:if>
    </xsl:if>
    <xsl:apply-templates select="." mode="title.markup" />
  </xsl:variable>

  <xsl:variable name="href">
    <xsl:call-template name="href.target.with.base.dir">
      <xsl:with-param name="context" select="/" />
      <!-- Generate links relative to the location of root file/toc.xml file -->
    </xsl:call-template>
  </xsl:variable>

  <xsl:variable name="id">
    <xsl:value-of select="generate-id(.)"/>
  </xsl:variable>
  <xsl:variable name="order">
    <xsl:value-of select="$depth +
                                count(preceding::part|
                                preceding::reference|
                                preceding::book[parent::set]|
                                preceding::preface|
                                preceding::chapter|
                                preceding::bibliography|
                                preceding::appendix|
                                preceding::article|
                                preceding::topic|
                                preceding::glossary|
                                preceding::section[not(parent::partintro)]|
                                preceding::sect1[not(parent::partintro)]|
                                preceding::sect2[not(ancestor::partintro)]|
                                preceding::sect3[not(ancestor::partintro)]|
                                preceding::sect4[not(ancestor::partintro)]|
                                preceding::sect5[not(ancestor::partintro)]|
                                preceding::refentry|
                                preceding::colophon|
                                preceding::bibliodiv[title]|
                                preceding::index)"/>
  </xsl:variable>

  <xsl:element name="navPoint" namespace="{$ncx.namespace}">
    <xsl:attribute name="id">
      <xsl:value-of select="$id"/>
    </xsl:attribute>

    <xsl:attribute name="playOrder">
      <xsl:choose>
        <xsl:when test="/*[self::set]">
          <xsl:value-of select="$order"/>
        </xsl:when>
        <xsl:when test="$root.is.a.chunk != '0'">
          <xsl:value-of select="$order + 1"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$order - 0"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:attribute>
    <xsl:element name="navLabel" namespace="{$ncx.namespace}">
      <xsl:element name="text" namespace="{$ncx.namespace}"><xsl:value-of select="normalize-space($title)"/> </xsl:element>
    </xsl:element>
    <xsl:element name="content" namespace="{$ncx.namespace}">
      <xsl:attribute name="src">
        <xsl:value-of select="$href"/>
      </xsl:attribute>
    </xsl:element>
    <xsl:if test="$depth != 0">
      <!-- Don't recurse on root element, but treat it as a single point so
      the progress bar shows all top level children -->
      <xsl:apply-templates select="book[parent::set]|part|reference|preface|chapter|bibliography|appendix|article|topic|glossary|section|sect1|sect2|sect3|sect4|sect5|refentry|colophon|bibliodiv[title]|setindex|index" mode="ncx"/>
    </xsl:if>
  </xsl:element>

</xsl:template>

<xsl:template match="text()" mode="ncx" />

<xsl:template name="package.spine">

  <xsl:variable name="toc.params">
    <xsl:call-template name="find.path.params">
      <xsl:with-param name="node" select="."/>
      <xsl:with-param name="table" select="normalize-space($generate.toc)"/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:element namespace="http://www.idpf.org/2007/opf" name="spine">
    <xsl:if test="$epub.include.ncx != 0">
      <xsl:attribute name="toc">
        <xsl:value-of select="$epub.ncx.manifest.id"/>
      </xsl:attribute>
    </xsl:if>

    <xsl:call-template name="spine.cover"/>


    <xsl:if test="contains($toc.params, 'toc')">
      <xsl:element namespace="http://www.idpf.org/2007/opf" name="itemref">
        <xsl:attribute name="idref"> <xsl:value-of select="$epub.html.toc.id"/> </xsl:attribute>
        <xsl:attribute name="linear">yes</xsl:attribute>
      </xsl:element>
    </xsl:if>  

    <!-- TODO: be nice to have a idref="titlepage" here -->
    <xsl:choose>
      <xsl:when test="$root.is.a.chunk != '0'">
        <xsl:apply-templates select="/*" mode="package.spine"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:apply-templates select="/*/*" mode="package.spine"/>
      </xsl:otherwise>
    </xsl:choose>
                                 
  </xsl:element>
</xsl:template>

<xsl:template name="spine.cover">
  <xsl:variable name="info" select="./*[contains(local-name(.), 'info')][1]"/>
  <xsl:variable name="cover.image" 
                select="$info//mediaobject[@role='cover' or ancestor::cover]"/>

  <xsl:if test="$cover.image">
    <!-- generate the spine reference to that cover html file -->
    <xsl:element namespace="http://www.idpf.org/2007/opf" name="itemref">
      <xsl:attribute name="idref">
        <xsl:value-of select="$epub.cover.filename.id"/>
      </xsl:attribute>
      <xsl:attribute name="linear">
      <xsl:choose>
        <xsl:when test="$epub.cover.linear">
          <xsl:text>yes</xsl:text>
        </xsl:when>
        <xsl:otherwise>no</xsl:otherwise>
      </xsl:choose>
      </xsl:attribute>
    </xsl:element>
  </xsl:if>
</xsl:template>

<xsl:template match="*" mode="package.spine">
  <xsl:variable name="is.chunk">
    <xsl:call-template name="chunk">
      <xsl:with-param name="node" select="."/>
    </xsl:call-template>
  </xsl:variable>

  <xsl:if test="$is.chunk != 0">
    <xsl:element namespace="http://www.idpf.org/2007/opf" name="itemref">
      <xsl:attribute name="idref">
        <xsl:value-of select="concat($epub.package.id.prefix, generate-id(.))"/>
      </xsl:attribute>
    </xsl:element>
    <xsl:apply-templates select="*|.//refentry" mode="package.spine"/>
  </xsl:if>
</xsl:template>

<xsl:template match="*" mode="container">
  <xsl:call-template name="container"/>
</xsl:template>

<xsl:template name="container">
  <!-- The path in rootfile does not include all of base.dir, only the last part -->
  <xsl:variable name="full-path-dir">
    <xsl:call-template name="filename-basename">
      <xsl:with-param name="filename" select="$chunk.base.dir"/>
    </xsl:call-template>
  </xsl:variable>
  <xsl:call-template name="write.chunk">
    <xsl:with-param name="filename">
      <xsl:value-of select="$epub.container.pathname" />
    </xsl:with-param>
    <xsl:with-param name="method" select="'xml'" />
    <xsl:with-param name="encoding" select="'utf-8'" />
    <xsl:with-param name="indent" select="'no'" />
    <xsl:with-param name="quiet" select="$chunk.quietly" />
    <xsl:with-param name="doctype-public" select="''"/> <!-- intentionally blank -->
    <xsl:with-param name="doctype-system" select="''"/> <!-- intentionally blank -->

    <xsl:with-param name="content">
      <xsl:element namespace="urn:oasis:names:tc:opendocument:xmlns:container" name="container">
        <xsl:attribute name="version">1.0</xsl:attribute>
        <xsl:element namespace="urn:oasis:names:tc:opendocument:xmlns:container" name="rootfiles">
          <xsl:element namespace="urn:oasis:names:tc:opendocument:xmlns:container" name="rootfile">
            <xsl:attribute name="full-path">
              <xsl:value-of 
                     select="concat($full-path-dir, $epub.package.filename)"/>
            </xsl:attribute>
            <xsl:attribute name="media-type">
              <xsl:text>application/oebps-package+xml</xsl:text>
            </xsl:attribute>
          </xsl:element>
        </xsl:element>
      </xsl:element>
    </xsl:with-param>
  </xsl:call-template>
</xsl:template>

<xsl:template name="mimetype">
  <xsl:call-template name="write.text.chunk">
    <xsl:with-param name="filename" select="$epub.mimetype.pathname"/>
    <xsl:with-param name="content" select="$epub.mimetype.value"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="mediaobject[@role='cover' or ancestor::cover]">
  <xsl:call-template name="write.chunk">
    <xsl:with-param name="filename">
      <xsl:value-of select="$epub.cover.pathname" />
    </xsl:with-param>
    <xsl:with-param name="method" select="'xml'" />
    <xsl:with-param name="encoding" select="'utf-8'" />
    <xsl:with-param name="indent" select="'no'" />
    <xsl:with-param name="quiet" select="$chunk.quietly" />
    <xsl:with-param name="content">
      <xsl:element namespace="http://www.w3.org/1999/xhtml" name="html">
        <xsl:element namespace="http://www.w3.org/1999/xhtml" name="head">
          <xsl:element namespace="http://www.w3.org/1999/xhtml" name="title">Cover</xsl:element>
          <xsl:element namespace="http://www.w3.org/1999/xhtml" name="style">
            <xsl:attribute name="type">text/css</xsl:attribute>
            <!-- Help the cover image scale nicely in the CSS then apply a max-width to look better in Adobe Digital Editions -->
            <xsl:text> img { max-width: 100%; }</xsl:text>
          </xsl:element>
        </xsl:element>
        <xsl:element namespace="http://www.w3.org/1999/xhtml" name="body">
          <xsl:element namespace="http://www.w3.org/1999/xhtml" name="div">
            <xsl:attribute name="id">
              <xsl:value-of select="$epub.cover.image.id"/>
            </xsl:attribute>
            <xsl:choose>
              <xsl:when test="imageobject[@role='front-large']">
                <xsl:apply-templates select="imageobject[@role='front-large']"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:apply-templates select="imageobject[1]"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
          <!-- If this is defined as an explicit cover page, then process
          any remaining text -->
          <xsl:if test="ancestor::cover">
            <xsl:apply-templates select="ancestor::cover/para"/>
          </xsl:if>
        </xsl:element>
      </xsl:element>
    </xsl:with-param>  
  </xsl:call-template>  
</xsl:template>

<!-- EPUB3: use <nav> and <ol> in TOC lists  -->
<xsl:template name="make.toc">
  <xsl:param name="toc-context" select="."/>
  <xsl:param name="toc.title.p" select="true()"/>
  <xsl:param name="nodes" select="/NOT-AN-ELEMENT"/>

  <xsl:variable name="nodes.plus" select="$nodes | qandaset"/>

  <xsl:variable name="toc.title">
    <xsl:if test="$toc.title.p">
      <xsl:choose>
        <xsl:when test="$make.clean.html != 0">
          <div class="toc-title">
            <xsl:call-template name="gentext">
              <xsl:with-param name="key">TableofContents</xsl:with-param>
            </xsl:call-template>
          </div>
        </xsl:when>
        <xsl:otherwise>
          <p>
            <b>
              <xsl:call-template name="gentext">
                <xsl:with-param name="key">TableofContents</xsl:with-param>
              </xsl:call-template>
            </b>
          </p>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$manual.toc != ''">
      <xsl:variable name="id">
        <xsl:call-template name="object.id"/>
      </xsl:variable>
      <xsl:variable name="toc" select="document($manual.toc, .)"/>
      <xsl:variable name="tocentry" select="$toc//tocentry[@linkend=$id]"/>
      <xsl:if test="$tocentry and $tocentry/*">
        <div class="toc">
          <xsl:copy-of select="$toc.title"/>
          <nav epub:type="toc">
            <xsl:element name="{$toc.list.type}">
              <xsl:call-template name="manual-toc">
                <xsl:with-param name="tocentry" select="$tocentry/*[1]"/>
              </xsl:call-template>
            </xsl:element>
          </nav>
        </div>
      </xsl:if>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$qanda.in.toc != 0">
          <div class="toc">
            <xsl:copy-of select="$toc.title"/>
            <nav epub:type="toc">
              <xsl:element name="{$toc.list.type}">
                <xsl:if test="$nodes.plus">
                  <xsl:apply-templates select="$nodes.plus" mode="toc">
                    <xsl:with-param name="toc-context" select="$toc-context"/>
                  </xsl:apply-templates>
                </xsl:if>
              </xsl:element>
            </nav>
          </div>
        </xsl:when>
        <xsl:otherwise>
          <div class="toc">
            <xsl:copy-of select="$toc.title"/>
            <nav epub:type="toc">
              <xsl:element name="{$toc.list.type}">
                <xsl:if test="$nodes">
                  <xsl:apply-templates select="$nodes" mode="toc">
                    <xsl:with-param name="toc-context" select="$toc-context"/>
                  </xsl:apply-templates>
                </xsl:if>
              </xsl:element>
            </nav>
          </div>
        </xsl:otherwise>
      </xsl:choose>

    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="list.of.titles">
  <xsl:param name="toc-context" select="."/>
  <xsl:param name="titles" select="'table'"/>
  <xsl:param name="nodes" select=".//table"/>

  <xsl:variable name="epub.type">
    <xsl:choose>
      <xsl:when test="$titles='table'">lot</xsl:when>
      <xsl:when test="$titles='figure'">loi</xsl:when>
      <xsl:when test="$titles='equation'">loi</xsl:when>
      <xsl:when test="$titles='example'">loi</xsl:when>
      <xsl:when test="$titles='procedure'">loi</xsl:when>
      <xsl:otherwise>loi</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:if test="$nodes">
    <div class="list-of-{$titles}s">
      <nav epub:type="{$epub.type}">
        <h4 class="toc-title">
          <xsl:call-template name="gentext">
            <xsl:with-param name="key">
              <xsl:choose>
                <xsl:when test="$titles='table'">ListofTables</xsl:when>
                <xsl:when test="$titles='figure'">ListofFigures</xsl:when>
                <xsl:when test="$titles='equation'">ListofEquations</xsl:when>
                <xsl:when test="$titles='example'">ListofExamples</xsl:when>
                <xsl:when test="$titles='procedure'">ListofProcedures</xsl:when>
                <xsl:otherwise>ListofUnknown</xsl:otherwise>
              </xsl:choose>
            </xsl:with-param>
          </xsl:call-template>
        </h4>

        <xsl:element name="{$toc.list.type}">
          <xsl:apply-templates select="$nodes" mode="toc">
            <xsl:with-param name="toc-context" select="$toc-context"/>
          </xsl:apply-templates>
        </xsl:element>
      </nav>
    </div>
  </xsl:if>
</xsl:template>

<!-- EPUB3: add hidden="" for sections below toc.section.depth -->
<xsl:template name="subtoc">
  <xsl:param name="toc-context" select="."/>
  <xsl:param name="nodes" select="NOT-AN-ELEMENT"/>

  <xsl:variable name="nodes.plus" select="$nodes | qandaset"/>

  <xsl:variable name="depth">
    <xsl:choose>
      <xsl:when test="local-name(.) = 'section'">
        <xsl:value-of select="count(ancestor::section) + 1"/>
      </xsl:when>
      <xsl:when test="local-name(.) = 'sect1'">1</xsl:when>
      <xsl:when test="local-name(.) = 'sect2'">2</xsl:when>
      <xsl:when test="local-name(.) = 'sect3'">3</xsl:when>
      <xsl:when test="local-name(.) = 'sect4'">4</xsl:when>
      <xsl:when test="local-name(.) = 'sect5'">5</xsl:when>
      <xsl:when test="local-name(.) = 'refsect1'">1</xsl:when>
      <xsl:when test="local-name(.) = 'refsect2'">2</xsl:when>
      <xsl:when test="local-name(.) = 'refsect3'">3</xsl:when>
      <xsl:when test="local-name(.) = 'simplesect'">
        <!-- sigh... -->
        <xsl:choose>
          <xsl:when test="local-name(..) = 'section'">
            <xsl:value-of select="count(ancestor::section)"/>
          </xsl:when>
          <xsl:when test="local-name(..) = 'sect1'">2</xsl:when>
          <xsl:when test="local-name(..) = 'sect2'">3</xsl:when>
          <xsl:when test="local-name(..) = 'sect3'">4</xsl:when>
          <xsl:when test="local-name(..) = 'sect4'">5</xsl:when>
          <xsl:when test="local-name(..) = 'sect5'">6</xsl:when>
          <xsl:when test="local-name(..) = 'refsect1'">2</xsl:when>
          <xsl:when test="local-name(..) = 'refsect2'">3</xsl:when>
          <xsl:when test="local-name(..) = 'refsect3'">4</xsl:when>
          <xsl:otherwise>1</xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>0</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:variable name="depth.from.context" select="count(ancestor::*)-count($toc-context/ancestor::*)"/>

  <xsl:variable name="subtoc">
    <xsl:element name="{$toc.list.type}">
      <xsl:choose>
        <xsl:when test="$toc.listitem.type = 'li'
                  and $toc.section.depth > $depth and 
                  ( ($qanda.in.toc = 0 and count($nodes)&gt;0) or
                    ($qanda.in.toc != 0 and count($nodes.plus)&gt;0) )
                  and $toc.max.depth > $depth.from.context">
          <!-- No @hidden attribute -->
        </xsl:when>
        <xsl:otherwise>
          <!-- Add empty @hidden attribute if too deep -->
          <xsl:attribute name="hidden"></xsl:attribute>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:choose>
        <xsl:when test="$qanda.in.toc != 0">
          <xsl:apply-templates mode="toc" select="$nodes.plus">
            <xsl:with-param name="toc-context" select="$toc-context"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates mode="toc" select="$nodes">
            <xsl:with-param name="toc-context" select="$toc-context"/>
          </xsl:apply-templates>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:element>
  </xsl:variable>

  <xsl:variable name="subtoc.list">
    <xsl:choose>
      <xsl:when test="$toc.dd.type = ''">
        <xsl:copy-of select="$subtoc"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:element name="{$toc.dd.type}">
          <xsl:copy-of select="$subtoc"/>
        </xsl:element>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <xsl:element name="{$toc.listitem.type}">
    <xsl:call-template name="toc.line">
      <xsl:with-param name="toc-context" select="$toc-context"/>
    </xsl:call-template>
    <xsl:if test="$toc.listitem.type = 'li'
                  and 
                  ( ($qanda.in.toc = 0 and count($nodes)&gt;0) or
                    ($qanda.in.toc != 0 and count($nodes.plus)&gt;0) ) ">
      <xsl:copy-of select="$subtoc.list"/>
    </xsl:if>
  </xsl:element>
</xsl:template>

<!-- Inserted when a title is blank to avoid epubcheck error -->
<xsl:param name="toc.entry.default.text">&#xA0;</xsl:param>

<!-- EPUB3: either <a> or <span>, but not both  -->
<xsl:template name="toc.line">
  <xsl:param name="toc-context" select="."/>
  <xsl:param name="depth" select="1"/>
  <xsl:param name="depth.from.context" select="8"/>

  <xsl:variable name="title">
    <xsl:apply-templates select="." mode="title.markup"/>
  </xsl:variable>

  <a>
    <xsl:attribute name="href">
      <xsl:call-template name="href.target">
        <xsl:with-param name="context" select="$toc-context"/>
        <xsl:with-param name="toc-context" select="$toc-context"/>
      </xsl:call-template>
    </xsl:attribute>
    
    <!-- * if $autotoc.label.in.hyperlink is non-zero, then output the label -->
    <!-- * as part of the hyperlinked title -->
    <xsl:if test="not($autotoc.label.in.hyperlink = 0)">
      <xsl:variable name="label">
        <xsl:apply-templates select="." mode="label.markup"/>
      </xsl:variable>
      <xsl:copy-of select="$label"/>
      <xsl:if test="$label != ''">
        <xsl:value-of select="$autotoc.label.separator"/>
      </xsl:if>
    </xsl:if>

    <xsl:choose>
      <xsl:when test="string-length(normalize-space($title)) != 0">
        <xsl:copy-of select="$title"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$toc.entry.default.text"/>
      </xsl:otherwise>
    </xsl:choose>
  </a>
</xsl:template>

<!-- Make sure all text is inside the <a> element for epub3 -->
<xsl:template match="figure|table|example|equation|procedure" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:element name="{$toc.listitem.type}">
    <a>
      <xsl:attribute name="href">
        <xsl:call-template name="href.target">
          <xsl:with-param name="toc-context" select="$toc-context"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:variable name="label">
        <xsl:apply-templates select="." mode="label.markup"/>
      </xsl:variable>
      <xsl:copy-of select="$label"/>
      <xsl:if test="$label != ''">
        <xsl:value-of select="$autotoc.label.separator"/>
      </xsl:if>
      <xsl:apply-templates select="." mode="titleabbrev.markup"/>
    </a>
  </xsl:element>
</xsl:template>

<!-- Remove spans from refentry TOC lines for epub3check -->
<xsl:template match="refentry" mode="toc">
  <xsl:param name="toc-context" select="."/>

  <xsl:variable name="refmeta" select=".//refmeta"/>
  <xsl:variable name="refentrytitle" select="$refmeta//refentrytitle"/>
  <xsl:variable name="refnamediv" select=".//refnamediv"/>
  <xsl:variable name="refname" select="$refnamediv//refname"/>
  <xsl:variable name="refdesc" select="$refnamediv//refdescriptor"/>
  <xsl:variable name="title">
    <xsl:choose>
      <xsl:when test="$refentrytitle">
        <xsl:apply-templates select="$refentrytitle[1]" mode="titleabbrev.markup"/>
      </xsl:when>
      <xsl:when test="$refdesc">
        <xsl:apply-templates select="$refdesc" mode="titleabbrev.markup"/>
      </xsl:when>
      <xsl:when test="$refname">
        <xsl:apply-templates select="$refname[1]" mode="titleabbrev.markup"/>
      </xsl:when>
    </xsl:choose>
  </xsl:variable>

  <xsl:element name="{$toc.listitem.type}" namespace="http://www.w3.org/1999/xhtml">
    <a>
      <xsl:attribute name="href">
        <xsl:call-template name="href.target">
          <xsl:with-param name="toc-context" select="$toc-context"/>
        </xsl:call-template>
      </xsl:attribute>
      <xsl:copy-of select="$title"/>
      <xsl:if test="$annotate.toc != 0">
        <!-- * DocBook 5 says inlinemediaobject (among other things) -->
        <!-- * is allowed in refpurpose; so we need to run -->
        <!-- * apply-templates on refpurpose here, instead of value-of  -->
        <!-- Set allow-anchors=0 to avoid indexterms and other links -->
        <xsl:text> - </xsl:text>
        <xsl:apply-templates select="refnamediv/refpurpose" mode="no.anchor.mode"/>
      </xsl:if>
    </a>
  </xsl:element>
</xsl:template>

<!-- Copy these here so relative document() open gets the correct css source -->
<xsl:template name="generate.default.css.file">
  <xsl:if test="$make.clean.html != 0 and 
                $generate.css.header = 0 and
                $docbook.css.source != ''">
    <!-- Select default file relative to stylesheet -->
    <xsl:variable name="css.node" select="document($docbook.css.source)/*[1]"/>

    <xsl:call-template name="generate.css.file">
      <xsl:with-param name="src" select="$docbook.css.source"/>
      <xsl:with-param name="css.node" select="$css.node"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="generate.custom.css.file">
  <xsl:if test="$custom.css.source != '' and
                $generate.css.header = 0">
    <!-- Select custom file relative to document -->
    <xsl:variable name="css.node" select="document($custom.css.source,.)/*[1]"/>

    <xsl:call-template name="generate.css.file">
      <xsl:with-param name="src" select="$custom.css.source"/>
      <xsl:with-param name="css.node" select="$css.node"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
