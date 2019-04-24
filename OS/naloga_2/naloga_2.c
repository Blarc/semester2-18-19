#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/file.h>

#define SYS_CASE "sys"

// return a file descriptor
int openFile(char* path) 
{
	int fd;
	if ((fd = open(input, O_RDONLY)) < 0) {
		char error[100];
		strcpy(error, "Unable to open ");
		strcpy(error, path);
		perror(error);
	    exit(1);
	}

	return fd;
}

void sys()
{

	char* path = "/proc/cpuinfo";
	int fdCPU = openFile(path);


}

int main(int argc, char* argv[])
{
	char* input = argv[1];

	if (strcmp(input, "sys")) {

	}
	else if(strcmp(input, "pids")) {

	}
	else if(strcmp(input, "names")) {
		
	}
	else if(strcmp(input, "ps")) {
		
	}
	else if(strcmp(input, "psext")) {
		
	}
	else if(strcmp(input, "forktree")) {
		
	}
	else if(strcmp(input, "help")) {
		printf("%s\n", "help page");	
	}
	else {
		printf("%s\n", "Invalid");
	}
}