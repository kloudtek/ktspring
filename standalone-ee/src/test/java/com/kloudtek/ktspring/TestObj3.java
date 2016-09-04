/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by yannick on 25/8/16.
 */
@Entity
public class TestObj3 {
    @Id
    private int id;
    @OneToOne
    private TestObj2 testObj2;

    public TestObj3() {
    }

    public TestObj3(int id) {
        this.id = id;
    }

    public TestObj3(int id, TestObj2 testObj2) {
        this.id = id;
        this.testObj2 = testObj2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
