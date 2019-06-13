#include <stdio.h>
#include <unistd.h>

int main() {
  int p[2];
  pipe(p);

  if (fork() == 0) {
    dup2(p[1], 1);
    close(p[0]);
    close(p[1]);
    execlp("ls", "ls", NULL);
  }

  if (fork() == 0) {
    int fd = open("f.txt", O_WRONLY | O_CREAT, O_IUSR);
    dup2(p[0], 0);
    dup2(fd, 1);
    close(p[0]);
    close(p[1]);
    close(fd);
    execlp("grep", "grep", "b", NULL);
  }

  close(p[0]);
  close(p[1]);

  wait(NULL);
  wait(NULL);
}
