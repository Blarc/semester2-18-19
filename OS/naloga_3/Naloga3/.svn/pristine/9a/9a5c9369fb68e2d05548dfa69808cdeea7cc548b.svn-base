#include <ctype.h>
#include <dirent.h>
#include <errno.h>
#include <fcntl.h>
#include <limits.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/file.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define BUF_SIZE 1024
#define MAX_LINKS 16
#define MAX_TOKENS 32
#define MAX_TOKEN_SIZE 16

char shell_name[64] = "mysh";
char base_dir[PATH_MAX];
char *tokens[MAX_TOKENS];
char *line;
int num_tokens;
int exit_status = 0;
int new_directory = 0;

void bash_handle_this() {
  int pid = fork();
  if (pid == 0) {
    tokens[num_tokens] = NULL;
    // printf("%s\n", "HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEELLLLLLLLLLLLLLLLOOOO");
    execvp(tokens[0], tokens);
    exit(0);
    return;
  }
  int status;
  waitpid(pid, &status, 0);
}

// FILE FUNCTIONS

void cpcat() {
  char buffer[BUF_SIZE];
  int fd1 = 0, fd2, len;

  if (num_tokens > 1 && (tokens[1][0] != '-')) {
    if ((fd1 = open(tokens[1], O_RDONLY)) != 0) {
      printf("cpcat: %s\n", strerror(errno));
      exit_status = errno;
      return;
    }

    flock(fd1, LOCK_SH);
    dup2(fd1, 0);
  }

  if (num_tokens > 2 && (tokens[2][0] != '-')) {
    if ((fd2 = open(tokens[2], O_APPEND | O_WRONLY | O_CREAT,
                    S_IRUSR | S_IWUSR)) != 0) {
      printf("cpcat: %s\n", strerror(errno));
      exit_status = errno;
      return;
    }

    flock(fd2, LOCK_EX);
    dup2(fd2, 1);
  }

  while ((len = read(fd1, buffer, sizeof(buffer))) > 0) {
    write(1, buffer, len);
  }

  flock(fd1, LOCK_UN);
  flock(fd2, LOCK_UN);

  close(fd1);
  close(fd2);
}

int findlinks(int ino, char *path, int index) {
  DIR *dir = opendir(path);
  struct dirent *data;

  while ((data = readdir(dir))) {
    if (data->d_ino == ino) {
      printf("%s ", data->d_name);
      // links[index] = data->d_name;
      index += 1;
    }
    if (data->d_type == DT_DIR) {
      char new_path[PATH_MAX];
      if (strcmp(data->d_name, ".") != 0 && strcmp(data->d_name, "..") != 0) {
        snprintf(new_path, sizeof(new_path), "%s/%s", path, data->d_name);
        index = findlinks(ino, new_path, index);
      }
    }
  }
  closedir(dir);
  return index;
}

void linklist() {
  struct stat s;
  if (stat(tokens[1], &s) != 0) {
    printf("linklist: %s\n", strerror(errno));
  } else {
    // char *links[MAX_LINKS];
    int num_links = findlinks(s.st_ino, base_dir, 0);
    printf("%c", '\n');
    // if (num_links > 1) {
    //   for (int i = 0; i < num_links; i++) {
    //     printf("%s ", links[i]);
    //   }
    //   printf("%c", '\n');
    // }
  }
}

void my_unlink() {
  if (unlink(tokens[1]) != 0) {
    printf("linkread: %s\n", strerror(errno));
  }
  exit_status = errno;
}

void linkread() {
  int len;
  char buffer[NAME_MAX];
  if ((len = readlink(tokens[1], buffer, NAME_MAX)) == -1) {
    printf("linkread: %s\n", strerror(errno));
  } else {
    buffer[len] = '\0';
    printf("%s\n", buffer);
  }
  exit_status = errno;
}

void linksoft() {
  if (num_tokens > 2) {
    if (symlink(tokens[1], tokens[2]) != 0) {
      printf("linksoft: %s\n", strerror(errno));
    }
  }
  exit_status = errno;
}

void linkhard() {
  if (num_tokens > 2) {
    if (link(tokens[1], tokens[2]) != 0) {
      printf("linkhard: %s\n", strerror(errno));
    }
  }
  exit_status = errno;
}

void my_rename() {
  if (num_tokens > 2) {
    if (rename(tokens[1], tokens[2]) != 0) {
      printf("rename: %s\n", strerror(errno));
    }
  }
  exit_status = errno;
}

// DIRECTORY FUNCTIONS

void dirlist() {
  char *dir_path = "./";
  if (num_tokens > 1) {
    dir_path = tokens[1];
  }

  DIR *dir;
  if ((dir = opendir(dir_path)) == NULL) {
    printf("dirlist: %s\n", strerror(errno));
    exit_status = errno;
    return;
  }

  struct dirent *data;
  while ((data = readdir(dir))) {
    printf("%s  ", data->d_name);
  }
  printf("%s", "\n");

  exit_status = errno;
}

void dirremove() {
  if (rmdir(tokens[1]) == -1) {
    printf("dirremove: %s\n", strerror(errno));
  }
  exit_status = errno;
}

