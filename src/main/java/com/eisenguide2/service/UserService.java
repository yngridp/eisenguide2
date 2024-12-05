package com.eisenguide2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eisenguide2.model.User;
import com.eisenguide2.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Método para buscar usuário por username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Método para registrar novo usuário
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Criptografa a senha
        return userRepository.save(user);
    }

    // Método para login de usuário
    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    // Método para atualizar a senha
    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Criptografa a nova senha antes de salvar
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // Método para verificar se a senha informada corresponde à senha criptografada
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    // Método para buscar usuário por ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Método para buscar todos os usuários
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Método para buscar usuário por e-mail
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Método para verificar se a nova senha é diferente da senha atual
    public boolean isNewPasswordSameAsOld(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a nova senha é igual à senha já registrada
        return passwordEncoder.matches(newPassword, user.getPassword());
    }
}
