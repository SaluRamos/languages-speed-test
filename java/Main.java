import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        long startFib = System.currentTimeMillis();
        fib(34);
        long finishFib = System.currentTimeMillis();

        double[] vector3_1 = {randomUniform(1, 10), randomUniform(1, 10), randomUniform(1, 10)};
        double[] vector3_2 = {randomUniform(1, 10), randomUniform(1, 10), randomUniform(1, 10)};
        double radius1 = randomUniform(1, 10);
        double radius2 = randomUniform(1, 10);
        double radius3 = randomUniform(1, 10);
        long startGeometry = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            testGeometry(vector3_1, vector3_2, radius1, radius2, radius3);
        }
        long finishGeometry = System.currentTimeMillis();

        long startData = System.currentTimeMillis();
        String resultData = loadData();
        long finishData = System.currentTimeMillis();
        System.out.println("data hash = " + md5(resultData));

        System.out.println("Java:");
        System.out.println("teste fib: " + (finishFib - startFib) + " ms");
        System.out.println("teste geometry: " + (finishGeometry - startGeometry) + " ms");
        System.out.println("teste data: " + (finishData - startData) + " ms\n");
    }

    public static int fib(int n) {
        if (n == 0 || n == 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    public static void testGeometry(double[] vector3_1, double[] vector3_2, double radius1, double radius2, double radius3) {
        double distance = Math.sqrt(Math.pow(vector3_2[0] - vector3_1[0], 2) + Math.pow(vector3_2[1] - vector3_1[1], 2) + Math.pow(vector3_2[2] - vector3_1[2], 2));
        double distanceBetweenCenters = Math.sqrt(Math.pow(vector3_2[0] - vector3_1[0], 2) + Math.pow(vector3_2[1] - vector3_1[1], 2) + Math.pow(vector3_2[2] - vector3_1[2], 2));
        double sphereVolume = (4.0 / 3.0) * Math.PI * Math.pow(radius3, 3);
    }

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(str.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String loadData() {
        StringBuilder data = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    public static double randomUniform(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}