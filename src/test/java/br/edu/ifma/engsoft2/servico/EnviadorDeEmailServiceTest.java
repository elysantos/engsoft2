package br.edu.ifma.engsoft2.servico;

import br.edu.ifma.engsoft2.exceptions.EmailNaoEnviadoException;
import br.edu.ifma.engsoft2.modelo.Aluguel;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnviadorDeEmailServiceTest {

    private EnviadorDeEmailService enviadorDeEmailService;
    private LocacaoRepository locacaoRepository;

    @BeforeEach
    void setUp() {
        locacaoRepository = mock(LocacaoRepository.class);
        enviadorDeEmailService = new EnviadorDeEmailService(locacaoRepository);
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
                        new Aluguel(locacao0, LocalDate.now().minusMonths(1))
                )
        );

        //em dia
        Locacao locacao1 = new Locacao();
        locacao0.setInquilino(cliente1);
        locacao0.setImovel(imovel);
        locacao0.setAlugueis(
                Arrays.asList(
                        new Aluguel(locacao0, LocalDate.now())
                )
        );

        //em atraso
        Locacao locacao2 = new Locacao();
        locacao0.setInquilino(cliente2);
        locacao0.setImovel(imovel);
        locacao0.setAlugueis(
                Arrays.asList(
                        new Aluguel(locacao0, LocalDate.now().minusMonths(1))
                )
        );


        when(locacaoRepository.findAll()).thenReturn(
                Arrays.asList(locacao0, locacao1, locacao2)
        );

    }

    @Test
    void deveEnviarEmailComSucesso() throws EmailNaoEnviadoException {
        
        enviadorDeEmailService.enviarEmailDevedores();
    }

    @Test
    void deveEnviarEmailComExcecaoTratada() {

    }
}