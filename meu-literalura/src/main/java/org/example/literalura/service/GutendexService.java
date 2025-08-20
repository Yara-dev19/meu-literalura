package org.example.literalura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.literalura.model.Autor;
import org.example.literalura.model.Livro;
import org.example.repository.LivroRepository;
import org.example.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GutendexService {

    private final LivroRepository livroRepo;
    private final AutorRepository autorRepo;

    public GutendexService(LivroRepository livroRepo, AutorRepository autorRepo) {
        this.livroRepo = livroRepo;
        this.autorRepo = autorRepo;
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        try {
            String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            JsonNode results = root.path("results").get(0);

            if (results == null) return null;

            Livro livro = new Livro();
            livro.setTitulo(results.path("title").asText());
            livro.setIdioma(results.path("languages").get(0).asText());
            livro.setDownloads(results.path("download_count").asInt());

            JsonNode autorNode = results.path("authors").get(0);
            Autor autor = new Autor();
            autor.setNome(autorNode.path("name").asText());
            if (!autorNode.path("birth_year").isNull()) autor.setAnoNascimento(autorNode.path("birth_year").asInt());
            if (!autorNode.path("death_year").isNull()) autor.setAnoFalecimento(autorNode.path("death_year").asInt());

            livro.setAutor(autor);

            autorRepo.save(autor);
            livroRepo.save(livro);

            return livro;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
