all: build

build:
	javac src/*.java
	cd src; jar cfe Automat.jar Main *.class; mv Automat.jar ..
	@echo 'Fertig!'
	@echo 'Zum Ausführen: java -jar Automat.jar [<args>]'

help:
	@echo "Usage: make <commands>\n"
	@echo "The commands are:"
	@echo "	all         Default target, runs build"
	@echo "	build       Compiles and packs the .jar."
	@echo "	help        Prints this help."
	@echo "	list        Lists all source files."
	@echo "	install     Installs the .jar on the system. (Only Mac OS)"
	@echo "	run         Runs the .jar."
	@echo "	clean       Deletes the .jar and the binaries.\n"
list:
	@cd src; \
	ls *.java

install:
	mv Automat.jar /Applications

run:
	java -jar Automat.jar

clean:
	rm -f src/*.class
	rm -f Automat.jar
