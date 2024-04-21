## Test running HTTPS with Local Self-Signed-SSL-Certificate-Authority with Android Emulator [Demo Local NodeJS server in MacOS]

### I. Prerequisites
1. Macbook (I have not tested this project on Linux).
2. Node JS Environment.
3. Android Emulator.


### II. Generate server certificate for NodeJS.
1. Run the following command to generate a private key for the custom server-certificate:
   
```
openssl genrsa -out [KeyName].key 2048
```
For example: **openssl genrsa -out custom_cer.key 2048**

2. Now, generate the self-signed server certificate using the private key:

```
openssl req -x509 -new -nodes -key [KeyName].key -sha256 -days 365 -out [CertificateName].crt
```

For example: **openssl req -x509 -new -nodes -key custom_cer.key -sha256 -days 365 -out custom_cer.crt**


After running both commands, it will generate two files:

<img width="393" alt="Screenshot 2024-04-21 at 16 23 57" src="https://github.com/caosytrung/AndriodSelfCertificateAuthority/assets/17381611/f9f0739d-d55b-483f-ac55-bcf40fbe4dbb">



Place both files into the DemoLocalNodeJsServer in the NodeJS Project: 

<img width="409" alt="Screenshot 2024-04-21 at 16 54 44" src="https://github.com/caosytrung/AndriodSelfCertificateAuthority/assets/17381611/02b0a61a-7221-47c9-8538-6d2f19325210">




### III. Seting up NodeJS server
You guys can follow my plain example:

app.js
~~~js
const express = require('express');
const https = require('https');
const fs = require('fs');

const app = express();
app.get('/hello', (req, res) => {
    res.setHeader('Content-Type', 'application/json');
    res.status(200).end(JSON.stringify({ hello: 'Hello My Fen', }));
});

const options = {
    key: fs.readFileSync(__dirname + '/custom_cer.key'), // replace it with your key name
    cert: fs.readFileSync(__dirname + '/custom_cer.crt'), // replace it with your certificate name
}

https.createServer(options, app).listen(443);
~~~

Install packages:

```
npm install 
```

Run these command to start nodejs server that serve GET (/hello):
```
node app.js
```

### Configure your Android Project

1. Copy the server self-certificate (custom_cer.crt) to the raw folder of your Android project: 

<img width="354" alt="Screenshot 2024-04-21 at 16 39 31" src="https://github.com/caosytrung/AndriodSelfCertificateAuthority/assets/17381611/7b1ffd2f-7bb4-4868-8c9c-9cbfbdb1434c">


2. Create **network_security_config.xml** like this:

```
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">localhost</domain>
        <domain includeSubdomains="true">10.0.2.2</domain>
        // example <certificates src="@raw/xxx.crt" />
        <trust-anchors>
            <certificates src="@raw/custom_cer" />
        </trust-anchors>
    </domain-config>
</network-security-config>
```

3. Open **AndroidManifest.xml**, add this line: 
```
<application
        android:networkSecurityConfig="@xml/network_security_config"
```

4. In case you're using Android Emulator, please use **10.0.2.2** instead of **localhost** to call local API.

5. Call API and test your works.

### Test Android Certificate spinning by using public key.

*Normally, an app trusts all pre-installed CAs. If any of these CAs were to issue a fraudulent certificate, the app would be at risk from an on-path attacker. Some apps choose to limit the set of certificates they accept by either limiting the set of CAs they trust or by certificate pinning.*

1. Generate public key
```
 openssl x509 -in [CerName].crt -pubkey -noout | openssl pkey -pubin -outform der | openssl dgst -sha256 -binary | openssl enc -base64
```

Example: openssl x509 -in custom_cer.crt -pubkey -noout | openssl pkey -pubin -outform der | openssl dgst -sha256 -binary | openssl enc -base64

2. Copy the sha256 output to  **network_security_config.xml**:

~~~xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">localhost</domain>
        <domain includeSubdomains="true">10.0.2.2</domain>
        <pin-set expiration="2025-01-01">
            <pin digest="SHA-256">[SHA256-OUTPUT]</pin>
        </pin-set>
        
        <trust-anchors>
            <certificates src="@raw/custom_cer" />
        </trust-anchors>
    </domain-config>
</network-security-config>
~~~

After adding this `pin-set`, The API should work as usual. When modifying the SHA256 Key, The API should throw error related SSL.



