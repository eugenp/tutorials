<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <!-- Define the layout for the PDF -->
    <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4"
                               page-width="8.5in" page-height="11in" margin-top="0.5in"
                               margin-bottom="0.5in" margin-left="0.5in" margin-right="0.5in">
            <fo:region-body margin-top="0.5in" margin-bottom="0.5in"/>
        </fo:simple-page-master>
    </fo:layout-master-set>

    <!-- Define the content of the PDF -->
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="18pt" font-weight="bold" text-align="center">
                        Bookstore Inventory
                    </fo:block>
                    <fo:table table-layout="fixed" width="100%">
                        <fo:table-column column-width="20%"/>
                        <fo:table-column column-width="20%"/>
                        <fo:table-column column-width="20%"/>
                        <fo:table-column column-width="20%"/>
                        <fo:table-header>
                            <fo:table-row>
                                <fo:table-cell border="solid 1px" padding="5pt" background-color="#CCCCCC">
                                    <fo:block>Bookstore ID</fo:block>
                                </fo:table-cell>
                                <fo:table-cell border="solid 1px" padding="5pt" background-color="#CCCCCC">
                                    <fo:block>Book ID</fo:block>
                                </fo:table-cell>
                                <fo:table-cell border="solid 1px" padding="5pt" background-color="#CCCCCC">
                                    <fo:block>Title</fo:block>
                                </fo:table-cell>
                                <fo:table-cell border="solid 1px" padding="5pt" background-color="#CCCCCC">
                                    <fo:block>Author</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>
                        <fo:table-body>
                            <xsl:apply-templates select="//Book"/>
                        </fo:table-body>
                    </fo:table>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="Book">
        <fo:table-row>
            <fo:table-cell border="solid 1px" padding="5pt">
                <fo:block><xsl:value-of select="ancestor::Bookstore/@id"/></fo:block>
            </fo:table-cell>
            <fo:table-cell border="solid 1px" padding="5pt">
                <fo:block><xsl:value-of select="@id"/></fo:block>
            </fo:table-cell>
            <fo:table-cell border="solid 1px" padding="5pt">
                <fo:block><xsl:value-of select="Title"/></fo:block>
            </fo:table-cell>
            <fo:table-cell border="solid 1px" padding="5pt">
                <fo:block><xsl:value-of select="Author"/></fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

</xsl:stylesheet>
