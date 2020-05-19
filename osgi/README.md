## OSGi

This module contains articles about OSGi.

### Relevant articles:
 - [Introduction to OSGi](https://www.baeldung.com/osgi)

Info
---

com.baeldung.osgi
com.baeldung.osgi.sample.activator

Apache Felix
---


### Start

Download Apache Felix Framework Distribution 
from <https://felix.apache.org/downloads.cgi>
org.apache.felix.main.distribution-5.6.8 

No! The Apache Karaf container is best.
Download it from: <https://karaf.apache.org/download.html>

Download a binary distribution and unzip wherever you prefer.

Then run

    bin\karaf.bat start


Unzip, pay attention to the files not being clipped(!).

    system:exit 
    
exit!

    shutdown -h
 
or `^D`

### clean start

full clean, remove "data directory "

or...

    bin\karaf.bat clean

    bin\start.bat clean

### run mode

can be launched in

- the "regular" mode starts Apache Karaf in foreground, including the shell console.
- the "server" mode starts Apache Karaf in foreground, without the shell console.
- the "background" mode starts Apache Karaf in background.

### Logging

https://karaf.apache.org/manual/latest/#_log

can be logged to console


### Bundle deploy

    bundle:install mvn:com.baeldung/osgi-intro-sample-activator/1.0-SNAPSHOT
    
    install mvn:com.baeldung/osgi-intro-sample-service/1.0-SNAPSHOT
    install mvn:com.baeldung/osgi-intro-sample-client/1.0-SNAPSHOT

Eclipse's Equinox
====

Eclipse's OSGi platform
http://www.eclipse.org/equinox/

http://www.eclipse.org/equinox/documents/quickstart-framework.php

click on "download"

Latest Release
Oxygen.1 	Wed, 6 Sep 2017 -- 17:00 (-0400)

org.eclipse.osgi_3.12.1.v20170821-1548.jar

 = = NOT GOOD = = 



