package project.comunication;


public interface HTTPInterface {
    /*
        Usado para recuperar dados
        Não altera dados do servidor
        Não pode ser armazenado em cache
        Pode ser repetido sem efeitos colaterais
        idempotente
    */
    public String GET();
    
    /*
        Usado para enviar dados ao servidor
        Pode alterar os dados do servidor
        Geralmente usado criar recursos
        Não idempotente
    */
    public void POST(String body);

    /* 
        Substitui completamente o recurso
        Se o recurso existir ele é susbstituido
        idempotente
    */
    public void PUT();

    /*
        Atualiza apenas alguns campos.
    */
   //public void PATCH();

   /*
        Remove um recurso
        idempotente
   */
    public void DELETE();

    /*
        Semelhante ao GET, mas retorna apenas os
        cabeçálhos
        idempotente
    */
    //public void HEAD();

    /*
        Permite descobrir quais métodos são aceitos
        idempotente
    */
    //public void OPTIONS();

    /*
        Faz o servidor devolver a própria requisição
        recebida
    */
    //public void TRACE();

    /*
        Utilizado para criar túneis através de proxies
        especialmente em HTTPS
    */
    //public void CONNECT();
}