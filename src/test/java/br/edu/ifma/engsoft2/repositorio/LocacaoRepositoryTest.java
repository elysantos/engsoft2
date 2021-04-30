package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.config.BootstrapMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest(bootstrapMode = BootstrapMode.LAZY)
class LocacaoRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    private Cliente cliente;
    private Imovel imovel;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Adão Godson", "00011100011", "998110022", "9988221100", "adao@gmail.com", LocalDate.of(1990, 1, 20));
        clienteRepository.save(cliente);

        imovel = new Imovel("Apartamento", "Rua 1", "Araçagy", "65000000",
                BigDecimal.valueOf(65), 2, 2, 1, 1,
                BigDecimal.valueOf(1000), ""
        );
        imovelRepository.save(imovel);

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
        clienteRepository.deleteAll();
        imovelRepository.deleteAll();

    }

    @Test
    void deveTestarListarPorCliente() {
        List<Locacao> locacoes = locacaoRepository.findAllByInquilino(cliente);
        assertFalse(locacoes.isEmpty());
    }

    @Test
    void deveListarTodosPorAtivoEAluguel() {
        List<Locacao> locacoes = locacaoRepository.findAllByAtivoIsTrueAndValorAluguelIsLessThanEqual(BigDecimal.valueOf(2000));
        log.info("Total de locações encontadas: {}",locacoes.size());
        assertFalse(locacoes.isEmpty());
    }

    @Test
    void deveListarNenhumPorAtivoeAluguel() {
        List<Locacao> locacoes = locacaoRepository.findAllByAtivoIsTrueAndValorAluguelIsLessThanEqual(BigDecimal.valueOf(200));
        assertTrue(locacoes.isEmpty());
    }

    @Test
    void deveTestarDeletarUmaLocacao() {
        List<Locacao> l1 = locacaoRepository.findAll();
        locacaoRepository.delete(l1.get(0));
        List<Locacao> l2 = locacaoRepository.findAll();
        assertEquals(l1.size(), l2.size() + 1);
    }
    @Test
    void deveTestarAlterarUmaLocacao() {
        List<Locacao> l1 = locacaoRepository.findAll();
        Locacao locacaoAntiga = l1.get(0);
        locacaoAntiga.setValorAluguel(BigDecimal.valueOf(2000));
        Locacao locacaoSalva = locacaoRepository.save(locacaoAntiga);
        assertEquals(locacaoSalva, locacaoAntiga);
    }
}