package com.qt.demo.controller.es;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedTopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 文件处理接口
 */
@RestController
@RequestMapping("/es")
@Slf4j
public class EsController {

    @Autowired
    private RestHighLevelClient esClient;

    @GetMapping("/query")
    public String upload() {
        String indexName ="stress_error_metric_v2_insert";
        String type ="errorKey";
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("report_id.keyword", 505468759721394176L))
//                .must(QueryBuilders.termQuery("label.keyword", ""))
                .must(QueryBuilders.termQuery("error_type.keyword", type));
        String field = "error_code.keyword";
        switch (type) {
            case "keyword":
                field = "error_code.keyword";
                break;
            case "errorKey":
                field = "error_key.keyword";
                break;
            case "errorCode":
                field = "error_code.keyword";
                break;
            default:
                break;
        }
        SearchSourceBuilder aggregation1 = SearchSourceBuilder.searchSource()
                .size(0)
                .query(boolQueryBuilder)
                .aggregation(AggregationBuilders
                        .terms("group_by_error")
                        .field(field)
                        .size(100)
                        .subAggregation(AggregationBuilders
                                .terms("group_by_name")
                                .field("name.keyword")
                                .size(100)
                                .subAggregation(AggregationBuilders
                                        .topHits("top_one")
                                        .sort("total", SortOrder.DESC)
                                        .size(1)
                                )));

        SearchRequest request = new SearchRequest(indexName);
        request.source(aggregation1);
        SearchResponse response = null;
        try {
            response = esClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Aggregations aggregations = response.getAggregations();
        List<ErrorMetric> list = Lists.newArrayList();
        for (Aggregation aggregation : aggregations) {
            for (Terms.Bucket groupByErr : ((ParsedStringTerms) aggregation).getBuckets()) {
                for (Aggregation agg : groupByErr.getAggregations()) {
                    for (Terms.Bucket groupByName : ((ParsedStringTerms) agg).getBuckets()) {
                        ErrorMetric errorMetric = new ErrorMetric();
                        errorMetric.setName(groupByName.getKeyAsString());
                        for (Aggregation a : groupByName.getAggregations()) {
                            ((ParsedTopHits) a).getHits().forEach(var -> list.add(JSONUtil.toBean(var.getSourceAsString(), ErrorMetric.class)));
                        }
                    }
                }
            }
        }



        return "";
    }



}
