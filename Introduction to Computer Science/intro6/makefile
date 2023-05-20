a.out: main.o element.o dictionary.o
	gcc -std=c99 main.o element.o dictionary.o
main.o: main.c
	gcc -c -std=c99 main.c
element.o: element.c element.h
	gcc -c -std=c99 element.c
dictionary.o: dictionary.c dictionary.h
	gcc -c -std=c99 dictionary.c
clean:
	rm -f main.o element.o dictionary.o a.out
run: a.out
	./a.out