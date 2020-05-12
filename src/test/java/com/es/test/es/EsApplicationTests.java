package com.es.test.es;

import com.es.test.es.utils.ESclient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@SpringBootTest
class EsApplicationTests {
    @Autowired
    @Qualifier("getClient")
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testEs1(){
        CreateIndexRequest myjavaes = new CreateIndexRequest("myjavaes");
        try {
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(myjavaes, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
