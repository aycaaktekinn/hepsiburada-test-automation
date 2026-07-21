# Hepsiburada Test Automation

Bu proje, Hepsiburada web sitesi üzerinde uçtan uca test otomasyonu gerçekleştirmek amacıyla geliştirilmiştir.

Proje kapsamında ürün arama, sonuçları en düşük fiyata göre sıralama, ikinci ürünü seçme, sepete ekleme ve sepetteki ürün bilgilerinin doğrulanması işlemleri otomatikleştirilmiştir.

## Kullanılan Teknolojiler

- Java
- Selenium WebDriver
- TestNG
- Cucumber
- Gherkin
- Maven
- Page Object Model
- SLF4J
- Logback
- IntelliJ IDEA

## Test Senaryosu

Otomasyon senaryosu aşağıdaki adımlardan oluşmaktadır:

1. Hepsiburada ana sayfası açılır.
2. `Samsung Galaxy S26 Ultra` ürünü aranır.
3. Arama sonuçları en düşük fiyata göre sıralanır.
4. Sonuç listesindeki ikinci ürün seçilir.
5. Seçilen ürünün adı ve fiyatı kaydedilir.
6. Ürün sepete eklenir.
7. Sepet sayfasına gidilir.
8. Sepetteki ürün adı ile seçilen ürün adı karşılaştırılır.
9. Sepetteki ürün fiyatı ile listedeki ürün fiyatı karşılaştırılır.

## Proje Yapısı

```text
src
└── test
    ├── java
    │   ├── hooks
    │   │   └── Hooks.java
    │   ├── pages
    │   │   ├── HomePage.java
    │   │   ├── SearchResultsPage.java
    │   │   ├── ProductPage.java
    │   │   └── CartPage.java
    │   ├── runners
    │   │   └── TestRunner.java
    │   ├── stepDefinitions
    │   │   └── ProductSearchSteps.java
    │   └── utilities
    │       └── Driver.java
    └── resources
        ├── features
        │   └── product_search.feature
        └── logback.xml
```

## Kullanılan Yapılar

### Page Object Model

Her web sayfası için ayrı bir Page sınıfı oluşturulmuştur. Locator ve sayfa işlemleri ilgili sınıflarda tutulmuştur.

Bu yapı sayesinde kodlar daha düzenli, okunabilir ve sürdürülebilir hale getirilmiştir.

### BDD ve Cucumber

Test senaryosu Gherkin diliyle yazılmıştır. Feature dosyasındaki adımlar, step definition sınıfında Java metotlarına bağlanmıştır.

### Explicit Wait

Dinamik elementlerin yüklenmesini beklemek için `WebDriverWait` ve `ExpectedConditions` kullanılmıştır.

### Assertion

Sepetteki ürün adı ve fiyatı, arama sonuçlarında seçilen ürün bilgileriyle TestNG assertion metotları kullanılarak karşılaştırılmıştır.

### Locator Kullanımı

Sayfa elementlerini bulmak için XPath ve CSS Selector locator türleri kullanılmıştır.

Dinamik class değerlerine karşı daha dayanıklı seçimler yapılabilmesi için class başlangıcına göre çalışan CSS Selector ifadeleri tercih edilmiştir.

## Loglama

Test başlangıcı, test adımları, doğrulamalar ve hata durumları SLF4J ve Logback kullanılarak kayıt altına alınmaktadır.

Log dosyaları test çalıştırıldıktan sonra aşağıdaki klasörde oluşur:

```text
target/logs/
```

## Raporlama

Cucumber kullanılarak HTML, JSON ve XML formatlarında test raporları oluşturulmaktadır.

Oluşan rapor dosyaları:

```text
target/cucumber-report.html
target/cucumber-report.json
target/cucumber-report.xml
```

Başarısız senaryolarda ekran görüntüsü alınır ve rapora eklenir.

Ekran görüntüleri aşağıdaki klasörde saklanır:

```text
target/screenshots/
```

## Test Raporunu Görüntüleme

Test tamamlandıktan sonra aşağıdaki dosya tarayıcıda açılabilir:

```text
target/cucumber-report.html
```

## Not

Hepsiburada web sitesindeki dinamik HTML yapısı, element locator değerleri veya ürün sıralaması zaman içinde değişebilir. Bu durumda ilgili locator ve test verilerinin güncellenmesi gerekebilir.
