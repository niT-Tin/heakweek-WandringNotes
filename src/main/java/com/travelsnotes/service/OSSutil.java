package com.travelsnotes.service;

import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.travelsnotes.pojo.OSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import java.io.*;
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

    public boolean setAvatar(String base64Data, int id) throws IOException {
        try {
            ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getKeyid(), ossProperties.getKeysecret());
            System.out.println(base64Data);
            String imgFilePath = "/Avatar/";
            String dataPrix = ""; //base64格式前头
            String data = "";//实体部分数据
            if(base64Data==null||"".equals(base64Data)){
                return false;
            }else {
                String [] d = base64Data.split("base64,");//将字符串分成数组
                if(d != null && d.length == 2){
                    dataPrix = d[0];
                    data = d[1];
                }else {
                    return false;
                }
            }
            String suffix = "";//图片后缀，用以识别哪种格式数据
            //data:image/jpeg;base64,base64编码的jpeg图片数据
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){
                suffix = ".jpg";
            }else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){
                //data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            }else if("data:image/gif;".equalsIgnoreCase(dataPrix)){
                //data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            }else if("data:image/png;".equalsIgnoreCase(dataPrix)){
                //data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else {
                return false;
            }

            BASE64Decoder decoder = new BASE64Decoder();
            try {
                //Base64解码
                byte[] b = decoder.decodeBuffer(data);
                for(int i=0;i<b.length;++i) {
                    if(b[i]<0) {
                        //调整异常数据
                        b[i]+=256;
                    }
                }
                OutputStream out = new FileOutputStream(imgFilePath + id + suffix);
                out.write(b);
                out.flush();
                out.close();
                File file = new File(imgFilePath + id + suffix);
                InputStream inputStream = new FileInputStream(imgFilePath + id + suffix);
                ossClient.putObject(ossProperties.getHeadPicbucket(), id + suffix, inputStream);
                if(file.exists())
                    if(file.isFile())
                        file.delete();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
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
