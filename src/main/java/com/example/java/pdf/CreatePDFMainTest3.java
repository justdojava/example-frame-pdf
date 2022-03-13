package com.example.java.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author panzhi
 * @since 2021-09-12
 */
public class CreatePDFMainTest3 {


    /**
     * 将html文件转成PDF
     * @param htmlStr
     * @throws Exception
     */
    public static void writeToOutputStreamAsPDF(String htmlStr) throws Exception {
        //第一步，定义一个文档尺寸
        Document document = new Document();
        //A4页面横向
        Rectangle pageSize = new Rectangle(25, 25, PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
        document.setPageSize(pageSize);
        //第二步，创建Writer实例
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("/Users/panzhi/Documents/pz/coding/learning/java-example/example-frame-pdf/pdf-file/htmlDemo.pdf"));
        //第三步，打开文档
        document.open();
        //第四步，将html文件转成PDF
        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        worker.parseXHtml(pdfWriter, document, new ByteArrayInputStream(htmlStr.getBytes()), Charset.forName("UTF-8"), new XMLWorkerFontProvider(){
            @Override
            public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {
                try {
                    //用于中文显示的Provider
                    BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                    return new Font(bfChinese, size, style);
                } catch (Exception e) {
                    return super.getFont(fontname, encoding, size, style);
                }
            }
        });
        //第五步，关闭文档
        document.close();
    }

    /**
     * 读取 HTML 文件
     * @return
     */
    private static String readHtmlFile() {
        StringBuffer textHtml = new StringBuffer();
        try {
            File file = new File("/Users/panzhi/Documents/pz/coding/learning/java-example/example-frame-pdf/src/main/resources/template/printDemo2.html");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                textHtml.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }
        return textHtml.toString();
    }

    public static void main(String[] args) throws Exception {
        //读取html文件
        String htmlStr = readHtmlFile();
        //将html文件转成PDF
        writeToOutputStreamAsPDF(htmlStr);
    }
}
