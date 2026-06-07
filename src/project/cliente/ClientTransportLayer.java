package project.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class ClientTransportLayer {
    // Se comunica com o servidor
    private Socket cliente = null;

    private DataInputStream in;
    private DataOutputStream out;

    private String ipLocal = "10.11.111.34";
    private int serverPort = 2213;

    public ClientTransportLayer(){
        try {
            // Se comunica com o intermediario
            this.cliente = new Socket(ipLocal,serverPort);

            // Fluxos de dados
            this.in = new DataInputStream(cliente.getInputStream());
            this.out = new DataOutputStream(cliente.getOutputStream());
        } catch(UnknownHostException e){
            System.out.println("ClientTransportLayer.java[1]");
            System.out.println("UnknownHostException: " + e.getLocalizedMessage());
        } catch(IOException e){
            System.out.println("ClientTransportLayer.java[1]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        } catch(IllegalArgumentException e){
            System.out.println("ClientTransportLayer.java[1]");
            System.out.println("IllegalArgumentException: " + e.getLocalizedMessage());
        }
    }

    // receber mensagens do servidor
    public byte[] receive(){
        byte[] bytes = null;
        try{
            int tamanho = this.in.readInt();
            bytes = new byte[tamanho];
            this.in.readFully(bytes);
        } catch(IOException e){
            System.out.println("ClientTransportLayer.java[4]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        } catch(OutOfMemoryError e){
            System.out.println("ClientTransportLayer.java[4]");
            System.out.println("OutOfMemoryError: " + e.getLocalizedMessage());
        }
        return bytes;
    }

    // enviar mensagens para o servidor
    public void send(String msg){
        try {
            this.out.writeUTF(msg);
        } catch(IOException e){
            System.out.println("ClientTransportLayer.java[5]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        }
    }
}