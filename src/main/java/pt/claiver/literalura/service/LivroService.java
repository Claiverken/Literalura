package pt.claiver.literalura.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.claiver.literalura.model.Autor;
import pt.claiver.literalura.model.DadosLivro;
import pt.claiver.literalura.model.DadosResultado;
import pt.claiver.literalura.model.Livro;
import pt.claiver.literalura.repository.AutorRepository;
import pt.claiver.literalura.repository.LivroRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverterDados conversor = new ConverterDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Transactional
    public void buscarSalvarLivroPorTitulo(String titulo) {
        // 1. Busca dados do livro na API
        String url = ENDERECO + titulo.replace(" ", "%20");
        String json = consumo.obterDados(url);
        DadosResultado resultado = conversor.obterDados(json, DadosResultado.class);

        if (resultado == null || resultado.results().isEmpty()) {
            System.out.println("Livro não encontrado na API externa.");
            return;
        }

        DadosLivro dadosLivro = resultado.results().stream()
                .filter(l -> l.titulo() != null && !l.autor().isEmpty())
                .findFirst()
                .orElse(null);

        if (dadosLivro == null) {
            System.out.println("Nenhum livro encontrado com título e autor válidos.");
            return;
        }

        // 2. Verifica se o livro já existe no nosso banco
        if (livroRepository.existsByTitulo(dadosLivro.titulo())) {
            System.out.println("O livro '" + dadosLivro.titulo() + "' já está registrado.");
            return;
        }

        // 3. Busca ou cria o autor (DENTRO da mesma transação)
        var dadosAutor = dadosLivro.autor().get(0);
        Optional<Autor> autorOptional = autorRepository.findByNome(dadosAutor.nome());

        // Se o autor não existir, cria um novo. Se existir, usa o que foi encontrado.
        // O orElseGet garante que a nova instância do Autor só é criada se necessário.
        Autor autor = autorOptional.orElseGet(() -> new Autor(dadosAutor.nome(), dadosAutor.anodeNascimento(), dadosAutor.anoDeFalecimento()));

        // 4. Cria o livro e associa o autor
        Livro livro = new Livro();
        livro.setTitulo(dadosLivro.titulo());
        livro.setIdioma(String.join(",", dadosLivro.idioma()));
        livro.setDownload(dadosLivro.numeroDeDownloads() != null ? dadosLivro.numeroDeDownloads() : 0);
        livro.setAutor(autor);

        // 5. Salva o livro (e o autor, se for novo, graças à cascata e à transação)
        livroRepository.save(livro);

        System.out.println("----- Livro Salvo com Sucesso -----");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor().getNome());
        System.out.println("Idioma: " + livro.getIdioma());
        System.out.println("Número de downloads: " + livro.getDownload());
        System.out.println("-----------------------------------");
    }

    @Transactional(readOnly = true)
    public List<Livro> listarTodosOsLivros() {
        return livroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Map<String, List<Livro>> listarAutoresRegistrados() {
        return livroRepository.findAll().stream()
                .filter(livro -> livro.getAutor() != null)
                .collect(Collectors.groupingBy(livro -> livro.getAutor().getNome()));
    }

    @Transactional(readOnly = true)
    public Map<String, List<Livro>> listarAutoresVivosNoAno(int ano) {
        return livroRepository.findAll().stream()
                .filter(livro -> {
                    Autor autor = livro.getAutor();
                    return autor != null &&
                            autor.getAnoDeNascimento() != null &&
                            autor.getAnoDeNascimento() <= ano &&
                            (autor.getAnoDeFalecimento() == null || autor.getAnoDeFalecimento() > ano);
                })
                .collect(Collectors.groupingBy(livro -> livro.getAutor().getNome()));
    }

    @Transactional(readOnly = true)
    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findAll().stream()
                .filter(livro -> livro.getIdioma() != null && livro.getIdioma().toLowerCase().contains(idioma.toLowerCase()))
                .collect(Collectors.toList());
    }
}
