/**
 * A tokenizer for encoding and decoding LZ77-style compression tokens in a readable format.
 * Tokens are represented as: "^distance,length^".
 */
public class ReadableTokenizer implements Tokenizer {

    //TODO: TASK 4
    public String toTokenString(int distance, int length) {
        return "^"+distance+','+length+'^';
    }

    // TODO TASK 4
    public int[] fromTokenString(String tokenText, int index) {
        int[] res = new int[3];
        String str = "";
        int startIdx = index;
        index++;
        while(tokenText.charAt(index) != ','){
            str += tokenText.charAt(index);
            index++;
        }//am
        index++;
        res[0] = Integer.parseInt(str);
        str = "";
        while(tokenText.charAt(index) != '^'){
            str += tokenText.charAt(index);
            index++;
        }
        res[1] = Integer.parseInt(str);
        res[2] = index-startIdx+1;
        return res;
    }
}