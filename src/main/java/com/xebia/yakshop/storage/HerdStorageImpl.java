package com.xebia.yakshop.storage;

import com.xebia.yakshop.models.LabYakInternal;
import com.xebia.yakshop.models.StockInternal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope("singleton")
public class HerdStorageImpl implements HerdStorage {
    @Builder.Default
    List<LabYakInternal> herd = new ArrayList<>();
}
