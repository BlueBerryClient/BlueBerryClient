package xyz.blackdev.Blueberry.utils.math;

public class MathUtils {
   private static final float[] SIN_TABLE = new float[4096];

   public static float clampFloat(float num, float min, float max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static int clampInt(int num, int min, int max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static float sin(float value) {
      return SIN_TABLE[(int)(value * 651.8986F) & 4095];
   }

   public static float cos(float value) {
      return SIN_TABLE[(int)(value * 651.8986F + 1024.0F) & 4095];
   }

   public static double easeOutBack(double x) {
      double c1 = 1.70158D;
      double c3 = c1 + 1.0D;
      return 1.0D + c3 * Math.pow(x - 1.0D, 3.0D) + c1 * Math.pow(x - 1.0D, 2.0D);
   }

   public static double easeInBack(double x) {
      return 1.0D - Math.cos(x * 3.141592653589793D / 2.0D);
   }

   public static int floorDouble(double value) {
      int i = (int)value;
      return value < (double)i ? i - 1 : i;
   }

   public static float floorDoubleToFloat(double value) {
      float i = (float)value;
      return value < (double)i ? i - 1.0F : i;
   }

   public static int absInt(int value) {
      return value >= 0 ? value : -value;
   }

   static {
      for(int i = 0; i < 4096; ++i) {
         SIN_TABLE[i] = (float)Math.sin((double)i * 3.141592653589793D * 2.0D / 4096.0D);
      }

   }
}
