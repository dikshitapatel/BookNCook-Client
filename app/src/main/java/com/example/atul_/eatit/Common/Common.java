package com.example.atul_.eatit.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.example.atul_.eatit.model.User;

/**
 * Created by Admin on 13-Jan-18.
 */

public class Common {
    public static User currentUser;

    public static final String USER_KEY="User";
    public static final String PWD_KEY="Password";

    public static boolean isConnectedToInternet(Context baseContext) {

        ConnectivityManager connectivityManager=(ConnectivityManager) baseContext.getSystemService(baseContext.CONNECTIVITY_SERVICE);

        if(connectivityManager !=null)
        {
            NetworkInfo[] info=connectivityManager.getAllNetworkInfo();

            if(info !=null)
            {
                for(int i=0;i<info.length;i++)
                {
                    if(info[i].getState() ==NetworkInfo.State.CONNECTED)
                        return  true;
                }
            }

        }
        return false;

    }

    public static String codeConversion(String status) {
        if("0".equals(status))
            return "Placed";
        else if("1".equals(status))
            return "On The Way";
        else
            return "Shipped";

    }


}
