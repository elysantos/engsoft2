package br.edu.ifma.engsoft2.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ALUGUEIS")
@IdClass(AluguelID.class)
public class Aluguel {

    @Id
    @Column(name = "ID_LOCACAO")
    private int idLocacao;

    @Id
    @Column(name = "DATA_VENCIMENTO")
    private LocalDate dataVencimento;

    @Column(name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @Column(name = "OBS")
    private String obs;

    @Column(name = "VALOR_PAGO")
    private BigDecimal valorPago;


    public Aluguel() {
    }

    public Aluguel(int idLocacao, LocalDate dataVencimento, LocalDate dataPagamento, String obs, BigDecimal valorPago) {
        this.idLocacao = idLocacao;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.obs = obs;
        this.valorPago = valorPago;
    }

    @Override
    public String toString() {
        return "Aluguel{" +
                "dataVencimento=" + dataVencimento +
                ", dataPagamento=" + dataPagamento +
                ", obs='" + obs + '\'' +
                '}';
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
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

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

}
