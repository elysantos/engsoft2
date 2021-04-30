package br.edu.ifma.engsoft2;

import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.ClienteRepository;
import br.edu.ifma.engsoft2.repositorio.ImovelRepository;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
import br.edu.ifma.engsoft2.servico.LocacaoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class ImoveisDisponiveisTests {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private LocacaoService locacaoService;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("Adão Godson", "00011100011", "998110022", "9988221100", "adao@gmail.com", LocalDate.of(1990, 1, 20));
        clienteRepository.save(cliente);

        Imovel imovel = new Imovel("Apartamento", "Rua 1", "Araçagy", "65000000",
                BigDecimal.valueOf(65), 2, 2, 1, 1,
                BigDecimal.valueOf(1000), ""
        );
        Imovel imovel1 = new Imovel(
                 "Apartamento", "Rua 1", "Calhau", "65000000",
                BigDecimal.valueOf(48), 2, 2, 1,
                1, BigDecimal.valueOf(2000), ""
        );
        Imovel imovel2 = new Imovel( "Casa", "Rua 1",
                "Araçagy", "65000000", BigDecimal.valueOf(72),
                2, 2, 1, 1,
                BigDecimal.valueOf(1300), ""
        );
        Imovel imovel3 = new Imovel(
                 "Apartamento", "Rua 2", "Araçagy", "65000000",
                BigDecimal.valueOf(65),
                2, 2, 1, 1, BigDecimal.valueOf(1200), "");

        imovelRepository.save(imovel);
        imovelRepository.save(imovel1);
        imovelRepository.save(imovel2);
        imovelRepository.save(imovel3);

        Locacao locacao = new Locacao();
        locacao.setAtivo(true);
        locacao.setDataInicio(LocalDate.now());
        locacao.setImovel(imovel);
        locacao.setInquilino(cliente);
        locacao.setDiaVencimento(4);
        locacao.setValorAluguel(BigDecimal.valueOf(1200));

        locacaoRepository.save(locacao);
    }

    @AfterEach
    void tearDown() {
        locacaoRepository.deleteAll();
        imovelRepository.deleteAll();
    }

    @Test
    void dadoUmBairroRecuperarApartamentos() {
        List<Imovel> imoveis = locacaoService.apartamentosDisponivelsPorBairro("Araçagy");
        assertTrue(imoveis.size() > 0);
    }


    @Test
    void dadoLimiteDePrecoRecuperarDisponiveis() {
        BigDecimal limitePreco = BigDecimal.valueOf(1500);

        List<Imovel> imoveis = locacaoService.recuperarDisponiveisPorPreco(limitePreco);
        assertTrue(imoveis.size() > 0);
    }
}
