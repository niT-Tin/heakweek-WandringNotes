package com.travelsnotes.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travelsnotes.pojo.Article;
import com.travelsnotes.pojo.Result;
import com.travelsnotes.pojo.ResultCodeEnum;
import com.travelsnotes.pojo.ResultPage;
import com.travelsnotes.service.ArticleService;
import com.travelsnotes.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    FileUploadUtil fileUtil;
    @CrossOrigin
    @RequestMapping(value = "/addArticle",method = RequestMethod.POST)
    Result createArticle(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "title",required = false) String title,
                                      @RequestParam(value = "content",required = false) String content,
                                      @RequestParam(value = "tag",required = false,defaultValue = "1") int tag,
                                      @RequestParam(value = "img",required = false) MultipartFile img,
                                      HttpServletRequest request){
        try {
            Object attribute = request.getSession().getAttribute(token);
            if (attribute == null) {
                return Result.error(ResultCodeEnum.FAIL_TOKENNOFINDED);     //token未找到 20010
            }
            String imgUrl = fileUtil.uploadPage(request, img);
            boolean flag=false;
            if (imgUrl==null) flag=true;
            int userId=(int)attribute;
            if (title==null||title.length()==0) title="记录今日的美好";
            Article article = new Article(title, content, tag, imgUrl, new Date(), userId);
            articleService.addArticle(article);
            if (flag) return Result.error(ResultCodeEnum.FAIL_SAVEFILE); //添加手记成功但图片存取失败
            return Result.ok(ResultCodeEnum.SUCCESS_ADDARTICLE) ; //添加文章成功
        }catch (Exception e){
           return Result.error(ResultCodeEnum.PARAM_ERROR); //参数不正确
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/listArticle",method = RequestMethod.GET)
   public ResultPage list(@RequestParam(value = "token") String token,
                           @RequestParam(value = "start",defaultValue = "0")int start,
                           @RequestParam(value = "size",defaultValue = "30")int size,
                           Model model,
                           HttpServletRequest request)throws Exception{
   try {
           Object attribute = request.getSession().getAttribute(token);
           if (attribute == null) {
               return ResultPage.error(ResultCodeEnum.FAIL_TOKENNOFINDED); //token未找到
           }
           int userId = (int) attribute;
           PageHelper.startPage(start, size, "articleId desc");
           List<Article> articleList = articleService.listArticle(userId);
           PageInfo<Article> page = new PageInfo<>(articleList);
           model.addAttribute("page", page);
           Map<String, PageInfo<Article>> map = new HashMap<>();
           map.put("page",page);
            return  ResultPage.ok(ResultCodeEnum.SUCCESS).pageData(map);
       }catch (Exception e){
           return ResultPage.error(ResultCodeEnum.PARAM_ERROR);
       }
    }

}
