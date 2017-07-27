<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"

  xmlns:sfa="http://developer.apple.com/namespaces/sfa"
  xmlns:sf="http://developer.apple.com/namespaces/sf"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:appsl="http://developer.apple.com/namespaces/sl"

  xmlns:dbk='http://docbook.org/ns/docbook'
  xmlns:rnd='http://docbook.org/ns/docbook/roundtrip'
  exclude-result-prefixes='sfa sf xsi appsl'>

  <xsl:output method="xml" indent='yes'/>

  <!-- ********************************************************************
       $Id: pages2normalise.xsl 7637 2008-01-09 20:48:30Z balls $
       ********************************************************************

       This file is part of the XSL DocBook Stylesheet distribution.
       See ../README or http://nwalsh.com/docbook/xsl/ for copyright
       and other information.

       ******************************************************************** -->

  <xsl:strip-space elements='*'/>
  <xsl:preserve-space elements='sf:span'/>

  <xsl:key name='styles'
	   match='sf:paragraphstyle[not(ancestor::appsl:section-prototypes)] |
		  sf:characterstyle[not(ancestor::appsl:section-prototypes)] |
                  sf:table-style'
	   use='@sf:ident|@sfa:ID'/>

  <xsl:key name='ids'
    match='*'
    use='@sfa:ID'/>

  <xsl:template match='appsl:document'>
    <dbk:article>
      <!-- TODO: headers and footers -->
      <xsl:apply-templates select='sf:text-storage'/>
    </dbk:article>
  </xsl:template>

  <xsl:template match='sf:p'>
    <xsl:choose>
      <xsl:when test='sf:attachment-ref and
                      count(*) = count(sf:attachment-ref|sf:br|sf:selection-start|sf:selection-end)'>
        <xsl:apply-templates/>
      </xsl:when>
      <xsl:otherwise>
        <dbk:para>
          <xsl:variable name='style-name'>
            <xsl:call-template name='rnd:find-style'/>
          </xsl:variable>
          <xsl:if test='$style-name != "" and
                        $style-name != "para"'>
            <xsl:attribute name='rnd:style'>
              <xsl:value-of select='$style-name'/>
            </xsl:attribute>
          </xsl:if>

          <xsl:apply-templates/>
        </dbk:para>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='sf:span'>
    <xsl:variable name='style-name'
		  select='key("styles", @sf:style)/self::sf:characterstyle/@sf:name'/>

    <xsl:variable name='char-style'>
      <xsl:call-template name='rnd:find-style'>
        <xsl:with-param name='char-style-name' select='$style-name'/>
      </xsl:call-template>
    </xsl:variable>

    <xsl:choose>
      <xsl:when test='$style-name = "attribute-name"'>
	<xsl:if test='not(preceding-sibling::node()[not(self::text()) or (self::text() and normalize-space() != "")])'>
          <xsl:attribute name='{.}'>
            <xsl:apply-templates select='following-sibling::*[1][self::sf:span]'
              mode='attribute'/>
          </xsl:attribute>
	</xsl:if>
      </xsl:when>
      <xsl:when test='$style-name = "attribute-value"'/>
      <xsl:when test='$style-name = ""'>
        <xsl:apply-templates/>
      </xsl:when>
      <xsl:when test='$char-style = "superscript" or
                      $char-style = "subscript"'>
        <xsl:element name='{$char-style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:apply-templates/>
        </xsl:element>
      </xsl:when>
      <xsl:otherwise>
	<dbk:emphasis>
          <xsl:choose>
            <xsl:when test='$char-style = "emphasis-bold" or
                            $char-style = "emphasis-strong"'>
              <xsl:attribute name='role'>bold</xsl:attribute>
            </xsl:when>
            <xsl:when test='$char-style != "" and
                            $char-style != "emphasis"'>
              <xsl:attribute name='rnd:style'>
                <xsl:value-of select='$char-style'/>
              </xsl:attribute>
            </xsl:when>
          </xsl:choose>

	  <xsl:apply-templates/>
        </dbk:emphasis>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='*' mode='attribute'>
    <xsl:variable name='style-name'
		  select='key("styles", @sf:style)/self::sf:characterstyle/@sf:name'/>

    <xsl:if test='$style-name = "attribute-value"'>
      <xsl:apply-templates/>
    </xsl:if>
  </xsl:template>

  <xsl:template match='sf:br'/>
  <xsl:template match='sf:lnbr|sf:crbr'>
    <xsl:text>&#xa;</xsl:text>
  </xsl:template>
  <xsl:template match='sf:tab'>
    <xsl:text>        </xsl:text>
  </xsl:template>
  <xsl:template match='sf:link'>
    <dbk:ulink url='{@href}'>
      <xsl:apply-templates/>
    </dbk:ulink>
  </xsl:template>

  <xsl:template match='sf:attachment-ref'>
    <xsl:if test='@sf:kind = "tabular-attachment"'>
      <xsl:apply-templates select='key("ids", @sfa:IDREF)'/>
    </xsl:if>
  </xsl:template>

  <xsl:template match='sf:attachment[@sf:kind = "tabular-attachment"]'>
    <xsl:variable name='model'
      select='key("ids", sf:tabular-info/sf:tabular-model-ref/@sfa:IDREF)'/>

    <xsl:variable name='num-cols' select='$model/sf:grid/@sf:numcols'/>
    <xsl:variable name='num-rows' select='$model/sf:grid/@sf:numrows'/>

    <xsl:variable name='border.top'
      select='count($model/sf:grid/sf:horizontal-gridline-styles/*) = 0 or
              not($model/sf:grid/sf:horizontal-gridline-styles/sf:style-run[@sf:gridline-index = "0"])'/>
    <xsl:variable name='border.bottom'
      select='count($model/sf:grid/sf:horizontal-gridline-styles/*) = 0 or
              not($model/sf:grid/sf:horizontal-gridline-styles/sf:style-run[@sf:gridline-index = $num-rows - 1])'/>
    <xsl:variable name='border.left'
      select='count($model/sf:grid/sf:vertical-gridline-styles/*) = 0 or
              not($model/sf:grid/sf:vertical-gridline-styles/sf:style-run[@sf:gridline-index = "0"])'/>
    <xsl:variable name='border.right'
      select='count($model/sf:grid/sf:vertical-gridline-styles/*) = 0 or
              not($model/sf:grid/sf:vertical-gridline-styles/sf:style-run[@sf:gridline-index = $num-cols])'/>

    <xsl:choose>
      <xsl:when test='not($num-rows) or $num-rows = ""'>
        <xsl:message> cannot determine number of rows in table</xsl:message>
        <xsl:comment> cannot determine number of rows in table </xsl:comment>
      </xsl:when>
      <xsl:when test='not($num-cols) or $num-cols = ""'>
        <xsl:message> cannot determine number of columns in table</xsl:message>
        <xsl:comment> cannot determine number of columns in table </xsl:comment>
      </xsl:when>

      <xsl:otherwise>
        <dbk:informaltable>
          <xsl:choose>
            <xsl:when test='$border.top and $border.bottom and
                            $border.left and $border.right'>
              <xsl:attribute name='frame'>all</xsl:attribute>
            </xsl:when>
            <xsl:when test='$border.top and $border.bottom'>
              <xsl:attribute name='frame'>topbot</xsl:attribute>
            </xsl:when>
            <xsl:when test='$border.left and $border.right'>
              <xsl:attribute name='frame'>sides</xsl:attribute>
            </xsl:when>
            <xsl:when test='$border.top'>
              <xsl:attribute name='frame'>top</xsl:attribute>
            </xsl:when>
            <xsl:when test='$border.bottom'>
              <xsl:attribute name='frame'>bottom</xsl:attribute>
            </xsl:when>
          </xsl:choose>
          <dbk:tgroup cols='{$num-cols}'>
            <xsl:apply-templates select='$model/sf:grid/sf:columns/sf:grid-column'
              mode='rnd:colspec'/>
            <xsl:if test='$model/@sf:num-header-rows != 0'>
              <dbk:thead>
                <xsl:call-template name='rnd:make-table-rows'>
                  <xsl:with-param name='nodes'
                    select='$model/sf:grid/sf:datasource/sf:text-cell[@sf:row &lt; $model/@sf:num-header-rows]'/>
                  <xsl:with-param name='num-rows'
                    select='$model/@sf:num-header-rows'/>
                </xsl:call-template>
              </dbk:thead>
            </xsl:if>
            <dbk:tbody>
              <xsl:call-template name='rnd:make-table-rows'>
                <xsl:with-param name='nodes'
                  select='$model/sf:grid/sf:datasource/sf:text-cell[@sf:row >= $model/@sf:num-header-rows and
                  @sf:row &lt; $num-rows - $model/@sf:num-footer-rows]'/>
                <xsl:with-param name='num-rows' select='$num-rows - $model/@sf:num-header-rows - $model/@sf:num-footer-rows'/>
                <xsl:with-param name='row' select='$model/@sf:num-header-rows'/>
              </xsl:call-template>
            </dbk:tbody>
            <xsl:if test='$model/@sf:num-footer-rows != 0'>
              <dbk:tfoot>
                <xsl:call-template name='rnd:make-table-rows'>
                  <xsl:with-param name='nodes'
                    select='$model/sf:grid/sf:datasource/sf:text-cell[@sf:row &gt;= $num-rows - $model/@sf:num-footer-rows]'/>
                  <xsl:with-param name='num-rows'
                    select='$model/@sf:num-footer-rows'/>
                  <xsl:with-param name='row'
                    select='$num-rows - $model/@sf:num-footer-rows'/>
                </xsl:call-template>
              </dbk:tfoot>
            </xsl:if>
          </dbk:tgroup>
        </dbk:informaltable>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='sf:grid-column' mode='rnd:colspec'>
    <dbk:colspec colwidth='{@sf:width}'
      colname='column-{count(preceding-sibling::sf:grid-column) + 1}'/>
  </xsl:template>
  <xsl:template name='rnd:make-table-rows'>
    <xsl:param name='num-rows' select='0'/>
    <xsl:param name='nodes' select='/..'/>
    <xsl:param name='row' select='0'/>

    <xsl:choose>
      <xsl:when test='not($nodes) and $num-rows != 0'>
        <xsl:message>WARNING: insufficient table cells</xsl:message>
        <xsl:comment> WARNING: insufficient table cells (num-rows <xsl:value-of select='$num-rows'/>, row <xsl:value-of select='$row'/>) </xsl:comment>
      </xsl:when>
      <xsl:when test='$nodes and $num-rows = 0'>
        <xsl:message>WARNING: excess table cells</xsl:message>
        <xsl:comment> WARNING: excess table cells (num-rows <xsl:value-of select='$num-rows'/>, row <xsl:value-of select='$row'/>) </xsl:comment>
      </xsl:when>
      <xsl:when test='not($nodes)'/>
      <xsl:when test='$num-rows = 0'/>

      <xsl:otherwise>
        <dbk:row>
          <xsl:apply-templates select='$nodes[@sf:row = $row]'/>
        </dbk:row>
        <xsl:call-template name='rnd:make-table-rows'>
          <xsl:with-param name='num-rows'
            select='$num-rows - 1'/>
          <xsl:with-param name='row'
            select='$row + 1'/>
          <xsl:with-param name='nodes'
            select='$nodes[@sf:row != $row]'/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='sf:text-cell'>
    <dbk:entry>
      <!-- Does this cell have no bottom border? -->
      <xsl:variable name='horiz'
        select='ancestor::sf:grid/sf:horizontal-gridline-styles'/>
      <xsl:if test='not($horiz/*) or
                    not($horiz/sf:style-run[@sf:gridline-index = current()/@sf:row + 1]/sf:vector-style-ref[@sf:start-index &lt;= current()/@sf:col and @sf:stop-index >= current()/@sf:col])'>
        <xsl:attribute name='rowsep'>1</xsl:attribute>
      </xsl:if>
      <!-- Does this cell have no right border? -->
      <xsl:variable name='vert'
        select='ancestor::sf:grid/sf:vertical-gridline-styles'/>
      <xsl:if test='not($vert/*) or
                    not($vert/sf:style-run[@sf:gridline-index = current()/@sf:col + 1]/sf:vector-style-ref[@sf:start-index &lt;= current()/@sf:row and @sf:stop-index >= current()/@sf:row])'>
        <xsl:attribute name='colsep'>1</xsl:attribute>
      </xsl:if>

      <xsl:choose>
        <xsl:when test='sf:cell-text/@sfa:string'>
          <dbk:para>
            <xsl:apply-templates select='sf:cell-text/@sfa:string'/>
          </dbk:para>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select='sf:cell-text/sf:cell-storage/sf:text-body/*'/>
        </xsl:otherwise>
      </xsl:choose>
    </dbk:entry>
  </xsl:template>
  <xsl:template match='sf:tableAttachmentTable |
                       sf:tableModelCells'>
    <xsl:apply-templates/>
  </xsl:template>
  <xsl:template match='sf:tableModelPartitionSource |
                       sf:tableModelStyle-ref |
                       sf:tableModelVectors |
                       sf:tableCellArrayCellsByColumn |
                       sf:tableModelTableID'/>

  <xsl:template match='sf:text-storage |
                       sf:text-body |
                       sf:section |
                       sf:layout'>
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match='sf:stylesheet|sf:stylesheet-ref |
		       sf:container-hint |
		       sf:page-start|sf:br |
		       sf:selection-start|sf:selection-end |
		       sf:insertion-point |
		       sf:ghost-text |
                       sf:attachments'/>

  <xsl:template match='*'>
    <xsl:message>element "<xsl:value-of select='name()'/>" not handled</xsl:message>
  </xsl:template>

  <xsl:template name='rnd:find-style'>
    <xsl:param name='ident' select='@sf:style'/>
    <xsl:param name='para-style-name'
	       select='key("styles", $ident)/self::sf:paragraphstyle/@sf:name'/>
    <xsl:param name='char-style-name'
	       select='key("styles", $ident)/self::sf:characterstyle/@sf:name'/>

    <xsl:choose>
      <xsl:when test='$ident = "paragraph-style-default"'/>
      <xsl:when test='$para-style-name != ""'>
	<xsl:value-of select='$para-style-name'/>
      </xsl:when>
      <xsl:when test='key("styles", $ident)/self::sf:characterstyle/sf:property-map/sf:superscript/sf:number/@sfa:number = "1"'>superscript</xsl:when>
      <xsl:when test='key("styles", $ident)/self::sf:characterstyle/sf:property-map/sf:subscript/sf:number/@sfa:number = "1"'>subscript</xsl:when>
      <xsl:when test='$char-style-name != "" or
		      key("styles", $ident)/self::sf:characterstyle/sf:property-map/*'>
	<xsl:value-of select='$char-style-name'/>
      </xsl:when>
    </xsl:choose>
  </xsl:template>
</xsl:stylesheet>
