package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yishutansuo.R;

import java.util.List;

public class RecyclerViewAdapter1  extends   RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> list;
    public RecyclerViewAdapter1(Context context,List<String> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        RHolder rHolder = new RHolder(v);
        return rHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RHolder rHolder = (RHolder)holder;
        rHolder.tv.setText(list.get(position));
//        ViewGroup.LayoutParams lp = rHolder.tv.getLayoutParams();
//        lp.height = getRandomHeight();
//        rHolder.tv.setLayoutParams(lp);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public RHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.r_tv);
        }
    }
    private int getRandomHeight(){
        int randomHeight = 0;
        do{
            randomHeight = (int)(Math.random()*300);
        }while (randomHeight == 0);
        return randomHeight;
    }

}
