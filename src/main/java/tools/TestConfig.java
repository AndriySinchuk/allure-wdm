package tools;
import io.github.bonigarcia.wdm.OperativeSystem;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;


    public class TestConfig {

        private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

        @Parameters({"browser", "os", "platform"})
        @BeforeTest
        public void setUp(@Optional("chrome") String browser, @Optional("windows") String os, @Optional("desktop") String platform,
                          final ITestContext context) {

            if (browser.equals("chrome")) {
                ChromeOptions options = new ChromeOptions();
//          More info on: https://developers.google.com/web/updates/2017/04/headless-chrome
//                options.addArguments("--headless");
                options.addArguments("window-size=1366,768");
//                options.addArguments("screenshot");
                WebDriverManager.chromedriver().version("2.35").operativeSystem(getOS(os)).setup();
                driver.set(new ChromeDriver(options));
                System.out.println("Starting "+ driver.get().getClass().getSimpleName()+"..."
                        +WebDriverManager.chromedriver().getDownloadedVersion());

            }

            if (browser.equals("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                // More info on: https://developers.google.com/web/updates/2017/04/headless-chrome
                options.addArguments("-headless");
                WebDriverManager.firefoxdriver().operativeSystem(getOS(os)).setup();
                driver.set(new FirefoxDriver(options));
                System.out.println("Starting "+ driver.get().getClass().getSimpleName()+"..."
                        +WebDriverManager.firefoxdriver().getDownloadedVersion());
            }

            driver.get().manage().window().setSize(getDimension(platform));
            driver.get().manage().timeouts().pageLoadTimeout(35, TimeUnit.SECONDS);
            driver.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }

        private OperativeSystem getOS(String os) {
            switch (os) {
                case "linux":
                    return OperativeSystem.LINUX;
                case "windows":
                    return OperativeSystem.WIN;
                default:
                    return OperativeSystem.LINUX;
            }
        }

        private Dimension getDimension(String dimension){
            switch (dimension) {
                case "desktop":
                    return new Dimension(1366, 768);
                case "mobile":
                    return new Dimension(320, 568);
                default:
                    return new Dimension(1366, 768);
            }
        }

        @AfterTest(alwaysRun = true)
        public void BrowserQuit() {
            System.out.println("Closing "+driver.get().getClass().getSimpleName()+"...");
            if(driver!=null)
                driver.get().quit();
        }

        public static WebDriver getDriver(){
            return driver.get();
        }
}
