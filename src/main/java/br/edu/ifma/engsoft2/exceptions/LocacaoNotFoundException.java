package br.edu.ifma.engsoft2.exceptions;

public class LocacaoNotFoundException extends Exception{
    public LocacaoNotFoundException() {
        super("Locação não encontrada");
    }
}
