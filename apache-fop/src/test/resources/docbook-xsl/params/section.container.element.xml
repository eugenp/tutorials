<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="section.container.element">
<refmeta>
<refentrytitle>section.container.element</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">list</refmiscinfo>
<refmiscinfo class="other" otherclass="value">block</refmiscinfo>
<refmiscinfo class="other" otherclass="value">wrapper</refmiscinfo>
</refmeta>
<refnamediv>
<refname>section.container.element</refname>
<refpurpose>Select XSL-FO element name to contain sections</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="section.container.element.frag">
<xsl:param name="section.container.element">block</xsl:param>
</src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>Selects the element name for outer container of
each section. The choices are <literal>block</literal> (default)
or <literal>wrapper</literal>.
The <literal>fo:</literal> namespace prefix is added
by the stylesheet to form the full element name.
</para>

<para>This element receives the section <literal>id</literal>
attribute and the appropriate section level attribute-set.
</para>

<para>Changing this parameter to <literal>wrapper</literal>
is only necessary when producing multi-column output
that contains page-wide spans.  Using <literal>fo:wrapper</literal>
avoids the nesting of <literal>fo:block</literal>
elements that prevents spans from working (the standard says
a span must be on a block that is a direct child of 
<literal>fo:flow</literal>).
</para>

<para>If set to <literal>wrapper</literal>, the
section attribute-sets only support properties
that are inheritable.  That's because there is no
block to apply them to.  Properties such as
font-family are inheritable, but properties such as
border are not.
</para>

<para>Only some XSL-FO processors need to use this parameter.
The Antenna House processor, for example, will handle 
spans in nested blocks without changing the element name.
The RenderX XEP product and FOP follow the XSL-FO standard 
and need to use <literal>wrapper</literal>.
</para>

</refsection>
</refentry>
