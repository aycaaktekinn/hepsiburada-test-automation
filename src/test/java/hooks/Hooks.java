package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.Driver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    /*
     * Bu sınıfa ait logger nesnesidir.
     *
     * LoggerFactory, log mesajlarının hem konsola hem de
     * logback.xml içerisinde belirttiğimiz dosyaya yazılmasını sağlar.
     */
    private static final Logger logger =
            LoggerFactory.getLogger(Hooks.class);

    /*
     * Her Cucumber senaryosundan önce çalışır.
     */
    @Before
    public void beforeScenario(Scenario scenario) {

        logger.info(
                "Senaryo başladı: {}",
                scenario.getName()
        );
    }

    /*
     * Her Cucumber senaryosundan sonra çalışır.
     */
    @After
    public void afterScenario(Scenario scenario) {

        /*
         * Senaryo başarısız olmuşsa ekran görüntüsü alınır.
         */
        if (scenario.isFailed()) {

            logger.error(
                    "Senaryo başarısız oldu: {}",
                    scenario.getName()
            );

            takeScreenshot(scenario);

        } else {

            logger.info(
                    "Senaryo başarıyla tamamlandı: {}",
                    scenario.getName()
            );
        }

        /*
         * Senaryo tamamlandıktan sonra tarayıcı kapatılır.
         */
        Driver.closeDriver();

        logger.info("Tarayıcı kapatıldı.");
    }

    /*
     * Hata oluştuğunda ekran görüntüsü alır.
     *
     * Görüntü:
     * 1. Cucumber HTML raporuna eklenir.
     * 2. target/screenshots klasörüne kaydedilir.
     */
    private void takeScreenshot(Scenario scenario) {

        try {

            WebDriver driver = Driver.getDriver();

            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            /*
             * Ekran görüntüsünü Cucumber raporuna ekler.
             */
            scenario.attach(
                    screenshot,
                    "image/png",
                    "Hata Ekran Görüntüsü"
            );

            /*
             * Dosya adında kullanılamayacak karakterleri temizler.
             */
            String safeScenarioName = scenario.getName()
                    .replaceAll("[^a-zA-Z0-9ğüşöçıİĞÜŞÖÇ-]", "_");

            String timestamp = LocalDateTime.now()
                    .format(
                            DateTimeFormatter.ofPattern(
                                    "yyyyMMdd-HHmmss"
                            )
                    );

            Path screenshotDirectory =
                    Paths.get("target", "screenshots");

            /*
             * target/screenshots klasörü yoksa oluşturur.
             */
            Files.createDirectories(screenshotDirectory);

            Path screenshotPath = screenshotDirectory.resolve(
                    safeScenarioName
                            + "-"
                            + timestamp
                            + ".png"
            );

            Files.write(
                    screenshotPath,
                    screenshot
            );

            logger.info(
                    "Ekran görüntüsü kaydedildi: {}",
                    screenshotPath.toAbsolutePath()
            );

        } catch (IOException exception) {

            logger.error(
                    "Ekran görüntüsü dosyaya kaydedilemedi.",
                    exception
            );

        } catch (Exception exception) {

            logger.error(
                    "Ekran görüntüsü alınırken hata oluştu.",
                    exception
            );
        }
    }
}