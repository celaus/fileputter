/**
 * @author  Claus Matzinger (S0810307022)
 * @date    May 18, 2010
 * @file    Handler
 */

package broadcastListener;

/**
 *
 * 
 */
class Handler {

    public Handler() {
    }
    
    void post(Runnable backPoster) {
        new Thread(backPoster);
    }

}
