#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

int main() {
  int p[2];
  pipe(p);

  if (fork() == 0) {
    dup2(p[1], 1);
    close(p[0]);
    close(p[1]);
    execlp("cat", "cat", "izpis.txt", NULL);
  }

  int pid = fork();
  if (pid == 0) {
    int fd = open("nrows.txt", O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR);
    dup2(p[0], 0);
    dup2(fd, 1);
    close(p[0]);
    close(p[1]);
    close(fd);
    execlp("wc", "wc", "-l", NULL);
  }

  close(p[0]);
  close(p[1]);

  int status;
  waitpid(pid, &status, 0);
  if (status != 0) {
    if (fork() == 0) {
      execlp("cat", "cat", "nrows.txt", NULL);
    }
  }

  wait(NULL);
}
