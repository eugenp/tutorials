<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="refentry.version.suppress">
<refmeta>
<refentrytitle>refentry.version.suppress</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">boolean</refmiscinfo>
</refmeta>
<refnamediv>
<refname>refentry.version.suppress</refname>
<refpurpose>Suppress "version" part of refentry "source" contents?</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="refentry.version.suppress.frag">
<xsl:param name="refentry.version.suppress">0</xsl:param></src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>If the value of <parameter>refentry.version.suppress</parameter>
is non-zero, then during <tag>refentry</tag> metadata gathering, no
"version" data is added to the <tag>refentry</tag> "source"
contents. Instead (unless
<parameter>refentry.source.name.suppress</parameter> is also
non-zero), only "source name" data is added to the "source"
contents.</para>

<para>If you find that the <tag>refentry</tag> metadata gathering
mechanism is causing unwanted "version" data to show up in your output
-- for example, in the footer (or possibly header) of a man page --
then you might consider setting a non-zero value for
<parameter>refentry.version.suppress</parameter>.</para>

<para>Note that the terms "source", "source name", and "version" have
special meanings in this context. For details, see the documentation
for the <parameter>refentry.source.name.profile</parameter>
parameter.</para>

</refsection>
</refentry>
