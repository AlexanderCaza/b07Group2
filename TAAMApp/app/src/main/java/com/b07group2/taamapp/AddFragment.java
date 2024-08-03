package com.b07group2.taamapp;

import static com.google.android.gms.tasks.Tasks.await;

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
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Array;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddFragment extends Fragment {

    private TextInputLayout lotInput;
    private TextInputLayout nameInput;
    private TextInputLayout categoryInput;
    private TextInputLayout periodInput;
    private EditText descriptionInput;
    private Button selectMediaButton;
    private Button uploadMediaButton;
    private Button submitButton;

    private ArrayList<Uri> mediaUris;
    private ArrayList<Uri> cloudMediaUris;

    private CollectionsDatabase database;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        mediaUris = new ArrayList<>();
                        if (result.getData() != null && result.getData().getClipData() != null) {
                            int count = result.getData().getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                                mediaUris.add(imageUri);
                            }
                            if (mediaUris.size() >= 1) {
                                uploadMediaButton.setEnabled(true);
                                uploadMediaButton.setText(MessageFormat.format(
                                        "Upload {0} media files", mediaUris.size()));
                            }
                        }
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        database = new CollectionsDatabase();

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        AutoCompleteTextView autoCompleteCategories = view.findViewById(R.id.autoCompleteCategory);
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(
                getActivity().getApplicationContext(), R.layout.list_item, ItemCollection.getValidCategories());
        autoCompleteCategories.setAdapter(adapterCategories);
        autoCompleteCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Category: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        AutoCompleteTextView autoCompletePeriods = view.findViewById(R.id.autoCompletePeriod);
        ArrayAdapter<String> adapterPeriods = new ArrayAdapter<String>(
                getActivity().getApplicationContext(), R.layout.list_item, ItemCollection.getValidPeriods());
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

        selectMediaButton = view.findViewById(R.id.selectMediaButton);
        uploadMediaButton = view.findViewById(R.id.uploadMediaButton);
        submitButton = view.findViewById(R.id.submitButton);

        // The logic

        selectMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/* video/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                activityResultLauncher.launch(intent);
            }
        });

        uploadMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadMediaButton.setText("Uploading files...");
                uploadMediaButton.setEnabled(false);
                submitButton.setEnabled(false);
                cloudMediaUris = new ArrayList<>();
                uploadMedia(cloudMediaUris);
            }
        }
        );


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked on submit", "Clicked");
                if(!verifyAllInput())
                    return;
                if(uploadData()) {
//                    resetInput();
                }

            }
        });

        return view;
    }

    private boolean uploadData() {
        ItemCollection item;
        int lotNumber = Integer.parseInt(String.valueOf(lotInput.getEditText().getText()));
        String name = String.valueOf(nameInput.getEditText().getText());
        String category = String.valueOf(categoryInput.getEditText().getText());
        String period = String.valueOf(periodInput.getEditText().getText());
        String description = String.valueOf(descriptionInput.getText());
        if (!(cloudMediaUris == null)) {
            String[] urisStringData = new String[cloudMediaUris.size()];

            for (int i = 0; i < cloudMediaUris.size(); i++) {
                urisStringData[i] = cloudMediaUris.get(i).toString();
            }
            System.out.println(urisStringData[0]);
            item = new ItemCollection(lotNumber, name, category, period, description,
                    Arrays.asList(urisStringData));
            Log.d("Data Upload", "Yes Image");
        } else {
            item = new ItemCollection(lotNumber, name, category, period, description);
            Log.d("Data Upload", "No image");
        }

        database.addItemCollection(item);

        Log.d("Data Upload", "success");

        return true;
    }

    private void resetInput() {
        lotInput.getEditText().setText(null);
        nameInput.getEditText().setText(null);
        categoryInput.getEditText().setText(null);
        periodInput.getEditText().setText(null);
        descriptionInput.setText(null);
        mediaUris = null;
        cloudMediaUris = null;
        uploadMediaButton.setText("Upload Media Files");
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
        return verified;

    }
    private boolean verifyLotInput() {
        if  (lotInput.getEditText().getText().length() <= 0) {
            lotInput.setError("Required*");
            return false;
        }
        else if  (lotInput.getEditText().getText().length() > 9) {
            lotInput.setError("Max Lot Number: 999999999*");
            return false;
        }
        else if (!database.uniqueLotNumber(Integer.parseInt(lotInput.getEditText().getText()
                .toString()))) {
            lotInput.setError("Duplicate Lot Number*");
            return false;
        }
        lotInput.setError(null);
        return true;
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

    private void uploadMedia(List<Uri> returnedUris) {
        StorageReference imageSite = FirebaseStorage.getInstance(
                        "gs://cscb07-70b84.appspot.com").getReference().child("media")
                .child(UUID.randomUUID().toString());
        Uri fileToUpload = mediaUris.get(returnedUris.size());
        UploadTask task = imageSite.putFile(fileToUpload);
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageSite.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String result = task.getResult().toString();
                        returnedUris.add(Uri.parse(result));
                        if (mediaUris.size() == returnedUris.size()) {
                            Toast.makeText(getContext(), "Files uploaded successfully", Toast.LENGTH_SHORT).show();
                            uploadMediaButton.setText("Upload images");
                            uploadMediaButton.setEnabled(true);
                            submitButton.setEnabled(true);
                        } else {
                            uploadMedia(returnedUris);
                        }
                    }
                });
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
