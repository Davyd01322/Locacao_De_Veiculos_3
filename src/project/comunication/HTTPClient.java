package project.comunication;

import project.cliente.ClientTransportLayer;

public class HTTPClient{
    private ClientTransportLayer client;
    private int countIdClientMessage;

    public HTTPClient(){
        this.client = new ClientTransportLayer();
        this.countIdClientMessage = 0;
    }

    public String GET(){
        String message = "";
        message += """
                <?xml version="1.0" encoding="UTF-8"?>
                <get>
                    <id>%d</id>
                    <client>client</client>
                    <operation></operation>
                    <data></data>
                    <message></message>
                </get>
                """.formatted(this.countIdClientMessage);
        this.countIdClientMessage += 1;

        this.client.send(message);
        message = new String(this.client.receive());
        return message;
    }

    public void POST(String operation, String data){
        String message = String.format(
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <post>
            <id>%d</id>
            <client>client</client>
            <operation>%s</operation>
            <data>%s</data>
            <message></message>
        </post>
        """, this.countIdClientMessage, operation, data);

        this.countIdClientMessage += 1;
        this.client.send(message);
    }

    public void PUT(String operation, String data){
        String message = "";
        message += String.format(
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <put>
            <id>%d</id>
            <client>client</client>
            <operation>%s</operation>
            <data>%s</data>
            <message></message>
        </put>        
        """, this.countIdClientMessage, operation, data);
        this.countIdClientMessage += 1;
        this.client.send(message);
    }

    public void DELETE(){
        String message = "";
        message += String.format(
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <delete>
            <id>%d</id>
            <client>client</client>
            <operation>delete</operation>
            <data></data>
            <message></message>
        </delete>        
        """, this.countIdClientMessage);
        this.countIdClientMessage += 1;
        this.client.send(message);
    }

    public boolean checkNewQueueContent(){
        String message = "";
        message += """
                <?xml version="1.0" encoding="UTF-8"?>
                <hascontent>
                    <id>%d</id>
                    <client>client</client>
                    <operation>get</operation>
                    <data></data>
                    <message></message>
                </hascontent>
                """.formatted(this.countIdClientMessage);
        this.countIdClientMessage += 1;
        this.client.send(message);

        message = new String(this.client.receive());

        boolean flag = false;
        switch (message) {
            case "yes":
                flag = true;
                break;
            case "no":
                flag = false;
                break;
            default:
                break;
        }
        return flag;
    }
}