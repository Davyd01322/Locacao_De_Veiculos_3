package project.intermediario;

import java.io.ByteArrayInputStream;
import java.util.concurrent.BlockingQueue;

public class ThreadRecebeCliente implements Runnable{
    /* As filas que serão usadas para a manutenção das requições e respostas */
    private BlockingQueue<XMLElement> clientQueue;
    private BlockingQueue<XMLElement> serverQueue;
    /* Bytes vindos do cliente */
    private byte buffer[];
    /* XML Element */
    private XMLElement xml;

    /* Camada de transporte */
    TransportLayer transport = null;

    public ThreadRecebeCliente(BlockingQueue<XMLElement> clientQueue, BlockingQueue<XMLElement> serverQueue, TransportLayer transport){
        this.clientQueue = clientQueue;
        this.serverQueue = serverQueue;
        this.transport = transport;
    }

    @Override
    public void run(){
        System.out.println("Recebendo requisições do cliente...");
        System.out.println("[Client Socket] " + this.transport.getClientSocketInfo());
        while(true){
            // Aguarda uma requisição do cliente
            while(buffer == null){
                buffer = transport.receive_client();
            }
            System.out.println("[Client request]\n" + new String(buffer));

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
                String message = "";
                switch(xml.getType()) {
                    case "get":
                        // Retorna o conteúdo do primeiro elemento da fila
                        // do cliente
                        XMLElement getFirst = clientQueue.take();

                        System.out.println("[ThreadRecebeCliente.java]");
                        System.out.println(getFirst.getData());
                        transport.send_client(getFirst.getData());
                        break;
                    case "post":
                        // Adiciona o elemento a fila do servidor
                        serverQueue.put(xml);
                        break;
                    case "put":
                        // Vai substituir o primeiro elemento da lista
                        XMLElement last = serverQueue.poll();
                        // Adiciona o elemento a fila do servidor
                        serverQueue.put(xml);
                        break;
                    case "delete":
                        // vai remover o primeiro elemento da lista
                        XMLElement first = clientQueue.remove();
                        break;
                    case "hascontent":
                        if(clientQueue.isEmpty()){
                            this.transport.send_client("no");
                        } else{
                            this.transport.send_client("yes");
                        }
                        break;
                    default:
                        break;
                    }
            } catch(InterruptedException e){
                System.out.println("ThreadRecebeCliente.java[1]");
                System.out.println("InterruptedException: " + e.getLocalizedMessage());
            }
        }
    }
}