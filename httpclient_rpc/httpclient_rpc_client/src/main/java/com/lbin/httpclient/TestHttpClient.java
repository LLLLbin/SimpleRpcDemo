package com.lbin.httpclient;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbin.httpclient.pojo.User;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TestHttpClient {
    public static void main(String[] args) throws IOException, URISyntaxException {
        testPost();
    }

    /**
     * 无参数GET请求
     * 访问网站过程：1.打开浏览器 2.输入网址 3.访问 4.看结果
     * 使用HtppClient 1.创建客户端 2.创建请求地址 3.发起请求 4.处理响应结果
     */
    public static void testGetNoParams() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost/test");
        CloseableHttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "UTF-8");
        System.out.println("客户端响应数据" + string);
        client.close();
    }

    /**
     * 有参调用
     *
     * @throws IOException
     */
    public static void testGetWithParams() throws IOException, URISyntaxException {
        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder("http://localhost/params");
        //单个传递
//        builder.addParameter("name","lbin");
//        builder.addParameter("password","121522");
//        URI uri = builder.build();
        List<NameValuePair> nps = new ArrayList<NameValuePair>();
        nps.add(new BasicNameValuePair("name", "lbin"));
        nps.add(new BasicNameValuePair("password", "admin121522"));
        builder.addParameters(nps);
        URI uri = builder.build();
        HttpEntity entity = client.execute(new HttpGet(uri)).getEntity();
        String string = EntityUtils.toString(entity, "UTF-8");
        System.out.println("客户端响应数据" + string);
        client.close();
    }

    public static void testPost() throws IOException, URISyntaxException {
        CloseableHttpClient client = HttpClients.createDefault();
        //无参数构造
        HttpPost post = new HttpPost("http://localhost/test");
        CloseableHttpResponse response = client.execute(post);
        System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        //有参的Post请求
        //请求头传递参数，和Get请求携带参数的方式一致
        URIBuilder builder = new URIBuilder("http://localhost/params");
        builder.addParameter("name", "lbin");
        builder.addParameter("password", "121522");
        CloseableHttpResponse response1 = client.execute(new HttpPost(builder.build()));
        System.out.println(EntityUtils.toString(response1.getEntity(), "UTF-8"));

        //使用请求体传递参数
        HttpPost bodyParamsPost = new HttpPost("http://localhost/bodyParams");
        //定义请求协议体，设置请求参数
        //使用请求体进行传递参数的时候，需要定义请求体格式，默认是表单格式
        //使用URIBuilder构建URI对象，就是请求体传递参数
        User user = new User();
        user.setName("name1");
        user.setPassword("password1");
        User user1 = new User();
        user1.setName("name2");
        user1.setPassword("password2");
        List<User> list=new ArrayList<User>();
        list.add(user);
        list.add(user1);
        ObjectMapper mapper = new ObjectMapper();
        String paramsString = mapper.writeValueAsString(list);
        //自己拼接成JSON，不好
//        String paramsString = "[" + user.toString() + ", " + user1.toString() + "]";
        HttpEntity entity = new StringEntity(paramsString, "application/json", "UTF-8");
        bodyParamsPost.setEntity(entity);
        String responseUser = EntityUtils.toString(client.execute(bodyParamsPost).getEntity(), "UTF-8");
        String substring = responseUser.substring(1, responseUser.indexOf("},") + 1);
        User rUser = mapper.readValue(substring, User.class);
        System.out.println("密码"+rUser.getPassword());
        JavaType valueType = mapper.getTypeFactory().constructParametricType(List.class, User.class);
        List<User> responseList = mapper.readValue(responseUser, valueType);
        System.out.println(responseList);
    }
}
