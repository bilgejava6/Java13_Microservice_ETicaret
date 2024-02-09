# KURULUMLAR VE PROJE TEKNOLOJİLERİ

## Docker üzerinde postgreSQL kurulumu
        Uygulamamızda Auth servis üzerinde kullanıcı oturum açma işlemlerini ve kayıtlarını ilişkisel bir 
    veritabanında tutuyoruz. Veritabanı olarak PostgreSQL kullanıyoruz. postgreSQL i docker üzerinde çalıştırmak
    için aşağıdaki kodu kullanabiliriniz.

```
    docker run --name postgreSQL -e POSTGRES_PASSWORD=root -p 5432:5432 -d postgres
```

## Docker üzerinde mongoDB çalıştırmak
    MongoDB kurulumu yaparken yetkili kullanıcı bilgilerinin girilmesi gereklidir. Bu bilgileri imajların 
    Ortam Değişkenlerine atayarak yapabiliyorsunuz. Docker bu tarz bilgileri ekleyebilmeniz için size 
    ek parametreler sunmaktadır.
    EK BİLGİ:
    docker üzerinde bir imaj eklemek istiyorsak -> docker pull [IMAGE_NAME]
    docker üzerinde bir imajı çalıştırmak istiyorsak -> docker run [IMAGE_NAME]
    Burada önemli bir nokta vardır. docker run eğer ortamda ilgili imaj yok ise önce imajı indirir sonra çalıştırır
    yani docker run yapmak için önce imajı pull etmenize gerek yoktur.
    Aşağıdaki kod çalışan bir mongoDB oluşturacaktır.
```bash
    docker run --name java13MongoDB -d -e "MONGO_INITDB_ROOT_USERNAME=admin" -e "MONGO_INITDB_ROOT_PASSWORD=root" -p 27017:27017 mongo:jammy
```

    MongoDB yi yönetebilmek için bir araç a ihtiytacımız var. Bu aracın adı. MobgoDB Compas tool. bu aracı indirip 
    kurmanız gereklidir. Adres:  https://www.mongodb.com/try/download/compass

    Compass kurulumu bittikten sonra, açılan yeni pencerede "New Connection +" butonuna tıklıyorsunuz. ekranın ortasında
    "> Advanced connection options" butonuna tıklayarak detaylı bağlantı ayarlarını yapıyoruz.
    1- açılan ekranda "Host" kısmına veritabanınızın ip adresini ve port numarasını giriyorsunuz. yerel bilgisayarınız
    için kullanılacak ise ya da docker desktop üzerinde ise (localhost:27017) şeklinde yazıyoruz.
    2- Authantication kısmına geçiş yaparak kurulum sırasında girdiğiniz kullanıcı adı ve şifreyi yazıyorsunuz. Docker
    run komutu ile çalıştırdı iseniz -e ile giriş yaptığınız bilgileri yazınız. (admin - root)

    NOT: MongoDB yi ilk kurulumları ve kullanımları için admin kullanıcı ile işlemleri yapabilrisiniz. Ancak, 
    veritabanlarını yönetmek ve işlemek için kullanmayınız. her DB için ayrı kullanıcı ve yetkiler oluşturunuz
    root kullanıcısı ve şifreleri sadece ilk kurulum için kullanılmalı ve tekrar kullanılmamalıdır. Sadece gerekli
    olduğu durumlarda mühedale için kullanınız.
    Gerekli dkümantasyonlara: https://www.mongodb.com/docs/manual/  ulaşabilirsiniz.
    
    Yetkilendirme işlemleri
    1- MONGOSH a tıklayarak açıyorsunuz
    2- açılan kısımda test> şeklinde bir yer greceksiniz, öncelikle test DB den kendi DB nize geçmek için
    use [DB_ADI] yazıp enter e basınız.
    Örn:
    use UserProfile
    switched to db UserProfile
    UserProfile>  şeklinde bir görüntü elde edeceksiniz.
    3- burada kullanıcı oluşturmak için gerekli komutları giriyoruz.
    db.createUser({
        user: "bilgeUser",
        pwd: "bilgeUser*",
        roles: ["readWrite","dbAdmin"]
    })
```
   db.createUser({user: "bilgeUser",pwd: "bilgeUser*",roles: ["readWrite","dbAdmin"]}) 
```




## Serevisler arası iletişim

## Application.yml bilgisini config serverdan almak ve configure etmek.
    Application Properties (yml) için gerekli configler.
    https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html

##    

    AA - ghp_1mejOFCl5f8Ih

    BB - x2LKo2mC2F8u

    CC - Hnywk4DDaYN