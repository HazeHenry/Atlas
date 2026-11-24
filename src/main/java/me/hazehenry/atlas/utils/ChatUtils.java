package me.hazehenry.atlas.utils;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {

    public static String process(String input) {
        if (input == null) return "";

        Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]{6})>");
        Matcher matcher = hexPattern.matcher(input);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder replacement = new StringBuilder("§x");
            for (char c : hex.toCharArray()) {
                replacement.append('§').append(c);
            }
            matcher.appendReplacement(sb, replacement.toString());
        }

        matcher.appendTail(sb);

        String message = sb.toString();

        IridiumColorAPI.process(message);
        message = ChatColor.translateAlternateColorCodes('&', message);

        return message;
    }

    public static List<String> process(List<String> input) {
        if (input == null) return new ArrayList<>();

        List<String> finalList = new ArrayList<>();

        for (String line : input) {
            Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]{6})>");
            Matcher matcher = hexPattern.matcher(line);
            StringBuilder sb = new StringBuilder();

            while (matcher.find()) {
                String hex = matcher.group(1);
                StringBuilder replacement = new StringBuilder("§x");
                for (char c : hex.toCharArray()) {
                    replacement.append('§').append(c);
                }
                matcher.appendReplacement(sb, replacement.toString());
            }

            matcher.appendTail(sb);

            String message = sb.toString();

            IridiumColorAPI.process(message);
            message = ChatColor.translateAlternateColorCodes('&', message);

            finalList.add(message);
        }

        return finalList;
    }
}
