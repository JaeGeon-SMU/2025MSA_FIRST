package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class SHA256PasswordSecurity {
	public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // SHA-256 알고리즘 사용
            md.update(salt.getBytes()); // Salt 추가
            byte[] hashedBytes = md.digest(password.getBytes()); // 비밀번호 해싱
            return Base64.getEncoder().encodeToString(hashedBytes); // Base64로 인코딩하여 반환
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 해싱 오류", e);
        }
    }

    // 랜덤 Salt 생성 메서드 (보안 강화)
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16바이트의 랜덤 Salt 생성
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // 비밀번호 검증 메서드 (입력된 비밀번호가 저장된 해시와 일치하는지 확인)
    public static boolean verifyPassword(String inputPassword, String storedHash, String salt) {
        String hashedInput = hashPassword(inputPassword, salt); // 입력된 비밀번호 해싱
        return storedHash.equals(hashedInput); // 기존 해시와 비교
    }
}
