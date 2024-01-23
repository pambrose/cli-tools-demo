default: versioncheck

stop:
	./gradlew --stop

clean:
	./gradlew clean

compile:
	./gradlew build -xtest

build: compile

versioncheck:
	./gradlew dependencyUpdates

depends:
	./gradlew dependencies

jars: clean
	./gradlew kotter clikt combo

upgrade-wrapper:
	./gradlew wrapper --gradle-version=8.5 --distribution-type=bin
