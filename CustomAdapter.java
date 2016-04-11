package org.wehavepower.joboffers;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pc on 3/17/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";




    private ArrayList<String> arraynombre_empresa = new ArrayList<String>();
    private ArrayList<String> arraypuesto_trabajo = new ArrayList<String>();
    private ArrayList<String> arrayrequisitos = new ArrayList<String>();
    private ArrayList<String> arraycontacto = new ArrayList<String>();
    private ArrayList<String> arrayfecha = new ArrayList<String>();
    private ArrayList<Bitmap> arrayimagenes = new ArrayList<Bitmap>();



    private String[] mDataSet;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView card_title;
        private TextView card_content;
        private TextView card_contact;
        private TextView card_date;
        private TextView card_requirements;
        private ImageView OfferImage;



        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });


            card_title = (TextView) v.findViewById(R.id.card_title);
            card_content = (TextView) v.findViewById(R.id.card_content);
            card_contact = (TextView) v.findViewById(R.id.card_contact);
            card_date = (TextView) v.findViewById(R.id.card_date);
            card_requirements = (TextView) v.findViewById(R.id.card_requirements);
            OfferImage = (ImageView) v.findViewById(R.id.offer_mainimage);



        }


        public TextView getTAG_EMPRESA() {
            return card_title;
        }

        public TextView getTAG_Oferta () {
            return card_content;
        }

        public TextView getTAG_REQUISITOS(){
            return card_requirements;
        }

        public TextView getTAG_CONTACTO(){
            return card_contact;
        }

        public TextView getTAG_FECHA(){
            return card_date;
        }

        public ImageView getTAG_OFFERBITMAP(){
            return OfferImage;
        }


    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param //dataSet String[] containing the data to populate views to be used by RecyclerView.
     */

   /*/ public CustomAdapter(String[] dataSet) {
        mDataSet = dataSet;
    }
   /*/

    public CustomAdapter(
            ArrayList<String> empresas,
            ArrayList<String> puestos,
            ArrayList<String> contactos,
            ArrayList<String> fechas,
            ArrayList<String> requisitos,
            ArrayList<Bitmap> imagenes) {

        arraynombre_empresa = empresas;
        arraypuesto_trabajo = puestos;
        arraycontacto = contactos;
        arrayfecha = fechas;
        arrayrequisitos = requisitos;
        arrayimagenes = imagenes;

    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_item, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        viewHolder.getTAG_EMPRESA().setText(arraynombre_empresa.get(position));
        viewHolder.getTAG_Oferta().setText(arraypuesto_trabajo.get(position));
        viewHolder.getTAG_REQUISITOS().setText(arrayrequisitos.get(position));
        viewHolder.getTAG_CONTACTO().setText(arrayrequisitos.get(position));
        viewHolder.getTAG_FECHA().setText(arrayrequisitos.get(position));
        viewHolder.getTAG_OFFERBITMAP().setImageBitmap(arrayimagenes.get(position));
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arraynombre_empresa.size();
    }
}
