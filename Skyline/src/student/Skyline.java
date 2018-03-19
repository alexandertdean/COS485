package student;

/* 
 * This Skyline class is meant to contain your algorithm.
 * You should implement the static method: findSkyline
 * The input is an ArrayList of Point objects representing the buildings.
 * The output should be a new ArrayList of Point objects with all the buildings
 *  merged into a single profile. (Or more than 1 profile if they are non-overlapping.)
 */
import java.awt.Point;
import java.util.ArrayList;

public class Skyline
{
	static final int testcase = 6;
	static int countCall = 0;
	static boolean done;
	// Example routine that just looks at the input list of points 
	// and constructs a bounding rectangle.
	// This is interesting, but not the correct solution to this problem.
	public static ArrayList<Point> findSkyline(ArrayList<Point> input) 
	{	
		ArrayList<Point> leftResult;
		ArrayList<Point> rightResult;
		if (input.size() <= 2)
		{
			return input;
		}
		else 
		{
			ArrayList<Point> returnVal = new ArrayList<Point>();
			leftResult = findSkyline(new ArrayList<Point>(input.subList(0, (input.size() / 2) - ((input.size() / 2) % 2))));
			rightResult = findSkyline(new ArrayList<Point>(input.subList(input.size()/2 - ((input.size() /2) % 2), input.size())));
			int leftHeight = 0;
			int rightHeight = 0;
			int leftIter = 0;
			int rightIter = 0;
			
			while (leftIter < leftResult.size() && rightIter < rightResult.size())
			{
				Point left = leftResult.get(leftIter);
				Point right = rightResult.get(rightIter);
				
				if (left.x < right.x) // handle the left point next
				{
					// if the point is higher than both skylines at this point, add it
					if (left.y > rightHeight && left.y > leftHeight)
					{
						returnVal.add(left);
					}
					else
					{
						// if the right skyline is higher at this point, it takes over
						if (rightHeight >= left.y)
						{
							if (rightHeight != returnVal.get(returnVal.size() - 1).y) returnVal.add(new Point(left.x, rightHeight));
						}
						else if (left.y < leftHeight) 
						{
							if (left.x != returnVal.get(returnVal.size() -1).x) returnVal.add(left);
						}
					}
					leftIter++;
					leftHeight = left.y;
				}
				else if (left.x > right.x)// handle the right point next
				{
					
					// if the point is higher than both skylines at this point, add it
					if (right.y > leftHeight && right.y > rightHeight)
					{
						returnVal.add(right);
					}
					else
					{
						// if the left skyline is higher at this point
						if (leftHeight >= right.y)
						{
							if (leftHeight != returnVal.get(returnVal.size() - 1).y) returnVal.add(new Point(right.x, leftHeight));
						}
						else if (right.y < rightHeight) 
						{
							if (right.x != returnVal.get(returnVal.size() -1).x) returnVal.add(right);
						}
					}
					rightIter++;
					rightHeight = right.y;
				}
				// same x coordinate
				else
				{
					returnVal.add(new Point(left.x, Math.max(left.y, right.y)));
					leftHeight = left.y;
					rightHeight = right.y;
					rightIter++;
					leftIter++;
				}
			}
			for (int i = leftIter; i < leftResult.size(); i++)
			{
				returnVal.add(leftResult.get(i));
			}
			for (int i = rightIter; i < rightResult.size(); i++)
			{
				returnVal.add(rightResult.get(i));
			}
			return returnVal;
		}
	}	
}