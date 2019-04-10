#include <errno.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <dirent.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv) {

	char path[255];

	if (argc < 2) {
	    strcat(path, "./");
		DIR* odir = opendir(path);
		struct dirent* data;
		while (data = readdir(odir)) {
			printf("%s\n", data -> d_name);
		}
		exit(0);
	}

	for (int i = 1; i < argc; i++) {
		strcat(path, argv[i]);
		strcat(path, "/");
	}

	DIR* odir = opendir(path);
	if (errno > 0) {
		perror("Error");
		exit(1);
	}
	struct dirent* data;
	while (data = readdir(odir)) {
		printf("%s\n", data -> d_name);
	}
	exit(0);

}