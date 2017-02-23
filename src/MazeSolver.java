/**
 *  
 * This program reads a text file to create a maze of hexagonal tiles with a start tile, wall tiles,
 * end tiles and unvisited tiles. <p>
 * 
 * This MazeSolver class will start at the start tile and search for
 * the exit(s) of the maze using a stack to keep track of tiles yet to be checked.
 * 
 * @author Edward Kim 
 * 
 */


import java.io.IOException;
import java.util.Stack;

public class MazeSolver {
	/**
	 * This is the main method using the Hexagon and Maze classes to solve the maze.
	 * @param args stores any tokens following MazeSolver
	 */
	public static void main(String[] args){
		try{
			//In case an argument is not provided, throw an exception.
			if (args.length < 1){
				throw new IllegalArgumentException("No Maze Provided");
			}
			
			//Pass name of file as command line argument 0.
			String mazeFileName = args[0];
			
			//Create maze object.
			Maze objMaze = new Maze(mazeFileName);
			
			//Obtain a reference to the Start Hexagon in the Maze.
			Hexagon beginSearch = objMaze.getStart();
			
			//Create a stack and push the starting hexagon. Call repaint() to refresh and show the animation.
			Stack<Hexagon> hexPositions = new Stack<Hexagon>();
			hexPositions.push(beginSearch);
			objMaze.repaint();
			
			//Initialize variables to keep track of hexagons processed and exits found.
			int numProcessed = 0;
			int numExits = 0;
			
			//Begin search with while loop when stack isn't empty and still exits left to find.
			while (!hexPositions.isEmpty() && numExits < objMaze.getNumExits()){
				//Start search by popping from the stack, mark as processed and increment numProcessed and
				//if the stack is empty, throw EmptyCollectionException
				beginSearch = hexPositions.pop();
				if (hexPositions.isEmpty()){
					throw new EmptyCollectionException(mazeFileName);
				}
				beginSearch.setProcessed();
				objMaze.repaint();
				numProcessed++;
				//For each neighbouring hexagon on each side (6 sides, so 0-5)
				for (int i = 0; i <= 5; i++){
					//Create Hexagon object to call getNeighbour().
					Hexagon neighbour = beginSearch.getNeighbour(i);
					//In case of NullPointerExceptions.
					if (neighbour == null)
						continue;
					//If neighbouring hexagon is unvisited and is an exit, mark as processed and increment numExits.
					if (neighbour.isUnvisited() == true && neighbour.isEnd() == true){
						neighbour.setProcessed();
						objMaze.repaint();
						numExits++;
					}
					//If neighbouring hexagon is unvisited but isn't an exit, push and check other neighbours.
					else if (neighbour.isUnvisited() == true && neighbour.isEnd() == false){
						hexPositions.push(neighbour);
						neighbour.setPushed();
						objMaze.repaint();
					}
				}
			}
			
			//Print the results with an if-statement for when all the required exits are found and when
			//not enough exits were found.
			if (numExits == objMaze.getNumExits()){
				System.out.println("The required number of " + numExits + " exit(s) were found in " + numProcessed + 
						" steps.");
			}
			else if (numExits < objMaze.getNumExits()){
				System.out.println("Not enough exits were found! " + numExits + " out of " + objMaze.getNumExits() + 
						" required exits were found in " + numProcessed + " steps.");
			}
		}
		//Handle IOException, FileNotFoundException
		catch (IOException e){
			e.printStackTrace();
		}
		catch (EmptyCollectionException f){
			f.printStackTrace();
		}
		catch (NullPointerException g){
			g.printStackTrace();
		}
	}

}
