package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Integer> {

    List<Imovel> findAll();

    List<Imovel> findAllByTipoImovelAndBairro(String tipoImovel, String bairro);

    Imovel save(Imovel imovel);

    void delete(Imovel imovel);


}
