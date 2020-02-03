package com.educandoweb.course.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.domain.Usuario;

/**
 * Spring Data JPA repository for the {@link Usuario} entity.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findOneByActivationKey(String activationKey);

    List<Usuario> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<Usuario> findOneByResetKey(String resetKey);

    @EntityGraph(attributePaths = "authorities")
    Optional<Usuario> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<Usuario> findOneWithAuthoritiesByLogin(String login);
    
    Optional<Usuario> findOneByLoginIgnoreCase(String login);

    //@EntityGraph(attributePaths = "authorities")
    //Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<Usuario> findAllByLoginNot(Pageable pageable, String login);
    
    @Query("select c.user from Cliente c where c.email = :email")
	Optional<Usuario> findOneByEmailIgnoreCase(@Param("email") String email);
	
	@Query("select c.user from Cliente c where c.user.login = :login")
	Optional<Usuario> findOneByLogin(@Param("login") String login);
}
