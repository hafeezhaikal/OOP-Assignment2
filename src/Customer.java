/**
 * Customer: ADDITIONAL CLASS 1. Holds the buyer's details.
 * All attributes are private and validated in the setters.
 */
public class Customer {
    private String name;
    private String email;
    private String phone;

    public Customer(String name, String email, String phone) {
        setName(name);
        setEmail(email);
        setPhone(phone);
    }

    public String getName()  { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setEmail(String email) {
        // simple validity check: must contain "@" and a "."
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        this.email = email.trim();
    }

    public void setPhone(String phone) {
        // digits only (optionally starting with +), length 9-13
        if (phone == null || !phone.matches("\\+?[0-9]{9,13}")) {
            throw new IllegalArgumentException("Invalid phone number.");
        }
        this.phone = phone;
    }

    @Override
    public String toString() { return name + " (" + email + ")"; }
}
