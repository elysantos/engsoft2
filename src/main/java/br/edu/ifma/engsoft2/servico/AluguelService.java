package br.edu.ifma.engsoft2.servico;

import br.edu.ifma.engsoft2.exceptions.*;
import br.edu.ifma.engsoft2.modelo.Aluguel;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.AluguelRepository;
import br.edu.ifma.engsoft2.repositorio.ClienteRepository;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AluguelService {
    private final AluguelRepository aluguelRepository;
    private final LocacaoRepository locacaoRepository;
    private final ClienteRepository clienteRepository;

    public List<Aluguel> alugueisPagosPorCliente(String nomeCliente) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findByNomeLike(nomeCliente);
        if (cliente == null) {
            throw new ClienteNotFoundException();
        }

        List<Locacao> locacoes = locacaoRepository.findAllByInquilino(cliente);

        List<Aluguel> alugueis = new ArrayList<>();

        for (Locacao locacao : locacoes) {
            alugueis.addAll(locacao.getAlugueis().stream()
                    .filter(a ->
                            a.getDataPagamento() != null
                                    && a.getValorPago().compareTo(BigDecimal.ONE) < 1
                    ).collect(Collectors.toList()));
        }

        return alugueis;
    }

    public List<Aluguel> alugueisPagosEmAtrasoPorCliente(String nomeCliente) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findByNomeLike(nomeCliente);
        if (cliente == null) {
            throw new ClienteNotFoundException();
        }

        List<Locacao> locacoes = locacaoRepository.findAllByInquilino(cliente);

        List<Aluguel> alugueis = new ArrayList<>();

        for (Locacao locacao : locacoes) {
            alugueis.addAll(locacao.getAlugueis().stream()
                    .filter(a ->
                            (a.getDataPagamento() != null &&
                                    a.getDataPagamento()
                                    .isAfter(a.getDataVencimento())
                                    && a.getValorPago()
                                    .compareTo(BigDecimal.ONE) < 1)
                    ).collect(Collectors.toList()));
        }

        return alugueis;
    }


    public void realizarPagamentoAluguel(BigDecimal valorPago, LocalDate dataPagamento, int idLocacao) throws LocacaoNotFoundException, ValorIncorretoException, SemAluguelException, ValorBaixoException {
        Optional<Locacao> optional = locacaoRepository.findById(idLocacao);
        if(optional.isEmpty()){
            throw new LocacaoNotFoundException();
        }
        Locacao locacao = optional.get();
        BigDecimal valorPagar = locacao.valorPagar(dataPagamento);
        log.info("Valor pagar: {}", valorPagar);
        log.info("Valor pago: {}", valorPago);
        if(valorPago.compareTo(valorPagar) > 0){
            throw new ValorIncorretoException();
        }else if(valorPago.compareTo(valorPagar) < 0){
            throw new ValorBaixoException();
        }

        Aluguel aluguel = locacao.proximoPagar();
        aluguel.setDataPagamento(dataPagamento);
        aluguel.setValorPago(valorPago);
        aluguelRepository.save(aluguel);
    }

}
