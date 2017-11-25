package com.baeldung.recursion;

public class RecursionExample {
    
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
    
    public void solveHanoiTower(String c1,String c2,String c3,int n){
        
        if (n == 1){
            System.out.println("Move 1 disk from "+c1+" to "+c3);
            return;
        }
        
        solveHanoiTower(c1, c3, c2, n-1);
        solveHanoiTower(c1, c2, c3, 1);
        solveHanoiTower(c2, c1, c3, n-1);
    }
    
}
