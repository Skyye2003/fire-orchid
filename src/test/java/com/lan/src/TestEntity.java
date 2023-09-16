package com.lan.src;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    private String name;
    private String type;
    private Integer attribute;
    private Integer startId;
    private Integer len;
}
