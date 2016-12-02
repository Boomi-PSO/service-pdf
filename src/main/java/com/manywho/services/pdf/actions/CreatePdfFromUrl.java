package com.manywho.services.pdf.actions;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.actions.Action;
import com.manywho.sdk.services.types.system.$File;

@Action.Metadata(name = "Create PDF from URL", summary = "Create PDF from URL", uri = "pdf-from-url")
public class CreatePdfFromUrl {

    public static class Input {
        @Action.Input(name = "PDF URL", contentType = ContentType.String, required = true)
        private String pdfUrl;

        public String getPdfUrl() {
            return pdfUrl;
        }
    }

    public static class Output {
        @Action.Output(name = "PDF File", contentType = ContentType.Object)
        private $File pdfFile;

        public Output($File pdfFile) {
            this.pdfFile = pdfFile;
        }
    }
}
