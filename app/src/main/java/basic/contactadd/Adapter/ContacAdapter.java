package basic.contactadd.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Locale;

import basic.contactadd.Dbmanager;
import basic.contactadd.MainActivity;
import basic.contactadd.Model.CDel;
import basic.contactadd.R;

public class ContacAdapter extends RecyclerView.Adapter<ContacAdapter.ContctHolder> {
    private ArrayList<CDel> date;
    private Context context;
    private Cursor cursor;

    public ContacAdapter(ArrayList<CDel> date, Context context, Cursor cursor) {
        this.date = date;
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ContctHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ctc_item, parent, false);
        return new ContctHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContctHolder holder, int position) {
//        if (!cursor.move(position)) {
//            return;
//        }
        int po = position;
        long pop = position + 1;
        String ID = "id";
        holder.imageView.setImageBitmap(date.get(position).getImage());
//        holder.itemView.setTag(position);
   /*     holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new Dbmanager(context).deleteEntry(pop);
                date.remove(po);
                notifyItemRemoved(po);

                return true;
            }
        });*/


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri number = Uri.parse("tel:"+date.get(po).getNumber());
//                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);


            /*    Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + date.get(po).getNumber()));


                holder.itemView.getContext().startActivity(callIntent);*/
                checkPermi(holder.itemView.getContext(), po);
            }
        });

        //delete dialog
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setMessage("Are you sure you wants to Delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Dbmanager(context).deleteEntry(date.get(po).getName());
                                date.remove(po);
                                notifyItemRemoved(po);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });



       /* new Dbmanager(context).deleteEntry(pop);
        date.remove(po);
        notifyItemRemoved(po);*/
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class ContctHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView callNo;

        public ContctHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagee);
            callNo = itemView.findViewById(R.id.callno);
        }
    }

    private void checkPermi(Context context, int po) {

        Dexter.withContext(context)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + date.get(po).getNumber()));


                        context.startActivity(callIntent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

//    private void Texttopech() {
//        // create an object textToSpeech and adding features into it
//        TextToSpeech textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int i) {
//                textToSpeech.setLanguage(Locale.UK);
//            }
//        });
//    }


}
