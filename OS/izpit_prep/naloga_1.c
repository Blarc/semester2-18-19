#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int main() {
  int p[2];
  pipe(p);

  if (fork() == 0) {
    dup2(p[1], 1);
    close(p[0]);
    close(p[1]);
    execlp("cat", "cat", "tocke", NULL);
  }

  int pid = fork();
  if (pid == 0) {
    int fd = open("log", O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR);
    dup2(fd, 2);
    dup2(p[1], 0);
    close(p[0]);
    close(p[1]);
    execlp("izracun_ocena.sh", "izracun_ocena", NULL);
  }

  close(p[0]);
  close(p[1]);

  int status;
  waitpid(pid, &status, 0);

  if (status != 0) {
    if (fork() == 0) {
      execlp("mkdir", "mkdir", "FAIL", "&", NULL);
      exit(0);
    }
  }

  wait(NULL);
  wait(NULL);
}
