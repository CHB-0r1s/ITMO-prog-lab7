<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/">
        <html>
            <body>
                <table border ="1">
                    <tr>
                        <xsl:for-each select="*/*[1]/*">
                            <th>
                                <xsl:value-of select="name()"/>
                            </th>
                        </xsl:for-each>
                    </tr>
                    <xsl:apply-templates select="*/*"/>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="*">
        <tr>
            <xsl:for-each select="*">
                <td>
                    <xsl:value-of select="."/>
                </td>
            </xsl:for-each>
        </tr>
    </xsl:template>

</xsl:stylesheet>
