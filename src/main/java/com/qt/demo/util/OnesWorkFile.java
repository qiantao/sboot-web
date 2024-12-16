package com.qt.demo.util;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName:
 * @Description: ones导出实体
 * @author: Muyang 
 * @date:   2022/06/17 15:56:51
 * @version: V1.0   
 */
@Data
public class OnesWorkFile implements Serializable {

    @Excel(name = "日期", width = 20)
//    @Excel(name = "日期", width = 20,importFormat = "yyyy/MM/dd")
    private String date;
    @Excel(name = "周归属", width = 20,mergeVertical = true)
    private String week;
    @Excel(name = "工作内容", width = 100)
    private String workContent;
    @Excel(name = "备注", width = 20)
    private String desc;







}