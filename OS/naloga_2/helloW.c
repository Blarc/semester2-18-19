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
	if (i >= n) {
		return;
	}

	for (int j = 0; j < arr[i]; j++) {
		int pid = fork();
		if (pid == 0) {
			sleep(SLEEP);
			exit(0);
		}

		if (i+j < n && arr[i+j] > 0) {
			forking(i+arr[i+j], n, arr);
		}
	}

	/* for (int j = 0; j < arr[i]; j++) {
		int pid = fork();
		if (pid == 0 && i+j < n && arr[i+j] > 0) {

			printf("%d %d\n", i, arr[i+j]);

			forking(i+arr[i], n, arr);

			sleep(SLEEP);
			exit(0);
		}
		exit(0);
	}
	*/
}

int main()
{
	int mainPid = getpid();
	int pid1, pid2, pid3, pid4;
	int arr[4] = {1, 5, 0, 3};

	pid1 = fork();
	if (pid1 != 0) {

		/*pid2 = fork();
		if (pid2 == 0) {

			pid3 = fork();
			if (pid3 == 0) {
				sleep(SLEEP);
				exit(0);
			}

			pid3 = fork();
			if (pid3 == 0) {

				for (int i = 0; i < 3; i++) {	
					pid4 = fork();
					if (pid4 == 0) {
						sleep(SLEEP);
						exit(0);
					}
				}

				sleep(SLEEP);
				exit(0);
			}

			pid3 = fork();
			if (pid3 == 0) {
				sleep(SLEEP);
				exit(0);
			}

			pid3 = fork();
			if (pid3 == 0) {
				sleep(SLEEP);
				exit(0);
			}

			pid3 = fork();
			if (pid3 == 0) {
				sleep(SLEEP);
				exit(0);
			}

			sleep(SLEEP);
			exit(0);
		}
		*/

		forking(0, 4, arr);

		sleep(SLEEP);
		exit(0);
	}

	char a[100];
	sprintf(a, "%d", mainPid);
	execlp("pstree", "pstree", "-c", a, NULL);
}