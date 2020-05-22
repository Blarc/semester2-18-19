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
char** tokens;

int tokenize(char* line) {
    free(tokens);
    tokens = malloc(20 * sizeof(char*));
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

        tmp += sizeof(char);
        i += 1;
    }
    return i;
}

int main()
{
    unsigned long max_input = 256;
    while(1) {
        char* line = malloc(MAX_INPUT * sizeof(char));
        printf("%s: ", name);
        getline(&line, &max_input, stdin);
        printf("%s\n", line);
        int num_tokens = tokenize(line);
        free(line);

        printf("%d\n", num_tokens);
        for (int i = 0; i < num_tokens; i++) {
            printf("%s\n", tokens[i]);
        }
        printf("%s\n", tokens[0]);

    }
}