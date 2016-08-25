/*----------------CACHE SIMULATOR--------------------*
 * This is a cache simulator that will print out the *
 * 	   of a CPU cache. This program takes 6 inputs,  *
 *     												 *
 *     1. Cachesize 								 *
 *     2. Blocksize									 *
 *     3. Associativity                              *
 *     4. Policy (LRU or FCFS)                       *
 *     5. Tracking (on or off)                       *
 *     6. File (containing the memory accesses       *
 *                                                   *
 *     after those three arguments are chosen, the   *
 *     program will then read from the file and      *
 *     create objects for the cache, memory, set and *
 *     block. 										 *
 * 													 */



package Assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
		//Initialize variables
		public static int accesses = 0;
		public static int associativity;
		public static MemAddress address;
		public static int blockAddress;
		private static int blockHit;
		public static int blocknum;
		public static int blocksPerSet;
		public static int BlockSize;
		public static int CacheSize;
		public static String ctags;
		public static int hits = 0;
		public static String HitorMiss;
		public static int intAddress;
		public static long longAddress;
		public static String line;
		public static int misses = 0;
		public static double missRatio;
		public static String policy;
		public static int setnum;
		public static String tracking;
	
	//Main function
	public static void main(String[] args) throws FileNotFoundException {
		int CacheSize = Integer.parseInt(args[0]);												//first argument
		int BlockSize = Integer.parseInt(args[1]);												//second argument
		associativity = Integer.parseInt(args[2]);												//third argument
		policy = args[3];																		//fourth argument
		tracking = args[4];                                                                     //fifth argument
		Scanner input = new Scanner(new File(args[5]));											//sixth argument
		if(CacheSize < BlockSize || CacheSize <= 0 || BlockSize <= 0){							//error checking on user input
			System.err.printf("Invalid Cache size and/or blocksize");							//System error message
			System.exit(0); 																	//terminate the program
		}
		if(associativity < 0 || associativity > (CacheSize - BlockSize))						//This calculates the number of blocks per set
			associativity = -1;
		CacheSize = (int) (Math.pow(2, CacheSize));
		BlockSize = (int) (Math.pow(2, BlockSize));
		blocknum = CacheSize / BlockSize;
		if(associativity == -1)
			blocksPerSet = blocknum;
		else																					//If the cahce is fully associative
			blocksPerSet = (int) Math.pow(2, associativity);
		setnum = (int) (blocknum / blocksPerSet);												//Calculates the number of sets
		if(tracking.equals("on")){																//Prints the header for the results if tracking is on
			System.out.printf("%8s %8s %8s %8s %8s %7s %7s %7s %10s",
							  "addr", "atag", "set", "h/m", "#hits", 
							  "#misses", "#access", "ratio",
							  "tags\n");
		}
		if(!tracking.equals("on") && !tracking.equals("off")){									//Error checking on tracking
			System.err.printf("The tracker must be either \"on\" or \"off\".");
			System.exit(-1);
		}
		Cache Cache = new Cache(setnum, blocksPerSet);											//Creates new Cache object
		while(input.hasNext()){																	//reads from the user input file until EOF
			line = input.next();
			if(line.matches("-?[0-9]+")){														//checks if the address is in decimal
				longAddress = Long.parseLong(line);
				address = new MemAddress(longAddress, CacheSize, BlockSize, associativity);
			}
			if(line.matches("^(0x|0X)[a-fA-F0-9]+$")){											//checks if the address is in hex
				line = line.replaceAll("0x", "");
				longAddress = Long.parseLong(line ,16);
				address = new MemAddress(longAddress, CacheSize, BlockSize, associativity);
			}
			Set CurrSet = Cache.accessSet(address.getSetNum());									//Sets the current set
			blockHit = CurrSet.hitOrMiss(address.getHexTag());									//If there is a hit, it updates the counter
			if(CurrSet.getTags() == null)														//if there is not a current tag, it prints blank
				ctags = "";
			else																				//if there is a tag, it sets it to ctags
				ctags = CurrSet.printSet();
			if(blockHit >= 0){																	//if there is a hit and is LRU, it updates the address hit
				if(policy.equals("lru"))
					CurrSet.updateAge(blockHit, accesses + 1); 
				hits++;
				HitorMiss = "hit";
			}else{																				//If there is a miss
				if(CurrSet.isFull() == -1){														//set is full, replace based on the algorithm(LRU or FCFS)
					CurrSet.replace(accesses + 1, address.getHexTag());
				}
				else{																			
					CurrSet.addTag(CurrSet.isFull(), accesses + 1, address.getTag());           //if the set is not full, add to it
				}
				misses++;
				HitorMiss = "miss";
			}
			accesses++;																			//update total cache accesses
			CurrSet.printSet();
			missRatio = (double) misses/accesses;												//calculate the miss ratio
			if(tracking.equals("on")){															//if tracking is on, print out the results as it runs
				System.out.printf("%8s %8s %8s  %8s %7d %7d %7d %10.8f %-8s\n",
						  address.getHexValue(), address.getHexTag(), address.getSet(),
						  HitorMiss, hits, misses, accesses, missRatio, ctags);
			}
		}
		System.out.print("Jordan Meurer\n");													//my name
		for(int i = 0; i < args.length; i++){													//prints out the given arguments in a loop
			System.out.printf("%s ", args[i]);
		}
		System.out.printf("\naccesses: %d\n"													//prints out the ending results (tracking on and off)
				+         "hits: %d\n"
			          	+ "misses: %d\n"
			        	+ "miss ratio: %.8f\n", accesses, hits, misses, missRatio);
		input.close();																			//close the open file
	}
}