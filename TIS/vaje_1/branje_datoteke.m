datoteka = fopen("vsota.m", "r");
vsebina = fread(datoteka, inf,"uchar");
crka = char(vsebina)'
fclose(datoteka)