package kr.ac.skuniv.di.hangulstudy;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StudyListActivity extends FragmentActivity {

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
                Intent intent = new Intent(StudyListActivity.this, StudyActivity.class);
                startActivity(intent);
            }
        });
    }
}
