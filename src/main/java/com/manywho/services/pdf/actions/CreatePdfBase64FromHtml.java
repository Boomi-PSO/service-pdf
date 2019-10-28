package com.manywho.services.pdf.actions;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.actions.Action;

@Action.Metadata(name = "Create PDF (Base64 encoded) from HTML", summary = "Convert HTML to a PDF (Base64 encoded)", uri = "html-to-pdf-base64")
public class CreatePdfBase64FromHtml {

    public static class Input {
        @Action.Input(name = "HTML Content", contentType = ContentType.Content, required = true)
        private String htmlContent;

        public String getHtmlContent() {
            return htmlContent;
        }
    }

    public static class Output {
        @Action.Output(name = "PDF Base64 Encoded", contentType = ContentType.String)
        private String pdfBase64;

        public Output(String pdfBase64) {
            this.pdfBase64 = pdfBase64;
        }
    }
}
