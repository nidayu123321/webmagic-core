package com.ndy.spider.phone;

import com.ndy.util.StringUtil;
import org.json.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
/**
 * @author nidayu
 * @Description:
 * @date 2016/3/17
 */
public class LT extends AbstractCrawler{

    private String phoneNo;
    private String password;
    private String authCode;

    public LT(Spider spider, String phoneNo, String password, String authCode){
        this.spider = spider;
        spider.getSite().setDomain("uac.10010.com");
//        spider.getSite().setHttpProxy(new HttpHost("182.92.129.13", 7077));
        this.phoneNo = phoneNo;
        this.password = password;
        this.authCode = authCode;
    }

    private void login(){
        String url = "https://uac.10010.com/portal/Service/MallLogin?callback=jQuery17203066010744048846_1451617005181&req_time="+System.currentTimeMillis()+"&redirectURL=http%3A%2F%2Fwww.10010.com&userName="+phoneNo+"&password="+password+"&pwdType=01&productType=01&redirectType=01&rememberMe=1&_="+System.currentTimeMillis();
        getUrl(url, "https://uac.10010.com/portal/homeLogin", null, new CommonObserver(){
            @Override
            public void afterRequest(Page page) throws Exception {
                String text = page.getRawText();
                text = StringUtil.subStrIgnoreFirst("jQuery17203066010744048846_1451617005181(", ");", text);
                JSONObject json2 = new JSONObject(text);
                String resultCode2 = json2.optString("resultCode");
                if ("0000".equals(resultCode2) || "0301".equals(resultCode2)) {
                    String url = "http://iservice.10010.com/e3/static/check/checklogin/";
                    String[][] pairs = new String[][] {{ "_", System.currentTimeMillis() + "" }};
                    postUrl(url, null, pairs, null, new CommonObserver(){
                        @Override
                        public void afterRequest(Page page) throws Exception {
                            String text = page.getRawText();
                            System.out.println(text);
                        }
                    });
                }
            }
        });
    }

    public static void main(String[] args){
        Spider spider = new Spider();
        LT lt = new LT(spider, "18570913849", "198888", "");
        lt.login();
        spider.start();
    }

}
