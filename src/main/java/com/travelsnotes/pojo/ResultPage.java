package com.travelsnotes.pojo;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "统一返回页面格式")
public class ResultPage {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回文章列表")
    private Map<String, PageInfo<Article>> pageData = new HashMap<String ,PageInfo<Article>>();
    private ResultPage(){}
    public static ResultPage ok(){
        ResultPage r = new ResultPage();
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setSuccess(ResultCodeEnum.SUCCESS.getStatus());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static ResultPage error(){
        ResultPage r = new ResultPage();
        r.setCode(ResultCodeEnum.UNKNOW_REASON.getCode());
        r.setSuccess(ResultCodeEnum.UNKNOW_REASON.getStatus());
        r.setMessage(ResultCodeEnum.UNKNOW_REASON.getMessage());
        return r;
    }

    public static ResultPage ok(ResultCodeEnum codeEnum){
        ResultPage r = new ResultPage();
        r.setCode(codeEnum.getCode());
        r.setSuccess(codeEnum.getStatus());
        r.setMessage(codeEnum.getMessage());
        return r;
    }

    public static ResultPage error(ResultCodeEnum codeEnum){
        ResultPage r = new ResultPage();
        r.setCode(codeEnum.getCode());
        r.setSuccess(codeEnum.getStatus());
        r.setMessage(codeEnum.getMessage());
        return r;
    }
    public ResultPage pageData(Map<String,PageInfo<Article>> map){
        this.setPageData(map);
        return this;
    }


    public ResultPage message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultPage code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultPage success(Boolean success){
        this.setSuccess(success);
        return this;
    }

}
