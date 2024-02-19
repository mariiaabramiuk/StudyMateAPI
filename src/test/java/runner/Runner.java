package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin= {"html:target/cucumber.html", "json:target/report.json", "pretty"},
        features = "/Users/mariiazatylna/Desktop/projects/StudyMateApiProject/src/test/resources/features",
        glue = "steps",
        tags = "@GroupsTest or @LoginTest or @TeachersTest"
)
public class Runner {

}
