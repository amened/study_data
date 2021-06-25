package 无重复字符的最长子串;//给定一个字符串，请你找出其中不含有重复字符的最长子串的长度。
//        示例1:
//
//        输入: s = "abcabcbb"
//        输出: 3
//        解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
//        示例 2:
//
//        输入: s = "bbbbb"
//        输出: 1
//        解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
//        示例 3:
//
//        输入: s = "pwwkew"
//        输出: 3
//        解释: 因为无重复字符的最长子串"wke"，所以其长度为 3。
//
//        请注意，你的答案必须是 子串 的长度，"pwke"是一个子序列，不是子串。

import java.util.HashMap;
import java.util.Map;

public class Hot3 {
    public static void main(String[] args) {
        Hot3 hot3 = new Hot3();
        int length = hot3.lengthOfLongestSubstring("pwwkew");
        System.out.println(length);
    }

    public int lengthOfLongestSubstring(String s) {
        int nos = 0;

        // hashmap存储的是每个字符最后一次出现的位置
        Map<Character , Integer> hashmap = new HashMap<>();
        for (int start = 0 ,end = 0 ; end < s.length() ; end ++){
            // 检测到map有值，代表可能是由这个值重复，如果这个值还在检测的区间之内，一定是这个值引起的重复，不在区间之内，代表与这个值无关
            if (hashmap.containsKey(s.charAt(end))){
                if(hashmap.get(s.charAt(end)) >= start){
                    start = hashmap.get(s.charAt(end)) + 1;
                }else {
                }
            }
            nos = Math.max(nos, end - start +1);
            hashmap.put(s.charAt(end), end);
        }
        return nos;
    }
}
