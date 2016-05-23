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

    private static final int[] cards = new int[]{R.drawable.color1, R.drawable.color2, R.drawable.color3, R.drawable.color4,
            R.drawable.color5, R.drawable.color6, R.drawable.color7, R.drawable.color8, R.drawable.color1,
            R.drawable.color2, R.drawable.color3, R.drawable.color4, R.drawable.color5, R.drawable.color6,
            R.drawable.color7, R.drawable.color8};

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
