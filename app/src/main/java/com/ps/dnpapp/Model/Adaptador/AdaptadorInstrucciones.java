package com.ps.dnpapp.Model.Adaptador;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ps.dnpapp.Model.InstruccionesClass;
import com.ps.dnpapp.R;

import java.util.List;


public class AdaptadorInstrucciones extends ArrayAdapter<InstruccionesClass> {

    Context context;

    public AdaptadorInstrucciones(Context context, int resourceId,
                                  List<InstruccionesClass> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        InstruccionesClass instrucciones = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_instrucciones, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.descAli);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.titleAli);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iconAli);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtDesc.setText(instrucciones.getDesc());
        holder.txtTitle.setText(instrucciones.getTitle());
        holder.imageView.setImageResource(instrucciones.getImageId());

        return convertView;
    }
}