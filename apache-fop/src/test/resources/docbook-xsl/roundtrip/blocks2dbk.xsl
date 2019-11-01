<!DOCTYPE xsl:stylesheet [
<!-- External DTD defines entities:
     components :- QNames of component-level elements
     blocks :- QNames of block-level elements
     metadata-content :- XPath expression matching metadata styles
     author-content :- XPath expression matching author styles
     admonition :- XPath expression matching admonition styles
     admonition-title :- XPath expression matching admonition title styles
-->
<!ENTITY % ext SYSTEM "blocks2dbk.dtd">
%ext;
]>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:dbk='http://docbook.org/ns/docbook'
  xmlns:rnd='http://docbook.org/ns/docbook/roundtrip'
  xmlns:xlink='http://www.w3.org/1999/xlink'>

  <!-- $Id: blocks2dbk.xsl 8137 2008-10-09 03:37:56Z balls $ -->
  <!-- Stylesheet to convert word processing docs to DocBook -->
  <!-- This stylesheet processes the output of sections2blocks.xsl -->

  <xsl:output indent="yes" method="xml"
    cdata-section-elements='dbk:programlisting dbk:literallayout'/>

  <!-- ================================================== -->
  <!--    Parameters                                      -->
  <!-- ================================================== -->

  <xsl:param name='docbook5'>0</xsl:param>
  <xsl:param name="nest.sections">1</xsl:param>

  <xsl:strip-space elements='*'/>
  <xsl:preserve-space elements='dbk:para dbk:emphasis'/>

  <xsl:template match="&components; |
                       &blocks;">
    <xsl:choose>
      <xsl:when test='self::dbk:article and
                      count(dbk:book) = 1 and
                      count(dbk:info|dbk:book) &lt;= 2'>
        <xsl:apply-templates select='dbk:book'/>
      </xsl:when>
      <xsl:when test='self::dbk:article and
                      *[1][self::dbk:para] and
                      *[1]/@rnd:style != "article-title" and
                      contains(*[1]/@rnd:style, "-title")'>
        <xsl:variable name='element-name'
          select='substring-before(*[1]/@rnd:style, "-title")'/>
        <xsl:element name='{$element-name}'
          namespace='http://docbook.org/ns/docbook'>
          <dbk:info>
            <xsl:apply-templates select='*[1]'
              mode='rnd:metadata'/>
          </dbk:info>
          <xsl:apply-templates/>
        </xsl:element>
      </xsl:when>
      <xsl:otherwise>
        <xsl:copy>
          <xsl:call-template name='rnd:attributes'/>

          <xsl:variable name='metadata'>
            <xsl:apply-templates select='*[1]'
              mode='rnd:metadata'/>
          </xsl:variable>
          <xsl:if test='$metadata'>
            <dbk:info>
              <xsl:copy-of select='$metadata'/>
            </dbk:info>
          </xsl:if>

          <xsl:apply-templates/>
        </xsl:copy>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='dbk:book-component'>
    <xsl:variable name='element-name'
      select='substring-before(*[1]/@rnd:style, "-title")'/>

    <xsl:element name='{$element-name}'
      namespace='http://docbook.org/ns/docbook'>
      <dbk:info>
        <xsl:apply-templates select='*[1]'
          mode='rnd:metadata'/>
      </dbk:info>
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="dbk:para" name='rnd:para'>
    <!-- Some elements are normally suppressed,
         since they are processed in a different context.
         If this parameter is false then the element will be processed normally.
      -->
    <xsl:param name='suppress' select='true()'/>

    <!-- This paragraph may be in a sidebar -->
    <xsl:variable name='sidebar'
		  select='preceding-sibling::*[self::dbk:para and @rnd:style = "sidebar-title"][1]'/>

    <!-- This paragraph may be in the textobject of a table or figure -->
    <xsl:variable name='table'
		  select='preceding-sibling::dbk:informaltable[1]'/>
    <xsl:variable name='figure'
		  select='preceding-sibling::dbk:para[@rnd:style = "informalfigure-imagedata" or (dbk:inlinemediaobject and count(*) = 1 and normalize-space(.) = "")][1]'/>
    <xsl:variable name='caption'
		  select='following-sibling::dbk:para[@rnd:style = "caption" or @rnd:style = "Caption"]'/>

    <xsl:choose>
      <!-- continue style paragraphs are handled in context -->
      <xsl:when test='$suppress and
                      @rnd:style = "para-continue"'/>

      <!-- Certain elements gather the following paragraph -->
      <xsl:when test='$suppress and
                      preceding-sibling::*[1][self::dbk:para and
                      @rnd:style = "example-title"]'/>

      <xsl:when test='$suppress and
		      $sidebar and
		      not(preceding-sibling::dbk:para[(not(@rnd:style) or @rnd:style = "") and
		        preceding-sibling::*[preceding-sibling::*[generate-id() = generate-id($sidebar)]]])'/>

      <!-- Separate processing is performed for table/figure titles and captions -->
      <xsl:when test='$suppress and
		      @rnd:style = "table-title" and
		      following-sibling::*[1][self::dbk:informaltable|self::dbk:para[@rnd:style = "informalfigure-imagedata"]|self::dbk:para[dbk:inlinemediaobject and count(*) = 1 and normalize-space() = ""]]'/>
      <xsl:when test='$suppress and
		      @rnd:style = "figure-title" and
		      following-sibling::*[1][self::dbk:para][@rnd:style = "informalfigure-imagedata" or (dbk:inlinemediaobject and count(*) = 1 and normalize-space(.) = "")]'/>
      <xsl:when test='$suppress and
		      (@rnd:style = "caption" or @rnd:style = "Caption") and
		      (preceding-sibling::*[self::dbk:informaltable] or
		      preceding-sibling::*[self::dbk:para][@rnd:style = "informalfigure-imagedata" or (dbk:inlinemediaobject and count(*) = 1 and normalize-space(.) = "")])'/>

      <xsl:when test='$suppress and
		      $table and
		      $caption and
		      generate-id($caption/preceding-sibling::dbk:informaltable[1]) = generate-id($table)'/>
      <xsl:when test='$suppress and
		      $figure and
		      $caption and
		      generate-id($caption/preceding-sibling::dbk:para[@rnd:style = "informalfigure-imagedata" or (dbk:inlinemediaobject and count(*) = 1 and normalize-space(.) = "")][1]) = generate-id($figure)'/>

      <xsl:when test='@rnd:style = "imagedata-metadata" or
                      @rnd:style = "table-metadata"'/>

      <!-- Ignore empty paragraphs -->
      <xsl:when test='(not(@rnd:style) or
                      @rnd:style = "") and
                      normalize-space(.) = "" and
		      not(*)'/>

      <!-- Image inline or block? -->
      <xsl:when test='(not(@rnd:style) or
                      @rnd:style = "") and
                      normalize-space(.) = "" and
		      count(*) = 1 and
		      dbk:inlinemediaobject'>
        <xsl:apply-templates/>
      </xsl:when>
      <xsl:when test='not(@rnd:style) or
                      @rnd:style = "" or
                      @rnd:style = "para-continue"'>
        <dbk:para>
          <xsl:call-template name='rnd:attributes'/>
          <xsl:apply-templates/>
        </dbk:para>
      </xsl:when>

      <xsl:when test='@rnd:style = "xinclude"'
        xmlns:xi='http://www.w3.org/2001/XInclude'>
        <xi:include>
          <xsl:attribute name='href'>
            <xsl:apply-templates mode='rnd:xinclude'/>
          </xsl:attribute>
        </xi:include>
      </xsl:when>

      <xsl:when test='$suppress and
                      preceding-sibling::*[1]/self::dbk:para[&admonition-title;]'/>
      <xsl:when test='&admonition-title;'>
        <xsl:element name='{substring-before(@rnd:style, "-title")}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:call-template name='rnd:attributes'/>
          <dbk:title>
            <xsl:apply-templates/>
          </dbk:title>
          <xsl:apply-templates select='following-sibling::*[1]'>
            <xsl:with-param name='suppress' select='false()'/>
          </xsl:apply-templates>
          <xsl:apply-templates select='following-sibling::*[2]'
            mode='rnd:continue'/>
        </xsl:element>
      </xsl:when>

      <xsl:when test='starts-with(@rnd:style, "itemizedlist") or
                      starts-with(@rnd:style, "orderedlist")'>

        <xsl:variable name='stop.node'
          select='following-sibling::dbk:para[not(@rnd:style) or
                  (not(starts-with(@rnd:style, "itemizedlist") or starts-with(@rnd:style, "orderedlist")) and @rnd:style != "para-continue")][1]'/>

        <xsl:choose>
          <xsl:when test='translate(substring-after(@rnd:style, "list"), "0123456789", "") != "" or
                          substring-after(@rnd:style, "list") = ""'>
            <xsl:call-template name='rnd:error'>
              <xsl:with-param name='code' select='"list-bad-level"'/>
              <xsl:with-param name='message'>style "<xsl:value-of select='@rnd:style'/>" is not a valid list style</xsl:with-param>
            </xsl:call-template>
          </xsl:when>

          <!-- TODO: the previous para-continue may not be associated with a list -->

          <!-- NB. Look back at the previous paragraph. There may be intervening tables or images. -->
          <xsl:when test='preceding-sibling::dbk:para[1][starts-with(@rnd:style, "itemizedlist") or starts-with(@rnd:style, "orderedlist") or @rnd:style = "para-continue"]'/>
          <xsl:when test='substring-after(@rnd:style, "list") != 1'>
            <xsl:call-template name='rnd:error'>
              <xsl:with-param name='code'>list-wrong-level</xsl:with-param>
              <xsl:with-param name='message'>list started at the wrong level</xsl:with-param>
            </xsl:call-template>
          </xsl:when>
          <xsl:when test='$stop.node'>
            <xsl:element name='{substring-before(@rnd:style, "1")}'
              namespace='http://docbook.org/ns/docbook'>
              <xsl:apply-templates select='.|following-sibling::dbk:para[@rnd:style = current()/@rnd:style][following-sibling::*[generate-id() = generate-id($stop.node)]]'
                mode='rnd:listitem'/>
            </xsl:element>
          </xsl:when>
          <xsl:otherwise>
            <xsl:element name='{substring-before(@rnd:style, "1")}'
              namespace='http://docbook.org/ns/docbook'>
              <xsl:apply-templates select='.|following-sibling::dbk:para[@rnd:style = current()/@rnd:style]'
                mode='rnd:listitem'/>
            </xsl:element>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:when test='@rnd:style = "programlisting" and
                      preceding-sibling::*[1][self::dbk:para and @rnd:style = "programlisting"]'/>
      <xsl:when test='@rnd:style = "literallayout" and
                      preceding-sibling::*[1][self::dbk:para and @rnd:style = "literallayout"]'/>
      <xsl:when test='@rnd:style = "programlisting" or
                      @rnd:style = "literallayout"'>

        <xsl:variable name='stop.node'
          select='following-sibling::dbk:para[@rnd:style != current()/@rnd:style][1]'/>

        <xsl:element name='{@rnd:style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:apply-templates/>

          <xsl:choose>
            <xsl:when test='$stop.node'>
              <xsl:apply-templates select='following-sibling::dbk:para[following-sibling::*[generate-id() = generate-id($stop.node)]]'
                mode='rnd:programlisting'/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select='following-sibling::dbk:para'
                mode='rnd:programlisting'/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:element>
      </xsl:when>

      <xsl:when test='@rnd:style = "example-title"'>
        <xsl:element name='{substring-before(@rnd:style, "-title")}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:call-template name='rnd:attributes'/>
          <dbk:title>
            <xsl:apply-templates/>
          </dbk:title>

          <xsl:apply-templates select='following-sibling::*[1]'>
            <xsl:with-param name='suppress' select='false()'/>
          </xsl:apply-templates>
        </xsl:element>
      </xsl:when>

      <xsl:when test='@rnd:style = "sidebar-title"'>
        <!-- TODO: next sidebar should stop this sidebar -->
	<xsl:variable name='stop.node'
		      select='following-sibling::dbk:para[(not(@rnd:style) or @rnd:style = "") and
			      normalize-space(.) = ""][1]'/>

	<dbk:sidebar>
          <xsl:call-template name='rnd:attributes'/>
	  <dbk:info>
	    <dbk:title>
	      <xsl:apply-templates/>
	    </dbk:title>
	  </dbk:info>

	  <xsl:choose>
	    <xsl:when test='$stop.node'>
	      <xsl:apply-templates select='following-sibling::*[following-sibling::*[generate-id() = generate-id($stop.node)]]'
				   mode='rnd:sidebar'/>
	    </xsl:when>
	    <xsl:otherwise>
	      <xsl:apply-templates select='following-sibling::*'
				   mode='rnd:sidebar'/>
	    </xsl:otherwise>
	  </xsl:choose>
	</dbk:sidebar>
      </xsl:when>

      <xsl:when test='&admonition;'>
        <xsl:element name='{@rnd:style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:call-template name='rnd:attributes'/>
          <dbk:para>
            <xsl:apply-templates/>
          </dbk:para>
          <xsl:apply-templates select='following-sibling::*[1]'
            mode='rnd:continue'/>
        </xsl:element>
      </xsl:when>

      <!-- TODO: make sure this is in a bibliography.
	   If not, create a bibliolist.
	-->
      <xsl:when test='@rnd:style = "bibliomixed"'>
	<dbk:bibliomixed>
          <xsl:call-template name='rnd:attributes'/>
	  <xsl:apply-templates/>
	</dbk:bibliomixed>
      </xsl:when>
      <xsl:when test='@rnd:style = "biblioentry-title"'>
	<dbk:biblioentry>
          <xsl:call-template name='rnd:attributes'/>
          <dbk:title>
            <xsl:apply-templates/>
          </dbk:title>
          <xsl:if test='following-sibling::*[1][&metadata-content;]'>
            <xsl:apply-templates select='following-sibling::*[1]'
              mode='rnd:metadata'/>
          </xsl:if>
	</dbk:biblioentry>
      </xsl:when>

      <xsl:when test='@rnd:style = "blockquote-attribution" and
                      preceding-sibling::*[1][self::dbk:para][@rnd:style = "blockquote-title" or @rnd:style = "blockquote"]'/>
      <xsl:when test='@rnd:style = "blockquote-attribution"'>
        <xsl:call-template name='rnd:error'>
          <xsl:with-param name='code'>improper-blockquote-attribution</xsl:with-param>
          <xsl:with-param name='message'>blockquote attribution must follow a blockquote title</xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test='@rnd:style = "blockquote" or
                      @rnd:style = "blockquote-title"'>
        <xsl:choose>
          <xsl:when test='@rnd:style = "blockquote" and
                          preceding-sibling::*[1][self::dbk:para][starts-with(@rnd:style, "blockquote")]'/>
          <xsl:otherwise>

            <xsl:variable name='stop.node'
              select='following-sibling::*[not(@rnd:style = "blockquote" or
                      @rnd:style = "blockquote-attribution")][1]'/>

            <dbk:blockquote>
              <xsl:call-template name='rnd:attributes'/>
	      <xsl:if test='@rnd:style = "blockquote-title"'>
		<dbk:info>
		  <dbk:title>
		    <xsl:apply-templates/>
		  </dbk:title>
		</dbk:info>
	      </xsl:if>
              <xsl:choose>
                <xsl:when test='$stop.node'>
		  <xsl:apply-templates select='following-sibling::*[following-sibling::*[generate-id() = generate-id($stop.node)]][@rnd:style = "blockquote-attribution"]' mode='rnd:blockquote-attribution'/>
                  <xsl:apply-templates select='self::*[@rnd:style = "blockquote"] |
					       following-sibling::*[following-sibling::*[generate-id() = generate-id($stop.node)]]'
                    mode='rnd:blockquote'/>
                </xsl:when>
                <xsl:otherwise>
		  <xsl:apply-templates select='following-sibling::*[@rnd:style = "blockquote-attribution"]' mode='rnd:blockquote-attribution'/>
                  <xsl:apply-templates select='self::*[@rnd:style = "blockquote"] |
					       following-sibling::*'
                    mode='rnd:blockquote'/>
                </xsl:otherwise>
              </xsl:choose>
            </dbk:blockquote>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:when test='@rnd:style = "bridgehead"'>
        <xsl:element name='{@rnd:style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:call-template name='rnd:attributes'/>
          <xsl:apply-templates/>
        </xsl:element>
      </xsl:when>

      <xsl:when test='@rnd:style = "formalpara-title"'>
        <dbk:formalpara>
          <dbk:title>
            <xsl:call-template name='rnd:attributes'/>
            <xsl:apply-templates/>
          </dbk:title>
          <xsl:choose>
            <xsl:when test='following-sibling::*[1][self::dbk:para][@rnd:style = "formalpara"]'>
              <dbk:para>
                <xsl:call-template name='rnd:attributes'>
                  <xsl:with-param name='node'
                    select='following-sibling::*[1]'/>
                </xsl:call-template>
                <xsl:apply-templates select='following-sibling::*[1]/node()'/>
              </dbk:para>
            </xsl:when>
          </xsl:choose>
        </dbk:formalpara>
      </xsl:when>
      <xsl:when test='@rnd:style = "formalpara" and
                      preceding-sibling::*[1][self::dbk:para][@rnd:style = "formalpara-title"]'/>
      <xsl:when test='@rnd:style = "formalpara"'>
        <xsl:call-template name='rnd:error'>
          <xsl:with-param name='code'>formalpara-notitle</xsl:with-param>
          <xsl:with-param name='message'>formalpara used without a title</xsl:with-param>
        </xsl:call-template>
      </xsl:when>

      <xsl:when test='@rnd:style = "informalfigure-imagedata"'>
        <xsl:variable name='caption.next'
          select='following-sibling::dbk:para[@rnd:style = "caption" or @rnd:style = "Caption"][1]'/>

        <xsl:variable name='metadata'
          select='preceding-sibling::*[1][self::dbk:para][@rnd:style = "imagedata-metadata"]'/>

        <xsl:choose>
          <xsl:when test='preceding-sibling::*[1][self::dbk:para][@rnd:style = "figure-title"] or
                          ($metadata and preceding-sibling::*[2][self::dbk:para][@rnd:style = "figure-title"])'>
            <dbk:figure>
              <xsl:call-template name='rnd:attributes'/>
              <dbk:info>
                <dbk:title>
                  <xsl:choose>
                    <xsl:when test='$metadata'>
                      <xsl:apply-templates
                        select='preceding-sibling::*[2]/node()'/>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:apply-templates
                        select='preceding-sibling::*[1]/node()'/>
                    </xsl:otherwise>
                  </xsl:choose>
                </dbk:title>
              </dbk:info>
              <dbk:mediaobject>
                <dbk:imageobject>
                  <dbk:imagedata>
                    <xsl:attribute name='fileref'>
                      <xsl:choose>
                        <xsl:when test='dbk:inlinemediaobject/dbk:imageobject/dbk:imagedata/@fileref != ""'>
                          <xsl:value-of select='dbk:inlinemediaobject/dbk:imageobject/dbk:imagedata/@fileref'/>
                        </xsl:when>
                        <xsl:otherwise>
                          <xsl:value-of select='.'/>
                        </xsl:otherwise>
                      </xsl:choose>
                    </xsl:attribute>
                    <xsl:call-template name='rnd:imagedata-attributes'>
                      <xsl:with-param name='metadata' select='$metadata'/>
                    </xsl:call-template>
                  </dbk:imagedata>
                </dbk:imageobject>
              </dbk:mediaobject>
              <xsl:call-template name='rnd:figure-text-caption'>
                <xsl:with-param name='caption' select='$caption.next'/>
              </xsl:call-template>
            </dbk:figure>
          </xsl:when>
          <xsl:when test='preceding-sibling::*[1][self::dbk:para][@rnd:style = "table-title"] or
                          ($metadata and preceding-sibling::*[2][self::dbk:para][@rnd:style = "table-title"])'>
            <dbk:table>
              <xsl:call-template name='rnd:attributes'/>
              <dbk:info>
                <dbk:title>
                  <xsl:choose>
                    <xsl:when test='$metadata'>
                      <xsl:apply-templates
                        select='preceding-sibling::*[2]/node()'/>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:apply-templates
                        select='preceding-sibling::*[1]/node()'/>
                    </xsl:otherwise>
                  </xsl:choose>
                </dbk:title>
              </dbk:info>

              <dbk:mediaobject>
                <dbk:imageobject>
                  <dbk:imagedata>
                    <xsl:attribute name='fileref'>
                      <xsl:choose>
                        <xsl:when test='dbk:inlinemediaobject/dbk:imageobject/dbk:imagedata/@fileref != ""'>
                          <xsl:value-of select='dbk:inlinemediaobject/dbk:imageobject/dbk:imagedata/@fileref'/>
                        </xsl:when>
                        <xsl:otherwise>
                          <xsl:value-of select='.'/>
                        </xsl:otherwise>
                      </xsl:choose>
                    </xsl:attribute>
                    <xsl:call-template name='rnd:imagedata-attributes'>
                      <xsl:with-param name='metadata' select='$metadata'/>
                    </xsl:call-template>
                  </dbk:imagedata>
                </dbk:imageobject>
              </dbk:mediaobject>
              <xsl:call-template name='rnd:figure-text-caption'>
                <xsl:with-param name='caption' select='$caption.next'/>
              </xsl:call-template>
            </dbk:table>
          </xsl:when>
          <xsl:otherwise>
            <dbk:informalfigure>
              <xsl:call-template name='rnd:attributes'/>
              <dbk:mediaobject>
                <dbk:imageobject>
                  <dbk:imagedata>
                    <xsl:attribute name='fileref'>
                      <xsl:choose>
                        <xsl:when test='dbk:inlinemediaobject/dbk:imageobject/dbk:imagedata/@fileref != ""'>
                          <xsl:value-of select='dbk:inlinemediaobject/dbk:imageobject/dbk:imagedata/@fileref'/>
                        </xsl:when>
                        <xsl:otherwise>
                          <xsl:value-of select='.'/>
                        </xsl:otherwise>
                      </xsl:choose>
                    </xsl:attribute>
                    <xsl:call-template name='rnd:imagedata-attributes'>
                      <xsl:with-param name='metadata' select='$metadata'/>
                    </xsl:call-template>
                  </dbk:imagedata>
                </dbk:imageobject>
              </dbk:mediaobject>
              <xsl:call-template name='rnd:figure-text-caption'>
                <xsl:with-param name='caption' select='$caption.next'/>
              </xsl:call-template>
            </dbk:informalfigure>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:when test='(@rnd:style = "caption" or @rnd:style = "Caption") and
                      preceding-sibling::*[(self::dbk:para and contains(@rnd:style, "imagedata")) or self::dbk:informaltable]'/>
      <xsl:when test='@rnd:style = "caption" or @rnd:style = "Caption"'>
        <xsl:call-template name='rnd:error'>
          <xsl:with-param name='code'>bad-caption</xsl:with-param>
          <xsl:with-param name='message'>caption does not follow table or figure</xsl:with-param>
        </xsl:call-template>
      </xsl:when>

      <xsl:when test='(contains(@rnd:style, "-title") or
                      contains(@rnd:style, "-titleabbrev") or
                      contains(@rnd:style, "-subtitle")) and
                      not(starts-with(@rnd:style, "blockquote") or starts-with(@rnd:style, "formal"))'>
        <!-- TODO: check that no non-metadata elements occur before this paragraph -->
      </xsl:when>

      <!-- Metadata elements are handled in rnd:metadata mode -->
      <!-- TODO: check that no non-metadata elements occur before this paragraph -->
      <xsl:when test='&metadata-content;'/>

      <xsl:otherwise>
        <xsl:call-template name='rnd:error'>
          <xsl:with-param name='code'>unknown-style</xsl:with-param>
          <xsl:with-param name='message'>unknown paragraph style "<xsl:value-of select='@rnd:style'/>" encountered</xsl:with-param>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- Determine if the caption belongs to the current figure.
       If so, then process the textobject content and the caption.
    -->
  <xsl:template name='rnd:figure-text-caption'>
    <xsl:param name='caption' select='/..'/>

    <xsl:variable name='textobjs'
      select='following-sibling::*[following-sibling::*[generate-id() = generate-id($caption)]]'/>

    <xsl:choose>
      <xsl:when test='not($caption)'/> <!-- nothing to do -->
      <xsl:when test='$textobjs[self::dbk:informaltable |
                      self::dbk:inlinemediaobject |
                      self::dbk:para[@rnd:style = "informalfigure-imagedata" or
                      @rnd:style = "mediaobject-imagedata"]]'/> <!-- caption belongs to something else -->
      <xsl:otherwise>
        <xsl:apply-templates select='$textobjs' mode='rnd:textobject'/>

        <xsl:apply-templates select='$caption' mode='rnd:caption'/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='dbk:para' mode='rnd:caption'>
    <dbk:caption>
      <xsl:call-template name='rnd:attributes'/>
      <xsl:apply-templates/>
    </dbk:caption>
  </xsl:template>

  <xsl:template name='rnd:imagedata-attributes'>
    <xsl:param name='metadata' select='""'/>

    <xsl:choose>
      <xsl:when test='not($metadata)'/>
      <xsl:when test='contains($metadata, " ")'>
        <xsl:call-template name='rnd:imagedata-attributes'>
          <xsl:with-param name='metadata'
            select='substring-before($metadata, " ")'/>
        </xsl:call-template>
        <xsl:call-template name='rnd:imagedata-attributes'>
          <xsl:with-param name='metadata'
            select='substring-after($metadata, " ")'/>
        </xsl:call-template>
      </xsl:when>
      <xsl:when test='not(contains($metadata, "="))'>
        <xsl:call-template name='rnd:warning'>
          <xsl:with-param name='code' select='"imagedata-missing-value"'/>
          <xsl:with-param name='message'>
            <xsl:text>imagedata-metadata missing value for attribute "</xsl:text>
            <xsl:value-of select='$metadata'/>
            <xsl:text>"</xsl:text>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:variable name='name'
          select='translate(normalize-space(substring-before($metadata, "=")), "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyz")'/>
        <xsl:variable name='value'
          select='translate(normalize-space(substring-after($metadata, "=")), "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyz")'/>

        <xsl:choose>
          <xsl:when test='$name = "scalefit"'>
            <xsl:attribute name='scalefit'>
              <xsl:choose>
                <xsl:when test='$value = "1" or
                                $value = "yes" or
                                $value = "true"'>1</xsl:when>
                <xsl:otherwise>0</xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </xsl:when>
          <xsl:when test='$name = "align" or
                          $name = "contentdepth" or
                          $name = "contentwidth" or
                          $name = "depth" or
                          $name = "scale" or
                          $name = "valign" or
                          $name = "width"'>
            <!-- TODO: check enumerate values-->
            <xsl:attribute name='{$name}'>
              <xsl:value-of select='$value'/>
            </xsl:attribute>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name='rnd:error'>
              <xsl:with-param name='code' select='"imagedata-unknown-attribute"'/>
              <xsl:with-param name='message'>
                <xsl:text>imagedata-metadata unknown attribute "</xsl:text>
                <xsl:value-of select='$name'/>
                <xsl:text>"</xsl:text>
              </xsl:with-param>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='dbk:emphasis'>
    <xsl:choose>
      <xsl:when test='not(@rnd:style) and @role = "italic"'>
	<xsl:copy>
          <xsl:apply-templates mode='rnd:copy'/>
	</xsl:copy>
      </xsl:when>
      <xsl:when test='not(@rnd:style) or @role'>
        <xsl:copy>
          <xsl:call-template name='rnd:attributes'/>
          <xsl:apply-templates mode='rnd:copy'/>
        </xsl:copy>
      </xsl:when>

      <xsl:when test='@rnd:style = preceding-sibling::node()[1][self::dbk:emphasis]/@rnd:style'/>

      <xsl:when test='@rnd:style = "emphasis"'>
        <xsl:copy>
          <xsl:call-template name='rnd:attributes'/>
          <xsl:apply-templates mode='rnd:copy'/>
          <xsl:apply-templates select='following-sibling::node()[1]'
            mode='rnd:emphasis'/>
        </xsl:copy>
      </xsl:when>
      <xsl:when test='@rnd:style = "emphasis-bold" or
                      @rnd:style = "emphasis-strong"'>
        <xsl:copy>
          <xsl:attribute name='role'>bold</xsl:attribute>
          <xsl:call-template name='rnd:attributes'/>
          <xsl:apply-templates mode='rnd:copy'/>
          <xsl:apply-templates select='following-sibling::node()[1]'
            mode='rnd:emphasis'/>
        </xsl:copy>
      </xsl:when>
      <xsl:when test='@rnd:style = "emphasis-underline"'>
        <xsl:copy>
          <xsl:attribute name='role'>underline</xsl:attribute>
          <xsl:call-template name='rnd:attributes'/>
          <xsl:apply-templates mode='rnd:copy'/>
          <xsl:apply-templates select='following-sibling::node()[1]'
            mode='rnd:emphasis'/>
        </xsl:copy>
      </xsl:when>

      <xsl:when test='@rnd:style = "citetitle" or
                      @rnd:style = "literal" or
                      @rnd:style = "sgmltag"'>
        <xsl:element name='{@rnd:style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:call-template name='rnd:attributes'/>
          <xsl:apply-templates/>
          <xsl:apply-templates select='following-sibling::node()[1]'
            mode='rnd:emphasis'/>
        </xsl:element>
      </xsl:when>

      <xsl:when test='@rnd:style = "Hyperlink" and
		      parent::dbk:link'>
	<!-- This occurs in a hyperlink; parent should be dbk:link -->
	<xsl:apply-templates/>
      </xsl:when>
      <xsl:when test='@rnd:style = "Hyperlink"'>
        <!-- dbk:link is missing -->
        <dbk:link xlink:href='{.}'>
          <xsl:apply-templates/>
        </dbk:link>
      </xsl:when>

      <!-- In rare circumstances, Word inserts an empty element that appears to be something like a space in the editor -->
      <xsl:when test='(@rnd:style = "EndnoteReference" or
                      @rnd:style = "FootnoteReference") and
                      . = ""'>
        <xsl:text> </xsl:text>
      </xsl:when>

      <xsl:otherwise>
        <xsl:call-template name='rnd:error'>
          <xsl:with-param name='code'>unknown-style</xsl:with-param>
          <xsl:with-param name='message'>unknown character span style "<xsl:value-of select='@rnd:style'/>" encountered</xsl:with-param>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!-- Coalesce emphasis elements into a single element -->
  <xsl:template match='dbk:emphasis' mode='rnd:emphasis'>
    <xsl:choose>
      <xsl:when test='@rnd:style = preceding-sibling::node()[self::dbk:emphasis]/@rnd:style'>
        <xsl:apply-templates/>
        <xsl:apply-templates select='following-sibling::node()[1]'
          mode='rnd:emphasis'/>
      </xsl:when>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='*|text()' mode='rnd:emphasis'/>

  <xsl:template match='dbk:emphasis' mode='rnd:personname-emphasis'>
    <xsl:param name='style'/>

    <xsl:choose>
      <xsl:when test='@rnd:style = $style'>
        <xsl:apply-templates/>
        <xsl:apply-templates select='following-sibling::dbk:emphasis[1]'
          mode='rnd:personname-emphasis'>
          <xsl:with-param name='style' select='$style'/>
        </xsl:apply-templates>
      </xsl:when>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='*|text()' mode='rnd:emphasis'/>

  <xsl:template match='dbk:subscript|dbk:superscript'>
    <xsl:copy>
      <xsl:apply-templates select='@*' mode='rnd:copy'/>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- Images -->

  <xsl:template match='dbk:inlinemediaobject'>
    <xsl:choose>
      <xsl:when test='not(preceding-sibling::*|following-sibling::*) and
		      normalize-space(..) = ""'>

	<xsl:variable name='next.captioned'
		      select='ancestor::dbk:para/following-sibling::*[self::dbk:informaltable or self::dbk:para[dbk:inlinemediaobject and count(*) = 1 and normalize-space() = ""]][1]'/>

	<xsl:variable name='caption'
		      select='ancestor::dbk:para/following-sibling::dbk:para[@rnd:style = "caption" or @rnd:style = "Caption"]'/>

	<xsl:variable name='metadata'>
	  <xsl:apply-templates select='ancestor::dbk:para/following-sibling::*[1]'
			       mode='rnd:metadata'/>
	</xsl:variable>

	<dbk:figure>
	  <xsl:if test='ancestor::dbk:para/preceding-sibling::*[1][self::dbk:para][@rnd:style = "figure-title"] or
			$metadata'>
	    <dbk:info>
	      <xsl:if test='ancestor::dbk:para/preceding-sibling::*[1][self::dbk:para][@rnd:style = "figure-title"]'>
		<dbk:title>
		  <xsl:apply-templates select='ancestor::dbk:para/preceding-sibling::*[1]/node()'/>
		</dbk:title>
	      </xsl:if>
	      <xsl:copy-of select='$metadata'/>
	    </dbk:info>
	  </xsl:if>

	  <dbk:mediaobject>
	    <xsl:apply-templates mode='rnd:copy'/>
	  </dbk:mediaobject>

	  <xsl:choose>
	    <xsl:when test='not($caption)'/>
	    <xsl:when test='not($next.captioned)'>
	      <xsl:apply-templates select='ancestor::dbk:para/following-sibling::*[following-sibling::*[generate-id() = generate-id($caption)]][not(&metadata-content;)]'
				   mode='rnd:figure'/>
	      <xsl:apply-templates select='$caption'
				   mode='rnd:caption'/>
	    </xsl:when>
	    <!-- Does caption belong to this image or next.captioned?
	       - Only if it belongs to this image do we process it here.
	      -->
	    <xsl:when test='$next.captioned[preceding-sibling::*[generate-id() = generate-id($caption)]]'>
	      <xsl:apply-templates select='ancestor::dbk:para/following-sibling::*[following-sibling::*[generate-id() = generate-id($caption)]][not(&metadata-content;)]'
				   mode='rnd:figure'/>
	      <xsl:apply-templates select='$caption'
				   mode='rnd:caption'/>
	    </xsl:when>
	    <!-- otherwise caption does not belong to this figure -->
	  </xsl:choose>
	</dbk:figure>
      </xsl:when>
      <xsl:otherwise>
	<xsl:call-template name='rnd:copy'/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='dbk:para[@rnd:style = "caption" or @rnd:style = "Caption"]'
    mode='rnd:caption'>
    <dbk:caption>
      <dbk:para>
	<xsl:apply-templates/>
      </dbk:para>
    </dbk:caption>
  </xsl:template>
  <xsl:template match='*' mode='rnd:caption'/>

  <xsl:template match='*' mode='rnd:figure'>
    <xsl:call-template name='rnd:para'>
      <xsl:with-param name='suppress' select='false()'/>
    </xsl:call-template>
  </xsl:template>

  <!-- Sidebars -->

  <xsl:template match='*' mode='rnd:sidebar'>
    <xsl:call-template name='rnd:para'>
      <xsl:with-param name='suppress' select='false()'/>
    </xsl:call-template>
  </xsl:template>

  <!-- Lists -->

  <xsl:template match='dbk:para' mode='rnd:listitem'>
    <dbk:listitem>
      <dbk:para>
        <xsl:call-template name='rnd:attributes'/>
        <xsl:apply-templates/>
      </dbk:para>
      <xsl:apply-templates select='following-sibling::*[1]'
        mode='rnd:continue'/>

      <!-- Handle nested lists -->
      <xsl:variable name='list-type'
        select='concat(substring-before(@rnd:style, "list"), "list")'/>
      <xsl:variable name='list-level'
        select='substring-after(@rnd:style, $list-type)'/>

      <!-- Assuming only five levels of list nesting.
         - This is probably better done in a previous stage using grouping.
        -->
      <xsl:variable name='stop.node'
        select='following-sibling::dbk:para[@rnd:style != concat("itemizedlist", $list-level + 1) and
                @rnd:style != concat("orderedlist", $list-level + 1) and
                @rnd:style != concat("itemizedlist", $list-level + 2) and
                @rnd:style != concat("orderedlist", $list-level + 2) and
                @rnd:style != concat("itemizedlist", $list-level + 3) and
                @rnd:style != concat("orderedlist", $list-level + 3) and
                @rnd:style != "para-continue"][1]'/>

      <xsl:variable name='nested'
        select='following-sibling::dbk:para[@rnd:style = concat("itemizedlist", $list-level + 1) or @rnd:style = concat("orderedlist", $list-level + 1)][1]'/>

      <xsl:choose>
        <!-- Is there a nested list at all? -->
        <xsl:when test='following-sibling::*[self::dbk:para and @rnd:style != "para-continue"][1][@rnd:style != concat("itemizedlist", $list-level + 1) and @rnd:style != concat("orderedlist", $list-level + 1)]'/>

        <xsl:when test='following-sibling::dbk:para[@rnd:style = concat("itemizedlist", $list-level + 1) or @rnd:style = concat("orderedlist", $list-level + 1)] and
                        $stop.node'>
          <xsl:element name='{concat(substring-before($nested/@rnd:style, "list"), "list")}'
            namespace='http://docbook.org/ns/docbook'>
            <xsl:apply-templates select='following-sibling::dbk:para[@rnd:style = concat("itemizedlist", $list-level + 1) or @rnd:style = concat("orderedlist", $list-level + 1)][following-sibling::*[generate-id() = generate-id($stop.node)]]'
              mode='rnd:listitem'/>
          </xsl:element>
        </xsl:when>
        <xsl:when test='following-sibling::dbk:para[@rnd:style = concat("itemizedlist", $list-level + 1) or @rnd:style = concat("orderedlist", $list-level + 1)]'>

          <xsl:element name='{concat(substring-before($nested/@rnd:style, "list"), "list")}'
            namespace='http://docbook.org/ns/docbook'>
            <xsl:apply-templates select='following-sibling::dbk:para[@rnd:style = concat("itemizedlist", $list-level + 1) or @rnd:style = concat("orderedlist", $list-level + 1)]'
              mode='rnd:listitem'/>
          </xsl:element>
        </xsl:when>
      </xsl:choose>
    </dbk:listitem>
  </xsl:template>

  <!-- Blockquotes -->

  <xsl:template match='dbk:para' mode='rnd:blockquote'>
    <xsl:choose>
      <xsl:when test='@rnd:style ="blockquote-attribution"'/>
      <xsl:when test='@rnd:style ="blockquote-title"'/>
      <xsl:otherwise>
        <dbk:para>
          <xsl:apply-templates/>
        </dbk:para>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='dbk:para' mode='rnd:blockquote-attribution'>
    <xsl:if test='@rnd:style ="blockquote-attribution"'>
      <dbk:attribution>
        <xsl:apply-templates/>
      </dbk:attribution>
    </xsl:if>
  </xsl:template>

  <!-- Metadata -->

  <xsl:template match='dbk:para' mode='rnd:metadata'>
    <xsl:choose>
      <xsl:when test='@rnd:style = "biblioentry-title" and
                      parent::dbk:bibliography|parent::dbk:bibliodiv'/>
      <xsl:when test='@rnd:style = "biblioentry-title"'>
        <xsl:call-template name='rnd:error'>
          <xsl:with-param name='code'>bad-metadata</xsl:with-param>
          <xsl:with-param name='message'>style "<xsl:value-of select='@rnd:style'/>" must not be metadata for parent "<xsl:value-of select='local-name(..)'/>"</xsl:with-param>
        </xsl:call-template>
      </xsl:when>

      <xsl:when test='@rnd:style = "abstract-title" or
                      @rnd:style = "abstract"'>
        <xsl:variable name='stop.node'
          select='following-sibling::dbk:para[@rnd:style != "abstract"][1]'/>
        <xsl:choose>
          <xsl:when test='$stop.node'>
            <dbk:abstract>
              <xsl:apply-templates select='.|following-sibling::dbk:para[@rnd:style = "abstract"][following-sibling::*[generate-id() = generate-id($stop.node)]]'
                mode='rnd:abstract'/>
            </dbk:abstract>
            <xsl:apply-templates select='$stop.node'
              mode='rnd:metadata'/>
          </xsl:when>
          <xsl:otherwise>
            <dbk:abstract>
              <xsl:apply-templates select='.|following-sibling::dbk:para[@rnd:style = "abstract"]' mode='rnd:abstract'/>
            </dbk:abstract>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:when test='@rnd:style = "legalnotice"'>
        <xsl:variable name='stop.node'
          select='following-sibling::dbk:para[@rnd:style != "legalnotice"][1]'/>

        <xsl:choose>
          <xsl:when test='$stop.node'>
            <dbk:legalnotice>
              <xsl:apply-templates select='.|following-sibling::dbk:para[@rnd:style = "legalnotice"][following-sibling::*[generate-id() = generate-id($stop.node)]]'
                mode='rnd:legalnotice'/>
            </dbk:legalnotice>
            <xsl:apply-templates select='$stop.node'
              mode='rnd:metadata'/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select='.|following-sibling::dbk:para[@rnd:style = "legalnotice"]'
              mode='rnd:legalnotice'/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:when test='@rnd:style = "keyword"'>
        <xsl:variable name='stop.node'
          select='following-sibling::*[not(self::dbk:para) or
                  (self::dbk:para and @rnd:style != "keyword")][1]'/>

        <xsl:choose>
          <xsl:when test='$stop.node'>
            <dbk:keywordset>
              <xsl:call-template name='rnd:keyword'>
                <xsl:with-param name='nodes'
                  select='.|following-sibling::dbk:para[@rnd:style = "keyword"][following-sibling::*[generate-id() = generate-id($stop.node)]]'/>
              </xsl:call-template>
            </dbk:keywordset>
            <xsl:apply-templates select='$stop.node'
              mode='rnd:metadata'/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name='rnd:keyword'>
              <xsl:with-param name='nodes'
                select='.|following-sibling::dbk:para[@rnd:style = "keyword"]'/>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:when test='@rnd:style = "author"'>
        <dbk:author>
          <xsl:choose>
            <xsl:when test='dbk:emphasis[@rnd:style = "orgname"]'>
              <dbk:orgname>
                <xsl:apply-templates
                  select='dbk:emphasis[@rnd:style = "orgname"]'
                  mode='rnd:orgname'/>
              </dbk:orgname>
              <xsl:if test='*[not(@rnd:style = "orgname")]'>
                <xsl:call-template name='rnd:error'>
                  <xsl:with-param name='code'>bad-author-orgname-combo</xsl:with-param>
                  <xsl:with-param name='message'>character span "<xsl:value-of select='dbk:emphasis[@rnd:style != "orgname"][1]/@rnd:style'/>" not allowed in an author paragraph combined with orgname</xsl:with-param>
                </xsl:call-template>
              </xsl:if>
            </xsl:when>
            <xsl:otherwise>
              <dbk:personname>
                <!-- TODO: check style of author; mixed content or structured -->
                <xsl:apply-templates mode='rnd:personname'/>
              </dbk:personname>
            </xsl:otherwise>
          </xsl:choose>
          <xsl:apply-templates mode='rnd:author-personblurb'/>
          <xsl:apply-templates select='following-sibling::*[1]'
            mode='rnd:author'/>
        </dbk:author>
        <xsl:call-template name='rnd:resume-metadata'>
	  <xsl:with-param name='node' select='following-sibling::*[1]'/>
	</xsl:call-template>
      </xsl:when>
      <xsl:when test='@rnd:style = "personblurb" or
                      @rnd:style = "address" or
                      @rnd:style = "affiliation" or
                      @rnd:style = "contrib" or
                      @rnd:style = "email"'/>

      <xsl:when test='@rnd:style = "releaseinfo" or
                      @rnd:style = "date" or
                      @rnd:style = "pubdate" or
                      @rnd:style = "pagenums" or
                      @rnd:style = "issuenum" or
                      @rnd:style = "volumenum" or
                      @rnd:style = "edition" or
                      @rnd:style = "editor" or
                      @rnd:style = "othercredit" or
                      @rnd:style = "biblioid" or
                      @rnd:style = "bibliosource" or
                      @rnd:style = "bibliomisc" or
                      @rnd:style = "revhistory" or
                      @rnd:style = "revision"'>
        <xsl:element name='{@rnd:style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:apply-templates mode='rnd:metadata'/>
        </xsl:element>
        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:metadata'/>
      </xsl:when>
      <xsl:when test='contains(@rnd:style, "-titleabbrev")'>
        <xsl:variable name='parent'
          select='substring-before(@rnd:style, "-titleabbrev")'/>

        <xsl:choose>
          <xsl:when test='$parent = local-name(..)'>
            <dbk:titleabbrev>
              <xsl:apply-templates mode='rnd:metadata'/>
            </dbk:titleabbrev>
          </xsl:when>
          <xsl:when test='(parent::dbk:article or parent::dbk:book-component) and
                          preceding-sibling::dbk:para[@rnd:style = concat($parent, "-title")]'>
            <dbk:titleabbrev>
              <xsl:apply-templates mode='rnd:metadata'/>
            </dbk:titleabbrev>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name='rnd:error'>
              <xsl:with-param name='code'>bad-titleabbrev</xsl:with-param>
              <xsl:with-param name='message'>titleabbrev style "<xsl:value-of select='@rnd:style'/>" mismatches parent "<xsl:value-of select='local-name(..)'/>"</xsl:with-param>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>

        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:metadata'/>
      </xsl:when>
      <xsl:when test='contains(@rnd:style, "-title")'>
        <xsl:variable name='parent'
          select='substring-before(@rnd:style, "-title")'/>

        <xsl:choose>
          <xsl:when test='$parent = "table" or
                          $parent = "figure"'>
            <dbk:title>
              <xsl:apply-templates mode='rnd:metadata'/>
            </dbk:title>
          </xsl:when>
          <xsl:when test='$parent = local-name(..)'>
            <dbk:title>
              <xsl:apply-templates mode='rnd:metadata'/>
            </dbk:title>
          </xsl:when>
          <xsl:when test='parent::dbk:book-component'>
            <dbk:title>
              <xsl:apply-templates mode='rnd:metadata'/>
            </dbk:title>
          </xsl:when>
          <xsl:when test='parent::dbk:article and
                          not(../../..)'>
            <dbk:title>
              <xsl:apply-templates mode='rnd:metadata'/>
            </dbk:title>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name='rnd:error'>
              <xsl:with-param name='code'>bad-title</xsl:with-param>
              <xsl:with-param name='message'>title style "<xsl:value-of select='@rnd:style'/>" mismatches parent "<xsl:value-of select='local-name(..)'/>"</xsl:with-param>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>

        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:metadata'/>
      </xsl:when>

      <!-- Exception to normal subtitle handling is biblioentry-subtitle -->
      <xsl:when test='@rnd:style = "biblioentry-subtitle"'>
        <!-- TODO: check that this is in a biblioentry -->
        <dbk:subtitle>
          <xsl:apply-templates mode='rnd:metadata'/>
        </dbk:subtitle>

        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:metadata'/>
      </xsl:when>
      <xsl:when test='contains(@rnd:style, "-subtitle")'>
        <xsl:variable name='parent'
          select='substring-before(@rnd:style, "-subtitle")'/>

        <xsl:choose>
          <xsl:when test='$parent = local-name(..)'>
            <dbk:subtitle>
              <xsl:apply-templates mode='rnd:metadata'/>
            </dbk:subtitle>
          </xsl:when>
          <xsl:when test='(parent::dbk:article or parent::dbk:book-component) and
                          preceding-sibling::dbk:para[@rnd:style = concat($parent, "-title")]'>
            <dbk:subtitle>
              <xsl:apply-templates mode='rnd:metadata'/>
            </dbk:subtitle>
          </xsl:when>
          <xsl:otherwise>
            <xsl:call-template name='rnd:error'>
              <xsl:with-param name='code'>bad-subtitle</xsl:with-param>
              <xsl:with-param name='message'>subtitle style "<xsl:value-of select='@rnd:style'/>" mismatches parent "<xsl:value-of select='local-name(..)'/>"</xsl:with-param>
            </xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>

        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:metadata'/>
      </xsl:when>

      <xsl:when test='@rnd:style = "publisher-address" and
                      preceding-sibling::*[1][not(self::dbk:para) or not(@rnd:style = "publisher")]'>
        <xsl:call-template name='rnd:error'>
          <xsl:with-param name='code'>bad-publisher-address</xsl:with-param>
          <xsl:with-param name='message'>publisher-address must follow publisher</xsl:with-param>
        </xsl:call-template>
        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:metadata'/>
      </xsl:when>
      <xsl:when test='@rnd:style = "publisher-address"'>
        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:metadata'/>
      </xsl:when>
      <xsl:when test='@rnd:style = "publisher"'>
        <dbk:publisher>
          <dbk:publishername>
            <xsl:apply-templates/>
          </dbk:publishername>
          <xsl:if test='following-sibling::*[1][@rnd:style = "publisher-address"]'>
            <xsl:apply-templates select='following-sibling::*[1]'
              mode='rnd:publisher'/>
          </xsl:if>
        </dbk:publisher>

        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:metadata'/>
      </xsl:when>
    </xsl:choose>
  </xsl:template>

  <xsl:template name='rnd:keyword'>
    <xsl:param name='nodes' select='/..'/>

    <xsl:choose>
      <xsl:when test='not($nodes)'/>
      <xsl:otherwise>
        <xsl:call-template name='rnd:keyword-phrases'>
          <xsl:with-param name='text' select='$nodes[1]'/>
        </xsl:call-template>
        <xsl:call-template name='rnd:keyword'>
          <xsl:with-param name='nodes' select='$nodes[position() != 1]'/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template name='rnd:keyword-phrases'>
    <xsl:param name='text'/>

    <xsl:choose>
      <xsl:when test='not($text)'/>
      <xsl:when test='contains($text, ",")'>
        <dbk:keyword>
          <xsl:value-of select='normalize-space(substring-before($text, ","))'/>
        </dbk:keyword>
        <xsl:call-template name='rnd:keyword-phrases'>
          <xsl:with-param name='text' select='substring-after($text, ",")'/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <dbk:keyword>
          <xsl:value-of select='normalize-space($text)'/>
        </dbk:keyword>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='dbk:emphasis' mode='rnd:metadata'>
    <xsl:choose>
      <xsl:when test='not(@rnd:style)'>
        <xsl:copy>
          <xsl:apply-templates mode='rnd:metadata'/>
        </xsl:copy>
      </xsl:when>
      <xsl:when test='@rnd:style = "Hyperlink" and
                      parent::dbk:link'>
        <xsl:apply-templates mode='rnd:metadata'/>
      </xsl:when>
      <xsl:when test='@rnd:style = "Hyperlink"'>
        <dbk:link xlink:href='{.}'>
          <xsl:apply-templates mode='rnd:metadata'/>
        </dbk:link>
      </xsl:when>
      <xsl:otherwise>
        <xsl:element name='{@rnd:style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:apply-templates mode='rnd:metadata'/>
        </xsl:element>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='dbk:link' mode='rnd:metadata'>
    <xsl:copy>
      <xsl:apply-templates select='@*' mode='rnd:copy'/>
      <xsl:apply-templates mode='rnd:metadata'/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match='dbk:inlinemediaobject' mode='rnd:metadata'>
    <xsl:call-template name='rnd:copy'/>
  </xsl:template>
  <xsl:template match='*' mode='rnd:metadata'/>

  <xsl:template name='rnd:resume-metadata'>
    <xsl:param name='node' select='/..'/>

    <xsl:choose>
      <xsl:when test='$node[self::dbk:para][&author-content;]'>
	<xsl:call-template name='rnd:resume-metadata'>
	  <xsl:with-param name='node' select='$node/following-sibling::*[1]'/>
	</xsl:call-template>
      </xsl:when>
      <xsl:when test='$node[self::dbk:para][&metadata-content;]'>
	<xsl:apply-templates select='$node' mode='rnd:metadata'/>
      </xsl:when>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='dbk:para' mode='rnd:abstract'>
    <xsl:choose>
      <xsl:when test='@rnd:style = "abstract-title"'>
        <dbk:title>
          <xsl:call-template name='rnd:attributes'/>
          <xsl:apply-templates/>
        </dbk:title>
      </xsl:when>
      <xsl:otherwise>
        <dbk:para>
          <xsl:call-template name='rnd:attributes'/>
          <xsl:apply-templates/>
        </dbk:para>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='dbk:para' mode='rnd:legalnotice'>
    <dbk:para>
      <xsl:call-template name='rnd:attributes'/>
      <xsl:apply-templates/>
    </dbk:para>
  </xsl:template>

  <xsl:template match='dbk:footnote' mode='rnd:personname'/>
  <xsl:template match='dbk:emphasis' mode='rnd:personname'>
    <!-- Need to check preceding emphasis for same style,
         but blocks pretty-prints and all text nodes
         are preserved in paragraph content.
      -->
    <xsl:variable name='previous'
      select='preceding-sibling::node()[not(self::text()) or (self::text() and normalize-space() != "")]'/>
    <!--
    <xsl:comment> this style is <xsl:value-of select='@rnd:style'/>, previous style is <xsl:value-of select='$previous[last()]/@rnd:style'/> # previous <xsl:value-of select='count($previous)'/></xsl:comment>
