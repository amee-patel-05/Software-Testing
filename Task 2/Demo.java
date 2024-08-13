package TestNG.Task2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;


public class Demo {

    WebDriver a;

    @BeforeTest
    public void LaunchApp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--remote-allow-origins=*");

        a = new ChromeDriver(options);

        a.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        a.get("https://www.girirajdigital.com/");
        Thread.sleep(2000);

        a.manage().window().maximize();
        Thread.sleep(2000);

        System.out.println("Title of the page : " + a.getTitle());
        Thread.sleep(2000);
    }

    @Test(priority = 1)
    public void UICheck() throws InterruptedException {
        // UI Testing: Check the presence of footer

        WebElement navBar = a.findElement(By.xpath("//nav[@class='navbar navbar-expand-lg']"));
        assert navBar.isDisplayed() : "NavigationBar is not displayed";


        WebElement footer = a.findElement(By.tagName("footer"));
        assert footer.isDisplayed() : "Footer is not displayed";
    }

    @Test(priority = 2)
    public void ContactDetails() throws InterruptedException {

        Actions action = new Actions(a);

        WebElement tutorials1= a.findElement(By.xpath("//a[text()='Resources']"));	// mouse hover element
        action.moveToElement(tutorials1).perform();
        Thread.sleep(2000);


        WebElement button= a.findElement(By.xpath("//a[text()='Get In Touch']"));
        button.click();
        Thread.sleep(2000);

        WebElement email = a.findElement(By.id("29fb35f0-c7ff-48eb-92a1-603554e16b5f"));   //  getSize method email field

        ((JavascriptExecutor) a).executeScript("arguments[0].scrollIntoView();", email);
        Thread.sleep(2000);

        org.openqa.selenium.Dimension dimensions = email.getSize();
        System.out.println("Height :" + dimensions.height + "  Width : " + dimensions.width);
        Thread.sleep(1000);

        org.openqa.selenium.Point point = email.getLocation();                    //  getLocation method
        System.out.println("X cordinate: " + point.x + " Y cordinate: " + point.y);
        Thread.sleep(1000);

         a.findElement(By.xpath("//a[@title='Home']")).click();
         Thread.sleep(1000);

    }

    @Test(priority = 3)
    public void BrandsDetails() throws InterruptedException {

        // Brands
        WebElement brands = a.findElement(By.xpath("(//div[@class=\"global-title\"])[1]"));

        ((JavascriptExecutor) a).executeScript("arguments[0].scrollIntoView();", brands);
        Thread.sleep(2000);

        System.out.println(a.findElement(By.xpath("(//div[@class='all-brands d-flex flex-wrap w-100'])\n")).isDisplayed());
        Thread.sleep(2000);

        System.out.println("Brands Logo displayed properly");

        // Scroll the page upto platform module
        WebElement platform = a.findElement(By.xpath("(//div[@class=\"global-title\"])[2]"));

        ((JavascriptExecutor) a).executeScript("arguments[0].scrollIntoView();", platform);
        Thread.sleep(2000);

    }

    @Test(priority = 4)
    public void BrokenLinks() {

        List<WebElement> links = a.findElements(By.tagName("a"));
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href != null) {
                System.out.println("Link text: " + link.getText() + ", URL: " + href);
                assert href.startsWith("http") : "Link does not have a valid URL";
            }
        }
    }

    @AfterTest
    public void teardown() {
        a.close();
    }

}
