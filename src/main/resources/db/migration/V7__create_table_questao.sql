-- Migration: Criação da tabela questao com suporte a herança SINGLE_TABLE
CREATE TABLE questao (
    id BIGSERIAL PRIMARY KEY,
    enunciado TEXT NOT NULL,
    nivel_dificuldade VARCHAR(20) NOT NULL,
    disciplina_id BIGINT NOT NULL,
    assunto_id BIGINT NOT NULL,
    banca_id BIGINT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    -- Campos para QuestaoMultiplaEscolha
    alternativa_a VARCHAR(255),
    alternativa_b VARCHAR(255),
    alternativa_c VARCHAR(255),
    alternativa_d VARCHAR(255),
    alternativa_e VARCHAR(255),
    alternativa_correta VARCHAR(5),
    -- Campo para QuestaoCertoErrado
    CONSTRAINT fk_disciplina FOREIGN KEY (disciplina_id) REFERENCES disciplina(id),
    CONSTRAINT fk_assunto FOREIGN KEY (assunto_id) REFERENCES assunto(id),
    CONSTRAINT fk_banca FOREIGN KEY (banca_id) REFERENCES banca(id)
);

-- Índices opcionais
CREATE INDEX idx_questao_disciplina ON questao(disciplina_id);
CREATE INDEX idx_questao_assunto ON questao(assunto_id);
CREATE INDEX idx_questao_banca ON questao(banca_id);
CREATE INDEX idx_questao_tipo ON questao(tipo);