package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

// TODO#4
/**
 * TODO#4
 * Crea el repositorio para la entidad AppUser de modo que,
 * además de las operaciones CRUD, se pueda consultar el AppUser asociado
 * a un email dado
 */

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    // Método para buscar un AppUser por su email
    AppUser findByEmail(String email);
}