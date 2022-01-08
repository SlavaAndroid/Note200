package com.example.note200.ui.details;

import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.note200.R;
import com.example.note200.database.source.Note;
import com.example.note200.databinding.DialogDelete1Binding;
import com.example.note200.ui.list.ListFragment;
import com.example.note200.ui.list.NoteAdapter;

import java.util.Objects;
import java.util.zip.Inflater;

public class DialogDelete1 extends DialogFragment {

    final String LOG_TAG = "myLogs";
    DialogDelete1Binding binding;

    DeleteYesButtonListener listener;

    public void setListener(DeleteYesButtonListener listener) {
        this.listener = listener;
    }

    public static DialogDelete1 newInstance() {
        return new DialogDelete1();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog()).setTitle(R.string.message_dialog_delete);
        binding = DialogDelete1Binding.inflate(inflater, container, false);
        binding.btnYes.setOnClickListener(v -> {
            if (listener != null) listener.delete();
            dismiss();
        });
        binding.btnNo.setOnClickListener(v -> dismiss());
        return binding.getRoot();
    }

    public void onClick(View v) {
        Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());
//        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    public interface DeleteYesButtonListener {
        public void delete();
    }

}
