package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import javax.net.ssl.HttpsURLConnection;

import domain.Food;
import domain.User;
import util.enums.Labels;

public class ChatGptSummaryService {
	private final String url = "https://api.openai.com/v1/chat/completions";
	private final String apiKey = ""; //텍스트파일 저장 or 키 값 입력 
	private final String model = "gpt-5-nano";
	public String getApiKey() {
		return apiKey;
	}
	public String chatGptAsk(String prompt) {
			
			
			try {
				//URL 커넥션 필요
				URL obj = new URL(url);
				HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Authorization", "Bearer " + apiKey);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setDoOutput(true);
				
				// TLS 버전 강제 (필요시)
				System.setProperty("https.protocols", "TLSv1.2");
				
				// api로 쏠 request body 작성
				String body = "{"
				        + "\"model\": \"" + model + "\","
				        + "\"messages\": ["
				        + "  {\"role\": \"user\", \"content\": \"" 
				        + prompt.replace("\"", "\\\"").replace("\n", "\\n") + "\"}"
				        + "]"
				        + "}";
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
				
				return extractMessageFromJSONResponse(response.toString());
				
			}catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				throw new RuntimeException(e);
			}
			return "응답 에러";

	}
	public String askGptWithUserPrompt(User user) {
		// 주간 요약
		LocalDate today = LocalDate.now();
		StringBuffer weeklyInfo = new StringBuffer("");
		weeklyInfo.append("유저 아이디 : " +user.getUserId() + "\n");
		weeklyInfo.append("오늘은 " + today.getMonth().getValue() + "월" + today.getDayOfMonth() + "일" + "\n");
		final int week = 7;
        int todayCalories;
        int todayProtein;
        int targetCalories;
        int targetProtein;
        int currentWater;
        int targetWater;	        
		for(int i = 0 ; i < week ; i++) {
			//정보 있는지 체크
			if(user.getGoalHistory().get(today.plusDays(i-6)) == null || user.getEatingHistory().get(today.plusDays(i-6)) == null ) {
        		weeklyInfo.append(today.plusDays(i-6).toString()+" 정보 없음.\n");
        		continue;
        	}
	        //문제 없다면
        	todayCalories = 0 ;
            todayProtein = 0 ;
            targetCalories = user.getGoalHistory().get(today.plusDays(i-6)).getTargetCalories() ;
            targetProtein = user.getGoalHistory().get(today.plusDays(i-6)).getTargetProtein();
            currentWater = user.getGoalHistory().get(today.plusDays(i-6)).getCurrentWater();
            targetWater = user.getGoalHistory().get(today.plusDays(i-6)).getTargetWater() ;
            for(Food food : user.getEatingHistory().get(today.plusDays(i-6))) {            	
            	todayCalories += food.getCalorie();
            	todayProtein += food.getProtein();
            }
            //일 정보 넣어주기
            weeklyInfo.append(today.plusDays(i-6).toString() + "의 유저 개인 달성 정보 -");
            weeklyInfo.append(Labels.TARGETCALORIES.getValue() + targetCalories);
            weeklyInfo.append(Labels.CHECKCALORIES.getValue() + todayCalories);
            weeklyInfo.append(Labels.TARGETPROTEIN.getValue() + targetProtein);
            weeklyInfo.append(Labels.CHECKPROTEIN.getValue() + todayProtein);
            weeklyInfo.append(Labels.TARGETWATER.getValue() + targetWater);
            weeklyInfo.append(Labels.CHECKWATER.getValue() + currentWater);
            weeklyInfo.append("\n");

		}
		System.out.println(weeklyInfo);
		return chatGptAsk(weeklyInfo.toString()+"\n위의 정보로 이번 주 요약과 다음 주 식단 계획 한 줄로 정리해줘");
	}

   private static String extractMessageFromJSONResponse(String response) {
       int start = response.indexOf("content")+ 11;

       int end = response.indexOf("\"", start);

       return response.substring(start, end);

   }
}
