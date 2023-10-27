package pl.touljaboy.io;

import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;
import java.util.Scanner;

public class DataReader {
    private Scanner scanner = new Scanner(System.in);

    public DataReader() {

    }


    public int readInt() {
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }
    public double readDouble() {
        try {
            return scanner.nextDouble();
        } finally {
            scanner.nextLine();
        }
    }
    public String readLine() {
        return scanner.nextLine();
    }

    public void closeRead() {
        scanner.close();
    }
}
