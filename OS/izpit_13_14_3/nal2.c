
int main() {
  if (fork() == 0) {
    printf("Novodojencek");
    exit(0);
  } else {
    printf("Jaz sem ocka!");
  }
}

printf("Novodojencek!") & printf("Jaz sem ocka!");
