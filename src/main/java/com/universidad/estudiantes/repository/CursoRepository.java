package com.universidad.estudiantes.repository;

import com.universidad.estudiantes.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    // JOIN FETCH para cargar estudiantes en la misma consulta (evita N+1)
    @Query("SELECT c FROM Curso c LEFT JOIN FETCH c.estudiantes")
    List<Curso> findAllConEstudiantes();
// Cargar un curso específico con sus estudiantes
    @Query("SELECT c FROM Curso c LEFT JOIN FETCH c.estudiantes WHERE c.id= :id")
    Optional<Curso> findByIdConEstudiantes(Long id);
}
