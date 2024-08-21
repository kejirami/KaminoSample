package jp.or.manmaru_system.kaminosample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import org.riversun.promise.Promise;

import java.util.List;

import jp.or.manmaru_system.kaminosample.localdb.LocalDB;
import jp.or.manmaru_system.kaminosample.localdb.Work;
import jp.or.manmaru_system.kaminosample.localdb.WorkDAO;

public class SubActivity extends AppCompatActivity {

    private RecyclerView rvWorks;
    private LocalDB mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        mDb = LocalDB.getDatabase(SubActivity.this);
        rvWorks = findViewById(R.id.WorkList);
        rvWorks.setLayoutManager(new LinearLayoutManager(this));

        WorkDAO dao = mDb.workDao();
        Promise.resolve().then(new Promise((action, data)->{
            List<Work> works = dao.getAll();
            WorkRecycleViewAdapter adapter = new WorkRecycleViewAdapter(works);
            rvWorks.setAdapter(adapter);
            action.resolve();
        })).start();
    }
}