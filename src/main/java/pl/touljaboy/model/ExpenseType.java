package pl.touljaboy.model;

import pl.touljaboy.exception.NoSuchOptionException;
import pl.touljaboy.io.CSVConvertible;

import java.util.Objects;


/**
 * @param id there might be more stuff I want to add to a single ExpenseType object, like String name, so leave id for now
 */
public record ExpenseType(String description, int id) implements CSVConvertible {

    @Override
    public String description() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static ExpenseType createFromInt(int choice) throws NoSuchOptionException {
        try {
            return Environment.expenseTypes.get(choice);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchOptionException("Brak wydatku o ID: " + choice);
        }
    }

    @Override
    public String toCSV() {
        return id + "," +
                description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseType that = (ExpenseType) o;
        return id == that.id && Objects.equals(description, that.description);
    }

}
