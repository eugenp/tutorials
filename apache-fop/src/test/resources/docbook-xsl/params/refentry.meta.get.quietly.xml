<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="refentry.meta.get.quietly">
<refmeta>
<refentrytitle>refentry.meta.get.quietly</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">boolean</refmiscinfo>
</refmeta>
<refnamediv>
<refname>refentry.meta.get.quietly</refname>
<refpurpose>Suppress notes and warnings when gathering refentry metadata?</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="refentry.meta.get.quietly.frag">
<xsl:param name="refentry.meta.get.quietly" select="0"/>
</src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>If zero (the default), notes and warnings about “missing” markup
are generated during gathering of refentry metadata. If non-zero, the
metadata is gathered “quietly” -- that is, the notes and warnings are
suppressed.</para>

<tip>
  <para>If you are processing a large amount of <tag>refentry</tag>
  content, you may be able to speed up processing significantly by
  setting a non-zero value for
  <parameter>refentry.meta.get.quietly</parameter>.</para>
</tip>

</refsection>
</refentry>
