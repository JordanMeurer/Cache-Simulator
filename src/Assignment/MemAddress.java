package Assignment;


public class MemAddress {
	private int AddressTag;
	private int assignedSet;
	private int block;
	private String binaryValue;
	private int blockAddress;
	private int blocknum;
	private String hexValue;
	private int index;
	private int setnum;
	private String indexHex;
	
	//Creates the MemAddress object
	public MemAddress(long decimalValue, int CacheSize, int BlockSize, int associativity){
		hexValue = Long.toHexString(decimalValue);												//converts the decimal to hex
		blockAddress = (int) (decimalValue/BlockSize);											//calculates the block address
		blocknum = (CacheSize / BlockSize);														//calculates the block number	
		if(associativity == -1){																//checks if the associativity is 0
			setnum = 1;
			AddressTag = (int) (blockAddress / setnum);
			assignedSet = 0;
		}else{																					//if associativity is not 0, calculate the number of sets and tag
			setnum = (int) (blocknum / Math.pow(2, associativity));
			AddressTag = (int) (blockAddress / setnum);
			assignedSet = (blockAddress % setnum);
		}
		block = (blockAddress %  blocknum);
	}
	public int getTag(){																		//returns the address tag
		return AddressTag;
	}
	
	public int getIndex(){																		//returns the index
		return index;
	}
	
	
	public String getHexTag(){																	//returns the hex value of the tag
		return Integer.toHexString(AddressTag);
	}
	
	public String getHexValue(){																//returns the hex value
		return hexValue;
	}
	
	public String getBinaryValue(){																//returns the binary value
		return binaryValue;
	}
	
	public String getBlock(){																	//returns the block number
		return Integer.toHexString(block);
	}

	
	public String getHexIndex(){																//returns the index in hex
		return indexHex;
	}
	
	public int getSetNum(){																		//returns the set number
		return assignedSet;
	}

	public String getSet(){																		//returns the assigned set
		return Integer.toHexString(assignedSet);
	}
}
