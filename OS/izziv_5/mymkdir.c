#include <errno.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv) {

	if (argc < 2) {
		write(1, "No name specified!\n", 19);
		exit(1);
	}

	char path[255];
	strcat(path, "./");

	for (int i = 1; i < argc-1; i++) {
		strcat(path, argv[i]);
		strcat(path, "/");
	}

	strcat(path, argv[argc-1]);

	mkdir(path, 0700);
	if (errno > 0) {
		perror("Error");
	}
}