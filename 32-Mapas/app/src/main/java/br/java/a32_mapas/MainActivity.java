package br.java.a32_mapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;

import android.location.Location;
import android.location.LocationListener;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
         implements
                GoogleApiClient.ConnectionCallbacks,
                GoogleApiClient.OnConnectionFailedListener,
                LocationListener,
                GoogleMap.OnMapLongClickListener{

    private static final int REQUEST_ERRO_PLAY_SERVICES = 1;
    private static final int REQUEST_CHECAR_GPS = 2;
    private  static final int REQUEST_PERMISSIONS = 3;

    private static final String EXTRA_DIALOG = "dialog";

    private static final int LOADER_ENDERECO = 1;
    private static final int LOADER_ROTA = 2;
    private static final String EXTRA_ROTA = "rota";

    private static final String EXTRA_ORIG = "orig";
    private static final String EXTRA_DEST = "dest";

    private static final String TAG = "map";

    GoogleApiClient mGoogleApiClient;
    GoogleMap mGoogleMap;
    LatLng mOrigem;

    Handler mHandler;
    boolean mDeveExibirDialog;
    int mTentativas;

    EditText mEdtLocal;
    ImageButton mBtnBuscar;
    DialogFragment mDialogEnderecos;
    TextView mTxtProgresso;
    LinearLayout mLayoutProgresso;
    LoaderManager mLoaderManager;
    LatLng mDestino;

    ArrayList<LatLng> mRota;
    Marker mMarkerLocalAtual;

    GeofenceInfo mGeofenceInfo;
    GeofenceDB mGeofenceDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("NGVL", "onCreate::BEGIN");
        setContentView(R.layout.activity_main);

        mEdtLocal = (EditText)findViewById(R.id.edtLocal);
        mBtnBuscar = (ImageButton)findViewById(R.id.imgBtnBuscar);
        mBtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBtnBuscar.setEnabled(false);
                buscarEndereco();
            }
        });

        mLoaderManager = getSupportLoaderManager();
        mTxtProgresso = (TextView)findViewById(R.id.txtProgresso);
        mLayoutProgresso = (LinearLayout)findViewById(R.id.llProgresso);
        mDeveExibirDialog = savedInstanceState == null;
        mHandler = new Handler();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGeofenceDB = new GeofenceDB(this);
        Log.d("NGVL", "onCreate::END");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}