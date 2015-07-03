package com.myicpc.service.quest.report.template;

import com.myicpc.model.quest.QuestChallenge;
import com.myicpc.service.report.template.ReportFormatter;
import com.myicpc.service.report.template.ReportTemplate;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.SplitType;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.myicpc.service.report.template.ReportTemplate.labeledText;
import static com.myicpc.service.report.template.ReportTemplate.translateText;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.tableOfContentsHeading;

/**
 * @author Roman Smetana
 */
public class QuestChallengeGuide {

    private boolean generateTOC;

    public QuestChallengeGuide(boolean generateTOC) {
        this.generateTOC = generateTOC;
    }

    public JasperReportBuilder build(List<QuestChallenge> questChallenges) {
        JasperReportBuilder report = ReportTemplate.baseReport();

        SubreportBuilder subreport = cmp.subreport(new SubreportExpression(questChallenges))
                .setDataSource(new JREmptyDataSource());

        if (generateTOC) {
            report.tableOfContents();
        }
        report.detail(subreport);
        report.setDetailSplitType(SplitType.PREVENT);

        report.setDataSource(new JREmptyDataSource(questChallenges.size()));
        return report;
    }

    private class SubreportExpression extends AbstractSimpleExpression<JasperReportBuilder> {
        private final List<QuestChallenge> challenges;

        public SubreportExpression(List<QuestChallenge> challenges) {
            this.challenges = challenges;
        }

        @Override
        public JasperReportBuilder evaluate(ReportParameters reportParameters) {
            int index = reportParameters.getReportRowNumber() - 1;
            QuestChallenge challenge = challenges.get(index);

            TextFieldBuilder<String> title = cmp.text(challenge.getName() + " (#" + challenge.getHashtag() + ")")
                    .setStyle(ReportTemplate.h1Style)
                    .setTableOfContentsHeading(tableOfContentsHeading());

            JasperReportBuilder report = ReportTemplate.baseSubreport();
            report.title(title);
            report.detail(
                    createChallengeComponent(challenge),
                    cmp.verticalGap(10));
            return report;
        }
    }

    protected ComponentBuilder<?, ?> createChallengeComponent(QuestChallenge challenge) {

        VerticalListBuilder verticalList = cmp.verticalList();

        verticalList.add(labeledText("quest.challengeGuide.hashtag", "#" + challenge.getHashtag()));
        verticalList.add(labeledText("quest.challengeGuide.points.awarded", translateText("quest.challengeGuide.points.x", challenge.getDefaultPoints())));
        verticalList.add(labeledText("quest.challengeGuide.startDate", challenge.getLocalStartDate(), ReportFormatter.dateTimeFormatter));
        if (challenge.getEndDate() != null) {
            verticalList.add(labeledText("quest.challengeGuide.endDate", challenge.getLocalEndDate(), ReportFormatter.dateTimeFormatter));
        }
        if (challenge.isRequiresPhoto() || challenge.isRequiresVideo()) {
            RequiredAttachmentsExpression requiredAttachmentsExpression = new RequiredAttachmentsExpression(challenge.isRequiresPhoto(), challenge.isRequiresVideo());
            verticalList.add(labeledText("quest.challengeGuide.points.attachments", requiredAttachmentsExpression));
        }
        verticalList.add(cmp.text(challenge.getDescription()));
        if (challenge.getImageURL() != null) {
            try {
                URL imageURL = new URL(challenge.getImageURL());
                verticalList.add(cmp.image(imageURL).setHorizontalAlignment(HorizontalAlignment.CENTER));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return verticalList;
    }

    private static class RequiredAttachmentsExpression extends AbstractSimpleExpression<String> {
        private final boolean attachPhoto;
        private final boolean attachVideo;

        public RequiredAttachmentsExpression(boolean attachPhoto, boolean attachVideo) {
            this.attachPhoto = attachPhoto;
            this.attachVideo = attachVideo;
        }

        @Override
        public String evaluate(ReportParameters reportParameters) {
            StringBuilder sb = new StringBuilder();
            if (attachPhoto && attachVideo) {
                sb.append(translateText("quest.challengeGuide.points.attachPhotoOrVideo").evaluate(reportParameters));
            } else if (attachPhoto) {
                sb.append(translateText("quest.challengeGuide.points.attachPhoto").evaluate(reportParameters));
            } else if (attachVideo) {
                sb.append(translateText("quest.challengeGuide.points.attachVideo").evaluate(reportParameters));
            }
            return sb.toString();
        }
    }



    public JasperReportBuilder build2(List<QuestChallenge> questChallenges) {
        JasperReportBuilder report = ReportTemplate.baseReport();

        report.tableOfContents();

        ComponentBuilder<?, ?>[] details = new ComponentBuilder[questChallenges.size()];
        int index = 0;
        for (QuestChallenge challenge : questChallenges) {
            details[index++] = createChallengeComponent(challenge);
//            report.detail(createChallengeComponent(challenge)).setDetailSplitType(SplitType.PREVENT);
        }
        report.detail(details);
//        report.setDetailSplitType(SplitType.PREVENT);

        report.setDataSource(new JRBeanCollectionDataSource(questChallenges));
        report.setDataSource(new JREmptyDataSource());
        return report;
    }

}
