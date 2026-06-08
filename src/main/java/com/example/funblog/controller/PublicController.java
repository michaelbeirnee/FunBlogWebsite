//tells java this files eblongs inside the controller package 
package com.example.funblog.controller; 

//imports the article class so this controlelr can work with aritcle object
import com.example.funblog.model.Article;

//article service so the ccontroller can ask for article data
import com.exmaple.funblog.service.ArticleService;

//import controller which tells sprig this class handles web pages 
import org.springframework.stereotype.Controller; 

//imports mopdel which lets us send data from java to the html page
import org.springframework.ui.Model; 

//imports getMapping which handles get requests like visiting a page
import org.springframework.web.bind.annotation.GetMapping; 

//path variable whic gtet values from the url 
import org.springframework.web.bind.annotation.PathVariable; 

import java.util.List; 

//controller classes handle web requests 
@Controller     //tells spring - hey this class is the controller 
public class PublicController{

    //stores the articleService object so this controller can use it 
    private final ArticleService articleService; 

    //gives publicController an article service object 
    public PublicController(ArticleService articleService){
        this.articleService = articleService; 
    }

    @GetMapping ("/")
    public String showHomePage(Model model){

        //gets all artciles from the articles folder 
        List<Article> articles = articleService.getAllArticles(); 

        //this sends teh artilces list to the html page 
        model.addAttribute("articles", articles); 

        //tell spring to show index.html 
        return "index"; 
    }

    @GetMapping("/articles/{id}")
    public String showArticlePage(@PathVariable String id, Model model){

        //get article 
        Article article = articleService.getArticleById(id); 
        if(article == null){
            return "/redirect:/"; 
        }

        //sends the article object to the HTML page - name in html "article"
        model.addAttribute("article", article);

        return "article"; 
    }
}