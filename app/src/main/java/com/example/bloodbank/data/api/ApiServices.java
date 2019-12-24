package com.example.bloodbank.data.api;



import com.example.bloodbank.model.listOfCities.GeneralResponse;
import com.example.bloodbank.model.login.Login;
import com.example.bloodbank.model.newPassword.NewPassword;
import com.example.bloodbank.model.register.Register;
import com.example.bloodbank.model.resetPassword.ResetPassword;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @FormUrlEncoded
    @POST("signup")
    Call<Register> userRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("birth_date") String birth_date,
            @Field("city_id") String city_id,
            @Field("phone") String phone,
            @Field("donation_last_date") String donation_last_date,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("blood_type_id") String blood_type_id
                                    );
    @FormUrlEncoded
    @POST("login")
    Call<Login> userLogin(
            @Field("phone") String phone,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("reset-password")
    Call<ResetPassword> userResetPassword(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("new-password")
    Call<NewPassword> userNewPassword(
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("pin_code") String pin_code,
            @Field("phone") String phone
    );

    @GET("blood-types")
    Call<GeneralResponse> getBloodType(
    );

    @GET("governorates")
    Call<GeneralResponse> getGovenorate(
    );

    @GET("cities?governorate_id=1")
    Call<GeneralResponse> getCity(
            @Query("governorate_id") String governorate_id
    );

//    @FormUrlEncoded
//    @POST("qaeah/user_registration.php")
//    Call<LoginUserService> userRegistration(@FieldMap Map<String, String> fields);
//
//    @GET("get_qaeah/home.php")
//    Call<Home> getHomePage();
}
