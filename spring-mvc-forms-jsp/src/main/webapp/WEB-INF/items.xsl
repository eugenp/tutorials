<?xml version = "1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html" indent="yes"/>
    <xsl:param name="bgColor"/>

    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="items">
        <table border="1" width="40%" bgColor="{$bgColor}">
            <xsl:for-each select="item">
                <tr>
                    <td>
                        <i>
                            <xsl:value-of select="name"/>
                        </i>
                    </td>
                    <td>
                        <xsl:value-of select="category"/>
                    </td>
                    <td>
                        <xsl:value-of select="price"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

</xsl:stylesheet>