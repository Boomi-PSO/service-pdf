package com.manywho.services.pdf.services;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.api.run.elements.type.Property;
import com.manywho.services.pdf.types.FormField;
import com.manywho.services.pdf.utilities.FieldMapperUtility;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.*;
import java.net.URL;
import java.util.*;


public class PdfGeneratorService {

    public InputStream generatePdfFromHtml(String html) throws IOException {
        ITextRenderer iTextRenderer = new ITextRenderer();
        iTextRenderer.setDocumentFromString(cleanHtml(html));
        iTextRenderer.layout();

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            iTextRenderer.createPDF(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        }
    }

    private String cleanHtml(String dirtyHtml) {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        InputStream dirtyStream = new ByteArrayInputStream(dirtyHtml.getBytes());
        ByteArrayOutputStream xhtmlStream = new ByteArrayOutputStream();
        tidy.parse(dirtyStream, xhtmlStream);

        return xhtmlStream.toString();
    }

    public ByteArrayInputStream populatePdfFromFields(InputStream originalPdf, List<FormField> fields) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(originalPdf);
        ByteArrayOutputStream populatePDF = new ByteArrayOutputStream();

        PdfStamper stamper = new PdfStamper(reader, populatePDF);
        AcroFields form = stamper.getAcroFields();

        for (FormField field: fields) {
            FieldMapperUtility.populateForm(form, field);
        }

        stamper.close();
        reader.close();

        return new ByteArrayInputStream(populatePDF.toByteArray());
    }

    public HashMap<String, ContentType> getTypePropertiesFromPdfForm(String pdfFormUrl) throws IOException {
        InputStream inputStream = new URL(pdfFormUrl).openStream();
        PdfReader reader = new PdfReader(inputStream);
        AcroFields fields = reader.getAcroFields();
        Set<String> formFieldNames = fields.getFields().keySet();
        HashMap inputFields = new HashMap<>();

        for (String fieldName : formFieldNames) {
            ContentType contentType = FieldMapperUtility.getContentTypeForFieldType(fields.getFieldType(fieldName));

            if(contentType != null) {
                inputFields.put(fieldName, contentType);
            }
        }

        return inputFields;
    }

    public List<FormField> getFormFieldsFromProperties(List<Property> properties) {
        List<FormField> formFields = new ArrayList<>();

        for (Property p: properties) {
            switch(p.getContentType()){
                case Boolean:
                    if (Objects.equals(p.getContentValue(), "true")) {
                        formFields.add(new FormField(p.getDeveloperName(), "Yes"));
                    } else {
                        formFields.add(new FormField(p.getDeveloperName(), "No"));
                    }
                case String:
                default:
                    formFields.add(new FormField(p.getDeveloperName(), p.getContentValue()));
                    break;
            }
        }

        return formFields;
    }

    public InputStream concatenatePdfs(List<InputStream> pdfs) throws Exception {
        ByteArrayOutputStream mergedPdfOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, mergedPdfOutputStream);
        document.open();
        for (InputStream pdf:pdfs) {
            addPdfPages(pdf, copy);
        }
        document.close();

        return new ByteArrayInputStream(mergedPdfOutputStream.toByteArray());
    }

    private void addPdfPages(InputStream originalInputStream, PdfCopy copy) throws IOException, BadPdfFormatException {
        PdfImportedPage page;
        PdfCopy.PageStamp stamp;
        PdfReader reader = new PdfReader(originalInputStream);
        int numberOfPages = reader.getNumberOfPages();

        for (int pageNumber = 0; pageNumber < numberOfPages; ) {
            page = copy.getImportedPage(reader, ++pageNumber);
            stamp = copy.createPageStamp(page);
            stamp.alterContents();
            copy.addPage(page);
        }
        reader.close();
    }
}
