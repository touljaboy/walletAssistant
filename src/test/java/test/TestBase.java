package test;

import io.qameta.allure.Step;
import org.junit.Test;
import org.testng.Assert;
import org.testng.annotations.*;
import pl.touljaboy.app.ExpenseAnalyser;
import pl.touljaboy.app.ExpenseAppManager;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;
import pl.touljaboy.model.User;

import java.time.LocalDate;

public class TestBase {
    //Base class for other tests, functions such as adding the test user, adding the sample data etc.
    //Test Base will contain these static variables, until I figure out a better way
    public static final User user = new User("test","test",true);
    public static final ExpenseType sample1 = new ExpenseType("ENERGY",99);
    public static final ExpenseType sample2 = new ExpenseType("PAVEMENT", 98);

    //TODO make allure report work properly
    @Step("Adding sample user and sample data")
    @BeforeMethod()
    public void beforeTest(){
        //Add test user
        Environment.users.add(user);
        //Add sample ExpenseTypes to expenseTypes arraylist
        Environment.expenseTypes.add(sample1);
        Environment.expenseTypes.add(sample2);

        //Add test user to listmultimap expenses together with test values. Using current date as example
        Environment.expenses.put(user.getUsername(), new Expense(1380.77, sample1, LocalDate.now()));
        Environment.expenses.put(user.getUsername(), new Expense(777.11, sample1, LocalDate.now()));

        Environment.expenses.put(user.getUsername(), new Expense(200.0, sample2, LocalDate.now()));
        Environment.expenses.put(user.getUsername(), new Expense(215.0, sample2, LocalDate.now()));

        //Add random dates also
        Environment.expenses.put(user.getUsername(),
                new Expense(999.15, sample1, LocalDate.of(2021,10,2)));
        Environment.expenses.put(user.getUsername(),
                new Expense(333.22, sample1, LocalDate.of(2020,1,3)));

        Environment.expenses.put(user.getUsername(),
                new Expense(420.1, sample2, LocalDate.of(2022,9,13)));
        Environment.expenses.put(user.getUsername(),
                new Expense(606.27, sample2, LocalDate.of(2023,4,12)));

        //average1 for all dates will equal (1380.77+777.11+999.15+333.22)/4
        //average2 for all dates will equal (200+215+420.1+606.27)/4

    }


}
