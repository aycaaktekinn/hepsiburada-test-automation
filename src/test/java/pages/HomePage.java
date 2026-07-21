package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class HomePage {

    // Proje genelinde kullanılan ortak tarayıcı nesnesi.
    private final WebDriver driver;

    // Sayfa geçişlerini ve elementleri beklemek için kullanılır.
    private final WebDriverWait wait;

    public HomePage() {

        // Driver sınıfındaki mevcut tarayıcı nesnesini alır.
        driver = Driver.getDriver();

        // Maksimum 15 saniyelik bekleme süresi tanımlar.
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    // Hepsiburada ana sayfasını açar.
    public void openHomePage() {

        driver.get("https://www.hepsiburada.com");
    }

    /*
     * Girilen ürün adına göre Hepsiburada arama sonuçları sayfasını açar.
     *
     * Şimdilik arama kutusunun dinamik yapısından kaynaklanan
     * stale element problemini aşmak için arama URL'si oluşturuyoruz.
     */
    public void searchProduct(String productName) {

        try {
            String encodedProductName = URLEncoder.encode(
                    productName,
                    StandardCharsets.UTF_8.name()
            );

            String searchUrl =
                    "https://www.hepsiburada.com/ara?q="
                            + encodedProductName;

            driver.get(searchUrl);

            wait.until(webDriver ->
                    webDriver.getCurrentUrl().contains("/ara")
            );

        } catch (Exception exception) {
            throw new RuntimeException(
                    "Ürün adı URL için dönüştürülemedi.",
                    exception
            );
        }
    }
}