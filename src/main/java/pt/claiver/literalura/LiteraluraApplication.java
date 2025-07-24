package pt.claiver.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pt.claiver.literalura.principal.Principal;
import pt.claiver.literalura.repository.AutorRepository;
import pt.claiver.literalura.repository.LivroRepository;
import pt.claiver.literalura.service.LivroService;


@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository repositorio;

	@Autowired
	private AutorRepository autorRepositorio;

	@Autowired
	private LivroService livroService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio, autorRepositorio, livroService);
		principal.exibeMenu();
	}
}
