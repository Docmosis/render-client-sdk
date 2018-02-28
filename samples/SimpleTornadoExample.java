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
import java.util.Date;

import com.docmosis.render.RenderRequest;
import com.docmosis.render.RenderRequestFactory;
import com.docmosis.render.RenderResponse;
import com.docmosis.render.RendererException;
import com.google.gson.Gson;


/**
 * This example invokes a render using a local Docmosis Tornado server.
 * The code is the same as for using the public cloud, but has a different URL and
 * doesn't require an access key.
 * It renders the built-in WelcomeTemplate.doc template into a PDF which is
 * saved to the local file system.  The code also populates the "message" field
 * within the template.
 * 
 * How to use:
 * 
 *  1) Install, configure and run Docmosis Tornado
 *  2) set/confirm the TORNADO_RENDER_URL setting is correct
 *  3) run the class and see the result
 *  
 * You can find a lot more about the Docmosis rendering capability by reading
 * the Web Services Guide and the Docmosis Template guide in the support area
 * of the Docmosis web site (http://www.docmosis.com/support) 
 *  
 */public class SimpleTornadoExample 
{
   
	private static final String TORNADO_RENDER_URL = "http://localhost:8080/rs/render";	
	
	private static final String TEMPLATE_NAME = "WelcomeTemplate.doc";
	
	// The output format we want to produce (pdf, doc, odt and more exist).
	private static final String OUTPUT_FORMAT = "pdf";

	// The name of the file we are going to write the document to.
	private static final String OUTPUT_FILE = "output." + OUTPUT_FORMAT;
	
	
	public static void main(String args[]) throws IOException, RendererException {
	    
		RenderRequestFactory.setDefaultUrl(TORNADO_RENDER_URL);		
        final RenderRequest req = RenderRequestFactory.getRequest();

		final Data data = new Data();
		data.setMessage("Hello at:" + new Date());

		req.setTemplateName(TEMPLATE_NAME);
		req.setOutputName(OUTPUT_FILE);
		req.setData(new Gson().toJson(data));
		RenderResponse response = req.execute();
		try {

			if (response.hasSucceeded()) {
				// great - render succeeded.

				// lets get the document out and put it in a file
				File f = new File("testDocument." + OUTPUT_FORMAT);
				response.sendDocumentTo(f);
				System.out.println("Written:" + f.getAbsolutePath());

			} else {
				// something went wrong, tell the user
				System.err.println("Render failed: status="
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
	
	public static class Data {
		private String message;
		
		public void setMessage(String msg) 
		{
			this.message = msg;
		}
		
		public String getMessage() {
			return message;
		}

	}
}
