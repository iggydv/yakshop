package com.xebia.yakshop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LabYakInternal {
    String name;
    String age;
    int sex;
    int ageLastShaved;
}
