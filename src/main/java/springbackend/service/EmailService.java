package springbackend.service;

import java.util.Map;

/**
 * Service for send email message
 */
public interface EmailService {
    /**
     * Sends email message.
     *
     * @param templateName - template name with message
     * @param model        - information for message
     * @return logical value depending on the message status (sent/ not sent).
     */
    boolean sendEmail(final String templateName, final Map<String, Object> model);

    /**
     * Generates string for url to confirm account.
     *
     * @param length - string size
     * @return logical value depending on the message status (sent/ not sent).
     */
    String generateString(int length);
}
