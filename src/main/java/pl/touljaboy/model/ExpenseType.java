package pl.touljaboy.model;

import pl.touljaboy.exception.NoSuchOptionException;
import pl.touljaboy.io.CSVConvertible;
import pl.touljaboy.io.ConsolePrinter;

import java.util.ArrayList;
import java.util.Objects;


public class ExpenseType implements CSVConvertible {
//    FOOD("Jedzenie", 0), LEISURE("Wypoczynek",1), TAXES("Podatki",2),
//    CAR("Samochód",3);
    public static ArrayList<ExpenseType> expenseTypes = new ArrayList<>();
    private final String description;
    //I do not think that this 'id' is necessary.
    // I can just use indexes from the ArrayList, but maybe leave it be for now, because there might be more
    //stuff I want to add to a single ExpenseType object, like String name
    private final int id;
    public ExpenseType(String description, int id) {
        this.description = description;
        this.id = id;
    }

    public static ArrayList<ExpenseType> getExpenseTypes() {
        return expenseTypes;
    }
    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public static void addExpenseType(ExpenseType expenseType) {
        expenseTypes.add(expenseType);
    }

    public static void removeExpense(int id) {
        //Well, I am pretty tired and I thought I will check if an element is present in arraylist this way
        //probably there is a very easy way to do this, but I need to get off the computer ASAP
            boolean isIdPresentInArrayList =
                    expenseTypes.stream().filter(expenseType -> expenseType.getId()==id).count()==1;
            if(isIdPresentInArrayList)
                expenseTypes.removeIf(expenseType -> expenseType.getId()==id);
            else ConsolePrinter.printError("Nieznaleziono elementu o podanym ID");
        }




    @Override
    public String toString() {
        return "OPIS KATEGORII: " +description +", ID: " +id;
    }

    public static ExpenseType createFromInt(int choice) throws NoSuchOptionException {
        try {
            return expenseTypes.get(choice);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchOptionException("Brak wydatku o ID: " + choice);
        }
    }

    @Override
    public String toCSV() {
        return id+","+
                description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseType that = (ExpenseType) o;
        return id == that.id && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id);
    }
}
