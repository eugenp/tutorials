<refentry xmlns="http://docbook.org/ns/docbook"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xi="http://www.w3.org/2001/XInclude"
          xmlns:src="http://nwalsh.com/xmlns/litprog/fragment"
          xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
          version="5.0" xml:id="target.database.document"> 
<refmeta> 
<refentrytitle>target.database.document</refentrytitle> 
<refmiscinfo class="other" otherclass="datatype">uri</refmiscinfo> 
</refmeta> 
<refnamediv> 
<refname>target.database.document</refname> 
<refpurpose>Name of master database file for resolving
olinks</refpurpose> 
</refnamediv> 
<refsynopsisdiv> 
<src:fragment xml:id="target.database.document.frag">
 <xsl:param name="target.database.document">olinkdb.xml</xsl:param>
</src:fragment> 
</refsynopsisdiv> 
<refsection><info><title>Description</title></info>
 
<para>
To resolve olinks between documents, the stylesheets use a master
database document that identifies the target datafiles for all the
documents within the scope of the olinks. This parameter value is the
URI of the master document to be read during processing to resolve
olinks.  The default value is <filename>olinkdb.xml</filename>.</para>

<para>The data structure of the file is defined in the
<filename>targetdatabase.dtd</filename> DTD.  The database file
provides the high level elements to record the identifiers, locations,
and relationships of documents. The cross reference data for
individual documents is generally pulled into the database using
system entity references or XIncludes. See also
<parameter>targets.filename</parameter>.  </para> </refsection>
</refentry>
