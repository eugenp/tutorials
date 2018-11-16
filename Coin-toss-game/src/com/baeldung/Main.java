package com.baeldung;

import com.baeldung.hexagonaldesign.Display;
import com.baeldung.hexagonaldesign.HexagonalCoinToss;
import com.baeldung.hexagonaldesign.UserInput;
import com.baeldung.hexagonaldesign.impl.ConsoleDisplay;
import com.baeldung.hexagonaldesign.impl.KeyboardInput;

import java.util.*;

public class Main {

    public static void main(String[] args) {
//        CoinToss game = new CoinToss();
//        game.play();

//        Display display = new ConsoleDisplay();
//        UserInput userInput = new KeyboardInput();
//        new HexagonalCoinToss(display, userInput).play();


//       String str = "abaaa";
//       int n = str.length();
//        Set<String> set = new HashSet<>();
//       for(int i=0;i<n;i++){
//           for(int j=i+1;j<=n;j++){
//               String sub = str.substring(i,j);
//               if(isPal(sub)){
//                   if(!set.contains(sub))
//                        System.out.println(sub);
//                   set.add(sub);
//               }
//           }
//       }

//        List<String> words = new ArrayList<>();
//        words.add("Abc");
//        words.add("Abc");
//        words.add("Abc");
//        words.add("Abc");
//        words.add("bAbc");
//        words.add("bAbc");
//        words.add("Abc");
//        words.add("cvAbc");
//        words.add("eweAbc");
//        words.add("cAbc");
//        words.add("b");
//        words.add("lAbc");
//        words.add("fAbc");
//        words.add("fAbc");
//        words.add("fAbc");
//
//        List<String> strings = topKFrequent(words, 5);
//        for(String word :strings){
//            System.out.println(word);
//        }

        int arr[] = { 10, 5, 2, 7, 1, 9 };
        int n = arr.length;
        int k = 15;

        System.out.print(GetmNumberOfSubsets(arr,15));

    }

    public static boolean isPal(String s){
        int n = s.length();
        for(int i=0;i<n/2;++i){
            if(s.charAt(i)!=s.charAt(n-i-1)) return false;
        }
        return true;
    }

    public static List<String> topKFrequent(List<String> words, int k) {
        List<String> ans = new ArrayList<>();
        if(words == null || words.size() == 0) {
            return ans;
        }
        Map<String, Integer> map = new HashMap<>();
        for(String word : words) {
            map.put(word,map.getOrDefault(word, 0) + 1);
        }
        PriorityQueue<String> pq = new PriorityQueue<>
                ((a,b) -> map.get(b) ==  map.get(a)? b.compareTo(a) : map.get(a) - map.get(b));
        for (String word: map.keySet()) {
            pq.offer(word);
            if (pq.size() > k) pq.poll();
        }
        while (!pq.isEmpty()) ans.add(0,pq.poll());
        return ans;
    }

    public static int GetmNumberOfSubsets(int[] numbers, int sum)
    {
        int[] dp = new int[sum + 1];
        dp[0] = 1;
        int currentSum =0;
        for (int i = 0; i < numbers.length; i++)
        {
            currentSum += numbers[i];
            for (int j = Math.min(sum, currentSum); j >= numbers[i]; j--)
                dp[j] += dp[j - numbers[i]];
        }

        return dp[sum];
    }
}
