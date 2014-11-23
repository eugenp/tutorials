<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="use.role.for.mediaobject">
<refmeta>
<refentrytitle>use.role.for.mediaobject</refentrytitle>
<refmiscinfo class="other" otherclass="datatype">boolean</refmiscinfo>
</refmeta>
<refnamediv>
<refname>use.role.for.mediaobject</refname>
<refpurpose>Use <tag class="attribute">role</tag> attribute 
value for selecting which of several objects within a mediaobject to use.
</refpurpose>
</refnamediv>

<refsynopsisdiv>
<src:fragment xml:id="use.role.for.mediaobject.frag">
<xsl:param name="use.role.for.mediaobject" select="1"/>
</src:fragment>
</refsynopsisdiv>

<refsection><info><title>Description</title></info>

<para>If non-zero, the <tag class="attribute">role</tag> attribute on
<tag>imageobject</tag>s or other objects within a <tag>mediaobject</tag> container will be used to select which object will be
used.
</para>
<para>
The order of selection when then parameter is non-zero is:
</para>
<orderedlist>
<listitem>
    <para>If the stylesheet parameter <parameter>preferred.mediaobject.role</parameter> has a value, then the object whose role equals that value is selected.</para>
</listitem>
<listitem>
<para>Else if an object's role attribute has a value of
<literal>html</literal> for HTML processing or
<literal>fo</literal> for FO output, then the first
of such objects is selected.
</para>
</listitem>
<listitem>
<para>Else the first suitable object is selected.</para>
</listitem>
</orderedlist>
<para>
If the value of 
<parameter>use.role.for.mediaobject</parameter>
is zero, then role attributes are not considered
and the first suitable object
with or without a role value is used.
</para>
</refsection>
</refentry>
