package com.equityinfo.pipealarm.view;

/**
 * Created by user on 2016/12/9.
 */
public enum PicStatus {
    both("99"), read("1"), unread("0");
    String value;

    PicStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

