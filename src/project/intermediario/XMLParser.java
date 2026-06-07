package project.intermediario;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
    private Document doc = null;
    private DocumentBuilderFactory factory = null;
    private DocumentBuilder builder = null;
    private Element raiz = null;
    private NodeList lista = null;

    public XMLParser(){
        try{
            this.factory = DocumentBuilderFactory.newInstance();
            this.builder = this.factory.newDocumentBuilder();
        }catch(ParserConfigurationException e){
            System.out.println("XMLParser.java[1]");
            System.out.println("ParserConfigurationException: " + e.getLocalizedMessage());
        }
    }
    
    public void setInputStream(InputStream in){
        try {
            this.doc = builder.parse(in);
            this.raiz = doc.getDocumentElement();
        } catch (SAXException e){
            System.out.println("XMLParser.java[2]");
            System.out.println("SAXException: " + e.getLocalizedMessage());
        } catch (IOException e){
            System.out.println("XMLParser.java[2]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        }
    }

    public String getNomeElementoRaiz(){
        return this.raiz.getNodeName();
    }

    public String getOperation(){
        String operation;

        this.lista = this.doc.getElementsByTagName("operation");
        operation = this.lista.item(0).getTextContent();

        return operation;
    }

    public String getContentByTag(String tag){
        String content = "";
        content = this.doc.getElementsByTagName(tag).item(0).getTextContent();

        return content;
    }
}