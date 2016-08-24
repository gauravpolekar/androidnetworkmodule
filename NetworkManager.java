

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by gaurav_polekar on 4/25/2016.
 */
public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static NetworkManager mInstance = null;
    //for Volley API
    public RequestQueue requestQueue;

    private NetworkManager(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    private RequestQueue getRequestQueue(){
        return requestQueue;
    }
    public static synchronized NetworkManager getInstance(Context context)
    {
        if (null == mInstance)
            mInstance = new NetworkManager(context);
        return mInstance;
    }


  /*
  Network request for JSON Object
  */
    public void makeNetworkRequestForJSON(final int requestCode, int method, String url, JSONObject param, final Map<String, String> headers, final NetworkResponseListener listener){
        JsonObjectRequest request = new JsonObjectRequest(
                method, url, param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onDataReceived(requestCode,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onDataFailed(requestCode,error);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(headers != null)
                    return headers;
                else
                    return super.getHeaders();
            }
        };
        request.setTag(requestCode);
        getRequestQueue().add(request);
    }

/*
Network request for JSON Array
*/
public void makeNetworkRequestForJSONArray(final int requestCode, int method, String url, JSONArray param, final Map<String, String> headers, final NetworkResponseListener listener){
        JsonArrayRequest request = new JsonArrayRequest(
                method, url, param,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "]");
                        listener.onDataReceived(requestCode, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse() called with: " + "error = [" + error + "]");
                listener.onDataFailed(requestCode,error);
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(headers != null)
                    return headers;
                else
                    return super.getHeaders();
            }
        };
        request.setTag(requestCode);
        getRequestQueue().add(request);
    }


}
