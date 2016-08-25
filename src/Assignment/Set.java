package Assignment;

public class Set {
	private Block[] blocks;
	private int blocksPerSet;
	private String ctags;
	private int i;
	private int j;
	private int CurrentSets;
	private int Oldest;
	private Block temp;
	private int SmallestValue;
	private StringBuilder Finaltags;

	//Creates the blocks for each set
	public Set(int blocksPerSet){
		this.blocksPerSet = blocksPerSet;
		blocks = new Block[blocksPerSet];
		for(i = 0; i < blocksPerSet; i++)
			blocks[i] = new Block();
	}
	
	public Block accessBlock(int i){							              	//way to access individual blocks
		return blocks[i]; 
	}
	
	public int isFull(){                                                       	 //Goes through the set checking for open blocks
		for(i = 0; i < blocksPerSet; i++){
			if(blocks[i].getValid() == false)
				return i;
		}
		return -1;
	}
	
	
	public int hitOrMiss(String addressTag){     								//returns 1 on a hit or a 0 on a miss
		if(blocks[0].getHexTag() == null)
			return -1;
		for(i = 0; i < blocksPerSet; i++){
			if(blocks[i].getValid() == false)
					return -1;
			if((blocks[i].getHexTag()).equals(addressTag))
				return i;
		}
		return -1;
	}
	
	public void updateAge(int block, int age){                               	//returns the blocks age
		blocks[block].updateAge(age);
		sortBlocks();
	}
	
	public void addTag(int block, int age, int tag){
		blocks[block].addTag(tag, age);
	}
	
	public void replace(int age, String address){								//replaces the last 
		Oldest = getOldest();
		blocks[Oldest].updateBlock(age, address);
		if(blocksPerSet > 1)
			sortBlocks();
	}
	
	public void sortBlocks(){													//sorts the blocks in the correct order (least to greatest)
		CurrentSets = getCurrentSets();
		if(CurrentSets == 1){
			return;
		}else{
			for(i = 0; i < CurrentSets - 1; i++){
				for(j = i + 1; j < CurrentSets; j++){
					if((Integer.parseInt(blocks[i].getHexTag(), 16)) > (Integer.parseInt(blocks[j].getHexTag(), 16))){
						temp = blocks[j];
						blocks[j] = blocks[i];
						blocks[i] = temp;
					}
				}
			}
		}
	}
	
	public String getTags(){													//gets the tags from the current set
		ctags = null;
		CurrentSets = getCurrentSets();
		if(blocks.length == 1)
			ctags = Integer.toString(blocks[0].getAge());
		else{
			if(CurrentSets == 0){
				return null;
			}else{
				for(i = 0; i < CurrentSets; i++){
					ctags = blocks[i].getHexTag();
				}
			}
		}
		return ctags;
	}
	
	public int getCurrentSets(){												//returns the current set
		CurrentSets = 0;
		while(blocks[CurrentSets].getValid() == true){
			CurrentSets++;
			if(CurrentSets == blocksPerSet)
				break;
		}
		return CurrentSets ;
	}
	
	public int getOldest(){														//gets the oldest for LRU
		CurrentSets = getCurrentSets();
		Oldest = 0;
		SmallestValue = blocks[0].getAge();
		if(CurrentSets == 1){
			return 0;
		}
		else if(CurrentSets == 2){
			if(blocks[0].getAge() > blocks[1].getAge())
				return 1;
			else
				return 0;
		}
		for(i = 0; i < CurrentSets - 1; i++){
			if(blocks[i].getAge() < SmallestValue){
				Oldest = i;
				SmallestValue = blocks[i].getAge();
			}
		}
		return Oldest;
	}
	
	public String printSet(){												//Test code
		sortBlocks();
		Finaltags = new StringBuilder();
		CurrentSets = getCurrentSets();
		for(i = 0; i < CurrentSets; i++){
			Finaltags.append(blocks[i].getHexTag() +"(" + blocks[i].getAge() + "),");
		}
		if(Finaltags.length() == 0){
			return "";
		}
		else
			return Finaltags.substring(0,(Finaltags.length()-1));
	}
}
