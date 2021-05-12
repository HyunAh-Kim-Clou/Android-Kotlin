package mac.stranger.helloworld

import android.app.Activity
import android.os.Bundle

class Main: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val moduleJ2O: J2O = J2O()
        moduleJ2O.getObjectListFrom(this, "person.json")
    }
}