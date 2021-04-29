package br.edu.ifma.engsoft2.exceptions;

public class ClienteNotFoundException extends Exception{
    public ClienteNotFoundException() {
        super("Cliente não encontrado");
    }
}
