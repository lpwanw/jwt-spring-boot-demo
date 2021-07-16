package com.lpwanw.AppServer.Calculator;

import org.springframework.stereotype.Component;

@Component
public class Cal2Ipml implements  Cal2{
    @Override
    public int tinhtong(int a, int b) {
        return a+b;
    }
}
