package com.project.xetnghiem.api.services;

import com.project.xetnghiem.models.SampleDto;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface SampleService {
    @GET("api/sample/get-all")
    Single<Response<List<SampleDto>>> getAllSample();
}
