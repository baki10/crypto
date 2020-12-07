## Keytool commands:
### 1) Generating Key Pair:
```shell script
keytool -genkeypair -alias testkey \
        -keyalg RSA -keysize 2048 \
        -dname "CN=Baki, OU=BakiG, O=BakiG, L=Magnitka, ST=Chelyaba, C=MG" \
        -validity 365 \
        -storetype PKCS12 \
        -keystore testkey.jks \
        -storepass 123456
```
### 2) Export Certificate:
```shell script
keytool -exportcert -alias testkey \
        -keypass 123456 \
        -storetype PKCS12 \
        -keystore testkey.jks \
        -file exported_cert.cert \
        -rfc \
        -storepass 123456
```
### 3) Import Certificate:
```shell script
keytool -importcert -alias testkey \
        -keypass 123456 \
        -storetype PKCS12 \
        -keystore to_import.jks \
        -file exported_cert.cert \
        -rfc \
        -storepass 123456
```
### 4) List KeyStore Entries:
```shell script
keytool -list \
        -v \
        -storetype PKCS12 \
        -keystore to_import.jks \ 
        -storepass 123456
```
### 5) Delete KeyStore Entry:
```shell script
keytool -delete -alias testkey \
        -storetype PKCS12 \
        -keystore to_import.jks \
        -storepass 123456
```
### 6) Generate a Certificate Request:
A certificate request is a request for a certificate authority (CA) to create a public certificate for your organization. 
Once generated, the certificate request should be sent to the CA you want to create a certificate for you.
```shell script
keytool -certreq -alias testkey \
        -storetype PKCS12 \
        -keystore testkey.jks \
        -storepass 123456 \
        -file certreq.certreq
```
