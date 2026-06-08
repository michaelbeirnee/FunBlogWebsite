package com.example.funblog.controller; 

import com.example.funblog.model.Article; 
import com.example.funblog.service.ArticleService;

// imports HttpSession which lets the app remember that the admin is logged in
import jakarta.servlet.http.HttpSession;

//tells the spring this class handles web page requests
import org.springframework.stereotype.Controller; 

//lets java send data to httpPages
import org.springframework.ui.Model; 

//imports Spring web annotation like GetMapping, PostMapping, PathVariable ,etc 
import org.springframework.web.bind.annotation.*; 

//tells us this interacts with web aka web controller
@Controller
public class AdminController{

    private final ArticleService articleService; 
    private final String username = "admin"; 
    private final String password = "password"; 

    public AdminController(ArticleService articleService){
        this.articleService = articleService; 
    }

    //runs when someone visits login 
    @GetMapping("/login")
    public String showLoginPage(){
        return "login"; 
    }

    @PostMapping("/login")
    public String login(
        @RequestParam String username,
        @RequestParam String password,
        // gives us access to the user's session
        HttpSession session
    ){
        //this checks if our username & password match the hard codeded ones 
        boolean validLogin = this.username.equals(username) && this.password.equals(password); 

        if(!validLogin){
            return "redirect:/login"; 
        }

        session.setAttribute("loggedIn", true); 

        return "redirect:/admin"; 

    }

    
    @GetMapping("/admin")
    public String showAdminPage(HttpSession session, Model model){

        if(!isLoggedIn(session)){
            return "redirect:/login"; 
        }

        model.addAttribute("articles", articleService.getAllArticles()); 
        return "admin"; 
    }

    @GetMapping("/admin/articles/new")
    public String showNewArticlePage(HttpSession session, Model model){

        if(!isLoggedIn(session)){
            return "redirect:/login";
        }

        model.addAttribute("article", new Article()); 

        return "new-article"; 
    }

    //method runs when the article form is submitted
    @PostMapping("/admin/article")
    public String createArticle(
        @ModelAttribute Article article, 
        HttpSession session
    ){
        if(!isLoggedIn(session)){
            return "redirect:/login";
        }

        articleService.createArticle(article);
        return "redirect:/admin"; 
    }

    @GetMapping("/admin/articles/{id}/edit")
    public String showEditArticlePage(
        @PathVariable String id,
        // gives access to the user's session
        HttpSession session,
        // lets java send data to the HTML page
        Model model
    ){
        if(!isLoggedIn(session)){
            return "redirect:/login"; 
        }

        Article article = articleService.getArticleById(id); 

        if(article == null){
            return "redirect:/admin"; 
        }
        
        model.addAttribute("article", article); 

        return "edit-article";

    }

    @PostMapping("/admin/articles/{id}/edit")
    public String updateArticle(

        @PathVariable String id, 
        //takes form fields and turns them into an article object 
        @ModelAttribute Article article, 
        HttpSession session

    ){

        if(!isLoggedIn(session)){
            return "redirect:/login"; 
        }

        articleService.updateArticle(id, article); 

        return "redirect:/admin";
    }

    @PostMapping ("/admin/articles/{id}/delete")
    public String deleteArticle(

        @PathVariable String id, 
        HttpSession session
    ){
        if(!isLoggedIn(session)){
            return "redirect:/login"; 
        }

        articleService.deleteArticle(id); 

        return "redirect:/admin";
    }

    private boolean isLoggedIn(HttpSession session){
    
        Object loggedIn = session.getAttribute("loggedIn");
        
        return loggedIn != null && loggedIn.equals(true); 
    }
}