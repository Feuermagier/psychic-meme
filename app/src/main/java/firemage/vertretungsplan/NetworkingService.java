package firemage.vertretungsplan;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by flose on 25.07.2017.
 */

public class NetworkingService extends JobService {

    private static final String TAG = NetworkingService.class.getSimpleName();


    public static final String URL_TODAY = "http://129.143.230.62/vertretungsplan/heute.html";
    public static final String URL_TOMORROW = "http://129.143.230.62/vertretungsplan/morgen.html";
    //public static final String STANDARD_PAGE = "http://www.goethelb.de/sys_page/Page-Intern.html";

    private void doConnection() {
        if (isConnectionPossible()) {
            Document doc = tryToConnect(URL_TODAY);
            if (doc != null) {
                MainActivity.addNewSyncResultForToday(filterByRelevance(parseHTML(doc)));
                Log.d(TAG, "Connected to: \"" + URL_TODAY + "\"");
            }
            doc = tryToConnect(URL_TOMORROW);
            if (doc != null) {
                MainActivity.addNewSyncResultForTomorrow(filterByRelevance(parseHTML(doc)));
                Log.d(TAG, "Connected to: \"" + URL_TOMORROW + "\"");
            }
        } else {
            noConnection();
        }
    }

    private Document tryToConnect(String url) {
        Log.d(TAG, "Trying to connect to: \"" + url + "\"");
        try {
            return Jsoup.parse(new URL(url), 20000);
        } catch (IOException ex) {
            Log.w(TAG, ex);
            return null;
        }
    }

    private ArrayList<ChangeContainer> parseHTML(Document doc) {

        ArrayList<ChangeContainer> list = new ArrayList<>();

        Elements classRows = doc.select("table.mon_list");
        for (Element row : classRows.select("tr.list.odd, tr.list.even")) {
            String className = row.select("td.list").get(0).text();
            String hour = row.select("td.list").get(1).text();
            String lesson = row.select("td.list").get(2).text();
            String room = row.select("td.list").get(3).text();
            String type = row.select("td.list").get(4).text();
            String replace = row.select("td.list").get(6).text();
            String note = row.select("td.list").get(7).text();

            list.add(new ChangeContainer(className, hour, lesson, room, type, replace, note));
        }
        return list;
    }

    private ArrayList<ChangeContainer> filterByRelevance(ArrayList<ChangeContainer> containers) {
        ArrayList<ChangeContainer> result = new ArrayList<>();

        for(ChangeContainer container : containers) {
            String className = Utils.removeAllDots(container.schoolClass);
            String classNumber = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.pref_school_class_number_key), "");
            String classLetter = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.pref_school_class_letter_key), "");
            if(className.contains(classNumber) && className.contains(classLetter)) {
                result.add(container);
                continue;
            }
            if(Utils.isRange(className)) {
                if (Utils.containsLetters(Utils.removeAllNumbers(className))) {
                    if (className.contains(classNumber) && Utils.isLetterInRange(classLetter.charAt(0), Utils.removeAllNumbers(className))) {
                        result.add(container);
                        continue;
                    }
                } else {
                    if (Utils.isNumberInRange(Integer.parseInt(classNumber), className)) {
                        result.add(container);
                        continue;
                    }
                }
            }
        }
        return result;
    }



    private void noConnection() {
        //TODO: NO connection
    }

    private boolean isConnectionPossible() {
        NetworkInfo info = ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "NetworkingService started");

         Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doConnection();
                    }
                });
        t.start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
