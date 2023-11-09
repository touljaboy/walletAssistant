package pl.touljaboy.model;

import pl.touljaboy.io.CSVConvertible;
import pl.touljaboy.io.ConsolePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

//TODO create functionality regarding logging in and user restrictions (admin/not admin)
//Class used to store user info. In the future, I opt towards storing the information in MySql database.
public class User implements CSVConvertible{
    public static List<User> users = new ArrayList<>();
    String username;
    String password;
    boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public static void addUser(User user) {
        if(users.contains(user))
            ConsolePrinter.printLine("Użytkownik o podanej nazwie już istnieje! ");
        else users.add(user);
    }

    @Override
    public String toCSV() {
        return username+","+
                password+","+
                isAdmin+",";
    }

    @Override
    public String toString() {
        return "Nazwa użytkownika: " + username;
    }
}
