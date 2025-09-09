# 2025MSA_FIRST

1조 프로젝트 
    
    프로젝트명 : 나, 기현을 부탁해

    # Trouble Shooting - Factory Pattern 적용 🍳

## 📌 프로젝트 소개
본 프로젝트는 **냉장고 관리 및 식단 추천 애플리케이션**을 개발하면서,  
음식 객체 생성 과정에 **팩토리 패턴(Factory Pattern)**을 적용한 예시를 다룹니다.  

목표는 **객체 생성 책임을 분리**하여 코드의 응집도를 높이고,  
새로운 음식 타입이 추가될 때 **유연하고 확장 가능**한 구조를 만드는 것입니다.  

---

## 🏗️ 클래스 다이어그램
팩토리 패턴 적용 전후의 구조를 비교합니다.

```mermaid
classDiagram
  class Food {
    - id : int
    - name : String
    - calorie : int
    - protein : int
    - allergy : List
  }

  class HomeFood {
    - expireDate : LocalDate
    - reorderPoint : int
    - sortReorderPoint : int
  }

  class EatingOutFood {
    - servingSize : int
  }

  Food <|-- HomeFood
  Food <|-- EatingOutFood
  HomeFood <|-- ChickenBreast
  HomeFood <|-- Rice
  HomeFood <|-- ProteinBar

  class FoodFactory {
    + createHomeFood(name: String) : HomeFood
    + createEatingOutFood(name: String) : EatingOutFood
  }


