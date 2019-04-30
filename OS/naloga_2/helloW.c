#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h>
#include <stdio.h>
#include <sys/file.h>
#include <sys/wait.h>

int main()
{
	int mainPid = getpid();

	int pid = fork();
	if (pid == 0) {

		int pid2 = fork();
		if (pid2 == 0) {

			int pid3 = fork();
			if (pid3 == 0) {
				sleep(2);
				exit(0);
			}

			sleep(2);
			exit(0);
		}

		sleep(2);
		exit(0);
	}

	pid = fork();
	if (pid == 0) {
		sleep(2);
		exit(0);
	}

	pid = fork();
	if (pid == 0) {
		sleep(2);
		exit(0);
	}

		char a[100];
		sprintf(a, "%d", mainPid);
		execlp("pstree", "pstree", "-c", a, NULL);
}