package com.aram_201572.Api;
import com.aram_201572.Models.Student;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("students.json")
    Call<ResponseBody> getStudents();

    @GET("students.json")
    Call<ResponseBody> getStudentById(@Query("orderBy") String orderBy, @Query("equalTo") String id);

    @PUT("students.json")
    Call<ResponseBody> addStudents(@Body List<Student> students);

    @PATCH("students/{id}.json")
    Call<ResponseBody> updateStudents(@Path("id") String id,@Body Student students);

    @DELETE("students/{id}.json")
    Call<ResponseBody> deleteStudent(@Path("id") String id );


}
