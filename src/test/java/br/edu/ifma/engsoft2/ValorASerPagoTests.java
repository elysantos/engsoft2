package br.edu.ifma.engsoft2;


import br.edu.ifma.engsoft2.modelo.Aluguel;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class ValorASerPagoTests {

    private Cliente cliente;
    private Imovel imovel;

    private Locacao locacao;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Adão Godson", "00011100011", "998110022", "9988221100", "adao@gmail.com", LocalDate.of(1990, 1, 20));

        imovel = new Imovel("Apartamento", "Rua 1", "Araçagy", "65000000",
                BigDecimal.valueOf(65), 2, 2, 1, 1,
                BigDecimal.valueOf(1000), ""
        );


        locacao = new Locacao();
        locacao.setAtivo(true);
        locacao.setDataInicio(LocalDate.now());
        locacao.setImovel(imovel);
        locacao.setInquilino(cliente);
        locacao.setDiaVencimento(4);
        locacao.setValorAluguel(BigDecimal.valueOf(1200));


        BigDecimal valorPagar = locacao.valorPagar(LocalDate.now());
        Aluguel aluguel0 = new Aluguel(locacao, LocalDate.now().plusMonths(1));
        Aluguel aluguel1 = new Aluguel(locacao, LocalDate.now().minusMonths(1));
        aluguel1.setDataPagamento(LocalDate.now());
        aluguel1.setValorPago(valorPagar);
        aluguel1.setLocacao(locacao);
        Aluguel aluguel2 = new Aluguel(locacao, LocalDate.now().plusMonths(2));
        Aluguel aluguel3 = new Aluguel(locacao, LocalDate.now(), LocalDate.now(), locacao.getValorAluguel());

        List<Aluguel> alugueis = Arrays.asList(
                aluguel0, aluguel1, aluguel2, aluguel3
        );
        locacao.setAlugueis(alugueis);
    }
}
