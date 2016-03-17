package com.ndy.spider.phone;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.utils.RegexPaserUtil;

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
        spider.getSite().setDomain("passport.17500.cn");
//        spider.getSite().setHttpProxy(new HttpHost("182.92.129.13", 7077));
        this.phoneNo = phoneNo;
        this.password = password;
        this.authCode = authCode;
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
        YD yd = new YD(spider, "18888888888", "", "");
        yd.test();
        spider.start();
    }
}
