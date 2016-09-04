/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by yannick on 25/8/16.
 */
@Entity
public class TestObj2 {
    @Id
    private int id;

    public TestObj2() {
    }

    public TestObj2(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
