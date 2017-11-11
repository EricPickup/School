/*Eric Pickup
03-60-254 Assigment #1
Instructions: Write a program that allows the user to store information about 
Facebook posts. For each post, the user wants to store the number of likes and
the number of comments the post received. The name of the post owner (account name),
the size of the post (number of chars), and the date of the posting. The date could
be a string or a number in various formats.
When the program executes it should ask the user the following options:
1. Display the stored posts
2. Display the first post with a given attribute value (e.g. post author = John)
3. Display the current total number of stored posts
4. Store the data of a new post
5. Delete a post by one of the following options (date, author)
6. Delete all the stored posts
7. Sort the post based on an attribute (num likes, num comments, date, or post size)
8. Exit
*/

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

struct Facebook {
	int numLikes;
	int numComments;
	char author[20];
	int size;
	char date[20];
	struct Facebook *nextPost;
};

typedef struct Facebook postInfo;

postInfo *addPost (postInfo *postList);
void printPosts (postInfo *postList);
int searchPost (postInfo *postList, int choice);
void printNumPosts();
postInfo *deletePost (postInfo *postList);
postInfo *deleteAllPosts(postInfo * postList);
postInfo *sortPosts(postInfo *postList);
int doesPostExist (postInfo *postList, int attribute);
int errorCatcher (int type, char attribute[20]);

int numPosts = 0;

int main(void) {

	postInfo *postList = NULL;

	int input = 0;

	while (input != 8) {

		printf("\n\n~~~~~MENU~~~~~\n");
		printf("1. Display the stored posts\n");
		printf("2. Search for post\n");
		printf("3. Display the current total number of stored posts\n");
		printf("4. Store new post\n");
		printf("5. Delete a post (by date, author)\n");
		printf("6. Delete all posts\n");
		printf("7. Sort posts (by # of likes, # of comments, date or size\n");
		printf("8. Exit\n");

		printf("\n>>> ");
		scanf("%d", &input);
		if (input == 1) {
			printPosts(postList);
		} else if (input == 2) {
			printf("Enter the attribute you wish to search with from the following list:\n1. Author\n2. Date\n3. Size\n4. Number of likes\n5. Number of comments\n>>>");
			int choice;
			scanf("%d",&choice);
			searchPost(postList,choice);
		} else if (input == 3) {
			printNumPosts();
		} else if (input == 4) {
			postList = addPost(postList);
		} else if (input == 5) {
			postList = deletePost(postList);
		} else if (input == 6) {
			postList = deleteAllPosts(postList);
		} else if (input == 7) {
			postList = sortPosts(postList);
		}
	}
}

void printPosts (postInfo *postList) {

	postInfo *tempPostList = postList;
	while (tempPostList != NULL) {
		printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		printf("Author: %s\n",tempPostList->author);
		printf("Date: %s\n",tempPostList->date);
		printf("Post size: %d character(s)\n",tempPostList->size);
		printf("Number of likes: %d\n",tempPostList->numLikes);
		printf("Number of comments: %d\n",tempPostList->numComments);
		tempPostList = tempPostList->nextPost;
	}
}

postInfo *addPost (postInfo *postList) {

	postInfo *newPost =  (postInfo *) malloc(sizeof(postInfo));
	postInfo *tempPostList = postList;
	printf("Enter the author's name:\n>>>");
	scanf("%s",newPost->author);
	printf("Enter the date (DDMMYYYY):\n>>>");
	scanf("%s",newPost->date);
	if (errorCatcher(1,newPost->date) == 1) {
		return postList;
	}

	printf("Enter the size of the post (# of chars):\n>>>");
	char testSize[20];
	scanf("%s",testSize);
	if (errorCatcher(2,testSize) == 1) {
		return postList;
	}
	newPost->size = atoi(testSize);
	printf("Enter number of likes:\n>>>");
	char testLikes[20];
	scanf("%s",testLikes);
	if (errorCatcher(2,testLikes) == 1) {
		return postList;
	}
	newPost->numLikes = atoi(testLikes);
	printf("Enter number of comments:\n>>>");
	char testComments[20];
	scanf("%s", testComments);
	if (errorCatcher(2,testComments) == 1) {
		return postList;
	}
	newPost->numComments = atoi(testComments);

	printf("\nPost added successfully.\n");
	if (postList == NULL) {	//If list was empty
		newPost->nextPost = NULL;
		numPosts++;
		return newPost;
	}
	while (tempPostList->nextPost != NULL) {
		tempPostList = tempPostList->nextPost;
	}
	newPost->nextPost = NULL;
	tempPostList->nextPost = newPost;
	numPosts++;
	return postList;

}

