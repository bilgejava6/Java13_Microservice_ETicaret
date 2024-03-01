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

## Docker üzerinde Redis Single Node oluşturmak

```bash
    docker run --name java13-redis -p 6379:6379 -d redis
```

```bash
    docker run  --name redis-gui -d -p 8001:8001 redislabs/redisinsight:1.14.0
```

## Redis Spring Boot Configuration
    İlgili bağımlılık eklenir.
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis
    implementation 'org.springframework.boot:spring-boot-starter-data-redis:3.2.2'

    DİKKAT!!!!
    Redis repository olarak kullanılabileceği gibi, Cache olarakta kullanılabilir, Bu nedenle Spring te Cache i 
    ve Redis Repostory aktif etmek için gerekli annotasyonları config e eklemeniz uygun olacaktır.

```java
@Configuration
@EnableRedisRepositories
@EnableCaching
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }

}
```

        Rediste cache oluşturmak için, istediğiniz methodun üzerinde @Cachable anatasyonunu ekliyorsunuz
    böylelikle bu method a girilen değerler için bir Key oluşturuluyor ve return değeri redis üzerinde 
    cache lenmiş oluyor.

    DİKKAT!!!! Spring Boot üzerinde alınan Cache lerin temizlenmesi
    1-  Objects.requireNonNull(cacheManager.getCache("user-find-all")).clear();
    bu işlem bir key e sahip olmayan cache leri temizlemek için kullanılır.
    2- Objects.requireNonNull(cacheManager.getCache("user-find-all")).evict([KEY]);
    bu işlem dışarıdan değer alan bir methodun cache lenmiş datalarını özel olarak silmek 
    için kullanılır.
    @Cachable("find-by-ad")
    findByAd("muhammet") -> Redis => find-by-ad::muhammet
    clear cache -> getCache("find-by-ad").evict("muhammet");
    Eğer bellir bir cache in tamamını temizlemek isterseniz, 1. maddeyi kullanın.

## ElasticSearch Kurulumu ve Kullanımı

```bash
    docker network create java13-network
```

```bash
    docker run -d --name elasticsearch --net java13-network -p 9200:9200 -p 9300:9300 -e "xpack.security.enabled=false" -e "xpack.security.transport.ssl.enabled=false" -e "discovery.type=single-node" -e "ELASTIC_USERNAME=admin"  -e "ELASTIC_PASSWORD=root" -e "ES_JAVA_OPTS=-Xms512m -Xmx1024m" elasticsearch:8.12.1
```

```bash
    docker run -d --name kibana --net java13-network -p 5601:5601 kibana:8.12.1
```


    DİKKAT!!!!!
    Elasticsearch sürümleri ile Spring sürümleri arasında bir uyum olması gerekli. çünkü eski sürümleri kullanabilmek için
    belli spring boot sürümlrerini kullanmanız gereklidir.

    Spring Boot ile kullanmak için öncelikle bağımlılık ekliyoruz.
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-elasticsearch
    implementation 'org.springframework.boot:spring-boot-starter-data-elasticsearch:3.2.2'

    İligi elasticsearch e bağlanmak için gerekli olana bağlantı configlerini application.yml içine yazoyoruz.

```yml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: admin
    password: root 
```


## RabbitMQ Kurulum ve Kullanım
    RabbitMQ iki port ile çalışır. 5672 , 15672 bu portlardan;
    1- 5672 olan port Rest isteklerini işlemek için kullanılır, bu nedenle Spring Boot bu port a bağlanır.
    2- 15672 olan port arayüz webUI kısmıdır. yönetim ekranı burasıdır.

```bash
  docker run -d --name java13-rabbit -p 5672:5672 -p 15672:15672  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=root rabbitmq:3-management
```

    Spring boot ile kullanım için;
    implemantation 'org.springframework.boot:spring-boot-starter-amqp:3.2.2'


    DİKKAT!!!!!
    RabbitMQ Desrializable işleminde getirillen yeni güvenlik config nedeniyle şu ENV nin eklenmesi
    gereklidir. "SPRING_AMQP_DESERIALIZATION_TRUST_ALL=true"
    Bu environment ı eklemek için user microsevisin main class üzerine sağ tıklayarak
    run modify configuration diyerek environment variable eklemeniz gerekmektedir.

    http://34.155.28.255:8888/application-user.yml    


## Serevisler arası iletişim

## Application.yml bilgisini config serverdan almak ve configure etmek.
    Application Properties (yml) için gerekli configler.
    https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html

## Docker İmage oluşturma
    İmage Oluşturmak için 
    - İlk olarak gradle ile ilgili modül ün build işlemi yapılır.
    - İkinci aşamada gradle ile builddependents yapılır.
    - sonra Eğe konum olarak ilgili modülün dizini içinde değil isek, terminal ekranından 
    ilgili modülün dizinine geçiş yaparız. Bunu yapmak için kullanabileceğiniz komutlar
    > cd .. // bir üst dizine geçer
    > cd <modul_adı> ilgili modülün içine giriş yapar
    - terminal ekranında aşağıda bulunan ilgili modül için docker build komutu çalıştırılır.
    - son olarak ta docker desktop üzerinden docker hub e push işlemi yapılır.

    docker build -t <HUB_REPOSITORY_NAME/IMAGE NAME:VERSION> .
    DİKKAT!!!! MacOS M Chipset kullananlar özellikle platform belirtmelidirler.

    1- docker build --platform linux/amd64 -t javaboost2/auth-service:v.0.2 .

    2- docker build --platform linux/amd64 -t javaboost2/config-service:v.0.2 .

    3- docker build --platform linux/amd64 -t javaboost2/user-service:v.0.2 .

    



