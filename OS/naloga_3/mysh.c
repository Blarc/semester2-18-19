#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <ctype.h>

#define MAX_INPUT 256
char name[64] = "myShell";
char* tokens[20];

int tokenize(char* line) {
	*tokens = tokens[20];
	char* tmp = line;
	int i = 0;

	while(*tmp != '\0') {

		while (isspace(*tmp)) {
			tmp += sizeof(char);
		}

		tokens[i] = tmp;

		while (!isspace(*tmp)) {
			tmp += sizeof(char);
		}

		*tmp = '\0';
		printf(": %s\n", tokens[i]);

		tmp += sizeof(char);
		i += 1;
	}
	printf("%d\n", i);
}

void printCharArr(char* line) {
	for (int i = 0; i < MAX_INPUT && line[i] != '\0'; i++) {
		printf("%c, ", line[i]);
	}
	printf("%c", '\n');
}

int readLine(char* line) {
	char c;
	int i = 0;
	while((c = getchar()) != '\n') {
		line[i] = c;
		i += 1;
	}
	line[i] = '\0';
}

int main()
{
	while(1) {
		printf("%s: ", name);
		char line[MAX_INPUT];
		readLine(line);
		// printCharArr(line);
		tokenize(line);

		//for (int i = 0; i < 5; i++) {
		//	printf("%s\n", tokens[i]);
		//}

	}
}