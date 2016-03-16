package us.codecraft.webmagic.processor.example;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.RegexPaserUtil;

/**
 * @author nidayu
 * @Description: 测试手机号是否注册过乐彩网
 * @date 2016/3/16
 */
public class HtmlPageProcessor implements PageProcessor {

    private Site site = Site.me().setDomain("passport.17500.cn");

    @Override
    public void process(Page page) {
        System.out.println("start spider...");
        System.out.println(RegexPaserUtil.unicodeDecode(page.getRawText()));
    }

    @Override
    public Site getSite() {
        site.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        site.addHeader("X-Requested-With", "XMLHttpRequest");
        site.addHeader("Referer", "https://passport.17500.cn/reg/index/redirect/http_referer.html");
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new HtmlPageProcessor());
        Request request = new Request();
        request.setUrl("https://passport.17500.cn/check/input2.html");
        request.setMethod("POST");
        request.putExtra("nameValuePair", new NameValuePair[]{new BasicNameValuePair("input2", "18888888888"), new BasicNameValuePair("unique", "1")});
        spider.addRequest(request);
        spider.run();
    }

}
