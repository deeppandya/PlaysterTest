package mainpackage.playstertest.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by deeppandya on 2017-04-09.
 */

public class Utils {

    public static final String ITEM="item";
    public static final String TITLE="title";
    public static final String PUBDATE="pubDate";
    public static final String AUTHOR="author";
    public static final String DESCRIPTION="description";

    public static final String CBCLINK = "http://www.cbc.ca/cmlink/rss-topstories";
    public static final String LINK = "link";

    public static String getImageUrlFromNode(String imgTag){
        Matcher matcher = Pattern.compile("<img src=\'([^\']+)").matcher(imgTag);
        if (matcher.find()) {
            System.out.println("img url: " + matcher.group(1));
            return matcher.group(1);
        }
        return "";
    }

}
