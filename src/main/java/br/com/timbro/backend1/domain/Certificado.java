package br.com.timbro.backend1.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Document(collection = "certificados")
public class Certificado {

    @Id
    private Integer certificadoId;
    
    private Integer emissorId;
    private String nomeAluno;
    private String curso;
    private Integer cargaHoraria;
    private LocalDate dataConclusao; 
    private String hash;
    private StatusCertificado status;
    private LocalDateTime dataEmissao; 
}