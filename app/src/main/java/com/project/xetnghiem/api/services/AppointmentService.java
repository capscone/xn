package com.project.xetnghiem.api.services;

import com.project.xetnghiem.models.Appointment;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppointmentService{
    @GET("api/appointment/get-new-appointment-by-patient-id")
    Single<Response<List<Appointment>>> getNewApptByPatientId(@Query("patientId") int patientId);

}
