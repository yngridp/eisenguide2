package com.eisenguide2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eisenguide2.dto.TaskRequest;
import com.eisenguide2.model.Task;
import com.eisenguide2.model.TaskCategory;
import com.eisenguide2.model.User;
import com.eisenguide2.repository.TaskRepository;
import com.eisenguide2.service.TaskService;
import com.eisenguide2.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
@Tag(name ="Tarefas", description = "Controlador para salvar e editar dados das tarefas" )
public class TaskController {

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
	private TaskRepository taskRepository;

    @GetMapping
    @Operation(summary="Lista todas as tarefas criadas", description ="Método para listar todas as tarefas")
    public List<Task> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary="Procurar tarefa por id", description ="Método para mostrar tarefa por id")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/objective/{objective}")
    @Operation(summary="Listar tarefas através do atributo 'Objective' ", description ="Método para mostrar tarefas pelo mesmo objetivo")
    public List<Task> getTasksByObjective(@PathVariable String objective) {
        return taskService.getTasksByObjective(objective);
    }

    @PostMapping
    @Operation(summary="Criar tarefa", description ="Método para criar uma tarefa")
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody TaskRequest taskRequest) {
        // Verifica se o ID do usuário foi enviado na requisição
        if (taskRequest.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("message", "O ID do usuário é obrigatório."));
        }

        // Recupera o usuário com base no ID
        Optional<User> userOptional = userService.findById(taskRequest.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("message", "Usuário não encontrado."));
        }
        
        User user = userOptional.get();

        // Cria uma nova tarefa e associa o usuário
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());

        // Verifica se a categoria é válida antes de definir
        TaskCategory category;
        try {
            category = TaskCategory.valueOf(taskRequest.getCategory()); // Converte String para Enum
            task.setCategory(category); // Define a categoria da tarefa
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Categoria inválida."));
        }

        task.setUser(user);

        // Salva a tarefa
        Task createdTask = taskService.save(task);
        
        // Prepara a resposta com os dados da tarefa e do usuário
        Map<String, Object> response = new HashMap<>();
        response.put("task", createdTask);
        response.put("user", user);
        response.put("actionMessage", createdTask.getActionMessage());
        
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary="Atualizar uma tarefa por id", description ="Método para atualizar tarefa por id")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = taskService.findById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setObjective(updatedTask.getObjective());
        task.setCategory(updatedTask.getCategory());
        task.setCompleted(updatedTask.isCompleted());

        Task savedTask = taskService.save(task);
        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Deletar uma tarefa por id", description ="Método para deletar tarefa por id")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
