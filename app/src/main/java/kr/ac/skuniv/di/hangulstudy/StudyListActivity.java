package kr.ac.skuniv.di.hangulstudy;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;

public class StudyListActivity extends FragmentActivity {
    kr.ac.skuniv.di.hangulstudy.BringHangulInfo bringHangulInfo;
    String hangulinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_studylist);




        ListView listview = (ListView) findViewById(R.id.listview) ;
        ListViewAdapter adapter = new ListViewAdapter();
        listview.setAdapter(adapter) ;

        adapter.addItem("1일차","간다");
        adapter.addItem("2일차","하루");
        adapter.addItem("3일차","비행기");
        adapter.addItem("4일차","배");




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int i, long l) {

                bringHangulInfo = new kr.ac.skuniv.di.hangulstudy.BringHangulInfo(String.valueOf(i+1));
                try {
                    hangulinfo = bringHangulInfo.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Log.d("aaa",hangulinfo+"<<<<<<<<<<<<");

                Intent intent = new Intent(StudyListActivity.this, StudyActivity.class);
                intent.putExtra("hangulinfo",hangulinfo);
                startActivity(intent);
            }
        });
    }
}
