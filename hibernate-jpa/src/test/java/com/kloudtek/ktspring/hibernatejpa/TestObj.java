/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring.hibernatejpa;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by yannick on 25/8/16.
 */
@Entity
public class TestObj {
    @Id
    private int id;

    public TestObj() {
    }

    public TestObj(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
