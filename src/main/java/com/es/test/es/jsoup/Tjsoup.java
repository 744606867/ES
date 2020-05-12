package com.es.test.es.jsoup;

import com.es.test.es.model.JDdata;
import com.es.test.es.model.Person;
import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

public class Tjsoup {


    private static RestHighLevelClient client;
    static {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("123.57.79.201", 9200, "http")));

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
                    .source(new Gson().toJson(people.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulk = client.bulk(xxxa, RequestOptions.DEFAULT);
        //判断执行成功返回false
        System.out.println(bulk.hasFailures());
        System.out.println("==================================");
        System.out.println(bulk);
    }


    public static void main(String[] args) throws IOException {
        String s = "java";
        String encode = URLEncoder.encode(s, "GBK");
        Document document = Jsoup.parse(new URL("http://search.jd.com/Search?keyword="+encode), 30000);
        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        ArrayList<JDdata> jdDatee = new ArrayList<JDdata>();

        for (Element element1 : elements) {
            String img = element1.getElementsByTag("img").eq(0).attr("source-data-lazy-img");
            String price = element1.getElementsByClass("p-price").eq(0).text();
            String name = element1.getElementsByClass("p-name").eq(0).text();

            JDdata jDdata = new JDdata();
            jDdata.setName(name);
            jDdata.setPrice(price);
            jDdata.setImg(img);
            jdDatee.add(jDdata);
        }
        BulkRequest xxxa = new BulkRequest("tb");
        jdDatee.forEach(e->{
            xxxa.add(new IndexRequest()
            .source(new Gson().toJson(e),XContentType.JSON));
        });
        BulkResponse bulk = client.bulk(xxxa, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }
}
