#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/wait.h>

int energy = 42;
char* out = "*";

void handler(int signum)
{
	int childpid, exit_status;

	switch(signum)
	{
		case SIGTERM:
			energy += 10;
			printf("Abrakadabra! Bonus energy! (%d).\n", energy);
			break;

		case SIGUSR1:
			if (out[0] == '*') {
				out = ".";
			}
			else {
				out = "*";
			}
			break;

		case SIGUSR2:
			childpid = fork();
			if (childpid == 0) {
				exit_status = (energy * 42) % 128;
				printf("Forked child %d.\n", exit_status);
				sleep(energy % 7 + 1);
				printf("Child exit with status %d.\n", exit_status);
				exit(exit_status);
			}
			break;

		case SIGCHLD:
			printf("Zombie caught with status %d.\n", exit_status);
			wait(NULL);
			break;
	}
}

int main(int argc, char* argv[]) 
{
	int pid;

	pid = getpid();
	if (argc > 1) {
		energy = atoi(argv[1]);
	}

	printf("PID: %d\n", pid);
	printf("Energy: %d\n", energy);

	signal(SIGTERM, handler);
	signal(SIGUSR1, handler);
	signal(SIGUSR2, handler);
	signal(SIGCHLD, handler);

	while(energy > 0) {
		fflush(stdout);
		sleep(1);
		printf("%s", out);
		energy -= 1;
	}
}
