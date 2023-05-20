package com.epam.training.ekaterina_sycheva.hardcore.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverSingleton {

    private static WebDriver driver;
    private static String browser;
    protected static final Logger logger = LogManager.getLogger(DriverSingleton.class);
    public static WebDriver getDriver()  {
        browser = System.getProperty("browser");
        logger.info("Entered browser is: {}", browser);
        logger.info("Initializing driver...");
        if (driver == null)  {
            switch (browser) {
                case "edge": {
                    WebDriverManager.edgedriver().setup();
                    logger.info("Driver setup");
                    EdgeOptions options = new EdgeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    driver = new EdgeDriver(options);
                    logger.info("Driver initialized: {}", driver.toString());
                    break;
                }
                case "firefox": {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions options = new FirefoxOptions();
                    options.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
                    driver = new FirefoxDriver(options);
                    break;
                }
                default: {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver(options);
                    break;
                }
            }
            driver.manage().window().maximize();
        }
        logger.debug("Driver initialized: {}", driver.toString());
        return driver;
    }

    public static String getTab(){
        return driver.getWindowHandle();
    }

    public static void openNewTab() {
        driver.switchTo().newWindow(WindowType.TAB);
        logger.debug("New tab opened");
    }

    public static void swichToTab(String tabName) {
        driver.switchTo().window(tabName);
        logger.debug("Switched to tab: {}", tabName);
    }

    public static void closeDriver() {
        logger.debug("Closing driver...");
        driver.quit();
        driver = null;
        logger.debug("Driver closed");
    }
}