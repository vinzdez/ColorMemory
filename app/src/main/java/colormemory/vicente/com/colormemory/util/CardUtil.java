package colormemory.vicente.com.colormemory.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import colormemory.vicente.com.colormemory.R;

/**
 * Created by Vicente on 4/21/2016.
 */
public class CardUtil {

    private static final int[] cards = new int[]{
            R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6,
            R.drawable.card7, R.drawable.card8, R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6,
            R.drawable.card7, R.drawable.card8
    };

    public static Map<Integer, Integer> shuffleCards() {
        List<Integer> myArray = new ArrayList<>();
        Map<Integer, Integer> cardImageMap = new HashMap<>();
        for (int card : cards) {
            myArray.add(card);
        }
        Collections.shuffle(myArray);

        for (int x = 0; x < cards.length; x++) {
            cardImageMap.put(x, myArray.get(x));
        }
        return cardImageMap;
    }
}
