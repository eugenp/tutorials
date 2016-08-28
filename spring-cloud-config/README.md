## Spring Cloud Config ##

To get this example working, you have to initialize a new *Git* repository in
the ```client-config``` directory first *and* you have to set the environment variable
```CONFIG_REPO``` to an absolute path of that directory.

```
$> cd client-config
$> git init
$> git add .
$> git commit -m 'Initial commit'
$> export CONFIG_REPO=$(pwd)
```

Then you're able to run the examples with ```mvn install spring-boot:run```.

### Docker ###

To get the *Docker* examples working, you have to repackage the ```spring-cloud-config-server```
and ```spring-cloud-config-client``` modules first:

```
$> mvn install spring-boot:repackage
```

Don't forget to download the *Java JCE* package from
(Oracle)[http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html].
