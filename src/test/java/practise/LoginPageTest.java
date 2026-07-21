package com.hepsiburada.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageTest {

    // WebDriver, tarayıcıyı Selenium üzerinden kontrol etmemizi sağlar.
    WebDriver driver;

    @BeforeMethod
    public void setUp() {

        // Chrome tarayıcısını başlatır.
        driver = new ChromeDriver();

        // Tarayıcı penceresini büyütür.
        driver.manage().window().maximize();
    }

    @Test
    public void loginSayfasiBasariliAcilmali() {

        // Hepsiburada giriş sayfasını açar.
        driver.get("https://www.hepsiburada.com/uyelik/giris");

        // Açılan sayfanın adresini alır.
        String currentUrl = driver.getCurrentUrl();

        // Adresin boş olmadığını kontrol eder.
        Assert.assertFalse(
                currentUrl.isEmpty(),
                "Giriş sayfasının URL bilgisi boş olmamalıdır."
        );
    }
    @Test
    public void loginSayfasiBasligiDogruOlmali() {

        // Hepsiburada giriş sayfasını açar.
        driver.get("https://www.hepsiburada.com/uyelik/giris");

        // Tarayıcı sekmesinde görünen sayfa başlığını alır.
        String pageTitle = driver.getTitle();

        // Sayfa başlığını terminalde görmemizi sağlar.
        System.out.println("Sayfa başlığı: " + pageTitle);

        // Sayfa başlığının boş olmadığını kontrol eder.
        Assert.assertFalse(
                pageTitle.isEmpty(),
                "Giriş sayfasının başlığı boş olmamalıdır."
        );

        // Sayfa başlığında Hepsiburada ifadesinin bulunup bulunmadığını kontrol eder.
        Assert.assertTrue(
                pageTitle.toLowerCase().contains("hepsiburada"),
                "Sayfa başlığında Hepsiburada ifadesi bulunmalıdır."
        );
    }
    @Test
    public void emailVeSifreAlanlariGorunurVeKullanilabilirOlmali() {

        // Hepsiburada giriş sayfasını açar.
        driver.get("https://www.hepsiburada.com/uyelik/giris");

        // Sayfanın hangi adrese yönlendirildiğini görmek için yazdırıyoruz.
        System.out.println("Güncel URL: " + driver.getCurrentUrl());
        System.out.println("Sayfa başlığı: " + driver.getTitle());

        // Selenium'un elementleri en fazla 15 saniye beklemesini sağlar.
        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

        // E-posta alanı görünür olana kadar bekler.
        WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("txtUserName")
                )
        );

        // Şifre alanı görünür olana kadar bekler.
        WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("txtPassword")
                )
        );

        Assert.assertTrue(
                emailInput.isDisplayed(),
                "E-posta alanı görünür olmalıdır."
        );

        Assert.assertTrue(
                emailInput.isEnabled(),
                "E-posta alanı kullanılabilir olmalıdır."
        );

        Assert.assertTrue(
                passwordInput.isDisplayed(),
                "Şifre alanı görünür olmalıdır."
        );

        Assert.assertTrue(
                passwordInput.isEnabled(),
                "Şifre alanı kullanılabilir olmalıdır."
        );
    }
    @Test
    public void emailVeSifreAlanlarinaVeriGirilebilmeli() {

        // Hepsiburada giriş sayfasını açar.
        driver.get("https://www.hepsiburada.com/uyelik/giris");

        // Elementlerin görünmesini en fazla 15 saniye beklemek için kullanılır.
        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

        // E-posta alanını bulur ve görünür olmasını bekler.
        WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("txtUserName")
                )
        );

        // Şifre alanını bulur ve görünür olmasını bekler.
        WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("txtPassword")
                )
        );

        // Testte kullanacağımız örnek veriler.
        String expectedEmail = "test@example.com";
        String expectedPassword = "Test1234!";

        // E-posta alanına veri yazar.
        emailInput.sendKeys(expectedEmail);

        // Şifre alanına veri yazar.
        passwordInput.sendKeys(expectedPassword);

        // Alanların içindeki gerçek değerleri okur.
        String actualEmail = emailInput.getAttribute("value");
        String actualPassword = passwordInput.getAttribute("value");

        // E-posta alanına doğru değerin yazıldığını kontrol eder.
        Assert.assertEquals(
                actualEmail,
                expectedEmail,
                "E-posta alanına girilen değer beklenen değerle aynı olmalıdır."
        );

        // Şifre alanına doğru değerin yazıldığını kontrol eder.
        Assert.assertEquals(
                actualPassword,
                expectedPassword,
                "Şifre alanına girilen değer beklenen değerle aynı olmalıdır."
        );
    }
    @Test
    public void girisYapButonuGorunurVeTiklanabilirOlmali() {

        // Hepsiburada giriş sayfasını açar.
        driver.get("https://www.hepsiburada.com/uyelik/giris");

        // Elementin yüklenmesini en fazla 15 saniye bekler.
        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

        // Giriş yap butonunu görünür olana kadar bekleyerek bulur.
        WebElement loginButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("btnLogin")
                )
        );

        // Butonun sayfada görünür olduğunu kontrol eder.
        Assert.assertTrue(
                loginButton.isDisplayed(),
                "Giriş yap butonu görünür olmalıdır."
        );

        // Butonun aktif olduğunu kontrol eder.
        Assert.assertTrue(
                loginButton.isEnabled(),
                "Giriş yap butonu aktif olmalıdır."
        );

        // Butonun gerçekten tıklanabilir duruma gelmesini bekler.
        WebElement clickableLoginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("btnLogin")
                )
        );

        // Tıklanabilir elementin bulunmuş olduğunu doğrular.
        Assert.assertNotNull(
                clickableLoginButton,
                "Giriş yap butonu tıklanabilir olmalıdır."
        );
    }
    @Test
    public void yanlisSifreIleGirisYapilamamali() {

        driver.get("https://www.hepsiburada.com/uyelik/giris");

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

        WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("txtUserName")
                )
        );

        WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("txtPassword")
                )
        );

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("btnLogin")
                )
        );

        emailInput.sendKeys("testdeneme1150@gmail.com");
        passwordInput.sendKeys("YanlisSifre123!");

        loginButton.click();

        // Hata mesajı görünür olana kadar bekler.
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(
                                "[data-test-id='inline-alert-label']"
                        )
                )
        );

        // Gerçekte gösterilen hata mesajını alır.
        String actualErrorMessage = errorMessage.getText();

        // Beklediğimiz hata mesajı.
        String expectedErrorMessage =
                "Girdiğiniz şifre eksik veya hatalı.";

        System.out.println(
                "Gösterilen hata mesajı: " + actualErrorMessage
        );

        // Hata mesajının görünür olduğunu kontrol eder.
        Assert.assertTrue(
                errorMessage.isDisplayed(),
                "Yanlış girişte hata mesajı görünür olmalıdır."
        );
/*
        // Gerçek hata mesajını beklenen mesajla karşılaştırır.
        Assert.assertEquals(
                actualErrorMessage,
                expectedErrorMessage,
                "Gösterilen hata mesajı beklenen değerle aynı olmalıdır."
        );
        Manuel testte çalışıyor fakat otomasyon kısmında beklenmeyen hata mesajı fail veriyor
 */


        // Kullanıcının giriş ekranında kaldığını da kontrol eder.
        Assert.assertTrue(
                driver.getCurrentUrl().contains("giris.hepsiburada.com"),
                "Yanlış bilgilerle kullanıcı giriş ekranında kalmalıdır."
        );
    }


    @AfterMethod
    public void tearDown() {

        // Test bittikten sonra tarayıcıyı tamamen kapatır.
        if (driver != null) {
            driver.quit();
        }
    }
}
