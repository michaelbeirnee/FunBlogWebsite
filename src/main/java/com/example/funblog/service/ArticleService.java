//This tells Java which package this files belong to
package com.example.funblog.service; 

//This imports your article class so this service can use Article objects 
import com.example.funblog.model.Article; 

//This imports ObjectMapper - which converts Java objects to JSON and JSON back to Java Objects 
import com.fasterxml.jackson.databind.ObjectMapper; 

//This imports the @Service annotation from Spring 
import org.springframework.stereotype.Service; 

//Imports IOException which handles file reading/writing errors
import java.io.IOException; 

//imports DirectoryStream - whuch lets us loop through files in a folder 
import java.nio.file.DirectoryStream; 

//import Files - which gives us method to create, read, write, and delete files 
import java.nio.file.Files; 

//imports Path - which represents a file or folder path 
import java.nio.file.Path;

//imports Path - which helps create Path objects from stream 
import java.nio.file.Paths; 

//Import ArrayList
import java.util.ArrayList; 

//importsLIst - which is general list type 
import java.util.List; 

@Service 
public class ArticleService{

    
    private final Path articlesFolder = Paths.get("articles"); 
    private final ObjectMapper objectMapper = new ObjectMapper(); 

    //This constructor runs automatically when ArticleService is created 
    public ArticleService(){
        try{
            //This checks if the articles folder does not exist yet
            if(!Files.exists(articleFolder)){
                Files.createDirectories(articleFolder);
            }
        //This catches errors that happen while creating the folder 
        }catch(IOException exception){
            //This stops the app and explains that the articles folder count not be created 
            throw new RuntimeException("Could not create articles foldert", exception); 
        }
    }
}
