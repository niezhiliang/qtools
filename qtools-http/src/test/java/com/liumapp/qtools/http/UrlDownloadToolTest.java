package com.liumapp.qtools.http;

import org.junit.Assert;
import org.junit.Test;

import javax.naming.NoPermissionException;
import java.io.IOException;

/**
 * file UrlDownloadToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/4
 */
public class UrlDownloadToolTest {

    private String savePath = "/usr/local/tomcat/project/qtools/data/mock/";

    @Test
    public void testIODownloadForJDK6 () throws IOException {
        try {
            UrlDownloadTool.IODownloadForJDK6("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543900473203&di=5f61d97cc97d9eb509605ef25c6cace8&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20150908%2Fmp31043208_1441704680666_3.png",
                    savePath + "/test.png");
        } catch (NoPermissionException e) {
            System.out.println("no permission to download");
        }
    }

    @Test
    public void testIODownload () throws IOException {
        try {
            UrlDownloadTool.IODownload("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543900473203&di=5f61d97cc97d9eb509605ef25c6cace8&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20150908%2Fmp31043208_1441704680666_3.png",
                    savePath + "/test2.png");
        } catch (NoPermissionException e) {
            System.out.println("no permission to download");
        }
    }

    @Test
    public void testNIODownload () throws IOException {
        try {
            UrlDownloadTool.NIODownload("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543900473203&di=5f61d97cc97d9eb509605ef25c6cace8&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20150908%2Fmp31043208_1441704680666_3.png",
                    savePath + "/test3.png");
        } catch (NoPermissionException e) {
            System.out.println("no permission to download");
        }
    }

}
