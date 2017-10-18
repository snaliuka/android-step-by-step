package com.exadel.sampleapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exadel.sampleapp.R;

import java.util.ArrayList;
import java.util.List;


public class SampleSelectionSpinnerAdapter extends BaseAdapter {    // need to extend adapter class

    private List<Integer> actionNameResourcesIds = new ArrayList<>();
    private LayoutInflater inflater;

    public SampleSelectionSpinnerAdapter(Context context, List<Integer> actionNameResourcesIds) {
        this.actionNameResourcesIds.addAll(actionNameResourcesIds);
        this.inflater = LayoutInflater.from(context);
    }

    // total amount of items in the list (total amount of items, that can be seen if user scroll over
    // all of them
    @Override
    public int getCount() {
        return actionNameResourcesIds.size();
    }


    // get particular item by it's possition
    @Override
    public Integer getItem(int position) {
        return actionNameResourcesIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Create (or reuse) existing view. In usual adapters user need to implement manually
     * ViewHolder pattern for reusing items (old layouts)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_spinner_item, parent, false);
            holder = new ViewHolder();
            holder.captionView = convertView.findViewById(R.id.tv_caption);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Integer resId = actionNameResourcesIds.get(position);
        holder.captionView.setText(convertView.getContext().getString(resId));
        convertView.setTag(holder);
        return convertView;
    }

    private class ViewHolder {
        private TextView captionView;
    }
}
