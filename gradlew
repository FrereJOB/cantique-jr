#!/bin/sh
# Télécharge automatiquement la bonne version
curl -s https://raw.githubusercontent.com/gradle/gradle/master/gradlew -o gradlew.tmp
mv gradlew.tmp gradlew
chmod +x gradlew
./gradlew "$@"