CP = commons-logging-1.2.jar:pdfbox-1.8.9.jar:.

all:
	javac -classpath $(CP) org/apache/pdfbox/examples/pdmodel/*.java

run:
	java -classpath $(CP) org.apache.pdfbox.examples.pdmodel.CreateBookmarks in.pdf out.pdf
