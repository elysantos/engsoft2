package br.edu.ifma.engsoft2.servico;

import br.edu.ifma.engsoft2.exceptions.LocacaoNotFoundException;
import br.edu.ifma.engsoft2.modelo.Imovel;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.ImovelRepository;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocacaoService {
    private final LocacaoRepository locacaoRepository;
    private final ImovelRepository imovelRepository;

    public List<Imovel> apartamentosDisponivelsPorBairro(String bairro){

        List<Imovel> imoveis = imovelRepository
                .findAllByTipoImovelAndBairro("Apartamento", bairro);
        imoveis.stream()
                .filter(imovel ->
                        locacaoRepository.existsByImovelAndAtivo(imovel, true))
                .collect(Collectors.toList());
     return imoveis;
    }

    public List<Imovel> recuperarDisponiveisPorPreco(BigDecimal preco){
        List<Locacao> locacoes = locacaoRepository
                .findAllByAtivoIsTrueAndValorAluguelIsLessThanEqual(preco);
        return locacoes
                .stream()
                .map(Locacao::getImovel)
                .collect(Collectors.toList());
    }

    public BigDecimal valorPagarComMulta(int idLocacao, LocalDate diaPagamento) throws LocacaoNotFoundException {
        Optional<Locacao> optionalLocacao = locacaoRepository.findById(idLocacao);
        if(optionalLocacao.isPresent()){
            Locacao locacao = optionalLocacao.get();
            return locacao.valorPagar(diaPagamento);
        }
        throw new LocacaoNotFoundException();
    }

}
