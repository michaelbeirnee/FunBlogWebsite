package com.example.funblog.model; 

//This Class represents one blog article 
public class Article{
    
    private String id; 
    private String title; 
    private String content; 
    private String publishedDate; 

    //Empty constructor 
    //Spring / Java often needs this to create 
    public Article(){

    }

    //Constructor used to create an Article with all fields filled in 
    public Article(
        String id, String title, String content, String publishedDate
    ){
        this.id = id; 
        this.title = title; 
        this.content = content; 
        this.publishedDate = publishedData;  
    }

    public String getId(){
        return id; 
    }

    private void setId(String id){
        this.id = id; 
    }

    public String getTitle(){
        return title;  
    }

    private void setTitle(){
        this.title = title; 
    }

    public String getContent(){
        return content; 
    }

    private void setContent(){
        this.content = content; 
    }

    public String getPublishedDate(){
        return publishedDate; 
    }

    private void setPublishedDate(){
        this.publishedDate = publishedDate; 
    }
}