<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:ng="http://docbook.org/docbook-ng" 
		xmlns:db="http://docbook.org/ns/docbook"
		xmlns:exsl="http://exslt.org/common" version="1.0"
		exclude-result-prefixes="exsl db ng">

<!-- 
********************************************************************************
     $Id: eclipse3.xsl 9149 2011-11-12 00:12:07Z bobstayton $

     This file is part of the XSL DocBook Stylesheet distribution.
     See ../README or http://docbook.sf.net/release/xsl/current/ for
     copyright and other information.

 * Copyright (c) 2008 Standards for Technology in Automotive Retail and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     David Carver - STAR - Extended existing eclipse.xsl file to produce valid
 *                           eclipse 3.3 plugin.xml with a manifest file.
 *******************************************************************************
 -->
  
  <xsl:import href="eclipse.xsl"/>

  <xsl:param name="eclipse.manifest">1</xsl:param>
  <xsl:param name="create.plugin.xml">1</xsl:param>
  
  <xsl:template name="plugin.xml">
    <xsl:if test="$create.plugin.xml != 0">
      <xsl:call-template name="write.chunk">
	<xsl:with-param name="filename">
	  <xsl:if test="$manifest.in.base.dir != 0">
	    <xsl:value-of select="$chunk.base.dir" />
	  </xsl:if>
	  <xsl:value-of select="'plugin.xml'" />
	</xsl:with-param>
	<xsl:with-param name="method" select="'xml'" />
	<xsl:with-param name="encoding" select="'utf-8'" />
	<xsl:with-param name="indent" select="'yes'" />
	<xsl:with-param name="quiet" select="$chunk.quietly"/>
	<xsl:with-param name="content">
	  <xsl:choose>
	    
	    <xsl:when test="$eclipse.manifest = '1'">
	      <plugin>
		<extension point="org.eclipse.help.toc">
		  <toc file="toc.xml" primary="true" />
		</extension>
		<xsl:if test="$generate.index = 1">
		  <extension point="org.eclipse.help.index">
		    <index file="index.xml"/>
		 </extension>
		</xsl:if>
	      </plugin>
	      <xsl:call-template name="write.chunk">
		<xsl:with-param name="filename">
		  <xsl:if test="$manifest.in.base.dir != 0">
		    <xsl:value-of select="$chunk.base.dir" />
		  </xsl:if>
		  <xsl:value-of select="'META-INF/'" />
		  <xsl:value-of select="'MANIFEST.MF'" />
		</xsl:with-param>
		<xsl:with-param name="method" select="'text'" />
		<xsl:with-param name="encoding" select="'utf-8'" />
		<xsl:with-param name="quiet" select="$chunk.quietly"/>
		<xsl:with-param name="content">
		  <xsl:call-template name="manifest.content"/>
		</xsl:with-param>
	      </xsl:call-template>
	    </xsl:when>

	    <xsl:otherwise>
	      <plugin name="{$eclipse.plugin.name}" id="{$eclipse.plugin.id}"
		      version="1.0" provider-name="{$eclipse.plugin.provider}">
		<extension point="org.eclipse.help.toc">
		  <toc file="toc.xml" primary="true" />
		</extension>
		<xsl:if test="$generate.index = 1">
		  <extension point="org.eclipse.help.index">
		  <index file="index.xml"/>
		  </extension>
		</xsl:if>
	      </plugin>
	    </xsl:otherwise>

	  </xsl:choose>
	</xsl:with-param>
      </xsl:call-template>	    
    </xsl:if>
  </xsl:template>

  <xsl:template name="manifest.content">
    <xsl:text>Manifest-Version: 1.0</xsl:text>
    <xsl:text>&#xA;</xsl:text>
    <xsl:text>Bundle-Version: 1.0</xsl:text>
    <xsl:text>&#xA;</xsl:text>
    <xsl:text>Bundle-Name: </xsl:text><xsl:value-of select="$eclipse.plugin.name"/>
    <xsl:text>&#xA;</xsl:text>
    <xsl:text>Bundle-SymbolicName: </xsl:text><xsl:value-of select="$eclipse.plugin.id"/>
    <xsl:text>&#xA;</xsl:text>
    <xsl:text>Bundle-Vendor: </xsl:text><xsl:value-of select="$eclipse.plugin.provider"/>
    <xsl:text>&#xA;</xsl:text>
  </xsl:template>

</xsl:stylesheet>
 	  	 
