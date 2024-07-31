#!/usr/bin/perl

$jar = "/tmp/jmxterm-1.0.4-uber.jar";

$host = "localhost";
$port = 11234;

$mbean = $ARGV[0] || "com.baeldung.jxmshell:name=calculator,type=basic";
$operation = $ARGV[1] || "sum";

open JMX, "| java -jar $jar -n";

print JMX "open $host:$port\n";
$attribute_value = $ARGV[2];
if (defined $attribute_value) {
    print JMX "set -b ${mbean} ${operation} ${attribute_value}\n";
} else {
    print JMX "run -b ${mbean} ${operation}\n";
}
print JMX "close\n";

close JMX;
