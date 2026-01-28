-- V5__create_table_assunto.sql
CREATE TABLE assunto (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    disciplina_id BIGINT NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_assunto_disciplina FOREIGN KEY (disciplina_id) REFERENCES disciplina(id)
);

CREATE INDEX idx_assunto_disciplina_id ON assunto(disciplina_id);