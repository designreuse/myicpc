package com.myicpc.service.report.template;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.expression.MessageExpression;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.Markup;
import net.sf.dynamicreports.report.constant.VerticalAlignment;

import java.awt.*;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

/**
 * @author Roman Smetana
 */
public class ReportTemplate {
    public static final StyleBuilder rootStyle;
    public static final StyleBuilder headlineStyle;
    public static final StyleBuilder h1Style;
    public static final StyleBuilder boldStyle;
    public static final StyleBuilder columnStyle;
    public static final StyleBuilder columnTitleStyle;

    static {
        rootStyle = stl.style().setPadding(2);
        headlineStyle = stl.style().setFontSize(24).setBottomPadding(20);
        h1Style = stl.style().setFontSize(18);

        boldStyle = stl.style().bold();

        columnStyle = stl.style(rootStyle).setVerticalAlignment(VerticalAlignment.MIDDLE);
        columnTitleStyle = stl.style(columnStyle)
                .setBorder(stl.pen1Point())
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.TOP)
                .setBackgroundColor(Color.LIGHT_GRAY)
                .bold();
    }

    public static JasperReportBuilder baseReport() {
        JasperReportBuilder baseReport = baseSubreport()
                .pageFooter(createFooter());
        return baseReport;
    }

    public static JasperReportBuilder baseSubreport() {
        ResourceBundle reportResourceBundle = ResourceBundle.getBundle("i18n/report", Locale.US);
        JasperReportBuilder baseReport = report()
                .setResourceBundle(reportResourceBundle)
                .setColumnStyle(columnStyle)
                .setColumnTitleStyle(columnTitleStyle)
                .highlightDetailEvenRows();
        return baseReport;
    }

    public static ComponentBuilder<?, ?> createFooter() {
        HorizontalListBuilder list = cmp.horizontalList();
        list.add(cmp.text(new ReportExpressions.DateExpression(new Date())));
        list.add(cmp.pageXofY().setHorizontalAlignment(HorizontalAlignment.CENTER));
        list.add(cmp.text(""));
        list.setStyle(stl.style().setTopBorder(stl.pen1Point()));
        return list;
    }

    public static MessageExpression translateText(String resourceKey) {
        return new MessageExpression(resourceKey);
    }

    public static MessageExpression translateText(String resourceKey, Object... arguments) {
        return new MessageExpression(resourceKey, arguments);
    }

    public static <T> TextFieldBuilder<String> labeledText(String key, T value) {
        TextFieldBuilder<String> builder = cmp.text(new ReportExpressions.LabelExpression<>(translateText(key), value));
        builder.setStyle(stl.style().setMarkup(Markup.HTML));
        return builder;
    }

    public static <T> TextFieldBuilder<String> labeledText(String key, T value, AbstractValueFormatter formatter) {
        TextFieldBuilder<String> builder = cmp.text(new ReportExpressions.LabelExpression<>(translateText(key), value, formatter));
        builder.setStyle(stl.style().setMarkup(Markup.HTML));
        return builder;
    }

    public static <T> TextFieldBuilder<String> labeledText(String key, AbstractSimpleExpression<T> expression) {
        TextFieldBuilder<String> builder = cmp.text(new ReportExpressions.LabelExpression<>(translateText(key), expression));
        builder.setStyle(stl.style().setMarkup(Markup.HTML));
        return builder;
    }
}
