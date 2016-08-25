 package Assignment;

public class Cache {
	private int i;
	private Set[] set;

	public Cache(int setnum, int blocksPerSet){								//Creates a cache object
		set = new Set[setnum];
		for(i = 0; i < setnum; i++){
			set[i] = new Set(blocksPerSet);
		}
	}

	public Set accessSet(int i){											//Returns the set of the argument
		return set[i];
	}
}