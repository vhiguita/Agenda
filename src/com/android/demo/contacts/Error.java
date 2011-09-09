package com.android.demo.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/*
 * Created on 15/03/2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class Error extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv=new TextView(this);
        tv.append("Indice seleccionado no valido");
        setContentView(tv);
        setContentView(R.layout.main);
    }
}
