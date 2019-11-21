import java.util.Random;

public class Test {
	
	private static void buildTree(AVLTree tree) 
	{
		for (int i = 1; i < 10000; i+=3) {
			tree.insert(i, String.valueOf(i));
		}
		//tree.insert(3997, "aaa");
	}
	
	private static AVLTree buildRandomTree(int n)
	{
		int num;
		AVLTree tree = new AVLTree();
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			num = Math.abs(rand.nextInt());
			tree.insert(num, String.valueOf(num));
		}
		return tree;
	}
	
	private static boolean correctTreeRelations(AVLTree tree)
	{
		return correctNodeRelations(tree.getRoot());
	}
	
	private static boolean correctNodeRelations(AVLTree.IAVLNode node)
	{
		if (!node.isRealNode()) return true;
		if (!(node.getLeft().isRealNode()) && !(node.getRight().isRealNode())) {
			return true;
		}
		if (node.getLeft().isRealNode()) {
			if (node.getLeft().getParent() != node) return false;
		}
		if (node.getRight().isRealNode()) {
			if (node.getRight().getParent() != node) return false;
		}
		return (correctNodeRelations(node.getRight()) &&
				correctNodeRelations(node.getLeft()));
	}
	
	private static int numOfcorrectNodeRelations(AVLTree.IAVLNode node)
	{
		if (node == null || !node.isRealNode()) return 0;
		int sumOfSubs = (numOfcorrectNodeRelations(node.getRight()) +
			numOfcorrectNodeRelations(node.getLeft()));
		if (node.getParent() != null) sumOfSubs++;
		return sumOfSubs;
	}
	
	private static int numOfcorrectNodeRelations(AVLTree tree)
	{
		return 1 + numOfcorrectNodeRelations(tree.getRoot());
	}

	
	private static boolean realParents(AVLTree tree)
	{
		return (realParents(tree.getRoot().getRight())
				&& realParents(tree.getRoot().getLeft()));
	}
	
	private static boolean realParents(AVLTree.IAVLNode node)
	{
		if(node.getParent() == null) {
			return false;
		}
		return (realParents(node.getRight()) && realParents(node.getLeft()));
	}
	
	public static void printError(boolean condition, String message) {
		if (!condition) {
			throw new RuntimeException("[ERROR] " + message);
		}
	}
	
	private static AVLTree buildSpecific() {
		AVLTree tree = new AVLTree();
		tree.insert(5, "5");
		tree.insert(3, "3");
		tree.insert(8, "8");
		tree.insert(2, "2");
		tree.insert(4, "4");
		tree.insert(7, "7");
		tree.insert(11, "11");
		tree.insert(1, "1");
		tree.insert(6, "6");
		tree.insert(10, "10");
		tree.insert(12, "12");
		tree.insert(9, "9");

		return tree;
	}
	
	public static void main(String[] args) {
		AVLTree tree = buildSpecific();
		System.out.println("tree size: " + tree.size());
		System.out.println("tree height: " + tree.getRoot().getHeight());
		System.out.println("left size: " + tree.getRoot().getLeft().getSubtreeSize());
		System.out.println("left hegiht: " + tree.getRoot().getLeft().getHeight());
		System.out.println("right size: " + tree.getRoot().getRight().getSubtreeSize());
		System.out.println("right height: " + tree.getRoot().getRight().getHeight());
		System.out.println("delete 4: " + tree.delete(4));
		System.out.println("left hegiht: " + tree.getRoot().getLeft().getHeight());
		System.out.println("right height: " + tree.getRoot().getRight().getHeight());
		System.out.println("tree size: " + tree.size());

	}
}
