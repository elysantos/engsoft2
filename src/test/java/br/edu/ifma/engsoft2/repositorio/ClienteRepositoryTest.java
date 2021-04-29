package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        Cliente cliente0 = new Cliente("Adão Godson", "00011100011", "998110022", "9988221100", "adao@gmail.com", LocalDate.of(1990, 1, 20));
        Cliente cliente1 = new Cliente("Eva Godson", "00011100011", "998110022", "9988221100", "eva@gmail.com", LocalDate.of(1991, 2, 20));
        Cliente cliente2 = new Cliente("Caim Adanson", "00011100011", "998110022", "9988221100", "caim@gmail.com", LocalDate.of(2010, 3, 20));

        clienteRepository.save(cliente0);
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
    }

    @Test
    void deveBuscarClientePorNome() {
        Cliente cliente = clienteRepository.findByNomeLike("Eva");
        assertTrue(cliente.getNome().equals("Eva Godson"));
    }

    @Test
    void deveBuscarTodosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        assertTrue(clientes.size() == 3);
    }
}