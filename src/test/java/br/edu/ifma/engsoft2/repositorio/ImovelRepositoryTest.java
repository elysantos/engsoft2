package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Imovel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImovelRepositoryTest {

    private ImovelRepository imovelRepository;

    @BeforeEach
    void setUp() {
        imovelRepository = mock(ImovelRepository.class);
        when(imovelRepository.findAll()).thenReturn(
                Arrays.asList(new Imovel())
        );
        when(imovelRepository.save(any())).thenReturn(new Imovel());
        doNothing().when(imovelRepository).delete(any());
        when(imovelRepository.findAllByTipoImovelAndBairro(anyString(), anyString()))
                .thenReturn(
                        Arrays.asList(new Imovel())
                );
    }

    @Test
    void deveTestarSalvar() {
        imovelRepository.save(new Imovel());
        assertTrue(true);
    }

    @Test
    void deveTestarDelete() {
        Imovel imovel = new Imovel();
        imovelRepository.delete(imovel);
        assertTrue(true);
    }

    @Test
    void deveListarTodos() {
        List<Imovel> imoveis = imovelRepository.findAll();
        assertEquals(1, imoveis.size());
    }

    @Test
    void deveListarPorBairroETipo() {
        List<Imovel> imoveis = imovelRepository.findAllByTipoImovelAndBairro("Casa", "Cohama");
        assertEquals(1, imoveis.size());
    }
}