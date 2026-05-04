package com.universidad.estudiantes.service;

import com.universidad.estudiantes.model.Usuario;
import com.universidad.estudiantes.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Usuario u = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + email));
        return User.builder()
                .username(u.getEmail())
                .password(u.getContrasenia())   // BCrypt hash
                .roles(u.getRol().replace("ROLE_", ""))  // "ADMIN" o "USER"
                .disabled(!u.isActivo())
                .build();
    }
}