void dirmake() {
  if (mkdir(tokens[1], 0700) != 0) {
    printf("dirmake: %s\n", strerror(errno));
  }
  if (new_directory == 0) {
    new_directory = 1;
    getcwd(base_dir, sizeof(base_dir));
  }
  exit_status = errno;
}

void dirwhere() {
  char cwd[PATH_MAX];
  if (getcwd(cwd, sizeof(cwd)) != NULL) {
    printf("%s\n", cwd);
  } else {
    printf("dirwhere: %s\n", strerror(errno));
    exit_status = 1;
  }
  exit_status = 0;
}

void dirchange() {
  char *dir = "/";
  if (num_tokens > 1) {
    dir = tokens[1];
  }
  if (chdir(dir) != 0) {
    printf("dirchange: %s\n", strerror(errno));
  }
  exit_status = errno;
}

// COMMAND FUNCTIONS

void my_ppid() {
  printf("%d\n", getppid());
  exit_status = 0;
}

void my_pid() {
  printf("%d\n", getpid());
  exit_status = 0;
}

void my_print() {
  int i;
  for (i = 1; i < num_tokens - 1; i++) {
    printf("%s ", tokens[i]);
  }
  printf("%s", tokens[i]);
  exit_status = 0;
}

void my_echo() {
  my_print();
  printf("%s", "\n");
  exit_status = 0;
}

void my_exit() { exit(atoi(tokens[1])); }

void status() {
  printf("%d\n", exit_status);
  exit_status = 0;
}

void help() {
  printf("\n%s\n\n",
         "============================ HELP ============================");
  printf("%s\n", "name {shellName} ~ set shell's name");
  printf("%s\n", "status ~ prints last exit status");
  printf("%s\n", "exit {exitStatus} ~ exits the shell with {exitStatus}");
  printf("%s\n", "print {args} ~ prints {args} to stdout");
  printf("%s\n", "echo {args} ~ print with newline at the end");
  printf("%s\n", "pid ~ prints process's pid");
  printf("%s\n", "ppid ~ prints process's parent's pid");
  printf("\n%s\n\n",
         "================================================================");
  exit_status = 0;
}

void name() {
  if (num_tokens < 2) {
    printf("%s\n", shell_name);
  } else {
    strcpy(shell_name, tokens[1]);
  }
  exit_status = 0;
}

// UTILITY FUNCTIONS

void tokenize() {

  num_tokens = 0;
  int i = 0;
  while (*line != '\n') {
    if (isspace(*line) != 0) {
      line += 1;
    } else if (*line == '"') {
      line += 1;
      tokens[i] = line;
      i += 1;
      while (*line != '"') {
        line += 1;
      }
      *line = '\0';
      line += 1;
    } else {
      tokens[i] = line;
      i += 1;
      while (isspace(*line) == 0) {
        line += 1;
      }
      // we got to the end of line
      if (*line == '\n') {
        *line = '\0';
        break;
      }
      *line = '\0';
      line += 1;
    }
  }
  num_tokens = i;
}

int main() {
  getcwd(base_dir, sizeof(base_dir));
  unsigned long max_input = 128;

  while (1) {
    if (isatty(0) == 1) {
      printf("%s> ", shell_name);
    }
    if (getline(&line, &max_input, stdin) == -1) {
      exit(0);
    }
    tokenize();

    if (num_tokens > 0) {
      if (strcmp(tokens[0], "name") == 0) {
        name();
      } else if (strcmp(tokens[0], "help") == 0) {
        help();
      } else if (strcmp(tokens[0], "status") == 0) {
        status();
      } else if (strcmp(tokens[0], "exit") == 0) {
        my_exit();
      } else if (strcmp(tokens[0], "print") == 0) {
        my_print();
      } else if (strcmp(tokens[0], "echo") == 0) {
        my_echo();
      } else if (strcmp(tokens[0], "pid") == 0) {
        my_pid();
      } else if (strcmp(tokens[0], "ppid") == 0) {
        my_ppid();
      } else if (strcmp(tokens[0], "dirchange") == 0) {
        dirchange();
      } else if (strcmp(tokens[0], "dirwhere") == 0) {
        dirwhere();
      } else if (strcmp(tokens[0], "dirmake") == 0) {
        dirmake();
      } else if (strcmp(tokens[0], "dirremove") == 0) {
        dirremove();
      } else if (strcmp(tokens[0], "dirlist") == 0) {
        dirlist();
      } else if (strcmp(tokens[0], "rename") == 0) {
        my_rename();
      } else if (strcmp(tokens[0], "linkhard") == 0) {
        linkhard();
      } else if (strcmp(tokens[0], "linksoft") == 0) {
        linksoft();
      } else if (strcmp(tokens[0], "linkread") == 0) {
        linkread();
      } else if (strcmp(tokens[0], "linklist") == 0) {
        linklist();
      } else if (strcmp(tokens[0], "unlink") == 0) {
        my_unlink();
      } else if (strcmp(tokens[0], "cpcat") == 0) {
        cpcat();
      } else if (strcmp(tokens[0], "#") != 0) {
        bash_handle_this();
      }
    }
  }

  exit(0);
}
