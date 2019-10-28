package com.manywho.services.pdf.actions;

import com.manywho.sdk.api.run.elements.config.ServiceRequest;
import com.manywho.sdk.services.actions.ActionCommand;
import com.manywho.sdk.services.actions.ActionResponse;
import com.manywho.services.pdf.ServiceConfiguration;
import com.manywho.services.pdf.services.PdfGeneratorService;
import com.manywho.services.pdf.types.PDFBase64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.util.Base64;

public class ConcatenatePdfBase64Command implements ActionCommand<ServiceConfiguration, ConcatenatePdfBase64, ConcatenatePdfBase64.Input, ConcatenatePdfBase64.Output> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcatenatePdfBase64Command.class);
    private PdfGeneratorService pdfGeneratorService;

    @Inject
    public ConcatenatePdfBase64Command(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @Override
    public ActionResponse<ConcatenatePdfBase64.Output> execute(ServiceConfiguration serviceConfiguration, ServiceRequest serviceRequest, ConcatenatePdfBase64.Input input) {
        try {
            List<InputStream> inputStreams = input.getPDFs()
                .stream()
                .map(item -> this.decode(item))
                .collect(Collectors.toList());

            InputStream pdf = pdfGeneratorService.concatenatePdfs(inputStreams);
            byte[] bytes = IOUtils.toByteArray(pdf);
            ConcatenatePdfBase64.Output output = new ConcatenatePdfBase64.Output(Base64.encodeBytes(bytes));

            return new ActionResponse<>(output);
        } catch (Exception e) {
            e.printStackTrace();

            LOGGER.error("There was a problem concatenating the PDF from the provided files: {}", e.getMessage(), e);

            throw new RuntimeException(String.format("There was a problem concatenating the PDF from the provided files: %s", e.getMessage()));
        }
    }

    private InputStream decode(PDFBase64 item) {
        try {
            return new ByteArrayInputStream(Base64.decode(item.getData()));
        }
        catch (Exception e) {
            return null;
        }
    }
}
