package com.and.wodness.api;
/**
 * Created by GangXian on 8/24/2017.
 */


import com.and.wodness.model.LoginRequestInfo;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface WodnessAPIService {
    @POST("api/user_login")
    Observable<LoginResponse> login(@Body LoginRequestInfo loginInfo);
//    @POST("api/register")
//    Observable<RegisterResponse> register(@Body RegisterRequestInfo registerInfo);
//    @GET("/api/user_login/{userid}")
//    Observable<SettingResponse> getSettingInfo(@Path("userid") String uid);

//    @GET("/enrich_mobile/WzWpdatatable4/getSettingsNew/{userid}")
//    Observable<SettingResponse> getSettingInfo(@Path("userid") String uid);
//
//    @POST("/enrich_mobile/WzWpdatatable4/updateSettingNew")
//    Observable<SettingResponse> updateSettingInfo(@Body SettingInfo settingInfo);
//
//    @GET("/enrich_mobile/DapUsers/getAvailableCardsTest/{userid}")
//    Observable<GetMyContactsResponse> getMyContacts(@Path("userid") String uid);
//
//    @Multipart
//    @POST("user/updateprofile")
//    Observable<ResponseBody> uploadAudioFile(@Part MultipartBody.Part audioFile);

//    @POST("/hooks/catch/772315/t2hhp8/")
//    Call<ResponseBody> sendSmsMsgTestNow(@Body SmsMsgTestNowRequestBody body);
//
//
//
//    @GET("/pages/upload_from_bucket.php?")
//    Call<ResponseBody> sendTranscription(@Query("Message") String message);
//
//    @GET("/private/setting/pages/activate_mvm_campaign.php?")
//    Call<ResponseBody> activateMVMCampaign(@Query("user_id") String userId, @Query("audio_filename") String audioFileName);
//
//    @GET("/enrich_mobile/HtJobs/deleteContact/{deleteIndexString}")
//    Observable<DeleteContactResponse> deleteContacts(@Path("deleteIndexString") String indexes);
//
//    @GET("/{account_id}/{fileName}")
//    Call<ResponseBody> downloadAudioFileUrlAsync(@Path("account_id") String strAccountId, @Path("fileName") String fileName);
//
//    @GET("/enrich-scanner/{fileName}")
//    Call<ResponseBody> downloadScannerImageFileUrlAsync(@Path("fileName") String fileName);

}