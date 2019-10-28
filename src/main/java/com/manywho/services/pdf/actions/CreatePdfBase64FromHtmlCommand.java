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
import javax.inject.Inject;
import java.io.InputStream;

public class CreatePdfBase64FromHtmlCommand implements ActionCommand<ServiceConfiguration, CreatePdfBase64FromHtml, CreatePdfBase64FromHtml.Input, CreatePdfBase64FromHtml.Output> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreatePdfBase64FromHtmlCommand.class);

    private PdfGeneratorService pdfGeneratorService;

    @Inject
    public CreatePdfBase64FromHtmlCommand(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @Override
    public ActionResponse<CreatePdfBase64FromHtml.Output> execute(ServiceConfiguration serviceConfiguration, ServiceRequest serviceRequest, CreatePdfBase64FromHtml.Input input) {
        try {
            InputStream inputPdf = pdfGeneratorService.generatePdfFromHtml(input.getHtmlContent());
            byte[] bytes = IOUtils.toByteArray(inputPdf);
            CreatePdfBase64FromHtml.Output output = new CreatePdfBase64FromHtml.Output(Base64.encodeBytes(bytes));

            return new ActionResponse<>(output);
        } catch (Exception e) {
            LOGGER.error("There was a problem generating a PDF from the provided HTML content: {}", e.getMessage(), e);

            throw new RuntimeException(String.format("There was a problem generating a PDF from the provided HTML content: %s", e.getMessage()));
        }
    }
}
