package com.baeldung.recursion;

public class RecursionExample {
    
    public int sum(int n){
        if (n >= 1){
            return sum(n - 1) + n;
        }
        return n;
    }
    
    public int tailSum(int currentSum, int n){
        if (n <= 1) {
            return currentSum + n;
        }
        return tailSum(currentSum + n, n - 1);
    }
    
    public int iterativeSum(int n){
        int sum = 0;
        if(n < 0){
            return -1;
        }
        for(int i=0; i<=n; i++){
            sum += i;
        }
        return sum;
    }
    
    public int powerOf10(int n){
        if (n == 0){
            return 1;
        }
        return powerOf10(n-1)*10;
    }
    
    public int fibonacci(int n){
        if (n <=1 ){
            return n;
        }
        return fibonacci(n-1) + fibonacci(n-2);
    }
    
    public String toBinary(int n){
        if (n <= 1 ){
            return String.valueOf(n);
        }
        return toBinary(n / 2) + String.valueOf(n % 2);
    }
    
    public int calculateTreeHeight(BinaryNode root){
        if (root!= null){
            if (root.getLeft() != null || root.getRight() != null){
                return 1 + max(calculateTreeHeight(root.left) , calculateTreeHeight(root.right));
            }
        }
        return 0;
    }
    
    public int max(int a,int b){
        return a>b ? a:b;
    }
    
}
