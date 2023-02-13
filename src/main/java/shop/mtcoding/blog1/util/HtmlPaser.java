package shop.mtcoding.blog1.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlPaser {
    public static String getThumnail(String html) {
        Document doc = Jsoup.parse(html);
        Elements els = doc.select("img");
        String thumbnail = "";
        // System.out.println(els);
        if (els.size() == 0) {
            thumbnail = "/images/dora.png";
            // 임시사진
            // 디비 섬네일 -> /image/profile.png
        } else {
            Element el = els.get(0);
            thumbnail = el.attr("src");
        }
        return thumbnail;
    }

    public static String getThumnail2(String html) {
        Document doc = Jsoup.parse(html);
        Elements els = doc.select("img");
        String thumbnail = "";
        // System.out.println(els);
        if (els.size() == 0) {
            // 임시사진
            // 디비 섬네일 -> /image/profile.png
        } else {
            Element el = els.get(1);
            thumbnail = el.attr("src");
        }
        return thumbnail;
    }
}
