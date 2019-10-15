package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * Created by Alex Russell on 13/12/2018.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Customizes and loads the WebView.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ChartViewHandler {

    private WebView webView;
    private ChartViewClient client;

    /**
     * Recommended constuctor.
     * @param webView the WebView to be handled.
     * @param holder the ChartHolder containing the charts.
     */
    public ChartViewHandler(WebView webView, ChartHolder holder) {
        this.client = new ChartViewClient(holder);
        this.webView = webView;
        configureWebView();
    }

    /**
     * Constructor for those who wish to modify the ChartViewClient.
     * @param webView the WebView to be handled.
     * @param holder the ChartHolder containing the charts.
     * @param client the ChartViewClient.
     */
    public ChartViewHandler(WebView webView, ChartHolder holder, ChartViewClient client) {
        this.client = client;
        this.webView = webView;
        configureWebView();
    }

    private void configureWebView() {
        webView.setWebViewClient(client);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
    }

    /**
     * Loads the charts into the WebView.
     */
    public void loadCharts() {
        webView.loadUrl("file:///android_asset/blank.html");
    }

    /**
     * Loads an update.
     * @param chartUpdate the ChartUpdate object specifying the nature of the update.
     */
    public void loadUpdate(ChartUpdate chartUpdate) {
        webView.evaluateJavascript(chartUpdate.getUpdateString(), null);
    }

    private void writeUpdate (String update) {
        try {
            Context context = webView.getContext();
            File path = context.getFilesDir();
            File file = new File(path, "updateOutput.js");
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(update.getBytes());
            stream.close();
        } catch (Exception e) {
            Log.v("tag", e.getMessage());
        }
    }
}
