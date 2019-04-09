#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv)
{	
	for (int i = 1; i < argc; i++) {
		int j = 0;
		while (argv[i][j] != '\0') {
			printf("%c", argv[i][j]);
			char* tmp = argv[i][j];
			write(1, tmp, 2);
			j++;
		}
	}
	printf("\n");
	
}