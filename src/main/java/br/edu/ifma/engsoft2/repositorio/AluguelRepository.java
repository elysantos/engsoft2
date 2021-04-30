package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Integer> {

    List<Aluguel> findAll();

    Aluguel save(Aluguel aluguel);

    void delete(Aluguel aluguel);

}
