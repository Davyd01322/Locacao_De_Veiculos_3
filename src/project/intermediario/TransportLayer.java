package project.intermediario;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TransportLayer {
    // Vai se comportar como servidor (para se comunicar com o cliente)
    // e como cliente (para se comunicar com o servidor)
    // Se comunica com o servidor
    private Socket client = null;
    // Se comunica com o cliente
    private ServerSocket server = null;
    private Socket socket = null;

    private DataInputStream server_in;
    private DataOutputStream server_out;
    private DataInputStream client_in;
    private DataOutputStream client_out;

    private String ipLocal = "10.11.111.34";

    private int serverPort = 1322;
    private int clientPort = 2213;

    public TransportLayer(){
        try {
            // Se comunica com o servidor
            this.client = new Socket(ipLocal, serverPort);

            // Se comunica com o cliente
            this.server = new ServerSocket(clientPort);
            this.socket = server.accept();

            if(this.socket != null){
                System.out.println("Cliente conseguiu se conectar com o intermediário");
                System.out.println(this.socket.toString());
            }

            // Fluxos de dados
            this.server_in = new DataInputStream(socket.getInputStream());
            this.server_out = new DataOutputStream(socket.getOutputStream());

            this.client_in = new DataInputStream(client.getInputStream());
            this.client_out = new DataOutputStream(client.getOutputStream());

        } catch(UnknownHostException e){
            System.out.println("TransportLayer.java[1]");
            System.out.println("UnknownHostException: " + e.getLocalizedMessage());
        } catch(IOException e){
            System.out.println("TransportLayer.java[1]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        } catch(IllegalArgumentException e){
            System.out.println("TransportLayer.java[1]");
            System.out.println("IllegalArgumentException: " + e.getLocalizedMessage());
        }
    }

    // Enviar mensagem para o servidor
    public void send_server(String msg){
        try {
            this.client_out.writeUTF(msg);
        } catch(IOException e){
            System.out.println("TransportLayer.java[2]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        }
    }

    // receber mensagem do servidor
    public byte[] receive_server(){
        String message = "";
        try{
            message = this.client_in.readUTF();
        } catch(IOException e){
            System.out.println("TransportLayer.java[3]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        } catch(OutOfMemoryError e){
            System.out.println("TransportLayer.java[3]");
            System.out.println("OutOfMemoryError: " + e.getLocalizedMessage());
        }
        return message.getBytes();
    }

    // receber mensagens do cliente
    public byte[] receive_client(){
        String buffer = "";
        try{
            buffer = this.server_in.readUTF();
        } catch(IOException e){
            System.out.println("TransportLayer.java[4]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        } catch(OutOfMemoryError e){
            System.out.println("TransportLayer.java[4]");
            System.out.println("OutOfMemoryError: " + e.getLocalizedMessage());
        }
        return buffer.getBytes();
    }

    // enviar mensagens para o cliente
    public void send_client(String msg){
        try {
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            this.server_out.writeInt(bytes.length);
            this.server_out.write(bytes);
            this.server_out.flush();
        } catch(IOException e){
            System.out.println("TransportLayer.java[5]");
            System.out.println("IOException: " + e.getLocalizedMessage());
        }
    }

    // debug
    public String getClientSocketInfo(){
        return this.socket.toString();
    }

    // debug
    public String getServerSocketInfo(){
        return this.client.toString();
    }
}