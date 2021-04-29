package br.edu.ifma.engsoft2.modelo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "IMOVEIS")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "TIPO_IMOVEL")
    private String tipoImovel;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "METRAGEM")
    private BigDecimal metragem;

    @Column(name = "DORMITORIOS")
    private int dormitorios;

    @Column(name = "BANHEIROS")
    private int banheiros;

    @Column(name = "SUITES")
    private int suites;

    @Column(name = "VAGAS_GARAGEM")
    private int vagasGaragem;

    @Column(name = "VALOR_ALUGUEL_SUGERIDO")
    private BigDecimal valorAluguelSugerido;

    @Column(name = "OBS")
    private String obs;

    @Override
    public String toString() {
        return "Imovel{" +
                "id=" + id +
                ", tipoImovel='" + tipoImovel + '\'' +
                ", endereco='" + endereco + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cep='" + cep + '\'' +
                ", metragem=" + metragem +
                ", dormitorios=" + dormitorios +
                ", banheiros=" + banheiros +
                ", suites=" + suites +
                ", vagasGaragem=" + vagasGaragem +
                ", valorAluguelSugerido=" + valorAluguelSugerido +
                ", obs='" + obs + '\'' +
                '}';
    }



    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public BigDecimal getMetragem() {
        return metragem;
    }

    public void setMetragem(BigDecimal metragem) {
        this.metragem = metragem;
    }

    public int getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(int dormitorios) {
        this.dormitorios = dormitorios;
    }

    public int getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(int banheiros) {
        this.banheiros = banheiros;
    }

    public int getSuites() {
        return suites;
    }

    public void setSuites(int suites) {
        this.suites = suites;
    }

    public int getVagasGaragem() {
        return vagasGaragem;
    }

    public void setVagasGaragem(int vagasGaragem) {
        this.vagasGaragem = vagasGaragem;
    }

    public BigDecimal getValorAluguelSugerido() {
        return valorAluguelSugerido;
    }

    public void setValorAluguelSugerido(BigDecimal valorAluguelSugerido) {
        this.valorAluguelSugerido = valorAluguelSugerido;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
