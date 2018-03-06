/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.servlet.nonblocking;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;

/**
 * 数据写入回调，即可以写数据时回调，实现无阻塞写
 *
 * 回调方法将由容器根据情况自动调用
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-20 下午5:47
 * <p>Version: 1.0
 */
public class MyWriteListener implements WriteListener {

    private final ServletOutputStream os;
    private final AsyncContext asyncContext;

    private int count = 0;

    public MyWriteListener(ServletOutputStream os, AsyncContext asyncContext) {
        this.os = os;
        this.asyncContext = asyncContext;
    }

    @Override
    public void onWritePossible() throws IOException {
        // isReady : 类似于ServletInputStream的isReady，如果有可以无阻塞写数据(从false变为true时)，返回true
        boolean isClose = false;
        while(os.isReady()) {
            os.write((byte)'i');
            count++;

            //此处即需要用户判断什么时候结束写，如果不及时处理，可能遭遇timeout
            if(count >= (1024 * 1000)) { //写1024 * 1000个就结束 即完成写（目的是发现阻塞的情况）
                isClose = true;
                System.out.println("服务器写完1024 * 1000个字节了");
                os.close();
                asyncContext.complete();
                break;
            }
        }
        if(!isClose) {//忽略写完的情况
            //如果此时还输出true，表示还可以往外写 即速度很快
            System.out.println("服务器当前可写:" + os.isReady());//即阻塞时，返回false
        }
    }

    @Override
    public void onError(final Throwable t) {
        //使用非阻塞I/O写失败时回调
        System.out.println("\n服务器写入失败了");
        t.printStackTrace(System.out);
        asyncContext.complete();
    }
}
