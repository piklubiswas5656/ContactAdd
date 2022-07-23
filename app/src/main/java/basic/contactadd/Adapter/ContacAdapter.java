package basic.contactadd.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        holder.textName.setText(date.get(position).getName());
        holder.textPhone.setText(date.get(position).getNumber());
        holder.imageView.setImageBitmap(date.get(position).getImage());
        holder.itemView.setTag(position);
   /*     holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new Dbmanager(context).deleteEntry(pop);
                date.remove(po);
                notifyItemRemoved(po);

                return true;
            }
        });*/

        holder.callNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:"+date.get(po).getNumber());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                holder.itemView.getContext().startActivity(callIntent);
            }
        });

        //delete dialog
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
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
        TextView textPhone, textName;
        ImageView imageView,callNo;

        public ContctHolder(@NonNull View itemView) {
            super(itemView);
            textPhone = itemView.findViewById(R.id.text_phone);
            textName = itemView.findViewById(R.id.text_name);
            imageView = itemView.findViewById(R.id.imagee);
            callNo=itemView.findViewById(R.id.callno);
        }
    }
}
