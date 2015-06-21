package com.nishanth.upload;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import junit.framework.TestCase;

public class UploadFileRestControllerTest extends TestCase {

	private static final String TARGET_URL = "http://localhost:8080/upload";
	
	HttpHeaders headers = new HttpHeaders();
	RestTemplate template = new RestTemplate();
	FileSystemResource fileContent = new FileSystemResource("/Users/Nishanth/Downloads/simplebot.py");
	LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
	
	public void testCase()
	{
		try
		{	
			map.add("file", fileContent);
			map.add("fileName", "FILENAME1");
			map.add("uniqueId", "343567");
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
					map, headers);
			ResponseEntity<String> response = template.exchange(TARGET_URL, HttpMethod.POST, requestEntity,
					String.class);
			
			if(response.getStatusCode().value() == 200){
				String ack = response.getBody().toString();
				System.out.println("RESPONSE  = "+ack);
			}else {
				System.out.println("Error CODE  = " +response.getStatusCode());
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
