<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="man.th.extra2.max.length">
<refmeta>
<refentrytitle>man.th.extra2.max.length</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">integer</refmiscinfo>
</refmeta>
<refnamediv>
<refname>man.th.extra2.max.length</refname>
<refpurpose>Maximum length of extra2 in header/footer</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="man.th.extra2.max.length.frag">
<xsl:param name="man.th.extra2.max.length">30</xsl:param>
</src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>Specifies the maximum permitted length of the
<literal>extra2</literal> part of the man-page part of the
<literal>.TH</literal> title line header/footer. If the
<literal>extra2</literal> content exceeds the maxiumum specified, it
is truncated down to the maximum permitted length.</para>

<para>The content of the <literal>extra2</literal> field is usually
displayed in the left footer of the page and is typically "source"
data indicating the software system or product that the item
documented in the man page belongs to, often in the form
<replaceable>Name</replaceable> <replaceable>Version</replaceable>;
for example, "GTK+ 1.2" (from the <literal>gtk-options(7)</literal>
man page).</para>

<para>The default value for this parameter is reasonable but somewhat
arbitrary. If you are processing pages with long "source" information,
you may want to experiment with changing the value in order to achieve
the correct aesthetic results.</para>
</refsection>
</refentry>
