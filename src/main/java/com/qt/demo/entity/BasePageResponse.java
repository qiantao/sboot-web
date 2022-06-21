package com.qt.demo.entity;

import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.qt.demo.enums.RequestStatusEnum;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author MuYang @Perfma
 * @Date 2022/03/10 17:53
 * @version: V1.0
 */
@Data
public class BasePageResponse<T> {

    private List<T> dataList = null;


    private long totalSize;

    private int current = 0;
    private int pageSize = 0;
    private int pages = 0;


    public static <T> BasePageResponse<T> result(List<T> data,List sourceList) {
        BasePageResponse<T> response = new BasePageResponse<>();
        convetPageResult(response,sourceList);
        response.setDataList(data);
        return response;
    }

    public static <T> BasePageResponse<T> result(List<T> data) {
        BasePageResponse<T> response = new BasePageResponse<>();
        convetPageResult(response,data);
        return response;
    }



    public static <T> BasePageResponse<T> result() {
        BasePageResponse<T> response = new BasePageResponse<>();
        response.dataList = Lists.newArrayList();
        return response;
    }

    public static <T> void convetPageResult(BasePageResponse<T> response, List pageList){
        int pageNum = 0;
        int pageSize = pageList.size();
        long totalSize = pageList.size();
        int pages = 1;
        if(pageList instanceof Page){
            Page page = (Page)pageList;
            pageNum = page.getPageNum();
            pageSize = page.getPageSize();
            pages = page.getPages();
            totalSize = page.getTotal();
        }
        response.setPages(pages);
        response.setPageSize(pageSize);
        response.setCurrent(pageNum);
        response.setDataList(pageList);
        response.setTotalSize(totalSize);
    }
}
