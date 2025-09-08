package service;

import java.util.List;
import java.util.Objects;

import domain.User;
import domain.dto.SignUpInfo;
import domain.enums.Allergy;
import repo.UserRepo;
import util.SHA256PasswordSecurity;

public class AuthenticationService {
    private final UserRepo userRepo;

    public AuthenticationService() {
        this.userRepo = new UserRepo();
    }

    public AuthenticationService(UserRepo userRepo) {
        this.userRepo = Objects.requireNonNull(userRepo);
    }

    /** 로그인: 성공 시 User, 실패 시 null */
    public User login(String id, String password) {
        if (id == null || id.isBlank() || password == null) return null;

        User user = userRepo.findById(id);
        if (user == null) return null;;

        boolean ok = SHA256PasswordSecurity.verifyPassword(
                password,
                user.getPasswordHash(),
                user.getPasswordSalt()
        );
        return ok ? user : null;
    }

    /** 회원가입 */
    public void signUp(SignUpInfo signUpInfo) {
        validateSignUp(signUpInfo);

        if (userRepo.findById(signUpInfo.getUserId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        
        if (!isValidPassword(signUpInfo.getPassword())) {
            throw new IllegalArgumentException("비밀번호는 8글자 이상, 특수문자 포함이어야 합니다.");
        }

        String salt = SHA256PasswordSecurity.generateSalt();
        String hash = SHA256PasswordSecurity.hashPassword(signUpInfo.getPassword(), salt);

        User user = new User(
                signUpInfo.getUserId(),
                hash,
                salt,
                signUpInfo.getCurrentWeight(),
                signUpInfo.getTargetWeight(),
                signUpInfo.getTargetProtein(),
                signUpInfo.getTargetCalories(),
                signUpInfo.getMinMeal(),
                signUpInfo.getAge(),
                signUpInfo.getHeight(),
                signUpInfo.getTargetWater(),
                normalizeAllergy(signUpInfo.getAllergy())
        );

        userRepo.save(user);
        System.out.println("회원가입 완료: " + signUpInfo.getUserId());
    }

    public User findById(String id) {
        return userRepo.findById(id);
    }
    
    private boolean isValidPassword(String pw) {
        return pw.matches("^(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$");
    }

    // ---------- 내부 유틸 ----------

    private void validateSignUp(SignUpInfo s) {
        if (s == null) throw new IllegalArgumentException("입력이 없습니다.");
        if (isBlank(s.getUserId()))  throw new IllegalArgumentException("아이디를 입력하세요.");
        if (isBlank(s.getPassword())) throw new IllegalArgumentException("비밀번호를 입력하세요.");

        if (s.getCurrentWeight() <= 0 || s.getCurrentWeight() > 500)
            throw new IllegalArgumentException("현재 체중 범위가 올바르지 않습니다.");
        if (s.getTargetWeight() <= 0 || s.getTargetWeight() > 500)
            throw new IllegalArgumentException("목표 체중 범위가 올바르지 않습니다.");
        if (s.getTargetProtein() < 0 || s.getTargetProtein() > 1000)
            throw new IllegalArgumentException("목표 단백질 범위가 올바르지 않습니다.");
        if (s.getTargetCalories() < 0 || s.getTargetCalories() > 20000)
            throw new IllegalArgumentException("목표 칼로리 범위가 올바르지 않습니다.");
        if (s.getMinMeal() < 1 || s.getMinMeal() > 12)
            throw new IllegalArgumentException("최소 끼니 수는 1~12 사이여야 합니다.");
        if (s.getAge() > 2031 || s.getAge() < 1800)
            throw new IllegalArgumentException("출생년도가 올바르지 않습니다. (유효 범위 1800~2030)");
        if (s.getHeight() < 50 || s.getHeight() > 300)
            throw new IllegalArgumentException("키 범위가 올바르지 않습니다.");
        if (s.getTargetWater() < 0 || s.getTargetWater() > 20000)
            throw new IllegalArgumentException("목표 수분 섭취량 범위가 올바르지 않습니다.");
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private List<Allergy> normalizeAllergy(List<Allergy> list) {
        return (list == null || list.isEmpty()) ? List.of() : List.copyOf(list);
    }
}
