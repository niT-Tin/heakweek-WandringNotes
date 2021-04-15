package com.travelsnotes.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travelsnotes.pojo.Article;
import com.travelsnotes.pojo.Result;
import com.travelsnotes.pojo.ResultCodeEnum;
import com.travelsnotes.pojo.ResultPage;
import com.travelsnotes.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/addArticle",method = RequestMethod.POST)
    Result createArticle(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "title",required = false) String title,
                                      @RequestParam(value = "content",required = false) String content,
                                      @RequestParam(value = "tag",required = false,defaultValue = "1") int tag,
                                      @RequestParam(value = "img",required = false) String img,
                                      HttpServletRequest request){
        try {
            Object attribute = request.getSession().getAttribute(token);
            if (attribute == null) {
                return Result.error(ResultCodeEnum.FAIL_TOKENNOFINDED);     //token未找到 20010
            }
            int userId=(int)attribute;
            if (title==null||title.length()==0) title="记录今日的美好";
            Article article = new Article(title, content, tag, img, new Date(), userId);
            articleService.addArticle(article);
            return Result.ok(ResultCodeEnum.SUCCESS_ADDARTICLE) ; //添加文章成功
        }catch (Exception e){
           return Result.error(ResultCodeEnum.PARAM_ERROR); //参数不正确
        }
    }

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
