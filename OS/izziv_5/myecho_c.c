#include <unistd.h>
#include <stdio.h>
#include <sys/syscall.h>

int main(int argc, char** argv)
{	
	for (int i = 1; i < argc; i++) {
		int j = 0;
		while (argv[i][j] != '\0') {	
			// write(1, &argv[i][j], 1);
			syscall(SYS_write, 1, &argv[i][j], 1);
			j++;
		}
		// write(1, " ", 1);
		syscall(SYS_write, 1, " ", 1);
	}
	// write(1, "\n", 1);
	syscall(SYS_write, 1, "\n", 1);
	
}