int searchPost (postInfo *postList, int choice) {

	int matchFlag = 0;
	postInfo *tempPostList = postList; 
	if (choice == 1) {	//Search by name
		printf("Enter the author's name:\n>>>");
		char searchName[40];
		char tempName[40]; //temp name for converting name to uppercase
		scanf("%s",searchName);
		while (tempPostList != NULL) {
			strcpy(tempName,tempPostList->author);
			if (strcmp(strupr(searchName),strupr(tempName)) == 0) {	//Converts both names to uppercase to make it non-case sensitive
				matchFlag = 1;
				break;
			}
			tempPostList = tempPostList->nextPost; 
		}
	} else if (choice == 2) {	//Search by date
		printf("Enter the date to search for (DDMMYYYY)\n>>>");
		char searchDate[20];
		scanf("%s",searchDate);
		if (errorCatcher(1,searchDate) == 1) {
			return 0;
		}
		while (tempPostList != NULL) {
			if (strcmp(searchDate,tempPostList->date) == 0) {
				matchFlag = 1;
				break;
			}
			tempPostList = tempPostList->nextPost; 
		}
	} else if (choice == 3) { //Search by size
		printf("Enter the size to search for (# of characters):\n>>>");
		char searchSize[10];
		scanf("%s",searchSize);
		if (errorCatcher(2,searchSize) == 1) {
			return 0;
		}
		while (tempPostList != NULL) {
			if (atoi(searchSize) == tempPostList->size) {
				matchFlag = 1;
				break;
			}
			tempPostList = tempPostList->nextPost; 
		}
	} else if (choice == 4) {	//Search by likes
		printf("Enter # of likes to search for:\n>>>");
		char searchLikes[10];
		scanf("%s",searchLikes);
		if (errorCatcher(2,searchLikes) == 1) {
			return 0;
		}
		while (tempPostList != NULL) {
			if (atoi(searchLikes) == tempPostList->numLikes) {
				matchFlag = 1;
				break;
			}
			tempPostList = tempPostList->nextPost; 
		}
	} else if (choice == 5) {	//Search by comments
		printf("Enter # of comments to search for:\n>>>");
		char searchComments[10];
		scanf("%s",searchComments);
		if (errorCatcher(2,searchComments) == 1) {
			return 0;
		}
		while (tempPostList != NULL) {
			if (atoi(searchComments) == tempPostList->numComments) {
				matchFlag = 1;
				break;
			}
			tempPostList = tempPostList->nextPost; 
		}
	}

	if (matchFlag == 1) {
		printf("\n~~~~~~~~FOUND MATCH!~~~~~~~~~\n");
		printf("Author: %s\n",tempPostList->author);
		printf("Date: %s\n",tempPostList->date);
		printf("Post size: %d characters\n",tempPostList->size);
		printf("Number of likes: %d\n",tempPostList->numLikes);
		printf("Number of comments: %d\n",tempPostList->numComments);
		printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		return 1;
	} else {
		printf("NO MATCH FOUND (make sure input is correct format)!\n");
		return 0;
	}
}

void printNumPosts() {
	printf("There are currently %d post(s) stored.\n",numPosts);
}

