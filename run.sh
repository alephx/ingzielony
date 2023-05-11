#!/bin/bash
JAVA_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED -Xms2048m -Xmx2048m"
java -jar $JAVA_OPTS target/ING-1.0.0.jar
