package domain;


//formatting해서 사용하기 위한 label들
public enum Labels{
	AGE("나이 : "),
	HEIGHT("현재 키 : "),
	CURRENTWETIGHT("현재 체중 : "),
	CURRENTWATTERINTAKE("현재 물 섭취량 : "),
	TARGETWATER("목표 물 섭취량 : "),
	TARGETWEIGHT("목표 체중 : "),
	TARGETPROTEIN("목표 단백질 섭취량 : "),
	TARGETCALORIES("목표 칼로리 섭취량 : "),
	CHECKCALORIES("오늘 목표로 해야 할 칼로리 섭취량 : "),
	CHECKPROTEIN("오늘 목표로 해야 할 단백질 섭취량 : ");
	
	
	private final String value;
   
	Labels(String value){
		this.value = value;
	}
    public String getValue(){
        return this.value;
    }
	
}
