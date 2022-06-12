package com.xebia.yakshop.storage;

import com.xebia.yakshop.models.LabYakInternal;
import java.util.List;

public interface HerdStorage {

    void setHerd(List<LabYakInternal> herd);

    List<LabYakInternal> getHerd();
}
