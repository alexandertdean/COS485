package student;
/* 
 * This Student class is meant to contain your algorithm.
 * You should implement the static method:
 *   solveTSP - Finds a tour that visits all the vertices and then returns to the starting
 *   vertex.  It does not have to be optimal, but shorter is better.
 *   It should return the length of the tour as the return value,
 *   and it should mark the edges that are used by your tour and the vertices
 *   that are visited.
 *
 * The input is a Graph object, which has:
 *   an ArrayList of all vertices - use graph.vertexIterator()
 *   an ArrayList of all edges - use graph.edgeIterator()
 *   each vertex has an ArrayList of its edges - use vertex.edgeIterator()
 *   
 *   see the documentation for: Graph, Vertex, and Edge for more details
 *   
 * You can animate your algorithm by periodically calling TSPtester.show(g).
 * 
 * If in your algorithm you want to save a copy of the graph, for instance 
 * the best solution found so far, you can clone it with.
 * 		static Graph bestSolution;   // a global class variable
 * 		...
 * 		bestSolution = g.clone();    // presumably called from a searching method 
 * 
 * To copy the best solution back into the original graph use:
 * 		g.copyMarks(bestSolution);
 */
import java.util.*;

import graph.*;
import tsp.TSPtester;

public class TSP
{
	private static boolean done = false;
	private static int numMarked = 0;
	private static Vertex startingVertex;
	private static Vertex problemChild;
	
	// simple example routine that just repeatedly finds the first valid
	// edge until it fails
	// this returns:
	//   the length of the path that it found until it got stuck
	//   it marks the vertices and edges in the graph that are on the path	
	public static int solveTSP(Graph g) 
	{	
		done = false;
		numMarked = 0;
		problemChild = null;
		int length = 0;
		Iterator<Vertex> vitr = g.vertexIterator();
		/*if (g.numVertices() == 10)
		{*/
		// sort each vertex's edges shortest first
		g.sortVertexEdgeLists(new Graph.CompareEdgesSmallestFirst());
		
		Vertex v = g.vertexIterator().next();
		startingVertex = v;
		return recSolve(g, v);
		
		/*
		// just start at first vertex
		
		g.clear();  // clear all marks and values
		
		// start building a path, mark vertices so don't revisit them
		
		v.setMark(1);
		boolean stuck;
		do {  // build path until stuck
			Iterator<Edge> itr = v.edgeIterator();
			stuck = true;    // set to false if it find a good edge
			while (itr.hasNext()) {
				Edge e = itr.next();
				Vertex newv = e.getOppositeVertexOf(v);
				if (newv.getMark() == 0) { // found an unmarked vertex
					e.setMark(1);
					newv.setMark(1);
					length += e.getWeight();
					v = newv;
					stuck = false;
					break;  // search form this new vertex in outer loop
				}
			}
			
			TSPtester.show(g); // this will animate the algorithm at each step 
		} while (!stuck);
		}
		return 0;*/

	}
	
	private static int recSolve(Graph g, Vertex v)
	{
		int length = 0;
		v.setMark(1);
		TSPtester.show(g);
		numMarked++;
		Iterator<Edge> eitr = v.edgeIterator();
		while (eitr.hasNext())
		{
			Edge edge = eitr.next();
			if (edge.getOppositeVertexOf(v).getId() == startingVertex.getId())
			{
				if (numMarked == g.numVertices()) 
				{
					done = true;
					edge.setMark(1);
					TSPtester.show(g);
					return edge.getWeight();
				}
			}
			else if (edge.getOppositeVertexOf(v).getMark() != 1)
			{
				edge.setMark(1);
				length = recSolve(g, edge.getOppositeVertexOf(v));
				if (!done)
				{
					edge.setMark(0);
					TSPtester.show(g);
				}
				else
				{
					return length + edge.getWeight();
				}
			}
		}
		v.setMark(0);
		TSPtester.show(g);
		numMarked--;
		return 0;
	}
}


