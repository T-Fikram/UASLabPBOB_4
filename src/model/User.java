package model;

public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username tidak boleh kosong");
        }
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong");
        }
        if (password.length() < 4) {
            throw new IllegalArgumentException("Password minimal 4 karakter");
        }
        this.password = password;
    }

    public boolean login(String inputUser, String inputPass) {
        if (inputUser == null || inputPass == null) {
            throw new IllegalArgumentException("Input login tidak boleh null");
        }
        return username.equals(inputUser.trim()) && password.equals(inputPass);
    }

    @Override
    public String toString() {
        return "User: " + username + " (password disembunyikan)";
    }
}