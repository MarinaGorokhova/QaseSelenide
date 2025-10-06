package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.ProjectsPage;
import utils.PropertyReader;

import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class BaseTest {
    LoginPage loginPage;
    ProjectsPage projectsPage;
    protected String user;
    protected  String password;
    protected String baseUrl;

    @BeforeMethod
    public void setUp() {
        baseUrl = PropertyReader.getProperty("qaseselenide.url");
        user = PropertyReader.getProperty("qaseselenide.user");
        password = PropertyReader.getProperty("qaseselenide.password");
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 10000;
        Configuration.baseUrl = baseUrl;
        Configuration.browserSize = "1920*1080";
        Configuration.holdBrowserOpen = true;
        loginPage = new LoginPage(user, password);
        projectsPage = new ProjectsPage();
    }

    @AfterMethod
    public void closeWind() {
        clearBrowserCache();
        Selenide.closeWebDriver();
    }
}
