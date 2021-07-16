package com.lpwanw.AppServer.Calculator;

public class Cal {
    int a;
    int b;
    public Cal(int a,int b){
        this.a=a;
        this.b=b;
    }
    public Cal(){
    }
    public int tinhtong(int a,int b){
        return a+b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
