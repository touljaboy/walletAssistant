package pl.touljaboy.model;

import pl.touljaboy.exception.NoSuchOptionException;


//NEED TO TWEAK THIS CLASS A BIT, so it is saved to CSV file, as app user can add new expenseTypes himself.
public enum ExpenseType {
    FOOD("Jedzenie", 0), LEISURE("Wypoczynek",1), TAXES("Podatki",2),
    CAR("Samochód",3);

    private String description;
    private int id;
    ExpenseType(String description, int id) {
        this.description = description;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OPIS KATEGORII: " +description +", ID: " +id;
    }

    public static ExpenseType createFromInt(int choice) throws NoSuchOptionException {
        try {
            return ExpenseType.values()[choice];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchOptionException("Brak wydatku o ID: " + choice);
        }
    }
}
