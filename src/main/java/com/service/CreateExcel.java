package com.service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.core.util.Base64;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class CreateExcel {
	// filepath=/usr/local/tomcat9/webapps/CarrotRuleAngularExcel/DatasourceExcelFiles/
	static ResourceBundle bundleststic = ResourceBundle.getBundle("config");
	public static void main(String args[]) {
		System.out.println("test= "+bundleststic.getString("Serverip"));
		
	}
	
	public JSONObject createExcel(JSONArray fieldslist, String datasourcename,String email) {
		HSSFWorkbook workbook = null;
		FileOutputStream fileOut = null;
		String filrpath = "";
		String fileurl="";
		String filename="";
		JSONObject res= new JSONObject();
		try {
			// String filename = "D:/NewExcelFile.xls" ;
			filrpath = bundleststic.getString("filepath")+email.replace("@", "_")+"_" + datasourcename + ".xls";
			if (fieldslist.length() > 0 && fieldslist != null) {
//				System.out.println("in if");
				workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("FirstSheet");

				HSSFRow rowhead = sheet.createRow((short) 0);
				for (int i = 0; i < fieldslist.length(); i++) {
					Cell keycell = rowhead.createCell(i);
//					System.out.println(fieldslist.getString(i));
					keycell.setCellValue(fieldslist.getString(i));
				}
				fileOut = new FileOutputStream(filrpath);
				workbook.write(fileOut);
				System.out.println("Done");
				String addr = bundleststic.getString("Serverip"); //https://bluealgo.com:8088/
				//http://bluealgo.com:8087/CarrotRuleAngularExcel/UploadOutputBuilder/sampleuser_gmail.com_PromotionDemonstration.xlsx
				fileurl=addr+"CarrotRuleAngularExcel/DatasourceExcelFiles/"+email.replace("@", "_")+"_"+datasourcename+".xls";
				filename= email.replace("@", "_")+"_" + datasourcename + ".xls";
				res.put("filename", filename);
				res.put("fileurl", fileurl);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				fileOut.close();
				// workbook.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return res;
	}
	
	
}
