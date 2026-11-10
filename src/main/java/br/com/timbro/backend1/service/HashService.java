package br.com.timbro.backend1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.security.MessageDigest;

@Service
public class HashService {

    public String gerarHashSha256(MultipartFile arquivoPdf) throws Exception {
        // 1. Instancia o motor de hash nativo do Java configurado para SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        
        // 2. Lê todos os bytes do arquivo PDF enviado pelo Frontend
        byte[] bytesDoArquivo = arquivoPdf.getBytes();
        
        // 3. O motor processa os bytes e devolve o hash em formato de bytes criptografados
        byte[] hashBytes = digest.digest(bytesDoArquivo);
        
        // 4. Converte o array de bytes para uma String Hexadecimal legível
        StringBuilder hexString = new StringBuilder();
        
        // Adiciona o prefixo "0x" padronizado nos contratos da especificação (ex: CT9)
        hexString.append("0x"); 
        
        for (byte b : hashBytes) {
            // O %02x garante que bytes menores que 16 fiquem com um zero à esquerda (ex: 0a, 0b)
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
}