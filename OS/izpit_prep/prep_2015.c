#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

int main() {
  int p[2];
  pipe(p);

  if (fork() == 0) {
    if (fork() == 0) {
      if (fork() == 0) {
        dup2(p[1], 1);
        close(p[1]);
        close(p[0]);
        execlp("ls", "ls", NULL);
      }
      wait(NULL);
    }
    wait(NULL);

    if (fork() == 0) {
      if (fork() == 0) {
        dup2(p[0], 0);
        dup2(2, 1);
        close(p[1]);
        close(p[0]);
        execlp("cat", "cat", NULL);
      }
      wait(NULL);
    }
    wait(NULL);
  }

  close(p[0]);
  close(p[1]);

  exit(0);
}
