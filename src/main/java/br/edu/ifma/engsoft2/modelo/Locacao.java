package br.edu.ifma.engsoft2.modelo;

import br.edu.ifma.engsoft2.exceptions.SemAluguelException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "LOCACAO")
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Imovel imovel;


    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Cliente inquilino;

    @Column(name = "VALOR_ALUGUEL")
    private BigDecimal valorAluguel;

    @Column(name = "PERCENTUAL_MULTA")
    private BigDecimal percentualMulta = BigDecimal.valueOf(0.33);

    @Column(name = "DIA_VENCIMENTO")
    private int diaVencimento;

    @Column(name = "DATA_INICIO")
    private LocalDate dataInicio;

    @Column(name = "DATA_FIM")
    private LocalDate dataFim;

    @Column(name = "ATIVO")
    private boolean ativo;

    @Column(name = "OBS")
    private String obs;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "locacao")
    private List<Aluguel> alugueis;

    @Override
    public String toString() {
        return "Locacao{" +
                "id=" + id +
                ", imovel=" + imovel +
                ", inquilino=" + inquilino +
                ", valorAluguel=" + valorAluguel +
                ", percentualMulta=" + percentualMulta +
                ", diaVencimento=" + diaVencimento +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", ativo=" + ativo +
                ", obs='" + obs + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Cliente getInquilino() {
        return inquilino;
    }

    public void setInquilino(Cliente inquilino) {
        this.inquilino = inquilino;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public BigDecimal getPercentualMulta() {
        return percentualMulta;
    }

    public void setPercentualMulta(BigDecimal percentualMulta) {
        this.percentualMulta = percentualMulta;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    public BigDecimal valorPagar(LocalDate data){
        BigDecimal maximoMulta = this.valorAluguel.multiply(BigDecimal.valueOf(0.8));
        BigDecimal diaMulta = this.valorAluguel.multiply(BigDecimal.valueOf(0.0033));

        Optional<Aluguel> aluguel = this.alugueis.stream()
                .filter(a ->
                    !(a.getValorPago().compareTo(BigDecimal.ZERO) < 1
                            || a.getDataPagamento() == null)
                )
                .min(Comparator.comparing(e ->
                     e.getDataVencimento().toEpochDay()
                ));
        if(aluguel.isPresent()){
            int dias = data.until(aluguel.get().getDataVencimento()).getDays();
            BigDecimal valorTotalMulta = diaMulta.multiply(BigDecimal.valueOf(dias));
            if(valorTotalMulta.compareTo(maximoMulta) > 0){
                return this.valorAluguel.add(maximoMulta);
            }
            return this.valorAluguel.add(valorTotalMulta);
        }
        return BigDecimal.ZERO;
    }

    public Aluguel proximoPagar() throws SemAluguelException {
        Optional<Aluguel> optional = this.alugueis.stream()
                .filter(a ->
                        !(a.getValorPago().compareTo(BigDecimal.ZERO) < 1
                                || a.getDataPagamento() == null)
                )
                .min(Comparator.comparing(e ->
                        e.getDataVencimento().toEpochDay()
                ));
        if(optional.isPresent()){
            return optional.get();
        }
        throw new SemAluguelException();
    }
}
