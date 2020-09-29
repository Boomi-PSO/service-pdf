This fork added support for Base64 encoded PDF's: https://github.com/Boomi-PSO/service-pdf/commit/f322453c66a2bd8095fc123b3275c74024c0267c

Pdf Service
===========

This service allows you to generate and manipulate PDFs.

#### Building

To build the service, you will need to have Maven 3 and a Java 8 implementation installed.

You can build the runnable shaded JAR:

```bash
$ mvn clean package -DskipTests=true
```

### Configuration

This service require an account with amazon s3.
This service accept different ways of configuration.

#### Using a configuration file

Make a copy of src\main\resources\service.properties.dist in the same folder and remove the ".dist" replace the fields {{ fields }} with your own configuration
If you prefer is a properties file you can do the same but with the file service.properties

#### Using Environment Variables

You will need to create these variables s3.bucketName, s3.awsAccessKeyId and s3.awsSecretAccessKey 


#### Using maven for configure the deployment

mvn resources:resources -DbucketName=aaa -DawsAccessKeyId=bbb -DawsSecretAccessKey=ccc
mvn package

### Commands


##### Defaults

Running the following command will start the service listening on `0.0.0.0:8080/api/pdf/1`:

```bash
$ java -jar target/demo-1.0-SNAPSHOT.jar
```

##### Custom Port

You can specify a custom port to run the service on by passing the `server.port` property when running the JAR. The
following command will start the service listening on port 9090 (`0.0.0.0:9090/api/pdf/1`):

```bash
$ java -Dserver.port=9090 -jar target/demo-1.0-SNAPSHOT.jar
```


## Contributing

Contribution are welcome to the project - whether they are feature requests, improvements or bug fixes! Refer to 
[CONTRIBUTING.md](CONTRIBUTING.md) for our contribution requirements.

## License

This service is released under the [MIT License](http://opensource.org/licenses/mit-license.php).
