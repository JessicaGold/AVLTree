import java.util.Random;

/*note that the integrity of this file is not maintained*/

public class Experiment {

	private static int[] experiment1(int i)
	{
		int num;
		int tmp;
		AVLTree tree = new AVLTree();
		Random rand = new Random();
		int maxInsertionsFixes = 0;
		int maxDeletionsFixes = 0;
		int[] res = new int[4];
		for (int t = 0; t < 30; t++) {
			int insertionsFixes = 0;
			int deletionsFixes = 0;
			for (int j = 0; j < i * 10000; j++) {
				num = Math.abs(rand.nextInt());
				tmp = tree.insert(num, String.valueOf(num));
				if (tmp > maxInsertionsFixes) maxInsertionsFixes = tmp;
				if (tmp >= 0) insertionsFixes += tmp;
			}
			
			int[] keys = tree.keysToArray();
			
			for (int j = 0; j < keys.length; j++) {
				tmp = tree.delete(keys[j]);
				if (tmp > maxDeletionsFixes) maxDeletionsFixes = tmp;
				if (tmp >= 0) deletionsFixes += tmp;
			}
			
			res[0] += insertionsFixes;
			res[1] += deletionsFixes;
			res[2] = maxInsertionsFixes > res[2] ? maxInsertionsFixes : res[2];
			res[3] = maxDeletionsFixes > res[3] ? maxDeletionsFixes : res[3];
		}
		res[0] = res[0]/30;
		res[1] = res[1]/30;
		
		return res;
	}
	
	
	private static AVLTree buildRandomTree(int n)
	{
		int num;
		AVLTree tree = new AVLTree();
		Random rand = new Random();
		while (tree.size() < n) {
			num = Math.abs(rand.nextInt());
			tree.insert(num, String.valueOf(num));
		}
		return tree;
	}
	
	/*private static long[] experiment2(int i)
	{

		AVLTree tree = buildRandomTree(i * 10000);

		long start;
		long end;
		
		start = System.nanoTime();
		int[] keys = tree.keysToArray();
		end = System.nanoTime();
		long inOrderTime = (end - start) / 1000;
		
		int[] keys2 = new int[keys.length];
		int k;
		start = System.nanoTime();
		for (int j = 0; j < keys2.length; j++) {
			//turn minNode into public before you do this
			k = tree.minNode.getKey();
			keys2[j] = k;
			tree.delete(k);
		}
		end = System.nanoTime();
		long deleteMinsTime = (end - start) / 1000;

		long[] res = {inOrderTime, deleteMinsTime};
		
		return res;
	}
	
	/*private static long[] experiment20(int i)
	{
		long[] res = new long[2];
		long[] tmp = new long[2];
		for (int j = 0; j < 10; j++) {
			tmp = experiment2(i);
			res[0] += tmp[0];
			res[1] += tmp[1];
		}
		return res;
	}*/
	
	public static void main(String[] args)
	{
		int[] res = experiment1(10);
		for (int i = 0; i < 4; i++) {
			System.out.print(res[i] + "\t");
		}
	}
}
