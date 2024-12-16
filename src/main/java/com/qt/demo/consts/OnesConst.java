package com.qt.demo.consts;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2024/08/28 上午9:46
 * @version: V1.0
 */
public interface OnesConst {


    String exportUrl = "https://pms.sinopharmholding.com/project/api/project/team/9XGSEMCg/items/graphql?t=report-data__workspace_manhour-9XGSEMCg";


    String requestBody = "{\"query\":\"query QUERY_MANHOURS($groupBy: GroupBy, $orderBy: OrderBy, $timeSeries: TimeSeriesArgs, $timeSeriesWithWorkDays: TimeSeriesArgs, $actualHoursSum: String, $filter: Filter, $columnSource: Source) {\\n  buckets(groupBy: $groupBy, orderBy: $orderBy, filter: $filter) {\\n    ...ColumnBucketFragment\\n  }\\n}\\n\\nfragment TaskSimple on Task {\\n  key\\n  uuid\\n  name\\n  number\\n  project {\\n    uuid\\n  }\\n}\\n\\nfragment ColumnBucketFragment on Bucket {\\n  key\\n  columnField: aggregateTask(source: $columnSource) {\\n    ...TaskSimple\\n  }\\n  actualHours(sum: $actualHoursSum)\\n  actualHoursSeries(timeSeries: $timeSeries) {\\n    times\\n    values\\n  }\\n}\\n\",\"variables\":{\"groupBy\":{\"manhours\":{\"task\":{}}},\"orderBy\":{\"aggregateTask\":{\"createTime\":\"DESC\"}},\"filter\":{\"manhours\":{\"task_notIn\":[null],\"startTime_range\":{\"unit\":\"day\",\"from\":\"%s\",\"to\":\"%s\"},\"owner_in\":[\"$currentUser\"]},\"actualHours_notIn\":[0]},\"actualHoursSum\":\"manhours.recordedHour\",\"timeSeries\":{\"timeField\":\"manhours.startTime\",\"valueField\":\"manhours.recordedHour\",\"unit\":\"day\",\"from\":\"%s\",\"to\":\"%s\"},\"timeSeriesWithWorkDays\":{\"timeField\":\"manhours.startTime\",\"valueField\":\"manhours.recordedHour\",\"unit\":\"day\",\"from\":\"%s\",\"to\":\"%s\",\"constant\":800000,\"workdays\":[\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fri\"]},\"columnSource\":\"task\"}}";


    String cookie = "language=zh; UM_distinctid=18ef416a5228fe-0cd5c53c379709-1b525637-1ea000-18ef416a523f55; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22GMr5xcq1%22%2C%22first_id%22%3A%2218eb61f93c27ae-0aba5423adafcb-1c525637-2007040-18eb61f93c3421%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfbG9naW5faWQiOiJHTXI1eGNxMSIsIiRpZGVudGl0eV9jb29raWVfaWQiOiIxOGViNjFmOTNjMjdhZS0wYWJhNTQyM2FkYWZjYi0xYzUyNTYzNy0yMDA3MDQwLTE4ZWI2MWY5M2MzNDIxIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22GMr5xcq1%22%7D%2C%22%24device_id%22%3A%2218eb61f93c27ae-0aba5423adafcb-1c525637-2007040-18eb61f93c3421%22%7D; sajssdk_2015_cookie_access_test=1; route=c80d0adc843285e911472da0dca597a1; timezone=Asia/Shanghai; APPHUB_WEBUI_SID=055091C16A474934BDE15231E94C7C34; OAMAuthnHintCookie=0@1724115955; SELF_SERVICE_SID=B53F24A952E3404B96A820948469C4AC; third_state=ones_123; ct=c3f3e56a882e1360e12f82c99b9ca2509031aade61b921d30d14f14bdc0d7ccd; ones-uid=GMr5xcq1; ones-lt=nCy82doxgdfeslvixVdhqr9fTcNmxxNU0IaXXQvQyyYAk2aqcTnAp4BnDIYA7ejd";



}
