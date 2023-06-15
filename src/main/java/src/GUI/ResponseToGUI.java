package src.GUI;

import src.Command.Command;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

public class ResponseToGUI
{
    private static String xml = new String();
    private static String html = new String();
    private static Command command;

    public static void setCommand(Command command)
    {
        ResponseToGUI.command = command;
    }

    public static void setXml(String xmlLine)
    {
        xml = xmlLine;
    }

    public static void setHtml()
    {
        String xmlString = xml;

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            StreamSource xsltSource;
            if(command.getTableType())
            {
                xsltSource = new StreamSource(new StringReader("" +
                        "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                        "  <xsl:output method=\"html\" indent=\"yes\"/>\n" +
                        "  \n" +
                        "  <xsl:template match=\"/\">\n" +
                        "    <html>\n" +
                        "      <body>\n" +
                        "        <table border=\"1\">\n" +
                        "          <xsl:apply-templates/>\n" +
                        "        </table>\n" +
                        "      </body>\n" +
                        "    </html>\n" +
                        "  </xsl:template>\n" +
                        "  \n" +
                        "  <xsl:template match=\"*\">\n" +
                        "    <tr>\n" +
                        "      <xsl:element name=\"{name()}\">\n" +
                        "        <xsl:apply-templates/>\n" +
                        "      </xsl:element>\n" +
                        "    </tr>\n" +
                        "  </xsl:template>\n" +
                        "  \n" +
                        "</xsl:stylesheet>\n"));
            }
            else
            {
                xsltSource = new StreamSource(new StringReader("" +
                        "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                        "  <xsl:output method=\"html\" indent=\"yes\"/>\n" +
                        "  \n" +
                        "  <xsl:template match=\"/\">\n" +
                        "    <html>\n" +
                        "      <body>\n" +
                        "        <table border=\"1\">\n" +
                        "          <xsl:apply-templates/>\n" +
                        "        </table>\n" +
                        "      </body>\n" +
                        "    </html>\n" +
                        "  </xsl:template>\n" +
                        "  \n" +
                        "  <xsl:template match=\"*\">\n" +
                        "    <tr>\n" +
                        "      <xsl:element name=\"{name()}\">\n" +
                        "        <xsl:apply-templates/>\n" +
                        "      </xsl:element>\n" +
                        "    </tr>\n" +
                        "  </xsl:template>\n" +
                        "  \n" +
                        "</xsl:stylesheet>\n"));
            }
            transformer = transformerFactory.newTransformer(xsltSource);

            StringWriter htmlWriter = new StringWriter();
            transformer.transform(new StreamSource(new StringReader(xmlString)), new StreamResult(htmlWriter));
            String htmlString = htmlWriter.toString();

            html = htmlString;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHtml()
    {
        return html;
    }

    public static String getXml()
    {
        return xml;
    }
}
