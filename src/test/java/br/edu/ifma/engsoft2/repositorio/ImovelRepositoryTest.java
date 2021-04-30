package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ImovelRepositoryTest {

    @Autowired
    private ImovelRepository imovelRepository;

    @BeforeEach
    void setUp() {
        Imovel imovel0 = new Imovel(
                1, "Apartamento", "Rua 1", "Araçagy", "65000000",
                BigDecimal.valueOf(65), 2, 2, 1, 1,
                BigDecimal.valueOf(1000), ""
        );
        Imovel imovel1 = new Imovel(
                1, "Apartamento", "Rua 1", "Araçagy", "65000000",
                BigDecimal.valueOf(48), 2, 2, 1,
                1, BigDecimal.valueOf(1100), ""
        );
        Imovel imovel2 = new Imovel(1, "Casa", "Rua 1",
                "Araçagy", "65000000", BigDecimal.valueOf(72),
                2, 2, 1, 1,
                BigDecimal.valueOf(1300), ""
        );
        Imovel imovel3 = new Imovel(
                1, "Apartamento", "Rua 1", "Araçagy", "65000000",
                BigDecimal.valueOf(65),
                2, 2, 1, 1, BigDecimal.valueOf(1200), "");
        imovelRepository.save(imovel0);
        imovelRepository.save(imovel1);
        imovelRepository.save(imovel2);
        imovelRepository.save(imovel3);
    }

    @AfterEach
    void tearDown() {
        imovelRepository.deleteAll();
    }


    @Test
    void deveListarTodos() {
        List<Imovel> imoveis = imovelRepository.findAll();
        assertEquals(4, imoveis.size() );
    }

    @Test
    void deveTestarAlteracao() {
        List<Imovel> imoveis = imovelRepository.findAll();
        Optional<Imovel> optionalImovel = imovelRepository.findById(imoveis.get(0).getId());
        if (optionalImovel.isPresent()) {
            Imovel imovel = optionalImovel.get();
            imovel.setTipoImovel("Casa");
            Imovel imovelSalvo = imovelRepository.save(imovel);
            assertEquals(imovelSalvo, imovel);
            return;
        }
        fail();

    }

    @Test
    void deveRemoverUmItem() {
        List<Imovel> imoveis = imovelRepository.findAll();
        imovelRepository.delete(imoveis.get(0));
        List<Imovel> novaLista = imovelRepository.findAll();
        assertEquals(imoveis.size(), (novaLista.size() + 1));
    }

    @Test
    void deveRemoverTodos() {
        imovelRepository.deleteAll();
        List<Imovel> items = imovelRepository.findAll();
        assertTrue(items.isEmpty());
    }
}