package com.geektcp.alpha.tool.upload;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author tanghaiyang on 2020/3/7 20:03.
 */
@Slf4j
public class UploadTest {


    @Test
    public void name() {
        Assert.assertTrue(true);
    }

    @Test
    public void upload() throws Exception {
        String url = "http://localhost:9000/upload?name=test";
        HashMap<String,Object> map = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));
        // body
//        String jsonBody = new ObjectMapper().writeValueAsString(map);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        FileSystemResource fileResource = new FileSystemResource(new File("src.txt"));
        param.add("image", fileResource);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, httpHeaders);

        JSONObject response = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        log.info("response: {}",JSON.toJSONString(response,true));


        Assert.assertTrue(true);


    }



    @Test
    public void multipartFileTest() throws Exception{
        File file = new File("src.txt");
        String path = file.getAbsolutePath();
        InputStream inputStream = new  FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test",inputStream);

        File destFile = new File("dest.txt");
        multipartFile.transferTo(destFile);

        Assert.assertTrue(true);
    }
}
