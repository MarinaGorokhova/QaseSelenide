package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement emailInput = $x("//*[@placeholder='Work email']");
    private final SelenideElement passwordInput = $x("//*[@placeholder='Password']");
    private final SelenideElement submit = $x("//*[@type='submit']");

    private String user;
    private String password;

    public LoginPage(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public LoginPage openPage() {
        open("/login");
        return this;
    }

    public LoginPage login() {
        open("/login");
        emailInput.setValue(user).pressEnter();
        passwordInput.sendKeys(password);
        submit.submit();
        return this;
    }
}
