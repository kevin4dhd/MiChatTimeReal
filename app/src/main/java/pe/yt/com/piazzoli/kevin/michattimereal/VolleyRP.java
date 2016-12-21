package pe.yt.com.piazzoli.kevin.michattimereal;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRP {
    
    private static VolleyRP mVolleyRP = null;
    private RequestQueue mRequestQueue;

    private VolleyRP(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyRP getInstance(Context context) {
        if (mVolleyRP == null) {
            mVolleyRP = new VolleyRP(context);
        }
        return mVolleyRP;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static void addToQueue(Request request, RequestQueue fRequestQueue, Context context, VolleyRP volley) {
        if (request != null) {
            request.setTag(context);
            if (fRequestQueue == null)
                fRequestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            fRequestQueue.add(request);
        }
    }
}
