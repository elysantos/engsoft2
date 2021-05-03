package br.edu.ifma.engsoft2.util;

import br.edu.ifma.engsoft2.exceptions.EmailNaoEnviadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailUtil {
    public void enviarEmail(String email) throws EmailNaoEnviadoException {
        log.info("Enviando email: {}", email);
    }
}
