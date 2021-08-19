<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tns="http://www.example.com/devices">
    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    <xsl:template match="/">
        <critical>
            <true>
                <devices>
                    <xsl:apply-templates select="//tns:device">
                        <xsl:with-param name="criticalValue" select="'true'"/>
                    </xsl:apply-templates>
                </devices>
            </true>
            <false>
                <devices>
                    <xsl:apply-templates select="//tns:device">
                        <xsl:with-param name="criticalValue" select="'false'"/>
                    </xsl:apply-templates>
                </devices>
            </false>
        </critical>
    </xsl:template>

    <xsl:template match="tns:device">
        <xsl:param name="criticalValue"/>
        <xsl:if test="tns:critical = $criticalValue">
            <device>
                <name>
                    <xsl:value-of select="tns:name"/>
                </name>
                <origin>
                    <xsl:value-of select="tns:origin"/>
                </origin>
                <price>
                    <xsl:value-of select="tns:price"/>
                </price>
                <xsl:apply-templates select="tns:type"/>
            </device>
        </xsl:if>
    </xsl:template>

    <xsl:template match="tns:type">
        <type>
            <peripheral>
                <xsl:value-of select="tns:peripheral"/>
            </peripheral>
            <powerUsage>
                <xsl:value-of select="tns:powerUsage"/>
            </powerUsage>
            <hasCooler>
                <xsl:value-of select="tns:hasCooler"/>
            </hasCooler>
            <ports>
                <xsl:apply-templates select="tns:ports//tns:port"/>
            </ports>
            <components>
                <xsl:apply-templates select="tns:components//tns:component"/>
            </components>
        </type>
    </xsl:template>

    <xsl:template match="tns:port">
        <port>
            <xsl:attribute name="type">
                <xsl:value-of select="@type"/>
            </xsl:attribute>
            <xsl:attribute name="count">
                <xsl:value-of select="@count"/>
            </xsl:attribute>
        </port>
    </xsl:template>

    <xsl:template match="tns:component">
        <component>
            <xsl:attribute name="name">
                <xsl:value-of select="@name"/>
            </xsl:attribute>
        </component>
    </xsl:template>

</xsl:stylesheet>