<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="process.empty.source.toc">
<refmeta>
<refentrytitle>process.empty.source.toc</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">boolean</refmiscinfo>
</refmeta>
<refnamediv>
<refname>process.empty.source.toc</refname>
<refpurpose>Generate automated TOC if <tag>toc</tag> element occurs in a source document?</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="process.empty.source.toc.frag"><xsl:param name="process.empty.source.toc" select="0"/></src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>Specifies that if an empty <tag>toc</tag> element is found in a
source document, an automated TOC is generated at this point in the
document.
<note>
  <para>Depending on what the value of the
  <parameter>generate.toc</parameter> parameter is, setting this
  parameter to <literal>1</literal> could result in generation of
  duplicate automated TOCs. So the
  <parameter>process.empty.source.toc</parameter> is primarily useful
  as an "override": by placing an empty <tag>toc</tag> in your
  document and setting this parameter to <literal>1</literal>, you can
  force a TOC to be generated even if <tag>generate.toc</tag>
  says not to.</para>
</note>
</para>

</refsection>
</refentry>
