<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="person[@gender='male']">
        <xsl:element name="p">
            <xsl:text>Male person: </xsl:text>
            <xsl:value-of select="name"/>
            <xsl:text>, Age: </xsl:text>
            <xsl:value-of select="age"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="person[@gender='female']">
        <xsl:element name="p">
            <xsl:text>Female person: </xsl:text>
            <xsl:value-of select="name"/>
            <xsl:text>, Age: </xsl:text>
            <xsl:value-of select="age"/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
