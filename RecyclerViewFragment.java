package org.wehavepower.joboffers;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by pc on 3/17/2016.
 */
public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    //private static final int SPAN_COUNT = 2;
    //private static final int DATASET_COUNT = 60;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_NUMBER2 = "section_number2";

    private String TAG_RESULTS="result";
    private String TAG_EMPRESA = "nombre_empresa";
    private String TAG_OFERTA = "puesto_trabajo";
    private String TAG_REQUISITOS ="requisitos";
    private String TAG_CONTACTO ="contacto";
    private String TAG_FECHA ="fecha_listado";
    private String TAG_IMAGEURL ="url_imagen";
    private Bitmap TAG_OFFERBITMAP;


    private ArrayList<String> arraynombre_empresa = new ArrayList<String>();
    private ArrayList<String> arraypuesto_trabajo = new ArrayList<String>();
    private ArrayList<String> arrayrequisitos = new ArrayList<String>();
    private ArrayList<String> arraycontacto = new ArrayList<String>();
    private ArrayList<String> arrayfecha = new ArrayList<String>();
    private ArrayList<Bitmap> arrayimagenes = new ArrayList<Bitmap>();


    String myJSON;
    JSONArray offers = null;


    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

   /*/ protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;
/*/

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;


        public static RecyclerViewFragment newInstance(int sectionNumber, String sectionName) {


        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        Bundle args2 = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_SECTION_NUMBER2, sectionName);
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        if (savedInstanceState == null) {
            initDataset();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag,container, false);
        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new CustomAdapter(
                arraynombre_empresa,
                arraypuesto_trabajo,
                arrayrequisitos,
                arraycontacto,
                arrayfecha,
                arrayimagenes);

        setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);

        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)

        //mLinearLayoutRadioButton = (RadioButton) rootView.findViewById(R.id.linear_layout_rb);
        //mLinearLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {

            //}
       // });

       /*/ mGridLayoutRadioButton = (RadioButton) rootView.findViewById(R.id.grid_layout_rb);
        mGridLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
            }
        });
/*/
        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            //case GRID_LAYOUT_MANAGER:
              //  mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
            //mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
              //  break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                //break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {

        class GetDataJSON extends AsyncTask<String, Void, String> {
            String reg_url = "http://www.wehavepower.org/jobquery.php";

            @Override
            protected String doInBackground(String... params) {
                URL url = null;
                try {
                    url = new URL(reg_url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    assert url != null;
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }


                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;


                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                //Log.d("myJSON", result);
                decompileData();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void decompileData(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            offers = jsonObj.getJSONArray(TAG_RESULTS);


            for(int i=0;i<offers.length();i++){
                JSONObject c = offers.getJSONObject(i);
                String empresa = c.getString(TAG_EMPRESA);
                String puesto = c.getString(TAG_OFERTA);
                String contacto = c.getString(TAG_CONTACTO);
                String fecha = c.getString(TAG_FECHA);
                String requisitos = c.getString(TAG_REQUISITOS);
                String imageURL = c.getString(TAG_IMAGEURL);


                arraynombre_empresa.add(empresa);
                arraypuesto_trabajo.add(puesto);
                arrayrequisitos.add(requisitos);
                arraycontacto.add(contacto);
                arrayfecha.add(fecha);

                Bitmap jobOfferImage = new DecodeImageURLs(new DecodeImageURLs.AsyncResponseURLDecode() {
                    @Override
                    public void processFinish(Bitmap output) {

                    }
                }).execute(imageURL).get();

                arrayimagenes.add(jobOfferImage);
                mLayoutManager.onSaveInstanceState();

            }


        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Toast.makeText(getActivity(),"Loaded", Toast.LENGTH_LONG).show();

    }

}


