package com.eisenguide2.model;

import java.time.LocalDate;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100)
	@NotBlank(message = "O Atributo título é obrigatório!")
	@Size(min = 5, max = 100, message="O Atributo título deve conter no mínimo 5 e no máximo 100 caracteres.")
    private String title;
    
    @Column(length = 1000)
	@NotBlank(message = "O Atributo descrição é obrigatório!")
	@Size(min = 10, max = 1000, message="O Atributo texto deve conter no mínimo 10 e no máximo 1000 caracteres.")
    private String description;
    
    @UpdateTimestamp
    private LocalDate dueDate;
    
    private String objective;
    
    @ManyToOne
    @JsonIgnoreProperties("task")
    private User user; 
    
    @Enumerated(EnumType.STRING)
    private TaskCategory category; // Supondo que você tenha uma enumeração ou classe TaskCategory
    
    private boolean completed;
	
	 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}


	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TaskCategory getCategory() {
		return category;
	}

	public void setCategory(TaskCategory category) {
		this.category = category;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getActionMessage() {
	        switch (category) {
	            case IMPORTANTE_E_URGENTE:
	                return "Faça agora";
	            case IMPORTANTE_MAS_NAO_URGENTE:
	                return "Programe-se";
	            case URGENTE_MAS_NAO_IMPORTANTE:
	                return "Delegue";
	            case NAO_URGENTE_NAO_IMPORTANTE:
	                return "Faça no tempo livre ou elimine";
	            default:
	                return "Categoria inválida";
	        }
	    }
}