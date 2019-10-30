<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:w='http://schemas.microsoft.com/office/word/2003/wordml'
  xmlns:v='urn:schemas-microsoft-com:vml'
  xmlns:w10="urn:schemas-microsoft-com:office:word"
  xmlns:aml="http://schemas.microsoft.com/aml/2001/core"
  xmlns:wx='http://schemas.microsoft.com/office/word/2003/auxHint'
  xmlns:o="urn:schemas-microsoft-com:office:office"
  xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"
  xmlns:sl='http://schemas.microsoft.com/schemaLibrary/2003/core'
  xmlns:doc='http://docbook.org/ns/docbook'
  exclude-result-prefixes='doc'>

  <xsl:import href='dbk2wp.xsl'/>

  <xsl:output method="xml" indent='yes' standalone='yes' encoding='UTF-8'/>

  <!-- ********************************************************************
       $Id: dbk2wordml.xsl 9651 2012-10-26 20:44:10Z bobstayton $
       ********************************************************************

       This file is part of the XSL DocBook Stylesheet distribution.
       See ../README or http://docbook.sf.net/release/xsl/current/ for
       copyright and other information.

       ******************************************************************** -->

  <xsl:include href='../VERSION.xsl'/>
  <xsl:include href='param.xsl'/>

  <xsl:strip-space elements='*'/>
  <xsl:preserve-space elements='literallayout doc:literallayout
                                programlisting doc:programlisting'/>

  <xsl:variable name='templatedoc' select='document($wordml.template)'/>

  <xsl:template match="/" name='wordml.top'>
    <xsl:param name='doc' select='/'/>

    <xsl:if test='not($wordml.template)'>
      <xsl:message terminate='yes'>Please specify the template document with the "wordml.template" parameter</xsl:message>
    </xsl:if>
    <xsl:if test='not($templatedoc)'>
      <xsl:message terminate='yes'>Unable to open template document "<xsl:value-of select='$wordml.template'/>"</xsl:message>
    </xsl:if>

    <xsl:processing-instruction name='mso-application'>
      <xsl:text>progid="Word.Document"</xsl:text>
    </xsl:processing-instruction>
    <xsl:text>&#xa;</xsl:text>

    <xsl:variable name='info'
      select='$doc/book/bookinfo|$doc/article/articleinfo'/>
    <xsl:variable name='authors' select='$info/author|$info/authorinitials|$info/authorgroup/author|$info/authorgroup/editor'/>

    <w:wordDocument
      w:macrosPresent="no" w:embeddedObjPresent="no" w:ocxPresent="no">
      <xsl:attribute name='xml:space'>preserve</xsl:attribute>

      <o:DocumentProperties>
        <o:Author>
          <xsl:choose>
            <xsl:when test='$authors'>
              <xsl:variable name="content">
                <xsl:apply-templates select='$authors[1]' mode='doc:docprop.author'/>
              </xsl:variable>
              <xsl:value-of select="$content"/>
            </xsl:when>
            <xsl:otherwise>Unknown</xsl:otherwise>
          </xsl:choose>
        </o:Author>
        <o:LastAuthor>
          <xsl:variable name="content">
            <xsl:choose>
              <xsl:when test='$info/revhistory/revision[1]/*[self::author|self::authorinitials]'>
                <xsl:apply-templates select='$info/revhistory/revision[1]/*[self::author|self::authorinitials]' mode='doc:docprop.author'/>
              </xsl:when>
              <xsl:when test='$authors'>
                <xsl:apply-templates select='$authors[1]' mode='doc:docprop.author'/>
              </xsl:when>
              <xsl:otherwise>Unknown</xsl:otherwise>
            </xsl:choose>
          </xsl:variable>
          <xsl:value-of select="$content"/>
        </o:LastAuthor>
        <o:Revision>1</o:Revision>
        <o:TotalTime></o:TotalTime>

        <!-- dummy values -->
        <o:Created>2004-01-01T07:07:00Z</o:Created>
        <o:LastSaved>2004-01-01T08:08:00Z</o:LastSaved>

        <o:Pages>1</o:Pages>
        <o:Words>1</o:Words>
        <o:Characters>1</o:Characters>

        <!-- could derive this from author -->
        <o:Company>DocBook</o:Company>

        <o:Lines>1</o:Lines>
        <o:Paragraphs>1</o:Paragraphs>
        <o:CharactersWithSpaces>1</o:CharactersWithSpaces>
        <o:Version>11.6113</o:Version>
      </o:DocumentProperties>

      <xsl:apply-templates select='$templatedoc/w:wordDocument/o:CustomDocumentProperties|$templatedoc/w:wordDocument/w:fonts|$templatedoc/w:wordDocument/w:lists|$templatedoc/w:wordDocument/w:styles' mode='doc:copy'/>

      <w:docPr>
        <w:view w:val="print"/>
        <w:zoom w:percent="100"/>
        <w:doNotEmbedSystemFonts/>
        <w:attachedTemplate w:val=""/>
        <w:documentProtection w:formatting='on' w:enforcement='on'
          w:unprotectPassword='CAA7FF77'/>
        <w:defaultTabStop w:val="720"/>
        <w:autoHyphenation/>
        <w:hyphenationZone w:val="357"/>
        <w:doNotHyphenateCaps/>
        <w:evenAndOddHeaders/>
        <w:characterSpacingControl w:val="DontCompress"/>
        <w:optimizeForBrowser/>
        <w:validateAgainstSchema/>
        <w:saveInvalidXML w:val="off"/>
        <w:ignoreMixedContent w:val="off"/>
        <w:alwaysShowPlaceholderText w:val="off"/>
        <w:footnotePr>
          <w:footnote w:type="separator">
            <w:p>
              <w:r>
                <w:separator/>
              </w:r>
            </w:p>
          </w:footnote>
          <w:footnote w:type="continuation-separator">
            <w:p>
              <w:r>
                <w:continuationSeparator/>
              </w:r>
            </w:p>
          </w:footnote>
        </w:footnotePr>
        <w:endnotePr>
          <w:endnote w:type="separator">
            <w:p>
              <w:r>
                <w:separator/>
              </w:r>
            </w:p>
          </w:endnote>
          <w:endnote w:type="continuation-separator">
            <w:p>
              <w:r>
                <w:continuationSeparator/>
              </w:r>
            </w:p>
          </w:endnote>
        </w:endnotePr>
        <w:compat>
          <w:breakWrappedTables/>
          <w:snapToGridInCell/>
          <w:wrapTextWithPunct/>
          <w:useAsianBreakRules/>
          <w:useWord2002TableStyleRules/>
        </w:compat>
        <w:docVars>
        </w:docVars>
      </w:docPr>

      <xsl:apply-templates select='$doc/*' mode='doc:toplevel'/>

    </w:wordDocument>
  </xsl:template>

  <xsl:template name='doc:make-body'>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <w:body>
      <wx:sect>
        <wx:sub-section>
          <xsl:copy-of select='$content'/>
        </wx:sub-section>
      </wx:sect>
    </w:body>
  </xsl:template>

  <xsl:template name='doc:make-subsection'>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <wx:sub-section>
      <xsl:copy-of select='$content'/>
    </wx:sub-section>
  </xsl:template>

  <xsl:template name='doc:make-paragraph'>
    <xsl:param name='style' select='"unknown"'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>
    <xsl:param name='outline.level' select='0'/>
    <xsl:param name='attributes.node' select='.'/>

    <w:p>
      <xsl:if test='$style != "" or
		    $outline.level != 0'>
	<w:pPr>
	  <xsl:if test='$style != ""'>
            <w:pStyle w:val='{$style}'/>
	  </xsl:if>

	  <xsl:if test='$outline.level != 0'>
            <w:outlineLvl w:val='{$outline.level}'/>
	  </xsl:if>
	</w:pPr>
      </xsl:if>

      <xsl:call-template name='attributes'>
        <xsl:with-param name='node' select='$attributes.node'/>
      </xsl:call-template>

      <xsl:copy-of select='$content'/>
    </w:p>
  </xsl:template>

  <xsl:template name='doc:make-phrase'>
    <xsl:param name='style' select='""'/>
    <xsl:param name='italic' select='0'/>
    <xsl:param name='bold' select='0'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:phrase'/>
    </xsl:param>

    <w:r>
      <xsl:if test='$style != "" or
                    $bold = 1 or
                    $italic = 1'>
	<w:rPr>
          <xsl:if test='$style != ""'>
            <w:rStyle w:val='{$style}'/>
          </xsl:if>
          <xsl:if test='$italic = 1'>
            <w:i/>
          </xsl:if>
          <xsl:if test='$bold = 1'>
            <w:b/>
          </xsl:if>
	</w:rPr>
      </xsl:if>

      <w:t>
        <xsl:copy-of select='$content'/>
      </w:t>
    </w:r>
  </xsl:template>

  <xsl:template name='doc:make-hyperlink'>
    <xsl:param name='target'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <w:hlink w:dest='{$target}'>
      <xsl:copy-of select='$content'/>
    </w:hlink>
  </xsl:template>

  <xsl:template name='doc:make-table'>
    <xsl:param name='columns'/>
    <xsl:param name='content'>
      <xsl:apply-templates select='*[not(self::caption|self::doc:caption|self::textobject|self::doc:textobject)]'
        mode='doc:body'/>
    </xsl:param>

    <w:tbl>
      <w:tblPr>
        <w:tblW w:w="0" w:type="auto"/>
        <w:tblInd w:w="108" w:type="dxa"/>
        <w:tblLayout w:type="Fixed"/>
      </w:tblPr>
      <w:tblGrid>
        <xsl:copy-of select='$columns'/>
      </w:tblGrid>
      <xsl:copy-of select='$content'/>
    </w:tbl>
  </xsl:template>

  <xsl:template name='doc:make-column'>
    <xsl:param name='width' select='0'/>

    <w:gridcol w:w='{$width}'/>
  </xsl:template>

  <xsl:template name='doc:make-table-row'>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>
    <xsl:param name='is-header' select='false()'/>

    <w:tr>
      <w:trPr>
        <xsl:if test='$is-header'>
          <w:tblHeader/>
        </xsl:if>
      </w:trPr>
      <xsl:copy-of select='$content'/>
    </w:tr>
  </xsl:template>

  <xsl:template name='doc:make-table-cell'>
    <xsl:param name='width' select='0'/>
    <xsl:param name='hidden' select='false()'/>
    <xsl:param name='rowspan' select='1'/>
    <xsl:param name='colspan' select='1'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <w:tc>
      <xsl:if test='$colspan != 1 or
                    $width != 0'>
        <w:tcPr>
          <xsl:if test='$colspan != 1 or
                        $width != 0'>
            <w:tcW w:w='{$width}' w:type='dxa'/>
          </xsl:if>
          <xsl:if test='$hidden'>
            <w:vmerge w:val='{$hidden}'/>
          </xsl:if>
          <xsl:if test='$rowspan != 1'>          
            <w:vmerge w:val='restart'/>
          </xsl:if>
          <xsl:if test='$colspan != 1'>
            <w:gridspan w:val='{$colspan}'/>
          </xsl:if>
        </w:tcPr>
      </xsl:if>

      <xsl:copy-of select='$content'/>
    </w:tc>
  </xsl:template>

  <xsl:template name='doc:make-soft-break'>
    <w:br/>
  </xsl:template>

  <xsl:template name='attributes'>
    <xsl:param name='node' select='.'/>

    <xsl:if test='$node/@*'>
      <aml:annotation aml:id='{count(preceding::*) + 1}' w:type='Word.Comment.Start'/>
      <w:r>
        <w:rPr>
          <w:rStyle w:val='attributes'/>
        </w:rPr>
        <w:t>
          <xsl:text> </xsl:text>
        </w:t>
      </w:r>
      <aml:annotation aml:id='{count(preceding::*) + 1}' w:type='Word.Comment.End'/>
      <w:r>
        <w:rPr>
          <w:rStyle w:val='CommentReference'/>
        </w:rPr>
        <aml:annotation aml:id='{count(preceding::*) + 1}' aml:author="DocBook" aml:createdate='2004-12-23T00:01:00' w:type='Word.Comment' w:initials='DBK'>
          <aml:content>
            <w:p>
              <w:pPr>
                <w:pStyle w:val='CommentText'/>
              </w:pPr>
              <w:r>
                <w:rPr>
                  <w:rStyle w:val='CommentReference'/>
                </w:rPr>
                <w:annotationRef/>
              </w:r>
              <xsl:for-each select='$node/@*'>
                <w:r>
                  <w:rPr>
                    <w:rStyle w:val='attribute-name'/>
                  </w:rPr>
                  <w:t>
                    <xsl:value-of select='name()'/>
                  </w:t>
                </w:r>
                <w:r>
                  <w:t>=</w:t>
                </w:r>
                <w:r>
                  <w:rPr>
                    <w:rStyle w:val='attribute-value'/>
                  </w:rPr>
                  <w:t>
                    <xsl:value-of select='.'/>
                  </w:t>
                </w:r>
              </xsl:for-each>
            </w:p>
          </aml:content>
        </aml:annotation>
      </w:r>
    </xsl:if>
  </xsl:template>

</xsl:stylesheet>
