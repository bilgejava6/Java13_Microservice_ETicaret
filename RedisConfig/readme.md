# REDİS

    redis-cli --cluster create redis7001:7001 redis7002:7002 redis7003:7003 redis7004:7004 redis7005:7005 redis7006:7006 --cluster-replicas 1

    docker run --network redis-cluster-network --name redis7001 -d -p 7001:7001 redis7001
    docker run --network redis-cluster-network --name redis7002 -d -p 7002:7002 redis7002
    docker run --network redis-cluster-network --name redis7003 -d -p 7003:7003 redis7003
    docker run --network redis-cluster-network --name redis7004 -d -p 7004:7004 redis7004
    docker run --network redis-cluster-network --name redis7005 -d -p 7005:7005 redis7005
    docker run --network redis-cluster-network --name redis7006 -d -p 7006:7006 redis7006
    docker run --network redis-cluster-network --name redis-admin -d -p 6379:6379 redis
    docker run --network redis-cluster-network --name redis-gui -d -p 8001:8001 redislabs/redisinsight:latest

    docker run  --name redis-gui -d -p 8001:8001 redislabs/redisinsight:1.14.0



Evet, bu durum Redis küme kurulumunda sıkça karşılaşılan bir sorundur. Docker konteynerları varsayılan olarak kendi içlerinde izole bir ağda çalışırlar ve bu nedenle 127.0.0.1 (localhost) üzerinden birbirleriyle iletişim kuramazlar.

Bu sorunu çözmek için aşağıdaki adımları takip edebilirsiniz:

1. **Docker Ağı Oluşturun:**
    - Docker konteynerları arasında iletişimi sağlamak için bir Docker ağı oluşturun.
      ```bash
      docker network create redis-cluster-network
      ```

2. **Redis Konteynerlarını Aynı Ağa Ekleyin:**
    - Redis konteynerlarını oluştururken, özel ağı belirtin.
      ```bash
      docker run --name redis-node1 --network redis-cluster-network -p 7000:7000 -d redis redis-server --cluster-enabled yes --cluster-config-file nodes.conf --cluster-node-timeout 5000
      ```

      Yukarıdaki örnekte `--network redis-cluster-network` flag'i ile konteynerları aynı ağa eklemiş olduk.

3. **Diğer Redis Konteynerlarını da Ekleyin:**
    - Diğer Redis konteynerlarını da aynı ağa ekleyin, farklı port numaraları ve node adları ile.
      ```bash
      docker run --name redis-node2 --network redis-cluster-network -p 7001:7001 -d redis redis-server --cluster-enabled yes --cluster-config-file nodes.conf --cluster-node-timeout 5000
      ```

      Benzer şekilde diğer konteynerları da ekleyin.

4. **Redis Küme Konfigürasyonu:**
    - Redis küme konfigürasyon dosyalarında düğümler arasındaki IP adresleri doğru bir şekilde belirtilmiş olmalıdır. Örneğin, düğüm konfigürasyonlarında artık 127.0.0.1 yerine Docker ağının IP adresi veya düğümlerin isimlerini kullanabilirsiniz.

5. **Yeniden Başlatma:**
    - Redis konteynerlarını yeniden başlatın ve kümenin birleşip birleşmediğini kontrol edin.
      ```bash
      docker restart redis-node1 redis-node2
      ```

Bu adımları takip ettikten sonra Redis kümenizin oluşturulması ve birleşmesi gerekir. Docker ağını kullanarak konteynerlar arasında iletişim kurmak, bu tür senaryolarda oldukça yaygın bir çözümdür.    