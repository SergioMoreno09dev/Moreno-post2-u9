package com.universidad.estudiantes.controller;

import com.universidad.estudiantes.model.Usuario;
import com.universidad.estudiantes.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioService service;

    public AuthController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String mostrarLogin() { return "auth/login"; }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute Usuario usuario,
                            BindingResult result) {
        if (result.hasErrors()) return "auth/registro";
        try {
            service.registrar(usuario);
            return "redirect:/login?registrado";
        } catch (RuntimeException e) {
            result.rejectValue("email", "error.email", e.getMessage());
            return "auth/registro";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        // Busca el objeto Usuario completo por su email
        service.buscarPorEmail(auth.getName())
                .ifPresent(u -> model.addAttribute("usuario", u));
        model.addAttribute("roles", auth.getAuthorities());
        return "dashboard";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("usuarios", service.listarTodos());
        return "admin/panel";
    }
}