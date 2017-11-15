/*Eric Pickup

Write a C program with the parent process and a child process communicating with a pipe.
The child process will execute the shell command provided by the user via command line arguments.
The result of executing this shell command is passed to the parent process using a pipe. The
parent process will write the result into a file called result.txt and acknowledge the user on the
screen with the shell command and the total number of bytes in the result.
For simplicity, the shell command contains only the command name, no argument.
You can only use read, write, close for pipe operation.*/

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>

int main(int argc, char* argv[]) {

	int pipefd[2];

	if (pipe(pipefd) == -1) {
		printf("Pipe error");
		exit(1);
	}

	if (fork() == 0) {

		//Child process
		close(pipefd[0]);	//Close reading buffer

		dup2(pipefd[1], 1);	//Sends stdout to pipe buffer

		close(pipefd[1]);	//No longer needed since stdout will write to pipefd

		execlp(argv[1],argv[1],NULL);	//Excecute shell command (command line arg)

	} else {

		char buf[9999];

		close(pipefd[1]);	//Close writing buffer

		read(pipefd[0],buf,sizeof(buf));	//Read buffer from pipe

		int fd = open("result.txt", O_WRONLY | O_CREAT | O_TRUNC, 0777);

		int sizeOfBuf = 0;

		while (buf[sizeOfBuf] != '\0') {	//Finding actual size of buffer
			sizeOfBuf++;
		}

		write(fd,buf,sizeOfBuf);

		printf("The result of %s has been written to result.txt with total of %d bytes", argv[1], sizeOfBuf);

	}


}

