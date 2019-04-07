package com.nsu.to_letdhaka.Domain;

import java.io.Serializable;
import java.util.Objects;

public class Profile implements Serializable {
    private String username;
    private String email;
    private String passwordHash;

    public Profile() {
    }

    public Profile(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile)) return false;
        Profile profile = (Profile) o;
        return Objects.equals(getUsername(), profile.getUsername()) &&
                Objects.equals(getEmail(), profile.getEmail()) &&
                Objects.equals(getPasswordHash(), profile.getPasswordHash());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEmail(), getPasswordHash());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
