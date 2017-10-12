package com.xigua.constant;

public class TestStringToBinary {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tt= "asdfdigigi";
		System.out.println(StrToBinstr(tt));
	}
	static String StrToBinstr(String str) {
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        return result;
    }
}
