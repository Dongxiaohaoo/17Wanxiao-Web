package top.dongxiaohao.util;

public class RandomDev {

    public static String getDevID(){
        long dev = (long) (Math.random()*Math.pow(10,16));
        System.out.println(Math.pow(10,16));
        return String.valueOf(dev);
    }
}
