package com.project.xetnghiem.api.services;

import com.project.xetnghiem.api.requestObj.AppointmentRequest;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BookApptService {
    @POST("api/appointment/create")
    Single<Response<Boolean>> bookAppointment(@Body AppointmentRequest  request);
}
