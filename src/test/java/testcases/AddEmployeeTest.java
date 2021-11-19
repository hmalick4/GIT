package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.EmployeeListPage;
import pages.LoginPage;
import utils.CommonMethods;
import utils.ConfigReader;
import utils.Constants;
import utils.ExcelReading;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeTest extends CommonMethods {

    @Test(groups="smoke")
    public void addEmployee(){
        //login function
        LoginPage loginpage=new LoginPage();
        loginpage.login(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

        DashboardPage dashboardPage=new DashboardPage();
        click(dashboardPage.pimOption);
        click(dashboardPage.addEmployeeButton);

        //add employee page
        AddEmployeePage addEmployeePage=new AddEmployeePage();
        sendText(addEmployeePage.firstName, "789");
        sendText(addEmployeePage.middleName, "456");
        sendText(addEmployeePage.lastName, "123");
        click(addEmployeePage.saveButton);
    }

    @Test
    public void addMultipleEmployees(){
        //login
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

        //navigate to add employee page
        DashboardPage dashBoardPage = new DashboardPage();
        AddEmployeePage addEmployeePage =new AddEmployeePage();
        EmployeeListPage emplist = new EmployeeListPage();
        SoftAssert softAssert = new SoftAssert();

        List<Map<String, String>> newEmployees = ExcelReading.excelIntoListMap(Constants.TESTDATA_FILEPATH, "EmployeeData");
        Iterator<Map<String, String>> it = newEmployees.iterator();
        while (it.hasNext()){
            click(dashBoardPage.pimOption);
            click(dashBoardPage.addEmployeeButton);
            Map<String, String> mapNewEmployee = it.next();
            sendText(addEmployeePage.firstName, mapNewEmployee.get("FirstName"));
            sendText(addEmployeePage.middleName, mapNewEmployee.get("MiddleName"));
            sendText(addEmployeePage.lastName, mapNewEmployee.get("LastName"));

            //capturing employee id from system
            String employeeIdValue = addEmployeePage.employeeId.getAttribute("value");
            sendText(addEmployeePage.photograph, mapNewEmployee.get("Photograph"));
            //select checkbox
            if(!addEmployeePage.createLoginCheckBox.isSelected()){
                click(addEmployeePage.createLoginCheckBox);
            }

            //provide credentials for user
            sendText(addEmployeePage.createUsername, mapNewEmployee.get("Username"));
            sendText(addEmployeePage.createPassword, mapNewEmployee.get("Password"));
            sendText(addEmployeePage.rePassword, mapNewEmployee.get("Password"));
            click(addEmployeePage.saveButton);

            //navigate to employee list page
            click(dashBoardPage.pimOption);
            click(dashBoardPage.employeeListOption);

            sendText(emplist.idEmployee, employeeIdValue);
            click(emplist.searchButton);

            List<WebElement> rowData = driver.findElements(By.xpath("//*[@id='resultTable']/tbody/tr"));

            for(int i=0; i<rowData.size(); i++){
                System.out.println("I am inside the loop to get values for the newly generated employee");
                String rowText = rowData.get(i).getText();
                System.out.println(rowText);
                String expectedData = employeeIdValue + " " +mapNewEmployee.get("FirstName") + " " + mapNewEmployee.get("MiddleName") + " " + mapNewEmployee.get("LastName");
                softAssert.assertEquals(rowText, expectedData);
            }
        }

        softAssert.assertAll();
    }
}