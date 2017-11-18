/**
 * Eric Pickup
 * 03-60-254 Assignment #4
 * 
 * Write a program to help a droid navigate a maze of rooms. Your program should
 * help the droid to find the shortest direct path from a start room to an ending
 * room in the maze. The input will be a maze read from a text file. The file
 * has the following structure:
 * 
 * First line contains an int representing the number of rooms in the maze
 * Second/Third line contains an int representing the ID of the starting/ending rooms
 * Rest of file contains the maze represented as a 2-D square matrix. The row and column
 * indices represent the room ID. Ex. If maze[3][7] = 1, there is a door from room 3 to 7.
 * If maze[4][1] = 0, this means there is no door from room 4 to room 1.
 * EX:
 * Input:					Output:
 *  8						0, 5, 3, 6
 *	0
 *	6
 *	1,1,0,0,1,1,0,0
 *	1,1,0,0,1,0,0,0
 *	0,0,1,1,0,0,0,1
 *	0,0,1,1,0,1,1,0
 *	1,1,0,0,1,1,0,0
 *	1,0,0,1,1,1,0,0
 *	0,0,0,1,0,0,1,1
 *	0,0,1,0,0,0,1,1
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;

public class assign4 {

	public static void main(String args[]) throws FileNotFoundException {

		// ~~~~~~~~~~~ READING INPUT AND BUILDING ADJACENT LIST STRUCTURE  ~~~~~~~~~~~~~~~~~

		String inputFile = "input.txt";
		Scanner input = new Scanner(System.in);
		File file = new File(inputFile);
		input = new Scanner(file);

		int numRows = input.nextInt();
		int startRoom = input.nextInt();
		int endRoom = input.nextInt();
		input.nextLine();
		String line = null;

		ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>(); // Creating an Adjacent List which
																						// contains each room with a
																						// list of its adjacent rooms
		String[] currentRow;
		for (int i = 0; i < numRows; i++) { // Adding adjacent rooms for each room

			adjList.add(new ArrayList<Integer>());
			line = input.nextLine();
			currentRow = line.split(",");

			for (int j = 0; j < numRows; j++) {
				if (Integer.parseInt(currentRow[j]) == 1 && i != j) {
					adjList.get(i).add(j);
				}
			}
		}

		input.close();

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// ~~~~~~~~~~~~~~~~~ MODIFIED VERSION OF BFS ALGORITHM ~~~~~~~~~~~~~~~~~~~~~

		Queue<Integer> queue = new ArrayDeque<>(); // Queue for BFS
		HashSet<Integer> seen = new HashSet<>(); // Hash set that contains all nodes that have been visited by BFS
		HashMap<Integer, Integer> pathHistory = new HashMap<Integer, Integer>(); // Hash map that keeps track of which
																					// parent node discovered the index
																					// node first (key = node, value =
																					// parent who discovered key node)
		queue.add(startRoom);

		while (!queue.isEmpty()) {

			int node = queue.poll();

			if (!seen.contains(node)) { // If the current node hasn't been visited
				for (int i = 0; i < adjList.get(node).size(); i++) { // For each neighbor of the current node
					
					queue.add(adjList.get(node).get(i)); // Add the neighbor to the queue
					
					if (!pathHistory.containsKey(adjList.get(node).get(i))) { // If the neighbor hasn't already been
																				// discovered
						pathHistory.put(adjList.get(node).get(i), node); // Add the neighbor to the hash map and mark
																			// which node discovered it
					}
				}
				seen.add(node); // Mark the node as visited
			}
		}

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// ~~~~~~~~~~~~~~~~~~~~ FINAL CALCULATIONS / PRINTING ~~~~~~~~~~~~~~~~~~~~~~~~

		if (!pathHistory.containsKey(endRoom)) { // If the destination node was never reached (looping case)
			System.out.println("There is no path to the end room!");
		} else {

			int current = endRoom; // Traverse the shortest path backwards (cannot go from beginning to end with
									// the way the hash map is designed)
			ArrayList<Integer> finalList = new ArrayList<Integer>(); // Final list that contains the shortest path
																		// (backwards)
			finalList.add(endRoom);

			while (current != startRoom) { // Starting from the end node, list each discovering node all the way up to
											// starting node (this is shortest path)
				current = pathHistory.get(current);
				finalList.add(current);
			}

			Collections.reverse(finalList); // Reverse the list so that it goes from beginning to end

			System.out.printf("%d", finalList.get(0));
			for (int i = 1; i < finalList.size(); i++) { // Print final results
				System.out.printf(", %d", finalList.get(i));
			}
		}

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	}

}
