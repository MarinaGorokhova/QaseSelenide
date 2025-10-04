import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class BaseTest {
    LoginPage loginPage;
    ProjectsPage projectsPage;

    @BeforeMethod
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 10000;
        Configuration.baseUrl = "https://app.qase.io/";
        Configuration.browserSize = "1920*1080";
        Configuration.holdBrowserOpen = true;
        loginPage = new LoginPage();
        projectsPage = new ProjectsPage();
    }

    @AfterMethod
    public void closeWind() {
        clearBrowserCache();
        Selenide.closeWebDriver();
    }
}
