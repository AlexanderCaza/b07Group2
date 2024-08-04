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
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ReportFragment extends Fragment {

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

        //=====================Submit-buttons from here=========

        Button RLNButton = view.findViewById(R.id.RLNButton);
        Button RNButton = view.findViewById(R.id.RNButton);
        Button RCButton = view.findViewById(R.id.RCButton);
        Button RCDPButton = view.findViewById(R.id.RCDPButton);
        Button RPButton = view.findViewById(R.id.RPButton);
        Button RPDPButton = view.findViewById(R.id.RPDPButton);
        Button RAButton = view.findViewById(R.id.RAButton);
        Button RADPButton = view.findViewById(R.id.RADPButton);

        RLNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPdf();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return view;
    }

    private void createPdf() throws FileNotFoundException{
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"report.pdf");
        OutputStream outputStream  = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        Text text1 = new Text("Bold ").setBold();
        Text text2 = new Text("Ital ").setItalic();
        Text text3 = new Text("Under ").setUnderline();

        Paragraph paragraph1 = new Paragraph();
        paragraph1.add(text1).add(text2).add(text3);

        document.add(paragraph1);

        Paragraph paragraph2 = new Paragraph("This is paragraph 2");

        document.add(paragraph2);

        document.close();
        Toast.makeText(getContext(),"Pdf saved to downloads folder",Toast.LENGTH_LONG).show();
    }
}