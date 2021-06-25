package 最长回文子串;//给你一个字符串 s，找到 s 中最长的回文子串。
//
//示例 1：
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
//示例 2：
//
//输入：s = "cbbd"
//输出："bb"

public class Hot5 {
    public static void main(String[] args) {
        Hot5 hot4 = new Hot5();
        String s = hot4.longestPalindrome("babad");
        System.out.println(s);
    }

    // 动态规划
    public String longestPalindrome(String s) {
        String result = "";


        return result;

    }

//    // 暴力
//    public String longestPalindrome(String s) {
//        String result = "";
//        for (int i = 0 ; i < s.length(); i++ ){
//            for (int j = i ; j < s.length()  ; j++){
//                if ( isPalindromic(s.substring(i,j+1)) ){
//                    if ( s.substring(i,j+1).length() > result.length() ){
//                        result = s.substring(i,j+1);
//                    }
//                }
//            }
//        }
//
//        return result;
//
//    }
//
//    // 判断回文
//    public static boolean isPalindromic(String s) {
//        int len = s.length();
//        for (int i = 0; i < len / 2; i++) {
//            if (s.charAt(i) != s.charAt(len - i - 1)) {
//                return false;
//            }
//        }
//        return true;
//    }
}