postInfo * deletePost(postInfo * postList) {

	printf("Enter the attribute you wish to delete by from the following list:\n1. Author\n2. Date\n>>>");
	int deleteChoice;
	scanf("%d", &deleteChoice);
	if (deleteChoice == 1) {	//DELETE BY AUTHOR
		printf("Enter the name of the post's author:\n>>>");
		char deleteName[20];
		scanf("%s", deleteName);
		postInfo *tempPostList = postList;
		if (strcmp(tempPostList->author,deleteName) == 0) {	//if it's the first element in list
			postList = tempPostList->nextPost;
		free(tempPostList);
	} else {
		if (tempPostList->nextPost == NULL) {
			printf("ENTRY NOT FOUND IN LIST. RETURNING TO MENU.\n");
			return postList;
		}
		while (strcmp(tempPostList->nextPost->author,deleteName) != 0) {
			tempPostList = tempPostList->nextPost;
			if (tempPostList -> nextPost == NULL) {
				printf("ENTRY NOT FOUND IN LIST. RETURNING TO MENU.");
				return postList;
			}
		}
			if (tempPostList->nextPost->nextPost == NULL) { //Deleting from end of list
				free(tempPostList->nextPost);
				tempPostList->nextPost = NULL;
			} else {
				free(tempPostList->nextPost);
				tempPostList->nextPost = tempPostList->nextPost->nextPost;
			}
		}
	} else if (deleteChoice == 2) {	//DELETE BY DATE
		printf("Enter the date of the post you would like to delete:\n>>>");
		char deleteDate[20];
		scanf("%s", deleteDate);
		if (errorCatcher(1,deleteDate) == 1) {
			return postList;
		}
		postInfo *tempPostList = postList;
		if (strcmp(tempPostList->date,deleteDate) == 0) {	//if it's the first element in list
			postList = tempPostList->nextPost;
		free(tempPostList);
	} else {
		if (tempPostList->nextPost == NULL) {
			printf("ENTRY NOT FOUND IN LIST. RETURNING TO MENU.\n");
			return postList;
		}
		while (strcmp(tempPostList->nextPost->date,deleteDate) != 0) {
			tempPostList = tempPostList->nextPost;
			if (tempPostList -> nextPost == NULL) {
				printf("ENTRY NOT FOUND IN LIST. RETURNING TO MENU.");
				return postList;
			}
		}
			if (tempPostList->nextPost->nextPost == NULL) { //Deleting from end of list
				free(tempPostList->nextPost);
				tempPostList->nextPost = NULL;
			} else {
				free(tempPostList->nextPost);
				tempPostList->nextPost = tempPostList->nextPost->nextPost;
			}
		}
	}
	printf("DELETED SUCCESSFULLY.\n");
	numPosts--;
	return postList;

}

postInfo *deleteAllPosts(postInfo * postList) {
	while (postList != NULL) {
		postInfo *tempPostList = postList;
		postList = postList->nextPost;
		free(tempPostList);
	}
	printf("ALL POSTS SUCCESSFULLY DELETED.");
	numPosts = 0;
	return postList;
}

