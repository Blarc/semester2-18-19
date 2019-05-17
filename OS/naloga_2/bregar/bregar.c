#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h> 
#include <stdbool.h>
#include <ctype.h>


typedef char *string;


void vrniTab( string pot, char** array){
   int stevec=0;
   int cur_size = 0;
	FILE *my;
   my = fopen(pot,"r");
  

   while(!(fscanf(my, "%*s%n", &cur_size))){
         array[stevec] = malloc((cur_size+1)*sizeof(*array[stevec]));
		stevec++;
   }
   fclose(my);

   //printf("%d\n",stevec);
   my = fopen(pot,"r");
   for(int i = 0; i < stevec; i++){
         fscanf(my, "%s", array[i]);
		 //printf("%s\n",array[i]);
   }
   fclose(my);

} 

int vrniStVrstic( string pot){
   int stevec=0;
	char ch;
   FILE *my;
   my = fopen(pot,"r");
  
	while((ch=fgetc(my))!=EOF)
	{
		if(ch=='\n')
			stevec++;
	}

   fclose(my);
   return stevec;
} 


void freeTab ( char** array, int stevec){
	  for(int i = 0; i < stevec; i++){
         free(array[i]);
   }
}

int vrniStev ( char** array){
	int stevec=0;
	  for(int i = 0; array[i]!=NULL; i++){
		     //printf("%s\n",array[i]);
			stevec++;
	   }
	return stevec;

}

int isDirectory(const char *path) {
   struct stat statbuf;
   if (stat(path, &statbuf) != 0)
       return 0;
   return S_ISDIR(statbuf.st_mode);
}

int cmpfunc (const void * a, const void * b) {
   return ( *(int*)a - *(int*)b );
}


void sys(){
	string arrayVersion[150];
	string arraySwap[500];
	

    vrniTab("proc-demo/version",arrayVersion);
    printf("%s: %s\n",arrayVersion[0],arrayVersion[2]);
    printf("%s: %s\n",arrayVersion[4]+1,arrayVersion[6]);
    int stev=vrniStev(arrayVersion);
    freeTab(arrayVersion,stev);

    vrniTab("proc-demo/swaps",arraySwap);
    printf("Swap: %s\n",arraySwap[1]);
    stev=vrniStev(arraySwap);
    freeTab(arraySwap,stev);

    stev=vrniStVrstic("proc-demo/modules");
    printf("Modules: %d\n",stev);
}

void pids(){
	struct dirent *de;  // Pointer for directory entry 
  
    // opendir() returns a pointer of DIR type.  
    DIR *dr = opendir("proc-demo"); 
  
    if (dr == NULL)  // opendir returns NULL if couldn't open directory 
    { 
        printf("Could not open current directory" ); 
        exit(1); 
    } 
   
	int* arrayPID= (int*) malloc(15 * sizeof(int));
	char* pid;
	int stevec=0;
	char* ime;
    while ((de = readdir(dr)) != NULL){
			ime=de->d_name; 
			//printf("%s\n",ime);
			//printf("/%s",ime);
			if (atoi(ime)){
				if(	strcmp (ime,".")!=0 && strcmp (ime,"..")!=0){
					//printf("%s\n",ime);	
					int st= atoi(ime);
					//printf("%d",st);	
					arrayPID[stevec]=st;
					//printf("%d\n",arrayPID[stevec]);	
					stevec++;		
				}
			}         	
  	}

	qsort(arrayPID, stevec, sizeof(int), cmpfunc);
	for(int n = 0 ; n < stevec; n++ ) {   
      printf("%d\n", arrayPID[n]);
   	}
    closedir(dr); 

}


void strlwr(const char* str, char* nova){
		
	
	for(int i = 0; str[i]; i++){
		  nova[i] = tolower(str[i]);
	}
	
}



