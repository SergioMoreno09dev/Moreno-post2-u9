package com.universidad.estudiantes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    @Email(message = "El correo debe ser una dirección válida")
    @NotBlank(message = "El correo es obligatorio")
    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;
    @NotBlank(message = "La carrera es obligatoria")
    @Column(name = "carrera", nullable = false, length = 100)
    private String carrera;

    // Constructor vacío requerido por JPA
    public Estudiante() {
    }

    // Getters y setters (generados por el IDE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    // Agregar en la clase Estudiante (lado inverso — mappedBy):
    @ManyToMany(mappedBy = "estudiantes")
    private Set<Curso> cursos = new HashSet<>();
    // Getter:
    public Set<Curso> getCursos() { return cursos; }
}
