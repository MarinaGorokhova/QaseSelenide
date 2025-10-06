package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProjectsPage {
    private final SelenideElement title = $x("//h1[text()='Projects']");

    public void waitPageLoaded() {
        title.should(Condition.exist).shouldBe(visible);
    }
}
