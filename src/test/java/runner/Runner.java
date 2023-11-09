package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin= {"html:target/cucumber.html", "json:target/report.json"},
        features = "/Users/hassonzy/Desktop/CodeWise/FrameWorks/StudyMateAPI/src/test/resources/features/StudentApiTest.feature",
        glue = "steps",
        tags = "@GroupsTest or @LoginTest or @TeachersTest or @StudentTest",
        dryRun = false
)
public class Runner {

}
