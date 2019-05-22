#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>

#define DIR "/tmp"
#define LOCK_FILE "lovec.lock"
#define LOG_FILE "lovec.log"

<<<<<<< HEAD
int energy = 12000;
=======
int energy = 42;
>>>>>>> 63f73365d14aa5462059c66d41d92b46471c0d32
char* out = "*";
char str[128];

void log_message(char* filename, char* message)
{
	FILE *logfile;
	logfile = fopen(filename, "a");
	if(!logfile)
		return;
	fprintf(logfile, "%s", message);
	fclose(logfile);
}

void handler(int signum)
{
	int childpid, exit_status;

	switch(signum)
	{
		case SIGTERM:
			energy += 10;
			sprintf(str, "Abrakadabra! Bonus energy! (%d).\n", energy);
<<<<<<< HEAD
			log_message(LOG_FILE, str);
=======
>>>>>>> 63f73365d14aa5462059c66d41d92b46471c0d32
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
				sprintf(str, "Forked child %d.\n", exit_status);
<<<<<<< HEAD
				log_message(LOG_FILE, str);
				sleep(energy % 7 + 1);
				sprintf(str, "Child exit with status %d.\n", exit_status);
				log_message(LOG_FILE, str);
=======
				sleep(energy % 7 + 1);
				sprintf(str, "Child exit with status %d.\n", exit_status);
>>>>>>> 63f73365d14aa5462059c66d41d92b46471c0d32
				exit(exit_status);
			}
			break;

		case SIGCHLD:
			sprintf(str, "Zombie caught with status %d.\n", exit_status);
<<<<<<< HEAD
			log_message(LOG_FILE, str);
=======
>>>>>>> 63f73365d14aa5462059c66d41d92b46471c0d32
			wait(NULL);
			break;
	}
}

void daemonize()
{
	int pid, lfp;

	if (getpid() == 1) {
		return;
	}

	pid = fork();

	if(pid < 0) {
		exit(1);
	}

	if(pid > 0) {
		exit(0);
	}

	setsid();

	for (pid = getdtablesize(); pid >= 0; --pid) {
		close(pid);
	}

	pid = open("/dev/null", O_RDWR);
	dup(pid);
	dup(pid);
	umask(027);

	chdir(DIR);

	lfp = open(LOCK_FILE, O_RDWR|O_CREAT, 0640);
	if (lfp < 0) {
		exit(1);
	}
	if (lockf(lfp, F_TLOCK, 0) < 0) {
		exit(1);
	}

	sprintf(str, "%d\n", getpid());
<<<<<<< HEAD
	printf("%s\n", str);
=======
>>>>>>> 63f73365d14aa5462059c66d41d92b46471c0d32
	write(lfp, str, strlen(str));

	signal(SIGTERM, handler);
	signal(SIGUSR1, handler);
	signal(SIGUSR2, handler);
	signal(SIGCHLD, handler);
}

int main()
{
<<<<<<< HEAD
	printf("%s\n", "Hello");
=======
>>>>>>> 63f73365d14aa5462059c66d41d92b46471c0d32
	daemonize();
	return 0;
}