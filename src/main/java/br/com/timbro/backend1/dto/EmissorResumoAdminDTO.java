package br.com.timbro.backend1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmissorResumoAdminDTO {
    private Integer emissorId;
    private String nome;
    private String email;
    private String cnpjCpf;
    private String status;
}