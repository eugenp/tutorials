package com.alibaba.dubbo.provider.service.impl;

import com.alibaba.dubbo.provider.service.DemoService;

/**
 * Created by lihongjie on 7/10/17.
 */
public class DemoServiceImpl implements DemoService {

    public String sayHello() {
        System.out.print("----------Hello World!-----------");
        return "Hello World";
    }
}
