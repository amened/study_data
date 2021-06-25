package 两数之和;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值的两数，并返回它们的数组下标。
//
//        你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
//
//        你可以按任意顺序返回答案。
//
//
//
//        示例 1：
//
//        输入：nums = [2,7,11,15], target = 9
//        输出：[0,1]
//        解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
//        示例 2：
//
//        输入：nums = [3,2,4], target = 6
//        输出：[1,2]
//        示例 3：
//
//        输入：nums = [3,3], target = 6
//        输出：[0,1]
public class Hot1 {
    public static void main(String[] args) {
        Hot1 hot1 = new Hot1();
        int[] ints = new int[]{2, 3, 5, 7, 3, 4};
        int[] ints1 = hot1.twoSum(ints, 100);
        System.out.println(Arrays.toString(ints1));

    }
    public int[] twoSum(int[] nums , int target){
        Map<Integer,Integer> hashmap = new HashMap<>();
        for (int i = 0 ; i < nums.length ; i++){
            if(hashmap.containsKey(target - nums[i])){
                return new int[] {hashmap.get(target - nums[i]),i};
            }
            hashmap.put(nums[i] , i);
        }
        return null;
    }
}
