package com.xebia.yakshop.service;

import com.xebia.yakshop.models.HerdInternal;

public interface HerdStorage {
    HerdInternal getHerd();
    void calculateStock();
}
