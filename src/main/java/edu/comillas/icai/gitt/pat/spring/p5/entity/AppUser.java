package edu.comillas.icai.gitt.pat.spring.p5.entity;

import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

// TODO#2
/**
 * TODO#2
 * Completa la entidad AppUser (cuya tabla en BD se llamará APP_USER)
 * para que tenga los siguientes campos obligatorios:
 * - id, que será la clave primaria numérica y autogenerada
 * - email, que debe tener valores únicos en toda la tabla
 * - password
 * - role, modelado con la clase Role ya existente en el proyecto
 * - name
 */

@Data
@Entity
@Table(name = "APP_USER")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    
    @Column(nullable = false)
    @NotBlank
    private String password;

    /*
    @OneToOne
    @NotNull
    private Role role;
    */
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;


    @Column(nullable = false)
    @NotBlank
    private String name;
}