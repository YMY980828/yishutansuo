package com.example.yishutansuo;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;
import adapter.RecyclerViewAdapter1;
import butterknife.BindView;
import util.CustomLayoutManager;
import util.LinearItemDecoration;
public class Activity7 extends BaseActivity {

    @BindView(R.id.rv)
    public RecyclerView rv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        init();
        Test1();
    }
    private void Test1(){
        ArrayList<String> arrayList = new ArrayList<>();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
      // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
     //   GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        //   StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        CustomLayoutManager layoutManager = new CustomLayoutManager();

        for (int i=0;i<100;i++){
            arrayList.add("第"+i+"个item");
        }

      //  rv.setLayoutManager(linearLayoutManager);
      //  rv.setLayoutManager(gridLayoutManager);
        rv.setLayoutManager(layoutManager);
        RecyclerViewAdapter1 adapter1 = new RecyclerViewAdapter1(this,arrayList);
        rv.setAdapter(adapter1);
//        DividerItemDecoration mDivider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        rv.addItemDecoration(mDivider);

        LinearItemDecoration itemDecoration = new LinearItemDecoration();
        rv.addItemDecoration(itemDecoration);

    }



}
