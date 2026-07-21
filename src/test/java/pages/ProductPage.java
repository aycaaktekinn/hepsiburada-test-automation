package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ProductPage {

    // Projede ortak kullanılan tarayıcı nesnesi.
    private final WebDriver driver;

    // Dinamik elementlerin yüklenmesini beklemek için kullanılır.
    private final WebDriverWait wait;

    /*
     * Sayfada aynı data-test-id değerine sahip birden fazla
     * "Sepete ekle" butonu bulunabildiği için locator tümünü bulur.
     */
    private final By addToCartButtons =
            By.cssSelector("[data-test-id='addToCart']");

    private final By goToCartButton =
            By.xpath("//button[normalize-space()='Sepete git']");

    public ProductPage() {

        // Ortak driver nesnesini alır.
        driver = Driver.getDriver();

        // Elementler için maksimum 15 saniye bekleme tanımlar.
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    /*
     * Ürün detay sayfasındaki görünür ve aktif
     * "Sepete ekle" butonuna tıklar.
     */
    public void addProductToCart() {

        WebElement addToCartButton = wait.until(webDriver ->

                webDriver.findElements(addToCartButtons)
                        .stream()
                        .filter(WebElement::isDisplayed)
                        .filter(WebElement::isEnabled)
                        .findFirst()
                        .orElse(null)
        );

        if (addToCartButton == null) {
            throw new IllegalStateException(
                    "Görünür ve kullanılabilir Sepete ekle butonu bulunamadı."
            );
        }

        /*
         * Butonu ekranın ortasına getirir.
         * Böylece başka bir sabit alanın butonun önünü kapatması önlenir.
         */
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                addToCartButton
        );

        /*
         * Normal click bazı dinamik sayfalarda engellenebildiği için
         * önce normal tıklama denenir, hata olursa JavaScript ile tıklanır.
         */
        try {
            addToCartButton.click();
        } catch (Exception exception) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    addToCartButton
            );
        }
    }
    public void goToCart() {

        /*
         * Sepete ekleme işleminden sonra görünen
         * "Sepete git" butonunun tıklanabilir olmasını bekler.
         */
        WebElement goToCart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        goToCartButton
                )
        );

        // Sepet sayfasına gider.
        goToCart.click();
    }
}
