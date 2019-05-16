#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h>
#include <stdio.h>
#include <sys/file.h>
#include <sys/wait.h>

#define BUFFER_LEN 1024
char* DIR_PATH;
char OS_PATH[BUFFER_LEN];
char SWAP_PATH[BUFFER_LEN];
char MODULES_PATH[BUFFER_LEN];
int PIDS_LEN = 8;

struct direntC
{
	int pid;
	int ppid;
	char* processName;
	char state;	
	int threads;
	int file;	
};


// return a file descriptor
int openFile(char* path) 
{
	int fd;
	if ((fd = open(path, O_RDONLY)) < 0) {
		char error[100];
		strcpy(error, "Unable to open ");
		strcpy(error, path);
		perror(error);
	    exit(1);
	}

	return fd;
}

void readFile(int fd, char* buffer, int n)
{
	if (read(fd, buffer, n) < 0) {
		write(2, "An error occurred while reading a char.\n", 41);
		exit(1);
	}
}

char* readWord(char* data, char* buffer)
{
	int iter = 0;
	while(data[iter] == ' ' || data[iter]  == '\n') {
		iter += 1;
	}

	int index = 0;
	// printf("%s\n", data);
	while(data[iter] != ' ' && data[iter] != '\n' && data[iter] != '\0') {
		buffer[index] = data[iter];
		index += 1;
		iter += 1;
	}

	buffer[index] = '\0';
	//printf("%s\n", buffer);
	return &data[iter];
}

int lineCount(char* data)
{
	int count = 0;
	int i = 0;

	while (data[i] != '\0') {
		if (data[i] == '\n') {
			count += 1;
		}
		i += 1;
	}

	return count;
}

void bubbleSortNum(struct direntC arr[], int n) {
	int i, j, a, b;
	for (i = 0; i < n-1; i++) {
		for (j = 0; j < n-i-1; j++) {

			a = arr[j].pid;
			b = arr[j+1].pid;

			if (a > b) {
				struct direntC tmp = arr[j];
				arr[j] = arr[j+1];
				arr[j+1] = tmp;
			}
		}
	}
}

int charToLow(char a) {
	if ('A' <= a && a <= 'Z') {
		a += 32;
	}
	return a;
}

// -1; a smaller
//  0; equal
//  1; b smaller
int wordCompare(char* a, char* b)
{
	int i = 0, c, d;
	while (a[i] != '\0' && b[i] != '\0') {

		c = charToLow(a[i]);
		d = charToLow(b[i]);

		if (c < d) {
			return -1;
		}
		else if (d < c) {
			return 1;
		}
		else {
			i += 1;
		}
	}

	if (a[i] == '\0' && b[i] == '\0') {
		return 0;
	}
	else if (a[i] == '\0') {
		return -1;
	}
	else {
		return 1;
	}
}

void bubbleSortAlph(struct direntC arr[], int n) {
	int i, j, a, b, tmp;
	char* e, *f;
	for (i = 0; i < n-1; i++) {
		for (j = 0; j < n-i-1; j++) {

			e = arr[j].processName;
			f = arr[j+1].processName;

			tmp = wordCompare(e, f);

			if (tmp == 1) {
				struct direntC tmp = arr[j];
				arr[j] = arr[j+1];
				arr[j+1] = tmp;
			}
			else if (tmp == 0) {
				a = arr[j].pid;
				b = arr[j+1].pid;

				if (a > b) {
					struct direntC tmp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = tmp;
				}
			}
		}
	}
}

void sys()
{
	char data[4096];
	char buffer[1024];
	char* pointer;
	int lc;
	int fd;

	fd = openFile(OS_PATH);
	readFile(fd, data, 1024);

	pointer = strstr(data, "Linux version");
	pointer = readWord(pointer, buffer);
	printf("%s: ", buffer);
	pointer = readWord(pointer, buffer);
	pointer = readWord(pointer, buffer);
	printf("%s\n", buffer);

	pointer = strstr(data, "gcc version");
	pointer = readWord(pointer, buffer);
	printf("%s: ", buffer);
	pointer = readWord(pointer, buffer);
	pointer = readWord(pointer, buffer);
	printf("%s\n", buffer);

	close(fd);
	fd = openFile(SWAP_PATH);
	readFile(fd, data, 1024);

	pointer = strstr(data, "\n");
	pointer = readWord(pointer, buffer);
	printf("Swap: %s\n", buffer);

	close(fd);
	fd = openFile(MODULES_PATH);
	readFile(fd, data, 4096);
	close(fd);
	lc = lineCount(data);
	printf("Modules: %d\n", lc);

}


