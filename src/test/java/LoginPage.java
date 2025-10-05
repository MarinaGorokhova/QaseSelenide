import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement emailInput = $x("//*[@placeholder='Password']");
    private final SelenideElement passwordInput = $x("//*[@placeholder='Password']");
    private final SelenideElement submit = $x("//*[@type='submit']");

    public void openPage() {
        open("login");
    }

    public void login() {
        open("login");
        emailInput.setValue("t123@yandex.ru").pressEnter();
        passwordInput.sendKeys("****");
        submit.submit();
    }
}
