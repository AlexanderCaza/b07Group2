package com.b07group2.taamapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Array;
import java.util.ArrayList;
import java.io.File;

public class AddFragment extends Fragment {

    private TextInputLayout lotInput;
    private TextInputLayout nameInput;
    private TextInputLayout categoryInput;
    private TextInputLayout periodInput;
    private EditText descriptionInput;
    private Button uploadImageButton;

    private ArrayList<Uri> urisData;

    private CollectionsDatabase database;

    private static final String[] validCategories = {"Jade", "Paintings", "Calligraphy", "Rubbings",
            "Bronze", "Brass and Copper", "Gold and Silvers", "Lacquer", "Enamels"};
    private static final String[] validPeriods = {"Xia", "Shang", "Zhou", "Chuanqiu", "Zhanggou", "Qin",
            "hang", "Shangou", "Ji", "South and North", "Shui", "Tang", "Liao", "Song",
            "Jin", "Yuan", "Ming", "Qing", "Modern"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        database = new CollectionsDatabase();

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        AutoCompleteTextView autoCompleteCategories = view.findViewById(R.id.autoCompleteCategory);
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item, validCategories);
        autoCompleteCategories.setAdapter(adapterCategories);
        autoCompleteCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Category: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        AutoCompleteTextView autoCompletePeriods = view.findViewById(R.id.autoCompletePeriod);
        ArrayAdapter<String> adapterPeriods = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item, validPeriods);
        autoCompletePeriods.setAdapter(adapterPeriods);
        autoCompletePeriods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Period: " + item, Toast.LENGTH_SHORT).show();
            }
        });


        lotInput = view.findViewById(R.id.lotInput);
        nameInput = view.findViewById(R.id.nameInput);
        categoryInput = view.findViewById(R.id.categoryInput);
        periodInput = view.findViewById(R.id.periodInput);
        descriptionInput = view.findViewById(R.id.descriptionInput);

        //add event listener for submit Button

        uploadImageButton = view.findViewById(R.id.uploadImageButton);
        Button submitButton = view.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked on submit", "Clicked");
                if(!verifyAllInput())
                    return;
                if(uploadData()) {
                    resetInput();
                }

            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Upload Image Button", "Functioning");
                String[] mimeTypes = {"image/*", "video/*"};
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    someActivityResultLauncher.launch(intent);
                } catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        return view;
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        intentDataToUris(data);
                        System.out.println(urisData);
                    }
                }
            });
    private void intentDataToUris(Intent data) {
        urisData = new ArrayList<Uri>();
        ClipData clipData = data.getClipData();
        for(int i  = 0 ; i < clipData.getItemCount(); i++) {
            urisData.add(clipData.getItemAt(i).getUri());
        }
    }
    private boolean uploadData() {
        int lotNumber = Integer.parseInt(String.valueOf(lotInput.getEditText().getText()));
        String name = String.valueOf(nameInput.getEditText().getText());
        String category = String.valueOf(categoryInput.getEditText().getText());
        String period = String.valueOf(periodInput.getEditText().getText());
        String description = String.valueOf(descriptionInput.getText());
//        String[] urisStringData = new String[urisData.size()];
        String[] urisStringData = new String[1];
        for(int i = 0; i < urisData.size(); i++) {
            urisStringData[i] = urisData.get(i).toString();
        }

        ItemCollection item = new ItemCollection(lotNumber, name, category, period, description, urisStringData);
        System.out.println(item.getCategory() +  item.getName() +  item.getLotNumber() + item.getPeriod());
        Log.d("Data Upload", "success");

        database.addItemCollection(item);
        return true;
    }

    private void resetInput() {
        lotInput.getEditText().setText(null);
        nameInput.getEditText().setText(null);
        categoryInput.getEditText().setText(null);
        periodInput.getEditText().setText(null);
        descriptionInput.setText(null);
        urisData = null;
        uploadImageButton.setText("Upload Image");
    }
    private boolean verifyAllInput() {
        boolean verified = true;
        if(!verifyLotInput())
            verified = false;
        if(!verifyNameInput())
            verified = false;
        if(!verifyPeriodInput())
            verified = false;
        if(!verifyCategoryInput())
            verified = false;
        if(!verifyImageInput())  // May not need to check in final result
            verified = false;
        return verified;

    }
    private boolean verifyLotInput() {
        if(lotInput.getEditText().getText().length() > 0) {
            lotInput.setError(null);
            return true;
        }
        lotInput.setError("Required*");
        return false;
    }
    private boolean verifyNameInput() {
        if(nameInput.getEditText().getText().length() > 0) {
            nameInput.setError(null);
            return true;
        }
        nameInput.setError("Required*");

        return false;
    }
    private boolean verifyCategoryInput() {
        if(categoryInput.getEditText().getText().length() > 0) {
            categoryInput.setError(null);
            return true;
        }
        categoryInput.setError("Required*");
        return false;
    }
    private boolean verifyPeriodInput() {
        if(periodInput.getEditText().getText().length() > 0) {
            periodInput.setError(null);
            return true;
        }
        periodInput.setError("Required*");
        return false;
    }

    private boolean verifyImageInput() {
        if(urisData != null) {
            return true;
        }
        return false;
    }
}
