package br.edu.ifma.engsoft2.servico;

import br.edu.ifma.engsoft2.exceptions.EmailNaoEnviadoException;
import br.edu.ifma.engsoft2.modelo.Aluguel;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.AluguelRepository;
import br.edu.ifma.engsoft2.repositorio.ClienteRepository;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
import br.edu.ifma.engsoft2.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class EnviadorDeEmailServiceTests {

    private EnviadorDeEmailService enviadorDeEmailService;

    private LocacaoRepository locacaoRepository;
    private EmailUtil emailUtil;

    @BeforeEach
    void setUp() {
        locacaoRepository = mock(LocacaoRepository.class);
        emailUtil = mock(EmailUtil.class);
        enviadorDeEmailService = new EnviadorDeEmailService(locacaoRepository, emailUtil);
        Cliente cliente0 = new Cliente("Bill", "bill@mail.com");
        Cliente cliente1 = new Cliente("Larry", "larry@mail.com");
        Cliente cliente2 = new Cliente("Steve", "steve@mail.com");

        Imovel imovel = new Imovel();

        //em atraso
        Locacao locacao0 = new Locacao();
        locacao0.setInquilino(cliente0);
        locacao0.setImovel(imovel);
        locacao0.setAlugueis(
                Arrays.asList(
                        new Aluguel(locacao0, LocalDate.now().minusMonths(2))
                )
        );

        //em dia
        Locacao locacao1 = new Locacao();
        locacao1.setInquilino(cliente1);
        locacao1.setImovel(imovel);
        locacao1.setAlugueis(
                Arrays.asList(
                        new Aluguel(locacao1, LocalDate.now())
                )
        );

        //em atraso
        Locacao locacao2 = new Locacao();
        locacao2.setInquilino(cliente2);
        locacao2.setImovel(imovel);
        locacao2.setAlugueis(
                Arrays.asList(
                        new Aluguel(locacao2, LocalDate.now().minusMonths(1))
                )
        );

        when(locacaoRepository.findAll()).thenReturn(
                Arrays.asList(locacao0, locacao1, locacao2)
        );
    }

    @Test
    void deveEnviarEmailComSucesso(){
        try {
            doNothing()
                    .when(emailUtil)
                    .enviarEmail(anyString());
        enviadorDeEmailService.enviarEmailDevedores();
        } catch (EmailNaoEnviadoException e) {
            log.error(e.getMessage());
            fail();
        }
        assertTrue(true);
    }

    @Test
    void deveEnviarEmailComExcecaoTratada(){
        try {
            doNothing().doThrow(new EmailNaoEnviadoException("Exceção deve ser tratada"))
                    .when(emailUtil)
                    .enviarEmail(anyString());
            enviadorDeEmailService.enviarEmailDevedores();
        } catch (EmailNaoEnviadoException e) {
            fail();
        }
        assertTrue(true);
    }



    @Test
    void deveTestarValorPagarSemMulta() {

    }

    @Test
    void deveTestarValorPagarComMulta() {

    }
}