package com.es.test.es.action;

import com.es.test.es.utils.ESclient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class EsTest {
    @Autowired
    @Qualifier("getClient")
    private RestHighLevelClient restHighLevelClient;

   /* @RequestMapping("/")
    public void testEs(){
        CreateIndexRequest myjavaes = new CreateIndexRequest("myjavaes");
        try {
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(myjavaes, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}
