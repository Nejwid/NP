projekt opierający się na wykorzystaniu kontenerów Docker;

główne cele:
- stworzenie testowalnego kodu i stała kontrola jakości kodu
- skonfigurowanie powtarzalnego procesu CI/CD
- możliwie dokładne logowanie i analiza zdarzeń
- symulacja utrzymania w środowisku produkcyjnym i ogólnie bardziej zaawansowana praca z GitLabem
Aplikacja służy do obsługi i walidacji zamówień w sklepie internetowym.
Podstawowym efektem projektu jest aplikacja Java (Spring Boot) która: jest automatycznie sprawdzana pod kątem jakości kodu, posiada generowaną dokumentację techniczną, jest pakowana do wykonywalnej paczki JAR, może być uruchamiana w kontenerze Docker, ma konfigurację dostosowaną do środowiska kontenerowego.
Ponadto, jeśli aplikacja jest uruchamiana w dockerze: jest konfigurowana przez zmienne środowiskowe, a dane aplikacji i usług są trwale przechowywane dzięki podpięciu "volumes".
Logika aplikacji jest pokryta testami jednostkowymi, dodano również narzędzie służące do weryfikacji, jaka dokładnie część kodu jest testowana.
Projekt realizuje CI/CD, zawiera runnery działające w dockerze, każda wersja aplikacji jest oznaczona stosownym tagiem i jej obraz Dockera jest przechowywany w prywatnym rejestrze.

Do logowania zdarzeń używana jest biblioteka Log4j, zdarzenia przechowywane są w zewnętrznej aplikacji OpenObserver mającej swoją instancje także w dockerze.

Projekt zawiera również lokalny serwer SMTP gdzie wysyłane są potwierdzenia złożenia poprawnych zamówień.
