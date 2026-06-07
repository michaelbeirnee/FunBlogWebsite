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

    //Method gets every article from the articles folder 
    public List<Article> getAllArticles(){

        //create an empty array list to hold all articles
        List<Article> articles = new ArrayList<>();

        //Open the articels folder and only look for files ending with .json
        try(DirectoryStream<Path> files = Files.newDirectoryStream(articlesFolder, "*.json")){
            for(Path file : files){
                
                Article article = objectMapper.readValue(file.toFile(), Article.class); 
                articles.add(article); 

            }
            //Catch file errors that could happen while reading article files
        }catch(IOException exception){
            //Stop the app and show a clearer error message
            throw new RunTimeException("Could not load articles", exception); 
        }

        return articles; 
    }

    public Article getArticleById(String id){
        //Build the file path - like article/ my first post.json
        Path articleFile = articlesFolder.resolve(id + ".json");

        //If the article file does not exist = return null because no articles were found
        if(!Files.exists(articleFile)){
            return null; 
        }

        try{
            //Read the Json file and turn it into an Article object 
            return objectMapper.readValue(articleFile.toFile(), Article.class); //why do we invoke .class 
        } catch (IOException exception){
            //Stop the app and show a clearer error message 
            throw new RuntimeException("Could not load article", exception); 
        }
    }

    //Method create a new article file
    public void createArticle(Article article){

        //convert article title to id 
        String id = convertTitleToId(article.getTitle()); 
        //Store the generated id inside the article object 
        article.setId(id); 
        //Build the file path where the article will be saved
        Path articleFile = articlesFolder.resolve(id + ".json"); 

        try{
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(articleFile.toFile(), article); 
        }catch (IOException exception){
            throw new RuntimeException("could not save article", exception); 
        }
    }

    public void updateArticle(String id, Article article){

        //keep the id the same 
        article.setId(id); 

        //build the path to the exisiting article file
        //resolve == .add()
        Path articleFile = articlesFolder.resolve(id + ".json"); 

        //converting out Article article "object" into json AKA website posting 
        try{
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(articleFile.toFile(), article); 
        }catch (IOException exception){
            throw new RuntimeException("Could not update Article", exception); 
        }
    }
    
    public void deleteArticle(String id){

        Path articleFile = articlesFolder.resolve(id + ".json"); 
        try{
            Files.deleteIfExists(articleFile); 
        }catch (IOException exception){
            throw new RuntimeException("couldn't find it ", exception); 
        }
    }


    private String convertTitleToId(String title){
        //Start with the title
        return title.toLowerCase().replaceAll("[^a-z0-9 ]", "").trim().replaceAll("\\s+", "-"); 
    }
}
