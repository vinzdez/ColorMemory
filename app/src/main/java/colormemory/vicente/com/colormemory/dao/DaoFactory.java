package colormemory.vicente.com.colormemory.dao;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by vangcaway on 7/8/2015.
 */
public class DaoFactory {
  private static Realm REALM_INSTANCE;

  public static Realm getInstance(Context context) {
    if (REALM_INSTANCE == null) {
      // Create the Realm configuration
      RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
      REALM_INSTANCE = Realm.getInstance(realmConfig);
      return REALM_INSTANCE;
    } else {
      return REALM_INSTANCE;
    }
  }
}
