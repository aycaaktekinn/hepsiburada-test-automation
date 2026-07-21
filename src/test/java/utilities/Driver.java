package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Driver {

    // Proje genelinde kullanılacak tek WebDriver nesnesi.
    private static WebDriver driver;

    // Bu sınıftan nesne oluşturulmasını engeller.
    private Driver() {
    }

    public static WebDriver getDriver() {

        // Driver henüz oluşturulmadıysa Chrome başlatılır.
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        return driver;
    }

    public static void closeDriver() {

        // Tarayıcı açıksa tamamen kapatılır.
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
