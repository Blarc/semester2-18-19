#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <stdlib.h>
#include <dirent.h>
#include <stdio.h>
#include <sys/file.h>

char* DIR_PATH;
char OS_PATH[32];
char SWAP_PATH[32];
char MODULES_PATH[32];
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
	while(data[iter] != ' ') {
		buffer[index] = data[iter];
		index += 1;
		iter += 1;
	}

	buffer[index] = '\0';
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

void bubbleSortAlph(struct direntC arr[], int n) {
	int i, j, a, b, e, f;
	for (i = 0; i < n-1; i++) {
		for (j = 0; j < n-i-1; j++) {

			e = (arr[j].processName)[0];
			f = (arr[j+1].processName)[0];

			if (e > f) {
				struct direntC tmp = arr[j];
				arr[j] = arr[j+1];
				arr[j+1] = tmp;
			}
			else if (e == f) {
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

	bubbleSortNum(arr, PIDS_LEN);

	for (int i = 0; i < PIDS_LEN; i++) {
		printf("%d\n", arr[i].pid);
	}
}


void names()
{

	int num, fd;
	int index = 0;
	int bufferLen = 30;

	char* name;
	char processNamePathOrg[bufferLen];
	char processNamePath[bufferLen];
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

			processName = malloc(bufferLen * sizeof(char));
			fd = openFile(processNamePath);
			readFile(fd, processName, bufferLen);

			dataA.pid = num;
			dataA.processName = processName;

			arr[index] = dataA;
			index += 1;
		}
	}

	bubbleSortAlph(arr, PIDS_LEN);

	for (int i = 0; i < PIDS_LEN; i++) {
		printf("%d %s", arr[i].pid, arr[i].processName);
	}
}

void ps(char* arg)
{
	// TODO dodatni ARGUMENT PREDNIK
	int num, fd;
	int index = 0;
	int bufferLen = 30;

	char* name;
	char processNamePathOrg[bufferLen];
	char processNamePath[bufferLen];
	char processStatPath[bufferLen];
	char* processName;
	char* processStat;
	char statBuffer[bufferLen];

	struct dirent* data;
	struct direntC dataA;

	DIR* dir;

	struct direntC arr[PIDS_LEN];

	if (arg != NULL) {
		
	}

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

			processName = malloc(bufferLen * sizeof(char));
			fd = openFile(processNamePath);
			readFile(fd, processName, bufferLen);
			close(fd);

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

			dataA.pid = num;
			dataA.processName = processName;

			arr[index] = dataA;
			index += 1;
		}
	}

	bubbleSortNum(arr, PIDS_LEN);

	printf("%5s %5s %6s %s\n", "PID", "PPID", "STANJE", "IME");
	for (int i = 0; i < PIDS_LEN; i++) {
		printf("%5d %5d %6c %s", arr[i].pid, arr[i].ppid, arr[i].state, arr[i].processName);
	}
}

void psext(char* arg)
{
	int num, fd, count;
	int index = 0;
	int bufferLen = 1024;

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

			dataA.file = count;

			arr[index] = dataA;
			index += 1;
		}
	}

	bubbleSortNum(arr, PIDS_LEN);

	printf("%5s %5s %6s %6s %6s %s\n", "PID", "PPID", "STANJE", "#NITI", "#DAT", "IME");
	for (int i = 0; i < PIDS_LEN; i++) {
		printf("%5d %5d %6c %6d %6d %s", arr[i].pid, arr[i].ppid, arr[i].state, arr[i].threads, arr[i].file, arr[i].processName);
	}
}

int main(int argc, char* argv[])
{
	if (argc < 2) {
		exit(1);
	}

	char* input = argv[1];
	DIR_PATH = argv[2];
	strcpy(OS_PATH, DIR_PATH);
	strcat(OS_PATH, "/version");
	strcpy(SWAP_PATH, DIR_PATH);
	strcat(SWAP_PATH, "/swaps");
	strcpy(MODULES_PATH, DIR_PATH);
    strcat(MODULES_PATH, "/modules");

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
	else if(!strcmp(input, "forktree")) {
		
	}
	else if(!strcmp(input, "help")) {
		printf("%s\n", "help page");	
	}
	else {
		printf("%s\n", "Invalid");
	}
}