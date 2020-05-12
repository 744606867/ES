package com.es.test.es.testES;

import com.es.test.es.model.Person;
import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ESt {

    private static  RestHighLevelClient client;
    static {
         client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("123.57.79.201", 9200, "http")));

    }



    /*
    * 关于索引操作
    * createIndexRequest
    * getIndexRequest
    * deleteIndexRequest
    * */
    //ES 7.6.2  Rest操作    创建索引
    @Test
    public void createIndexRequest(){
        //指定索引名称
        //索引名称不能有大写
        CreateIndexRequest myjavaes = new CreateIndexRequest("xxxa");
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(myjavaes, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //ES 7.6.2  Rest操作    判断索引是否存在
    @Test
    public void existsindex(){
        GetIndexRequest xxxa = new GetIndexRequest("x1xxa");
        boolean exists = false;
        try {
            exists = client.indices().exists(xxxa, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(exists);

    }

    //ES 7.6.2  Rest操作    获取索引信息
    @Test
    public void getIndexRequest(){
        GetIndexRequest xxxa = new GetIndexRequest("xxxa");
        try {
            GetIndexResponse getIndexResponse = client.indices().get(xxxa, RequestOptions.DEFAULT);
            getIndexResponse.getAliases().forEach((k,v)->{
                System.out.println(k+"==>"+v);
            });
            System.out.println("==================================");
            getIndexResponse.getDefaultSettings().forEach((k,v)->{
                System.out.println(k+"==>"+v);
            });
            System.out.println("==================================");
            for (String index : getIndexResponse.getIndices()) {
                System.out.println(index);
            }
            System.out.println("==================================");
            getIndexResponse.getSettings().forEach((k,v)->{
                System.out.println(k+"==>"+v);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //ES 7.6.2  Rest操作    删除索引
    @Test
    public void deleteIndexRequest(){
        DeleteIndexRequest xxxa = new DeleteIndexRequest("xxxa");
        try {
            AcknowledgedResponse delete = client.indices().delete(xxxa,RequestOptions.DEFAULT);
            System.out.println(delete.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * 关于文档操作
     * new IndexRequest("索引名称")  指定要操作那个索引
     * getRequest
     * updateRequest
     * deleteRequest
     * BulkRequest
     * */
    //ES 7.6.2  Rest操作    创建文档
    @Test
    public void create1IndexRequest(){
        Date data = new Date();
        Person person = new Person("李四", "河南", 10, data);
        IndexRequest xxxa = new IndexRequest("xxxa");
        xxxa.id("3");
        IndexRequest source = xxxa.source(new Gson().toJson(person).toString(), XContentType.JSON);
        try {
            IndexResponse index = client.index(source, RequestOptions.DEFAULT);
            System.out.println(index.status());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //ES 7.6.2  Rest操作    获取文档信息
    @Test
    public void getRequest(){
        try {
            GetRequest xxxa = new GetRequest("xxxa", "2");
            GetResponse   documentFields = client.get(xxxa, RequestOptions.DEFAULT);
           /* documentFields.getFields().forEach((k,v)->{
                System.out.println(k+"==>"+v);
            });*/
            documentFields.getSource().forEach((k,v)->{
                System.out.println(k+"==>"+v);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //ES 7.6.2  Rest操作    更新文档信息
    @Test
    public void updateRequest(){
        try {
            Date data = new Date();
            Person person = new Person("王五", "四川", 33, data);
            UpdateRequest xxxa = new UpdateRequest("xxxa","1");
            UpdateRequest updateRequest = xxxa.doc(new Gson().toJson(person), XContentType.JSON);
            UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(update.status());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //ES 7.6.2  Rest操作    删除文档信息
    @Test
    public void deleteRequest(){

        try {
            DeleteRequest xxxa = new DeleteRequest("xxxa", "1");
            DeleteResponse delete = client.delete(xxxa, RequestOptions.DEFAULT);
            System.out.println(delete.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //ES 7.6.2  Rest操作    批量插入文档信息
    @Test
    public void BulkRequest() throws IOException {
        Date data = new Date();
        BulkRequest xxxa = new BulkRequest("xxxa");
        ArrayList<Person> people = new ArrayList<>();
        for (int i = 0;i<50;i++){
            people.add(new Person("liu"+i,"a"+i,1+i,data));
        }
        for (int i = 0; i < people.size(); i++) {
            xxxa.add(new IndexRequest()
                    .source(new Gson().toJson(people.get(i)),XContentType.JSON)
            );
        }
        BulkResponse bulk = client.bulk(xxxa, RequestOptions.DEFAULT);
        //判断执行成功返回false
        System.out.println(bulk.hasFailures());
        System.out.println("==================================");
        System.out.println(bulk);
    }


    //ES 7.6.2  Rest操作    复杂文档搜索（搜索条件）
    @Test
    public void searchRequest() throws IOException {
        //指定查询索引
        SearchRequest xxxa = new SearchRequest("xxxa");
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //精确查询
        QueryBuilder termQuery = QueryBuilders.termQuery("address", "a1");

        SearchSourceBuilder query = searchSourceBuilder.query(termQuery);

        SearchRequest source = xxxa.source(query);
        SearchResponse search = client.search(source, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }
    }


}
