package com.nishanth.upload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Controller
public class UploadFileRestController {

	private static final String fileLocation = "/Users/Nishanth/Downloads/";
	@RequestMapping( value="/")
	public @ResponseBody String welcomePage()
	{
		return "Welcome to Spring File Upload Service Application Rest Layer";
	}

	@RequestMapping(value="/upload", method=RequestMethod.POST, produces={"application/json"})
	public @ResponseBody Ack handleFileUpload(@RequestParam("fileName") String fileName, 
												@RequestParam("uniqueId") String uniqueId,
												@RequestParam("file") MultipartFile file, 
												HttpServletRequest httpReq, HttpServletResponse httpResp)
	{
		Ack ack = null;
		try {
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				InputStream stream = new ByteArrayInputStream(bytes); 
				writeToFile(stream, fileName);
				System.out.println("You successfully uploaded " + fileName + "!");

				ack = new Ack();
				ack.setUniqueId(uniqueId);
			} else {
				ack = new Ack();
				ack.setErrCode("Empty_File");
				ack.setErrMessage("Uploaded file was empty");
				return ack;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception = "+e.getMessage());
			e.printStackTrace();
		}
		return ack;
	}
	
	private void writeToFile(InputStream inputStream, String fileName) {
		OutputStream out = null;
		try {
			String fileDestination = fileLocation + fileName;
			int read = 0;
			byte[] buffer = new byte[1024];
			out = new FileOutputStream(new File(fileDestination), true);

			while ((read = inputStream.read(buffer)) > 0) {
				out.write(buffer, 0, read);
			}

		} catch (Exception e) {
			System.out.println("Error occurred while writing the file to location, "+e.getMessage());
		}
	}

	@Autowired
	@SuppressWarnings("rawtypes")
	private void setRequestMappingHandlerAdapter(RequestMappingHandlerAdapter mappingManager) {
		if(mappingManager == null) {
			return;
		}
		for( HttpMessageConverter conv : mappingManager.getMessageConverters()) {
			if( conv instanceof MappingJackson2HttpMessageConverter) {
				( (MappingJackson2HttpMessageConverter) conv).setObjectMapper(new JacksonCustomObjectMapper());
			}
		}
	}

}
