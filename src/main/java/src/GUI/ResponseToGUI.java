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
                xsltSource = new StreamSource("TablePattern.xsl");
            }
            else
            {
                xsltSource = new StreamSource("NotTablePattern.xsl");
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
