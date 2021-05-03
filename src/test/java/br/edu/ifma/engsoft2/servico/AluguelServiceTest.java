package br.edu.ifma.engsoft2.servico;

import br.edu.ifma.engsoft2.exceptions.LocacaoNotFoundException;
import br.edu.ifma.engsoft2.exceptions.SemAluguelException;
import br.edu.ifma.engsoft2.exceptions.ValorBaixoException;
import br.edu.ifma.engsoft2.exceptions.ValorIncorretoException;
import br.edu.ifma.engsoft2.modelo.Aluguel;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.AluguelRepository;
import br.edu.ifma.engsoft2.repositorio.ClienteRepository;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class AluguelServiceTest {
    private AluguelService aluguelService;
    private AluguelRepository aluguelRepository;
    private ClienteRepository clienteRepository;
    private LocacaoRepository locacaoRepository;

    private Locacao locacaoAtrasada;
    private Locacao locacaoEmDia;

    @BeforeEach
    void setUp() {
        locacaoRepository = mock(LocacaoRepository.class);
        aluguelRepository = mock(AluguelRepository.class);
        clienteRepository = mock(ClienteRepository.class);
        aluguelService = new AluguelService(aluguelRepository, locacaoRepository, clienteRepository);

        inicializaObjetosTeste();


    }

    private void inicializaObjetosTeste(){
        Cliente cliente0 = new Cliente("Bill", "bill@mail.com");
        Cliente cliente1 = new Cliente("Larry", "larry@mail.com");

        Imovel imovel = new Imovel();

        //em atraso
        locacaoAtrasada = new Locacao();
        locacaoAtrasada.setInquilino(cliente0);
        locacaoAtrasada.setImovel(imovel);
        locacaoAtrasada.setAlugueis(
                Arrays.asList(
                        new Aluguel(locacaoAtrasada, LocalDate.now().minusMonths(2))
                )
        );

        //em dia
        locacaoEmDia = new Locacao();
        locacaoEmDia.setInquilino(cliente1);
        locacaoEmDia.setImovel(imovel);
        locacaoEmDia.setValorAluguel(BigDecimal.valueOf(350));
        locacaoEmDia.setAlugueis(
                Arrays.asList(
                        new Aluguel(locacaoEmDia, LocalDate.now())
                )
        );

        when(locacaoRepository.findAll()).thenReturn(
                Arrays.asList(locacaoAtrasada, locacaoEmDia)
        );

        when(locacaoRepository.findById(anyInt()))
                .thenReturn(ofNullable(locacaoEmDia));
    }

    @Test
    void deveTestarGeradorDePagamentoComValorMenor() throws SemAluguelException {

        try {
            BigDecimal valorAluguel = locacaoEmDia.getValorAluguel()
                    .subtract(BigDecimal.ONE);
            log.info("Valor do Aluguel {}", valorAluguel);
            aluguelService.realizarPagamentoAluguel(
                    valorAluguel,LocalDate.now(), locacaoEmDia.getId());
            fail();
        } catch (LocacaoNotFoundException  | ValorIncorretoException e) {
            log.error(e.getMessage());
            fail();
        } catch (ValorBaixoException e) {
            assertTrue(true);
        }

    }

    @Test
    void deveTestarGeradorDePagamentoComValorCorreto() {
        try {
            aluguelService.realizarPagamentoAluguel(
                    locacaoEmDia.getValorAluguel(),LocalDate.now(), locacaoEmDia.getId());
            assertTrue(true);
        } catch (LocacaoNotFoundException  | ValorIncorretoException  | ValorBaixoException | SemAluguelException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}