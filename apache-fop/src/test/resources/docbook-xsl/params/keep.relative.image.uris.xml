<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="keep.relative.image.uris">
<refmeta>
<refentrytitle>keep.relative.image.uris</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">boolean</refmiscinfo>
</refmeta>
<refnamediv>
<refname>keep.relative.image.uris</refname>
<refpurpose>Should image URIs be resolved against xml:base?</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="keep.relative.image.uris.frag">
<xsl:param condition="html" name="keep.relative.image.uris" select="1"/>
<xsl:param condition="fo" name="keep.relative.image.uris" select="0"/>
</src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>If non-zero, relative URIs (in, for example
<literal>fileref</literal> attributes) will be used in the generated
output. Otherwise, the URIs will be made absolute with respect to the
base URI.</para>

<para>Note that the stylesheets calculate (and use) the absolute form
for some purposes, this only applies to the resulting output.</para>

</refsection>
</refentry>
