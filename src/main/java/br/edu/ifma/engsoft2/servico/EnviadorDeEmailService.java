package br.edu.ifma.engsoft2.servico;

import br.edu.ifma.engsoft2.exceptions.EmailNaoEnviadoException;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
import br.edu.ifma.engsoft2.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnviadorDeEmailService {
    private final LocacaoRepository locacaoRepository;
    private final EmailUtil emailUtil;

    private List<String> emailDevedores(){
        List<Locacao> locacoes = locacaoRepository.findAll();
        log.info("Iniciando envio de cobranças, total: {}", locacoes.size());
        if(locacoes.isEmpty()){
            log.info("nenhuma cobrança a enviar");
        }
        List<Cliente> inadimplentes = locacoes.stream()
                .filter(Locacao::existeVencido)
                .map(Locacao::getInquilino)
                .collect(Collectors.toList());
        log.info("Total de inadimplentes, total: {}", inadimplentes.size());
        return inadimplentes.stream()
                .map(Cliente::getEmail)
                .collect(Collectors.toList());
    }

    public void enviarEmailDevedores(){
        for (String s : emailDevedores()) {
            try {
                emailUtil.enviarEmail(s);
                log.info("Email enviado: {}", s);
            } catch (EmailNaoEnviadoException e) {
                log.error(e.getMessage());
            }
        }
    }


}
