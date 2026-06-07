package project.servidor;

import java.io.ByteArrayInputStream;

import project.common.LocadoraDeVeiculos;
import project.common.MeiosDeTransporte;
import project.comunication.HTTPServer;
import project.intermediario.XMLElement;

public class ServerAplicationLayer {
    public static void main(String[] args){
        HTTPServer httpServer = new HTTPServer();
        LocadoraDeVeiculos locadora = new LocadoraDeVeiculos();

        String message = "";

        while(true){
            // Recupera o primeiro elemento de sua fila
            message = httpServer.GET();

            /* Debug */
            System.out.println(message);

            String tags[] = message.split(",");
            String operation[] = tags[3].split(":");
            String data[] = tags[4].split(":");

            switch (operation[1]) {
                case "listar":
                    System.out.println("[Listar Todos os Veiculos]");
                    message = locadora.toString();
                    httpServer.POST(message);
                    break;
                case "alugar":
                    System.out.println("[Alugar Veiculo]");
                    MeiosDeTransporte veiculoAlugado = locadora.Alugar(data[1]);
                    httpServer.POST(veiculoAlugado.toString());
                    break;
                case "devolver":
                    System.out.println("[Devolver Veiculo]");
                    MeiosDeTransporte veiculoDevolvido = locadora.Devolver(data[1]);
                    httpServer.POST(veiculoDevolvido.toString());
                    break;
                default:
                    break;
            }
        }
    }
}