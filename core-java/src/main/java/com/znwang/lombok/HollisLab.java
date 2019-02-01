package com.znwang.lombok;

import lombok.Data;

/**
 * lombok插件测试
 * @author zn.wang
 */
public class HollisLab {

    @Data
    private static class Wechat {
        private String id;
        private String desc;
    }

    public static void main(String[] args) {
        Wechat wechat = new Wechat();
        wechat.setId("Hollis");
        wechat.setDesc("每日更新Java技术文章");
        System.out.println(wechat);
    }
}

