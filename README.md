### Features ๐

+ [x] Built-in config for development & production
+ [x] Built-in scala wrapped service
+ [x] Predefine project template with Result & Thrift for microservice

### Geting start ๐

โถ Run project:

```sh
mvn package
chmod +x runservice
./runservice start development
```

๐งช Test API:

```sh
curl -XGET localhost:8080/ping
```

๐ Stop project

```sh
./runservice stop
```

โ  Warning
+ In **MacOS** please install core utils `brew install coreutils` before execute runservice script

### Author

๐ [@tvc12](https://github.com/tvc12)
