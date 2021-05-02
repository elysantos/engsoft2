package br.edu.ifma.engsoft2.exceptions;

public class EmailNaoEnviadoException extends Exception{
    public EmailNaoEnviadoException(String message) {
        super("Email não enviado: " + message);
    }
}
