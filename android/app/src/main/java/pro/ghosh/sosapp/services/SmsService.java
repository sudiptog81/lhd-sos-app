package pro.ghosh.sosapp.services;

import pro.ghosh.sosapp.models.RequestObject;
import pro.ghosh.sosapp.models.ResponseObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SmsService {
    @POST("sms")
    Call<ResponseObject> sendSms(@Body RequestObject request);
}
