package mainpackage.playstertest.support;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mainpackage.playstertest.R;
import mainpackage.playstertest.activity.MainActivity;

/**
 * Created by deeppandya on 2017-04-09.
 */

public class Utils {

    public static final String ITEM = "item";
    public static final String TITLE = "title";
    public static final String PUBDATE = "pubDate";
    public static final String AUTHOR = "author";
    public static final String DESCRIPTION = "description";

    public static final String CBCLINK = "http://www.cbc.ca/cmlink/rss-topstories";
    public static final String LINK = "link";
    private static AlertDialog alertDialog;

    public static String getImageUrlFromNode(String imgTag) {
        Matcher matcher = Pattern.compile("<img src=\'([^\']+)").matcher(imgTag);
        if (matcher.find()) {
            System.out.println("img url: " + matcher.group(1));
            return matcher.group(1);
        }
        return "";
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }

    public static void showNoInternetDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.no_internet));
        builder.setMessage(context.getResources().getString(R.string.no_internet_msg));
        builder.setNegativeButton(context.getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();

    }

    public static void closeNoInternetDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

}
