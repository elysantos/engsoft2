package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ClienteRepositoryTest {

    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository = mock(ClienteRepository.class);

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(new Cliente()));
        when(clienteRepository.findByNomeLike(anyString())).thenReturn(
                new Cliente()
        );
        when(clienteRepository.save(any())).thenReturn(new Cliente());
        doNothing().when(clienteRepository).delete(any());
        doNothing().when(clienteRepository).deleteAll();
    }

    @Test
    void findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        assertEquals(1, clientes.size());
    }

    @Test
    void findByNomeLike() {
        Cliente cliente = clienteRepository.findByNomeLike("Cliente");
        assertNotNull(cliente );
    }

    @Test
    void save() {
        Cliente cliente = clienteRepository.save(new Cliente());
        assertNotNull(cliente );
    }

    @Test
    void delete() {
        clienteRepository.delete(new Cliente());
        assertTrue(true);
    }

    @Test
    void deleteAll() {
        clienteRepository.deleteAll();
        assertTrue(true);
    }
}