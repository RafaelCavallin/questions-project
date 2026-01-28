package br.com.questoes.questoes_api.exception;

public class BancaNotFoundException extends RuntimeException {
    public BancaNotFoundException(Long id) {
        super("Banca não encontrada para o id: " + id);
    }
}
