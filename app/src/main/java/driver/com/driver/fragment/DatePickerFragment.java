package driver.com.driver.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView source;
    int year, month, day;

    public DatePickerFragment() {
        //Required no Constructor
    }

    public DatePickerFragment(TextView textView) {
        this.source = textView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 0);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dpd = null;
        try {
            dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.getDatePicker().updateDate(year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dpd;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        try {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(c.getTime());
            source.setText(formattedDate);
            Log.d("Date", formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}