package springbackend.service;

import springbackend.model.Service;
import springbackend.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service for users services
 */
public interface ServiceForService {
    /**
     * Deletes service.
     *
     * @param service - service which would be delete.
     */
    void delete(Service service);

    /**
     * Saves service for the first time.
     *
     * @param service - service which should be saved.
     */
    void save(Service service);

    /**
     * Saves service with replace.
     *
     * @param service - service which would be saved.
     */
    void saveAndFlush(Service service);

    /**
     * Finds service by owner id.
     *
     * @param userId - owner id
     * @return service.
     */
    Service findByUserId(Long userId);

    /**
     * Finds service by id.
     *
     * @param id - service id
     * @return found service.
     */
    Service findById(Long id);

    /**
     * Finds all services.
     *
     * @return found set of services.
     */
    Set<Service> findAll();

    /**
     * Finds all services by service category.
     *
     * @param category - category, direction or subject of service
     * @return found set of services.
     */
    Set<Service> findAllByCategory(String category);

    /**
     * Finds all services by owner id.
     *
     * @param userId - user id
     * @return found set of services.
     */
    Set<Service> findAllByUserId(Long userId);
}