## Kubernetes POD

        Pod, Nodes içinde yer alan sanal Pc lerdir. içerisinde image ya da image lar barındırabilir. Bir yaşam döngüsü vardır
    bu nedenle başlar, işlemlerini yürütür, bir süre sonra kaybolur. Bu nedenle bir pod restart olsa bile aynı şekilde 
    kalmaz yani bir pod yeniden başlamaz yeniden doğar. Bu nedenle yer yeni başlamada yeni bir pod oluşur  ve ip adresi
    değişir.
    - Pod lar yeniden doğduğu için içinde barındırdığı bilgiler silinir.
    - Bağlantılar var ise kaybolur.

        Bir pod DB olarak kullanılıyor ise içinde tuttuğu tüm bilgiler restart ettiğinde kaybolur. peki Çözüm nedir?
    her PC nin bir harddiski vardır ve içinde bulunur, ancak kubernetes yapısında harddisk olarak adlandırdığımız 
    bileleşenlere karşılık gelen Volume kavramı bir pod un içinden kubenetes cluster içine alınabilir. Böylece pod
    ayağa kalkarken kendisine tahsis edilen Volume a bağlanarak verilerini oradan çekebilir.

##    

    AA - ghp_1mejOFCl5f8Ih

    BB - x2LKo2mC2F8u

    CC - Hnywk4DDaYN


## BİR PROGRAMCI NELERİ ANADİLİ GİBİ BİLMEK ZORUNDA

    - OOP ve AOP
    - Data Structures 
    - Algorithms
    - Debugging
    - Java Stream API
    - Design Patterns

## KUBERNETES SORUNLAR - ÇÖZÜM ÖNERİLERİ

    - Temel Komutlar
    * kubectl get [KUBERNETES OBJESI] -> görüntülemek istediğiniz objelerin listesini getirir.
        - kubectl get pods
        - kubectl get deployments
        - kubectl get services
        - kubectl get nodes
        - kubectl get cronjobs
        - kubectl get jobs
        - kubectl get secrets
    ** kubectl get pods -o wide -> objenin daha detaylı bilgisini listeler
    * kubectl describe [KUBERNETES OBJESI] [OBJENIN ADI] -> objenin detayını getirir.
        - kubectl describe pods <POD ADI>
        - kubectl describe deployments <DEPLOYMENT ADI>
        - kubectl describe services <SERVICE ADI>
    * kubectl top [KUBERNETES OBJESI] -> objenin cpu ve memory bilgisini getirir.
        - kubectl top pods <POD ADI>
        - kubectl top nodes <DEPLOYMENT ADI>
    * kubectl logs [KUBERNETES OBJESI] [OBJENIN ADI] -> objenin loglarını getirir.
    DİKKAT!! bu kullanım log bilgisini tek seferlik o an için oluşmuş log ları verir.
        - kubectl logs pods <POD ADI>
    * kubectl logs -f [KUBERNETES OBJESI] [OBJENIN ADI] -> objenin loglarını anlık olarak takip etmek için kullanır.
    DİKKAT!!! bu kullanımda log lar sürekli izlendiği için çıkmak istediğiniz CTRL+C tuş kombinasyonunu kullanın.
    * kubectl delete [KUBERNETES OBJESI] [OBJENIN ADI] -> objeyi siler.
    - kubectl delete pods <POD ADI>
    - kubectl delete deployments <DEPLOYMENT ADI>
    - kubectl delete services <SERVICE ADI>
    DİKKAT!!!, pod lar eğer bir deployment objesine bağlı ise sadece pod silinir sonra aynı özellikte yeni bir 
    pod ayağa kalkar. Eğer tamamen podları silmek istiyorsanız o zaman bğlı bulunduğu deployment objesini silmek
    zorundasınız.
    
    ** Auth Micro Servisine ulaşamıyorum(ip:9094/swagger-ui/index.html). Connection Error hatası alıyorum.
    - pod ayakta mı reset felan atıyormu buna bakın.
    - kuberntes clusterı içinde olan bir pod a erişim servis objesi ile sağlanır bu nedenle servis objelerini listeleyin
    - dışarıdan erişim için External-IP gereklidir, bu nedenle ip adresini ve servis objesinin LoadBalancer olup 
    olmadığını kontrol edin.
    - servis objesinin iç yapısında bulunan ClusterIP bacağına pod ların bağlanmış olması gereklidir. Bu kontrol 
    etmelisiniz. Servis Objesine describe komutu ile bakın. Burada Endpoints şeklinde bir özellik olacak orada
    sizin pod larınızın ip bilgisinin burada olması gereklidir. Eğer burası boş ise sorun vardır.
    bu sorunun temel kaynağı deployment objesini oluştururken girdiğiniz "label" ile servis objesinin oluştururken
    girmiş olduğunuz "selector" bilgileri uyumsuz olduğu için bu sorun oluşabilir kontrol ederek düzeltiniz.
    - Etiketlerin doğruluğunu nasıl test edebiliriz? iki objeye de (POD, SERVICE) describe komutu ile detaylandırıp
    kontrol edebilirsiniz.
    - yukarıda oluşan sorunlar çözüldükten sonra bile hata hata alınıyor ise, sorun sisemseldir çözebilmek için
    servis ve depoylment objelerini de silip tekrar oluşturmalıyız.

