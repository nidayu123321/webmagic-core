package com.ndy.spider.phone;

import com.ndy.util.DateUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.observer.ProcessorObserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

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


    public String saveFileTest(String url, String referer, String host, String picName) {
        final String suffix = DateUtil.formatDate(new Date(), "MMdd");

        File file2 = new File(suffix);
        if (!file2.exists() && !file2.isDirectory()) {
            file2.mkdir();
        }
        final String destfilename = suffix + "/"+ picName + ".jpg";
        Request req = new Request(url);
        req.setMethod("GET");
        req.putExtra("INPUTSTREAM", true);
        if (referer != null) {
            spider.getSite().addHeader("Referer", referer);
        }
        if (host != null) {
            spider.getSite().addHeader("Host", host);
        }
        spider.getSite().addHeader("Accept", "image/webp,image/*,*/*;q=0.8");
        spider.getSite().addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
        req.addObserver(new CommonObserver() {
            @Override
            public void afterRequest(Page page) {
                try {
                    InputStream is = page.getInputStream();
                    if (is != null) {
                        FileOutputStream output = new FileOutputStream(destfilename);
                        try {
                            IOUtils.copy(is, output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            IOUtils.closeQuietly(is);
                            IOUtils.closeQuietly(output);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
        spider.addRequest(req);
        return suffix + "/"+ picName;
    }

    public long timeMillis(){
        return System.currentTimeMillis();
    }

}
