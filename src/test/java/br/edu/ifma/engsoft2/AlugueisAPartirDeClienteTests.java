package br.edu.ifma.engsoft2;

import br.edu.ifma.engsoft2.exceptions.ClienteNotFoundException;
import br.edu.ifma.engsoft2.modelo.Aluguel;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.AluguelRepository;
import br.edu.ifma.engsoft2.repositorio.ClienteRepository;
import br.edu.ifma.engsoft2.repositorio.ImovelRepository;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
import br.edu.ifma.engsoft2.servico.AluguelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
@SpringBootTest
public class AlugueisAPartirDeClienteTests {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private AluguelService aluguelService;

    private Cliente cliente;
    @BeforeEach
    void setUp() {
        cliente = new Cliente("Adão Godson", "00011100011", "998110022", "9988221100", "adao@gmail.com", LocalDate.of(1990, 1, 20));
        clienteRepository.save(cliente);

        Imovel imovel = new Imovel("Apartamento", "Rua 1", "Araçagy", "65000000",
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


//        locacao.setAlugueis(alugueis);
        aluguelRepository.saveAll(alugueis);
//        locacaoRepository.save(locacao);

    }

    @AfterEach
    void tearDown() {
        aluguelRepository.deleteAll();
        locacaoRepository.deleteAll();
        imovelRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void deveRecuperarAlugeisPagosPorCliente() {
        try {
            List<Aluguel> alugueis = aluguelService.alugueisPagosPorCliente(cliente.getNome());
            assertTrue(alugueis.size() > 0);
        } catch (ClienteNotFoundException e) {
            log.error(e.getMessage());
            fail();
        }
    }

    @Test
    void deveRecuperarAlugeisPagosComAtraso() {
        try {
            List<Aluguel> alugueis = aluguelService
                    .alugueisPagosEmAtrasoPorCliente(cliente.getNome());
            assertTrue(alugueis.size() > 0);
        } catch (ClienteNotFoundException e) {
            log.error(e.getMessage());
            fail();
        }
    }
}
