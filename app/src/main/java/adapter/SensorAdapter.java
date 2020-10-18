package adapter;

import android.content.Context;
import android.hardware.Sensor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yishutansuo.R;

import java.util.List;

public class SensorAdapter extends BaseAdapter {

    private List<Sensor> sensors;
    private Context context;
    public SensorAdapter(List<Sensor> sensors,Context context){
            this.sensors=sensors;
            this.context=context;
    }
    @Override
    public int getCount() {
        return sensors.size();
    }

    @Override
    public Object getItem(int i) {
        return sensors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.e("ymy",String.valueOf(i));
        Holder holder ;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item1,viewGroup,false);
             holder = new Holder(view);
            view.setTag(holder);
        }else {
            holder = (Holder)view.getTag();
        }
        holder.support_bus.setText("设备供应商:"+sensors.get(i).getVendor());
        holder.device_name.setText("设备名:"+sensors.get(i).getName());
        holder.device_version.setText("设备版本:"+sensors.get(i).getVersion());

        return view;
    }

    public class Holder{

        public TextView support_bus;
        public TextView device_name;
        public TextView device_version;
        public Holder(View v){

            this.support_bus=v.findViewById(R.id.support_bus);
            this.device_name=v.findViewById(R.id.device_name);
            this.device_version=v.findViewById(R.id.device_version);

        }

    }



}
