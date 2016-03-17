package com.ndy.spider.phone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.observer.ProcessorObserver;

/**
 * @author nidayu
 * @Description:
 * @date 2016/3/16
 */
public class AbstractCrawler {

    protected Spider spider;

    public void getUrl(String url){
        getUrl(url, null, null, null);
    }

    public void getUrl(String url, String referer, String[][] headers, ProcessorObserver observer){
        getUrl(url, referer, null, headers, observer);
    }

    public void getUrl(String url, String referer, String[] param, String[][] headers, ProcessorObserver observer){
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
        if (observer != null){
            request.addObserver(observer);
        }
        spider.addRequest(request);
    }

    public void postUrl(String url, String referer, String[][] nameValuePairs, String[][] headers, ProcessorObserver observer){
        postUrl(url, referer, null, nameValuePairs, headers, observer);
    }

    public void postUrl(String url, String referer, String[] param, String[][] nameValuePairs, String[][] headers, ProcessorObserver observer){
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
        if (observer != null){
            request.addObserver(observer);
        }
        spider.addRequest(request);
    }

}
