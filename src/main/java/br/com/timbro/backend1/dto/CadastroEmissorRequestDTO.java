package br.com.timbro.backend1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CadastroEmissorRequestDTO {
    @NotBlank (message = "O nome é obrigatório")
    private String nome;

    @NotBlank (message = "O email é obrigatório")
    private String email;

    @NotBlank (message = "O CNPJ é obrigatório")
    private String cnpj;

    @NotBlank (message = "A senha é obrigatória")
    private String senha;
}