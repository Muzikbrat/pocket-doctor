package com.example.tanisha.pocketdoctor;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
public class SymptomAdapter extends BaseAdapter {
    Activity context;
    ArrayList<String> symptomname;
    ArrayList<Integer> severity;
    public SymptomAdapter(Activity context, ArrayList<String> symptomname, ArrayList<Integer> severity) {
        super();
        this.context = context;
        this.symptomname = symptomname;
        this.severity = severity;
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return symptomname.size();
    }
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_symptom, null);
            holder = new ViewHolder();
            holder.txtViewSymptomname = (TextView) convertView.findViewById(R.id.symptomname);
            holder.txtViewSeverity = (TextView) convertView.findViewById(R.id.severity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtViewSymptomname.setText(symptomname.get(position));
        holder.txtViewSeverity.setText(severity.get(position).toString());
        return convertView;
    }
    private class ViewHolder {
        TextView txtViewSymptomname;
        TextView txtViewSeverity;
    }
}

