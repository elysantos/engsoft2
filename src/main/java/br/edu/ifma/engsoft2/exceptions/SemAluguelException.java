package br.edu.ifma.engsoft2.exceptions;

public class SemAluguelException extends Exception{
    public SemAluguelException() {
        super("Nenhum aluguel a pagar encontrado");
    }
}
