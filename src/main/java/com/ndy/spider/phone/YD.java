package com.ndy.spider.phone;

import com.ndy.util.DateUtil;
import org.json.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

import java.util.Date;
import java.util.Scanner;

/**
 * @author nidayu
 * @Description: 移动
 * @date 2016/3/16
 */
public class YD extends AbstractCrawler{

    private String phoneNo;
    private String password;
    private String authCode;

    public YD(Spider spider, String phoneNo, String password, String authCode){
        this.spider = spider;
        spider.getSite().setDomain("10086.cn");
        spider.getSite().setCharset("utf-8");
//        spider.getSite().setHttpProxy(new HttpHost("182.92.129.13", 7077));
        this.phoneNo = phoneNo;
        this.password = password;
        this.authCode = authCode;
    }

    private void getImg(final String filePath){
        getUrl("https://login.10086.cn/needVerifyCode.htm?accountType=01&account="+phoneNo+"&timestamp="+timeMillis(),
                "https://login.10086.cn/login.html?channelID=12003&backUrl=http://shop.10086.cn/i/", null, null, new CommonObserver(){
            @Override
            public void afterRequest(Page page) throws Exception {
                String html = page.getRawText();
                System.out.println(html);
                if (html != null && html.contains("{\"needVerifyCode\":\"1\"}")){
                    String info = saveFileTest("https://login.10086.cn/captchazh.htm?type=05&timestamp=" + timeMillis(),
                            "https://login.10086.cn/login.html?channelID=12003&backUrl=http://shop.10086.cn/i/",
                            "login.10086.cn", "aa");
                    System.out.println(info);
                }
            }
        });
    }

    private void login(String authCode){
        String url = "https://login.10086.cn/login" +
                ".htm?accountType=01&account="+phoneNo+"&password="+password
                +"&pwdType=01&inputCode="+(authCode == null ? "" : authCode)+"&backUrl=http%3A%2F" +
                "%2Fshop.10086.cn%2Fi%2F&rememberMe=0&channelID=12003&protocol=https%3A&timestamp="+timeMillis();
        getUrl(url, "https://login.10086.cn/login.html?channelID=12003&backUrl=http://shop.10086.cn/i/", null, null, new CommonObserver(){
                    @Override
                    public void afterRequest(Page page) throws Exception {
                        String postResult = page.getRawText();
                        System.out.println(postResult);
                        if (postResult != null) {
                            JSONObject obj = new JSONObject(postResult);
                            if (obj.optString("code").contains("0000")) {
                                String artifact = obj.optString("artifact");
                                String url = obj.optString("assertAcceptURL");
                                url = url + "?backUrl=http://shop.10086.cn/i/&artifact=" + artifact;
                                getUrl(url, null, null, new CommonObserver() {
                                    @Override
                                    public void afterRequest(Page page) throws Exception {
                                        String url = "http://shop.10086.cn/i/?welcome=" + timeMillis();
                                        getUrl(url, null, null, new CommonObserver() {
                                            @Override
                                            public void afterRequest(Page page) throws Exception {
                                                final String param = DateUtil.formatDate(new Date(), "yyyyMMddHHmmsssss");
                                                String url = "http://shop.10086.cn/i/v1/fee/real/" + phoneNo + "?time=" + param;
                                                getUrl(url, null, null, new CommonObserver() {
                                                    @Override
                                                    public void afterRequest(Page page) throws Exception {
                                                        String text = page.getRawText();
                                                        JSONObject obj = new JSONObject(text);
                                                        if (obj.optString("retCode").contains("000000")) {
                                                            //成功
                                                            System.out.println("登录成功");
                                                            System.out.println(text);

                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    }
                });
    }

    private void requestMonthBill(){

    }

    public static void main(String[] args){
        Spider spider = new Spider();
        YD yd = new YD(spider, "18317042860", "163163", null);
        yd.getImg("c://a.png");
        spider.start();
        System.out.println("请输入验证码:");
        Scanner in = new Scanner(System.in);
        String authCode = in.nextLine();
        yd.login(authCode);
        spider.start();

    }
}
