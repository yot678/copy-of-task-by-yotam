/**
 * Class for performing LZ77 compression/decompression.
 */

/**
 * Class for performing compression/decompression loosely based on LZ77.
 */
public class LZLite {
    public static int MAX_WINDOW_SIZE = 65535;
    private int windowSize;
    private String slidingWindow;
    private Tokenizer tokenizer;

    //TODO: TASK 1
    public LZLite(int windowSize, boolean readable)
    {

        this.windowSize = windowSize;
        this.slidingWindow = "";
        if(readable){
            this.tokenizer = new ReadableTokenizer();
        } else{
            this.tokenizer = new LeanTokenizer();
        }


    }

    //TODO: TASK 2
    public void appendToSlidingWindow(String st) {
        this.slidingWindow+=st;
        if(this.slidingWindow.length()>this.windowSize){
            this.slidingWindow = slidingWindow.substring(this.slidingWindow.length()-this.windowSize);
        }
    }

    //TODO: TASK 3
    public String maxMatchInWindow(String input, int pos) {
        String res = "";
        String prevRes = "";
        while (slidingWindow.contains(res) && pos<input.length()){
            res += input.charAt(pos);
            pos++;
        }
        if(slidingWindow.contains(res)){
            return res;
        }
        return res.substring(0, res.length()-1);
    }

    //TODO: TASK 5
    public String zip(String input) {
        String res = "";
        int idx= 0;
        while(idx<input.length()){
            String maxText = maxMatchInWindow(input, idx);
            if(maxText.isEmpty()){
                appendToSlidingWindow(""+input.charAt(idx));
                res+=input.charAt(idx);
                idx++;
                continue;
            }

            String token = this.tokenizer.toTokenString(idx-input.indexOf(maxText), maxText.length());
            if(token.length()<maxText.length()){
                res+=token;
                idx += maxText.length();
                appendToSlidingWindow(maxText);
            } else{
                appendToSlidingWindow(""+input.charAt(idx));
                res += input.charAt(idx);
                idx += 1;
            }

        }
        return res;
    }

    //TODO: TASK 6
    public static String zipFileName(String fileName)
    {
        if(!fileName.endsWith(".txt")){
            return null;
        }
        return fileName.substring(0, fileName.length()-4) + ".lz77.txt";

    }

    //TODO: TASK 6
    public static String unzipFileName(String fileName) {
        if(!fileName.endsWith(".lz77.txt")){
            return null;
        }
        return fileName.substring(0, fileName.length()-9)+".decompressed.txt";
    }

    //TODO: TASK 7
    public static String zipFile(String file, int windowSize, boolean readable) {
        LZLite lz = new LZLite(windowSize, readable);
        String correctedName = zipFileName(file);
        String fileContent = FileUtils.readFile(file);
        FileUtils.writeFile(correctedName, lz.zip(fileContent));
        return correctedName;
    }

    //TODO: TASK 8
    public String unzip(String input) {
        String res = "";
        int idx = 0;
        while(idx<input.length()){
            if(input.charAt(idx) == '^'){
                int[] tokenArr = this.tokenizer.fromTokenString(input, idx);
                res += res.substring(res.length()-tokenArr[0], res.length()-tokenArr[0]+tokenArr[1]);
                idx+=tokenArr[2];
            }else{
                res += input.charAt(idx);
                idx++;
            }
        }
        return res;
    }

    //TODO: TASK 9
    public static String unzipFile(String file, int windowSize, boolean readable) {
        LZLite lz = new LZLite(windowSize, readable);
        String correctedName = unzipFileName(file);
        String fileContent = FileUtils.readFile(file);
        FileUtils.writeFile(correctedName, lz.unzip(fileContent));
        return correctedName;
    }

    //TODO: TASK 9
    public static void main(String[] args) {
        String zipFileName = zipFile("test_files/genesis.txt",
                MAX_WINDOW_SIZE, false);
        System.out.println(zipFileName);
        String unzipFile = unzipFile(zipFileName,
                MAX_WINDOW_SIZE, false);
        System.out.println("Unzip to " + unzipFile +" completed!");
    }
//am

    // DON'T DELETE THE GETTERS! THEY ARE REQUIRED FOR TESTING
    public int getWindowSize() {
        return windowSize;
    }

    public String getSlidingWindow() {
        return slidingWindow;
    }

    public Tokenizer getTokenizer() {
        return tokenizer;
    }
}
