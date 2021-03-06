package net.xiaoyu233.fml;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import net.xiaoyu233.fml.asm.annotations.Dist;
import net.xiaoyu233.fml.config.Config;
import net.xiaoyu233.fml.config.JsonConfig;
import net.xiaoyu233.fml.util.ModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FishModLoader {
   public static final Logger logger = LogManager.getLogger("FishModLoader");
   private static final ArrayList<ModInfo> mods = new ArrayList();
   private static final Map<String, ModInfo> modsMap = new HashMap();
   private static final Map<String, ModInfo> modsMapForLoginCheck;
   private static final boolean allowsClientMods;
   private static final boolean sideSett = false;
   private static final boolean isServer = false;
   public static final String VERSION = "B0.1.4";
   public static final int VERSION_NUM = 5;
   private static final int fpsLimit = 0;
   private static final String onlineVersion = versionCheck();
   public static JsonConfig config;

   public static void addModInfo(ModInfo modInfo) {
      mods.add(modInfo);
      modsMap.put(modInfo.getModid(), modInfo);
      if (modInfo.canBeUsedAt(Dist.CLIENT)) {
         modsMapForLoginCheck.put(modInfo.getModid(), modInfo);
      }

   }

   public static JsonElement getModsJson() {
      return (new Gson()).toJsonTree(mods);
   }

   public static void setIsServer(boolean isServer) {
      isServer = isServer;
   }

   public static String getOnlineVersion() {
      return onlineVersion;
   }

   public static boolean isServer() {
      return isServer;
   }

   public static String versionCheck() {
      try {
         URL url = new URL("https://raw.githubusercontent.com/XiaoYuOvO/FishModLoader/master/VERSION.txt");
         HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
         SSLContext sc = SSLContext.getInstance("TLSv1.2");
         sc.init(null, null, new SecureRandom());
         connection.setSSLSocketFactory(sc.getSocketFactory());
         connection.setRequestMethod("GET");
         connection.setDoInput(true);
         connection.setInstanceFollowRedirects(false);
         connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:74.0) Gecko/20100101 Firefox/74.0");
         connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
         connection.setRequestProperty("Accept-Encoding", "text");
         connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
         connection.setRequestProperty("Cache-Control", "max-age=0");
         connection.setRequestProperty("Host", "raw.githubusercontent.com");
         connection.setRequestProperty("Connection", "keep-alive");
         connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
         connection.connect();
         return (new Scanner(connection.getInputStream(), String.valueOf(StandardCharsets.UTF_8))).nextLine();
      } catch (IOException var3) {
      } catch (KeyManagementException | NoSuchAlgorithmException var4) {
         var4.printStackTrace();
      }

      return null;
   }

   private static void saveDefault(Config var0) {
      var0.set("jarPath", "server.jar");
      var0.set("debug", Boolean.FALSE);
      var0.set("dumpClass", Boolean.FALSE);
      var0.set("printClassLoadInfo", Boolean.FALSE);
      var0.set("fpsLimit", 120);
      var0.save();
   }

   public static Map<String, ModInfo> getModsMapForLoginCheck() {
      return new HashMap(modsMapForLoginCheck);
   }

   public static Map<String, ModInfo> getModsMap() {
      return new HashMap(modsMap);
   }

   public static void loadConfig() {
//      File config = new File(System.getProperty("user.dir"));
//      if (!(new File(config, "config.json")).exists()) {
//         config = new JsonConfig(new File(config, "config.json"));
//         saveDefault(config);
//         config.load();
//      } else {
//         config = new JsonConfig(new File(config, "config.json"));
//         config.load();
//         if (!config.has("fpsLimit")) {
//            config.set("fpsLimit", 0);
//            config.save();
//         }
//
//         fpsLimit = Math.abs(config.getInt("fpsLimit"));
//      }

   }

   public static int getFpsLimit() {
      return fpsLimit;
   }

   public static boolean isAllowsClientMods() {
      return allowsClientMods;
   }

   static {
      try {
         UIManager.setLookAndFeel(new WindowsLookAndFeel());
      } catch (UnsupportedLookAndFeelException var1) {
         var1.printStackTrace();
      }

      if (isServer()) {
         allowsClientMods = config.get("allowsClientMods");
      } else {
         allowsClientMods = true;
      }

      modsMapForLoginCheck = new HashMap();
      addModInfo(new ModInfo("FishModLoader", "B0.1.4", 5, Dist.SERVER, Dist.CLIENT));
   }
}
