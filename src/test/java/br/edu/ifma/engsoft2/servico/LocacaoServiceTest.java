package br.edu.ifma.engsoft2.servico;

import br.edu.ifma.engsoft2.exceptions.LocacaoNotFoundException;
import br.edu.ifma.engsoft2.modelo.Aluguel;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.ImovelRepository;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class LocacaoServiceTest {

    private LocacaoRepository locacaoRepository;
    private ImovelRepository imovelRepository;
    private LocacaoService locacaoService;

    private Locacao locacaoAtrasada;
    private Locacao locacaoEmDia;


    @BeforeEach
    void setUp() {
        locacaoRepository = mock(LocacaoRepository.class);
        imovelRepository = mock(ImovelRepository.class);
        locacaoService = new LocacaoService(locacaoRepository, imovelRepository);

        Cliente cliente0 = new Cliente("Bill", "bill@mail.com");
        Cliente cliente1 = new Cliente("Larry", "larry@mail.com");

        Imovel imovel = new Imovel();

        //em atraso
        locacaoAtrasada = new Locacao();
        locacaoAtrasada.setInquilino(cliente0);
        locacaoAtrasada.setImovel(imovel);
        locacaoAtrasada.setValorAluguel(BigDecimal.valueOf(1000));
        locacaoAtrasada.setAlugueis(
                Arrays.asList(
                        new Aluguel(locacaoAtrasada, LocalDate.now().minusDays(2))
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
    }

    @Test
    void deveTestarValorPagoComMulta() {
        when(locacaoRepository.findById(anyInt()))
                .thenReturn(ofNullable(locacaoAtrasada));

        BigDecimal valorCalculado = locacaoAtrasada.getValorAluguel();
        valorCalculado = valorCalculado
                .multiply(BigDecimal.valueOf(0.0033))
                .multiply(BigDecimal.valueOf(2))
                .add(valorCalculado);
        try {
            BigDecimal valorPagar = locacaoService.valorPagarComMulta(locacaoAtrasada.getId(), LocalDate.now());
            if(valorPagar.compareTo(valorCalculado) != 0){
                log.error("Valor Pagar: {}", valorPagar);
                log.error("Valor Calculado: {}", valorCalculado);
                fail();
            }
            assertTrue(true);
        } catch (LocacaoNotFoundException e) {
            log.error(e.getMessage());
            fail();
        }
    }

    @Test
    void deveTestarValorPagoSemMulta() {
        when(locacaoRepository.findById(anyInt()))
                .thenReturn(ofNullable(locacaoEmDia));
        BigDecimal valorCalculado = locacaoEmDia.getValorAluguel();
        try {
            BigDecimal valorPagar = locacaoService.valorPagarComMulta(locacaoEmDia.getId(), LocalDate.now());
            if(valorPagar.compareTo(valorCalculado) != 0){
                fail();
            }
            assertTrue(true);
        } catch (LocacaoNotFoundException e) {
            log.error(e.getMessage());
            fail();
        }
    }


}