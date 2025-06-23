package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mcommercemidtermtest.R;

import java.util.ArrayList;

import model.LOT;

public class LOTAdapter extends ArrayAdapter<LOT> {
    Activity context;
    int resource;
    ArrayList<LOT> data;

    public LOTAdapter(Activity context, int resource, ArrayList<LOT> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    static class ViewHolder {
        TextView tvTaskTitle, tvTaskDate, tvTaskStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(resource, null);
            holder = new ViewHolder();

            holder.tvTaskTitle = convertView.findViewById(R.id.tv_task_title);
            holder.tvTaskDate = convertView.findViewById(R.id.tv_task_date);
            holder.tvTaskStatus = convertView.findViewById(R.id.tv_task_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LOT task = data.get(position);
        holder.tvTaskTitle.setText(task.getTaskTitle());
        holder.tvTaskDate.setText("Ngày giao: " + task.getDateAssigned());
        holder.tvTaskStatus.setText("Trạng thái: " + (task.getIsCompleted() == 1 ? "Đã hoàn thành" : "Chưa hoàn thành"));

        return convertView;
    }
}
