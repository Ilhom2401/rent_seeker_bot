package service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import util.security.BaseData;

import java.io.*;
import java.util.*;

public class FileService {

    static GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    static Gson gson = builder.create();
    static String line;
    static StringBuilder str;

    public static void setUsers(List<User> userList) {
        File file = new File(BaseData.FILE_URL + "/baseFile/user.txt");
        String jsonFile = gson.toJson(userList);
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getUsers(){
        File file = new File(BaseData.FILE_URL + "/baseFile/user.txt");
        str = new StringBuilder();
        try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null)
                str.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonFile = new String(str);
        return gson.fromJson(jsonFile,new TypeToken<List<User>>(){}.getType());
    }

    public static void setHomes(List<Home> homeList)  {
        File file = new File(BaseData.FILE_URL+"/baseFile/home.txt");
        String jsonFile = gson.toJson(homeList);
        try(FileWriter fileWriter=new FileWriter(file); BufferedWriter bufferedWriter=new BufferedWriter(fileWriter)) {
            bufferedWriter.write(jsonFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static List<Home> getHomes(){
        File file = new File(BaseData.FILE_URL + "/baseFile/home.txt");
        str = new StringBuilder();
        try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null)
                str.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonFile = new String(str);
        return gson.fromJson(jsonFile,new TypeToken<List<Home>>(){}.getType());

    }
    public static void setLikes(List<Like> likeList)  {
        File file = new File(BaseData.FILE_URL+"/baseFile/like.txt");
        String jsonFile = gson.toJson(likeList);
        try(FileWriter fileWriter=new FileWriter(file); BufferedWriter bufferedWriter=new BufferedWriter(fileWriter)) {
            bufferedWriter.write(jsonFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static List<Like> getLikes() {
        File file = new File(BaseData.FILE_URL + "/baseFile/like.txt");
        str = new StringBuilder();
        try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null)
                str.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonFile = new String(str);
        return gson.fromJson(jsonFile,new TypeToken<List<Like>>(){}.getType());
    }
    public static void setSearch(List<Search> searchList) {
        File file = new File(BaseData.FILE_URL+"/baseFile/search.txt");
        String jsonFile = gson.toJson(searchList);
        try(FileWriter fileWriter=new FileWriter(file); BufferedWriter bufferedWriter=new BufferedWriter(fileWriter)) {
            bufferedWriter.write(jsonFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static List<Search> getSearch(){
        File file = new File(BaseData.FILE_URL + "/baseFile/search.txt");
        str = new StringBuilder();
        try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null)
                str.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonFile = new String(str);
        return gson.fromJson(jsonFile,new TypeToken<List<Search>>(){}.getType());
    }

    public static void setInterest(List<Interest> interestList)  {
        File file = new File(BaseData.FILE_URL+"/baseFile/interest.txt");
        String jsonFile = gson.toJson(interestList);
        try(FileWriter fileWriter=new FileWriter(file); BufferedWriter bufferedWriter=new BufferedWriter(fileWriter)) {
            bufferedWriter.write(jsonFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static List<Interest> getInterest() {
        File file = new File(BaseData.FILE_URL + "/baseFile/interest.txt");
        str = new StringBuilder();
        try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null)
                str.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonFile = new String(str);
        return gson.fromJson(jsonFile,new TypeToken<List<Interest>>(){}.getType());
    }


    public static void saveFile(File file, String originalName) {
        File file1=new File(BaseData.FILE_URL+"/img/"+originalName);
        try(    FileOutputStream fileOutputStream = new FileOutputStream(file1);
                FileInputStream fileInputStream = new FileInputStream(file) )
        {
                fileOutputStream.write(fileInputStream.readAllBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