void pids()
{
	char* name;
	int num;
	int index = 0;
	struct dirent* data;
	struct direntC dataA;
	DIR* dir;
	struct direntC arr[PIDS_LEN];

	dir = opendir(DIR_PATH);

	while (data = readdir(dir)) {
		name = data -> d_name;
		num = atoi(name);

		if (num != 0 || name[0] == '0') {
			dataA.pid = num;
			arr[index] = dataA;
			index += 1;
		}
	}

	closedir(dir);

	bubbleSortNum(arr, PIDS_LEN);

	for (int i = 0; i < PIDS_LEN; i++) {
		printf("%d\n", arr[i].pid);
	}
}


void names()
{

	int num, fd;
	int index = 0;

	char* name;
	char processNamePathOrg[BUFFER_LEN];
	char processNamePath[BUFFER_LEN];
	char* processName;

	struct dirent* data;
	struct direntC dataA;

	DIR* dir;

	struct direntC arr[PIDS_LEN];

	dir = opendir(DIR_PATH);
	strcpy(processNamePathOrg, DIR_PATH);
	strcat(processNamePathOrg, "/");

	while (data = readdir(dir)) {
		name = data -> d_name;
		num = atoi(name);
		strcpy(processNamePath, processNamePathOrg);

		if (num != 0 || name[0] == '0') {

			strcat(processNamePath, name);
			strcat(processNamePath, "/comm");

			processName = malloc(BUFFER_LEN * sizeof(char));
			fd = openFile(processNamePath);
			readFile(fd, processName, BUFFER_LEN);
			close(fd);

			dataA.pid = num;
			dataA.processName = processName;

			arr[index] = dataA;
			index += 1;
		}
	}

	closedir(dir);

	bubbleSortAlph(arr, PIDS_LEN);

	for (int i = 0; i < PIDS_LEN; i++) {
		printf("%d %s", arr[i].pid, arr[i].processName);
	}
}


// returns PPID 
int parse_ppid(int pid) {
	int fd;
	char path[BUFFER_LEN], pidBuffer[BUFFER_LEN];
	char* stats = malloc(BUFFER_LEN * sizeof(char));
	sprintf(path, "%s/%d/%s", DIR_PATH, pid, "stat");
	fd = openFile(path);
	readFile(fd, stats, BUFFER_LEN);
	stats = readWord(stats, pidBuffer);
	stats = readWord(stats, pidBuffer);
	stats = readWord(stats, pidBuffer);
	stats = readWord(stats, pidBuffer);

	close(fd);

	return atoi(pidBuffer);
}


// 1 if true
// 0 if false
int ancestor(int pid, int arg) {
	while (pid != arg) {
		pid = parse_ppid(pid);
		if (pid == 1 || pid == 0) {
			return 0;
		}
	}
	return 1;
}

void ps(char* arg)
{
	int num, fd;
	int index = 0;
 
	char* name;
	char processNamePathOrg[BUFFER_LEN];
	char processNamePath[BUFFER_LEN];
	char processStatPath[BUFFER_LEN];
	char* processName;
	char* processStat;
	char statBuffer[BUFFER_LEN];

	struct dirent* data;
	struct direntC dataA;

	DIR* dir;

	struct direntC arr[PIDS_LEN];

	dir = opendir(DIR_PATH);
	strcpy(processNamePathOrg, DIR_PATH);
	strcat(processNamePathOrg, "/");

	while (data = readdir(dir)) {
		name = data -> d_name;
		num = atoi(name);
		strcpy(processNamePath, processNamePathOrg);
		strcpy(processStatPath, processNamePathOrg);

		if (num != 0 || name[0] == '0') {

			strcat(processNamePath, name);
			strcat(processNamePath, "/comm");

			processName = malloc(BUFFER_LEN * sizeof(char));
			fd = openFile(processNamePath);
			readFile(fd, processName, BUFFER_LEN);
			close(fd);

			strcat(processStatPath, name);
			strcat(processStatPath, "/stat");

			processStat = malloc(BUFFER_LEN * sizeof(char));
			fd = openFile(processStatPath);
			readFile(fd, processStat, BUFFER_LEN);
			close(fd);

			processStat = readWord(processStat, statBuffer);
			processStat = readWord(processStat, statBuffer);
			dataA.state = processStat[1];

			dataA.pid = num;
			dataA.ppid = parse_ppid(num);
			dataA.processName = processName;

			if (arg != NULL && ancestor(num, atoi(arg))) {
				arr[index] = dataA;
				index += 1;
			}

			else if (arg == NULL) {
				arr[index] = dataA;
				index += 1;
			}
		}
	}

	closedir(dir);

	bubbleSortNum(arr, index);

	printf("%5s %5s %6s %s\n", "PID", "PPID", "STANJE", "IME");
	for (int i = 0; i < index; i++) {
		printf("%5d %5d %6c %s", arr[i].pid, arr[i].ppid, arr[i].state, arr[i].processName);
	}
}

