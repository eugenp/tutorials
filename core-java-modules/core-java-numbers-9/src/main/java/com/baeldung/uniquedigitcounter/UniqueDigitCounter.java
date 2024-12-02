package com.baeldung.uniquedigitcounter;
  
public class UniqueDigitCounter {
    static int bruteForce(int n) {
        int count = 0;
        int limit = (int)Math.pow(10, n);
        
        for (int num = 0; num < limit; num++) {
            if (hasUniqueDigits(num)) {
                count++;
            }
        }
        return count;
    }

    static boolean hasUniqueDigits(int num) {
        String str = Integer.toString(num);
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    static int combinatorial(int n) {
        if (n == 0) return 1;
        
        int result = 10;
        int current = 9;
        int available = 9;
    
        for (int i = 2; i <= n; i++) {
            current *= available;
            result += current;
            available--;
        }
        return result;
    }

    static int dp(int n) {
        if (n == 0) return 1;
        
        int result = 10; 
        int current = 9;
        int available = 9;
        
        for (int i = 2; i <= n; i++) {
            current *= available;
            result += current;
            available--;
        }
        return result;
    }
}


