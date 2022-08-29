### Features 👏

+ [x] Built-in config for development & production
+ [x] Built-in scala wrapped service
+ [x] Predefine project template with Result & Thrift for microservice

### Geting start 👍

▶ Run project:

```sh
mvn package
chmod +x runservice
./runservice start development
```

🧪 Test API:

```sh
curl -XGET localhost:8080/ping
```

🛑 Stop project

```sh
./runservice stop
```

⚠ Warning
+ In **MacOS** please install core utils `brew install coreutils` before execute runservice script

### Author

👉 [@tvc12](https://github.com/tvc12)
