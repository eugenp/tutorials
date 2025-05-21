<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
    <xsl:template match="/">
        <xsl:text>bookstore_id,book_id,category,title,author_id,author_name,price</xsl:text>
        <xsl:text>&#xA;</xsl:text>
        <xsl:for-each select="//Bookstore">
            <xsl:variable name="bookstore_id" select="@id"/>
            <xsl:for-each select="./Books/Book">
                <xsl:variable name="book_id" select="@id"/>
                <xsl:variable name="category" select="@category"/>
                <xsl:variable name="title" select="Title"/>
                <xsl:variable name="author_id" select="Author/@id"/>
                <xsl:variable name="author_name" select="Author"/>
                <xsl:variable name="price" select="Price"/>
                <xsl:value-of select="concat($bookstore_id, ',', $book_id, ',', $category, ',', $title, ',', $author_id, ',', $author_name, ',', $price)"/>
                <xsl:text>&#xA;</xsl:text>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
