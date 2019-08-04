/**
 * 
 */
package in.inishant.puzzles.arcesium.geeks4geeks;

/**
 * problem URI : https://www.geeksforgeeks.org/cutting-a-rod-dp-13/
 * 
 * Given a rod of length n inches and an array of prices that contains prices of
 * all pieces of size smaller than n. Determine the maximum value obtainable by
 * cutting up the rod and selling the pieces. For example, if length of the rod
 * is 8 and the values of different pieces are given as following, then the
 * maximum obtainable value is 22 (by cutting in two pieces of lengths 2 and 6)
 * 
 * 
 * length | 1 2 3 4 5 6 7 8 -------------------------------------------- 
 * price |  1 5 8 9 10 17 17 20
 * 
 * @author Nishant
 *
 */
public class RodCutting {

	/**
	 * 
	 */
	public RodCutting() {
		// TODO Auto-generated constructor stub
	}

	static int[] maxValues;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int arr[] = { 1, 5, 8, 9, 10, 17, 17, 20 };
		//rod length

		int n = arr.length;
		int x=maxCutRodRecurse(arr,n);
		System.out.println("max price b cutting rod : "+ x);
		
		
//		for dynamic programming table is initialized
		maxValues=new int[n+1];
		maxValues[0]=0;
		maxValues[1]=arr[0];
		for(int i=2;i<=n;i++)
			maxValues[i]=-1;
		
		x=maxCutRodDynamic(arr,n);
		System.out.println("max price b cutting rod : "+ x);
		
		System.out.println();
		for(int i=0;i<n;i++) {
			System.out.print(arr[i]+" ");
		}
		
		System.out.println();
		for(int i=1;i<=n;i++) {
			System.out.print(maxValues[i]+" ");
		}
	}

	private static int maxCutRodRecurse(int[] arr, int n) {
		if(n<=0)return 0;
		int maxValue=Integer.MIN_VALUE;
		
		for(int i=0;i<n;i++) {
			maxValue=Math.max(maxValue, arr[i]+maxCutRodRecurse(arr, n-i-1));
		}
		return maxValue;
	}
	
	 
	private static int maxCutRodDynamic(int[] arr, int n) {
		int maxVal=Integer.MIN_VALUE;
		if(n<=0) return 0;
		if(n==1) return arr[0];
		for(int i=2;i<=n;i++) {
			
			if(maxValues[i]==-1) {
				for(int j=i;j>1;j--) {
					maxVal=Math.max(maxVal,Math.max(arr[j-1], maxValues[j-1]+maxCutRodDynamic(arr,i-j+1)));
				}
				
				maxValues[i]=maxVal;
			}
		}
		return maxValues[n];
	}

	

}
