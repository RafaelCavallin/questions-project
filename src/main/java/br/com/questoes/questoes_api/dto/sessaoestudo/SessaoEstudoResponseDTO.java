package br.com.questoes.questoes_api.dto.sessaoestudo;

import br.com.questoes.questoes_api.enums.SessaoEstudoStatus;
import java.time.LocalDateTime;

public class SessaoEstudoResponseDTO {
    private Long id;
    private Long usuarioId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private SessaoEstudoStatus status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public LocalDateTime getEndedAt() { return endedAt; }
    public void setEndedAt(LocalDateTime endedAt) { this.endedAt = endedAt; }
    public SessaoEstudoStatus getStatus() { return status; }
    public void setStatus(SessaoEstudoStatus status) { this.status = status; }
}
