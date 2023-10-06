import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
// favoritee
class Solution {
    public int characterReplacement(String s, int k) {
        int start = 0;
        int end = 0;
        int[] charMap = new int[26];
        int maxFreq = 0;
        int result = 0;

        while(end<s.length()){
            charMap[s.charAt(end)-'A']++; // no sorting, just increment ASCII index val
            maxFreq = Math.max(maxFreq, charMap[s.charAt(end)-'A']); // max freq updated as needed instead of sort
            if((end-start+1-maxFreq)>k){ // if length-maxFreq is > k then insufficient letter chnages to makee that length
                charMap[s.charAt(start++)-'A']--; // java short for decrementing val at start since taking that letter out of
                // the window then increment start
            }
            result = Math.max(result, (end-start+1)); // max length updated as needed
            end++; // note that window can only expand in size. if L barrier incremented, R is always inc too so no change
            // but end and increement withot start to expand window
        }
        return result;
    }
}

//class Solution {
//    public int characterReplacement(String s, int k) {
//        int start = 0;
//        int[] frequencyMap = new int[26];
//        int maxFrequency = 0;
//        int longestSubstringLength = 0;
//
//        for (int end = 0; end < s.length(); end += 1) {
//            int currentChar = s.charAt(end) - 'A'; // ID current char index
//            frequencyMap[currentChar] += 1; // increment
//            maxFrequency = Math.max(maxFrequency, frequencyMap[currentChar]); // ID max frequency as we go
//
//            Boolean isValid = (end + 1 - start - maxFrequency <= k); // state if window contents valid given k
//            if (!isValid) {
//                int outgoingChar = s.charAt(start) - 'A'; // reduce freq at L wall of window
//                frequencyMap[outgoingChar] -= 1;
//                start += 1; // move L barrier of window +1
//            }
//            longestSubstringLength = end + 1 - start; // length will never decrease of window. eithre stays same fromm
//            // end++ and start++ or length inc just by end++
//        }
//
//        return longestSubstringLength;
//    }
//}

//----------------------------------------------------------------------------------
// concept, using sliding window with binary search. where window length of mid tested and is slid over entire string.
// if none found, then new smaller mid. if found then new larger mid. Improves efficiency since if substring of mid length
// found then no smaller substrings need to be checked since all will be valid
//O(n log n)
//class Solution {
//    public int characterReplacement(String s, int k) {
//        int lo = 1; // lo is longest value which must at least be one
//        int hi = s.length() + 1; // OOB must be higher than length of string
//
//        while (lo + 1 < hi) { // loop will call to canMakeValidSubstring for various windows of size 'mid'
//            int mid = lo + (hi - lo) / 2;
//            if (canMakeValidSubstring(s, mid, k)) { //if found, then longest length = mid
//                lo = mid;
//            } else { // else OOB updated to mid
//                hi = mid;
//            }
//        }
//        return lo; // binary sliding window search complete, return lo
//    }
//
//    private Boolean canMakeValidSubstring(String s, int substringLength, int k) {
//        // slide window of length subStringLength
//        int[] freqMap = new int[26];
//        int maxFrequency = 0;
//        int start = 0;
//        for (int end = 0; end < s.length(); end += 1) {
//            freqMap[s.charAt(end) - 'A'] += 1; // ASCII freq table
//            if (end + 1 - start > substringLength) { // if substring > window, drop the char at start from table and update start
//                freqMap[s.charAt(start) - 'A'] -= 1;
//                start += 1;
//            }
//
//            maxFrequency = Math.max(maxFrequency, freqMap[s.charAt(end) - 'A']); // this checks when a new char ASCII value
//            // added to table, says, is this now greater than current max?
//            // but what about when an item is deleted? don't we need to reduce max freq? No? because testing for a lower
//            // value frequency will never create a longer length potential. if window is 4 and substring is 8 and max freq of 6
//            // k of 2. max length will be 6. reducing freq to 3 would mean max length of 5 and does not help?
//            if (substringLength - maxFrequency <= k) { //also, if say window of 4 already found valid,
//                return true; // it already would have returned, not continued sliding the window and updating for lesser freqs
//            }
//            // if not true, slide window to right via 'end' increment and then it will catch substring > window, adjust table, etc.
//        }
//        return false; // if window of length subStringLength slid over entire substring and no valids found, then false
//    }
//}

//----------------------------------------------------------------------------------

// MY IDEA doesn't quite work but essentially finds the max freq and then attempts a sliding window and adjusting the
// count, substitutions remaining and updates longest length when needed
//class Solution {
//    public int characterReplacement(String s, int k) {
//        int[] sArray = new int[26];
//        for(int i = 0; i < s.length(); i++){
//            sArray[s.charAt(i)]++;
//        }
//        Arrays.sort(sArray);
//        char topFreq = (char)sArray[25];
//
//        int count = 0;
//        int nonFreqAvailability = k;
//        int L = 0;
//        int longLen = 0;
//
//        Queue<Integer> q = new LinkedList<Integer>();
//
//        for(int i = 0; i<s.length(); i++){
//            if(!(s.charAt(i) == (topFreq))){ // == can be used with primitives like .equals used with classes for non-memory location equivalency
//                q.add(i);
//                if(nonFreqAvailability >= 1){
//                    nonFreqAvailability--;
//                    count++;
//                } else{
//                    L = q.poll() +1;
//                    q.poll();
//                    count = i - L;
//                }
//            } else{
//                count++;
//            }
//            if(count>longLen){
//                longLen = count;
//            }
//        }
//        return count;
//    }
//}

// perhaps ASCII count to determine most frequent int
// then have temp varriable = k and then count = 0 and a L = 0 and a
// longestLength variable
// say that count++ each loop and if non-most frequent char found, then temp--
// check if count > longestLen and update as needed. need a way to update L to
// the first key after the first non-most freq letter if count ever < 0 and
// then to decrease count by L which is the place holder for non freq char
// and increase temp++ to account for the now excluded
// non-most freq char

// BBABABBABBBBBBA. suppose k = 2, most freq = B
// temp -- x2, count 7 before temp < 0
// count -2 since -3 + 1 of current char
// question is how to store all locations of non-freq # to know where to update
// L to? perhaps a queue where if non-freq then push index to queue and then
// deque when temp <0 and then reassign L as queue.peek