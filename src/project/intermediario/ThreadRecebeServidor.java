package project.intermediario;

import java.io.ByteArrayInputStream;
import java.util.concurrent.BlockingQueue;

public class ThreadRecebeServidor implements Runnable{
    /* As filas que serão usadas para a manutenção das requições e respostas */
    private BlockingQueue<XMLElement> clientQueue;
    private BlockingQueue<XMLElement> serverQueue;
    /* Bytes vindos do servidor */
    private byte buffer[];
    /* XML Element */
    private XMLElement xml;

    /* Camada de transporte */
    TransportLayer transport;

    public ThreadRecebeServidor(BlockingQueue<XMLElement> clientQueue, BlockingQueue<XMLElement> serverQueue, TransportLayer transport){
        this.clientQueue = clientQueue;
        this.serverQueue = serverQueue;
        this.transport = transport;
    }

    @Override
    public void run(){
        System.out.println("Recebendo requisições do servidor...");
        System.out.println("[Server Socket] " + this.transport.getServerSocketInfo());
        while(true){
            // Aguarda uma requisição do servidor
            while(buffer == null){
                buffer = transport.receive_server();
            }
            System.out.println("[Server request]\n" + new String(buffer));

            // Converte para xmlelement
            xml = new XMLElement(new ByteArrayInputStream(buffer));
            buffer = null;

            /* 
                verificar o tipo de requisição do cliente
                - GET
                - POST
                - PUT
            */
            try{
                switch(xml.getType()) {
                    case "get":
                        // Remove o primeiro elemento da fila de mensagens
                        // do servidor
                        XMLElement getFirst = serverQueue.take();
                        
                        /* Debug */
                        System.out.println("[ThreadRecebeServidor.java]");
                        System.out.println(getFirst.toString());
                        transport.send_server(getFirst.toString());
                        break;
                    case "post":
                        // Adiciona o elemento a fila do cliente
                        clientQueue.put(xml);
                        break;
                    case "put":
                        // Vai substituir o primeiro elemento da lista
                        XMLElement last = clientQueue.poll();
                        // Adiciona o elemento a fila do servidor
                        clientQueue.put(xml);
                        break;
                    case "delete":
                        // vai remover o primeiro elemento da lista
                        XMLElement first = serverQueue.remove();
                        break;
                    case "hascontent":
                        if(serverQueue.isEmpty()){
                            this.transport.send_server("no");
                        } else{
                            this.transport.send_server("yes");
                        }
                        break;
                    default:
                        break;
                    }
            } catch(InterruptedException e){
                System.out.println("ThreadRecebeServer.java[1]");
                System.out.println("InterruptedException: " + e.getLocalizedMessage());
            }
        }
    }
}