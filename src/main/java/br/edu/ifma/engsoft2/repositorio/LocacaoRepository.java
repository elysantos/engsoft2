package br.edu.ifma.engsoft2.repositorio;

import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Integer> {

    List<Locacao> findAllByInquilino(Cliente cliente);

    void delete(Locacao locacao);

    void deleteAll();

    Locacao save(Locacao locacao);

    boolean existsByImovelAndAtivo(Imovel imovel, boolean ativo);

    List<Locacao> findAllByAtivoIsTrueAndValorAluguelIsLessThanEqual(BigDecimal valor);
}
