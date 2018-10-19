<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="refentry.version.profile">
<refmeta>
<refentrytitle>refentry.version.profile</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">string</refmiscinfo>
</refmeta>
<refnamediv>
<refname>refentry.version.profile</refname>
<refpurpose>Specifies profile for refentry "version" data</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="refentry.version.profile.frag">
<xsl:param name="refentry.version.profile">
  (($info[//productnumber])[last()]/productnumber)[1]|
  (($info[//edition])[last()]/edition)[1]|
  (($info[//releaseinfo])[last()]/releaseinfo)[1]
</xsl:param>
</src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>The value of <parameter>refentry.version.profile</parameter> is
a string representing an XPath expression. It is evaluated at
run-time and used only if
<parameter>refentry.version.profile.enabled</parameter> is
non-zero. Otherwise, the <tag>refentry</tag> metadata-gathering logic
"hard coded" into the stylesheets is used.</para>

<para>A "source.name" is one part of a (potentially) two-part
<replaceable>Name</replaceable> <replaceable>Version</replaceable>
"source" field. For more details, see the documentation for the
<parameter>refentry.source.name.profile</parameter> parameter.</para>

</refsection>
</refentry>
