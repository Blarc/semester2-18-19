#include <dirent.h>
#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>

int main() {
  struct dirent *data;
  DIR *dir;
  int fd;

  fd = open("f.txt", O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR);
  dir = opendir(".");

  while ((data = readdir(dir)) != NULL) {
    char *name = data->d_name;
    for (int i = 0; i < sizeof(name); i++) {
      if (name[i] == 'b') {
        write(fd, name, sizeof(name));
        break;
      }
    }
  }
}
