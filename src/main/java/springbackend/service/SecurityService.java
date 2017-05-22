package springbackend.service;

/**
 * Service for Security.
 */
public interface SecurityService {
    /**
     * @return null.
     */
    String findLoggedInUsername();

    /**
     * Automatic enters after registration.
     *
     * @param username - user's email-address
     * @param password - coded user's password
     */
    void autoLogin(String username, String password);
}
