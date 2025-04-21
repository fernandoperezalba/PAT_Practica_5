package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.util.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

// TODO#6
/**
 * TODO#6
 * Completa los métodos del servicio para que cumplan con el contrato
 * especificado en el interface UserServiceInterface, utilizando
 * los repositorios y entidades creados anteriormente
 */

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private Hashing hashing;

    @Override
    public Token login(String email, String password) {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null || !hashing.compare(appUser.getPassword(), password)) return null;

        Token token = tokenRepository.findByAppUser(appUser);
        if (token != null) return token;

        token = new Token();
        token.setAppUser(appUser);
        tokenRepository.save(token);
        return null;
    }
    
    @Override
    public AppUser authentication(String tokenId) {
        Token token = tokenRepository.findById(tokenId).orElse(null);
        if (token != null) {
            return token.getAppUser();
        } else {
            return null;
        }
    }

    @Override
    public ProfileResponse profile(AppUser appUser) {
        return new ProfileResponse(appUser.getName(), appUser.getEmail(), appUser.getRole());
    }

    @Override
    public ProfileResponse profile(AppUser appUser, ProfileRequest profile) {
        // Actualizar los datos del perfil del usuario
        appUser.setName(profile.name());
        appUser.setRole(profile.role());
        appUser.setPassword(profile.password());

        // Guardar los cambios en el repositorio
        appUserRepository.save(appUser);

        // Crear y devolver el objeto ProfileResponse actualizado
        return new ProfileResponse(
            appUser.getName(),
            appUser.getEmail(),
            appUser.getRole()
        );
    }

    @Override
    public ProfileResponse profile(RegisterRequest register) {
        String hashedPassword = hashing.hash(register.password());

        // Crear un nuevo usuario con los datos del registro
        AppUser newUser = new AppUser();
        newUser.setName(register.name());
        newUser.setEmail(register.email());
        newUser.setPassword(hashedPassword);
        newUser.setRole(register.role());

        // Guardar el nuevo usuario en el repositorio
        appUserRepository.save(newUser);

        // Crear y devolver el objeto ProfileResponse para el nuevo usuario
        return new ProfileResponse(
            newUser.getName(),
            newUser.getEmail(),
            newUser.getRole()
        );
    }

    @Override
    public void logout(String tokenId) {
        // Eliminar la sesión correspondiente al tokenId del repositorio de tokens
        tokenRepository.deleteById(tokenId);
    }

    @Override
    public void delete(AppUser appUser) {
        // Borrar el usuario y su sesión asociada (si existe) del repositorio de usuarios y tokens
        appUserRepository.delete(appUser);
    }

}
