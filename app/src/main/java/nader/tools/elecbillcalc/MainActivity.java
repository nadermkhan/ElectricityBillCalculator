package nader.tools.elecbillcalc;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.Spanned;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	
	private EditText editTextWattage, editTextTime, editTextCostPerKwh;
	private Spinner spinnerTimeType;
	private Button buttonAddItem, buttonCalculateTotal;
	private RecyclerView recyclerView;
	private ApplianceAdapter applianceAdapter;
	private ArrayList<NaderAppliance> appliances;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editTextWattage = findViewById(R.id.editTextWattage);
		editTextTime = findViewById(R.id.editTextTime);
		//editTextCostPerKwh = findViewById(R.id.editTextCostPerKwh);
		spinnerTimeType = findViewById(R.id.spinnerTimeType);
		buttonAddItem = findViewById(R.id.buttonAddItem);
		buttonCalculateTotal = findViewById(R.id.buttonCalculateTotal);
		recyclerView = findViewById(R.id.recyclerView);
		
		appliances = new ArrayList<>();
		applianceAdapter = new ApplianceAdapter(appliances);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(applianceAdapter);
		
		buttonAddItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addItem();
			}
		});
		
		buttonCalculateTotal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				calculateTotal();
			}
		});
	}
	
	private void addItem() {
		try {
			int wattage = Integer.parseInt(editTextWattage.getText().toString());
			int time = Integer.parseInt(editTextTime.getText().toString());
			//double costPerKwh = Double.parseDouble(editTextCostPerKwh.getText().toString());
			TIMETYPE timeType = TIMETYPE.valueOf(spinnerTimeType.getSelectedItem().toString().toUpperCase());
			
			NaderAppliance appliance = new NaderAppliance(wattage, timeType, time);
			appliances.add(appliance);
			applianceAdapter.notifyItemInserted(appliances.size() - 1);
			} catch (NumberFormatException e) {
			Toast.makeText(this, "Please enter valid inputs", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void calculateTotal() {
		double totalExpense = 0.0;
		for (NaderAppliance appliance : appliances) {
			totalExpense += appliance.costPerItem();
		}
		totalExpense *= 31; // for a month
		
		SpannableString message = new SpannableString("Your Estimated Total Monthly Electricity Bill: " + String.format("%.2f", totalExpense) + " TK only.");
		message.setSpan(new StyleSpan(Typeface.BOLD),
		"Your Estimated Total Monthly Electricity Bill: ".length(),
		message.length() - " TK only.".length(),
		Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
		builder.setTitle("Success");
		builder.setMessage(message);
		
		builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Respond to positive button press
			}
		});
		builder.show();
	}
}