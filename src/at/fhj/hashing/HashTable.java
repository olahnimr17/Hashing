package at.fhj.hashing;

/**
 * Hash table for arbitrary objects using the double hashing algorithm with Brent.
 * The standard hashCode() method is used to get the key value of the objects
 *
 */
public class HashTable {
	/**
	 * Objects to be stored are wrapped in a HashObject object before being put into
	 * the hash table:
	 * - If an entry in the hash table contains null, the entry is empty. 
	 * - If the table entry contains a HashObject wrapper object with a null data object, 
	 *   it is free, meaning there was a data object that has been deleted.
	 * - If the wrapper object contains a valid data object, the hash table entry is in 
	 *   use.
     */
	private HashObject[] table;
	
	/**
	 * Create a hash table of the specified size
	 * @param size ... size of the hash table
	 */
	public HashTable(int size) {
		this.table = new HashObject[size];
	}

	/**
	 * Create a hash table Object from a given HashObject array (for testing purposes)
	 * @param table ... HashObject array
	 */
	public HashTable(HashObject[] table) {
		this.table = table;
	}
	
	/**
	 * Return the array containing the HashObject wrapper objects
	 * @return ... Hash array
	 */
	public HashObject[] getTable() {
		return this.table;
	}
	
	/**
	 * Insert the given into the hash table using double hashing (without Brent)
	 * @param obj ... object to insert into the hash table
	 * @return table index where the object was successfully inserted, -1 if table 
	 * is full or an object with the same key already exists within the table
	 */
	public int insert(Object obj) {
		int key = obj.hashCode();
		// Begin implementation


		int K1 = calcK1(obj.hashCode());
		int K2 = calcK2(obj.hashCode());

		do{
			if(isFree(K1)){
				setEntry(K1, obj);
				return K1;
			}

			else{
				if(!isFree(K1)) {
					if (table[K1].getData().equals(obj)) {
						return -1;
					}
				}
				K1 = calcNewK(K1, K2);
			}

		}while(K1 != calcK1(obj.hashCode()));




		return -1;



		 // change it!
		// End implementation
	}
	 
	/**
	 * Insert the given into the hash table using double hashing with Brent
	 * @param obj ... object to insert into the hash table
	 * @return table index where the object was successfully inserted, -1 if table 
	 * is full or an object with the same key already exists within the table
	 */
	public int insertBrent(Object obj) {
		int key = obj.hashCode();
		int i = calcK1(key);
		int b;
		int b2;
		Object oldObj = null;

		// Begin implementation


			if (!isFree(i) && table[i].getData().equals(obj)) {
				return -1;
			}
			if (isFree(i)) {
				setEntry(i, obj);
				return i;
			}
			b = calcNewK(i, calcK2(key));
			if (isFree(b)) {
				setEntry(b, obj);
				return b;
			}
			oldObj = getEntry(i);
			b2 = calcNewK(i, calcK2(oldObj.hashCode()));
			if (isFree(b2)) {
				setEntry(b2, oldObj);
				setEntry(i, obj);
				return i;
			} else {
				insertBrent(getEntry(i));
			}




		if(i == calcK1(obj.hashCode())){
			return -1;
		}else{
			return i;
		}


		// change it!
		// End implementation
	}
	
	/**
	 * Get the object identified by it's key (hashCode()) value from the hash table
	 * @param key 
	 * @return the object with the specified key value if it exists in the table, null otherwise
	 */
	public Object retrieve(int key) {
		// Begin implementation

		for (int i = 0; i <table.length; i++){
			if(!isFree(i)) {
				if (table[i].getData().hashCode() == key) {
					return table[i].getData();
				}
			}
		}

		return null;

		// change it!
		// End implementation
	}
	
	/**
	 * Delete the object identified by a key (hashCode()) value from the hash table
	 * @param key
	 * @return true if the object with the specified key has been found and deleted, false otherwise
	 */
	public boolean delete(int key) {
		// Begin implementation
		for (int i = 0; i < table.length; i++){
			if(!isFree(i)) {
				if (table[i].getData().hashCode() == key) {
					deleteEntry(i);
					return true;
				}
			}
		}
		return false;  // change it!
		// End implementation
	}
	
	/**
	 * Check if a given position in the hash table is empty, i.e. free and no deleted object either
	 * @param pos ... position in hash table
	 * @return true, if the position is empty, false otherwise
	 */
	private boolean isEmpty(int pos) {
		if (table[pos] == null)
			return true;
		else
			return false;
	}
	
	/**
	 * Check if a given position in the hash table is free, i.e. either free or it contains a deleted
	 * object
	 * @param pos ... position in hash table
	 * @return true, if the position is free, false otherwise
	 */
	private boolean isFree(int pos) {
		if (table[pos] == null) // empty
			return true;
		if (table[pos].getData() == null) // free
			return true;
		return false; // occupied
	}
	
	/**
	 * Set an entry in the hash table at a given position
	 * @param pos ... position for inserting
	 * @param obj ... object to insert
	 */
	private void setEntry(int pos, Object obj) {
		if (isEmpty(pos))
			table[pos] = new HashObject(obj);
		else
			table[pos].setData(obj);
	}
	
	/**
	 * Get the entry in the hash table at a given position
	 * @param pos ... position in the hash table
	 * @return object stored in this Position if the position is not free, null otherwise
	 */
	private Object getEntry(int pos) {
		if (!isFree(pos))
			return table[pos].getData();
		else 
			return null;
	}
	
	/**
	 * Delete an entry in the hash table at a give position
	 * @param pos ... position of the entry to be deleted
	 */
	private void deleteEntry(int pos) {
		if (!isEmpty(pos))
			setEntry(pos, null);
	}
	
	// Place your private methods here
	// Begin implementation

	private int calcK1(int hash){
		int k = hash%table.length;
		return k;
	}

	private int calcK2(int hash){
		int k = 1+(hash%(table.length-2));
		return k;
	}

	private int calcNewK(int K1, int K2){
		if((K1 - K2) < 0){
			return K1-K2+table.length;
		}else{
			return K1-K2;
		}
	}
	

	// End implementation
}
