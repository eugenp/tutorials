package com.baeldung.wordtemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.util.LocaleUtil;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.Includes;
import com.deepoove.poi.data.MergeCellRule;
import com.deepoove.poi.data.MergeCellRule.Grid;
import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.Numberings.NumberingBuilder;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.plugin.comment.CommentRenderData;
import com.deepoove.poi.plugin.comment.CommentRenderPolicy;
import com.deepoove.poi.plugin.comment.Comments;

public class WordDocumentFromTemplate {

    private Map<String, Object> templateData;

    public Map<String, Object> getTemplateData() {
        return templateData;
    }

    public void setTemplateData(Map<String, Object> templateData) {
        this.templateData = templateData;
    }

    public WordDocumentFromTemplate() {
        this.templateData = new HashMap<String, Object>();
    }

    public static void main(String[] args) throws Exception {
        WordDocumentFromTemplate docEditor = new WordDocumentFromTemplate();
        docEditor.processTemplate();
    }

    public void processTemplate() throws FileNotFoundException, IOException {
        ConfigureBuilder builder = Configure.builder();
        builder.bind("sample", new SampleRenderPolicy());
        builder.bind("comment", new CommentRenderPolicy());

        InputStream inStream = WordDocumentFromTemplate.class.getClassLoader()
            .getResourceAsStream("template.docx");

        this.titleTemplate();
        this.textTagTemplate();
        this.useDataModel();
        this.imageTagTemplate();
        this.numberingTagTemplate();
        this.tableTagTemplate();
        this.sectionTemplate();
        this.nestingTemplate();
        this.refrenceTags();
        this.usingPlugins();
        this.commentsplugin();

        XWPFTemplate template = XWPFTemplate.compile(inStream, builder.build())
            .render(this.templateData);
        template.writeAndClose(new FileOutputStream("output.docx"));
    }

    public void useDataModel() {
        this.templateData.put("person", new Person("Jimmy", 35));
    }

    public void commentsplugin() {
        CommentRenderData comment = Comments.of("SampleExample")
            .signature("John", "S", LocaleUtil.getLocaleCalendar())
            .comment("Authored by John")
            .create();
        this.templateData.put("comment", comment);
    }

    public void titleTemplate() throws FileNotFoundException, IOException {
        templateData.put("title", "Hi, poi-tl Word. this is title using common map. Title example. processDocument");
    }

    public void textTagTemplate() {

        this.templateData.put("name", "John");
        this.templateData.put("author", Texts.of("John")
            .color("000000")
            .bold()
            .create());
        this.templateData.put("link", Texts.of("website")
            .link("http://deepoove.com")
            .create());
        this.templateData.put("anchor", Texts.of("anchortxt")
            .anchor("appendix1")
            .create());
    }

    public void imageTagTemplate() throws FileNotFoundException {
        this.templateData.put("svg", "https://img.shields.io/badge/jdk-1.6%2B-orange.svg");
        this.templateData.put("urlImg", Pictures.ofUrl("http://deepoove.com/images/icecream.png")
            .size(100, 100)
            .create());
    }

    public void tableTagTemplate() {
        this.templateData.put("table0", Tables.of(new String[][] { new String[] { "00", "01" }, new String[] { "10", "11" } })
            .border(BorderStyle.DEFAULT)
            .create());

        RowRenderData row0 = Rows.of("xx", "yyy")
            .textColor("FFFFFF")
            .bgColor("4472C4")
            .center()
            .create();
        RowRenderData row1 = Rows.create("adding large text to check how it behaves if it goes beyond table column length limit.", "mmmm");
        this.templateData.put("table1", Tables.create(row0, row1));

        RowRenderData row01 = Rows.of("Col0", "col1", "col2")
            .center()
            .bgColor("4472C4")
            .create();
        RowRenderData row11 = Rows.create("adding data to merged column in a table so it does not overflow in next row", null, null);
        MergeCellRule rule = MergeCellRule.builder()
            .map(Grid.of(1, 0), Grid.of(1, 2))
            .build();
        this.templateData.put("table3", Tables.of(row01, row11)
            .mergeRule(rule)
            .create());
    }

    public void numberingTagTemplate() {

        List<String> list = new ArrayList<String>();
        list.add("Plug-in grammar");
        list.add("Supports word text, pictures, table...");
        list.add("Not just templates");

        NumberingBuilder builder = Numberings.of(NumberingFormat.DECIMAL);
        for (String s : list) {
            builder.addItem(s);
        }

        NumberingRenderData renderData = builder.create();

        this.templateData.put("list", renderData);
    }

    public void sectionTemplate() {
        Map<String, Object> students = new LinkedHashMap<String, Object>();
        students.put("name", "John");
        students.put("name", "Mary");
        students.put("name", "Disarray");
        this.templateData.put("students", students);
    }

    public void nestingTemplate() {
        List<Address> subData = new ArrayList<>();
        subData.add(new Address("Florida,USA"));
        subData.add(new Address("Texas,USA"));
        this.templateData.put("nested", Includes.ofStream(WordDocumentFromTemplate.class.getClassLoader()
            .getResourceAsStream("nested.docx"))
            .setRenderModel(subData)
            .create());
    }

    public void refrenceTags() {
        ChartMultiSeriesRenderData chart = Charts.ofMultiSeries("ChartTitle", new String[] { "Title", "English" })
            .addSeries("countries", new Double[] { 15.0, 6.0 })
            .addSeries("speakers", new Double[] { 223.0, 119.0 })
            .create();

        this.templateData.put("barChart", chart);
    }

    public void usingPlugins() {
        this.templateData.put("sample", "custom-plugin");
    }
}
