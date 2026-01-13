package com.dhruv.hospital_staffing.dto.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResponseDto<T> {
    private int page;
    private int size;
    private long total;
    private List<T> items;
}
