package com.tmikulsk1.tvinfo;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmikulsk1 on 10/05/2018.
 */

public final class TvInfoJson {

    private TvInfoJson() {
    }

    public static List<TvInfo> extractFeatureFromJson(String tvInfoJSON) {

        if (TextUtils.isEmpty(tvInfoJSON)) {
            return null;
        }

        List<TvInfo> tvInfoList = new ArrayList<>();

        try {

            switch (Main.TYPE_JSON) {

                case 0:

                    JSONArray indexBaseJSON = new JSONArray(tvInfoJSON);
                    JSONObject indexMainJSON;


                    for (int i = 0; i < indexBaseJSON.length(); i++) {

                        indexMainJSON = new JSONObject(indexBaseJSON.getString(i));

                        String showName = indexMainJSON.getString("name");
                        String showGenre = "";
                        String showImage;
                        String showSummary = indexMainJSON.getString("summary");
                        String showPremiered = indexMainJSON.getString("premiered");


                        JSONArray jsonShowGenre = indexMainJSON.getJSONArray("genres");

                        for (int l = 0; l < jsonShowGenre.length(); l++) {

                            showGenre += jsonShowGenre.getString(l).toString();
                            if (l != jsonShowGenre.length() - 1) {
                                showGenre += " / ";
                            }

                        }

                        JSONObject jsonShowImage = indexMainJSON.getJSONObject("image");
                        showImage = jsonShowImage.getString("original");

                        TvInfo tvInfos = new TvInfo(showName, showGenre, showImage, showSummary, showPremiered);
                        tvInfoList.add(tvInfos);
                    }

                    break;

                case 1:

                    JSONArray searchBaseJson = new JSONArray(tvInfoJSON);

                    for (int h = 0; h < searchBaseJson.length(); h++) {

                        JSONObject searchMainJson = new JSONObject(searchBaseJson.getString(h));

                        JSONObject showJSON = searchMainJson.getJSONObject("show");

                        String showName = showJSON.getString("name");
                        String showGenre = "";
                        String showImage;
                        String showSummary = showJSON.getString("summary");
                        String showPremiered = showJSON.getString("premiered");


                        JSONArray jsonShowGenre = showJSON.getJSONArray("genres");

                        for (int l = 0; l < jsonShowGenre.length(); l++) {

                            showGenre += jsonShowGenre.getString(l).toString();
                            if (l != jsonShowGenre.length() - 1) {
                                showGenre += " / ";
                            }

                        }

                        JSONObject jsonShowImage = showJSON.getJSONObject("image");
                        showImage = jsonShowImage.getString("original");

                        TvInfo tvInfos = new TvInfo(showName, showGenre, showImage, showSummary, showPremiered);
                        tvInfoList.add(tvInfos);

                    }

                    break;

            }

        } catch (JSONException e) {
            Log.e("JSON", "Problem retrieving JSON data: ", e);
        }

        return tvInfoList;

    }

    public static List<TvInfo> fetchEarthquakeData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("JSON", "Problem making the HTTP request.", e);
        }

        List<TvInfo> tvInfoList = extractFeatureFromJson(jsonResponse);

        return tvInfoList;
    }

    private static URL createUrl(String stringUrl) {

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("JSON", "Problem building the URL", e);
        }

        return url;

    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e("JSON", "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e("JSON", "Problem retrieving the Tv Show JSON results.", e);
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }

        }

        return output.toString();
    }

}
