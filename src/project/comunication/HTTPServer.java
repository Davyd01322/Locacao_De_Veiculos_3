package project.comunication;

import project.servidor.ServerTransportLayer;

public class HTTPServer implements HTTPInterface{
    private ServerTransportLayer server;
    private int countIdServerMessage;

    public HTTPServer(){
        this.server = new ServerTransportLayer();
        this.countIdServerMessage = 0;
    }

    @Override
    public String GET(){
        String message = "";
        message += """
                <?xml version="1.0" encoding="UTF-8"?>
                <get>
                    <id>%d</id>
                    <client>server</client>
                    <operation>get</operation>
                    <data></data>
                    <message></message>
                </get>
                """.formatted(this.countIdServerMessage);
        this.countIdServerMessage += 1;

        this.server.send(message);

        message = new String(this.server.receive());
        return message;
    }

    @Override
    public void POST(String body){
        String message = 
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <post>
            <id>%d</id>
            <client>server</client>
            <operation>post</operation>
            <data>%s</data>
            <message></message>
        </post>
        """.formatted(this.countIdServerMessage, body);

        this.countIdServerMessage += 1;
        this.server.send(message);
    }

    @Override
    public void PUT(){
        String message = "";
        message += 
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <put>
            <id>%d</id>
            <client>server</client>
            <operation>put</operation>
            <data></data>
            <message></message>
        </put>        
        """.formatted(this.countIdServerMessage);
        this.countIdServerMessage += 1;
        this.server.send(message);
    }

    @Override
    public void DELETE(){
        String message = "";
        message += 
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <delete>
            <id>%d</id>
            <client>server</client>
            <operation>delete</operation>
            <data></data>
            <message></message>
        </delete>        
        """.formatted(this.countIdServerMessage);
        this.countIdServerMessage += 1;
        this.server.send(message);
    }

    public boolean checkNewQueueContent(){
        String message = "";
        message += """
                <?xml version="1.0" encoding="UTF-8"?>
                <hascontent>
                    <id>%d</id>
                    <client>server</client>
                    <operation>get</operation>
                    <data></data>
                    <message></message>
                </hascontent>
                """.formatted(this.countIdServerMessage);
        this.countIdServerMessage += 1;
        this.server.send(message);

        message = new String(this.server.receive());

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