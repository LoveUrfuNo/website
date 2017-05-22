package springbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springbackend.model.UserFile;

import java.util.Set;

/**
 * Dao for class {@link springbackend.model.UserFile}.
 */
public interface UserFileDao extends JpaRepository<UserFile, Long> {
    void delete(UserFile file);

    Set<UserFile> findAllByUserId(Long id);

    Set<UserFile> findAllByServiceName(String name);
}
