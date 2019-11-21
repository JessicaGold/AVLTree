
/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {

	boolean RIGHT = true;
	boolean LEFT = false;
	
	private IAVLNode rootNode;
	
	private IAVLNode minNode;
	
	public AVLTree() {
		
		rootNode = new AVLNode();
		minNode = rootNode;
	}
	
	public AVLTree(IAVLNode root) {
		
		rootNode = root;
		minNode = rootNode;
	}
	
   /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   *time complexity O(1)
   */
   public boolean empty() {
	   return !(rootNode.isRealNode());
   }

   /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   * 
   * time complexity O(log n)
   */
   public String search(int k)
   {
	  if (empty()) {
			return null;
		}
		IAVLNode curr = rootNode;
		while (curr.isRealNode()) {
			if (curr.getKey() == k) {
				return curr.getValue();
			}
			//implementing the if operator: 
			// <boolean-expression> ? <t-val> : <f-val>
			curr = curr.getKey() < k ?  curr.getRight() : curr.getLeft();
		}
		  return null;
    }


	/**
	 * Simple nodes linked list
	 * @author me
	 *
	 */
	public class nodeLL
	{
		public IAVLNode node = null;
		public nodeLL nextNodeLL = null;
		
		public nodeLL(IAVLNode nodeIn)
		{
			node = nodeIn;
		}
	}
	
	/**
	 * Calculates the node's balance
	 * 
	 * @param node
	 * @return node balance
	 */
	private int calcNodeBalance(IAVLNode node)
	{
		if (!node.isRealNode())
			return 0;
		return (node.getLeft().getHeight() - node.getRight().getHeight());
	}
	
	/**
	 * Calculates the node's height based on childs.
	 * 
	 * @param node
	 * @return node's height
	 */
	private int calcNodeHeight(IAVLNode node)
	{
		if (!node.isRealNode())
			return -1;
		return Math.max(node.getRight().getHeight(), 
				node.getLeft().getHeight()) + 1;
	}
	
	/**
	 * Fixes the node height after role (internal function).
	 * @param node
	 */
	private void rollFixHeight(IAVLNode node)
	{
		  node.setHeight(calcNodeHeight(node));
	}

	private void LLroll(IAVLNode node)
	{
		  IAVLNode leftNode = node.getLeft();
		  IAVLNode rightOfLeftNode = leftNode.getRight();
		  IAVLNode nodeParent = node.getParent();
		  
		  //Roll
		  leftNode.setRight(node);
		  node.setParent(leftNode);
		  
		  node.setLeft(rightOfLeftNode);
		  if (rightOfLeftNode.isRealNode())
		  {
		  rightOfLeftNode.setParent(node);
		  }
		  //Fix height
		  rollFixHeight(node);
		  rollFixHeight(leftNode);
		  
		  //Fix size
		  node.setSubtreeSize(node.getRight().getSubtreeSize()+ 
				  node.getLeft().getSubtreeSize()+1);
		  leftNode.setSubtreeSize(leftNode.getRight().getSubtreeSize()+ 
				  leftNode.getLeft().getSubtreeSize()+1);
		  
		  //Fix parent
		  if (nodeParent == null)
		  {
			  /*node is root */
			  rootNode = leftNode;
			  rootNode.setParent(null);
		  }
		  else
		  {
			  if (nodeParent.getLeft() == node) nodeParent.setLeft(leftNode);
			  else nodeParent.setRight(leftNode); 
			  leftNode.setParent(nodeParent);
		  }
	}
	
	private void RRroll(IAVLNode node)
	{
		  IAVLNode rightNode = node.getRight();
		  IAVLNode LeftOfRightNode = rightNode.getLeft();
		  IAVLNode nodeParent = node.getParent();
		  
		  //Roll
		  rightNode.setLeft(node);
		  node.setParent(rightNode);
		  
		  node.setRight(LeftOfRightNode);
		  if (LeftOfRightNode.isRealNode())
		  {
		  LeftOfRightNode.setParent(node);
		  }
		  //Fix height
		  rollFixHeight(node);
		  rollFixHeight(rightNode);
		  
		//Fix size
		  node.setSubtreeSize(node.getRight().getSubtreeSize()+ 
				  node.getLeft().getSubtreeSize()+1);
		  rightNode.setSubtreeSize(rightNode.getRight().getSubtreeSize()+ 
				  rightNode.getLeft().getSubtreeSize()+1);
		  
		  //Fix new root parent
		  if (nodeParent == null)
		  {
			  rootNode = rightNode;
			  rootNode.setParent(null);
		  }
		  else
		  {
			  if (nodeParent.getLeft() == node) nodeParent.setLeft(rightNode);
			  else nodeParent.setRight(rightNode); 
			  rightNode.setParent(nodeParent);
		  }
	}
	
	private void LRroll(IAVLNode node)
	{
		  RRroll(node.getLeft());
		  LLroll(node);
	}
	
	private void RLroll(IAVLNode node)
	{
		  LLroll(node.getRight());
		  RRroll(node);
	}
	
	/**
	 * Inserts an item with key k and info i to a tree.
	 * 
	 * 
	 * @param k the key of the node to be inserted.
	 * @param i the info of the node to be inserted.
	 * @return 
	 * 	The inserted node or null if the key already exists in the tree.
	 */
	private IAVLNode insertTreeNode(int k, String i)
	{
		  IAVLNode currentNode = rootNode;
		  IAVLNode addedNode = null;
		  int currentKey;
		  nodeLL nodeLL = new nodeLL(currentNode);
		  nodeLL rootNodeLL = nodeLL;
		  
		  while(currentNode.isRealNode())
		  {
			  currentKey = currentNode.getKey();
			  
			  /*
			   * Fix sizes:
			   * Adding one to every node we are passing.
			   * if node already exist in the tree, we will fix this.
			   * adding node to LL at the end of the loop as to not add root twice
			   */
			  currentNode.setSubtreeSize(currentNode.getSubtreeSize() + 1);
			  
			  if (k == currentKey)
			  {
				  //Fix size back - no node added to tree
				  while (rootNodeLL != null)
				  {
					  rootNodeLL.node.setSubtreeSize
					  		(rootNodeLL.node.getSubtreeSize()-1);
					  rootNodeLL = rootNodeLL.nextNodeLL;
				  }
				  return null;
			  }
			  
			  if (k > currentKey)
			  {
				  IAVLNode rightNode = currentNode.getRight();
				  if (rightNode.isRealNode() == false)
				  {
					  addedNode = new AVLNode(k, i, currentNode);
					  currentNode.setRight(addedNode);
				  }
				  currentNode = rightNode;		    
			  }
			  else	//k < currentKey 
			  { 
				  IAVLNode leftNode = currentNode.getLeft();
				  if (leftNode.isRealNode() == false)
				  {
					  addedNode = new AVLNode(k, i, currentNode);
					  currentNode.setLeft(addedNode);
				  }
				  currentNode = leftNode; 
			  }
			  nodeLL.nextNodeLL = new nodeLL(currentNode);
			  nodeLL = nodeLL.nextNodeLL;
		  }
		  
		  return addedNode;
		  
	}


	/**
	 * fixes the tree after insertion or deletion.<br>
	 * The exact implementation of the algorithm from 3-4_rank_public (3)
	 * 
	 * @post Tree is valid.
	 * @pre node must have a valid parent.
	 * @param node the key of the node to be deleted
	 * @param del true if the last operation was deletion | false if insertion
	 * @return 
	 * 	The number of re-balance operations (0 for none)
	 * 	 
	 */
	private int adjustAVLTree(IAVLNode node, boolean del)
	{
		  int numOfRebalancingOps = 0;
		  int BF;
		  boolean ins = !(del);
		  
		  /* Climbing up the tree until null (root's parent) */
		  while (node != null)
		  {
			  
			  BF = calcNodeBalance(node);
			  if (Math.abs(BF) < 2)
			  {
				  int newNodeHeight = calcNodeHeight(node);	
				  
				  if (newNodeHeight == node.getHeight())
					  break;
				  else
				  {
				  if (del)
					  node.setSubtreeSize(node.getSubtreeSize() - 1);
				  node.setHeight(newNodeHeight);
				  node = node.getParent();
				  }
			  }
			  else
			  {
				  int lBF = calcNodeBalance(node.getLeft());
				  int rBF = calcNodeBalance(node.getRight());
				  IAVLNode parent = node.getParent();
				  
				  if (BF == 2 && ((ins && lBF == 1) || 
						  (del && (lBF == 0 || lBF == 1))))
				  {
					  LLroll(node);
					  numOfRebalancingOps += 1;
				  }
				  else if (BF == 2 && ((ins && lBF == -1) || (del && lBF == -1)))
				  {
					  LRroll(node);
					  numOfRebalancingOps += 2;
				  }
				  else if (BF == -2 && ((ins && rBF == -1) || 
						  (del && (rBF == 0 || rBF == -1))))
				  {
					  RRroll(node);
					  numOfRebalancingOps += 1;
				  }
				  else if (BF == -2 && ((ins && rBF == 1) ||(del && rBF == 1)))
				  {
					  RLroll(node);
					  numOfRebalancingOps += 2;
				  }
				  
	
				  if (ins)
					  return numOfRebalancingOps;
					  
				  node = parent;
			  }
		  }
		  if (del)
		  {
			  while (node != null)
			  {
				  node.setSubtreeSize(node.getSubtreeSize() - 1);
				  node = node.getParent();
			  }
		  }
		  return numOfRebalancingOps;
	}
	/**
	 * inserts an item with key k and info i to the AVL tree.
	 * 
	 * @post Tree remains valid.
	 * @param k the key of the node to be inserted.
	 * @param i the info (value) of the node to be inserted.
	 * @return 
	 * 	the number of re-balance operations (0 for none)
	 * 	 or -1 if the item already in the tree.
	 * 
	 * time complexity O(log n)
	 */
	 public int insert(int k, String i) {
		   
		   if (empty())
		   {
			   rootNode = new AVLNode(k, i, null);
			   minNode = rootNode;
			   return 0;
		   }
		   else
		   {
			   /* Regular insertion of a node in a tree */
			   IAVLNode addedNode = insertTreeNode(k, i);
			   if (addedNode != null)
			   {
				   /* Fix minimum node
				    * At this point the minNode is not virtual
				    */
				   if (k < minNode.getKey())
					   minNode = addedNode;
					   
				   return adjustAVLTree(addedNode.getParent(), false);
				   
			   }
			   /* Node wasn't inserted because already exist */
			   return -1;  
		  }
	 }
	 
	
	 /**
	  * Check if child is right or left child of parent
	  * @pre child.getParent() == parent
	  * @return true for right false for left
	  * @param parent - parent node
	  * @param child - child node
	  */
	 private boolean sidePa(IAVLNode parent, IAVLNode child)
	 {
		   if (parent.getRight() == child)
				   return RIGHT;
		   else return LEFT;
	 }
	 /**
	  * deletes an item with key k.
	  * 
	  * 
	  * @param k the key of the node to be deleted.
	  * @return 
	  * 	The deleted node's parent or null if it wasn't found.
	  */
	 private IAVLNode deleteTreeNode(int k)
	 {
		   IAVLNode nodeParent = null;
		   IAVLNode currentNode = rootNode;
		   while (currentNode.isRealNode() && currentNode.getKey() != k)
		   {
			   currentNode = (currentNode.getKey() > k) ? 
					   currentNode.getLeft() : currentNode.getRight(); 
		   }
		   if (currentNode.isRealNode())
		   {
			   nodeParent = currentNode.getParent();
			   IAVLNode nodeR = currentNode.getRight();
			   IAVLNode nodeL = currentNode.getLeft();
			   
			   
	
			   if (!nodeL.isRealNode() && !nodeR.isRealNode())
			   {
				   if (minNode == currentNode)
					   minNode = nodeParent;
					   
				   if (nodeParent == null)
					   rootNode = minNode = new AVLNode();
					   
				   else if (sidePa(nodeParent, currentNode) == LEFT) 
					   nodeParent.setLeft(new AVLNode()); 
				   else nodeParent.setRight(new AVLNode());
				   
				   
			   }
			   else if (!nodeL.isRealNode())
			   {
				   if (nodeParent == null)
				   {
					   rootNode = nodeR;
				   }   
				   else if (sidePa(nodeParent, currentNode) == LEFT)  
					   nodeParent.setLeft(nodeR); 
				   else nodeParent.setRight(nodeR);
				   
				   nodeR.setParent(nodeParent);
			   }
			   else if (!nodeR.isRealNode())
			   {
				   if (nodeParent == null)
				   {
					   rootNode = nodeL;
				   }  
				   else if (sidePa(nodeParent, currentNode) == LEFT)
					   nodeParent.setLeft(nodeL); 
				   else nodeParent.setRight(nodeL);
				   
				   nodeL.setParent(nodeParent);
			   }
			   else
			   {
				   /*
				    * currentNode can't be maximum in tree (has right node).
				    * hence, sucNode is not null
				    */
				   IAVLNode sucNode = getSuccessor(currentNode); 
				   /*
				    * sucNode can't be the root of the tree because
				    * the sucNode is descendant of currentNode
				    * hence, sucNodeParent is not null
				    */
				   IAVLNode sucNodeParent = sucNode.getParent(); 
				   IAVLNode sucNodeRight = sucNode.getRight();
				   
				   /* remove sucNode */
				   if (sidePa(sucNodeParent, sucNode) == RIGHT)
					   sucNodeParent.setRight(sucNodeRight);
				   else
					   sucNodeParent.setLeft(sucNodeRight);
				   sucNodeRight.setParent(sucNodeParent);
				   
				   if (nodeParent == null)
					   rootNode = sucNode;
				   else
				   {
					   if (sidePa(nodeParent, currentNode) == LEFT)
						   nodeParent.setLeft(sucNode);
					   else nodeParent.setRight(sucNode);
				   }
				   
				   /* if we wanted, we could just change key and value */
				   
				   sucNode.setParent(nodeParent);
				   sucNode.setRight(currentNode.getRight());
				   sucNode.setLeft(currentNode.getLeft());
				   sucNode.setHeight(currentNode.getHeight());
				   sucNode.setSubtreeSize(currentNode.getSubtreeSize());
				   sucNode.getRight().setParent(sucNode);
				   sucNode.getLeft().setParent(sucNode);
				   
				   /*
				    * if successor == rightChild
				    */
				   if (sucNodeParent == currentNode)
					   nodeParent = sucNode;
				   else nodeParent = sucNodeParent;	   
			   } 		      
		   }
		   /*
		    * If the deleted node is root Node, the parent would be null
		    */
		   return nodeParent;
	 }
	/**
	 * deletes an item with key k from the AVL tree,
	 * if it is there and fixes the tree.
	 * 
	 * @post Tree remains valid.
	 * @param k the key of the node to be deleted.
	 * @return 
	 * 	the number of re-balance operations (0 for none)
	 * 	 or -1 if the item was not found.
	 * 
	 * time complexity O(log n)
	 */
	 public int delete(int k)
	 {
		   
		   /* Tree is empty */
		   if (empty() || (k < 0)) return -1;
		   
	
		   /* Regular deletion of a node in a tree */
		   IAVLNode parentOfDeletedNode = deleteTreeNode(k);
		   
		   /* Node wasn't found in the tree */
		   if (parentOfDeletedNode == null)
			   return -1;
		   else
		   {
			   return adjustAVLTree(parentOfDeletedNode, true);
		   }
		      
	 }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    * 
    * time complexity O(1)
    */
   public String min()
   {
	   if (empty()) {
		   return null;
	   }
	   return minNode.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    * 
    * time complexity O(log n)
    */
   public String max()
   {
	   if (empty()) {
		   return null;
	   }
	   IAVLNode curr = rootNode; 
	   while (curr.getRight().isRealNode()) {
		   curr =curr.getRight();
	   }
	   return curr.getValue();
   }
 
   /**
    * 
    * @return node with max key
    * 
    * @pre !empty()
    */
   private IAVLNode maxNode()
   {
	   IAVLNode curr = rootNode;
	   while (curr.getRight().isRealNode()) {
		   curr =curr.getRight();
	   }
	   return curr;
   }
   
   
   /**
    * 
    * @param node
    * @return pointer to the node with min key value in input's sub-tree
    * 
    * @pre node.isReal()
    */
   private IAVLNode getSubMin(IAVLNode node) {
	   IAVLNode curr = node;
	   while (curr.getLeft().isRealNode()) {
		   curr = curr.getLeft();
	   }
	   return curr;
   }
   
  /**
   * 
   * @param node
   * @return node's successor
   * 
   * @pre node.isReal()
   * 
   * time complexity O(log n)
   */
   private IAVLNode getSuccessor(IAVLNode node) {
	   if (node == maxNode()) {
		   return null;
	   }
	   
	   if (node.getRight().isRealNode()) {
		   return getSubMin(node.getRight());
	   }
	   IAVLNode curr = node;
	   IAVLNode next = node.getParent();
	   while (curr == next.getRight()) {
		   curr = next;
		   next = next.getParent();
	   }
		 
	   return next;
   }

   /**
    * 
    * @param node
    * @param array of ints the size of the entire tree
    * @param l stands for left, 
    * 			as in the starting index for this call of the method
    * 
    * method works in-place with no return value
    */
   private void subTreeInOrder(IAVLNode node, int[] array, int l)
   {
	   array[l + node.getLeft().getSubtreeSize()] = node.getKey();
	   if (node.getLeft().isRealNode()) {
		   subTreeInOrder(node.getLeft(), array, l);
	   }
	   if (node.getRight().isRealNode()) {
		   subTreeInOrder(node.getRight(), array, l + 
				   node.getLeft().getSubtreeSize() + 1);
	   }

   }
   
  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   * 
   * time complexity O(n)
   */
  public int[] keysToArray()
  {
        int[] arr = new int[size()];
        if (size() > 0) {
        	subTreeInOrder(rootNode, arr, 0);
        }
        return arr;             
  }

  
  /**
   * 
   * @param node
   * @param array
   * @param l
   * 
   * the same as with the keys but with Strings for values
   */
  private void subTreeInOrder(IAVLNode node, String[] array, int l)
  {
	   array[l + node.getLeft().getSubtreeSize()] = node.getValue();
	   if (node.getLeft().isRealNode()) {
		   subTreeInOrder(node.getLeft(), array, l);
	   }
	   if (node.getRight().isRealNode()) {
		   subTreeInOrder(node.getRight(), array, l + 
				   node.getLeft().getSubtreeSize() + 1);
	   }

  }
  
  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   * 
   * time complexity O(n)
   */
  public String[] infoToArray()
  {
        String[] arr = new String[size()];
        if (size() > 0) {
        	subTreeInOrder(rootNode, arr, 0);
        }
        return arr;
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    * 
    * time complexity O(1)
    */
   public int size()
   {
	   return rootNode.getSubtreeSize();
   }
   
     /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    * 
    * time complexity O(1)
    */
   public IAVLNode getRoot()
   {
	   return rootNode;
   }
   
     /**
    * public string select(int i)
    *
    * Returns the value of the i'th smallest key (return null if tree is empty)
    * Example 1: select(1) returns the value of the node with minimal key 
	* Example 2: select(size()) returns the value of the node with maximal key 
	* Example 3: select(2) returns the value 2nd smallest minimal node, 
	* 		i.e the value of the node minimal node's successor 	
    *
	* precondition: size() >= i > 0
    * postcondition: none
    * 
    * time complexity O(log i)
    */   
   public String select(int i)
   {
	   IAVLNode subRoot = minNode;
	   if (i == 1) {
		   return subRoot.getValue();
	   }
	   while (subRoot.getSubtreeSize() < i) {
		   
		   subRoot = subRoot.getParent();
	   }
	   int curr = subRoot.getLeft().getSubtreeSize() + 1;
	   //notice at this point: curr <= i <= tree size (from @pre)
	   //meaning the following loop will in fact come to a halt
	   while (true) {
		   if (curr == i) {
			   return subRoot.getValue();
		   }
		   if (i > curr) {
			   subRoot = subRoot.getRight();
			   curr += subRoot.getLeft().getSubtreeSize() + 1;
		   }
		   else {
			   subRoot = subRoot.getLeft();
			   curr -= (subRoot.getRight().getSubtreeSize() + 1);
		   }
	   }
   }
   
   
   /**
    * public int less(int i)
    *
    * Returns the sum of all keys which are less or equal to i
    * i is not neccessarily a key in the tree 	
    *
	* precondition: none
    * postcondition: none
    * 
    * time complexity O(log n)
    */   
   public int less(int i)
   {
	   return recLess(i, rootNode);
   }
   
   /**
    * 
    * @param i same as less
    * @param node - the root of the sub tree
    * @return the return value of less if this node were the root
    */
   private int recLess(int i, IAVLNode node)
   {
	   if (node == null || !node.isRealNode()) {
		   return 0;
	   }
	   int k = node.getKey();
	   if(node.getSubtreeSize() == 1) {
		   return k <= i ? k : 0;
	   }
	   if (k <= i) {
		   return k + recLess(i, node.getLeft()) + recLess(i, node.getRight());
	   }
	   return recLess(i, node.getLeft());
   }

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
    public void setSubtreeSize(int size); // sets the number of real nodes in this node's subtree
    public int getSubtreeSize(); // Returns the number of real nodes in this node's subtree (Should be implemented in O(1))
    public void setHeight(int height); // sets the height of the node
    public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
	}

   /**
   * public class AVLNode
   *
   * If you wish to implement classes other than AVLTree
   * (for example AVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IAVLNode)
   */
  public class AVLNode implements IAVLNode{
	  
	  
	  private int key;
	  private String value;
	  public IAVLNode parent;
	  private IAVLNode right;
	  private IAVLNode left;
	  private int size; //number of nodes in sub-tree including itself
	  private int height;
	  
	  
	  public AVLNode() {
		  key = height = -1;
		  size = 0;
		  value = null;
		  parent = right = left  = null;
		  //notice it has no parent. we can change this if needed.
	  }
	  
	  public AVLNode(int key, String val, IAVLNode par) {
		  this.key = key;
		  value = val;
		  parent = par;
		  right = left = new AVLNode(); //setting children to virtual nodes
		  size = 1;
		  height = 0;
	  }
	  
		public int getKey()
		{
			return key;
		}
		public String getValue()
		{
			return value;
		}
		public void setLeft(IAVLNode node)
		{
			left = node;
		}
		public IAVLNode getLeft()
		{
			return left;
		}
		public void setRight(IAVLNode node)
		{
			right = node;
		}
		public IAVLNode getRight()
		{
			return right;
		}
		public void setParent(IAVLNode node)
		{
			parent = node;
		}
		public IAVLNode getParent()
		{
			return parent;
		}
		
		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode()
		{
			return size > 0;
		}
    public void setSubtreeSize(int size)
    {
      this.size = size;
    }
		public int getSubtreeSize()
		{
			return size;
		}
    public void setHeight(int height)
    {
      this.height = height;
    }
    public int getHeight()
    {
      return height;
    }
  }

}
