package com.liumapp.qtools.file.base64;

import com.liumapp.qtools.file.config.TestConfig;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author liumapp
 * @file Base64FileToolTest.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2018/8/8
 */
public class Base64FileToolTest extends TestCase {

    public void testFilePathToBase64 () throws IOException {
        System.out.println(Base64FileTool.filePathToBase64(TestConfig.savePath + "test.pdf"));
    }

    public void testBase64StringToFile () throws IOException {
        String base64File = Base64FileTool.filePathToBase64(TestConfig.savePath + "test.pdf");
        Base64FileTool.saveBase64File(base64File, TestConfig.savePath + "/tt/dd/out.pdf");
    }

}
