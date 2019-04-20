#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>


int main(int argc, char* argv[]) {

	// FILE *fopen( const char * filename, const char * mode );

	// ce vhod / izhod enak "-" se nesme izvest
	char* input = argv[1];
	char* output = argv[2];
	char* cmd = argv[3];
	char** args = argv + 3;

	int fd1;
	int fd2;

	if (strcmp("-", input) != 0) {
		// does not work ?
		fd1 = open(input, O_RDONLY);
		// make stdin
		dup2(fd1, 0);
	}

	if (strcmp("-", output) != 0) {
		fd2 = open(output, O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR);
		// make stdout go to file
		dup2(fd2, 1);
	}

	execvp(cmd, args);

	close(fd1);
	close(fd2);
}