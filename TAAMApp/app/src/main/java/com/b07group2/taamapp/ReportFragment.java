package com.b07group2.taamapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ReportFragment extends Fragment {
    ArrayList<ItemCollection> items;

    private static final String[] validCategories = {"Jade", "Paintings", "Calligraphy", "Rubbings",
            "Bronze", "Brass and Copper", "Gold and Silvers", "Lacquer", "Enamels"};
    private static final String[] validPeriods = {"Xia", "Shang", "Zhou", "Chuanqiu", "Zhanggou", "Qin",
            "hang", "Shangou", "Ji", "South and North", "Shui", "Tang", "Liao", "Song",
            "Jin", "Yuan", "Ming", "Qing", "Modern"};

    private AutoCompleteTextView autoCC;
    private ArrayAdapter<String> adapterC;

    private AutoCompleteTextView autoCCDP;
    private ArrayAdapter<String> adapterCDP;


    private AutoCompleteTextView autoCP;
    private ArrayAdapter<String> adapterP;

    private AutoCompleteTextView autoCPDP;
    private ArrayAdapter<String> adapterPDP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CollectionsDatabase.getCollections(collectionsList -> {
            items = collectionsList;
            if (items == null) {
                Toast.makeText(getContext(),"Empty DB",Toast.LENGTH_SHORT).show();
            }
        });

        View view = inflater.inflate(R.layout.fragment_report, container, false);

        autoCC = view.findViewById(R.id.autoCC);
        adapterC = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_item, validCategories);
        autoCC.setAdapter(adapterC);
        autoCC.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Category: " + item, Toast.LENGTH_SHORT).show();

            }
        });

        autoCCDP = view.findViewById(R.id.autoCCDP);
        adapterCDP = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_item, validCategories);
        autoCCDP.setAdapter(adapterCDP);
        autoCCDP.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Category: " + item, Toast.LENGTH_SHORT).show();

            }
        });

        autoCP = view.findViewById(R.id.autoCP);
        adapterP = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_item, validPeriods);
        autoCP.setAdapter(adapterP);
        autoCP.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Period: " + item, Toast.LENGTH_SHORT).show();

            }
        });

        autoCPDP = view.findViewById(R.id.autoCPDP);
        adapterPDP = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_item, validPeriods);
        autoCPDP.setAdapter(adapterPDP);
        autoCPDP.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Period: " + item, Toast.LENGTH_SHORT).show();

            }
        });
        //=====================Data fields from here===========

        EditText RLNNum = (EditText) view.findViewById(R.id.RLNNum);
        EditText RNText = (EditText) view.findViewById(R.id.RNText);
        AutoCompleteTextView autoCC = (AutoCompleteTextView) view.findViewById(R.id.autoCC);
        AutoCompleteTextView autoCCDP = (AutoCompleteTextView) view.findViewById(R.id.autoCCDP);
        AutoCompleteTextView autoCP = (AutoCompleteTextView) view.findViewById(R.id.autoCP);
        AutoCompleteTextView autoCPDP = (AutoCompleteTextView) view.findViewById(R.id.autoCPDP);

        //=====================Submit-buttons from here=========

        Button RLNButton = view.findViewById(R.id.RLNButton);
        Button RNButton = view.findViewById(R.id.RNButton);
        Button RCButton = view.findViewById(R.id.RCButton);
        Button RCDPButton = view.findViewById(R.id.RCDPButton);
        Button RPButton = view.findViewById(R.id.RPButton);
        Button RPDPButton = view.findViewById(R.id.RPDPButton);
        Button RAButton = view.findViewById(R.id.RAButton);
        Button RADPButton = view.findViewById(R.id.RADPButton);

        //=======================================================

        RLNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int lotNum=0;
                    if(!RLNNum.getText().toString().isEmpty()){
                        lotNum = Integer.parseInt(RLNNum.getText().toString());
                    }
                    else{
                        Toast.makeText(getContext(),"Enter a lot number",Toast.LENGTH_LONG).show();
                    }
                    boolean lnExist=false;
                    for(ItemCollection i:items) {
                        if (i.getLotNumber() == lotNum) {
                            lnExist = true;
                            break;
                        }
                    }
                    if(!lnExist){
                        Toast.makeText(getContext(),"Nonexistent lot number",Toast.LENGTH_LONG).show();
                        return;
                    }

                    Document document = new Document(setPdf());

                    for(ItemCollection i:items){
                        if(i.getLotNumber()==lotNum){
                            createPdf(document,lotNum,i.getName(),i.getCategory(),
                                    i.getPeriod(),i.getDescription(),"Media comes Here");
                            break;
                        }
                    }

                    document.close();
                    Toast.makeText(getContext(),"PDF saved to downloads folder",Toast.LENGTH_LONG).show();
                }
                catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        RNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String name = "foo";
                    if(!RNText.getText().toString().isEmpty()){
                        name = RNText.getText().toString();
                    }
                    else{
                        Toast.makeText(getContext(),"Enter a name",Toast.LENGTH_LONG).show();
                    }
                    boolean nmExist=false;
                    for(ItemCollection i:items) {
                        if (i.getName().equals(name)) {
                            nmExist = true;
                            break;
                        }
                    }
                    if(!nmExist){
                        Toast.makeText(getContext(),"Nonexistent Name",Toast.LENGTH_LONG).show();
                        return;
                    }

                    Document document = new Document(setPdf());

                    for(ItemCollection i:items){
                        if(i.getName().equals(name)){
                            createPdf(document,i.getLotNumber(),name,i.getCategory(),
                                    i.getPeriod(),i.getDescription(),"Media comes Here");
                        }
                    }

                    document.close();
                    Toast.makeText(getContext(),"PDF saved to downloads folder",Toast.LENGTH_LONG).show();
                }
                catch(FileNotFoundException e){
                    throw new RuntimeException(e);
                }
            }
        });

        RCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String category = "foo";
                    if(!autoCC.getText().toString().isEmpty()){
                        category = autoCC.getText().toString();
                    }
                    else{
                        Toast.makeText(getContext(),"Select a category",Toast.LENGTH_LONG).show();
                    }
                    boolean ccExist=false;
                    for(ItemCollection i:items) {
                        if (i.getCategory().equals(category)) {
                            ccExist = true;
                            break;
                        }
                    }
                    if(!ccExist){
                        Toast.makeText(getContext(),"Category does not exist in database",Toast.LENGTH_LONG).show();
                        return;
                    }

                    Document document = new Document(setPdf());

                    for(ItemCollection i:items){
                        if(i.getCategory().equals(category)){
                            createPdf(document,i.getLotNumber(),i.getName(),category,
                                    i.getPeriod(),i.getDescription(),"Media comes Here");
                        }
                    }

                    document.close();
                    Toast.makeText(getContext(),"PDF saved to downloads folder",Toast.LENGTH_LONG).show();
                }
                catch(FileNotFoundException e){
                    throw new RuntimeException(e);
                }
            }
        });

        RCDPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String category = "foo";
                    if(!autoCC.getText().toString().isEmpty()){
                        category = autoCC.getText().toString();
                    }
                    else{
                        Toast.makeText(getContext(),"Select a category",Toast.LENGTH_LONG).show();
                    }
                    boolean ccExist=false;
                    for(ItemCollection i:items) {
                        if (i.getCategory().equals(category)) {
                            ccExist = true;
                            break;
                        }
                    }
                    if(!ccExist){
                        Toast.makeText(getContext(),"Category does not exist in database",Toast.LENGTH_LONG).show();
                        return;
                    }

                    Document document = new Document(setPdf());

                    for(ItemCollection i:items){
                        if(i.getCategory().equals(category)){
                            createPdf(document,-901232342,"","",
                                    "",i.getDescription(),"Media comes Here");
                        }
                    }

                    document.close();
                    Toast.makeText(getContext(),"PDF saved to downloads folder",Toast.LENGTH_LONG).show();
                }
                catch(FileNotFoundException e){
                    throw new RuntimeException(e);
                }
            }
        });

        RPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String period = "foo";
                    if(!autoCP.getText().toString().isEmpty()){
                        period = autoCP.getText().toString();
                    }
                    else{
                        Toast.makeText(getContext(),"Select a period",Toast.LENGTH_LONG).show();
                    }
                    boolean cpExist=false;
                    for(ItemCollection i:items) {
                        if (i.getPeriod().equals(period)) {
                            cpExist = true;
                            break;
                        }
                    }
                    if(!cpExist){
                        Toast.makeText(getContext(),"Period does not exist in database",Toast.LENGTH_LONG).show();
                        return;
                    }

                    Document document = new Document(setPdf());

                    for(ItemCollection i:items){
                        if(i.getPeriod().equals(period)){
                            createPdf(document,i.getLotNumber(),i.getName(),i.getCategory(),
                                    period,i.getDescription(),"Media comes Here");
                        }
                    }

                    document.close();
                    Toast.makeText(getContext(),"PDF saved to downloads folder",Toast.LENGTH_LONG).show();
                }
                catch(FileNotFoundException e){
                    throw new RuntimeException(e);
                }
            }
        });

        RPDPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String period = "foo";
                    if(!autoCPDP.getText().toString().isEmpty()){
                        period = autoCPDP.getText().toString();
                    }
                    else{
                        Toast.makeText(getContext(),"Select a period",Toast.LENGTH_LONG).show();
                    }
                    boolean ccdpExist=false;
                    for(ItemCollection i:items) {
                        if (i.getPeriod().equals(period)) {
                            ccdpExist = true;
                            break;
                        }
                    }
                    if(!ccdpExist){
                        Toast.makeText(getContext(),"Period does not exist in database",Toast.LENGTH_LONG).show();
                        return;
                    }

                    Document document = new Document(setPdf());

                    for(ItemCollection i:items){
                        if(i.getPeriod().equals(period)){
                            createPdf(document,-901232342,"","",
                                    "",i.getDescription(),"Media comes Here");
                        }
                    }

                    document.close();
                    Toast.makeText(getContext(),"PDF saved to downloads folder",Toast.LENGTH_LONG).show();
                }
                catch(FileNotFoundException e){
                    throw new RuntimeException(e);
                }
            }
        });

        RAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Document document = new Document(setPdf());

                    for(ItemCollection i:items){
                        createPdf(document,i.getLotNumber(),i.getName(),i.getCategory(),
                                    i.getPeriod(),i.getDescription(),"Media comes Here");
                    }

                    document.close();
                    Toast.makeText(getContext(),"PDF saved to downloads folder",Toast.LENGTH_LONG).show();
                }
                catch(FileNotFoundException e){
                    throw new RuntimeException(e);
                }
            }
        });

        RADPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Document document = new Document(setPdf());

                    for(ItemCollection i:items){
                        createPdf(document,-901232342,"","",
                                "",i.getDescription(),"Media comes Here");
                    }

                    document.close();
                    Toast.makeText(getContext(),"PDF saved to downloads folder",Toast.LENGTH_LONG).show();
                }
                catch(FileNotFoundException e){
                    throw new RuntimeException(e);
                }
            }
        });
        return view;
    }

    //adds pages given the document and data
    private void createPdf(Document reportDocument, int lotnumber, String name, String category,
                           String period, String itemDescription, String media) throws FileNotFoundException{
        Document document = reportDocument;

        String lotNumber = Integer.toString(lotnumber);
        if(lotnumber==-901232342){
            lotNumber = "";
        }
        String description = itemDescription;
        if(description.isEmpty()){
            description = "There is no description for this item";
        }

        Paragraph lotNumberP = new Paragraph(lotNumber);
        document.add(lotNumberP.setBold().setFontSize(42));

        Paragraph nameP = new Paragraph(name);
        document.add(nameP.setFontSize(30));

        Paragraph categoryP = new Paragraph(category);
        document.add(categoryP.setFontSize(30));

        Paragraph periodP = new Paragraph(period);
        document.add(periodP.setFontSize(30));

        Paragraph descriptionP = new Paragraph(description);
        document.add(descriptionP.setFontSize(20).setItalic());

        Paragraph mediaP = new Paragraph(media);
        document.add(mediaP);

        document.add(new AreaBreak());
    }

    //creates a canvas for document creation
    private PdfDocument setPdf() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"TAAM_Report.pdf");
        PdfWriter writer = new PdfWriter(file);
        return new PdfDocument(writer);
    }
}