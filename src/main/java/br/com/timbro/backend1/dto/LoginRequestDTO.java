package br.com.timbro.backend1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    
    // O @NotBlank aciona automaticamente o seu TratadorDeErros (Erro 400) se o Frontend mandar vazio
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;
}