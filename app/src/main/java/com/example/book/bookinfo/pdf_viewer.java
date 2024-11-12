package com.example.book.bookinfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class pdf_viewer {

    private Context context;

    public pdf_viewer(Context context) {
        this.context = context;
    }

    private File getFileFromAssets(String fileName) throws IOException {
        InputStream inputStream = context.getAssets().open(fileName);
        File outFile = new File(context.getCacheDir(), fileName);
        FileOutputStream outputStream = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();

        return outFile;
    }

    public void initPdfViewer(String fileName) {
        try {
            File pdfFile = getFileFromAssets(fileName);
            Uri pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", pdfFile);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            context.startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
