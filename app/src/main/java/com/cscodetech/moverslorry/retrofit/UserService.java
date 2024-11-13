package com.cscodetech.moverslorry.retrofit;


import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserService {

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "reg_user.php")
    Call<JsonObject> createUser(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "login_user.php")
    Call<JsonObject> userLogin(@Body RequestBody requestBody);


    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "mobile_check.php")
    Call<JsonObject> mobileCheck(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "country_code.php")
    Call<JsonObject> getCodelist(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "home_page.php")
    Call<JsonObject> getHomePage(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "vehicle_list.php")
    Call<JsonObject> getVehocle(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "statelist.php")
    Call<JsonObject> getStatelist(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "getstateid.php")
    Call<JsonObject> getstateid(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "bid_load.php")
    Call<JsonObject> bidload(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "lorry_list.php")
    Call<JsonObject> getlorryList(@Body RequestBody requestBody);


    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "delete_bid.php")
    Call<JsonObject> deleteBid(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "find_load.php")
    Call<JsonObject> findLoad(@Body RequestBody requestBody);


    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "load_history.php")
    Call<JsonObject> loadHistory(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "load_details.php")
    Call<JsonObject> loadDetails(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "delete_load.php")
    Call<JsonObject> deleteLoad(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "payout_list.php")
    Call<JsonObject> payoutList(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "getearning.php")
    Call<JsonObject> getearning(@Body RequestBody requestBody);


    @Headers("X-API-KEY:cscodetech")
    @GET("/validator/api/validationserverAPI.php")
    Call<JsonObject> tragection(@Query("val_id") String valid, @Query("store_id") String storeid, @Query("store_passwd") String storepasswd);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "pagelist.php")
    Call<JsonObject> pagelist(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "profile_edit.php")
    Call<JsonObject> profileEdit(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "referdata.php")
    Call<JsonObject> referdata(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "add_lorry.php")
    @Multipart
    Call<JsonObject> uploadMultiFile(@Part("owner_id") RequestBody ownerid, @Part("lorry_no") RequestBody lorryno, @Part("weight") RequestBody weight,@Part("description") RequestBody description,@Part("vehicle_id") RequestBody vehicleid,@Part("status") RequestBody status,@Part("curr_location") RequestBody currlocation,@Part("curr_state_id") RequestBody currstateid,@Part("routes") RequestBody routes, @Part("size") RequestBody size, @Part List<MultipartBody.Part> parts);


    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "edit_lorry.php")
    @Multipart
    Call<JsonObject> uploadEditMultiFile(@Part("record_id") RequestBody recordid,@Part("owner_id") RequestBody ownerid, @Part("lorry_no") RequestBody lorryno, @Part("weight") RequestBody weight,@Part("description") RequestBody description,@Part("vehicle_id") RequestBody vehicleid,@Part("status") RequestBody status,@Part("curr_location") RequestBody currlocation,@Part("curr_state_id") RequestBody currstateid,@Part("routes") RequestBody routes, @Part("size") RequestBody size, @Part List<MultipartBody.Part> parts);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "book_history.php")
    Call<JsonObject> bookHistory(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "book_details.php")
    Call<JsonObject> bookDetails(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "offer_decision.php")
    Call<JsonObject> offerDecision(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "lorrydecision.php")
    Call<JsonObject> lorrydecision(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "rate_update.php")
    Call<JsonObject> rateUpdate(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @Multipart
    @POST(APIClient.APPEND_URL + "personal_document.php")
    Call<JsonObject> dyAnswer(@Part("owner_id") RequestBody riderid, @Part("status")RequestBody status, @Part("size")RequestBody size, @Part List<MultipartBody.Part> parts, @Part("sizes")RequestBody sizes, @Part List<MultipartBody.Part> partss);

    @Headers("X-API-KEY:cscodetech")
    @Multipart
    @POST(APIClient.APPEND_URL + "pro_image.php")
    Call<JsonObject> profileImageUpdate(@Part("owner_id") RequestBody userid, @Part List<MultipartBody.Part> parts , @Part("size")RequestBody size);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "notification.php")
    Call<JsonObject> getNote(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "trans_profile.php")
    Call<JsonObject> lorriProfile(@Body RequestBody requestBody);


    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "lorri_profile.php")
    Call<JsonObject> transProfile(@Body RequestBody requestBody);



    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "near_load.php")
    Call<JsonObject> nearLoad(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "request_withdraw.php")
    Call<JsonObject> requestWithdraw(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "forget_password.php")
    Call<JsonObject> getForgot(@Body RequestBody requestBody);

    @Headers("X-API-KEY:cscodetech")
    @POST(APIClient.APPEND_URL + "faq.php")
    Call<JsonObject> getFaq(@Body RequestBody requestBody);

}
