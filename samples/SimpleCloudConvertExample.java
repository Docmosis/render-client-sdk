/*
 *   Copyright 2012 Docmosis.com or its affiliates.  All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 *   or in the LICENSE file accompanying this file.
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */


import java.io.File;
import java.io.IOException;

import com.docmosis.convert.ConverterException;
import com.docmosis.convert.ConverterRequest;
import com.docmosis.convert.ConverterResponse;

/**
 * 
 * This example connects to the public Docmosis cloud server and 
 * converts the given document into a PDF which is then 
 * saved to the local file system.  
 * 
 * How to use:
 * 
 *  1) sign up to the Docmosis Cloud Services
 *  2) plug your ACCESS_KEY into this class
 *  3) run the class and see the result
 *  
 * You can find a lot more about the Docmosis conversion capability by reading
 * the Web Services Guide and the Docmosis Template guide in the support area
 * of the Docmosis web site (http://www.docmosis.com/support) 
 *  
 */
public class SimpleCloudConvertExample
{

	// you get an access key when you sign up to the Docmosis cloud service
	private static final String ACCESS_KEY = "XXX";
	// Set the name of the output file to create
	private static final String OUTPUT_FILE_NAME = "myResult.xpdf";
	// Set the path to the file you want to convert
	private static final String FILE_TO_CONVERT = "c:/example/myDocument.doc";

	private static final String URL = "https://dws2.docmosis.com/services/rs/convert";

	public static void main(String args[]) throws ConverterException,
			IOException
	{
		
		if (ACCESS_KEY.equals("XXX")) {
			System.err.println("Please set your ACCESS_KEY");
			System.exit(1);
		}

		ConverterRequest req = new ConverterRequest(URL, ACCESS_KEY);
		
		File convertFile = new File(FILE_TO_CONVERT);
		req.setFileToConvert(convertFile);
		req.setOutputName(OUTPUT_FILE_NAME);

		ConverterResponse response = req.execute();
		try {

			if (response.hasSucceeded()) {
				// great - convert succeeded.

				// lets get the document out and put it in a file
				File f = new File(OUTPUT_FILE_NAME);
				response.sendDocumentTo(f);
				System.out.println("Written:" + f.getAbsolutePath());

			} else {
				// something went wrong, tell the user
				System.err.println("Convert failed: status="
						+ response.getStatus()
						+ " shortMsg="
						+ response.getShortMsg()
						+ ((response.getLongMsg() == null) ? "" : " longMsg="
								+ response.getLongMsg()));
			}
		} finally {
			response.cleanup();
		}
	}


}
