package com.xigua.wexin;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String sessionid = "OU4aMRIjMSXxdGXcUgPw0dAOSzzfRqfeNjl9q0SEV5RsPlDSV0YGjrwBBQxRAQA"
                + "BqztlehuE61FdRMLAzyRpxVaSULbu11QedV7pZ8nnMP1+0naSN/oC8mqm0vi31ToyAN0WHH5kMe"
                + "L9daKmZ2ffiA==";
        String score = "888";
        String times = "700";
        String result = Util.postData(score, times, sessionid);
        System.out.println(result);
    }
}
