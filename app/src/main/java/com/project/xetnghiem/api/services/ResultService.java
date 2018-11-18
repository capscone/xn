package com.project.xetnghiem.api.services;

import com.project.xetnghiem.models.Result;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ResultService {
    @GET("api/appointment/create")
    Single<Response<Result>> getPatientResult(@Query("patientId") int patientId);
}
