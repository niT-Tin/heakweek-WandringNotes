package com.travelsnotes.pojo;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(true, 200, "成功"),
    UNKNOW_REASON(false, 20001, "未知错误"),
    BAD_SQL_GRAMMER(false, 21001, "sql语法错误"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
    PARAM_ERROR(false, 21003, "参数不正确"),
    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
    EXCEL_DATA_IMPORT_ERROR(false, 21005, "Excel数据导入错误"),
    ERROR_AUTH_CODE(false, 21006, "验证码错误!"),
    ERROR_NOT_EXISTS_USER(false, 21007, "用户不存在!"),
    ERROR_PASSWORD(false, 21008, "密码错误,请重新输入!"),
    FAIL_REGISTER(false, 21009, "注册失败!"),
    FAIL_TOKENNOFINDED(false, 21010, "token未找到!"),
    SUCCESS_LOGIN(true, 200, "登陆成功!"),
    SUCCESS_REGISTER(true, 200, "注册成功!"),
    SUCCESS_USABLE(true, 200, "可以使用该用户名!"),
    SUCCESS_ADDARTICLE(true, 200, "添加文章成功!");
    private Boolean status; //响应是否成功
    private Integer code; //返回码
    private String message; //返回消息
    ResultCodeEnum(Boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}