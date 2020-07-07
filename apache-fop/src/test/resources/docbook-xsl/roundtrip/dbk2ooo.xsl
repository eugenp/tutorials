<xsl:stylesheet version="1.0"
  xmlns:xsl='http://www.w3.org/1999/XSL/Transform'
  xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
  xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
  xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
  xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
  xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
  xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
  xmlns:xlink="http://www.w3.org/1999/xlink"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
  xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0"
  xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
  xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0"
  xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0"
  xmlns:math="http://www.w3.org/1998/Math/MathML"
  xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0"
  xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0"
  xmlns:ooo="http://openoffice.org/2004/office"
  xmlns:ooow="http://openoffice.org/2004/writer"
  xmlns:oooc="http://openoffice.org/2004/calc"
  xmlns:dom="http://www.w3.org/2001/xml-events"
  xmlns:xforms="http://www.w3.org/2002/xforms"
  xmlns:xsd="http://www.w3.org/2001/XMLSchema"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:doc='http://docbook.org/ns/docbook'
  exclude-result-prefixes='doc'>

  <xsl:import href='dbk2wp.xsl'/>

  <xsl:output method="xml" indent='yes'/>

  <!-- ********************************************************************
       $Id: dbk2ooo.xsl 9396 2012-06-02 21:56:19Z bobstayton $
       ********************************************************************

       This file is part of the XSL DocBook Stylesheet distribution.
       See ../README or http://docbook.sf.net/release/xsl/current/ for
       copyright and other information.

       ******************************************************************** -->

  <xsl:include href='../VERSION.xsl'/>

  <xsl:template match="/" name='ooo.top'>
    <xsl:param name='doc' select='/'/>

    <office:document-content
      office:version='1.0'>

      <office:script/>
      <office:font-face-decls>
        <style:font-face style:name="Nimbus Roman No9 L"
          svg:font-family="'Nimbus Roman No9 L'"
          style:font-family-generic="roman"
          style:font-pitch="variable"/>
        <style:font-face style:name="Nimbus Sans L"
          svg:font-family="'Nimbus Sans L'"
          style:font-family-generic="swiss"
          style:font-pitch="variable"/>
        <style:font-face style:name="DejaVu LGC Sans"
          svg:font-family="'DejaVu LGC Sans'"
          style:font-family-generic="system"
          style:font-pitch="variable"/>
      </office:font-face-decls>
      <office:automatic-styles/>

      <xsl:apply-templates select='$doc/*'
        mode='doc:toplevel'/>
    </office:document-content>
  </xsl:template>

  <xsl:template name='doc:make-body'>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <office:body>
      <office:text>
        <office-forms form:automatic-focus='false'
          form:apply-design-mode='false'/>
        <text:sequence-decls>
          <text:sequence-decl text:display-outline-level="0" text:name="Illustration"/>
          <text:sequence-decl text:display-outline-level="0" text:name="Table"/>
          <text:sequence-decl text:display-outline-level="0" text:name="Text"/>
          <text:sequence-decl text:display-outline-level="0" text:name="Drawing"/>
        </text:sequence-decls>

        <xsl:copy-of select='$content'/>
      </office:text>
    </office:body>
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

    <text:p text:style-name='{$style}'>

      <xsl:call-template name='attributes'>
        <xsl:with-param name='node' select='$attributes.node'/>
      </xsl:call-template>

      <xsl:copy-of select='$content'/>
    </text:p>
  </xsl:template>

  <xsl:template name='doc:make-phrase'>
    <xsl:param name='style' select='"unknown"'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <text:span text:style-name='{$style}'>
      <xsl:copy-of select='$content'/>
    </text:span>
  </xsl:template>

  <xsl:template name='doc:make-hyperlink'/>
  <xsl:template name='doc:make-hyperlink-not-implemented'>
    <xsl:param name='target'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

    <text:link href='{$target}'>
    </text:link>
  </xsl:template>

  <xsl:template name='doc:make-table'/>
  <xsl:template name='doc:make-table-not-yet-implemented'>
    <xsl:param name='columns'/>
    <xsl:param name='content'>
      <xsl:apply-templates mode='doc:body'/>
    </xsl:param>

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
    <text:br/>
  </xsl:template>

  <xsl:template name='attributes'>
    <xsl:param name='node' select='.'/>

  </xsl:template>
</xsl:stylesheet>
