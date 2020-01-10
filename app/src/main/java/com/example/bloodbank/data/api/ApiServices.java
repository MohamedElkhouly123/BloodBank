package com.example.bloodbank.data.api;



import com.example.bloodbank.data.model.contactUs.ContactUs;
import com.example.bloodbank.data.model.donations.creatDonationRequest.CreatDonationRequest;
import com.example.bloodbank.data.model.donations.DonationRequests.GetAllDonationRequests;
import com.example.bloodbank.data.model.generalResponse.GeneralResponse;
import com.example.bloodbank.data.model.posts.getAllPosts.GetAllPosts;
import com.example.bloodbank.data.model.notifications.getNotificationList.GetNotificationList;
import com.example.bloodbank.data.model.notificationSetting.NotificationSetting;
import com.example.bloodbank.data.model.client.Client;
import com.example.bloodbank.data.model.notifications.notificationCount.NotificationCount;
import com.example.bloodbank.data.model.posts.postToggleFavourite.PostToggleFavourite;
import com.example.bloodbank.data.model.resetPassword.ResetPassword;
import com.example.bloodbank.data.model.setting.Setting;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @FormUrlEncoded
    @POST("signup")
    Call<Client> userRegister(
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
    Call<Client> userLogin(
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
    Call<ResetPassword> userNewPassword(
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("pin_code") String pin_code,
            @Field("phone") String phone
    );



    @FormUrlEncoded
    @POST("profile")
    Call<Client> getProfileData(
            @Field("api_token") String api_token

    );
    @FormUrlEncoded
    @POST("profile")
    Call<Client> editProfileData(
            @Field("name") String name,
            @Field("email") String email,
            @Field("birth_date") String birth_date,
            @Field("city_id") String city_id,
            @Field("phone") String phone,
            @Field("donation_last_date") String donation_last_date,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("blood_type_id") String blood_type_id,
            @Field("api_token") String api_token

    );
    @FormUrlEncoded
    @POST("signup-token")
    Call<GetNotificationList> registerNotificationToken(
            @Field("token") String token,
            @Field("api_token") String api_token,
            @Field("type") String type

    );

    @FormUrlEncoded
    @POST("remove-token")
    Call<GetNotificationList> removeNotificationToken(
            @Field("token") String token,
            @Field("api_token") String api_token

    );

    @FormUrlEncoded
    @POST("notifications-settings")
    Call<NotificationSetting> changeNotificationSetting(
            @Field("api_token") String api_token,
            @Field("governorates[]") List<Integer> governorates,
            @Field("blood_types[]") List<Integer> blood_types


    );

    @FormUrlEncoded
    @POST("notifications-settings")
    Call<NotificationSetting> getYourNotificationSetting(
            @Field("api_token") String api_token

    );

    @FormUrlEncoded
    @POST("donation-request/create")
    Call<CreatDonationRequest> creatDonationRequest(
            @Field("api_token") String api_token,
            @Field("patient_name") String patient_name,
            @Field("patient_age") String patient_age,
            @Field("blood_type_id") String blood_type_id,
            @Field("bags_num") String bags_num,
            @Field("hospital_name") String hospital_name,
            @Field("hospital_address") String hospital_address,
            @Field("city_id") String city_id,
            @Field("phone") String phone,
            @Field("notes") String notes,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude

    );

    @GET("blood-types")
    Call<GeneralResponse> getBloodType(
    );

    @GET("governorates")
    Call<GeneralResponse> getGovenorate(
    );

    @GET("cities")
    Call<GeneralResponse> getCity(
            @Query("governorate_id") String governorate_id
    );

    @GET("categories")
    Call<GeneralResponse> getCategories();

    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> contactUs(@Field("title") String title,
                              @Field("message") String message,
                              @Field("api_token") String api_token);

    @GET("settings")
    Call<Setting> getSettings(@Query("api_token") String api_token);


    @GET("notifications")
    Call<GetNotificationList> getNotificationList(
            @Query("api_token") String api_token,
            @Query("page") int page

    );

    @GET("notifications-count")
    Call<NotificationCount> NotificationCount(
            @Query("api_token") String api_token

    );

    @GET("donation-requests")
    Call<GetAllDonationRequests> getAllDonationRequests(
            @Query("api_token") String api_token,
            @Query("page") String page

    );

    @GET("donation-requests")
    Call<GetAllDonationRequests> getAllDonationRequestsFilter(
            @Query("api_token") String api_token,
            @Query("blood_type_id") String blood_type_id,
            @Query("governorate_id") String governorate_id,
            @Query("page") String page

    );

    @GET("donation-request")
    Call<GetAllDonationRequests> displayDonationDetails(
            @Query("api_token") String api_token,
            @Query("donation_id") String donation_id

    );

    @GET("posts")
    Call<GetAllPosts> getAllPosts(
            @Query("api_token") String api_token,
            @Query("page") int page

    );

    @GET("posts")
    Call<GetAllPosts> postsFilter(
            @Query("api_token") String api_token,
            @Query("page") String page,
            @Query("keyword") int keyword,
            @Query("category_id") int category_id

    );

    @GET("posts")
    Call<GetAllPosts> displayPostsDetails(
            @Query("api_token") String api_token,
            @Query("page") String page,
            @Query("keyword") String keyword,
            @Query("category_id") String category_id

    );
    @GET("my-favourites")
    Call<GetAllPosts> getAllFavouritePosts(
            @Query("api_token") String api_token,
            @Query("page") int page

    );

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<PostToggleFavourite> getPostToggleFavourite(@Field("post_id") int post_id,
                                                     @Field("api_token") String api_token);
//    @FormUrlEncoded
//    @POST("qaeah/user_registration.php")
//    Call<LoginUserService> userRegistration(@FieldMap Map<String, String> fields);
//
//    @GET("get_qaeah/home.php")
//    Call<Home> getHomePage();
}
