package test;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.touljaboy.app.ExpenseAnalyser;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExpenseAnalyserTests extends TestBase{
    ExpenseAnalyser expenseAnalyser = new ExpenseAnalyser();
    //StartDate and EndDate for datePeriod test
    LocalDate startDate = LocalDate.of(2022,1,1);
    LocalDate endDate = LocalDate.of(2023,12,30);
    //Tests in this class will verify whether the statistical functionality (f.e. that of ExpenseAnalyser class)
    //functions as expected.
    @TmsLink("ID - 01")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Testing whether the calculateAverageExpenses function works well based on pre-entered sample data")
    @Step("Running averageExpensesTest")
    @Test()
    public void averageExpensesTest() {
        double average1 = expenseAnalyser.calculateAverageExpenses(Environment.expenses.get(user.getUsername())
                ,sample1, user.getUsername());
        double average2 = expenseAnalyser.calculateAverageExpenses(Environment.expenses.get(user.getUsername())
                ,sample2, user.getUsername());

        double expected1 = (1380.77+777.11+999.15+333.22)/4;
        double expected2 = (200+215+420.1+606.27)/4;

        Assert.assertEquals(average1,expected1);
        Assert.assertEquals(average2,expected2);
    }

    @TmsLink("ID - 01")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Testing whether the average expenses for a date period for sample data is calculated properly")
    @Step("Running datePeriodAverageExpensesTest")
    @Test()
    public void datePeriodAverageExpensesTest() {
        ArrayList<Expense> datePeriodExpenses = expenseAnalyser
                .getDatePeriodExpenses(startDate, endDate, user.getUsername());

        //This has to be hardcoded for now
        double expected1 = (1380.77+777.11)/2;
        double expected2 = (200+215+420.1+606.27)/4;

        double average1 = expenseAnalyser.calculateAverageExpenses(datePeriodExpenses, sample1,user.getUsername());
        double average2 = expenseAnalyser.calculateAverageExpenses(datePeriodExpenses, sample2,user.getUsername());

        Assert.assertEquals(average1,expected1);
        Assert.assertEquals(average2,expected2);
    }

}
