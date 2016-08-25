package Assignment;

public class Block {
	private String hexTag;
	private int tag;
	private boolean valid;
	private int age;
	

	public Block(){												//Creates the block object
		hexTag = null;
		valid = false;
	}
	
	
	public void addTag(int tag, int age){						//sets the tag of the block
		this.tag = tag;
		hexTag = Integer.toHexString(this.tag);
		valid = true;
		this.age = age;
	}
	
	public int getAge(){										//Returns the age of a block
		return age;
	}
	
	public int getTag(){										//Returns the tag
		return tag;
	}
	
	public String getHexTag(){									//Returns the tag in hex
		return hexTag;
	}
	
	public boolean getValid(){									//Returns if the block is valid (true or false)
		return valid;
	}
	
	public void updateAge(int age){								//Updates the age of a block
		this.age = age;
	}
	
	public void updateBlock(int age, String hexTag){			//Updates the block
		this.age = age;
		this.hexTag = hexTag;
	}
}