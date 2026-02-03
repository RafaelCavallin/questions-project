CREATE TABLE tentativa (
    id BIGSERIAL PRIMARY KEY,
    criada_em TIMESTAMP NOT NULL,
    finalizada_em TIMESTAMP,
    tempo_gasto_em_segundos INTEGER,
    status VARCHAR(20) NOT NULL,
    acertou BOOLEAN,
    usuario_id BIGINT NOT NULL,
    questao_id BIGINT NOT NULL,
    sessao_estudo_id BIGINT,
    prova_id BIGINT,
    CONSTRAINT fk_tentativa_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_tentativa_questao FOREIGN KEY (questao_id) REFERENCES questao(id),
    CONSTRAINT fk_tentativa_sessao_estudo FOREIGN KEY (sessao_estudo_id) REFERENCES sessao_estudo(id),
    CONSTRAINT fk_tentativa_prova FOREIGN KEY (prova_id) REFERENCES prova(id),
    CONSTRAINT tentativa_contexto_check CHECK (
        (sessao_estudo_id IS NOT NULL OR prova_id IS NOT NULL)
    )
);

CREATE INDEX idx_tentativa_usuario_id ON tentativa(usuario_id);
CREATE INDEX idx_tentativa_questao_id ON tentativa(questao_id);
CREATE INDEX idx_tentativa_criada_em ON tentativa(criada_em);
