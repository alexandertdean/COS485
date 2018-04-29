package student;
/* 
 * This Student class is meant to contain your algorithm.
 * You should implement the two static methods:
 *   estimatePegJump - does a monte carlo estimation of the number or promising
 *                     nodes in the search tree
 *   solvePegJump - finds a solution and the number of nodes examined in the search
 *                  it should fill in the jumpList argument with the jumps that form
 *                  your solution
 *
 * The input is a PegJumpPuzzle object, which has:
 *   a size, the number of holes numbered 0 .. size()-1
 *   the startHole that is initially empty
 *   an ArrayList of allowed jumps, which are triples (from, over, dest)
 *   a jump takes the peg 'from' over the peg in 'over' (removing it) and into 'dest'
 */
import java.util.ArrayList;
import java.util.Iterator;
import pegJump.*;

public class PegJump {

	// returns a monte-carlo estimate of the number of promising nodes in the
	// search tree.
	public static double estimatePegJump(PegJumpPuzzle puzzle) {
		double finalValue = 1;
		boolean pegs[] = new boolean[puzzle.numHoles()];  // hole numbers start at 0
		for (int i = 0; i < puzzle.numHoles(); i++)  
			pegs[i] = true;                      // fill all holes
    	pegs[puzzle.getStartHole()] = false;  
		for (int i = 0; i < pegs.length - 1; i++)
		{
			int from;
			int over;
			int dest;
			double innerValue = 0;
			Jump actualJump = new Jump(1, 2, 3);
			Iterator<Jump> jumpIter = puzzle.jumpIterator();
			while (jumpIter.hasNext())
			{
				Jump j = jumpIter.next();
				from = j.getFrom();
				over = j.getOver();
				dest = j.getDest();
				if (pegs[from] && pegs[over] && !pegs[dest]) {
					innerValue++;
					actualJump = j;
				}
			}
			if (innerValue == 0) return finalValue;
			pegs[actualJump.getFrom()] = false;
			pegs[actualJump.getOver()] = false;
			pegs[actualJump.getDest()] = true;
			finalValue = finalValue + finalValue * innerValue;
		}
		return finalValue;
	}
	
	// simple example routine that just repeatedly finds the first valid
	// jump until it fails
	// this returns:
	//   the number of jumps tried
	//   and as a modifiable argument, it fills in the jumpList
	public static double solvePegJump(PegJumpPuzzle puzzle, ArrayList<Jump> jumpList) 
	{	
	    // initialize the puzzle
		// make an array to keep tracks of where the pegs are
		// and put pegs in all holes except the starting hole
		boolean pegs[] = new boolean[puzzle.numHoles()];  // hole numbers start at 0
		for (int i = 0; i < puzzle.numHoles(); i++)  
			pegs[i] = true;                      // fill all holes
    	pegs[puzzle.getStartHole()] = false;     // clear starting hole
    	
    	// start doing jumps
    	return doRecursiveJump(puzzle, jumpList, pegs);
	}	

	private static double doRecursiveJump(PegJumpPuzzle puzzle, ArrayList<Jump> jumpList, boolean[] pegs)
	{
		int jumpCnt = 0;
		Iterator<Jump> jitr = puzzle.jumpIterator();
		while (jitr.hasNext()) {
			Jump j = jitr.next();

			int from = j.getFrom();
			int over = j.getOver();
			int dest = j.getDest();
			if (pegs[from] && pegs[over] && !pegs[dest]) {
				// found a valid jump
				jumpList.add(j);       // add to the result list
				pegs[from] = false;    // do the jump
				pegs[over] = false;
				pegs[dest] = true;
				jumpCnt++;
				jumpCnt += doRecursiveJump(puzzle, jumpList, pegs);
				if (done(pegs, puzzle))
				{
					return jumpCnt;
				}
				else 
				{
					pegs[from] = true;
					pegs[over] = true;
					pegs[dest] = false;
					jumpList.remove(jumpList.size() - 1);
				}
			}
		}
		return jumpCnt;
	}
	
	private static boolean done(boolean[] pegs, PegJumpPuzzle puzzle)
	{
		int numTrue = 0;
		for (int i = 0; i < pegs.length; i++)
		{
			if (pegs[i]) numTrue++;
		}
		if (numTrue <= 1)
		{
			return pegs[puzzle.getStartHole()];
		}
		else return false;
	}
	
}
