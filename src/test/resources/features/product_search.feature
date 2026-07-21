Feature: Hepsiburada ürün arama ve sepete ekleme

  Scenario: İkinci ürünün sepete eklenmesi ve bilgilerinin doğrulanması
    Given Kullanıcı Hepsiburada ana sayfasındadır
    When Kullanıcı "Samsung Galaxy S26 Ultra" ürününü arar
    And Arama sonuçlarını en düşük fiyata göre sıralar
    And Sonuç listesindeki ikinci ürünü seçer
    And Seçilen ürünü sepete ekler
    Then Sepetteki ürün adı seçilen ürün adıyla aynı olmalıdır
    And Sepetteki ürün fiyatı listedeki ürün fiyatıyla aynı olmalıdır