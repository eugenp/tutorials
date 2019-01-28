package com.baeldung.inheritance;

/**
 * 太空旅行者
 * @author zn.wang
 */
public interface SpaceTraveller extends Floatable, Flyable {
    int duration = 10;
    void remoteControl();
}