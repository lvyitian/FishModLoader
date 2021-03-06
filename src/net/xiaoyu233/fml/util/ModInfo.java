package net.xiaoyu233.fml.util;

import com.google.common.collect.Lists;
import net.xiaoyu233.fml.asm.annotations.Dist;

import java.util.ArrayList;

public class ModInfo {
   private final String modid;
   private final String modVerStr;
   private final int modVerNum;
   private final ArrayList<Dist> dists;

   public ModInfo(String modid, String modVerStr, int modVerNum, Dist... dist) {
      this.modid = modid;
      this.modVerStr = modVerStr;
      this.modVerNum = modVerNum;
      this.dists = Lists.newArrayList(dist);
   }

   public int getModVerNum() {
      return this.modVerNum;
   }

   public String getModid() {
      return this.modid;
   }

   public String getModVerStr() {
      return this.modVerStr;
   }

   public boolean canBeUsedAt(Dist dist) {
      return this.dists.contains(dist);
   }
}
