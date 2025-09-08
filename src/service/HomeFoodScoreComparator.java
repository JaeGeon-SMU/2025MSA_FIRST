package service;

import java.util.Comparator;
import java.util.Map;

import domain.HomeFood;

/*
 * 점수 정렬 기준 제공 클래스
 * 점수는 내림차순 정렬
 * 점수가 같을 시 유통기한 오름차순 정렬
 * 유통기한이 같을 시 음식 이름 사전순 정렬
 */
public class HomeFoodScoreComparator implements Comparator<HomeFood>{
	
	private Map<HomeFood, Double> scoreMap;
	
	public HomeFoodScoreComparator(Map<HomeFood, Double> scoreMap) {
		this.scoreMap = scoreMap;
	}
	
	@Override
	public int compare(HomeFood homeFood1, HomeFood homeFood2) {
		//점수 비교
		int byScoreDesc = Double.compare(scoreMap.get(homeFood2), scoreMap.get(homeFood1));
		if(byScoreDesc != 0) return byScoreDesc;
		
		//유통기한 비교
		int byExpAsc = homeFood1.getExpireDate().compareTo(homeFood2.getExpireDate());
		if(byExpAsc != 0) return byExpAsc;
		
		return homeFood1.toString().compareTo(homeFood2.toString());
	}

}
