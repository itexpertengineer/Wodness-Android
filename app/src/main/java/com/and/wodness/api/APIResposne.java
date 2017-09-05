package com.and.wodness.api;
/**
 * Created by GangXian on 8/24/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class APIResposne<T> implements Serializable {
    //@SerializedName("response_code")
    //@Expose
    public int statusCode;

    //@SerializedName("data")
    //@Expose
    public T data;




    public APIResposne()
    {

    }

    public int getStatusCode(){return this.statusCode;}
    public void setStatusCode(int code){this.statusCode = code;}

    public T getData(){return data;}
    public void setData(T data){this.data = data;}


}
