/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.web.controller.bigpage;

import com.sishuok.bigpipe.BigPipeTask;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 类似于pipe，但把文档分成若干个独立的pagelet，每个pagelet可单独渲染；其组成部分是：
 * 1、1个主框架
 * 2、N个独立的pagelet
 *
 *
 * 传统方式：
 * 1、浏览器发请求；
 * 2、服务器接收到请求，开始写响应（比如之前的pipe，是串行的）；
 * 3、接着返回响应；
 * 3.1、下载css
 * 3.2、渲染页面
 * 3.3、下载js
 *
 * bigpipe：
 * 1、浏览器发请求；
 * 3、服务器接收到请求，首先返回第一屏响应（主框架）
 * 4、接着返回N个pagelet的响应；
 * 4.1、下载css
 * 4.2、渲染页面
 * 4.3、下载js
 *
 * bigpipe的好处是第3步只返回主框架 而且速度非常快，
 * 接着服务器端并发计算响应内容，谁先计算完就立即返回，客户端再渲染出来
 *
 * 优缺点请参考 新浪微博的《使用Big pipe提升浏览速度》
 *
 *
 * 此次请求一般情况下在5秒左右渲染完毕
 *
 *
 * 一些资料：
 * 使用Big pipe提升浏览速度v2
 * http://www.slideshare.net/kumawu/big-pipe-9048228

 * Big pipe backend2
 * http://www.slideshare.net/kidwish/big-pipe-backend2

 * Facebook让网站速度提升一倍的BigPipe技术分析
 * http://limu.iteye.com/blog/765173

 * [译]BigPipe：高性能的“流水线技术”网页
 * http://www.alibuybuy.com/posts/66345.html

 * BigPipe学习研究
 * http://www.searchtb.com/2011/04/an-introduction-to-bigpipe.html
 *
 * Facebook创新之BigPipe：优化页面加载时间
 * http://www.infoq.com/cn/news/2010/08/bigpipe-facebook-optimize
 *
 *
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-28 下午7:30
 * <p>Version: 1.0
 */
@Controller
public class BigPipeController {

    @RequestMapping("/bigpipe")
    public Object bigpipe(final HttpServletRequest request, final HttpServletResponse response) {

        //提供给各pagelet的数据
        Map<String, Object> model = new HashMap();

        return new BigPipeTask(
                model,
                "bigpipe/index",
                "bigpipe/part1",
                "bigpipe/part2",
                "bigpipe/part3"
        );

    }

}
