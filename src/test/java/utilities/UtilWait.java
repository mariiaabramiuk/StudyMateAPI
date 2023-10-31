package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class UtilWait {
    public static WebElement waitUntilElementIsVisible(WebDriver driver, int seconds, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitUntilElementIsClickable(WebDriver driver, int seconds, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static List<WebElement> waitUntilAllElementsAreVisible(WebDriver driver, int seconds, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    //    public static Object waitFluentlyUntilElementIsClickable(WebDriverWait driver, int totalWaitTIme, int pollingTime, By locator){
//        Wait<WebDriverWait> wait = new FluentWait<>(driver).
//                withTimeout(Duration.ofSeconds(totalWaitTIme)).
//                pollingEvery(Duration.ofSeconds(pollingTime)).
//                ignoring(NoSuchElementException.class);
//        return wait.until(ExpectedConditions.elementToBeClickable(locator));
//    }
//
    public static WebElement waitForElementWithFluentWait(WebDriver driver, final By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>(){
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
        return element;
    }

}

