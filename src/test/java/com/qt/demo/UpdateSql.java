package com.qt.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2024/04/23 下午3:04
 * @version: V1.0
 */
public class UpdateSql {
    static List<String> list = new ArrayList<>();
    static {
        list.add("539438089797637,guokong-guanaiyaofang,1235");
        list.add("504420406755333,guokong-fujian,1109");
        list.add("539435464196101,guoyao-xinan,1010");
        list.add("539435952791557,guokong-gansu,1114");
    }
    public static void main(String[] args) {

        String tenantCode = "guokong-guanaiyaofang";
        String tenantId = "";
        String systemCode = "";
        String shame = "gksk_rebate_calculate_"+systemCode;
        String sql = "update \""+shame+"\".\"calculate_base_data\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_basedata_variaorigi_rel\" set tenant_id = '"+tenantId+"';\n" +
                "update \""+shame+"\".\"calculate_data_source\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_event_inbox\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_event_outbox\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_external_api_log\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_formula\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_scene\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_scene_batch\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_scene_result\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_schedule_retry\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_variable_dtl\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n" +
                "update \""+shame+"\".\"calculate_variable_hrd\" set tenant_code = '"+tenantCode+"', tenant_id = '"+tenantId+"',system_code = '"+systemCode+"' ;\n";


    }
}
