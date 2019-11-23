#include<stdio.h>
#include<stdlib.h>

int main(int argc, char const *argv[]){	
	int i, n = atoi(argv[1]);
	for(i = 1; i <= atoi(argv[2]); i++){
		printf("%d x %d = %d\n",n,i,n*i );
	}
	return 0;
}