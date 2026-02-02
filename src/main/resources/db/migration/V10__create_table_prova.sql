CREATE TABLE prova (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    objetivo VARCHAR(20) NOT NULL,
    tempo_em_minutos INTEGER NOT NULL,
    ativa BOOLEAN NOT NULL DEFAULT TRUE,
    criada_em TIMESTAMP NOT NULL,
    atualizada_em TIMESTAMP NOT NULL,
    banca_id BIGINT,
    CONSTRAINT fk_banca FOREIGN KEY (banca_id) REFERENCES banca(id)
);

CREATE INDEX idx_prova_ativa ON prova(ativa);
CREATE INDEX idx_prova_banca ON prova(banca_id);

-- Tabela de relacionamento PROVA_DISCIPLINA
CREATE TABLE prova_disciplina (
    prova_id BIGINT NOT NULL,
    disciplina_id BIGINT NOT NULL,
    PRIMARY KEY (prova_id, disciplina_id),
    CONSTRAINT fk_prova_disciplina_prova FOREIGN KEY (prova_id) REFERENCES prova(id),
    CONSTRAINT fk_prova_disciplina_disciplina FOREIGN KEY (disciplina_id) REFERENCES disciplina(id)
);

CREATE INDEX idx_prova_disciplina_prova ON prova_disciplina(prova_id);
CREATE INDEX idx_prova_disciplina_disciplina ON prova_disciplina(disciplina_id);

-- Tabela de relacionamento PROVA_QUESTAO
CREATE TABLE prova_questao (
    prova_id BIGINT NOT NULL,
    questao_id BIGINT NOT NULL,
    PRIMARY KEY (prova_id, questao_id),
    CONSTRAINT fk_prova FOREIGN KEY (prova_id) REFERENCES prova(id),
    CONSTRAINT fk_questao FOREIGN KEY (questao_id) REFERENCES questao(id)
);

CREATE INDEX idx_prova_questao_prova ON prova_questao(prova_id);
CREATE INDEX idx_prova_questao_questao ON prova_questao(questao_id);
