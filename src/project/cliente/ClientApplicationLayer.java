package project.cliente;

import java.util.Scanner;

import project.comunication.HTTPClient;

public class ClientApplicationLayer{
    public static void main(String[] args){
        HTTPClient httpClient = new HTTPClient();

        Scanner reader = new Scanner(System.in);
        String[] command = new String[2];

        while(true){    
            System.out.println("Seja Bem vindo ao sistema de aluguel de veiculos");

            System.out.println("Escolha uma opção");
            System.out.println("1. listar");
            System.out.println("2. alugar");
            System.out.println("3. devolver");
            
            String line = reader.nextLine();
            command = line.split(" ");
            System.out.println("~" + command[0]);
            switch (command[0]) {
                case "listar":
                    httpClient.POST("listar","");
                    String message = ";-;";

                    message = httpClient.GET();
                    System.out.println(message);
                    break;
                case "alugar":
                    httpClient.POST("alugar", command[1]);
                    message = httpClient.GET();
                    System.out.println("Voce alugou:\n" + message);
                    break;
                case "devolver":
                    httpClient.POST("devolver", command[1]);
                    message = httpClient.GET();
                    System.out.println("Voce devolveu:\n" + message);
                    break;
                default:
                    break;
            }
        }
    }
}