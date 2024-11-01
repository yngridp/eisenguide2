package com.eisenguide2.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eisenguide2.exception.UserNotFoundException;
import com.eisenguide2.model.User;
import com.eisenguide2.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name ="Usuários", description = "Controlador para salvar e editar dados de usuários" )
public class UserController {

    @Autowired
    private UserService userService;

    
    @PostMapping("/register")
    @Operation(summary="Salva dados de usuário", description ="Método para salvar dados de usuários")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Verifica se o usuário já existe
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Conflito se o usuário já existe
        }

        // Cadastra o novo usuário
        User registeredUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser); // Retorna o usuário cadastrado
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Optional<User> user = userService.login(username, password);
        if (user.isPresent()) {
            return ResponseEntity.ok("Login successful"); // Login bem-sucedido
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"); // Credenciais inválidas
    }

    @PutMapping("/update-password/{id}")
    @Operation(summary = "Atualizar senha de usuário", description = "Método para atualizar senha de usuários")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newPassword = request.get("novaSenha");

        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Retorna 400 Bad Request
        }

        try {
            userService.updatePassword(id, newPassword); // Atualiza a senha
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 se o usuário não for encontrado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error
        }
    }


    @GetMapping("/{id}")
    @Operation(summary="Busca usuário por id", description ="Método para buscar usuário por id")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // Retorna os dados do usuário
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 se o usuário não for encontrado
    }
    @GetMapping
    @Operation(summary="Busca todos os usuários cadastrados", description ="Método para listar todos os usuários")
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
