package br.edu.ifma.engsoft2.servico;

import br.edu.ifma.engsoft2.exceptions.EmailNaoEnviadoException;
import br.edu.ifma.engsoft2.modelo.Cliente;
import br.edu.ifma.engsoft2.modelo.Locacao;
import br.edu.ifma.engsoft2.repositorio.LocacaoRepository;
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

    private List<String> emailDevedores(){
        List<Locacao> locaoces = locacaoRepository.findAll();
        List<Cliente> inadimplentes = locaoces.stream()
                .filter(Locacao::existeVencido)
                .map(Locacao::getInquilino)
                .collect(Collectors.toList());
        return inadimplentes.stream()
                .map(Cliente::getEmail)
                .collect(Collectors.toList());
    }

    public void enviarEmailDevedores(){
        for (String s : emailDevedores()) {
            try {
                enviarEmail(s);
            } catch (EmailNaoEnviadoException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void enviarEmail(String email) throws EmailNaoEnviadoException {
        log.info("Enviando email: {}", email);
    }
}
