package com.travelsnotes.service;

import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.travelsnotes.pojo.OSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class OSSutil {
    com.aliyun.oss.OSS ossClient;
    @Autowired
    private OSS ossProperties;

    public List<String> getPicList(String bucketName) {
        List<String> listPic = new ArrayList<>();
        //连接阿里云OSS对象存储
        String endpoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getKeyid();
        String accessKeySecret = ossProperties.getKeysecret();

        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ObjectListing lists = ossClient.listObjects(bucketName);
            for (OSSObjectSummary ossObjectSummary : lists.getObjectSummaries()) {
                //获取图片url
                listPic.add(ossObjectSummary.getKey());
            }
            return listPic;
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            ossClient.shutdown();
        }
    }

    public boolean setAvatar(String AvatarUrl, int i) throws IOException {
        try {
            ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getKeyid(), ossProperties.getKeysecret());
            InputStream inputStream = new URL(AvatarUrl).openStream();
            ossClient.putObject(ossProperties.getHeadPicbucket(), i + ".jpg", inputStream);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            ossClient.shutdown();
        }
    }

    public String getAvatar(int id) {

        try {
            ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getKeyid(), ossProperties.getKeysecret());
            ObjectListing lists = ossClient.listObjects(ossProperties.getHeadPicbucket());
            return ossProperties.getFileheadhost() + lists.getObjectSummaries().get(id).getKey();
        } catch (Exception e) {
            return "failed!";
        }
    }

}
