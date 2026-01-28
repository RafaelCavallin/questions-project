package br.com.questoes.questoes_api.exception;

public class DisciplinaNotFoundException extends RuntimeException {
    public DisciplinaNotFoundException(Long id) {
        super("Disciplina não encontrada: " + id);
    }
}