void psext(char* arg)
{
	int num, fd, count;
	int index = 0;
	int bufferLen = 2048;

	char* name;
	char processNamePathOrg[bufferLen];
	char processNamePath[bufferLen];
	char processStatPath[bufferLen];
	char processFilePath[bufferLen];
	char* processName;
	char* processStat;
	char statBuffer[bufferLen];

	struct dirent* data;
	struct dirent* dataFile;
	struct direntC dataA;

	DIR* dir;
	DIR* dirFile;

	struct direntC arr[PIDS_LEN];

	dir = opendir(DIR_PATH);
	strcpy(processNamePathOrg, DIR_PATH);
	strcat(processNamePathOrg, "/");

	while (data = readdir(dir)) {
		name = data -> d_name;
		num = atoi(name);

		strcpy(processNamePath, processNamePathOrg);
		strcpy(processStatPath, processNamePathOrg);

		strcpy(processNamePath, processNamePathOrg);
		strcpy(processFilePath, processNamePathOrg);

		count = 0;

		if (num != 0 || name[0] == '0') {

			dataA.pid = num;

			strcat(processNamePath, name);
			strcat(processNamePath, "/comm");

			processName = malloc(bufferLen * sizeof(char));
			fd = openFile(processNamePath);
			readFile(fd, processName, bufferLen);
			close(fd);

			processName[strlen(processName) - 1] = '\0';
			dataA.processName = processName;

			strcat(processStatPath, name);
			strcat(processStatPath, "/stat");

			processStat = malloc(bufferLen * sizeof(char));
			fd = openFile(processStatPath);
			readFile(fd, processStat, bufferLen);
			close(fd);

			processStat = readWord(processStat, statBuffer);
			processStat = readWord(processStat, statBuffer);
			dataA.state = processStat[1];
			processStat = readWord(processStat, statBuffer);
			processStat = readWord(processStat, statBuffer);
			dataA.ppid = atoi(statBuffer);

			for (int i = 0; i < 16; i++) {
				processStat = readWord(processStat, statBuffer);
			}

			dataA.threads = atoi(statBuffer);

			strcat(processFilePath, name);
			strcat(processFilePath, "/fd");

			dirFile = opendir(processFilePath);
			while (data = readdir(dirFile)) {
				count += 1;
			}
			closedir(dirFile);

			dataA.file = count - 2;

			if (arg != NULL && ancestor(num, atoi(arg))) {
				arr[index] = dataA;
				index += 1;
			}
			else if (arg == NULL) {
				arr[index] = dataA;
				index += 1;
			}
		}
	}

	bubbleSortNum(arr, index);

	printf("%5s %5s %6s %6s %6s %s\n", "PID", "PPID", "STANJE", "#NITI", "#DAT", "IME");
	for (int i = 0; i < index; i++) {
		printf("new: %5d %5d %6c %6d %6d %s\n", arr[i].pid, arr[i].ppid, arr[i].state, arr[i].threads, arr[i].file, arr[i].processName);
	}
}


// 	execlp("pstree", "-c", getpid(), (char *) NULL);
void forktree(int mainPid)
{

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

int main(int argc, char* argv[])
{
	if (argc < 2) {
		exit(1);
	}

	char* input = argv[1];

	// tole je pomeni da input ni "forktree"
	if (strcmp(input, "forktree")) {
		DIR_PATH = argv[2];
		strcpy(OS_PATH, DIR_PATH);
		strcat(OS_PATH, "/version");
		strcpy(SWAP_PATH, DIR_PATH);
		strcat(SWAP_PATH, "/swaps");
		strcpy(MODULES_PATH, DIR_PATH);
	    strcat(MODULES_PATH, "/modules");

	    DIR* dir = opendir(DIR_PATH);
	    int len = 0, num;
	    char* name;
	    struct dirent* data;

		while (data = readdir(dir)) {

			name = data -> d_name;
			num = atoi(name);

			if (num != 0 || name[0] == '0') {
				len += 1;
			}
		}

		PIDS_LEN = len;
	}
	else {
		forktree(getpid());
	}

	if (!strcmp(input, "sys")) {
		sys();
	}
	else if(!strcmp(input, "pids")) {
		pids();
	}
	else if(!strcmp(input, "names")) {
		names();
	}
	else if(!strcmp(input, "ps")) {
		ps(argv[3]);
	}
	else if(!strcmp(input, "psext")) {
		psext(argv[3]);
	}
	else if(!strcmp(input, "help")) {
		printf("%s\n", "help page");	
	}
	// else {
	//	printf("%s\n", "Invalide");
	//}
	exit(0);
}
