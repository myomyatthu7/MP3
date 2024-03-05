package app.mmt.test.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.mmt.test.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    Context context;
    ArrayList<MyData > arrayList;

    public MyAdapter(Context context, ArrayList<MyData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custm_list_p,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.btnTitle.setText(arrayList.get(position).getTitle());
        holder.btnTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Detail_P.class);
                intent.putExtra("Title",arrayList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("Desc",arrayList.get(holder.getAdapterPosition()).getDesc());
                context.startActivity(intent);
//                Toast.makeText(context, String.valueOf(arrayList.get(holder.getAdapterPosition()).getTitle()), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, String.valueOf(arrayList.get(holder.getAdapterPosition()).getDesc()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnTitle = itemView.findViewById(R.id.btnTitle);
        }
    }
}
