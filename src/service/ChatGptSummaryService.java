package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// https://rollbar.com/blog/how-to-use-chatgpt-api-with-java/?utm_source=chatgpt.com
public class ChatGptSummaryService {
	public static String chatGptAsk(String prompt) {
			String url = "https://api.openai.com/v1/chat/completions";
			String apiKey = ""; //텍스트파일 저장 or 키 값 입력 
			String model = "gpt-5-nano";
			
			try {
				//URL 커넥션 필요
				URL obj = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Authorization", "Bearer " + apiKey);
				connection.setRequestProperty("Content-Type", "application/json");
				
				// api로 쏠 request body 작성
				String body = "{\"model\": \"" + model + "\", \"messages\":[{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
				// io입출력으로 사용할 것이냐?
				connection.setDoOutput(true); 
				// 입출력 전용 Stream
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				//요청 보내기
				writer.write(body);
				writer.flush();
				writer.close();
				
				// Response from ChatGPT
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				
				StringBuffer response = new StringBuffer();
				while( (line = br.readLine()) != null ) {
					response.append(line);
				}
				br.close();
				
				return extactMessageFromJSONResponse(response.toString());
				
			}catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				throw new RuntimeException(e);
			}
			return "응답 에러";

	}

	private static String extactMessageFromJSONResponse(String response) {
		int start = response.indexOf("content") + 11;
		int end = response.indexOf("\"", start);
		
		return response.substring(start, end);
	}

}
