<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="insert.olink.page.number">
<refmeta>
<refentrytitle>insert.olink.page.number</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">list</refmiscinfo> 
<refmiscinfo class="other" otherclass="value">no</refmiscinfo>
<refmiscinfo class="other" otherclass="value">yes</refmiscinfo>
<refmiscinfo class="other" otherclass="value">maybe</refmiscinfo>
</refmeta>
<refnamediv>
<refname>insert.olink.page.number</refname>
<refpurpose>Turns page numbers in olinks on and off</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="insert.olink.page.number.frag">
<xsl:param name="insert.olink.page.number">no</xsl:param>
</src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>The value of this parameter determines if
cross references made between documents with
<tag>olink</tag> will 
include page number citations.
In most cases this is only applicable to references in printed output.
</para>
<para>The parameter has three possible values.
</para>
<variablelist>
<varlistentry>
<term>no</term>
<listitem><para>No page number references will be generated for olinks.
</para></listitem>
</varlistentry>
<varlistentry>
<term>yes</term>
<listitem><para>Page number references will be generated
for all <tag>olink</tag> references.
The style of page reference may be changed
if an <tag class="attribute">xrefstyle</tag>
attribute is used.
</para></listitem>
</varlistentry>
<varlistentry>
<term>maybe</term>
<listitem><para>Page number references will not be generated
for an <tag>olink</tag> element unless 
it has an
<tag class="attribute">xrefstyle</tag>
attribute whose value specifies a page reference.
</para></listitem>
</varlistentry>
</variablelist>
<para>Olinks that point to targets within the same document
are treated as <tag>xref</tag>s, and controlled by
the <parameter>insert.xref.page.number</parameter> parameter.
</para>

<para>Page number references for olinks to
external documents can only be inserted if the 
information exists in the olink database. 
This means each olink target element 
(<tag>div</tag> or <tag>obj</tag>)
must have a <tag class="attribute">page</tag> attribute
whose value is its page number in the target document.
The XSL stylesheets are not able to extract that information
during processing because pages have not yet been created in
XSLT transformation.  Only the XSL-FO processor knows what
page each element is placed on.
Therefore some postprocessing must take place to populate
page numbers in the olink database.
</para>



</refsection>
</refentry>
