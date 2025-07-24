package pt.claiver.literalura.principal;

import pt.claiver.literalura.model.Autor;
import pt.claiver.literalura.model.Livro;
import pt.claiver.literalura.repository.AutorRepository;
import pt.claiver.literalura.repository.LivroRepository;
import pt.claiver.literalura.service.LivroService;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private final LivroService livroService;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository, LivroService livroService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.livroService = livroService;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                1- Buscar livro pelo título
                2- Listar livros registrados
                3- Listar autores registrados
                4- Listar autores vivos em um determinado ano
                5- Listar livros em um determinado idioma
                0- Sair
                """;
            System.out.println("----- Menu Principal -----");
            System.out.println(menu);
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer do scanner

                switch (opcao) {
                    case 1 -> buscarLivroPorTitulo();
                    case 2 -> listarLivrosRegistrados();
                    case 3 -> listarAutoresRegistrados();
                    case 4 -> listarAutoresVivosEmDeterminadoAno();
                    case 5 -> listarLivrosEmDeterminadoIdioma();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Por favor, digite um número válido para a opção.");
                scanner.nextLine();
                opcao = -1;
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o título do livro que deseja buscar:");
        var titulo = scanner.nextLine();
        livroService.buscarSalvarLivroPorTitulo(titulo);
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroService.listarTodosOsLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            System.out.println("----- Livros Registrados -----");
            livros.forEach(livro -> {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor().getNome());
                System.out.println("Idioma: " + livro.getIdioma());
                System.out.println("Downloads: " + livro.getDownload());
                System.out.println("------------------------------");
            });
        }
    }

    private void listarAutoresRegistrados() {
        // 1. Chama o serviço para obter os dados
        Map<String, List<Livro>> autores = livroService.listarAutoresRegistrados();

        // 2. Lida com a exibição dos dados
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            System.out.println("----- Autores Registrados -----");
            // Ordena a saída pelo nome do autor
            autores.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        String nomeAutor = entry.getKey();
                        List<Livro> livros = entry.getValue();
                        Autor autor = livros.get(0).getAutor(); // Pega o autor de um dos livros

                        System.out.println("Autor: " + nomeAutor);
                        System.out.println("  Nascimento: " + (autor.getAnoDeNascimento() != null ? autor.getAnoDeNascimento() : "N/A"));
                        System.out.println("  Falecimento: " + (autor.getAnoDeFalecimento() != null ? autor.getAnoDeFalecimento() : "Vivo"));

                        // Coleta os títulos dos livros em uma string
                        String titulos = livros.stream()
                                .map(Livro::getTitulo)
                                .collect(Collectors.joining(", "));
                        System.out.println("  Livros: [" + titulos + "]");
                        System.out.println("-----------------------------");
                    });
        }
    }

    private void listarAutoresVivosEmDeterminadoAno() {
        System.out.println("Digite o ano para listar autores vivos:");
        int ano = scanner.nextInt();
        scanner.nextLine();

        Map<String, List<Livro>> autoresVivos = livroService.listarAutoresVivosNoAno(ano);
        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado no ano " + ano + ".");
        } else {
            System.out.println("----- Autores Vivos em " + ano + " -----");
            autoresVivos.forEach((nome, livros) -> {
                System.out.println("Autor: " + nome);
                System.out.println("Livros: " + livros.stream().map(Livro::getTitulo).toList());
                System.out.println("-----------------------------");
            });
        }
    }

    private void listarLivrosEmDeterminadoIdioma() {
        var menuIdioma = """
                Escolha o idioma para listar os livros:
                en - inglês
                pt - português
                es - espanhol
                fr - francês
                """;
        System.out.println(menuIdioma);
        String idioma = scanner.nextLine().trim().toLowerCase();

        List<Livro> livros = livroService.listarLivrosPorIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma: " + idioma);
        } else {
            System.out.println("----- Livros em '" + idioma + "' -----");
            livros.forEach(livro -> {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor().getNome());
                System.out.println("Idioma: " + livro.getIdioma());
                System.out.println("Número de downloads: " + (livro.getDownload() != null ? livro.getDownload() : "Desconhecido"));
                System.out.println("-----------------------------");
            });
        }
    }
}
