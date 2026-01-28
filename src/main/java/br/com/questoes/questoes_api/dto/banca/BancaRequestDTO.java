package br.com.questoes.questoes_api.dto.banca;

public class BancaRequestDTO {
    private String nome;
    private String sigla;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }
}
