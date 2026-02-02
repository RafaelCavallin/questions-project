-- Migration: Criação da tabela sessao_estudo
CREATE TABLE sessao_estudo (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_sessao_estudo_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE INDEX idx_sessao_estudo_usuario ON sessao_estudo(usuario_id);
