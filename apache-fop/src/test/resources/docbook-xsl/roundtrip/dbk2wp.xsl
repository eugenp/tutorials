<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:doc='http://docbook.org/ns/docbook'
  exclude-result-prefixes='doc'>

  <!-- ********************************************************************
       $Id: dbk2wp.xsl 9396 2012-06-02 21:56:19Z bobstayton $
       ********************************************************************

       This file is part of the XSL DocBook Stylesheet distribution.
       See ../README or http://docbook.sf.net/release/xsl/current/ for
       copyright and other information.

       ******************************************************************** -->

  <!-- DO NOT USE THIS STYLESHEET!

       This stylesheet is imported by the other dbk2* stylesheets.
       Use one of those instead.

    -->

  <xsl:include href='../VERSION.xsl'/>

  <!-- doc:docprop.author mode is for creating document metadata -->

  <xsl:template match='author|doc:author|editor|doc:editor' mode='doc:docprop.author'>
    <xsl:apply-templates select='firstname|doc:firstname |
                                 personname/firstname|doc:personname/doc:firstname'
      mode='doc:docprop.author'/>
    <xsl:text> </xsl:text>
    <xsl:apply-templates select='surname|doc:surname |
                                 personname/surname|doc:personname/doc:surname'
      mode='doc:docprop.author'/>
  </xsl:template>

  <xsl:template match='firstname|doc:firstname |
                       surname|doc:surname'
    mode='doc:docprop.author'>
    <xsl:apply-templates select='.' mode='doc:body'/>
  </xsl:template>

  <!-- doc:toplevel mode is for processing whole documents -->

  <xsl:template match='*' mode='doc:toplevel'>
    <xsl:call-template name='doc:make-body'/>
  </xsl:template>

  <!-- doc:body mode is for processing components of a document -->

  <xsl:template match='book|article|chapter|section|sect1|sect2|sect3|sect4|sect5|simplesect |
                       doc:book|doc:article|doc:chapter|doc:section|doc:sect1|doc:sect2|doc:sect3|doc:sect4|doc:sect5|doc:simplesect'
    mode='doc:body'>
    <xsl:call-template name='doc:make-subsection'/>
  </xsl:template>

  <xsl:template match='articleinfo |
		       chapterinfo |
		       bookinfo |
                       doc:info |
                       doc:articleinfo |
		       doc:chapterinfo |
		       doc:bookinfo'
    mode='doc:body'>
    <xsl:apply-templates select='title|subtitle|titleabbrev |
                                 doc:title|doc:subtitle|doc:titleabbrev'
      mode='doc:body'/>
    <xsl:apply-templates select='author|releaseinfo|abstract |
                                 doc:author|doc:releaseinfo|doc:abstract'
      mode='doc:body'/>
    <!-- current implementation ignores all other metadata -->
    <xsl:for-each select='*[not(self::title|self::subtitle|self::titleabbrev|self::author|self::releaseinfo|self::abstract |
                          self::doc:title|self::doc:subtitle|self::doc:titleabbrev|self::doc:author|self::doc:releaseinfo|self::doc:abstract)]'>
      <xsl:call-template name='doc:nomatch'/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match='title|subtitle|titleabbrev |
                       doc:title|doc:subtitle|doc:titleabbrev'
    mode='doc:body'>
    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style'>
	<xsl:choose>
          <xsl:when test='(parent::section|parent::doc:section or
                          parent::sectioninfo/parent::section|parent::doc:sectioninfo/parent::doc:section) and
                          count(ancestor::section|ancestor::doc:section) > 5'>
            <xsl:call-template name='doc:warning'>
	      <xsl:with-param name='message'>section nested deeper than 5 levels</xsl:with-param>
	    </xsl:call-template>
            <xsl:text>sect5-</xsl:text>
            <xsl:value-of select='local-name()'/>
          </xsl:when>
          <xsl:when test='parent::section|parent::doc:section or
                          parent::sectioninfo/parent::section|parent::doc:sectioninfo/parent::doc:section'>
            <xsl:text>sect</xsl:text>
            <xsl:value-of select='count(ancestor::section|ancestor::doc:section)'/>
            <xsl:text>-</xsl:text>
            <xsl:value-of select='local-name()'/>
          </xsl:when>
          <xsl:when test='contains(local-name(..), "info")'>
            <xsl:value-of select='local-name(../..)'/>
            <xsl:text>-</xsl:text>
            <xsl:value-of select='local-name()'/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select='local-name(..)'/>
            <xsl:text>-</xsl:text>
            <xsl:value-of select='local-name()'/>
          </xsl:otherwise>
	</xsl:choose>
      </xsl:with-param>
      <xsl:with-param name='outline.level'
		      select='count(ancestor::*) - count(parent::*[contains(local-name(), "info")]) - 1'/>
      <xsl:with-param name='attributes.node'
		      select='../parent::*[contains(local-name(current()), "info")] |
			      parent::*[not(contains(local-name(current()), "info"))]'/>
      <xsl:with-param name='content'>
	<xsl:apply-templates mode='doc:body'/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <doc:template name='metadata' xmlns=''>
    <title>Metadata</title>

    <para>TODO: Handle all metadata elements, apart from titles.</para>
  </doc:template>
  <xsl:template match='*[contains(local-name(), "info")]/*[not(self::title|self::subtitle|self::titleabbrev|self::doc:title|self::doc:subtitle|self::doc:titleabbrev)]'
		priority='0'
		mode='doc:body'/>

  <xsl:template match='author|editor|othercredit |
                       doc:author|doc:editor|doc:othercredit'
    mode='doc:body'>
    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style'
		      select='local-name()'/>
      <xsl:with-param name='content'>
	<xsl:apply-templates select='personname|surname|firstname|honorific|lineage|othername|contrib |
                                     doc:personname|doc:surname|doc:firstname|doc:honorific|doc:lineage|doc:othername|doc:contrib'
			     mode='doc:body'/>
      </xsl:with-param>
    </xsl:call-template>

    <xsl:apply-templates select='affiliation|address |
                                 doc:affiliation|doc:address'
      mode='doc:body'/>
    <xsl:apply-templates select='authorblurb|personblurb |
                                 doc:authorblurb|doc:personblurb'
      mode='doc:body'/>
  </xsl:template>
  <xsl:template match='affiliation|doc:affiliation'
    mode='doc:body'>
    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style' select='"affiliation"'/>
      <xsl:with-param name='content'>
	<xsl:apply-templates mode='doc:body'/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>
  <xsl:template match='address[parent::author|parent::editor|parent::othercredit] |
                       doc:address[parent::doc:author|parent::doc:editor|parent::doc:othercredit]'
		mode='doc:body'>
    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style' select='"address"'/>
      <xsl:with-param name='content'>
	<xsl:apply-templates mode='doc:body'/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>
  <!-- do not attempt to handle recursive structures -->
  <xsl:template match='address[not(parent::author|parent::editor|parent::othercredit)] |
                       doc:address[not(parent::doc:author|parent::doc:editor|parent::doc:othercredit)]'
    mode='doc:body'>
    <xsl:apply-templates select='node()[not(self::affiliation|self::authorblurb|self::doc:affiliation|self::doc:authorblurb)]'/>
  </xsl:template>
  <xsl:template match='abstract|doc:abstract'
    mode='doc:body'>
    <xsl:if test='title|doc:title'>
      <xsl:call-template name='doc:make-paragraph'>
        <xsl:with-param name='style' select='"abstract-title"'/>
        <xsl:with-param name='content'>
          <xsl:apply-templates select='title/node()|doc:title/node()'
            mode='doc:body'/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:if>

    <xsl:apply-templates select='*[not(self::title|self::doc:title)]'
      mode='doc:body'>
      <xsl:with-param name='class'>abstract</xsl:with-param>
    </xsl:apply-templates>
  </xsl:template>
  <!-- TODO -->
  <xsl:template match='authorblurb|personblurb |
                       doc:authorblurb|doc:personblurb'
    mode='doc:body'/>

  <!-- TODO: handle inline markup (eg. emphasis) -->
  <xsl:template match='surname|firstname|honorific|lineage|othername|contrib|email|shortaffil|jobtitle|orgname|orgdiv|street|pob|postcode|city|state|country|phone|fax|citetitle |
                       doc:surname|doc:firstname|doc:honorific|doc:lineage|doc:othername|doc:contrib|doc:email|doc:shortaffil|doc:jobtitle|doc:orgname|doc:orgdiv|doc:street|doc:pob|doc:postcode|doc:city|doc:state|doc:country|doc:phone|doc:fax|doc:citetitle'
    mode='doc:body'>
    <xsl:if test='preceding-sibling::*'>
      <xsl:call-template name='doc:make-phrase'>
	<xsl:with-param name='content'>
          <xsl:text> </xsl:text>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:if>
    <xsl:call-template name='doc:handle-linebreaks'>
      <xsl:with-param name='style' select='local-name()'/>
      <xsl:with-param name='content' select='node()'/>
    </xsl:call-template>
  </xsl:template>
  <xsl:template match='email|doc:email'
    mode='doc:body'>
    <xsl:variable name='address'>
      <xsl:choose>
        <xsl:when test='starts-with(., "mailto:")'>
          <xsl:value-of select='.'/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:text>mailto:</xsl:text>
          <xsl:value-of select='.'/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:call-template name='doc:make-hyperlink'>
      <xsl:with-param name='target' select='$address'/>
      <xsl:with-param name='content'>
	<xsl:call-template name='doc:handle-linebreaks'>
	  <xsl:with-param name='style'>Hyperlink</xsl:with-param>
	</xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>
  <!-- otheraddr often contains ulink -->
  <xsl:template match='otheraddr|doc:otheraddr'
    mode='doc:body'>
    <xsl:choose>
      <xsl:when test='ulink|doc:ulink'>
        <xsl:for-each select='ulink|doc:ulink'>
          <xsl:variable name='prev'
            select='preceding-sibling::ulink[1] |
                    preceding-sibling::doc:ulink[1]'/>
          <xsl:choose>
            <xsl:when test='$prev'>
              <xsl:for-each
                select='preceding-sibling::node()[generate-id(following-sibling::*[self::ulink|self::doc:ulink][1]) = generate-id(current())]'>
		<xsl:call-template name='doc:handle-linebreaks'>
		  <xsl:with-param name='style'>otheraddr</xsl:with-param>
		</xsl:call-template>
              </xsl:for-each>
            </xsl:when>
            <xsl:when test='preceding-sibling::node()'>
	      <xsl:call-template name='doc:handle-linebreaks'>
		<xsl:with-param name='style'>otheraddr</xsl:with-param>
	      </xsl:call-template>
            </xsl:when>
          </xsl:choose>
          <xsl:apply-templates select='.'/>
        </xsl:for-each>
        <xsl:if test='*[self::ulink|self::doc:ulink][last()]/following-sibling::node()'>
	  <xsl:call-template name='doc:handle-linebreaks'>
	    <xsl:with-param name='content'
	      select='*[self::ulink|self::doc:ulink][last()]/following-sibling::node()'/>
	    <xsl:with-param name='style'>otheraddr</xsl:with-param>
	  </xsl:call-template>
        </xsl:if>
      </xsl:when>
      <xsl:otherwise>
	<xsl:call-template name='doc:handle-linebreaks'>
	  <xsl:with-param name='style'>otheraddr</xsl:with-param>
	</xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='ulink|doc:ulink'
    mode='doc:body'>
    <xsl:call-template name='doc:make-hyperlink'>
      <xsl:with-param name='target' select='@url'/>
      <xsl:with-param name='content'>
	<xsl:call-template name='doc:handle-linebreaks'>
	  <xsl:with-param name='style'>Hyperlink</xsl:with-param>
	</xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <!-- Cannot round-trip this element -->
  <xsl:template match='personname|doc:personname'
    mode='doc:body'>
    <xsl:apply-templates mode='doc:body'/>
  </xsl:template>

  <xsl:template match='releaseinfo|doc:releaseinfo'
    mode='doc:body'>
    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style'
        select='"releaseinfo"'/>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match='para|doc:para'
    mode='doc:body'>
    <xsl:param name='class'/>

    <xsl:variable name='block'
      select='blockquote|calloutlist|classsynopsis|funcsynopsis|figure|glosslist|graphic|informalfigure|informaltable|itemizedlist|literallayout|mediaobject|mediaobjectco|note|caution|warning|important|tip|orderedlist|programlisting|revhistory|segmentedlist|simplelist|table|variablelist |
              doc:blockquote|doc:calloutlist|doc:classsynopsis|doc:funcsynopsis|doc:figure|doc:glosslist|doc:graphic|doc:informalfigure|doc:informaltable|doc:itemizedlist|doc:literallayout|doc:mediaobject|doc:mediaobjectco|doc:note|doc:caution|doc:warning|doc:important|doc:tip|doc:orderedlist|doc:programlisting|doc:revhistory|doc:segmentedlist|doc:simplelist|doc:table|doc:variablelist'/>

    <xsl:choose>
      <xsl:when test='$block'>
	<xsl:call-template name='doc:make-paragraph'>
	  <xsl:with-param name='style'>
            <xsl:choose>
              <xsl:when test='$class != ""'>
                <xsl:value-of select='$class'/>
              </xsl:when>
              <xsl:otherwise>para</xsl:otherwise>
            </xsl:choose>
	  </xsl:with-param>
	  <xsl:with-param name='content'
			  select='$block[1]/preceding-sibling::node()'/>
        </xsl:call-template>

        <xsl:for-each select='$block'>
          <xsl:apply-templates select='.'/>

	  <xsl:call-template name='doc:make-paragraph'>
	    <xsl:with-param name='style'>
              <xsl:choose>
		<xsl:when test='$class != ""'>
                  <xsl:value-of select='$class'/>
		</xsl:when>
		<xsl:otherwise>Normal</xsl:otherwise>
              </xsl:choose>
	    </xsl:with-param>
	    <xsl:with-param name='content'
			    select='following-sibling::node()[generate-id(preceding-sibling::*[self::blockquote|self::calloutlist|self::figure|self::glosslist|self::graphic|self::informalfigure|self::informaltable|self::itemizedlist|self::literallayout|self::mediaobject|self::mediaobjectco|self::note|self::caution|self::warning|self::important|self::tip|self::orderedlist|self::programlisting|self::revhistory|self::segmentedlist|self::simplelist|self::table|self::variablelist | self::doc:blockquote|self::doc:calloutlist|self::doc:figure|self::doc:glosslist|self::doc:graphic|self::doc:informalfigure|self::doc:informaltable|self::doc:itemizedlist|self::doc:literallayout|self::doc:mediaobject|self::doc:mediaobjectco|self::doc:note|self::doc:caution|self::doc:warning|self::doc:important|self::doc:tip|self::doc:orderedlist|self::doc:programlisting|self::doc:revhistory|self::doc:segmentedlist|self::doc:simplelist|self::doc:table|self::doc:variablelist][1]) = generate-id(current())]'/>
          </xsl:call-template>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise>
	<xsl:call-template name='doc:make-paragraph'>
	  <xsl:with-param name='style'>
            <xsl:choose>
	      <xsl:when test='$class != ""'>
                <xsl:value-of select='$class'/>
	      </xsl:when>
	      <xsl:otherwise>Normal</xsl:otherwise>
            </xsl:choose>
	  </xsl:with-param>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='simpara|doc:simpara'
    mode='doc:body'>
    <xsl:param name='class'/>

    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style'>
        <xsl:choose>
	  <xsl:when test='$class != ""'>
            <xsl:value-of select='concat("sim-", $class)'/>
	  </xsl:when>
	  <xsl:otherwise>simpara</xsl:otherwise>
        </xsl:choose>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match='emphasis|doc:emphasis'
    mode='doc:body'>
    <xsl:call-template name='doc:make-phrase'>
      <xsl:with-param name='italic'>
	<xsl:choose>
	  <xsl:when test='not(@role)'>1</xsl:when>
	  <xsl:otherwise>0</xsl:otherwise>
	</xsl:choose>
      </xsl:with-param>
      <xsl:with-param name='bold'>
	<xsl:choose>
	  <xsl:when test='@role = "bold" or @role = "strong"'>1</xsl:when>
	  <xsl:otherwise>0</xsl:otherwise>
	</xsl:choose>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match='informalfigure|doc:informalfigure'
    mode='doc:body'>
    <xsl:if test='mediaobject/imageobject/imagedata |
                  doc:mediaobject/doc:imageobject/doc:imagedata'>
      <xsl:call-template name='doc:make-paragraph'>
	<xsl:with-param name='style' select='"informalfigure-imagedata"'/>
	<xsl:with-param name='content'>
	  <xsl:call-template name='doc:make-phrase'>
            <xsl:with-param name='style'/>
	    <xsl:with-param name='content'>
	      <xsl:apply-templates select='mediaobject/imageobject/imagedata/@fileref |
                                           doc:mediaobject/doc:imageobject/doc:imagedata/@fileref'
				   mode='textonly'/>
	    </xsl:with-param>
	  </xsl:call-template>
	</xsl:with-param>
      </xsl:call-template>
    </xsl:if>
    <xsl:apply-templates select='caption|doc:caption'
      mode='doc:body'/>
    <xsl:for-each select='*[not(self::mediaobject|self::doc:mediaobject|self::caption|self::doc:caption)]'>
      <xsl:call-template name='doc:nomatch'/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match='mediaobject|mediaobjectco |
                       doc:mediaobject|doc:mediaobjectco'
    mode='doc:body'>
    <xsl:apply-templates select='objectinfo/title|doc:objectinfo/doc:title'/>
    <xsl:apply-templates select='objectinfo/subtitle|objectinfo/subtitle |
                                 doc:objectinfo/doc:subtitle|doc:objectinfo/doc:subtitle'/>
    <!-- TODO: indicate error for other children of objectinfo -->

    <xsl:apply-templates select='*[not(self::objectinfo|self::doc:objectinfo)]'/>
  </xsl:template>
  <xsl:template match='imageobject|imageobjectco|audioobject|videoobject |
                       doc:imageobject|doc:imageobjectco|doc:audioobject|doc:videoobject'
    mode='doc:body'>
    <xsl:apply-templates select='objectinfo/title|doc:objectinfo/doc:title'/>
    <xsl:apply-templates select='objectinfo/subtitle|doc:objectinfo/doc:subtitle'/>
    <!-- TODO: indicate error for other children of objectinfo -->

    <xsl:apply-templates select='areaspec|doc:areaspec'/>

    <xsl:choose>
      <xsl:when test='imagedata|audiodata|videodata |
                      doc:imagedata|doc:audiodata|doc:videodata'>
	<xsl:call-template name='doc:make-paragraph'>
	  <xsl:with-param name='style'
			  select='concat(local-name(), "-", local-name(imagedata|audiodata|videodata|doc:imagedata|doc:audiodata|doc:videodata))'/>
	  <xsl:with-param name='content'>
	    <xsl:call-template name='doc:make-phrase'>
	      <xsl:with-param name='content'>
		<xsl:apply-templates select='*/@fileref'
				     mode='textonly'/>
	      </xsl:with-param>
	    </xsl:call-template>
	  </xsl:with-param>
	</xsl:call-template>
      </xsl:when>
      <xsl:when test='self::imageobjectco/imageobject/imagedata |
                      self::doc:imageobjectco/doc:imageobject/doc:imagedata'>
	<xsl:call-template name='doc:make-paragraph'>
	  <xsl:with-param name='style'
			  select='concat(local-name(), "-imagedata")'/>
	  <xsl:with-param name='content'>
	    <xsl:call-template name='doc:make-phrase'>
	      <xsl:with-param name='content'>
		<xsl:apply-templates select='*/@fileref'
				     mode='textonly'/>
	      </xsl:with-param>
	    </xsl:call-template>
	  </xsl:with-param>
	</xsl:call-template>
      </xsl:when>
    </xsl:choose>
    <xsl:apply-templates select='calloutlist|doc:calloutlist'/>

    <xsl:for-each select='*[not(self::imageobject |
			        self::imagedata |
			        self::audiodata |
				self::videodata |
				self::areaspec  |
				self::calloutlist |
                                self::doc:imageobject |
			        self::doc:imagedata |
			        self::doc:audiodata |
				self::doc:videodata |
				self::doc:areaspec  |
				self::doc:calloutlist)]'>
      <xsl:call-template name='doc:nomatch'/>
    </xsl:for-each>
  </xsl:template>
  <xsl:template match='textobject|doc:textobject'
    mode='doc:body'>
    <xsl:choose>
      <xsl:when test='objectinfo/title|objectinfo|subtitle |
                      doc:objectinfo/doc:title|doc:objectinfo|doc:subtitle'>
	<xsl:apply-templates select='objectinfo/title|doc:objectinfo/doc:title'
          mode='doc:body'/>
	<xsl:apply-templates select='objectinfo/subtitle|doc:objectinfo/doc:subtitle'
          mode='doc:body'/>
	<!-- TODO: indicate error for other children of objectinfo -->
      </xsl:when>

      <!-- In a table, the table itself and the caption delimit the textobject -->
      <xsl:when test='ancestor::table |
                      ancestor::doc:table |
                      ancestor::informaltable |
                      ancestor::doc:informaltable'/>

      <xsl:otherwise>
	<!-- synthesize a title so that the parent textobject
	     can be recreated.
	  -->
	<xsl:call-template name='doc:make-paragraph'>
	  <xsl:with-param name='style' select='"textobject-title"'/>
	  <xsl:with-param name='content'>
	    <xsl:call-template name='doc:make-phrase'>
	      <xsl:with-param name='content'>
		<xsl:text>Text Object </xsl:text>
		<xsl:number level='any'/>
	      </xsl:with-param>
	    </xsl:call-template>
	  </xsl:with-param>
	</xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>

    <xsl:apply-templates select='*[not(self::objectinfo|self::doc:objectinfo)]'
      mode='doc:body'/>
  </xsl:template>

  <xsl:template match='caption|doc:caption'
    mode='doc:body'>
    <xsl:choose>
      <xsl:when test='not(*)'>
        <xsl:call-template name='doc:make-paragraph'>
          <xsl:with-param name='style' select='"Caption"'/>
          <xsl:with-param name='content'>
            <xsl:apply-templates/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test='not(text()) and
                      count(*) = count(para|doc:para) and
                      count(*) = 1'>
        <xsl:call-template name='doc:make-paragraph'>
          <xsl:with-param name='style' select='"caption"'/>
          <xsl:with-param name='content'>
            <xsl:apply-templates select='*/node()' mode='doc:body'/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test='text()'>
        <!-- Not valid DocBook -->
        <xsl:call-template name='doc:nomatch'/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name='doc:make-paragraph'>
          <xsl:with-param name='style' select='"Caption"'/>
          <xsl:with-param name='content'>
            <xsl:apply-templates select='*[self::para|self::doc:para][1]/node()'
              mode='doc:body'/>
          </xsl:with-param>
        </xsl:call-template>
        <xsl:for-each select='text()|*[not(self::para|self::doc:para)]|*[self::para|self::doc:para][position() != 1]'>
          <xsl:call-template name='doc:nomatch'/>
        </xsl:for-each>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='area|areaspec|doc:area|doc:areaspec'
    mode='doc:body'>
    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style' select='local-name()'/>
      <xsl:with-param name='content'/>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match='calloutlist|doc:calloutlist'
    mode='doc:body'>
    <xsl:apply-templates select='callout|doc:callout'/>
  </xsl:template>

  <xsl:template match='callout|doc:callout'
    mode='doc:body'>
    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style' select='"callout"'/>
      <xsl:with-param name='content'>
	<!-- Normally a para would be the first child of a callout -->
	<xsl:apply-templates select='*[1][self::para|self::doc:para]/node()'
          mode='list'/>
      </xsl:with-param>
    </xsl:call-template>

    <!-- This is to catch the case where a listitem's first child is not a paragraph.
       - We may not be able to represent this properly.
      -->
    <xsl:apply-templates select='*[1][not(self::para|self::doc:para)]'
      mode='list'/>

    <xsl:apply-templates select='*[position() != 1]'
      mode='list'/>
  </xsl:template>

  <xsl:template match='table|informaltable |
                       doc:table|doc:informaltable'
    mode='doc:body'>
    <xsl:call-template name='doc:make-table'>
      <xsl:with-param name='columns'>
        <xsl:apply-templates select='tgroup/colspec|doc:tgroup/doc:colspec'
          mode='doc:column'/>
      </xsl:with-param>
    </xsl:call-template>
    <xsl:apply-templates select='textobject|doc:textobject'
      mode='doc:body'/>
    <xsl:choose>
      <xsl:when test='caption|doc:caption'>
        <xsl:apply-templates select='caption|doc:caption'
          mode='doc:body'/>
      </xsl:when>
      <xsl:when test='textobject|doc:textobject'>
        <!-- Synthesize a caption to delimit the textobject -->
        <xsl:call-template name='doc:make-paragraph'>
          <xsl:with-param name='style'>Caption</xsl:with-param>
          <xsl:with-param name='content'/>
          <xsl:with-param name='attributes.node' select='/..'/>
        </xsl:call-template>
      </xsl:when>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='colspec|doc:colspec' mode='doc:column'>
    <xsl:variable name='width'>
      <xsl:choose>
        <xsl:when test='contains(@colwidth, "*")'>
          <!-- May need to resolve proportional width with other columns -->
          <xsl:value-of select='substring-before(@colwidth, "*")'/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select='@colwidth'/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:call-template name='doc:make-column'>
      <xsl:with-param name='width' select='$width'/>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match='colspec|doc:colspec' mode='doc:body'/>

  <xsl:template name='doc:repeat'>
    <xsl:param name='repeats' select='0'/>
    <xsl:param name='content'/>

    <xsl:if test='$repeats > 0'>
      <xsl:copy-of select='$content'/>
      <xsl:call-template name='doc:repeat'>
        <xsl:with-param name='repeats' select='$repeats - 1'/>
        <xsl:with-param name='content' select='$content'/>
      </xsl:call-template>
    </xsl:if>
  </xsl:template>
  <xsl:template match='tgroup|tbody|thead |
                       doc:tgroup|doc:tbody|doc:thead'
    mode='doc:body'>
    <xsl:apply-templates mode='doc:body'/>
  </xsl:template>
  <xsl:template match='row|doc:row' mode='doc:body'>
    <xsl:call-template name='doc:make-table-row'>
      <xsl:with-param name='is-header' select='boolean(parent::thead|parent::doc:thead)'/>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match='entry|doc:entry' mode='doc:body'>

    <!-- 
         Position = Sum(i,preceding-sibling[@colspan = ""]) + entry[i].@colspan)
      -->

    <xsl:variable name='position'>
      <xsl:call-template name='doc:sum-sibling'>
        <xsl:with-param name='sum' select='"1"'/>
        <xsl:with-param name='node' select='.'/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:variable name='limit' select='$position + @colspan'/>

    <xsl:variable name='width.raw'>
      <xsl:choose>
        <xsl:when test='@colspan != ""'>

          <!-- Select all the colspec nodes which correspond to the
               column. That is all the nodes between the current 
               column number and the column number plus the span.
            -->

          <xsl:variable name='combinedWidth'>
            <xsl:call-template name='doc:sum'>
              <xsl:with-param name='nodes' select='ancestor::*[self::table|self::doc:table|self::informaltable|self::doc:informaltable][1]/*[self::tgroup|self::doc:tgroup]/*[self::colspec|self::doc:colspec][not(position() &lt; $position) and position() &lt; $limit]'/>
              <xsl:with-param name='sum' select='"0"'/>
            </xsl:call-template>
          </xsl:variable>
          <xsl:value-of select='$combinedWidth'/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select='ancestor::*[self::table|self::doc:table|self::informaltable|self::doc:informaltable][1]/*[self::tgroup|self::doc:tgroup]/*[self::colspec|self::doc:colspec][position() = $position]/@colwidth'/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:call-template name='doc:make-table-cell'>
      <xsl:with-param name='width'>
        <xsl:choose>
          <xsl:when test='contains($width.raw, "*")'>

            <!-- Select all the colspec nodes which correspond to the
                 column. That is all the nodes between the current 
                 column number and the column number plus the span.
              -->

            <xsl:value-of select='substring-before($width.raw, "*")'/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select='$width.raw'/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:with-param>

      <xsl:with-param name='hidden' select='@hidden'/>
      <xsl:with-param name='rowspan' select='@rowspan'/>
      <xsl:with-param name='colspan' select='@colspan'/>

      <xsl:with-param name='content'>
	<xsl:choose>
          <xsl:when test='not(para|doc:para)'>
            <!-- TODO: check for any block elements -->
	    <xsl:call-template name='doc:make-paragraph'>
              <xsl:with-param name='style'/>
              <xsl:with-param name='attributes.node' select='/..'/>
              <xsl:with-param name='content'/>
            </xsl:call-template>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates mode='doc:body'/>
          </xsl:otherwise>
	</xsl:choose>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <!-- Calculates the position by adding the 
       count of the preceding siblings where they aren't colspans
       and adding the colspans of those entries which do.
    -->

  <xsl:template name='doc:sum-sibling'>    
    <xsl:param name='sum'/>
    <xsl:param name='node'/>

    <xsl:variable name='add'>
      <xsl:choose>
        <xsl:when test='$node/preceding-sibling::*[self::entry|self::doc:entry]/@colspan != ""'>
          <xsl:value-of select='$node/preceding-sibling::*[self::entry|self::doc:entry]/@colspan'/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select='"1"'/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:choose>
      <xsl:when test='count($node/preceding-sibling::*[self::entry|self::doc:entry]) &gt; 0'>
        <xsl:call-template name='doc:sum-sibling'>
          <xsl:with-param name='sum' select='$sum + $add'/>
          <xsl:with-param name='node'
            select='$node/preceding-sibling::*[self::entry|self::doc:entry][1]'/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select='$sum'/>
      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>

  <xsl:template name='doc:sum'>
    <xsl:param name='sum' select='"0"'/>
    <xsl:param name='nodes'/>

    <xsl:variable name='tmpSum' select='$sum + $nodes[1]/@colwidth'/>

    <xsl:choose>
      <xsl:when test='count($nodes) &gt; 1'>
        <xsl:call-template name='doc:sum'>
          <xsl:with-param name='nodes' select='$nodes[position() != 1]'/>
          <xsl:with-param name='sum' select='$tmpSum'/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select='$tmpSum'/>
      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>

  <xsl:template match='*[self::para|self::simpara|self::doc:para|self::doc:simpara]/text()[string-length(normalize-space(.)) != 0]'
    mode='doc:body'>
    <xsl:call-template name='doc:handle-linebreaks'/>
  </xsl:template>

  <xsl:template match='text()[not(parent::para|parent::simpara|parent::literallayout|parent::programlisting | parent::doc:para|parent::doc:simpara|parent::doc:literallayout|parent::doc:programlisting)][string-length(normalize-space(.)) != 0]'
    mode='doc:body'>
    <xsl:call-template name='doc:handle-linebreaks'/>
  </xsl:template>
  <xsl:template match='text()[string-length(normalize-space(.)) = 0]'
    mode='doc:body'/>
  <xsl:template match='literallayout/text()|programlisting/text() |
                       doc:literallayout/text()|doc:programlisting/text()'
    mode='doc:body'>
    <xsl:call-template name='doc:handle-linebreaks'/>
  </xsl:template>
  <xsl:template name='doc:handle-linebreaks'>
    <xsl:param name='content' select='.'/>
    <xsl:param name='style'/>

    <xsl:choose>
      <xsl:when test='not($content)'/>
      <xsl:when test='contains($content, "&#xa;")'>
	<xsl:call-template name='doc:make-phrase'>
	  <xsl:with-param name='style' select='$style'/>
	  <xsl:with-param name='content'
			  select='substring-before($content, "&#xa;")'/>
        </xsl:call-template>

        <xsl:call-template name='doc:handle-linebreaks-aux'>
          <xsl:with-param name='content'
            select='substring-after($content, "&#xa;")'/>
	  <xsl:with-param name='style' select='$style'/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
	<xsl:call-template name='doc:make-phrase'>
          <xsl:with-param name='style' select='$style'/>
	  <xsl:with-param name='content' select='$content'/>
	</xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- pre-condition: leading linefeed has been stripped -->
  <xsl:template name='doc:handle-linebreaks-aux'>
    <xsl:param name='content'/>
    <xsl:param name='style'/>

    <xsl:choose>
      <xsl:when test='contains($content, "&#xa;")'>
        <xsl:call-template name='doc:make-phrase'>
	  <xsl:with-param name='style' select='$style'/>
	  <xsl:with-param name='content'>
	    <xsl:call-template name='doc:make-soft-break'/>
            <xsl:value-of select='substring-before($content, "&#xa;")'/>
          </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name='doc:handle-linebreaks-aux'>
          <xsl:with-param name='content'
			  select='substring-after($content, "&#xa;")'/>
	  <xsl:with-param name='style' select='$style'/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name='doc:make-phrase'>
	  <xsl:with-param name='style' select='$style'/>
	  <xsl:with-param name='content'>
	    <xsl:call-template name='doc:make-soft-break'/>
            <xsl:value-of select='$content'/>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='authorblurb|formalpara|legalnotice|note|caution|warning|important|tip |
                       doc:authorblurb|doc:formalpara|doc:legalnotice|doc:note|doc:caution|doc:warning|doc:important|doc:tip'
    mode='doc:body'>
    <xsl:apply-templates select='*'>
      <xsl:with-param name='class'>
        <xsl:value-of select='local-name()'/>
      </xsl:with-param>
    </xsl:apply-templates>
  </xsl:template>

  <xsl:template match='blockquote|doc:blockquote'
    mode='doc:body'>
    <xsl:apply-templates select='blockinfo|title|doc:info|doc:title'
      mode='doc:body'>
      <xsl:with-param name='class'>
        <xsl:value-of select='local-name()'/>
      </xsl:with-param>
    </xsl:apply-templates>
    <xsl:apply-templates select='*[not(self::blockinfo|self::title|self::attribution|self::doc:info|self::doc:title|self::doc:attribution)]'
      mode='doc:body'>
      <xsl:with-param name='class' select='"blockquote"'/>
    </xsl:apply-templates>
    <xsl:if test='attribution|doc:attribution'>
      <xsl:call-template name='doc:make-paragraph'>
	<xsl:with-param name='style' select='"blockquote-attribution"'/>
	<xsl:with-param name='content'>
          <xsl:call-template name='doc:make-phrase'>
            <xsl:with-param name='content'>
              <xsl:apply-templates select='attribution/node()|doc:attribution/node()'/>
            </xsl:with-param>
          </xsl:call-template>
	</xsl:with-param>
      </xsl:call-template>
    </xsl:if>
  </xsl:template>

  <xsl:template match='literallayout|programlisting|doc:literallayout|doc:programlisting'
    mode='doc:body'>
    <xsl:param name='class'/>

    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style' select='local-name()'/>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match='bridgehead|doc:bridgehead'
    mode='doc:body'>
    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style' select='"bridgehead"'/>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match='itemizedlist|orderedlist |
                       doc:itemizedlist|doc:orderedlist'
    mode='doc:body'>
    <xsl:apply-templates select='listitem|doc:listitem'
      mode='doc:body'/>
  </xsl:template>

  <xsl:template match='listitem|doc:listitem'
    mode='doc:body'>
    <xsl:call-template name='doc:make-paragraph'>
      <xsl:with-param name='style'
		      select='concat(local-name(..), 
			      count(ancestor::itemizedlist|ancestor::orderedlist|ancestor::doc:itemizedlist|ancestor::doc:orderedlist))'/>
      <xsl:with-param name='is-listitem' select='true()'/>

      <xsl:with-param name='content'>
	<!-- Normally a para would be the first child of a listitem -->
	<xsl:apply-templates select='*[1][self::para|self::doc:para]/node()'
          mode='doc:body'/>
      </xsl:with-param>
    </xsl:call-template>

    <!-- This is to catch the case where a listitem's first child is not a paragraph.
       - We may not be able to represent this properly.
      -->
    <xsl:apply-templates select='*[1][not(self::para|self::doc:para)]'
      mode='doc:list-continue'/>

    <xsl:apply-templates select='*[position() != 1]'
      mode='doc:list-continue'/>
  </xsl:template>  

  <xsl:template match='para|doc:para' mode='doc:list-continue'>
    <xsl:apply-templates select='.'
      mode='doc:body'>
      <xsl:with-param name='class' select='"para-continue"'/>
    </xsl:apply-templates>
  </xsl:template>
  <!-- non-paragraph elements in a listitem are rolled back into
       the list item upon conversion.
       -->
  <xsl:template match='*' mode='doc:list-continue'>
    <xsl:apply-templates select='.' mode='doc:body'/>
  </xsl:template>

  <xsl:template match='variablelist|doc:variablelist'
    mode='doc:body'>
    <xsl:apply-templates select='*[not(self::varlistentry|self::doc:varlistentry)]'/>

    <xsl:call-template name='doc:make-table'>
      <xsl:with-param name='columns'>
	<xsl:call-template name='doc:make-column'>
	  <xsl:with-param name='width' select='"1"'/>
	</xsl:call-template>
	<xsl:call-template name='doc:make-column'>
	  <xsl:with-param name='width' select='"3"'/>
	</xsl:call-template>
      </xsl:with-param>
      <xsl:with-param name='rows'>
	<xsl:apply-templates select='varlistentry|doc:varlistentry'/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>
  <xsl:template match='varlistentry|doc:varlistentry'
    mode='doc:body'>
    <xsl:call-template name='doc:make-table-row'>
      <xsl:with-param name='content'>
	<xsl:call-template name='doc:make-table-cell'>
	  <xsl:with-param name='content'>
	    <xsl:call-template name='doc:make-paragraph'>
	      <xsl:with-param name='style' select='"variablelist-term"'/>
	      <xsl:with-param name='content'>
		<xsl:apply-templates select='*[self::term|self::doc:term][1]/node()'
                  mode='doc:body'/>
		<xsl:for-each select='*[self::term|self::doc:term][position() != 1]'>
		  <xsl:call-template name='doc:make-phrase'>
		    <xsl:with-param name='content'>
		      <xsl:call-template name='doc:make-soft-break'/>
		    </xsl:with-param>
		  </xsl:call-template>
		  <xsl:apply-templates/>
		</xsl:for-each>
              </xsl:with-param>
	    </xsl:call-template>
	  </xsl:with-param>
	</xsl:call-template>
	<xsl:call-template name='doc:make-table-cell'>
	  <xsl:with-param name='content'>
            <xsl:apply-templates select='listitem/node()|doc:listitem/node()'
              mode='doc:body'/>
	  </xsl:with-param>
	</xsl:call-template>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <!-- These elements are not displayed.
     - However, they may need to be added (perhaps as hidden text)
     - for round-tripping.
    -->
  <xsl:template match='anchor|areaset|audiodata|audioobject|
                       beginpage|
                       constraint|
                       indexterm|itermset|
                       keywordset|
                       msg |
                       doc:anchor|doc:areaset|doc:audiodata|doc:audioobject|
                       doc:beginpage|
                       doc:constraint|
                       doc:indexterm|doc:itermset|
                       doc:keywordset|
                       doc:msg'
    mode='doc:body'/>

  <xsl:template match='*' name='doc:nomatch'>
    <xsl:param name='node' select='.'/>

    <xsl:message>
      <xsl:value-of select='local-name($node)'/>
      <xsl:if test='namespace-uri($node) != ""'>
        <xsl:text> [</xsl:text>
        <xsl:value-of select='namespace-uri($node)'/>
        <xsl:text>]</xsl:text>
      </xsl:if>
      <xsl:text> encountered</xsl:text>
      <xsl:if test='$node/parent::*'>
        <xsl:text> in </xsl:text>
        <xsl:value-of select='local-name($node/parent::*)'/>
      </xsl:if>
      <xsl:text>, but no template matches.</xsl:text>
    </xsl:message>

    <xsl:for-each select='$node'>
      <xsl:choose>
        <xsl:when test='self::abstract |
                        self::ackno |
                        self::address |
                        self::answer |
                        self::appendix |
                        self::artheader |
                        self::authorgroup |
                        self::bibliodiv |
                        self::biblioentry |
                        self::bibliography |
                        self::bibliomixed |
                        self::bibliomset |
                        self::biblioset |
                        self::bridgehead |
                        self::calloutlist |
                        self::caption |
                        self::classsynopsis |
                        self::colophon |
                        self::constraintdef |
                        self::copyright |
                        self::dedication |
                        self::epigraph |
                        self::equation |
                        self::example |
                        self::figure |
                        self::funcsynopsis |
                        self::glossary |
                        self::glossdef |
                        self::glossdiv |
                        self::glossentry |
                        self::glosslist |
                        self::graphic |
                        self::highlights |
                        self::imageobject |
                        self::imageobjectco |
                        self::index |
                        self::indexdiv |
                        self::indexentry |
                        self::informalequation |
                        self::informalexample |
                        self::informalfigure |
                        self::lot |
                        self::lotentry |
                        self::mediaobject |
                        self::mediaobjectco |
                        self::member |
                        self::msgentry |
                        self::msgset |
                        self::part |
                        self::partintro |
                        self::personblurb |
                        self::preface |
                        self::printhistory |
                        self::procedure |
                        self::programlisting |
                        self::programlistingco |
                        self::publisher |
                        self::qandadiv |
                        self::qandaentry |
                        self::qandaset |
                        self::question |
                        self::refdescriptor |
                        self::refentry |
                        self::refentrytitle |
                        self::reference |
                        self::refmeta |
                        self::refname |
                        self::refnamediv |
                        self::refpurpose |
                        self::refsect1 |
                        self::refsect2 |
                        self::refsect3 |
                        self::refsection |
                        self::refsynopsisdiv |
                        self::screen |
                        self::screenco |
                        self::screenshot |
                        self::seg |
                        self::seglistitem |
                        self::segmentedlist |
                        self::segtitle |
                        self::set |
                        self::setindex |
                        self::sidebar |
                        self::simplelist |
                        self::simplemsgentry |
                        self::step |
                        self::stepalternatives |
                        self::subjectset |
                        self::substeps |
                        self::task |
                        self::textobject |
                        self::toc |
                        self::videodata |
                        self::videoobject |

                        self::doc:abstract |
                        self::doc:ackno |
                        self::doc:address |
                        self::doc:answer |
                        self::doc:appendix |
                        self::doc:artheader |
                        self::doc:authorgroup |
                        self::doc:bibliodiv |
                        self::doc:biblioentry |
                        self::doc:bibliography |
                        self::doc:bibliomixed |
                        self::doc:bibliomset |
                        self::doc:biblioset |
                        self::doc:bridgehead |
                        self::doc:calloutlist |
                        self::doc:caption |
                        self::doc:classsynopsis |
                        self::doc:colophon |
                        self::doc:constraintdef |
                        self::doc:copyright |
                        self::doc:dedication |
                        self::doc:epigraph |
                        self::doc:equation |
                        self::doc:example |
                        self::doc:figure |
                        self::doc:funcsynopsis |
                        self::doc:glossary |
                        self::doc:glossdef |
                        self::doc:glossdiv |
                        self::doc:glossentry |
                        self::doc:glosslist |
                        self::doc:graphic |
                        self::doc:highlights |
                        self::doc:imageobject |
                        self::doc:imageobjectco |
                        self::doc:index |
                        self::doc:indexdiv |
                        self::doc:indexentry |
                        self::doc:informalequation |
                        self::doc:informalexample |
                        self::doc:informalfigure |
                        self::doc:lot |
                        self::doc:lotentry |
                        self::doc:mediaobject |
                        self::doc:mediaobjectco |
                        self::doc:member |
                        self::doc:msgentry |
                        self::doc:msgset |
                        self::doc:part |
                        self::doc:partintro |
                        self::doc:personblurb |
                        self::doc:preface |
                        self::doc:printhistory |
                        self::doc:procedure |
                        self::doc:programlisting |
                        self::doc:programlistingco |
                        self::doc:publisher |
                        self::doc:qandadiv |
                        self::doc:qandaentry |
                        self::doc:qandaset |
                        self::doc:question |
                        self::doc:refdescriptor |
                        self::doc:refentry |
                        self::doc:refentrytitle |
                        self::doc:reference |
                        self::doc:refmeta |
                        self::doc:refname |
                        self::doc:refnamediv |
                        self::doc:refpurpose |
                        self::doc:refsect1 |
                        self::doc:refsect2 |
                        self::doc:refsect3 |
                        self::doc:refsection |
                        self::doc:refsynopsisdiv |
                        self::doc:screen |
                        self::doc:screenco |
                        self::doc:screenshot |
                        self::doc:seg |
                        self::doc:seglistitem |
                        self::doc:segmentedlist |
                        self::doc:segtitle |
                        self::doc:set |
                        self::doc:setindex |
                        self::doc:sidebar |
                        self::doc:simplelist |
                        self::doc:simplemsgentry |
                        self::doc:step |
                        self::doc:stepalternatives |
                        self::doc:subjectset |
                        self::doc:substeps |
                        self::doc:task |
                        self::doc:textobject |
                        self::doc:toc |
                        self::doc:videodata |
                        self::doc:videoobject |

                        self::*[not(starts-with(local-name(), "informal")) and contains(local-name(), "info")]'>
          <xsl:call-template name='doc:make-paragraph'>
            <xsl:with-param name='style' select='"blockerror"'/>
            <xsl:with-param name='content'>
              <xsl:call-template name='doc:make-phrase'>
                <xsl:with-param name='content'>
                  <xsl:value-of select='local-name()'/>
                  <xsl:if test='namespace-uri() != ""'>
                    <xsl:text> [</xsl:text>
                    <xsl:value-of select='namespace-uri()'/>
                    <xsl:text>]</xsl:text>
                  </xsl:if>
                  <xsl:text> encountered</xsl:text>
                  <xsl:if test='parent::*'>
                    <xsl:text> in </xsl:text>
                    <xsl:value-of select='local-name(parent::*)'/>
                  </xsl:if>
                  <xsl:text>, but no template matches.</xsl:text>
                </xsl:with-param>
              </xsl:call-template>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:when>
        <!-- Some elements are sometimes blocks, sometimes inline
             <xsl:when test='self::affiliation |
                             self::alt |
                             self::attribution |
                             self::collab |
                             self::collabname |
                             self::confdates |
                             self::confgroup |
                             self::confnum |
                             self::confsponsor |
                             self::conftitle |
                             self::contractnum |
                             self::contractsponsor |
                             self::contrib |
                             self::corpauthor |
                             self::corpcredit |
                             self::corpname |
                             self::edition |
                             self::editor |
                             self::jobtitle |
                             self::personname |
                             self::publishername |
                             self::remark |

                             self::doc:affiliation |
                             self::doc:alt |
                             self::doc:attribution |
                             self::doc:collab |
                             self::doc:collabname |
                             self::doc:confdates |
                             self::doc:confgroup |
                             self::doc:confnum |
                             self::doc:confsponsor |
                             self::doc:conftitle |
                             self::doc:contractnum |
                             self::doc:contractsponsor |
                             self::doc:contrib |
                             self::doc:corpauthor |
                             self::doc:corpcredit |
                             self::doc:corpname |
                             self::doc:edition |
                             self::doc:editor |
                             self::doc:jobtitle |
                             self::doc:personname |
                             self::doc:publishername |
                             self::doc:remark'>

             </xsl:when>
             -->
        <xsl:otherwise>
          <xsl:call-template name='doc:make-phrase'>
            <xsl:with-param name='style' select='"inlineerror"'/>
            <xsl:with-param name='content'>
              <xsl:value-of select='local-name()'/>
              <xsl:text> encountered</xsl:text>
              <xsl:if test='parent::*'>
                <xsl:text> in </xsl:text>
                <xsl:value-of select='local-name(parent::*)'/>
              </xsl:if>
              <xsl:text>, but no template matches.</xsl:text>
            </xsl:with-param>
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match='*' mode='doc:copy'>
    <xsl:copy>
      <xsl:apply-templates select='@*' mode='doc:copy'/>
      <xsl:apply-templates mode='doc:copy'/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match='@*' mode='doc:copy'>
    <xsl:copy/>
  </xsl:template>

  <!-- Stubs: the importing stylesheet must override these -->

  <!-- stub template for creating a paragraph -->
  <xsl:template name='doc:make-paragraph'>
  </xsl:template>
  <!-- stub template for creating a phrase -->
  <xsl:template name='doc:make-phrase'>
  </xsl:template>

  <!-- stub template for inserting attributes -->
  <xsl:template name='doc:attributes'/>

  <!-- emit a message -->
  <xsl:template name='doc:warning'>
    <xsl:param name='message'/>

    <xsl:message>WARNING: <xsl:value-of select='$message'/></xsl:message>
  </xsl:template>

</xsl:stylesheet>
