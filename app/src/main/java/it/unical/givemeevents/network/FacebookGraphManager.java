package it.unical.givemeevents.network;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.unical.givemeevents.BuildConfig;
import it.unical.givemeevents.R;
import it.unical.givemeevents.model.FacebookEvent;
import it.unical.givemeevents.model.FacebookPlace;
import it.unical.givemeevents.model.GraphSearchData;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by ManoWar on 5/12/2017.
 */

public class FacebookGraphManager {

    private Context ctx;
    private FacebookGraphServices graphService;

    public FacebookGraphManager(Context ctx) {
        this.ctx = ctx;

        Builder b = new Builder();
        b.readTimeout(30, TimeUnit.SECONDS);
        b.writeTimeout(30, TimeUnit.SECONDS);
        b.retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            b.addInterceptor(interceptor);
        }
        OkHttpClient okHttpClient = b.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        graphService = retrofit.create(FacebookGraphServices.class);
    }

    private String getBaseUrl() {
        return ctx.getString(R.string.fb_graph_url);
    }

    private String getAuthToken() {
        return ctx.getString(R.string.facebook_app_id);
    }


    public List<String> findPlacesId(GraphSearchData searchData) {
        List<String> results = new LinkedList<>();
        try {
            results = findPlacesIdPaging(searchData);
            return results;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }


    /*API GRAPH FACEBOOK SDK*/
    private List<String> findPlacesIdPaging(GraphSearchData searchData) throws JSONException {
        List<String> ids = new LinkedList<String>();
        /*////////////////URL PARAMETERS BUILDING///////////////////////*/
        Bundle params = new Bundle();
        params.putString("center", searchData.getCenter());
        params.putString("distance", searchData.getDistance() + "");
        params.putString("limit", "200");
        params.putString("q", (searchData.getQuery() != null) ? searchData.getQuery() : "");
        params.putString("fields", ctx.getString(R.string.fb_graph_field_id));
        params.putString("type", ctx.getString(R.string.fb_graph_type_place));
        if (searchData.getCategories() != null && searchData.getCategories().length >= 0) {
            JSONArray arr = new JSONArray(Arrays.asList(searchData.getCategories()));
            params.putString("categories", arr.toString());
        }
        /*///////////////////////REQUEST EXECUTION///////////////////////*/
        GraphRequest request = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), ctx.getString(R.string.fb_graph_url_search), null);
        request.setParameters(params);
        /*///////////////////////ADDING THE IDS TO THE LIST//////////////*/
        do {
            GraphResponse resp = request.executeAndWait();
            if (resp.getError() == null) {/*IF NO ERRORS*/
                JSONObject json = resp.getJSONObject();
                if (json.has("data")) {
                    JSONArray array = json.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        ids.add(((JSONObject) array.get(i)).getString("id"));
                    }
                }
            } else {
                Log.e("API ERROR", resp.getError().getErrorMessage());
            }
            request = resp.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
        } while (request != null);
        /*///////////////////////////////////////////////////////////////*/
        return ids;
    }

    public List<FacebookEvent> findEvents(List<String> ids, GraphSearchData searchData) throws JSONException {

        List<FacebookEvent> events = new LinkedList<FacebookEvent>();
        if (ids != null && ids.size() > 0) {
            int idsSize = ids.size();
            String fields = ctx.getString(R.string.fb_graph_field_placefields).replace("{field_evt}", ctx.getString(R.string.fb_graph_field_eventfields));
            fields += ("." + ctx.getString(R.string.fb_graph_field_since) + "(" + (searchData.getSince().getTime() / 1000) + ")");
            if (searchData.getUntil() != null) {
                fields += ("." + ctx.getString(R.string.fb_graph_field_until) + "(" + (searchData.getUntil().getTime() / 1000) + ")");
            }
            Bundle params = new Bundle();
            if (idsSize <= 50) {
                String idsTemp = Arrays.toString(ids.toArray());
                params.putString(ctx.getString(R.string.fb_graph_field_ids), idsTemp.substring(1, idsTemp.length() - 1));
                params.putString(ctx.getString(R.string.fb_graph_field_fields), fields);
                events.addAll(findEventsAux(params));
            } else {
                int count = 0;
                params.putString(ctx.getString(R.string.fb_graph_field_fields), fields);
                for (int i = 0; i < (idsSize - 50); i += 50) {
                    String idsTemp = ids.subList(i, i + 50).toString();
                    params.putString(ctx.getString(R.string.fb_graph_field_ids), idsTemp.substring(1, idsTemp.length() - 1));
                    events.addAll(findEventsAux(params));
                }
                if (idsSize % 50 != 0) {
                    String idsTemp = ids.subList(50 * (idsSize / 50), idsSize).toString();
                    params.putString(ctx.getString(R.string.fb_graph_field_ids), idsTemp.substring(1, idsTemp.length() - 1));
                    events.addAll(findEventsAux(params));
                }
            }
        }
        return events;
    }

    private void addJsonWithEvents(JSONObject js1, JSONObject js2) throws JSONException {
        Iterator<?> keys = js2.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (js2.getJSONObject(key).has(ctx.getString(R.string.fb_graph_field_events))) {
                js1.put(key, js2.get(key));
            }
        }
    }

    private List<FacebookEvent> findEventsAux(Bundle params) throws JSONException {
        /*///////////////////////REQUEST EXECUTION///////////////////////*/
        GraphRequest request = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/", null);
        request.setParameters(params);
        List<FacebookEvent> events = new LinkedList<FacebookEvent>();
        do {
            GraphResponse resp = request.executeAndWait();
            if (resp.getError() == null) {/*IF NO ERRORS*/
                JSONObject jsonResp = resp.getJSONObject();
                Iterator<?> keys = jsonResp.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Gson gson = new Gson();
                    JSONObject aux = jsonResp.getJSONObject(key);
                    if (aux.has(ctx.getString(R.string.fb_graph_field_events))) {
                        FacebookPlace eventPlace = gson.fromJson(aux.toString(), FacebookPlace.class);
                        JSONArray eventsArr = aux.getJSONObject(ctx.getString(R.string.fb_graph_field_events)).getJSONArray("data");
                        for (int i = 0; i < eventsArr.length(); i++) {
                            FacebookEvent event = gson.fromJson(eventsArr.get(i).toString(), FacebookEvent.class);
                            event.setPlaceOwner(eventPlace);
                            events.add(event);
                        }
                    }
                }
            }
            request = resp.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
        } while (request != null);
        return events;
    }

    public List<FacebookEvent> findEventsOfPlace(String placeId, GraphSearchData sd) throws JSONException {
        if (placeId == null || placeId.isEmpty()) {
            throw new IllegalArgumentException("PlaceId cannot be null or empty");
        }
        List<String> id = new ArrayList<>();
        id.add(placeId);
        return findEvents(id, sd);
    }

    public static boolean isLogged() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    public void findUserBasicInfo(GraphRequest.GraphJSONObjectCallback callback) {
        if (isLogged()) {
            AccessToken access = AccessToken.getCurrentAccessToken();
            if (access != null) {
                GraphRequest request = GraphRequest.newMeRequest(access, callback);
                Bundle params = new Bundle();
                params.putString("fields", ctx.getString(R.string.fb_graph_field_name) + "," + ctx.getString(R.string.fb_graph_field_pictureLarge));
                request.setParameters(params);
                request.executeAsync();
            }
        }
    }

    public void getUserAccountPicture(GraphRequest.Callback callback) {

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                ctx.getString(R.string.fb_graph_url_picture),
                callback);
        Bundle params = new Bundle();
        params.putBoolean("redirect", false);
        request.setParameters(params);
        request.executeAsync();
    }
}
