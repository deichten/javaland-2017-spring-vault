# Spring Cloud Config Server Example
This example will demo you the benefits as well as shortcomings of spring cloud config server. In any case you'll have to start the configserver first and give it some time to start up before starting any of the dependent services.

You do so by opening up a terminal, navigating to the appropriate folder and start the service by calling `./mvnw spring-boot:run` 

Note, that your config server runs on [http://localhost:8888](), service A on [http://localhost:8080]() and service B on [http://localhost:8081](). All services do have the actuator installed, enabled and are accessible without any authentication. So basically every service should answer to `/env`, `/health` or `/metrics` endpoints. 

Navigating to [http://localhost:8888]() should show you an overview over the configure JSON

```javascript
{
  "profiles": [
    "native"
  ],
  "server.ports": {
    "local.server.port": 8888
  },
  "servletContextInitParams": {},
  "systemProperties": {
    "java.runtime.name": "Java(TM) SE Runtime Environment",
    "sun.boot.library.path": "/Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home/jre/lib",
    "java.vm.version": "25.121-b13",
    "gopherProxySet": "false",
    "java.vm.vendor": "Oracle Corporation",
    "java.vendor.url": "http://java.oracle.com/",
    "path.separator": ":",
    "java.vm.name": "Java HotSpot(TM) 64-Bit Server VM",
    …
```

If you now navigate to [http://localhost:8888/application-default.json]() you should see the configured values for all service you'll be running:

```javascript
{
  "foo": {
    "bar": "lsmf"
  },
  "management": {
    "security": {
      "enabled": false
    }
  }
}
```

And navigating to [http://localhost:8888/serviceA-default.json]() shows you its configuration:

```javascript
{
  "foo": {
    "bar": "lsmf",
    "baz": "5744be6aa2a9641fac133ff3aaf150372665d599a72dc8a6c77fab079e1a6a57"
  },
  "management": {
    "security": {
      "enabled": false
    }
  },
  "server": {
    "port": 8080
  },
  "spring": {
    "datasource": {
      "password": "toor",
      "url": "mysql://localhost/database",
      "username": "root"
    }
  }
}
```

You'll note that `foo.bar=lsmf` and `management.security.enabled=false` are inherited application-wide values. The second thing to note here is that `foo.baz` seems to show only gibberish data. This is due to the fact that it is an encrpyted value in the original file 

```
foo:
  baz: "{cipher}5744be6aa2a9641fac133ff3aaf150372665d599a72dc8a6c77fab079e1a6a57" 
```
You'll only be able to show the correct value by passing in the right encrpytion key. In this case this is a symmetric encryption using a shared secret which shouldn't be used in production.

Pleas go to your running instance of config server in the Terminal and terminate the execution to restart it afterwards passing in the encrpytion key by starting again

```
ENCRYPT_KEY=12345 ./mvnw spring-boot:run
```

After the service succesfully came back you'll be able to retrieve the unencrypted value of `foo.baz` with a value of `very-secret`. At the same time you'll also see that if you retrieve [http://localhost:8888/serviceB-default.json]() it contains:

```javascript
  "invalid": {
    "foo": {
      "bay": "<n/a>"
    }
   },
```
Which indicated that the cipher you passed is not correct. If you also want to check this value accordingly, go back to your config server, stop it and restart it with the `ENCRPYT_KEY=54321`.

**NOTE:** This shortcoming is only while using symmetric encryption cause there can be only one key. If you're using PKI you can have values encrypted using multiple keys, but this doesn't solve the issue of missing ACLs allowing you to retrieve all values stored in a config server.