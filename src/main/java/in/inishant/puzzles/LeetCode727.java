/**
 * 
 */
package in.inishant.puzzles;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 * @author Nishant
 *
 */
public class LeetCode727 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int nums[]= {0,0,1,1,1,2,2,3,3,4};
		System.out.println(removeDuplicates(nums));
		System.out.println(Arrays.toString(nums));
	}

	public static int removeDuplicates(int[] nums) {
		int cp = 0,i = 0,count = 0;
		while(cp<nums.length) {
			if(cp == i) {
				cp++;
				count++;
			}else if(nums[cp]==nums[i]) {
				cp++;
			}else {
				i++;
				nums[i]=nums[cp];
				cp++;
				count++;
			}
			
		}
		return count;
	}

}
