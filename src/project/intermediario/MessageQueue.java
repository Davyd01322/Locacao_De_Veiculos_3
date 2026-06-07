package project.intermediario;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class MessageQueue {
    public static void main(String[] args){
        /* Camada de transporte */
        TransportLayer transport = new TransportLayer();

        /* As filas que serão usadas para a manutenção das requições e respostas */
        BlockingQueue<XMLElement> clientQueue = new LinkedBlockingQueue<>();
        BlockingQueue<XMLElement> serverQueue = new LinkedBlockingQueue<>();

        /* Threads */
        Thread cliente = new Thread(new ThreadRecebeCliente(clientQueue, serverQueue, transport));
        Thread servidor = new Thread(new ThreadRecebeServidor(clientQueue, serverQueue, transport));

        /* Executar as threads */
        cliente.start();
        servidor.start();
    }
}