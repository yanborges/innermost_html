CC=javac

all: clean compile test 

compile:
	$(CC) HtmlAnalyzer.java

test:
	java HtmlAnalyzer https://www.axur.com/en-us/home
	java HtmlAnalyzer http://hiring.axreng.com/internship/example1.html
	java HtmlAnalyzer http://hiring.axreng.com/internship/example1.htm

clean:
	rm -f HtmlAnalyzer.class 
