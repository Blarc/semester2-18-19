#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>

int main() {
  int fd_in = open("f.txt", O_RDONLY);
  int fd_err = open("f.err", O_RDWR | O_CREAT, S_IRUSR | S_IWUSR);
  int fd_out = open("b.txt", O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR);

  int stderr = dup(2);
  dup2(fd_err, 2);

  char c[1];
  while (read(fd_in, c, 1) != 0) {
    printf("%s", c);
  }
  close(fd_in);

  dup2(2, stderr);
  int lc = 0;
  while (read(fd_err, c, 1) != 0) {
    if (c[0] == '\n') {
      lc += 1;
    }
  }
  close(fd_err);

  char r[100];
  sprintf(r, "%d", lc);
  printf("%d\n", lc);
  write(fd_out, r, sizeof(lc));
  close(fd_out);
}
