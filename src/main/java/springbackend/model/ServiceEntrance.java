package springbackend.model;

/**
 * Simple JavaBean Object for service entrance
 */
public class ServiceEntrance {
    private String enteredPassword;

    private final String correctPassword = "deal";

    public String getCorrectPassword() {
        return this.correctPassword;
    }

    public String getEnteredPassword() {
        return this.enteredPassword;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }

    @Override
    public String toString() {
        return "ServiceEntrance{" +
                "enteredPassword='" + enteredPassword + '\'' +
                ", correctPassword='" + correctPassword + '\'' +
                '}';
    }
}