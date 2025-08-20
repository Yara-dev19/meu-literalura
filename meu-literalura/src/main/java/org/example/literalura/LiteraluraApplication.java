package org.example.literalura;

import org.example.literalura.model.Livro;
import org.example.model.repositorio.LivroRepository;
import org.example.model.repositoio.AutorRepository;
import org.example.literalura.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    private final GutendexService gutendexService;
    private final LivroRepository livroRepo;
    private final AutorRepository autorRepo;

    public LiteraluraApplication(GutendexService gutendexService, LivroRepository livroRepo, AutorRepository autorRepo) {
        this.gutendexService = gutendexService;
        this.livroRepo = livroRepo;
        this.autorRepo = autorRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== LITERALURA =====");
            System.out.println("1 - Buscar livro por título");
            System.out.println("2 - Listar todos os livros");
            System.out.println("3 - Listar autores");
            System.out.println("4 - Listar autores vivos em determinado ano");
            System.out.println("5 - Estatísticas de livros por idioma");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine(); // consome \n

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o título do livro: ");
                    String titulo = sc.nextLine();
                    Livro livro = gutendexService.buscarLivroPorTitulo(titulo);
                    if (livro != null) System.out.println(livro);
                    else System.out.println("Livro não encontrado.");
                }
                case 2 -> livroRepo.findAll().forEach(System.out::println);
                case 3 -> autorRepo.findAll().forEach(System.out::println);
                case 4 -> {
                    System.out.print("Digite o ano: ");
                    int ano = sc.nextInt();
                    autorRepo.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano)
                            .forEach(System.out::println);
                }
                case 5 -> {
                    System.out.println("Idioma 1: English");
                    System.out.println("Quantidade: " + livroRepo.findByIdioma("en").size());

                    System.out.println("Idioma 2: French");
                    System.out.println("Quantidade: " + livroRepo.findByIdioma("fr").size());
                }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        }
        sc.close();
    }
}