<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:sfa="http://developer.apple.com/namespaces/sfa"
  xmlns:sf="http://developer.apple.com/namespaces/sf"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:sl="http://developer.apple.com/namespaces/sl"
  xmlns:xi="http://www.w3.org/2001/XInclude"
  xmlns:doc='http://docbook.org/ns/docbook'
  xmlns:rnd='http://docbook.org/ns/docbook/roundtrip'
  exclude-result-prefixes='doc rnd xi'>

  <xsl:import href='dbk2wp.xsl'/>

  <xsl:output method="xml" indent='yes' encoding='ascii'/>

  <!-- ********************************************************************
       $Id: dbk2pages.xsl 9396 2012-06-02 21:56:19Z bobstayton $
       ********************************************************************

       This file is part of the XSL DocBook Stylesheet distribution.
       See ../README or http://docbook.sf.net/release/xsl/current/ for
       copyright and other information.

       ******************************************************************** -->

  <xsl:include href='../VERSION.xsl'/>
  <xsl:include href='param.xsl'/>

  <xsl:variable name='templatedoc' select='document($pages.template)'/>

  <!-- Find all tables in the document once,
          as we will be iterating over them in more than one place.
      -->
  <xsl:variable name='rnd:pages-tables'
    select='//doc:table|//doc:informaltable'/>

  <!-- Lookup style identifiers from their user-visible name -->
  <xsl:variable name='paragraph-styles'
		select='$templatedoc//sf:paragraphstyle'/>
  <xsl:variable name='character-styles'
		select='$templatedoc//sf:characterstyle'/>

  <xsl:template match="/" name='pages.top'>
    <xsl:param name='doc' select='/'/>

    <xsl:if test='not($pages.template)'>
      <xsl:message terminate='yes'>Please specify the template document with the "pages.template" parameter</xsl:message>
    </xsl:if>
    <xsl:if test='not($templatedoc)'>
      <xsl:message terminate='yes'>Unable to open template document "<xsl:value-of select='$pages.template'/>"</xsl:message>
    </xsl:if>

    <sl:document
      sfa:ID="SLPublicationModel-0"
      sl:version="72007061400"
      sl:generator="slingshot"
      sl:app_build_date="Sep 26 2007, 14:46:11">

      <xsl:apply-templates select='$templatedoc/sl:document/*[not(self::sf:text-storage)]'
        mode='doc:copy'/>

      <xsl:apply-templates select='$doc/*'
        mode='doc:toplevel'/>

      <xsl:apply-templates select='$templatedoc/sl:document/sf:text-storage/following-sibling::*'
        mode='doc:copy'/>
    </sl:document>
  </xsl:template>

  <xsl:template match='sf:calc-engine' mode='doc:copy'>
    <xsl:copy>
      <xsl:apply-templates select='@*' mode='doc:copy'/>

      <xsl:choose>
        <xsl:when test='not(sf:calc-engine-entities)'>
          <xsl:call-template name='rnd:pages-make-calc-engine-entities'/>
          <xsl:apply-templates mode='doc:copy'/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates mode='doc:copy'/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:copy>
  </xsl:template>
  <xsl:template name='rnd:pages-make-calc-engine-entities'
    match='sf:calc-engine-entities' mode='doc:copy'>
    <sf:calc-engine-entities>
      <xsl:choose>
        <xsl:when test='self::calc-engine-entities'>
          <xsl:apply-templates select='@*' mode='doc:copy'/>
          <xsl:apply-templates mode='doc:copy'/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:attribute name='sfa:ID'>
            <xsl:text>DocBookRoundtrip-1</xsl:text>
          </xsl:attribute>
        </xsl:otherwise>
      </xsl:choose>

      <xsl:for-each select='$rnd:pages-tables'>
        <xsl:variable name='num' select='position()'/>

        <xsl:variable name='num-cells'
          select='count(doc:tgroup/*/doc:row/doc:entry)'/>
        <xsl:variable name='num-rows' select='count(doc:tgroup/*/doc:row)'/>
        <xsl:variable name='num-cols' select='$num-cells div $num-rows'/>

        <sf:tabular-model sfa:ID='SFTTableModel-{$num}' sf:name='Unnamed Table'
          sfa:id=''
          sf:num-header-rows='{count(doc:tgroup/doc:thead/doc:row)}' sf:num-footer-rows='{count(doc:tgroup/doc:tfoot/doc:row)}'
          sf:num-header-columns='0' sf:name-is-visible='false' sf:grouping-enabled='false'>
          <sf:grid sfa:ID='SFTTableGrid-{$num}'
            sf:ocnt='{$num-cells}'
            sf:numcols='{$num-cols}'
            sf:numrows='{$num-rows}'
            sf:hiddennumcols='0' sf:hiddennumrows='0'>
            <sf:columns sf:count='{$num-cols}'>
              <xsl:apply-templates select='doc:tgroup/doc:colspec'
                mode='rnd:pages-table-colspec'/>
            </sf:columns>
            <sf:vertical-gridline-styles sf:array-size='0'/> <!-- TODO: borders -->
            <sf:rows sf:count='{$num-rows}'>
              <xsl:call-template name='rnd:pages-make-table-rowspecs'>
                <xsl:with-param name='number' select='$num-rows'/>
              </xsl:call-template>
            </sf:rows>
            <sf:horizontal-gridline-styles sf:array-size='0'/> <!-- TODO: borders -->
            <sf:datasource sfa:ID='SFTConcreteTableDataSource-{$num}'>
              <xsl:apply-templates select='doc:tgroup/doc:thead/doc:row'
                mode='rnd:pages-table-data'>
                <xsl:with-param name='start-row' select='0'/>
              </xsl:apply-templates>
              <xsl:apply-templates select='doc:tgroup/doc:tbody/doc:row'
                mode='rnd:pages-table-data'>
                <xsl:with-param name='start-row' select='count(doc:tgroup/doc:thead/doc:row)'/>
              </xsl:apply-templates>
              <xsl:apply-templates select='doc:tgroup/doc:tfoot/doc:row'
                mode='rnd:pages-table-data'>
                <xsl:with-param name='start-row' select='count(doc:tgroup/doc:thead/doc:row) + count(doc:tgroup/doc:tbody/doc:row)'/>
              </xsl:apply-templates>
            </sf:datasource>
            <sf:sort sfa:ID='DocBookPagesTableNSArray-{$num}'>
              <sf:sort-spec sfa:ID='SFTTableSortSpec-{$num}'
                sf:sort-col='0' sf:sort-order='true'/>
            </sf:sort>
            <sf:filterset sfa:ID='SFTTableFilterSet-{$num}'
              sf:type='0' sf:enabled='false' sf:spec-count='1'>
              <sf:filterspec sfa:ID='SFTTableFilterSpec-{$num}'
                sf:filtercol='0' sf:predicate='1' sf:keyscale='0' sf:key1=''/>
            </sf:filterset>
            <sf:cell-comment-mapping sfa:ID='DocBookPagesTableCellCommentNSMutableDictionary-{$num}'/>
            <sf:error_warning_mapping sfa:ID='DocBookPagesTableErrorWarningNSMutableDictionary-{$num}'/>
          </sf:grid>
        </sf:tabular-model>
      </xsl:for-each>
    </sf:calc-engine-entities>
  </xsl:template>

  <xsl:template match='doc:row' mode='rnd:pages-table-data'>
    <xsl:param name='start-row' select='0'/>
    <xsl:apply-templates mode='rnd:pages-table-data'>
      <xsl:with-param name='row'
        select='$start-row + count(preceding-sibling::doc:row)'/>
    </xsl:apply-templates>
  </xsl:template>
  <xsl:template match='doc:entry' mode='rnd:pages-table-data'>
    <xsl:param name='row' select='0'/>
    <sf:text-cell sf:flags='4' sf:col='{count(preceding-sibling::doc:entry)}' sf:row='{$row}'>
      <sf:cell-style-ref sfa:IDREF='SFTCellStyle-3'/>
      <sf:content-size sfa:w='60' sfa:h='25'/>
      <sf:cell-text>
        <xsl:choose>
          <xsl:when test='count(.//*) > 2'>
            <sf:cell-storage>
              <sf:stylesheet-ref sfa:IDREF="SFSStylesheet-1"/>
              <sf:text-body>
                <xsl:apply-templates/>
              </sf:text-body>
            </sf:cell-storage>
          </xsl:when>
          <xsl:otherwise>
            <xsl:attribute name='sfa:string'>
              <xsl:apply-templates mode='rnd:pages-table-text'/>
            </xsl:attribute>
          </xsl:otherwise>
        </xsl:choose>
      </sf:cell-text>
    </sf:text-cell>
  </xsl:template>

  <xsl:template name='rnd:pages-make-table-rowspecs'>
    <xsl:param name='number' select='0'/>

    <xsl:choose>
      <xsl:when test='$number &lt;= 0'/>
      <xsl:otherwise>
        <sf:grid-row sf:height='25' sf:fitting-height='25'/>
        <xsl:call-template name='rnd:pages-make-table-rowspecs'>
          <xsl:with-param name='number' select='$number - 1'/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='doc:colspec' mode='rnd:pages-table-colspec'>
    <sf:grid-column sf:width='{@colwidth}' sf:preferred-width='{@colwidth}'
      sf:fitting-width='{@colwidth}'/>
  </xsl:template>

  <xsl:template name='doc:make-body'>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <sf:text-storage sf:kind='body' sfa:ID='SFWPStorage-7'>
      <sf:stylesheet-ref sfa:IDREF='SFSStylesheet-1'/>

      <xsl:if test='$rnd:pages-tables'>
        <sf:attachments>
          <xsl:for-each select='$rnd:pages-tables'>
            <xsl:variable name='num' select='position()'/>

            <sf:attachment sfa:ID='SFTTableAttachment-{$num}'
              sfa:sfclass='' sf:kind='tabular-attachment'>
              <sf:tabular-info sfa:ID='SFTTableInfo-{$num}'>
                <sf:geometry sfa:ID='DocBookAffineGeometry-{$num}'
                  sf:sizesLocked='true'>
                  <sf:naturalSize sfa:w='480' sfa:h='126'/>
                  <sf:size sfa:w='480' sfa:h='126'/>
                  <sf:position sfa:x='0' sfa:y='0'/>
                </sf:geometry>
                <sf:style>
                  <sf:tabular-style-ref sfa:IDREF='SFTTableStyle-0'/>
                </sf:style>
                <sf:tabular-model-ref sfa:IDREF='SFTTableModel-{$num}'/>
              </sf:tabular-info>
              <sf:position sfa:x='0' sfa:y='0'/>
            </sf:attachment>

          </xsl:for-each>
        </sf:attachments>
      </xsl:if>

      <sf:text-body>
        <sf:page-start sf:page-index='0'/>
        <sf:container-hint sf:page-index="0" sf:cindex="0" sf:sindex="0" sf:lindex="0" sf:frame-x="56.692913055419922" sf:frame-y="56.692913055419922" sf:frame-w="481.61416625976562" sf:frame-h="714" sf:anchor-loc="0"/>

        <sf:section sf:name="Chapter 1" sf:style="section-style-0">
          <sf:layout sf:style="layout-style-20">
            <xsl:copy-of select='$content'/>
          </sf:layout>
        </sf:section>
      </sf:text-body>
    </sf:text-storage>
  </xsl:template>

  <xsl:template name='doc:make-subsection'>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <xsl:copy-of select='$content'/>
  </xsl:template>

  <xsl:template name='doc:make-paragraph'>
    <xsl:param name='style' select='"unknown"'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>
    <xsl:param name='outline.level' select='0'/>
    <xsl:param name='attributes.node' select='.'/>

    <sf:p>
      <xsl:if test='$style != ""'>
	<xsl:attribute name='sf:style'>
          <xsl:call-template name='doc:lookup-paragraph-style'>
            <xsl:with-param name='style' select='$style'/>
          </xsl:call-template>
        </xsl:attribute>
      </xsl:if>

      <xsl:call-template name='attributes'>
        <xsl:with-param name='node' select='$attributes.node'/>
      </xsl:call-template>

      <xsl:copy-of select='$content'/>
      <sf:br/>
    </sf:p>
  </xsl:template>

  <xsl:template name='doc:make-phrase'>
    <xsl:param name='style' select='""'/>
    <xsl:param name='italic' select='0'/>
    <xsl:param name='bold' select='0'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:phrase'/>
    </xsl:param>

    <!-- TODO: handle italic and bold parameters -->

    <xsl:choose>
      <xsl:when test='$style != ""'>
        <sf:span>
          <xsl:attribute name='sf:style'>
            <xsl:call-template name='doc:lookup-character-style'>
              <xsl:with-param name='style' select='$style'/>
            </xsl:call-template>
          </xsl:attribute>

          <xsl:copy-of select='$content'/>
        </sf:span>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy-of select='$content'/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name='doc:make-hyperlink'>
    <xsl:param name='target'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <sf:link href='{$target}'>
      <sf:span>
        <xsl:attribute name='sf:style'>
          <xsl:call-template name='doc:lookup-character-style'>
            <xsl:with-param name='style'>email</xsl:with-param>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:copy-of select='$content'/>
      </sf:span>
    </sf:link>
  </xsl:template>

  <xsl:template name='doc:make-table'>
    <xsl:param name='columns'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <xsl:variable name='this' select='.'/>

    <sf:p>
      <xsl:attribute name='sf:style'>
        <xsl:call-template name='doc:lookup-paragraph-style'>
          <xsl:with-param name='style' select='"para"'/>
        </xsl:call-template>
      </xsl:attribute>

      <sf:attachment-ref sf:kind='tabular-attachment'>
        <xsl:attribute name='sfa:IDREF'>
          <xsl:text>SFTTableAttachment-</xsl:text>
          <xsl:for-each select='$rnd:pages-tables'>
            <xsl:if test='generate-id() = generate-id($this)'>
              <xsl:value-of select='position()'/>
            </xsl:if>
          </xsl:for-each>
        </xsl:attribute>
      </sf:attachment-ref>
      <sf:br/>
    </sf:p>
  </xsl:template>

  <xsl:template name='doc:make-column'>
    <xsl:param name='width' select='0'/>
  </xsl:template>

  <xsl:template name='doc:make-table-row'>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>
    <xsl:param name='is-header' select='false()'/>
  </xsl:template>

  <xsl:template name='doc:make-table-cell'>
    <xsl:param name='width' select='0'/>
    <xsl:param name='hidden' select='false()'/>
    <xsl:param name='rowspan' select='1'/>
    <xsl:param name='colspan' select='1'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>
  </xsl:template>

  <xsl:template name='doc:make-soft-break'>
    <sf:br/>
  </xsl:template>

  <xsl:template name='attributes'>
    <xsl:param name='node' select='.'/>

    <xsl:for-each select='$node/@*'>
      <sf:span>
	<xsl:attribute name='sf:style'>
	  <xsl:call-template name='doc:lookup-character-style'>
	    <xsl:with-param name='style'>attribute-name</xsl:with-param>
	  </xsl:call-template>
	</xsl:attribute>
	<xsl:value-of select='name()'/>
      </sf:span>
      <sf:span>
	<xsl:attribute name='sf:style'>
	  <xsl:call-template name='doc:lookup-character-style'>
	    <xsl:with-param name='style'>attribute-value</xsl:with-param>
	  </xsl:call-template>
	</xsl:attribute>
	<xsl:value-of select='.'/>
      </sf:span>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name='doc:lookup-paragraph-style'>
    <xsl:param name='style'/>

    <xsl:variable name='style.cooked'>
      <xsl:choose>
        <xsl:when test='$style = "Normal"'>para</xsl:when>
        <xsl:otherwise>
          <xsl:value-of select='$style'/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:if test='not($paragraph-styles[@sf:name = $style.cooked])'>
      <xsl:message>unable to find paragraph style "<xsl:value-of select='$style.cooked'/>"</xsl:message>
    </xsl:if>

    <xsl:value-of select='$paragraph-styles[@sf:name = $style.cooked]/@sf:ident'/>
  </xsl:template>
  <xsl:template name='doc:lookup-character-style'>
    <xsl:param name='style'/>

    <xsl:if test='not($character-styles[@sf:name = $style])'>
      <xsl:message>unable to find character style "<xsl:value-of select='$style'/>"</xsl:message>
    </xsl:if>

    <xsl:value-of select='$character-styles[@sf:name = $style]/@sf:ident'/>
  </xsl:template>
</xsl:stylesheet>
