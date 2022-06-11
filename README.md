# ps-auto-servis
Ispitni zadatak iz predmeta Projektovanje softvera na temu Aplikacija za upravljanje auto servisom

## Pokretanje aplikacije

Aplikacija je dostupna online na nadresi https://ps.pequla.com

### Lokalno okruženje

Nakon kloniranja repozitorijuma potrebno je uvezti *dump* baze podataka koji se nalazi u datoteci `database-dump.sql` na MySQL server verzije 8.0

#### Backend

Proveriti da li je na računaru dostupna verzija 11 ili veća Java JDK okruženja. Ukoliko nije može se preuzeit sa [Adoptim websajta](https://adoptium.net/temurin/releases/?version=11)

Otoviti ceo direktorijum u IDE-u po izboru. Projekat je pisan u Jetbrains IntellJ Idea a kao package manager upotrebljen je Maven.

Pre pokretanja backend-a potrebno je napraviti `application.properties` datoteku u direktorijumu `src/main/resources` naravno uz odgovarajucu izmenu po potrebi

```properties
spring.data.web.pageable.default-page-size=10
spring.data.web.pageable.max-page-size=30
spring.datasource.url=jdbc:mysql://localhost:3306/auto_servis
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.open-in-view=true
server.port=8080
server.address=localhost
```
#### Frontend

Kod koristi apsolutne putanje pa je potrebno frontend pokrenuti na nekom webserver-u. Ukoliko koristite windows postji nekoliko opcija:

- Ukoliko imate php instaliran na racunaru mozete pokrenuti development server uz pomoc
 `php -S localhost:4000`
- Ukoliko vas IDE poseduje web server mozete njega koristiti (npr Visual Studio run. plugin ili Atom Text Editor)
- Uz pomoc python3 development web servera `python3 -m http.server`

> Kod je razvijan upotrebom PHP Development Server-a zbog svoje brzine i kompatibilnosti sa autosave-om. Kod moze pokrenuti bilo koji web server jer su fajlovi staticni

## Produkcija

Aplikacija je trestirana u produkcionom okruzenju Ubuntu Server 22.04LTS. Potrebno je instalirati sledece module:

```bash
git clone https://github.com/Pequla/ps-auto-servis.git
sudo apt install mysql-server apache2 openjdk-11-jre -y

# Instalacija mysql-a
mysql < alter user 'root'@'localhost' identified by 'root'; # bug u mysql_secure_install
mysql < database-dump.sql # ucitava bazu iz dump-a
sudo mysql_secure_install
```
Nakon instalacione skripte mysql-a potrebno je genrisati jar fajl za backend

```bash
mvn compile
mvn package
```

Prekopirat fajl `target/auto-servis-0.0.1.jar` u radni direktorijum. U ovom slucaju to ce biti `/home/<user>/backend`

Napraviti `run.sh` skriptu za pokretanje

```bash
cd ~
nano run.sh
# Sadrzaj fajla prekopirati
cd /home/<user>/backend
java -jar auto-servis-0.0.1.jar

# fajl mora dobiti execute flag
chmod u+x run.sh

# Pravljenje systemd servisa
sudo nano /etc/systemd/system/backend.service
```

Sadrzaj `backend.service`IZMENITI PO POTREBI:
```
[Unit]
Description=autoservis-backend

[Service]
User=<user>
Restart=on-failure
ExecStart=/home/<user>/backend/run.sh

[Install]
WantedBy=multi-user.target
```

Nakon toga potrebno je pokrenuti servis
```bash
sudo systemctl enable backend
sudo systemctl start backend
```

Postavljanje frontenda je jednostavno

```bash
sudo a2enmod proxy
sudo a2enmod proxy_http
sudo a2enmod proxy_balancer
sudo a2enmod lbmethod_byrequests
sudo systemctl reload apache2

nano ps-auto-servis/public/js/main.js
```
Potrebno je promeniti prvu liniju koda u 
`const baseUrl = "/api`

```bash
# Kopirati public folder projekta u /var/www/
sudo cp -r ps-auto-servis/public /var/www/public 

sudo nano /etc/apache2/sites-avalable/autoservis.conf
```

Sadrzaj fajla

```
<VirtualHost *:80>
        #ServerName app.example.com

        ServerAdmin webmaster@localhost
        DocumentRoot /var/www/public

        ProxyPreserveHost On

        ProxyPass /api http://127.0.0.1:8080/api
        ProxyPassReverse /api http://127.0.0.1:8080/api

        ErrorLog ${APACHE_LOG_DIR}/autoservis-error.log
        CustomLog ${APACHE_LOG_DIR}/autoservis-access.log combined
</VirtualHost>
```

```bash
sudo a2ensite autoservis.conf
sudo systemctl restart apache2
```