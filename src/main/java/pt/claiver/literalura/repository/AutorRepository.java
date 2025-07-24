package pt.claiver.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.claiver.literalura.model.Autor;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);
}
