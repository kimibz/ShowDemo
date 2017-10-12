package com.xigua.constant;

import java.util.Random;

public class GotoTest {
    @SuppressWarnings("unused")
    public static void main(String args[]){
        outer: 
            for (int i = 0; i < 10; i++) { 
                    System.out.println("\nouter_loop:" + i); 
                    inner: 
                    for (int k = 0; i < 10; k++) { 
                            System.out.print(k + " "); 
                            int x = new Random().nextInt(10); 
                            if (x > 7) { 
                                    System.out.print(" >>x == " + x + "，结束inner循环，继续迭代执行outer循环了！"); 
                                    continue outer; 
                            } 
                            if (x == 1) { 
                                    System.out.print(" >>x == 1，跳出并结束整个outer和inner循环！"); 
                                    break outer; 
                            } 
                    } 
            } 
            System.out.println("------>>>所有循环执行完毕！"); 
    }
}
