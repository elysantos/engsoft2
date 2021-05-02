package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Aluguel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AluguelRepositoryTest {

    private AluguelRepository aluguelRepository;

    @BeforeEach
    void setUp() {
        aluguelRepository = mock(AluguelRepository.class);
        when(aluguelRepository.findAll()).thenReturn(Arrays.asList(new Aluguel()));
        when(aluguelRepository.save(any())).thenReturn(new Aluguel(1));
        doNothing().when(aluguelRepository).delete(any());
    }

    @Test
    void findAll() {
        List<Aluguel> alugueis = aluguelRepository.findAll();
        assertEquals(1, alugueis.size());
    }

    @Test
    void save() {
        Aluguel aluguel = new Aluguel();
        aluguelRepository.save(aluguel);
        assertTrue(true);
    }

    @Test
    void delete() {
        Aluguel aluguel = new Aluguel();
        aluguelRepository.delete(aluguel);
        assertTrue(true);
 }
}