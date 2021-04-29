package br.edu.ifma.engsoft2.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class AluguelID implements Serializable {
    private int idLocacao;
    private LocalDate dataVencimento;

    public AluguelID(int idLocacao, LocalDate dataVencimento) {
        this.idLocacao = idLocacao;
        this.dataVencimento = dataVencimento;
    }

    public int getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(int idLocacao) {
        this.idLocacao = idLocacao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
