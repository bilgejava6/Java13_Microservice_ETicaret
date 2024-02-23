# KOMUT SETLERİ

    kubectl - cli aracı

## get komutu - kubernetes objelerinin bilgilerini çeker

    kubectl get nodes -> çalışan asıl sanal PC miz 
    kubectl get pods -> nodes lar üzerinde çalıştırılan sanal küçük containerlar
    kubectl get deployments
    kubectl get services
    kubectl get cronjob
    kubectl get secrets

    NOT!!!! bilgileri daha kapsamlı almak için -o wide eklemelisiniz
    kubectl get nodes -o wide
 
## Pod nedir? ne işe yarar.

    kubernetes içinde çalışabilir en küçük objemiz, oluşturmak için create komutunu kullanabiliriz
    ancak bu yöntem hantal ve kullanışsızdır. Bu nedenle daha kullanışlı olan deployment objesini 
    kullanarak bunu yönetme yoluna gideriz.
    > kubectl run pod-autservice --image=javaboost2/auth-service:v.0.1
    NOT!!! çalışmayan yada gereksiz olduğunu düşündüğünüz podları silmek istiyorsanız,
    > kubectl delete pods [POD_NAME] yazmanız yeterlidir.

## Deployment objesi

    podların durumarını gözlemleyen, verilen emitler doğrultusunda podları oluşturann, güncelleme
    gerekli olduğunda bu güncellemeleri sisteme zarar vermeden ve aksatmadan güncellenmesini 
    sağlayan k8s(k ubernete s) objesidir.

    kubectl ile bir deploymeny objesi create edebilisiniz, ancak doğru yöntem bir yml dosyası
    kullanarak bunu yapmaktır.

### DİKKAT!!!! bir yml doasyasını deploy etmek

    kubectl apply -f [YML_DOSYA ADI]
