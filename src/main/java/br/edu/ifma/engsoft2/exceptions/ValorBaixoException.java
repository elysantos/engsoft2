package br.edu.ifma.engsoft2.exceptions;

public class ValorBaixoException extends Exception{
    public ValorBaixoException() {
        super("Valor menor que o esperado");
    }
}
