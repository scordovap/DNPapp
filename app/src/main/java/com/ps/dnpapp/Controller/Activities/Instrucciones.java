package com.ps.dnpapp.Controller.Activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


import com.ps.dnpapp.Model.Adaptador.AdaptadorInstrucciones;
import com.ps.dnpapp.Model.InstruccionesClass;
import com.ps.dnpapp.R;

import java.util.ArrayList;
import java.util.List;

public class Instrucciones extends Activity implements OnItemClickListener {

    public static final String[] titles = new String[] { "Enfocar Foto",
            "Tomar Foto", "Estado de sensores"};

    public static final String[] descriptions = new String[] {
            " Enfocar la camara",
            "Tomar la foto",
            "Estado de los sensores" };

    public static final Integer[] images = { R.drawable.enfocar, R.drawable.captura, R.drawable.sensores };

    ListView listView;
    List<InstruccionesClass> medicamentos;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);

        medicamentos = new ArrayList<InstruccionesClass>();
        for (int i = 0; i < titles.length; i++) {
            InstruccionesClass item = new InstruccionesClass(images[i], titles[i], descriptions[i]);
            medicamentos.add(item);
        }

        listView = (ListView) findViewById(R.id.listInstruc);
        AdaptadorInstrucciones adapter = new AdaptadorInstrucciones(this,
                R.layout.list_item_instrucciones, medicamentos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + medicamentos.get(position),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}