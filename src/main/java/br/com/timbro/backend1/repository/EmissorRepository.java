package br.com.timbro.backend1.repository;

import br.com.timbro.backend1.domain.Emissor;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface EmissorRepository extends MongoRepository<Emissor, Integer> {
    // Esse método customizado será usado para buscar o emissor no login
    Optional<Emissor> findByEmail(String email);
}