package pt.claiver.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.claiver.literalura.model.Livro;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTitulo(String titulo);
}
