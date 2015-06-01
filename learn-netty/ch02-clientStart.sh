#!/bin/bash

JDK_BIT=64
ID=ch02Client


CLASSPATH=./$ID:.
for file in `ls *.jar`
do
CLASSPATH=$CLASSPATH:./$file
done

for file in `ls ../dependency/*.jar`
do
CLASSPATH=$CLASSPATH:./$file
done

echo $CLASSPATH

if [ $JDK_BIT -eq 64 ] ; then
java -D$ID -Xmx256m -classpath $CLASSPATH -XX:-CMSConcurrentMTEnabled -XX:+UseConcMarkSweepGC ch02.EchoClient
else
java -D$ID -Xmx256m -classpath $CLASSPATH -XX:-CMSConcurrentMTEnabled -XX:+UseConcMarkSweepGC ch02.EchoClient
fi
