package nader.tools.elecbillcalc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.ApplianceViewHolder> {
	
	private ArrayList<NaderAppliance> appliances;
	
	public ApplianceAdapter(ArrayList<NaderAppliance> appliances) {
		this.appliances = appliances;
	}
	
	@NonNull
	@Override
	public ApplianceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appliance, parent, false);
		return new ApplianceViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ApplianceViewHolder holder, int position) {
		NaderAppliance appliance = appliances.get(position);
		holder.textViewWattage.setText("" + appliance.getWattage()+"W");
		holder.textViewTime.setText("" + appliance.getTimeInSeconds() + " seconds");
		//holder.textViewCostPerKwh.setText("Cost Per kWh: " + appliance.getCostPerKwh());
		holder.textViewCostPerItem.setText("" + String.format("%.2f", appliance.costPerItem())+" ৳");
		
		// Set long click listener
		holder.itemView.setOnLongClickListener(v -> {
			// Remove item from list
			int positionToRemove = holder.getAdapterPosition();
			if (positionToRemove != RecyclerView.NO_POSITION) {
				appliances.remove(positionToRemove);
				notifyItemRemoved(positionToRemove);
				Toast.makeText(v.getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
			}
			return true;
		});
	}
	
	@Override
	public int getItemCount() {
		return appliances.size();
	}
	
	static class ApplianceViewHolder extends RecyclerView.ViewHolder {
		
		TextView textViewWattage, textViewTime, textViewCostPerKwh, textViewCostPerItem;
		
		public ApplianceViewHolder(@NonNull View itemView) {
			super(itemView);
			textViewWattage = itemView.findViewById(R.id.textViewWattage);
			textViewTime = itemView.findViewById(R.id.textViewTime);
			//textViewCostPerKwh = itemView.findViewById(R.id.textViewCostPerKwh);
			textViewCostPerItem = itemView.findViewById(R.id.textViewCostPerItem);
		}
	}
}