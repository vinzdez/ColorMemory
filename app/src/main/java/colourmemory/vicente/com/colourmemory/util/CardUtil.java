package colourmemory.vicente.com.colourmemory.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import colourmemory.vicente.com.colourmemory.R;

/**
 * Created by Vicente on 4/21/2016.
 */
public class CardUtil {

    private static final int[] cards = new int[]{R.drawable.colour1, R.drawable.colour2, R.drawable.colour3, R.drawable.colour4,
            R.drawable.colour5, R.drawable.colour6, R.drawable.colour7, R.drawable.colour8, R.drawable.colour1,
            R.drawable.colour2, R.drawable.colour3, R.drawable.colour4, R.drawable.colour5, R.drawable.colour6,
            R.drawable.colour7, R.drawable.colour8};

    public static Map<Integer, Integer> shuffleCards() {
        List<Integer> myArray = new ArrayList<>();
        Map<Integer, Integer> cardImageMap = new HashMap<>();
        for (int i = 0; i < cards.length; i++) {
            myArray.add(cards[i]);
        }
        Collections.shuffle(myArray);

        for (int x = 0; x < cards.length; x++) {
            cardImageMap.put(x, myArray.get(x));
        }
        return cardImageMap;
    }

}
