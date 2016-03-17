package com.ndy.spider.phone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

/**
 * @author nidayu
 * @Description:
 * @date 2016/3/16
 */
public class AbstractCrawler {

    protected Spider spider;

    public void getUrl(String url){
        getUrl(url, null, null);
    }

    public void getUrl(String url, String referer, String[][] headers){
        getUrl(url, referer, null, headers);
    }

    public void getUrl(String url, String referer, String[] param, String[][] headers){
        Site site = spider.getSite();
        Request request = new Request();
        request.setMethod("GET");
        request.setUrl(url);
        if (referer != null){
            site.addHeader("Referer", referer);
        }
        if (param != null && param.length > 0){
            site.setCharset(param[0]);
        }
        if (headers != null){
            for (int i = 0; i < headers.length; i++){
                site.addHeader(headers[i][0], headers[i][1]);
            }
        }
        spider.addRequest(request);
    }

    public void postUrl(String url, String referer, String[][] nameValuePairs, String[][] headers){
        postUrl(url, referer, null, nameValuePairs, headers);
    }

    public void postUrl(String url, String referer, String[] param, String[][] nameValuePairs, String[][] headers){
        Site site = spider.getSite();
        Request request = new Request();
        request.setMethod("POST");
        request.setUrl(url);
        if (referer != null){
            site.addHeader("Referer", referer);
        }
        if (param != null && param.length > 0){
            site.setCharset(param[0]);
        }
        if (nameValuePairs != null){
            NameValuePair[] nameValuePairsArr = new NameValuePair[nameValuePairs.length];
            for (int i = 0; i < nameValuePairs.length; i++){
                nameValuePairsArr[i] = new BasicNameValuePair(nameValuePairs[i][0], nameValuePairs[i][1]);
            }
            request.putExtra("nameValuePair", nameValuePairsArr);
        }
        if (headers != null){
            for (int i = 0; i < headers.length; i++){
                site.addHeader(headers[i][0], headers[i][1]);
            }
        }
        spider.addRequest(request);
    }

}
