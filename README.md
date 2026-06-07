# Locação De Veiculos 3
### Como Excutar?
Primeiro Ajuste as variáveis de ip nas classes: ClientTransportLayer.java, TransportLayer.java e ServerTransportLayer.
Em seguida abra 3 terminais: Um executa a classe ServerAplicationLayer.java, os outros dois executam respectivamente MessageQueue.java e 
ClientApplicationLayer.java. Depois disso você poderá visualizar o menu de opções do lado cliente e enviar as requisições.

## Sobre as classes
### ClientAplicationLayer.java
Sua função é toda a parte da interação com o usuário.

### HTTPClient.java
Implementa as funções do protocolo HTTP, ela possui um objeto da classe ClientTransportLayer.java.

### ClientTransportLayer.java
Implementa as funções send() e receive() usadas pelo HTTPClient.java. As funções usam o protocolo TCP para fazer a implementação.

### ServerAplicationLayer.java
Implementa o servidor. É essa classe que faz a manutenção da nossa principal classe: LocadoraDeVeiculos.java. Ela fica recorrentemente enviando solicitações
GET() para verrificar se há alguma mensagem em sua fila.

### HTTPServer.java
De forma análoga ao cliente, essa classe implementa todo o protocolo HTTP para o lado servidor e possui um objeto da classe ServerTransportLayer.java

### ServerTransportLayer.java 
Implementa as funções send() e receive() usando o protocolo TCP

### MessageQueue.java
A maior modificação em relação as demais implementações Locadora de veiculos: A adição de um intermediário para viabilizar a comunicação indireta. 
Essa classe contém duas filas de mensagens: clientQueue e serverQueue. Essas duas filas são objetos da classe LinkedBlockingQueue que é uma excelente 
escolha para implementar uma fila que é acessada por dois agentes de forma concorrente. Para conseguir gerênciar ao mesmo tempo as requições de dois 
agentes, MessageQueue precisa rodar em MultiThread e para isso são criadas duas threads: ThreadRecebeCliente.java e ThreadRecebeServidor.java. 
Também não podemos esquecer que para se comunicar com ambos os lados o intermediário precisa de uma camada de transporte.

### TransportLayer.java
Aqui há uma modificação em relação as demais camadas de transporte dos lados cliente e servidor. Por que no caso do intermediário a camada de transporte
precisa se comportar como um servidor para o cliente, e como um cliente para o servidor. Por isso ela usa tanto um objeto Socket quanto um objeto 
ServerSocket. 

### ThreadRecebeCliente.java e ThreadRecebeServidor.java
Essas duas threads recebem requisições respectivamente do cliente e do servidor. Para conseguir armazenar as mensagens e criar um padrão único 
(Representação Externa de Dados) para as três entidades, as duas threads usam um objeto da classe XMLElement.java que possui funções que servem para recuperar
o conteúdo de cada elemento de acordo com a necessidade.

### XMLElement.java e XMLParser.java
De maneira resumida serve para fazer a manipulação das mensagens XML.
