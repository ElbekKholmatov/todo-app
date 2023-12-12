package com.example.todoapp.utils;


import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @Author ElbekKholmatov
 * @Date 08/12/2023
 */


@Component
public class BaseUtils {
    public static final String smsBody = """
            Psixologiktestlar.uz saytiga xush kelibsiz!
            Tasdiqlash kodi: %s
            """;

    @NotNull
    public static String backslashReplacer(@NotNull String element) {
        return element.replaceAll("\n", " ");
    }

}