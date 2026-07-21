package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

import java.time.Duration;

public class CartPage {

    // Proje genelinde kullanılan ortak WebDriver nesnesi.
    private final WebDriver driver;

    // Sepet sayfasındaki elementlerin yüklenmesini beklemek için kullanılır.
    private final WebDriverWait wait;

    /*
     * Sepetteki ürün adını taşıyan bağlantıyı bulur.
     *
     * Class değerinin son kısmı dinamik olabileceği için
     * yalnızca "product_name_" ile başlayan div hedeflenir.
     */
    private final By cartProductName =
            By.cssSelector("div[class^='product_name_'] a");

    /*
     * Sepette görüntülenen ürün fiyatını bulur.
     *
     * Class değerinin son kısmı dinamik olabileceği için
     * yalnızca "product_price_" ile başlayan div hedeflenir.
     */
    private final By cartProductPrice =
            By.cssSelector("div[class^='product_price_']");

    public CartPage() {

        // Driver sınıfındaki ortak tarayıcı nesnesini alır.
        driver = Driver.getDriver();

        // Elementlerin görünmesi için en fazla 15 saniye bekler.
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    // Sepette görüntülenen ürün adını döndürür.
    public String getCartProductName() {

        WebElement productNameElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartProductName
                )
        );

        /*
         * Önce innerText değeri okunur.
         * Bazı dinamik sayfalarda getText() boş dönebilir.
         */
        String productName =
                productNameElement.getAttribute("innerText");

        /*
         * innerText boşsa textContent denenir.
         */
        if (productName == null || productName.isBlank()) {
            productName =
                    productNameElement.getAttribute("textContent");
        }

        /*
         * Değer null ise boş metin döndürülür.
         * Değer varsa başındaki ve sonundaki boşluklar temizlenir.
         */
        return productName == null
                ? ""
                : productName.trim();
    }

    // Sepette görüntülenen ürün fiyatını döndürür.
    public String getCartProductPrice() {

        WebElement productPriceElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartProductPrice
                )
        );

        /*
         * Fiyat metnini okur ve gereksiz boşlukları temizler.
         */
        String productPrice =
                productPriceElement.getAttribute("innerText");

        if (productPrice == null || productPrice.isBlank()) {
            productPrice =
                    productPriceElement.getAttribute("textContent");
        }

        return productPrice == null
                ? ""
                : productPrice.trim();
    }
}