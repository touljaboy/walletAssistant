package pl.touljaboy.model;

import pl.touljaboy.io.CSVConvertible;


//TODO create functionality regarding logging in and user restrictions (admin/not admin)
//Class used to store user info. In the future, I opt towards storing the information in MySql database.
public class User implements CSVConvertible{
    String username;
    String password;
    boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
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
