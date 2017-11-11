/*Eric Pickup
You are given a two-dimensional array of values that specify the height of a terrain
at different points in a square. Write a Java program with the following tasks:
• A method to read the heights of all the points of a terrain and store them into the two-dimensional
array. You can assume that the heights are integer values.
• Constructor of the class
• A method to find the lowest point of the terrain.
• A method to find the highest point of the terrain.
• A method to print out a flood map, showing which of the points in the terrain would be flooded if the
water level was a given value. In the flood map, print a * for each flooded point and a – for each
point that is not flooded. Here is a sample map for a 5 by 5 square:
* * - * -
* - - * *
* * * * *
- - * - *
* * - - -

*/


import java.util.Scanner;

/**
 * TerrainMap Class - allows user to input a 2D array containing points (heights)
 * @author Eric Pickup
 *
 */
public class TerrainMap {
	
	//Instance Variables
	int size;
	int[][] map;
	
	/**
	 * Constructor for a map
	 * @param newMap	Array containing the map points
	 * @param size	Map size (size x size)
	 */
	TerrainMap(int[][] newMap, int size) {
		this.map = newMap;
		this.size = size;
	}
	
	/**
	 * Method to find the lowest point in the map
	 * @return The lowest height
	 */
	int findLowestPoint() {
		
		int lowest = map[0][0];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (map[i][j] < lowest) {
					lowest = map[i][j];
				}
			}
		}
		return lowest;
	}
	
	/**
	 * Method to find the highest point in the map
	 * @return The highest point
	 */
	int findHighestPoint() {
		int highest = map[0][0];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (map[i][j] > highest) {
					highest = map[i][j];
				}
			}
		}
		return highest;
	}
	
	/**
	 * Prints the flood map (any points < the water level will be flooded and marked as *)
	 * @param waterLevel	Height of the water
	 */
	void printFloodMap(int waterLevel) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (map[i][j] < waterLevel) {
					System.out.printf("* ");
				} else {
					System.out.printf("- ");
				}
			}
			System.out.printf("\n");
		}
	}
	
	/**
	 * Prints the map
	 */
	void printMap() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.printf("%d ", map[i][j]);
			}
			System.out.printf("\n");
		}
	}
	
	public static void main(String args[]) {
		
		System.out.println("Enter the map size: ");
		Scanner input = new Scanner(System.in);
		int size = input.nextInt();
		int[][] mapArray = new int[size][size];
		int currentPoint;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				currentPoint = input.nextInt();
				mapArray[i][j] = currentPoint;
			}
		}
		TerrainMap newMap = new TerrainMap(mapArray,size);
		newMap.printMap();
		newMap.printFloodMap(3);
		
	}
	
}
