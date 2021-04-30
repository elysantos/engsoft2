package br.edu.ifma.engsoft2.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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

    public Imovel() {
    }

    public Imovel(String tipoImovel, String endereco, String bairro, String cep, BigDecimal metragem, int dormitorios, int banheiros, int suites, int vagasGaragem, BigDecimal valorAluguelSugerido, String obs) {
        this.tipoImovel = tipoImovel;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cep = cep;
        this.metragem = metragem;
        this.dormitorios = dormitorios;
        this.banheiros = banheiros;
        this.suites = suites;
        this.vagasGaragem = vagasGaragem;
        this.valorAluguelSugerido = valorAluguelSugerido;
        this.obs = obs;
    }

    public Imovel(int id, String tipoImovel, String endereco, String bairro, String cep, BigDecimal metragem, int dormitorios, int banheiros, int suites, int vagasGaragem, BigDecimal valorAluguelSugerido, String obs) {
        this.id = id;
        this.tipoImovel = tipoImovel;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cep = cep;
        this.metragem = metragem;
        this.dormitorios = dormitorios;
        this.banheiros = banheiros;
        this.suites = suites;
        this.vagasGaragem = vagasGaragem;
        this.valorAluguelSugerido = valorAluguelSugerido;
        this.obs = obs;
    }

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

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Imovel imovel = (Imovel) o;
        return id == imovel.id && dormitorios == imovel.dormitorios && banheiros == imovel.banheiros && suites == imovel.suites && vagasGaragem == imovel.vagasGaragem && Objects.equals(tipoImovel, imovel.tipoImovel) && Objects.equals(endereco, imovel.endereco) && Objects.equals(bairro, imovel.bairro) && Objects.equals(cep, imovel.cep) && Objects.equals(metragem, imovel.metragem) && Objects.equals(valorAluguelSugerido, imovel.valorAluguelSugerido) && Objects.equals(obs, imovel.obs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoImovel, endereco, bairro, cep, metragem, dormitorios, banheiros, suites, vagasGaragem, valorAluguelSugerido, obs);
    }


    public int getId() {
        return id;
    }
}
