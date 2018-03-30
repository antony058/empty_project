package ru.bellintegrator.practice.user.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class HashGenerator {

    public static String sha256Hex(String source_str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(source_str.getBytes());

        return Base64.getEncoder().encodeToString(md.digest());
    }

    public static String generateStringToHash() {
        String randomString = UUID.randomUUID().toString() + System.currentTimeMillis();
        return randomString; // тут breakpoint, чтобы достать отсюда строку и протестить активацию юзера
    }
}
