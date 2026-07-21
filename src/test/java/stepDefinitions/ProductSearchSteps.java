package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.ProductPage;
import pages.CartPage;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ProductSearchSteps {

    // Ana sayfa işlemlerini yönetir.
    private final HomePage homePage = new HomePage();

    // Arama sonuçları sayfasındaki işlemleri yönetir.
    private final SearchResultsPage searchResultsPage =
            new SearchResultsPage();

    private final ProductPage productPage =
            new ProductPage();

    private final CartPage cartPage =
            new CartPage();
    private static final Logger logger =
            LoggerFactory.getLogger(ProductSearchSteps.class);
    private String selectedProductName;
    private String selectedProductPrice;

    @Given("Kullanıcı Hepsiburada ana sayfasındadır")
    public void kullaniciHepsiburadaAnaSayfasindadir() {

        logger.info("Hepsiburada ana sayfası açılıyor.");

        homePage.openHomePage();

        logger.info("Hepsiburada ana sayfası açıldı.");
    }

    @When("Kullanıcı {string} ürününü arar")
    public void kullaniciUrunuArar(String productName) {

        logger.info(
                "Aranacak ürün: {}",
                productName
        );

        homePage.searchProduct(productName);

        logger.info("Ürün arama işlemi tamamlandı.");
    }

    @When("Arama sonuçlarını en düşük fiyata göre sıralar")
    public void aramaSonuclariniEnDusukFiyataGoreSiralar() {

        logger.info(
                "Arama sonuçları en düşük fiyata göre sıralanıyor."
        );

        searchResultsPage.sortByLowestPrice();

        logger.info("Fiyat sıralama işlemi tamamlandı.");
    }
    @When("Sonuç listesindeki ikinci ürünü seçer")
    public void sonucListesindekiIkinciUrunuSecer() {

        logger.info("Sonuç listesindeki ikinci ürün seçiliyor.");

        searchResultsPage.selectSecondProduct();

        selectedProductName =
                searchResultsPage.getSelectedProductName();

        selectedProductPrice =
                searchResultsPage.getSelectedProductPrice();

        logger.info(
                "Seçilen ürün adı: {}",
                selectedProductName
        );

        logger.info(
                "Seçilen ürün fiyatı: {}",
                selectedProductPrice
        );
    }
    @When("Seçilen ürünü sepete ekler")
    public void secilenUrunuSepeteEkler() {

        logger.info("Ürün sepete ekleniyor.");

        productPage.addProductToCart();
        productPage.goToCart();

        logger.info("Ürün sepete eklendi ve sepet sayfası açıldı.");
    }

    @Then("Sepetteki ürün adı seçilen ürün adıyla aynı olmalıdır")
    public void sepettekiUrunAdiSecilenUrunAdiylaAyniOlmalidir() {

        // Sepet sayfasındaki ürün adını alır.
        String cartProductName =
                cartPage.getCartProductName();

        // Konsola sepette okunan ürün adını yazdırır.
        System.out.println(
                "Sepetteki ürün adı: " + cartProductName
        );

        // Sepetteki ürün adı ile seçilen ürün adını karşılaştırır.
        org.testng.Assert.assertEquals(
                cartProductName,
                selectedProductName,
                "Sepetteki ürün adı seçilen ürün adıyla aynı olmalıdır."
        );
    }
    @Then("Sepetteki ürün fiyatı listedeki ürün fiyatıyla aynı olmalıdır")
    public void sepettekiUrunFiyatiListedekiUrunFiyatiylaAyniOlmalidir() {

        // Sepette görüntülenen ham fiyat metnini alır.
        String cartProductPrice =
                cartPage.getCartProductPrice();

        System.out.println(
                "Sepetteki ham fiyat: " + cartProductPrice
        );

        /*
         * Sepetteki metin şu şekilde gelebilir:
         *
         * maks
         * 92.599,00 TL
         *
         * Regex ile yalnızca fiyat bölümünü alıyoruz.
         */
        java.util.regex.Pattern pricePattern =
                java.util.regex.Pattern.compile(
                        "\\d{1,3}(?:\\.\\d{3})*(?:,\\d{2})?\\s*TL"
                );

        java.util.regex.Matcher matcher =
                pricePattern.matcher(cartProductPrice);

        org.testng.Assert.assertTrue(
                matcher.find(),
                "Sepette geçerli bir fiyat metni bulunamadı."
        );

        // Regex ile bulunan fiyat.
        String extractedCartPrice =
                matcher.group();

        /*
         * Sepet fiyatı:
         * 92.599,00 TL
         *
         * Liste fiyatı:
         * 92.599 TL
         *
         * Karşılaştırmadan önce kuruş bölümünü kaldırıyoruz.
         */
        String normalizedCartPrice =
                extractedCartPrice.replace(",00", "").trim();

        String normalizedSelectedPrice =
                selectedProductPrice.trim();

        System.out.println(
                "Sepette normalize edilen fiyat: "
                        + normalizedCartPrice
        );

        System.out.println(
                "Listedeki fiyat: "
                        + normalizedSelectedPrice
        );

        org.testng.Assert.assertEquals(
                normalizedCartPrice,
                normalizedSelectedPrice,
                "Sepetteki ürün fiyatı listedeki fiyatla aynı olmalıdır."
        );
    }
}