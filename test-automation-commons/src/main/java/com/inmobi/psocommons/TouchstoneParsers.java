package com.inmobi.psocommons;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class TouchstoneParsers {

	String LOGTAG = "+ [TouchstoneParsers]: ";
	public static void main(String[] args) throws Exception {

		String xmlFile 		= "/home/archana/INMOBI/github/org-archana/touchstone/Unification/src/test/resources/com/inmobi/qa/" + "/dwhfanout/expectedResults/demandfact.xml";
		//HashMap<String, HashMap<String, String>> map = TouchstoneParsers.readXml(xmlFile,"demandfact");
		//System.out.println(map.toString());
	}


	/*  ***************************************************************************************************************
	 * Method: readPropsFile()
	 * Action: To read grouped config properties file
	 * Eg: file format:
	 * 					[PATHS]
	 * 					path1=/dir
	 * 
	 * 					[VARS]
	 * 					path1=/dir1/dir2
	 * 
	 * Author: Mukthar.Ahmed@inmobi.com
	 * Date: 25-Dec'12
	 * 
	 ************************************************************************************************************ */

	public HashMap<String, HashMap<String, String>> readPropsFile (File cfgFile) {
		System.out.println(LOGTAG + " PARSE CONFIG/PROPERTIES FILE: " + cfgFile);

		String rootKey = null;
		HashMap<String, HashMap<String, String>> configHash = new HashMap<String, HashMap<String,String>>();
		RegExpCollection regEx = new RegExpCollection();

		try {
			BufferedReader br = new BufferedReader(new FileReader(cfgFile));
			String line;

			while ( ( line = br.readLine() ) != null )  {
				line.trim();	// remove trailing characters

				// ^, $ marks the begining and end of line and \A, \z marks start and end of input 
				// with \G marking end of previous match 
				if (!line.matches("^\\s*$") ) {			// skip blank lines

					if ( line.matches("\\A\\[.*\\]\\z") ) {
						rootKey = regEx.getPropKey(line);
						configHash.put(rootKey, new HashMap<String, String>());	// just create a root/group key with blank/empty hashmap

					} else if ( (!line.matches("\\A\\[.*\\]\\z") ) && ( configHash.containsKey(rootKey) ) ) {
						String[] keyValPair = line.split("=");	// split line by ' = ' and populate the hash based on the root key
						configHash.get(rootKey).put(keyValPair[0], keyValPair[1]);	

					}

				} // end if

			} // end while

		} catch (Exception e) {
			System.out.println(e);

		}

		return configHash;
	} // end readPropsFile()


	/*   ******************************************************************************************************************
	 * Method: readExcel()
	 * Action: To read excel file
	 * 
	 * Author: archana.c@inmobi.com
	 * Date: 17-Jan'13
	 * 
	 *************************************************************************************************************** */

	public static HashMap<String, HashMap<String, String>> readExcel(String excelFile, String sheetName, String colName) {
		HashMap<String, HashMap<String, String>> schemaFromXls = new HashMap<String, HashMap<String,String>>();

		try {  

			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelFile));  
			HSSFSheet sheet 		= workbook.getSheet(sheetName);
			System.out.println("TOTAL ROWS: " + sheet.getLastRowNum());

			HSSFRow headingRow 	= sheet.getRow(0);
			Iterator cellIter 	= headingRow.cellIterator();  
			int reqCol=-1;

			while(cellIter.hasNext()){  
				HSSFCell myCell = (HSSFCell) cellIter.next();  
				if(myCell.getStringCellValue().equals(colName)){
					reqCol = myCell.getCellNum();
					System.out.println("READING COLUMN # : " + reqCol + "\n");
				}
			} 

			if(reqCol == -1){
				System.out.println("Specified column not found");
				return schemaFromXls;
			}

			for (int row=1; row <= sheet.getLastRowNum(); row++) {
				HSSFRow rowData = sheet.getRow(row);

				if(rowData != null){
					HSSFCell myCell = rowData.getCell((short)reqCol);

					if(myCell != null){

						String cellvalue 		= myCell.getStringCellValue(); 

						HSSFCellStyle style 	= myCell.getCellStyle();
						HSSFFont font 		= workbook.getFontAt(style.getFontIndex());
						boolean isbold 		= font.getBoldweight() > HSSFFont.BOLDWEIGHT_NORMAL;

						if(cellvalue != null) {
							schemaFromXls.put(cellvalue, new HashMap<String, String>());	// just create a root/group key with blank/empty hashmap
							schemaFromXls.get(cellvalue).put("COLNUM",Integer.toString(row) );	
							schemaFromXls.get(cellvalue).put("ISBOLD",Boolean.toString(isbold));

						} else{
							System.out.println("col null");

						} // if-else (cellval)

					} // 

				}

			}

		} catch (Exception e) {  
			e.printStackTrace();

		} 

		return schemaFromXls;
	}
	
//	/*   ******************************************************************************************************************
//	 * Method: readXml()
//	 * Action: To read excel file
//	 * 
//	 * Author: archana.c@inmobi.com
//	 * Date: 17-Jan'13
//	 * 
//	 *************************************************************************************************************** */	
//	public static HashMap<String, HashMap<String, String>> readXml(String fileName, String moduleName){
//		HashMap<String, HashMap<String, String>> map = new HashMap<String,HashMap<String, String>>();
//
//		try {
//
//			Document doc = XMLParser.getDocument(fileName);
//			doc.getDocumentElement().normalize();
//
//			NodeList module 	= doc.getElementsByTagName(moduleName);
//			NodeList testcases 	= XMLParser.getChildNodes(module.item(0));
//			NodeList testcase	= XMLParser.getChildNodes(testcases.item(0));
//
//
//			for(int j=0; j<testcase.getLength(); j++) {
//				NodeList dimensions = testcase.item(j).getChildNodes();
//				String testNo 		= XMLParser.getAttribute(testcase.item(j),"id");
//				map.put("TEST"+testNo, new HashMap<String,String>());
//
//
//				for(int i=0; i<dimensions.getLength(); i++) {
//					//						map.put("TEST"+testNo, new HashMap<String,String>());
//					if(dimensions.item(i).getNodeType() == Node.ELEMENT_NODE){
//						map.get("TEST"+testNo).put(dimensions.item(i).getNodeName(), dimensions.item(i).getTextContent());
//
//					}
//				}
//			}
//
//			//System.out.println(map.toString());
//
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//
//		return map;
//	}

}	// end TouchstoneParsers
