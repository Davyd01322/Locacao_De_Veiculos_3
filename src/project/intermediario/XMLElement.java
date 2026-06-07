package project.intermediario;

import java.io.InputStream;

public class XMLElement {
    private XMLParser xml = null;

    private String type = ""; /* GET, POST, PUT */
    private String id = "";
    private String client = "";
    private String operation = "";
    private String data = "";
    private String message = "";

    public XMLElement(InputStream in){
        this.xml = new XMLParser();
        this.xml.setInputStream(in);

        this.type = this.xml.getNomeElementoRaiz();
        this.id = this.xml.getContentByTag("id");
        this.client = this.xml.getContentByTag("client");
        this.operation = this.xml.getContentByTag("operation");
        this.data = this.xml.getContentByTag("data");
        this.message = this.xml.getContentByTag("message");
    }

    public String getType(){
        return this.type;
    }

    public String getId(){
        return this.id;
    }

    public String getClient(){
        return this.client;
    }

    public String getOperation(){
        return this.operation;
    }

    public String getData(){
        return this.data;
    }

    public String getMessage(){
        return this.message;
    }

    public String toString(){
        String message = "";
        message += "Type:" + getType() + ",\n";
        message += "Client:" + getClient() + ",\n";
        message += "Id:" + getId() + ",\n";
        message += "Operation:" + getOperation() + ",\n";
        message += "Data:" + getData() + ",\n";
        message += "Message:" + getMessage();
        return message;
    }

}