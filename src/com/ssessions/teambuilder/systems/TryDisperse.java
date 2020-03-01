package com.ssessions.teambuilder.systems;


import java.util.Random;


/**
 * Sorting engine to sort a Teammate[] into diverse groups.
 * <p>
 * The main functionality of this class is to evaluate a large number
 * of randomized {@link Teammate} arrays based on an original Teammate[]
 * using {@link #evaluate(Teammate[][])}, a method that generates an <code>int</code> 
 * score based on the number of duplicate elements (smaller <code>int</code> = less duplicates).
 * <p>
 * This Class extends the more simple {@link TeamSort}, used
 * to divide a Teammate[] roster into a designated number of
 * groups. This Class uses the {@link #groupMaker(int, Teammate[])}
 * method of {@link TeamSort}.
 * <p>
 * This Class is the parent of {@link PrioritySort}, a method
 * that accounts for prioritized elements, instances of
 * {@link PrioritySort} may use {@link #evaluate(Teammate[][])}
 *
 * @author 		Shotaro Sessions
 *
 * @see 		TeamSort
 * @see 		PrioritySort
 * @see			#sort(int, Teammate[])
 * @see 		#evaluate(Teammate[][])
 * @see 		TeamSort#groupMaker(int, Teammate[])
 */
public class TryDisperse extends TeamSort{

	//Number of random permutations evaluated
	protected int permutations = 5000000;
	
	//dataMax array see constructor
	protected int[] dataMax;
	
	/**
	 * TryDisperse sorting engine constructor.
	 * 
	 * @param dataMax The highest possible values for each element
	 */
	public TryDisperse(int[] dataMax) {
		this.dataMax = dataMax;
	}

	/**
	 * The Team Builder default sorting function.
	 * Called by {@link SystemInterface#sortRoster(int)}
	 * <p>
	 * This sorting function is used by the SystemInterface
	 * when the user initiates a sort without specifying priority
	 * elements. This sort method creates <code>int</code>
	 * {@link #permutations} number of random permutations
	 * of an original Teammate[] using {@link #randomize(Teammate[])}.
	 * It divides each permutation into groups using {@link TeamSort#groupMaker(int, Teammate[])} 
	 * and evaluates each set of groups using {@link #evaluate(Teammate[][])}.
	 * The set of groups that evaluates to the smallest number
	 * is returned
	 * 
	 * @param groups	The number of groups to divide the group into
	 * @param roster	The Teammate roster to sort
	 * @return			A Teammate[][] of Teammate arrays that evaluated
	 * 					to the smallest value
	 * 
	 * @see 			#randomize(Teammate[] roster)
	 * @see 			#evaluate(Teammate[][] groups)
	 * @see 			TeamSort#groupMaker(int, Teammate[])
	 * @see 			SystemInterface#sortRoster(int)
	 * 
	 */
	public Teammate[][] sort(int groups, Teammate[] roster) {
		
		int min;
		Teammate[] returnRoster = new Teammate[roster.length];
		Teammate[] tempRoster = new Teammate[roster.length];
		
		tempRoster = roster.clone();
		returnRoster = roster.clone();
		
		//evaluate tempRoster, set min to the value
		min = evaluate(groupMaker(groups, tempRoster));
		
		for(int i = 0; i < permutations; i++) {
			
			tempRoster = randomize(tempRoster);
			int dupes = evaluate(groupMaker(groups, tempRoster));
			if(dupes <= min) {
				min = dupes;
				returnRoster = tempRoster.clone();
			}
		}
		
		return groupMaker(groups,returnRoster);
	}
	
	/**
	 * General evaluation method without priority elements, see
	 * {@link PrioritySort#evaluate(Teammate[][], int[])} for the 
	 * version that accounts for priority elements.
	 * <p>
	 * This method evaluates the Teammate[][] and returns a score
	 * based on the number of duplicates. The lower the score, the
	 * less duplicates on average in each group
	 * 
	 * @param groups	Teammate[][] to evaluate
	 * @return			An <code>int</code> score based on the number of 
	 * 					duplicate elements
	 * 
	 * @see				PrioritySort#evaluate(Teammate[][], int[])
	 */
	protected int evaluate (Teammate[][] groups) {
		
		int dupes = 0;
		
		for(int dataType = 0; dataType < dataMax.length; dataType++) {
			
			for(Teammate[] group : groups) {
					
				int[] dupeCounter = new int[dataMax[dataType]];
				
				for(Teammate t : group) {
					dupeCounter[t.data(dataType)-1]++;
				}
				
				for(int i : dupeCounter) {
					dupes += i^2;
					if(i==0) dupes++;
				}
			}
		}
		
		return dupes;
	}
	
	/**
	 * Generates a randomized version of the Teammate[]
	 * passed in.
	 * <p>
	 * Used by the {@link #sort(int, Teammate[])} as well as the sort
	 * methods within {@link PrioritySort}
	 * 
	 * @param roster	Teammate[] to randomize
	 * @return			randomized Teammate[]
	 * 
	 * @see 			#sort(int, Teammate[])
	 * @see 			PrioritySort
	 */
	protected Teammate[] randomize(Teammate[] roster) {
		
		Random rgen = new Random();  
		
		for (int j = 0; j < roster.length; j++) {
		    int randomPosition = rgen.nextInt(roster.length);
		    Teammate temp = roster[j];
		    roster[j] = roster[randomPosition];
		    roster[randomPosition] = temp;
		}
		
		return roster;
	}
	
}
