package br.edu.ifma.engsoft2.modelo;

import br.edu.ifma.engsoft2.exceptions.SemAluguelException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "LOCACAO")
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Imovel imovel;

    @ManyToOne
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "locacao", cascade = CascadeType.ALL)
    private List<Aluguel> alugueis = new ArrayList<>();

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

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
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

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(List<Aluguel> alugueis) {
        this.alugueis = alugueis;
    }

    public void addAluguel(Aluguel aluguel){
        this.alugueis.add(aluguel);
    }

    public BigDecimal valorPagar(LocalDate data){
        BigDecimal maximoMulta = this.valorAluguel.multiply(BigDecimal.valueOf(0.8));
        BigDecimal diaMulta = this.valorAluguel.multiply(BigDecimal.valueOf(0.0033));

        Optional<Aluguel> aluguel = this.alugueis.stream()
                .filter(a ->
                    !((a.getValorPago()!= null
                            && a.getValorPago().compareTo(BigDecimal.ZERO) < 1)
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
                        (a.getValorPago() != null && !(a.getValorPago().compareTo(BigDecimal.ZERO) < 1
                                || a.getDataPagamento() == null))
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
