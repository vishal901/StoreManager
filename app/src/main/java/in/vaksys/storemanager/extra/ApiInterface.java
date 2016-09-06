package in.vaksys.storemanager.extra;

import java.util.Map;

import in.vaksys.storemanager.model.findus;
import in.vaksys.storemanager.response.Ailments;
import in.vaksys.storemanager.response.FindAs;
import in.vaksys.storemanager.response.GetBranchList;
import in.vaksys.storemanager.response.RegisterResponse;
import in.vaksys.storemanager.response.UploadImage;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by lenovoi3 on 8/8/2016.
 */
public interface ApiInterface {


    @FormUrlEncoded
    @POST(AppConfig.URL_REGISTER)
    Call<RegisterResponse> REGISTER_RESPONSE_CALL(@Field("branch_id") String branch_id,
                                                  @Field("first_name") String first_name,
                                                  @Field("gender") String Gender,
                                                  @Field("address") String Address,
                                                  @Field("mobile") String mobile,
                                                  @Field("email") String email,
                                                  @Field("ailments") String ailments,
                                                  @Field("last_name") String last_name,
                                                  @Field("date_of_birth") String date_of_birth,
                                                  @Field("address1") String address1,
                                                  @Field("city") String city,
                                                  @Field("state") String state,
                                                  @Field("zip") String zip,
                                                  @Field("how_did_you_find_us") String how_did_you_find_us,
                                                  @Field("pain_picture") String pain_picture,
                                                  @Field("find_us_reason") String find_us_reason,
                                                  @Field("landline") String landline);

    @GET(AppConfig.URL_FINDUS)
    Call<FindAs> findus();

    @GET(AppConfig.URL_GET_AILMENTS)
    Call<Ailments> ailments();

    @Multipart
    @POST(AppConfig.URL_UPLOAD)
    Call<UploadImage> getupload(@Part MultipartBody.Part file, @QueryMap Map<String, String> filters);

    @POST("upload")
    Call<UploadImage> addEvent(@Body RequestBody body);

    @GET(AppConfig.URL_GET_BRANCH)
    Call<GetBranchList> GET_BRANCH_CALL();
}
