package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;
import utils.ConfigReader;

public class LoginPage extends CommonMethods {


    //can use encapsulation/getters and setters here
    @FindBy(id="txtUsername")
    public WebElement userNameBox;

    @FindBy(id="txtPassword")
    public WebElement passwordBox;

    @FindBy(id="btnLogin")
    public WebElement loginButton;

    @FindBy(id="spanMessage")
    public WebElement errorMessage;

    public LoginPage(){
        PageFactory.initElements(driver, this);
    }

    //two ways to write login method. one using configReader, other with constructor
   // public void login(){
    //    sendText(usernameBox, ConfigReader.getPropertyValue("username"));
    //    sendText(passwordBox, ConfigReader.getPropertyValue("password"));

    //second way
    public void login(String username, String password){
        sendText(userNameBox, username);
        sendText(passwordBox, password);
        click(loginButton);
}

}
