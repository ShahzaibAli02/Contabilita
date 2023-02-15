package com.gilbertig.contabilita;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.widget.Toast;


import com.gilbertig.contabilita.model.Data;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class XlsSheetManager extends ContextWrapper
{


    HSSFWorkbook workbook;
    HSSFSheet sheet;
    int rowCount=1;
    public XlsSheetManager(Context base)
    {
        super(base);

        if (alreadyExistWorkBook()) {
            try {

                workbook = new HSSFWorkbook(getInputStream());
            } catch (Exception e) {
                createNewBook();
            }
        } else {
            createNewBook();
        }


    }

    private void createNewBook() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("Sheet No 1");
        addHeader();
    }

    private void  addHeader()
    {

        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLUE.index);
        font.setBoldweight((short) 35);
        /**
         *Id,Art.,Descrizione,Lunghezza,Larghezza,Altezza,U.M.,Tot.,Descrizione,detrazioni,Lunghezza,Larghezza,Altezza,U.M.,Q.tà,Prezzo,Importo
         */

        String[] headerVals=new String[]{"Id","Art.","Descrizione","Lunghezza","Larghezza","Altezza","U.M.","Tot.","Descrizione detrazioni","Lunghezza","Larghezza","Altezza","U.M.","Q.tà","Prezzo","Importo"};
        HSSFFont font2 = workbook.createFont();
        font2.setColor(HSSFColor.BLACK.index);
        font2.setBoldweight((short) 35);

        HSSFRow row = sheet.createRow(rowCount);
        final HSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setFont(font2);
        HSSFCell cellA=row.createCell(0);
        cellA.setCellStyle(cellStyle);
        cellA.setCellValue("DATE");

        cellA=row.createCell(1);
        cellA.setCellStyle(cellStyle);
        cellA.setCellValue(new SimpleDateFormat("dd-MM-yyyy hh:mm:aa", Locale.ENGLISH).format(new Date()));



        int columnindex=-1;
        HSSFRow rowA = sheet.createRow(++rowCount);
        for(String val:headerVals)
        {
            HSSFCell cell=rowA.createCell(++columnindex);
            cell.getCellStyle().setFont(font);
            cell.setCellValue(val);
        }


    }
    public  void  addRow(Data data)
    {
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight((short) 35);
        final HSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setFont(font);

        int columnindex=-1;
        HSSFRow rowA = sheet.createRow(++rowCount);

        HSSFCell cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getId());

        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getArt());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getDescrizione());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getMLunghezza());



        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getMLunghezza2());



        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getMAltezza());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getUM1());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getMTotal());



        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getDescrizione2());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getMADLunghezza());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getMADLunghezza2());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getMADAltezza());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getUM2());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getQta());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getPrezzo());


        cell=rowA.createCell(++columnindex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.getImporto());



    }


    String file_name = "contabilita.xls";
    public FileInputStream getInputStream() throws FileNotFoundException {
        if(getFile()==null)
            throw  new FileNotFoundException();

        return new FileInputStream(getFile());

    }

    private File getFile() {
        File str_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!str_path.exists() && !str_path.mkdirs()) {
            return null;
        }
        return new File(str_path, file_name);
    }

    public boolean alreadyExistWorkBook()
    {
        return getFile()!=null && getFile().exists();
    }
    public  String save()
    {

        FileOutputStream fos = null;
        File file=null ;
        try {



            File str_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            boolean isCreated=false;
            if(!str_path.exists() && !str_path.mkdirs())
            {
                showMessage("Failed to create folder ");
                return null;
            }

            file = new File(str_path,  file_name);
            if(!file.exists() && ! file.createNewFile())
            {
                showMessage("Failed to create xls  file");
                return null;
            }
            fos = new FileOutputStream(file);
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        showMessage("Excel Sheet Saved At : "+ file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
