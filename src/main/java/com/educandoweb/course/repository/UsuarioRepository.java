package com.educandoweb.course.repository;

import com.educandoweb.course.domain.Usuario;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.Instant;

/**
 * Spring Data JPA repository for the {@link Usuario} entity.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findOneByActivationKey(String activationKey);

    List<Usuario> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<Usuario> findOneByResetKey(String resetKey);

    Optional<Usuario> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<Usuario> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<Usuario> findOneWithAuthoritiesByLogin(String login);

    //@EntityGraph(attributePaths = "authorities")
    //Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<Usuario> findAllByLoginNot(Pageable pageable, String login);
}
