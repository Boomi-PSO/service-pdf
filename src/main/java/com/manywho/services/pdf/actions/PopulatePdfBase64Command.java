package com.manywho.services.pdf.actions;

import com.manywho.sdk.api.run.elements.config.ServiceRequest;
import com.manywho.sdk.services.actions.ActionCommand;
import com.manywho.sdk.services.actions.ActionResponse;
import com.manywho.services.pdf.ServiceConfiguration;
import com.manywho.services.pdf.services.PdfGeneratorService;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.inject.Inject;

public class PopulatePdfBase64Command implements ActionCommand<ServiceConfiguration, PopulatePdfBase64, PopulatePdfBase64.Input, PopulatePdfBase64.Output> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PopulatePdfCommand.class);
    
    private PdfGeneratorService pdfGeneratorService;

    @Inject
    public PopulatePdfBase64Command(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @Override
    public ActionResponse<PopulatePdfBase64.Output> execute(ServiceConfiguration serviceConfiguration, ServiceRequest serviceRequest, PopulatePdfBase64.Input input) {
        try {
            InputStream inputPdf = new ByteArrayInputStream(Base64.decode(input.getPdfFile()));
            InputStream ouputPdf = pdfGeneratorService.populatePdfFromFields(inputPdf, input.getPdfFields());

            byte[] bytes = IOUtils.toByteArray(ouputPdf);
            PopulatePdfBase64.Output output = new PopulatePdfBase64.Output(Base64.encodeBytes(bytes));

            return new ActionResponse<>(output);
        } catch (Exception e) {
            LOGGER.error("There was a problem populating the PDF: {}", e.getMessage(), e);

            throw new RuntimeException(String.format("There was a problem populating the PDF: %s", e.getMessage()));
        }
    }
}
