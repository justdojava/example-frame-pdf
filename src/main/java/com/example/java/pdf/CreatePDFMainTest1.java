package com.example.java.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import java.io.*;

/**
 * @author panzhi
 * @since 2021-09-12
 */
public class CreatePDFMainTest1 {

    public static void main(String[] args) throws Exception {
        Document document = new Document(PageSize.A4);
        //第二步，创建Writer实例
        PdfWriter.getInstance(document, new FileOutputStream("/Users/panzhi/Documents/pz/coding/learning/java-example/example-frame-pdf/pdf-file/hello.pdf"));
        //创建中文字体
        BaseFont bfchinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(bfchinese, 12, Font.NORMAL);
        //第三步，打开文档
        document.open();
        //第四步，写入内容
        Paragraph paragraph = new Paragraph("hello world", fontChinese);
        document.add(paragraph);
        //第五步，关闭文档
        document.close();
    }
}
