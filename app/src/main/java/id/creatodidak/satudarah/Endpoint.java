package id.creatodidak.satudarah;

import java.lang.reflect.Array;
import java.util.List;

import id.creatodidak.satudarah.models.ListfaskesItem;
import id.creatodidak.satudarah.models.MDataDonor;
import id.creatodidak.satudarah.models.MPointRank;
import id.creatodidak.satudarah.models.MRegistrasi;
import id.creatodidak.satudarah.models.MRequestDarah;
import id.creatodidak.satudarah.models.MRequestSaya;
import id.creatodidak.satudarah.models.MResponse;
import id.creatodidak.satudarah.models.MWilayah;
import id.creatodidak.satudarah.models.Mevents;
import id.creatodidak.satudarah.models.Mfaskes;
import id.creatodidak.satudarah.models.Mutd;
import id.creatodidak.satudarah.models.MyRanks;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Endpoint {

    @FormUrlEncoded
    @POST("wilayah")
    Call<MWilayah> getWilayah(
            @Field("jenis") String jenis,
            @Field("prov") String prov,
            @Field("kab") String kab,
            @Field("kec") String kec,
            @Field("des") String des
    );

    @FormUrlEncoded
    @POST("register")
    Call<MRegistrasi> register(
            @Field("nama") String nama,
            @Field("nik") String nik,
            @Field("tanggallahir") String tanggallahir,
            @Field("prov") String prov,
            @Field("kab") String kab,
            @Field("kec") String kec,
            @Field("des") String des,
            @Field("dus") String dus,
            @Field("golongandarah") String golongandarah,
            @Field("email") String email,
            @Field("foto") String foto,
            @Field("password") String password,
            @Field("token") String svToken,
            @Field("jeniskelamin") String jeniskelamin);

    @FormUrlEncoded
    @POST("updgoldar")
    Call<MResponse> updGoldar(
            @Field("memberid") String memberid,
            @Field("goldar") String goldars
    );

    @FormUrlEncoded
    @POST("cekverifikasi")
    Call<MResponse> cekVerifikasi(
            @Field("token") String memberid
    );

    @Multipart
    @POST("updatefoto")
    Call<MResponse> uploadFotoProfile(
            @Part("memberid") String memberid,
            @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("login")
    Call<MRegistrasi> login(
            @Field("nik") String nik,
            @Field("password") String password,
            @Field("token") String svToken);

    @GET("pointrank")
    Call<MPointRank> cekGlobalRank();

    @FormUrlEncoded
    @POST("reqdonorlist")
    Call<MRequestDarah> getRequestDarah(
            @Field("memberid") String memberid
    );

    @FormUrlEncoded
    @POST("riwayatdonor")
    Call<MDataDonor> getDataDonor(
            @Field("memberid") String memberid
    );

    @FormUrlEncoded
    @POST("responserequest")
    Call<MResponse> resprequest(
            @Field("memberid") String memberid,
            @Field("requestid") String requestid
    );

    @GET("eventdonorlist")
    Call<Mevents> dataEvent();

    @Multipart
    @POST("newfoto")
    Call<MResponse> updateFotoProfile(
            @Part("memberid") String memberid,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("listutd")
    Call<Mutd> getUtd(@Field("idfaskes") String selected);

    @FormUrlEncoded
    @POST("listfaskes")
    Call<Mfaskes> getFaskes(@Field("kab") String selected);

    @FormUrlEncoded
    @POST("newrequestdonor")
    Call<MResponse> newRequest(
            @Field("memberid") String memberid,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("golongandarah") String goldar,
            @Field("jumlah") String jumlah,
            @Field("diagnosa") String diagnosa,
            @Field("kab") String rkab,
            @Field("faskes") String rfaskes,
            @Field("utd") String rutd,
            @Field("namacp") String namacp,
            @Field("kontakcp") String kontakcp
    );

    @FormUrlEncoded
    @POST("reqdonorlistsaya")
    Call<MRequestSaya> getReqDarahSaya(
            @Field("memberid") String memberid
    );

    @FormUrlEncoded
    @POST("konfirmasidonor")
    Call<MResponse> sudahDonor(
            @Field("requestid") String reqid,
            @Field("memberid") String memberid
    );

    @FormUrlEncoded
    @POST("canceldonor")
    Call<MResponse> cancelDonor(
            @Field("requestid") String reqid,
            @Field("memberid") String memberid
    );

    @GET("mypoint/{memberid}")
    Call<MyRanks> mypoint(@Path("memberid") String memberid);

    @FormUrlEncoded
    @POST("confirmevent")
    Call<MResponse> confirmEvent(
            @Field("memberid") String memberid,
            @Field("eventid") String eventid
    );

    @GET("listallfaskes")
    Call<List<ListfaskesItem>> allfaskes();
}
