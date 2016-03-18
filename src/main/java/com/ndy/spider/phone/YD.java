package com.ndy.spider.phone;

import com.ndy.util.RegexPaserUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

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
        String info = saveFileTest("https://login.10086.cn/captchazh.htm?type=05&timestamp=" + timeMillis(),
                "https://login.10086.cn/login.html?channelID=12003&backUrl=http://shop.10086.cn/i/",
                "login.10086.cn", "aa");
        System.out.println(info);
//        getUrl("https://login.10086.cn/needVerifyCode.htm?accountType=01&account="+phoneNo+"&timestamp="+timeMillis(),
//                "https://login.10086.cn/login.html?channelID=12003&backUrl=http://shop.10086.cn/i/", null, null, new CommonObserver(){
//            @Override
//            public void afterRequest(Page page) throws Exception {
//                String html = page.getRawText();
//                System.out.println(html);
//                if (html != null && html.contains("{\"needVerifyCode\":\"1\"}")){
//                    String info = saveFileTest("https://login.10086.cn/captchazh.htm?type=05&timestamp=" + timeMillis(),
//                            "https://login.10086.cn/login.html?channelID=12003&backUrl=http://shop.10086.cn/i/",
//                            "login.10086.cn", "aa");
//                    System.out.println(info);
//                }
//            }
//        });
    }

    private void test(){
        postUrl("https://passport.17500.cn/check/input2.html", "https://passport.17500.cn/reg/index/redirect/http_referer.html", null,
                new String[][]{{"input2", phoneNo}, {"unique", "1"}}, new String[][]{{"X-Requested-With", "XMLHttpRequest"},
                        {"Accept", "application/json, text/javascript, */*; q=0.01"}}, new CommonObserver(){
                    @Override
                    public void afterRequest(Page page) throws Exception {
                        System.out.println("执行完毕！");
                        System.out.println(RegexPaserUtil.unicodeDecode(page.getRawText()));
                    }
                });
    }

    public static void main(String[] args){
        Spider spider = new Spider();
        YD yd = new YD(spider, "18317042860", "163163", null);
        yd.getImg("c://a.png");
        spider.start();
//        System.out.println("请输入验证码:");
//        Scanner in = new Scanner(System.in);
//        String authCode = in.nextLine();

    }
}
