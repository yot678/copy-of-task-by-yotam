/**
 * A lightweight tokenizer for encoding and decoding.
 * Uses a compact three-character format to represent references in the compressed text.
 */
public class LeanTokenizer implements Tokenizer {
    public LeanTokenizer() {

    }

    //TODO: TASK 10
    public int[] fromTokenString(String tokenText, int index) {
        int[] resArr = new int[3];
        resArr[0] = tokenText.charAt(index+1);
        resArr[1] = tokenText.charAt(index+2);
        resArr[2] = 3;
        return resArr;
    }
//am
    //TODO: TASK 10
    public String toTokenString(int distance, int length) {
        return "^"+(char)distance +(char)length;
    }
}