void names(){

	


	struct dirent *de;  // Pointer for directory entry 
  
	struct pidName_{
	   int  stPid;
	   char imeProcesa[20];
	   
	};
	
	typedef struct pidName_ pidName;

	int compare(pidName s1_a, pidName s2_b)
	{
		char novaA[50];
		char novaB[50];

	    pidName pidName_a = s1_a;
  	    pidName pidName_b = s2_b;
		
		strlwr(pidName_a.imeProcesa,novaA);
		strlwr(pidName_b.imeProcesa,novaB);
		
		int aPid = pidName_a.stPid;
		int bPid = pidName_b.stPid;

	    int strcomp = strcmp(novaA,novaB);
		if (strcomp == 0){
			//printf("%d",aPid - bPid);
			return aPid - bPid;
		}  
			
		else{
			//printf("%d",strcomp);
			return strcomp;
		}

	}

	void swap(pidName *xp, pidName *yp) 
		{ 
			pidName temp = *xp; 
			*xp = *yp; 
			*yp = temp; 
		} 
  
// A function to implement bubble sort 
	void bubbleSort(pidName arr[], int n) 
	{ 
	   int i, j; 
	   for (i = 0; i < n-1; i++){       
	  
		   // Last i elements are already in place    
		   for (j = 0; j < n-i-1; j++) { 
		       if (compare(arr[j],arr[j+1])==1) {
					swap(&arr[j], &arr[j+1]);
					//pidName temp = arr[j];
					//arr[j]=arr[j+1];
					//arr[j+1]=temp;				
				}
			}
		 }          
	} 

/*
	int compare(const void *s1_a, const void *s2_b)
	{
	   const pidName *pidName_a = s1_a;
  	   const pidName *pidName_b = s2_b;
	
		char* a=pidName_a->imeProcesa;
		char* b=pidName_b->imeProcesa;
		
		int aPid = pidName_a->stPid;
		int bPid = pidName_b->stPid;

	   	int i=0,c,d;
		while(a[i]!='\0' && b[i]!='\0'){
			c=tolower(a[i]);
			d=tolower(b[i]);
			
			if(c < d)
		}

	}
*/
    char* pot="proc-demo";
	char potComm[100];
	strcpy(potComm,"proc-demo/");
	//printf("%s",potComm);	
    // opendir() returns a pointer of DIR type.  
    DIR *dr = opendir(pot); 
  
    if (dr == NULL)  // opendir returns NULL if couldn't open directory 
    { 
        printf("Could not open current directory" ); 
        exit(1); 
    } 
	
    pidName *array = malloc(sizeof(pidName) * 15);
	int stevec=0;
	char* ime;
	char* potProcesa;
	char filebuffer[1000];
    while ((de = readdir(dr)) != NULL){
			ime=de->d_name; 
			//printf("%s\n",ime);
			//printf("/%s",ime);
			if (atoi(ime)){
				
					//printf("%s\n",ime);
					strcat(potComm,ime);
					strcat(potComm,"/comm");
					FILE *my;
   					my = fopen(potComm,"r");
					//printf("%s\n",potComm);	
					fscanf(my, "%s", filebuffer);
					//printf("%s\n",filebuffer);	
					int st= atoi(ime);
					//printf("%d",st);	
					array[stevec].stPid=st;
					strcpy(array[stevec].imeProcesa,filebuffer);
					//printf("%d %s\n",array[stevec].stPid,array[stevec].imeProcesa);	
					strcpy(potComm,"proc-demo/");
					stevec++;
					fclose(my);		
				
			}         	
  	}
	//qsort(array, stevec, sizeof(pidName), compare);
	
	bubbleSort(array, stevec);
	
	for(int n = 0 ; n < stevec; n++ ) {   
      printf("%d %s\n",array[n].stPid,array[n].imeProcesa);	
   	}
	closedir(dr); 
}










int main( int argc, char *argv[] )  {	
    
	if (argc <= 1) {
		exit(1);
	}

	char* akcija = argv[1];

	if(strcmp(akcija,"sys")==0){
		sys();
	}
	
	else if(strcmp(akcija,"pids")==0){
		pids();
	}
	 
	else if(strcmp(akcija,"names")==0){
		names();
	}   
    
   return 0;
}



