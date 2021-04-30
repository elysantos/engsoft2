package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Aluguel;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DataJpaTest
class AluguelRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private AluguelRepository aluguelRepository;

    private Cliente cliente;
    private Imovel imovel;
    private Locacao locacao;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Adão Godson", "00011100011", "998110022", "9988221100", "adao@gmail.com", LocalDate.of(1990, 1, 20));
        clienteRepository.save(cliente);

        imovel = new Imovel("Apartamento", "Rua 1", "Araçagy", "65000000",
                BigDecimal.valueOf(65), 2, 2, 1, 1,
                BigDecimal.valueOf(1000), ""
        );
        imovelRepository.save(imovel);

        locacao = new Locacao();
        locacao.setAtivo(true);
        locacao.setDataInicio(LocalDate.now());
        locacao.setImovel(imovel);
        locacao.setInquilino(cliente);
        locacao.setDiaVencimento(4);
        locacao.setValorAluguel(BigDecimal.valueOf(1200));
        Aluguel aluguel = new Aluguel();
        aluguel.setDataVencimento(LocalDate.now());

        aluguel.setLocacao(locacao);

        locacaoRepository.save(locacao);
        aluguelRepository.save(aluguel);
        locacao.addAluguel(aluguel);
    }

    @AfterEach
    void tearDown() {
        aluguelRepository.deleteAll();
        locacaoRepository.deleteAll();
        clienteRepository.deleteAll();
        imovelRepository.deleteAll();
    }

    @Test
    void deveListarTodos() {
        List<Aluguel> alugueis = aluguelRepository.findAll();
        assertTrue(!alugueis.isEmpty());
    }

//    @Test
    void deveDeletarTodos() {
        aluguelRepository.deleteAll();
        List<Aluguel> alugueis = aluguelRepository.findAll();
        assertTrue(alugueis.isEmpty());
    }

    @Test
    void deveAlterarUmAluguel() {
        Aluguel aluguel = locacao.getAlugueis().get(0);
        aluguel.setValorPago(locacao.getValorAluguel());
        aluguel.setDataPagamento(LocalDate.now());

        aluguelRepository.save(aluguel);

        List<Aluguel> alugueis = aluguelRepository.findAll();
        assertEquals(alugueis.get(0), aluguel);
    }

//    @Test
    void deveDeletarUmAluguel() {
        Aluguel aluguel = aluguelRepository.findAll().get(0);
        aluguelRepository.delete(aluguel);
        List<Aluguel> alugueis = aluguelRepository.findAll();
        assertTrue(alugueis.isEmpty());
    }
}