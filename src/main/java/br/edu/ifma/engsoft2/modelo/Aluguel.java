package br.edu.ifma.engsoft2.modelo;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ALUGUEIS")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Locacao locacao;

    private LocalDate dataVencimento;

    @Column(name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @Column(name = "OBS")
    private String obs;

    @Column(name = "VALOR_PAGO")
    private BigDecimal valorPago;


    public Aluguel() {
    }

    public Aluguel(Locacao locacao, LocalDate dataVencimento) {
        this.locacao = locacao;
        this.dataVencimento = dataVencimento;
    }

    public Aluguel(Locacao locacao, LocalDate dataVencimento, LocalDate dataPagamento, BigDecimal valorPago) {
        this.locacao = locacao;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
    }

    public Aluguel(LocalDate dataVencimento, LocalDate dataPagamento, BigDecimal valorPago) {
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluguel aluguel = (Aluguel) o;
        return id == aluguel.id && Objects.equals(locacao, aluguel.locacao) && Objects.equals(dataVencimento, aluguel.dataVencimento) && Objects.equals(dataPagamento, aluguel.dataPagamento) && Objects.equals(valorPago, aluguel.valorPago);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataVencimento, dataPagamento, valorPago);
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }


    public Locacao getLocacao() {
        return locacao;
    }

    public void setLocacao(Locacao locacao) {
        this.locacao = locacao;
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


}
