/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Created by yannick on 25/8/16.
 */
@Entity
public class TestObj {
    @Id
    private int id;
    @OneToOne
//    @Transient
    private TestObj2 testObj2;

    public TestObj() {
    }

    public TestObj(int id) {
        this.id = id;
    }

    public TestObj(int id, TestObj2 testObj2) {
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