package com.summerproject.myproj.buzzcom;

import android.graphics.Bitmap;

public class Feed {
    public enum Status{
        Error,Question_Image,Question_Imageless,Post_Imageless,Post_Image,Blank;
    }
    Bitmap uploader_icon=null;
    String uploadername="Admin";
    String threadname="Admin Talks";
    Bitmap Image=null;
    String maintext=null;
    int no_of_likes=0,no_of_comments=0;
     Status status=Status.Blank;
    String question=null;

    public void setUploadername(String uploadername) {
        this.uploadername = uploadername;
    }
    public void setNo_of_likes(int no_of_likes) {
        this.no_of_likes = no_of_likes;
    }
    public void setNo_of_comments(int no_of_comments) {
        this.no_of_comments = no_of_comments;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setThreadname(String threadname) {
        this.threadname = threadname;
    }
    public String getUploadername() {
        return uploadername;
    }
    public String getThreadname() {
        return threadname;
    }
    public Status getStatus() {
        return status;
    }
    public Feed(Bitmap uploader_icon, Bitmap image, String maintext, String question) {
        this.uploader_icon = uploader_icon;
        Image = image;
        this.maintext = maintext;
        this.question = question;


    }
    public Bitmap getUploader_icon() {
        return uploader_icon;
    }
    public int getNo_of_likes() {
        return no_of_likes;
    }
    public int getNo_of_comments() {
        return no_of_comments;
    }
    public Bitmap getImage() {
        return Image;
    }
    public String getMaintext() {
        return maintext;
    }
    public String getQuestion() {
        return question;
    }
    public void setUploader_icon(Bitmap uploader_icon) {
        this.uploader_icon = uploader_icon;
    }
    public void setImage(Bitmap image) {
        Image = image;
    }
    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public Status updatestatus(){
        if(Image==null &&question==null &&maintext!=null){
            status=Status.Post_Imageless;
        }
        else if(Image==null &&question!=null &&maintext==null){
            status=Status.Question_Imageless;
        }
        else if(Image!=null &&question!=null &&maintext==null){
            status=Status.Question_Image;
        }
        else if(Image!=null &&question==null &&maintext!=null){
            status=Status.Post_Image;
        }
        else
            status=Status.Error;
        return status;

    }
}