-->
    <xsl:choose>
      <!-- inlines are coalesced -->
      <xsl:when test='@rnd:style = $previous[last()][self::dbk:emphasis]/@rnd:style'/>
      <xsl:when test='@rnd:style = "honorific" or
                      @rnd:style = "firstname" or
                      @rnd:style = "lineage" or
                      @rnd:style = "othername" or
                      @rnd:style = "surname"'>
        <xsl:element name='{@rnd:style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:apply-templates/>
          <xsl:apply-templates select='following-sibling::dbk:emphasis[1]'
            mode='rnd:personname-emphasis'>
            <xsl:with-param name='style' select='@rnd:style'/>
          </xsl:apply-templates>
        </xsl:element>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name='rnd:error'>
          <xsl:with-param name='code'>bad-author-inline</xsl:with-param>
          <xsl:with-param name='message'>character span "<xsl:value-of select='@rnd:style'/>" not allowed in an author paragraph</xsl:with-param>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='text()' mode='rnd:author-personblurb'/>
  <xsl:template match='dbk:emphasis' mode='rnd:author-personblurb'/>
  <xsl:template match='dbk:footnote' mode='rnd:author-personblurb'>
    <dbk:personblurb>
      <dbk:para>
        <xsl:copy>
          <xsl:apply-templates select='@*' mode='rnd:copy'/>
          <xsl:apply-templates/>
        </xsl:copy>
      </dbk:para>
    </dbk:personblurb>
  </xsl:template>

  <xsl:template match='dbk:para' mode='rnd:author'>
    <xsl:choose>
      <xsl:when test='@rnd:style = "personblurb" and
                      preceding-sibling::*[1][self::dbk:para and @rnd:style != "personblurb"]'>
        <dbk:personblurb>
          <xsl:apply-templates select='.'
            mode='rnd:personblurb'/>
        </dbk:personblurb>
      </xsl:when>
      <xsl:when test='@rnd:style = "personblurb"'>
        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:author'/>
      </xsl:when>

      <!-- Web and mail addresses may appear in a simplified form -->
      <xsl:when test='@rnd:style = "address"'>
        <xsl:choose>
          <xsl:when test='dbk:link and
                          count(dbk:link) = count(*)'>
            <!-- simplified form -->
            <dbk:otheraddr>
              <xsl:apply-templates select='dbk:link'
                mode='rnd:otheraddr'/>
            </dbk:otheraddr>
          </xsl:when>
          <xsl:otherwise>
            <dbk:address>
              <xsl:apply-templates mode='rnd:author'/>
            </dbk:address>
            <xsl:apply-templates select='following-sibling::*[1]'
              mode='rnd:author'/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>

      <xsl:when test='@rnd:style = "affiliation"'>
	<dbk:affiliation>
	  <xsl:choose>
	    <xsl:when test='not(*)'>
              <dbk:jobtitle>
		<xsl:apply-templates mode='rnd:author'/>
	      </dbk:jobtitle>
	    </xsl:when>
	    <xsl:otherwise>
              <xsl:apply-templates mode='rnd:author'/>
	    </xsl:otherwise>
	  </xsl:choose>
	</dbk:affiliation>
	<xsl:apply-templates select='following-sibling::*[1]'
			     mode='rnd:author'/>
      </xsl:when>
      <xsl:when test='@rnd:style = "contrib" or
                      @rnd:style = "email"'>
        <xsl:element name='{@rnd:style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:apply-templates mode='rnd:author'/>
        </xsl:element>
        <xsl:apply-templates select='following-sibling::*[1]'
          mode='rnd:author'/>
      </xsl:when>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='dbk:link' mode='rnd:otheraddr'>
    <xsl:copy>
      <xsl:apply-templates select='@*' mode='rnd:copy'/>
      <xsl:apply-templates mode='rnd:otheraddr'/>
    </xsl:copy>
  </xsl:template>

  <!-- TODO: not all of these inlines are allowed in all elements that are children of author.
       Need to further refine validation of inlines.
    -->
  <xsl:template match='dbk:emphasis' mode='rnd:author'>
    <xsl:choose>
      <xsl:when test='@rnd:style = "city" or
                      @rnd:style = "country" or
                      @rnd:style = "email" or
                      @rnd:style = "fax" or
                      @rnd:style = "jobtitle" or
                      @rnd:style = "orgdiv" or
                      @rnd:style = "orgname" or
                      @rnd:style = "otheraddr" or
                      @rnd:style = "phone" or
                      @rnd:style = "pob" or
                      @rnd:style = "postcode" or
                      @rnd:style = "shortaffil" or
                      @rnd:style = "state" or
                      @rnd:style = "street"'>
        <xsl:element name='{@rnd:style}'
          namespace='http://docbook.org/ns/docbook'>
          <xsl:apply-templates/>
        </xsl:element>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name='rnd:error'>
          <xsl:with-param name='code'>metadata-bad-inline</xsl:with-param>
          <xsl:with-param name='message'>character span "<xsl:value-of select='@rnd:style'/>" not allowed in author metadata</xsl:with-param>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match='dbk:para' mode='rnd:personblurb'>
    <xsl:if test='@rnd:style = "personblurb"'>
      <dbk:para>
        <xsl:apply-templates/>
      </dbk:para>
      <xsl:apply-templates select='following-sibling::*[1]'
        mode='rnd:personblurb'/>
    </xsl:if>
  </xsl:template>

  <xsl:template match='dbk:para' mode='rnd:publisher'>
    <xsl:if test='@rnd:style = "publisher-address"'>
      <dbk:address>
        <xsl:apply-templates/>
      </dbk:address>
    </xsl:if>
  </xsl:template>

  <xsl:template match='dbk:para' mode='rnd:programlisting'>
    <xsl:text>&#xa;</xsl:text>
    <xsl:apply-templates/>
  </xsl:template>

  <!-- Continuing paragraphs -->

  <xsl:template match='*' mode='rnd:continue'/>
  <xsl:template match='dbk:para' mode='rnd:continue'>
    <xsl:if test='@rnd:style = "para-continue"'>
      <dbk:para>
        <xsl:call-template name='rnd:attributes'/>
        <xsl:apply-templates/>
      </dbk:para>
      <xsl:apply-templates select='following-sibling::*[1]'
        mode='rnd:continue'/>
    </xsl:if>
  </xsl:template>
  <xsl:template match='dbk:informaltable' mode='rnd:continue'>
    <xsl:apply-templates select='.'>
      <xsl:with-param name='in-list' select='true()'/>
    </xsl:apply-templates>
    <xsl:apply-templates select='following-sibling::*[1]'
      mode='rnd:continue'/>
  </xsl:template>

  <!-- Tables -->

  <xsl:template match='dbk:informaltable'>
    <xsl:param name='in-list' select='false()'/>

    <xsl:choose>
      <xsl:when test='not($in-list) and
                      preceding-sibling::dbk:para[1][starts-with(@rnd:style, "itemizedlist") or starts-with(@rnd:style, "orderedlist") or @rnd:style = "para-continue"]'/>
      <xsl:when test='preceding-sibling::*[1][self::dbk:para][@rnd:style ="table-title"]'>
	<dbk:table>
	  <xsl:apply-templates select='@*' mode='rnd:copy'/>

	  <dbk:info>
	    <xsl:apply-templates select='preceding-sibling::dbk:para[1]'
				 mode='rnd:table-title'/>
	  </dbk:info>

	  <xsl:call-template name='rnd:table-textobject'/>

	  <xsl:apply-templates/>

	  <xsl:call-template name='rnd:table-caption'/>
	</dbk:table>
      </xsl:when>
      <xsl:otherwise>
	<xsl:copy>
	  <xsl:apply-templates select='@*' mode='rnd:copy'/>

	  <xsl:call-template name='rnd:table-textobject'/>

	  <xsl:apply-templates/>

	  <xsl:call-template name='rnd:table-caption'/>
	</xsl:copy>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='dbk:tgroup'>
    <xsl:copy>
      <xsl:apply-templates select='@*' mode='rnd:copy'/>
      <xsl:if test='not(@cols)'>
        <xsl:attribute name='cols'>
          <xsl:call-template name='dbk:max-columns'>
            <xsl:with-param name='rows' select='*/dbk:row'/>
          </xsl:call-template>
        </xsl:attribute>
      </xsl:if>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template name='dbk:max-columns'>
    <xsl:param name='rows' select='/..'/>
    <xsl:param name='max' select='0'/>

    <!-- This is a tail-recursive algorithm.
         Could improve this with other algorithm(s),
         eg. divide-and-conquer.
      -->

    <xsl:choose>
      <xsl:when test='not($rows)'>
        <xsl:value-of select='$max'/>
      </xsl:when>
      <xsl:when test='count($rows[1]/dbk:entry) > $max'>
        <xsl:call-template name='dbk:max-columns'>
          <xsl:with-param name='rows' select='$rows[position() != 1]'/>
          <xsl:with-param name='max' select='count($rows[1]/dbk:entry)'/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name='dbk:max-columns'>
          <xsl:with-param name='rows' select='$rows[position() != 1]'/>
          <xsl:with-param name='max' select='$max'/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match='dbk:tbody|dbk:thead|dbk:tfoot|dbk:row|dbk:colspec'>
    <xsl:copy>
      <xsl:apply-templates select='@*' mode='rnd:copy'/>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match='dbk:entry'>
    <dbk:entry>
      <xsl:apply-templates select='@*' mode='rnd:copy'/>
      <xsl:apply-templates/>
    </dbk:entry>
  </xsl:template>

  <xsl:template match='dbk:para' mode='rnd:table-title'>
    <dbk:title>
      <xsl:apply-templates/>
    </dbk:title>
  </xsl:template>

  <!-- Find the caption associated with this table -->
  <xsl:template name='rnd:table-caption'>
    <xsl:variable name='candidate'
		  select='following-sibling::dbk:para[@rnd:style = "caption" or @rnd:style = "Caption"][1]'/>

    <xsl:if test='$candidate != "" and
		  generate-id($candidate/preceding-sibling::dbk:informaltable[1]) = generate-id(.)'>
      <dbk:caption>
	<dbk:para>
	  <xsl:apply-templates select='$candidate/node()'/>
	</dbk:para>
      </dbk:caption>
    </xsl:if>
  </xsl:template>

  <!-- Find table associated text -->
  <xsl:template name='rnd:table-textobject'>
    <xsl:variable name='caption'
		  select='following-sibling::dbk:para[@rnd:style = "caption" or @rnd:style = "Caption"][1]'/>

    <xsl:if test='generate-id($caption/preceding-sibling::dbk:informaltable[1]) = generate-id(.)'>
      <xsl:variable name='content'
		    select='following-sibling::*[following-sibling::*[generate-id($caption) = generate-id()]]'/>
      <xsl:if test='$content'>
	<dbk:textobject>
	  <xsl:apply-templates select='$content' mode='rnd:textobject'/>
	</dbk:textobject>
      </xsl:if>
    </xsl:if>
  </xsl:template>
  <xsl:template match='dbk:para' mode='rnd:textobject'>
    <xsl:call-template name='rnd:para'>
      <xsl:with-param name='suppress' select='false()'/>
    </xsl:call-template>
  </xsl:template>

  <!-- Footnotes -->
  <xsl:template match='dbk:footnote'>
    <xsl:copy>
      <xsl:apply-templates select='@*' mode='rnd:copy'/>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>

  <!-- utilities -->

  <!-- rnd:attributes reconstitutes an element's attributes -->
  <xsl:template name='rnd:attributes'>
    <xsl:param name='node' select='.'/>

    <xsl:apply-templates select='$node/@*[namespace-uri() != "http://docbook.org/ns/docbook/roundtrip"]' mode='rnd:copy'/>
  </xsl:template>

  <xsl:template match='*' name='rnd:copy' mode='rnd:copy'>
    <xsl:copy>
      <xsl:apply-templates select='@*' mode='rnd:copy'/>
      <xsl:apply-templates mode='rnd:copy'/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match='@*' mode='rnd:copy'>
    <xsl:copy/>
  </xsl:template>

  <!-- These templates are invoked whenever an error condition is detected in the conversion of a document.
    -->
  <xsl:template name='rnd:error'>
    <xsl:param name='node' select='.'/>
    <xsl:param name='code'/>
    <xsl:param name='message'/>

    <xsl:comment><xsl:value-of select='$message'/></xsl:comment>
    <xsl:message>ERROR "<xsl:value-of select='$code'/>": <xsl:value-of select='$message'/></xsl:message>
    <rnd:error>
      <rnd:code>
        <xsl:value-of select='$code'/>
      </rnd:code>
      <rnd:message>
        <xsl:value-of select='$message'/>
      </rnd:message>
    </rnd:error>
  </xsl:template>
  <xsl:template name='rnd:warning'>
    <xsl:param name='node' select='.'/>
    <xsl:param name='code'/>
    <xsl:param name='message'/>

    <xsl:comment><xsl:value-of select='$message'/></xsl:comment>
    <xsl:message>WARNING "<xsl:value-of select='$code'/>": <xsl:value-of select='$message'/></xsl:message>
    <rnd:warning>
      <rnd:code>
        <xsl:value-of select='$code'/>
      </rnd:code>
      <rnd:message>
        <xsl:value-of select='$message'/>
      </rnd:message>
    </rnd:warning>
  </xsl:template>

</xsl:stylesheet>
