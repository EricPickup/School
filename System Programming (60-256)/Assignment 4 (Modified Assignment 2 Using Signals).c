/*Eric Pickup, 104555011
03-60-256 Assignment #4
Rewrite your solution to Assignment 2 with changed requirements on parallel execution.

Requirement from previous assignment:
• The parent process waits for the child process to terminate before creating the next.

Replacement:
• The parent process will not wait for the child process to terminate before creating the
next process.
• The processes will do the calculation in parallel. Add some sleep time to simulate larger
calculation.
• The processes will write the data into the image file sequentially. This is coordinated by
the parent using signals.
• You can only use signal for inter-process communication.

*/

#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>

char* convertColor(char *colorText);
void myAlarmHandler(int dummy){};

int main(int n, char* args[]) {	//Read arguments into args[] array of strings

	char *color1 = convertColor(args[2]);	//Convert array arguments into colors
	char *color2 = convertColor(args[3]);
	char *color3 = convertColor(args[4]);
	char *color4 = convertColor(args[5]);
	char *color5 = convertColor(args[6]);

	int fd = open(args[1], O_WRONLY | O_CREAT | O_TRUNC, 0777);	//Open file (named from first cmd line arg) for writing, create if it doesn't exist
	char buf1[] = "P6\n1000 1000\n255\n";	//Header
	write(fd,buf1,sizeof(buf1));	//Write header
	lseek(fd,17,SEEK_SET);	//Seek past header info to write pixel data
	
	int offset;	
	int i;

	int pids[10];
	pid_t pid;
	for (i = 0; i < 10; i++) {	//Creating 10 children

		if ((pid = fork()) == 0) {

			printf("Child %d:\t(pid %d) waiting for signal from parent..\n",i+1, getpid());
			pids[i] = getpid();
			signal(SIGALRM, myAlarmHandler);
			pause();	//Await signal from parent to begin
			printf("Child %d:\trunning and writing to file\n", i+1);

			if (i < 5) {	//Top half of image
				for (int y = i*100; y < (i+1)*100; y++) {	//For 100 rows (i*100) and (i+1)*100 keeps track of y-coordinate		
					if ((offset = y-250) < 0) {	//Checking if y is greater than 250 (start of center shape) and if it is, records the offset which is how many pixels should be printed per line 
					//if this condition is met, then we do not need to worry about printing the center shape yet
						for (int x = 0; x < 1000; x++) {	//For 1000 pixels (1 row)
							if (x >= 500) {	//Second half of row
								write(fd,color3,3);
							} else {	//First half of row
								write(fd,color2,3);
							}		
						}
					} else {	//If statement condition wasn't true, therefore we will be printing the center shape in this row
					int x;
						for (x = 0; x < 500-offset; x++) {	//offset is the offset of pixels from the vertical center, print color2 until we reach that index
							write(fd,color2,3);
						}
						for (; x < 500+offset; x++) {	//From that left-offset index to the offset on the other side of vertical center, print center shape color
							write(fd,color1,3);		
						}
						for (; x < 1000; x++) {	//From right-offset to end of row, print color3
							write(fd,color3,3);
						}
					}
				}
			} else if (i >= 5 && i < 10) {	//Bottom half of image
				for (int y = i*100; y < (i+1)*100; y++) {	//For 100 rows			
					if ((offset = 750-y) < 0) {	//Offset is difference from 750 to y-coordinate (reversed), if this condition is met then we don't need to worry about printing centre shape in this row
						for (int x = 0; x < 1000; x++) {		//For each pixel in the row
							if (x >= 500) {	//Second half
								write(fd,color5,3);
							} else {
								write(fd,color4,3);
							}		
						}
					} else {
						int x;
						for (x = 0; x < 500-offset; x++) {
							write(fd,color4,3);
						}
						for (; x < 500+offset; x++) {
							write(fd,color1,3);		
						}
						for (; x < 1000; x++) {
							write(fd,color5,3);
						}
					}
				}
			}

			printf("Child %d:\tterminating and signalling parent\n", i+1);
			kill(getppid(),SIGALRM);	//Send signal to parent notifying that this process is done
			break;
			
		} else {	//Parent storing the PIDs of each child (so we can signal them)
			pids[i] = pid;
		}
	}

	if (pid > 0) {
		for (int j = 0; j < 10; j++) {	//For each child process
			sleep(1);
			printf("\n\nParent:\t\tSending signal to child %d (pid %d) to begin\n", j+1, pids[j]);
			kill(pids[j],SIGALRM);	//Send signal to child to begin
			signal(SIGALRM, myAlarmHandler);	
			pause();	//Await signal back from child
			printf("Parent:\t\tReceived signal from child %d, moving on..",j+1);
		}
		close(fd);
	} 
}
/*Function: convertColor
Objective: Accepts a string name of a color, then converts and returns it as decimal form
Input: char *colorText - string that describes the color we wish to return in decimal code form
Output: color - returns character pointer that contains decimal code form of input color*/
char* convertColor(char *colorText) {
	char *color = (char *) malloc(sizeof(char) * 3);
	if (strcmp(colorText,"red") == 0) {
		color[0] = 255, color[1] = 0, color[2] = 0;
	} else if (strcmp(colorText,"green") == 0) {
		color[0] = 0, color[1] = 128, color[2] = 0;
	} else if (strcmp(colorText,"blue") == 0) {
		color[0] = 0, color[1] = 0, color[2] = 255;
	} else if (strcmp(colorText,"yellow") == 0) {
		color[0] = 255, color[1] = 255; color[2] = 0;
	} else if (strcmp(colorText,"orange") == 0) {
		color[0] = 255, color[1] = 165, color[2] = 0;
	} else if (strcmp(colorText,"cyan") == 0) {
		color[0] = 0, color[1] = 255, color[2] = 255;
	} else if (strcmp(colorText,"magenta") == 0) {
		color[0] = 255, color[1] = 0, color[2] = 255;
	} else if (strcmp(colorText,"ocean") == 0) {
		color[0] = 66, color[1] = 105, color[2] = 225;
	} else if (strcmp(colorText,"violet") == 0) {
		color[0] = 138, color[1] = 43, color[2] = 226;
	}
	return color;
}