postInfo *sortPosts(postInfo *postList) {

	printf("Enter the attribute you wish to sort by from the following list:\n1. Date\n2. # Of Likes\n3. # Of Comments\n4. Post size (# of chars)\n>>>");
	int choice;
	scanf("%d",&choice);

	if (choice == 1) {	//SORT BY DATE
		for (int i = 0; i < numPosts; i++) {
			postInfo *current = postList;
			postInfo *next = current->nextPost;
			postInfo *previous = NULL;
			while (next != NULL) {
				int lessThan;
				char year1[4], year2[4], month1[2], month2[2], day1[2], day2[2];
				for (int i = 0; i < 4; i++) {
					year1[i] = current->date[i+4];
					year2[i] = next->date[i+4];
				}
				for (int i = 0; i < 2; i++) {
					month1[i] = current->date[i+2];
					month2[i] = next->date[i+2];
					day1[i] = current->date[i];
					day2[i] = next->date[i];
				}
				if (atoi(year1) > atoi(year2)) {
					lessThan = 1;
				} else if (atoi(year2) > atoi(year1)) {
					lessThan = 0;
				} else if (atoi(month1) > atoi(month2)) {
					lessThan = 1;
				} else if (atoi(month2) > atoi(month1)) {
					lessThan = 0;
				} else if (atoi(day1) < atoi(day2)) {
					lessThan = 1;
				} else if (atoi(day2) < atoi(day1)) {
					lessThan = 0;
				} else {
					lessThan = 0;
				}

				if (lessThan == 0) {
					if (current == postList) {
						postList = next;
					} else {
						previous->nextPost = next;
					}
					current->nextPost = next->nextPost;
					next->nextPost = current;
					previous = next;
					next = current->nextPost;
				} else {
					previous = current;
					current = current->nextPost;
					next = current->nextPost;
				}
			}
		}

	} else if (choice == 2) {	//SORT BY LIKES
		for (int i = 0; i < numPosts; i++) {
			postInfo *current = postList;
			postInfo *next = current->nextPost;
			postInfo *previous = NULL;

			while (next != NULL) {
				if (current->numLikes > next->numLikes) {
					if (current == postList) {
						postList = next;
					} else {
						previous->nextPost = next;
					}
					current->nextPost = next->nextPost;
					next->nextPost = current;
					previous = next;
					next = current->nextPost;
				} else {
					previous = current;
					current = current->nextPost;
					next = current->nextPost;
				}
			}

		}
	} else if (choice == 3) {	//SORT BY COMMENTS
		for (int i = 0; i < numPosts; i++) {
			postInfo *current = postList;
			postInfo *next = current->nextPost;
			postInfo *previous = NULL;

			while (next != NULL) {
				if (current->numComments > next->numComments) {
					if (current == postList) {
						postList = next;
					} else {
						previous->nextPost = next;
					}
					current->nextPost = next->nextPost;
					next->nextPost = current;
					previous = next;
					next = current->nextPost;
				}
				else {
					previous = current;
					current = current->nextPost;
					next = current->nextPost;
				}
			}

		}
	} else if (choice == 4) {	//SORT BY SIZE
		for (int i = 0; i < numPosts; i++) {
			postInfo *current = postList;
			postInfo *next = current->nextPost;
			postInfo *previous = NULL;

			while (next != NULL) {
				if (current->size > next->size) {
					if (current == postList) {
						postList = next;
					} else {
						previous->nextPost = next;
					}
					current->nextPost = next->nextPost;
					next->nextPost = current;
					previous = next;
					next = current->nextPost;
				} else {
					previous = current;
					current = current->nextPost;
					next = current->nextPost;
				}
			}

		}
	}
	return postList;

}

int errorCatcher (int type, char attribute[20]) {

	//1 = Date 	2 = Comments, size, likes
	
	if (type == 1) {		//Date
		if (strlen(attribute) != 8) {	//Check if it's < or > 8 characters
			printf("ERROR: INCORRECT FORMAT! FORMAT: DDMMYYYY, SHOULD BE 8 CHARACTERS!");
			return 1;	//Return error
		} else {
			for (int i = 0; i < 8; i++) {	//Check if date only contains digits (no /'s)
				if (isdigit(attribute[i]) == 0) {
					printf("ERROR: INVALID INPUT (CORRECT FORMAT: DDMMYYYY), DETECTED ALPHABETICAL LETTERS (NUMBERS ONLY!) RETURNING TO MENU\n");
					return 1;
				}
			}
		}
		return 0;
	} else if (type == 2) {
		if (atoi(attribute) == 0) {
			printf("ERROR: INVALID INPUT (SHOULD BE INTEGER #), DETECTED CHARACTERS! RETURNING TO MENU\n");
			return 1;
		}
		return 0;
	}
	return 0;
}
