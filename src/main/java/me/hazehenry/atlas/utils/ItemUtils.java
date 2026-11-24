package me.hazehenry.atlas.utils;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class ItemUtils {

    public static ItemStack createHead(String textureValue) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        String decoded = new String(
                java.util.Base64.getDecoder().decode(textureValue),
                java.nio.charset.StandardCharsets.UTF_8
        );

        com.google.gson.JsonObject json =
                com.google.gson.JsonParser.parseString(decoded).getAsJsonObject();

        String textureUrl = json
                .getAsJsonObject("textures")
                .getAsJsonObject("SKIN")
                .get("url")
                .getAsString();

        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        try {
            textures.setSkin(new URL(textureUrl));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid texture URL", e);
        }

        profile.setTextures(textures);
        meta.setOwnerProfile(profile);

        head.setItemMeta(meta);
        return head;
    }

    public static ItemStack createHeadFromTexture(String textureUrl) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        try {
            textures.setSkin(new URL(textureUrl));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid head texture URL: " + textureUrl, e);
        }

        profile.setTextures(textures);
        meta.setOwnerProfile(profile);

        head.setItemMeta(meta);
        return head;
    }
}
