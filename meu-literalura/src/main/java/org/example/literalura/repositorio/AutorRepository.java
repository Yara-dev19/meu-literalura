package org.example.literalura.repositorio;

import org.example.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(int anoInicio, int anoFim);
}
