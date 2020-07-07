<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="man.th.extra3.max.length">
<refmeta>
<refentrytitle>man.th.extra3.max.length</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">integer</refmiscinfo>
</refmeta>
<refnamediv>
<refname>man.th.extra3.max.length</refname>
<refpurpose>Maximum length of extra3 in header/footer</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="man.th.extra3.max.length.frag">
<xsl:param name="man.th.extra3.max.length">30</xsl:param>
</src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>Specifies the maximum permitted length of the
<literal>extra3</literal> part of the man-page <literal>.TH</literal>
title line header/footer. If the <literal>extra3</literal> content
exceeds the maxiumum specified, it is truncated down to the maximum
permitted length.</para>

<para>The content of the <literal>extra3</literal> field is usually
displayed in the middle header of the page and is typically a "manual
name"; for example, "GTK+ User's Manual" (from the
<literal>gtk-options(7)</literal> man page).</para>

<para>The default value for this parameter is reasonable but somewhat
arbitrary. If you are processing pages with long "manual names" -- or
especially if you are processing pages that have both long "title"
parts (command/function, etc. names) <emphasis>and</emphasis> long
manual names -- you may want to experiment with changing the value in
order to achieve the correct aesthetic results.</para>
</refsection>
</refentry>
