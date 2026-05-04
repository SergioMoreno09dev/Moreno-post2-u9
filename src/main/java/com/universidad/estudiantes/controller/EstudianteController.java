package com.universidad.estudiantes.controller;

import com.universidad.estudiantes.model.Estudiante;
import com.universidad.estudiantes.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {
    private final EstudianteService service;
    public EstudianteController(EstudianteService service) {
        this.service = service;
    }
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", service.listarTodos());
        return "estudiantes/lista";
    }
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("titulo", "Nuevo Estudiante");
        return "estudiantes/formulario";
    }
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Estudiante estudiante,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", estudiante.getId() == null
                    ? "Nuevo Estudiante" : "Editar Estudiante");
            return "estudiantes/formulario";
        }
        service.guardar(estudiante);
        return "redirect:/estudiantes";
    }
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model
            model) {
        model.addAttribute("estudiante", service.buscarPorId(id));
        model.addAttribute("titulo", "Editar Estudiante");
        return "estudiantes/formulario";
    }
    @GetMapping("/eliminar/{id}")
    public String confirmarEliminar(@PathVariable Long id, Model model) {
        model.addAttribute("estudiante", service.buscarPorId(id));
        return "estudiantes/confirmar-eliminar";
    }
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/estudiantes";
    }
}