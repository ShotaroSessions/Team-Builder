package com.ssessions.teambuilder.systems;


import javax.swing.JOptionPane;

/**
 * Sorting engine to sort a Teammate[] into diverse groups with weighted elements.
 * <p>
 * Similarly to {@link TryDisperse}, the sorting method evaluates a large number
 * of randomized groups, however, the PrioritySort sorting method first sorts
 * the randomized list by the first priority element using {@link #elemSort(int,Teammate[],int)}. 
 * <p>
 * If there is only one priority element, the sort uses the generic {@link TryDisperse#evaluate(Teammate[][])}
 * to evaluate the lists. With two or three priority elements, the sort uses {@link #evaluate(Teammate[][], int[])},
 * an evaluation method that takes into account up to two weighted elements.
 * 
 * @author Shotaro Sessions
 *
 * @see TryDisperse
 * @see #sort(int, Teammate[], int[])
 */
public class PrioritySort extends TryDisperse {

	/**
	 * PrioritySort sorting engine constructor.
	 * 
	 * @param dataMax The highest possible values for each element
	 */
	public PrioritySort(int[] dataMax) {
		super(dataMax);
	}
	
	/**
	 * The Team Builder priority sorting function
	 * Called by {@link SystemInterface#sortRoster(int)}
	 * <p>
	 * This sorting function is used by the System Interface
	 * when the user initiates a sort with one or more priority
	 * elements specified. This sort method creates <code>int</code>
	 * {@link #permutations} number of random permutations
	 * of an original Teammate[] using {@link #randomize(Teammate[])}.
	 * It disperses the roster into groups based on only the 
	 * first priority element using {@link #elemSort(int, Teammate[], int)} 
	 * and evaluates each set of groups using {@link TryDisperse#evaluate(Teammate[][])}
	 * if there is only one priority element set, if more than one
	 * priority element is given, the method uses {@link #elemSort(int, Teammate[], int)}
	 * The set of groups that evaluates to the smallest number 
	 * is returned
	 * 
	 * @param groups	The number of groups to divide the group into
	 * @param roster	The Teammate roster to sort
	 * @param elements	An array indicating which elements to prioritize (the lower the index the higher the priority)
	 * @return			The Teammate[][] that evaluated to the smallest number
	 */
	public Teammate[][] sort(int groups, Teammate[] roster, int[] elements) {
		
		Teammate[][] returnGroups = null;
		Teammate[] tempRoster = roster.clone();
		Teammate[][] tempGroups;
		int[] secondaryElems = new int[elements.length - 1];
		
		Integer currResult;
		
		Integer min = Integer.MAX_VALUE;
		
		for(int i = 1; i < elements.length; i++) secondaryElems[i-1] = elements[i];
		
		for(int i = 0; i < permutations; i++) {
			
			tempRoster = randomize(tempRoster);
			tempGroups = elemSort(elements[0],tempRoster,groups);
			
			if(elements.length == 1) currResult = evaluate(tempGroups);
			else currResult = evaluate(tempGroups, secondaryElems);
			
			if(currResult < min) {
				min = currResult;
				returnGroups = new Teammate[tempGroups.length][];
				for(int j = 0; j < tempGroups.length; j++)
					returnGroups[j] = tempGroups[j].clone();
			}
			
		}
		
		
		return returnGroups;
	}
	

	private Teammate[][] elemSort(int element, Teammate[] roster,int groups) {
		Teammate[][] returnGroups = groupMaker(groups,roster);
		
		int groupIndex = 0;
		int studentIndex = 0;
		
		for(int elementIndex = 0; elementIndex < dataMax[element]; elementIndex++) {
			for(int rosterIndex = 0; rosterIndex < roster.length; rosterIndex++) {
				if(roster[rosterIndex].data(element)-1==elementIndex) {
					
					if(groupIndex != returnGroups.length) 
						returnGroups[groupIndex++][studentIndex] = roster[rosterIndex];
					else {
						groupIndex = 0;
						returnGroups[groupIndex++][++studentIndex] = roster[rosterIndex];
					}
				}
			}
		}
		
		return returnGroups;
	}

	private int evaluate(Teammate[][] groups, int[] elements) {
		
		int element = elements[0];
		int dupes1 = 0;
		int dupes2 = 0;
		
		for(Teammate[] group : groups) {
			
			int[] dupeCounter = new int[dataMax[element]];
			
			for(Teammate t : group) {
				dupeCounter[t.data(element)-1]++;
			}
			
			for(int i : dupeCounter) {
				dupes1 += i^2;
				if(i==0) dupes1++;
			}
			
		}
		
		if(elements.length == 2) {
			
			element = elements[1];
			
			for(Teammate[] group : groups) {
				
				int[] dupeCounter = new int[dataMax[element]];
				
				for(Teammate t : group) {
					dupeCounter[t.data(element)-1]++;
				}
				
				for(int i : dupeCounter) {
					dupes2 += i^2;
					if(i==0) dupes2++;
				}
				
			}
			
			dupes1 += dupes2/2;
		}
		
		return dupes1;
	}
	
}
