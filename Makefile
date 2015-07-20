CP = commons-logging-1.2.jar:pdfbox-1.8.9.jar:.

all:
	javac -classpath $(CP) -Xlint:unchecked *.java

run:
	java -classpath $(CP) TOC toc.txt in.pdf out.pdf
