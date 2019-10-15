package uk.ac.brookes.alexrussell.chartinglibrary;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


/**
 * The WebViewClient for the WebView. Its principal purpose is to start evaluating the charting library's JS statements only once the
 * page and the D3 library have been fully loaded in memory.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ChartViewClient extends WebViewClient {
    ChartHolder chartHolder;
    Context context;
    ArrayList<String> JSlist = new ArrayList<>();

    public ChartViewClient(ChartHolder chartHolder) {
        super();
        this.chartHolder = chartHolder;
        this.context = chartHolder.getContext();
    }

    public void addJS(String newString) {
        JSlist.add(newString);
    }


    /**
     * Sends the JS statements to the WebView for evaluation
     * @param view the WebView.
     * @param url String of URL address of the webpage to be loaded.
     */
    @Override
    public final void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        StringBuilder finalString = new StringBuilder();

        //The statements for the top panel of the dashboard have to be added first, then the
        //statements for the charting library
        for (String JSString : JSlist) {
            finalString.append(JSString);
            view.evaluateJavascript(JSString, null);
        }
        view.evaluateJavascript(chartHolder.createChartSet().toString(), null);
        finalString.append(chartHolder.createChartSet().toString());
        //writeJSFile(finalString.toString());
    }

    /**
     * Outputs the JS statements that are sent to the WebView into an file in the internal file system.
     * @param returnString the JS specification sent to the WebView.
     */
    private void writeJSFile(String returnString) {
        try {
            File path = context.getFilesDir();
            File file = new File(path, "javascriptOutput.js");
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(returnString.toString().getBytes());
            stream.close();
        } catch (Exception e) {
            Log.v("tag", e.getMessage());
        }
    }
}
