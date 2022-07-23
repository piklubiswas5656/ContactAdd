package basic.contactadd.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import basic.contactadd.Model.CDel;
import basic.contactadd.R;

public class ContacAdapter extends RecyclerView.Adapter<ContacAdapter.ContctHolder> {
    private ArrayList<CDel> date;
    private Context context;

    public ContacAdapter(ArrayList<CDel> date, Context context) {
        this.date = date;
        this.context = context;
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
        holder.textName.setText(date.get(position).getName());
        holder.textPhone.setText(date.get(position).getNumber());
        holder.imageView.setImageBitmap(date.get(position).getImage());
    /*    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure")
                        .setIcon(R.drawable.ic_baseline_add_24)
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            //for remove data
                                date.remove(position);
                                notifyItemRemoved(position);
                            }
                        });
                alertDialog.show();
                return true;
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class ContctHolder extends RecyclerView.ViewHolder {
        TextView textPhone, textName;
        ImageView imageView;

        public ContctHolder(@NonNull View itemView) {
            super(itemView);
            textPhone = itemView.findViewById(R.id.text_phone);
            textName = itemView.findViewById(R.id.text_name);
            imageView = itemView.findViewById(R.id.imagee);
        }
    }
}
