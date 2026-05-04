package com.universidad.estudiantes.controller;

import com.universidad.estudiantes.model.Curso;
import com.universidad.estudiantes.service.CursoService;
import com.universidad.estudiantes.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cursos")
public class CursoController {
    private final CursoService cursoService;
    private final EstudianteService estudianteService;

    public CursoController(CursoService c, EstudianteService e) {
        this.cursoService = c;
        this.estudianteService = e;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cursos", cursoService.listarTodos());
        return "cursos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarNuevo(Model model) {
        model.addAttribute("curso", new Curso());
        return "cursos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Curso curso,
                          BindingResult result) {
        if (result.hasErrors()) return "cursos/formulario";
        cursoService.guardar(curso);
        return "redirect:/cursos";
    }

    @GetMapping("/{id}/inscribir")
    public String mostrarInscripcion(@PathVariable Long id, Model model) {
        model.addAttribute("curso", cursoService.buscarPorId(id));
        model.addAttribute("estudiantes",
                estudianteService.listarTodos());
        return "cursos/inscribir";
    }

    @PostMapping("/{cursoId}/inscribir/{estudianteId}")
    public String inscribir(@PathVariable Long cursoId,
                            @PathVariable Long estudianteId) {
        cursoService.inscribirEstudiante(cursoId, estudianteId);
        return "redirect:/cursos";
    }

    @PostMapping("/{cursoId}/desinscribir/{estudianteId}")
    public String desinscribir(@PathVariable Long cursoId,
                               @PathVariable Long estudianteId) {
        cursoService.desinscribirEstudiante(cursoId, estudianteId);
        return "redirect:/cursos";
    }
}

