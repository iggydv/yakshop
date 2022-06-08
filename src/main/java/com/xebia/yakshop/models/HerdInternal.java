package com.xebia.yakshop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class HerdInternal {
    List<LabYakInternal> herd;
}
