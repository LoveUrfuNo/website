package springbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springbackend.model.Role;

/**
 * Dao for class {@link springbackend.model.Role}.
 */
public interface RoleDao extends JpaRepository<Role, Long> {
    Role findById(Long id);
}
