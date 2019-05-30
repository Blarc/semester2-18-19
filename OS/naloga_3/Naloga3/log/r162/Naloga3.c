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
#include <errno.h>

#define MAX_TOKENS 32
#define MAX_TOKEN_SIZE 16

char shell_name[64] = "mysh";
char* tokens[MAX_TOKENS];
char* line;
int num_tokens;
int exit_status = 0;

// FILE FUNCTIONS

void my_rename() {
	if (num_tokens > 2) {
		if (rename(tokens[1], tokens[2]) != 0) {
			printf("rename: %s\n", strerror(errno));
		}
	}
	exit_status = errno;
}

// DIRECTORY FUNCTIONS

void dirlist() {
	char* dir_path = "./";
	if (num_tokens > 1) {
		dir_path = tokens[1];
	}

	DIR* dir;
	if ((dir = opendir(dir_path)) == NULL) {
		printf("dirlist: %s\n", strerror(errno));
		exit_status = errno;
		return;
	}

	struct dirent* data;
	while ((data = readdir(dir))) {
		printf("%s  ", data -> d_name);
	}
	printf("%s", "\n");

	exit_status = errno;
}

void dirremove() {
	if (rmdir(tokens[1]) == -1) {
			printf("dirremove: %s\n", strerror(errno));
	}
	exit_status = errno;
}

void dirmake() {
	if (mkdir(tokens[1], 0700) != 0) {
			printf("dirmake: %s\n", strerror(errno));
	}
	exit_status = errno;
}

void dirwhere() {
	char cwd[PATH_MAX];
	if (getcwd(cwd, sizeof(cwd)) != NULL) {
			printf("%s\n", cwd);
	} else {
			printf("dirwhere: %s\n", strerror(errno));
			exit_status = 1;
	}
	exit_status = 0;
}

void dirchange() {
	if (num_tokens < 2) {
		chdir("/");
	}
	else {
		chdir(tokens[1]);
	}
	if (exit_status != 0) {
		printf("dirchange: %s\n", strerror(errno));
	}
	exit_status = errno;
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
	int i;
	for (i = 1; i < num_tokens - 1; i++) {
		printf("%s ", tokens[i]);
	}
	printf("%s", tokens[i]);
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
	if (num_tokens < 2) {
		printf("%s\n", shell_name);
	}
	else {
		strcpy(shell_name, tokens[1]);
	}
	exit_status = 0;
}

// UTILITY FUNCTIONS

void tokenize() {

	num_tokens = 0;
	int i = 0;
	while(*line != '\n') {
		if (isspace(*line) != 0) {
			line += 1;
		}
		else if (*line == '"') {
			line += 1;
			tokens[i] = line;
			i += 1;
			while (*line != '"') {
				line += 1;
			}
			*line = '\0';
			line += 1;
		}
		else {
			tokens[i] = line;
			i += 1;
			while (isspace(*line) == 0) {
				line += 1;
			}
			// we got to the end of line
			if (*line == '\n') {
				*line = '\0';
				break;
			}
			*line = '\0';
			line += 1;
		}
	}
	num_tokens = i;
}

int main()
{
	unsigned long max_input = 128;
	dup2(1, 2);

	while(1) {
		if (isatty(0) == 1) {
			printf("%s> ", shell_name);
		}
		getline(&line, &max_input, stdin);
		tokenize();

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
		else if (strcmp(tokens[0], "echo") == 0) {
			my_echo();
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
		else if (strcmp(tokens[0], "rename") == 0) {
			my_rename();
		}
	}
}
