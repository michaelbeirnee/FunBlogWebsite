//This files belongs to the com.example.funblog package
//Package is like a folder / group for your java files 
package com.example.funblog; 

//importing the SpringBoot application
import org.springframework.boot.SpringApplication; 
//imports the SpringBoot annotation - which says this is a springboot app 
import org.springframework.boot.autconfigure.SpringBootApplication; 

//Says this is the starting point 
@SpringBootApplication 
public class FunBlogWebsiteApplication{
    //First methods that java runs when the application starts 
    public static void main(String[] args){
        //Starts the Spring Boot application 
        //Passing args as startup options 
        SpringApplication.run(FunBlogWebsiteApplication.class, args); 
    }
}

