package service;

import java.util.Comparator;
import java.util.Map;

import domain.EatingOutFood;


/*
 * 점수 정렬 기준 제공 클래스
 * 점수는 내림차순 정렬
 * 점수가 같을 시 가격 오름차순 정렬
 * 가격이 같을 시 음식 이름 사전순 정렬
 */
public class EatingOutFoodScoreComparator implements Comparator<EatingOutFood>{
	
	private Map<EatingOutFood, Double> scoreMap;
	
	public EatingOutFoodScoreComparator(Map<EatingOutFood, Double> scoreMap) {
		this.scoreMap = scoreMap;
	}
	
	@Override
	public int compare(EatingOutFood eatingOutFood1, EatingOutFood eatingOutFood2) {
		//점수 비교
		int byScoreDesc = Double.compare(scoreMap.get(eatingOutFood2), scoreMap.get(eatingOutFood1));
		if(byScoreDesc != 0) return byScoreDesc;
		
		//가격 비교
		int byPriceAsc = Integer.compare(eatingOutFood1.getPrice(), eatingOutFood2.getPrice());
		if(byPriceAsc != 0) return byPriceAsc;
		
		return eatingOutFood1.toString().compareTo(eatingOutFood2.toString());
	}
	

}
