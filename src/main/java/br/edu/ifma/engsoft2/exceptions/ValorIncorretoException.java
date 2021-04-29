package br.edu.ifma.engsoft2.exceptions;

public class ValorIncorretoException extends Exception{
    public ValorIncorretoException() {
        super("Valor incorreto para pagamento de Aluguel");
    }
}
