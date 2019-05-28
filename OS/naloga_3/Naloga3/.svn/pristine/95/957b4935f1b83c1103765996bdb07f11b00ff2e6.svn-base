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
#include <limits.h>
#include <dirent.h>

#define MAX_TOKENS 32
#define MAX_TOKEN_SIZE 16

char shell_name[64] = "myShell";
char** tokens;
int num_tokens, exit_status = 0;

// DIRECTORY FUNCTIONS

void dirlist() {
	char* dir_path = ".";
	if (tokens[1] != NULL) {
		dir_path = tokens[1];
	}
	DIR* dir = opendir(dir_path);

	struct dirent* data;
	while ((data = readdir(dir))) {
		printf("%s  ", data -> d_name);
	}
	printf("%s", "\n");

}

void dirremove() {
	if (rmdir(tokens[1]) == -1) {
			perror("error");
			exit_status = 1;
	} else {
			exit_status = 0;
	}
}

void dirmake() {
	struct stat st = {0};
	if (stat(tokens[1], &st) == -1) {
		if (mkdir(tokens[1], 0700) == -1) {
				perror("error");
				exit_status = 1;
		} else {
				exit_status = 0;
		}
	}
}

void dirwhere() {
	char cwd[PATH_MAX];
	if (getcwd(cwd, sizeof(cwd)) != NULL) {
			printf("%s\n", cwd);
	} else {
			perror("error");
			exit_status = 1;
	}
	exit_status = 0;
}

void dirchange() {
	int error;
	if (tokens[1] == NULL) {
		error = chdir("/");
	}
	else {
		error = chdir(tokens[1]);
	}

	if (error == -1) {
		perror("error");
		exit_status = -1;
	}
	else {
		exit_status = 0;
	}
}

// COMMAND FUNCTIONS

void my_ppid() {
	printf("%d\n", getppid());
	exit_status = 0;
}

void my_pid() {
	printf("%d\n", getpid());
	exit_status = 0;
}

void my_print() {
	for (int i = 1; i < num_tokens; i++) {
		printf("%s ", tokens[i]);
	}
	exit_status = 0;
}

void my_echo() {
	my_print();
	printf("%s", "\n");
	exit_status = 0;
}

void my_exit() {
	exit(atoi(tokens[1]));
}

void status() {
	printf("%d\n", exit_status);
	exit_status = 0;
}

void help() {
	printf("\n%s\n\n", "============================ HELP ============================");
	printf("%s\n", "name {shellName} ~ set shell's name");
	printf("%s\n", "status ~ prints last exit status");
	printf("%s\n", "exit {exitStatus} ~ exits the shell with {exitStatus}");
	printf("%s\n", "print {args} ~ prints {args} to stdout");
	printf("%s\n", "echo {args} ~ print with newline at the end");
	printf("%s\n", "pid ~ prints process's pid");
	printf("%s\n", "ppid ~ prints process's parent's pid");
	printf("\n%s\n\n", "================================================================");
	exit_status = 0;
}

void name() {
	if (tokens[1] == NULL) {
		printf("%s\n", shell_name);
	}
	else {
		strcpy(shell_name, tokens[1]);
	}
	exit_status = 0;
}

// UTILITY FUNCTIONS

int tokenize(char* line) {
	char* token;
	int i;
	free(tokens);
	tokens = malloc(MAX_TOKENS * sizeof(char*));

	token = strtok(line, " ");
	i = 0;
	while (token != NULL) {
		tokens[i] = malloc(MAX_TOKEN_SIZE * sizeof(char));
		strcpy(tokens[i], token);
		i += 1;
		token = strtok(NULL, " ");
	}

	// returns number of tokens
	return i;
}

int main()
{
	unsigned long max_input = MAX_INPUT;
	while(1) {
		char* line = malloc(MAX_INPUT * sizeof(char));
		printf("%s: ", shell_name);
		int num_chars = getline(&line, &max_input, stdin);
		line[num_chars-1] = '\0';
		num_tokens = tokenize(line);
		free(line);

		if (strcmp(tokens[0], "name") == 0) {
			name();
		}
		else if (strcmp(tokens[0], "help") == 0) {
			help();
		}
		else if (strcmp(tokens[0], "status") == 0) {
			status();
		}
		else if (strcmp(tokens[0], "exit") == 0) {
			my_exit();
		}
		else if (strcmp(tokens[0], "print") == 0) {
			my_print();
		}
		else if (strcmp(tokens[0], "pid") == 0) {
			my_pid();
		}
		else if (strcmp(tokens[0], "ppid") == 0) {
			my_ppid();
		}
		else if (strcmp(tokens[0], "dirchange") == 0) {
			dirchange();
		}
		else if (strcmp(tokens[0], "dirwhere") == 0) {
			dirwhere();
		}
		else if (strcmp(tokens[0], "dirmake") == 0) {
			dirmake();
		}
		else if (strcmp(tokens[0], "dirremove") == 0) {
			dirremove();
		}
		else if (strcmp(tokens[0], "dirlist") == 0) {
			dirlist();
		}
		else {
			printf("%s\n", "Unsupported command, sorry!");
		}
	}
}

