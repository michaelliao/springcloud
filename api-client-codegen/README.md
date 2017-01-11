# api client codegen

A simple feign client codegen tool based on swagger schema.

Build jar:

```
mvn clean package
```

Usage:

```
java -jar ./target/api-client-codegen-0.0.1-SNAPSHOT.jar --name=<name> --url=<path/to/api-docs> --output=<outputpath>
```

For example, name: account, url: http://localhost:9002/v2/api-docs

```
java -jar ./target/api-client-codegen-0.0.1-SNAPSHOT.jar --name=account --url=http://localhost:9002/v2/api-docs --output=../petstore-clients
```

will generate client 'account' as Maven project at './account-client'.
