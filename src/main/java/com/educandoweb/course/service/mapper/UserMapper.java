package com.educandoweb.course.service.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.educandoweb.course.domain.Autorizacao;
import com.educandoweb.course.domain.Usuario;
import com.educandoweb.course.service.dto.UserDTO;

/**
 * Mapper for the entity {@link Usuario} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Component
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<Usuario> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(Usuario user) {
        return new UserDTO(user);
    }

    public List<Usuario> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public Usuario userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            Usuario user = new Usuario();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            //user.setEmail(userDTO.getEmail());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Autorizacao> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }


    private Set<Autorizacao> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Autorizacao> authorities = new HashSet<>();

        if(authoritiesAsString != null){
            authorities = authoritiesAsString.stream().map(string -> {
                Autorizacao auth = new Autorizacao();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }

    public Usuario userFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario user = new Usuario();
        user.setId(id);
        return user;
    }
}
