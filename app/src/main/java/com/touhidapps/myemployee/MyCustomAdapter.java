package com.touhidapps.myemployee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Touhid on 6/19/2016.
 */
public class MyCustomAdapter extends BaseAdapter {
    Context _context;
    String[] _id, _name, _phone;
    public static LayoutInflater _my_layoutInflater = null;


    public MyCustomAdapter(AdminPanel context, String[] id, String[] name, String[] phone) {

        _context = context;
        _id = id;
        _name = name;
        _phone = phone;


        _my_layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return _id.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    /**
     * Inner class
     */

    public class HolderClass{
        TextView textView_list_item_id;
        TextView textView_list_item_name;
        TextView textView_list_item_phone;
    }









    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HolderClass myHolder = new HolderClass();
        View myView = _my_layoutInflater.inflate(R.layout.my_custom_list_layout, null);

        myHolder.textView_list_item_id = (TextView) myView.findViewById(R.id.textView_list_item_id);
        myHolder.textView_list_item_name = (TextView) myView.findViewById(R.id.textView_list_item_name);
        myHolder.textView_list_item_phone = (TextView) myView.findViewById(R.id.textView_list_item_phone);

        myHolder.textView_list_item_id.setText(_id[position]);
        myHolder.textView_list_item_name.setText(_name[position]);
        myHolder.textView_list_item_phone.setText(_phone[position]);




        return myView;
    }
}
