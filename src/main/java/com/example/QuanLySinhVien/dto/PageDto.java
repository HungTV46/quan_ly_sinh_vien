package com.example.QuanLySinhVien.dto;

import com.example.QuanLySinhVien.constant.Constant;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDto {
    private static ObjectMapper mapper = new ObjectMapper();

    @JsonIgnore
    @JsonAnyGetter
    @JsonAnySetter
    private Map<String,Object> data;
    private Integer page;
    private Integer size;
    private Boolean unPaged = false;
    private Boolean unSorted = false;

    public Pageable toPageable() {
        return unPaged ? Pageable.unpaged() :
                Pageable.ofSize(size != null ? size : Constant.Pageable.DEFAULT_SIZE)
                        .withPage((page != null ? page : Constant.Pageable.DEFAULT_PAGE) - 1);

    }

    public Pageable toPageable(Sort sortAddition) {
        Sort sort = unSorted ? Sort.unsorted() : sortAddition;
        return unPaged ? Pageable.unpaged(sort) :
                PageRequest.of((page != null ? page : Constant.Pageable.DEFAULT_PAGE) - 1,
                        size != null ? size : Constant.Pageable.DEFAULT_SIZE,
                        sort);
    }

}
