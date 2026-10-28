package br.com.timbro.backend1.repository;

import br.com.timbro.backend1.domain.Certificado;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface CertificadoRepository extends MongoRepository<Certificado, Integer> {
    // Usado na Task 5.1 (CT15) para listar os certificados de um emissor específico
    List<Certificado> findByEmissorId(Integer emissorId);
    
    // Usado na Task 4.1 (CT12) para a validação via número de série (hash)
    Optional<Certificado> findByHash(String hash);
}