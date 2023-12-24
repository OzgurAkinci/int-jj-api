# Getting Started

#### 1. generating java files with javaCC
```
$ javacc *.jj
```

#### 2. compile classes with jdk1.8
```
$ javac *.java
```

#### 3. run application with input.txt file
```
$ java Main < input.txt
```

#### 4. Matrisi Satır Basamak Formuna Dönüştürme Algoritması
```
https://www.math.purdue.edu/~shao92/documents/Algorithm%20REF.pdf

Adım 1. m × n matrisi A ile başlayın. Eğer A = 0 ise, Adım 7'ye geçin. 
Adım 2. En sola yerleşmiş sıfır olmayan bir sütunu belirleyin. 
Adım 3. Bu sütunun en üst pozisyonuna (bu pozisyonu pivot pozisyon olarak adlandırıyoruz) bir yerine getirmek için elementer satır işlemlerini kullanın. 
Adım 4. Pivot pozisyonun altına (kesinlikle) sıfır yerleştirmek için elementer satır işlemlerini kullanın. 
Adım 5. Pivot pozisyonunun altında (kesinlikle) sıfır olan başka satırlar yoksa, Adım 7'ye gidin. 
Adım 6. Pivot pozisyonunun altındaki satırlardan oluşan alt matrise Adım 2-5'i uygulayın. 
Adım 7. Elde edilen matris satır basamak formundadır.
Daha fazla işlem yapmak için aşağıdaki adımları takip edebilirsiniz, Satır Basamak Formunu Azaltma
Adım 8. Adım 7'de elde edilen satır basamak formundaki tüm öncül birleri belirleyin. 
Adım 9. Adım 8'deki öncül bir içeren en sağdaki sütunu belirleyin (bu sütunu pivot sütunu olarak adlandırıyoruz). 
Adım 10. Pivot sütunundaki öncül birin üzerindeki tüm sıfır olmayan girişleri silmek için tip III elementer satır işlemlerini kullanın. 
Adım 11. Pivot sütununun solunda başka öncül bir içeren sütun kalmadıysa, Adım 13'e gidin. 
Adım 12. Pivot sütunun solundaki sütunlardan oluşan alt matrise Adım 9-11'i uygulayın. 
Adım 13. Elde edilen matris azaltılmış satır basamak formundadır.

```

