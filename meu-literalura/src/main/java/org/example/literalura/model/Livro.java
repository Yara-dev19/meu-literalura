package org.example.literalura.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("title")
    private String titulo;

    private String idioma;

    @JsonAlias("download_count")
    private Integer downloads;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
}