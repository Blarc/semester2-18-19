#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h>
#include <stdio.h>
#include <sys/file.h>
#include <sys/wait.h>

#define SLEEP 2


void forking(int i, int n, int* arr)
{
	int stat;

	if (i >= n) {
		return;
	}

	for (int j = 0; j < arr[i]; j++) {
		int pid = fork();
		if (pid == 0) {
			forking(i + j + 1, n, arr);
		}
		waitpid(pid, &stat, 0);
	}
}

void forking2(int n, int* arr)
{
	int stat;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < arr[i]; j++) {
			int pid = fork();
			if (pid == 0) {
				i += arr[i];
				j = 0;
				if (arr[i] < 1) {
					exit(0);
				}
			}
		}
	}
}

void forka(int i, int n, int* arr)
{
	if (arr[i] == 0 || i >= n) {
		return;
	}

	for (int j = 0; j < arr[i]; j++) {
		int pid = fork();
		if (pid == 0) {
			// printf("i + j + 1: %d\n", i + j + 1);
			// printf("array num: %d\n", arr[i] + i);
			forka(arr[i] + i + j, n, arr);
			sleep(SLEEP);
			exit(0);
		}
	}
}

int main()
{
	int mainPid = getpid();
	int pid1, pid2, pid3, pid4;
	// int arr[4] = {1, 5, 0, 3};
	int arr[7] = {2, 0, 3, 0, 1, 0, 3};

	pid1 = fork();
	if (pid1 != 0) {

		forka(0, 7, arr);
		sleep(SLEEP);
		exit(0);
	}

	char a[100];
	sprintf(a, "%d", mainPid);
	execlp("pstree", "pstree", "-c", a, NULL);
}