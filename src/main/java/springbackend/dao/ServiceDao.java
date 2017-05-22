package springbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springbackend.model.Service;

import java.util.Set;

/**
 * Dao for class {@link springbackend.model.Service}.
 */
public interface ServiceDao extends JpaRepository<Service, Long> {
    void delete(Service service);

    Service findById(Long id);

    Set<Service> findAllByUserId(Long userId);

    Set<Service> findAllByCategory(String category);
}
