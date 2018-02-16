package baghi.naeem.com.assignment6.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import baghi.naeem.com.assignment6.entities.Hotel;
import baghi.naeem.com.assignment6.entities.Reservation;
import baghi.naeem.com.assignment6.entities.User;

public class NetworkHandler {

    private static final String TAG = "NETWORK";
    private static final String SAVE_USER_TAG = "SAVE_USER";
    private static final String SAVE_RESERVATION_TAG = "SAVE_RESERVATION";

    private static NetworkHandler instance;
    private Context context;
    private RequestQueue requestQueue;

    private String serverURL;
    private String encodedCredentials;

    private NetworkHandler(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public static NetworkHandler getInstance(Context context) {
        if(instance == null)
            instance = new NetworkHandler(context);
        return instance;
    }

    public String getServerURL() {
        return this.serverURL;
    }

    public void setServerURL(String url) {
        this.serverURL = url;

        if(!this.serverURL.startsWith("http://"))
            this.serverURL = "http://" + this.serverURL;

        if(!this.serverURL.endsWith("/"))
            this.serverURL = this.serverURL + "/";
    }

    public void setCommunicationProperties(String username, String password) {
        this.encodedCredentials = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    /*public void getHotels(final INetworkCallback<List<Hotel>> listener) {
        if(listener == null) {
            Log.e(TAG, "Null listener");
            return;
        }
        if(!isNetworkConnected()) {
            listener.callback(null, "Network is not connected!");
            return;
        }

        Log.d(TAG, "Getting list of hotels...");

        final JsonArrayRequest request = new JsonArrayRequest(this.serverURL + "hotel",
            new Response.Listener<JSONArray>() {

                public void onResponse(JSONArray response) {
                    try {
                        List<Hotel> hotels = new ArrayList<>();

                        for(int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            Hotel hotel = new Hotel();
                            hotel.fromJson(object);
                            hotels.add(hotel);
                        }

                        Log.d(TAG, "List of hotels loaded successfully");

                        listener.callback(hotels, null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "parsing hotels failed");
                    }
                }
            },
            new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Pair<String, String> errorMessage = getErrorMessage(error, GET_HOTELS_TAG);
                    Log.e(TAG, errorMessage.first);
                    listener.callback(null, errorMessage.second);
                }
            }
        ) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + encodedCredentials);
                return headers;
            }
        };

        request.setTag(GET_HOTELS_TAG);
        requestQueue.add(request);
    }*/

    /*public void saveHotel(Hotel hotel, final INetworkCallback<Hotel> listener) {
        if(listener == null) {
            Log.e(TAG, "Null listener");
            return;
        }
        if(!isNetworkConnected()) {
            listener.callback(null, "Network is not connected!");
            return;
        }

        JSONObject object;
        try {
            object = hotel.toJson();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            listener.callback(null, "Failed to parse hotel to JSON");
            return;
        }

        Log.d(TAG, "Saving hotel...");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, serverURL + "hotel", object,
            new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        Hotel hotel = new Hotel();
                        hotel.fromJson(response);

                        Log.d(TAG, "New hotel saved successfully");

                        listener.callback(hotel, null);
                    } catch(JSONException e) {
                        Log.d(TAG, e.getMessage());
                        listener.callback(null, "Saving hotel failed");
                    }
                }
            },
            new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Pair<String, String> errorMessage = getErrorMessage(error, SAVE_HOTEL_TAG);
                    Log.e(TAG, errorMessage.first);
                    listener.callback(null, errorMessage.second);
                }
            }
        ) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + encodedCredentials);
                return headers;
            }
        };

        request.setTag(SAVE_HOTEL_TAG);
        requestQueue.add(request);
    }*/

    public void saveReservation(final Reservation reservation, final INetworkCallback<Reservation> listener) {
        if(listener == null) {
            Log.e(TAG, "Null listener");
            return;
        }

        if(!isNetworkConnected()) {
            listener.callback(null, "Network is not connected!");
            return;
        }

        JSONObject object;
        try {
            object = reservation.toJson();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            listener.callback(null, "Failed to parse reservation to JSON");
            return;
        }

        Log.d(TAG, "Saving reservation...");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, serverURL + "Reservation", object,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            Reservation savedReservation = new Reservation();
                            savedReservation.fromJson(response);
                            reservation.setId(savedReservation.getId());

                            Log.d(TAG, "New reservation saved successfully");

                            listener.callback(reservation, null);
                        } catch(JSONException e) {
                            Log.d(TAG, e.getMessage());
                            listener.callback(null, "Saving reservation failed");
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Pair<String, String> errorMessage = getErrorMessage(error, SAVE_RESERVATION_TAG);
                        Log.e(TAG, errorMessage.first);
                        listener.callback(null, errorMessage.second);
                    }
                }
        ) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + encodedCredentials);
                return headers;
            }
        };

        request.setTag(SAVE_RESERVATION_TAG);
        requestQueue.add(request);
    }

    public void saveUser(final User user, final INetworkCallback<User> listener) {
        if(listener == null) {
            Log.e(TAG, "Null listener");
            return;
        }
        if(!isNetworkConnected()) {
            listener.callback(null, "Network is not connected!");
            return;
        }

        JSONObject object;
        try {
            object = user.toJson();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            listener.callback(null, "Failed to parse user to JSON");
            return;
        }

        Log.d(TAG, "Saving user...");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, serverURL + "signup", object,
            new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        User savedUser = new User();
                        savedUser.fromJson(response);
                        user.setId(savedUser.getId());

                        Log.d(TAG, "New user saved successfully");

                        listener.callback(user, null);
                    } catch(JSONException e) {
                        Log.d(TAG, e.getMessage());
                        listener.callback(null, "Saving user failed");
                    }
                }
            },
            new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Pair<String, String> errorMessage = getErrorMessage(error, SAVE_USER_TAG);
                    Log.e(TAG, errorMessage.first);
                    listener.callback(null, errorMessage.second);
                }
            }
        ) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }
        };

        request.setTag(SAVE_USER_TAG);
        requestQueue.add(request);
    }

    public void cancelRequests() {
        Log.d(TAG, "Cancelling all requests...");
        requestQueue.cancelAll(SAVE_USER_TAG);
        requestQueue.cancelAll(SAVE_RESERVATION_TAG);
    }

    private Pair<String, String> getErrorMessage(VolleyError error, String requestTag) {
        String logMessage = "";
        String userMessage;

        String errorData = null;
        String errorMessage = error.getMessage();
        NetworkResponse response = error.networkResponse;

        if(response != null) {
            if(response.data != null)
                errorData = new String(response.data);
        }

        if(!TextUtils.isEmpty(errorMessage))
            logMessage += (!TextUtils.isEmpty(logMessage)? " - " : "") + errorMessage;

        if(!TextUtils.isEmpty(errorData))
            logMessage += (!TextUtils.isEmpty(logMessage)? " - " : "") + errorData;

        String requestInfo = "The request ";

        if(requestTag.equals(SAVE_USER_TAG))
            requestInfo += requestTag;

        if(requestInfo.equals(SAVE_RESERVATION_TAG))
            requestInfo += requestTag;

        if (error instanceof NetworkError) {
            userMessage = "Failed to communicate with server for " + requestInfo + "!";
        } else if (error instanceof AuthFailureError) {
            userMessage = "Cannot authenticate with server for " + requestInfo + "!";
        } else if (error instanceof ClientError) {
            userMessage = "Incomplete or inappropriate request for " + requestInfo + "!";
        } else if (error instanceof ServerError) {
            userMessage = "Some server error occurred during " + requestInfo + "!";
        } else if (error instanceof ParseError) {
            userMessage = "Failed to parse the results after " + requestInfo + "!";
        } else {
            userMessage = "Failed in " + requestInfo + "!";
        }

        if (TextUtils.isEmpty(logMessage)) {
            logMessage = userMessage;
        }

        return new Pair<>(logMessage, userMessage);
    }
}
