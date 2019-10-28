package com.manywho.services.pdf.actions;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.actions.Action;
import com.manywho.services.pdf.types.PDFBase64;

import java.util.List;

@Action.Metadata(name = "Concatenate PDF (Base64 encoded)", summary = "Concatenate PDFs to a Base64 encoded string", uri = "concatenate-pdf-base64")
public class ConcatenatePdfBase64 {

    public static class Input {
        @Action.Input(name = "PDFs (Base64 Encoded)", contentType = ContentType.List, required = true)
        private List<PDFBase64> pdfs;

        public List<PDFBase64> getPDFs() {
            return pdfs;
        }
    }

    public static class Output {
        @Action.Output(name = "PDF Concatenated (Base64 Encoded)", contentType = ContentType.String)
        private String pdf;

        public Output(String pdf) {
            this.pdf = pdf;
        }
    }
}
