package com.semesterproject.tourplanner.bl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import com.semesterproject.tourplanner.Application;
import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ReportHelper {
    //private static final LoggerWrapper logger = LoggerFactory.getLogger(ReportHelper.class);
    private Tour tour;
    private String MAPS_PNG;
    private String TARGET_PDF;
    private String target_pdf_summary;


    public ReportHelper(Tour tour) throws FileNotFoundException {
        this.tour = tour;
       MAPS_PNG = ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "path") + tour.getName() + ".jpg";
        TARGET_PDF =  ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "report", "path") + tour.getName() + "_summary.pdf";
    }

    public ReportHelper() throws FileNotFoundException {
        target_pdf_summary = ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "report", "path") + "summary.pdf";
    }


    public void generatePdf() throws IOException {
        PdfWriter writer = new PdfWriter(TARGET_PDF);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph loremIpsumHeader = new Paragraph(tour.getName() + " - Tour Report")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.DARK_GRAY);
        document.add(loremIpsumHeader);
        document.add(new Paragraph(tour.getDescription()));

        Paragraph listHeader = new Paragraph("Tour Details")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD));
        list.add(new ListItem("start: "+ tour.getStart()))
                .add(new ListItem("destination: " + tour.getDestination()))
                .add(new ListItem("distance: "+ tour.getDistance()))
                .add(new ListItem("planned time: "+ tour.getTime()))
                .add(new ListItem("transport type: "+ tour.getTransport_type()));
        document.add(listHeader);
        document.add(list);

        Paragraph tableHeader = new Paragraph("Tour Logs")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.GREEN);
        document.add(tableHeader);
        Table table = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
        table.addHeaderCell(getHeaderCell("Date"));
        table.addHeaderCell(getHeaderCell("Total Time"));
        table.addHeaderCell(getHeaderCell("Distance"));
        table.addHeaderCell(getHeaderCell("Comment"));
        table.addHeaderCell(getHeaderCell("Difficulty"));
        table.addHeaderCell(getHeaderCell("Rating"));
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);

        for(TourLog tourLog : tour.getLog()) {
            table.addCell(String.valueOf(tourLog.getDate()));
            table.addCell(String.valueOf(tourLog.getTotalTime()));
            table.addCell(String.valueOf(tourLog.getDistance()));
            table.addCell(tourLog.getComment());
            table.addCell(tourLog.getDifficulty());
            table.addCell(String.valueOf(tourLog.getRating()));
        }
        document.add(table);

        document.add(new AreaBreak());

        Paragraph imageHeader = new Paragraph(tour.getName() + " - Map Image")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.GREEN);
        document.add(imageHeader);
        ImageData imageData = ImageDataFactory.create(MAPS_PNG);
        document.add(new Image(imageData));

        document.close();
        writer.close();
    }


    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
    }

    public void generateSummary(ArrayList<Tour> allTours) throws IOException {
        PdfWriter writer = new PdfWriter(target_pdf_summary);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph loremIpsumHeader = new Paragraph("Tours Summary")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.DARK_GRAY);
        document.add(loremIpsumHeader);



        Paragraph tableHeader = new Paragraph("Tour Statistics")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.GREEN);
        document.add(tableHeader);
        Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        table.addHeaderCell(getHeaderCell("Name"));
        table.addHeaderCell(getHeaderCell("Average Time"));
        table.addHeaderCell(getHeaderCell("Average Distance"));
        table.addHeaderCell(getHeaderCell("Average Rating"));
        table.addHeaderCell(getHeaderCell("Number of Logs"));
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);

        for (Tour tour:allTours) {
            if(!tour.getLog().isEmpty()) {
                int avgTime = 0;
                int avgDistance = 0;
                int avgRating = 0;
                int i = 0;
                for (TourLog tourLog : tour.getLog()) {
                    avgTime += tourLog.getTotalTime();
                    avgRating += tourLog.getRating();
                    avgDistance += tourLog.getDistance();
                    i++;
                }
                avgTime = avgTime / i;
                avgDistance = avgDistance / i;
                avgRating = avgRating / i;

                table.addCell(tour.getName());
                table.addCell(String.valueOf(avgTime));
                table.addCell(String.valueOf(avgDistance));
                table.addCell(String.valueOf(avgRating));
                table.addCell(String.valueOf(i));
            }else{
                table.addCell(tour.getName());
                table.addCell("planned: " + String.valueOf(tour.getTime()));
                table.addCell("planned: " + String.valueOf(tour.getDistance()));
                table.addCell("-");
                table.addCell("no logs yet");
            }
        }
        document.add(table);

        document.close();
        writer.close();
    }
}