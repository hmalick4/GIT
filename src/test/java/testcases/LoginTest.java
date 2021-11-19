package testcases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.CommonMethods;
import utils.ConfigReader;

public class LoginTest extends CommonMethods {

    @Test(groups="regression")
    public void adminLogin() {
        LoginPage login = new LoginPage();
        sendText(login.userNameBox, ConfigReader.getPropertyValue("username"));
        sendText(login.passwordBox, ConfigReader.getPropertyValue("password"));
        click(login.loginButton);

        //assertion to verify
        DashboardPage dashboardPage = new DashboardPage();
        Assert.assertTrue(dashboardPage.welcomeMessage.isDisplayed());
    }

        @DataProvider
        public Object[][] invalidData(){
            Object[][] data={
                    {"Ken", "123", "Invalid credentials"},
                    {"Barbie", "ooo", "Invalid"},
                    {"hfkh", "hfhgf", "wrong pw"},
                    {"", "srdsxdx", "username cant be empty"}
            };
            return data;

        }

        @Test(dataProvider = "invalidData")
    public void invalidLoginErrorMessageValidation(String username, String password, String message) {
        LoginPage loginPage=new LoginPage();
        loginPage.login(username, password);
        String actualError= loginPage.errorMessage.getText();
        Assert.assertEquals(actualError, message, "Error message does not match");
    }
}
