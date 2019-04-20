#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/file.h>

#define BUFSIZE 1024


int main(int argc, char* argv[]) {

	char buffer [BUFSIZE];
	char* input = argv[1];
	char* output = argv[2];

	int fd1;
	int fd2;
	int length;

	if (strcmp("-", input) != 0) {

		if ((fd1 = open(input, O_RDONLY)) < 0) {
			perror("Unable to open input");
		    exit(1);
		}

		flock(fd1, LOCK_SH);
		dup2(fd1, 0);
	}

	if (strcmp("-", output) != 0) {
		if ((fd2 = open(output, O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR)) < 0) {
			perror("Unable to open output");
		    exit(1);
		}

		flock(fd2, LOCK_EX);
		dup2(fd2, 1);
	}

	while ((length = read(fd1, buffer, BUFSIZE)) > 0) {
		write(1, buffer, length);
	}

	flock(fd1, LOCK_UN);
	flock(fd2, LOCK_UN);

	close(fd1);
	close(fd2);

	exit(0);
}