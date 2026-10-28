package br.com.timbro.backend1.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "emissores")
public class Emissor {
    
    @Id
    private Integer emissorId; 
    
    private String nome;
    private String cnpjCpf;
    private String email;
    private String senha;
    private StatusEmissor status;
}