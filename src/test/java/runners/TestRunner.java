package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(

        // Feature dosyalarının bulunduğu klasör.
        features = "src/test/resources/features",

        /*
         * Step definition ve hook sınıflarının paketleri.
         *
         * hooks paketini eklemezsek @Before ve @After
         * metotları çalışmaz.
         */
        glue = {
                "stepDefinitions",
                "hooks"
        },

        /*
         * Oluşturulacak rapor türleri.
         */
        plugin = {
                "pretty",

                // Görsel HTML raporu.
                "html:target/cucumber-report.html",

                // JSON formatında sonuçlar.
                "json:target/cucumber-report.json",

                // JUnit XML formatında sonuçlar.
                "junit:target/cucumber-report.xml"
        },

        // Konsol çıktısındaki gereksiz karakterleri azaltır.
        monochrome = true
)
public class TestRunner
        extends AbstractTestNGCucumberTests {
}