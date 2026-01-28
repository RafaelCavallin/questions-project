-- Adiciona a coluna 'ativo' na tabela usuario
ALTER TABLE usuario
ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;

-- Cria a tabela role
CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Cria a tabela de associação usuario_role para o relacionamento muitos-para-muitos
CREATE TABLE usuario_role (
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);