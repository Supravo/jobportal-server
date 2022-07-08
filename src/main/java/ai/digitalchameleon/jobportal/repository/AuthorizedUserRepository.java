package ai.digitalchameleon.jobportal.repository;

import ai.digitalchameleon.jobportal.model.AuthorizedUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface AuthorizedUserRepository extends CrudRepository<AuthorizedUser, String> {

    List<AuthorizedUser> findAllByRole(String role);

    Optional<AuthorizedUser> findOneByIdAndPassword(String authorizedUserId, String password);

    Optional<AuthorizedUser> findByid(String authorizedUserId);

    Boolean existsByid(String authorizedUserId);

    @Modifying
    @Transactional
    @Query("UPDATE AuthorizedUser a SET a.password = ?2 WHERE a.id = ?1")
    int changePassword(@Param("authorizedUserId") String authorizedUserId, @Param("newPassword") String newPassword);

    @Modifying
    @Transactional
    void deleteByid(String authorizedUserId);
}
