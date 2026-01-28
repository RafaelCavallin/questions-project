package br.com.questoes.questoes_api.exception;

public class AssuntoNotFoundException extends RuntimeException {
    public AssuntoNotFoundException(Long id) {
        super("Assunto não encontrado: " + id);
    }
}
