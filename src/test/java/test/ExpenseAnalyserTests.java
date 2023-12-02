package test;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.touljaboy.app.ExpenseAnalyser;
import pl.touljaboy.model.Environment;

public class ExpenseAnalyserTests extends TestBase{
    //Tests in this class will verify whether the statistical functionality (f.e. that of ExpenseAnalyser class)
    //functions as expected.
    @TmsLink("ID - 01")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Testing whether the calculateAverageExpenses function works well based on pre-entered sample data")
    @Step("Running averageExpensesTest")
    @Test()
    public void averageExpensesTest() {
        //checking first if user is present
        Assert.assertTrue(Environment.users.contains(user));

        ExpenseAnalyser expenseAnalyser = new ExpenseAnalyser();
        double average1 = expenseAnalyser.calculateAverageExpenses(TestBase.sample1, TestBase.user.getUsername());
        double average2 = expenseAnalyser.calculateAverageExpenses(TestBase.sample2, TestBase.user.getUsername());

        double expected1 = (1380.77+777.11+999.15+333.22)/4;
        double expected2 = (200+215+420.1+606.27)/4;

        Assert.assertEquals(average1,expected1);
        Assert.assertEquals(average2,expected2);
    }
}
