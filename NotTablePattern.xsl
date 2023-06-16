<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/">
        <html>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="*">
        <tr>
            <xsl:element name="{name()}">
                <xsl:apply-templates/>
            </xsl:element>
        </tr>
    </xsl:template>

</xsl:stylesheet>
