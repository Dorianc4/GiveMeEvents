package it.unical.givemeevents.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Manuel on 5/12/2017.
 */

public interface FacebookGraphServices {

//    String TYPE = "type";
//    String COUNTRY = "country";

//    "&q=" + queryOptions.query +
//            "&center=" + queryOptions.latitude + "," + queryOptions.longitude +
//            "&distance=" + queryOptions.distance +
//            "&limit=100" +
//            "&fields=id" +
//            "&access_token=" + queryOptions.accessToken;


    @GET("/v2.11/search")
    Call<String> search(@QueryMap Map<String, String> params);

    @GET("/")
    Call findEvents(@QueryMap Map<String, String> params);

}
