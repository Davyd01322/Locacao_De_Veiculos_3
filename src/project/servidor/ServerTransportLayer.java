package project.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class ServerTransportLayer {
    // Se comunica com o cliente
    private ServerSocket server = null;
    private Socket socket = null;

    private DataInputStream in;
    private DataOutputStream out;

    private String ipLocal = "10.11.111.34";

    private int serverPort = 1322;

    public ServerTransportLayer(){
        try {
            // Se comunica com o cliente
            this.server = new ServerSocket(serverPort);
            this.socket = server.accept();

            if(this.socket != null){
                System.out.println("O intermediário conseguiu se conectar com o servidor");
                System.out.println(this.socket.toString());
            }

            // Fluxos de dados
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch(UnknownHostException e){
            System.out.println("ServerTransportLayer.java[1]");
            System.out.println("UnknownHostException: " + e.getLocalizedMessage());
        } catch(IOException e){
            System.out.println("ServerTransportLayer.java[1]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        } catch(IllegalArgumentException e){
            System.out.println("ServerTransportLayer.java[1]");
            System.out.println("IllegalArgumentException: " + e.getLocalizedMessage());
        }
    }

    // receber mensagens do cliente
    public byte[] receive(){
        String message = "";
        try{
            message = this.in.readUTF();
        } catch(IOException e){
            System.out.println("ServerTransportLayer.java[4]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        } catch(OutOfMemoryError e){
            System.out.println("ServerTransportLayer.java[4]");
            System.out.println("OutOfMemoryError: " + e.getLocalizedMessage());
        }
        return message.getBytes();
    }

    // enviar mensagens para o cliente
    public void send(String msg){
        try {
            this.out.writeUTF(msg);
        } catch(IOException e){
            System.out.println("ServerTransportLayer.java[5]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        }
    }

    // verificar se há conteúdo para ser lido
    public boolean itHasContent(){
        boolean flag = false;
        try{
            if(this.in.available() != 0){
                flag = true;
            } 
        }catch(IOException e){
            System.out.println("ServerTransportLayer.java[6]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        }

        return flag;
    }
}