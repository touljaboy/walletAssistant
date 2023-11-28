package pl.touljaboy.model;

import pl.touljaboy.io.CSVConvertible;
import pl.touljaboy.io.DataReader;

import java.util.ArrayList;
import java.util.Objects;


//Class used to store user info. In the future, I opt towards storing the information in MySql database.
public class User implements CSVConvertible{

    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toCSV() {
        return username+","+
                password+","+
                isAdmin;
    }

    @Override
    public String toString() {
        return "Nazwa użytkownika: " + username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, isAdmin);
    }
}
