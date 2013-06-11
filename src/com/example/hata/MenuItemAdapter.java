package com.example.hata;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class MenuItemAdapter extends ArrayAdapter<Dish> implements Filterable {
    private int resource;
    private final Activity context;
    private List<Dish> items;
    private List<Dish> original;
    private  Typeface font;
    private Dish item;
    private int position;
    DishFilter dishFilter;

    public MenuItemAdapter(Activity _context, int _resource, List<Dish> _items) {
        super(_context, _resource, _items);
        resource=_resource;
        this.context = _context;
        this.items=_items;
        this.original = new ArrayList<Dish>(items);
//        font= Typeface.createFromAsset(context.getAssets(), "fonts/Tokio.tf");
    }

    static class ViewHolder {
        TextView name;
        TextView price;
        ImageView icon;
    }

    public static String fmt(double d)
    {
/*        if(d == (int) d)
            return String.format("%d",(int)d);
        else*/
            return String.format("%.2f грн.",d);
    }

    @Override
    public View getView(final int _position, View convertView, ViewGroup parent) {
        //To change body of overridden methods use File | Settings | File Templates.
        ViewHolder holder;
        View rowView = convertView;
        position = _position;
        item = getItem(position);
        String name = item.getName();
        double price = item.getPrice();
        Bitmap icon = item.getImage_bmp();


        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.dish_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.nameTV);
            holder.price = (TextView) rowView.findViewById(R.id.priceTV);
            holder.icon = (ImageView) rowView.findViewById(R.id.itemIV);

            rowView.setTag(holder);

        }  else {
            holder = (ViewHolder) rowView.getTag();
        }
//


        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.

                OpenProductActivity(_position);
            }
        });

        holder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.

                OpenProductActivity(_position);
            }
        });

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.

                OpenProductActivity(_position);
            }
        });



//      holder.name.setTypeface(font);
//      holder.price.setTypeface(font);
        holder.name.setText(name);
        holder.icon.setImageBitmap(icon);
        String s = fmt(price);
        holder.price.setText(s);

      return rowView;
    }

    @Override
    public Filter getFilter() {

        if (dishFilter == null)
            dishFilter = new DishFilter();

        return dishFilter;

    }

    private void OpenProductActivity(int position){
        Dish cur_item = items.get(position);
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("product", cur_item);
        context.startActivity(intent);
    }

    private class DishFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint != null && constraint.toString().length() > 0) {
                List<Dish> founded = new ArrayList<Dish>();
                for (Dish d : items) {
                    if (d.getName().toLowerCase().contains(constraint))
                        founded.add(d);
                }
                result.values = founded;
                result.count = founded.size();
            } else {
                result.values = items;
                result.count = items.size();
            }
            return result;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //To change body of implemented methods use File | Settings | File Templates.
            // Now we have to inform the adapter about the new list filtered
            clear();
            ArrayList<Dish> fitems = (ArrayList<Dish>) results.values;

            for (Dish d : fitems) {
                add(d);
            }
            if (fitems.size()>0) notifyDataSetChanged();
            else notifyDataSetInvalidated();
            }

    }
}


