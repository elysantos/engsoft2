package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findAll();

    @Query("SELECT c from Cliente c WHERE nome LIKE CONCAT(?1, '%') ")
    Cliente findByNomeLike( String nome);

    Cliente save(Cliente cliente);

    void delete(Cliente cliente);

    void deleteAll();

}
