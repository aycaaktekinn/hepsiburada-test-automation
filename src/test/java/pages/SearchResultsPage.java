package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class SearchResultsPage {

    // Proje genelinde kullanılan ortak tarayıcı nesnesi.
    private final WebDriver driver;

    // Dinamik elementlerin yüklenmesini beklemek için kullanılır.
    private final WebDriverWait wait;

    /*
     * "Önerilen sıralama" yazısını içeren label elementi.
     *
     * Span yalnızca yazıyı taşıdığı için doğrudan label elementini
     * hedefliyoruz. Tıklanabilir alan label olduğu için bu locator
     * daha doğru çalışır.
     */
    private final By sortingDropdown =
            By.xpath(
                    "//label[.//span[normalize-space()='Önerilen sıralama']]"
            );

    /*
     * Açılan sıralama menüsündeki "En düşük fiyat" seçeneği.
     *
     * Elementin tag'i değişebileceği için yalnızca div ile
     * sınırlandırmadan metne göre arıyoruz.
     */
    private final By lowestPriceOption =
            By.xpath(
                    "//*[normalize-space()='En düşük fiyat']"
            );

    // İkinci ürünün başlık alanı.
    private final By secondProductTitle =
            By.cssSelector("[data-test-id='title-2']");

    // Daha sonra sepette karşılaştırmak için saklanacak bilgiler.
    private String selectedProductName;
    private String selectedProductPrice;

    public SearchResultsPage() {

        // Driver sınıfındaki mevcut tarayıcı nesnesini alır.
        driver = Driver.getDriver();

        // Elementlerin yüklenmesi için maksimum 15 saniye bekler.
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    /*
     * Arama sonuçlarını en düşük fiyata göre sıralar.
     */
    public void sortByLowestPrice() {

        // "Önerilen sıralama" alanının tıklanabilir olmasını bekler.
        WebElement sortingDropdownElement = wait.until(
                ExpectedConditions.elementToBeClickable(
                        sortingDropdown
                )
        );

        // Sıralama menüsünü açar.
        sortingDropdownElement.click();

        // Açılan menüde "En düşük fiyat" seçeneğini bekler.
        WebElement lowestPriceElement = wait.until(
                ExpectedConditions.elementToBeClickable(
                        lowestPriceOption
                )
        );

        // En düşük fiyat seçeneğine tıklar.
        lowestPriceElement.click();
    }
    public void selectSecondProduct() {

        /*
         * İkinci ürünün başlık alanının görünmesini bekler.
         */
        WebElement productTitleElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        secondProductTitle
                )
        );

        /*
         * Başlık alanının içindeki bağlantıyı bulur.
         * Ürüne tıklanacak asıl element bu <a> etiketidir.
         */
        WebElement productLink = productTitleElement.findElement(
                By.tagName("a")
        );

        /*
         * Ürün adını bağlantının title özelliğinden alır.
         */
        selectedProductName = productLink.getAttribute("title");

        /*
         * aria-label içerisinde ürün fiyatı bulunuyor.
         *
         * Örnek:
         * Sepete ekle, fiyat: 92.599 TL, Samsung Galaxy...
         */
        String ariaLabel =
                productTitleElement.getAttribute("aria-label");

        selectedProductPrice = ariaLabel
                .split("fiyat:")[1]
                .split(",")[0]
                .trim();

        System.out.println(
                "Seçilen ürün adı: " + selectedProductName
        );

        System.out.println(
                "Listedeki ürün fiyatı: " + selectedProductPrice
        );

        /*
         * Element görünür alana kaydırılır.
         */
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                productLink
        );

        /*
         * İkinci ürüne tıklanır.
         */
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        productLink
                )
        ).click();
    }
    public String getSelectedProductName() {
        return selectedProductName;
    }

    public String getSelectedProductPrice() {
        return selectedProductPrice;
    }
}