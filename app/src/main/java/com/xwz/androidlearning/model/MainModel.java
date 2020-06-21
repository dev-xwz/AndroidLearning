package com.xwz.androidlearning.model;

import java.io.Serializable;

public class MainModel implements Serializable {

//    @SerializedName("name")
    private String name;

//    @SerializedName("value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
