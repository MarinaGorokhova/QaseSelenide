import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void projectLogin() {
        loginPage.openPage();
        loginPage.login();
        projectsPage.waitPageLoaded();
    }
}
