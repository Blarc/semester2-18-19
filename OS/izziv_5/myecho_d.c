#include <stdio.h>
#include <sys/syscall.h>
#include <unistd.h>

int mywrite(int i, char* w, int len) {
	syscall(SYS_write, i, w, len);
}

int main(int argc, char** argv)
{	
	for (int i = 1; i < argc; i++) {
		int j = 0;
		while (argv[i][j] != '\0') {	
			mywrite(1, &argv[i][j], 1);
			j++;
		}
		mywrite(1, " ", 1);
	}
	mywrite(1, "\n", 1);
	
}